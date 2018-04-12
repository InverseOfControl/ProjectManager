/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.audit.service;

import java.util.List;

import com.ezendai.credit2.audit.model.LoanHistory;
import com.ezendai.credit2.framework.util.Pager;


/**
 * <pre>
 * 
 * </pre>
 *
 * @author  
 * @version  
 */
public interface InternalMatchingService {
	 
	     Pager findLoanHistoryWithPG(LoanHistory lh) ;
	 	  List<LoanHistory> selectOptionContacterPhone(LoanHistory lh)  ;
	 	 List<LoanHistory> selectOptionPersonPhone(LoanHistory lh) ;
	 	 List<LoanHistory> selectOptionHomePhone(LoanHistory lh) ;
		 List<LoanHistory> selectOptionCompanyPhone(LoanHistory lh) ;
	 	
	}
