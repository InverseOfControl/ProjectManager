package com.ezendai.credit2.sign.lcb.model;

public class LcbModel{

	private static final long serialVersionUID = -485773328890386623L;
	
	private long loanId; //借款ID
	private long personId; //用户ID
	private String name; //用户姓名
	private String idnum; //用户省份证号
	private String mobilePhone; //用户常用手机号
	private String lcbAuthStatus; //捞财宝认证状态（10-手机一致性校验；20-注册；30-实名；40-绑卡）
	private String lcbVerifyCode; //验证码
	private String customerId; //捞财宝用户编码
	private String bankCode; //银行编码
	private String bankCard; //银行卡号
	private String lcbBorrowNo; //借款编号
	private String lcbBorrowStatus; //捞财宝流标、借款状态
	private String bankAccount; //已绑定银行卡号
	
	public long getLoanId() {
		return loanId;
	}
	public void setLoanId(long loanId) {
		this.loanId = loanId;
	}
	public long getPersonId() {
		return personId;
	}
	public void setPersonId(long personId) {
		this.personId = personId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdnum() {
		return idnum;
	}
	public void setIdnum(String idnum) {
		this.idnum = idnum;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getLcbVerifyCode() {
		return lcbVerifyCode;
	}
	public void setLcbVerifyCode(String lcbVerifyCode) {
		this.lcbVerifyCode = lcbVerifyCode;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getBankCard() {
		return bankCard;
	}
	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}
	public String getLcbBorrowNo() {
		return lcbBorrowNo;
	}
	public void setLcbBorrowNo(String lcbBorrowNo) {
		this.lcbBorrowNo = lcbBorrowNo;
	}
	public String getLcbBorrowStatus() {
		return lcbBorrowStatus;
	}
	public void setLcbBorrowStatus(String lcbBorrowStatus) {
		this.lcbBorrowStatus = lcbBorrowStatus;
	}
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public String getLcbAuthStatus() {
		return lcbAuthStatus;
	}
	public void setLcbAuthStatus(String lcbAuthStatus) {
		this.lcbAuthStatus = lcbAuthStatus;
	}
}
