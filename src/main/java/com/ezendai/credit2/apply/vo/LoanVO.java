package com.ezendai.credit2.apply.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.ezendai.credit2.framework.vo.BaseVO;

/**
 * 
 * @author zhuyiguo
 * @version $Id: LoanVO.java, v 0.1 2014年6月26日 上午9:13:51 zhuyiguo Exp $
 */
public class LoanVO extends BaseVO {

	private static final long serialVersionUID = -5200601342962759617L;

	private List<Long> idList;
	/** 客户信息ID */
	private Long personId;
	/** 抵押车辆ID */
	private Long vehicleId;
	/** 产品信息ID */
	private Long productId;
	/** 风险金 */
	private BigDecimal risk;
	/** 合同金额 */
	private BigDecimal pactMoney;
	/** 还款周期 */
	private Long time;
	/** 申请期限 */
	private Long requestTime;
	/** 销售团队ID */
	private Long salesTeamId;
	/** 剩余本金 */
	private BigDecimal residualPactMoney;
	/** 实际月利率 */
	private BigDecimal monthRate;
	/** 放款银行 */
	private Long grantAccountId;
	/** 贷款类型 */
	private Integer loanType;
	/** 还款银行 */
	private Long repayAccountId;
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
	/** 审批期限 */
	private Integer auditTime;
	/** 有条件审批指定担保人名字 */
	private String guaranteeName;
	/** 申请金额 */
	private BigDecimal requestMoney;
	/** 约定还款日 */
	private Integer returnDate;
	/** 首期还款日 */
	private Date startRepayDate;
	/** 首期还款日开始 */
	private Date startRepayDateStart;
	/** 首期还款日结束 */
	private Date startRepayDateEnd;
	/** 同城费 */
	private BigDecimal thirdFee;

	/** 结束还款日 */
	private Date endRepayDate;
	private Date grantDate;
	private String customerSource;
	private Long serviceId;
	private Long crmId;
	/** 业务主任ID */
	private Long bizDirectorId;
	private Long assessorId;
	private Long salesDeptId;
	/** 营业网点列表 **/
	private List<Long> salesDeptIdList;
	private Long cityId;
	private String purpose;
	private Integer status;
	// "合同取消状态" add by zsh
	private Integer cancelStatus;
	private Long userId;
	private String remark;
	private String contractNo;
	/** 借款人姓名 */
	private String personName;
	/** 借款人姓名(供模糊查询用) */
	private String personFuzzyName;
	private String personIdnum;
	/** 借款人手机号码 */
	private String personMobilePhone;
	private Long selectedProductId;
	private List<Long> productIdList;
	private String productCode;
	private String productName;
	private String salesDeptCode;
	private String salesDeptName;
	private List<Integer> statusList;
	// 财务审核时间
	private Date financeAuditTime;

	private Date submitDate;
	private Integer hasHouse;
	// 签约日期
	private Date auditDateStart;
	private Date auditDateEnd;
	// 签批日期
	private Date signDateStart;
	private Date signDateEnd;

	// 申请日期
	private Date requestStartDate;
	private Date requestEndDate;

	// 合同确认时间
	private Date contractConfirmDate;

	private Date contractConfirmStartDate;
	private Date contractConfirmEndDate;
	// 合同退回时间
	private Date contractBackDate;
	// 财务审核时间
	private Date financeAuditTimeStart;
	private Date financeAuditTimeEnd;

	// 财务放款时间
	private Date financeGrantTimeStart;
	private Date financeGrantTimeEnd;
	
	// 创建开始时间
	private Date createdTimeStart;

	// 合同来源
	private Integer contractSrc;

	private String operations;

	// 合同生成时间
	private Date contractCreatedTime;

	// 评估费
	private BigDecimal assessment;

	// 咨询费
	private BigDecimal consult;

	// 乙方管理费
	private BigDecimal bManage;

	// 丙方管理费
	private BigDecimal cManage;
	
	//服务费
	private BigDecimal serviceCharges;
	//前期利息(针对车贷短期  前期利息)
	private BigDecimal prophaseInterest;

	// 还款方式
	private Integer repaymentMethod;

	private String naturalGuaranteeName1;

	private String naturalGuaranteeName2;

	private String legalGuaranteeCname1;

	private String legalGuaranteeCname2;

	/** 剩余期数 */
	private Integer residualTime;

	/** 管理客服ID */
	private Long managerId;

	/** 还款日期 */
	private Date repayDate;

	/** 还款类型 */
	private Integer repaymentType;

	/** 还款类型 */
	private String overdueTerm;

	private Integer notStatus;

	/** status扩展字段:为生成报盘记录加过滤条件 **/
	private Integer overdueStatus;

	/** 展期第几次次数 */
	private Integer extensionTime;
	/** 借款Id */
	private Long loanId;
	/** 是否展期 */
	private Integer isExtension;

	/** 查询日期 */
	private Date queryDate;

	/** 产品类型 */
	private Integer productType;
	/** loan表标志位 */
	private Integer flag;

	private List<Integer> productTypeList;

	/** 还款来源 */
	private String sourceOfRepay;

	/** 网商贷审批会员类型 */
	private Integer auditMemberType;
	/** 初审员 */
	private Long firstTrialId;
	
	
	private Long organID;
	private Long schemeID;
	private String organCode;
	
	private String organName;
	
	private String recordEmpty;
	
	private Long businessCompanyId;
	private String frameNumber;
	
	 //车牌号
	private String plateNumber;
	//手机号
	private String mobilePhoneLoan;
	//客户经理Id
	private String customerManagerId;

	/** 捞财宝借款编号 */
	private String lcbBorrowNo;
	/** 捞财宝借款状态 */
	private Integer lcbBorrowStatus;

	public String getMobilePhoneLoan() {
		return mobilePhoneLoan;
	}

	public void setMobilePhoneLoan(String mobilePhoneLoan) {
		this.mobilePhoneLoan = mobilePhoneLoan;
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	public String getCustomerManagerId() {
		return customerManagerId;
	}

	public void setCustomerManagerId(String customerManagerId) {
		this.customerManagerId = customerManagerId;
	}

	public BigDecimal getProphaseInterest() {
		return prophaseInterest;
	}

	public void setProphaseInterest(BigDecimal prophaseInterest) {
		this.prophaseInterest = prophaseInterest;
	}
	
	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public Long getOrganID() {
		return organID;
	}

	public void setOrganID(Long organID) {
		this.organID = organID;
	}

	public Long getSchemeID() {
		return schemeID;
	}

	public void setSchemeID(Long schemeID) {
		this.schemeID = schemeID;
	}

	public String getOrganCode() {
		return organCode;
	}

	public void setOrganCode(String organCode) {
		this.organCode = organCode;
	}

	public Long getFirstTrialId() {
		return firstTrialId;
	}

	public void setFirstTrialId(Long firstTrialId) {
		this.firstTrialId = firstTrialId;
	}

	public Integer getAuditMemberType() {
		return auditMemberType;
	}

	public void setAuditMemberType(Integer auditMemberType) {
		this.auditMemberType = auditMemberType;
	}

	public String getSourceOfRepay() {
		return sourceOfRepay;
	}

	public void setSourceOfRepay(String sourceOfRepay) {
		this.sourceOfRepay = sourceOfRepay;
	}

	public List<Long> getIdList() {
		return idList;
	}

	public List<Integer> getProductTypeList() {
		return productTypeList;
	}

	public void setProductTypeList(List<Integer> productTypeList) {
		this.productTypeList = productTypeList;
	}

	public Integer getProductType() {
		return productType;
	}

	public void setProductType(Integer productType) {
		this.productType = productType;
	}

	public void setIdList(List<Long> idList) {
		this.idList = idList;
	}

	public Long getPersonId() {
		return personId;
	}

	public String getPersonFuzzyName() {
		return personFuzzyName;
	}

	public void setPersonFuzzyName(String personFuzzyName) {
		this.personFuzzyName = personFuzzyName;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public String getPersonMobilePhone() {
		return personMobilePhone;
	}

	public void setPersonMobilePhone(String personMobilePhone) {
		this.personMobilePhone = personMobilePhone;
	}

	public Long getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public BigDecimal getRisk() {
		return risk;
	}

	public void setRisk(BigDecimal risk) {
		this.risk = risk;
	}

	public BigDecimal getPactMoney() {
		return pactMoney;
	}

	public void setPactMoney(BigDecimal pactMoney) {
		this.pactMoney = pactMoney;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public Long getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(Long requestTime) {
		this.requestTime = requestTime;
	}

	public Long getSalesTeamId() {
		return salesTeamId;
	}

	public void setSalesTeamId(Long salesTeamId) {
		this.salesTeamId = salesTeamId;
	}

	public Integer getCancelStatus() {
		return cancelStatus;
	}

	public void setCancelStatus(Integer cancelStatus) {
		this.cancelStatus = cancelStatus;
	}

	public BigDecimal getResidualPactMoney() {
		return residualPactMoney;
	}

	public void setResidualPactMoney(BigDecimal residualPactMoney) {
		this.residualPactMoney = residualPactMoney;
	}

	public BigDecimal getMonthRate() {
		return monthRate;
	}

	public void setMonthRate(BigDecimal monthRate) {
		this.monthRate = monthRate;
	}

	public Integer getLoanType() {
		return loanType;
	}

	public void setLoanType(Integer loanType) {
		this.loanType = loanType;
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

	public Integer getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Integer auditTime) {
		this.auditTime = auditTime;
	}

	public String getGuaranteeName() {
		return guaranteeName;
	}

	public void setGuaranteeName(String guaranteeName) {
		this.guaranteeName = guaranteeName;
	}

	public BigDecimal getRequestMoney() {
		return requestMoney;
	}

	public void setRequestMoney(BigDecimal requestMoney) {
		this.requestMoney = requestMoney;
	}

	public Integer getCurrNum() {
		return currNum;
	}

	public void setCurrNum(Integer currNum) {
		this.currNum = currNum;
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

	public Date getStartRepayDateStart() {
		return startRepayDateStart;
	}

	public void setStartRepayDateStart(Date startRepayDateStart) {
		this.startRepayDateStart = startRepayDateStart;
	}

	public Date getStartRepayDateEnd() {
		return startRepayDateEnd;
	}

	public void setStartRepayDateEnd(Date startRepayDateEnd) {
		this.startRepayDateEnd = startRepayDateEnd;
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

	public String getCustomerSource() {
		return customerSource;
	}

	public void setCustomerSource(String customerSource) {
		this.customerSource = customerSource;
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

	public Long getSalesDeptId() {
		return salesDeptId;
	}

	public void setSalesDeptId(Long salesDeptId) {
		this.salesDeptId = salesDeptId;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getPersonIdnum() {
		return personIdnum;
	}

	public void setPersonIdnum(String personIdnum) {
		this.personIdnum = personIdnum;
	}

	public Long getSelectedProductId() {
		return selectedProductId;
	}

	public void setSelectedProductId(Long selectedProductId) {
		this.selectedProductId = selectedProductId;
	}

	public List<Long> getProductIdList() {
		return productIdList;
	}

	public void setProductIdList(List<Long> productIdList) {
		this.productIdList = productIdList;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getSalesDeptCode() {
		return salesDeptCode;
	}

	public void setSalesDeptCode(String salesDeptCode) {
		this.salesDeptCode = salesDeptCode;
	}

	public String getSalesDeptName() {
		return salesDeptName;
	}

	public void setSalesDeptName(String salesDeptName) {
		this.salesDeptName = salesDeptName;
	}

	public List<Integer> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<Integer> statusList) {
		this.statusList = statusList;
	}

	public Date getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}

	public Integer getHasHouse() {
		return hasHouse;
	}

	public void setHasHouse(Integer hasHouse) {
		this.hasHouse = hasHouse;
	}

	public Date getAuditDateStart() {
		return auditDateStart;
	}

	public void setAuditDateStart(Date auditDateStart) {
		this.auditDateStart = auditDateStart;
	}

	public Date getAuditDateEnd() {
		return auditDateEnd;
	}

	public void setAuditDateEnd(Date auditDateEnd) {
		this.auditDateEnd = auditDateEnd;
	}

	public Integer getContractSrc() {
		return contractSrc;
	}

	public void setContractSrc(Integer contractSrc) {
		this.contractSrc = contractSrc;
	}

	public String getOperations() {
		return operations;
	}

	public void setOperations(String operations) {
		this.operations = operations;
	}

	public Date getSignDateStart() {
		return signDateStart;
	}

	public void setSignDateStart(Date signDateStart) {
		this.signDateStart = signDateStart;
	}

	public Date getSignDateEnd() {
		return signDateEnd;
	}

	public void setSignDateEnd(Date signDateEnd) {
		this.signDateEnd = signDateEnd;
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

	public Date getFinanceAuditTimeStart() {
		return financeAuditTimeStart;
	}

	public void setFinanceAuditTimeStart(Date financeAuditTimeStart) {
		this.financeAuditTimeStart = financeAuditTimeStart;
	}

	public Date getFinanceAuditTimeEnd() {
		return financeAuditTimeEnd;
	}

	public void setFinanceAuditTimeEnd(Date financeAuditTimeEnd) {
		this.financeAuditTimeEnd = financeAuditTimeEnd;
	}

	public Date getContractCreatedTime() {
		return contractCreatedTime;
	}

	public void setContractCreatedTime(Date contractCreatedTime) {
		this.contractCreatedTime = contractCreatedTime;
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

	public Date getContractConfirmStartDate() {
		return contractConfirmStartDate;
	}

	public void setContractConfirmStartDate(Date contractConfirmStartDate) {
		this.contractConfirmStartDate = contractConfirmStartDate;
	}

	public Date getContractConfirmEndDate() {
		return contractConfirmEndDate;
	}

	public void setContractConfirmEndDate(Date contractConfirmEndDate) {
		this.contractConfirmEndDate = contractConfirmEndDate;
	}

	public Date getFinanceAuditTime() {
		return financeAuditTime;
	}

	public void setFinanceAuditTime(Date financeAuditTime) {
		this.financeAuditTime = financeAuditTime;
	}

	public Integer getRepaymentMethod() {
		return repaymentMethod;
	}

	public void setRepaymentMethod(Integer repaymentMethod) {
		this.repaymentMethod = repaymentMethod;
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

	public Integer getResidualTime() {
		return residualTime;
	}

	public void setResidualTime(Integer residualTime) {
		this.residualTime = residualTime;
	}

	public Long getManagerId() {
		return managerId;
	}

	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}

	public Date getRepayDate() {
		return repayDate;
	}

	public void setRepayDate(Date repayDate) {
		this.repayDate = repayDate;
	}

	public Integer getRepaymentType() {
		return repaymentType;
	}

	public void setRepaymentType(Integer repaymentType) {
		this.repaymentType = repaymentType;
	}

	public String getOverdueTerm() {
		return overdueTerm;
	}

	public void setOverdueTerm(String overdueTerm) {
		this.overdueTerm = overdueTerm;
	}

	public Integer getNotStatus() {
		return notStatus;
	}

	public void setNotStatus(Integer notStatus) {
		this.notStatus = notStatus;
	}

	public Date getCreatedTimeStart() {
		return createdTimeStart;
	}

	public void setCreatedTimeStart(Date createdTimeStart) {
		this.createdTimeStart = createdTimeStart;
	}

	public Integer getOverdueStatus() {
		return overdueStatus;
	}

	public void setOverdueStatus(Integer overdueStatus) {
		this.overdueStatus = overdueStatus;
	}

	public Integer getExtensionTime() {
		return extensionTime;
	}

	public void setExtensionTime(Integer extensionTime) {
		this.extensionTime = extensionTime;
	}

	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public Integer getIsExtension() {
		return isExtension;
	}

	public void setIsExtension(Integer isExtension) {
		this.isExtension = isExtension;
	}

	public Date getQueryDate() {
		return queryDate;
	}

	public void setQueryDate(Date queryDate) {
		this.queryDate = queryDate;
	}

	public Long getBizDirectorId() {
		return bizDirectorId;
	}

	public void setBizDirectorId(Long bizDirectorId) {
		this.bizDirectorId = bizDirectorId;
	}

	public List<Long> getSalesDeptIdList() {
		return salesDeptIdList;
	}

	public void setSalesDeptIdList(List<Long> salesDeptIdList) {
		this.salesDeptIdList = salesDeptIdList;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public BigDecimal getThirdFee() {
		return thirdFee;
	}

	public void setThirdFee(BigDecimal thirdFee) {
		this.thirdFee = thirdFee;
	}

	public Date getRequestStartDate() {
		return requestStartDate;
	}

	public void setRequestStartDate(Date requestStartDate) {
		this.requestStartDate = requestStartDate;
	}

	public Date getRequestEndDate() {
		return requestEndDate;
	}

	public void setRequestEndDate(Date requestEndDate) {
		this.requestEndDate = requestEndDate;
	}

	public BigDecimal getServiceCharges() {
		return serviceCharges;
	}

	public void setServiceCharges(BigDecimal serviceCharges) {
		this.serviceCharges = serviceCharges;
	}

	public String getRecordEmpty() {
		return recordEmpty;
	}

	public void setRecordEmpty(String recordEmpty) {
		this.recordEmpty = recordEmpty;
	}

	public Date getFinanceGrantTimeStart() {
		return financeGrantTimeStart;
	}

	public void setFinanceGrantTimeStart(Date financeGrantTimeStart) {
		this.financeGrantTimeStart = financeGrantTimeStart;
	}

	public Date getFinanceGrantTimeEnd() {
		return financeGrantTimeEnd;
	}

	public void setFinanceGrantTimeEnd(Date financeGrantTimeEnd) {
		this.financeGrantTimeEnd = financeGrantTimeEnd;
	}

	public Long getBusinessCompanyId() {
		return businessCompanyId;
	}

	public void setBusinessCompanyId(Long businessCompanyId) {
		this.businessCompanyId = businessCompanyId;
	}

	public String getFrameNumber() {
		return frameNumber;
	}

	public void setFrameNumber(String frameNumber) {
		this.frameNumber = frameNumber;
	}
   /** 应还款日 */
	private String repayDay;

	public String getRepayDay() {
		return repayDay;
	}

	public void setRepayDay(String repayDay) {
		this.repayDay = repayDay;
	}
	/** 用于存储是否为批量*/
	private String pl;

	public String getPl() {
		return pl;
	}

	public void setPl(String pl) {
		this.pl = pl;
	}

	public String getLcbBorrowNo() {
		return lcbBorrowNo;
	}

	public void setLcbBorrowNo(String lcbBorrowNo) {
		this.lcbBorrowNo = lcbBorrowNo;
	}

	public Integer getLcbBorrowStatus() {
		return lcbBorrowStatus;
	}

	public void setLcbBorrowStatus(Integer lcbBorrowStatus) {
		this.lcbBorrowStatus = lcbBorrowStatus;
	}
	
}
