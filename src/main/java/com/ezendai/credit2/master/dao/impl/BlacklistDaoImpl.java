package com.ezendai.credit2.master.dao.impl;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;
import com.ezendai.credit2.master.dao.BlacklistDao;
import com.ezendai.credit2.master.model.Blacklist;
import com.ezendai.credit2.master.vo.BlacklistVO;

@Repository
public class BlacklistDaoImpl extends BaseDaoImpl<Blacklist> implements BlacklistDao {

	@Override
	public int updateLimitDays(BlacklistVO blacklistVO) {
		// TODO Auto-generated method stub
		return this.getSqlSession().update(getIbatisMapperNameSpace()+".updateLimitDays",blacklistVO);
	}

}
