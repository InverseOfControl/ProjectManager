package com.ezendai.credit2.master.dao.impl;

import java.util.List;


import org.springframework.stereotype.Repository;

import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.dao.WorkPlaceInfoDao;
import com.ezendai.credit2.master.model.BaseArea;
import com.ezendai.credit2.master.model.WorkPlaceInfo;
import com.ezendai.credit2.master.vo.WorkPlaceInfoVO;

/**
 * 办公地信息管理Dao实现类
 * Author: kimi
 * Date: 14-6-24
 * Time: 上午11:39
 */
@Repository
public class WorkPlaceInfoDaoImpl extends BaseDaoImpl<WorkPlaceInfo> implements WorkPlaceInfoDao {
	
	@Override
	public Pager findWithPg(WorkPlaceInfoVO vo) {
		Pager pager =vo.getPager();
		//查询总行数
		int totalCount = (Integer)getSqlSession().selectOne(getIbatisMapperNameSpace() + ".count", vo);
		pager.setTotalCount(totalCount);
		pager.calStart();
		List<BaseArea> resultList = getSqlSession().selectList(getIbatisMapperNameSpace() + ".findListByVO",vo);
		pager.setResultList(resultList);
		return pager;
	}
	
	@Override
	public int existsWorkPlaceInfo(WorkPlaceInfoVO vo) {
		return getSqlSession().selectOne(getIbatisMapperNameSpace() +".existsWorkPlaceInfo",vo);
	}
}
