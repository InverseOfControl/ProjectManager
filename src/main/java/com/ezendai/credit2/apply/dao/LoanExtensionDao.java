/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.apply.dao;

import java.util.Map;

import com.ezendai.credit2.apply.model.LoanExtension;
import com.ezendai.credit2.framework.dao.BaseDao;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author zhangshihai
 * @version $Id: LoanExtensionDao.java, v 0.1 2015年3月10日 下午2:26:13 00221921 Exp $
 */
public interface LoanExtensionDao extends BaseDao<LoanExtension>{
	
	/**
	 * 
	 * <pre>
	 * 根据展期id获得loanId
	 * </pre>
	 *
	 * @param extensionId
	 * @return
	 */
	public Long getLoanIdByExtensionId(Long extensionId);


	public Long getPreExtensionId(Map<String, Object> map);
	
	
	public Integer maxExtensionTime();
}
