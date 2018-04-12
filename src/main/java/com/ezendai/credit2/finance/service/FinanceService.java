/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.finance.service;

import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.audit.model.ApproveResult;


/**
 * <pre>
 * 
 * </pre>
 *
 * @author liyuepeng
 * @version $Id: FinanceService.java, v 0.1 2014-9-10 下午03:28:42 liyuepeng Exp $
 */
public interface FinanceService {
	
	/**
	 * 财务放款
	 * @param Loan
	 */
	void makeLoan(Loan loan);
	
	/**
	 * 财务放款退回
	 * @param approveResult
	 * @return
	 */
	void financeReturn(ApproveResult approveResult, Integer borrowStatus);

}
