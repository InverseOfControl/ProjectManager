/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.apply.vo;

import java.util.List;

import com.ezendai.credit2.framework.vo.BaseVO;

/**
 * <pre>
 * 借款展期关联类
 * </pre>
 *
 * @author zhangshihai
 * @version $Id: LoanExtensionVO.java, v 0.1 2015年3月10日 上午11:16:29 00221921 Exp $
 */
public class LoanExtensionVO extends BaseVO{
	/**  */
	private static final long serialVersionUID = -9069651242647508548L;

	/** 借款ID */
	private Long loanId;
	
	/** 展期借款ID */
	private Long extensionId;
	
	private List<Long> idList;

	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public Long getExtensionId() {
		return extensionId;
	}

	public List<Long> getIdList() {
		return idList;
	}

	public void setIdList(List<Long> idList) {
		this.idList = idList;
	}

	public void setExtensionId(Long extensionId) {
		this.extensionId = extensionId;
	}
}
