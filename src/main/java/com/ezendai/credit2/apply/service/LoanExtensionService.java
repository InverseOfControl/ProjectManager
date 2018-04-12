/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.apply.service;

import java.util.List;
import java.util.Map;

import com.ezendai.credit2.apply.model.LoanExtension;
import com.ezendai.credit2.apply.vo.LoanExtensionVO;
import com.ezendai.credit2.framework.util.Pager;

/**
 * <pre>
 * 借款-展期关联业务逻辑
 * </pre>
 *
 * @author zhangshihai
 * @version $Id: LoanExtensionService.java, v 0.1 2015年3月10日 下午2:32:24 00221921 Exp $
 */
public interface LoanExtensionService {
	
	LoanExtension insert(LoanExtension loanExtension);
	
	void deleteById(Long id);
	
	void deleteByIdList(LoanExtensionVO loanExtensionVO);
	
	int update(LoanExtensionVO loanExtensionVO);
	
	LoanExtension get(Long id);
	
	List<LoanExtension> findListByVo(LoanExtensionVO loanExtensionVO);
	
	boolean exists(Map<String, Object> map);

	Pager findWithPg(LoanExtensionVO loanExtensionVO);

	boolean exists(Long id);
	
	/**
	 * 
	 * <pre>
	 * 根据展期id获得loanId
	 * </pre>
	 *
	 * @param extensionId
	 * @return
	 */
	public Long getLoanIdByExtensionId(Long extensionId);
	
	/**
	 * 
	 * <pre>
	 * 根据extensionId, extensionTime获取上一期extensionId，如果为第一次展期，则获取原loanId
	 * </pre>
	 *
	 * @param loanId
	 * @param extensionTime
	 * @return
	 */
	public Long getPreExtensionId(Long extensionId, Integer extensionTime);
	
	public Integer maxExtensionTime();
}
