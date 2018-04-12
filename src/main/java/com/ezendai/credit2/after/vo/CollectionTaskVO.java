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
public class CollectionTaskVO  extends BaseVO {

	private static final long serialVersionUID = -5200601342962759617L;
	private Long caseId;
	private Integer taskState;
	private Integer taskType;
	private String taskMemo;
	private BigDecimal promisedRepayMent;	
	private BigDecimal factRepayment;
	private Date taskEndDate;
	private Long operatorId;
	



	private Date taskStartDate;
	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}
	public Long getCaseId() {
		return caseId;
	}

	public void setCaseId(Long caseId) {
		this.caseId = caseId;
	}

	public Integer getTaskState() {
		return taskState;
	}

	public void setTaskState(Integer taskState) {
		this.taskState = taskState;
	}

	public Integer getTaskType() {
		return taskType;
	}

	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}

	public String getTaskMemo() {
		return taskMemo;
	}

	public void setTaskMemo(String taskMemo) {
		this.taskMemo = taskMemo;
	}

	public BigDecimal getPromisedRepayMent() {
		return promisedRepayMent;
	}

	public void setPromisedRepayMent(BigDecimal promisedRepayMent) {
		this.promisedRepayMent = promisedRepayMent;
	}

	public BigDecimal getFactRepayment() {
		return factRepayment;
	}

	public void setFactRepayment(BigDecimal factRepayment) {
		this.factRepayment = factRepayment;
	}

	public Date getTaskEndDate() {
		return taskEndDate;
	}

	public void setTaskEndDate(Date taskEndDate) {
		this.taskEndDate = taskEndDate;
	}

	

	public Date getTaskStartDate() {
		return taskStartDate;
	}

	public void setTaskStartDate(Date taskStartDate) {
		this.taskStartDate = taskStartDate;
	}
 
	
	 
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
	 
	/** 贷款状态 */
	private Integer status;
	 
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

 
}
