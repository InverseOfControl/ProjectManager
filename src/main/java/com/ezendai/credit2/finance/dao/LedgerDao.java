package com.ezendai.credit2.finance.dao;

import java.util.List;

import com.ezendai.credit2.finance.model.Ledger;
import com.ezendai.credit2.framework.dao.BaseDao;

public interface LedgerDao extends BaseDao<Ledger> {
	
	/**
	 * <pre>
	 * 根据accountId获取该accountId对应的总账信息 
	 * </pre>
	 *
	 * @param accountId
	 * @return
	 */
	Ledger findLedgerByAccountId(Long accountId);
	/**
	 * 
	 * <pre>
	 * 根据还款日获取借款金额大于0的借款ID
	 * </pre>
	 *
	 * @param returnDate
	 * @return
	 */
	List<Long> getAccountByReturnDate(Integer returnDate);
}
