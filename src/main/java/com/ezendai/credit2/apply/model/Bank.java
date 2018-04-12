package com.ezendai.credit2.apply.model;

import java.util.Date;

import com.ezendai.credit2.framework.model.BaseModel;

public class Bank extends BaseModel {
    /**
     * <pre>
     * 
     * </pre>
     */
    private static final long serialVersionUID = 9057032159489764721L;

    private Long id;

    private String bankName;

    private String fullName;

    private String bankCode;

    private Long creatorId;

    private String creator;

    private Date createdTime;

    private Long modifierId;

    private String modifier;

    private Date modifiedTime;

    private Long version;
    
    private String tppBankCode;
    
    //tpp发送类型
    private Integer tppType;
    
    private Long accountId;
    
    private Integer bankType;
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName == null ? null : fullName.trim();
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode == null ? null : bankCode.trim();
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Long getModifierId() {
        return modifierId;
    }

    public void setModifierId(Long modifierId) {
        this.modifierId = modifierId;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
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

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Integer getBankType() {
		return bankType;
	}

	public void setBankType(Integer bankType) {
		this.bankType = bankType;
	}

    
}