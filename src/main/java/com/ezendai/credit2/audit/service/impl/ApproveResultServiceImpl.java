package com.ezendai.credit2.audit.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.audit.dao.ApproveResultDao;
import com.ezendai.credit2.audit.model.ApproveResult;
import com.ezendai.credit2.audit.service.ApproveResultService;
import com.ezendai.credit2.audit.vo.ApproveResultVO;

@Service
public class ApproveResultServiceImpl implements ApproveResultService {
	@Autowired
	private ApproveResultDao approveResultDao;

	@Override
	public ApproveResult get(Long id) {
		return approveResultDao.get(id);
	}

	@Override
	public void deleteById(Long id) {
		approveResultDao.deleteById(id);
	}

	@Override
	public ApproveResult insert(ApproveResult approveResult) {
		return approveResultDao.insert(approveResult);
	}

	@Override
	public int update(ApproveResultVO approveResultVO) {
		return approveResultDao.update(approveResultVO);
	}

	@Override
	public List<ApproveResult> getApproveResultByLoanId(Long loanId) {
		return approveResultDao.getApproveResultByLoanId(loanId);
	}

	@Override
	public List<ApproveResult> findListByVo(ApproveResultVO approveResultVO) {
		return approveResultDao.findListByVo(approveResultVO);
	}

}
