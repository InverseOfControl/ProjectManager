package com.ezendai.credit2.system.service;

import java.util.List;

import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.system.model.SysJobLog;
import com.ezendai.credit2.system.vo.SysJobLogVO;

public interface SysJobLogService {
	/**
	 * 
	 * <pre>
	 * 添加系统工作日志记录
	 * </pre>
	 *
	 * @param sysJobLog
	 * @return
	 */
	public SysJobLog insertSysJobLog(SysJobLog sysJobLog);
	
	Pager findSysJobLogWithPG(SysJobLogVO sysJobLogVO);
	
	List<SysJobLog> findList(SysJobLogVO sysJobLogVO);
}
