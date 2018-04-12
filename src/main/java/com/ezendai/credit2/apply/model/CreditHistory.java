package com.ezendai.credit2.apply.model;

import java.math.BigDecimal;
import java.util.Date;

import com.ezendai.credit2.framework.model.BaseModel;

public class CreditHistory extends BaseModel{

	private static final long serialVersionUID = 1556622345539496283L;

	/** 借款人ID */
	private Long personId;

	/** 是否有信用卡 */
	private Long hasCreditCard;

	/** 信用卡总数 */
	private Integer cardNum;

	/** 总额度 */
	private BigDecimal totalAmount;

	/** 已透支 */
	private BigDecimal overdrawAmount;

	/**借款id*/
	private Long loanId;
	
	/** 信贷渠道*/
	private String historyLoanChannel;
	/**信贷金额*/
	private BigDecimal historyAmount;
	/**放款日期*/
	private Date historyGrantDate;
	/**以往月还款额*/
	private BigDecimal historyMonPay;
	/**以往逾期信息*/
	private String historyOverdue;
	
	private String hasCreditCardText;
	
	private Loan loan;
	
	
	
	
	public String getHasCreditCardText() {
		return hasCreditCardText;
	}

	public void setHasCreditCardText(String hasCreditCardText) {
		this.hasCreditCardText = hasCreditCardText;
	}

	public String getHistoryLoanChannel() {
		return historyLoanChannel;
	}

	public void setHistoryLoanChannel(String historyLoanChannel) {
		this.historyLoanChannel = historyLoanChannel;
	}

	public BigDecimal getHistoryAmount() {
		return historyAmount;
	}

	public void setHistoryAmount(BigDecimal historyAmount) {
		this.historyAmount = historyAmount;
	}

	public Date getHistoryGrantDate() {
		return historyGrantDate;
	}

	public void setHistoryGrantDate(Date historyGrantDate) {
		this.historyGrantDate = historyGrantDate;
	}

	public BigDecimal getHistoryMonPay() {
		return historyMonPay;
	}

	public void setHistoryMonPay(BigDecimal historyMonPay) {
		this.historyMonPay = historyMonPay;
	}

	public String getHistoryOverdue() {
		return historyOverdue;
	}

	public void setHistoryOverdue(String historyOverdue) {
		this.historyOverdue = historyOverdue;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public Integer getCardNum() {
		return cardNum;
	}

	public void setCardNum(Integer cardNum) {
		this.cardNum = cardNum;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getOverdrawAmount() {
		return overdrawAmount;
	}

	public void setOverdrawAmount(BigDecimal overdrawAmount) {
		this.overdrawAmount = overdrawAmount;
	}

	public Long getHasCreditCard() {
		return hasCreditCard;
	}

	public void setHasCreditCard(Long hasCreditCard) {
		this.hasCreditCard = hasCreditCard;
	}

	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public Loan getLoan() {
		return loan;
	}

	public void setLoan(Loan loan) {
		this.loan = loan;
	}
	
}