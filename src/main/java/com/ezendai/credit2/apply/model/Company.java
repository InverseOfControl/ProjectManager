package com.ezendai.credit2.apply.model;

import java.util.Date;

import com.ezendai.credit2.framework.model.BaseModel;

public class Company extends BaseModel{
	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	private static final long serialVersionUID = -4552861176839021261L;

	private Long id;

	/** 企业全称 */
	private String name;

	/** 所属行业 */
	private String industryInvolved;

	/** 法人代表 */
	private String legalRepresentative;

	/** 法人代表身份证号 */
	private String legalRepresentativeId;

	/** 近年营业额 */
	private Long incomePerMonth;

	/** 成立时间 */
	private Date foundedDate;

	/** 企业类型 */
	private String category;

	/** 企业地址 */
	private String address;

	/** 平均年利率 */
	private Long avgProfitPerYear;

	/** 企业电话 */
	private String phone;

	/** 邮政编码 */
	private String zipCode;

	/** 营业场所 */
	private String operationSite;

	/** 主营业务 */
	private String majorBusiness;

	/** 员工人数 */
	private Long employeesNumber;

	/** 员工工资支出 */
	private Long employeesWagesPerMonth;
	
	/**POS收单机构*/
	private String posAcquirer;
	/**POS人均交易量*/
	private Long posCapitavolume;
	/**企业负债余额*/
	private Long companyDebtBalance;
	/**POS入网时间*/
	private Date posOpenDate;
	/**企业月营业额*/
	private Long monthTurnOver;

	/**网商会员平台*/
	private String platform;
	/**网商注册日期*/
	private Date regDate;
	/**网商会员类型*/
	private Long memberType;
	private String memberTypeText;
	
	

	public String getMemberTypeText() {
		return memberTypeText;
	}

	public void setMemberTypeText(String memberTypeText) {
		this.memberTypeText = memberTypeText;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public Long getMemberType() {
		return memberType;
	}

	public void setMemberType(Long memberType) {
		this.memberType = memberType;
	}
	
	

	public Long getMonthTurnOver() {
		return monthTurnOver;
	}

	public void setMonthTurnOver(Long monthTurnOver) {
		this.monthTurnOver = monthTurnOver;
	}

	public String getPosAcquirer() {
		return posAcquirer;
	}

	public void setPosAcquirer(String posAcquirer) {
		this.posAcquirer = posAcquirer;
	}

	public Long getPosCapitavolume() {
		return posCapitavolume;
	}

	public void setPosCapitavolume(Long posCapitavolume) {
		this.posCapitavolume = posCapitavolume;
	}

	public Long getCompanyDebtBalance() {
		return companyDebtBalance;
	}

	public void setCompanyDebtBalance(Long companyDebtBalance) {
		this.companyDebtBalance = companyDebtBalance;
	}

	public Date getPosOpenDate() {
		return posOpenDate;
	}

	public void setPosOpenDate(Date posOpenDate) {
		this.posOpenDate = posOpenDate;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getIndustryInvolved() {
		return industryInvolved;
	}

	public void setIndustryInvolved(String industryInvolved) {
		this.industryInvolved = industryInvolved == null ? null : industryInvolved.trim();
	}

	public String getLegalRepresentative() {
		return legalRepresentative;
	}

	public void setLegalRepresentative(String legalRepresentative) {
		this.legalRepresentative = legalRepresentative == null ? null : legalRepresentative.trim();
	}

	public String getLegalRepresentativeId() {
		return legalRepresentativeId;
	}

	public void setLegalRepresentativeId(String legalRepresentativeId) {
		this.legalRepresentativeId = legalRepresentativeId == null ? null : legalRepresentativeId
			.trim();
	}

	public Long getIncomePerMonth() {
		return incomePerMonth;
	}

	public void setIncomePerMonth(Long incomePerMonth) {
		this.incomePerMonth = incomePerMonth;
	}

	public Date getFoundedDate() {
		return foundedDate;
	}

	public void setFoundedDate(Date foundedDate) {
		this.foundedDate = foundedDate;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category == null ? null : category.trim();
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address == null ? null : address.trim();
	}

	public Long getAvgProfitPerYear() {
		return avgProfitPerYear;
	}

	public void setAvgProfitPerYear(Long avgProfitPerYear) {
		this.avgProfitPerYear = avgProfitPerYear;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone == null ? null : phone.trim();
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode == null ? null : zipCode.trim();
	}

	public String getOperationSite() {
		return operationSite;
	}

	public void setOperationSite(String operationSite) {
		this.operationSite = operationSite == null ? null : operationSite.trim();
	}

	public String getMajorBusiness() {
		return majorBusiness;
	}

	public void setMajorBusiness(String majorBusiness) {
		this.majorBusiness = majorBusiness == null ? null : majorBusiness.trim();
	}

	public Long getEmployeesNumber() {
		return employeesNumber;
	}

	public void setEmployeesNumber(Long employeesNumber) {
		this.employeesNumber = employeesNumber;
	}

	public Long getEmployeesWagesPerMonth() {
		return employeesWagesPerMonth;
	}

	public void setEmployeesWagesPerMonth(Long employeesWagesPerMonth) {
		this.employeesWagesPerMonth = employeesWagesPerMonth;
	}

}