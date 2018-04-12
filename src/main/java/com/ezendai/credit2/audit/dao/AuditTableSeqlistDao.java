package com.ezendai.credit2.audit.dao;

import java.util.List;

import com.ezendai.credit2.audit.model.AuditTableSeqlist;
import com.ezendai.credit2.framework.dao.BaseDao;


public interface AuditTableSeqlistDao  extends BaseDao<AuditTableSeqlist>{

	List<AuditTableSeqlist> findByLoanId(long loanId);

	void deleteSeqById(Long id);
}