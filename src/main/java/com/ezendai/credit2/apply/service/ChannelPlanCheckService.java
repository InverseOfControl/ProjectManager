/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.apply.service;

import java.util.List;
 





import com.ezendai.credit2.apply.model.ChannelPlan;
import com.ezendai.credit2.apply.model.ChannelPlanCheck;
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
public interface ChannelPlanCheckService {
	
	/**
	 *获取所有方案
	 * @param Loan
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
	 
	void updateAndInsertPlan(ChannelPlanCheckVO vo,ChannelPlan plan );
	void updateAndUpdatePlan(ChannelPlanCheckVO vo,ChannelPlanVO plan );
	void deleteAnddeletePlan(ChannelPlanCheckVO vo,ChannelPlanVO plan );
	List<ChannelPlanCheck> findListByVo(ChannelPlanCheckVO channelPlanCheckVO);
}
