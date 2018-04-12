/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.after.view;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <pre>
 * 罚息减免查询返回结果实体类
 * </pre>
 *
 * @author zhangshihai
 * @version $Id: PenaltyReduce.java, v 0.1 2014年12月12日 下午2:11:45 00221921 Exp $
 */
public class PenaltyReduce implements Serializable{

	private static final long serialVersionUID = 613333305691851086L;
	
	/** 借款ID */
	private Long loanId;

	/** 姓名(借款人) */
	private String name;

	/** 身份证号码 */
	private String idNum;

	/** 借款类型 */
	private Long productType;
	
	/** 借款类型 */
	private Long productId;
	
	/** 合同金额 */
	private BigDecimal pactMoney;

	/** 期限 */
	private Long time;
	
	/** 逾期应还 */
	private BigDecimal overdueAmount;
	
	/** 逾期应还总额 */
	private BigDecimal overdueAllAmount;
	
	/** 逾期违约金 */
	private BigDecimal fine;
	
	/** 逾期本金 */
	private BigDecimal principal;
	
	/** 逾期利息 */
	private BigDecimal interest;
	
	/** 逾期管理费 */
	private BigDecimal managementFee;
	
	/** 期末预收 */
	private BigDecimal accAmount;

	/** 借款状态 */
	private Integer status;
	
	/** 减免金额 */
	private BigDecimal amount;

	/** 操作 */
	private String operations;
	
	/** 展期期次 */
	private Integer extensionTime;
	
	/** 车架号 */
	private String frameNumber;

	
	
	public String getFrameNumber() {
		return frameNumber;
	}

	public void setFrameNumber(String frameNumber) {
		this.frameNumber = frameNumber;
	}

	public Long getLoanId() {
		return loanId;
	}

	public Integer getExtensionTime() {
		return extensionTime;
	}

	public void setExtensionTime(Integer extensionTime) {
		this.extensionTime = extensionTime;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
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

	public BigDecimal getOverdueAllAmount() {
		return overdueAllAmount;
	}

	public void setOverdueAllAmount(BigDecimal overdueAllAmount) {
		this.overdueAllAmount = overdueAllAmount;
	}

	public Long getProductType() {
		return productType;
	}

	public void setProductType(Long productType) {
		this.productType = productType;
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

	public BigDecimal getPrincipal() {
		return principal;
	}

	public void setPrincipal(BigDecimal principal) {
		this.principal = principal;
	}

	public BigDecimal getInterest() {
		return interest;
	}

	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}

	public BigDecimal getManagementFee() {
		return managementFee;
	}

	public void setManagementFee(BigDecimal managementFee) {
		this.managementFee = managementFee;
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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getOperations() {
		return operations;
	}

	public void setOperations(String operations) {
		this.operations = operations;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	
}
