package com.ezendai.credit2.audit.service;

import com.ezendai.credit2.audit.model.AuditTable;
import com.ezendai.credit2.audit.vo.AuditTableVO;

public interface EduCreditAuditTableService {
	public AuditTable getByLoanId(long loanId);
	public void insertOrUpdateAuditTable(AuditTable auditTable);
	public void updateAuditTable(AuditTableVO auditTableVO);
}
