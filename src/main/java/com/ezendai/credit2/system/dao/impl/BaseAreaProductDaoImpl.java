/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.system.dao.impl;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;
import com.ezendai.credit2.system.dao.BaseAreaProductDao;
import com.ezendai.credit2.system.model.BaseAreaProduct;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author 00221921
 * @version $Id: BaseAreaProductDaoImpl.java, v 0.1 2015年4月13日 下午3:16:51 00221921 Exp $
 */
@Repository
public class BaseAreaProductDaoImpl extends BaseDaoImpl<BaseAreaProduct> implements BaseAreaProductDao{

	/** 
	 * @param productId
	 * @see com.ezendai.credit2.system.dao.BaseAreaProductDao#deleteByProductId(java.lang.Long)
	 */
	@Override
	public void deleteByProductId(Long productId) {
		this.getSqlSession().update(getIbatisMapperNameSpace() + ".deleteByProductId",productId);
	}

}
