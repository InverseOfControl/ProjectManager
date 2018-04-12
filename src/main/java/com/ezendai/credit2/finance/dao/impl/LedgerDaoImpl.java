package com.ezendai.credit2.finance.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.finance.dao.LedgerDao;
import com.ezendai.credit2.finance.model.Ledger;
import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;

@Repository
public class LedgerDaoImpl extends BaseDaoImpl<Ledger> implements LedgerDao {

	public Ledger findLedgerByAccountId(Long accountId) {
		return (Ledger) getSqlSession().selectOne(getIbatisMapperNameSpace() + ".findLedgerByAccountId", accountId);
	}
	/**
	 * 
	 * <pre>
	 * 根据还款日获取借款金额大于0的借款ID
	 * </pre>
	 *
	 * @param returnDate
	 * @return
	 */
	@Override
	public List<Long> getAccountByReturnDate(Integer returnDate){
		return  getSqlSession().selectList(getIbatisMapperNameSpace()+".getAccountByReturnDate", returnDate);
	}

}
