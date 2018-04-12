/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.apply.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.apply.dao.LoanExtensionDao;
import com.ezendai.credit2.apply.model.LoanExtension;
import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author 00221921
 * @version $Id: LoanExtensionDaoImpl.java, v 0.1 2015年3月10日 下午2:28:19 00221921 Exp $
 */
@Repository
public class LoanExtensionDaoImpl extends BaseDaoImpl<LoanExtension> implements LoanExtensionDao{

	@Override
	public Long getLoanIdByExtensionId(Long extensionId) {
		return getSqlSession().selectOne(getIbatisMapperNameSpace() + ".getLoanIdByExtensionId", extensionId);
	}

	/** 
	 * @param map
	 * @return
	 * @see com.ezendai.credit2.apply.dao.LoanExtensionDao#getPreExtensionId(java.util.Map)
	 */
	@Override
	public Long getPreExtensionId(Map<String, Object> map) {
		return getSqlSession().selectOne(getIbatisMapperNameSpace() + ".getPreExtensionId", map);
	}

	@Override
	public Integer maxExtensionTime() {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne(getIbatisMapperNameSpace() + ".maxExtensionTime");
	}

}
