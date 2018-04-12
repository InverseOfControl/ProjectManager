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
import com.ezendai.credit2.audit.service.CityWideContractAuditService;
import com.ezendai.credit2.audit.service.CityWideContractService;
import com.ezendai.credit2.audit.service.ContractService;
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

/***
 * 
 * <pre>
 * 新的合同生成服务,用来生成小企业贷的新产品:同城小微贷和同城POS贷
 * </pre>
 * 
 * @author HQ-AT6
 * @version $Id: ApplyServiceImpl.java, v 0.1 2014年6月24日 上午9:04:27 HQ-AT6 Exp $
 */
@Service
public class CityWideContractAuditServiceImpl extends AbstractContractAuditService implements CityWideContractAuditService {

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
	private CityWideContractService cityWideContractService;
	
	@Autowired
	private ContractService contractService;
	
	protected static Log logger = LogFactory.getLog(CityWideContractAuditServiceImpl.class);

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

			String contractNo = createdContractNo(loan.getSalesDeptId(),"ZDB-SME-TC",SerialNum.CONTRACT3);
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
			ProductDetail productDetail = productDetailService.getProductDetailByVO(productDetailVO);
			// 期数
			Integer auditTime = loan.getAuditTime();
			// 风险金比率
			BigDecimal riskRate = product.getRiskRate();
			// 丙方管理费费率
			//BigDecimal managePartRate = product.getManagePartRate();
			// 同城费费率
			BigDecimal thirdFeeRate = product.getThirdFeeRate();
			// 月利率
			BigDecimal monthRate = productDetail.getYearRate().divide(new BigDecimal("12"));
			// 咨询费比例
			BigDecimal consultingFeeRate = product.getConsultingFeeRate();
			// 评估费比例
			BigDecimal assessmentFeeRate = product.getAssessmentFeeRate();
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
			BigDecimal auditTimeBg = new BigDecimal(auditTime);// 审核期数
			// 每月等额本息
			BigDecimal a = sumRate.multiply(auditTimeBg).add(new BigDecimal("1"));
			BigDecimal averageCapital = auditMoney.multiply(a).divide(auditTimeBg, 8, BigDecimal.ROUND_HALF_UP).setScale(2, BigDecimal.ROUND_HALF_UP);
			// 合同金额
			BigDecimal pactMoney = BigDecimal.ZERO;
			for (int i = 1; i <= auditTime; i++) {
				BigDecimal pow = new BigDecimal(Math.pow(monthRate.add(new BigDecimal("1")).doubleValue(), i));
				pactMoney = pactMoney.add(averageCapital.divide(pow, 8, BigDecimal.ROUND_HALF_UP));
			}
			pactMoney = pactMoney.setScale(2, BigDecimal.ROUND_HALF_UP);//总和做ROUND
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
			BigDecimal remainingPrincipal = pactMoney;
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
				// 还款金额 = 每月等额本息
				repaymentPlanVO.setRepayAmount(averageCapital);
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
				// 违约金
				BigDecimal penalty = pactMoney.multiply(penaltyRate).setScale(2, BigDecimal.ROUND_HALF_UP);
				repaymentPlanVO.setPenalty(penalty);
				repaymentPlanVO.setCurRemainingManagePart0Fee(BigDecimal.ZERO);
				repaymentPlanVO.setCurRemainingManagePart1Fee(BigDecimal.ZERO);
				repaymentPlanVO.setCurRemainingEvalRate(BigDecimal.ZERO);
				repaymentPlanVO.setCurRemainingReferRate(BigDecimal.ZERO);
				repaymentPlanVO.setCurRemainingRisk(BigDecimal.ZERO);
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
			repaymentPlanVO.setRepayAmount(averageCapital);
			// 当期归还本金
			principalAmt = pactMoney.subtract(totalPrincipalAmt).setScale(2, BigDecimal.ROUND_HALF_UP);
			repaymentPlanVO.setPrincipalAmt(principalAmt);
			repaymentPlanVO.setCurRemainingPrincipal(principalAmt);
			// 当期归还利息
			interestAmt = averageCapital.subtract(principalAmt).setScale(2, BigDecimal.ROUND_HALF_UP);
			repaymentPlanVO.setInterestAmt(interestAmt);
			repaymentPlanVO.setCurRemainingInterestAmt(interestAmt);
			totalPrincipalAmt = totalPrincipalAmt.add(principalAmt).setScale(2, BigDecimal.ROUND_HALF_UP);
			totalInterestAmt = totalInterestAmt.add(interestAmt).setScale(2, BigDecimal.ROUND_HALF_UP);
			// 最后一期剩余本金,提前还款违约金,退费置为0
			repaymentPlanVO.setPenalty(BigDecimal.ZERO);
			repaymentPlanVO.setRemainingPrincipal(BigDecimal.ZERO);
			repaymentPlanVO.setRefund(BigDecimal.ZERO);
			// 最后一期的一次性还款金额 =还款金额
			oneTimeRepaymentAmount = averageCapital;
			repaymentPlanVO.setOneTimeRepaymentAmount(oneTimeRepaymentAmount);
			repayDay = DateUtil.getNowDateAfter(1, repayDay);
			repaymentPlanVO.setRepayDay(DateUtil.formatDate(repayDay));
			repaymentPlanVO.setPenaltyDate(DateUtil.formatDate(repayDay));
			repaymentPlanVO.setCurRemainingManagePart0Fee(BigDecimal.ZERO);
			repaymentPlanVO.setCurRemainingManagePart1Fee(BigDecimal.ZERO);
			repaymentPlanVO.setCurRemainingEvalRate(BigDecimal.ZERO);
			repaymentPlanVO.setCurRemainingReferRate(BigDecimal.ZERO);
			repaymentPlanVO.setCurRemainingRisk(BigDecimal.ZERO);
			BigDecimal deficit = repaymentPlanVO.getCurRemainingInterestAmt().add(repaymentPlanVO.getCurRemainingManagePart0Fee()).add(repaymentPlanVO.getCurRemainingManagePart1Fee())
					.add(repaymentPlanVO.getCurRemainingPrincipal()).add(repaymentPlanVO.getCurRemainingReferRate()).add(repaymentPlanVO.getCurRemainingEvalRate())
					.add(repaymentPlanVO.getCurRemainingRisk()).setScale(2, BigDecimal.ROUND_HALF_UP);
			repaymentPlanVO.setDeficit(deficit);
			repaymentPlanVO.setStatus(EnumConstants.RepaymentPlanState.NOT_REPAYMENT.getValue());
			repaymentPlanList.add(repaymentPlanVO);
			// 处理LOAN
			loanVO.setTime(auditTime.longValue());
			loanVO.setMonthRate(monthRate);
			// 综合费用
			BigDecimal totalCharges = auditMoney.multiply(auditTimeBg).multiply(sumRate).setScale(2, BigDecimal.ROUND_HALF_UP);
			// 风险金
			BigDecimal risk = pactMoney.multiply(riskRate).multiply(auditTimeBg).divide(new BigDecimal("12"), 8, BigDecimal.ROUND_HALF_UP).setScale(2, BigDecimal.ROUND_HALF_UP);
			loanVO.setRisk(risk);
			// 服务费
			BigDecimal serviceCharges = pactMoney.subtract(auditMoney).subtract(risk).setScale(2, BigDecimal.ROUND_HALF_UP);
			// 丙方管理费--大拇指
			BigDecimal cManage = BigDecimal.ZERO;
			loanVO.setcManage(cManage);
			// 同城
			BigDecimal thirdFee = pactMoney.multiply(thirdFeeRate).multiply(auditTimeBg).divide(new BigDecimal("12"), 8, BigDecimal.ROUND_HALF_UP).setScale(2, BigDecimal.ROUND_HALF_UP);
			loanVO.setThirdFee(thirdFee);
			// 咨询费
			BigDecimal consult = serviceCharges.multiply(consultingFeeRate).setScale(2, BigDecimal.ROUND_HALF_UP);
			loanVO.setConsult(consult);
			// 评估费
			BigDecimal assessment = serviceCharges.multiply(assessmentFeeRate).setScale(2, BigDecimal.ROUND_HALF_UP);
			loanVO.setAssessment(assessment);
			// 管理费
			BigDecimal manageFee = serviceCharges.subtract(consult).subtract(assessment).setScale(2, BigDecimal.ROUND_HALF_UP);
			// 乙方管理费
			BigDecimal bManage = manageFee.subtract(cManage).setScale(2, BigDecimal.ROUND_HALF_UP);
			loanVO.setbManage(bManage);
			// 退费递减金额
			BigDecimal diffRefund = manageFee.divide(auditTimeBg, 8, BigDecimal.ROUND_HALF_UP)
				.setScale(2, BigDecimal.ROUND_HALF_UP);
			// 第1期到第n-1期--退费和一次性还款金额
			for (int curNum = 1; curNum < auditTime; curNum++) {
				RepaymentPlanVO repaymentPlan = repaymentPlanList.get(curNum - 1);
				BigDecimal refund = diffRefund.multiply(auditTimeBg.subtract(new BigDecimal(curNum))).setScale(2, BigDecimal.ROUND_HALF_UP);
				repaymentPlan.setRefund(refund);
				repaymentPlan.setOneTimeRepaymentAmount(repaymentPlan.getAverageCapital().add(repaymentPlan.getRemainingPrincipal()).add(repaymentPlan.getPenalty()).subtract(refund)
						.setScale(2, BigDecimal.ROUND_HALF_UP));
			}
			// 写入还款计划
			for (RepaymentPlanVO plan : repaymentPlanList) {
				repaymentPlanService.insert(plan);
			}
			// 生成合同--TBD
			cityWideContractService.createCityWidePersonLoanContract(loan, person, contractNo);
			cityWideContractService.createCityWideLoanContract(loan, person, repaymentPlanVO, endRepayDate, startRepayDate, generateContractVO, contractNo);
			cityWideContractService.createCityWideRepaymentContract(loan, person, repaymentPlanVO, contractNo, loanVO.getConsult(), loanVO, diffRefund, generateContractVO);
			cityWideContractService.createCityWideEntrustContract(loan, person, generateContractVO, contractNo);
			cityWideContractService.createCityWideRepaymentFundContract(loan, person, repaymentPlanVO, contractNo, startRepayDate);
			// 担保人合同
			GuaranteeVO gVO = new GuaranteeVO();
			gVO.setLoan(loan);
			gVO.setPersonId(personId);
			gVO.setFlag(EnumConstants.YesOrNo.YES.getValue());
			List<Guarantee> gList = guaranteeService.findListByVo(gVO);
			if (CollectionUtil.isNotEmpty(gList)) {
			
				for (Guarantee guarantee : gList) {

					if (guarantee.getGuaranteeType().compareTo(1) == 0) {
						cityWideContractService.createCityWideNaturalLegalContract(loan, person, endRepayDate, startRepayDate, generateAgreeNo(contractNo), guarantee.getName());
					} else {
						BankAccount guarantBankAccount = bankAccountService.get(guarantee.getBankAccountId());
						cityWideContractService.createCityWideNaturalGuaranteeContract(loan, person, endRepayDate, startRepayDate,  generateAgreeNo(contractNo), guarantee);
						cityWideContractService.createCityWideEntrustGuaranteeContract(loan, person, guarantBankAccount,  generateAgreeNo(contractNo), guarantee);
					}
					
				}
			}
			GUARANTEE_SEQ = 5; //每次生成完合同后,重新初始化为原值
			loanVO.setStartRepayDate(DateUtil.formatDate(startRepayDate));
			loanVO.setEndRepayDate(DateUtil.formatDate(endRepayDate));
			loanVO.setPactMoney(pactMoney);
			loanVO.setContractCreatedTime(contractCreatedDate);
			loanVO.setRepaymentMethod(EnumConstants.RepaymentMethod.AVERAGE_CAPITAL_PLUS_INTEREST.getValue());
			// 放款金额=审批金额
			loanVO.setGrantMoney(auditMoney);

			// 还款日
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(loanVO.getEndRepayDate());
			loanVO.setReturnDate(calendar.get(Calendar.DATE));
			loanVO.setResidualPactMoney(pactMoney);
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
	 * 生成首期还款日
	 * </pre>
	 * 
	 * @param generateContractVO
	 * @param contractCreatedDate
	 * @param loanVO
	 * @param product
	 * @param productDetail
	 * @return
	 *//*
	private Date generateRepayDate(GenerateContractVO generateContractVO, Date contractCreatedDate, LoanVO loanVO, Product product, ProductDetail productDetail) {
		Date repayDay = new Date();
		// 查看是否有还款日规则
		LoanRuleVO loanRuleVo = new LoanRuleVO();
		loanRuleVo.setRuleType(EnumConstants.RuleType.REPAYDATE_RULE.getValue());
		loanRuleVo.setProductType(product.getProductType());
		if (productDetail.getCarProductType() != null) {
			loanRuleVo.setProductSubtype(productDetail.getCarProductType());
		}
		loanRuleVo.setContractSrc(generateContractVO.getContractSrc());
		List<LoanRule> loanRepayDateRuleList = repayDateRuleService.findRuleByParams(loanRuleVo);
		if (CollectionUtil.isNullOrEmpty(loanRepayDateRuleList)) {
			throw new BusinessException("没有还款日规则");
		}
		if (loanRepayDateRuleList.size() > 1) {
			throw new BusinessException("还款日规则大于1条");
		}
		LoanRule loanRepayDate = loanRepayDateRuleList.get(0);
		if (loanRepayDate.getRepaydateRule().equals(EnumConstants.RepayDateRuleType.FIXED_DATE.getValue())) {
			// 固定还款日规则的
			repayDay = getRepayRuleDate(contractCreatedDate);
			// 当前月天数
			int dayOfMonth = DateUtil.getDayOfMonthByFixedDate(contractCreatedDate);
			Integer nowDay = DateUtil.getNowDayByFixedDate(contractCreatedDate);
			if ((nowDay >= 13 && nowDay <= 15) || (dayOfMonth - nowDay) < 3) {
				// 是否有特殊签单规则
				loanRuleVo.setRuleType(EnumConstants.RuleType.SIGN_RULE.getValue());
				loanRuleVo.setContractSrc(null);
				List<LoanRule> loanSignRuleList = signLoanRuleService.findSignRuleByLoanRuleVo(loanRuleVo);
				if (CollectionUtil.isNotEmpty(loanSignRuleList)) {
					if (loanSignRuleList.size() > 1) {
						throw new BusinessException("特殊签单规则大于1条");
					}
					repayDay = getSignRuleDate(repayDay);
					// add by LIN 特殊签单的话,在主表写标记
					loanVO.setFlag(EnumConstants.LoanRuleType.SIGN_RULE.getValue());
				}
			}
		} else {
			// 非固定还款日规则的
			repayDay = getNotRepayRuleDate(contractCreatedDate);
		}
		return repayDay;
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
	/*private Bank processBankInfoAndPersonBank(GenerateContractVO generateContractVO, Loan loan) {
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
	 * 删除原先数据
	 * 1.合同;2.还款计划;3.银行信息;4.客户银行卡关联
	 * </pre>
	 * 
	 * @param loan
	 */
	/*private void processOrginalData(Loan loan) {
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
	 * 处理担保人信息
	 * </pre>
	 *
	 * @param generateContractVO
	 * @param loan
	 * @param guaranteeVO
	 * @param g
	 */
//	private void processGuarntee(GenerateContractVO generateContractVO, Loan loan, GuaranteeVO guaranteeVO, Guarantee g) {
//		BankAccount guarantBankAccount;
//		BankAccountVO guaranteeBankAccountVO = new BankAccountVO();
//		Bank guaranteeBank;
//		// 自然人1
//		if (g.getName().equals(generateContractVO.getNaturalGuaranteeName1())) {
//			guaranteeBankAccountVO.setAccount(generateContractVO.getNaturalGuaranteeBankAccount1());
//			guaranteeBankAccountVO.setBranchName(generateContractVO.getNaturalGuaranteeBankBranch1());
//			guaranteeBank = bankService.get(Long.valueOf(generateContractVO.getNaturalGuaranteeBank1()));
//			guaranteeBankAccountVO.setBankName(guaranteeBank.getBankName());
//			guaranteeBankAccountVO.setBank(guaranteeBank);
//			// 查看银行账户是否存在,如不存在则插入
//			guarantBankAccount = bankAccountService.get(guaranteeBankAccountVO);
//			if (guarantBankAccount == null) {
//				guarantBankAccount = BankAccountAssembler.transferVO2Model(guaranteeBankAccountVO);
//				guarantBankAccount = bankAccountService.insert(guarantBankAccount);
//			}
//			// 更新担保人信息
//			guaranteeVO.setId(g.getId());
//			Loan guaranteeLoan = new Loan();
//			guaranteeLoan.setId(loan.getId());
//			guaranteeVO.setLoan(guaranteeLoan);
//			guaranteeVO.setPersonId(loan.getPersonId());
//			guaranteeVO.setBankAccountId(guarantBankAccount.getId());
//			guaranteeService.update(guaranteeVO);
//			// 自然人2
//		} else if (g.getName().equals(generateContractVO.getNaturalGuaranteeName2())) {
//			guaranteeBankAccountVO.setAccount(generateContractVO.getNaturalGuaranteeBankAccount2());
//			guaranteeBankAccountVO.setBranchName(generateContractVO.getNaturalGuaranteeBankBranch2());
//			guaranteeBank = bankService.get(Long.valueOf(generateContractVO.getNaturalGuaranteeBank2()));
//			guaranteeBankAccountVO.setBankName(guaranteeBank.getBankName());
//			guaranteeBankAccountVO.setBank(guaranteeBank);
//			// 查看银行账户是否存在,如不存在则插入
//			guarantBankAccount = bankAccountService.get(guaranteeBankAccountVO);
//			if (guarantBankAccount == null) {
//				guarantBankAccount = BankAccountAssembler.transferVO2Model(guaranteeBankAccountVO);
//				guarantBankAccount = bankAccountService.insert(guarantBankAccount);
//			}
//			// 更新担保人信息
//			guaranteeVO.setId(g.getId());
//			Loan guaranteeLoan = new Loan();
//			guaranteeLoan.setId(loan.getId());
//			guaranteeVO.setLoan(guaranteeLoan);
//			guaranteeVO.setPersonId(loan.getPersonId());
//			guaranteeVO.setBankAccountId(guarantBankAccount.getId());
//			guaranteeService.update(guaranteeVO);
//			// 法人1
//		} else if (g.getName().equals(generateContractVO.getLegalGuaranteeName1())) {
//			guaranteeBankAccountVO.setAccount(generateContractVO.getLegalGuaranteeBankAccount1());
//			guaranteeBankAccountVO.setBranchName(generateContractVO.getLegalGuaranteeBankBranch1());
//			guaranteeBank = bankService.get(Long.valueOf(generateContractVO.getLegalGuaranteeBank1()));
//			guaranteeBankAccountVO.setBankName(guaranteeBank.getBankName());
//			guaranteeBankAccountVO.setBank(guaranteeBank);
//			// 查看银行账户是否存在,如不存在则插入
//			guarantBankAccount = bankAccountService.get(guaranteeBankAccountVO);
//			if (guarantBankAccount == null) {
//				guarantBankAccount = BankAccountAssembler.transferVO2Model(guaranteeBankAccountVO);
//				guarantBankAccount = bankAccountService.insert(guarantBankAccount);
//			}
//			// 更新担保人信息
//			guaranteeVO.setId(g.getId());
//			Loan guaranteeLoan = new Loan();
//			guaranteeLoan.setId(loan.getId());
//			guaranteeVO.setLoan(guaranteeLoan);
//			guaranteeVO.setPersonId(loan.getPersonId());
//			guaranteeVO.setBankAccountId(guarantBankAccount.getId());
//			guaranteeService.update(guaranteeVO);
//			// 法人2
//		} else if (g.getName().equals(generateContractVO.getLegalGuaranteeName2())) {
//			guaranteeBankAccountVO.setAccount(generateContractVO.getLegalGuaranteeBankAccount2());
//			guaranteeBankAccountVO.setBranchName(generateContractVO.getLegalGuaranteeBankBranch2());
//			guaranteeBank = bankService.get(Long.valueOf(generateContractVO.getLegalGuaranteeBank2()));
//			guaranteeBankAccountVO.setBankName(guaranteeBank.getBankName());
//			guaranteeBankAccountVO.setBank(guaranteeBank);
//			// 查看银行账户是否存在,如不存在则插入
//			guarantBankAccount = bankAccountService.get(guaranteeBankAccountVO);
//			if (guarantBankAccount == null) {
//				guarantBankAccount = BankAccountAssembler.transferVO2Model(guaranteeBankAccountVO);
//				guarantBankAccount = bankAccountService.insert(guarantBankAccount);
//			}
//			// 更新担保人信息
//			guaranteeVO.setId(g.getId());
//			Loan guaranteeLoan = new Loan();
//			guaranteeLoan.setId(loan.getId());
//			guaranteeVO.setLoan(guaranteeLoan);
//			guaranteeVO.setPersonId(loan.getPersonId());
//			guaranteeVO.setBankAccountId(guarantBankAccount.getId());
//			guaranteeService.update(guaranteeVO);
//		}
//	}

	/**
	 * 
	 * <pre>
	 * 生成规则：ZDB-SME-TC+城市编号(work_place_info.zone_code)+yyyymmdd+3位数字序列号(当天第N份合同)
	 * 例:ZDB-SME-TC002120150625001表示: 上海地区,20150625第1份同城贷合同
	 * </pre>
	 * 
	 * @param loanId
	 * @param contractCreatedDate
	 * @return
	 */
	/*@Transactional
	private String createdContractNo(Long baseAreaId) {
		StringBuilder contractNo = new StringBuilder();
		contractNo.append("ZDB-SME-TC");
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
		// 车贷合同
		SerialNumResult serialNumResult = sysSerialNumService.getSerialNum(SerialNum.CONTRACT3);
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
/*	private Date getRepayRuleDate(Date contractCreatedDate) {
		Calendar c = Calendar.getInstance();
		c.setTime(contractCreatedDate);
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		if (day > 15) {
			c.set(year, month + 1, 16);
		} else {
			c.set(year, month + 1, 1);
		}
		return c.getTime();
	}*/

	/**
	 * 特殊签单日期变化
	 * 
	 * @param date
	 * @return
	 */
	/*private Date getSignRuleDate(Date date) {
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
	/*private Date getNotRepayRuleDate(Date contractCreatedDate) {
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
	}*/
	
   /* private static String generateAgreeNo(String contractNo) {
        String contractNum = "";
		DecimalFormat df=new DecimalFormat("-000");
		contractNum=contractNo+ df.format(GUARANTEE_SEQ);
        GUARANTEE_SEQ = GUARANTEE_SEQ + 1;
        return contractNum;
    }*/

}
