package com.ezendai.credit2.audit.dao;

import java.util.List;

import com.ezendai.credit2.audit.model.LoanHistory;
import com.ezendai.credit2.framework.dao.BaseDao;
import com.ezendai.credit2.framework.util.Pager;

public interface InternalMatchingDao extends BaseDao<LoanHistory> {
	
	 
	 
	Pager findLoanHistoryWithPG(LoanHistory lh);
	
	 
	List<LoanHistory>	selectOptionContacterPhone(LoanHistory lh);
	
	List<LoanHistory>	selectOptionPersonPhone(LoanHistory lh);
	 List<LoanHistory> selectOptionHomePhone(LoanHistory lh) ;
	 List<LoanHistory> selectOptionCompanyPhone(LoanHistory lh) ;
	 
}
