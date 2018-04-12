package com.ezendai.credit2.report.service;
import java.io.OutputStream;
import java.util.List;

import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.report.model.RepaymentReport;
import com.ezendai.credit2.report.vo.RepaymentReportVO;

public interface RepaymentReportService {
	
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
	 
	 /**
	  * 
	  * <pre>
	  * 还款流水记录查询
	  * </pre>
	  *
	  * @param vo
	  * @return
	  */
	 List<RepaymentReport> queryRepaymentReport(RepaymentReportVO vo);
	 /**
	  * 
	  * <pre>
	  * 还款流水记录查询-总数查询
	  * </pre>
	  *
	  * @param vo
	  * @return
	  */
	 int queryRepaymentReportCount(RepaymentReportVO vo);
	 
	 /**
	  * 
	  * <pre>
	  * 无还款流水记录查询-总数查询
	  * </pre>
	  *
	  * @param vo
	  * @return
	  */
	 int queryNoRepaymentReportCount(RepaymentReportVO vo);
	 /**
	  * 
	  * <pre>
	  * 无还款流水记录查询
	  * </pre>
	  *
	  * @param vo
	  * @return
	  */
	 List<RepaymentReport> queryNoRepaymentReport(RepaymentReportVO vo);
	 
	/**
	 * <pre>
	 * 查询有还款记录
	 * </pre>
	 *
	 * @param offerList
	 * @param sheetName
	 * @param os
	 */
	void exportRepaymentReportExcel(RepaymentReportVO vo,List<RepaymentReport> repaymentReportList, String sheetName, OutputStream os);
	/**
	 * <pre>
	 * 查询无还款记录
	 * </pre>
	 *
	 * @param offerList
	 * @param sheetName
	 * @param os
	 */
	void exportNoRepaymentReportExcel(RepaymentReportVO vo,List<RepaymentReport> repaymentReportList, String sheetName, OutputStream os);
}
