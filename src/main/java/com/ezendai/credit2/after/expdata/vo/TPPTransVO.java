package com.ezendai.credit2.after.expdata.vo;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */

@XmlRootElement(name = "trans")
@XmlAccessorType(XmlAccessType.FIELD)
public class TPPTransVO  implements Serializable{
	private static final long serialVersionUID = -2723673438634198707L;
	
	@XmlElement(name = "tran")
	private List<TPPTranVO> transData = null;

	public List<TPPTranVO> getTransData() {
		return transData;
	}
	public void setTransData(List<TPPTranVO> transData) {
		this.transData = transData;
	}
}
