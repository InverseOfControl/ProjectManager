package com.ezendai.credit2.audit.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezendai.credit2.audit.assembler.EduCreditAuditTableSeqAssembler;
import com.ezendai.credit2.audit.dao.AuditTableSeqlistDao;
import com.ezendai.credit2.audit.model.AuditTableSeqlist;
import com.ezendai.credit2.audit.service.EduCreditAuditTableSeqService;
import com.ezendai.credit2.audit.vo.AuditTableSeqlistVO;

@Service
public class EduCreditAuditTableSeqServiceImpl implements
		EduCreditAuditTableSeqService {

	@Autowired
	private AuditTableSeqlistDao auditTableSeqlistDao;

	@Override
	@Transactional
	public void insertOrUpdateAuditTableSeq(List<AuditTableSeqlistVO> data) {
		// TODO Auto-generated method stub
		for(int i=0;i<data.size();i++){
			AuditTableSeqlistVO  jobj =   data.get(i);
			AuditTableSeqlist auditTableSeqlist=EduCreditAuditTableSeqAssembler.transferVO2Model(jobj);
			if(auditTableSeqlist.getType()!=null){
				if(auditTableSeqlist.getId()==null){
					auditTableSeqlistDao.insert(auditTableSeqlist);
				}else{
					AuditTableSeqlistVO auditTableSeqlistVO = EduCreditAuditTableSeqAssembler.transferModel2VO(auditTableSeqlist);
					auditTableSeqlistDao.update(auditTableSeqlistVO);
				}
			}
		}
	}

	@Override
	public List<AuditTableSeqlist> findByLoanId(long loanId) {
		// TODO Auto-generated method stub
		return auditTableSeqlistDao.findByLoanId(loanId);
	}

	@Override
	@Transactional
	public void delAuditTableSeq(List<AuditTableSeqlistVO> data) {
		// TODO Auto-generated method stub
		for(int i=0;i<data.size();i++){
			AuditTableSeqlistVO  jobj = data.get(i);
			AuditTableSeqlist auditTableSeqlist=EduCreditAuditTableSeqAssembler.transferVO2Model(jobj);
			auditTableSeqlistDao.deleteSeqById(auditTableSeqlist.getId());
		}
	}
}
