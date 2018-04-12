package com.ezendai.credit2.audit.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.ezendai.credit2.framework.vo.BaseVO;

public class ContractVO extends BaseVO {

	/**  */
	private static final long serialVersionUID = 5422302692286955668L;

	/**贷款信息ID*/
	private long loanId;
	/**合同编号*/
	private String contractNo;
	/**合同签订日期*/
	private Date signDate;
	/**合同名称*/
	private String contractName;
	/**合同类型*/
	private Integer type;
	/**区名*/
	private String areaName;
	/**市名*/
	private String cityName;
	/**借款人名字*/
	private String personName;
	/**身份证号码*/
	private String idNum;
	/**住址*/
	private String address;
	/**邮政编码*/
	private String zipCode;
	/**电子邮箱*/
	private String email;
	/**支付金额*/
	private BigDecimal payAmount;
	/**借款详细用途*/
	private String purpose;
	/**借款本金金额*/
	private BigDecimal pactMoney;
	/**月偿还本息数额*/
	private BigDecimal monthInterestAmount;
	/**分期期数*/
	private Integer times;
	/**还款开始日期*/
	private Date startRepayDate;
	/**还款结束日期*/
	private Date endRepayDate;
	/**银行户名*/
	private String bankAccountName;
	/**银行账号*/
	private String bankAccountNum;
	/**开户行*/
	private String openingBank;
	/**甲方支付评估费*/
	private BigDecimal assessmentFees;
	/**甲方支付每月管理费*/
	private BigDecimal manageFees;
	/**甲方支付丙方管理费*/
	private BigDecimal ttpManageFees;
	/**联系方式*/
	private String contact;
	/**月还人民币*/
	private BigDecimal monthAmount;
	/**自然担保人姓名*/
	private String guaranteeName;
	/**自然担保人身份证*/
	private String guaranteeIdNum;
	/**法人担保公司名称*/
	private String legalGuarantee;
	/**营业部公司名*/
	private String businessCompanyName;
	/**前期费用*/
	private BigDecimal preparatoryAmount;
	/**电话*/
	private String tell;
	/**总利息数额*/
	private BigDecimal totalAmount;
	/**应还本金*/
	private BigDecimal principalAmount;
	/**前期风险金*/
	private BigDecimal raskAmount;
	/**车牌号*/
	private String plateNumber;
	/**车架号*/
	private String frameNumber;
	/**出借人*/
	private String lender;
	/**支行名称*/
	private String bankBranchName;
	/**是否已婚*/
	private Integer isMarried;
	/**营业部地址*/
	private String businessAddress;
	/***/
	private BigDecimal referRate;
	/**还款日*/
	private String repayDate;
	/**借款协议编号*/
	private String loanAgreeNum;
	/**拍照所属地*/
	private String territory;
	/**品牌*/
	private String brand;
	
	/**原借款金额*/
	private BigDecimal orgPactMoney;
	
	/**审批金额*/
	private BigDecimal auditMoney;

	public long getLoanId() {
		return loanId;
	}

	public void setLoanId(long loanId) {
		this.loanId = loanId;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public BigDecimal getPactMoney() {
		return pactMoney;
	}

	public void setPactMoney(BigDecimal pactMoney) {
		this.pactMoney = pactMoney;
	}

	public BigDecimal getMonthInterestAmount() {
		return monthInterestAmount;
	}

	public void setMonthInterestAmount(BigDecimal monthInterestAmount) {
		this.monthInterestAmount = monthInterestAmount;
	}

	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
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

	public String getBankAccountName() {
		return bankAccountName;
	}

	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}

	public String getBankAccountNum() {
		return bankAccountNum;
	}

	public void setBankAccountNum(String bankAccountNum) {
		this.bankAccountNum = bankAccountNum;
	}

	public String getOpeningBank() {
		return openingBank;
	}

	public void setOpeningBank(String openingBank) {
		this.openingBank = openingBank;
	}

	public BigDecimal getAssessmentFees() {
		return assessmentFees;
	}

	public void setAssessmentFees(BigDecimal assessmentFees) {
		this.assessmentFees = assessmentFees;
	}

	public BigDecimal getManageFees() {
		return manageFees;
	}

	public void setManageFees(BigDecimal manageFees) {
		this.manageFees = manageFees;
	}

	public BigDecimal getTtpManageFees() {
		return ttpManageFees;
	}

	public void setTtpManageFees(BigDecimal ttpManageFees) {
		this.ttpManageFees = ttpManageFees;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public BigDecimal getMonthAmount() {
		return monthAmount;
	}

	public void setMonthAmount(BigDecimal monthAmount) {
		this.monthAmount = monthAmount;
	}

	public String getGuaranteeName() {
		return guaranteeName;
	}

	public void setGuaranteeName(String guaranteeName) {
		this.guaranteeName = guaranteeName;
	}

	public String getGuaranteeIdNum() {
		return guaranteeIdNum;
	}

	public void setGuaranteeIdNum(String guaranteeIdNum) {
		this.guaranteeIdNum = guaranteeIdNum;
	}

	public String getLegalGuarantee() {
		return legalGuarantee;
	}

	public void setLegalGuarantee(String legalGuarantee) {
		this.legalGuarantee = legalGuarantee;
	}

	public String getBusinessCompanyName() {
		return businessCompanyName;
	}

	public void setBusinessCompanyName(String businessCompanyName) {
		this.businessCompanyName = businessCompanyName;
	}

	public BigDecimal getPreparatoryAmount() {
		return preparatoryAmount;
	}

	public void setPreparatoryAmount(BigDecimal preparatoryAmount) {
		this.preparatoryAmount = preparatoryAmount;
	}

	public String getTell() {
		return tell;
	}

	public void setTell(String tell) {
		this.tell = tell;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getPrincipalAmount() {
		return principalAmount;
	}

	public void setPrincipalAmount(BigDecimal principalAmount) {
		this.principalAmount = principalAmount;
	}

	public BigDecimal getRaskAmount() {
		return raskAmount;
	}

	public void setRaskAmount(BigDecimal raskAmount) {
		this.raskAmount = raskAmount;
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	public String getFrameNumber() {
		return frameNumber;
	}

	public void setFrameNumber(String frameNumber) {
		this.frameNumber = frameNumber;
	}

	public String getLender() {
		return lender;
	}

	public void setLender(String lender) {
		this.lender = lender;
	}

	public String getBankBranchName() {
		return bankBranchName;
	}

	public void setBankBranchName(String bankBranchName) {
		this.bankBranchName = bankBranchName;
	}

	public Integer getIsMarried() {
		return isMarried;
	}

	public void setIsMarried(Integer isMarried) {
		this.isMarried = isMarried;
	}

	public String getBusinessAddress() {
		return businessAddress;
	}

	public void setBusinessAddress(String businessAddress) {
		this.businessAddress = businessAddress;
	}

	public BigDecimal getReferRate() {
		return referRate;
	}

	public void setReferRate(BigDecimal referRate) {
		this.referRate = referRate;
	}

	public String getRepayDate() {
		return repayDate;
	}

	public void setRepayDate(String repayDate) {
		this.repayDate = repayDate;
	}

	public String getLoanAgreeNum() {
		return loanAgreeNum;
	}

	public void setLoanAgreeNum(String loanAgreeNum) {
		this.loanAgreeNum = loanAgreeNum;
	}

	public String getTerritory() {
		return territory;
	}

	public void setTerritory(String territory) {
		this.territory = territory;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public BigDecimal getOrgPactMoney() {
		return orgPactMoney;
	}

	public void setOrgPactMoney(BigDecimal orgPactMoney) {
		this.orgPactMoney = orgPactMoney;
	}

	public BigDecimal getAuditMoney() {
		return auditMoney;
	}

	public void setAuditMoney(BigDecimal auditMoney) {
		this.auditMoney = auditMoney;
	}
	
}
