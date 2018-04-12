package com.ezendai.credit2.apply.dao;


import com.ezendai.credit2.apply.model.Person;
import com.ezendai.credit2.apply.vo.PersonVO;
import com.ezendai.credit2.framework.dao.BaseDao;

public interface PersonDao extends BaseDao<Person>{

	int insertSelective(Person record);

	Person selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(Person record);

	Person getPersonByIdNum(PersonVO personVo);

}