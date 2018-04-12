package com.ezendai.credit2.apply.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.apply.dao.SysProductUserDao;
import com.ezendai.credit2.apply.service.SysProductUserService;
import com.ezendai.credit2.apply.vo.SysProductUserVO;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.system.model.SysLog;
import com.ezendai.credit2.system.service.SysLogService;

@Service
public class SysProductUserServiceImpl implements SysProductUserService{
	@Autowired
	private SysProductUserDao sysProductUserDao;
	@Autowired
	private SysLogService sysLogService;
	
	@Override
	public List<Long> getProductIdByUserId(Long userId) {
		return sysProductUserDao.findProductIdByUserId(userId);
	}
	
	/**
	 * 修改员工与产品组对照列表
	 * @param sysGroupUserVO
	 * @return
	 */
	@Override
	public void modifyProductUserMap(SysProductUserVO sysProductUserVO) {
		sysProductUserDao.modifyProductUserMap(sysProductUserVO);
		// 插入系统日志
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.EMPLOYEE_INFORMATION_MAINTENANCE.getValue());
		sysLog.setOptType(EnumConstants.OptionType.MAINTAIN_PRODUCT.getValue());
		sysLogService.insert(sysLog);
	}
}
