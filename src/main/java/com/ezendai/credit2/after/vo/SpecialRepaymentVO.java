package com.ezendai.credit2.after.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.ezendai.credit2.framework.vo.BaseVO;

public class SpecialRepaymentVO extends BaseVO{
	private static final long serialVersionUID = 1L;
	/** 借款Id */
	private Long loanId;
	/** 申请日期 */
	private Date requestDate;
	/** 申请类型 */
	private Integer type;
	/** 流程状态 */
	private Integer status;
	/** 申请人 */
	private String proposer;
	/** 申请金额 */
	private BigDecimal amount;
	/** 备注 */
	private String remark;
	/**还款类型列表**/
	private List<Integer> typeList;
	/**状态列表**/
	private List<Integer> statusList;
	/** 申请日期开始 */
	private Date requestDateStart;
	/** 约定还款日 */
	private Integer returnDate;
	/** 创建开始时间 */
	private Date createdTimeStart;
	/** 营业网点ID */
	private Long salesDeptId;
	/** 所在的状态*/
	private Integer notStatus;
	/**是否需要报盘 */
	private Integer needOffer;
	/**是否展期申请标志*/
	private Integer extensionFlag;
	
	/**姓名*/
	private String name;
	/**身份证*/
    private String idnum;
    /**合同金额*/
    private BigDecimal pactMoney;
    /**客服经理id*/
    private Long crmId;
    /**产品类型*/
    private Long productId;
    /**借款类型*/
    private Integer productType;
    /**手机号*/
    private String mobilePhone;
    /**借款期限*/
    private Long time;
	/** 借款状态 */
	private Integer loanStatus;
	/**展期时间*/
	private Integer extensionTime;
    /**当前期数*/
	private Integer currNum;
	
	/**过滤LOAN的状态列表**/
	private List<Integer> excludeStatusList;
    /**车架号*/
	private String frameNumber;
	
	private String plFlag;
	
	public String getPlFlag() {
		return plFlag;
	}
	public void setPlFlag(String plFlag) {
		this.plFlag = plFlag;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdnum() {
		return idnum;
	}
	public void setIdnum(String idnum) {
		this.idnum = idnum;
	}
	public BigDecimal getPactMoney() {
		return pactMoney;
	}
	public void setPactMoney(BigDecimal pactMoney) {
		this.pactMoney = pactMoney;
	}
	public Long getCrmId() {
		return crmId;
	}
	public void setCrmId(Long crmId) {
		this.crmId = crmId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Integer getProductType() {
		return productType;
	}
	public void setProductType(Integer productType) {
		this.productType = productType;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public List<Integer> getStatusList() {
		return statusList;
	}
	public void setStatusList(List<Integer> statusList) {
		this.statusList = statusList;
	}
	public List<Integer> getTypeList() {
		return typeList;
	}
	public void setTypeList(List<Integer> typeList) {
		this.typeList = typeList;
	}
	public Long getLoanId() {
		return loanId;
	}
	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}
	public Date getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getProposer() {
		return proposer;
	}
	public void setProposer(String proposer) {
		this.proposer = proposer;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getRequestDateStart() {
		return requestDateStart;
	}
	public void setRequestDateStart(Date requestDateStart) {
		this.requestDateStart = requestDateStart;
	}
	public Integer getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(Integer returnDate) {
		this.returnDate = returnDate;
	}
	public Date getCreatedTimeStart() {
		return createdTimeStart;
	}
	public void setCreatedTimeStart(Date createdTimeStart) {
		this.createdTimeStart = createdTimeStart;
	}
	public Long getSalesDeptId() {
		return salesDeptId;
	}
	public void setSalesDeptId(Long salesDeptId) {
		this.salesDeptId = salesDeptId;
	}
	public Integer getNotStatus() {
		return notStatus;
	}
	public void setNotStatus(Integer notStatus) {
		this.notStatus = notStatus;
	}
	public List<Integer> getExcludeStatusList() {
		return excludeStatusList;
	}
	public void setExcludeStatusList(List<Integer> excludeStatusList) {
		this.excludeStatusList = excludeStatusList;
	}
	
	public Integer getNeedOffer() {
		return needOffer;
	}
	public void setNeedOffer(Integer needOffer) {
		this.needOffer = needOffer;
	}
	public Integer getExtensionFlag() {
		return extensionFlag;
	}
	public void setExtensionFlag(Integer extensionFlag) {
		this.extensionFlag = extensionFlag;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public Integer getLoanStatus() {
		return loanStatus;
	}
	public void setLoanStatus(Integer loanStatus) {
		this.loanStatus = loanStatus;
	}
	public Integer getExtensionTime() {
		return extensionTime;
	}
	public void setExtensionTime(Integer extensionTime) {
		this.extensionTime = extensionTime;
	}
	public Integer getCurrNum() {
		return currNum;
	}
	public void setCurrNum(Integer currNum) {
		this.currNum = currNum;
	}
	public String getFrameNumber() {
		return frameNumber;
	}
	public void setFrameNumber(String frameNumber) {
		this.frameNumber = frameNumber;
	}
	
}
