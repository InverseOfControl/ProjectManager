package com.ezendai.credit2.master.model;

import com.ezendai.credit2.framework.model.BaseModel;

/**
 * 拒绝原因模型
 * 
 * @author chenqi
 * @version 1.0, 2014-08-26
 * @since 1.0
 */
public class RejectReason extends BaseModel {

	private static final long serialVersionUID = 3968517694643253950L;
	/** 拒绝原因 */
	private String reason;
	/** 原因解释*/
	private String explain;
	/** 拒绝分类*/
	private String type;
	/**父原因id*/
	private Long parentId;
	/**排序*/
	private Integer levelOrder;
	/**贷款类型*/
	private Integer loanType;
	/**对所有产品有效*/
	private Integer toAllProduct;
	/**多久时间可以再申请*/
	private Integer canRequestDays;
	/**是否逻辑删除*/
	private Integer isDeleted;
	/**备注*/
	private String remark;

	/**父节点名称*/
	private RejectReason parent;

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Integer getLevelOrder() {
		return levelOrder;
	}

	public void setLevelOrder(Integer levelOrder) {
		this.levelOrder = levelOrder;
	}

	public Integer getLoanType() {
		return loanType;
	}

	public void setLoanType(Integer loanType) {
		this.loanType = loanType;
	}

	public Integer getToAllProduct() {
		return toAllProduct;
	}

	public void setToAllProduct(Integer toAllProduct) {
		this.toAllProduct = toAllProduct;
	}

	public Integer getCanRequestDays() {
		return canRequestDays;
	}

	public void setCanRequestDays(Integer canRequestDays) {
		this.canRequestDays = canRequestDays;
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

	public RejectReason getParent() {
		return parent;
	}

	public void setParent(RejectReason parent) {
		this.parent = parent;
	}

}
