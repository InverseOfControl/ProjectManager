package com.ezendai.credit2.after.service;

import com.ezendai.credit2.after.vo.CustomerManagerVO;
import com.ezendai.credit2.after.vo.RepayEntryDetailsVO;
import com.ezendai.credit2.finance.model.RepayInfo;
import com.ezendai.credit2.framework.util.Pager;

public interface CustomerManagerService {
	
	public Pager getCustomerManagerList(CustomerManagerVO cvo);
	public RepayEntryDetailsVO viewEdit(Long loanId) ;
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
