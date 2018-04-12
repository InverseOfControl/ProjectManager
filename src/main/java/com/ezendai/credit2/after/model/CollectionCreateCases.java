package com.ezendai.credit2.after.model;

 
import java.math.BigDecimal;
import java.util.Date;

 

import com.ezendai.credit2.framework.model.BaseModel;

public class CollectionCreateCases extends BaseModel {
	private static final long serialVersionUID = -5200601342962759617L;
	
	/** 客户信息ID */
	private Long personId;
	private Long productId;
	private Long tid;
	private Long caseId;
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
	private String caseMemo;
	
	/**移交代码 */
	private String transferCode;
	 
	/** 贷款状态 */
	private Integer status;
	/** 贷款状态 */
	private String statusName;
	 
	/** 备注 */
	private String remark;
	
	/**案件状态 */
	private String caseState;
	private String idNum;
	private Integer caseType;
 
	private String contractNo;
	private String deptName;
	private String transferDate;
	private Long loanId;
	
	private Date taskEndDate;
	private BigDecimal pactMoney;
	
	private BigDecimal factRepaymentSum;
	private Date requestDate;
	private Integer caseFrom;
	private Integer productType;
	private String caseNum;
	private String productName;
	private Date transferDateStr;
	
	private String productTypeName;
	 private Date caseEndDate;
	 public Date getCaseEndDate() {
		return caseEndDate;
	}

	public void setCaseEndDate(Date caseEndDate) {
		this.caseEndDate = caseEndDate;
	}
	//逾期起始日
    public String overdueStartDate;
	//逾期本息和
    public BigDecimal overduePrincipalInterestSum;
    //罚息
    public BigDecimal fine;
    //应还总额
    public BigDecimal repayAllAmount;
    
    private String taskStartDate;
    public Date getSbDate() {
		return sbDate;
	}

	public void setSbDate(Date sbDate) {
		this.sbDate = sbDate;
	}

	public BigDecimal getSbAmount() {
		return sbAmount;
	}

	public void setSbAmount(BigDecimal sbAmount) {
		this.sbAmount = sbAmount;
	}
	private Date sbDate;
    private BigDecimal sbAmount;
	private String optName;
	  public Long getProductId() {
			return productId;
		}

		public void setProductId(Long productId) {
			this.productId = productId;
		}

		public String getTaskStartDate() {
			return taskStartDate;
		}

		public void setTaskStartDate(String taskStartDate) {
			this.taskStartDate = taskStartDate;
		}

		public String getOptName() {
			return optName;
		}

		public void setOptName(String optName) {
			this.optName = optName;
		}
    public String getOverdueStartDate() {
		return overdueStartDate;
	}

	public void setOverdueStartDate(String overdueStartDate) {
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
	public Date getTransferDateStr() {
		return transferDateStr;
	}

	public void setTransferDateStr(Date transferDateStr) {
		this.transferDateStr = transferDateStr;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getPactMoney() {
		return pactMoney;
	}

	public void setPactMoney(BigDecimal pactMoney) {
		this.pactMoney = pactMoney;
	}
	public String getCaseNum() {
		return caseNum;
	}

	public void setCaseNum(String caseNum) {
		this.caseNum = caseNum;
	}

	public Date getTaskEndDate() {
		return taskEndDate;
	}

	public void setTaskEndDate(Date taskEndDate) {
		this.taskEndDate = taskEndDate;
	}

	public BigDecimal getFactRepaymentSum() {
		return factRepaymentSum;
	}

	public void setFactRepaymentSum(BigDecimal factRepaymentSum) {
		this.factRepaymentSum = factRepaymentSum;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public Integer getCaseFrom() {
		return caseFrom;
	}

	public void setCaseFrom(Integer caseFrom) {
		this.caseFrom = caseFrom;
	}

	public Integer getProductType() {
		return productType;
	}

	public void setProductType(Integer productType) {
		this.productType = productType;
	}
	public String getCaseMemo() {
		return caseMemo;
	}

	public void setCaseMemo(String caseMemo) {
		this.caseMemo = caseMemo;
	}
	public Long getCaseId() {
		return caseId;
	}

	public void setCaseId(Long caseId) {
		this.caseId = caseId;
	}
	public Integer getCaseType() {
		return caseType;
	}

	public void setCaseType(Integer caseType) {
		this.caseType = caseType;
	}
	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getTransferDate() {
		return transferDate;
	}

	public void setTransferDate(String transferDate) {
		this.transferDate = transferDate;
	}
	public String getIdNum() {
		return idNum;
	}

	public void setIdNum(String idNum) {
		this.idNum = idNum;
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
	 

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getProductTypeName() {
		return productTypeName;
	}

	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

 
	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}
  

}