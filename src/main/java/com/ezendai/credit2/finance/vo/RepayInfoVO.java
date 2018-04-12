/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.finance.vo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ezendai.credit2.framework.vo.BaseVO;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author liyuepeng
 * @version $Id: RepayInfoVO.java, v 0.1 2014-9-10 上午11:19:30 liyuepeng Exp $
 */
public class RepayInfoVO extends BaseVO{

	/**  */
	private static final long serialVersionUID = -4098828470874194087L;
	
	/** 账号(借款ID) */
	private Long accountId;
	
	/** 交易时间 */
	private Date tradeTime;
	
	/** 交易码 */
	private String tradeCode;
	
	/** 交易金额 */
	private BigDecimal tradeAmount;
	
	/** 付款类型 */
	private Integer payType;
	
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
	
	/** 期数 */
	private Long term;
	
	/** 回盘ID */
	private Long offerId;
	
	/** 营业网点ID */
	private Long salesdepartmentId;
	
	/** 备注 */
	private String remark;
	
	/** 交易时间开始 */
	private Date tradeTimeStart;
	
	/** 交易时间结束 */
	private Date tradeTimeEnd;
	
	/** 按多组付款类型查询 **/
	private List<Integer> payTypes = new ArrayList<Integer>();
	
	/** 账户余额 */
	private BigDecimal cash;
	
	

	public BigDecimal getCash() {
		return cash;
	}

	public void setCash(BigDecimal cash) {
		this.cash = cash;
	}

	public List<Integer> getPayTypes() {
		return payTypes;
	}

	public void setPayTypes(List<Integer> payTypes) {
		this.payTypes = payTypes;
	}

	/** 交易时间 trunc(sysdate)方式判断 **/
	private String[] tradeTimes;
	
	
	/** 按多组交易码查询 **/
	private List<String> tradeCodes = new ArrayList<String>();

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
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

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
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

	public Long getTerm() {
		return term;
	}

	public void setTerm(Long term) {
		this.term = term;
	}

	public Long getOfferId() {
		return offerId;
	}

	public void setOfferId(Long offerId) {
		this.offerId = offerId;
	}

	public Long getSalesdepartmentId() {
		return salesdepartmentId;
	}

	public void setSalesdepartmentId(Long salesdepartmentId) {
		this.salesdepartmentId = salesdepartmentId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getTradeTimeStart() {
		return tradeTimeStart;
	}

	public void setTradeTimeStart(Date tradeTimeStart) {
		this.tradeTimeStart = tradeTimeStart;
	}

	public Date getTradeTimeEnd() {
		return tradeTimeEnd;
	}

	public void setTradeTimeEnd(Date tradeTimeEnd) {
		this.tradeTimeEnd = tradeTimeEnd;
	}

	public List<String> getTradeCodes() {
		return tradeCodes;
	}

	public void setTradeCodes(List<String> tradeCodes) {
		this.tradeCodes = tradeCodes;
	}

	public String[] getTradeTimes() {
		return tradeTimes;
	}

	public void setTradeTimes(String[] tradeTimes) {
		this.tradeTimes = tradeTimes;
	}

	

}
