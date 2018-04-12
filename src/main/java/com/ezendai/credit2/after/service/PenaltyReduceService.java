/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.after.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <pre>
 * 罚息减免
 * </pre>
 *
 * @author zhangshihai
 * @version $Id: PenaltyReduceService.java, v 0.1 2014年12月16日 下午3:07:58 00221921 Exp $
 */
public interface PenaltyReduceService {
	/**
	 * 
	 * <pre>
	 * 批量导入
	 * </pre>
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	public Map<String, Object> batchUpload(HttpServletRequest request, HttpServletResponse response);

	Map<String, Object> batchCostUpload(HttpServletRequest request,
			HttpServletResponse response);
}
