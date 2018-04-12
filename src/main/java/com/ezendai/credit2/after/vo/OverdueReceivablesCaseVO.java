package com.ezendai.credit2.after.vo;

import java.util.Date;

import com.ezendai.credit2.framework.vo.BaseVO;

public class OverdueReceivablesCaseVO extends BaseVO{
    /**
	 * 
	 */
	private static final long serialVersionUID = -7260470206585523197L;

	/**
	 * 
	 */

   private Long id;

   

 

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private Long version;

    private Long loanId;

    private Integer caseFrom;

    private String caseNum;

    private Integer caseState;

    private Integer caseType;

    private Date caseEndDate;

    private String caseMemo;

    private Long operatorId;

    private Date overdueDate;
    private String overdueDateStr;

    

	public String getOverdueDateStr() {
		return overdueDateStr;
	}

	public void setOverdueDateStr(String overdueDateStr) {
		this.overdueDateStr = overdueDateStr;
	}

	private Integer transferType;

    private Date transferDate;

    private String creator;

    private Date createdTime;

    private Long creatorId;

    private Long modifierId;

    private String modifier;

    private Date modifiedTime;

    

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    public Integer getCaseFrom() {
        return caseFrom;
    }

    public void setCaseFrom(Integer caseFrom) {
        this.caseFrom = caseFrom;
    }

    public String getCaseNum() {
        return caseNum;
    }

    public void setCaseNum(String caseNum) {
        this.caseNum = caseNum;
    }

    public Integer getCaseState() {
        return caseState;
    }

    public void setCaseState(Integer caseState) {
        this.caseState = caseState;
    }

    public Integer getCaseType() {
        return caseType;
    }

    public void setCaseType(Integer caseType) {
        this.caseType = caseType;
    }

    public Date getCaseEndDate() {
        return caseEndDate;
    }

    public void setCaseEndDate(Date caseEndDate) {
        this.caseEndDate = caseEndDate;
    }

    public String getCaseMemo() {
        return caseMemo;
    }

    public void setCaseMemo(String caseMemo) {
        this.caseMemo = caseMemo;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public Date getOverdueDate() {
        return overdueDate;
    }

    public void setOverdueDate(Date overdueDate) {
        this.overdueDate = overdueDate;
    }

    public Integer getTransferType() {
        return transferType;
    }

    public void setTransferType(Integer transferType) {
        this.transferType = transferType;
    }

    public Date getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
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

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
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
}