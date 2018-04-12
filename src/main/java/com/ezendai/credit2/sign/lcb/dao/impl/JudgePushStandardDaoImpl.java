package com.ezendai.credit2.sign.lcb.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.model.LoanManage;
import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;
import com.ezendai.credit2.sign.lcb.dao.JudgePushStandardDao;

@Repository
public class JudgePushStandardDaoImpl extends BaseDaoImpl<LoanManage> implements JudgePushStandardDao{

	@Override
	public Map<String, Object> findLonaById(Long id) {
		System.out.println(getIbatisMapperNameSpace());
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("id", id);
		return getSqlSession().selectOne(getIbatisMapperNameSpace()+".findLonaById",map);
	}

}
