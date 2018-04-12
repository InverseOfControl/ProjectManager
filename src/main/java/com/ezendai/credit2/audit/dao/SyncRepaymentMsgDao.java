package com.ezendai.credit2.audit.dao;

import com.ezendai.credit2.audit.model.SyncRepaymentMsg;
import com.ezendai.credit2.audit.vo.SyncRepaymentMsgVO;
import com.ezendai.credit2.framework.dao.BaseDao;
import com.ezendai.credit2.framework.util.Pager;

public interface SyncRepaymentMsgDao extends BaseDao<SyncRepaymentMsg> {
	public int count(SyncRepaymentMsgVO syncRepaymentMsgVO);

}
