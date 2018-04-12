package com.ezendai.credit2.audit.dao.impl;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.audit.dao.ContractSequenceDao;
import com.ezendai.credit2.audit.model.ContractSequence;
import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;

@Repository
public class ContractSequenceDaoImpl extends BaseDaoImpl<ContractSequence> implements ContractSequenceDao {

	@Override
	public Long getContractSequenceForCar() {
		return getSqlSession().selectOne(getIbatisMapperNameSpace() + ".getContractSequenceForCar");
	}

	@Override
	public Long getContractSequenceForPeanuts() {
		return getSqlSession().selectOne(getIbatisMapperNameSpace() + ".getContractSequenceForPeanuts");
	}


}
