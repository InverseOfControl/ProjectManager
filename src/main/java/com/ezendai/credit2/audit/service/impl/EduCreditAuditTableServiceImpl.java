package com.ezendai.credit2.audit.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezendai.credit2.apply.assembler.CompanyAssembler;
import com.ezendai.credit2.apply.assembler.PersonAssembler;
import com.ezendai.credit2.apply.dao.CompanyDao;
import com.ezendai.credit2.apply.dao.PersonDao;
import com.ezendai.credit2.apply.model.Company;
import com.ezendai.credit2.apply.model.Person;
import com.ezendai.credit2.audit.assembler.EduCreditAuditTableAssembler;
import com.ezendai.credit2.audit.dao.AuditTableDao;
import com.ezendai.credit2.audit.model.AuditTable;
import com.ezendai.credit2.audit.service.EduCreditAuditTableService;
import com.ezendai.credit2.audit.vo.AuditTableVO;

@Service
public class EduCreditAuditTableServiceImpl implements
		EduCreditAuditTableService {

	@Autowired
	private AuditTableDao auditTableDao;
	
	@Autowired
	private PersonDao personDao;
	
	@Autowired
	private CompanyDao companyDao;
	
	
	@Override
	public AuditTable getByLoanId(long loanId) {
		// TODO Auto-generated method stub
		return auditTableDao.getByLoanId(loanId);
	}

	@Override
	@Transactional
	public void insertOrUpdateAuditTable(AuditTable auditTable) {
		// TODO Auto-generated method stub
		if(auditTableDao.getByLoanId(auditTable.getLoanId())==null){
			Person person=personDao.get(auditTable.getPersonid());
			if(person != null){
				Company personCompany=companyDao.get(person.getCompanyId());
				if(personCompany != null){
					if(auditTable.getMajorBusiness() != null){
						personCompany.setMajorBusiness(auditTable.getMajorBusiness());
					}
					if(auditTable.getCompCreateDate() != null){
						personCompany.setFoundedDate(auditTable.getCompCreateDate());
					}
					if(auditTable.getCompanyName() != null && !auditTable.getCompanyName().equals("")){
						personCompany.setName(auditTable.getCompanyName());
					}
					if(auditTable.getRatioOfInvestments() != null){
						person.setRatioOfInvestments(auditTable.getRatioOfInvestments().intValue());
					}
					if(auditTable.getAuditMonthAmount() != null){
						person.setMonthRepayAmount(auditTable.getAuditMonthAmount().longValue());
					}
					if(auditTable.getIncomePerMonth()!=null){
						person.setIncomePerMonth(auditTable.getIncomePerMonth());
					}
				}
				companyDao.update(CompanyAssembler.transferModel2VO(personCompany));
				personDao.update(PersonAssembler.transferModel2VO(person));
			}
			auditTableDao.insert(auditTable);
		}else{
			AuditTableVO auditTableVO=EduCreditAuditTableAssembler.transferModel2VO(auditTable);
			Person person=personDao.get(auditTableVO.getPersonid());
			if(person != null){
				Company personCompany=companyDao.get(person.getCompanyId());
				if(personCompany != null){
					if(auditTableVO.getMajorBusiness() != null){
						personCompany.setMajorBusiness(auditTableVO.getMajorBusiness());
					}
					if(auditTableVO.getCompCreateDate() != null){
						personCompany.setFoundedDate(auditTableVO.getCompCreateDate());
					}
					if(auditTableVO.getCompanyName() != null && !auditTableVO.getCompanyName().equals("")){
						personCompany.setName(auditTableVO.getCompanyName());
					}
					if(auditTableVO.getRatioOfInvestments() != null){
						person.setRatioOfInvestments(auditTableVO.getRatioOfInvestments().intValue());
					}
					if(auditTableVO.getAuditMonthAmount() != null){
						person.setMonthRepayAmount(auditTableVO.getAuditMonthAmount().longValue());
					}
					if(auditTableVO.getIncomePerMonth() != null){
						person.setIncomePerMonth(auditTableVO.getIncomePerMonth());
					}
				}
				companyDao.update(CompanyAssembler.transferModel2VO(personCompany));
				personDao.update(PersonAssembler.transferModel2VO(person));
			}
			auditTableDao.update(auditTableVO);
		}
	}

	@Override
	@Transactional
	public void updateAuditTable(AuditTableVO auditTableVO) {
		// TODO Auto-generated method stub
		Person person=personDao.get(auditTableVO.getPersonid());
		if(person != null){
			Company personCompany=companyDao.get(person.getCompanyId());
			if(personCompany != null){
				if(auditTableVO.getMajorBusiness() != null){
					personCompany.setMajorBusiness(auditTableVO.getMajorBusiness());
				}
				if(auditTableVO.getCompCreateDate() != null){
					personCompany.setFoundedDate(auditTableVO.getCompCreateDate());
				}
				if(auditTableVO.getCompanyName() != null && !auditTableVO.getCompanyName().equals("")){
					personCompany.setName(auditTableVO.getCompanyName());
				}
				if(auditTableVO.getRatioOfInvestments() != null){
					person.setRatioOfInvestments(auditTableVO.getRatioOfInvestments().intValue());
				}
				if(auditTableVO.getAuditMonthAmount() != null){
					person.setMonthRepayAmount(auditTableVO.getAuditMonthAmount().longValue());
				}
				if(auditTableVO.getIncomePerMonth()!=null){
					person.setIncomePerMonth(auditTableVO.getIncomePerMonth());
				}
			}
			companyDao.update(CompanyAssembler.transferModel2VO(personCompany));
			personDao.update(PersonAssembler.transferModel2VO(person));
		}
		auditTableDao.update(auditTableVO);
	}


}
