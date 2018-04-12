package com.ezendai.credit2.audit.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.audit.dao.AuditTableSeqlistDao;
import com.ezendai.credit2.audit.model.AuditTableSeqlist;
import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;

@Repository
public class AuditTableSeqlistDaoImpl extends BaseDaoImpl<AuditTableSeqlist> implements AuditTableSeqlistDao {

	@Override
	public List<AuditTableSeqlist> findByLoanId(long loanId) {
		// TODO Auto-generated method stub
		return getSqlSession().selectList(getIbatisMapperNameSpace() + ".findByLoanId",loanId);
	}

	@Override
	public void deleteSeqById(Long id) {
		// TODO Auto-generated method stub
		getSqlSession().update(getIbatisMapperNameSpace() + ".deleteSeqById",id);
	}
}