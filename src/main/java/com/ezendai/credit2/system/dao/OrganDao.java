/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.system.dao;

import com.ezendai.credit2.framework.dao.BaseDao;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.system.model.Organ;
import com.ezendai.credit2.system.vo.OrganVO;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author 00226557
 * @version $Id: OrganDao.java, v 0.1 2015年9月15日 下午4:37:17 00226557 Exp $
 */
public interface OrganDao extends BaseDao<Organ> {
	/**
	 * <pre>
	 * 查询机构下面有没有生效的批复方案，
	 * </pre>
	 * @param organId
	 * @return
	 */
	boolean existValidCheckPlan(Long organId);
	
	/**
	 * <pre>
	 * 逻辑删除此机构下面所以的方案信息
	 * </pre>
	 * @param organId
	 * @return
	 */
	void deleteChannelPlan(Long organId);
	/**
	 * <pre>
	 * 逻辑删除此机构下面所以的方案批复信息
	 * </pre>
	 * @param organId
	 * @return
	 */
	void deleteChannelPlanCheck(Long organId);
	
	/** 根据查询条件vo物理分页查询出数据行：{mapper.xml需要实现} 
	 *  只支持门店客服、门店经理&副理单门店查询,不支持以上人员跨门店查询,实现见mapper
	 * */
	Pager findWithPgByUser(OrganVO vo);
}
