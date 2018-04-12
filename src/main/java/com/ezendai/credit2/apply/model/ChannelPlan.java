package com.ezendai.credit2.apply.model;

import java.math.BigDecimal;
import java.util.Date;

import com.ezendai.credit2.framework.model.BaseModel;

public class ChannelPlan extends BaseModel{
    /**
	 * 
	 */
	private static final long serialVersionUID = 8775921550583234713L;

	// 方案代码
	private String code;
	
	// 方案名称
	private String name;
	
	// 开售日期
	private Date startDate;
	
	// 停售日期
	private Date endDate;
	
	// 机构代码
	private Long organizationId;
	
	// 审核人ID
	private Long approverId;
	
	//保证金
	private BigDecimal margin;

	//备注
	private String memo;

	//操作员ID
	private Long operatorId;

	//机构还款期限
	private Integer orgRepayTerm;

	//合同金额
	private BigDecimal pactMoney;
	
	//方案状态
	private String planState;

	//方案类型
	private String planType;

	//费用合计
	private BigDecimal rateSum;

	//申请金额上限
	private BigDecimal requestMoney;

	//一期每期还款金额
	private BigDecimal returneterm1;

	//二期每期还款金额
	private BigDecimal returneterm2;

	//退回原因
	private String sendBackMemo;

	//申请期数
	private Integer time;

	//一期止月
	private Integer toTerm1;

	//二期止月
	private Integer toTerm2;

	//月综合费率
	private BigDecimal actualRate;
	
	//机构是否承担服务费
	private String orgFeeState;
	
	//还款类型
	private Integer returnType;
	
	//是否已删除
	private Integer isDeleted;
	
	// 机构名称
	private String organname;

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

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getMargin() {
        return margin;
    }

    public void setMargin(BigDecimal margin) {
        this.margin = margin;
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

    public String getPlanState() {
        return planState;
    }

    public void setPlanState(String planState) {
        this.planState = planState;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
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

    public Integer getToTerm2() {
        return toTerm2;
    }

    public void setToTerm2(Integer toTerm2) {
        this.toTerm2 = toTerm2;
    }

    public BigDecimal getActualRate() {
        return actualRate;
    }

    public void setActualRate(BigDecimal actualRate) {
        this.actualRate = actualRate;
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

	public String getOrganname() {
		return organname;
	}

	public void setOrganname(String organname) {
		this.organname = organname;
	}
}