/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.apply.vo;

import java.util.ArrayList;
import java.util.List;

import com.ezendai.credit2.framework.vo.BaseVO;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author xiaoxiong
 * @version $Id: BankVO.java, v 0.1 2014年6月24日 上午8:54:14 xiaoxiong Exp $
 */
public class BankVO extends BaseVO {

    private static final long serialVersionUID = 738873798908388991L;

    private String            bankName;
    private String            bankCode;

    private List<Long>        idList           = new ArrayList<Long>();
    
    private String tppBankCode;
    
    private Integer tppType;
    
    private Integer bankType;

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public List<Long> getIdList() {
        return idList;
    }

    public void setIdList(List<Long> idList) {
        this.idList = idList;
    }

	public String getTppBankCode() {
		return tppBankCode;
	}

	public void setTppBankCode(String tppBankCode) {
		this.tppBankCode = tppBankCode;
	}

	public Integer getTppType() {
		return tppType;
	}

	public void setTppType(Integer tppType) {
		this.tppType = tppType;
	}

	public Integer getBankType() {
		return bankType;
	}

	public void setBankType(Integer bankType) {
		this.bankType = bankType;
	}

}
