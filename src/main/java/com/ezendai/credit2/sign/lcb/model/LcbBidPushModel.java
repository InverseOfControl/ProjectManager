package com.ezendai.credit2.sign.lcb.model;

import java.math.BigDecimal;

public class LcbBidPushModel{

	private static final long serialVersionUID = -485773328890386623L;
	
	private String borrowNo; //借款编号
	private BigDecimal borrowAmount; //借款金额
	private String borrowTerm; //借款期限
	private String remitMoney; //划拨金额
	private String yearRate; //年化利率
	private String serviceFee; //服务费
	private String riskFund; //风险金
	private String consultFee; //咨询费
	private String auditFee; //审核费
	private String managementFee; //管理费1
	private String otherManagementFee; //管理费2
	private BigDecimal borrowRate; //借款费率
	private String repaymentType; //借款还款方式
	private String borrowPurpose; //借款用途
	private String productName; //借款产品名称
	private String borrowerName; //借款人姓名
	private String borrowerPhone; //借款人手机号
	private String idType; //借款人证件类型
	private String idNo; //借款人证件号
	private String sex; //借款人性别
	private String maritalStatus; //借款人婚姻状态
	private String birth; //借款人出生年份
	private String city; //借款人所在城市
	private String educationLevel; //借款人教育程度
	private String hasCar; //借款人是否有车
	private String hasHourse; //借款人是否有房
	private String trade; //借款人行业信息
	private String post; //借款人岗位信息
	private String companyNature; //借款人单位性质
	private String monthlyIncome; //借款人月收入信息
	private String creditNums; //借款人信用卡数
	private String loanNums; //借款人贷款笔数
	private String hasCarLoan; //借款人是否有车贷
	private String hasHourseLoan; //借款人是否有房贷
	private String attachmentUrl ; //附件下载地址
	
	public String getBorrowNo() {
		return borrowNo;
	}
	public void setBorrowNo(String borrowNo) {
		this.borrowNo = borrowNo;
	}
	public BigDecimal getBorrowAmount() {
		return borrowAmount;
	}
	
	public void setBorrowAmount(BigDecimal borrowAmount) {
		this.borrowAmount = borrowAmount;
	}
	public String getBorrowTerm() {
		return borrowTerm;
	}
	public void setBorrowTerm(String borrowTerm) {
		this.borrowTerm = borrowTerm;
	}
	public String getRemitMoney() {
		return remitMoney;
	}
	public void setRemitMoney(String remitMoney) {
		this.remitMoney = remitMoney;
	}
	public String getYearRate() {
		return yearRate;
	}
	public void setYearRate(String yearRate) {
		this.yearRate = yearRate;
	}
	public String getServiceFee() {
		return serviceFee;
	}
	public void setServiceFee(String serviceFee) {
		this.serviceFee = serviceFee;
	}
	public String getRiskFund() {
		return riskFund;
	}
	public void setRiskFund(String riskFund) {
		this.riskFund = riskFund;
	}
	public String getConsultFee() {
		return consultFee;
	}
	public void setConsultFee(String consultFee) {
		this.consultFee = consultFee;
	}
	public String getAuditFee() {
		return auditFee;
	}
	public void setAuditFee(String auditFee) {
		this.auditFee = auditFee;
	}
	public String getManagementFee() {
		return managementFee;
	}
	public void setManagementFee(String managementFee) {
		this.managementFee = managementFee;
	}
	public String getOtherManagementFee() {
		return otherManagementFee;
	}
	public void setOtherManagementFee(String otherManagementFee) {
		this.otherManagementFee = otherManagementFee;
	}
	public BigDecimal getBorrowRate() {
		return borrowRate;
	}
	public void setBorrowRate(BigDecimal borrowRate) {
		this.borrowRate = borrowRate;
	}
	public String getRepaymentType() {
		return repaymentType;
	}
	public void setRepaymentType(String repaymentType) {
		this.repaymentType = repaymentType;
	}
	public String getBorrowPurpose() {
		return borrowPurpose;
	}
	public void setBorrowPurpose(String borrowPurpose) {
		this.borrowPurpose = borrowPurpose;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getBorrowerName() {
		return borrowerName;
	}
	public void setBorrowerName(String borrowerName) {
		this.borrowerName = borrowerName;
	}
	public String getBorrowerPhone() {
		return borrowerPhone;
	}
	public void setBorrowerPhone(String borrowerPhone) {
		this.borrowerPhone = borrowerPhone;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getEducationLevel() {
		return educationLevel;
	}
	public void setEducationLevel(String educationLevel) {
		this.educationLevel = educationLevel;
	}
	public String getHasCar() {
		return hasCar;
	}
	public void setHasCar(String hasCar) {
		this.hasCar = hasCar;
	}
	public String getHasHourse() {
		return hasHourse;
	}
	public void setHasHourse(String hasHourse) {
		this.hasHourse = hasHourse;
	}
	public String getTrade() {
		return trade;
	}
	public void setTrade(String trade) {
		this.trade = trade;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public String getCompanyNature() {
		return companyNature;
	}
	public void setCompanyNature(String companyNature) {
		this.companyNature = companyNature;
	}
	public String getMonthlyIncome() {
		return monthlyIncome;
	}
	public void setMonthlyIncome(String monthlyIncome) {
		this.monthlyIncome = monthlyIncome;
	}
	public String getCreditNums() {
		return creditNums;
	}
	public void setCreditNums(String creditNums) {
		this.creditNums = creditNums;
	}
	public String getLoanNums() {
		return loanNums;
	}
	public void setLoanNums(String loanNums) {
		this.loanNums = loanNums;
	}
	public String getHasCarLoan() {
		return hasCarLoan;
	}
	public void setHasCarLoan(String hasCarLoan) {
		this.hasCarLoan = hasCarLoan;
	}
	public String getHasHourseLoan() {
		return hasHourseLoan;
	}
	public void setHasHourseLoan(String hasHourseLoan) {
		this.hasHourseLoan = hasHourseLoan;
	}
	public String getAttachmentUrl() {
		return attachmentUrl;
	}
	public void setAttachmentUrl(String attachmentUrl) {
		this.attachmentUrl = attachmentUrl;
	}
}
