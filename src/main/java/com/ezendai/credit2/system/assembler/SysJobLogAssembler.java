package com.ezendai.credit2.system.assembler;

import com.ezendai.credit2.system.model.SysJobLog;
import com.ezendai.credit2.system.vo.SysJobLogVO;

/**
 * 
 * <pre>
 * 系统工作日志类型转换
 * </pre>
 *
 * @author zhangshihai
 * @version $Id: SysJobLogAssembler.java, v 0.1 2014年9月4日 上午11:54:21 zhangshihai Exp $
 */
public class SysJobLogAssembler {
	
	/**
	 * 
	 * <pre>
	 * Model转为VO
	 * </pre>
	 *
	 * @param sysJobLog
	 * @return
	 */
	public static SysJobLogVO transferModel2VO (SysJobLog sysJobLog) {
		if(sysJobLog == null) {
			return null;
		}
		
		SysJobLogVO sysJobLogVO = new SysJobLogVO();
		sysJobLogVO.setId(sysJobLog.getId());
		sysJobLogVO.setName(sysJobLog.getName());
		sysJobLogVO.setRunIP(sysJobLog.getRunIP());
		sysJobLogVO.setStartTime(sysJobLog.getStartTime());
		sysJobLogVO.setEndTime(sysJobLog.getEndTime());
		sysJobLogVO.setResultState(sysJobLog.getResultState());
		sysJobLogVO.setHandleNum(sysJobLog.getHandleNum());
		sysJobLogVO.setSuccessNum(sysJobLog.getSuccessNum());
		sysJobLogVO.setErrorMessage(sysJobLog.getErrorMessage());
		sysJobLogVO.setRemark(sysJobLog.getRemark());
		sysJobLogVO.setCreator(sysJobLog.getCreator());
		sysJobLogVO.setCreatorId(sysJobLog.getCreatorId());
		sysJobLogVO.setCreatedTime(sysJobLog.getCreatedTime());
		sysJobLogVO.setVersion(sysJobLog.getVersion());
		
		return sysJobLogVO;
	}
	
	/**
	 * 
	 * <pre>
	 * VO转Model
	 * </pre>
	 *
	 * @param sysJobLogVO
	 * @return
	 */
	public static SysJobLog transferVO2Model (SysJobLogVO sysJobLogVO) {
		if(sysJobLogVO == null) {
			return null;
		}
		
		SysJobLog sysJobLog = new SysJobLog();
		sysJobLog.setId(sysJobLogVO.getId());
		sysJobLog.setName(sysJobLogVO.getName());
		sysJobLog.setRunIP(sysJobLogVO.getRunIP());
		sysJobLog.setStartTime(sysJobLogVO.getStartTime());
		sysJobLog.setEndTime(sysJobLogVO.getEndTime());
		sysJobLog.setResultState(sysJobLogVO.getResultState());
		sysJobLog.setHandleNum(sysJobLogVO.getHandleNum());
		sysJobLog.setSuccessNum(sysJobLogVO.getSuccessNum());
		sysJobLog.setErrorMessage(sysJobLogVO.getErrorMessage());
		sysJobLog.setRemark(sysJobLogVO.getRemark());
		sysJobLog.setCreator(sysJobLogVO.getCreator());
		sysJobLog.setCreatorId(sysJobLogVO.getCreatorId());
		sysJobLog.setCreatedTime(sysJobLogVO.getCreatedTime());
		sysJobLog.setVersion(sysJobLogVO.getVersion());
		
		return sysJobLog;
	}
}
