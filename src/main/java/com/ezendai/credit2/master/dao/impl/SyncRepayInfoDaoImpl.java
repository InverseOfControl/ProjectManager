package com.ezendai.credit2.master.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.finance.model.RepayInfo;
import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.dao.SyncRepayInfoDao;
import com.ezendai.credit2.master.model.SyncRepayInfo;
import com.ezendai.credit2.master.vo.SyncRepayInfoVO;

@Repository
public class SyncRepayInfoDaoImpl extends BaseDaoImpl<SyncRepayInfo> implements SyncRepayInfoDao {
	
	/**
	 * 查询正常还款的数据
	 * @param repayInfo
	 */
	@Override
	public List<SyncRepayInfo> queryNormalRepayInfo(RepayInfo repayInfo) {
		List<SyncRepayInfo> syncRepayInfos  = new ArrayList<SyncRepayInfo>();
		syncRepayInfos = getSqlSession().selectList(getIbatisMapperNameSpace() + ".queryNormalRepayInfo",repayInfo);
		return syncRepayInfos;
	}
	
	/**
	 * 查询一次性（提前）还款数据 存在逾期一次性还款，结果为集合
	 * @param repayInfo
	 */
	@Override
	public List<SyncRepayInfo> queryPreRepayInfo(RepayInfo repayInfo) {
		List<SyncRepayInfo> resultList = getSqlSession().selectList(getIbatisMapperNameSpace() + ".queryPreRepayInfo",repayInfo);
		return resultList;
	}
	
	/**
	 * 查询实际还款数据（同步表）
	 * @param syncLoan
	 * @return
	 */
	@Override
	public Pager findList(SyncRepayInfoVO syncRepayInfoVO) {
		Pager pager = syncRepayInfoVO.getPager();
		/** 查询总行数 **/
		int totalCount = (Integer)getSqlSession().selectOne(getIbatisMapperNameSpace() + ".count", syncRepayInfoVO);
		pager.setTotalCount(totalCount);;
		pager.calStart();
		List<SyncRepayInfo> resultList = findListByVo(syncRepayInfoVO);
		pager.setResultList(resultList);
		return pager;
	}
	
	/**
	 * 更新同步状态
	 */
	@Override
	public void updateSyncStatus(SyncRepayInfo syncRepayInfo) {
		getSqlSession().update(getIbatisMapperNameSpace() + ".update", syncRepayInfo);
	}
	
	/**
	 * 查询第X期做的提前一次性还清操作
	 */
	@Override
	public int queryEqCurNum(RepayInfo repayInfo) {
		Integer curNum = getSqlSession().selectOne(getIbatisMapperNameSpace() + ".queryEqCurNum", repayInfo);
		if (curNum == null) {
			curNum = 0;
		}
		return curNum;
	}
	
	/**
	 * 检查POJO在数据库是否存在
	 * @param syncRepayInfo
	 * @return
	 */
	@Override
	public boolean isExists(SyncRepayInfo syncRepayInfo) {
		Integer curNum = getSqlSession().selectOne(getIbatisMapperNameSpace() + ".exists", syncRepayInfo);
		return curNum > 0;
	}
	
}
