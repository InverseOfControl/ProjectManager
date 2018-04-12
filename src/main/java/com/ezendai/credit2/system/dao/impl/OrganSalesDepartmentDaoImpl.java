/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.system.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;
import com.ezendai.credit2.master.model.BaseArea;
import com.ezendai.credit2.system.dao.OrganSalesDepartmentDao;
import com.ezendai.credit2.system.model.OrganSalesDepartment;
import com.ezendai.credit2.system.vo.OrganSalesDepartmentVO;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author 00226557
 * @version $Id: OrganDaoImpl.java, v 0.1 2015年9月15日 下午4:38:38 00226557 Exp $
 */
@Repository
public class OrganSalesDepartmentDaoImpl extends BaseDaoImpl<OrganSalesDepartment> implements OrganSalesDepartmentDao {

	/** 
	 * @param organSalesDepartmentVO
	 * @return
	 * @see com.ezendai.credit2.system.dao.OrganSalesDepartmentDao#findSalesDeptListByVo(com.ezendai.credit2.system.vo.OrganSalesDepartmentVO)
	 */
	@Override
	public List<BaseArea> findSalesDeptListByVo(OrganSalesDepartmentVO organSalesDepartmentVO) {
		List<BaseArea> rstList = getSqlSession().selectList(getIbatisMapperNameSpace() + ".findSalesDeptListByVo", organSalesDepartmentVO);
		return rstList;
	}

	/** 
	 * @param organSalesDepartmentVO
	 * @see com.ezendai.credit2.system.dao.OrganSalesDepartmentDao#deleteListByVo(com.ezendai.credit2.system.vo.OrganSalesDepartmentVO)
	 */
	@Override
	public void deleteListByVo(OrganSalesDepartmentVO organSalesDepartmentVO) {
		this.getSqlSession().update(getIbatisMapperNameSpace() + ".deleteListByVo", organSalesDepartmentVO);
	}
	
}
