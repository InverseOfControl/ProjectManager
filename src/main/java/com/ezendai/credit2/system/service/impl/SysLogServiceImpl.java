package com.ezendai.credit2.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.dao.SysEnumerateDao;
import com.ezendai.credit2.master.model.SysEnumerate;
import com.ezendai.credit2.master.vo.SysEnumerateVO;
import com.ezendai.credit2.system.dao.SysLogDao;
import com.ezendai.credit2.system.model.SysLog;
import com.ezendai.credit2.system.model.SysUser;
import com.ezendai.credit2.system.service.SysLogService;
import com.ezendai.credit2.system.service.SysUserService;
import com.ezendai.credit2.system.vo.SysLogVO;
import com.ezendai.credit2.system.vo.SysUserVO;

@Service
public class SysLogServiceImpl implements  SysLogService{
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysLogDao sysLogDao;
	@Autowired
	private SysEnumerateDao sysEnumerateDao;
	/**
	 * 
	 * <pre>
	 * 获取所有用户
	 * </pre>
	 *
	 * @param sysUserVo
	 * @return
	 */
	@Override
	public List<SysUser> findAllSysUser(SysUserVO sysUserVo) {
		return sysUserService.findListByVO(sysUserVo);
	}
	/***
	 * 
	 * <pre>
	 * 以枚举 类型为条件加载的数据字典数据
	 * </pre>
	 *
	 * @param enumType
	 * @return
	 */
	@Override
	public List<SysEnumerate> findSysEnumerateListByType(String enumType) {
		List<SysEnumerate> listEnum = sysEnumerateDao.findSysEnumerateListByType(enumType);
		return listEnum;
	}
	/***
	 * 
	 * <pre>
	 * 以 enumType 和 code为条件加载的数据字典数据
	 * </pre>
	 * 必须指定enumType
	 * Code前缀 取得枚举值 例如:获取code为 7 开头
	 * @param enumType
	 * @return
	 */
	@Override
	public List<SysEnumerate> findOptionTypeListByOptionModule(SysEnumerateVO sysEnumerateVo) {		
		List<SysEnumerate> listEnum = sysEnumerateDao.findOptionTypeListByOptionModule(sysEnumerateVo);
		return listEnum;
	}
	
	/***
	 * 根据参数查询日志列表
	 * @param sysLog
	 * @return
	 * @see com.ezendai.credit2.system.dao.SysLogDao#findSysLogByParam(com.ezendai.credit2.system.model.SysLog)
	 */
	@Override
	public Pager findSysLogByParam(SysLogVO sysLogVo) {
		return sysLogDao.findWithPg(sysLogVo);
	}
	
	/***
	 * 新增 日志
	 * @param sysLog
	 * @return
	 * @see com.ezendai.credit2.system.dao.SysLogDao#findSysLogByParam(com.ezendai.credit2.system.model.SysLog)
	 */
	@Override
	public SysLog insert(SysLog sysLog) {
		if(ApplicationContext.getUser()!=null){
			sysLog.setIpAddress(ApplicationContext.getUser().getIpAddress());	
		}		
		return sysLogDao.insert(sysLog);
	}
	
}
