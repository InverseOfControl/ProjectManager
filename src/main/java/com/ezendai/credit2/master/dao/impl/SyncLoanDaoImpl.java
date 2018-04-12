package com.ezendai.credit2.master.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.dao.SyncLoanDao;
import com.ezendai.credit2.master.dao.SyncRepaymentPlanDao;
import com.ezendai.credit2.master.model.SyncLoan;
import com.ezendai.credit2.master.model.SyncRepaymentPlan;
import com.ezendai.credit2.master.vo.SyncLoanVO;

@Repository
public class SyncLoanDaoImpl extends BaseDaoImpl<SyncLoan> implements SyncLoanDao {
	
	@Autowired
	SyncRepaymentPlanDao syncRepaymentPlanDao;
	
	/**
	 * 获取需要同步的正常借款和展期借款数据
	 * @param syncLoan
	 */
	@Override
	public List<SyncLoan> getSyncLoanData(SyncLoan syncLoan) {
		List<SyncLoan> list = getSqlSession().selectList(getIbatisMapperNameSpace() + ".getSyncLoanData", syncLoan);
		return list;
	}
	
	/**
	 * 写入借款及还款计划到同步表
	 */
	@Override
	@Transactional
	public void insertSyncLoanAndRepaymentPlan(SyncLoan syncLoan,List<SyncRepaymentPlan> plans){
		/**判断此借款是否已经生成了**/
		int count = (Integer)getSqlSession().selectOne(getIbatisMapperNameSpace() + ".isExistsByLoanId",syncLoan);
		if (count == 0) {
			insert(syncLoan);
			for (SyncRepaymentPlan syncRepaymentPlan : plans) {
				((SyncRepaymentPlanDaoImpl)syncRepaymentPlanDao).insert(syncRepaymentPlan);
			}
		}
	}
	
	/**
	 * 查询借款数据（同步表）
	 * @param syncLoan
	 * @return
	 */
	@Override
	public Pager findList(SyncLoanVO syncLoanVO) {
		Pager pager = syncLoanVO.getPager();
		/** 查询总行数 **/
		int totalCount = (Integer)getSqlSession().selectOne(getIbatisMapperNameSpace() + ".count", syncLoanVO);
		pager.setTotalCount(totalCount);;
		pager.calStart();
		List<SyncLoan> resultList = findListByVo(syncLoanVO);
		pager.setResultList(resultList);
		return pager;
	}
	
	/**
	 * 更新同步状态
	 */
	@Override
	public void updateSyncStatus(SyncLoan syncLoan) {
		getSqlSession().update(getIbatisMapperNameSpace() + ".update", syncLoan);
	}

	/**
	 * 获取所有外债贷款
	 */
	@Override
	public List<SyncLoan> findListBySyncLoanVO(SyncLoanVO vo) {
		
		return getSqlSession().selectList(getIbatisMapperNameSpace() + ".getListBySyncLoanVO",vo);
	}

	/**
	 * 获取所有外债条数
	 */
	@Override
	public int syncLoanCount(SyncLoanVO vo) {
		
		return  (Integer)getSqlSession().selectOne(getIbatisMapperNameSpace() + ".count", vo);
	}

	@Override
	public SyncLoan findSyncLoanByLoanId(Long id) {
		// TODO Auto-generated method stub
		return   getSqlSession().selectOne(getIbatisMapperNameSpace() + ".findSyncLoanByLoanId", id);
	}
	
	
	
}
