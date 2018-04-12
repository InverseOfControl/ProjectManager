package com.ezendai.credit2.apply.dao;

import com.ezendai.credit2.apply.model.ChannelPlan;
import com.ezendai.credit2.apply.vo.ChannelPlanVO;
import com.ezendai.credit2.framework.dao.BaseDao;
import com.ezendai.credit2.framework.util.Pager;

public interface ChannelPlanDao extends BaseDao<ChannelPlan>{

	public Pager findWithPGExtension(ChannelPlanVO channelPlanVO) ;

	/**
	 * <pre>
	 * 删除方案
	 * @param returnDate
	 * @return
	 */
	int deletePlan(long id);
}
