package com.ezendai.credit2.system.service;

import java.util.List;

import com.ezendai.credit2.apply.model.BankAccount;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.system.model.OrganBank;
import com.ezendai.credit2.system.vo.OrganBankVO;

/**
 * @author LinSanfu
 */

public interface OrganBankService {
	int update(OrganBankVO organBankVO);
	
	OrganBank insert(OrganBank organBank);
	
	List<OrganBank> findListByVo(OrganBankVO organBankVO);
	
	void deleteListByVo(OrganBankVO organBankVO);
	
	Pager findWithPg(OrganBankVO organBankVO);
	
	/**通过vo查询单日符合条件记录的总和**/
	Integer count(OrganBankVO organBankVO);
	
	OrganBank get(Long id);
	
	/**通过OrganBankVO获取相应的bankaccountlist**/
	List<BankAccount> findBankAccountListByVo(OrganBankVO organBankVO);
}
