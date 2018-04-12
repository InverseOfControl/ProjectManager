package com.ezendai.credit2.audit.model;

 
import java.math.BigDecimal;
import java.util.Date;

import com.ezendai.credit2.framework.model.BaseModel;
import com.ezendai.credit2.framework.util.Pager;

public class LoanHistory extends BaseModel {
	private static final long serialVersionUID = -5200601342962759617L;
	
	private Long loanId;
	private Long personId;
	private Long productId;
	private Integer productType;
	
	private String idNum;
	private String personName;
	private String productName;
	private String companyName;
	private String guaranteeName;
	private String organ;
	private BigDecimal curDebtbalancePerson;
	private BigDecimal curDebtbalanceCompany;
	private BigDecimal	requestMoney;
	private Integer Status;
	private Date requestDate;
	private String  salesDept;
	private String  approvalConclusion;
	private Pager  pager;
	
	 
	
	private String matchIngPhone;
	private String requestDataOption;
	private String requestCorrespondingMsg;
	private String matchingDataOption;
	private String matchingCorrespondingMsg;
	private String matchingPerson;
	private String matchingRequestDate;
	public BigDecimal getRequestMoney() {
		return requestMoney;
	}
	public void setRequestMoney(BigDecimal requestMoney) {
		this.requestMoney = requestMoney;
	}
	public Integer getProductType() {
		return productType;
	}
	public void setProductType(Integer productType) {
		this.productType = productType;
	}
	public String getMatchIngPhone() {
		return matchIngPhone;
	}
	public void setMatchIngPhone(String matchIngPhone) {
		this.matchIngPhone = matchIngPhone;
	}
	public String getRequestDataOption() {
		return requestDataOption;
	}
	public void setRequestDataOption(String requestDataOption) {
		this.requestDataOption = requestDataOption;
	}
	public String getRequestCorrespondingMsg() {
		return requestCorrespondingMsg;
	}
	public void setRequestCorrespondingMsg(String requestCorrespondingMsg) {
		this.requestCorrespondingMsg = requestCorrespondingMsg;
	}
	public String getMatchingDataOption() {
		return matchingDataOption;
	}
	public void setMatchingDataOption(String matchingDataOption) {
		this.matchingDataOption = matchingDataOption;
	}
	public String getMatchingCorrespondingMsg() {
		return matchingCorrespondingMsg;
	}
	public void setMatchingCorrespondingMsg(String matchingCorrespondingMsg) {
		this.matchingCorrespondingMsg = matchingCorrespondingMsg;
	}
	public String getMatchingPerson() {
		return matchingPerson;
	}
	public void setMatchingPerson(String matchingPerson) {
		this.matchingPerson = matchingPerson;
	}
	public String getMatchingRequestDate() {
		return matchingRequestDate;
	}
	public void setMatchingRequestDate(String matchingRequestDate) {
		this.matchingRequestDate = matchingRequestDate;
	}
	public Pager getPager() {
		return pager;
	}
	public void setPager(Pager pager) {
		this.pager = pager;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getGuaranteeName() {
		return guaranteeName;
	}
	public void setGuaranteeName(String guaranteeName) {
		this.guaranteeName = guaranteeName;
	}
	public String getOrgan() {
		return organ;
	}
	public void setOrgan(String organ) {
		this.organ = organ;
	}
	public BigDecimal getCurDebtbalancePerson() {
		return curDebtbalancePerson;
	}
	public void setCurDebtbalancePerson(BigDecimal curDebtbalancePerson) {
		this.curDebtbalancePerson = curDebtbalancePerson;
	}
	public BigDecimal getCurDebtbalanceCompany() {
		return curDebtbalanceCompany;
	}
	public void setCurDebtbalanceCompany(BigDecimal curDebtbalanceCompany) {
		this.curDebtbalanceCompany = curDebtbalanceCompany;
	}
	public Integer getStatus() {
		return Status;
	}
	public void setStatus(Integer status) {
		Status = status;
	}
	public Date getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
	public String getSalesDept() {
		return salesDept;
	}
	public void setSalesDept(String salesDept) {
		this.salesDept = salesDept;
	}
	public String getApprovalConclusion() {
		return approvalConclusion;
	}
	public void setApprovalConclusion(String approvalConclusion) {
		this.approvalConclusion = approvalConclusion;
	}
	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getIdNum() {
		return idNum;
	}
	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}
	public Long getLoanId() {
		return loanId;
	}
	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}
}