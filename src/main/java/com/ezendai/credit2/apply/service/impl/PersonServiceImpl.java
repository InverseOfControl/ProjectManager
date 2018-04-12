package com.ezendai.credit2.apply.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.apply.dao.PersonDao;
import com.ezendai.credit2.apply.model.Person;
import com.ezendai.credit2.apply.service.PersonService;
import com.ezendai.credit2.apply.vo.PersonVO;
import com.ezendai.credit2.framework.util.Pager;

@Service
public class PersonServiceImpl implements PersonService {

	@Autowired
	private PersonDao personDao;

	/***
	 * 根据身份证号码获取客户信息 
	 * @param idum
	 * @return
	 * @see com.ezendai.credit2.apply.service.PersonService#getPersonByIdum(java.lang.String)
	 */
	@Override
	public Person getPersonByIdNum(String idNum, Integer productType) {
		PersonVO personVo = new PersonVO();
		personVo.setIdnum(idNum);
		personVo.setProductType(productType);
		return personDao.getPersonByIdNum(personVo);
	}

	@Override
	public Person insert(Person person) {
		return personDao.insert(person);
	}

	@Override
	public void deleteById(Long id) {
		personDao.deleteById(id);
	}

	@Override
	public void deleteByIdList(PersonVO personVO) {
		personDao.deleteByIdList(personVO);
	}

	@Override
	public int update(PersonVO personVO) {
		if (personVO.getHasChildren() != null && personVO.getHasChildren().compareTo(0L) == 0) {
			personVO.setChildrenNum(0);
		}
		
		if (personVO.getMarried()!= null && personVO.getMarried().compareTo(1L) != 0) {
			personVO.setChildrenNum(0);
		}
		if (personVO.getHouseEstateType()!= null ) {
			if("租赁".equals( personVO.getHouseEstateType()) != true && "租用".equals( personVO.getHouseEstateType()) != true){
				
				personVO.setRentPerMonth(new BigDecimal(0));
			}
		}
		return personDao.update(personVO);
	}

	@Override
	public Person get(Long id) {
		return personDao.get(id);
	}

	@Override
	public List<Person> findListByVo(PersonVO personVO) {
		return personDao.findListByVo(personVO);
	}

	@Override
	public boolean exists(Map<String, Object> map) {
		return personDao.exists(map);
	}

	@Override
	public Pager findWithPg(PersonVO personVO) {
		return personDao.findWithPg(personVO);
	}

	@Override
	public Person get(PersonVO personVO) {
		return personDao.get(personVO);
	}

	@Override
	public boolean exists(Long id) {
		return personDao.exists(id);
	}

}
