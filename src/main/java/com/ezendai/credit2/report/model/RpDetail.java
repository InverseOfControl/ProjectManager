package com.ezendai.credit2.report.model;

import java.math.BigDecimal;
import java.util.Date;

import com.ezendai.credit2.framework.model.BaseModel;

public class RpDetail  extends BaseModel {
	private static final long serialVersionUID = 2294189194709523247L;
	private Long id;//PK
	/**
	 * LOAN_ID
	 */
	
	private Long loanId;
	/**
	 * STATUS
	 */
	
	private Integer status;
	/**
	 * REPAY_AMOUNT
	 */
	
	private BigDecimal repayAmount;
	/**
	 * CUR_NUM
	 */
	
	private Integer curNum;
	/**
	 * PRINCIPAL_AMT
	 */
	
	private BigDecimal principalAmt;
	/**
	 * OUTSTANDING
	 */
	
	private BigDecimal outstanding;
	/**
	 * AVERAGE_CAPITAL
	 */
	
	private BigDecimal averageCapital;
	/**
	 * REMAINING_PRINCIPAL
	 */
	
	private BigDecimal remainingPrincipal;
	/**
	 * PENALTY
	 */
	
	private BigDecimal penalty;
	/**
	 * RISK
	 */
	
	private BigDecimal risk;
	/**
	 * ONE_TIME_REPAYMENT_AMOUNT
	 */
	
	private BigDecimal oneTimeRepaymentAmount;
	/**
	 * FACT_RETURNDATE
	 */
	private Date factReturndate;
	/**
	 * DEFICIT
	 */
	
	private BigDecimal deficit;
	/**
	 * MANAGE_PART0_FEE
	 */
	
	private BigDecimal managePart0Fee;
	/**
	 * MANAGE_PART1_FEE
	 */
	
	private BigDecimal managePart1Fee;
	/**
	 * REFER_RATE
	 */
	
	private BigDecimal referRate;
	/**
	 * EVAL_RATE
	 */
	
	private BigDecimal evalRate;
	/**
	 * INTEREST_AMT
	 */
	
	private BigDecimal interestAmt;
	/**
	 * REPAY_DAY
	 */
	private Date repayDay;
	/**
	 * VERSION
	 */
	
	private Long version;
	/*
	 * (non-Javadoc)
	 */
	public Long getId(){
		return id;
	}
	/*
	 * (non-Javadoc)
	 */
	public void setId(Long id){
		this.id=id;
	}

	/**
	 * return LOAN_ID
	 */
	public Long getLoanId() {
		return this.loanId;
	}
	public void setLoanId(Long value) {
		this.loanId = value;
	}

	/**
	 * return STATUS
	 */
	public Integer getStatus() {
		return this.status;
	}
	public void setStatus(Integer value) {
		this.status = value;
	}

	/**
	 * return CUR_NUM
	 */
	public Integer getCurNum() {
		return this.curNum;
	}
	public void setCurNum(Integer value) {
		this.curNum = value;
	}

	/**
	 * return FACT_RETURNDATE
	 */
	public Date getFactReturndate() {
		return this.factReturndate;
	}
	public void setFactReturndate(Date value) {
		this.factReturndate = value;
	}

	/**
	 * return REPAY_DAY
	 */
	public Date getRepayDay() {
		return this.repayDay;
	}
	

	
	public void setRepayDay(Date value) {
		this.repayDay = value;
	}

	/**
	 * return VERSION
	 */
	public Long getVersion() {
		return this.version;
	}
	public void setVersion(Long value) {
		this.version = value;
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
	public BigDecimal getRisk() {
		return risk;
	}
	public void setRisk(BigDecimal risk) {
		this.risk = risk;
	}
	public BigDecimal getOneTimeRepaymentAmount() {
		return oneTimeRepaymentAmount;
	}
	public void setOneTimeRepaymentAmount(BigDecimal oneTimeRepaymentAmount) {
		this.oneTimeRepaymentAmount = oneTimeRepaymentAmount;
	}
	public BigDecimal getDeficit() {
		return deficit;
	}
	public void setDeficit(BigDecimal deficit) {
		this.deficit = deficit;
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
	
}
