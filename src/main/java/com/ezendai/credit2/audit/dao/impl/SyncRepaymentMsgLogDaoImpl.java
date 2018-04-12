package com.ezendai.credit2.audit.dao.impl;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.audit.dao.SyncRepaymentMsgLogDao;
import com.ezendai.credit2.audit.model.SyncRepaymentMsgLog;
import com.ezendai.credit2.audit.vo.SyncRepaymentMsgLogVO;
import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;

@Repository
public class SyncRepaymentMsgLogDaoImpl extends BaseDaoImpl<SyncRepaymentMsgLog> implements SyncRepaymentMsgLogDao {

	@Override
	public int count(SyncRepaymentMsgLogVO syncRepaymentMsgLogVO) {
		// TODO Auto-generated method stub
		Object count = getSqlSession().selectOne(getIbatisMapperNameSpace() + ".count", syncRepaymentMsgLogVO);
		int totalCount = Integer.parseInt(count.toString());
		return totalCount;
	}


}
