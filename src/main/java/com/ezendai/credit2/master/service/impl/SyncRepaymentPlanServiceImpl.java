package com.ezendai.credit2.master.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.constant.SyncLoanConstants;
import com.ezendai.credit2.master.dao.SyncRepaymentPlanDao;
import com.ezendai.credit2.master.model.SyncRepaymentPlan;
import com.ezendai.credit2.master.service.SyncRepaymentPlanService;
import com.ezendai.credit2.master.vo.SyncRepaymentPlanVO;

/**
 * 还款计划表_第三方数据同步流水
 * @author Ivan
 *
 */
@Service
public class SyncRepaymentPlanServiceImpl implements SyncRepaymentPlanService {
	
	@Autowired
	SyncRepaymentPlanDao syncRepaymentPlanDao;
	
	/**
	 * 查询借款的还款计划列表
	 * @param loanId 借款编号
	 * @return
	 */
	@Override
	public List<SyncRepaymentPlan> getSyncRepaymentPlanData(Long loanId) {
		return syncRepaymentPlanDao.getSyncRepaymentPlanData(loanId);
	}

	/**
	 * 查询借款的期数
	 * @param loanId 借款编号
	 * @return
	 */
	@Override
	public int getSyncRepaymentPlanCount(Long loanId) {
		return syncRepaymentPlanDao.getSyncRepaymentPlanCount(loanId);
	}
	
	/**
	 * 查询还款计划数据（同步表）
	 * @param syncLoan
	 * @return
	 */
	@Override
	public Pager findList(SyncRepaymentPlanVO syncRepaymentPlanVO) {
		return syncRepaymentPlanDao.findList(syncRepaymentPlanVO);
	}
	
	/**
	 * 更新同步状态
	 */
	@Override
	public void updateSyncStatus(SyncRepaymentPlan syncRepaymentPlan) {
		if (SyncLoanConstants.SyncStatus.同步中.getValue().equals(syncRepaymentPlan.getStatus())) {
			syncRepaymentPlan.setSendDate(new Date());
		} else if (SyncLoanConstants.SyncStatus.同步成功.getValue().equals(syncRepaymentPlan.getStatus()) 
				|| SyncLoanConstants.SyncStatus.拒绝同步.getValue().equals(syncRepaymentPlan.getStatus())) {
			syncRepaymentPlan.setReturnDate(new Date());
		}
		syncRepaymentPlanDao.updateSyncStatus(syncRepaymentPlan);
	}
}
