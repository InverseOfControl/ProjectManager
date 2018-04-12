/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.audit.service;

import java.util.Map;

import com.ezendai.credit2.audit.vo.FirstApproveVO;
import com.ezendai.credit2.framework.util.Pager;


/**
 * <pre>
 * 
 * </pre>
 *
 * @author  
 * @version  
 */
public interface FirstApproveService {
	
	/**
	 *获取所有方案
	 * @param Loan
	 */
	  Pager findFirstApproveWithPG(FirstApproveVO vo) ;
	  
	  
		 String getAcceptAudit(long id);
		 
			  void updateAcceptAudit(Map map) ;
			  
			  int selectSysUserCount(String code);
	}
