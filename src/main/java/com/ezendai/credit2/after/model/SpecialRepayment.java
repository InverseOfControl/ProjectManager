package com.ezendai.credit2.after.model;

import java.math.BigDecimal;
import java.util.Date;

import com.ezendai.credit2.apply.model.Vehicle;
import com.ezendai.credit2.framework.model.BaseModel;

/**
 * <pre>
 * 特殊还款表
 * </pre>
 * @author zhangshihai
 * @Description:TODO
 */
public class SpecialRepayment extends BaseModel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8675087255828191307L;
	/** 借款Id */
	private Long loanId;
	/** 申请日期 */
	private Date requestDate;
	/** 申请类型 */
	private Integer type;
	/** 流程状态 */
	private Integer status;
	/** 申请人 */
	private String proposer;
	/** 申请金额 */
	private BigDecimal amount;
	/** 备注 */
	private String remark;
	/** 营业网点ID */
	private Long salesDeptId;
	/**是否需要报盘 */
	private Integer needOffer;
	/**是否展期申请标志*/
	private Integer extensionFlag;
	/**姓名*/
	private String name;
	/**身份证*/
    private String idnum;
    /**合同金额*/
    private BigDecimal pactMoney;
    /**客服经理id*/
    private Long crmId;
    /**产品类型*/
    private Long productId;
    /**借款类型*/
    private Integer productType;
    /**手机号*/
    private String mobilePhone;
    /**借款期限*/
    private Long time;
	/** 借款状态 */
	private Integer loanStatus;
	/**展期时间*/
	private Integer extensionTime;
    /**当前期数*/
	private Integer currNum;
    
	 /**审批期数*/
	private Integer auditTime;	
	 /**车辆信息*/
	private Vehicle vehicle;
	 /**批量操作标识*/
	private String plFlag;
	
	public String getPlFlag() {
		return plFlag;
	}
	public void setPlFlag(String plFlag) {
		this.plFlag = plFlag;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
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
	public BigDecimal getPactMoney() {
		return pactMoney;
	}
	public void setPactMoney(BigDecimal pactMoney) {
		this.pactMoney = pactMoney;
	}
	public Long getCrmId() {
		return crmId;
	}
	public void setCrmId(Long crmId) {
		this.crmId = crmId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Integer getProductType() {
		return productType;
	}
	public void setProductType(Integer productType) {
		this.productType = productType;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public Long getLoanId() {
		return loanId;
	}
	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}
	public Date getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getProposer() {
		return proposer;
	}
	public void setProposer(String proposer) {
		this.proposer = proposer;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getSalesDeptId() {
		return salesDeptId;
	}
	public void setSalesDeptId(Long salesDeptId) {
		this.salesDeptId = salesDeptId;
	}
	public Integer getNeedOffer() {
		return needOffer;
	}
	public void setNeedOffer(Integer needOffer) {
		this.needOffer = needOffer;
	}
	public Integer getExtensionFlag() {
		return extensionFlag;
	}
	public void setExtensionFlag(Integer extensionFlag) {
		this.extensionFlag = extensionFlag;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public Integer getLoanStatus() {
		return loanStatus;
	}
	public void setLoanStatus(Integer loanStatus) {
		this.loanStatus = loanStatus;
	}
	public Integer getExtensionTime() {
		return extensionTime;
	}
	public void setExtensionTime(Integer extensionTime) {
		this.extensionTime = extensionTime;
	}
	public Integer getCurrNum() {
		return currNum;
	}
	public void setCurrNum(Integer currNum) {
		this.currNum = currNum;
	}
	public Integer getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(Integer auditTime) {
		this.auditTime = auditTime;
	}
	public Vehicle getVehicle() {
		return vehicle;
	}
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	
}
