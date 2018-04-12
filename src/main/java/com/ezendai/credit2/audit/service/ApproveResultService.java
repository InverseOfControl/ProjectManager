package com.ezendai.credit2.audit.service;

import java.util.List;

import com.ezendai.credit2.audit.model.ApproveResult;
import com.ezendai.credit2.audit.vo.ApproveResultVO;

public interface ApproveResultService {
	ApproveResult get(Long id);

	void deleteById(Long id);

	ApproveResult insert(ApproveResult approveResult);

	int update(ApproveResultVO approveResultVO);

	List<ApproveResult> getApproveResultByLoanId(Long loanId);
	
	List<ApproveResult> findListByVo(ApproveResultVO approveResultVO);
}
