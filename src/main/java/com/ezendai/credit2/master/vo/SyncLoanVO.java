package com.ezendai.credit2.master.vo;

import java.util.Date;

import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.framework.vo.BaseVO;

public class SyncLoanVO extends BaseVO{
	private static final long serialVersionUID = -668444156095857158L;
	/** 同步流水编号 **/
	private Long id;
	/** 借款编号 **/
	private Long loanId;
	/** 第三方机构编号 **/
	private String companyNo = "002";
	/** 第三方机构名称 **/
	private String companyName = "证大";
	/** 债权编号 **/
	private String contractNo;
	/** 客户姓名 **/
	private String name;
	/** 身份证号 **/
	private String idNum;
	/** 职业类型 **/
	private String professionType;
	/** 借款类型 **/
	private String loanType;
	/** 借款金额/合同金额 **/
	private Double pactMoney;
	/** 借款期限 **/
	private Long pactTime;
	/** 债权到期日/还款到期日 **/
	private Date endRepayDate;
	/** 放款日期 **/
	private Date SignDate;
	/** 用途 **/
	private String purpose;
	/** 月还款能力 **/
	private Double maxRepayAmount;
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
	/** 查询时间点范围 **/
	private String [] times = new String[0];
	/**生成时间-开始时间**/
	private Date buildDateStart;
	/**生成时间-结束时间**/
	private Date buildDateEnd;
	/**请求时间-开始时间**/
	private Date sendDateStart;
	/**请求时间-结束时间**/
	private Date sendDateEnd;
	
	
	/** 分页PG **/
	private Pager pager = new Pager();
	
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
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
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
	public String getProfessionType() {
		return professionType;
	}
	public void setProfessionType(String professionType) {
		this.professionType = professionType;
	}
	public String getLoanType() {
		return loanType;
	}
	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}
	public Double getPactMoney() {
		return pactMoney;
	}
	public void setPactMoney(Double pactMoney) {
		this.pactMoney = pactMoney;
	}
	public Long getPactTime() {
		return pactTime;
	}
	public void setPactTime(Long pactTime) {
		this.pactTime = pactTime;
	}
	public Date getEndRepayDate() {
		return endRepayDate;
	}
	public void setEndRepayDate(Date endRepayDate) {
		this.endRepayDate = endRepayDate;
	}
	public Date getSignDate() {
		return SignDate;
	}
	public void setSignDate(Date signDate) {
		SignDate = signDate;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public Double getMaxRepayAmount() {
		return maxRepayAmount;
	}
	public void setMaxRepayAmount(Double maxRepayAmount) {
		this.maxRepayAmount = maxRepayAmount;
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
	public String[] getTimes() {
		return times;
	}
	public void setTimes(String[] times) {
		this.times = times;
	}
	public Pager getPager() {
		return pager;
	}
	public void setPager(Pager pager) {
		this.pager = pager;
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
