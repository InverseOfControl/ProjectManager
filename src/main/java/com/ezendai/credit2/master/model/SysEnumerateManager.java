package com.ezendai.credit2.master.model;

import com.ezendai.credit2.framework.model.BaseModel;

/**
 * 
 * @Description: 枚举表
 * @author 张宜超
 * @date 2016年2月2日
 */
public class SysEnumerateManager extends BaseModel{

	
	private static final long serialVersionUID = -2972207069237655869L;

	private Long id;
	/**数据类型**/
	private String enumType;
	/**数据代码**/
	private Integer enumCode;
	/**数据值**/
	private String enumValue;
	/**版本**/
	private Integer enumversion;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEnumType() {
		return enumType;
	}

	public void setEnumType(String enumType) {
		this.enumType = enumType;
	}

	public Integer getEnumCode() {
		return enumCode;
	}

	public void setEnumCode(Integer enumCode) {
		this.enumCode = enumCode;
	}

	public String getEnumValue() {
		return enumValue;
	}

	public void setEnumValue(String enumValue) {
		this.enumValue = enumValue;
	}

	public Integer getEnumversion() {
		return enumversion;
	}

	public void setEnumversion(Integer enumversion) {
		this.enumversion = enumversion;
	}
	
	
	
}
