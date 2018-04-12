package com.ezendai.credit2.master.vo;

import java.math.BigDecimal;

import com.ezendai.credit2.framework.vo.BaseVO;

/**
 * 
 * @Description: 枚举VO前端传参数用
 * @author 张宜超
 * @date 2016年2月2日
 */
public class SysEnumerateManagerVO extends BaseVO{

	private static final long serialVersionUID = 2337840368413065035L;


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
