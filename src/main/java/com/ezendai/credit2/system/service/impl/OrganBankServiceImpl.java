/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.apply.model.BankAccount;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.system.dao.OrganBankDao;
import com.ezendai.credit2.system.model.OrganBank;
import com.ezendai.credit2.system.service.OrganBankService;
import com.ezendai.credit2.system.vo.OrganBankVO;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author 00226557
 * @version $Id: OrganServiceImpl.java, v 0.1 2015年9月15日 下午4:36:06 00226557 Exp $
 */
@Service
public class OrganBankServiceImpl implements OrganBankService {
	
	@Autowired
	private  OrganBankDao organBankDao;

	
	/** 
	 * @param organBankVO
	 * @return
	 * @see com.ezendai.credit2.system.service.OrganBankService#update(com.ezendai.credit2.system.vo.OrganBankVO)
	 */
	@Override
	public int update(OrganBankVO organBankVO) {
		return organBankDao.update(organBankVO);
	}


	/** 
	 * @param organBank
	 * @return
	 * @see com.ezendai.credit2.system.service.OrganBankService#insert(com.ezendai.credit2.system.model.OrganBank)
	 */
	@Override
	public OrganBank insert(OrganBank organBank) {
		return organBankDao.insert(organBank);
	}

	/** 
	 * @param organBankVO
	 * @return
	 * @see com.ezendai.credit2.system.service.OrganBankService#findListByVo(com.ezendai.credit2.system.vo.OrganBankVO)
	 */
	@Override
	public List<OrganBank> findListByVo(OrganBankVO organBankVO) {
		return organBankDao.findListByVo(organBankVO);
	}

	/** 
	 * @param organBankVO
	 * @return
	 * @see com.ezendai.credit2.system.service.OrganBankService#findWithPg(com.ezendai.credit2.system.vo.OrganBankVO)
	 */
	@Override
	public Pager findWithPg(OrganBankVO organBankVO) {
		return organBankDao.findWithPg(organBankVO);
	}

	/** 
	 * @param organBankVO
	 * @return
	 * @see com.ezendai.credit2.system.service.OrganBankService#count(com.ezendai.credit2.system.vo.OrganBankVO)
	 */
	@Override
	public Integer count(OrganBankVO organBankVO) {
		return null;
	}

	/** 
	 * @param id
	 * @return
	 * @see com.ezendai.credit2.system.service.OrganBankService#get(java.lang.Long)
	 */
	@Override
	public OrganBank get(Long id) {
		return organBankDao.get(id);
	}


	/** 
	 * @param organBankVO
	 * @return
	 * @see com.ezendai.credit2.system.service.OrganBankService#findBankAccountListByVo(com.ezendai.credit2.system.vo.OrganBankVO)
	 */
	@Override
	public List<BankAccount> findBankAccountListByVo(OrganBankVO organBankVO) {
		return organBankDao.findBankAccountListByVo(organBankVO);
	}


	/** 
	 * @param organBankVO
	 * @see com.ezendai.credit2.system.service.OrganBankService#deleteListByVo(com.ezendai.credit2.system.vo.OrganBankVO)
	 */
	@Override
	public void deleteListByVo(OrganBankVO organBankVO) {
		organBankDao.deleteListByVo(organBankVO);
	}
	
}
