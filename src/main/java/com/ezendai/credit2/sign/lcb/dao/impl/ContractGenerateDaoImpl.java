package com.ezendai.credit2.sign.lcb.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.audit.model.Contract;
import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;
import com.ezendai.credit2.sign.lcb.dao.IContractGenerateDao;
import com.ezendai.credit2.sign.lcb.model.LcbBidPushModel;
import com.ezendai.credit2.sign.lcb.model.LcbModel;

@Repository
public class ContractGenerateDaoImpl extends BaseDaoImpl<Contract> implements IContractGenerateDao {
	
	@Override
	public LcbModel getPersonInfoByLoanId(String loanId) {
		return this.getSqlSession().selectOne("com.ezendai.credit2.lcb.mapper.ContractGenerateMapper.getPersonInfoByLoanId", loanId);
	}

	@Override
	public int updateLcbStatus(Map<String,Object> map) {
		return this.getSqlSession().update("com.ezendai.credit2.lcb.mapper.ContractGenerateMapper.updateLcbStatus", map);
	}

	@Override
	public Map<String, Object> getBankCodeById(String id) {
		return this.getSqlSession().selectOne("com.ezendai.credit2.lcb.mapper.ContractGenerateMapper.getBankCodeById", id);
	}

	@Override
	public LcbBidPushModel getBidPushData(String id) {
		return this.getSqlSession().selectOne("com.ezendai.credit2.lcb.mapper.ContractGenerateMapper.getBidPushData", id);
	}

	@Override
	public Map<String, Object> getLoanInfo(String id) {
		return this.getSqlSession().selectOne("com.ezendai.credit2.lcb.mapper.ContractGenerateMapper.getLoanInfo", id);
	}

	@Override
	public int insertLcbBorrowNo(Map<String, Object> map) {
		return this.getSqlSession().update("com.ezendai.credit2.lcb.mapper.ContractGenerateMapper.insertLcbBorrowNo", map);
	}

}
