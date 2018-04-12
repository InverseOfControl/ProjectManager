/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.system.model;


import com.ezendai.credit2.framework.model.BaseModel;
/**
 * <pre>
 * 文字资源
 * </pre>
 *
 * @author wyj
 * @version  
 */
public class TextResource extends BaseModel{
	private static final long serialVersionUID = -5493788680377090790L;
	private String text;        
	private String code;
	private Integer type;
	private Integer status;
	private Integer language;
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getLanguage() {
		return language;
	}
	public void setLanguage(Integer language) {
		this.language = language;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
}
