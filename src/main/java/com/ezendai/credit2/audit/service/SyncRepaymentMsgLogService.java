package com.ezendai.credit2.audit.service;

import java.util.List;

import com.ezendai.credit2.audit.model.SyncRepaymentMsgLog;
import com.ezendai.credit2.audit.vo.SyncRepaymentMsgLogVO;
import com.ezendai.credit2.framework.util.Pager;

public interface SyncRepaymentMsgLogService {
	public int insertSelective(List<SyncRepaymentMsgLog> syncRepaymentMsgLogList);
	
	public void insert(SyncRepaymentMsgLog syncRepaymentMsgLog);
	
	public int count(SyncRepaymentMsgLogVO SyncRepaymentMsgLogVO);

	public List<SyncRepaymentMsgLog> getRepaymentMsgLogList(SyncRepaymentMsgLogVO syncRepaymentMsgLogVO);
	
	public int update(SyncRepaymentMsgLogVO syncRepaymentMsgLogVO);

	public Pager getRepaymentMsgLogListWithPg(
			SyncRepaymentMsgLogVO syncRepaymentMsgLogVO);
}
