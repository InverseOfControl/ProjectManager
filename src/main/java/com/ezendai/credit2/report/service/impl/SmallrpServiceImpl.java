package com.ezendai.credit2.report.service.impl;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.report.dao.SmallrpDao;
import com.ezendai.credit2.report.model.Smallrp;
import com.ezendai.credit2.report.service.SmallrpService;

@Service
public class SmallrpServiceImpl implements SmallrpService {
	
	@Autowired
	private SmallrpDao smallrpDao;


	public Smallrp getSmallrpByContractNo(Map<String,Object> map) {
		
		return smallrpDao.getSmallrpByContractNo(map);
	}

    @Override
    public Smallrp queryCarCreditByContractNo(Map<String,Object> map) {
        return smallrpDao.queryCarCreditByContractNo(map);
    }
    @Override
    public Smallrp queryCarCreditByExtensionId(Map<String,Object> map) {
    	return smallrpDao.queryCarCreditByExtensionId(map);
    }
}
