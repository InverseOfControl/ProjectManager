package com.ezendai.credit2.master.model;

import com.ezendai.credit2.framework.model.BaseModel;

/**
 * 枚举模型
 * 
 * @author chenqi
 * @version 1.0, 2014-06-23
 * @since 1.0
 */
public class SysEnumerate extends BaseModel {

	private static final long serialVersionUID = 3314305043921784657L;
	/** 数据类型 */
	private String enumType;
	/** 数据值 */
	private String enumValue;
	/** 数据代码 */
	private Integer enumCode;
	
	/** parent_id */
	private Long parentId;

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getEnumType() {
		return enumType;
	}

	public void setEnumType(String enumType) {
		this.enumType = enumType;
	}

	public String getEnumValue() {
		return enumValue;
	}

	public void setEnumValue(String enumValue) {
		this.enumValue = enumValue;
	}

	public Integer getEnumCode() {
		return enumCode;
	}

	public void setEnumCode(Integer enumCode) {
		this.enumCode = enumCode;
	}

}
