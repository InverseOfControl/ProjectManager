package com.ezendai.credit2.report.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.report.dao.OverdueMsgLogDao;
import com.ezendai.credit2.report.service.OverdueMsgLogService;
import com.ezendai.credit2.report.vo.OverdueMsgLogVO;
@Service
public class OverdueMsgLogServiceImpl implements OverdueMsgLogService{
	@Autowired
	private OverdueMsgLogDao overdueMsgLogDao;
	@Override
	public Pager findWithPG(OverdueMsgLogVO OverdueMsgLogVO) {
		// TODO Auto-generated method stub
		return overdueMsgLogDao.findWithPG(OverdueMsgLogVO);
	}

}
