package com.ezendai.credit2.apply.vo;

import java.util.Date;

import com.ezendai.credit2.framework.vo.BaseVO;

/**
 * 业务日志VO模型
 * 
 * @author larry
 * @version 1.0, 2014-06-23
 * @since 1.0
 */
public class BusinessLogVO extends BaseVO {

	private static final long serialVersionUID = 1L;
	
	/** 债权ID */
	private Long loanId;

	/** 日志消息 */
	private String message;

	/** 日志创建时间 */
	private Date createDate;

	/** 操作人员 */
	private String operator;

	/** 状态 */
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
		this.message = message;
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
		this.operator = operator;
	}

	public Integer getFlowStatus() {
		return flowStatus;
	}

	public void setFlowStatus(Integer flowStatus) {
		this.flowStatus = flowStatus;
	}

	
}
