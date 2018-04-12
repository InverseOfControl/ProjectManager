package com.ezendai.credit2.report.model;

import java.math.BigDecimal;
import java.util.Date;

import com.ezendai.credit2.framework.model.BaseModel;

public class RepaymentReport extends BaseModel {
	private static final long serialVersionUID = -1213539893334060836L;
	Integer repaymentType;//明细种类
	Integer productId;//产品ID
	Integer productType;//借款类型
	Date repayDateStart;//实际还款日-开始
	Date repayDateEnd;//实际还款日-结束
	Integer salesDeptId;//营业网点
	String salesDeptName;//营业网点名称
	String personName;
	String idnum;
	BigDecimal pactMoney;
	Integer time;
	Integer curNum;
	Date repayDay;
	BigDecimal repayAmount;
	BigDecimal principalAmt;
	BigDecimal interestAmt;
	BigDecimal evalRate;
	BigDecimal referRate;
	BigDecimal managePart0Fee;
	BigDecimal managePart1Fee;
	BigDecimal risk;
	Date tradeTime;
	Integer contractSrc;
	BigDecimal tradeAmount;
	String tradeCode;
	Integer payType;
	BigDecimal tradeAmountBegin;
	BigDecimal reliefAmount;
	BigDecimal penaltyInterestAmt;
	BigDecimal overdueInterestAmt;
	BigDecimal overduePrincipal;
	BigDecimal curInterestAmt;
	BigDecimal curPrincipal;
	BigDecimal penalty;
	BigDecimal curEvalRate;
	BigDecimal curReferRate;
	BigDecimal curManagePart0Fee;
	BigDecimal curManagePart1Fee;
	BigDecimal curRisk;
	BigDecimal overdueCurEvalRate;
	BigDecimal overdueCurReferRate;
	BigDecimal overdueCurManagePart0Fee;
	BigDecimal overdueCurManagePart1Fee;
	BigDecimal overdueCurRisk;
	BigDecimal tradeAmountEnd;
	String batchNo;
	String remark;
	String constractSrc;
	BigDecimal curRefundPart0Fee;
	
	
	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getSalesDeptName() {
		return salesDeptName;
	}

	public void setSalesDeptName(String salesDeptName) {
		this.salesDeptName = salesDeptName;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
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

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public Integer getCurNum() {
		return curNum;
	}

	public void setCurNum(Integer curNum) {
		this.curNum = curNum;
	}

	public Date getRepayDay() {
		return repayDay;
	}

	public void setRepayDay(Date repayDay) {
		this.repayDay = repayDay;
	}

	public BigDecimal getRepayAmount() {
		return repayAmount;
	}

	public void setRepayAmount(BigDecimal repayAmount) {
		this.repayAmount = repayAmount;
	}

	public BigDecimal getPrincipalAmt() {
		return principalAmt;
	}

	public void setPrincipalAmt(BigDecimal principalAmt) {
		this.principalAmt = principalAmt;
	}

	public BigDecimal getInterestAmt() {
		return interestAmt;
	}

	public void setInterestAmt(BigDecimal interestAmt) {
		this.interestAmt = interestAmt;
	}

	public Date getTradeTime() {
		return tradeTime;
	}

	public void setTradeTime(Date tradeTime) {
		this.tradeTime = tradeTime;
	}

	public BigDecimal getTradeAmount() {
		return tradeAmount;
	}

	public void setTradeAmount(BigDecimal tradeAmount) {
		this.tradeAmount = tradeAmount;
	}

	public String getTradeCode() {
		return tradeCode;
	}

	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public BigDecimal getTradeAmountBegin() {
		return tradeAmountBegin;
	}

	public void setTradeAmountBegin(BigDecimal tradeAmountBegin) {
		this.tradeAmountBegin = tradeAmountBegin;
	}

	public BigDecimal getReliefAmount() {
		return reliefAmount;
	}

	public void setReliefAmount(BigDecimal reliefAmount) {
		this.reliefAmount = reliefAmount;
	}

	public BigDecimal getPenaltyInterestAmt() {
		return penaltyInterestAmt;
	}

	public void setPenaltyInterestAmt(BigDecimal penaltyInterestAmt) {
		this.penaltyInterestAmt = penaltyInterestAmt;
	}

	public BigDecimal getOverdueInterestAmt() {
		return overdueInterestAmt;
	}

	public void setOverdueInterestAmt(BigDecimal overdueInterestAmt) {
		this.overdueInterestAmt = overdueInterestAmt;
	}

	public BigDecimal getOverduePrincipal() {
		return overduePrincipal;
	}

	public void setOverduePrincipal(BigDecimal overduePrincipal) {
		this.overduePrincipal = overduePrincipal;
	}

	public BigDecimal getCurInterestAmt() {
		return curInterestAmt;
	}

	public void setCurInterestAmt(BigDecimal curInterestAmt) {
		this.curInterestAmt = curInterestAmt;
	}

	public BigDecimal getCurPrincipal() {
		return curPrincipal;
	}

	public void setCurPrincipal(BigDecimal curPrincipal) {
		this.curPrincipal = curPrincipal;
	}

	public BigDecimal getPenalty() {
		return penalty;
	}

	public void setPenalty(BigDecimal penalty) {
		this.penalty = penalty;
	}

	public BigDecimal getReferRate() {
		return referRate;
	}

	public void setReferRate(BigDecimal referRate) {
		this.referRate = referRate;
	}

	public BigDecimal getManagePart0Fee() {
		return managePart0Fee;
	}

	public void setManagePart0Fee(BigDecimal managePart0Fee) {
		this.managePart0Fee = managePart0Fee;
	}

	public BigDecimal getManagePart1Fee() {
		return managePart1Fee;
	}

	public void setManagerPart1Fee(BigDecimal managePart1Fee) {
		this.managePart1Fee = managePart1Fee;
	}

	public BigDecimal getTradeAmountEnd() {
		return tradeAmountEnd;
	}

	public void setTradeAmountEnd(BigDecimal tradeAmountEnd) {
		this.tradeAmountEnd = tradeAmountEnd;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getRepaymentType() {
		return repaymentType;
	}

	public void setRepaymentType(Integer repaymentType) {
		this.repaymentType = repaymentType;
	}

	public Date getRepayDateStart() {
		return repayDateStart;
	}

	public void setRepayDateStart(Date repayDateStart) {
		this.repayDateStart = repayDateStart;
	}

	public Date getRepayDateEnd() {
		return repayDateEnd;
	}

	public void setRepayDateEnd(Date repayDateEnd) {
		this.repayDateEnd = repayDateEnd;
	}

	public Integer getSalesDeptId() {
		return salesDeptId;
	}

	public void setSalesDeptId(Integer salesDeptId) {
		this.salesDeptId = salesDeptId;
	}

	public Integer getContractSrc() {
		return contractSrc;
	}

	public void setContractSrc(Integer contractSrc) {
		this.contractSrc = contractSrc;
	}
	public BigDecimal getEvalRate() {
		return evalRate;
	}

	public void setEvalRate(BigDecimal evalRate) {
		this.evalRate = evalRate;
	}

	public BigDecimal getRisk() {
		return risk;
	}

	public void setRisk(BigDecimal risk) {
		this.risk = risk;
	}

	public String getConstractSrc() {
		return constractSrc;
	}

	public void setConstractSrc(String constractSrc) {
		this.constractSrc = constractSrc;
	}

	public BigDecimal getCurEvalRate() {
		return curEvalRate;
	}

	public void setCurEvalRate(BigDecimal curEvalRate) {
		this.curEvalRate = curEvalRate;
	}

	public BigDecimal getCurReferRate() {
		return curReferRate;
	}

	public void setCurReferRate(BigDecimal curReferRate) {
		this.curReferRate = curReferRate;
	}

	public BigDecimal getCurManagePart0Fee() {
		return curManagePart0Fee;
	}

	public void setCurManagePart0Fee(BigDecimal curManagePart0Fee) {
		this.curManagePart0Fee = curManagePart0Fee;
	}

	public BigDecimal getCurManagePart1Fee() {
		return curManagePart1Fee;
	}

	public void setCurManagePart1Fee(BigDecimal curManagePart1Fee) {
		this.curManagePart1Fee = curManagePart1Fee;
	}

	public BigDecimal getCurRisk() {
		return curRisk;
	}

	public void setCurRisk(BigDecimal curRisk) {
		this.curRisk = curRisk;
	}

	public BigDecimal getOverdueCurEvalRate() {
		return overdueCurEvalRate;
	}

	public void setOverdueCurEvalRate(BigDecimal overdueCurEvalRate) {
		this.overdueCurEvalRate = overdueCurEvalRate;
	}

	public BigDecimal getOverdueCurReferRate() {
		return overdueCurReferRate;
	}

	public void setOverdueCurReferRate(BigDecimal overdueCurReferRate) {
		this.overdueCurReferRate = overdueCurReferRate;
	}

	public BigDecimal getOverdueCurManagePart0Fee() {
		return overdueCurManagePart0Fee;
	}

	public void setOverdueCurManagePart0Fee(BigDecimal overdueCurManagePart0Fee) {
		this.overdueCurManagePart0Fee = overdueCurManagePart0Fee;
	}

	public BigDecimal getOverdueCurManagePart1Fee() {
		return overdueCurManagePart1Fee;
	}

	public void setOverdueCurManagePart1Fee(BigDecimal overdueCurManagePart1Fee) {
		this.overdueCurManagePart1Fee = overdueCurManagePart1Fee;
	}

	public BigDecimal getOverdueCurRisk() {
		return overdueCurRisk;
	}

	public void setOverdueCurRisk(BigDecimal overdueCurRisk) {
		this.overdueCurRisk = overdueCurRisk;
	}

	public void setManagePart1Fee(BigDecimal managePart1Fee) {
		this.managePart1Fee = managePart1Fee;
	}

	public Integer getProductType() {
		return productType;
	}

	public void setProductType(Integer productType) {
		this.productType = productType;
	}

	public BigDecimal getCurRefundPart0Fee() {
		return curRefundPart0Fee;
	}

	public void setCurRefundPart0Fee(BigDecimal curRefundPart0Fee) {
		this.curRefundPart0Fee = curRefundPart0Fee;
	}
	
}
