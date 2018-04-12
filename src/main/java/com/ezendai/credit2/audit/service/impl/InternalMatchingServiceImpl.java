/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.audit.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.audit.dao.InternalMatchingDao;
import com.ezendai.credit2.audit.model.LoanHistory;
import com.ezendai.credit2.audit.service.InternalMatchingService;
import com.ezendai.credit2.framework.util.Pager;
 

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author  
 * @version   
 */
@Service
public class InternalMatchingServiceImpl implements InternalMatchingService {

	@Autowired
	private InternalMatchingDao matchingDao;

	@Override
	public Pager findLoanHistoryWithPG(LoanHistory lh) {
		// TODO Auto-generated method stub
		return matchingDao.findLoanHistoryWithPG(lh);
	}

	@Override
	public List<LoanHistory> selectOptionContacterPhone(LoanHistory lh) {
		// TODO Auto-generated method stub
		return matchingDao.selectOptionContacterPhone(lh);
	}

	@Override
	public List<LoanHistory> selectOptionPersonPhone(LoanHistory lh) {
		// TODO Auto-generated method stub
		return matchingDao.selectOptionPersonPhone(lh);
	}

	@Override
	public List<LoanHistory> selectOptionHomePhone(LoanHistory lh) {
		// TODO Auto-generated method stub
		return matchingDao.selectOptionHomePhone(lh);
	}

	@Override
	public List<LoanHistory> selectOptionCompanyPhone(LoanHistory lh) {
		// TODO Auto-generated method stub
		return matchingDao.selectOptionCompanyPhone(lh);
	}
	 
	 
}
