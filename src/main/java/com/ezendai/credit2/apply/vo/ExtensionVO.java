/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.apply.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.ezendai.credit2.framework.vo.BaseVO;

/**
 * <pre>
 * 展期信息
 * </pre>
 *
 * @author zhangshihai
 * @version $Id: ExtensionVO.java, v 0.1 2015年3月10日 上午11:03:53 00221921 Exp $
 */
public class ExtensionVO  extends BaseVO{
	/**  */
	private static final long serialVersionUID = -6880425022116510756L;

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
	
	private List<Long> idList;
	
	private List<Integer> statusList;
	
	private Integer notStatus;
	
	private Long loanId;
	/**  查询日期 */
	private Date queryDate;
	
	/** 管理客服ID */
	private Long managerId;
	
	/** 借款类型 */
	private Integer productType;
	/** 销售团队ID */
	private Long salesTeamId;

	public Integer getAuditTime() {
		return auditTime;
	}

	public Integer getProductType() {
		return productType;
	}

	public void setProductType(Integer productType) {
		this.productType = productType;
	}

	public void setAuditTime(Integer auditTime) {
		this.auditTime = auditTime;
	}

	public Long getManagerId() {
		return managerId;
	}

	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}

	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public Integer getExtensionTime() {
		return extensionTime;
	}

	public void setExtensionTime(Integer extensionTime) {
		this.extensionTime = extensionTime;
	}

	public List<Long> getIdList() {
		return idList;
	}

	public void setIdList(List<Long> idList) {
		this.idList = idList;
	}
	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public BigDecimal getResidualPactMoney() {
		return residualPactMoney;
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

	public Long getBizDirectorId() {
		return bizDirectorId;
	}

	public void setBizDirectorId(Long bizDirectorId) {
		this.bizDirectorId = bizDirectorId;
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

	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
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

	public List<Integer> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<Integer> statusList) {
		this.statusList = statusList;
	}

	public Integer getNotStatus() {
		return notStatus;
	}

	public void setNotStatus(Integer notStatus) {
		this.notStatus = notStatus;
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

	public Date getQueryDate() {
		return queryDate;
	}

	public void setQueryDate(Date queryDate) {
		this.queryDate = queryDate;
	}

	public Long getSalesTeamId() {
		return salesTeamId;
	}

	public void setSalesTeamId(Long salesTeamId) {
		this.salesTeamId = salesTeamId;
	}
	
}
