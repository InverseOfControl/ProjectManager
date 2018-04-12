/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.after.model;

import java.util.Date;

import com.ezendai.credit2.framework.model.BaseModel;

/**
 * <pre>
 * 报盘文件表
 * </pre>
 *
 * @author zhangshihai
 * @version $Id: OfferFile.java, v 0.1 2014年12月11日 上午11:33:11 00221921 Exp $
 */
public class OfferFile extends BaseModel{
	private static final long serialVersionUID = -5493788680377090790L;

	/** 原始文件名 */
	private String originalName;
	
	/** 文件名 */
	private String name;
	
	/** 创建日期 */
	private Date createdDate;
	
	/** 类型 */
	private Integer type;
	
	/** 备注 */
	private String remark;

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
