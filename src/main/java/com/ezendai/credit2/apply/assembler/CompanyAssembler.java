/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.apply.assembler;

import com.ezendai.credit2.apply.model.Company;
import com.ezendai.credit2.apply.vo.CompanyVO;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author liyuepeng
 * @version $Id: CompanyAssembler.java, v 0.1 2014-7-25 下午01:38:35 liyuepeng Exp $
 */
public class CompanyAssembler {

	/**
	 * <pre>
	 * Model对象转换成Vo对象
	 * </pre>
	 *
	 * @param vo
	 * @return
	 */
	public static CompanyVO transferModel2VO(Company company) {
		if (company == null) {
			return null;
		}

		CompanyVO companyVO = new CompanyVO();
		companyVO.setId(company.getId());
		companyVO.setAddress(company.getAddress());
		companyVO.setAvgProfitPerYear(company.getAvgProfitPerYear());
		companyVO.setCategory(company.getCategory());
		companyVO.setEmployeesNumber(company.getEmployeesNumber());
		companyVO.setEmployeesWagesPerMonth(company.getEmployeesWagesPerMonth());
		companyVO.setFoundedDate(company.getFoundedDate());
		companyVO.setIncomePerMonth(company.getIncomePerMonth());
		companyVO.setIndustryInvolved(company.getIndustryInvolved());
		companyVO.setLegalRepresentative(company.getLegalRepresentative());
		companyVO.setLegalRepresentativeId(company.getLegalRepresentativeId());
		companyVO.setMajorBusiness(company.getMajorBusiness());
		companyVO.setName(company.getName());
		companyVO.setOperationSite(company.getOperationSite());
		companyVO.setPhone(company.getPhone());
		companyVO.setVersion(company.getVersion());
		companyVO.setZipCode(company.getZipCode());
		companyVO.setCreator(company.getCreator());
		companyVO.setCreatorId(company.getCreatorId());
		companyVO.setCreatedTime(company.getCreatedTime());
		companyVO.setModifier(company.getModifier());
		companyVO.setModifierId(company.getModifierId());
		companyVO.setModifiedTime(company.getModifiedTime());
		companyVO.setVersion(company.getVersion());
		companyVO.setPosAcquirer(company.getPosAcquirer());
		companyVO.setPosCapitavolume(company.getPosCapitavolume());
		companyVO.setPosOpenDate(company.getPosOpenDate());
		companyVO.setCompanyDebtBalance(company.getCompanyDebtBalance());
		companyVO.setMonthTurnOver(company.getMonthTurnOver());
		companyVO.setPlatform(company.getPlatform());
		companyVO.setRegDate(company.getRegDate());
		companyVO.setMemberType(company.getMemberType());
		return companyVO;
	}

	public static Company transferVO2Model(CompanyVO companyVO) {
		if (companyVO == null) {
			return null;
		}

		Company company = new Company();
		company.setId(companyVO.getId());
		company.setName(companyVO.getName());
		company.setIndustryInvolved(companyVO.getIndustryInvolved());
		company.setLegalRepresentative(companyVO.getLegalRepresentative());
		company.setLegalRepresentativeId(companyVO.getLegalRepresentativeId());
		company.setIncomePerMonth(companyVO.getIncomePerMonth());
		company.setFoundedDate(companyVO.getFoundedDate());
		company.setCategory(companyVO.getCategory());
		company.setAddress(companyVO.getAddress());
		company.setAvgProfitPerYear(companyVO.getAvgProfitPerYear());
		company.setPhone(companyVO.getPhone());
		company.setZipCode(companyVO.getZipCode());
		company.setOperationSite(companyVO.getOperationSite());
		company.setMajorBusiness(companyVO.getMajorBusiness());
		company.setEmployeesNumber(companyVO.getEmployeesNumber());
		company.setEmployeesWagesPerMonth(companyVO.getEmployeesWagesPerMonth());
		company.setCreator(companyVO.getCreator());
		company.setCreatorId(companyVO.getCreatorId());
		company.setCreatedTime(companyVO.getCreatedTime());
		company.setModifier(companyVO.getModifier());
		company.setModifierId(companyVO.getModifierId());
		company.setModifiedTime(companyVO.getModifiedTime());
		company.setVersion(companyVO.getVersion());
		company.setPosAcquirer(companyVO.getPosAcquirer());
		company.setPosCapitavolume(companyVO.getPosCapitavolume());
		company.setPosOpenDate(companyVO.getPosOpenDate());
		company.setCompanyDebtBalance(companyVO.getCompanyDebtBalance());
		company.setMonthTurnOver(companyVO.getMonthTurnOver());
		company.setPlatform(companyVO.getPlatform());
		company.setRegDate(companyVO.getRegDate());
		company.setMemberType(companyVO.getMemberType());
		return company;
	}

}
