/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.finance.service;

import java.util.List;

import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.finance.model.RepayInfo;
import com.ezendai.credit2.finance.vo.RepayInfoVO;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author liyuepeng
 * @version $Id: RepayInfoService.java, v 0.1 2014-9-11 下午05:24:20 liyuepeng Exp $
 */
public interface RepayInfoService {

	public RepayInfo insert(RepayInfoVO repayInfoVo);
	
	/**
	 * 放款时，生成还款表
	 * @param loan
	 * @return
	 */
	public RepayInfo makeLoanCreateRepayInfo(Loan loan);
	
	public void delete(Long id);

	List<RepayInfo> findListByVO(RepayInfoVO repayInfoVo);
	
	/**生成当天放款交易流水号**/
	String generateLoanTradeNo();
	
	/**生成当天还款交易流水号**/
	String generatePayTradeNo();
}
