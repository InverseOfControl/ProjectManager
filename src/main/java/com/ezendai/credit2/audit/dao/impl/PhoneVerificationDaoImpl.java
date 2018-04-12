package com.ezendai.credit2.audit.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.audit.dao.PhoneVerificationDao;
import com.ezendai.credit2.audit.model.PhoneVerification;
import com.ezendai.credit2.audit.vo.PhoneVerificationVO;
import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;
 
/***
 * <pre>
 * 电核
 * 
 * </pre>
 *
 * @author  
 * @version  
 */
@Repository
public class PhoneVerificationDaoImpl extends BaseDaoImpl<PhoneVerification> implements PhoneVerificationDao {

	 
	@Override
	public PhoneVerification getPersonInfo(long id) {
		// TODO Auto-generated method stub
		return  getSqlSession().selectOne(getIbatisMapperNameSpace() + ".getPersonInfo", id);
	}

	@Override
	public PhoneVerification getParentInfo(long id) {
		// TODO Auto-generated method stub
		return  getSqlSession().selectOne(getIbatisMapperNameSpace() + ".getParentInfo", id);
	}

	@Override
	public  List<PhoneVerification> getColleagueInfo(long id) {
		// TODO Auto-generated method stub
		return  getSqlSession().selectList(getIbatisMapperNameSpace() + ".getColleagueInfo", id);
	}

	@Override
	public List<PhoneVerification> getTelInquiryByType(PhoneVerificationVO pvo) {
		// TODO Auto-generated method stub
		return 	 getSqlSession().selectList(getIbatisMapperNameSpace() + ".getTelInquiryByType", pvo);
	}

	@Override
	public long insertTelInquiry(PhoneVerification pv) {
		getSqlSession().insert(getIbatisMapperNameSpace() + ".insertTelInquiry", pv);
		return pv.getId();
	}

	@Override
	public void updateTelInquiry(PhoneVerification pv) {
		// TODO Auto-generated method stub
		getSqlSession().update(getIbatisMapperNameSpace() + ".updateTelInquiry", pv);
	}

	@Override
	public void deleteTelInquiry(PhoneVerification pv) {
		// TODO Auto-generated method stub
		getSqlSession().delete(getIbatisMapperNameSpace() + ".deleteTelInquiry", pv);
	}

	@Override
	public void updateHomeAddres(PhoneVerification pv) {
		// TODO Auto-generated method stub
		getSqlSession().delete(getIbatisMapperNameSpace() + ".updateHomeAddres", pv);
	}

	@Override
	public void updateCompanyInfo(PhoneVerification pv) {
		// TODO Auto-generated method stub
		getSqlSession().delete(getIbatisMapperNameSpace() + ".updateCompanyInfo", pv);
	}
 
	 
}