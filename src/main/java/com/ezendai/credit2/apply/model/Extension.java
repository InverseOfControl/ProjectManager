/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.apply.model;

import java.math.BigDecimal;
import java.util.Date;

import com.ezendai.credit2.framework.model.BaseModel;
import com.ezendai.credit2.system.model.SysUser;

/**
 * <pre>
 * 展期信息
 * </pre>
 *
 * @author zhangshihai
 * @version $Id: Extension.java, v 0.1 2015年3月10日 上午10:37:45 00221921 Exp $
 */
public class Extension extends BaseModel{
	/**  */
	private static final long serialVersionUID = 7736815901061312530L;

	/** 客户信息ID */
	private Long personId;
	
	/** 展期合同金额 */
	private BigDecimal pactMoney;
	
	/** 还款周期 */
	private Integer time;
	
	/** 剩余本金 */
	private BigDecimal residualPactMoney;
	
	/** 当前期数 */
	private Integer currNum;
	
	/** 放款金额 */
	private BigDecimal grantMoney;
	
	/** 申请日期 */
	private Date requestDate;
	
	/** 签约日期 */
	private Date signDate;
	
	/** 审批日期 */
	private Date auditDate;
	
	/** 审批金额 */
	private BigDecimal auditMoney;
	
	/** 申请金额 */
	private BigDecimal requestMoney;
	
	/** 约定还款日 */
	private Integer returnDate;
	
	/** 首期还款日 */
	private Date startRepayDate;
	
	/** 结束还款日 */
	private Date endRepayDate;
	
	/** 放款时间 */
	private Date grantDate;
	
	/** 客服ID */
	private Long serviceId;
	
	/** 客户经理ID */
	private Long crmId;
	
	/** 业务主任ID */
	private Long bizDirectorId;
	
	/** 审核人员ID */
	private Long assessorId;
	
	/** 贷款状态 */
	private Integer status;
	
	/** 展期合同编号 */
	private String contractNo;
	
	/** 借款申请提交时间 */
	private Date submitDate;
	
	/** 合同确认时间 */
	private Date contractConfirmDate;
	
	/** 合同退回时间 */
	private Date contractBackDate;
	
	/** 合同生成时间 */
	private Date contractCreatedTime;
	
	/** 剩余期数 */
	private Integer residualTime;
	
	/** 备注 */
	private String remark;
	
	/** 审批期限 */
	private Integer auditTime;
	
	/** 展期期数 */
	private Integer extensionTime;
	
	/** 放款银行 */
	private Long grantAccountId;

	/** 还款银行 */
	private Long repayAccountId;
	
	/**产品信息id*/
	private Long productId;
	
	/**贷款类型*/
	private Integer loanType;
	
	/** 销售团队ID */
	private Long salesTeamId;
	
	/** 营业网点 */
	private Long salesDeptId;
	
	/** 年利率 */
	private BigDecimal yearRate;
	
	/** 实际月利率 */
	private BigDecimal monthRate;
	
	/** 合同来源*/
	private Integer contractSrc;
	
	/** 评估费 */
	private BigDecimal assessment;
	
	/** 咨询费 */
	private BigDecimal consult;
	
	/** 乙方管理费 */
	private BigDecimal bManage;
	
	/** 丙方管理费 */
	private BigDecimal cManage;
	
	/**风险金*/
	private BigDecimal risk;
	
	/** 管理客服ID */
	private Long managerId;
	
	/** 签约客服 */
	private SysUser service;
	
	/** 客户 */
	private Person person;
	
	/** 管理客服 */
	private SysUser manageService;
	
	/** 借款类型 */
	private Integer productType;
	
	public Integer getTime() {
		return time;
	}

	public Long getManagerId() {
		return managerId;
	}

	public Integer getProductType() {
		return productType;
	}

	public void setProductType(Integer productType) {
		this.productType = productType;
	}

	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}

	public SysUser getService() {
		return service;
	}

	public void setService(SysUser service) {
		this.service = service;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public SysUser getManageService() {
		return manageService;
	}

	public void setManageService(SysUser manageService) {
		this.manageService = manageService;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public BigDecimal getResidualPactMoney() {
		return residualPactMoney;
	}

	public Integer getExtensionTime() {
		return extensionTime;
	}

	public void setExtensionTime(Integer extensionTime) {
		this.extensionTime = extensionTime;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public void setResidualPactMoney(BigDecimal residualPactMoney) {
		this.residualPactMoney = residualPactMoney;
	}

	public Integer getCurrNum() {
		return currNum;
	}

	public void setCurrNum(Integer currNum) {
		this.currNum = currNum;
	}

	public BigDecimal getGrantMoney() {
		return grantMoney;
	}

	public void setGrantMoney(BigDecimal grantMoney) {
		this.grantMoney = grantMoney;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public BigDecimal getAuditMoney() {
		return auditMoney;
	}

	public void setAuditMoney(BigDecimal auditMoney) {
		this.auditMoney = auditMoney;
	}

	public BigDecimal getRequestMoney() {
		return requestMoney;
	}

	public Integer getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Integer auditTime) {
		this.auditTime = auditTime;
	}

	public void setRequestMoney(BigDecimal requestMoney) {
		this.requestMoney = requestMoney;
	}

	public Integer getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Integer returnDate) {
		this.returnDate = returnDate;
	}

	public Date getStartRepayDate() {
		return startRepayDate;
	}

	public void setStartRepayDate(Date startRepayDate) {
		this.startRepayDate = startRepayDate;
	}

	public Date getEndRepayDate() {
		return endRepayDate;
	}

	public void setEndRepayDate(Date endRepayDate) {
		this.endRepayDate = endRepayDate;
	}

	public Date getGrantDate() {
		return grantDate;
	}

	public void setGrantDate(Date grantDate) {
		this.grantDate = grantDate;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public Long getCrmId() {
		return crmId;
	}

	public void setCrmId(Long crmId) {
		this.crmId = crmId;
	}

	public Long getAssessorId() {
		return assessorId;
	}

	public void setAssessorId(Long assessorId) {
		this.assessorId = assessorId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}

	public BigDecimal getPactMoney() {
		return pactMoney;
	}

	public void setPactMoney(BigDecimal pactMoney) {
		this.pactMoney = pactMoney;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public Date getContractConfirmDate() {
		return contractConfirmDate;
	}

	public void setContractConfirmDate(Date contractConfirmDate) {
		this.contractConfirmDate = contractConfirmDate;
	}

	public Date getContractBackDate() {
		return contractBackDate;
	}

	public void setContractBackDate(Date contractBackDate) {
		this.contractBackDate = contractBackDate;
	}

	public Date getContractCreatedTime() {
		return contractCreatedTime;
	}

	public void setContractCreatedTime(Date contractCreatedTime) {
		this.contractCreatedTime = contractCreatedTime;
	}

	public Integer getResidualTime() {
		return residualTime;
	}

	public void setResidualTime(Integer residualTime) {
		this.residualTime = residualTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getBizDirectorId() {
		return bizDirectorId;
	}

	public void setBizDirectorId(Long bizDirectorId) {
		this.bizDirectorId = bizDirectorId;
	}

	public Long getGrantAccountId() {
		return grantAccountId;
	}

	public void setGrantAccountId(Long grantAccountId) {
		this.grantAccountId = grantAccountId;
	}

	public Long getRepayAccountId() {
		return repayAccountId;
	}

	public void setRepayAccountId(Long repayAccountId) {
		this.repayAccountId = repayAccountId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Integer getLoanType() {
		return loanType;
	}

	public void setLoanType(Integer loanType) {
		this.loanType = loanType;
	}
	
	public Long getSalesDeptId() {
		return salesDeptId;
	}

	public void setSalesDeptId(Long salesDeptId) {
		this.salesDeptId = salesDeptId;
	}

	public BigDecimal getYearRate() {
		return yearRate;
	}

	public void setYearRate(BigDecimal yearRate) {
		this.yearRate = yearRate;
	}

	public BigDecimal getMonthRate() {
		return monthRate;
	}

	public void setMonthRate(BigDecimal monthRate) {
		this.monthRate = monthRate;
	}

	public Integer getContractSrc() {
		return contractSrc;
	}

	public void setContractSrc(Integer contractSrc) {
		this.contractSrc = contractSrc;
	}

	public BigDecimal getAssessment() {
		return assessment;
	}

	public void setAssessment(BigDecimal assessment) {
		this.assessment = assessment;
	}

	public BigDecimal getConsult() {
		return consult;
	}

	public void setConsult(BigDecimal consult) {
		this.consult = consult;
	}

	public BigDecimal getbManage() {
		return bManage;
	}

	public void setbManage(BigDecimal bManage) {
		this.bManage = bManage;
	}

	public BigDecimal getcManage() {
		return cManage;
	}

	public void setcManage(BigDecimal cManage) {
		this.cManage = cManage;
	}

	public BigDecimal getRisk() {
		return risk;
	}

	public void setRisk(BigDecimal risk) {
		this.risk = risk;
	}

	public Long getSalesTeamId() {
		return salesTeamId;
	}

	public void setSalesTeamId(Long salesTeamId) {
		this.salesTeamId = salesTeamId;
	}
	
}
