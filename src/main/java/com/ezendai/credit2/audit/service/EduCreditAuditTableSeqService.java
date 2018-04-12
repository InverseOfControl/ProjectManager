package com.ezendai.credit2.audit.service;

import java.util.List;

import com.ezendai.credit2.audit.model.AuditTableSeqlist;
import com.ezendai.credit2.audit.vo.AuditTableSeqlistVO;

public interface EduCreditAuditTableSeqService {
	public void insertOrUpdateAuditTableSeq(List<AuditTableSeqlistVO> data);

	public List<AuditTableSeqlist> findByLoanId(long loanId);

	public void delAuditTableSeq(List<AuditTableSeqlistVO> data);
}
