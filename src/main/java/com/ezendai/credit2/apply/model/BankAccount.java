/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.apply.model;

import com.ezendai.credit2.framework.model.BaseModel;

/**
 * <pre>
 * 銀行账户信息
 * </pre>
 *
 * @author xiaoxiong
 * @version $Id: BankAccount.java, v 0.1 2014年6月23日 下午4:54:59 xiaoxiong Exp $
 */
public class BankAccount extends BaseModel {
    private static final long serialVersionUID = -7766910906102346948L;
    private Long id;
    private String            account;
	/**户名 */
	private String 			  accountName;
    private String            bankName;                                //银行名称
    private String            branchName;                              //支行名称
    private Integer           status           = 1;                    //1-->启用    0-->禁用
    private Integer           cardType   ;                    //1 对公;2 对私';

    private Long bankId;
    private Bank              bank;
    private Integer accountAuthType;
   
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public Integer getCardType() {
		return cardType;
	}

	public void setCardType(Integer cardType) {
		this.cardType = cardType;
	}

	public Integer getAccountAuthType() {
		return accountAuthType;
	}

	public void setAccountAuthType(Integer accountAuthType) {
		this.accountAuthType = accountAuthType;
	}
    
}
