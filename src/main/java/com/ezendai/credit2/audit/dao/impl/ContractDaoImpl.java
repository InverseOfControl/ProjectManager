package com.ezendai.credit2.audit.dao.impl;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.audit.dao.ContractDao;
import com.ezendai.credit2.audit.model.Contract;
import com.ezendai.credit2.audit.vo.ContractVO;
import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;

@Repository
public class ContractDaoImpl extends BaseDaoImpl<Contract> implements ContractDao {

	@Override
	public void deleteContractByLoanId(Long loanId) {
		this.getSqlSession().update(getIbatisMapperNameSpace() + ".deleteContractByLoanId", loanId);
	}
	/**
	 * 
	 * <pre>
	 * 根据合同编号和loanID查询合同
	 * </pre>
	 *
	 * @param contractVo
	 * @return
	 */
	@Override
	public Contract getContractByParams(ContractVO contractVo){
		return this.getSqlSession().selectOne(getIbatisMapperNameSpace()+".getContractByParams", contractVo);
	}

}
