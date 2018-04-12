/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.apply.dao;

import java.util.List;

import com.ezendai.credit2.apply.model.Extension;
import com.ezendai.credit2.apply.vo.ExtensionVO;
import com.ezendai.credit2.framework.dao.BaseDao;

/**
 * <pre>
 * 展期信息数据连接层
 * </pre>
 *
 * @author zhangshihai
 * @version $Id: ExtensionDao.java, v 0.1 2015年3月10日 下午2:21:19 00221921 Exp $
 */
public interface ExtensionDao extends BaseDao<Extension>{

	/**
	 * <pre>
	 * 更新状态不是某个状态的记录
	 * </pre>
	 *
	 * @param ExtensionVO
	 * @return
	 */
	int updateExtensionByStatus(ExtensionVO extensionVO);
	
	int updateByIdList(ExtensionVO extensionVO);
	
	Extension getExtensionByLoanId(ExtensionVO extensionVO);
	/**
	 * 
	 * <pre>
	 * 查询最后一期是逾期的记录
	 * </pre>
	 *
	 * @param loanId
	 * @return
	 */
	List<Extension> findLastOverdueList(ExtensionVO extensionVO);
	
	/**
	 * 
	 * <pre>
	 * 获取符合条件的extension总条数
	 * </pre>
	 *
	 * @param loanVO
	 * @return
	 */
	int getCountByExtensionVO(ExtensionVO extensionVO);
	
	
	/**
	 * 获取展期数据根据条件（现用于申请展期唯一性判断）
	 * @param extensionVO
	 * @return
	 */
	List<Extension> findExtensionByCondition(ExtensionVO extensionVO);
}
