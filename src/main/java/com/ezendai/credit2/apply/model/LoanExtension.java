/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.apply.model;

import com.ezendai.credit2.framework.model.BaseModel;

/**
 * <pre>
 * 借款展期关联类
 * </pre>
 *
 * @author zhangshihai
 * @version $Id: LoanExtension.java, v 0.1 2015年3月10日 上午11:12:30 00221921 Exp $
 */
public class LoanExtension extends BaseModel{
	/**  */
	private static final long serialVersionUID = -3266138455267355123L;
	
	/** 借款ID */
	private Long loanId;
	
	/** 展期借款ID */
	private Long extensionId;

	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public Long getExtensionId() {
		return extensionId;
	}

	public void setExtensionId(Long extensionId) {
		this.extensionId = extensionId;
	}
	
}
