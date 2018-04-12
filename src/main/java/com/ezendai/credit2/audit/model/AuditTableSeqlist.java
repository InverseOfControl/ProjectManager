package com.ezendai.credit2.audit.model;

import java.math.BigDecimal;
import java.util.Date;

import com.ezendai.credit2.framework.model.BaseModel;

public class AuditTableSeqlist extends BaseModel{
    /**
	 * 
	 */
	private static final long serialVersionUID = 4406703864248106702L;

	private Long id;

    private Long loanId;

    private Short type;

    private BigDecimal seqOne;

    private BigDecimal monthAmountOne;

    private BigDecimal seqSec;

    private BigDecimal monthAmountSec;

    private BigDecimal seqThr;

    private BigDecimal monthAmountThr;

    private BigDecimal seqFour;

    private BigDecimal monthAmountFour;

    private BigDecimal seqFive;

    private BigDecimal monthAmountFive;

    private BigDecimal seqSix;

    private BigDecimal monthAmountSix;

    private String creator;

    private Date createdTime;

    private Long creatorId;

    private Long modifierId;

    private String modifier;

    private Date modifiedTime;

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

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public BigDecimal getSeqOne() {
        return seqOne;
    }

    public void setSeqOne(BigDecimal seqOne) {
        this.seqOne = seqOne;
    }

    public BigDecimal getMonthAmountOne() {
        return monthAmountOne;
    }

    public void setMonthAmountOne(BigDecimal monthAmountOne) {
        this.monthAmountOne = monthAmountOne;
    }

    public BigDecimal getSeqSec() {
        return seqSec;
    }

    public void setSeqSec(BigDecimal seqSec) {
        this.seqSec = seqSec;
    }

    public BigDecimal getMonthAmountSec() {
        return monthAmountSec;
    }

    public void setMonthAmountSec(BigDecimal monthAmountSec) {
        this.monthAmountSec = monthAmountSec;
    }

    public BigDecimal getSeqThr() {
        return seqThr;
    }

    public void setSeqThr(BigDecimal seqThr) {
        this.seqThr = seqThr;
    }

    public BigDecimal getMonthAmountThr() {
        return monthAmountThr;
    }

    public void setMonthAmountThr(BigDecimal monthAmountThr) {
        this.monthAmountThr = monthAmountThr;
    }

    public BigDecimal getSeqFour() {
        return seqFour;
    }

    public void setSeqFour(BigDecimal seqFour) {
        this.seqFour = seqFour;
    }

    public BigDecimal getMonthAmountFour() {
        return monthAmountFour;
    }

    public void setMonthAmountFour(BigDecimal monthAmountFour) {
        this.monthAmountFour = monthAmountFour;
    }

    public BigDecimal getSeqFive() {
        return seqFive;
    }

    public void setSeqFive(BigDecimal seqFive) {
        this.seqFive = seqFive;
    }

    public BigDecimal getMonthAmountFive() {
        return monthAmountFive;
    }

    public void setMonthAmountFive(BigDecimal monthAmountFive) {
        this.monthAmountFive = monthAmountFive;
    }

    public BigDecimal getSeqSix() {
        return seqSix;
    }

    public void setSeqSix(BigDecimal seqSix) {
        this.seqSix = seqSix;
    }

    public BigDecimal getMonthAmountSix() {
        return monthAmountSix;
    }

    public void setMonthAmountSix(BigDecimal monthAmountSix) {
        this.monthAmountSix = monthAmountSix;
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