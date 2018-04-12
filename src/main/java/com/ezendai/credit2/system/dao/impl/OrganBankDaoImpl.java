/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.system.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.apply.model.BankAccount;
import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;
import com.ezendai.credit2.system.dao.OrganBankDao;
import com.ezendai.credit2.system.model.OrganBank;
import com.ezendai.credit2.system.vo.OrganBankVO;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author 00226557
 * @version $Id: OrganDaoImpl.java, v 0.1 2015年9月15日 下午4:38:38 00226557 Exp $
 */
@Repository
public class OrganBankDaoImpl extends BaseDaoImpl<OrganBank> implements OrganBankDao {

	/** 
	 * @param organBankVO
	 * @return
	 * @see com.ezendai.credit2.system.dao.OrganBankDao#findBankAccountListByVo(com.ezendai.credit2.system.vo.OrganBankVO)
	 */
	@Override
	public List<BankAccount> findBankAccountListByVo(OrganBankVO organBankVO) {
		List<BankAccount> rstList = getSqlSession().selectList(getIbatisMapperNameSpace() + ".findBankAccountListByVo", organBankVO);
		return rstList;
	}

	/** 
	 * @param organBankVO
	 * @see com.ezendai.credit2.system.dao.OrganBankDao#deleteListByVo(com.ezendai.credit2.system.vo.OrganBankVO)
	 */
	@Override
	public void deleteListByVo(OrganBankVO organBankVO) {
		this.getSqlSession().update(getIbatisMapperNameSpace() + ".deleteListByVo", organBankVO);
	}
	
}
