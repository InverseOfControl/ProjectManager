package com.ezendai.credit2.master.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.dao.SyncPreRepaymentInfoDao;
import com.ezendai.credit2.master.model.SyncPreRepaymentInfo;
import com.ezendai.credit2.master.vo.SyncPreRepaymentInfoVO;

@Repository
public class SyncPreRepaymentInfoDaoImpl extends BaseDaoImpl<SyncPreRepaymentInfo> implements SyncPreRepaymentInfoDao {
	
	/**
	 * 批量插入异常还款人员信息
	 * @param syncLoan
	 */
	@Override
	public void batchInsert(List<SyncPreRepaymentInfo> syncPreRepaymentInfos) {
		for (SyncPreRepaymentInfo syncPreRepaymentInfo : syncPreRepaymentInfos) {
			/**判断此异常还款信息是否已经生成了**/
			int count = (Integer)getSqlSession().selectOne(getIbatisMapperNameSpace() + ".isExistsByLoanId",syncPreRepaymentInfo);
			if (count == 0) {
				insert(syncPreRepaymentInfo);
			}
		}
	}
	
	/**
	 * 查询异常借款信息
	 * @param syncPreRepaymentInfo
	 * @return
	 */
	@Override
	public List<SyncPreRepaymentInfo>queryPreRepayInfo(SyncPreRepaymentInfo syncPreRepaymentInfo) {
		List<SyncPreRepaymentInfo> result = new ArrayList<SyncPreRepaymentInfo>();
		result = getSqlSession().selectList(getIbatisMapperNameSpace() + ".queryPreRepayInfo", syncPreRepaymentInfo);
		return result;
	}
	
	/**
	 * 查询提前一次性还款数据（同步表）
	 * @param syncPreRepaymentInfoVO
	 * @return
	 */
	@Override
	public Pager findList(SyncPreRepaymentInfoVO syncPreRepaymentInfoVO) {
		Pager pager = syncPreRepaymentInfoVO.getPager();
		/** 查询总行数 **/
		int totalCount = (Integer)getSqlSession().selectOne(getIbatisMapperNameSpace() + ".count", syncPreRepaymentInfoVO);
		pager.setTotalCount(totalCount);;
		pager.calStart();
		List<SyncPreRepaymentInfo> resultList = findListByVo(syncPreRepaymentInfoVO);
		pager.setResultList(resultList);
		return pager;
	}
	
	/**
	 * 更新同步状态
	 */
	@Override
	public void updateSyncStatus(SyncPreRepaymentInfo syncPreRepaymentInfo) {
		getSqlSession().update(getIbatisMapperNameSpace() + ".update", syncPreRepaymentInfo);
	}
	
}
