/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.after.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.ezendai.credit2.framework.vo.BaseVO;

/**
 * <pre>
 * 对公还款VO
 * </pre>
 *
 * @author zhangshihai
 * @version $Id: BusinessAccountVO.java, v 0.1 2014年12月11日 上午10:58:46 00221921 Exp $
 */
public class BusinessAccountVO extends BaseVO{
	private static final long serialVersionUID = 1076925573157401189L;

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
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") 
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
	/** 认领人工号 */
	private String recOperatorNo;
	/** 状态（未认领 已认领 已导出 渠道确认） */
	private Integer status;
	/** 认领时间开始 */
	private Date recTimeStart;
	/** 认领时间结束 */
	private Date recTimeEnd;
	//营业网点ID
	private Long salesDeptId;
	//姓名
	private String personName;
	//手机号码
	private String mobilePhone;
	//身份证号码
	private String idnum;
	//营业网点列表
	private List<Long> deptList;
	//产品列表
	private List<Long> productIds;	
	//状态列表
	private List<Integer> statusList;
	/** 认领人ID是否清空 */
	private boolean recOperatorIdIsNull;	
	/** 认领时间开始 参数表*/
	private String recStartTime;
	/** 认领时间结束 参数表*/
	private String recEndTime;
	/** 认领系统*/
	private String system;
	
	
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
	}
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
	public Date getRecTimeStart() {
		return recTimeStart;
	}
	public void setRecTimeStart(Date recTimeStart) {
		this.recTimeStart = recTimeStart;
	}
	public Date getRecTimeEnd() {
		return recTimeEnd;
	}
	public void setRecTimeEnd(Date recTimeEnd) {
		this.recTimeEnd = recTimeEnd;
	}
	public String getRecOperatorNo() {
		return recOperatorNo;
	}
	public void setRecOperatorNo(String recOperatorNo) {
		this.recOperatorNo = recOperatorNo;
	}
	public Long getSalesDeptId() {
		return salesDeptId;
	}
	public void setSalesDeptId(Long salesDeptId) {
		this.salesDeptId = salesDeptId;
	}
	public List<Long> getDeptList() {
		return deptList;
	}
	public void setDeptList(List<Long> deptList) {
		this.deptList = deptList;
	}
	public List<Long> getProductIds() {
		return productIds;
	}
	public void setProductIds(List<Long> productIds) {
		this.productIds = productIds;
	}
	public List<Integer> getStatusList() {
		return statusList;
	}
	public void setStatusList(List<Integer> statusList) {
		this.statusList = statusList;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getIdnum() {
		return idnum;
	}
	public void setIdnum(String idnum) {
		this.idnum = idnum;
	}
	public boolean isRecOperatorIdIsNull() {
		return recOperatorIdIsNull;
	}
	public void setRecOperatorIdIsNull(boolean recOperatorIdIsNull) {
		this.recOperatorIdIsNull = recOperatorIdIsNull;
	}
	public String getRecStartTime() {
		return recStartTime;
	}
	public void setRecStartTime(String recStartTime) {
		this.recStartTime = recStartTime;
	}
	public String getRecEndTime() {
		return recEndTime;
	}
	public void setRecEndTime(String recEndTime) {
		this.recEndTime = recEndTime;
	}
	
}
