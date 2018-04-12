/**
OrganDao.java * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.system.dao;

import java.util.List;

import com.ezendai.credit2.framework.dao.BaseDao;
import com.ezendai.credit2.master.model.BaseArea;
import com.ezendai.credit2.system.model.OrganSalesDepartment;
import com.ezendai.credit2.system.vo.OrganSalesDepartmentVO;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author 00226557
 * @version $Id: OrganDao.java, v 0.1 2015年9月15日 下午4:37:17 00226557 Exp $
 */
public interface OrganSalesDepartmentDao extends BaseDao<OrganSalesDepartment> {
	List<BaseArea> findSalesDeptListByVo(OrganSalesDepartmentVO organSalesDepartmentVO);
	
	void deleteListByVo(OrganSalesDepartmentVO organSalesDepartmentVO);
}
