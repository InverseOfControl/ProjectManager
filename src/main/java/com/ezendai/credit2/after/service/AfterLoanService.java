package com.ezendai.credit2.after.service;

import com.ezendai.credit2.finance.model.RepayInfo;

/***
 * 
 * @author liyuepeng
 *
 */
public interface AfterLoanService {

	/**
	 * 还款
	 * @param repayInfo
	 * @return
	 */
	public boolean repayDeal(RepayInfo repayInfo);
	
	/**
	 * 
	 * <pre>
	 * 展期还款
	 * </pre>
	 *
	 * @param repayInfo
	 * @return
	 */
	public boolean repayDealExtension(RepayInfo repayInfo);
}
