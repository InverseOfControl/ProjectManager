package com.ezendai.credit2.apply.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.apply.dao.EduLoanImageDao;
import com.ezendai.credit2.apply.model.EduLoanImage;
import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;
@Repository
public class EduLoanImageDaoImpl extends BaseDaoImpl<EduLoanImage> implements EduLoanImageDao {

	@Override
	public List<EduLoanImage> findByContractNo(String contractNo) {
		 return getSqlSession().selectList(getIbatisMapperNameSpace() + ".findByContractNo", contractNo);
	}


	
	
	


}
