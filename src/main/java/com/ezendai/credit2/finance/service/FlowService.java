package com.ezendai.credit2.finance.service;

import java.math.BigDecimal;
import java.util.List;

import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.finance.model.Flow;
import com.ezendai.credit2.finance.model.RepayInfo;
import com.ezendai.credit2.finance.vo.FlowVO;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author liyuepeng
 */
public interface FlowService {
	
	Flow insert(Flow flow);
	
	/**
	 * 创建乙方管理费流水
	 * 
	 * @param repayInfo
	 * @param loan
	 */
	public void createBManageFlow(RepayInfo repayInfo, Loan loan);
	
	/**
	 * 创建咨询费流水
	 * 
	 * @param repayInfo
	 * @param loan
	 */
	public void createConsultFlow(RepayInfo repayInfo, Loan loan);
	
	/**
	 * 创建评估费流水
	 * 
	 * @param repayInfo
	 * @param loan
	 */
	public void createAssessmentFeeFlow(RepayInfo repayInfo, Loan loan);
	
	/**
	 * 创建丙方管理费流水
	 * 
	 * @param repayInfo
	 * @param loan
	 */
	public void createCManageFlow(RepayInfo repayInfo, Loan loan);
	
	/**
	 * 创建合同金额流水
	 * 
	 * @param repayInfo
	 * @param loan
	 */
	public void createLoanFlow(RepayInfo repayInfo, Loan loan);
	
	/**
	 * 创建风险金流水
	 * 
	 * @param repayInfo
	 * @param loan
	 */
	public void createRiskFlow(RepayInfo repayInfo, Loan loan);
	
	/**
	 * 创建前期利息流水
	 * 
	 * @param repayInfo
	 * @param loan
	 */
	public void createprophaseInterestFlow(RepayInfo repayInfo, Loan loan);
	
	/**
	 * 创建流水
	 * @param repayInfo
	 * @param amount
	 * @param acctTitle
	 * @param memo
	 * @param dOrc
	 * @param oppDOrC
	 * @param oppDOrC
	 * @return
	 */
	public Flow createFlow(RepayInfo repayInfo,BigDecimal amount,String acctTitle,String memo,String dOrC,String oppDOrC,Integer term);
	
	/**
	 * 创建流水
	 * @param repayInfo
	 * @param amount
	 * @param acctTitle
	 * @param memo
	 * @param dOrc
	 * @param oppDOrC
	 * @param oppDOrC
	 * @return
	 */
	public Flow createFlowForOther(RepayInfo repayInfo,BigDecimal amount,String acctTitle,String memo,String dOrC,String oppDOrC,Integer term);
	
	
	/**
	 * 
	 * <pre>
	 * 创建流水
	 * </pre>
	 *
	 * @param repayInfo
	 * @param amount
	 * @param acct
	 * @param acctTitle
	 * @param oppAcct
	 * @param oppAcctTitle
	 * @param memo
	 * @param dOrC
	 * @param oppDOrC
	 * @return
	 */
	public Flow createFlow(RepayInfo repayInfo,BigDecimal amount,Long acct,String acctTitle,Long oppAcct,String oppAcctTitle,String memo,String dOrC,String oppDOrC);
	
	/**
	 * 
	 * <pre>
	 * 根据VO查找flow
	 * </pre>
	 *
	 * @param flowVO
	 * @return
	 */
	public Flow getFlowByVO(FlowVO flowVO);
	
	/**
	 * 
	 * <pre>
	 * 通过id删除Flow
	 * </pre>
	 *
	 * @param Long id
	 * @return
	 */
    void deleteLegById(Long id);
    /**
	 * 
	 * <pre>
	 * 根据VO查找flow
	 * </pre>
	 *
	 * @param flowVO
	 * @return
	 */
	public List<Flow> getListFlowByVO(FlowVO flowVO);
}
