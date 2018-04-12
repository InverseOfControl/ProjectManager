package com.ezendai.credit2.apply.model;

import java.math.BigDecimal;
import java.util.Date;

import com.ezendai.credit2.framework.model.BaseModel;

public class Person extends BaseModel {

	private static final long serialVersionUID = 7249177698181502433L;
	/** 姓名 */
	private String name;

	/** 性别 */
	private String sex;

	/** 客户编号 */
	private String personNo;

	/** 身份证号 */
	private String idnum;

	/** 公司ID */
	private Long companyId;

	/** 婚姻状况 */
	private Long married;

	/** 类别 */
	private String identifier;

	/** 最高学历 */
	private String educationLevel;

	/** 有无子女 */
	private Long hasChildren;

	/** 子女数目 */
	private Integer childrenNum;

	/** 邮政编码 */
	private String zipCode;

	/** 工作证明人 */
	private String witness;

	/** 其他收入 */
	private BigDecimal otherIncome;

	/** 月发薪日 */
	private Long payDate;

	/** 工作性质 */
	private String workNature;

	/** 职务 */
	private String job;

	/** 部门 */
	private String deptName;

	/** 固话分机 */
	private String ext;

	/** 户籍地址 */
	private String placeDomicile;

	/** 户籍地址邮政编码 */
	private String householdZipCode;

	/** 住宅地址 */
	private String address;

	/** 手机号码 */
	private String mobilePhone;

	/** 常用邮箱 */
	private String email;

	/** 住宅电话 */
	private String homePhone;

	/** 房产类型 */
	private String houseEstateType;

	/** 每月租金 */
	private BigDecimal rentPerMonth;

	/** 居住类型 */
	private String liveType;

	/** 房贷 */
	private Long hasHouseLoan;

	/** 房产地址 */
	private String houseEstateAddress;

	/** 月平均收入 */
	private BigDecimal incomePerMonth;

	/** 工作证明人的工作部门 */
	private String workThatDept;

	/** 工作证明人的职务 */
	private String workThatPosition;

	/** 工作证明人的电话 */
	private String workThatTell;

	/** 客户类型 */
	private Integer personType;

	/** 单位性质 */
	private Integer companyType;
	
	/** 职业类型 */
	private String professionType;
	
	/** 可接受最高还款额（车） */
	private BigDecimal maxRepayAmount;
	
	/** 子女在读学校（车） */
	private String childrenSchool;
	
	/** 私营企业类型（车）*/
	private String privateEnterpriseType;
	
	/** 公司成立时间（车） */
	private Date foundedDate;
	
	/** 经营场所（车） */
	private Integer businessPlace;
	
	/** 员工人数（车） */
	private Long totalEmployees;
	
	/** 占股比例(车) */
	private Integer ratioOfInvestments;
	
	/** 每月净利润额（车） */
	private BigDecimal monthOfProfit;
	
	/**产品类型**/
	private Integer productType;
	
	/**个人负债余额*/
	private Long personDebtBalance;
	/**月均还款额*/
	private Long monthRepayAmount; 
	/**与谁居住*/
	private String livingWith;
	/**收入来源*/
	private String incomeSource;	
	
	/**企业名称（数据导入使用)*/
	private String companyName;	
	
	private Company companyImp;
	
	private String sns;
	
	
	
	
	public String getSns() {
		return sns;
	}

	public void setSns(String sns) {
		this.sns = sns;
	}

	public String getLivingWith() {
		return livingWith;
	}

	public void setLivingWith(String livingWith) {
		this.livingWith = livingWith;
	}

	public String getIncomeSource() {
		return incomeSource;
	}

	public void setIncomeSource(String incomeSource) {
		this.incomeSource = incomeSource;
	}

	public Long getPersonDebtBalance() {
		return personDebtBalance;
	}

	public void setPersonDebtBalance(Long personDebtBalance) {
		this.personDebtBalance = personDebtBalance;
	}

	public Long getMonthRepayAmount() {
		return monthRepayAmount;
	}

	public void setMonthRepayAmount(Long monthRepayAmount) {
		this.monthRepayAmount = monthRepayAmount;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPersonNo() {
		return personNo;
	}

	public void setPersonNo(String personNo) {
		this.personNo = personNo;
	}

	public String getIdnum() {
		return idnum;
	}

	public void setIdnum(String idnum) {
		this.idnum = idnum;
	}

	public Long getMarried() {
		return married;
	}

	public void setMarried(Long married) {
		this.married = married;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getEducationLevel() {
		return educationLevel;
	}

	public void setEducationLevel(String educationLevel) {
		this.educationLevel = educationLevel;
	}

	public Long getHasChildren() {
		return hasChildren;
	}

	public void setHasChildren(Long hasChildren) {
		this.hasChildren = hasChildren;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getWitness() {
		return witness;
	}

	public void setWitness(String witness) {
		this.witness = witness;
	}

	public BigDecimal getOtherIncome() {
		return otherIncome;
	}

	public void setOtherIncome(BigDecimal otherIncome) {
		this.otherIncome = otherIncome;
	}

	public Long getPayDate() {
		return payDate;
	}

	public void setPayDate(Long payDate) {
		this.payDate = payDate;
	}

	public String getWorkNature() {
		return workNature;
	}

	public void setWorkNature(String workNature) {
		this.workNature = workNature;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getPlaceDomicile() {
		return placeDomicile;
	}

	public void setPlaceDomicile(String placeDomicile) {
		this.placeDomicile = placeDomicile;
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

	public String getHouseEstateType() {
		return houseEstateType;
	}

	public void setHouseEstateType(String houseEstateType) {
		this.houseEstateType = houseEstateType;
	}

	public BigDecimal getRentPerMonth() {
		return rentPerMonth;
	}

	public void setRentPerMonth(BigDecimal rentPerMonth) {
		this.rentPerMonth = rentPerMonth;
	}

	public String getLiveType() {
		return liveType;
	}

	public void setLiveType(String liveType) {
		this.liveType = liveType;
	}

	public Long getHasHouseLoan() {
		return hasHouseLoan;
	}

	public void setHasHouseLoan(Long hasHouseLoan) {
		this.hasHouseLoan = hasHouseLoan;
	}

	public String getHouseEstateAddress() {
		return houseEstateAddress;
	}

	public void setHouseEstateAddress(String houseEstateAddress) {
		this.houseEstateAddress = houseEstateAddress;
	}

	public BigDecimal getIncomePerMonth() {
		return incomePerMonth;
	}

	public void setIncomePerMonth(BigDecimal incomePerMonth) {
		this.incomePerMonth = incomePerMonth;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getHouseholdZipCode() {
		return householdZipCode;
	}

	public void setHouseholdZipCode(String householdZipCode) {
		this.householdZipCode = householdZipCode;
	}

	public String getWorkThatDept() {
		return workThatDept;
	}

	public void setWorkThatDept(String workThatDept) {
		this.workThatDept = workThatDept;
	}

	public String getWorkThatPosition() {
		return workThatPosition;
	}

	public void setWorkThatPosition(String workThatPosition) {
		this.workThatPosition = workThatPosition;
	}

	public String getWorkThatTell() {
		return workThatTell;
	}

	public void setWorkThatTell(String workThatTell) {
		this.workThatTell = workThatTell;
	}

	public Integer getChildrenNum() {
		return childrenNum;
	}

	public void setChildrenNum(Integer childrenNum) {
		this.childrenNum = childrenNum;
	}

	public Integer getPersonType() {
		return personType;
	}

	public void setPersonType(Integer personType) {
		this.personType = personType;
	}

	public Integer getCompanyType() {
		return companyType;
	}

	public void setCompanyType(Integer companyType) {
		this.companyType = companyType;
	}

	public String getProfessionType() {
		return professionType;
	}

	public void setProfessionType(String professionType) {
		this.professionType = professionType;
	}

	public BigDecimal getMaxRepayAmount() {
		return maxRepayAmount;
	}

	public void setMaxRepayAmount(BigDecimal maxRepayAmount) {
		this.maxRepayAmount = maxRepayAmount;
	}

	public String getChildrenSchool() {
		return childrenSchool;
	}

	public void setChildrenSchool(String childrenSchool) {
		this.childrenSchool = childrenSchool;
	}

	public String getPrivateEnterpriseType() {
		return privateEnterpriseType;
	}

	public void setPrivateEnterpriseType(String privateEnterpriseType) {
		this.privateEnterpriseType = privateEnterpriseType;
	}

	public Date getFoundedDate() {
		return foundedDate;
	}

	public void setFoundedDate(Date foundedDate) {
		this.foundedDate = foundedDate;
	}

	public Integer getBusinessPlace() {
		return businessPlace;
	}

	public void setBusinessPlace(Integer businessPlace) {
		this.businessPlace = businessPlace;
	}

	public Long getTotalEmployees() {
		return totalEmployees;
	}

	public void setTotalEmployees(Long totalEmployees) {
		this.totalEmployees = totalEmployees;
	}

	public Integer getRatioOfInvestments() {
		return ratioOfInvestments;
	}

	public void setRatioOfInvestments(Integer ratioOfInvestments) {
		this.ratioOfInvestments = ratioOfInvestments;
	}

	public BigDecimal getMonthOfProfit() {
		return monthOfProfit;
	}

	public void setMonthOfProfit(BigDecimal monthOfProfit) {
		this.monthOfProfit = monthOfProfit;
	}

	public Integer getProductType() {
		return productType;
	}

	public void setProductType(Integer productType) {
		this.productType = productType;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Company getCompanyImp() {
		return companyImp;
	}

	public void setCompanyImp(Company companyImp) {
		this.companyImp = companyImp;
	}
	private Integer topEducation;
	private Integer homeCondition;
	private Double monthIncome;
	private Integer isHaveCarLoan;
	private Integer loanSize;
	private Integer unitIndustryCategory;
	public Integer getTopEducation() {
		return topEducation;
	}

	public void setTopEducation(Integer topEducation) {
		this.topEducation = topEducation;
	}

	public Integer getHomeCondition() {
		return homeCondition;
	}

	public void setHomeCondition(Integer homeCondition) {
		this.homeCondition = homeCondition;
	}

	public Double getMonthIncome() {
		return monthIncome;
	}

	public void setMonthIncome(Double monthIncome) {
		this.monthIncome = monthIncome;
	}

	public Integer getIsHaveCarLoan() {
		return isHaveCarLoan;
	}

	public void setIsHaveCarLoan(Integer isHaveCarLoan) {
		this.isHaveCarLoan = isHaveCarLoan;
	}

	public Integer getLoanSize() {
		return loanSize;
	}

	public void setLoanSize(Integer loanSize) {
		this.loanSize = loanSize;
	}

	public Integer getUnitIndustryCategory() {
		return unitIndustryCategory;
	}

	public void setUnitIndustryCategory(Integer unitIndustryCategory) {
		this.unitIndustryCategory = unitIndustryCategory;
	}

}