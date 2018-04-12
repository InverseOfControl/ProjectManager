package com.ezendai.credit2.after.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.framework.vo.BaseVO;

/**
 * <pre>
 * 报盘VO
 * </pre>
 * @author 陈忠星
 * @Description:
 */
public class OfferVO extends BaseVO {
	/**	 */
	private static final long serialVersionUID = -457242451952353074L;
	/**
	 * 用于获取生成时间
	 */
	private String startStrDate;
	private String endStrDate;
	/**
	 * 用于获取创建时间
	 */
	private String startCreatStrDate;
	private String endCreatStrDate;
	/** 借款ID */
	private Long loanId;
	/** 借款人ID */
	private Long personId;
	/** 借款人姓名 */
	private String personName;
	/** 借款人账户类型 */
	private Integer accountType;
	/** 银行账号 */
	private String bankAccount;
	/** 银行名称 */
	private String bankName;
	/** 有无担保 */
	private Integer hasGuarantee;
	/** 担保人ＩＤ */
	private Long guaranteeId;
	/** 担保人姓名 */
	private String guaranteeName;
	/** 担保人银行账号 */
	private String guaranteeBankAccount;
	/** 担保人银行名称 */
	private String guaranteeBankName;
	/** 应报金额 */
	private BigDecimal receivableAmount;
	/** 实报金额 */
	private BigDecimal offerAmount;
	/** 发送报盘时间 */
	private Date sendDate;
	/** 划扣状态 */
	private Integer status;
	/** 客服ID */
	private Long tellerId;
	/** 营业网点ID */
	private Long salesDeptId;
	/** 备注 */
	private String remark;
	/** 第三方类型 */
	private Integer tppType;

	private String personIdnum;

	private Integer handleState;
	/** 返回信息 */
	private String returnInfo;
	/** 报盘开始日期 */
	private Date sendDateStart;
	/** 报盘结束日期 */
	private Date sendDateEnd;
	/** 营业网点列表 */
	private List<Long> deptList;
	/** 状态列表 */
	private List<Integer> statusList;
	/** 产品列表 */
	private List<Long> productIds;
	/** 创建时间开始 */
	private Date createdTimeStart;
	/** 创建时间结束 */
	private Date createdTimeEnd;
	/** 返回结果 */
	private String returnCode;
	
	/** 过滤条件:实报金额大于0 */
	private BigDecimal amountCriteria;
	/** 分页PG */
	private Pager pager = new Pager();
	/**报盘类型 0定时 1实时**/
	private Integer offerType;
	/**回盘金额*/
	private BigDecimal successAmount;
	
	private List<String> returnCodeList;
	/**产品类型*/
	private Integer productId;
	/**失败原因*/
	private String failReasonType;
	
	/** ID列表 */
	private List<Long> idList;
	
	/**放款营业部*/
	private String salesDeptName;

	 /**城市Id */
	private Long cityId;
	
	private String plFlag;
	
	
	public String getPlFlag() {
		return plFlag;
	}

	public void setPlFlag(String plFlag) {
		this.plFlag = plFlag;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public String getSalesDeptName() {
		return salesDeptName;
	}

	public void setSalesDeptName(String salesDeptName) {
		this.salesDeptName = salesDeptName;
	}

	public String getStartCreatStrDate() {
		return startCreatStrDate;
	}

	public void setStartCreatStrDate(String startCreatStrDate) {
		this.startCreatStrDate = startCreatStrDate;
	}

	public String getEndCreatStrDate() {
		return endCreatStrDate;
	}

	public void setEndCreatStrDate(String endCreatStrDate) {
		this.endCreatStrDate = endCreatStrDate;
	}

	public String getStartStrDate() {
		return startStrDate;
	}

	public void setStartStrDate(String startStrDate) {
		this.startStrDate = startStrDate;
	}

	public String getEndStrDate() {
		return endStrDate;
	}

	public void setEndStrDate(String endStrDate) {
		this.endStrDate = endStrDate;
	}

	public Integer getOfferType() {
		return offerType;
	}

	public void setOfferType(Integer offerType) {
		this.offerType = offerType;
	}

	public BigDecimal getSuccessAmount() {
		return successAmount;
	}

	public void setSuccessAmount(BigDecimal successAmount) {
		this.successAmount = successAmount;
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

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public Integer getAccountType() {
		return accountType;
	}

	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public Integer getHasGuarantee() {
		return hasGuarantee;
	}

	public void setHasGuarantee(Integer hasGuarantee) {
		this.hasGuarantee = hasGuarantee;
	}

	public Long getGuaranteeId() {
		return guaranteeId;
	}

	public void setGuaranteeId(Long guaranteeId) {
		this.guaranteeId = guaranteeId;
	}

	public String getGuaranteeName() {
		return guaranteeName;
	}

	public void setGuaranteeName(String guaranteeName) {
		this.guaranteeName = guaranteeName;
	}

	public String getGuaranteeBankAccount() {
		return guaranteeBankAccount;
	}

	public void setGuaranteeBankAccount(String guaranteeBankAccount) {
		this.guaranteeBankAccount = guaranteeBankAccount;
	}

	public String getGuaranteeBankName() {
		return guaranteeBankName;
	}

	public void setGuaranteeBankName(String guaranteeBankName) {
		this.guaranteeBankName = guaranteeBankName;
	}

	public BigDecimal getReceivableAmount() {
		return receivableAmount;
	}

	public void setReceivableAmount(BigDecimal receivableAmount) {
		this.receivableAmount = receivableAmount;
	}

	public BigDecimal getOfferAmount() {
		return offerAmount;
	}

	public void setOfferAmount(BigDecimal offerAmount) {
		this.offerAmount = offerAmount;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getTellerId() {
		return tellerId;
	}

	public void setTellerId(Long tellerId) {
		this.tellerId = tellerId;
	}

	public Long getSalesDeptId() {
		return salesDeptId;
	}

	public void setSalesDeptId(Long salesDeptId) {
		this.salesDeptId = salesDeptId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<Long> getDeptList() {
		return deptList;
	}

	public void setDeptList(List<Long> deptList) {
		this.deptList = deptList;
	}

	public List<Integer> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<Integer> statusList) {
		this.statusList = statusList;
	}

	public String getPersonIdnum() {
		return personIdnum;
	}

	public void setPersonIdnum(String personIdnum) {
		this.personIdnum = personIdnum;
	}

	public Integer getHandleState() {
		return handleState;
	}

	public void setHandleState(Integer handleState) {
		this.handleState = handleState;
	}

	public List<Long> getProductIds() {
		return productIds;
	}

	public void setProductIds(List<Long> productIds) {
		this.productIds = productIds;
	}

	public Integer getTppType() {
		return tppType;
	}

	public void setTppType(Integer tppType) {
		this.tppType = tppType;
	}

	public String getReturnInfo() {
		return returnInfo;
	}

	public void setReturnInfo(String returnInfo) {
		this.returnInfo = returnInfo;
	}

	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}

	public Date getSendDateStart() {
		return sendDateStart;
	}

	public void setSendDateStart(Date sendDateStart) {
		this.sendDateStart = sendDateStart;
	}

	public Date getSendDateEnd() {
		return sendDateEnd;
	}

	public void setSendDateEnd(Date sendDateEnd) {
		this.sendDateEnd = sendDateEnd;
	}

	public Date getCreatedTimeStart() {
		return createdTimeStart;
	}

	public void setCreatedTimeStart(Date createdTimeStart) {
		this.createdTimeStart = createdTimeStart;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public BigDecimal getAmountCriteria() {
		return amountCriteria;
	}

	public void setAmountCriteria(BigDecimal amountCriteria) {
		this.amountCriteria = amountCriteria;
	}

	public Date getCreatedTimeEnd() {
		return createdTimeEnd;
	}

	public void setCreatedTimeEnd(Date createdTimeEnd) {
		this.createdTimeEnd = createdTimeEnd;
	}

	public List<String> getReturnCodeList() {
		return returnCodeList;
	}

	public void setReturnCodeList(List<String> returnCodeList) {
		this.returnCodeList = returnCodeList;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getFailReasonType() {
		return failReasonType;
	}

	public void setFailReasonType(String failReasonType) {
		this.failReasonType = failReasonType;
	}

	public List<Long> getIdList() {
		return idList;
	}

	public void setIdList(List<Long> idList) {
		this.idList = idList;
	}


}