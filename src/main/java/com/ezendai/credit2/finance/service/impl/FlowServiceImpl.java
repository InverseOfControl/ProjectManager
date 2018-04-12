package com.ezendai.credit2.finance.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.finance.assembler.FlowAssembler;
import com.ezendai.credit2.finance.dao.FlowDao;
import com.ezendai.credit2.finance.model.Flow;
import com.ezendai.credit2.finance.model.RepayInfo;
import com.ezendai.credit2.finance.service.FlowService;
import com.ezendai.credit2.finance.vo.FlowVO;
import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.master.constant.BizConstants;
import com.ezendai.credit2.master.enumerate.EnumConstants;

/**
 * 
 * @author liyuepeng
 * 
 */
@Service
public class FlowServiceImpl implements FlowService {

	@Autowired
	private FlowDao flowDao;

	@Override
	public Flow insert(Flow flow) {
		return flowDao.insert(flow);
	}

	@Override
	public void createBManageFlow(RepayInfo repayInfo, Loan loan) {
		FlowVO flowVo = new FlowVO();
		flowVo.setAccountId(loan.getId());
		flowVo.setAccountTitle(EnumConstants.AccountTitle.B_MANAGE_EXPENSE
				.getValue());
		flowVo.setOppAccount(EnumConstants.InternalAccount.CONSULT_ACCOUNT
				.getValue().longValue());
		flowVo.setOppAccountTitle(EnumConstants.AccountTitle.MANAGE_INCOME
				.getValue());
		flowVo.setdOrC("D");
		flowVo.setOppDOrC("C");
		flowVo.setTradeTime(new Date());
		flowVo.setTradeCode(BizConstants.TRADE_CODE_OPENACC);
		flowVo.setTradeAmount(loan.getbManage());
		flowVo.setTradeType(EnumConstants.TradeType.CASH.getValue());
		flowVo.setTradeKind(EnumConstants.TradeKind.NORMAL_TRADE.getValue());
		flowVo.setTradeNo(repayInfo.getTradeNo());
		flowVo.setTeller(ApplicationContext.getUser().getId());
		if (repayInfo.getSalesdepartmentId() != null) {
			flowVo.setSalesdepartmentId(repayInfo.getSalesdepartmentId());
		}
		flowVo.setTerm(loan.getAuditTime());
		Flow flow = FlowAssembler.transferVO2Model(flowVo);
		flowDao.insert(flow);

	}

	@Override
	public void createConsultFlow(RepayInfo repayInfo, Loan loan) {
		FlowVO flowVo = new FlowVO();
		flowVo.setAccountId(loan.getId());
		flowVo.setAccountTitle(EnumConstants.AccountTitle.CONSULT_EXPENSE
				.getValue());
		flowVo.setOppAccount(EnumConstants.InternalAccount.CONSULT_ACCOUNT
				.getValue().longValue());
		flowVo.setOppAccountTitle(EnumConstants.AccountTitle.CONSULT_INCOME
				.getValue());
		flowVo.setdOrC("D");
		flowVo.setOppDOrC("C");
		flowVo.setTradeTime(new Date());
		flowVo.setTradeCode(BizConstants.TRADE_CODE_OPENACC);
		flowVo.setTradeAmount(loan.getConsult());
		flowVo.setTradeType(EnumConstants.TradeType.CASH.getValue());
		flowVo.setTradeKind(EnumConstants.TradeKind.NORMAL_TRADE.getValue());
		flowVo.setTradeNo(repayInfo.getTradeNo());
		flowVo.setTeller(ApplicationContext.getUser().getId());
		if (repayInfo.getSalesdepartmentId() != null) {
			flowVo.setSalesdepartmentId(repayInfo.getSalesdepartmentId());
		}
		flowVo.setTerm(loan.getAuditTime());
		Flow flow = FlowAssembler.transferVO2Model(flowVo);
		flowDao.insert(flow);

	}

	@Override
	public void createAssessmentFeeFlow(RepayInfo repayInfo, Loan loan) {
		FlowVO flowVo = new FlowVO();
		flowVo.setAccountId(loan.getId());
		flowVo.setAccountTitle(EnumConstants.AccountTitle.ASSESSMENT_FEE_EXPENSE
				.getValue());
		flowVo.setOppAccount(EnumConstants.InternalAccount.CONSULT_ACCOUNT
				.getValue().longValue());
		flowVo.setOppAccountTitle(EnumConstants.AccountTitle.ASSESSMENT_FEE_INCOME
				.getValue());
		flowVo.setdOrC("D");
		flowVo.setOppDOrC("C");
		flowVo.setTradeTime(new Date());
		flowVo.setTradeCode(BizConstants.TRADE_CODE_OPENACC);
		flowVo.setTradeAmount(loan.getAssessment());
		flowVo.setTradeType(EnumConstants.TradeType.CASH.getValue());
		flowVo.setTradeKind(EnumConstants.TradeKind.NORMAL_TRADE.getValue());
		flowVo.setTradeNo(repayInfo.getTradeNo());
		flowVo.setTeller(ApplicationContext.getUser().getId());
		if (repayInfo.getSalesdepartmentId() != null) {
			flowVo.setSalesdepartmentId(repayInfo.getSalesdepartmentId());
		}
		flowVo.setTerm(loan.getAuditTime());
		Flow flow = FlowAssembler.transferVO2Model(flowVo);
		flowDao.insert(flow);

	}

	@Override
	public void createCManageFlow(RepayInfo repayInfo, Loan loan) {
		FlowVO flowVo = new FlowVO();
		flowVo.setAccountId(loan.getId());
		flowVo.setAccountTitle(EnumConstants.AccountTitle.C_MANAGE_EXPENSE
				.getValue());
		flowVo.setOppAccount(EnumConstants.InternalAccount.TREASURE_ACCOUNT
				.getValue().longValue());
		flowVo.setOppAccountTitle(EnumConstants.AccountTitle.MANAGE_INCOME
				.getValue());
		flowVo.setdOrC("D");
		flowVo.setOppDOrC("C");
		flowVo.setTradeTime(new Date());
		flowVo.setTradeCode(BizConstants.TRADE_CODE_OPENACC);
		flowVo.setTradeAmount(loan.getcManage());
		flowVo.setTradeType(EnumConstants.TradeType.CASH.getValue());
		flowVo.setTradeKind(EnumConstants.TradeKind.NORMAL_TRADE.getValue());
		flowVo.setTradeNo(repayInfo.getTradeNo());
		flowVo.setTeller(ApplicationContext.getUser().getId());
		if (repayInfo.getSalesdepartmentId() != null) {
			flowVo.setSalesdepartmentId(repayInfo.getSalesdepartmentId());
		}
		flowVo.setTerm(loan.getAuditTime());
		Flow flow = FlowAssembler.transferVO2Model(flowVo);
		flowDao.insert(flow);

	}

	@Override
	public void createLoanFlow(RepayInfo repayInfo, Loan loan) {
		FlowVO flowVo = new FlowVO();
		flowVo.setAccountId(loan.getId());
		flowVo.setAccountTitle(EnumConstants.AccountTitle.LOAN_AMOUNT
				.getValue());
		flowVo.setOppAccount(EnumConstants.InternalAccount.BROKER_ACCOUNT
				.getValue().longValue());
		flowVo.setOppAccountTitle(EnumConstants.AccountTitle.INVEST_AMOUNT
				.getValue());
		flowVo.setdOrC("C");
		flowVo.setOppDOrC("D");
		flowVo.setTradeTime(new Date());
		flowVo.setTradeCode(BizConstants.TRADE_CODE_OPENACC);
		flowVo.setTradeAmount(loan.getPactMoney());
		flowVo.setTradeType(EnumConstants.TradeType.CASH.getValue());
		flowVo.setTradeKind(EnumConstants.TradeKind.NORMAL_TRADE.getValue());
		flowVo.setTradeNo(repayInfo.getTradeNo());
		flowVo.setTeller(ApplicationContext.getUser().getId());
		if (repayInfo.getSalesdepartmentId() != null) {
			flowVo.setSalesdepartmentId(repayInfo.getSalesdepartmentId());
		}
		flowVo.setTerm(loan.getAuditTime());
		Flow flow = FlowAssembler.transferVO2Model(flowVo);
		flowDao.insert(flow);

	}
	@Override
	public void createprophaseInterestFlow(RepayInfo repayInfo, Loan loan) {
		FlowVO flowVo = new FlowVO();
		flowVo.setAccountId(loan.getId());
		flowVo.setAccountTitle(EnumConstants.AccountTitle.INTEREST_EXPENSE
				.getValue());
		flowVo.setOppAccount(EnumConstants.InternalAccount.BROKER_ACCOUNT
				.getValue().longValue());
		flowVo.setOppAccountTitle(EnumConstants.AccountTitle.FINE_INCOME
				.getValue());
		flowVo.setdOrC("D");
		flowVo.setOppDOrC("C");
		flowVo.setTradeTime(new Date());
		flowVo.setTradeCode(BizConstants.TRADE_CODE_OPENACC);
		flowVo.setTradeAmount(loan.getProphaseInterest());
		flowVo.setTradeType(EnumConstants.TradeType.CASH.getValue());
		flowVo.setTradeKind(EnumConstants.TradeKind.NORMAL_TRADE.getValue());
		flowVo.setTradeNo(repayInfo.getTradeNo());
		flowVo.setTeller(ApplicationContext.getUser().getId());
		if (repayInfo.getSalesdepartmentId() != null) {
			flowVo.setSalesdepartmentId(repayInfo.getSalesdepartmentId());
		}
		flowVo.setTerm(loan.getAuditTime());
		Flow flow = FlowAssembler.transferVO2Model(flowVo);
		flowDao.insert(flow);

	}
	 
	@Override
	public void createRiskFlow(RepayInfo repayInfo, Loan loan) {
		FlowVO flowVo = new FlowVO();
		flowVo.setAccountId(loan.getId());
		flowVo.setAccountTitle(EnumConstants.AccountTitle.RISK_EXPENSE
				.getValue());
		flowVo.setOppAccount(EnumConstants.InternalAccount.RISK_ACCOUNT
				.getValue().longValue());
		flowVo.setOppAccountTitle(EnumConstants.AccountTitle.RISK_INCOME
				.getValue());
		flowVo.setdOrC("D");
		flowVo.setOppDOrC("C");
		flowVo.setTradeTime(new Date());
		flowVo.setTradeCode(BizConstants.TRADE_CODE_OPENACC);
		flowVo.setTradeAmount(loan.getRisk());
		flowVo.setTradeType(EnumConstants.TradeType.CASH.getValue());
		flowVo.setTradeKind(EnumConstants.TradeKind.NORMAL_TRADE.getValue());
		flowVo.setTradeNo(repayInfo.getTradeNo());
		flowVo.setTeller(ApplicationContext.getUser().getId());
		if (repayInfo.getSalesdepartmentId() != null) {
			flowVo.setSalesdepartmentId(repayInfo.getSalesdepartmentId());
		}
		flowVo.setTerm(loan.getAuditTime());
		Flow flow = FlowAssembler.transferVO2Model(flowVo);
		flowDao.insert(flow);

	}

	@Override
	public Flow createFlow(RepayInfo repayInfo, BigDecimal amount,
			String acctTitle, String memo, String dOrC, String oppDOrC,Integer term) {
		//金额小于0.01，不生成流水
		if(amount.compareTo(new BigDecimal("0.01"))<0){
			return null;
		}
		Flow flow = new Flow();
		flow.setAccountId(repayInfo.getAccountId());
		flow.setTradeKind(repayInfo.getTradeKind());
		flow.setTradeCode(repayInfo.getTradeCode());
		flow.setTradeType(repayInfo.getPayType());
		flow.setTradeNo(repayInfo.getTradeNo());
		flow.setTradeTime(repayInfo.getTradeTime());
		flow.setTeller(repayInfo.getTeller());
		flow.setAuthorizedTeller(repayInfo.getAuthorizedTeller());
		if (repayInfo.getSalesdepartmentId() != null) {
			flow.setSalesdepartmentId(repayInfo.getSalesdepartmentId());
		}
		flow.setRemark(repayInfo.getRemark());
		flow.setReversedNo(repayInfo.getReversedNo());
		flow.setAccountTitle(acctTitle);
		flow.setdOrC(dOrC);
		flow.setOppDOrC(oppDOrC);
		flow.setTradeAmount(amount);
		flow.setTerm(term);
		//现金
		if(acctTitle.equals(EnumConstants.AccountTitle.AMOUNT.getValue())){
			flow.setOppAccount(EnumConstants.InternalAccount.REPAYMENT_ACCOUNT.getValue().longValue());
			flow.setOppAccountTitle(EnumConstants.AccountTitle.AMOUNT.getValue());
		}
		//乙方管理费
		else if(acctTitle.equals(EnumConstants.AccountTitle.B_MANAGE_EXPENSE.getValue())){
			flow.setOppAccount(EnumConstants.InternalAccount.CONSULT_ACCOUNT.getValue().longValue());
			flow.setOppAccountTitle(EnumConstants.AccountTitle.MANAGE_INCOME.getValue());
		}
		//丙方管理费
		else if(acctTitle.equals(EnumConstants.AccountTitle.C_MANAGE_EXPENSE.getValue())){
			flow.setOppAccount(EnumConstants.InternalAccount.TREASURE_ACCOUNT.getValue().longValue());
			flow.setOppAccountTitle(EnumConstants.AccountTitle.MANAGE_INCOME.getValue());
		}
		//咨询费支出
		else if(acctTitle.equals(EnumConstants.AccountTitle.CONSULT_EXPENSE.getValue())){
			flow.setOppAccount(EnumConstants.InternalAccount.CONSULT_ACCOUNT.getValue().longValue());
			flow.setOppAccountTitle(EnumConstants.AccountTitle.CONSULT_INCOME.getValue());
		}
		//评估费支出
		else if(acctTitle.equals(EnumConstants.AccountTitle.ASSESSMENT_FEE_EXPENSE.getValue())){
			flow.setOppAccount(EnumConstants.InternalAccount.CONSULT_ACCOUNT.getValue().longValue());
			flow.setOppAccountTitle(EnumConstants.AccountTitle.ASSESSMENT_FEE_INCOME.getValue());
		}
		//贷款本金
		else if(acctTitle.equals(EnumConstants.AccountTitle.LOAN_AMOUNT.getValue())){
			flow.setOppAccount(EnumConstants.InternalAccount.BROKER_ACCOUNT.getValue().longValue());
			flow.setOppAccountTitle(EnumConstants.AccountTitle.INVEST_AMOUNT.getValue());
		}
		//风险金支出 
		else if (acctTitle.equals(EnumConstants.AccountTitle.RISK_EXPENSE.getValue())) {
			flow.setOppAccount(EnumConstants.InternalAccount.RISK_ACCOUNT.getValue()
				.longValue());
			flow.setOppAccountTitle(EnumConstants.AccountTitle.RISK_INCOME.getValue());
		}
		//罚息支出 
		else if(acctTitle.equals(EnumConstants.AccountTitle.FINE_EXPENSE.getValue())){
			flow.setOppAccount(EnumConstants.InternalAccount.GAINS_ACCOUNT.getValue().longValue());
			flow.setOppAccountTitle(EnumConstants.AccountTitle.FINE_INCOME.getValue());
		}
		//违约金支出 
		else if(acctTitle.equals(EnumConstants.AccountTitle.PENALTY_EXPENSE.getValue())){
			flow.setOppAccount(EnumConstants.InternalAccount.GAINS_ACCOUNT.getValue().longValue());
			flow.setOppAccountTitle(EnumConstants.AccountTitle.PENALTY_INCOME.getValue());
		}
		//利息支出 
		else if(acctTitle.equals(EnumConstants.AccountTitle.INTEREST_EXPENSE.getValue())){
			flow.setOppAccount(EnumConstants.InternalAccount.BROKER_ACCOUNT.getValue().longValue());
			flow.setOppAccountTitle(EnumConstants.AccountTitle.INTEREST_INCOME.getValue());
		}
		//其他应收款
		else if(acctTitle.equals(EnumConstants.AccountTitle.OTHER_RECEI.getValue())){
			flow.setOppAccount(EnumConstants.InternalAccount.RISK_ACCOUNT.getValue().longValue());
			flow.setOppAccountTitle(EnumConstants.AccountTitle.OTHER_PAYABLE.getValue());
		}
		//其他应付款
		else if(acctTitle.equals(EnumConstants.AccountTitle.OTHER_PAYABLE.getValue())){
			flow.setOppAccount(EnumConstants.InternalAccount.RISK_ACCOUNT.getValue().longValue());
			flow.setOppAccountTitle(EnumConstants.AccountTitle.RISK_INCOME.getValue());
		}
		//罚息收入
		else if(acctTitle.equals(EnumConstants.AccountTitle.FINE_INCOME.getValue())){
			flow.setAccountId(EnumConstants.InternalAccount.GAINS_ACCOUNT.getValue().longValue());
			flow.setOppAccount(repayInfo.getAccountId());
			flow.setOppAccountTitle(EnumConstants.AccountTitle.FINE_EXPENSE.getValue());
		}
		flow = flowDao.insert(flow);
		return flow;
	}
	
	@Override
	public Flow createFlowForOther(RepayInfo repayInfo, BigDecimal amount,
			String acctTitle, String memo, String dOrC, String oppDOrC,Integer term) {
		//金额小于0.01，不生成流水
		if(amount.compareTo(new BigDecimal("0.01"))<0){
			return null;
		}
		Flow flow = new Flow();
		flow.setAccountId(repayInfo.getAccountId());
		flow.setTradeKind(repayInfo.getTradeKind());
		flow.setTradeCode(repayInfo.getTradeCode());
		flow.setTradeType(repayInfo.getPayType());
		flow.setTradeNo(repayInfo.getTradeNo());
		flow.setTradeTime(repayInfo.getTradeTime());
		flow.setTeller(repayInfo.getTeller());
		flow.setAuthorizedTeller(repayInfo.getAuthorizedTeller());
		if (repayInfo.getSalesdepartmentId() != null) {
			flow.setSalesdepartmentId(repayInfo.getSalesdepartmentId());
		}
		flow.setRemark(repayInfo.getRemark());
		flow.setReversedNo(repayInfo.getReversedNo());
		flow.setAccountTitle(acctTitle);
		flow.setdOrC(dOrC);
		flow.setOppDOrC(oppDOrC);
		flow.setTradeAmount(amount);
		flow.setTerm(term);
		//现金
		if(acctTitle.equals(EnumConstants.AccountTitle.AMOUNT.getValue())){
			flow.setOppAccount(EnumConstants.InternalAccount.REPAYMENT_ACCOUNT.getValue().longValue());
			flow.setOppAccountTitle(EnumConstants.AccountTitle.AMOUNT.getValue());
		}
		//乙方管理费
		else if(acctTitle.equals(EnumConstants.AccountTitle.B_MANAGE_EXPENSE.getValue())){
			flow.setOppAccount(EnumConstants.InternalAccount.CONSULT_ACCOUNT.getValue().longValue());
			flow.setOppAccountTitle(EnumConstants.AccountTitle.MANAGE_INCOME.getValue());
		}
		//丙方管理费
		else if(acctTitle.equals(EnumConstants.AccountTitle.C_MANAGE_EXPENSE.getValue())){
			flow.setOppAccount(EnumConstants.InternalAccount.TREASURE_ACCOUNT.getValue().longValue());
			flow.setOppAccountTitle(EnumConstants.AccountTitle.MANAGE_INCOME.getValue());
		}
		//咨询费支出
		else if(acctTitle.equals(EnumConstants.AccountTitle.CONSULT_EXPENSE.getValue())){
			flow.setOppAccount(EnumConstants.InternalAccount.CONSULT_ACCOUNT.getValue().longValue());
			flow.setOppAccountTitle(EnumConstants.AccountTitle.CONSULT_INCOME.getValue());
		}
		//评估费支出
		else if(acctTitle.equals(EnumConstants.AccountTitle.ASSESSMENT_FEE_EXPENSE.getValue())){
			flow.setOppAccount(EnumConstants.InternalAccount.CONSULT_ACCOUNT.getValue().longValue());
			flow.setOppAccountTitle(EnumConstants.AccountTitle.ASSESSMENT_FEE_INCOME.getValue());
		}
		//贷款本金
		else if(acctTitle.equals(EnumConstants.AccountTitle.LOAN_AMOUNT.getValue())){
			flow.setOppAccount(EnumConstants.InternalAccount.BROKER_ACCOUNT.getValue().longValue());
			flow.setOppAccountTitle(EnumConstants.AccountTitle.INVEST_AMOUNT.getValue());
		}
		//风险金支出 
		else if (acctTitle.equals(EnumConstants.AccountTitle.RISK_EXPENSE.getValue())) {
			flow.setOppAccount(EnumConstants.InternalAccount.RISK_ACCOUNT.getValue()
				.longValue());
			flow.setOppAccountTitle(EnumConstants.AccountTitle.RISK_INCOME.getValue());
		}
		//罚息支出 
		else if(acctTitle.equals(EnumConstants.AccountTitle.FINE_EXPENSE.getValue())){
			flow.setOppAccount(EnumConstants.InternalAccount.GAINS_ACCOUNT.getValue().longValue());
			flow.setOppAccountTitle(EnumConstants.AccountTitle.FINE_INCOME.getValue());
		}
		//违约金支出 
		else if(acctTitle.equals(EnumConstants.AccountTitle.PENALTY_EXPENSE.getValue())){
			flow.setOppAccount(EnumConstants.InternalAccount.GAINS_ACCOUNT.getValue().longValue());
			flow.setOppAccountTitle(EnumConstants.AccountTitle.PENALTY_INCOME.getValue());
		}
		//利息支出 
		else if(acctTitle.equals(EnumConstants.AccountTitle.INTEREST_EXPENSE.getValue())){
			flow.setOppAccount(EnumConstants.InternalAccount.BROKER_ACCOUNT.getValue().longValue());
			flow.setOppAccountTitle(EnumConstants.AccountTitle.INTEREST_INCOME.getValue());
		}
		//其他应收款
		else if(acctTitle.equals(EnumConstants.AccountTitle.OTHER_RECEI.getValue())){
			flow.setOppAccount(EnumConstants.InternalAccount.RISK_ACCOUNT.getValue().longValue());
			flow.setOppAccountTitle(EnumConstants.AccountTitle.OTHER_PAYABLE.getValue());
		}
		//其他应付款
		else if(acctTitle.equals(EnumConstants.AccountTitle.OTHER_PAYABLE.getValue())){
			flow.setOppAccount(EnumConstants.InternalAccount.RISK_ACCOUNT.getValue().longValue());
			flow.setOppAccountTitle(EnumConstants.AccountTitle.RISK_INCOME.getValue());
		}
		//罚息收入
		else if(acctTitle.equals(EnumConstants.AccountTitle.FINE_INCOME.getValue())){
			flow.setAccountId(EnumConstants.InternalAccount.GAINS_ACCOUNT.getValue().longValue());
			flow.setOppAccount(repayInfo.getAccountId());
			flow.setOppAccountTitle(EnumConstants.AccountTitle.FINE_EXPENSE.getValue());
		}
		flow = flowDao.insert(flow);
		return flow;
	}
	
	@Override
	public Flow createFlow(RepayInfo repayInfo, BigDecimal amount, Long acct, String acctTitle,
							Long oppAcct, String oppAcctTitle, String memo, String dOrC,
							String oppDOrC) {
		//金额小于0.01，不生成流水
		if(amount.compareTo(new BigDecimal("0.01"))<0){
			return null;
		}
		Flow flow = new Flow();
		flow.setOppAccount(oppAcct);
		flow.setOppAccountTitle(oppAcctTitle);
		flow.setAccountId(acct);
		flow.setTradeKind(repayInfo.getTradeKind());
		flow.setTradeCode(repayInfo.getTradeCode());
		flow.setTradeType(repayInfo.getPayType());
		flow.setTradeNo(repayInfo.getTradeNo());
		flow.setTradeTime(repayInfo.getTradeTime());
		flow.setTeller(repayInfo.getTeller());
		flow.setAuthorizedTeller(repayInfo.getAuthorizedTeller());
		if (repayInfo.getSalesdepartmentId() != null) {
			flow.setSalesdepartmentId(repayInfo.getSalesdepartmentId());
		}
		flow.setRemark(repayInfo.getRemark());
		flow.setReversedNo(repayInfo.getReversedNo());
		flow.setAccountTitle(acctTitle);
		flow.setdOrC(dOrC);
		flow.setOppDOrC(oppDOrC);
		flow.setTradeAmount(amount);
		flow.setRemark(memo);
		flow = flowDao.insert(flow);
		return flow;
	}
	

	@Override
	public Flow getFlowByVO(FlowVO flowVO) {
		return flowDao.get(flowVO);
	}

	
	@Override
	public void deleteLegById(Long id) {
		flowDao.deleteById(id);
	}

	@Override
	public List<Flow> getListFlowByVO(FlowVO flowVO) {
		// TODO Auto-generated method stub
		return flowDao.getListFlowByVO(flowVO);
	}


}
