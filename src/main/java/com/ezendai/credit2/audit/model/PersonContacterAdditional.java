package com.ezendai.credit2.audit.model;

 
import java.util.Date;

 

import com.ezendai.credit2.framework.model.BaseModel;
import com.ezendai.credit2.framework.util.Pager;

public class PersonContacterAdditional extends BaseModel {
	private static final long serialVersionUID = -5200601342962759617L;
	private Long relationId;
	private String additionalTel;
	private Integer additionalType;
	private String additionalAddress;
	private String innerCollection;
	
	public String getInnerCollection() {
		return innerCollection;
	}
	public void setInnerCollection(String innerCollection) {
		this.innerCollection = innerCollection;
	}
	/**
	 * 分页PG
	 */
	private Pager pager = new Pager();
	public String getAdditionalTel() {
		return additionalTel;
	}
	public void setAdditionalTel(String additionalTel) {
		this.additionalTel = additionalTel;
	}
	public Integer getAdditionalType() {
		return additionalType;
	}
	public void setAdditionalType(Integer additionalType) {
		this.additionalType = additionalType;
	}
	public String getAdditionalAddress() {
		return additionalAddress;
	}
	public void setAdditionalAddress(String additionalAddress) {
		this.additionalAddress = additionalAddress;
	}
	public Pager getPager() {
		return pager;
	}
	public void setPager(Pager pager) {
		this.pager = pager;
	}
	public Long getRelationId() {
		return relationId;
	}
	public void setRelationId(Long relationId) {
		this.relationId = relationId;
	}
 
	 

	
	
	  
 
}