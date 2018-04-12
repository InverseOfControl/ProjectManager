package com.ezendai.credit2.apply.service;

import java.util.List;
import java.util.Map;

import com.ezendai.credit2.apply.model.Person;
import com.ezendai.credit2.apply.vo.PersonVO;
import com.ezendai.credit2.framework.util.Pager;

public interface PersonService {

	Person insert(Person person);

	void deleteById(Long id);

	void deleteByIdList(PersonVO personVO);

	Person get(Long id);

	List<Person> findListByVo(PersonVO personVO);

	boolean exists(Map<String, Object> map);

	Pager findWithPg(PersonVO personVO);

	Person get(PersonVO personVO);

	boolean exists(Long id);

	Person getPersonByIdNum(String idNum, Integer poductType);
	
	int update(PersonVO personVO);

}
