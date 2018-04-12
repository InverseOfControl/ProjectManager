package com.ezendai.credit2.after.expdata.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */

/**
 * <pre>
 * 
 * </pre>
 *
 * @author 00226557
 * @version $Id: Tran.java, v 0.1 2014年12月9日 下午6:35:42 00226557 Exp $
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class TPPTranVO   implements Serializable {
	private static final long serialVersionUID = -4982258696192693707L;
	
	@XmlElement(name = "requestId")
	private Long requestId;
	@XmlElement(name = "orderNo")
	private Long orderNo;
	@XmlElement(name = "returnCode")
	private String returnCode;
	@XmlElement(name = "returnInfo")
	private String returnInfo;

	public Long getRequestId() {
		return requestId;
	}
	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}
	public Long getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}
	public String getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	public String getReturnInfo() {
		return returnInfo;
	}
	public void setReturnInfo(String returnInfo) {
		this.returnInfo = returnInfo;
	}
	
}
