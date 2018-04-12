package com.ezendai.credit2.report.dao.impl;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;
import com.ezendai.credit2.framework.util.BeanUtil;
import com.ezendai.credit2.framework.util.CollectionUtil;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.report.dao.RepaymentReportDao;
import com.ezendai.credit2.report.model.RepaymentReport;
import com.ezendai.credit2.report.vo.RepaymentReportVO;

@Repository
public class RepaymentReportDaoImpl extends BaseDaoImpl<RepaymentReport> implements RepaymentReportDao {

	/** 
	 * @param vo
	 * @return
	 * @see com.ezendai.credit2.report.dao.RepaymentReportDao#queryRepaymentReport(com.ezendai.credit2.report.vo.RepaymentReportVO)
	 */
	@Override
	public List<RepaymentReport> queryRepaymentReport(RepaymentReportVO vo) {
		return this.getSqlSession().selectList(getIbatisMapperNameSpace()+".queryRepaymentReport",vo);
	}
	
	/** 
	 * @param vo
	 * @return
	 * @see com.ezendai.credit2.report.dao.RepaymentReportDao#queryRepaymentReport(com.ezendai.credit2.report.vo.RepaymentReportVO)
	 */
	@Override
	public List<RepaymentReport> queryNoRepaymentReport(RepaymentReportVO vo) {
		return this.getSqlSession().selectList(getIbatisMapperNameSpace()+".queryNoRepaymentReport",vo);
	}

	/** 
	 * @param vo
	 * @return
	 * @see com.ezendai.credit2.report.dao.RepaymentReportDao#queryRepaymentReportCount(com.ezendai.credit2.report.vo.RepaymentReportVO)
	 */
	@Override
	public int queryRepaymentReportCount(RepaymentReportVO vo) {
		Object object = getSqlSession().selectOne(getIbatisMapperNameSpace() + ".queryRepaymentReportCount", vo);
		return Integer.parseInt(object.toString());
	}

	/** 
	 * @param vo
	 * @return
	 * @see com.ezendai.credit2.report.dao.RepaymentReportDao#queryNoRepaymentReportCount(com.ezendai.credit2.report.vo.RepaymentReportVO)
	 */
	@Override
	public int queryNoRepaymentReportCount(RepaymentReportVO vo) {
		Object object = getSqlSession().selectOne(getIbatisMapperNameSpace() + ".queryNoRepaymentReportCount", vo);
		return Integer.parseInt(object.toString());
	}

	/** 
	 * @param vo
	 * @return
	 * @see com.ezendai.credit2.report.dao.RepaymentReportDao#findWithPgOnRepayment(com.ezendai.credit2.report.vo.RepaymentReportVO)
	 */
	@Override
	public Pager findWithPgOnRepayment(RepaymentReportVO vo) {
		Object count = getSqlSession().selectOne(getIbatisMapperNameSpace() + ".queryRepaymentReportCount", vo);
		int totalCount = Integer.parseInt(count.toString());
		Pager pg = vo.getPager();
		pg.setTotalCount(totalCount);
		pg.calStart();
		List rstList = null;
		try {
			rstList = getSqlSession().selectList(getIbatisMapperNameSpace() + ".findWithPgOnRepayment", vo);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		BeanUtil.copyProperties(pg, vo);
		pg.setTotalCount(totalCount);
		pg.setResultList(CollectionUtil.isNullOrEmpty(rstList)? new ArrayList() : rstList);
		return pg;
	}

	/** 
	 * @param vo
	 * @return
	 * @see com.ezendai.credit2.report.dao.RepaymentReportDao#findWithPgOnNoRepayment(com.ezendai.credit2.report.vo.RepaymentReportVO)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Pager findWithPgOnNoRepayment(RepaymentReportVO vo) {
		Object count = getSqlSession().selectOne(getIbatisMapperNameSpace() + ".queryNoRepaymentReportCount", vo);
		int totalCount = Integer.parseInt(count.toString());
		Pager pg = vo.getPager();
		pg.setTotalCount(totalCount);
		pg.calStart();
		List rstList = null;
		try {
			rstList = getSqlSession().selectList(getIbatisMapperNameSpace() + ".findWithPgOnNoRepayment", vo);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		BeanUtil.copyProperties(pg, vo);
		pg.setTotalCount(totalCount);
		pg.setResultList(CollectionUtil.isNullOrEmpty(rstList)? new ArrayList() : rstList);
		return pg;
	}
}
