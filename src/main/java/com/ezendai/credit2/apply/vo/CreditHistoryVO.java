package com.ezendai.credit2.apply.vo;

import java.math.BigDecimal;

import com.ezendai.credit2.framework.vo.BaseVO;

public class CreditHistoryVO extends BaseVO {

	private static final long serialVersionUID = -2408423781147152989L;
	
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
	
	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public Long getHasCreditCard() {
		return hasCreditCard;
	}

	public void setHasCreditCard(Long hasCreditCard) {
		this.hasCreditCard = hasCreditCard;
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

	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}
	
}
