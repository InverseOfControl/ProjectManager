package com.ezendai.credit2.report.service;

import java.util.List;

import com.ezendai.credit2.report.model.RpDetail;

public interface RpDetailService {
	/**
	 * 
	 * <pre>
	 * 根据loan_id查询还款计划
	 * </pre>
	 *
	 * @param loan_id
	 */
	public List<RpDetail> getListByLoanId(long loan_id);
}
