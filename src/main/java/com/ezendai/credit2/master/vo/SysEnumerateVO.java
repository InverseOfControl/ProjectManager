package com.ezendai.credit2.master.vo;

import com.ezendai.credit2.framework.vo.BaseVO;

/**
 * 枚举VO模型
 * 
 * @author chenqi
 * @version 1.0, 2014-06-23
 * @since 1.0
 */
public class SysEnumerateVO extends BaseVO {
	private static final long serialVersionUID = 6612348494681539045L;

	/** 数据类型 */
	private String enumType;
	/** 数据值 */
	private String enumValue;
	/** 数据代码 */
	private Integer enumCode;
	/** 父类 */
	private Long  parentId;
 
	private String  parentIdStr;
 

	public String getParentIdStr() {
		return parentIdStr;
	}

	public void setParentIdStr(String parentIdStr) {
		this.parentIdStr = parentIdStr;
	}

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
