package com.ezendai.credit2.apply.model;

import com.ezendai.credit2.framework.model.BaseModel;

public class GuaranteeBankAccount 
						extends BaseModel {
	
	private static final long serialVersionUID = -8382543644215592118L;

	private Long bankId;

	private Long guarantee;

	private String branchName;

	private String bankName;

	private String account;

	private Integer status;

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public Long getGuarantee() {
		return guarantee;
	}

	public void setGuarantee(Long guarantee) {
		this.guarantee = guarantee;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account == null ? null : account.trim();
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

}