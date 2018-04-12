package com.ezendai.credit2.audit.assembler;

import com.ezendai.credit2.audit.model.RepaymentPlan;
import com.ezendai.credit2.audit.vo.RepaymentPlanVO;

/**
 * 
 * <pre>
 * 还款计划 VO/Model类型转换
 * </pre>
 *
 * @author zhangshihai
 * @version $Id: RepaymentPlanAssembler.java, v 0.1 2014年8月1日 上午11:25:10 zhangshihai Exp $
 */
public class RepaymentPlanAssembler {

	/**
	 * 
	 * <pre>
	 * Model转为VO类型
	 * </pre>
	 *
	 * @param repaymentPlan
	 * @return
	 */
	public static RepaymentPlanVO transferModel2VO (RepaymentPlan repaymentPlan) {
		if (repaymentPlan == null) {
			return null;
		}
		
		RepaymentPlanVO repaymentPlanVO = new RepaymentPlanVO();
		repaymentPlanVO.setId(repaymentPlan.getId());
		repaymentPlanVO.setLoanId(repaymentPlan.getLoanId());
		repaymentPlanVO.setStatus(repaymentPlan.getStatus());
		repaymentPlanVO.setRepayAmount(repaymentPlan.getRepayAmount());
		repaymentPlanVO.setCurNum(repaymentPlan.getCurNum());
		repaymentPlanVO.setPrincipalAmt(repaymentPlan.getPrincipalAmt());
		repaymentPlanVO.setOutstanding(repaymentPlan.getOutstanding());
		repaymentPlanVO.setAverageCapital(repaymentPlan.getAverageCapital());
		repaymentPlanVO.setRemainingPrincipal(repaymentPlan.getRemainingPrincipal());
		repaymentPlanVO.setPenalty(repaymentPlan.getPenalty());
		repaymentPlanVO.setRisk(repaymentPlan.getRisk());
		repaymentPlanVO.setOneTimeRepaymentAmount(repaymentPlan.getOneTimeRepaymentAmount());
		repaymentPlanVO.setFactReturnDate(repaymentPlan.getFactReturnDate());
		repaymentPlanVO.setDeficit(repaymentPlan.getDeficit());
		repaymentPlanVO.setManagePart0Fee(repaymentPlan.getManagePart0Fee());
		repaymentPlanVO.setManagePart1Fee(repaymentPlan.getManagePart1Fee());
		repaymentPlanVO.setReferRate(repaymentPlan.getReferRate());
		repaymentPlanVO.setEvalRate(repaymentPlan.getEvalRate());
		repaymentPlanVO.setInterestAmt(repaymentPlan.getInterestAmt());
		repaymentPlanVO.setRepayDay(repaymentPlan.getRepayDay());
		repaymentPlanVO.setPenaltyDate(repaymentPlan.getPenaltyDate());
		repaymentPlanVO.setVersion(repaymentPlan.getVersion());
		repaymentPlanVO.setCurRemainingEvalRate(repaymentPlan.getCurRemainingEvalRate());
		repaymentPlanVO.setCurRemainingInterestAmt(repaymentPlan.getCurRemainingInterestAmt());
		repaymentPlanVO.setCurRemainingManagePart0Fee(repaymentPlan.getCurRemainingManagePart0Fee());
		repaymentPlanVO.setCurRemainingManagePart1Fee(repaymentPlan.getCurRemainingManagePart1Fee());
		repaymentPlanVO.setCurRemainingReferRate(repaymentPlan.getCurRemainingReferRate());
		repaymentPlanVO.setCurRemainingRisk(repaymentPlan.getCurRemainingRisk());
		repaymentPlanVO.setCurRemainingPrincipal(repaymentPlan.getCurRemainingPrincipal());
		repaymentPlanVO.setRefund(repaymentPlan.getRefund());
		return repaymentPlanVO;
	}
	
	/**
	 * 
	 * <pre>
	 * VO转为Model类型
	 * </pre>
	 *
	 * @param repaymentPlanVO
	 * @return
	 */
	public static RepaymentPlan transferVO2Model (RepaymentPlanVO repaymentPlanVO) {
		if (repaymentPlanVO == null) {
			return null;
		}
		
		RepaymentPlan repaymentPlan = new RepaymentPlan();
		repaymentPlan.setId(repaymentPlanVO.getId());
		repaymentPlan.setLoanId(repaymentPlanVO.getLoanId());
		repaymentPlan.setStatus(repaymentPlanVO.getStatus());
		repaymentPlan.setRepayAmount(repaymentPlanVO.getRepayAmount());
		repaymentPlan.setCurNum(repaymentPlanVO.getCurNum());
		repaymentPlan.setPrincipalAmt(repaymentPlanVO.getPrincipalAmt());
		repaymentPlan.setOutstanding(repaymentPlanVO.getOutstanding());
		repaymentPlan.setAverageCapital(repaymentPlanVO.getAverageCapital());
		repaymentPlan.setRemainingPrincipal(repaymentPlanVO.getRemainingPrincipal());
		repaymentPlan.setPenalty(repaymentPlanVO.getPenalty());
		repaymentPlan.setRisk(repaymentPlanVO.getRisk());
		repaymentPlan.setOneTimeRepaymentAmount(repaymentPlanVO.getOneTimeRepaymentAmount());
		repaymentPlan.setFactReturnDate(repaymentPlanVO.getFactReturnDate());
		repaymentPlan.setDeficit(repaymentPlanVO.getDeficit());
		repaymentPlan.setManagePart0Fee(repaymentPlanVO.getManagePart0Fee());
		repaymentPlan.setManagePart1Fee(repaymentPlanVO.getManagePart1Fee());
		repaymentPlan.setReferRate(repaymentPlanVO.getReferRate());
		repaymentPlan.setEvalRate(repaymentPlanVO.getEvalRate());
		repaymentPlan.setInterestAmt(repaymentPlanVO.getInterestAmt());
		repaymentPlan.setRepayDay(repaymentPlanVO.getRepayDay());
		repaymentPlan.setPenaltyDate(repaymentPlanVO.getPenaltyDate());
		repaymentPlan.setVersion(repaymentPlanVO.getVersion());
		repaymentPlan.setCurRemainingEvalRate(repaymentPlanVO.getCurRemainingEvalRate());
		repaymentPlan.setCurRemainingInterestAmt(repaymentPlanVO.getCurRemainingInterestAmt());
		repaymentPlan.setCurRemainingManagePart0Fee(repaymentPlanVO.getCurRemainingManagePart0Fee());
		repaymentPlan.setCurRemainingManagePart1Fee(repaymentPlanVO.getCurRemainingManagePart1Fee());
		repaymentPlan.setCurRemainingReferRate(repaymentPlanVO.getCurRemainingReferRate());
		repaymentPlan.setCurRemainingRisk(repaymentPlanVO.getCurRemainingRisk());
		repaymentPlan.setCurRemainingPrincipal(repaymentPlanVO.getCurRemainingPrincipal());
		repaymentPlan.setRefund(repaymentPlanVO.getRefund());
		return repaymentPlan;
	}
}
