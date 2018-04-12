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
import com.ezendai.credit2.system.dao.TextResourcesDao;
import com.ezendai.credit2.system.model.TextResource;
import com.ezendai.credit2.system.vo.TextResourceVO;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author 00226557
 * @version $Id: OrganDaoImpl.java, v 0.1 2015年9月15日 下午4:38:38 00226557 Exp $
 */
@Repository
public class TextResourcesDaoImpl extends BaseDaoImpl<TextResource> implements TextResourcesDao {

 
	@Override
	public List<TextResource> findTextResourcesByType(Integer type) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(getIbatisMapperNameSpace() + ".findTextResourcesByType", type);
	}

	@Override
	public Pager findTextResourcesWithPG(TextResourceVO textResourceVO) {
		// TODO Auto-generated method stub
		Object count = getSqlSession().selectOne(getIbatisMapperNameSpace() + ".findTextResourcesCount", textResourceVO);
		int totalCount = Integer.parseInt(count.toString());
		Pager pg = textResourceVO.getPager();
		pg.setTotalCount(totalCount);
		pg.calStart();
		List rstList = null;
		try {
			rstList = getSqlSession().selectList(getIbatisMapperNameSpace() + ".findTextResourcesWithPG", textResourceVO);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		BeanUtil.copyProperties(pg, textResourceVO);
		pg.setTotalCount(totalCount);
		pg.setResultList(rstList);
		 return pg;
	}

	@Override
	public void updateTextResource(TextResource textResource) {
		getSqlSession().update(getIbatisMapperNameSpace() + ".updateTextResource", textResource);
		
	}

	@Override
	public List<TextResource> findTextResourceWithCondition(
			TextResourceVO textResourceVO) {
		return  getSqlSession().selectList(getIbatisMapperNameSpace() + ".findTextResourcesWithCondition", textResourceVO);
	}

	 
}
