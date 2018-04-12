package com.ezendai.credit2.apply.assembler;

import java.text.ParseException;

import com.ezendai.credit2.apply.model.PersonTraining;
import com.ezendai.credit2.apply.vo.PersonTrainingVO;

public class PersonTrainingAssembler {
	
	
	
	public static PersonTraining  transferVO2Model(PersonTrainingVO personTrainingVO){
		PersonTraining   personTraining  = new PersonTraining();
		personTraining.setCourse(personTrainingVO.getCourse());
		personTraining.setCreatedTime(personTrainingVO.getCreatedTime());
		personTraining.setCreator(personTrainingVO.getCourse());
		personTraining.setCreatorId(personTrainingVO.getCreatorId());
		if(personTrainingVO.getEntranceDate() != null && personTrainingVO.getEntranceDate() !=""){
			try {
				personTraining.setEntranceDate(personTrainingVO.getEntranceDate());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
		}
		personTraining.setEntranceTime(personTrainingVO.getEntranceTime());
		personTraining.setId(personTrainingVO.getId());
		personTraining.setLoanId(personTrainingVO.getLoanId());;
		personTraining.setModifiedTime(personTrainingVO.getModifiedTime());
		personTraining.setModifier(personTrainingVO.getModifier());
		personTraining.setModifierId(personTrainingVO.getModifierId());
		personTraining.setPeriod(personTrainingVO.getPeriod());
		personTraining.setPersonIdnum(personTrainingVO.getPersonIdnum());
		personTraining.setSchoolDistrict(personTrainingVO.getSchoolDistrict());
		personTraining.setVersion(personTrainingVO.getVersion());
		
		
		return personTraining;
		
		
	}


}
