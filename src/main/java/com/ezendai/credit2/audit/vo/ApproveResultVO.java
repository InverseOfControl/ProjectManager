package com.ezendai.credit2.audit.vo;

import java.math.BigDecimal;
import java.util.List;

import com.ezendai.credit2.framework.vo.BaseVO;

public class ApproveResultVO extends BaseVO {
	private static final long serialVersionUID = 1L;
	/**贷款ID*/
	private Long loanId;

	/**状态--已经不使用*/
	private String status;

	/**原因*/
	private String reason;

	/**拒绝原因1*/
	private String reason1;

	/**拒绝原因2*/
	private String reason2;

	/**补充证件1*/
	private String certificates1;

	/**补充证件2*/
	private String certificates2;

	/**补充证件3*/
	private String certificates3;

	/**申请金额*/
	private Long requestMoney;

	private String guaranteeName;
	//指定的担保人 naturalGuaranteeName自然人担保
	private String naturalGuaranteeName1;

	private String naturalGuaranteeName2;

	private String legalGuaranteeCname1;

	private String legalGuaranteeCname2;

	/**二级原因对应的ID*/
	private Long refuseSecondReasonId;
	/**状态*/
	private Integer state;

	private List<Integer> statusList;
	
	private Integer loanType;
	
	private Integer productId;

	@Deprecated
	String guarantee; // 指定担保人
	/**申请期数*/
	private Integer term;
	BigDecimal auditMoney;
	/**管理费率(有房无房)*/
	private Integer hasHouse;
	/**签约事项*/
	private String contractMatters;

	/** 网商贷审批会员类型*/
	private Integer auditMemberType;
	
	
	/** 所有签批选中的担保人ID	 */
	private String guaIds;

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getLoanType() {
		return loanType;
	}

	public void setLoanType(Integer loanType) {
		this.loanType = loanType;
	}

	public String getGuaIds() {
		return guaIds;
	}

	public void setGuaIds(String guaIds) {
		this.guaIds = guaIds;
	}

	public Integer getAuditMemberType() {
		return auditMemberType;
	}

	public void setAuditMemberType(Integer auditMemberType) {
		this.auditMemberType = auditMemberType;
	}

	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getReason1() {
		return reason1;
	}

	public void setReason1(String reason1) {
		this.reason1 = reason1;
	}

	public String getReason2() {
		return reason2;
	}

	public void setReason2(String reason2) {
		this.reason2 = reason2;
	}

	public String getCertificates1() {
		return certificates1;
	}

	public void setCertificates1(String certificates1) {
		this.certificates1 = certificates1;
	}

	public String getCertificates2() {
		return certificates2;
	}

	public void setCertificates2(String certificates2) {
		this.certificates2 = certificates2;
	}

	public String getCertificates3() {
		return certificates3;
	}

	public void setCertificates3(String certificates3) {
		this.certificates3 = certificates3;
	}

	public String getGuaranteeName() {
		return guaranteeName;
	}

	public void setGuaranteeName(String guaranteeName) {
		this.guaranteeName = guaranteeName;
	}

	public String getGuarantee() {
		return guarantee;
	}

	public void setGuarantee(String guarantee) {
		this.guarantee = guarantee;
	}

	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	public BigDecimal getAuditMoney() {
		return auditMoney;
	}

	public void setAuditMoney(BigDecimal auditMoney) {
		this.auditMoney = auditMoney;
	}

	public Long getRequestMoney() {
		return requestMoney;
	}

	public void setRequestMoney(Long requestMoney) {
		this.requestMoney = requestMoney;
	}

	public String getContractMatters() {
		return contractMatters;
	}

	public void setContractMatters(String contractMatters) {
		this.contractMatters = contractMatters;
	}

	public Integer getHasHouse() {
		return hasHouse;
	}

	public void setHasHouse(Integer hasHouse) {
		this.hasHouse = hasHouse;
	}

	public Long getRefuseSecondReasonId() {
		return refuseSecondReasonId;
	}

	public void setRefuseSecondReasonId(Long refuseSecondReasonId) {
		this.refuseSecondReasonId = refuseSecondReasonId;
	}
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public List<Integer> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<Integer> statusList) {
		this.statusList = statusList;
	}

	public String getNaturalGuaranteeName1() {
		return naturalGuaranteeName1;
	}

	public void setNaturalGuaranteeName1(String naturalGuaranteeName1) {
		this.naturalGuaranteeName1 = naturalGuaranteeName1;
	}

	public String getNaturalGuaranteeName2() {
		return naturalGuaranteeName2;
	}

	public void setNaturalGuaranteeName2(String naturalGuaranteeName2) {
		this.naturalGuaranteeName2 = naturalGuaranteeName2;
	}

	public String getLegalGuaranteeCname1() {
		return legalGuaranteeCname1;
	}

	public void setLegalGuaranteeCname1(String legalGuaranteeCname1) {
		this.legalGuaranteeCname1 = legalGuaranteeCname1;
	}

	public String getLegalGuaranteeCname2() {
		return legalGuaranteeCname2;
	}

	public void setLegalGuaranteeCname2(String legalGuaranteeCname2) {
		this.legalGuaranteeCname2 = legalGuaranteeCname2;
	}

		
	
}
