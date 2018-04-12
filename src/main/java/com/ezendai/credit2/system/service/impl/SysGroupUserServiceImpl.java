package com.ezendai.credit2.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.system.dao.SysGroupUserDao;
import com.ezendai.credit2.system.model.SysGroupUser;
import com.ezendai.credit2.system.model.SysLog;
import com.ezendai.credit2.system.service.SysGroupUserService;
import com.ezendai.credit2.system.service.SysLogService;
import com.ezendai.credit2.system.vo.SysGroupUserVO;

@Service
public class SysGroupUserServiceImpl implements SysGroupUserService {

	@Autowired
	private SysGroupUserDao sysGroupUserDao;
	@Autowired
	private SysLogService sysLogService;

	@Override
	public List<SysGroupUser> findListByVO(SysGroupUserVO sysGroupUserVO) {
		return sysGroupUserDao.findListByVo(sysGroupUserVO);
	}
	
	/**
	 * 修改员工与权限组对照列表
	 * @param sysGroupUserVO
	 * @return
	 */
	@Override
	public void modifyGroupUserMap(SysGroupUserVO sysGroupUserVO) {
		sysGroupUserDao.modifyGroupUserMap(sysGroupUserVO);
		// 插入系统日志
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.EMPLOYEE_INFORMATION_MAINTENANCE.getValue());
		sysLog.setOptType(EnumConstants.OptionType.MAINTENANCE_AUTHORITY.getValue());
		sysLogService.insert(sysLog);
	}
	@Override
	public SysGroupUser findByVO(SysGroupUserVO sysGroupUserVO) {
		return 	sysGroupUserDao.findByVO(sysGroupUserVO);
	}

	@Override
	public List<SysGroupUser> findByVOTwo(SysGroupUserVO sysGroupUserVO) {
		return 	sysGroupUserDao.findByVOTwo(sysGroupUserVO);
	}
}
