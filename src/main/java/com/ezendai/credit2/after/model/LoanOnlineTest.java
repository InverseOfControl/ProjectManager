package com.ezendai.credit2.after.model;

import com.ezendai.credit2.framework.model.BaseModel;

/**
 * <pre>
 * 线上测试
 * </pre>
 */
public class LoanOnlineTest extends BaseModel {
	/**  */
	private static final long serialVersionUID = -5161895154355368897L;
	/** 借款ID */
	private Long loanId;
	/** 营业网点 */
	private Long salesDeptId;

	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public Long getSalesDeptId() {
		return salesDeptId;
	}

	public void setSalesDeptId(Long salesDeptId) {
		this.salesDeptId = salesDeptId;
	}
	
}