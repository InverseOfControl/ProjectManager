/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.master.model;

 

import java.math.BigDecimal;

import com.ezendai.credit2.framework.model.BaseModel;

/**
 * <pre>
 * 综合查询
 * </pre>
 *
 * @author wyj
 * @version  
 */
public class ComprehensiveSearch extends BaseModel{
	private static final long serialVersionUID = -5493788680377090790L;
	
	private Long loanId;
	private Long extenId;
	
	private Long personId;
	private String personName;
	private Long productId;
	private Integer productType;
	private String productName;
	private String crmName;
	private Long crmId;
	private Long serviceId;
	private String serviceName;
	private String idNum;
	private String professionType;
	private String purpose;
	private BigDecimal auditMoney;
	private BigDecimal pactMoney;
	private Integer time; 
	private Integer status; 
	private String statusStr; 
	private Integer extensionTime; 
	private String bankName; 
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	private String account; 
	public Long getExtenId() {
		return extenId;
	}
	public void setExtenId(Long extenId) {
		this.extenId = extenId;
	}
	public Integer getExtensionTime() {
		return extensionTime;
	}
	public void setExtensionTime(Integer extensionTime) {
		this.extensionTime = extensionTime;
	}
	public Long getLoanId() {
		return loanId;
	}
	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}
	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Integer getProductType() {
		return productType;
	}
	public void setProductType(Integer productType) {
		this.productType = productType;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getCrmName() {
		return crmName;
	}
	public void setCrmName(String crmName) {
		this.crmName = crmName;
	}
	public Long getCrmId() {
		return crmId;
	}
	public void setCrmId(Long crmId) {
		this.crmId = crmId;
	}
	public Long getServiceId() {
		return serviceId;
	}
	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getIdNum() {
		return idNum;
	}
	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}
	public String getProfessionType() {
		return professionType;
	}
	public void setProfessionType(String professionType) {
		this.professionType = professionType;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public BigDecimal getAuditMoney() {
		return auditMoney;
	}
	public void setAuditMoney(BigDecimal auditMoney) {
		this.auditMoney = auditMoney;
	}
	public BigDecimal getPactMoney() {
		return pactMoney;
	}
	public void setPactMoney(BigDecimal pactMoney) {
		this.pactMoney = pactMoney;
	}
	public Integer getTime() {
		return time;
	}
	public void setTime(Integer time) {
		this.time = time;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getStatusStr() {
		return statusStr;
	}
	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}
	
	 
	
}
