package com.ezendai.credit2.rule.vo;

import java.util.Date;
import java.util.List;

import com.ezendai.credit2.framework.vo.BaseVO;

/**
 * 
 * <pre>
 * 借款规则（还款日规则/特殊签单规则）
 * </pre>
 *
 * @author zhangshihai
 * @version $Id: LoanRuleVO.java, v 0.1 2014年8月22日 上午10:21:50 zhangshihai Exp $
 */
public class LoanRuleVO extends BaseVO {

	private static final long serialVersionUID = 7017255520690302577L;
	
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
	
	/** 查询条件->失效时间的开始日期 */
	private String overdueDateStartDate;
	
	/** 查条件 -> 失效时间的结束日期 */
	private String overdueDateEndDate;
	
	private List<Long> idList;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Date getExecuteTime() {
		return executeTime;
	}

	public void setExecuteTime(Date executeTime) {
		this.executeTime = executeTime;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<Long> getIdList() {
		return idList;
	}

	public void setIdList(List<Long> idList) {
		this.idList = idList;
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

	public String getOverdueDateStartDate() {
		return overdueDateStartDate;
	}

	public void setOverdueDateStartDate(String overdueDateStartDate) {
		this.overdueDateStartDate = overdueDateStartDate;
	}

	public String getOverdueDateEndDate() {
		return overdueDateEndDate;
	}

	public void setOverdueDateEndDate(String overdueDateEndDate) {
		this.overdueDateEndDate = overdueDateEndDate;
	}
	
}
