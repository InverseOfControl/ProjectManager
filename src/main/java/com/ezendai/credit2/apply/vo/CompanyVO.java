/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.apply.vo;

import java.util.Date;

import com.ezendai.credit2.framework.vo.BaseVO;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author liyuepeng
 * @version $Id: CompanyVO.java, v 0.1 2014-7-25 下午01:33:29 liyuepeng Exp $
 */
public class CompanyVO extends BaseVO {

	/**  */
	private static final long serialVersionUID = 4069031048404633882L;
	
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

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
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
		this.name = name;
	}

	public String getIndustryInvolved() {
		return industryInvolved;
	}

	public void setIndustryInvolved(String industryInvolved) {
		this.industryInvolved = industryInvolved;
	}

	public String getLegalRepresentative() {
		return legalRepresentative;
	}

	public void setLegalRepresentative(String legalRepresentative) {
		this.legalRepresentative = legalRepresentative;
	}

	public String getLegalRepresentativeId() {
		return legalRepresentativeId;
	}

	public void setLegalRepresentativeId(String legalRepresentativeId) {
		this.legalRepresentativeId = legalRepresentativeId;
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
		this.category = category;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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
		this.phone = phone;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getOperationSite() {
		return operationSite;
	}

	public void setOperationSite(String operationSite) {
		this.operationSite = operationSite;
	}

	public String getMajorBusiness() {
		return majorBusiness;
	}

	public void setMajorBusiness(String majorBusiness) {
		this.majorBusiness = majorBusiness;
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
