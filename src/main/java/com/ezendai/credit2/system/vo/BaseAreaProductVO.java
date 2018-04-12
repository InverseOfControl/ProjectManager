package com.ezendai.credit2.system.vo;

import com.ezendai.credit2.framework.vo.BaseVO;

/**
 * 
 * <pre>
 * 
 * </pre>
 *
 * @author 00221027
 * @version $Id: BaseAreaProductVO.java, v 0.1 2015-4-10 下午05:36:50 00221027 Exp $
 */
public class BaseAreaProductVO extends BaseVO{

	/**  */
	private static final long serialVersionUID = 7662013681823311034L;
	
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
