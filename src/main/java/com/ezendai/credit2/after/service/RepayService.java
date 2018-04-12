package com.ezendai.credit2.after.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.ezendai.credit2.audit.model.RepaymentPlan;

/**
 * <pre>
 * 还款相关Service接口定义
 * </pre>
 *
 * @author chenqi
 * @version $Id: RepayService.java, v 0.1 2014年11月26日 上午11:26:36 chenqi Exp $
 */
public interface RepayService {

	/**
	 * <pre>
	 * 根据当前日期和借款Id获取报盘金额
	 * </pre>
	 *
	 * @param currDate
	 * @param loanId
	 * @return
	 */
	BigDecimal getOfferAmount(Date currDate, Long loanId);

	/**
	 * <pre>
	 * 根据还款日期和loan返回所有逾期或者未还款的还款计划
	 * </pre>
	 *
	 * @param currDate
	 * @param loanId
	 * @return
	 */
	List<RepaymentPlan> getAllInterestOrLoan(Date currDate, Long loanId);
	
	
	/**
	 * <pre>
	 * 根据loan返回所有逾期或者未还款的还款计划
	 * </pre>
	 *
	 * @param loanId
	 * @return
	 */
	List<RepaymentPlan> getAllInterestOrLoan(Long loanId);
	/**
	 * <pre>
	 * 根据还款日期和loan返回所有已结清的还款计划
	 * </pre>
	 *
	 * @param currDate
	 * @param loanId
	 * @return
	 */
	List<RepaymentPlan> getAllInterestOrLoanBySettle(Date currDate, Long loanId);

	/**
	* <pre>
	*通过借款ID获取挂账金额
	* </pre>
	*
	* @param loanId
	* @return
	*/
	BigDecimal getAccAmount(Long loanId);

	/**
	 * <pre>
	 * 获取当期应还的本金
	 * </pre>
	 *
	 * @param repaymentPlanList
	 * @param currDate
	 * @return
	 */
	BigDecimal getCurrPrincipal(List<RepaymentPlan> repaymentPlanList, Date currDate);

	/**
	 * <pre>
	 * 获取当期应还的利息
	 * </pre>
	 *
	 * @param repaymentPlanList
	 * @param currDate
	 * @return
	 */
	BigDecimal getCurrInterest(List<RepaymentPlan> repaymentPlanList, Date currDate);

	/**
	 * <pre>
	 * 获取当期应还的管理费(乙方+丙方)
	 * </pre>
	 *
	 * @param repaymentPlanList
	 * @param currDate
	 * @return
	 */
	BigDecimal getCurrManageFee(List<RepaymentPlan> repaymentPlanList, Date currDate);

	/**
	* <pre>
	* 获取一次性结清金额
	* </pre>
	*
	* @param repaymentPlanList
	* @param currDate
	* @return
	*/
	BigDecimal getOnetimeRepaymentAmount(List<RepaymentPlan> repaymentPlanList, Date currDate);

	/**
	* <pre>
	* 获取逾期金额
	* </pre>
	*
	* @param repaymentPlanList
	* @param currDate
	* @return
	*/
	BigDecimal getOverdueAmount(List<RepaymentPlan> repaymentPlanList, Date currDate);

	/**
	 * <pre>
	 * 获取逾期本金
	 * </pre>
	 *
	 * @param repaymentPlanList
	 * @param currDate
	 * @return
	 */
	BigDecimal getOverduePrincipal(List<RepaymentPlan> repaymentPlanList, Date currDate);

	/**
	 * <pre>
	 * 获取逾期利息
	 * </pre>
	 *
	 * @param repaymentPlanList
	 * @param currDate
	 * @return
	 */
	BigDecimal getOverdueInterest(List<RepaymentPlan> repaymentPlanList, Date currDate);

	/**
	 * <pre>
	 * 获取逾期管理费
	 * </pre>
	 *
	 * @param repaymentPlanList
	 * @param currDate
	 * @return
	 */
	BigDecimal getOverdueManageFee(List<RepaymentPlan> repaymentPlanList, Date currDate);

	/**
	 * <pre>
	 * 获取逾期的其他费用(包括咨询费,评估费,风险金)
	 * </pre>
	 *
	 * @param repaymentPlanList
	 * @param currDate
	 * @return
	 */
	BigDecimal getOverdueOtherFee(List<RepaymentPlan> repaymentPlanList, Date currDate);

	/**
	 * <pre>
	 * 获取逾期罚息天数
	 * </pre>
	 *
	 * @param repaymentPlanList
	 * @param currDate
	 * @return
	 */
	int getOverdueFineDay(List<RepaymentPlan> repaymentPlanList, Date currDate);

	/**
	 * <pre>
	 *  通过传入借款计划list和日期 获取逾期罚息
	 * </pre>
	 *
	 * @param repaymentPlanList
	 * @param currDate
	 * @return
	 */
	BigDecimal getFine(List<RepaymentPlan> repaymentPlanList, Date currDate);

	/**
	 * <pre>
	 * 获取提前还款违约金
	 * </pre>
	 *
	 * @param repaymentPlanList
	 * @param currDate
	 * @return
	 */
	BigDecimal getPenalty(List<RepaymentPlan> repaymentPlanList, Date currDate);

	/**
	 * <pre>
	 * 获取当前期数
	 * </pre>
	 *
	 * @param repaymentPlanList
	 * @param currDate
	 * @return
	 */
	int getCurrTerm(List<RepaymentPlan> repaymentPlanList, Date currDate);

	/**
	 * <pre>
	 * 获取还款日当期应还
	 * </pre>
	 *
	 * @param repaymentPlanList
	 * @param currDate
	 * @return
	 */
	BigDecimal getCurrDeficitForRepayDay(List<RepaymentPlan> repaymentPlanList, Date currDate);

	/**
	 * <pre>
	 * 获取当期应还
	 * </pre>
	 *
	 * @param repaymentPlanList
	 * @param currDate
	 * @return
	 */
	BigDecimal getCurrRepayAmount(List<RepaymentPlan> repaymentPlanList, Date currDate);

	/**
	 * <pre>
	 * 获取下期应还
	 * </pre>
	 *
	 * @param repaymentPlanList
	 * @param currDate
	 * @return
	 */
	BigDecimal getNextRepayAmount(List<RepaymentPlan> repaymentPlanList, Date currDate);

	/**
	 * <pre>
	 * 获取逾期的总期数
	 * </pre>
	 *
	 * @param repaymentPlanList
	 * @param currDate
	 * @return
	 */
	int getOverdueTermCount(List<RepaymentPlan> repaymentPlanList, Date currDate);

	/**
	 * <pre>
	 *  计算至当前所有应还总和
	 * </pre>
	 *
	 * @param repaymentPlanList
	 * @param currDate
	 * @return
	 */
	BigDecimal getSumDeficit(List<RepaymentPlan> repaymentPlanList, Date currDate);

	/**
	 * <pre>
	 * 计算最大还款金额
	 * </pre>
	 *
	 * @param repaymentPlanList
	 * @param currDate
	 * @return
	 */
	BigDecimal getMaxRepayMoney(List<RepaymentPlan> repaymentPlanList, Date currDate);
	
	/**
	 * <pre>
	 * 计算车贷有展期的借款的最大还款金额
	 * </pre>
	 *
	 * @param repaymentPlanList
	 * @param currDate
	 * @return
	 */
	BigDecimal getCarExtensionMaxRepayMoney(List<RepaymentPlan> repaymentPlanList, Date currDate);


}
