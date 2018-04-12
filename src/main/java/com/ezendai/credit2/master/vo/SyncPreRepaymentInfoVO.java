package com.ezendai.credit2.master.vo;

import java.util.Date;

import com.ezendai.credit2.framework.vo.BaseVO;

/**
 *  提前一次性还款信息表_第三方数据同步流水
 *  @author Ivan
 */
public class SyncPreRepaymentInfoVO extends BaseVO {
	
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
	/** 客户姓名 **/
	private String name;
	/** 身份证号 **/
	private String idNum;
	/** 借款类型 **/
	private String loanType;
	/** 异常原因 **/
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
	/**生成时间-开始时间**/
	private Date buildDateStart;
	/**生成时间-结束时间**/
	private Date buildDateEnd;
	/**请求时间-开始时间**/
	private Date sendDateStart;
	/**请求时间-结束时间**/
	private Date sendDateEnd;
	
	/** 查询时间点范围 **/
	private String [] times = new String[0];
	
	/** 交易时间 **/
	private Date tradeTime;
	
	public Date getTradeTime() {
		return tradeTime;
	}
	public void setTradeTime(Date tradeTime) {
		this.tradeTime = tradeTime;
	}
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdNum() {
		return idNum;
	}
	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}
	public String getLoanType() {
		return loanType;
	}
	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}
	public String[] getTimes() {
		return times;
	}
	public void setTimes(String[] times) {
		this.times = times;
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
