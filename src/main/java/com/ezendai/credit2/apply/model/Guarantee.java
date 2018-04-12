/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.apply.model;

import com.ezendai.credit2.framework.model.BaseModel;

/**
 * <pre>
 * 担保人
 * </pre>
 *
 * @author xiaoxiong
 * @version $Id: Guarantee.java, v 0.1 2014年6月24日 下午2:48:42 xiaoxiong Exp $
 */
public class Guarantee extends BaseModel {

	private static final long serialVersionUID = 7492197588345494977L;

	private Loan loan;
	
	/** 借款人ID */
	private Long personId;
	
	/** 贷款信息ID */
	private Long loanId;
	
	/** 姓名 */
	private String name;
	
	/** 担保人类型(自然人或者法人) */
	private Integer guaranteeType; 

	/** 性别 */
	private Integer sex;
	
	/** 身份证号 */
	private String idnum;
	
	/** 婚姻状况 */
	private Integer married;
	
	/** 最高学历 */
	private String educationLevel;
	
	/** 子女 */
	private Integer hasChildren;

	/** 邮政编码 */
	private String zipCode;
	
	/** 住宅地址 */
	private String address;
	
	/** 手机号码 */
	private String mobilePhone;
	
	/** 常用邮箱 */
	private String email;
	private String emails;
	
	/** 住宅电话 */
	private String homePhone;

	/** 企业全称 */
	private String companyFullName;
	
	/** 企业电话 */
	private String companyPhone;
	
	/** 企业地址 */
	private String companyAddress;
	
	/** 是否是所选择的担保人 0 :不是  1: 是  */
	private Long check;

	/** 银行账户ID */
	private Long bankAccountId;

	private Person person;
	
	/** 标志指定担保人*/
	private Integer flag;
	
	/**特殊处理，将flag致空*/
	private Boolean isFlagNull;

	public Loan getLoan() {
		return loan;
	}

	public void setLoan(Loan loan) {
		this.loan = loan;
	}

	public Long getLoanId() {
		return loanId;
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

	public Integer getGuaranteeType() {
		return guaranteeType;
	}

	public void setGuaranteeType(Integer guaranteeType) {
		this.guaranteeType = guaranteeType;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getIdnum() {
		return idnum;
	}

	public void setIdnum(String idnum) {
		this.idnum = idnum;
	}

	public Integer getMarried() {
		return married;
	}

	public void setMarried(Integer married) {
		this.married = married;
	}

	public String getEducationLevel() {
		return educationLevel;
	}

	public void setEducationLevel(String educationLevel) {
		this.educationLevel = educationLevel;
	}

	public Integer getHasChildren() {
		return hasChildren;
	}

	public void setHasChildren(Integer hasChildren) {
		this.hasChildren = hasChildren;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getCompanyFullName() {
		return companyFullName;
	}

	public void setCompanyFullName(String companyFullName) {
		this.companyFullName = companyFullName;
	}

	public String getCompanyPhone() {
		return companyPhone;
	}

	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public Long getBankAccountId() {
		return bankAccountId;
	}

	public void setBankAccountId(Long bankAccountId) {
		this.bankAccountId = bankAccountId;
	}

	public String getEmails() {
		return emails;
	}

	public void setEmails(String emails) {
		this.emails = emails;
	}

	public Long getCheck() {
		return check;
	}

	public void setCheck(Long check) {
		this.check = check;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Boolean getIsFlagNull() {
		return isFlagNull;
	}

	public void setIsFlagNull(Boolean isFlagNull) {
		this.isFlagNull = isFlagNull;
	}

}
