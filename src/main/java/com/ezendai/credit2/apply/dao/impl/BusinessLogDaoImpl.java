package com.ezendai.credit2.apply.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.apply.dao.BusinessLogDao;
import com.ezendai.credit2.apply.model.BusinessLog;
import com.ezendai.credit2.apply.vo.BusinessLogVO;
import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;

@Repository
public class BusinessLogDaoImpl extends BaseDaoImpl<BusinessLog> implements BusinessLogDao {

	@Override
	public Integer getCountByVO(BusinessLogVO businessLogVO) {
		Object count = getSqlSession().selectOne(getIbatisMapperNameSpace() + ".count", businessLogVO);
		int totalCount = Integer.parseInt(count.toString());
		return totalCount;
	}

	@Override
	public List<BusinessLog> getLogByVO(BusinessLogVO businessLogVO) {
		// TODO Auto-generated method stub
		return  getSqlSession().selectList(getIbatisMapperNameSpace() + ".getLogByVO", businessLogVO);
	}
}
