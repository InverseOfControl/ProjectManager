/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.system.dao;

import java.util.List;

import com.ezendai.credit2.apply.model.BankAccount;
import com.ezendai.credit2.framework.dao.BaseDao;
import com.ezendai.credit2.system.model.OrganBank;
import com.ezendai.credit2.system.vo.OrganBankVO;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author 00226557
 * @version $Id: OrganDao.java, v 0.1 2015年9月15日 下午4:37:17 00226557 Exp $
 */
public interface OrganBankDao extends BaseDao<OrganBank> {
	List<BankAccount> findBankAccountListByVo(OrganBankVO organBankVO);
	
	void deleteListByVo(OrganBankVO organBankVO);
}
