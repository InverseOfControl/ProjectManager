package com.ezendai.credit2.master.model;

import com.ezendai.credit2.framework.model.BaseModel;

public class UserAreaManager extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8832468073976262836L;
	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * 区域id
	 */
	private Long baseAreaId;
	/**
	 * 区域类型
	 */
	private String baseAreaType;
	/**
	 * 是否删除，0否1是
	 */
	private Long isDeleted;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getBaseAreaId() {
		return baseAreaId;
	}
	public void setBaseAreaId(Long baseAreaId) {
		this.baseAreaId = baseAreaId;
	}
	public String getBaseAreaType() {
		return baseAreaType;
	}
	public void setBaseAreaType(String baseAreaType) {
		this.baseAreaType = baseAreaType;
	}
	public Long getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Long isDeleted) {
		this.isDeleted = isDeleted;
	}
	
}
