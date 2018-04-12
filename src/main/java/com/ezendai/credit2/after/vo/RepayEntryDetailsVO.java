package com.ezendai.credit2.after.vo;

import java.math.BigDecimal;

import com.ezendai.credit2.framework.vo.BaseVO;

/**
 * <pre>
 * 用于显示还款录入点击还款后的还款信息界面
 * </pre>
 *
 * @author chenqi
 * @version $Id: RepayEntryDetailsVO.java, v 0.1 2014年12月16日 上午11:09:37 chenqi Exp $
 */
public class RepayEntryDetailsVO extends BaseVO {
	private static final long serialVersionUID = 8601146230500300050L;

	private Long loanId;

	private Long productId;
	/** 客户姓名*/
	private String personName;
	/** 身份证号 */
	private String personIdnum;
	/** 手机 */
	private String personMobilePhone;
	/**逾期起始日期*/
	private String overdueStartDate;
	/**逾期总数*/
	private int overdueTerm;
	/** 逾期应还金额 */
	private BigDecimal overdueAmount;
	/** 逾期应还金额 */
	private BigDecimal overdueAllAmount;
	/**罚息起算日期*/
	private String fineDate;
	/**罚息天数 */
	private int fineDay;
	/** 罚息,逾期违约金*/
	private BigDecimal fine;
	/**当期还款日*/
	private String curRepayDate;
	/**当期期数 */
	private int currTerm;
	/**当期应还总额标题*/
	private String currAmountLabel;
	
	/**逾期本金*/
	private BigDecimal overduePrincipal;
	/**当期应还总额*/
	private BigDecimal currAmount;
	
	/**  一次性还款金额*/
	private BigDecimal onetimeRepaymentAmount;
	/** 挂账金额*/
	private BigDecimal accAmount;
	/** 减免金额*/
	private BigDecimal reliefOfFine;
	/** 应还总额（包含当期）*/
	private BigDecimal repayAllAmount;
	/** 应还总额（不包含当期）*/
	private BigDecimal repayAmount;
	/** 当前日期 */
	private String nowDate;
	/**备注*/
	private int payType;
	/**备注*/
	private String remark;
	/**还款金额*/
	private BigDecimal tradeAmount;
	/**逾期本息和*/
	private BigDecimal overduePrincipalInterestSum;
	/**剩余本息和*/
	private BigDecimal curRemainingAmount;
	/**逾期利息*/
	private BigDecimal interest;
	/**当期利息*/
	private BigDecimal curRemainingInterestAmt;
	/**当期本金*/
	private BigDecimal curRemainingPrincipal;
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

	
	 
	public BigDecimal getInterest() {
		return interest;
	}

	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}

	public BigDecimal getCurRemainingAmount() {
		return curRemainingAmount;
	}

	public void setCurRemainingAmount(BigDecimal curRemainingAmount) {
		this.curRemainingAmount = curRemainingAmount;
	}

	public BigDecimal getOverduePrincipal() {
		return overduePrincipal;
	}

	public void setOverduePrincipal(BigDecimal overduePrincipal) {
		this.overduePrincipal = overduePrincipal;
	}


	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getPersonIdnum() {
		return personIdnum;
	}

	public void setPersonIdnum(String personIdnum) {
		this.personIdnum = personIdnum;
	}

	public String getPersonMobilePhone() {
		return personMobilePhone;
	}

	public void setPersonMobilePhone(String personMobilePhone) {
		this.personMobilePhone = personMobilePhone;
	}

	public String getOverdueStartDate() {
		return overdueStartDate;
	}

	public void setOverdueStartDate(String overdueStartDate) {
		this.overdueStartDate = overdueStartDate;
	}

	public int getOverdueTerm() {
		return overdueTerm;
	}

	public void setOverdueTerm(int overdueTerm) {
		this.overdueTerm = overdueTerm;
	}

	public BigDecimal getOverdueAmount() {
		return overdueAmount;
	}

	public void setOverdueAmount(BigDecimal overdueAmount) {
		this.overdueAmount = overdueAmount;
	}

	public BigDecimal getOverdueAllAmount() {
		return overdueAllAmount;
	}

	public void setOverdueAllAmount(BigDecimal overdueAllAmount) {
		this.overdueAllAmount = overdueAllAmount;
	}

	public String getFineDate() {
		return fineDate;
	}

	public void setFineDate(String fineDate) {
		this.fineDate = fineDate;
	}

	public int getFineDay() {
		return fineDay;
	}

	public void setFineDay(int fineDay) {
		this.fineDay = fineDay;
	}

	public BigDecimal getFine() {
		return fine;
	}

	public void setFine(BigDecimal fine) {
		this.fine = fine;
	}

	public String getCurRepayDate() {
		return curRepayDate;
	}

	public void setCurRepayDate(String curRepayDate) {
		this.curRepayDate = curRepayDate;
	}

	public int getCurrTerm() {
		return currTerm;
	}

	public void setCurrTerm(int currTerm) {
		this.currTerm = currTerm;
	}
	public String getCurrAmountLabel() {
		return currAmountLabel;
	}

	public void setCurrAmountLabel(String currAmountLabel) {
		this.currAmountLabel = currAmountLabel;
	}
	public BigDecimal getCurrAmount() {
		return currAmount;
	}

	public void setCurrAmount(BigDecimal currAmount) {
		this.currAmount = currAmount;
	}

	public BigDecimal getOnetimeRepaymentAmount() {
		return onetimeRepaymentAmount;
	}

	public void setOnetimeRepaymentAmount(BigDecimal onetimeRepaymentAmount) {
		this.onetimeRepaymentAmount = onetimeRepaymentAmount;
	}

	public BigDecimal getAccAmount() {
		return accAmount;
	}

	public void setAccAmount(BigDecimal accAmount) {
		this.accAmount = accAmount;
	}

	public BigDecimal getReliefOfFine() {
		return reliefOfFine;
	}

	public void setReliefOfFine(BigDecimal reliefOfFine) {
		this.reliefOfFine = reliefOfFine;
	}

	public BigDecimal getRepayAllAmount() {
		return repayAllAmount;
	}

	public void setRepayAllAmount(BigDecimal repayAllAmount) {
		this.repayAllAmount = repayAllAmount;
	}

	public BigDecimal getRepayAmount() {
		return repayAmount;
	}

	public void setRepayAmount(BigDecimal repayAmount) {
		this.repayAmount = repayAmount;
	}

	public String getNowDate() {
		return nowDate;
	}

	public void setNowDate(String nowDate) {
		this.nowDate = nowDate;
	}

	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getTradeAmount() {
		return tradeAmount;
	}

	public void setTradeAmount(BigDecimal tradeAmount) {
		this.tradeAmount = tradeAmount;
	}
	public BigDecimal getOverduePrincipalInterestSum() {
		return overduePrincipalInterestSum;
	}

	public void setOverduePrincipalInterestSum(
			BigDecimal overduePrincipalInterestSum) {
		this.overduePrincipalInterestSum = overduePrincipalInterestSum;
	}

}
