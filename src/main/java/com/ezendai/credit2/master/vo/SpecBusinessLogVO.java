/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.master.vo;

import com.ezendai.credit2.framework.vo.BaseVO;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author zhangshihai
 * @version $Id: SpecBusinessLogVO.java, v 0.1 2014年12月11日 下午1:36:01 00221921 Exp $
 */
public class SpecBusinessLogVO extends BaseVO{
	private static final long serialVersionUID = -6022884809335958903L;
	
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
