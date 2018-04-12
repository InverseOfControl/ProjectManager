package com.ezendai.credit2.master.vo;

import java.util.Date;

import com.ezendai.credit2.framework.vo.BaseVO;

/**
 *  实际还款信息表_第三方数据同步流水
 *  @author Ivan
 */
public class SyncRepayInfoVO extends BaseVO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 同步流水编号 **/
	private Long id;
	/** 借款编号 **/
	private Long loanId;
	/** 第三方机构编号 **/
	private String companyNo = "002";
	/** 债权编号 **/
	private String contractNo;
	/** 当前期数 **/
	private Long curNum;
	/** 还款金额（当期本金+当期利息） **/
	private Double totalAmount;
	/** 还款方式 **/
	private String payType = "转账";
	/** 备注 **/
	private String remark;
	/** 同步状态 1.待同步 2.同步中 3.同步成功 4.同步失败 **/
	private Long status;
	/** 主要记录异常信息 **/
	private String msg;
	/** 流水数据生成时间 **/
	private Date buildDate;
	/** 发送同步请求时间 **/
	private Date sendDate;
	/** 同步结果反馈时间 **/
	private Date returnDate;
	
	/** 当前应还本金 **/
	private Double principalAmt;
	/** 当前应还利息 **/
	private Double interestAmt;
	/** 本金余额 **/
	private Double remainingPrincipal; 
	/** 一次性还款总额 **/
	private Double oneTimeRepaymentAmount;
	/**生成时间-开始时间**/
	private Date buildDateStart;
	/**生成时间-结束时间**/
	private Date buildDateEnd;
	/**请求时间-开始时间**/
	private Date sendDateStart;
	/**请求时间-结束时间**/
	private Date sendDateEnd;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getLoanId() {
		return loanId;
	}
	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}
	public String getCompanyNo() {
		return companyNo;
	}
	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public Long getCurNum() {
		return curNum;
	}
	public void setCurNum(Long curNum) {
		this.curNum = curNum;
	}
	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Date getBuildDate() {
		return buildDate;
	}
	public void setBuildDate(Date buildDate) {
		this.buildDate = buildDate;
	}
	public Date getSendDate() {
		return sendDate;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	public Date getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
	public Double getPrincipalAmt() {
		return principalAmt;
	}
	public void setPrincipalAmt(Double principalAmt) {
		this.principalAmt = principalAmt;
	}
	public Double getInterestAmt() {
		return interestAmt;
	}
	public void setInterestAmt(Double interestAmt) {
		this.interestAmt = interestAmt;
	}
	public Double getRemainingPrincipal() {
		return remainingPrincipal;
	}
	public void setRemainingPrincipal(Double remainingPrincipal) {
		this.remainingPrincipal = remainingPrincipal;
	}
	public Double getOneTimeRepaymentAmount() {
		return oneTimeRepaymentAmount;
	}
	public void setOneTimeRepaymentAmount(Double oneTimeRepaymentAmount) {
		this.oneTimeRepaymentAmount = oneTimeRepaymentAmount;
	}
	public Date getBuildDateStart() {
		return buildDateStart;
	}
	public void setBuildDateStart(Date buildDateStart) {
		this.buildDateStart = buildDateStart;
	}
	public Date getBuildDateEnd() {
		return buildDateEnd;
	}
	public void setBuildDateEnd(Date buildDateEnd) {
		this.buildDateEnd = buildDateEnd;
	}
	public Date getSendDateStart() {
		return sendDateStart;
	}
	public void setSendDateStart(Date sendDateStart) {
		this.sendDateStart = sendDateStart;
	}
	public Date getSendDateEnd() {
		return sendDateEnd;
	}
	public void setSendDateEnd(Date sendDateEnd) {
		this.sendDateEnd = sendDateEnd;
	}
}
