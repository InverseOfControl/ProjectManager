/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.finance.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezendai.credit2.apply.model.BusinessLog;
import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.model.Product;
import com.ezendai.credit2.apply.service.BusinessLogService;
import com.ezendai.credit2.apply.service.LoanService;
import com.ezendai.credit2.apply.service.ProductService;
import com.ezendai.credit2.apply.vo.LoanVO;
import com.ezendai.credit2.audit.model.ApproveResult;
import com.ezendai.credit2.audit.service.ApproveResultService;
import com.ezendai.credit2.finance.model.Ledger;
import com.ezendai.credit2.finance.model.RepayInfo;
import com.ezendai.credit2.finance.service.FinanceService;
import com.ezendai.credit2.finance.service.FlowService;
import com.ezendai.credit2.finance.service.LedgerService;
import com.ezendai.credit2.finance.service.RepayInfoService;
import com.ezendai.credit2.finance.vo.LedgerVO;
import com.ezendai.credit2.framework.exception.BusinessException;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.system.model.SysLog;
import com.ezendai.credit2.system.model.SysParameter;
import com.ezendai.credit2.system.service.SysLogService;
import com.ezendai.credit2.system.service.SysParameterService;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author liyuepeng
 * @version $Id: FinanceServiceImpl.java, v 0.1 2014-9-10 下午03:30:15 liyuepeng
 *          Exp $
 */
@Service
public class FinanceServiceImpl implements FinanceService {

	@Autowired
	private LoanService loanService;

	@Autowired
	private RepayInfoService repayInfoService;

	@Autowired
	private FlowService flowService;

	@Autowired
	private LedgerService ledgerService;

	@Autowired
	private ApproveResultService approveResultService;

	@Autowired
	private BusinessLogService businessLogService;

	@Autowired
	private SysLogService sysLogService;
	
	@Autowired
	private SysParameterService sysParameterService;
	
	@Autowired
	private ProductService productService;

	/**
	 * 财务放款
	 * 
	 * @param loanId
	 * @see com.ezendai.credit2.finance.service.FinanceService#makeLoan(java.lang.Long)
	 */
	@Override
	@Transactional
	public void makeLoan(Loan loan) {
		LoanVO loanVo = new LoanVO();
		loanVo.setId(loan.getId());
		loanVo.setStatus(EnumConstants.LoanStatus.正常.getValue());
		loanVo.setGrantDate(new Date());
		loanService.update(loanVo);

		// 生成还款表
		RepayInfo repayInfo = repayInfoService.makeLoanCreateRepayInfo(loan);
		if (repayInfo == null) {
			throw new BusinessException("");
		}

		// 创建外部账户总账
		Ledger externalAccountLedger = new Ledger();
		externalAccountLedger.setVersion(1L);
		externalAccountLedger.setAccountId(loan.getId());
		externalAccountLedger
				.setType(EnumConstants.AccountType.EXTERNAL_ACCOUNT.getValue());
		externalAccountLedger = ledgerService.insert(externalAccountLedger);

		externalAccountLedger = ledgerService
				.get(externalAccountLedger.getId());
		// 只有短期车贷才有丙方管理费 前期利息
		if (loan.getProductType().compareTo(
				EnumConstants.ProductType.CAR_LOAN.getValue()) == 0
				&& loan.getAuditTime().compareTo(3) == 0) {
			// BManageFlowBuilding(loan, repayInfo, externalAccountLedger);
			CManageFlowBuilding(loan, repayInfo, externalAccountLedger);
			//判断新计算器生效时间
			SysParameter parameter=	sysParameterService.getByCode(EnumConstants.CAR_FINE_NEW_RULE_EXECUTE_TIME);
			 SimpleDateFormat sdftime = new SimpleDateFormat("yyyy-MM-dd");                
			 Date date=new Date();
			try {
				date = sdftime.parse(parameter.getParameterValue());
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
			if(loan.getCreatedTime().after(date)&&loan.getLoanType().equals(EnumConstants.ProductCarType.CIRCULATE.getValue())){
				
				prophaseInterestFlowBuilding(loan, repayInfo);
			}
		}
		
		
		
		Product product = productService.get(loan.getProductId());
		
		//车贷 老产品 流通类6期特殊处理规则
		if(("002".equals(product.getProductCode()))
				&&loan.getLoanType().equals(EnumConstants.ProductCarType.CIRCULATE.getValue())
				&&loan.getAuditTime().compareTo(6) == 0){
			//车贷计算器-等额本息新模型
			SysParameter parameter=	sysParameterService.getByCode(EnumConstants.CAR_FINE_NEW_CAL_EXECUTE_TIME);
			
			SimpleDateFormat sdftime = new SimpleDateFormat("yyyy-MM-dd");                
			Date date=new Date();
			try {
				date = sdftime.parse(parameter.getParameterValue());
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
			//Date1.after(Date2),当Date1大于Date2时
			if(loan.getCreatedTime().after(date)){
				CManageFlowBuilding(loan, repayInfo, externalAccountLedger);
				prophaseInterestFlowBuilding(loan, repayInfo);
			}
		}
		
		
		
		
		
		
		
		
		ConsultFlowBuilding(loan, repayInfo, externalAccountLedger);

		AssessmentFeeFlowBuilding(loan, repayInfo, externalAccountLedger);

		LoanFlowBuilding(loan, repayInfo, externalAccountLedger);

		RiskFlowBuilding(loan, repayInfo, externalAccountLedger);

		// 插入审核日志
		ApproveResult approveResult = new ApproveResult();
		approveResult.setState(EnumConstants.ApproveResultState.FINANCIAL_LOAN
				.getValue());
		approveResult.setReason("财务放款");
		approveResult.setLoanId(loan.getId());
		approveResultService.insert(approveResult);
		// 插入业务日志表
		BusinessLog businessLog = new BusinessLog();
		businessLog.setLoanId(loan.getId());
		businessLog.setMessage("财务放款");
		businessLog.setFlowStatus(EnumConstants.BusinessLogStatus.FINANCE_GRANT
				.getValue());
		businessLogService.insert(businessLog);
		// 插入系统日志
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.FINANCE_REPAY.getValue());
		sysLog.setOptType(EnumConstants.OptionType.FINANCE_REPAY.getValue());
		sysLog.setRemark("财务放款   借款ID   "+loan.getId().toString());
		sysLogService.insert(sysLog);
	}

	/**
	 * 乙方管理费流水
	 * 
	 * @param loan
	 * @param repayInfo
	 * @param externalAccountLedger
	 */
	private void BManageFlowBuilding(Loan loan, RepayInfo repayInfo,
			Ledger externalAccountLedger) {
		// 拆分流水 &&更新内部账户总账 &&更新外部账户总账
		// 乙方管理费
		flowService.createBManageFlow(repayInfo, loan);

		// 咨询账户总账(乙方管理费)
		ledgerService.updateBrokerBManage(loan);

		// 外部账户总账(乙方管理费)
		LedgerVO externalAccountLedgerVo = new LedgerVO();
		externalAccountLedgerVo.setId(externalAccountLedger.getId());
		BigDecimal manageExpense = externalAccountLedger.getManageExpense()
				.add(loan.getbManage());
		externalAccountLedgerVo.setManageExpense(manageExpense);
		ledgerService.update(externalAccountLedgerVo);
	}

	/**
	 * 咨询费
	 * 
	 * @param loan
	 * @param repayInfo
	 * @param externalAccountLedger
	 */
	private void ConsultFlowBuilding(Loan loan, RepayInfo repayInfo,
			Ledger externalAccountLedger) {
		// 咨询费
		flowService.createConsultFlow(repayInfo, loan);

		// 咨询账户总账(咨询费)
		ledgerService.updateBrokerConsult(loan);

		// 外部账户总账(咨询费)
		LedgerVO externalAccountLedgerVo = new LedgerVO();
		externalAccountLedgerVo.setId(externalAccountLedger.getId());
		BigDecimal consultExpense = externalAccountLedger.getConsultExpense()
				.add(loan.getConsult());
		externalAccountLedgerVo.setConsultExpense(consultExpense);
		ledgerService.update(externalAccountLedgerVo);
	}

	/**
	 * 评估费
	 * 
	 * @param loan
	 * @param repayInfo
	 * @param externalAccountLedger
	 */
	private void AssessmentFeeFlowBuilding(Loan loan, RepayInfo repayInfo,
			Ledger externalAccountLedger) {
		// 评估费
		flowService.createAssessmentFeeFlow(repayInfo, loan);

		// 咨询账户总账(评估费)
		ledgerService.updateBrokerAssessmentFee(loan);

		// 外部账户总账(评估费)
		LedgerVO externalAccountLedgerVo = new LedgerVO();
		externalAccountLedgerVo.setId(externalAccountLedger.getId());
		BigDecimal assessmentFeeExpense = externalAccountLedger
				.getAssessmentFeeExpense().add(loan.getAssessment());
		externalAccountLedgerVo.setAssessmentFeeExpense(assessmentFeeExpense);
		ledgerService.update(externalAccountLedgerVo);
	}

	/**
	 * 丙方管理费
	 * 
	 * @param loan
	 * @param repayInfo
	 * @param externalAccountLedger
	 */
	private void CManageFlowBuilding(Loan loan, RepayInfo repayInfo,
			Ledger externalAccountLedger) {
		// 丙方管理费
		flowService.createCManageFlow(repayInfo, loan);

		// 财富账户总账(丙方管理费)
		ledgerService.updateTreasureCManage(loan);

		// 外部账户总账(丙方管理费)
		BigDecimal cManage = externalAccountLedger.getManageExpense().add(
				loan.getcManage());
		LedgerVO externalAccountLedgerVo = new LedgerVO();
		externalAccountLedgerVo.setId(externalAccountLedger.getId());
		externalAccountLedgerVo.setManageExpense(cManage);
		ledgerService.update(externalAccountLedgerVo);
	}

	/**
	 * 合同金额
	 * 
	 * @param loan
	 * @param repayInfo
	 * @param externalAccountLedger
	 */
	private void LoanFlowBuilding(Loan loan, RepayInfo repayInfo,
			Ledger externalAccountLedger) {
		// 合同金额
		flowService.createLoanFlow(repayInfo, loan);
		// 居间人账户总账(合同金额)
		ledgerService.updateBrokerPactMoney(loan);

		// 外部账户总账(合同金额)
		BigDecimal cash = externalAccountLedger.getLoanAmount().add(
				loan.getPactMoney());
		LedgerVO externalAccountLedgerVo = new LedgerVO();
		externalAccountLedgerVo.setId(externalAccountLedger.getId());
		externalAccountLedgerVo.setLoanAmount(cash);
		ledgerService.update(externalAccountLedgerVo);
	}

	/**
	 * 风险金
	 * 
	 * @param loan
	 * @param repayInfo
	 * @param externalAccountLedger
	 */
	private void RiskFlowBuilding(Loan loan, RepayInfo repayInfo,
			Ledger externalAccountLedger) {
		// 风险金
		flowService.createRiskFlow(repayInfo, loan);
		// 风险金账户总账(风险金)
		ledgerService.updateRisk(loan);

		// 外部账户总账(风险金)
		BigDecimal risk = externalAccountLedger.getOtherExpense().add(
				loan.getRisk());
		LedgerVO externalAccountLedgerVo = new LedgerVO();
		externalAccountLedgerVo.setId(externalAccountLedger.getId());
		externalAccountLedgerVo.setOtherExpense(risk);
		ledgerService.update(externalAccountLedgerVo);
	}

	@Override
	public void financeReturn(ApproveResult approveResult, Integer borrowStatus) {
		Loan loan = loanService.get(approveResult.getLoanId());
		String lcbBorrowNo = loan.getLcbBorrowNo();

		// 在推标处 处理
//		int lastNum = Integer.parseInt(lcbBorrowNo.substring(lcbBorrowNo.lastIndexOf("_")+1));
//		lcbBorrowNo = lcbBorrowNo.substring(0, lcbBorrowNo.lastIndexOf("_")+1) + ( lastNum ++ );

		LoanVO loanVO = new LoanVO();
		loanVO.setId(approveResult.getLoanId());
		loanVO.setStatus(EnumConstants.LoanStatus.财务放款退回.getValue());
		loanVO.setFinanceAuditTime(new Date());
		// 设置捞财宝借款状态(终止借款 或者 流标)
		loanVO.setLcbBorrowStatus(borrowStatus);
		// 更新捞财宝流水号
		loanVO.setLcbBorrowNo(lcbBorrowNo);
		loanService.update(loanVO);

		approveResult
				.setState(EnumConstants.ApproveResultState.FINANCIAL_LOAN_RETURN
						.getValue());
		approveResultService.insert(approveResult);

		BusinessLog businessLog = new BusinessLog();
		businessLog
				.setFlowStatus(EnumConstants.BusinessLogStatus.FINANCE_GRANT_RETURN
						.getValue());
		businessLog.setLoanId(approveResult.getLoanId());
		businessLog.setMessage(String.format("财务放款退回,一级原因：" + approveResult.getReason1() + ",二级原因：" +  approveResult.getReason2() + ",备注:" + approveResult.getReason()));
		
		businessLogService.insert(businessLog);
		// 插入系统日志
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.FINANCE_REPAY.getValue());
		sysLog.setOptType(EnumConstants.OptionType.FINANCE_REPAY_RETURN
				.getValue());
		sysLog.setRemark("财务放款退回  借款ID"+approveResult.getLoanId());
		sysLogService.insert(sysLog);
	}
	/**
	 * 前期利息（针对车贷短期流通类 新计算器）
	 * 
	 * @param loan
	 * @param repayInfo
	 * @param externalAccountLedger
	 */
	private void prophaseInterestFlowBuilding(Loan loan, RepayInfo repayInfo) {
		// 前期利息
		flowService.createprophaseInterestFlow(repayInfo, loan);
		 
 
		 
	}

}
