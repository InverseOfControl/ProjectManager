package com.ezendai.credit2.audit.dao;

import com.ezendai.credit2.audit.model.Contract;
import com.ezendai.credit2.audit.vo.ContractVO;
import com.ezendai.credit2.framework.dao.BaseDao;

public interface ContractDao extends BaseDao<Contract> {
	
	void deleteContractByLoanId(Long loanId);
	/**
	 * 
	 * <pre>
	 * 根据合同编号和loanID查询合同
	 * </pre>
	 *
	 * @param contractVo
	 * @return
	 */
	Contract getContractByParams(ContractVO contractVo);

}
