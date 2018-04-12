package com.ezendai.credit2.master.service;

import java.util.List;

import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.model.SyncLoan;
import com.ezendai.credit2.master.model.SyncRepaymentPlan;
import com.ezendai.credit2.master.vo.SyncLoanVO;

/**
 * 贷款信息表_第三方数据同步流水
 * @author Ivan
 *
 */
public interface SyncLoanService {
	/**
	 * 获取需要同步的正常借款和展期借款数据
	 * @param syncLoan
	 */
	public List<SyncLoan> getSyncLoanData(SyncLoan syncLoan);
	
	/**
	 * 写入借款及还款计划到同步表
	 */
	public void insertSyncLoanAndRepaymentPlan(SyncLoan syncLoan,List<SyncRepaymentPlan> plans);
	
	/**
	 * 查询借款数据（同步表）
	 * @param syncLoan
	 * @return
	 */
	public Pager findList(SyncLoanVO syncLoanVO);
	
	/**
	 * 更新同步状态
	 */
	public void updateSyncStatus(SyncLoan syncLoan);
	
	
	/**
	 * 
	 * @Description: 获取所有外债贷款
	 * @param @param vo
	 * @param @return   
	 * @return List<SyncLoan>  
	 * @throws
	 * @author 张宜超
	 * @date 2016年2月19日
	 */
	public List<SyncLoan> findListBySyncLoanVO(SyncLoanVO vo);
	
	/**
	 * 
	 * @Description: 获取外债贷款条数
	 * @param @param vo
	 * @param @return   
	 * @return int  
	 * @throws
	 * @author 张宜超
	 * @date 2016年2月19日
	 */
	public int syncLoanCount(SyncLoanVO vo);
	
	public SyncLoan findSyncLoanByLoanId(Long id) ;
}
