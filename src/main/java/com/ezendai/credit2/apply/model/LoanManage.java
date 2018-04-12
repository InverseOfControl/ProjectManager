package com.ezendai.credit2.apply.model;

import java.math.BigDecimal;
import java.util.Date;

import com.ezendai.credit2.framework.model.BaseModel;
import com.ezendai.credit2.master.model.BaseArea;
import com.ezendai.credit2.system.model.Organ;
import com.ezendai.credit2.system.model.SysUser;

public class LoanManage extends BaseModel{
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
	/** 审批期限 */
	private Integer auditTime;
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
	/** 客户来源 */
	private String customerSource;
	/** 客服ID */
	private Long serviceId;
	/** 客户经理ID */
	private Long crmId;

	/** 业务主任ID */
	private Long bizDirectorId;

	/** 审核人员ID */
	private Long assessorId;
	/** 所属网点 */
	private Long salesDeptId;
	/** 初审员ID */
	private Long firstTrialId;
	/** 贷款用途 */
	private String purpose;
	/** 贷款状态 */
	private Integer status;
	/** 客户经理工号 */
	private Long userId;
	/** 备注 */
	private String remark;
	/** 有条件审批指定担保人名字 */
	private String guaranteeName;
	/** 客户 */
	private Person person;
	/** 产品 */
	private Product product;
	/** 车辆 */
	private Vehicle vehicle;
	/** 团队 */
	private BaseArea salesTeam;
	/** 网点 */
	private BaseArea salesDept;
	/** 银行账户 */
	private BankAccount grantAccount;
	/** 银行账户 */
	private BankAccount repayAccount;
	/** 签约客服 */
	private SysUser service;
	/** 经理 */
	private SysUser crm;
	/** 复核人 */
	private SysUser assessor;
	/** 初审人 */
	private SysUser firstTrial;
	private SysUser user;
	/** 业务主任 */
	private SysUser bizDirector;
	/** 联系人 */
	private Guarantee guarantee;
	/** 合同编号 */
	private String contractNo;
	/** 借款申请提交时间 */
	private Date submitDate;
	/** 是否有房 */
	private Integer hasHouse;
	/** 合同来源 */
	private Integer contractSrc;
	/** 合同确认时间 */
	private Date contractConfirmDate;
	/** 合同退回时间 */
	private Date contractBackDate;

	/** 合同生成时间 */
	private Date contractCreatedTime;

	/** 评估费 */
	private BigDecimal assessment;

	/** 咨询费 */
	private BigDecimal consult;

	/** 乙方管理费 */
	private BigDecimal bManage;

	/** 丙方管理费 */
	private BigDecimal cManage;

	/** 同城费 */
	private BigDecimal thirdFee;

	/** 财务审核时间 */
	private Date financeAuditTime;

	/** 还款方式 */
	private Integer repaymentMethod;
	/** 指定的 担保人 */
	private String naturalGuaranteeName1;

	private String naturalGuaranteeName2;

	private String legalGuaranteeCname1;

	private String legalGuaranteeCname2;

	/** 剩余期数 */
	private Integer residualTime;

	/** 管理客服ID */
	private Long managerId;

	/** 管理客服 */
	private SysUser manageService;

	private String operations;

	/** 展期第几次次数 */
	private Integer extensionTime;

	/** 原始借款ID */
	private Long loanId;

	/** 产品类型 */
	private Integer productType;
	/** loan表标志位 */
	private Integer flag;

	/** 还款来源 */
	private String sourceOfRepay;

	private Integer productChannelID;

	/** 网商贷审批会员类型 */
	private Integer auditMemberType;

	private String auditMemberTypeText;
	/** 合作机构 */
	private Organ organ;
	private String organName;
	
	private String channelPlanName;
	
	private int curUserType;
	
	private BigDecimal serviceCharges;
	
	//前期利息(针对车贷短期  前期利息)
	private BigDecimal prophaseInterest;
	//0,代表不是自动取消，1代表自动取消
	private String isAutoCancel;
	
	//车牌号
	private String plateNumber;
	//手机号
	private String mobilePhoneLoan;
	//客户经理Id
	private String customerManagerId;

	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	public String getMobilePhoneLoan() {
		return mobilePhoneLoan;
	}

	public void setMobilePhoneLoan(String mobilePhoneLoan) {
		this.mobilePhoneLoan = mobilePhoneLoan;
	}

	public String getCustomerManagerId() {
		return customerManagerId;
	}

	public void setCustomerManagerId(String customerManagerId) {
		this.customerManagerId = customerManagerId;
	}

	public String getIsAutoCancel() {
		return isAutoCancel;
	}

	public void setIsAutoCancel(String isAutoCancel) {
		this.isAutoCancel = isAutoCancel;
	}

	public BigDecimal getProphaseInterest() {
		return prophaseInterest;
	}

	public void setProphaseInterest(BigDecimal prophaseInterest) {
		this.prophaseInterest = prophaseInterest;
	}

	public int getCurUserType() {
		return curUserType;
	}

	public void setCurUserType(int curUserType) {
		this.curUserType = curUserType;
	}

	public String getChannelPlanName() {
		return channelPlanName;
	}

	public void setChannelPlanName(String channelPlanName) {
		this.channelPlanName = channelPlanName;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public SysUser getFirstTrial() {
		return firstTrial;
	}

	public void setFirstTrial(SysUser firstTrial) {
		this.firstTrial = firstTrial;
	}

	public Organ getOrgan() {
		return organ;
	}

	public void setOrgan(Organ organ) {
		this.organ = organ;
	}

	private Long organID;
	private Long schemeID;
	private String organCode;

	private ChannelPlanCheck channelPlanCheck;

	public ChannelPlanCheck getChannelPlanCheck() {
		return channelPlanCheck;
	}

	public void setChannelPlanCheck(ChannelPlanCheck channelPlanCheck) {
		this.channelPlanCheck = channelPlanCheck;
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

	public Integer getAuditMemberType() {
		return auditMemberType;
	}

	public void setAuditMemberType(Integer auditMemberType) {
		this.auditMemberType = auditMemberType;
	}

	public Integer getProductChannelID() {
		return productChannelID;
	}

	public void setProductChannelID(Integer productChannelID) {
		this.productChannelID = productChannelID;
	}

	public String getSourceOfRepay() {
		return sourceOfRepay;
	}

	public void setSourceOfRepay(String sourceOfRepay) {
		this.sourceOfRepay = sourceOfRepay;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public Long getVehicleId() {
		return vehicleId;
	}

	public Integer getProductType() {
		return productType;
	}

	public void setProductType(Integer productType) {
		this.productType = productType;
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

	public SysUser getManageService() {
		return manageService;
	}

	public void setManageService(SysUser manageService) {
		this.manageService = manageService;
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

	public Long getGrantAccountId() {
		return grantAccountId;
	}

	public void setGrantAccountId(Long grantAccountId) {
		this.grantAccountId = grantAccountId;
	}

	public Integer getLoanType() {
		return loanType;
	}

	public void setLoanType(Integer loanType) {
		this.loanType = loanType;
	}

	public Long getRepayAccountId() {
		return repayAccountId;
	}

	public void setRepayAccountId(Long repayAccountId) {
		this.repayAccountId = repayAccountId;
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

	@Deprecated
	public Long getUserId() {
		return userId;
	}

	@Deprecated
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public BaseArea getSalesTeam() {
		return salesTeam;
	}

	public void setSalesTeam(BaseArea salesTeam) {
		this.salesTeam = salesTeam;
	}

	public BaseArea getSalesDept() {
		return salesDept;
	}

	public void setSalesDept(BaseArea salesDept) {
		this.salesDept = salesDept;
	}

	public BankAccount getGrantAccount() {
		return grantAccount;
	}

	public void setGrantAccount(BankAccount grantAccount) {
		this.grantAccount = grantAccount;
	}

	public BankAccount getRepayAccount() {
		return repayAccount;
	}

	public void setRepayAccount(BankAccount repayAccount) {
		this.repayAccount = repayAccount;
	}

	public SysUser getService() {
		return service;
	}

	public void setService(SysUser service) {
		this.service = service;
	}

	public SysUser getCrm() {
		return crm;
	}

	public void setCrm(SysUser crm) {
		this.crm = crm;
	}

	public SysUser getAssessor() {
		return assessor;
	}

	public void setAssessor(SysUser assessor) {
		this.assessor = assessor;
	}

	public SysUser getUser() {
		return user;
	}

	public void setUser(SysUser user) {
		this.user = user;
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

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public Guarantee getGuarantee() {
		return guarantee;
	}

	public void setGuarantee(Guarantee guarantee) {
		this.guarantee = guarantee;
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

	public Integer getContractSrc() {
		return contractSrc;
	}

	public void setContractSrc(Integer contractSrc) {
		this.contractSrc = contractSrc;
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

	public String getOperations() {
		return operations;
	}

	public void setOperations(String operations) {
		this.operations = operations;
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

	public Long getBizDirectorId() {
		return bizDirectorId;
	}

	public void setBizDirectorId(Long bizDirectorId) {
		this.bizDirectorId = bizDirectorId;
	}

	public SysUser getBizDirector() {
		return bizDirector;
	}

	public void setBizDirector(SysUser bizDirector) {
		this.bizDirector = bizDirector;
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

	public void setAuditMemberTypeText(String text) {
		this.auditMemberTypeText = text;

	}

	public String getAuditMemberTypeText() {
		return auditMemberTypeText;
	}

	public Long getFirstTrialId() {
		return firstTrialId;
	}

	public void setFirstTrialId(Long firstTrialId) {
		this.firstTrialId = firstTrialId;
	}

	public BigDecimal getServiceCharges() {
		return serviceCharges;
	}

	public void setServiceCharges(BigDecimal serviceCharges) {
		this.serviceCharges = serviceCharges;
	}

}
