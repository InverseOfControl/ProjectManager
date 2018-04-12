/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.master.model;

 

import java.math.BigDecimal;
import java.util.Date;

import com.ezendai.credit2.framework.model.BaseModel;

/**
 * <pre>
 * 综合查询--借款详细信息
 * </pre>
 *
 * @author wyj
 * @version  
 */
public class LoanDetails extends BaseModel{
	private static final long serialVersionUID = -5493788680377090790L;
	private String prudentName;
	private String personName;
	private String sex;
	private String	idNum ;
	private Date	requestDate ;
	private BigDecimal	requestMoney ;
	private Long	requestTime ;
	private BigDecimal	auditMoney ;
	private Long	time ;
	private String	statusStr ;
	private Double	maxRepayAmount ;
	private String	salesDept ;
	private String	repayBank ;
	private String	gantBank ;
	private String	purpose ;
	private String	crmName ;
	private String	serviceName ;
	private String	contractSource ;
	public String getGantBank() {
		return gantBank;
	}
	public void setGantBank(String gantBank) {
		this.gantBank = gantBank;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getPrudentName() {
		return prudentName;
	}
	public void setPrudentName(String prudentName) {
		this.prudentName = prudentName;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	 
	public String getIdNum() {
		return idNum;
	}
	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}
	public Date getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
	public BigDecimal getRequestMoney() {
		return requestMoney;
	}
	public void setRequestMoney(BigDecimal requestMoney) {
		this.requestMoney = requestMoney;
	}
	public Long getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(Long requestTime) {
		this.requestTime = requestTime;
	}
	public BigDecimal getAuditMoney() {
		return auditMoney;
	}
	public void setAuditMoney(BigDecimal auditMoney) {
		this.auditMoney = auditMoney;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public String getStatusStr() {
		return statusStr;
	}
	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}
	public Double getMaxRepayAmount() {
		return maxRepayAmount;
	}
	public void setMaxRepayAmount(Double maxRepayAmount) {
		this.maxRepayAmount = maxRepayAmount;
	}
	public String getSalesDept() {
		return salesDept;
	}
	public void setSalesDept(String salesDept) {
		this.salesDept = salesDept;
	}
	public String getRepayBank() {
		return repayBank;
	}
	public void setRepayBank(String repayBank) {
		this.repayBank = repayBank;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getCrmName() {
		return crmName;
	}
	public void setCrmName(String crmName) {
		this.crmName = crmName;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getContractSource() {
		return contractSource;
	}
	public void setContractSource(String contractSource) {
		this.contractSource = contractSource;
	}
}
