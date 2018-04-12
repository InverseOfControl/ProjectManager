package com.ezendai.credit2.audit.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezendai.credit2.apply.model.Bank;
import com.ezendai.credit2.apply.model.BankAccount;
import com.ezendai.credit2.apply.model.BusinessLog;
import com.ezendai.credit2.apply.model.Guarantee;
import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.model.Person;
import com.ezendai.credit2.apply.model.Product;
import com.ezendai.credit2.apply.model.ProductDetail;
import com.ezendai.credit2.apply.service.BankAccountService;
import com.ezendai.credit2.apply.service.BankService;
import com.ezendai.credit2.apply.service.BusinessLogService;
import com.ezendai.credit2.apply.service.GuaranteeService;
import com.ezendai.credit2.apply.service.LoanService;
import com.ezendai.credit2.apply.service.PersonService;
import com.ezendai.credit2.apply.service.ProductDetailService;
import com.ezendai.credit2.apply.service.ProductService;
import com.ezendai.credit2.apply.vo.GuaranteeVO;
import com.ezendai.credit2.apply.vo.LoanVO;
import com.ezendai.credit2.apply.vo.ProductDetailVO;
import com.ezendai.credit2.audit.service.AbstractContractAuditService;
import com.ezendai.credit2.audit.service.ContractService;
import com.ezendai.credit2.audit.service.NetBusinessAuditService;
import com.ezendai.credit2.audit.service.NetBusinessContractService;
import com.ezendai.credit2.audit.service.PersonBankService;
import com.ezendai.credit2.audit.service.RepaymentPlanService;
import com.ezendai.credit2.audit.vo.GenerateContractVO;
import com.ezendai.credit2.audit.vo.RepaymentPlanVO;
import com.ezendai.credit2.framework.util.CollectionUtil;
import com.ezendai.credit2.framework.util.DateUtil;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.master.enumerate.EnumConstants.SerialNum;
import com.ezendai.credit2.master.service.BaseAreaService;
import com.ezendai.credit2.master.service.WorkPlaceInfoService;
import com.ezendai.credit2.rule.service.RepayDateRuleService;
import com.ezendai.credit2.rule.service.SignLoanRuleService;
import com.ezendai.credit2.system.model.SysLog;
import com.ezendai.credit2.system.service.SysLogService;
import com.ezendai.credit2.system.service.SysSerialNumService;

/**
 * <pre>
 * 小企业贷网商贷合同生成
 * </pre>
 */
@Service
public class NetBusinessAuditServiceImpl extends AbstractContractAuditService implements NetBusinessAuditService {

    @Autowired
    private LoanService loanService;

    @Autowired
    private PersonService personService;

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private RepaymentPlanService repaymentPlanService;

    @Autowired
    private ProductService productService;

    @Autowired
    private WorkPlaceInfoService workPlaceInfoService;

    @Autowired
    private ProductDetailService productDetailService;

    @Autowired
    private PersonBankService personBankService;

    @Autowired
    private GuaranteeService guaranteeService;

    @Autowired
    private BankService bankService;

    @Autowired
    private BusinessLogService businessLogService;

    @Autowired
    private SysSerialNumService sysSerialNumService;

    @Autowired
    private SysLogService sysLogService;

    @Autowired
    private BaseAreaService baseAreaService;

    @Autowired
    private RepayDateRuleService repayDateRuleService;

    @Autowired
    private SignLoanRuleService signLoanRuleService;

    @Autowired
    private NetBusinessContractService netBusinessContractService;

    @Autowired
    private ContractService contractService;

    protected static Log logger = LogFactory.getLog(NetBusinessAuditServiceImpl.class);

    @Override
    @Transactional
    public void createdContract(GenerateContractVO generateContractVO) {
        Long loanId = Long.valueOf(generateContractVO.getLoanId());
        Loan loan = loanService.get(loanId);
        if (loan != null) {
            Long personId = loan.getPersonId();
            Person person = personService.get(personId);
            // 已经生成过合同-删除原先信息
            processOrginalData(generateContractVO,loan);
            // 开户人银行帐号信息已经存在则不保存，否则将客户账号信息保存到bank_account表中
            Bank bank = processBankInfoAndPersonBank(generateContractVO, loan);
            // 更新贷款信息还款银行和放款银行
            Date contractCreatedDate = new Date();// 小企业贷没有合同日期选择
            LoanVO loanVO = new LoanVO();
            loanVO.setId(loanId);
            loanVO.setContractSrc(generateContractVO.getContractSrc());
            loanVO.setGrantAccountId(bank.getId());
            loanVO.setRepayAccountId(bank.getId());
            // 签约日期
            loanVO.setSignDate(contractCreatedDate);

            String contractNo = createdContractNo(loan.getSalesDeptId(),"ZDB-SME-WS",SerialNum.CONTRACT4);
            loanVO.setContractNo(contractNo);
            // 担保人
            GuaranteeVO guaranteeVO = new GuaranteeVO();
            guaranteeVO.setLoan(loan);
            guaranteeVO.setPersonId(personId);
            guaranteeVO.setFlag(EnumConstants.YesOrNo.YES.getValue());
            List<Guarantee> guaranteeList = guaranteeService.findListByVo(guaranteeVO);
            if (CollectionUtil.isNotEmpty(guaranteeList)) {
                for (Guarantee g : guaranteeList) {
                    processGuarntee(generateContractVO, loan, guaranteeVO, g);
                }
            }
            Product product = productService.get(loan.getProductId());
            ProductDetailVO productDetailVO = new ProductDetailVO();
            productDetailVO.setProductId(product.getId());
            productDetailVO.setTerm(loan.getAuditTime());
            productDetailVO.setMemberType(loan.getAuditMemberType());
            ProductDetail productDetail = productDetailService.getProductDetailByVO(productDetailVO);
            // 期数
            Integer auditTime = loan.getAuditTime();
            // 风险金比率
            BigDecimal riskRate = productDetail.getRiskRate();
            // 网商贷机构返利费率
            BigDecimal thirdFeeRate = productDetail.getThirdFeeRate();
            // 月利率
            BigDecimal monthRate = productDetail.getMonthRate();
            // 咨询费比例
            BigDecimal consultingFeeRate = product.getConsultingFeeRate();
            // 评估费比例
            BigDecimal assessmentFeeRate = product.getAssessmentFeeRate();
            // 期缴管理费比例
//            BigDecimal manageFeeRate = productDetail.getManageFeeRate();
            // 月综合费率
            BigDecimal sumRate = productDetail.getSumRate();
            // 提前还款违约金
            BigDecimal penaltyRate = product.getPenaltyRate();
            // 还款开始日期
            Date startRepayDate = new Date();
            Date repayDay = generateRepayDate(generateContractVO, contractCreatedDate, loanVO, product, productDetail);
            startRepayDate = repayDay;
            Date endRepayDate = DateUtil.getNowDateAfter(auditTime - 1, repayDay);
            // 审核金额
            BigDecimal auditMoney = loan.getAuditMoney();
            //月偿还本息金额
            BigDecimal averageCapital = new BigDecimal(String.valueOf(PMT(monthRate.doubleValue(), auditTime.doubleValue(), auditMoney.doubleValue()))).setScale(2, BigDecimal.ROUND_HALF_UP);
            // 乙方管理费
            BigDecimal manageFee = auditMoney.multiply(manageFeeRate(averageCapital,sumRate,auditMoney,auditTime)).setScale(2, BigDecimal.ROUND_HALF_UP);
            // 还款金额 = 每月应减去管理费 + 当期管理费
            BigDecimal repayAmount = averageCapital.add(manageFee);
            // 违约金
            BigDecimal penalty = auditMoney.multiply(penaltyRate).setScale(2, BigDecimal.ROUND_HALF_UP);

            // 还款计划
            BigDecimal totalPrincipalAmt = BigDecimal.ZERO; // 总还本金
            BigDecimal totalInterestAmt = BigDecimal.ZERO; // 总还利息
            List<RepaymentPlanVO> repaymentPlanList = new ArrayList<RepaymentPlanVO>();
            BigDecimal oneTimeRepaymentAmount = BigDecimal.ZERO;
            // 当期归还本金
            BigDecimal principalAmt = BigDecimal.ZERO;
            // 当期归还利息(还款利息)
            BigDecimal interestAmt = BigDecimal.ZERO;
            // 剩余本金
            BigDecimal remainingPrincipal = auditMoney;
            // 第1期到第n-1期
            for (int curNum = 1; curNum < auditTime; curNum++) {
                // 生成还款信息
                RepaymentPlanVO repaymentPlanVO = new RepaymentPlanVO();
                // 当前期数
                repaymentPlanVO.setCurNum(curNum);
                repaymentPlanVO.setLoanId(loan.getId());
                repaymentPlanVO.setStatus(EnumConstants.RepaymentPlanState.NOT_REPAYMENT.getValue());
                if (curNum == 1) { // 第一期
                    repaymentPlanVO.setRepayDay(DateUtil.formatDate(repayDay));
                    repaymentPlanVO.setPenaltyDate(DateUtil.formatDate(repayDay));
                } else {
                    repayDay = DateUtil.getNowDateAfter(1, repayDay);
                    repaymentPlanVO.setRepayDay(DateUtil.formatDate(repayDay));
                    repaymentPlanVO.setPenaltyDate(DateUtil.formatDate(repayDay));
                }
                repaymentPlanVO.setAverageCapital(averageCapital);
                // 还款金额 = 每月等额本息+管理费
                repaymentPlanVO.setRepayAmount(repayAmount);
                // 当期归还利息(还款利息)
                interestAmt = remainingPrincipal.multiply(monthRate).setScale(2, BigDecimal.ROUND_HALF_UP);
                repaymentPlanVO.setInterestAmt(interestAmt);
                repaymentPlanVO.setCurRemainingInterestAmt(interestAmt);
                // 当期归还本金
                principalAmt = averageCapital.subtract(interestAmt).setScale(2, BigDecimal.ROUND_HALF_UP);
                repaymentPlanVO.setPrincipalAmt(principalAmt);
                repaymentPlanVO.setCurRemainingPrincipal(principalAmt);
                // 剩余本金
                remainingPrincipal = remainingPrincipal.subtract(principalAmt).setScale(2, BigDecimal.ROUND_HALF_UP);
                repaymentPlanVO.setRemainingPrincipal(remainingPrincipal);
                // 当期一次性还款金额 = 剩余本金 + 还款金额 + 违约金
                oneTimeRepaymentAmount = remainingPrincipal.add(repayAmount).add(penalty).setScale(2, BigDecimal.ROUND_HALF_UP);
                repaymentPlanVO.setOneTimeRepaymentAmount(oneTimeRepaymentAmount);

                repaymentPlanVO.setPenalty(penalty);
                repaymentPlanVO.setCurRemainingManagePart0Fee(manageFee);
                repaymentPlanVO.setCurRemainingManagePart1Fee(BigDecimal.ZERO);
                repaymentPlanVO.setCurRemainingEvalRate(BigDecimal.ZERO);
                repaymentPlanVO.setCurRemainingReferRate(BigDecimal.ZERO);
                repaymentPlanVO.setCurRemainingRisk(BigDecimal.ZERO);
                repaymentPlanVO.setManagePart0Fee(manageFee);
                BigDecimal deficit = repaymentPlanVO.getCurRemainingInterestAmt().add(repaymentPlanVO.getCurRemainingManagePart0Fee()).add(repaymentPlanVO.getCurRemainingManagePart1Fee())
                        .add(repaymentPlanVO.getCurRemainingPrincipal()).add(repaymentPlanVO.getCurRemainingReferRate()).add(repaymentPlanVO.getCurRemainingEvalRate())
                        .add(repaymentPlanVO.getCurRemainingRisk()).setScale(2, BigDecimal.ROUND_HALF_UP);
                repaymentPlanVO.setDeficit(deficit);
                repaymentPlanList.add(repaymentPlanVO);
                totalPrincipalAmt = totalPrincipalAmt.add(principalAmt);
                totalInterestAmt = totalInterestAmt.add(interestAmt);
            }
            // 最后1期
            RepaymentPlanVO repaymentPlanVO = new RepaymentPlanVO();
            repaymentPlanVO.setLoanId(loan.getId());
            repaymentPlanVO.setCurNum(auditTime);
            repaymentPlanVO.setAverageCapital(averageCapital);
            // 还款金额 = 每月等额本息
            repaymentPlanVO.setRepayAmount(repayAmount);
            // 当期归还本金
            principalAmt = auditMoney.subtract(totalPrincipalAmt).setScale(2, BigDecimal.ROUND_HALF_UP);
            repaymentPlanVO.setPrincipalAmt(principalAmt);
            repaymentPlanVO.setCurRemainingPrincipal(principalAmt);
            // 当期归还利息
            interestAmt = averageCapital.subtract(principalAmt).setScale(2, BigDecimal.ROUND_HALF_UP);
            repaymentPlanVO.setInterestAmt(interestAmt);
            repaymentPlanVO.setCurRemainingInterestAmt(interestAmt);
            totalPrincipalAmt = totalPrincipalAmt.add(principalAmt).setScale(2, BigDecimal.ROUND_HALF_UP);
            totalInterestAmt = totalInterestAmt.add(interestAmt).setScale(2, BigDecimal.ROUND_HALF_UP);
            // 最后一期剩余本金,提前还款违约金
            repaymentPlanVO.setPenalty(BigDecimal.ZERO);
            repaymentPlanVO.setRemainingPrincipal(BigDecimal.ZERO);
            // 最后一期的一次性还款金额 =还款金额
            oneTimeRepaymentAmount = averageCapital;
            repaymentPlanVO.setOneTimeRepaymentAmount(repayAmount);
            repayDay = DateUtil.getNowDateAfter(1, repayDay);
            repaymentPlanVO.setRepayDay(DateUtil.formatDate(repayDay));
            repaymentPlanVO.setPenaltyDate(DateUtil.formatDate(repayDay));
            repaymentPlanVO.setCurRemainingManagePart0Fee(manageFee);
            repaymentPlanVO.setCurRemainingManagePart1Fee(BigDecimal.ZERO);
            repaymentPlanVO.setCurRemainingEvalRate(BigDecimal.ZERO);
            repaymentPlanVO.setCurRemainingReferRate(BigDecimal.ZERO);
            repaymentPlanVO.setCurRemainingRisk(BigDecimal.ZERO);
            repaymentPlanVO.setManagePart0Fee(manageFee);
            BigDecimal deficit = repaymentPlanVO.getCurRemainingInterestAmt().add(repaymentPlanVO.getCurRemainingManagePart0Fee()).add(repaymentPlanVO.getCurRemainingManagePart1Fee())
                    .add(repaymentPlanVO.getCurRemainingPrincipal()).add(repaymentPlanVO.getCurRemainingReferRate()).add(repaymentPlanVO.getCurRemainingEvalRate())
                    .add(repaymentPlanVO.getCurRemainingRisk()).setScale(2, BigDecimal.ROUND_HALF_UP);
            repaymentPlanVO.setDeficit(deficit);
            repaymentPlanVO.setStatus(EnumConstants.RepaymentPlanState.NOT_REPAYMENT.getValue());
            repaymentPlanList.add(repaymentPlanVO);
            // 处理LOAN
            loanVO.setTime(auditTime.longValue());
            loanVO.setMonthRate(monthRate);
            // 风险金
            BigDecimal riskFee = auditMoney.multiply(riskRate).setScale(2, BigDecimal.ROUND_HALF_UP);
            loanVO.setRisk(riskFee);
            // 网商
            BigDecimal thirdFee = auditMoney.multiply(thirdFeeRate).setScale(2, BigDecimal.ROUND_HALF_UP);
            loanVO.setThirdFee(thirdFee);
            // 咨询费
            BigDecimal consultFee = auditMoney.multiply(consultingFeeRate).setScale(2, BigDecimal.ROUND_HALF_UP);
            loanVO.setConsult(consultFee);
            // 评估费
            BigDecimal assessmentFee = auditMoney.multiply(assessmentFeeRate).setScale(2, BigDecimal.ROUND_HALF_UP);
            loanVO.setAssessment(assessmentFee);
            // 乙方管理费
            loanVO.setbManage(manageFee);
            // 写入还款计划
            for (RepaymentPlanVO plan : repaymentPlanList) {
                repaymentPlanService.insert(plan);
            }
            // 生成合同--TBD
            netBusinessContractService.createNetBusinessPersonLoanContract(loan, person, contractNo);
            netBusinessContractService.createNetBusinessLoanContract(loan, person, repaymentPlanVO, endRepayDate, startRepayDate, generateContractVO, contractNo);
            netBusinessContractService.createNetBusinessRepaymentContract(loan, person, repaymentPlanVO, contractNo, loanVO.getConsult(), loanVO, generateContractVO);
            netBusinessContractService.createNetBusinessEntrustContract(loan, person, generateContractVO, contractNo);
            netBusinessContractService.createNetBusinessRepaymentFundContract(loan, person, repaymentPlanVO, contractNo, startRepayDate);
            // 担保人合同
            GuaranteeVO gVO = new GuaranteeVO();
            gVO.setLoan(loan);
            gVO.setPersonId(personId);
            gVO.setFlag(EnumConstants.YesOrNo.YES.getValue());
            List<Guarantee> gList = guaranteeService.findListByVo(gVO);
            if (CollectionUtil.isNotEmpty(gList)) {

                for (Guarantee guarantee : gList) {

                    if (guarantee.getGuaranteeType().compareTo(1) == 0) {
                        netBusinessContractService.createNetBusinessNaturalLegalContract(loan, person, endRepayDate, startRepayDate, generateAgreeNo(contractNo), guarantee.getName());
                    } else {
                        BankAccount guarantBankAccount = bankAccountService.get(guarantee.getBankAccountId());
                        netBusinessContractService.createNetBusinessNaturalGuaranteeContract(loan, person, endRepayDate, startRepayDate, generateAgreeNo(contractNo), guarantee);
                        netBusinessContractService.createNetBusinessEntrustGuaranteeContract(loan, person, guarantBankAccount, generateAgreeNo(contractNo), guarantee);
                    }

                }
            }
            GUARANTEE_SEQ = 5; //每次生成完合同后,重新初始化为原值
            loanVO.setStartRepayDate(DateUtil.formatDate(startRepayDate));
            loanVO.setEndRepayDate(DateUtil.formatDate(endRepayDate));
            loanVO.setPactMoney(auditMoney);
            loanVO.setContractCreatedTime(contractCreatedDate);
            loanVO.setRepaymentMethod(EnumConstants.RepaymentMethod.AVERAGE_CAPITAL_PLUS_INTEREST.getValue());
            // 放款金额=合同金额-风险金-咨询费-评估费
            BigDecimal grantMoney = loanVO.getPactMoney().subtract(loanVO.getRisk()).subtract(loanVO.getConsult()).subtract(loanVO.getAssessment());
            loanVO.setGrantMoney(grantMoney);
            // 还款日
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(loanVO.getEndRepayDate());
            loanVO.setReturnDate(calendar.get(Calendar.DATE));
            loanVO.setResidualPactMoney(auditMoney);
            loanVO.setResidualTime(auditTime);
            // 更新贷款信息表
            loanService.update(loanVO);
            // 插入日志
            BusinessLog businessLog = new BusinessLog();
            businessLog.setLoanId(loanId);
            businessLog.setMessage("合同生成");
            businessLog.setFlowStatus(EnumConstants.BusinessLogStatus.CONTRACT_AUDIT.getValue());
            businessLogService.insert(businessLog);

            // 插入系统日志
            SysLog sysLog = new SysLog();
            sysLog.setOptModule(EnumConstants.OptionModule.CONTRACT.getValue());
            sysLog.setOptType(EnumConstants.OptionType.CREATE_CONTRACT.getValue());
            sysLog.setRemark("借款ID   " + loanId.toString());
            sysLogService.insert(sysLog);

        }
    }

   


    /**
     * <pre>
     * 删除原先数据
     * 1.合同;2.还款计划;3.银行信息;4.客户银行卡关联
     * </pre>
     *
     * @param loan
     */
   /* private void processOrginalData(Loan loan) {
        // 已经生成过合同
        if (StringUtils.isNotEmpty(loan.getContractNo())) {
            // 删除还款计划
            repaymentPlanService.deleteRepaymentPlanByLoanId(loan.getId());
            // 删除合同信息
            contractService.deleteContractByLoanId(loan.getId());
            // 删除银行账户信息和客户银行关联
            PersonBankVO personBankVO = new PersonBankVO();
            personBankVO.setPersonId(loan.getPersonId());
            personBankVO.setLoanId(loan.getId());
            List<PersonBank> personBankList = personBankService.findPersonBankList(personBankVO);
            if (CollectionUtil.isNotEmpty(personBankList)) {
                for (PersonBank personBank : personBankList) {
                    Long bankAccountId = personBank.getBankAccountId();
                    bankAccountService.deleteById(bankAccountId);
                    personBankService.deletePersonBank(personBank.getId());
                }
            }
        }
    }*/

    /**
     * <pre>
     *
     * </pre>
     *
     * @param generateContractVO
     * @param loan
     * @return
     */
  /*  private Bank processBankInfoAndPersonBank(GenerateContractVO generateContractVO, Loan loan) {
        // 借款人
        BankAccount personBankAccount = new BankAccount();
        personBankAccount.setAccount(generateContractVO.getBankAccount());
        personBankAccount.setBranchName(generateContractVO.getBankBranch());
        Bank bank = bankService.get(Long.valueOf(generateContractVO.getBank()));
        personBankAccount.setBank(bank);
        personBankAccount.setBankName(bank.getBankName());
        personBankAccount.setStatus(EnumConstants.BankAccountStatus.ENABLED.getValue());
        personBankAccount = bankAccountService.insert(personBankAccount);

        // 插入客户银行关联
        PersonBank personBank = new PersonBank();
        personBank.setBankAccountId(personBankAccount.getId());
        personBank.setPersonId(loan.getPersonId());
        personBank.setLoanId(loan.getId());
        personBankService.insertPersonBank(personBank);
        return bank;
    }*/

    /**
     * <pre>
     * 生成规则：ZDB-SME-WS+城市编号(work_place_info.zone_code)+yyyymmdd+3位数字序列号(当天第N份合同)
     * 例:ZDB-SME-WS002120150625001表示: 上海地区,20150625第1份网商贷合同
     * </pre>
     *
     * @param loanId
     * @param contractCreatedDate
     * @return
     */
   /* @Transactional
    private String createdContractNo(Long baseAreaId) {
        StringBuilder contractNo = new StringBuilder();
        contractNo.append("ZDB-SME-WS");
        // 地区
        BaseArea baseArea = baseAreaService.get(baseAreaId);
        Long workPlaceInfoId = baseArea.getWorkPlaceInfoId();
        WorkPlaceInfo workPlaceInfo = workPlaceInfoService.loadOneWorkPlaceInfoById(workPlaceInfoId);
        contractNo.append(workPlaceInfo.getZoneCode());
        // 获取当前日期
        String nowDate = DateUtil.getDate(DateUtil.getToday(), DateUtil.pattern_day);
        contractNo.append(nowDate);
        // 3位数字序列号
        Long seqNumber;
        // 网商贷合同
        SerialNumResult serialNumResult = sysSerialNumService.getSerialNum(SerialNum.CONTRACT4);
        seqNumber = serialNumResult.getSeq();
        // 流水号不足3位补0
        DecimalFormat df = new DecimalFormat("000");
        String seq = df.format(seqNumber);
        contractNo.append(seq);
        return contractNo.toString();
    }*/


    /**
     * 固定还款日规则日期变化 如签约日为1-15号，还款日为下月1号，否则为下月16号
     */
//    private Date getRepayRuleDate(Date contractCreatedDate) {
//        Calendar c = Calendar.getInstance();
//        c.setTime(contractCreatedDate);
//        int year = c.get(Calendar.YEAR);
//        int month = c.get(Calendar.MONTH);
//        int day = c.get(Calendar.DAY_OF_MONTH);
//        if (day > 15) {
//            c.set(year, month + 1, 16);
//        } else {
//            c.set(year, month + 1, 1);
//        }
//        return c.getTime();
//    }

    /**
     * 特殊签单日期变化
     *
     * @param date
     * @return
     */
/*    private Date getSignRuleDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        if (day == 16) {
            c.set(year, month + 1, 1);
        } else {
            c.set(year, month, 16);
        }
        return c.getTime();

    }*/

    /**
     * 非固定还款日规则日期变化 7月27号签约，8月27号首期还款；28-31号签约，统一为下月28号还款
     *
     * @return
     */
   /* private Date getNotRepayRuleDate(Date contractCreatedDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(contractCreatedDate);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        if (day >= 28) {
            c.set(year, month + 1, 28);
        } else {
            c.set(year, month + 1, day);
        }
        return c.getTime();
    }
*/
  /*  private static String generateAgreeNo(String contractNo) {
        String contractNum = "";
		DecimalFormat df=new DecimalFormat("-000");
		contractNum=contractNo+ df.format(GUARANTEE_SEQ);
        GUARANTEE_SEQ = GUARANTEE_SEQ + 1;
        return contractNum;
    }*/

    /**
     * 计算月供
     *
     * @param rate          月利率
     * @param term          贷款期数，单位月
     * @param financeAmount 贷款金额
     * @return
     */
    private double PMT(double rate, double term, double financeAmount) {
        double v = (1 + rate);
        double t = (-(term / 12) * 12);
        double result = (financeAmount * rate) / (1 - Math.pow(v, t));
        return result;
    }
   /**
    * 计算期缴管理费率
    * @param averageCapital
    * @param sumRate
    * @param auditMoney
    * @param auditTime
    * @return
    */
    private BigDecimal manageFeeRate(BigDecimal averageCapital,
			BigDecimal sumRate, BigDecimal auditMoney, Integer auditTime) {
    	
    	BigDecimal auditTimeBD=new BigDecimal(auditTime) ;
    	return  sumRate.subtract(averageCapital.subtract(auditMoney.divide(auditTimeBD,8,BigDecimal.ROUND_HALF_UP)).divide(auditMoney,10,BigDecimal.ROUND_HALF_UP)); 
		
	}

}
