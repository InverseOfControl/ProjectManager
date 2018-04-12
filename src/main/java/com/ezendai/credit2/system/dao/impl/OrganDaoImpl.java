/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.system.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;
import com.ezendai.credit2.framework.util.BeanUtil;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.system.dao.OrganDao;
import com.ezendai.credit2.system.model.Organ;
import com.ezendai.credit2.system.vo.OrganVO;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author 00226557
 * @version $Id: OrganDaoImpl.java, v 0.1 2015年9月15日 下午4:38:38 00226557 Exp $
 */
@Repository
public class OrganDaoImpl extends BaseDaoImpl<Organ> implements OrganDao {

	/** 
	 * @param organId
	 * @return
	 * @see com.ezendai.credit2.system.dao.OrganDao#existValidCheckPlan(java.lang.Long)
	 */
	@Override
	public boolean existValidCheckPlan(Long organId) {
		Object count = getSqlSession().selectOne(getIbatisMapperNameSpace() + ".existValidCheckPlan", organId);
		int totalCount = Integer.parseInt(count.toString());
		return totalCount > 0 ? true : false;
	}

	/** 
	 * @param organId
	 * @return
	 * @see com.ezendai.credit2.system.dao.OrganDao#deleteChannelPlan(java.lang.Long)
	 */
	@Override
	public void deleteChannelPlan(Long organId) {
		this.getSqlSession().update(getIbatisMapperNameSpace() + ".deleteChannelPlan", organId);
	}

	/** 
	 * @param organId
	 * @return
	 * @see com.ezendai.credit2.system.dao.OrganDao#deleteChannelPlanCheck(java.lang.Long)
	 */
	@Override
	public void deleteChannelPlanCheck(Long organId) {
		this.getSqlSession().update(getIbatisMapperNameSpace() + ".deleteChannelPlanCheck", organId);
	}

	/** 
	 * @param vo
	 * @return
	 * @see com.ezendai.credit2.system.dao.OrganDao#findWithPgByUser(com.ezendai.credit2.framework.vo.BaseVO)
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public Pager findWithPgByUser(OrganVO vo) {
		Object count = getSqlSession().selectOne(getIbatisMapperNameSpace() + ".countUser", vo);
		int totalCount = Integer.parseInt(count.toString());
		Pager pg = vo.getPager();
		pg.setTotalCount(totalCount);
		pg.calStart();
		List rstList = null;
		try {
			rstList = getSqlSession().selectList(getIbatisMapperNameSpace() + ".findWithPgByUser", vo);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		BeanUtil.copyProperties(pg, vo);
		pg.setTotalCount(totalCount);
		pg.setResultList(rstList);
		return pg;
	}
}
