package com.ezendai.credit2.master.model;

import com.ezendai.credit2.framework.model.BaseModel;

public class ProductTypes extends BaseModel{

	private static final long serialVersionUID = 8616498363931220151L;

	/**产品类型**/
	private Integer productType;
	/**产品类型名称**/
	private String productTypeName;
	public Integer getProductType() {
		return productType;
	}
	public void setProductType(Integer productType) {
		this.productType = productType;
	}
	public String getProductTypeName() {
		return productTypeName;
	}
	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}
	
	
}
