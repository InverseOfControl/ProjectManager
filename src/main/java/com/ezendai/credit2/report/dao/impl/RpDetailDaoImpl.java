package com.ezendai.credit2.report.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;
import com.ezendai.credit2.report.dao.RpDetailDao;
import com.ezendai.credit2.report.model.RpDetail;
@Repository
public class RpDetailDaoImpl extends BaseDaoImpl<RpDetail> implements
		RpDetailDao {

	public List<RpDetail> getListByLoanId(long loan_id) {
		 return this.getSqlSession().selectList(getIbatisMapperNameSpace()+".getListByLoanId", loan_id);
	}

}
