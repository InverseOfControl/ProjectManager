/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.finance.service;

import java.io.OutputStream;
import java.util.List;

import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.audit.model.ApproveResult;

/***
 * 
 * <pre>
 * 财务审核
 * </pre>
 *
 * @author 陈忠星
 * @version $Id: FinancialAuditService.java, v 0.1 2014年9月11日 上午10:42:50 陈忠星 Exp $
 */
public interface FinancialAuditService {
	
	/***
	 * 
	 * <pre>
	 * 财务审核
	 * </pre>
	 *
	 * @param loanId
	 * @return
	 */
	String financialAudit(Long loanId);

	/**
	 * 财务审核退回
	 * @param approveResult
	 * @param borrowStatus 借款状态
	 * @return
	 */
	String financeReturn(ApproveResult approveResult, Integer borrowStatus);
	/***
	 * 
	 * <pre>
	 * 包装查询的数据后导出Excel
	 * </pre>
	 *
	 */
	void exportExcel(List<Loan> loanList,String sheetName, OutputStream os);	
	

}
