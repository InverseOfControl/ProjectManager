/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.system.dao.impl;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;
import com.ezendai.credit2.system.dao.OrganSalesManagerDao;
import com.ezendai.credit2.system.model.OrganSalesManager;
import com.ezendai.credit2.system.vo.OrganSalesManagerVO;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author 00226557
 * @version $Id: OrganDaoImpl.java, v 0.1 2015年9月15日 下午4:38:38 00226557 Exp $
 */
@Repository
public class OrganSalesManagerDaoImpl extends BaseDaoImpl<OrganSalesManager> implements OrganSalesManagerDao {

	/** 
	 * @param organSalesManagerVO
	 * @see com.ezendai.credit2.system.dao.OrganSalesManagerDao#deleteListByVo(com.ezendai.credit2.system.vo.OrganSalesManagerVO)
	 */
	@Override
	public void deleteListByVo(OrganSalesManagerVO organSalesManagerVO) {
		this.getSqlSession().update(getIbatisMapperNameSpace() + ".deleteListByVo", organSalesManagerVO);
	}
	
}
