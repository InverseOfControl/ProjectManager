package com.ezendai.credit2.apply.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.apply.dao.AccountAuthLogDao;
import com.ezendai.credit2.apply.model.AccountAuthLog;
import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.service.AccountAuthLogService;
import com.ezendai.credit2.apply.vo.AccountAuthLogVO;
import com.ezendai.credit2.framework.util.Pager;

/**
 * <pre>
 * 银行卡验证日志
 * </pre>
 *
 * @author lihongyi
 * @version $Id: AttachmentDetailServiceImpl.java, v 0.1 2016-2-25 11:31:25 lihongyi Exp $
 */
@Service
public class AccountAuthLogServiceImpl implements AccountAuthLogService {

	@Autowired
	private AccountAuthLogDao accountAuthLogDao;
	
	@Override
	public AccountAuthLog insert(AccountAuthLog accountAuthLog) {
		// TODO Auto-generated method stub
		return accountAuthLogDao.insert(accountAuthLog);
	}

	@Override
	public Pager findWithPg(AccountAuthLogVO accountAuthLogVO) {
		// TODO Auto-generated method stub
		return accountAuthLogDao.findWithPg(accountAuthLogVO);
	}

}
