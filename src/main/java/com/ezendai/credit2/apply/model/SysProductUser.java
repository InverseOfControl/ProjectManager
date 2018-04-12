package com.ezendai.credit2.apply.model;

import com.ezendai.credit2.framework.model.BaseModel;

public class SysProductUser extends BaseModel {

	private static final long serialVersionUID = -1545635363713557659L;

	/** 产品ID */
	private Long productId;

	/** 用户ID */
	private Long userId;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}