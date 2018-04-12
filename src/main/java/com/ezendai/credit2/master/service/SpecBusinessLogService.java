/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.master.service;

import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.model.SpecBusinessLog;
import com.ezendai.credit2.master.vo.SpecBusinessLogVO;

/**
 * <pre>
 * 特定业务日志业务逻辑层接口
 * </pre>
 *
 * @author zhangshihai
 * @version $Id: SpecBusinessLogService.java, v 0.1 2014年12月11日 下午5:28:11 00221921 Exp $
 */
public interface SpecBusinessLogService {

	Pager findWithPg(SpecBusinessLogVO specBusinessLogVo);
	
	
	SpecBusinessLog insert(SpecBusinessLog specBusinessLog);

}
