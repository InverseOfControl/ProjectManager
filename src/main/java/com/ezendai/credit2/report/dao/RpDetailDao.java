package com.ezendai.credit2.report.dao;

import java.util.List;

import com.ezendai.credit2.framework.dao.BaseDao;
import com.ezendai.credit2.report.model.RpDetail;

public interface RpDetailDao extends BaseDao<RpDetail> {
	public List<RpDetail> getListByLoanId(long loan_id);
}
