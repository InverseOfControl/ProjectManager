package com.ezendai.credit2.rule.model;

import java.util.Date;

import com.ezendai.credit2.framework.model.BaseModel;

/**
 * 
 * <pre>
 * 借款规则（还款日规则/特殊签单规则）
 * </pre>
 *
 * @author HQ-AT6
 * @version $Id: LoanRule.java, v 0.1 2014年8月22日 上午9:49:38 HQ-AT6 Exp $
 */
public class LoanRule extends BaseModel {

	private static final long serialVersionUID = -4727577042741118887L;
	
	/** 规则名称 */
	private String name;

	/** 规则类型 */
	private Integer ruleType;

	/** 产品类型 */
	private Integer productType;

	/** 产品子类型 */
	private Integer productSubtype;

	/** 合同来源 */
	private Integer contractSrc;

	/** 还款日规则 */
	private Integer repaydateRule;

	/** 失效时间 */
	private Date overdueDate;

	/** 是否执行 */
	private Integer isExecuted;
	
	/** 执行时间 */
	private Date executeTime;

	/** 是否逻辑删除 */
	private Integer isDeleted;	
	/** 备注 */
	private String remark;	
	/** 控制是否update  productSubtype 为true 时候update */
	private Boolean productSubtypeNull;
	
	/** 控制是否update  executeTime 为true 时候update */
	private Boolean executeTimeNull;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name ;
	}

	public Integer getRuleType() {
		return ruleType;
	}

	public void setRuleType(Integer ruleType) {
		this.ruleType = ruleType;
	}

	public Integer getProductType() {
		return productType;
	}

	public void setProductType(Integer productType) {
		this.productType = productType;
	}

	public Integer getProductSubtype() {
		return productSubtype;
	}

	public void setProductSubtype(Integer productSubtype) {
		this.productSubtype = productSubtype;
	}

	public Integer getContractSrc() {
		return contractSrc;
	}

	public void setContractSrc(Integer contractSrc) {
		this.contractSrc = contractSrc;
	}

	public Integer getRepaydateRule() {
		return repaydateRule;
	}

	public void setRepaydateRule(Integer repaydateRule) {
		this.repaydateRule = repaydateRule;
	}

	public Date getOverdueDate() {
		return overdueDate;
	}

	public void setOverdueDate(Date overdueDate) {
		this.overdueDate = overdueDate;
	}

	public Integer getIsExecuted() {
		return isExecuted;
	}

	public void setIsExecuted(Integer isExecuted) {
		this.isExecuted = isExecuted;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Date getExecuteTime() {
		return executeTime;
	}

	public void setExecuteTime(Date executeTime) {
		this.executeTime = executeTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Boolean getProductSubtypeNull() {
		return productSubtypeNull;
	}

	public void setProductSubtypeNull(Boolean productSubtypeNull) {
		this.productSubtypeNull = productSubtypeNull;
	}

	public Boolean getExecuteTimeNull() {
		return executeTimeNull;
	}

	public void setExecuteTimeNull(Boolean executeTimeNull) {
		this.executeTimeNull = executeTimeNull;
	}
	
	
}