package com.ezendai.credit2.apply.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.apply.dao.ChannelPlanDao;
import com.ezendai.credit2.apply.model.ChannelPlan;
import com.ezendai.credit2.apply.vo.ChannelPlanVO;
import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.system.model.SysUser;
@Repository
public class ChannelPlanDaoImpl extends BaseDaoImpl<ChannelPlan> implements ChannelPlanDao{

	
	/**
	 * 查询方案列表带分页功能
	 * @author liuhy
	 * @param channelPlanVO 查询VO
	 * @return 结果集，带总行数
	 */

	@Override
	public Pager findWithPGExtension(ChannelPlanVO channelPlanVO) {
		// TODO Auto-generated method stub
		Pager pager = channelPlanVO.getPager();
		//查询总行数
		int totalCount = (Integer)getSqlSession().selectOne(getIbatisMapperNameSpace() + ".count", channelPlanVO);
		pager.setTotalCount(totalCount);;
		pager.calStart();
		//查询员工列表
		List<SysUser> resultList = getSqlSession().selectList(getIbatisMapperNameSpace() + ".findListWithPGByVO",channelPlanVO);
		pager.setResultList(resultList);;
		return pager;
	}

	@Override
	public int deletePlan(long id) {
		// TODO Auto-generated method stub
		return (Integer)getSqlSession().update(getIbatisMapperNameSpace() + ".deletePlan", id);
	}
}
