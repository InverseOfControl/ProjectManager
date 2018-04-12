package com.ezendai.credit2.master.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.constant.SyncLoanConstants;
import com.ezendai.credit2.master.dao.SyncLoanDao;
import com.ezendai.credit2.master.model.SyncLoan;
import com.ezendai.credit2.master.model.SyncRepaymentPlan;
import com.ezendai.credit2.master.service.SyncLoanService;
import com.ezendai.credit2.master.vo.SyncLoanVO;

/**
 * 贷款信息表_第三方数据同步流水
 * @author Ivan
 *
 */
@Service
public class SyncLoanServiceImpl implements SyncLoanService {
	
	@Autowired
	SyncLoanDao syncLoanDao;
	
	/**
	 * 获取需要同步的正常借款和展期借款数据
	 * @param syncLoan
	 */
	@Override
	public List<SyncLoan> getSyncLoanData(SyncLoan syncLoan) {
		return syncLoanDao.getSyncLoanData(syncLoan);
	}
	
	/**
	 * 写入借款及还款计划到同步表
	 */
	@Override
	public void insertSyncLoanAndRepaymentPlan(SyncLoan syncLoan,List<SyncRepaymentPlan> plans) {
		syncLoanDao.insertSyncLoanAndRepaymentPlan(syncLoan, plans);
	}
	
	/**
	 * 查询借款数据（同步表）
	 * @param syncLoan
	 * @return
	 */
	@Override
	public Pager findList(SyncLoanVO syncLoanVO) {
		return syncLoanDao.findList(syncLoanVO);
	}
	
	/**
	 * 更新同步状态
	 */
	@Override
	public void updateSyncStatus(SyncLoan syncLoan) {
		if (SyncLoanConstants.SyncStatus.同步中.getValue().equals(syncLoan.getStatus())) {
			syncLoan.setSendDate(new Date());
		} else if (SyncLoanConstants.SyncStatus.同步成功.getValue().equals(syncLoan.getStatus()) 
				|| SyncLoanConstants.SyncStatus.拒绝同步.getValue().equals(syncLoan.getStatus())) {
			syncLoan.setReturnDate(new Date());
		}
		syncLoanDao.updateSyncStatus(syncLoan);
	}

	@Override
	public List<SyncLoan> findListBySyncLoanVO(SyncLoanVO vo) {
		
		return syncLoanDao.findListBySyncLoanVO(vo);
	}

	@Override
	public int syncLoanCount(SyncLoanVO vo) {
		
		return syncLoanDao.syncLoanCount(vo);
	}

	@Override
	public SyncLoan findSyncLoanByLoanId(Long id) {
		// TODO Auto-generated method stub
		return syncLoanDao.findSyncLoanByLoanId(id);
	}
	
}
