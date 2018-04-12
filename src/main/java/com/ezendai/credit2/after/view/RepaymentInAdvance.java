/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.after.view;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <pre>
 * 提前还款查询返回结果实体类
 * </pre>
 *
 * @author zhangshihai
 * @version $Id: RepaymentInAdvance.java, v 0.1 2014年12月4日 下午1:51:27 00221921 Exp $
 */
public class RepaymentInAdvance implements Serializable {

	private static final long serialVersionUID = 2390604602407318660L;

	/** 借款ID */
	private Long loanId;

	/** 姓名(借款人) */
	private String name;

	/** 身份证号码 */
	private String idNum;

	/** 借款类型 */
	private Long productType;
	/** 产品类型 */
	private Long productId;


	/** 借款子类型 */
	private Integer productSubType;

	/** 合同金额 */
	private BigDecimal pactMoney;

	/** 期限 */
	private Long time;

	/** 当期应还 */
	private BigDecimal currRepayAmount;

	/** 逾期应还 */
	private BigDecimal overdueAmount;

	/** 逾期应还总额 */
	private BigDecimal overdueAllAmount;

	/** 逾期违约金 */
	private BigDecimal fine;

	/** 一次性还款金额 */
	private BigDecimal oneTimeRepayment;

	/** 期末预收 */
	private BigDecimal accAmount;

	/** 借款状态 */
	private Integer status;

	/** 操作 */
	private String operations;
	
	/** 展期期次 */
	private Integer extensionTime;

	
	
	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public Integer getExtensionTime() {
		return extensionTime;
	}

	public void setExtensionTime(Integer extensionTime) {
		this.extensionTime = extensionTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdNum() {
		return idNum;
	}

	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}

	public Long getProductType() {
		return productType;
	}

	public void setProductType(Long productType) {
		this.productType = productType;
	}

	public Integer getProductSubType() {
		return productSubType;
	}

	public void setProductSubType(Integer productSubType) {
		this.productSubType = productSubType;
	}

	public BigDecimal getPactMoney() {
		return pactMoney;
	}

	public void setPactMoney(BigDecimal pactMoney) {
		this.pactMoney = pactMoney;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public BigDecimal getOneTimeRepayment() {
		return oneTimeRepayment;
	}

	public void setOneTimeRepayment(BigDecimal oneTimeRepayment) {
		this.oneTimeRepayment = oneTimeRepayment;
	}

	public BigDecimal getCurrRepayAmount() {
		return currRepayAmount;
	}

	public void setCurrRepayAmount(BigDecimal currRepayAmount) {
		this.currRepayAmount = currRepayAmount;
	}

	public BigDecimal getOverdueAmount() {
		return overdueAmount;
	}

	public void setOverdueAmount(BigDecimal overdueAmount) {
		this.overdueAmount = overdueAmount;
	}

	public BigDecimal getFine() {
		return fine;
	}

	public void setFine(BigDecimal fine) {
		this.fine = fine;
	}

	public BigDecimal getAccAmount() {
		return accAmount;
	}

	public void setAccAmount(BigDecimal accAmount) {
		this.accAmount = accAmount;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getOperations() {
		return operations;
	}

	public void setOperations(String operations) {
		this.operations = operations;
	}

	public BigDecimal getOverdueAllAmount() {
		return overdueAllAmount;
	}

	public void setOverdueAllAmount(BigDecimal overdueAllAmount) {
		this.overdueAllAmount = overdueAllAmount;
	}

}
