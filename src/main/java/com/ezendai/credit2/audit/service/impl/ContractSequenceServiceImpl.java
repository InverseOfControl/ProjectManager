package com.ezendai.credit2.audit.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.audit.dao.ContractSequenceDao;
import com.ezendai.credit2.audit.service.ContractSequenceService;

/**
 * 合同流水号seq
 * @author liyuepeng
 *
 */
@Service
public class ContractSequenceServiceImpl implements ContractSequenceService {
	
	@Autowired
	private ContractSequenceDao contractSequenceDao;

	@Override
	public Long getContractSequenceForCar() {
		return contractSequenceDao.getContractSequenceForCar();
	}

	@Override
	public Long getContractSequenceForPeanuts() {
		return contractSequenceDao.getContractSequenceForPeanuts();
	}

}
