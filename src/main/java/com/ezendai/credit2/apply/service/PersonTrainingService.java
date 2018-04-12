package com.ezendai.credit2.apply.service;

import java.util.List;

import com.ezendai.credit2.apply.model.PersonTraining;
import com.ezendai.credit2.apply.vo.PersonTrainingVO;

public interface PersonTrainingService {
	
	PersonTraining insert(PersonTraining personTraining);
	
	List<PersonTraining> findListByVo(PersonTrainingVO personTrainingVO);
	
	

	int update(PersonTrainingVO personTrainingVO);

}
