package com.ezendai.credit2.audit.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezendai.credit2.audit.dao.PersonBankDao;
import com.ezendai.credit2.audit.model.PersonBank;
import com.ezendai.credit2.audit.service.PersonBankService;
import com.ezendai.credit2.audit.vo.PersonBankVO;
import com.ezendai.credit2.framework.util.Pager;

@Service
public class PersonBankServiceImpl implements PersonBankService {

	@Autowired
	private PersonBankDao personBankDao;
	
	@Transactional
	@Override
	public void insertPersonBank(PersonBank personBank) {
		personBankDao.insert(personBank);	
	}

	@Override
	public PersonBank getPersonBank(PersonBankVO personBankVO) {
		return personBankDao.get(personBankVO);
	}

	@Override
	public List<PersonBank> findPersonBankList(PersonBankVO personBankVO) {
		return personBankDao.findListByVo(personBankVO);
	}

	@Transactional
	@Override
	public void deletePersonBank(Long id) {
		personBankDao.deleteById(id);
	}

	/** 
	 * @param vo
	 * @return
	 * @see com.ezendai.credit2.audit.service.PersonBankService#findWithPgExtension(com.ezendai.credit2.audit.vo.PersonBankVO)
	 */
	@Override
	public Pager findWithPgExtension(PersonBankVO vo) {
		return personBankDao.findWithPgExtension(vo);
	}

	/** 
	 * @param id
	 * @return
	 * @see com.ezendai.credit2.audit.service.PersonBankService#get(java.lang.Long)
	 */
	@Override
	public PersonBank getExtension(Long id) {
		return personBankDao.getExtension(id);
	}

	/** 
	 * @param personBankVO
	 * @return
	 * @see com.ezendai.credit2.audit.service.PersonBankService#update(com.ezendai.credit2.audit.vo.PersonBankVO)
	 */
	@Override
	public int update(PersonBankVO personBankVO) {
		return personBankDao.update(personBankVO);
	}

	@Override
	public List<PersonBank> findListByVOExtension(PersonBankVO personBankVO) {
		// TODO Auto-generated method stub
		return personBankDao.findListByVoExtension(personBankVO);
	}

	@Override
	public Pager findWithPG(PersonBankVO vo) {
		// TODO Auto-generated method stub
		return personBankDao.findWithPg(vo);
	}

}
