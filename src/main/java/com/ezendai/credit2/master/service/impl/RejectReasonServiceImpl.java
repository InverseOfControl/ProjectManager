package com.ezendai.credit2.master.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.master.dao.RejectReasonDao;
import com.ezendai.credit2.master.model.RejectReason;
import com.ezendai.credit2.master.service.RejectReasonService;
import com.ezendai.credit2.master.vo.RejectReasonVO;

@Service
public class RejectReasonServiceImpl implements RejectReasonService {
	@Autowired
	private RejectReasonDao rejectReasonDao;

	@Override
	public List<RejectReason> findRejectReasonByTypeAndLoanType(String type, Integer loanType) {
		RejectReasonVO rejectReasonVO = new RejectReasonVO();
		rejectReasonVO.setType(type);
		rejectReasonVO.setLoanType(loanType);
		return rejectReasonDao.findRejectReasonByTypeAndLoanType(rejectReasonVO);
	}

	@Override
	public List<RejectReason> findRejectReasonByParentId(Long parentId) {
		RejectReasonVO rejectReasonVO = new RejectReasonVO();
		rejectReasonVO.setParentId(parentId);
		return rejectReasonDao.findRejectReasonByParentId(rejectReasonVO);
	}

	@Override
	public RejectReason get(Long id) {
		return rejectReasonDao.get(id);
	}

}
