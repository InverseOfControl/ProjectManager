package com.ezendai.credit2.audit.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
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
import com.ezendai.credit2.apply.model.BusinessLog;
import com.ezendai.credit2.apply.model.ChannelPlanCheck;
import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.model.Person;
import com.ezendai.credit2.apply.model.Product;
import com.ezendai.credit2.apply.model.ProductDetail;
import com.ezendai.credit2.apply.service.BankAccountService;
import com.ezendai.credit2.apply.service.BankService;
import com.ezendai.credit2.apply.service.BusinessLogService;
import com.ezendai.credit2.apply.service.ChannelPlanCheckService;
import com.ezendai.credit2.apply.service.GuaranteeService;
import com.ezendai.credit2.apply.service.LoanService;
import com.ezendai.credit2.apply.service.PersonService;
import com.ezendai.credit2.apply.service.ProductDetailService;
import com.ezendai.credit2.apply.service.ProductService;
import com.ezendai.credit2.apply.vo.LoanVO;
import com.ezendai.credit2.apply.vo.ProductDetailVO;
import com.ezendai.credit2.audit.service.AbstractContractAuditService;
import com.ezendai.credit2.audit.service.ContractService;
import com.ezendai.credit2.audit.service.EduCreditAuditService;
import com.ezendai.credit2.audit.service.PersonBankService;
import com.ezendai.credit2.audit.service.RepaymentPlanService;
import com.ezendai.credit2.audit.service.StudentLoanContractService;
import com.ezendai.credit2.audit.vo.GenerateContractVO;
import com.ezendai.credit2.audit.vo.RepaymentPlanVO;
import com.ezendai.credit2.framework.util.DateUtil;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.master.enumerate.EnumConstants.SerialNum;
import com.ezendai.credit2.master.service.BaseAreaService;
import com.ezendai.credit2.master.service.WorkPlaceInfoService;
import com.ezendai.credit2.rule.service.RepayDateRuleService;
import com.ezendai.credit2.rule.service.SignLoanRuleService;
import com.ezendai.credit2.system.enumerate.SysParameterEnum;
import com.ezendai.credit2.system.model.SysLog;
import com.ezendai.credit2.system.model.SysParameter;
import com.ezendai.credit2.system.service.SysLogService;
import com.ezendai.credit2.system.service.SysParameterService;
import com.ezendai.credit2.system.service.SysSerialNumService;

/**
 * <pre>
 * 助学贷合同生成
 * </pre>
 */
@Service
public class EduCreditAuditServiceImpl extends AbstractContractAuditService implements EduCreditAuditService {

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
	    private StudentLoanContractService studentLoanContractService;

	    @Autowired
	    private ContractService contractService;
	    
		@Autowired
		private ChannelPlanCheckService checkService;
		
		@Autowired
		private SysParameterService sysParameterService;

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
	            loanVO.setGrantAccountId(generateContractVO.getOrganBankId());
	            loanVO.setRepayAccountId(bank.getAccountId());
	            // 签约日期
	            loanVO.setSignDate(contractCreatedDate);
	            String contractNo = new String();
	            if(loan.getContractNo() != null){
	            	contractNo=loan.getContractNo() ;
	            }else{
	            	contractNo=createdEduContractNo(loan.getSalesDeptId(),"ZDB-SME-ZX",SerialNum.CONTRACT5);
	            }
	            loanVO.setContractNo(contractNo);
	            Product product = productService.get(loan.getProductId());
	            
	            ChannelPlanCheck channelPlanCheck=checkService.getReplyById(loan.getSchemeID());
	            // 期数
	            Integer auditTime = loan.getAuditTime();
	            // 风险金比率
	            BigDecimal riskRate = product.getRiskRate();
//	            // 助学贷机构返利费率
//	            BigDecimal thirdFeeRate = product.getThirdFeeRate();
	            // 月利率
	            BigDecimal monthRate = product.getYearRate().divide(new BigDecimal(12), 20, BigDecimal.ROUND_HALF_UP);
	            // 咨询费比例
	            BigDecimal consultingFeeRate = product.getConsultingFeeRate();
	            // 评估费比例
	            BigDecimal assessmentFeeRate = product.getAssessmentFeeRate();
	            // 期缴管理费比例
//	            BigDecimal manageFeeRate = productDetail.getManageFeeRate();
	            // 月综合费率
	            BigDecimal sumRate = channelPlanCheck.getActualRate();
	            // 提前还款违约金
	            BigDecimal penaltyRate = product.getPenaltyRate();
	            // 还款开始日期
	            Date startRepayDate = new Date();
	            Date repayDay = generateRepayDate(generateContractVO, contractCreatedDate, loanVO, product, null);//算出还款天数
	            startRepayDate = repayDay;
	            Date endRepayDate = DateUtil.getNowDateAfter(auditTime - 1, repayDay);
	            // 审核金额
	            BigDecimal auditMoney = loan.getAuditMoney();
	            BigDecimal auditTimeBg = new BigDecimal(auditTime);// 审核期数
				// 每月等额本息
				BigDecimal a = sumRate.multiply(new BigDecimal(auditTime)).add(new BigDecimal("1"));
				BigDecimal averageCapital = auditMoney.multiply(a).divide(new BigDecimal(auditTime), 8, BigDecimal.ROUND_HALF_UP).setScale(8, BigDecimal.ROUND_HALF_UP);
				// 合同金额
	            BigDecimal pactMoney = BigDecimal.ZERO;
				pactMoney = loan.getAuditMoney().setScale(8, BigDecimal.ROUND_HALF_UP);//总和做ROUND
				if(channelPlanCheck.getOrgFeeState().equals(EnumConstants.OrganPayStatus.不承担.getValue())){
					double pactMoneyValue=0;
					for(double i=1;i<=auditTimeBg.doubleValue();i++){
						if(EnumConstants.ReturnTypeStatus.等额本息.getValue().compareTo(channelPlanCheck.getReturnType().longValue()) == 0 ){
								pactMoneyValue=pactMoneyValue+calRemainE(monthRate.doubleValue(),averageCapital.doubleValue(),i);
						}else if(EnumConstants.ReturnTypeStatus.前低后高.getValue().compareTo(channelPlanCheck.getReturnType().longValue()) == 0 ){
							if(i <= channelPlanCheck.getToTerm1()){
								averageCapital = channelPlanCheck.getReturneterm1().setScale(8, BigDecimal.ROUND_HALF_UP);
								pactMoneyValue=pactMoneyValue+calRemainE(monthRate.doubleValue(),averageCapital.doubleValue(),i);
							}else{
			                	averageCapital = new BigDecimal(String.valueOf(calStuAmountNE(sumRate.doubleValue(),channelPlanCheck.getReturneterm1().doubleValue(),channelPlanCheck.getToTerm1(),channelPlanCheck.getToTerm2(),auditMoney.doubleValue(),auditTime))).setScale(8, BigDecimal.ROUND_HALF_UP);
								pactMoneyValue=pactMoneyValue+calRemainE(monthRate.doubleValue(),averageCapital.doubleValue(),i);
							}
						}
					}
					pactMoney=new BigDecimal(pactMoneyValue).setScale(8, BigDecimal.ROUND_HALF_UP);
				}
				if(EnumConstants.ReturnTypeStatus.前低后高.getValue().compareTo(channelPlanCheck.getReturnType().longValue()) == 0
	            		&&channelPlanCheck.getOrgFeeState().equals(EnumConstants.OrganPayStatus.不承担.getValue())){
					pactMoney=pactMoney.setScale(2, BigDecimal.ROUND_HALF_UP);
	            }
	            // 提前还款违约金
	            BigDecimal penalty = pactMoney.multiply(penaltyRate).setScale(8, BigDecimal.ROUND_HALF_UP);
	            
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
	            // 风险金
				BigDecimal risk = pactMoney.multiply(riskRate).setScale(8, BigDecimal.ROUND_HALF_UP);
				//前低后高-机构不承担 风险金四舍五入
				if(EnumConstants.ReturnTypeStatus.前低后高.getValue().compareTo(channelPlanCheck.getReturnType().longValue()) == 0
	            		&&channelPlanCheck.getOrgFeeState().equals(EnumConstants.OrganPayStatus.不承担.getValue())){
					risk=risk.setScale(2, BigDecimal.ROUND_HALF_UP);
	            }
				// 综合费用
				BigDecimal totalCharges = auditMoney.multiply(new BigDecimal(auditTime)).multiply(sumRate).setScale(8, BigDecimal.ROUND_HALF_UP);
	
	            // 第1期到第n-1期
	            for (int curNum = 1; curNum < auditTime; curNum++) {
	                // 生成还款信息
	                RepaymentPlanVO repaymentPlanVO = new RepaymentPlanVO();
	                // 方案类型为等额本息
	                if(EnumConstants.ReturnTypeStatus.等额本息.getValue().compareTo(channelPlanCheck.getReturnType().longValue()) == 0 ){
	                	//学生承担费用
		                if( channelPlanCheck.getOrgFeeState().equals(EnumConstants.OrganPayStatus.不承担.getValue())){
		                	 //当月偿还本息金额(学生承担费用)
//		    	            averageCapital = channelPlanCheck.getReturneterm1().setScale(8, BigDecimal.ROUND_HALF_UP);
		    	            averageCapital = new BigDecimal(String.valueOf(calStuAmountE(sumRate.doubleValue(),auditMoney.doubleValue(),auditTimeBg.doubleValue()))).setScale(8, BigDecimal.ROUND_HALF_UP);
		                }		
		                //机构承担费用
		                else if(channelPlanCheck.getOrgFeeState().equals(EnumConstants.OrganPayStatus.承担.getValue())){
		                	 //当月偿还本息金额(机构承担服务费)
		    	            averageCapital = new BigDecimal(String.valueOf(PMT(monthRate.doubleValue(), auditTimeBg.doubleValue(), auditMoney.doubleValue()))).setScale(8, BigDecimal.ROUND_HALF_UP);
//		    	            averageCapital = channelPlanCheck.getReturneterm1().setScale(2, BigDecimal.ROUND_HALF_UP);
		                }
	                }
	                // 方案类型为前低后高
	                else if( EnumConstants.ReturnTypeStatus.前低后高.getValue().compareTo(channelPlanCheck.getReturnType().longValue()) == 0  ){
	                	//学生承担费用
		                if( channelPlanCheck.getOrgFeeState().equals(EnumConstants.OrganPayStatus.不承担.getValue()) && curNum <= channelPlanCheck.getToTerm1() ){
		                	 //当月偿还本息金额(学生承担费用)
		    	            averageCapital = channelPlanCheck.getReturneterm1().setScale(8, BigDecimal.ROUND_HALF_UP);
		                }else if( channelPlanCheck.getOrgFeeState().equals(EnumConstants.OrganPayStatus.不承担.getValue()) && curNum > channelPlanCheck.getToTerm1() ){
		                	 //当月偿还本息金额(学生承担费用)
//		                	 averageCapital = channelPlanCheck.getReturneterm2().setScale(2, BigDecimal.ROUND_HALF_UP);
		                	averageCapital = new BigDecimal(String.valueOf(calStuAmountNE(sumRate.doubleValue(),channelPlanCheck.getReturneterm1().doubleValue(),channelPlanCheck.getToTerm1(),channelPlanCheck.getToTerm2(),auditMoney.doubleValue(),auditTime))).setScale(8, BigDecimal.ROUND_HALF_UP);
		                }
		                //机构承担费用
		                else if( channelPlanCheck.getOrgFeeState().equals(EnumConstants.OrganPayStatus.承担.getValue()) && curNum <= channelPlanCheck.getToTerm1() ){
		                	 //当月偿还本息金额(机构承担服务费)
		    	            averageCapital = channelPlanCheck.getReturneterm1().setScale(8, BigDecimal.ROUND_HALF_UP);
		                }
		                else if( channelPlanCheck.getOrgFeeState().equals(EnumConstants.OrganPayStatus.承担.getValue()) && curNum > channelPlanCheck.getToTerm1() ){
		                	 //当月偿还本息金额(机构承担服务费)
//		                	averageCapital = channelPlanCheck.getReturneterm2().setScale(2, BigDecimal.ROUND_HALF_UP);
		                	averageCapital = new BigDecimal(String.valueOf(PMT(monthRate.doubleValue(), channelPlanCheck.getToTerm2(), Math.abs(FV(monthRate.doubleValue(),channelPlanCheck.getToTerm1(),channelPlanCheck.getReturneterm1().setScale(8, BigDecimal.ROUND_HALF_UP).doubleValue(),auditMoney.doubleValue()))))).setScale(8, BigDecimal.ROUND_HALF_UP);
		                }
	                }
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
					principalAmt = averageCapital.subtract(interestAmt).setScale(8, BigDecimal.ROUND_HALF_UP);
					repaymentPlanVO.setPrincipalAmt(principalAmt);
					repaymentPlanVO.setCurRemainingPrincipal(principalAmt);
					// 剩余本金
					remainingPrincipal = remainingPrincipal.subtract(principalAmt).setScale(8, BigDecimal.ROUND_HALF_UP);
					repaymentPlanVO.setRemainingPrincipal(remainingPrincipal);
					// 违约金
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
//				principalAmt = pactMoney.subtract(totalPrincipalAmt).setScale(8, BigDecimal.ROUND_HALF_UP);
				principalAmt =remainingPrincipal.setScale(8, BigDecimal.ROUND_HALF_UP);
				repaymentPlanVO.setPrincipalAmt(principalAmt);
				repaymentPlanVO.setCurRemainingPrincipal(principalAmt);
				// 当期归还利息
				interestAmt = averageCapital.subtract(principalAmt).setScale(8, BigDecimal.ROUND_HALF_UP);
				repaymentPlanVO.setInterestAmt(interestAmt);
				repaymentPlanVO.setCurRemainingInterestAmt(interestAmt);
				totalPrincipalAmt = totalPrincipalAmt.add(principalAmt).setScale(8, BigDecimal.ROUND_HALF_UP);
				totalInterestAmt = totalInterestAmt.add(interestAmt).setScale(8, BigDecimal.ROUND_HALF_UP);
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

				// 服务费
				BigDecimal serviceCharges = totalCharges.subtract(totalInterestAmt).subtract(risk).setScale(8, BigDecimal.ROUND_HALF_UP);

				// 同城
//				BigDecimal thirdFee = pactMoney.multiply(thirdFeeRate).multiply(new BigDecimal(auditTime)).divide(new BigDecimal("12"), 8, BigDecimal.ROUND_HALF_UP).setScale(2, BigDecimal.ROUND_HALF_UP);
//				loanVO.setThirdFee(thirdFee);
				// 咨询费
				BigDecimal consult = serviceCharges.multiply(consultingFeeRate).setScale(2, BigDecimal.ROUND_HALF_UP);
				
				// 评估费
				BigDecimal assessment = serviceCharges.multiply(assessmentFeeRate).setScale(2, BigDecimal.ROUND_HALF_UP);
				
				// 管理费
				BigDecimal manageFee = serviceCharges.subtract(consult).subtract(assessment).setScale(2, BigDecimal.ROUND_HALF_UP);
				// 丙方管理费--占管理费7成
				BigDecimal cManage = manageFee.multiply(new BigDecimal(0.7)).setScale(2, BigDecimal.ROUND_HALF_UP);;
				
	            // 乙方管理费--占管理费3成
				BigDecimal bManage = manageFee.subtract(cManage).setScale(2, BigDecimal.ROUND_HALF_UP);
				
				// 退费递减金额
				BigDecimal diffRefund = manageFee.divide(auditTimeBg, 8, BigDecimal.ROUND_HALF_UP);
				
				loanVO.setServiceCharges(serviceCharges.setScale(2, BigDecimal.ROUND_HALF_UP));
				loanVO.setcManage(cManage);
				loanVO.setbManage(bManage);
				loanVO.setConsult(consult.setScale(2, BigDecimal.ROUND_HALF_UP));
				loanVO.setAssessment(assessment.setScale(2, BigDecimal.ROUND_HALF_UP));
				loanVO.setTime(auditTime.longValue());
				loanVO.setMonthRate(monthRate);
				loanVO.setRisk(risk.setScale(2, BigDecimal.ROUND_HALF_UP));
				
				// 第1期到第n-1期--退费和一次性还款金额
				for (int curNum = 1; curNum < auditTime; curNum++) {
					RepaymentPlanVO repaymentPlan = repaymentPlanList.get(curNum - 1);
					BigDecimal refund = diffRefund.multiply(auditTimeBg.subtract(new BigDecimal(curNum))).setScale(8, BigDecimal.ROUND_HALF_UP);
					repaymentPlan.setRefund(refund.setScale(2, BigDecimal.ROUND_HALF_UP));
					if(channelPlanCheck.getOrgFeeState().equals(EnumConstants.OrganPayStatus.不承担.getValue())){
					    repaymentPlan.setOneTimeRepaymentAmount(repaymentPlan.getAverageCapital().add(repaymentPlan.getRemainingPrincipal()).add(repaymentPlan.getPenalty()).subtract(refund)
							.setScale(2, BigDecimal.ROUND_HALF_UP));
						repaymentPlan.setRefund(repaymentPlan.getRemainingPrincipal().add(repaymentPlan.getPenalty()).add(repaymentPlan.getRepayAmount()).subtract(repaymentPlan.getOneTimeRepaymentAmount()));
					}else{
						repaymentPlan.setOneTimeRepaymentAmount(repaymentPlan.getAverageCapital().add(repaymentPlan.getRemainingPrincipal()).add(repaymentPlan.getPenalty())
								.setScale(2, BigDecimal.ROUND_HALF_UP));
					}
				}
	            // 写入还款计划
	            for (RepaymentPlanVO plan : repaymentPlanList) {
	                repaymentPlanService.insert(plan);
	            }
	            // 生成合同--TBD
	            studentLoanContractService.createStudentLoanContract(loan, person, repaymentPlanVO, endRepayDate, startRepayDate, generateContractVO, contractNo);
	            studentLoanContractService.createStudentLoanEntrustContract(loan, person, generateContractVO, contractNo);
	            studentLoanContractService.createStudentLoanRepaymentContract(loan, diffRefund,person, repaymentPlanVO, contractNo, loanVO.getConsult(), loanVO, generateContractVO);
	            studentLoanContractService.createStudentLoanRepaymentFundContract(loan, person, repaymentPlanVO, contractNo, startRepayDate);
	            studentLoanContractService.createStudentLoanPersonLoanContract(loan, person, contractNo);
	            GUARANTEE_SEQ = 5; //每次生成完合同后,重新初始化为原值
	            loanVO.setStartRepayDate(DateUtil.formatDate(startRepayDate));
				loanVO.setEndRepayDate(DateUtil.formatDate(endRepayDate));
				loanVO.setPactMoney(pactMoney);
				loanVO.setContractCreatedTime(contractCreatedDate);
				// 方案类型为等额本息
                if(EnumConstants.ReturnTypeStatus.等额本息.getValue().compareTo(channelPlanCheck.getReturnType().longValue()) == 0 ){
                	loanVO.setRepaymentMethod(EnumConstants.RepaymentMethod.AVERAGE_CAPITAL_PLUS_INTEREST.getValue());
                }
                // 方案类型为前低后高
                else if( EnumConstants.ReturnTypeStatus.前低后高.getValue().compareTo(channelPlanCheck.getReturnType().longValue()) == 0  ){
                	loanVO.setRepaymentMethod(EnumConstants.RepaymentMethod.BEFORE_LOW_AFTER_HIGH_INTEREST.getValue());
                }
				// 放款金额=审批金额
				if(channelPlanCheck.getOrgFeeState().equals(EnumConstants.OrganPayStatus.不承担.getValue())){
					loanVO.setGrantMoney(auditMoney);
				}else{
					loanVO.setGrantMoney(auditMoney.subtract(serviceCharges).subtract(risk));
				}
				// 还款日
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(loanVO.getEndRepayDate());
				loanVO.setReturnDate(calendar.get(Calendar.DATE));
				loanVO.setResidualPactMoney(pactMoney);
				loanVO.setResidualTime(auditTime);
				
				
				SysParameter eduNewContract=sysParameterService.getByCode(SysParameterEnum.EDU_NEW_CONTRACT.name());
				SysParameter eduNewContractTime = sysParameterService.getByCode(SysParameterEnum.EDU_NEW_CONTRACT_TIME.name()); 
				try {
					if(eduNewContract != null && eduNewContractTime != null&& 
							eduNewContract.getParameterValue().equals("1") && DateUtil.getTodayHHmmss().after(DateUtil.strToDateTime(eduNewContractTime.getParameterValue()))){
						loanVO.setBusinessCompanyId((long)2);
					}else{
						loanVO.setBusinessCompanyId((long)1);
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
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
//	    private Date getRepayRuleDate(Date contractCreatedDate) {
//	        Calendar c = Calendar.getInstance();
//	        c.setTime(contractCreatedDate);
//	        int year = c.get(Calendar.YEAR);
//	        int month = c.get(Calendar.MONTH);
//	        int day = c.get(Calendar.DAY_OF_MONTH);
//	        if (day > 15) {
//	            c.set(year, month + 1, 16);
//	        } else {
//	            c.set(year, month + 1, 1);
//	        }
//	        return c.getTime();
//	    }

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
	     * 计算月供 有机构
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
	    
	    /**
	     * 计算FV 有机构
	     *
	     * @param rate          月利率
	     * @param nper          贷款期数，单位月
	     * @param pmt			每期金额
	     * @param pv			期望金额
	     * @return
	     */
	    private double FV(double rate, double nper, double pmt,double pv) {
	        double v = (1 + rate);
	        double pi=pv*Math.pow(v, nper);
	        double pn=-pmt*(Math.pow(v, nper)-1)/rate;
	        double result = -(pi+pn);
	        return result;
	    }
	    
	    /**
	     * 计算学生承担费用 前低后高
	     *
	     * @param rate          月服务费率
	     * @param term          贷款期数，单位月
	     * @param oldamt		上一期月金额
	     * @param oldterm		上一期月数
	     * @param newterm		当期月数
	     * @param pv			贷款金额
	     * @return
	     */
	    private double calStuAmountNE(double rate, double oldamt, double oldterm , double newterm ,double pv,double term) {
	        double pi=pv*(1+term*rate);
	        double pn=oldamt*oldterm;
	        double result = (pi-pn)/newterm;
	        return result;
	    }
	    
	    /**
	     * 计算学生承担费用 等额本息
	     *
	     * @param rate          月服务费率
	     * @param term          贷款期数，单位月
	     * @param pv			贷款金额
	     * @return
	     */
	    private double calStuAmountE(double rate, double pv,double term) {
	    	double pi=pv*(1+term*rate)/term;
	        double result = pi;
	        return result;
	    }
	    
	    private double calRemainE(double rate,double pv,double term){
	    	double pi=Math.pow(rate+1,term);
	    	double result=pv/pi;
	    	return result;
	    }

}
