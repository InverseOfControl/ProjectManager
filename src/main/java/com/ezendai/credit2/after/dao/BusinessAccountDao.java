/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.after.dao;

import java.util.List;

import com.ezendai.credit2.after.model.BusinessAccount;
import com.ezendai.credit2.after.vo.BusinessAccountVO;
import com.ezendai.credit2.framework.dao.BaseDao;

/**
 * <pre>
 * 对公还款
 * </pre>
 *
 * @author zhangshihai
 * @version $Id: BusinessAccountDao.java, v 0.1 2014年12月11日 下午3:44:54 00221921 Exp $
 */
public interface BusinessAccountDao extends BaseDao<BusinessAccount>{
	/**
	 * 
	 * <pre>
	 * 计算查询出已认领的条数
	 * </pre>
	 *
	 * @param businessAccountVo
	 * @return
	 */
	Integer receiveResultCount(BusinessAccountVO businessAccountVo);
	/**
	 * 
	 * <pre>
	 * 查询出已认领的结果
	 * </pre>
	 *
	 * @param businessAccountVo
	 * @return
	 */
	List<BusinessAccount>  findReceiveResult(BusinessAccountVO businessAccountVo);

}
