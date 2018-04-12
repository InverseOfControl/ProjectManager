package com.ezendai.credit2.audit.dao;

import java.util.List;

import com.ezendai.credit2.audit.model.ApproveResult;
import com.ezendai.credit2.framework.dao.BaseDao;

public interface ApproveResultDao extends BaseDao<ApproveResult> {

	public List<ApproveResult> getApproveResultByLoanId(Long loanId);

}
