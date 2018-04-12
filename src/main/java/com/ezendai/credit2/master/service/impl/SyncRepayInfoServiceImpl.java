package com.ezendai.credit2.master.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.finance.model.RepayInfo;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.constant.SyncLoanConstants;
import com.ezendai.credit2.master.dao.SyncRepayInfoDao;
import com.ezendai.credit2.master.dao.impl.SyncRepayInfoDaoImpl;
import com.ezendai.credit2.master.model.SyncRepayInfo;
import com.ezendai.credit2.master.service.SyncRepayInfoService;
import com.ezendai.credit2.master.vo.SyncRepayInfoVO;

/**
 * 实际还款信息表_第三方数据同步流水
 * @author Ivan
 *
 */
@Service
public class SyncRepayInfoServiceImpl implements SyncRepayInfoService {
	
	@Autowired
	SyncRepayInfoDao syncRepayInfoDao;
	
	/**
	 * 查询正常还款的数据
	 * @param repayInfo
	 */
	@Override
	public List<SyncRepayInfo> queryNormalRepayInfo(RepayInfo repayInfo) {
		return syncRepayInfoDao.queryNormalRepayInfo(repayInfo);
	}
	
	/**
	 * 查询一次性（提前）还款数据 存在逾期一次性还款，结果为集合
	 * @param repayInfo
	 */
	@Override
	public List<SyncRepayInfo> queryPreRepayInfo(RepayInfo repayInfo) {
		return syncRepayInfoDao.queryPreRepayInfo(repayInfo);
	}
	
	/**
	 * 插入数据
	 * @param syncRepayInfo
	 */
	public void insert(SyncRepayInfo syncRepayInfo) {
		((SyncRepayInfoDaoImpl)syncRepayInfoDao).insert(syncRepayInfo);
	}
	
	/**
	 * 查询实际还款数据（同步表）
	 * @param syncLoan
	 * @return
	 */
	@Override
	public Pager findList(SyncRepayInfoVO syncRepayInfoVO) {
		return syncRepayInfoDao.findList(syncRepayInfoVO);
	}
	
	/**
	 * 更新同步状态
	 */
	@Override
	public void updateSyncStatus(SyncRepayInfo syncRepayInfo) {
		if (SyncLoanConstants.SyncStatus.同步中.getValue().equals(syncRepayInfo.getStatus())) {
			syncRepayInfo.setSendDate(new Date());
		} else if (SyncLoanConstants.SyncStatus.同步成功.getValue().equals(syncRepayInfo.getStatus()) 
				||  SyncLoanConstants.SyncStatus.拒绝同步.getValue().equals(syncRepayInfo.getStatus())) {
			syncRepayInfo.setReturnDate(new Date());
		}
		syncRepayInfoDao.updateSyncStatus(syncRepayInfo);
	}
	
	/**
	 * 查询第X期做的提前一次性还清操作
	 */
	@Override
	public int queryEqCurNum(RepayInfo repayInfo) {
		return syncRepayInfoDao.queryEqCurNum(repayInfo);
	}
	
	/**
	 * 检查POJO在数据库是否存在
	 * @param syncRepayInfo
	 * @return
	 */
	@Override
	public boolean isExists(SyncRepayInfo syncRepayInfo) {
		return syncRepayInfoDao.isExists(syncRepayInfo);
	}
	
}
