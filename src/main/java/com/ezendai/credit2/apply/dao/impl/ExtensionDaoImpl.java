/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.apply.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.apply.dao.ExtensionDao;
import com.ezendai.credit2.apply.model.Extension;
import com.ezendai.credit2.apply.vo.ExtensionVO;
import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;

/**
 * <pre>
 * 展期信息数据连接
 * </pre>
 *
 * @author zhangshihai
 * @version $Id: ExtensionDaoImpl.java, v 0.1 2015年3月10日 下午2:23:02 00221921 Exp $
 */
@Repository
public class ExtensionDaoImpl extends BaseDaoImpl<Extension> implements ExtensionDao{

	@Override
	public int updateExtensionByStatus(ExtensionVO extensionVO) {
		return getSqlSession().update(getIbatisMapperNameSpace() + ".updateByStatus", extensionVO);
	}

	/** 
	 * @param extensionVO
	 * @return
	 * @see com.ezendai.credit2.apply.dao.ExtensionDao#getByVO(com.ezendai.credit2.apply.vo.ExtensionVO)
	 */
	@Override
	public Extension getExtensionByLoanId(ExtensionVO extensionVO) {
		return getSqlSession().selectOne(getIbatisMapperNameSpace() + ".getExtensionByLoanId", extensionVO);
	}
	
	@Override
	public List<Extension> findLastOverdueList(ExtensionVO extensionVO) {
		List<Extension> rstList = getSqlSession().selectList(getIbatisMapperNameSpace() + ".findLastOverdueList", extensionVO);
		return rstList;
	}

	/** 
	 * @param extensionVO
	 * @return
	 * @see com.ezendai.credit2.apply.dao.ExtensionDao#updateByIdList(com.ezendai.credit2.apply.vo.ExtensionVO)
	 */
	@Override
	public int updateByIdList(ExtensionVO extensionVO) {
		return getSqlSession().update(getIbatisMapperNameSpace() + ".updateByIdList", extensionVO);
	}

	/** 
	 * @param extensionVO
	 * @return
	 * @see com.ezendai.credit2.apply.dao.ExtensionDao#getCountByExtensionVO(com.ezendai.credit2.apply.vo.ExtensionVO)
	 */
	@Override
	public int getCountByExtensionVO(ExtensionVO extensionVO) {
		Object object = getSqlSession().selectOne(getIbatisMapperNameSpace() + ".count", extensionVO);
		return Integer.parseInt(object.toString());
	}

	@Override
	public List<Extension> findExtensionByCondition(ExtensionVO extensionVO) {
		List<Extension> rstList = getSqlSession().selectList(getIbatisMapperNameSpace() + ".findExtensionByCondition", extensionVO);
		return rstList;
	}
}
