package com.ezendai.credit2.audit.dao;

import com.ezendai.credit2.audit.model.SyncRepaymentMsgLog;
import com.ezendai.credit2.audit.vo.SyncRepaymentMsgLogVO;
import com.ezendai.credit2.framework.dao.BaseDao;

public interface SyncRepaymentMsgLogDao extends BaseDao<SyncRepaymentMsgLog> {
	public int count(SyncRepaymentMsgLogVO syncRepaymentMsgLogVO);
}
