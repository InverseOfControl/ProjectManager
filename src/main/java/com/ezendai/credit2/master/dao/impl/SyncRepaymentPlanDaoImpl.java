package com.ezendai.credit2.master.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.dao.SyncRepaymentPlanDao;
import com.ezendai.credit2.master.model.SyncRepaymentPlan;
import com.ezendai.credit2.master.vo.SyncRepaymentPlanVO;

@Repository
public class SyncRepaymentPlanDaoImpl extends BaseDaoImpl<SyncRepaymentPlan> implements SyncRepaymentPlanDao {
	
	/**
	 * 查询借款的还款计划列表
	 * @param loanId 借款编号
	 * @return
	 */
	@Override
	public List<SyncRepaymentPlan> getSyncRepaymentPlanData(Long loanId) {
		List<SyncRepaymentPlan> list = getSqlSession().selectList(getIbatisMapperNameSpace() + ".getSyncRepaymentPlanData",loanId);
		return list;
	}
	
	
	/**
	 * 查询借款的期数
	 * @param loanId 借款编号
	 * @return
	 */
	@Override
	public int getSyncRepaymentPlanCount(Long loanId) {
		return getSqlSession().selectOne(getIbatisMapperNameSpace() + ".getSyncRepaymentPlanCount", loanId);
	}
	
	/**
	 * 查询还款计划数据（同步表）
	 * @param syncLoan
	 * @return
	 */
	@Override
	public Pager findList(SyncRepaymentPlanVO syncRepaymentPlanVO) {
		Pager pager = syncRepaymentPlanVO.getPager();
		/** 查询总行数 **/
		int totalCount = (Integer)getSqlSession().selectOne(getIbatisMapperNameSpace() + ".count", syncRepaymentPlanVO);
		pager.setTotalCount(totalCount);;
		pager.calStart();
		List<SyncRepaymentPlan> resultList = findListByVo(syncRepaymentPlanVO);
		pager.setResultList(resultList);
		return pager;
	}
	
	/**
	 * 更新同步状态
	 */
	@Override
	public void updateSyncStatus(SyncRepaymentPlan syncRepaymentPlan) {
		getSqlSession().update(getIbatisMapperNameSpace() + ".update", syncRepaymentPlan);
	}
	
}
