package com.ezendai.credit2.apply.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.apply.dao.ChannelPlanCheckDao;
import com.ezendai.credit2.apply.model.ChannelPlanCheck;
import com.ezendai.credit2.apply.vo.ChannelPlanCheckVO;
import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;
import com.ezendai.credit2.framework.util.BeanUtil;
import com.ezendai.credit2.framework.util.Pager;

/***
 * <pre>
 * 批复
 * 
 * </pre>
 *
 * @author  
 * @version  
 */
@Repository
public class ChannelPlanCheckDaoImpl extends BaseDaoImpl<ChannelPlanCheck> implements ChannelPlanCheckDao {

	@SuppressWarnings("rawtypes")
	@Override
	public Pager checkFindWithPG(ChannelPlanCheckVO checkVO) {
		Object count = getSqlSession().selectOne(getIbatisMapperNameSpace() + ".checkCount", checkVO);
		int totalCount = Integer.parseInt(count.toString());
		Pager pg = checkVO.getPager();
		pg.setTotalCount(totalCount);
		pg.calStart();
		List rstList = null;
		try {
			rstList = getSqlSession().selectList(getIbatisMapperNameSpace() + ".findCheckFindWithPG", checkVO);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		BeanUtil.copyProperties(pg, checkVO);
		pg.setTotalCount(totalCount);
		pg.setResultList(rstList);
		return pg;
	}

	@Override
	public int addReply(ChannelPlanCheckVO vo) {
		// TODO Auto-generated method stub
		return  getSqlSession().insert(getIbatisMapperNameSpace() + ".addReply", vo);
	}

	@Override
	public int updateReply(ChannelPlanCheckVO vo) {
		// TODO Auto-generated method stub
		return  getSqlSession().update(getIbatisMapperNameSpace() + ".updateReply", vo);
	}

	@Override
	public int updateReplyStatus(ChannelPlanCheckVO vo) {
		// TODO Auto-generated method stub
		return  getSqlSession().update(getIbatisMapperNameSpace() + ".updateReplyStatus", vo);
	}

	@Override
	public int deleteReply(long id) {
		// TODO Auto-generated method stub
		return  getSqlSession().delete(getIbatisMapperNameSpace() + ".deleteReply", id);
	}

	@Override
	public ChannelPlanCheck getReplyById(long id) {
		// TODO Auto-generated method stub
		return   getSqlSession().selectOne(getIbatisMapperNameSpace() + ".getReplyById", id);
	}

	@Override
	public int isExistCount(ChannelPlanCheckVO channelPlanCheckVO) {
		// TODO Auto-generated method stub
		Object count = getSqlSession().selectOne(getIbatisMapperNameSpace() + ".isExistCount", channelPlanCheckVO);
		int totalCount = Integer.parseInt(count.toString());
		return totalCount;
	}
	 

	 
}