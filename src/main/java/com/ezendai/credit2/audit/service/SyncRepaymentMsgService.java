package com.ezendai.credit2.audit.service;

import java.util.List;

import com.ezendai.credit2.audit.model.SyncRepaymentMsg;
import com.ezendai.credit2.audit.vo.SyncRepaymentMsgVO;
import com.ezendai.credit2.framework.util.Pager;

public interface SyncRepaymentMsgService {
	public int insertSelective(List<SyncRepaymentMsg> syncRepaymentMsgList);
	
	public int count(SyncRepaymentMsgVO syncRepaymentMsgVO);

	public List<SyncRepaymentMsg> getRepaymentMsgList(SyncRepaymentMsgVO syncRepaymentMsgVO);
	
	public int update(SyncRepaymentMsgVO syncRepaymentMsgVO);
	
	public Pager getRepaymentMsgListWithPg(SyncRepaymentMsgVO syncRepaymentMsgVO); 
}
