package com.ezendai.credit2.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.system.dao.SysJobLogDao;
import com.ezendai.credit2.system.model.SysJobLog;
import com.ezendai.credit2.system.service.SysJobLogService;
import com.ezendai.credit2.system.vo.SysJobLogVO;

@Service
public class SysJobLogServiceImpl implements SysJobLogService{
	@Autowired
	private SysJobLogDao sysJobLogDao;
	
	@Override
	public SysJobLog insertSysJobLog(SysJobLog sysJobLog) {
		return sysJobLogDao.insert(sysJobLog);
	}

	@Override
	public Pager findSysJobLogWithPG(SysJobLogVO sysJobLogVO) {
		return sysJobLogDao.findWithPg(sysJobLogVO);
	}

	@Override
	public List<SysJobLog> findList(SysJobLogVO sysJobLogVO) {
		return sysJobLogDao.findListByVo(sysJobLogVO);
	}

}
