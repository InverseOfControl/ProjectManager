package com.ezendai.credit2.master.model;

import java.math.BigDecimal;
import java.util.Date;

import com.ezendai.credit2.framework.model.BaseModel;

public class LoanChangeLog extends BaseModel{
    /**
	 * 
	 */
	private static final long serialVersionUID = 5084263075182181269L;

	private Long id;

    private Long loanId;

    private String changeChoice;

    private String changeBefore;

    private String changeAfter;

    private Long operatorId;

    private Date operatorTime;

    private Long creatorId;

    private String creator;

    private Date createdTime;

    private Long modifierId;

    private String modifier;

    private Date modifiedTime;

    private Long version;
    
    
  private String personName;
  private String   organName;
  private String   salesDeptName;
  
	private BigDecimal  pactMoney;
	
	private String operatorName;


public String getSalesDeptName() {
	return salesDeptName;
}

public void setSalesDeptName(String salesDeptName) {
	this.salesDeptName = salesDeptName;
}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    public String getChangeChoice() {
        return changeChoice;
    }

    public void setChangeChoice(String changeChoice) {
        this.changeChoice = changeChoice;
    }

    public String getChangeBefore() {
        return changeBefore;
    }

    public void setChangeBefore(String changeBefore) {
        this.changeBefore = changeBefore;
    }

    public String getChangeAfter() {
        return changeAfter;
    }

    public void setChangeAfter(String changeAfter) {
        this.changeAfter = changeAfter;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public Date getOperatorTime() {
        return operatorTime;
    }

    public void setOperatorTime(Date operatorTime) {
        this.operatorTime = operatorTime;
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
        this.creator = creator;
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
        this.modifier = modifier;
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

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}


	public BigDecimal getPactMoney() {
		return pactMoney;
	}

	public void setPactMoney(BigDecimal pactMoney) {
		this.pactMoney = pactMoney;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
    
}