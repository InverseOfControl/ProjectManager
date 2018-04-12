/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.finance.vo;

import java.math.BigDecimal;
import java.util.List;

import com.ezendai.credit2.framework.vo.BaseVO;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author liyuepeng
 * @version $Id: LedgerVO.java, v 0.1 2014-9-11 下午01:57:12 liyuepeng Exp $
 */
public class LedgerVO extends BaseVO {
	
	/**  */
	private static final long serialVersionUID = -736249395832982071L;
	/** 账号(借款ID) */
	private Long accountId;
	/** 账号类型 */
	private Integer type;
	/** 现金 */
	private BigDecimal cash;
	/** 应收利息 */
	private BigDecimal interestReceivable;
	/** 应收罚息 */
	private BigDecimal fineReceivable;
	/** 其他应收款 */
	private BigDecimal otherReceivable;
	/** 贷款本金 */
	private BigDecimal loanAmount;
	/** 应付利息 */
	private BigDecimal interestPayable;
	/** 应付罚息 */
	private BigDecimal finePayable;
	/** 其他应付款 */
	private BigDecimal otherPayable;
	/** 逾期罚息收入 */
	private BigDecimal overdueInterestIncome;
	/** 咨询费收入 */
	private BigDecimal consultIncome;
	/** 管理费收入 */
	private BigDecimal manageIncome;
	/** 评估费收入 */
	private BigDecimal assessmentFeeIncome;
	/** 违约金收入 */
	private BigDecimal penaltyIncome;
	/** 其他营业收入 */
	private BigDecimal otherIncome;
	/** 营业外收入 */
	private BigDecimal nonbusinessIncome;
	/** 利息支出 */
	private BigDecimal interestExpense;
	/** 逾期罚息支出 */
	private BigDecimal overdueInterestExpense;
	/** 咨询费支出 */
	private BigDecimal consultExpense;
	/** 评估费支出 */
	private BigDecimal assessmentFeeExpense;
	/** 管理费支出 */
	private BigDecimal manageExpense;
	/** 违约金支出 */
	private BigDecimal penaltyExpense;
	/** 其他营业支出 */
	private BigDecimal otherExpense;
	/** 营业外支出 */
	private BigDecimal nonbusinessExpense;
	/**利息收入*/
	private BigDecimal interestIncome;
	/** 备注 */
	private String remark;
	
	private List<Long> accountList;

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public BigDecimal getCash() {
		return cash;
	}

	public void setCash(BigDecimal cash) {
		this.cash = cash;
	}

	public BigDecimal getInterestReceivable() {
		return interestReceivable;
	}

	public void setInterestReceivable(BigDecimal interestReceivable) {
		this.interestReceivable = interestReceivable;
	}

	public BigDecimal getFineReceivable() {
		return fineReceivable;
	}

	public void setFineReceivable(BigDecimal fineReceivable) {
		this.fineReceivable = fineReceivable;
	}

	public BigDecimal getOtherReceivable() {
		return otherReceivable;
	}

	public void setOtherReceivable(BigDecimal otherReceivable) {
		this.otherReceivable = otherReceivable;
	}

	public BigDecimal getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}

	public BigDecimal getInterestPayable() {
		return interestPayable;
	}

	public void setInterestPayable(BigDecimal interestPayable) {
		this.interestPayable = interestPayable;
	}

	public BigDecimal getFinePayable() {
		return finePayable;
	}

	public void setFinePayable(BigDecimal finePayable) {
		this.finePayable = finePayable;
	}

	public BigDecimal getOtherPayable() {
		return otherPayable;
	}

	public void setOtherPayable(BigDecimal otherPayable) {
		this.otherPayable = otherPayable;
	}

	public BigDecimal getOverdueInterestIncome() {
		return overdueInterestIncome;
	}

	public void setOverdueInterestIncome(BigDecimal overdueInterestIncome) {
		this.overdueInterestIncome = overdueInterestIncome;
	}

	public BigDecimal getConsultIncome() {
		return consultIncome;
	}

	public void setConsultIncome(BigDecimal consultIncome) {
		this.consultIncome = consultIncome;
	}

	public BigDecimal getManageIncome() {
		return manageIncome;
	}

	public void setManageIncome(BigDecimal manageIncome) {
		this.manageIncome = manageIncome;
	}

	public BigDecimal getAssessmentFeeIncome() {
		return assessmentFeeIncome;
	}

	public void setAssessmentFeeIncome(BigDecimal assessmentFeeIncome) {
		this.assessmentFeeIncome = assessmentFeeIncome;
	}

	public BigDecimal getPenaltyIncome() {
		return penaltyIncome;
	}

	public void setPenaltyIncome(BigDecimal penaltyIncome) {
		this.penaltyIncome = penaltyIncome;
	}

	public BigDecimal getOtherIncome() {
		return otherIncome;
	}

	public void setOtherIncome(BigDecimal otherIncome) {
		this.otherIncome = otherIncome;
	}

	public BigDecimal getNonbusinessIncome() {
		return nonbusinessIncome;
	}

	public void setNonbusinessIncome(BigDecimal nonbusinessIncome) {
		this.nonbusinessIncome = nonbusinessIncome;
	}

	public BigDecimal getInterestExpense() {
		return interestExpense;
	}

	public void setInterestExpense(BigDecimal interestExpense) {
		this.interestExpense = interestExpense;
	}

	public BigDecimal getOverdueInterestExpense() {
		return overdueInterestExpense;
	}

	public void setOverdueInterestExpense(BigDecimal overdueInterestExpense) {
		this.overdueInterestExpense = overdueInterestExpense;
	}

	public BigDecimal getConsultExpense() {
		return consultExpense;
	}

	public void setConsultExpense(BigDecimal consultExpense) {
		this.consultExpense = consultExpense;
	}

	public BigDecimal getAssessmentFeeExpense() {
		return assessmentFeeExpense;
	}

	public void setAssessmentFeeExpense(BigDecimal assessmentFeeExpense) {
		this.assessmentFeeExpense = assessmentFeeExpense;
	}

	public BigDecimal getManageExpense() {
		return manageExpense;
	}

	public void setManageExpense(BigDecimal manageExpense) {
		this.manageExpense = manageExpense;
	}

	public BigDecimal getPenaltyExpense() {
		return penaltyExpense;
	}

	public void setPenaltyExpense(BigDecimal penaltyExpense) {
		this.penaltyExpense = penaltyExpense;
	}

	public BigDecimal getOtherExpense() {
		return otherExpense;
	}

	public void setOtherExpense(BigDecimal otherExpense) {
		this.otherExpense = otherExpense;
	}

	public BigDecimal getNonbusinessExpense() {
		return nonbusinessExpense;
	}

	public void setNonbusinessExpense(BigDecimal nonbusinessExpense) {
		this.nonbusinessExpense = nonbusinessExpense;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<Long> getAccountList() {
		return accountList;
	}

	public void setAccountList(List<Long> accountList) {
		this.accountList = accountList;
	}

	public BigDecimal getInterestIncome() {
		return interestIncome;
	}

	public void setInterestIncome(BigDecimal interestIncome) {
		this.interestIncome = interestIncome;
	}
	

}
