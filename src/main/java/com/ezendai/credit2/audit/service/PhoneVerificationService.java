/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.audit.service;

 

import java.util.List;

import com.ezendai.credit2.audit.model.PhoneVerification;
import com.ezendai.credit2.audit.vo.PhoneVerificationVO;
 


/**
 * <pre>
 * 
 * </pre>
 *
 * @author  
 * @version  
 */
public interface PhoneVerificationService {
	
	PhoneVerification getPersonInfo(long id);
	
	PhoneVerification getParentInfo(long id);
	
	  List<PhoneVerification> getColleagueInfo(long id);
	
    List<PhoneVerification> getTelInquiryByType(PhoneVerificationVO pvo);
    
	long insertTelInquiry (PhoneVerification pv);
	
	void updateTelInquiry (PhoneVerification pv);
	
	void deleteTelInquiry (PhoneVerification pv);
	
	void updateHomeAddres (PhoneVerification pv);
	
	void updateCompanyInfo (PhoneVerification pv);
   }
	 
