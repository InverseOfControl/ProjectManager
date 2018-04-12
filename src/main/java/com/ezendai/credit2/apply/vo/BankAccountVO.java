/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.apply.vo;

import java.util.ArrayList;
import java.util.List;

import com.ezendai.credit2.apply.model.Bank;
import com.ezendai.credit2.framework.vo.BaseVO;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author xiaoxiong
 * @version $Id: BankAccountVO.java, v 0.1 2014年6月24日 上午8:54:30 xiaoxiong Exp $
 */
public class BankAccountVO extends BaseVO {
	private static final long serialVersionUID = -5816589266857778139L;
	/** 银行卡号 */
	private String account;
	/** 银行名称 */
	private String bankName;
	/** 分行名称 */
	private String branchName;
	/** 状态 */
	private Integer status;
	/** 银行 */
	private Bank bank;
	
	/**户名 */
	private String accountName;
	private Integer           cardType   ;  
	
	private String bankId;
	
	private String personId;
	
	private String loanId;

	private String isAllUpdate;
	
	private Integer accountAuthType;
	
	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getLoanId() {
		return loanId;
	}

	public String getIsAllUpdate() {
		return isAllUpdate;
	}

	public void setIsAllUpdate(String isAllUpdate) {
		this.isAllUpdate = isAllUpdate;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	private List<Long> idList = new ArrayList<Long>();

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getBankName() {
		return bankName;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public List<Long> getIdList() {
		return idList;
	}

	public void setIdList(List<Long> idList) {
		this.idList = idList;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public Integer getCardType() {
		return cardType;
	}

	public void setCardType(Integer cardType) {
		this.cardType = cardType;
	}

	public Integer getAccountAuthType() {
		return accountAuthType;
	}

	public void setAccountAuthType(Integer accountAuthType) {
		this.accountAuthType = accountAuthType;
	}
	
}
