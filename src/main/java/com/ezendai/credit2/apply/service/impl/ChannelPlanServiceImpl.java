package com.ezendai.credit2.apply.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezendai.credit2.apply.dao.ChannelPlanCheckDao;
import com.ezendai.credit2.apply.dao.ChannelPlanDao;
import com.ezendai.credit2.apply.model.ChannelPlan;
import com.ezendai.credit2.apply.service.ChannelPlanService;
import com.ezendai.credit2.apply.vo.ChannelPlanCheckVO;
import com.ezendai.credit2.apply.vo.ChannelPlanVO;
import com.ezendai.credit2.framework.util.Pager;

@Service
public class ChannelPlanServiceImpl implements ChannelPlanService {
	
	@Autowired
	ChannelPlanDao channelPlanDao;
	
	@Autowired
	ChannelPlanCheckDao channelPlanCheckDao;
	
	/**
	 * 查询方案列表带分页功能
	 * @author liuhy
	 * @param channelPlanVO 查询VO
	 * @return 结果集，带总行数
	 */
	@Override
	public Pager findWithPGExtension(ChannelPlanVO channelPlanVO) {
			return channelPlanDao.findWithPGExtension(channelPlanVO);
	}

	/**
	 * 插入方案信息
	 * @author liuhy
	 * @param channelPlan 
	 */
	@Override
	@Transactional
	public void insertChannelPlan(ChannelPlan channelPlan) {
		// TODO Auto-generated method stub
		channelPlanDao.insert(channelPlan);
	}

	/**
	 * 修改方案信息
	 * @author liuhy
	 * @param channelPlanVO
	 */
	@Override
	@Transactional
	public void updateChannelPlan(ChannelPlanVO channelPlanVO) {
		// TODO Auto-generated method stub
		channelPlanDao.update(channelPlanVO);
	}

	@Override
	public ChannelPlan get(long id) {
		// TODO Auto-generated method stub
		return channelPlanDao.get(id);
	}
	
	/**
	 * 插入方案信息,并插入到批复表中
	 * @author liuhy
	 * @param channelPlan,channelPlanCheckVO
	 */
	@Override
	@Transactional
	public void insertChannelPlan(ChannelPlan channelPlan,ChannelPlanCheckVO channelPlanCheckVO) {
		// TODO Auto-generated method stub
		channelPlanDao.insert(channelPlan);
		channelPlanCheckDao.addReply(channelPlanCheckVO);
	}

	/**
	 * 修改方案信息，更新方案信息表状态,并插入到批复表中
	 * @author liuhy
	 * @param channelPlan,channelPlanCheckVO
	 */
	@Override
	@Transactional
	public void updateChannelPlan(ChannelPlanVO channelPlanVO,
			ChannelPlanCheckVO channelPlanCheckVO) {
		channelPlanDao.update(channelPlanVO);
		channelPlanCheckDao.updateReply(channelPlanCheckVO);
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 修改方案信息，更新方案信息表状态,并插入到批复表中
	 * @author liuhy
	 * @param channelPlan,channelPlanCheckVO
	 */
	@Override
	@Transactional
	public void deleteChannelPlan(ChannelPlanVO channelPlanVO,
			ChannelPlanCheckVO channelPlanCheckVO) {
		channelPlanDao.deletePlan(channelPlanVO.getId());
		channelPlanCheckDao.deleteReply(channelPlanCheckVO.getId());
		// TODO Auto-generated method stub
		
	}

	@Override
	public int isExistCount(ChannelPlanCheckVO channelPlanCheckVO) {
		// TODO Auto-generated method stub
		 return channelPlanCheckDao.isExistCount(channelPlanCheckVO);
	}

}
