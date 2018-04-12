package com.ezendai.credit2.sign.lcb.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.ezendai.credit2.apply.model.PushZonganFraud;
import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;
import com.ezendai.credit2.sign.lcb.dao.ZonganRiskDao;
@Repository
public class ZonganRiskDaoImpl extends BaseDaoImpl<PushZonganFraud> implements ZonganRiskDao{

	@Override
	public void insertZonganReturnData(Map<String, Object> map) {
		System.out.println(getIbatisMapperNameSpace());
		getSqlSession().insert(getIbatisMapperNameSpace()+".insertZonganReturnData",map);
		
	}

	@Override
	public Integer findLoanIdAndStatus(String id) {
		return getSqlSession().selectOne(getIbatisMapperNameSpace()+".findLoanIdAndStatus",id);
	}

	@Override
	public Map<String, Object> getZongAnReturnData(String loanID) {
		return getSqlSession().selectOne(getIbatisMapperNameSpace()+".getZongAnReturnData",loanID);
	}

	@Override
	public Map<String, Object> findLoanMoneyNoBelongTo(String idNo, String laonId) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("idNo", idNo);
		map.put("laonId", laonId);
		return getSqlSession().selectOne(getIbatisMapperNameSpace()+".findLoanMoneyNoBelongTo",map);
	}

}
