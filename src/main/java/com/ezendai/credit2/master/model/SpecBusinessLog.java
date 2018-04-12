/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.master.model;

import com.ezendai.credit2.framework.model.BaseModel;

/**
 * <pre>
 * 特定业务日志
 * </pre>
 *
 * @author zhangshihai
 * @version $Id: SpecBusinessLog.java, v 0.1 2014年12月11日 下午1:21:52 00221921 Exp $
 */
public class SpecBusinessLog extends BaseModel{
	private static final long serialVersionUID = -3427915072397472947L;
	/** 关键key id */
	private Long keyId;
	/** 类型 */
	private Integer keyType;
	/** 消息 */
	private String message;
	/** 状态 */
	private Integer flowStatus;
	
	public Long getKeyId() {
		return keyId;
	}
	public void setKeyId(Long keyId) {
		this.keyId = keyId;
	}
	public Integer getKeyType() {
		return keyType;
	}
	public void setKeyType(Integer keyType) {
		this.keyType = keyType;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Integer getFlowStatus() {
		return flowStatus;
	}
	public void setFlowStatus(Integer flowStatus) {
		this.flowStatus = flowStatus;
	}
	
}
