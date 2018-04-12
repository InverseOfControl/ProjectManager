package com.ezendai.credit2.system.service;

import com.ezendai.credit2.master.enumerate.EnumConstants.SerialNum;
import com.ezendai.credit2.system.vo.SerialNumResult;

/**
 * <pre>
 * 序列号服务
 * </pre>
 *
 * @author fangqingyuan
 * @version $Id: SysSerialNumService.java, v 0.1 2014-7-5 下午3:07:54 fangqingyuan Exp $
 */
public interface SysSerialNumService {

	/**
	 * <pre>
	 * 获取某个业务的序列号
	 * </pre>
	 *
	 * @param serialNum
	 * @return
	 */
	SerialNumResult getSerialNum(SerialNum serialNum);

}
