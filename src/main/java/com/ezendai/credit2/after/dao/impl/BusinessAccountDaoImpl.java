/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.after.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.after.dao.BusinessAccountDao;
import com.ezendai.credit2.after.model.BusinessAccount;
import com.ezendai.credit2.after.vo.BusinessAccountVO;
import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;

/**
 * <pre>
 * 对公还款
 * </pre>
 *
 * @author zhangshihai
 * @version $Id: BusinessAccountDaoImpl.java, v 0.1 2014年12月11日 下午3:46:28 00221921 Exp $
 */
@Repository
public class BusinessAccountDaoImpl extends BaseDaoImpl<BusinessAccount> implements BusinessAccountDao {

	/**
	 * 
	 * <pre>
	 * 计算查询出已认领的条数
	 * </pre>
	 *
	 * @param businessAccountVo
	 * @return
	 */
	@Override
	public Integer receiveResultCount(BusinessAccountVO businessAccountVo) {
		return this.getSqlSession().selectOne(getIbatisMapperNameSpace()+".receiveResultCount",businessAccountVo);
	}
	/**
	 * 
	 * <pre>
	 * 查询出已认领的结果
	 * </pre>
	 *
	 * @param businessAccountVo
	 * @return
	 */
	@Override
	public List<BusinessAccount>  findReceiveResult(BusinessAccountVO businessAccountVo) {
		return this.getSqlSession().selectList(getIbatisMapperNameSpace()+".findReceiveResult",businessAccountVo);
	}
}
