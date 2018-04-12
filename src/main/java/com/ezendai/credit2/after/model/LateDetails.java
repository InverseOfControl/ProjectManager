package com.ezendai.credit2.after.model;

import java.math.BigDecimal;
import java.util.Date;

import com.ezendai.credit2.framework.model.BaseModel;

/**   
*    
* 项目名称：credit2-report   
* 类名称：   
* 类描述：   
* 创建人：Administrator   
* 创建时间：2015年11月4日 下午5:43:57   
* 修改人：Administrator   
* 修改时间：2015年11月4日 下午5:43:57   
* 修改备注：   
* @version    
*    
*/
public class LateDetails extends BaseModel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7440979648760821226L;
	 private Long loanId ;
	 private Long taskId ;
	public Long getLoanId() {
		return loanId;
	}
	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}
	/**营业部*/   
	private String salesDept;
	//产品类型
	private String productName;
	//客户姓名
    private String personName;
    //客户经理工号
    private String managerCode;
    //业务主任
  	private String directorName;
    //业务主任工号
  	private String directorCode;
    //身份证号码
    private String idNum ;
    //签约日期
    private Date signDate;
    //应还款日
    private Integer returnDate;
    //合同金额
    private BigDecimal pactMoney;
    //逾期起始日
    private Date overdueStartDate;
    //逾期期数
    private Integer lateCurNum;
    //逾期天数
    private Integer overdue;
    //剩余本金
    private BigDecimal residualPactMoney;
    //逾期本金
    private BigDecimal overduePrincipal;
    //逾期利息
    private BigDecimal overdueInterestAmt;
    //罚息起算日
    private Date penaltyDate;
    //罚息金额
    private BigDecimal repayInterest;
    //还款总额
    private BigDecimal totalAmount;
    //最后一次还款金额
    private BigDecimal lastRepayAmount;
    //最后一次还款日期
    private Date lastFactReturnDate;
    //最后一期应还款日期
    private Date endRepayDate;
   //逾期类型
    private String overdueType;
    // 跟踪情况/逾期状态
    private String tracking;
   
    //客户经理
    private String managerName;
    //初审员
    private String firstTirialName;
    //终审员
    private String finstTirialName;
    //客服
    private String serviceName;
    
	//机构名称
	private String organName;
	//合同来源
  	private String contractSrc;
	//业主类型
  	private String professionType;
	
  	 //借款次数
    private Integer loanNumber;
	
    //单笔借款次数
    private Integer singleLoanNumber;
    //上笔结清时间
    private Date lastTime;
    //信用记录
    private String creditRecord;
    //逾期超180天
    private String overDay;
	private String   collectors;
    private String   collectorsLoginName;
    public String getCollectors() {
  		return collectors;
  	}
  	public void setCollectors(String collectors) {
  		this.collectors = collectors;
  	}
  	public String getCollectorsLoginName() {
  		return collectorsLoginName;
  	}
  	public void setCollectorsLoginName(String collectorsLoginName) {
  		this.collectorsLoginName = collectorsLoginName;
  	}
    public String getSalesDept() {
		return salesDept;
	}
	public void setSalesDept(String salesDept) {
		this.salesDept = salesDept;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public String getManagerCode() {
		return managerCode;
	}
	public void setManagerCode(String managerCode) {
		this.managerCode = managerCode;
	}
	public String getDirectorName() {
		return directorName;
	}
	public void setDirectorName(String directorName) {
		this.directorName = directorName;
	}
	public String getDirectorCode() {
		return directorCode;
	}
	public void setDirectorCode(String directorCode) {
		this.directorCode = directorCode;
	}
	public String getIdNum() {
		return idNum;
	}
	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}
	public Date getSignDate() {
		return signDate;
	}
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}
	public Integer getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(Integer returnDate) {
		this.returnDate = returnDate;
	}
	public BigDecimal getPactMoney() {
		return pactMoney;
	}
	public void setPactMoney(BigDecimal pactMoney) {
		this.pactMoney = pactMoney;
	}
	public Date getOverdueStartDate() {
		return overdueStartDate;
	}
	public void setOverdueStartDate(Date overdueStartDate) {
		this.overdueStartDate = overdueStartDate;
	}
	public Integer getLateCurNum() {
		return lateCurNum;
	}
	public void setLateCurNum(Integer lateCurNum) {
		this.lateCurNum = lateCurNum;
	}
	public Integer getOverdue() {
		return overdue;
	}
	public void setOverdue(Integer overdue) {
		this.overdue = overdue;
	}
	public BigDecimal getResidualPactMoney() {
		return residualPactMoney;
	}
	public void setResidualPactMoney(BigDecimal residualPactMoney) {
		this.residualPactMoney = residualPactMoney;
	}
	public BigDecimal getOverduePrincipal() {
		return overduePrincipal;
	}
	public void setOverduePrincipal(BigDecimal overduePrincipal) {
		this.overduePrincipal = overduePrincipal;
	}
	public BigDecimal getOverdueInterestAmt() {
		return overdueInterestAmt;
	}
	public void setOverdueInterestAmt(BigDecimal overdueInterestAmt) {
		this.overdueInterestAmt = overdueInterestAmt;
	}
	public Date getPenaltyDate() {
		return penaltyDate;
	}
	public void setPenaltyDate(Date penaltyDate) {
		this.penaltyDate = penaltyDate;
	}
	public BigDecimal getRepayInterest() {
		return repayInterest;
	}
	public void setRepayInterest(BigDecimal repayInterest) {
		this.repayInterest = repayInterest;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public BigDecimal getLastRepayAmount() {
		return lastRepayAmount;
	}
	public void setLastRepayAmount(BigDecimal lastRepayAmount) {
		this.lastRepayAmount = lastRepayAmount;
	}
	public Date getLastFactReturnDate() {
		return lastFactReturnDate;
	}
	public void setLastFactReturnDate(Date lastFactReturnDate) {
		this.lastFactReturnDate = lastFactReturnDate;
	}
	public Date getEndRepayDate() {
		return endRepayDate;
	}
	public void setEndRepayDate(Date endRepayDate) {
		this.endRepayDate = endRepayDate;
	}
	public String getOverdueType() {
		return overdueType;
	}
	public void setOverdueType(String overdueType) {
		this.overdueType = overdueType;
	}
	public String getTracking() {
		return tracking;
	}
	public void setTracking(String tracking) {
		this.tracking = tracking;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public String getFirstTirialName() {
		return firstTirialName;
	}
	public void setFirstTirialName(String firstTirialName) {
		this.firstTirialName = firstTirialName;
	}
	public String getFinstTirialName() {
		return finstTirialName;
	}
	public void setFinstTirialName(String finstTirialName) {
		this.finstTirialName = finstTirialName;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getOrganName() {
		return organName;
	}
	public void setOrganName(String organName) {
		this.organName = organName;
	}
	public String getContractSrc() {
		return contractSrc;
	}
	public void setContractSrc(String contractSrc) {
		this.contractSrc = contractSrc;
	}
	public String getProfessionType() {
		return professionType;
	}
	public void setProfessionType(String professionType) {
		this.professionType = professionType;
	}
	public Integer getLoanNumber() {
		return loanNumber;
	}
	public void setLoanNumber(Integer loanNumber) {
		this.loanNumber = loanNumber;
	}
	public Integer getSingleLoanNumber() {
		return singleLoanNumber;
	}
	public void setSingleLoanNumber(Integer singleLoanNumber) {
		this.singleLoanNumber = singleLoanNumber;
	}
	public Date getLastTime() {
		return lastTime;
	}
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
	public String getCreditRecord() {
		return creditRecord;
	}
	public void setCreditRecord(String creditRecord) {
		this.creditRecord = creditRecord;
	}
	public String getOverDay() {
		return overDay;
	}
	public void setOverDay(String overDay) {
		this.overDay = overDay;
	}
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

}
