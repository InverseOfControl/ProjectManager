/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.apply.service;

import java.util.List;
import java.util.Map;

import com.ezendai.credit2.apply.model.BankAccount;
import com.ezendai.credit2.apply.vo.BankAccountVO;
import com.ezendai.credit2.framework.util.Pager;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author xiaoxiong
 * @version $Id: BankAccountService.java, v 0.1 2014年6月24日 下午1:19:48 xiaoxiong Exp $
 */
public interface BankAccountService {
    BankAccount insert(BankAccount bankAccount);

    void deleteById(Long id);

    void deleteByIdList(BankAccountVO bankAccountVO);

    void updateByIdList(BankAccountVO bankAccountVO);
    
    void update(BankAccountVO bankAccountVO);

    BankAccount get(Long id);

    List<BankAccount> findListByVo(BankAccountVO bankAccountVO);

    boolean exists(Map<String, Object> map);

    Pager findWithPg(BankAccountVO bankAccountVO);

    BankAccount get(BankAccountVO bankAccountVO);

    boolean exists(Long id);

    /**获取bankaccount->bank对象的属性,现在只能得到 bank tpp type**/
    BankAccount getBankAccount(Long id);
    
    BankAccount getBankAccountDetails(Long id);
    
	Pager findWithPGExtension(BankAccountVO bankAccountVO);

}
