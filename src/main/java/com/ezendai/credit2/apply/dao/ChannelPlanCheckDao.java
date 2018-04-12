package com.ezendai.credit2.apply.dao;

import com.ezendai.credit2.apply.model.ChannelPlanCheck;
import com.ezendai.credit2.apply.vo.ChannelPlanCheckVO;
import com.ezendai.credit2.framework.dao.BaseDao;
import com.ezendai.credit2.framework.util.Pager;

public interface ChannelPlanCheckDao extends BaseDao<ChannelPlanCheck> {
	
	 
	/**
	 * <pre>
	 * 批复之分页查询 
	 * @param returnDate
	 * @return
	 */
	Pager checkFindWithPG(ChannelPlanCheckVO checkVO);
	
	/**
	 * <pre>
	 * 新增批复方案
	 * @param returnDate
	 * @return
	 */
	int addReply(ChannelPlanCheckVO vo);
	
	/**
	 * <pre>
	 * 修改批复方案
	 * @param returnDate
	 * @return
	 */
	int updateReply(ChannelPlanCheckVO vo);
	/**
	 * <pre>
	 * 修改批复状态
	 * @param returnDate
	 * @return
	 */
	int updateReplyStatus(ChannelPlanCheckVO vo);
	/**
	 * <pre>
	 * 删除批复方案
	 * @param returnDate
	 * @return
	 */
	int deleteReply(long id);
	
	/**
	 * <pre>
	 * 获取批复方案
	 * @param returnDate
	 * @return
	 */
	ChannelPlanCheck 	getReplyById (long id);

	int isExistCount(ChannelPlanCheckVO channelPlanCheckVO);
	
}
