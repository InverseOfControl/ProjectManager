package com.ezendai.credit2.master.vo;

import com.ezendai.credit2.framework.vo.BaseVO;

public class UserAreaManagerVO extends BaseVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4711943176202247945L;
	
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
	/**
	 * 用户名称
	 */
	
	private String userName;
	
	/**
	 * 区域名称
	 */
	private String baseAreaName;

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getBaseAreaName() {
		return baseAreaName;
	}

	public void setBaseAreaName(String baseAreaName) {
		this.baseAreaName = baseAreaName;
	}
	
	
	
	

}
