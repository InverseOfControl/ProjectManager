package com.ezendai.credit2.audit.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.audit.dao.InternalMatchingDao;
import com.ezendai.credit2.audit.model.LoanHistory;
import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;
import com.ezendai.credit2.framework.util.BeanUtil;
import com.ezendai.credit2.framework.util.Pager;
 
@Repository
public class InternalMatchingDaoImpl extends BaseDaoImpl<LoanHistory> implements InternalMatchingDao {

	@SuppressWarnings("rawtypes")
	@Override
	public Pager findLoanHistoryWithPG(LoanHistory lh) {
		Object count = getSqlSession().selectOne("com.ezendai.credit2.audit.mapper.InternalMatchingMapper" + ".findLoanHistoryCount", lh);
		int totalCount = Integer.parseInt(count.toString());
		Pager pg = lh.getPager();
		pg.setTotalCount(totalCount);
		pg.calStart();
		List rstList = null;
		try {
			rstList = getSqlSession().selectList("com.ezendai.credit2.audit.mapper.InternalMatchingMapper" + ".findLoanHistoryWithPG", lh);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		BeanUtil.copyProperties(pg, lh);
		pg.setTotalCount(totalCount);
		pg.setResultList(rstList);
		return pg;
	}

	@Override
	public List<LoanHistory> selectOptionContacterPhone(LoanHistory lh) {
		// TODO Auto-generated method stub
		return 	 getSqlSession().selectList("com.ezendai.credit2.audit.mapper.InternalMatchingMapper" + ".selectOptionContacterPhone", lh);
	}

	@Override
	public List<LoanHistory> selectOptionPersonPhone(LoanHistory lh) {
		// TODO Auto-generated method stub
		return 	 getSqlSession().selectList("com.ezendai.credit2.audit.mapper.InternalMatchingMapper" + ".selectOptionPersonPhone", lh);
	}

	@Override
	public List<LoanHistory> selectOptionHomePhone(LoanHistory lh) {
		// TODO Auto-generated method stub
		return 	 getSqlSession().selectList("com.ezendai.credit2.audit.mapper.InternalMatchingMapper" + ".selectOptionHomePhone", lh);
	}

	@Override
	public List<LoanHistory> selectOptionCompanyPhone(LoanHistory lh) {
		// TODO Auto-generated method stub
		return 	 getSqlSession().selectList("com.ezendai.credit2.audit.mapper.InternalMatchingMapper" + ".selectOptionCompanyPhone", lh);
	}
	 
}