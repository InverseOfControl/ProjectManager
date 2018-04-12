/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.apply.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezendai.credit2.apply.dao.ChannelPlanCheckDao;
import com.ezendai.credit2.apply.dao.ChannelPlanDao;
import com.ezendai.credit2.apply.model.ChannelPlan;
import com.ezendai.credit2.apply.model.ChannelPlanCheck;
import com.ezendai.credit2.apply.service.ChannelPlanCheckService;
import com.ezendai.credit2.apply.vo.ChannelPlanCheckVO;
import com.ezendai.credit2.apply.vo.ChannelPlanVO;
import com.ezendai.credit2.framework.util.Pager;
 

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author  
 * @version   
 */
@Service
public class ChannelPlanCheckServiceImpl implements ChannelPlanCheckService {

	@Autowired
	private ChannelPlanCheckDao channelPlanCheckdaoDao;

	@Autowired
	private ChannelPlanDao channelPlanDao;

	@Override
	public Pager checkFindWithPG(ChannelPlanCheckVO checkVO) {
		// TODO Auto-generated method stub
		return channelPlanCheckdaoDao.checkFindWithPG(checkVO);
	}



	@Override
	public int addReply(ChannelPlanCheckVO vo) {
		// TODO Auto-generated method stub
		return channelPlanCheckdaoDao.addReply(vo);
	}



	@Override
	public int updateReply(ChannelPlanCheckVO vo) {
		// TODO Auto-generated method stub
		return channelPlanCheckdaoDao.updateReply(vo);
	}



	@Override
	public int updateReplyStatus(ChannelPlanCheckVO vo) {
		// TODO Auto-generated method stub
		return channelPlanCheckdaoDao.updateReplyStatus(vo);
	}



	@Override
	public int deleteReply(long id) {
		// TODO Auto-generated method stub
		return channelPlanCheckdaoDao.deleteReply(id);
	}



	@Override
	public ChannelPlanCheck getReplyById(long id) {
		// TODO Auto-generated method stub
		return channelPlanCheckdaoDao.getReplyById(id);
	}
	@Override
	@Transactional
	public void updateAndInsertPlan(ChannelPlanCheckVO vo,ChannelPlan plan ){
		channelPlanDao.insert(plan);
		channelPlanCheckdaoDao.updateReplyStatus(vo);
	}
	@Override
	@Transactional
	public void updateAndUpdatePlan(ChannelPlanCheckVO vo,ChannelPlanVO plan ){
		channelPlanDao.update(plan);
		channelPlanCheckdaoDao.updateReplyStatus(vo);
	}
	@Override
	@Transactional
	public void deleteAnddeletePlan(ChannelPlanCheckVO vo,ChannelPlanVO plan ){
		channelPlanDao.deletePlan(plan.getId());
		channelPlanCheckdaoDao.deleteReply(vo.getId());
	}



	@Override
	public List<ChannelPlanCheck> findListByVo(
			ChannelPlanCheckVO channelPlanCheckVO) {
		// TODO Auto-generated method stub
		return channelPlanCheckdaoDao.findListByVo(channelPlanCheckVO);
	}
 
}
