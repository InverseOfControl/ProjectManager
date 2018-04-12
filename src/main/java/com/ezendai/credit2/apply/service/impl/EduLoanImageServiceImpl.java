package com.ezendai.credit2.apply.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.apply.dao.EduLoanImageDao;
import com.ezendai.credit2.apply.model.EduLoanImage;
import com.ezendai.credit2.apply.service.EduLoanImageService;
@Service
public class EduLoanImageServiceImpl implements EduLoanImageService {
	
	@Autowired
	EduLoanImageDao eduLoanImageDao;
	@Override
	public List<EduLoanImage> findByContractNo(String contractNo) {
		return eduLoanImageDao.findByContractNo(contractNo);
	}


}
