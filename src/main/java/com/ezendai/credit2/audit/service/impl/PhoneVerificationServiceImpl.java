/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.audit.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.audit.dao.PhoneVerificationDao;
import com.ezendai.credit2.audit.model.PhoneVerification;
import com.ezendai.credit2.audit.service.PhoneVerificationService;
import com.ezendai.credit2.audit.vo.PhoneVerificationVO;
 
 

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author  
 * @version   
 */
@Service
public class PhoneVerificationServiceImpl implements PhoneVerificationService {

	@Autowired
	private PhoneVerificationDao phoneDao;

	@Override
	public PhoneVerification getPersonInfo(long id) {
		// TODO Auto-generated method stub
		return phoneDao.getPersonInfo(id);
	}

	@Override
	public PhoneVerification getParentInfo(long id) {
		// TODO Auto-generated method stub
		return phoneDao.getParentInfo(id);
	}

	@Override
	public   List<PhoneVerification> getColleagueInfo(long id) {
		// TODO Auto-generated method stub
		return phoneDao.getColleagueInfo(id);
	}

	@Override
	public List<PhoneVerification> getTelInquiryByType(PhoneVerificationVO pvo) {
		// TODO Auto-generated method stub
		return phoneDao.getTelInquiryByType(pvo);
	}

	@Override
	public long  insertTelInquiry(PhoneVerification pv) {
		// TODO Auto-generated method stub
		return	phoneDao.insertTelInquiry(pv);
	}

	@Override
	public void updateTelInquiry(PhoneVerification pv) {
		// TODO Auto-generated method stub
		phoneDao.updateTelInquiry(pv);
	}

	@Override
	public void deleteTelInquiry(PhoneVerification pv) {
		// TODO Auto-generated method stub
		phoneDao.deleteTelInquiry(pv);
	}

	@Override
	public void updateHomeAddres(PhoneVerification pv) {
		// TODO Auto-generated method stub
		phoneDao.updateHomeAddres(pv);
	}

	@Override
	public void updateCompanyInfo(PhoneVerification pv) {
		// TODO Auto-generated method stub
		phoneDao.updateCompanyInfo(pv);
	}

	 
}
