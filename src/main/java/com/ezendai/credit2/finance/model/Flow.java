/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.finance.model;

import java.math.BigDecimal;
import java.util.Date;

import com.ezendai.credit2.framework.model.BaseModel;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author liyuepeng
 * @version $Id: Flow.java, v 0.1 2014-9-11 下午01:30:55 liyuepeng Exp $
 */
public class Flow extends BaseModel {

	/**  */
	private static final long serialVersionUID = 7964312227108056054L;
	
	/** 账号(借款ID) */
	private Long accountId;
	
	/** 借款或贷款类别 */
	private String dOrC;
	
	/** 科目号 */
	private String accountTitle;
	
	/** 交易时间 */
	private Date tradeTime;
	
	/** 交易码 */
	private String tradeCode;
	
	/** 交易金额 */
	private BigDecimal tradeAmount;
	
	/** 余额 */
	private BigDecimal balance;
	
	/** 交易类型 */
	private Integer tradeType;
	
	/** 交易种类 */
	private Integer tradeKind;
	
	/** 交易流水号 */
	private String tradeNo;
	
	/** 冲正流水号 */
	private String reversedNo;
	
	/** 柜员号 */
	private Long teller;
	
	/** 授权柜员号 */
	private Long authorizedTeller;
	
	/** 营业网点ID */
	private Long salesdepartmentId;
	
	/** 对方账号 */
	private Long oppAccount;
	
	/** 对方科目号 */
	private String oppAccountTitle;
	
	/** 对方借贷类别 */
	private String oppDOrC;
	
	/** 还款期数 */
	private Integer term;
	
	/** 备注 */
	private String remark;

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	
	public String getdOrC() {
		return dOrC;
	}

	public void setdOrC(String dOrC) {
		this.dOrC = dOrC;
	}

	public String getAccountTitle() {
		return accountTitle;
	}

	public void setAccountTitle(String accountTitle) {
		this.accountTitle = accountTitle;
	}

	public Date getTradeTime() {
		return tradeTime;
	}

	public void setTradeTime(Date tradeTime) {
		this.tradeTime = tradeTime;
	}

	public String getTradeCode() {
		return tradeCode;
	}

	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}

	public BigDecimal getTradeAmount() {
		return tradeAmount;
	}

	public void setTradeAmount(BigDecimal tradeAmount) {
		this.tradeAmount = tradeAmount;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Integer getTradeType() {
		return tradeType;
	}

	public void setTradeType(Integer tradeType) {
		this.tradeType = tradeType;
	}

	public Integer getTradeKind() {
		return tradeKind;
	}

	public void setTradeKind(Integer tradeKind) {
		this.tradeKind = tradeKind;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getReversedNo() {
		return reversedNo;
	}

	public void setReversedNo(String reversedNo) {
		this.reversedNo = reversedNo;
	}

	public Long getTeller() {
		return teller;
	}

	public void setTeller(Long teller) {
		this.teller = teller;
	}

	public Long getAuthorizedTeller() {
		return authorizedTeller;
	}

	public void setAuthorizedTeller(Long authorizedTeller) {
		this.authorizedTeller = authorizedTeller;
	}

	public Long getSalesdepartmentId() {
		return salesdepartmentId;
	}

	public void setSalesdepartmentId(Long salesdepartmentId) {
		this.salesdepartmentId = salesdepartmentId;
	}

	public Long getOppAccount() {
		return oppAccount;
	}

	public void setOppAccount(Long oppAccount) {
		this.oppAccount = oppAccount;
	}

	public String getOppAccountTitle() {
		return oppAccountTitle;
	}

	public void setOppAccountTitle(String oppAccountTitle) {
		this.oppAccountTitle = oppAccountTitle;
	}

	public String getOppDOrC() {
		return oppDOrC;
	}

	public void setOppDOrC(String oppDOrC) {
		this.oppDOrC = oppDOrC;
	}

	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
