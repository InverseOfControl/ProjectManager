/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.system.dao;

import com.ezendai.credit2.framework.dao.BaseDao;
import com.ezendai.credit2.system.model.BaseAreaProduct;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author 00221921
 * @version $Id: BaseAreaProductDao.java, v 0.1 2015年4月13日 下午3:15:43 00221921 Exp $
 */
public interface BaseAreaProductDao extends BaseDao<BaseAreaProduct>{
	void deleteByProductId(Long productId);
}
