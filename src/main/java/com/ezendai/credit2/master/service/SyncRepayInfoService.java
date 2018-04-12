package com.ezendai.credit2.master.service;

import java.util.List;

import com.ezendai.credit2.finance.model.RepayInfo;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.model.SyncRepayInfo;
import com.ezendai.credit2.master.vo.SyncRepayInfoVO;

/**
 * 实际还款信息表_第三方数据同步流水
 * @author Ivan
 *
 */
public interface SyncRepayInfoService {
	/**
	 * 查询正常还款的数据
	 * @param repayInfo
	 */
	public List<SyncRepayInfo> queryNormalRepayInfo(RepayInfo repayInfo);
	
	/**
	 * 查询一次性（提前）还款数据 存在逾期一次性还款，结果为集合
	 * @param repayInfo
	 */
	public List<SyncRepayInfo> queryPreRepayInfo(RepayInfo repayInfo);
	
	/**
	 * 插入数据
	 * @param syncRepayInfo
	 */
	public void insert(SyncRepayInfo syncRepayInfo);
	
	/**
	 * 查询实际还款数据（同步表）
	 * @param syncRepayInfoVO
	 * @return
	 */
	public Pager findList(SyncRepayInfoVO syncRepayInfoVO);
	
	/**
	 * 更新同步状态
	 */
	public void updateSyncStatus(SyncRepayInfo syncRepayInfo);
	
	/**
	 * 查询第X期做的提前一次性还清操作
	 */
	public int queryEqCurNum(RepayInfo repayInfo);
	
	/**
	 * 检查POJO在数据库是否存在
	 * @param syncRepayInfo
	 * @return
	 */
	public boolean isExists(SyncRepayInfo syncRepayInfo);
	
}
