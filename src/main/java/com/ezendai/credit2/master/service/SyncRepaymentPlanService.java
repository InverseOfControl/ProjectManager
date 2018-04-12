package com.ezendai.credit2.master.service;

import java.util.List;

import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.model.SyncRepaymentPlan;
import com.ezendai.credit2.master.vo.SyncRepaymentPlanVO;

/**
 * 还款计划表_第三方数据同步流水
 * @author Ivan
 *
 */
public interface SyncRepaymentPlanService {
	/**
	 * 查询借款的还款计划列表
	 * @param loanId 借款编号
	 * @return
	 */
	public List<SyncRepaymentPlan> getSyncRepaymentPlanData(Long loanId);
	
	/**
	 * 查询借款的期数
	 * @param loanId 借款编号
	 * @return
	 */
	public int getSyncRepaymentPlanCount(Long loanId);
	
	/**
	 * 查询还款计划数据（同步表）
	 * @param syncLoan
	 * @return
	 */
	public Pager findList(SyncRepaymentPlanVO syncRepaymentPlanVO);
	
	/**
	 * 更新同步状态
	 */
	public void updateSyncStatus(SyncRepaymentPlan syncRepaymentPlan);
	
}
