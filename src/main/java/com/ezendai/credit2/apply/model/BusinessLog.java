package com.ezendai.credit2.apply.model;

import java.util.Date;

import com.ezendai.credit2.framework.model.BaseModel;

public class BusinessLog extends BaseModel {
	private static final long serialVersionUID = -8331764293963076660L;

	private Long loanId;

	private String message;

	private Date createDate;

	private String operator;

	private Integer flowStatus;

	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message == null ? null : message.trim();
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator == null ? null : operator.trim();
	}

	public Integer getFlowStatus() {
		return flowStatus;
	}

	public void setFlowStatus(Integer flowStatus) {
		this.flowStatus = flowStatus;
	}


}