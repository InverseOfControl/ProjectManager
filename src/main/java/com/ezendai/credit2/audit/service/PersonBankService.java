package com.ezendai.credit2.audit.service;

import java.util.List;

import com.ezendai.credit2.audit.model.PersonBank;
import com.ezendai.credit2.audit.vo.PersonBankVO;
import com.ezendai.credit2.framework.util.Pager;

public interface PersonBankService {

	void insertPersonBank(PersonBank personBank);
	
	PersonBank getPersonBank(PersonBankVO personBankVO);
	
	PersonBank getExtension(Long id);
	
	List<PersonBank> findPersonBankList(PersonBankVO personBankVO);
	
	void deletePersonBank(Long id);
	
	/** 根据查询条件vo物理分页查询出数据行：{mapper.xml需要实现} */
	Pager findWithPgExtension(PersonBankVO vo);
	
	int update(PersonBankVO personBankVO);
	
	List<PersonBank> findListByVOExtension(PersonBankVO personBankVO);
	
	Pager findWithPG(PersonBankVO vo);
}
