package com.ezendai.credit2.audit.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.ezendai.credit2.framework.vo.BaseVO;

public class RepaymentPlanVO extends BaseVO {
	

	private static final long serialVersionUID = -3186356612590100829L;

	/**贷款ID*/
	private Long loanId;

	/** 当前还款状态 */
	private Integer status;

	/**还款金额*/
	private BigDecimal repayAmount;

	/**当前期数*/
	private Integer curNum;

	/**还款本金*/
	private BigDecimal principalAmt;

	/**贷款余额*/
	private BigDecimal outstanding;

	/**每月应还减去管理费*/
	private BigDecimal averageCapital;

	/**剩余本金*/
	private BigDecimal remainingPrincipal;

	/** 违约金 */
	private BigDecimal penalty;

	/**风险金（车贷短期）*/
	private BigDecimal risk;

	/**当期一次性还款金额*/
	private BigDecimal oneTimeRepaymentAmount;

	/** 结清日期 */
	private Date factReturnDate;

	/** 剩余欠款，用于记录不足额部分 */
	private BigDecimal deficit;

	/**乙方管理费*/
	private BigDecimal managePart0Fee;

	/**丙方管理费*/
	private BigDecimal managePart1Fee;

	/**咨询费*/
	private BigDecimal referRate;

	/**评估费*/
	private BigDecimal evalRate;

	/**还款利息*/
	private BigDecimal interestAmt;

	/**还款日期*/
	private Date repayDay;
	
	/**还款日期开始*/
	private Date repayDayStart;
	
	/**还款日期结束*/
	private Date repayDayEnd;
	
	/**状态list**/
	 private List<Integer> statusList;
	 
	 /**特殊排序*/
	private String sort;
	
	 /**需要排除的状态**/
	 private Integer statusExcept;
	 
	/**当期剩余乙方管理费*/
	private BigDecimal curRemainingManagePart0Fee;

	/**当期剩余丙方管理费*/
	private BigDecimal curRemainingManagePart1Fee;

	/**当期剩余咨询费*/
	private BigDecimal curRemainingReferRate;

	/**当期剩余评估费*/
	private BigDecimal curRemainingEvalRate;
	
	/**当期剩余风险金*/
	private BigDecimal curRemainingRisk;

	/**当期剩余还款利息*/
	private BigDecimal curRemainingInterestAmt;
	
	/**当期剩余本金*/
	private BigDecimal curRemainingPrincipal;
	
	/**罚息起算日期*/
	private Date penaltyDate;
	
	/** 要排除的还款状态 */
	private Integer notStatus;
	
	/** 退费 */
	private BigDecimal refund;
	
	/** 要排除的状态list */
	private List<Integer> notStatusList;
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	private List<Long> idList;

	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public Integer getCurNum() {
		return curNum;
	}

	public void setCurNum(Integer curNum) {
		this.curNum = curNum;
	}

	public Date getRepayDay() {
		return repayDay;
	}

	public void setRepayDay(Date repayDay) {
		this.repayDay = repayDay;
	}

	public BigDecimal getRepayAmount() {
		return repayAmount;
	}

	public void setRepayAmount(BigDecimal repayAmount) {
		this.repayAmount = repayAmount;
	}

	public BigDecimal getPrincipalAmt() {
		return principalAmt;
	}

	public void setPrincipalAmt(BigDecimal principalAmt) {
		this.principalAmt = principalAmt;
	}

	public BigDecimal getOutstanding() {
		return outstanding;
	}

	public void setOutstanding(BigDecimal outstanding) {
		this.outstanding = outstanding;
	}

	public BigDecimal getManagePart0Fee() {
		return managePart0Fee;
	}

	public void setManagePart0Fee(BigDecimal managePart0Fee) {
		this.managePart0Fee = managePart0Fee;
	}

	public BigDecimal getManagePart1Fee() {
		return managePart1Fee;
	}

	public void setManagePart1Fee(BigDecimal managePart1Fee) {
		this.managePart1Fee = managePart1Fee;
	}

	public BigDecimal getReferRate() {
		return referRate;
	}

	public void setReferRate(BigDecimal referRate) {
		this.referRate = referRate;
	}

	public BigDecimal getEvalRate() {
		return evalRate;
	}

	public void setEvalRate(BigDecimal evalRate) {
		this.evalRate = evalRate;
	}

	public BigDecimal getInterestAmt() {
		return interestAmt;
	}

	public void setInterestAmt(BigDecimal interestAmt) {
		this.interestAmt = interestAmt;
	}

	public BigDecimal getAverageCapital() {
		return averageCapital;
	}

	public void setAverageCapital(BigDecimal averageCapital) {
		this.averageCapital = averageCapital;
	}

	public BigDecimal getRemainingPrincipal() {
		return remainingPrincipal;
	}

	public void setRemainingPrincipal(BigDecimal remainingPrincipal) {
		this.remainingPrincipal = remainingPrincipal;
	}

	public BigDecimal getPenalty() {
		return penalty;
	}

	public void setPenalty(BigDecimal penalty) {
		this.penalty = penalty;
	}

	public BigDecimal getOneTimeRepaymentAmount() {
		return oneTimeRepaymentAmount;
	}

	public void setOneTimeRepaymentAmount(BigDecimal oneTimeRepaymentAmount) {
		this.oneTimeRepaymentAmount = oneTimeRepaymentAmount;
	}

	public Date getFactReturnDate() {
		return factReturnDate;
	}

	public void setFactReturnDate(Date factReturnDate) {
		this.factReturnDate = factReturnDate;
	}

	public BigDecimal getDeficit() {
		return deficit;
	}

	public void setDeficit(BigDecimal deficit) {
		this.deficit = deficit;
	}

	public BigDecimal getRisk() {
		return risk;
	}

	public void setRisk(BigDecimal risk) {
		this.risk = risk;
	}

	public List<Long> getIdList() {
		return idList;
	}

	public void setIdList(List<Long> idList) {
		this.idList = idList;
	}
	public Date getRepayDayStart() {
		return repayDayStart;
	}

	public void setRepayDayStart(Date repayDayStart) {
		this.repayDayStart = repayDayStart;
	}

	public Date getRepayDayEnd() {
		return repayDayEnd;
	}

	public void setRepayDayEnd(Date repayDayEnd) {
		this.repayDayEnd = repayDayEnd;
	}

	public List<Integer> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<Integer> statusList) {
		this.statusList = statusList;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
	public Integer getStatusExcept() {
		return statusExcept;
	}

	public void setStatusExcept(Integer statusExcept) {
		this.statusExcept = statusExcept;
	}

	public BigDecimal getCurRemainingManagePart0Fee() {
		return curRemainingManagePart0Fee;
	}

	public void setCurRemainingManagePart0Fee(BigDecimal curRemainingManagePart0Fee) {
		this.curRemainingManagePart0Fee = curRemainingManagePart0Fee;
	}

	public BigDecimal getCurRemainingManagePart1Fee() {
		return curRemainingManagePart1Fee;
	}

	public void setCurRemainingManagePart1Fee(BigDecimal curRemainingManagePart1Fee) {
		this.curRemainingManagePart1Fee = curRemainingManagePart1Fee;
	}

	public BigDecimal getCurRemainingReferRate() {
		return curRemainingReferRate;
	}

	public void setCurRemainingReferRate(BigDecimal curRemainingReferRate) {
		this.curRemainingReferRate = curRemainingReferRate;
	}

	public BigDecimal getCurRemainingEvalRate() {
		return curRemainingEvalRate;
	}

	public void setCurRemainingEvalRate(BigDecimal curRemainingEvalRate) {
		this.curRemainingEvalRate = curRemainingEvalRate;
	}

	public BigDecimal getCurRemainingRisk() {
		return curRemainingRisk;
	}

	public void setCurRemainingRisk(BigDecimal curRemainingRisk) {
		this.curRemainingRisk = curRemainingRisk;
	}

	public BigDecimal getCurRemainingInterestAmt() {
		return curRemainingInterestAmt;
	}

	public void setCurRemainingInterestAmt(BigDecimal curRemainingInterestAmt) {
		this.curRemainingInterestAmt = curRemainingInterestAmt;
	}

	public BigDecimal getCurRemainingPrincipal() {
		return curRemainingPrincipal;
	}

	public void setCurRemainingPrincipal(BigDecimal curRemainingPrincipal) {
		this.curRemainingPrincipal = curRemainingPrincipal;
	}

	public Date getPenaltyDate() {
		return penaltyDate;
	}

	public void setPenaltyDate(Date penaltyDate) {
		this.penaltyDate = penaltyDate;
	}

	public Integer getNotStatus() {
		return notStatus;
	}

	public void setNotStatus(Integer notStatus) {
		this.notStatus = notStatus;
	}

	public List<Integer> getNotStatusList() {
		return notStatusList;
	}

	public void setNotStatusList(List<Integer> notStatusList) {
		this.notStatusList = notStatusList;
	}

	public BigDecimal getRefund() {
		return refund;
	}

	public void setRefund(BigDecimal refund) {
		this.refund = refund;
	}

}
