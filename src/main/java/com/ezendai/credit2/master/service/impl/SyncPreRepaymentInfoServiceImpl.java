package com.ezendai.credit2.master.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.constant.SyncLoanConstants;
import com.ezendai.credit2.master.dao.SyncPreRepaymentInfoDao;
import com.ezendai.credit2.master.model.SyncPreRepaymentInfo;
import com.ezendai.credit2.master.service.SyncPreRepaymentInfoService;
import com.ezendai.credit2.master.vo.SyncPreRepaymentInfoVO;

/**
 * 提前一次性还款信息表_第三方数据同步流水
 * @author Ivan
 *
 */
@Service
public class SyncPreRepaymentInfoServiceImpl implements SyncPreRepaymentInfoService {
	
	@Autowired
	SyncPreRepaymentInfoDao syncPreRepaymentInfoDao;
	
	/**
	 * 批量插入异常还款人员信息
	 * @param syncLoan
	 */
	@Override
	public void batchInsert(List<SyncPreRepaymentInfo> syncPreRepaymentInfos) {
		syncPreRepaymentInfoDao.batchInsert(syncPreRepaymentInfos);
	}
	
	/**
	 * 查询异常借款信息
	 * @param syncPreRepaymentInfo
	 * @return
	 */
	@Override
	public List<SyncPreRepaymentInfo>queryPreRepayInfo(SyncPreRepaymentInfo syncPreRepaymentInfo) {
		return syncPreRepaymentInfoDao.queryPreRepayInfo(syncPreRepaymentInfo);
	}
	
	
	/**
	 * 查询提前一次性还款数据（同步表）
	 * @param syncPreRepaymentInfoVO
	 * @return
	 */
	@Override
	public Pager findList(SyncPreRepaymentInfoVO syncPreRepaymentInfoVO) {
		return syncPreRepaymentInfoDao.findList(syncPreRepaymentInfoVO);
	}
	
	/**
	 * 更新同步状态
	 */
	@Override
	public void updateSyncStatus(SyncPreRepaymentInfo syncPreRepaymentInfo) {
		if (SyncLoanConstants.SyncStatus.同步中.getValue().equals(syncPreRepaymentInfo.getStatus())) {
			syncPreRepaymentInfo.setSendDate(new Date());
		} else if (SyncLoanConstants.SyncStatus.同步成功.getValue().equals(syncPreRepaymentInfo.getStatus()) 
				|| SyncLoanConstants.SyncStatus.拒绝同步.getValue().equals(syncPreRepaymentInfo.getStatus())) {
			syncPreRepaymentInfo.setReturnDate(new Date());
		}
		syncPreRepaymentInfoDao.updateSyncStatus(syncPreRepaymentInfo);
	}
	
}
