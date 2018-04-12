/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.apply.service;

import java.util.List;
import java.util.Map;

import com.ezendai.credit2.apply.model.Bank;
import com.ezendai.credit2.apply.vo.BankVO;
import com.ezendai.credit2.framework.util.Pager;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author xiaoxiong
 * @version $Id: BankService.java, v 0.1 2014年6月24日 下午1:27:26 xiaoxiong Exp $
 */
public interface BankService {

    Bank insert(Bank bank);

    void deleteById(Long id);

    void deleteByIdList(BankVO bankVO);

    void update(BankVO bankVO);

    Bank get(Long id);

    List<Bank> findListByVo(BankVO bankVO);

    boolean exists(Map<String, Object> map);

    Pager findWithPg(BankVO bankVO);

    Bank get(BankVO bankVO);

    boolean exists(Long id);
}
