/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.master.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.dao.SpecBusinessLogDao;
import com.ezendai.credit2.master.model.SpecBusinessLog;
import com.ezendai.credit2.master.service.SpecBusinessLogService;
import com.ezendai.credit2.master.vo.SpecBusinessLogVO;

/**
 * <pre>
 * 特定业务日志业务逻辑接口实现
 * </pre>
 *
 * @author 00221921
 * @version $Id: SpecBusinessLogServiceImpl.java, v 0.1 2014年12月11日 下午5:29:07 00221921 Exp $
 */
@Service
public class SpecBusinessLogServiceImpl implements SpecBusinessLogService {

	@Autowired
	private SpecBusinessLogDao specBusinessLogDao;

	@Override
	public Pager findWithPg(SpecBusinessLogVO specBusinessLogVo) {
		return specBusinessLogDao.findWithPg(specBusinessLogVo);
	}

	@Override
	public SpecBusinessLog insert(SpecBusinessLog specBusinessLog) {
		return specBusinessLogDao.insert(specBusinessLog);
	}
}
