package com.ezendai.credit2.report.model;

import java.math.BigDecimal;
import java.util.Date;
import com.ezendai.credit2.framework.model.BaseModel;

public class Smallrp extends BaseModel {
	private static final long serialVersionUID = 8124313710722149065L;
	private Long id;// PK
	/**
	 * LOAN_ID
	 */

	private Long loanId;
	/**
	 * CONTRACT_NO
	 */
	private String contractNo;
	/**
	 * SIGN_DATE
	 */
	private Date signDate;
	/**
	 * TYPE
	 */
	private Integer type;
	/**
	 * CONTRACT_NAME
	 */

	private String contractName;
	/**
	 * CITY_NAME
	 */

	private String cityName;
	/**
	 * AREA_NAME
	 */

	private String areaName;
	/**
	 * PERSON_NAME
	 */

	private String personName;
	/**
	 * ID_NUM
	 */

	private String idNum;
	/**
	 * ADDRESS
	 */

	private String address;
	/**
	 * EMAIL
	 */

	private String email;
	/**
	 * PAY_AMOUNT
	 */

	private BigDecimal payAmount;
	/**
	 * PURPOSE
	 */

	private String purpose;
	/**
	 * PACT_MONEY
	 */

	private BigDecimal pactMoney;
	/**
	 * MONTH_INTEREST_AMOUNT
	 */

	private BigDecimal monthInterestAmount;
	/**
	 * TIMES
	 */
	private Integer times;
	/**
	 * START_REPAY_DATE
	 */
	private Date startRepayDate;
	/**
	 * END_REPAY_DATE
	 */
	private Date endRepayDate;
	/**
	 * BANK_ACCOUNT_NAME
	 */

	private String bankAccountName;
	/**
	 * BANK_ACCOUNT_NUM
	 */

	private String bankAccountNum;
	/**
	 * OPENING_BANK
	 */

	private String openingBank;
	/**
	 * ASSESSMENT_FEES
	 */

	private BigDecimal assessmentFees;
	/**
	 * MANAGE_FEES
	 */

	private BigDecimal manageFees;
	/**
	 * TTP_MANAGE_FEES
	 */

	private BigDecimal ttpManageFees;
	/**
	 * CONTACT
	 */

	private String contact;
	/**
	 * MONTH_AMOUNT
	 */

	private BigDecimal monthAmount;
	/**
	 * GUARANTEE_NAME
	 */

	private String guaranteeName;
	
	
	/**
	 * GUARANTEE_ID_NUM
	 */
	private String guaranteeIdNum;   
	
	/**
	 * LOAN_AGREE_NUMR
	 */

	private String loanAgreeNum;
	/**
	 * COMPANY_NAME
	 */

	private String companyName;
	/**
	 * LEGAL_GUARANTEE
	 */

	private String legalGuarantee;
	/**
	 * BUSINESS_COMPANY_NAMET
	 */

	private String businessCompanyName;
	/**
	 * PREPARATORY_AMOUNT
	 */

	private BigDecimal preparatoryAmount;
	/**
	 * TELL
	 */

	private String tell;
	/**
	 * TOTAL_AMOUNT
	 */

	private BigDecimal totalAmount;
	/**
	 * PRINCIPAL_AMOUNT
	 */

	private BigDecimal principalAmount;
	/**
	 * RASK_AMOUNT
	 */

	private BigDecimal raskAmount;
	/**
	 * TERRITORY
	 */

	private String territory;
	/**
	 * PLATE_NUMBER
	 */

	private String plateNumber;
	/**
	 * FRAME_NUMBER
	 */

	private String frameNumber;
	/**
	 * LENDER
	 */

	private String lender;
	/**
	 * BANK_BRANCH_NAME
	 */

	private String bankBranchName;
	/**
	 * IS_MARRIED
	 */

	private Integer isMarried;
	/**
	 * BUSINESS_ADDRESS
	 */

	private String businessAddress;
	/**
	 * CREATOR_ID
	 */

	private Long creatorId;
	/**
	 * CREATOR
	 */
	private String creator;
	/**
	 * CREATED_TIME
	 */
	private Date createdTime;
	/**
	 * MODIFIER_ID
	 */

	private Long modifierId;
	/**
	 * MODIFIER
	 */

	private String modifier;
	/**
	 * MODIFIED_TIME
	 */
	private Date modifiedTime;
	/**
	 * VERSION
	 */

	private Long version;
	/**
	 * ZIP_CODE
	 */
	private String zipCode;

    private String brand;
    
	/**原借款金额*/
	private BigDecimal orgPactMoney;
	
	private BigDecimal auditMoney;
	/**递减费*/
	private BigDecimal 	diffRefund;
	/**咨询费*/
	private BigDecimal consultingFeeRate;


	public BigDecimal getConsultingFeeRate() {
		return consultingFeeRate;
	}

	public void setConsultingFeeRate(BigDecimal consultingFeeRate) {
		this.consultingFeeRate = consultingFeeRate;
	}

	public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }



	public BigDecimal getDiffRefund() {
		return diffRefund;
	}

	public void setDiffRefund(BigDecimal diffRefund) {
		this.diffRefund = diffRefund;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * return LOAN_ID
	 */
	public Long getLoanId() {
		return this.loanId;
	}

	public void setLoanId(Long value) {
		this.loanId = value;
	}

	/**
	 * return CONTRACT_NO
	 */
	public String getContractNo() {
		return this.contractNo;
	}

	public void setContractNo(String value) {
		this.contractNo = value;
	}

	/**
	 * return SIGN_DATE
	 */
	public java.util.Date getSignDate() {
		return this.signDate;
	}

	public void setSignDate(java.util.Date value) {
		this.signDate = value;
	}

	/**
	 * return TYPE
	 */
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer value) {
		this.type = value;
	}

	/**
	 * return CONTRACT_NAME
	 */
	public String getContractName() {
		return this.contractName;
	}

	public void setContractName(String value) {
		this.contractName = value;
	}

	/**
	 * return CITY_NAME
	 */
	public String getCityName() {
		return this.cityName;
	}

	public void setCityName(String value) {
		this.cityName = value;
	}

	/**
	 * return AREA_NAME
	 */
	public String getAreaName() {
		return this.areaName;
	}

	public void setAreaName(String value) {
		this.areaName = value;
	}

	/**
	 * return PERSON_NAME
	 */
	public String getPersonName() {
		return this.personName;
	}

	public void setPersonName(String value) {
		this.personName = value;
	}

	/**
	 * return ID_NUM
	 */
	public String getIdNum() {
		return this.idNum;
	}

	public void setIdNum(String value) {
		this.idNum = value;
	}

	/**
	 * return ADDRESS
	 */
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String value) {
		this.address = value;
	}

	/**
	 * return EMAIL
	 */
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String value) {
		this.email = value;
	}

	/**
	 * return PURPOSE
	 */
	public String getPurpose() {
		return this.purpose;
	}

	public void setPurpose(String value) {
		this.purpose = value;
	}

	/**
	 * return TIMES
	 */
	public Integer getTimes() {
		return this.times;
	}

	public void setTimes(Integer value) {
		this.times = value;
	}

	/**
	 * return START_REPAY_DATE
	 */
	public java.util.Date getStartRepayDate() {
		return this.startRepayDate;
	}

	public void setStartRepayDate(java.util.Date value) {
		this.startRepayDate = value;
	}

	/**
	 * return END_REPAY_DATE
	 */
	public java.util.Date getEndRepayDate() {
		return this.endRepayDate;
	}

	public void setEndRepayDate(java.util.Date value) {
		this.endRepayDate = value;
	}

	/**
	 * return BANK_ACCOUNT_NAME
	 */
	public String getBankAccountName() {
		return this.bankAccountName;
	}

	public void setBankAccountName(String value) {
		this.bankAccountName = value;
	}

	/**
	 * return BANK_ACCOUNT_NUM
	 */
	public String getBankAccountNum() {
		return this.bankAccountNum;
	}

	public void setBankAccountNum(String value) {
		this.bankAccountNum = value;
	}

	/**
	 * return OPENING_BANK
	 */
	public String getOpeningBank() {
		return this.openingBank;
	}

	public void setOpeningBank(String value) {
		this.openingBank = value;
	}

	/**
	 * return CONTACT
	 */
	public String getContact() {
		return this.contact;
	}

	public void setContact(String value) {
		this.contact = value;
	}

	/**
	 * return GUARANTEE_NAME
	 */
	public String getGuaranteeName() {
		return this.guaranteeName;
	}

	public void setGuaranteeName(String value) {
		this.guaranteeName = value;
	}

	/**
	 * return LOAN_AGREE_NUMR
	 */
	public String getLoanAgreeNum() {
		return this.loanAgreeNum;
	}

	public void setLoanAgreeNum(String value) {
		this.loanAgreeNum = value;
	}

	/**
	 * return COMPANY_NAME
	 */
	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String value) {
		this.companyName = value;
	}

	/**
	 * return LEGAL_GUARANTEE
	 */
	public String getLegalGuarantee() {
		return this.legalGuarantee;
	}

	public void setLegalGuarantee(String value) {
		this.legalGuarantee = value;
	}

	/**
	 * return BUSINESS_COMPANY_NAMET
	 */
	public String getBusinessCompanyName() {
		return this.businessCompanyName;
	}

	public void setBusinessCompanyName(String value) {
		this.businessCompanyName = value;
	}

	/**
	 * return TELL
	 */
	public String getTell() {
		return this.tell;
	}

	public void setTell(String value) {
		this.tell = value;
	}

	/**
	 * return TERRITORY
	 */
	public String getTerritory() {
		return this.territory;
	}

	public void setTerritory(String value) {
		this.territory = value;
	}

	/**
	 * return PLATE_NUMBER
	 */
	public String getPlateNumber() {
		return this.plateNumber;
	}

	public void setPlateNumber(String value) {
		this.plateNumber = value;
	}

	/**
	 * return FRAME_NUMBER
	 */
	public String getFrameNumber() {
		return this.frameNumber;
	}

	public void setFrameNumber(String value) {
		this.frameNumber = value;
	}

	/**
	 * return LENDER
	 */
	public String getLender() {
		return this.lender;
	}

	public void setLender(String value) {
		this.lender = value;
	}

	/**
	 * return BANK_BRANCH_NAME
	 */
	public String getBankBranchName() {
		return this.bankBranchName;
	}

	public void setBankBranchName(String value) {
		this.bankBranchName = value;
	}

	/**
	 * return IS_MARRIED
	 */
	public Integer getIsMarried() {
		return this.isMarried;
	}

	public void setIsMarried(Integer value) {
		this.isMarried = value;
	}

	/**
	 * return BUSINESS_ADDRESS
	 */
	public String getBusinessAddress() {
		return this.businessAddress;
	}

	public void setBusinessAddress(String value) {
		this.businessAddress = value;
	}

	/**
	 * return CREATOR_ID
	 */
	public Long getCreatorId() {
		return this.creatorId;
	}

	public void setCreatorId(Long value) {
		this.creatorId = value;
	}

	/**
	 * return CREATOR
	 */
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String value) {
		this.creator = value;
	}

	/**
	 * return CREATED_TIME
	 */
	public java.util.Date getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(java.util.Date value) {
		this.createdTime = value;
	}

	/**
	 * return MODIFIER_ID
	 */
	public Long getModifierId() {
		return this.modifierId;
	}

	public void setModifierId(Long value) {
		this.modifierId = value;
	}

	/**
	 * return MODIFIER
	 */
	public String getModifier() {
		return this.modifier;
	}

	public void setModifier(String value) {
		this.modifier = value;
	}

	/**
	 * return MODIFIED_TIME
	 */
	public java.util.Date getModifiedTime() {
		return this.modifiedTime;
	}

	public void setModifiedTime(java.util.Date value) {
		this.modifiedTime = value;
	}

	/**
	 * return VERSION
	 */
	public Long getVersion() {
		return this.version;
	}

	public void setVersion(Long value) {
		this.version = value;
	}

	public String getGuaranteeIdNum() {
		return guaranteeIdNum;
	}

	public void setGuaranteeIdNum(String guaranteeIdNum) {
		this.guaranteeIdNum = guaranteeIdNum;
	}
	/*
	 * REPAY_DATE
	 */
	private String repayDate;


	public String getRepayDate() {
		return repayDate;
	}

	public void setRepayDate(String repayDate) {
		this.repayDate = repayDate;
	}

	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
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

	public BigDecimal getMonthAmount() {
		return monthAmount;
	}

	public void setMonthAmount(BigDecimal monthAmount) {
		this.monthAmount = monthAmount;
	}

	public BigDecimal getPreparatoryAmount() {
		return preparatoryAmount;
	}

	public void setPreparatoryAmount(BigDecimal preparatoryAmount) {
		this.preparatoryAmount = preparatoryAmount;
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
