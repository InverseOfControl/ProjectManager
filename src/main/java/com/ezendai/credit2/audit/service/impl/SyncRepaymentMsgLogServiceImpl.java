package com.ezendai.credit2.audit.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezendai.credit2.audit.assembler.SyncRepaymentMsgLogAssembler;
import com.ezendai.credit2.audit.dao.SyncRepaymentMsgLogDao;
import com.ezendai.credit2.audit.model.SyncRepaymentMsgLog;
import com.ezendai.credit2.audit.service.SyncRepaymentMsgLogService;
import com.ezendai.credit2.audit.vo.SyncRepaymentMsgLogVO;
import com.ezendai.credit2.framework.util.Pager;

@Service
public class SyncRepaymentMsgLogServiceImpl implements SyncRepaymentMsgLogService{

	@Autowired
	SyncRepaymentMsgLogDao syncRepaymentMsgLogDao;
	
	@Transactional
	@Override
	public int insertSelective(List<SyncRepaymentMsgLog> syncRepaymentMsgLogList) {
		// 增量添加
		int insertCount = 0;
		for(SyncRepaymentMsgLog syncRepaymentMsgLog:syncRepaymentMsgLogList){
			SyncRepaymentMsgLogVO syncRepaymentMsgLogVO=SyncRepaymentMsgLogAssembler.transferModel2VO(syncRepaymentMsgLog);
			if(syncRepaymentMsgLogDao.count(syncRepaymentMsgLogVO) == 0){
				syncRepaymentMsgLogDao.insert(syncRepaymentMsgLog);
				insertCount++;
			}
		}
		return insertCount;
	}
	
	
	public int count(SyncRepaymentMsgLogVO syncRepaymentMsgLogVO){
		return syncRepaymentMsgLogDao.count(syncRepaymentMsgLogVO);
	}


	@Override
	public List<SyncRepaymentMsgLog> getRepaymentMsgLogList(
			SyncRepaymentMsgLogVO syncRepaymentMsgLogVO) {
		// TODO Auto-generated method stub
		return syncRepaymentMsgLogDao.findListByVo(syncRepaymentMsgLogVO);
	}


	@Override
	public int update(SyncRepaymentMsgLogVO syncRepaymentMsgLogVO) {
		// TODO Auto-generated method stub
		return syncRepaymentMsgLogDao.update(syncRepaymentMsgLogVO);
	}


	@Override
	public void insert(SyncRepaymentMsgLog syncRepaymentMsgLog) {
		// TODO Auto-generated method stub
		syncRepaymentMsgLogDao.insert(syncRepaymentMsgLog);
	}


	@Override
	public Pager getRepaymentMsgLogListWithPg(
			SyncRepaymentMsgLogVO syncRepaymentMsgLogVO) {
		// TODO Auto-generated method stub
		return syncRepaymentMsgLogDao.findWithPg(syncRepaymentMsgLogVO);
	}


}
