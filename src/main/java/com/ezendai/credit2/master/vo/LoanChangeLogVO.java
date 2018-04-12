package com.ezendai.credit2.master.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.ezendai.credit2.framework.vo.BaseVO;

public class LoanChangeLogVO extends BaseVO{
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
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") 
    private Date operatorTime;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") 
    private Date operatorStartTime;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") 
    private Date operatorEndTime;

    private Long creatorId;

    private String creator;

    private Date createdTime;

    private Long modifierId;

    private String modifier;

    private Date modifiedTime;

    private Long version;

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

	public Date getOperatorStartTime() {
		return operatorStartTime;
	}

	public void setOperatorStartTime(Date operatorStartTime) {
		this.operatorStartTime = operatorStartTime;
	}

	public Date getOperatorEndTime() {
		return operatorEndTime;
	}

	public void setOperatorEndTime(Date operatorEndTime) {
		this.operatorEndTime = operatorEndTime;
	}
}