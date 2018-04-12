/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.after.vo;

import java.util.Date;

import com.ezendai.credit2.framework.vo.BaseVO;

/**
 * <pre>
 * 报盘文件
 * </pre>
 *
 * @author zhangshihai
 * @version $Id: OfferFileVO.java, v 0.1 2014年12月11日 上午11:40:13 00221921 Exp $
 */
public class OfferFileVO extends BaseVO{
	private static final long serialVersionUID = 2265557225602124272L;

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
