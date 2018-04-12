package com.ezendai.credit2.apply.assembler;

import com.ezendai.credit2.apply.model.Person;
import com.ezendai.credit2.apply.vo.PersonVO;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author zhangshihai
 * @version $Id: PersonAssembler.java, v 0.1 2014年7月25日 下午2:36:29 zhangshihai Exp $
 */
public class PersonAssembler {

	/**
	 * 
	 * <pre>
	 * Model对象转VO对象
	 * </pre>
	 *
	 * @param person
	 * @return
	 */
	public static PersonVO transferModel2VO (Person person) {
		if(person == null){
			return null;
		}
		
		PersonVO personVO = new PersonVO();
		personVO.setId(person.getId());
		personVO.setName(person.getName());
		personVO.setSex(person.getSex());
		personVO.setPersonNo(person.getPersonNo());
		personVO.setIdnum(person.getIdnum());
		personVO.setCompanyId(person.getCompanyId());
		personVO.setMarried(person.getMarried());
		personVO.setIdentifier(person.getIdentifier());
		personVO.setEducationLevel(person.getEducationLevel());
		personVO.setHasChildren(person.getHasChildren());
		personVO.setChildrenNum(person.getChildrenNum());
		personVO.setZipCode(person.getZipCode());
		personVO.setWitness(person.getWitness());
		personVO.setOtherIncome(person.getOtherIncome());
		personVO.setPayDate(person.getPayDate());
		personVO.setWorkNature(person.getWorkNature());
		personVO.setJob(person.getJob());
		personVO.setDeptName(person.getDeptName());
		personVO.setExt(person.getExt());
		personVO.setPlaceDomicile(person.getPlaceDomicile());
		personVO.setHouseholdZipCode(person.getHouseholdZipCode());
		personVO.setAddress(person.getAddress());
		personVO.setMobilePhone(person.getMobilePhone());
		personVO.setEmail(person.getEmail());
		personVO.setHomePhone(person.getHomePhone());
		personVO.setHouseEstateType(person.getHouseEstateType());
		personVO.setRentPerMonth(person.getRentPerMonth());
		personVO.setLiveType(person.getLiveType());
		personVO.setHasHouseLoan(person.getHasHouseLoan());
		personVO.setHouseEstateAddress(person.getHouseEstateAddress());
		personVO.setIncomePerMonth(person.getIncomePerMonth());
		personVO.setWorkThatDept(person.getWorkThatDept());
		personVO.setWorkThatPosition(person.getWorkThatPosition());
		personVO.setWorkThatTell(person.getWorkThatTell());
		personVO.setPersonType(person.getPersonType());
		personVO.setCompanyType(person.getCompanyType());
		personVO.setCreatorId(person.getCreatorId());
		personVO.setCreator(person.getCreator());
		personVO.setCreatedTime(person.getCreatedTime());
		personVO.setModifierId(person.getModifierId());
		personVO.setModifier(person.getModifier());
		personVO.setModifiedTime(person.getModifiedTime());
		personVO.setVersion(person.getVersion());
		personVO.setProfessionType(person.getProfessionType());
		personVO.setMaxRepayAmount(person.getMaxRepayAmount());
		personVO.setChildrenSchool(person.getChildrenSchool());
		personVO.setPrivateEnterpriseType(person.getPrivateEnterpriseType());
		personVO.setFoundedDate(person.getFoundedDate());
		personVO.setBusinessPlace(person.getBusinessPlace());
		personVO.setTotalEmployees(person.getTotalEmployees());
		personVO.setRatioOfInvestments(person.getRatioOfInvestments());
		personVO.setMonthOfProfit(person.getMonthOfProfit());
		personVO.setProductType(person.getProductType());
		personVO.setPersonDebtBalance(person.getPersonDebtBalance());
		personVO.setMonthRepayAmount(person.getMonthRepayAmount());
		personVO.setLivingWith(person.getLivingWith());
		personVO.setIncomeSource(person.getIncomeSource());
		personVO.setSns(person.getSns());
		personVO.setTopEducation(person.getTopEducation());
		personVO.setIsHaveCarLoan(person.getIsHaveCarLoan());
		personVO.setMonthIncome(person.getMonthIncome());
		personVO.setHomeCondition(person.getHomeCondition());
		personVO.setLoanSize(person.getLoanSize());
		personVO.setUnitIndustryCategory(person.getUnitIndustryCategory());
		return personVO;
	}
	
	/**
	 * 
	 * <pre>
	 * VO类型转为Model类型
	 * </pre>
	 *
	 * @param personVO
	 * @return
	 */
	public static Person transferVO2Model (PersonVO personVO) {
		if (personVO == null) {
			return null;
		}
		Person person = new Person();
		person.setId(personVO.getId());
		person.setName(personVO.getName());
		person.setSex(personVO.getSex());
		person.setPersonNo(personVO.getPersonNo());
		person.setIdnum(personVO.getIdnum());
		person.setCompanyId(personVO.getCompanyId());
		person.setMarried(personVO.getMarried());
		person.setIdentifier(personVO.getIdentifier());
		person.setEducationLevel(personVO.getEducationLevel());
		person.setHasChildren(personVO.getHasChildren());
		person.setChildrenNum(personVO.getChildrenNum());
		person.setZipCode(personVO.getZipCode());
		person.setWitness(personVO.getWitness());
		person.setOtherIncome(personVO.getOtherIncome());
		person.setPayDate(personVO.getPayDate());
		person.setWorkNature(personVO.getWorkNature());
		person.setJob(personVO.getJob());
		person.setDeptName(personVO.getDeptName());
		person.setExt(personVO.getExt());
		person.setPlaceDomicile(personVO.getPlaceDomicile());
		person.setHouseholdZipCode(personVO.getHouseholdZipCode());
		person.setAddress(personVO.getAddress());
		person.setMobilePhone(personVO.getMobilePhone());
		person.setEmail(personVO.getEmail());
		person.setHomePhone(personVO.getHomePhone());
		person.setHouseEstateType(personVO.getHouseEstateType());
		person.setRentPerMonth(personVO.getRentPerMonth());
		person.setLiveType(personVO.getLiveType());
		person.setHasHouseLoan(personVO.getHasHouseLoan());
		person.setHouseEstateAddress(personVO.getHouseEstateAddress());
		person.setIncomePerMonth(personVO.getIncomePerMonth());
		person.setWorkThatDept(personVO.getWorkThatDept());
		person.setWorkThatPosition(personVO.getWorkThatPosition());
		person.setWorkThatTell(personVO.getWorkThatTell());
		person.setPersonType(personVO.getPersonType());
		person.setCompanyType(personVO.getCompanyType());
		person.setCreatorId(personVO.getCreatorId());
		person.setCreator(personVO.getCreator());
		person.setCreatedTime(personVO.getCreatedTime());
		person.setModifierId(personVO.getModifierId());
		person.setModifier(personVO.getModifier());
		person.setModifiedTime(personVO.getModifiedTime());
		person.setVersion(personVO.getVersion());
		person.setProfessionType(personVO.getProfessionType());
		person.setMaxRepayAmount(personVO.getMaxRepayAmount());
		person.setChildrenSchool(personVO.getChildrenSchool());
		person.setPrivateEnterpriseType(personVO.getPrivateEnterpriseType());
		person.setFoundedDate(personVO.getFoundedDate());
		person.setBusinessPlace(personVO.getBusinessPlace());
		person.setTotalEmployees(personVO.getTotalEmployees());
		person.setRatioOfInvestments(personVO.getRatioOfInvestments());
		person.setMonthOfProfit(personVO.getMonthOfProfit());
		person.setProductType(personVO.getProductType());
		person.setPersonDebtBalance(personVO.getPersonDebtBalance());
		person.setMonthRepayAmount(personVO.getMonthRepayAmount());
		person.setLivingWith(personVO.getLivingWith());
		person.setIncomeSource(personVO.getIncomeSource());
		return person;
	}
}
