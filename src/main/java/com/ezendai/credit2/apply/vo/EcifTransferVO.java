package com.ezendai.credit2.apply.vo;

import java.util.Date;

import com.ezendai.credit2.framework.vo.BaseVO;

public class EcifTransferVO extends BaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1683261114992901896L;

	private String ecifId;
	private Long personId;
	private String status;
	private String ecifReq;
	private String ecifRes;
	private String interfaceType;
	
	private String personName;
	private String  personIdnum;
	private Date createdTimeAfter;
	private Date createdTimeBefore;
	
	private Long productId;
	/**产品类型**/
	private Integer productType;
	
	private String statusMessage;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
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
	public Date getCreatedTimeAfter() {
		return createdTimeAfter;
	}
	public void setCreatedTimeAfter(Date createdTimeAfter) {
		this.createdTimeAfter = createdTimeAfter;
	}
	public Date getCreatedTimeBefore() {
		return createdTimeBefore;
	}
	public void setCreatedTimeBefore(Date createdTimeBefore) {
		this.createdTimeBefore = createdTimeBefore;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public String getPersonIdnum() {
		return personIdnum;
	}
	public void setPersonIdnum(String personIdnum) {
		this.personIdnum = personIdnum;
	}
	public String getInterfaceType() {
		return interfaceType;
	}
	public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}
	public String getEcifId() {
		return ecifId;
	}
	public void setEcifId(String ecifId) {
		this.ecifId = ecifId;
	}
	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getEcifReq() {
		return ecifReq;
	}
	public void setEcifReq(String ecifReq) {
		this.ecifReq = ecifReq;
	}
	public String getEcifRes() {
		return ecifRes;
	}
	public void setEcifRes(String ecifRes) {
		this.ecifRes = ecifRes;
	}
	
	
	
	
}
