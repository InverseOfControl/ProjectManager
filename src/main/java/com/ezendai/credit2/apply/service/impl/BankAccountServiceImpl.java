/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.apply.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezendai.credit2.apply.dao.BankAccountDao;
import com.ezendai.credit2.apply.model.BankAccount;
import com.ezendai.credit2.apply.service.BankAccountService;
import com.ezendai.credit2.apply.vo.BankAccountVO;
import com.ezendai.credit2.framework.util.Pager;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author xiaoxiong
 * @version $Id: BankAccountServiceImpl.java, v 0.1 2014年6月24日 下午1:21:59 xiaoxiong Exp $
 */
@Service
public class BankAccountServiceImpl implements BankAccountService {

    @Autowired
    BankAccountDao bankAccountDao;

    @Transactional
    @Override
    public BankAccount insert(BankAccount bankAccount) {
        return bankAccountDao.insert(bankAccount);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        bankAccountDao.deleteById(id);
    }

    @Override
    public void deleteByIdList(BankAccountVO bankAccountVO) {
        bankAccountDao.deleteByIdList(bankAccountVO);
    }

    @Override
    public void update(BankAccountVO bankAccountVO) {
        bankAccountDao.update(bankAccountVO);
    }

    @Override
    public BankAccount get(Long id) {
        return bankAccountDao.get(id);
    }

    @Override
    public List<BankAccount> findListByVo(BankAccountVO bankAccountVO) {
        return bankAccountDao.findListByVo(bankAccountVO);
    }

    @Override
    public boolean exists(Map<String, Object> map) {
        return bankAccountDao.exists(map);
    }

    @Override
    public Pager findWithPg(BankAccountVO bankAccountVO) {
        return bankAccountDao.findWithPg(bankAccountVO);
    }

    @Override
    public BankAccount get(BankAccountVO bankAccountVO) {
        return bankAccountDao.get(bankAccountVO);
    }

    @Override
    public boolean exists(Long id) {
        return bankAccountDao.exists(id);
    }

	/** 
	 * 获取bankaccount->bank对象的属性,现在只能得到 bank tpp type
	 * @param id
	 * @return
	 * @see com.ezendai.credit2.apply.service.BankAccountService#getTppType(java.lang.Long)
	 */
	@Override
	
	public BankAccount getBankAccount(Long id) {
		return bankAccountDao.getBankAccount(id);
	}

	/** 
	 * @param bankAccountVO
	 * @see com.ezendai.credit2.apply.service.BankAccountService#updateByIdList(com.ezendai.credit2.apply.vo.BankAccountVO)
	 */
	@Override
	public void updateByIdList(BankAccountVO bankAccountVO) {
		bankAccountDao.updateByIdList(bankAccountVO);
	}

	@Override
	public BankAccount getBankAccountDetails(Long id) {
		// TODO Auto-generated method stub
		return bankAccountDao.getBankAccountDetails(id);
	}

	@Override
	public Pager findWithPGExtension(BankAccountVO bankAccountVO) {
		// TODO Auto-generated method stub
		return bankAccountDao.findWithPGExtension(bankAccountVO);
	}

}
