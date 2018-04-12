package com.ezendai.credit2.audit.dao;

import com.ezendai.credit2.audit.model.ContractSequence;
import com.ezendai.credit2.framework.dao.BaseDao;

public interface ContractSequenceDao extends BaseDao<ContractSequence> {
	
	Long getContractSequenceForCar();
	
	Long getContractSequenceForPeanuts();

}
