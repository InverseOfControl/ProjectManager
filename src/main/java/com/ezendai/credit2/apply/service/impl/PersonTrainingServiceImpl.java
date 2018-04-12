package com.ezendai.credit2.apply.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.apply.dao.PersonTrainingDao;
import com.ezendai.credit2.apply.model.PersonTraining;
import com.ezendai.credit2.apply.service.PersonTrainingService;
import com.ezendai.credit2.apply.vo.PersonTrainingVO;


@Service
public class PersonTrainingServiceImpl implements PersonTrainingService {

	@Autowired
	private PersonTrainingDao personTrainingDao;


	@Override
	public PersonTraining insert(PersonTraining personTraining) {
		return  personTrainingDao.insert(personTraining);
	}


	@Override
	public List<PersonTraining> findListByVo(PersonTrainingVO personTrainingVO) {
		
		return personTrainingDao.findListByVo(personTrainingVO);
	}


	@Override
	public int update(PersonTrainingVO personTrainingVO) {
		
		return personTrainingDao.update(personTrainingVO);
	}
	
	
	
	
	
	

}
