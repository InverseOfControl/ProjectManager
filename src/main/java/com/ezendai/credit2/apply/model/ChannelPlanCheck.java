/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.apply.model;

import java.math.BigDecimal;
import java.util.Date;

import com.ezendai.credit2.framework.model.BaseModel;
 

/**
 * <pre>
 * 
 * </pre>
 *
 * @author liyuepeng
 * @version $Id: RhannelPlanCheckVO.java, v 0.1 2014-9-10 上午11:19:30 liyuepeng Exp $
 */
public class ChannelPlanCheck extends BaseModel{
  
	 
	/**
	 * 
	 */
	private static final long serialVersionUID = -8505576799442671752L;

	/** 批复序号 */
	private Long id;
	
	/** 审批操作人ID */
	private Long approverId;
 
	/** 方案代码 */
	private String code;
	/** 方案类型 */
	private String planType;	
	
	/** 创建时间 */
	private Date dateCreated;
	
	/** 停售日期*/
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
	/**	方案ID*/
	private Long plan_id ;
	/**	还款方式*/
	private Integer returnType	;
	/**	机构内部编号*/
	private String orgCode	;
	
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	private String creator;
	
	private Integer isDeleted;
	
	private String orgFeeState;
	private String modifier;
	private Date createdTime	;
	private Date modifiedTime	;
	private Long creatorId	;
	private Long modifierId	;
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
 
	public String getOrgFeeState() {
		return orgFeeState;
	}
	public void setOrgFeeState(String orgFeeState) {
		this.orgFeeState = orgFeeState;
	}
	public String getModifier() {
		return modifier;
	}
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public Date getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	public Long getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}
	public Long getModifierId() {
		return modifierId;
	}
	public void setModifierId(Long modifierId) {
		this.modifierId = modifierId;
	}   
	public Integer getReturnType() {
		return returnType;
	}
	public void setReturnType(Integer returnType) {
		this.returnType = returnType;
	}
	/**	内部机构编号*/
	private String orgNum ;
	/**	机构名称*/
	private String orgName ;
	/**	开售日期字符*/
	private String startDateStr ; 
	public String getStartDateStr() {
		return startDateStr;
	}
	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}
	public String getEndDateStr() {
		return endDateStr;
	}
	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}
	/**	停售日期字符*/
	private String endDateStr ; 
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
	public String getPlanType() {
		return planType;
	}
	public void setPlanType(String planType) {
		this.planType = planType;
	}
	public Integer getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

}
