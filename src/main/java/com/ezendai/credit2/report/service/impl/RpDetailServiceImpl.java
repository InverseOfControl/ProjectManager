package com.ezendai.credit2.report.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.report.dao.RpDetailDao;
import com.ezendai.credit2.report.model.RpDetail;
import com.ezendai.credit2.report.service.RpDetailService;


@Service
public class RpDetailServiceImpl  implements RpDetailService {

	@Autowired
	private RpDetailDao rpDetailDao;
	
	public List<RpDetail> getListByLoanId(long loan_id) {
		return rpDetailDao.getListByLoanId(loan_id);
	}

}
