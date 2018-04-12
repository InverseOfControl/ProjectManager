package com.ezendai.credit2.apply.service;

import com.ezendai.credit2.apply.model.ChannelPlan;
import com.ezendai.credit2.apply.vo.ChannelPlanCheckVO;
import com.ezendai.credit2.apply.vo.ChannelPlanVO;
import com.ezendai.credit2.framework.util.Pager;

public interface ChannelPlanService {
	/**
	 * 查询方案列表带分页功能
	 * @author liuhy
	 * @param channelPlanVO 查询VO
	 * @return 结果集，带总行数
	 */
	public Pager findWithPGExtension(ChannelPlanVO channelPlanVO) ;

	public void insertChannelPlan(ChannelPlan channelPlan);
	
	public void insertChannelPlan(ChannelPlan channelPlan,ChannelPlanCheckVO channelPlanCheckVO);

	public void updateChannelPlan(ChannelPlanVO channelPlanVO);

	public ChannelPlan get(long id);

	public void updateChannelPlan(ChannelPlanVO channelPlanVO, ChannelPlanCheckVO channelPlanCheckVO);

	public void deleteChannelPlan(ChannelPlanVO channelPlanVO,	ChannelPlanCheckVO channelPlanCheckVO);

	public int isExistCount(ChannelPlanCheckVO channelPlanCheckVO);
}
