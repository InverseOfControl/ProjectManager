package com.ezendai.credit2.system.model;

import com.ezendai.credit2.framework.model.BaseModel;

/**
 * 
 * <pre>
 * 基础区域产品关系表
 * </pre>
 *
 * @author 00221027
 * @version $Id: BaseAreaProduct.java, v 0.1 2015-4-10 下午05:33:09 00221027 Exp $
 */
public class BaseAreaProduct extends BaseModel{

	/**  */
	private static final long serialVersionUID = -7176303441698145417L;
	
	/**
	 * 区域ID
	 */
	private Long areaId;
	
	/**
	 * 产品ID
	 */
	private Integer productId;

	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

}
