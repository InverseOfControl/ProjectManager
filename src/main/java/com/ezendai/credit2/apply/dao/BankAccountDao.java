/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.apply.dao;

import com.ezendai.credit2.apply.model.BankAccount;
import com.ezendai.credit2.apply.vo.BankAccountVO;
import com.ezendai.credit2.framework.dao.BaseDao;
import com.ezendai.credit2.framework.util.Pager;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author xiaoxiong
 * @version $Id: BankAccountDao.java, v 0.1 2014年6月24日 下午1:23:43 xiaoxiong Exp $
 */
public interface BankAccountDao extends BaseDao<BankAccount> {

	/**获取bankaccount->bank对象的属性,现在只能得到 bank tpp type**/
	BankAccount getBankAccount(Long id);
	
	BankAccount getBankAccountDetails(Long id);
	
	int updateByIdList(BankAccountVO bankAccountVO); 
	
	Pager findWithPGExtension(BankAccountVO bankAccountVO);
}
