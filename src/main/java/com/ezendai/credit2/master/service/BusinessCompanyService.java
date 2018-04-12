package com.ezendai.credit2.master.service;

import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.model.BusinessCompany;
import com.ezendai.credit2.master.vo.BusinessCompanyVO;

public interface BusinessCompanyService {

	Pager findWithPG(BusinessCompanyVO businessCompanyVO);

	void insert(BusinessCompany vo);

	BusinessCompany get(Long id);

	BusinessCompany get(BusinessCompanyVO bcManager);

	int update(BusinessCompanyVO vo);

	void deleteById(Long id);

}
