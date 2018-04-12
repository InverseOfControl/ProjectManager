package com.ezendai.credit2.audit.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezendai.credit2.audit.assembler.SyncRepaymentMsgAssembler;
import com.ezendai.credit2.audit.dao.SyncRepaymentMsgDao;
import com.ezendai.credit2.audit.model.SyncRepaymentMsg;
import com.ezendai.credit2.audit.service.SyncRepaymentMsgService;
import com.ezendai.credit2.audit.vo.SyncRepaymentMsgVO;
import com.ezendai.credit2.framework.util.Pager;

@Service
public class SyncRepaymentMsgServiceImpl implements SyncRepaymentMsgService{

	@Autowired
	SyncRepaymentMsgDao syncRepaymentMsgDao;
	
	@Transactional
	@Override
	public int insertSelective(List<SyncRepaymentMsg> syncRepaymentMsgList) {
		// 增量添加
		int insertCount = 0;
		for(SyncRepaymentMsg syncRepaymentMsg:syncRepaymentMsgList){
			SyncRepaymentMsgVO syncRepaymentMsgVO=new SyncRepaymentMsgVO();
			syncRepaymentMsgVO.setRepaymentId(syncRepaymentMsg.getRepaymentId());
			if(syncRepaymentMsgDao.count(syncRepaymentMsgVO) == 0){
				syncRepaymentMsgDao.insert(syncRepaymentMsg);
				insertCount++;
			}
		}
		return insertCount;
	}
	
	
	public int count(SyncRepaymentMsgVO syncRepaymentMsgVO){
		return syncRepaymentMsgDao.count(syncRepaymentMsgVO);
	}


	@Override
	public List<SyncRepaymentMsg> getRepaymentMsgList(
			SyncRepaymentMsgVO syncRepaymentMsgVO) {
		// TODO Auto-generated method stub
		return syncRepaymentMsgDao.findListByVo(syncRepaymentMsgVO);
	}


	@Override
	public int update(SyncRepaymentMsgVO syncRepaymentMsgVO) {
		// TODO Auto-generated method stub
		return syncRepaymentMsgDao.update(syncRepaymentMsgVO);
	}


	@Override
	public Pager getRepaymentMsgListWithPg(SyncRepaymentMsgVO syncRepaymentMsgVO) {
		// TODO Auto-generated method stub
		return syncRepaymentMsgDao.findWithPg(syncRepaymentMsgVO);
	}

}
