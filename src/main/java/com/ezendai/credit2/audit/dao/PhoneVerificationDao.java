package com.ezendai.credit2.audit.dao;

import java.util.List;

import com.ezendai.credit2.audit.model.PhoneVerification;
import com.ezendai.credit2.audit.vo.PhoneVerificationVO;
import com.ezendai.credit2.framework.dao.BaseDao;

public interface PhoneVerificationDao extends BaseDao<PhoneVerification> {
	
	 
	PhoneVerification getPersonInfo(long id);
	
	PhoneVerification getParentInfo(long id);
	
	List<PhoneVerification> getColleagueInfo(long id);
	
	List<PhoneVerification>	getTelInquiryByType(PhoneVerificationVO pvo);
	
	long insertTelInquiry (PhoneVerification pv);
	
	void updateTelInquiry (PhoneVerification pv);
	
	void deleteTelInquiry (PhoneVerification pv);
	
	void updateHomeAddres (PhoneVerification pv);
	
	void updateCompanyInfo (PhoneVerification pv);
}
