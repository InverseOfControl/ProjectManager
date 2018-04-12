package com.ezendai.credit2.after.vo;

import com.ezendai.credit2.framework.vo.BaseVO;

/**
 * <pre>
 * </pre>
 * @Description:
 */
public class LoanOnlineTestVO extends BaseVO {
	
	/**  */
	private static final long serialVersionUID = 4997223191851133751L;
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