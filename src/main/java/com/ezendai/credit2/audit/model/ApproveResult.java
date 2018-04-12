package com.ezendai.credit2.audit.model;

import com.ezendai.credit2.framework.model.BaseModel;

public class ApproveResult extends BaseModel {
	private static final long serialVersionUID = -1073312209072031145L;

	/**贷款ID*/
	private Long loanId;
	
	/**状态*/
	private String status;
	
	/**原因*/
	private String reason;
	
	/**拒绝原因1*/
	private String reason1;
	
	/**拒绝原因2*/
	private String reason2;
	
	/**补充证件1*/
	private String certificates1;
	
	/**补充证件2*/
	private String certificates2;
	
	/**补充证件3*/
	private String certificates3;
	/**申请金额*/
	private Long requestMoney;
	
	/**申请期数*/
	private Integer term;
	
	/**签约事项*/
	private String contractMatters;
	
	/**状态*/
	private Integer state;

	/** 网商贷审批会员类型*/
	private Integer auditMemberType;

	public Integer getAuditMemberType() {
		return auditMemberType;
	}

	public void setAuditMemberType(Integer auditMemberType) {
		this.auditMemberType = auditMemberType;
	}


	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason == null ? null : reason.trim();
	}

	public String getReason1() {
		return reason1;
	}

	public void setReason1(String reason1) {
		this.reason1 = reason1 == null ? null : reason1.trim();
	}

	public String getReason2() {
		return reason2;
	}

	public void setReason2(String reason2) {
		this.reason2 = reason2 == null ? null : reason2.trim();
	}

	public String getCertificates1() {
		return certificates1;
	}

	public void setCertificates1(String certificates1) {
		this.certificates1 = certificates1 == null ? null : certificates1.trim();
	}

	public String getCertificates2() {
		return certificates2;
	}

	public void setCertificates2(String certificates2) {
		this.certificates2 = certificates2 == null ? null : certificates2.trim();
	}

	public String getCertificates3() {
		return certificates3;
	}

	public void setCertificates3(String certificates3) {
		this.certificates3 = certificates3 == null ? null : certificates3.trim();
	}

	public Long getRequestMoney() {
		return requestMoney;
	}

	public void setRequestMoney(Long requestMoney) {
		this.requestMoney = requestMoney;
	}

	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	public String getContractMatters() {
		return contractMatters;
	}

	public void setContractMatters(String contractMatters) {
		this.contractMatters = contractMatters;
	}
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}


}