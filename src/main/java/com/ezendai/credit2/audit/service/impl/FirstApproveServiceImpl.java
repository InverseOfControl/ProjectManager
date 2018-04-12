/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.audit.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.audit.dao.FirstApproveDao;
import com.ezendai.credit2.audit.service.FirstApproveService;
import com.ezendai.credit2.audit.vo.FirstApproveVO;
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
public class FirstApproveServiceImpl implements FirstApproveService {

	@Autowired
	private FirstApproveDao firstDao;

	@Override
	public Pager findFirstApproveWithPG(FirstApproveVO vo) {
		// TODO Auto-generated method stub
		return firstDao.findFirstApproveWithPG(vo);
	}

	@Override
	public String getAcceptAudit(long id) {
		// TODO Auto-generated method stub
		return firstDao.getAcceptAudit(id);
	}

	@Override
	public void updateAcceptAudit(Map map) {
		// TODO Auto-generated method stub
		  firstDao.updateAcceptAudit(map); 
		  
	}

	@Override
	public int selectSysUserCount(String code) {
		// TODO Auto-generated method stub
		return  firstDao.selectSysUserCount(code); 
	}
}
