package com.ezendai.credit2.audit.dao;

import com.ezendai.credit2.audit.model.AuditTable;
import com.ezendai.credit2.framework.dao.BaseDao;


public interface AuditTableDao extends BaseDao<AuditTable> {
	
	AuditTable getByLoanId(long loanId);
}