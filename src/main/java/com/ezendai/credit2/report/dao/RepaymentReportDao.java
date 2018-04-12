package com.ezendai.credit2.report.dao;

import java.util.List;

import com.ezendai.credit2.framework.dao.BaseDao;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.report.model.RepaymentReport;
import com.ezendai.credit2.report.vo.RepaymentReportVO;

public interface RepaymentReportDao extends BaseDao<RepaymentReport> {
	
	/**
	 * 
	 * <pre>
	 * 有还款流水记录分页查询
	 * </pre>
	 *
	 * @param vo
	 * @return
	 */
	 Pager findWithPgOnRepayment(RepaymentReportVO vo);
	 
	 /**
	  * 
	  * <pre>
	  * 有还款流水记录分页查询
	  * </pre>
	  *
	  * @param vo
	  * @return
	  */
	Pager findWithPgOnNoRepayment(RepaymentReportVO vo);
	
	List<RepaymentReport> queryRepaymentReport(RepaymentReportVO vo);

	List<RepaymentReport> queryNoRepaymentReport(RepaymentReportVO vo);
	
	int queryRepaymentReportCount(RepaymentReportVO vo);
	
	int queryNoRepaymentReportCount(RepaymentReportVO vo);
}
