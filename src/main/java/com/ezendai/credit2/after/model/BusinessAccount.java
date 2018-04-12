/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.after.model;

import java.math.BigDecimal;
import java.util.Date;

import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.model.Person;
import com.ezendai.credit2.framework.model.BaseModel;
import com.ezendai.credit2.system.model.SysUser;

/**
 * <pre>
 * 对公还款
 * </pre>
 *
 * @author zhangshihai
 * @version $Id: BusinessAccount.java, v 0.1 2014年12月11日 上午10:31:18 00221921 Exp $
 */
public class BusinessAccount  extends BaseModel{
	private static final long serialVersionUID = 8883208474694670699L;
	
	/** 借款ID */
	private Long loanId;
	/** 本方账号 */
	private String firstAccount;
	/** 对方账号 */
	private String secondAccount;
	/** 对方单位名称 */
	private String secondCompany;
	/** 对方行号 */
	private String secondBank;
	/** 交易日期 */
	private Date repayDate;
	/** 交易时间 */
	private String repayTime;
	/** 贷方发生金额 */
	private BigDecimal amount;
	/** 凭证号 */
	private String voucherNo;
	/** 借/贷类型 */
	private Integer type;
	/** 用途 */
	private String purpose;
	/** 摘要 */
	private String remark;
	/** 附言 */
	private String comments;
	/** 认领时间 */
	private Date recTime;
	/** 认领人ID */
	private Long recOperatorId;
	/** 状态（未认领 已认领 已导出 渠道确认） */
	private Integer status;
	//认领人
	private SysUser sysUser;
	//借款
	private Loan loan;
	//借款人
	private Person person;
	//管理客服
	private SysUser manager;
	//经理
	private SysUser crm;
	
	/** 借款人名字*/
	private String personName;
	
	
	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public String getFirstAccount() {
		return firstAccount;
	}

	public void setFirstAccount(String firstAccount) {
		this.firstAccount = firstAccount;
	}

	public String getSecondAccount() {
		return secondAccount;
	}

	public void setSecondAccount(String secondAccount) {
		this.secondAccount = secondAccount;
	}

	public String getSecondCompany() {
		return secondCompany;
	}

	public void setSecondCompany(String secondCompany) {
		this.secondCompany = secondCompany;
	}

	public String getSecondBank() {
		return secondBank;
	}

	public void setSecondBank(String secondBank) {
		this.secondBank = secondBank;
	}

	public Date getRepayDate() {
		return repayDate;
	}

	public void setRepayDate(Date repayDate) {
		this.repayDate = repayDate;
	}

	public String getRepayTime() {
		return repayTime;
	}

	public void setRepayTime(String repayTime) {
		this.repayTime = repayTime;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getVoucherNo() {
		return voucherNo;
	}

	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Date getRecTime() {
		return recTime;
	}

	public void setRecTime(Date recTime) {
		this.recTime = recTime;
	}

	public Long getRecOperatorId() {
		return recOperatorId;
	}

	public void setRecOperatorId(Long recOperatorId) {
		this.recOperatorId = recOperatorId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public SysUser getSysUser() {
		return sysUser;
	}

	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}

	public Loan getLoan() {
		return loan;
	}

	public void setLoan(Loan loan) {
		this.loan = loan;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public SysUser getManager() {
		return manager;
	}

	public void setManager(SysUser manager) {
		this.manager = manager;
	}

	public SysUser getCrm() {
		return crm;
	}

	public void setCrm(SysUser crm) {
		this.crm = crm;
	}
	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

}
