/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.apply.vo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.ezendai.credit2.framework.vo.BaseVO;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author liyuepeng
 * @version $Id: RhannelPlanCheckVO.java, v 0.1 2014-9-10 上午11:19:30 liyuepeng Exp $
 */
public class ChannelPlanCheckVO extends BaseVO{
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 7228264122512969109L;

	/** 批复序号 */
	private Long id;
	/** 审批操作人ID */
	private Long approverId;
 
	/** 方案代码 */
	private String code;
	
	/** 创建时间 */
	private Date dateCreated;
	
	/** 停售日期*/
	@DateTimeFormat(pattern="yyyy-MM-dd") 
	private Date endDate;
	
	/**利率 */
	private BigDecimal interestRate;
	
	/** 最后更新时间 */
	private Date lastUpdate;
	
	/** 保证金 */
	private BigDecimal margin;
	
	/** 保证金比率 */
	private BigDecimal marginRate;
	
	/** 备注*/
	private String memo	;
	/** 方案名称*/
	private String name	;
	/**操作员ID	 */
	private Long operatorId;
	
	/** 机构服务费承担比率*/
	private BigDecimal orgFeeRatio;
		
	/** 机构还款期限 */
	private Integer orgRepayTerm;
	
	/**所属机构ID*/
	private Long organizationId;
	
	/**	合同金额 */
	private BigDecimal pactMoney;
	/**	方案状态 */
	private Integer planState;

	/**	费用合计 */
	private BigDecimal rateSum	;
	
	/**	申请金额上限 */
	private BigDecimal requestMoney	;
	
	/**	一期每期还款金额*/
	private BigDecimal returneterm1	;
	/**		二期每期还款金额*/
	private BigDecimal returneterm2	;
	/**	退回原因*/
	private String sendBackMemo		;
	/**		开售日期*/
	@DateTimeFormat(pattern="yyyy-MM-dd") 
	private Date startDate	;
	/**	申请期数*/
	private Integer time	;
	/**	一期止月*/
	private Integer toTerm1	;
	/**	月综合费率*/
	private BigDecimal actualRate	;
	/**	二期止月*/
	private Integer toTerm2	;
	/**	审核状态*/
	private Integer approverState	;
	/**		方案ID*/
	private Long plan_id	;

	//机构是否承担服务费
	private String orgFeeState;
	
	//还款类型
	private Integer returnType;
	
	//是否已删除
	private Integer isDeleted;
	
	private String planType;
	
	/** 机构名称 */
	private String orgName;
 
	/** 机构 内部编码*/
	private String orgNum;
	public String getOrgNum() {
		return orgNum;
	}
	public void setOrgNum(String orgNum) {
		this.orgNum = orgNum;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getApproverId() {
		return approverId;
	}
	public void setApproverId(Long approverId) {
		this.approverId = approverId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public BigDecimal getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(BigDecimal interestRate) {
		this.interestRate = interestRate;
	}
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public BigDecimal getMargin() {
		return margin;
	}
	public void setMargin(BigDecimal margin) {
		this.margin = margin;
	}
	public BigDecimal getMarginRate() {
		return marginRate;
	}
	public void setMarginRate(BigDecimal marginRate) {
		this.marginRate = marginRate;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}
	public BigDecimal getOrgFeeRatio() {
		return orgFeeRatio;
	}
	public void setOrgFeeRatio(BigDecimal orgFeeRatio) {
		this.orgFeeRatio = orgFeeRatio;
	}
	public Integer getOrgRepayTerm() {
		return orgRepayTerm;
	}
	public void setOrgRepayTerm(Integer orgRepayTerm) {
		this.orgRepayTerm = orgRepayTerm;
	}
	public Long getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}
	public BigDecimal getPactMoney() {
		return pactMoney;
	}
	public void setPactMoney(BigDecimal pactMoney) {
		this.pactMoney = pactMoney;
	}
	public Integer getPlanState() {
		return planState;
	}
	public void setPlanState(Integer planState) {
		this.planState = planState;
	}
	public BigDecimal getRateSum() {
		return rateSum;
	}
	public void setRateSum(BigDecimal rateSum) {
		this.rateSum = rateSum;
	}
	public BigDecimal getRequestMoney() {
		return requestMoney;
	}
	public void setRequestMoney(BigDecimal requestMoney) {
		this.requestMoney = requestMoney;
	}
	public BigDecimal getReturneterm1() {
		return returneterm1;
	}
	public void setReturneterm1(BigDecimal returneterm1) {
		this.returneterm1 = returneterm1;
	}
	public BigDecimal getReturneterm2() {
		return returneterm2;
	}
	public void setReturneterm2(BigDecimal returneterm2) {
		this.returneterm2 = returneterm2;
	}
	public String getSendBackMemo() {
		return sendBackMemo;
	}
	public void setSendBackMemo(String sendBackMemo) {
		this.sendBackMemo = sendBackMemo;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Integer getTime() {
		return time;
	}
	public void setTime(Integer time) {
		this.time = time;
	}
	public Integer getToTerm1() {
		return toTerm1;
	}
	public void setToTerm1(Integer toTerm1) {
		this.toTerm1 = toTerm1;
	}
	public BigDecimal getActualRate() {
		return actualRate;
	}
	public void setActualRate(BigDecimal actualRate) {
		this.actualRate = actualRate;
	}
	public Integer getToTerm2() {
		return toTerm2;
	}
	public void setToTerm2(Integer toTerm2) {
		this.toTerm2 = toTerm2;
	}
	public Integer getApproverState() {
		return approverState;
	}
	public void setApproverState(Integer approverState) {
		this.approverState = approverState;
	}
	public Long getPlan_id() {
		return plan_id;
	}
	public void setPlan_id(Long plan_id) {
		this.plan_id = plan_id;
	}
	 
	public String getOrgFeeState() {
		return orgFeeState;
	}
	public void setOrgFeeState(String orgFeeState) {
		this.orgFeeState = orgFeeState;
	}
	public Integer getReturnType() {
		return returnType;
	}
	public void setReturnType(Integer returnType) {
		this.returnType = returnType;
	}
	public Integer getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}
	public String getPlanType() {
		return planType;
	}
	public void setPlanType(String planType) {
		this.planType = planType;
	}
}
