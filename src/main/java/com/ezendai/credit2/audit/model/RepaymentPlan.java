package com.ezendai.credit2.audit.model;

import java.math.BigDecimal;
import java.util.Date;

import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.framework.model.BaseModel;

public class RepaymentPlan extends BaseModel {
	private static final long serialVersionUID = -6506690385944230100L;

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
	
	/**罚息起算日期*/
	private Date penaltyDate;
	
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

	/** 退费 */
	private BigDecimal refund;
	
	/** 还款机构银行ID */
	private Long grantAccountId;
	
	/** 产品ID */
	private Long productId;
	
	/** 还款账户名 */
	private String name;
	
	/** 还款机构账户名 */
	private String accountName;
	
	/** 机构还款时间 */
	private Integer orgRepayTerm;
	
	private Loan loan;
	
	public Integer getOrgRepayTerm() {
		return orgRepayTerm;
	}

	public void setOrgRepayTerm(Integer orgRepayTerm) {
		this.orgRepayTerm = orgRepayTerm;
	}

	public Long getGrantAccountId() {
		return grantAccountId;
	}

	public void setGrantAccountId(Long grantAccountId) {
		this.grantAccountId = grantAccountId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

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

	public BigDecimal getRisk() {
		return risk;
	}

	public void setRisk(BigDecimal risk) {
		this.risk = risk;
	}

	public BigDecimal getRemainingPrincipal() {
		return remainingPrincipal;
	}

	public void setRemainingPrincipal(BigDecimal remainingPrincipal) {
		this.remainingPrincipal = remainingPrincipal;
	}

	public BigDecimal getOneTimeRepaymentAmount() {
		return oneTimeRepaymentAmount;
	}

	public void setOneTimeRepaymentAmount(BigDecimal oneTimeRepaymentAmount) {
		this.oneTimeRepaymentAmount = oneTimeRepaymentAmount;
	}

	public BigDecimal getAverageCapital() {
		return averageCapital;
	}

	public void setAverageCapital(BigDecimal averageCapital) {
		this.averageCapital = averageCapital;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public BigDecimal getPenalty() {
		return penalty;
	}

	public void setPenalty(BigDecimal penalty) {
		this.penalty = penalty;
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
	public Date getPenaltyDate() {
		return penaltyDate;
	}

	public void setPenaltyDate(Date penaltyDate) {
		this.penaltyDate = penaltyDate;
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

	public BigDecimal getRefund() {
		return refund;
	}

	public void setRefund(BigDecimal refund) {
		this.refund = refund;
	}

	public Loan getLoan() {
		return loan;
	}

	public void setLoan(Loan loan) {
		this.loan = loan;
	}

}