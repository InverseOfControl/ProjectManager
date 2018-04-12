package com.ezendai.credit2.after.model;

import java.util.List;

import com.ezendai.credit2.audit.model.PersonContacterAdditional;
import com.ezendai.credit2.audit.model.RepaymentPlan;
import com.ezendai.credit2.framework.model.BaseModel;

public class CollectionExcel extends BaseModel {
	private static final long serialVersionUID = -5200601342962759617L;
	
	//债务总额
	private String repayAllAmount;
	//逾期时间
	private String overdueNum;
	//开户行
	private String bankName;
	//提现卡号	
	private String cardNum;
	//抵押类型
	private String mortgageType;	
	//借款期限
    private String requestTime;	
    //逾期起始日期
    private String overdueStartDate;	
    //合同金额
    private String contractAmount;	
    //剩余本金
    private String residualPactMoney;	
    //逾期本金
    private String principal;	
    //逾期利息（元）
    private String interest;	
    //违约金（元）
    private String fine;	
    //	罚息（元）
    private String repayInterest;	 
    //债务人姓名	
    private String name;	
    //所在地（城市）
    private String city;	
    //身份证号
    private String idNum;	 
    //性别
    private String sex;	
    //婚姻状况	
    private String married;	
    // 年龄
    private String age;	
    //债务人手机号List
    private List<PersonContacterAdditional> phoneList;	 
    //家庭住址
    private String address;	
    //户籍住址
    private String placeDomicile;	
    //  工作单位		
    private String company;	
    //单位地址
    private String companyAddress;	
    //单位电话
    private String companyTel;	
    //联系人List  	相关联系人姓名	相关联系人关系	相关联系人电话
    private List<CasesPerson> contactserList;	
    //委案前末次还款金额（元）
    private String lastRepayAmount;
    //委案前末次还款时间
    private String lastFactReturnDate;
    //还款List 还款时间	还款金额（元） 
    private List<RepaymentPlan>  rpList;
    public String getRepayAllAmount() {
		return repayAllAmount;
	}
	public void setRepayAllAmount(String repayAllAmount) {
		this.repayAllAmount = repayAllAmount;
	}
	public String getOverdueNum() {
		return overdueNum;
	}
	public void setOverdueNum(String overdueNum) {
		this.overdueNum = overdueNum;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getCardNum() {
		return cardNum;
	}
	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}
	public String getMortgageType() {
		return mortgageType;
	}
	public void setMortgageType(String mortgageType) {
		this.mortgageType = mortgageType;
	}
	public String getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}
	public String getOverdueStartDate() {
		return overdueStartDate;
	}
	public void setOverdueStartDate(String overdueStartDate) {
		this.overdueStartDate = overdueStartDate;
	}
	public String getContractAmount() {
		return contractAmount;
	}
	public void setContractAmount(String contractAmount) {
		this.contractAmount = contractAmount;
	}
	public String getResidualPactMoney() {
		return residualPactMoney;
	}
	public void setResidualPactMoney(String residualPactMoney) {
		this.residualPactMoney = residualPactMoney;
	}
	public String getPrincipal() {
		return principal;
	}
	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	public String getInterest() {
		return interest;
	}
	public void setInterest(String interest) {
		this.interest = interest;
	}
	public String getFine() {
		return fine;
	}
	public void setFine(String fine) {
		this.fine = fine;
	}
	public String getRepayInterest() {
		return repayInterest;
	}
	public void setRepayInterest(String repayInterest) {
		this.repayInterest = repayInterest;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getIdNum() {
		return idNum;
	}
	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getMarried() {
		return married;
	}
	public void setMarried(String married) {
		this.married = married;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public List<PersonContacterAdditional> getPhoneList() {
		return phoneList;
	}
	public void setPhoneList(List<PersonContacterAdditional> phoneList) {
		this.phoneList = phoneList;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPlaceDomicile() {
		return placeDomicile;
	}
	public void setPlaceDomicile(String placeDomicile) {
		this.placeDomicile = placeDomicile;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getCompanyAddress() {
		return companyAddress;
	}
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	public String getCompanyTel() {
		return companyTel;
	}
	public void setCompanyTel(String companyTel) {
		this.companyTel = companyTel;
	}
	public List<CasesPerson> getContactserList() {
		return contactserList;
	}
	public void setContactserList(List<CasesPerson> contactserList) {
		this.contactserList = contactserList;
	}
	public String getLastRepayAmount() {
		return lastRepayAmount;
	}
	public void setLastRepayAmount(String lastRepayAmount) {
		this.lastRepayAmount = lastRepayAmount;
	}
	public String getLastFactReturnDate() {
		return lastFactReturnDate;
	}
	public void setLastFactReturnDate(String lastFactReturnDate) {
		this.lastFactReturnDate = lastFactReturnDate;
	}
	public List<RepaymentPlan> getRpList() {
		return rpList;
	}
	public void setRpList(List<RepaymentPlan> rpList) {
		this.rpList = rpList;
	}
}