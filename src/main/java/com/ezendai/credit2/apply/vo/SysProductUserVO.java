package com.ezendai.credit2.apply.vo;

import java.util.List;

import com.ezendai.credit2.framework.vo.BaseVO;

public class SysProductUserVO extends BaseVO {

	private static final long serialVersionUID = 7901398064134047191L;

	/** 产品ID */
	private Long productId;

	/** 用户ID */
	private Long userId;
	
	/** 批量移除集合 **/
	public List<Long> removeProductIdList;
	/** 批量新增集合 **/
	public List<Long> addProductIdList;

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

	public List<Long> getRemoveProductIdList() {
		return removeProductIdList;
	}

	public void setRemoveProductIdList(List<Long> removeProductIdList) {
		this.removeProductIdList = removeProductIdList;
	}

	public List<Long> getAddProductIdList() {
		return addProductIdList;
	}

	public void setAddProductIdList(List<Long> addProductIdList) {
		this.addProductIdList = addProductIdList;
	}
	
	

}