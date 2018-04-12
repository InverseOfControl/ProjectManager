package com.ezendai.credit2.apply.dao;

import java.util.List;

import com.ezendai.credit2.apply.model.EduLoanImage;
import com.ezendai.credit2.framework.dao.BaseDao;

public interface EduLoanImageDao extends BaseDao<EduLoanImage> {
	List<EduLoanImage> findByContractNo(String contractNo);

}
