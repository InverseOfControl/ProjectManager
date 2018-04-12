package com.ezendai.credit2.report.model;

import java.math.BigDecimal;
import java.util.Date;

import com.ezendai.credit2.framework.model.BaseModel;

public class OverdueMsgLog extends BaseModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -109030546681370383L;

	/**主键*/
	private Long id;

    private Long loanId;

    private Long repaymentId;

    private String name;

    private String idNum;

    private String mobile;

    private Long templetId;

    private BigDecimal repayAmount;

    private Integer curTime;

    private String productName;
    
    private String productType;

    private Integer sendtimes;

    private Integer status;

    private String msg;

    private Date buildDate;

    private Date sendDate;

    private Date returnDate;

    private Long version;
    
    private Date repayDay;
    
    private String sendDetails;

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

	public Long getRepaymentId() {
		return repaymentId;
	}

	public void setRepaymentId(Long repaymentId) {
		this.repaymentId = repaymentId;
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Long getTempletId() {
		return templetId;
	}

	public void setTempletId(Long templetId) {
		this.templetId = templetId;
	}

	public BigDecimal getRepayAmount() {
		return repayAmount;
	}

	public void setRepayAmount(BigDecimal repayAmount) {
		this.repayAmount = repayAmount;
	}

	public Integer getCurTime() {
		return curTime;
	}

	public void setCurTime(Integer curTime) {
		this.curTime = curTime;
	}

	

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public Integer getSendtimes() {
		return sendtimes;
	}

	public void setSendtimes(Integer sendtimes) {
		this.sendtimes = sendtimes;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
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

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Date getRepayDay() {
		return repayDay;
	}

	public void setRepayDay(Date repayDay) {
		this.repayDay = repayDay;
	}

	public String getSendDetails() {
		return sendDetails;
	}

	public void setSendDetails(String sendDetails) {
		this.sendDetails = sendDetails;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
    
}
