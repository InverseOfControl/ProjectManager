package com.ezendai.credit2.master.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.dao.BusinessCompanyDao;
import com.ezendai.credit2.master.model.BusinessCompany;
import com.ezendai.credit2.master.service.BusinessCompanyService;
import com.ezendai.credit2.master.vo.BusinessCompanyVO;

@Service
public class BusinessCompanyServiceImpl implements BusinessCompanyService {

	@Autowired
	BusinessCompanyDao businessCompanyDao;
	
	@Override
	public Pager findWithPG(BusinessCompanyVO businessCompanyVO) {
		// TODO Auto-generated method stub
		return businessCompanyDao.findWithPg(businessCompanyVO);
	}

	@Override
	public void insert(BusinessCompany vo) {
		// TODO Auto-generated method stub
		businessCompanyDao.insert(vo);
	}

	@Override
	public BusinessCompany get(Long id) {
		// TODO Auto-generated method stub
		return businessCompanyDao.get(id);
	}

	@Override
	public BusinessCompany get(BusinessCompanyVO vo) {
		// TODO Auto-generated method stub
		return businessCompanyDao.get(vo);
	}

	@Override
	public int update(BusinessCompanyVO vo) {
		// TODO Auto-generated method stub
		return businessCompanyDao.update(vo);
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		businessCompanyDao.deleteById(id);;
	}

}
