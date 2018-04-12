package com.ezendai.credit2.report.dao.impl;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.report.dao.OverdueMsgLogDao;
import com.ezendai.credit2.report.model.OverdueMsgLog;
import com.ezendai.credit2.report.vo.OverdueMsgLogVO;
@Repository
public class OverdueMsgLogDaoImpl extends BaseDaoImpl<OverdueMsgLog> implements OverdueMsgLogDao{

	@Override
	public Pager findWithPG(OverdueMsgLogVO OverdueMsgLogVO) {
		// TODO Auto-generated method stub
		return findWithPg(OverdueMsgLogVO);
	}

}
