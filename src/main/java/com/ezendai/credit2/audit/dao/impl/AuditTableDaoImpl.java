package com.ezendai.credit2.audit.dao.impl;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.audit.dao.AuditTableDao;
import com.ezendai.credit2.audit.model.AuditTable;
import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;

@Repository
public class AuditTableDaoImpl extends BaseDaoImpl<AuditTable> implements AuditTableDao {

	@Override
	public AuditTable getByLoanId(long loanId) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne(getIbatisMapperNameSpace() + ".getByLoanId",loanId);
	}

}