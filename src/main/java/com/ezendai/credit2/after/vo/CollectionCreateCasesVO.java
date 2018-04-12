package com.ezendai.credit2.after.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.ezendai.credit2.framework.vo.BaseVO;

/**
 * 
 * @author zhuyiguo
 * @version $Id: LoanVO.java, v 0.1 2014年6月26日 上午9:13:51 zhuyiguo Exp $
 */
public class CollectionCreateCasesVO  extends BaseVO {

	private static final long serialVersionUID = -5200601342962759617L;

	private Long taskId;
	private Long recordType;
	private Long LoanId;
	private Long caseId;
	
	/** 客户信息ID */
	private Long personId;
	/** 贷款类型 */
	private Integer loanType;
	/** 签约日期 */
	private Date signDate;
	 
	/** 借款期限 */
	private Integer time;
 
	/** 首期还款日 */
	private Date startRepayDate;
	/** 结束还款日 */
	private Date endRepayDate;
	/** 放款时间 */
	private Date grantDate;
	
	/** 逾期时间 */
	private Date orderDate;
	 
	private String personName;
	/**移交代码 */
	private String transferCode;
	private String caseFrom;
	private Long productId;
	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getCaseFrom() {
		return caseFrom;
	}

	public void setCaseFrom(String caseFrom) {
		this.caseFrom = caseFrom;
	}
	/** 贷款状态 */
	private Integer status;
	
	/** 贷款状态 */
	private List<Integer> statusList;
	 
	/** 备注 */
	private String remark;
 
	/**案件状态 */
	private String caseState;
	private String caseType;
	private String caseMemo;
	
	
	private String idNum;
	
	/** 案件开始时间 */
	private String casesStartDate;
	/** 案件结束时间 */
	
	private String casesEndDate;
/** 案件结束时间 */
	
	private Date caseEndDate;
	/** 移交时间 */
	private Date transferDate;
	public Date getTransferDate() {
		return transferDate;
	}

	public void setTransferDate(Date transferDate) {
		this.transferDate = transferDate;
	}

	public Date getCaseEndDate() {
	return caseEndDate;
}

public void setCaseEndDate(Date caseEndDate) {
	this.caseEndDate = caseEndDate;
}
	/** 营业部 */
	
	private String salesDeptId;
	
	/** 营业部 */
	
	private List<Long> salesDeptIdList;
	private Integer closedType;
	
	private String withoutNoSend;
	
	
	private Integer productType;
	private List<Long> productTypeList;
	
	 public List<Long> getProductTypeList() {
		return productTypeList;
	}

	public void setProductTypeList(List<Long> productTypeList) {
		this.productTypeList = productTypeList;
	}
	//逾期起始日
      public Date overdueStartDate;
	//逾期本息和
      public BigDecimal overduePrincipalInterestSum;
      //罚息
      public BigDecimal fine;
      //应还总额
      public BigDecimal repayAllAmount;
      
      
      public String overdueStart;
      public String overdueEnd;
      public String collectors;
      public String deptSerch;
      
      
    
	private String sort;
    private String order;
    private String pinyin;
    public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public String getSort() {
  		return sort;
  	}

  	public void setSort(String sort) {
  		this.sort = sort;
  	}

  	public String getOrder() {
  		return order;
  	}

  	public void setOrder(String order) {
  		this.order = order;
  	}
    public String getDeptSerch() {
		return deptSerch;
	}

	public void setDeptSerch(String deptSerch) {
		this.deptSerch = deptSerch;
	}

	public String getCollectors() {
		return collectors;
	}

	public void setCollectors(String collectors) {
		this.collectors = collectors;
	}

	public String getOverdueStart() {
		return overdueStart;
	}

	public void setOverdueStart(String overdueStart) {
		this.overdueStart = overdueStart;
	}

	public String getOverdueEnd() {
		return overdueEnd;
	}

	public void setOverdueEnd(String overdueEnd) {
		this.overdueEnd = overdueEnd;
	}

	public Date getOverdueStartDate() {
  		return overdueStartDate;
  	}

  	public void setOverdueStartDate(Date overdueStartDate) {
  		this.overdueStartDate = overdueStartDate;
  	}

  	public BigDecimal getOverduePrincipalInterestSum() {
  		return overduePrincipalInterestSum;
  	}

  	public void setOverduePrincipalInterestSum(
  			BigDecimal overduePrincipalInterestSum) {
  		this.overduePrincipalInterestSum = overduePrincipalInterestSum;
  	}

  	public BigDecimal getFine() {
  		return fine;
  	}

  	public void setFine(BigDecimal fine) {
  		this.fine = fine;
  	}

  	public BigDecimal getRepayAllAmount() {
  		return repayAllAmount;
  	}

  	public void setRepayAllAmount(BigDecimal repayAllAmount) {
  		this.repayAllAmount = repayAllAmount;
  	}
	public Long getLoanId() {
		return LoanId;
	}

	public void setLoanId(Long loanId) {
		LoanId = loanId;
	}
	public Integer getClosedType() {
		return closedType;
	}

	public void setClosedType(Integer closedType) {
		this.closedType = closedType;
	}

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}
	private List<Long>	idList;
	
	public List<Long> getIdList() {
		return idList;
	}

	public void setIdList(List<Long> idList) {
		this.idList = idList;
	}
	private Long operatorId;
	private List<Long> operatorIdList;
	public List<Long> getOperatorIdList() {
		return operatorIdList;
	}

	public void setOperatorIdList(List<Long> operatorIdList) {
		this.operatorIdList = operatorIdList;
	}
	private String caseStateList;
	public String getCaseStateList() {
		return caseStateList;
	}

	public void setCaseStateList(String caseStateList) {
		this.caseStateList = caseStateList;
	}

	public String getSalesDeptId() {
		return salesDeptId;
	}

	public void setSalesDeptId(String salesDeptId) {
		this.salesDeptId = salesDeptId;
	}

	public String getCasesStartDate() {
		return casesStartDate;
	}

	public void setCasesStartDate(String casesStartDate) {
		this.casesStartDate = casesStartDate;
	}

	public String getCasesEndDate() {
		return casesEndDate;
	}

	public void setCasesEndDate(String casesEndDate) {
		this.casesEndDate = casesEndDate;
	}
	
	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public Integer getLoanType() {
		return loanType;
	}

	public void setLoanType(Integer loanType) {
		this.loanType = loanType;
	}

	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
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

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getTransferCode() {
		return transferCode;
	}

	public void setTransferCode(String transferCode) {
		this.transferCode = transferCode;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCaseState() {
		return caseState;
	}

	public void setCaseState(String caseState) {
		this.caseState = caseState;
	}
	
	 
	 

	public Integer getStatusFlag() {
		return statusFlag;
	}

	public void setStatusFlag(Integer statusFlag) {
		this.statusFlag = statusFlag;
	}
	private Integer statusFlag;
	public String getIdNum() {
		return idNum;
	}

	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

 
	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Long getRecordType() {
		return recordType;
	}

	public void setRecordType(Long recordType) {
		this.recordType = recordType;
	}
	public String getCaseType() {
		return caseType;
	}

	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}
	public String getCaseMemo() {
		return caseMemo;
	}

	public void setCaseMemo(String caseMemo) {
		this.caseMemo = caseMemo;
	}

	public Integer getProductType() {
		return productType;
	}

	public void setProductType(Integer productType) {
		this.productType = productType;
	}

	public List<Integer> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<Integer> statusList) {
		this.statusList = statusList;
	}

	public List<Long> getSalesDeptIdList() {
		return salesDeptIdList;
	}

	public void setSalesDeptIdList(List<Long> salesDeptIdList) {
		this.salesDeptIdList = salesDeptIdList;
	}

	public String getWithoutNoSend() {
		return withoutNoSend;
	}

	public void setWithoutNoSend(String withoutNoSend) {
		this.withoutNoSend = withoutNoSend;
	}
	public Long getCaseId() {
		return caseId;
	}

	public void setCaseId(Long caseId) {
		this.caseId = caseId;
	}
}
