package com.ezendai.credit2.audit.model;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.ezendai.credit2.framework.model.BaseModel;

public class AuditTable  extends BaseModel{
    /**
	 * 
	 */
	private static final long serialVersionUID = -1640095476862166437L;

	private Long id;

    private Long loanId;

    private Long personid;

    private String companyName;

	@DateTimeFormat(pattern="yyyy-MM-dd") 
    private Date compCreateDate;

    private String majorBusiness;

    private String compAddStatus;

    private BigDecimal ratioOfInvestments;

    private BigDecimal compRegAmount;

    private String businessTime;

	@DateTimeFormat(pattern="yyyy-MM-dd") 
    private Date rentDate;

    private BigDecimal creditAmount;

    private BigDecimal auditAmount;

    private BigDecimal auditMonthAmount;

    private BigDecimal otherAmount;

    private String companyInfo;

    private String auditSystem;

    private String blackList;

    private String blackInfo;

    private String courtCompinfo;

    private String courtPersonInfo;

    private String passInfo;

    private String nowInfo;

    private String evaluationOverall;

    private String auditResult;

    private String creator;

    private Date createdTime;

    private Long creatorId;

    private Long modifierId;

    private String modifier;

    private Date modifiedTime;
    
    private BigDecimal incomePerMonth;
    
    private String auditResultFinal;

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

    public Long getPersonid() {
        return personid;
    }

    public void setPersonid(Long personid) {
        this.personid = personid;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Date getCompCreateDate() {
        return compCreateDate;
    }

    public void setCompCreateDate(Date compCreateDate) {
        this.compCreateDate = compCreateDate;
    }

    public String getMajorBusiness() {
        return majorBusiness;
    }

    public void setMajorBusiness(String majorBusiness) {
        this.majorBusiness = majorBusiness;
    }

    public String getCompAddStatus() {
        return compAddStatus;
    }

    public void setCompAddStatus(String compAddStatus) {
        this.compAddStatus = compAddStatus;
    }

    public BigDecimal getRatioOfInvestments() {
        return ratioOfInvestments;
    }

    public void setRatioOfInvestments(BigDecimal ratioOfInvestments) {
        this.ratioOfInvestments = ratioOfInvestments;
    }

    public BigDecimal getCompRegAmount() {
        return compRegAmount;
    }

    public void setCompRegAmount(BigDecimal compRegAmount) {
        this.compRegAmount = compRegAmount;
    }

    public String getBusinessTime() {
        return businessTime;
    }

    public void setBusinessTime(String businessTime) {
        this.businessTime = businessTime;
    }

    public Date getRentDate() {
        return rentDate;
    }

    public void setRentDate(Date rentDate) {
        this.rentDate = rentDate;
    }

    public BigDecimal getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(BigDecimal creditAmount) {
        this.creditAmount = creditAmount;
    }

    public BigDecimal getAuditAmount() {
        return auditAmount;
    }

    public void setAuditAmount(BigDecimal auditAmount) {
        this.auditAmount = auditAmount;
    }

    public BigDecimal getAuditMonthAmount() {
        return auditMonthAmount;
    }

    public void setAuditMonthAmount(BigDecimal auditMonthAmount) {
        this.auditMonthAmount = auditMonthAmount;
    }

    public BigDecimal getOtherAmount() {
        return otherAmount;
    }

    public void setOtherAmount(BigDecimal otherAmount) {
        this.otherAmount = otherAmount;
    }

    public String getCompanyInfo() {
        return companyInfo;
    }

    public void setCompanyInfo(String companyInfo) {
        this.companyInfo = companyInfo;
    }

    public String getAuditSystem() {
        return auditSystem;
    }

    public void setAuditSystem(String auditSystem) {
        this.auditSystem = auditSystem;
    }

    public String getBlackList() {
        return blackList;
    }

    public void setBlackList(String blackList) {
        this.blackList = blackList;
    }

    public String getBlackInfo() {
        return blackInfo;
    }

    public void setBlackInfo(String blackInfo) {
        this.blackInfo = blackInfo;
    }

    public String getCourtCompinfo() {
        return courtCompinfo;
    }

    public void setCourtCompinfo(String courtCompinfo) {
        this.courtCompinfo = courtCompinfo;
    }

    public String getCourtPersonInfo() {
        return courtPersonInfo;
    }

    public void setCourtPersonInfo(String courtPersonInfo) {
        this.courtPersonInfo = courtPersonInfo;
    }

    public String getPassInfo() {
        return passInfo;
    }

    public void setPassInfo(String passInfo) {
        this.passInfo = passInfo;
    }

    public String getNowInfo() {
        return nowInfo;
    }

    public void setNowInfo(String nowInfo) {
        this.nowInfo = nowInfo;
    }

    public String getEvaluationOverall() {
        return evaluationOverall;
    }

    public void setEvaluationOverall(String evaluationOverall) {
        this.evaluationOverall = evaluationOverall;
    }

    public String getAuditResult() {
        return auditResult;
    }

    public void setAuditResult(String auditResult) {
        this.auditResult = auditResult;
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

	public BigDecimal getIncomePerMonth() {
		return incomePerMonth;
	}

	public void setIncomePerMonth(BigDecimal incomePerMonth) {
		this.incomePerMonth = incomePerMonth;
	}

	public String getAuditResultFinal() {
		return auditResultFinal;
	}

	public void setAuditResultFinal(String auditResultFinal) {
		this.auditResultFinal = auditResultFinal;
	}
    
}