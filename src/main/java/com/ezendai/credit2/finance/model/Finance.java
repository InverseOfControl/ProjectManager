package com.ezendai.credit2.finance.model;

import java.math.BigDecimal;
import java.util.Date;

import com.ezendai.credit2.framework.model.BaseModel;

public class Finance extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4188732675021019192L;
	
	/**
	 * 费用合计
	 */
	private BigDecimal costs;
	
	private String salesDeptName;
	
	private String productName;
	
	private String loanTypeName;
	
	private String crmName;
	
	private String personName;
	
	private String idNum;
	
	private String bankName;
	
	private String branchName;
	
	private String accountName;
	
	private BigDecimal pactMoney;
	
	private BigDecimal auditMoney;
	
	private BigDecimal grantMoney;

	private Integer auditTime;
	
	private Integer returnDate;
	
	private Date startRepayDate;
	
	private Date endRepayDate;
	
	private BigDecimal risk;
	
	private BigDecimal consult;
	
	private BigDecimal assessment;
	
	private BigDecimal bManage;
	
	private BigDecimal cManage;
	
	private Date signDate;
	
	private String contractSrc;

	public BigDecimal getCosts() {
		return costs;
	}

	public void setCosts(BigDecimal costs) {
		this.costs = costs;
	}

	public String getSalesDeptName() {
		return salesDeptName;
	}

	public void setSalesDeptName(String salesDeptName) {
		this.salesDeptName = salesDeptName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getLoanTypeName() {
		return loanTypeName;
	}

	public void setLoanTypeName(String loanTypeName) {
		this.loanTypeName = loanTypeName;
	}

	public String getCrmName() {
		return crmName;
	}

	public void setCrmName(String crmName) {
		this.crmName = crmName;
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

	public String getBankName() {
		return bankName;
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

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public BigDecimal getPactMoney() {
		return pactMoney;
	}

	public void setPactMoney(BigDecimal pactMoney) {
		this.pactMoney = pactMoney;
	}

	public BigDecimal getAuditMoney() {
		return auditMoney;
	}

	public void setAuditMoney(BigDecimal auditMoney) {
		this.auditMoney = auditMoney;
	}

	public BigDecimal getGrantMoney() {
		return grantMoney;
	}

	public void setGrantMoney(BigDecimal grantMoney) {
		this.grantMoney = grantMoney;
	}

	public Integer getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Integer auditTime) {
		this.auditTime = auditTime;
	}

	public Integer getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Integer returnDate) {
		this.returnDate = returnDate;
	}

	public Date getStartRepayDate() {
		return startRepayDate;
	}

	public void setStartRepayDate(Date startRepayDate) {
		this.startRepayDate = startRepayDate;
	}

	public Date getEndRepayDate() {
		return endRepayDate;
	}

	public void setEndRepayDate(Date endRepayDate) {
		this.endRepayDate = endRepayDate;
	}

	public BigDecimal getRisk() {
		return risk;
	}

	public void setRisk(BigDecimal risk) {
		this.risk = risk;
	}

	public BigDecimal getConsult() {
		return consult;
	}

	public void setConsult(BigDecimal consult) {
		this.consult = consult;
	}

	public BigDecimal getAssessment() {
		return assessment;
	}

	public void setAssessment(BigDecimal assessment) {
		this.assessment = assessment;
	}

	public BigDecimal getbManage() {
		return bManage;
	}

	public void setbManage(BigDecimal bManage) {
		this.bManage = bManage;
	}

	public BigDecimal getcManage() {
		return cManage;
	}

	public void setcManage(BigDecimal cManage) {
		this.cManage = cManage;
	}

	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	public String getContractSrc() {
		return contractSrc;
	}

	public void setContractSrc(String contractSrc) {
		this.contractSrc = contractSrc;
	}
	
}
