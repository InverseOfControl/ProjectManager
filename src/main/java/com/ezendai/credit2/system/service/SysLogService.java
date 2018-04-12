package com.ezendai.credit2.system.service;

import java.util.List;

import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.model.SysEnumerate;
import com.ezendai.credit2.master.vo.SysEnumerateVO;
import com.ezendai.credit2.system.model.SysLog;
import com.ezendai.credit2.system.model.SysUser;
import com.ezendai.credit2.system.vo.SysLogVO;
import com.ezendai.credit2.system.vo.SysUserVO;

public interface SysLogService {
	
	/**
	 * 
	 * <pre>
	 * 获取所有用户
	 * </pre>
	 *
	 * @param sysUserVo
	 * @return
	 */
	List<SysUser> findAllSysUser(SysUserVO sysUserVo);
	/***
	 * 根据参数查询日志列表
	 * @param sysLog
	 * @return
	 * @see com.ezendai.credit2.system.dao.SysLogDao#findSysLogByParam(com.ezendai.credit2.system.model.SysLog)
	 */
	Pager findSysLogByParam(SysLogVO  sysLogVo);
	/***
	 * 
	 * <pre>
	 * 以枚举 类型为条件加载的数据字典数据
	 * </pre>
	 *
	 * @param enumType
	 * @return
	 */
	List<SysEnumerate> findSysEnumerateListByType(String enumType);
	/***
	 * 根据OPTION_MODULE类型的Code为前缀,查询OPTION_TYPE类型的List
	 * @param  enumType enumCode
	 * @return
	 * @see com.ezendai.credit2.master.dao.SysEnumerateDao#findSysEnumerateListByCode(com.ezendai.credit2.master.vo.SysEnumerateVO)
	 */
	List<SysEnumerate> findOptionTypeListByOptionModule(SysEnumerateVO sysEnumerateVo);
	/**
	 * 
	 * <pre>
	 * 新增日志 
	 * </pre>
	 *
	 * @param sysLog
	 * @return
	 */
	SysLog insert(SysLog sysLog);

}
