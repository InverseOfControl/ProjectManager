package com.ezendai.credit2.after.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.after.dao.SMSConfigrationDao;
import com.ezendai.credit2.after.model.SMSConfigration;
import com.ezendai.credit2.after.vo.SMSConfigrationVO;
import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;
import com.ezendai.credit2.framework.util.Pager;
@Repository
public class SMSConfigrationDaoImpl extends BaseDaoImpl<SMSConfigration> implements SMSConfigrationDao{

	@Override
	public List<SMSConfigrationVO> getCityList() {
		// TODO Auto-generated method stub
		return getSqlSession().selectList(getIbatisMapperNameSpace()+".getCitys");
	}

	@Override
	public Pager getSmsConfigrationList(SMSConfigrationVO smsConfigrationVO) {
		// TODO Auto-generated method stub
		return findWithPg(smsConfigrationVO);
	}

	@Override
	public int plUpdCityPhone(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return getSqlSession().update(getIbatisMapperNameSpace()+".plUpdCityPhone", map);
	}

	@Override
	public int addCityPhone(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return getSqlSession().insert(getIbatisMapperNameSpace()+".addCityPhone", map);
	}

	@Override
	public int checkPhoneIsExit(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne(getIbatisMapperNameSpace()+".checkPhoneIsExit", map);
	}

}
