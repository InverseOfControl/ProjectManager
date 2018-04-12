/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.apply.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.apply.dao.BankDao;
import com.ezendai.credit2.apply.model.Bank;
import com.ezendai.credit2.apply.service.BankService;
import com.ezendai.credit2.apply.vo.BankVO;
import com.ezendai.credit2.framework.util.Pager;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author xiaoxiong
 * @version $Id: BankServiceImpl.java, v 0.1 2014年6月24日 下午1:29:01 xiaoxiong Exp $
 */
@Service
public class BankServiceImpl implements BankService {
    
    @Autowired
    BankDao bankDao;

    @Override
    public Bank insert(Bank bank) {
        return bankDao.insert(bank);
    }

    @Override
    public void deleteById(Long id) {
        bankDao.deleteById(id);
    }

    @Override
    public void deleteByIdList(BankVO bankVO) {
        bankDao.deleteByIdList(bankVO);
    }

    @Override
    public void update(BankVO bankVO) {
        bankDao.update(bankVO);
    }

    @Override
    public Bank get(Long id) {
        return bankDao.get(id);
    }

    @Override
    public List<Bank> findListByVo(BankVO bankVO) {
        return bankDao.findListByVo(bankVO);
    }

    @Override
    public boolean exists(Map<String, Object> map) {
        return bankDao.exists(map);
    }

    @Override
    public Pager findWithPg(BankVO bankVO) {
        return bankDao.findWithPg(bankVO);
    }

    @Override
    public Bank get(BankVO bankVO) {
        return bankDao.get(bankVO);
    }

    @Override
    public boolean exists(Long id) {
        return bankDao.exists(id);
    }
}
