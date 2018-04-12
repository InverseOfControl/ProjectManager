package com.ezendai.credit2.audit.assembler;

import com.ezendai.credit2.audit.model.AuditTable;
import com.ezendai.credit2.audit.vo.AuditTableVO;

public class EduCreditAuditTableAssembler {

	/**
	 * 
	 * <pre>
	 * Model转为VO类型
	 * </pre>
	 *
	 * @param personBank
	 * @return
	 */
	public static AuditTableVO transferModel2VO (AuditTable auditTable) {
		if (auditTable == null) {
			return null;
		}
		AuditTableVO auditTableVO = new AuditTableVO();
		auditTableVO.setAuditAmount(auditTable.getAuditAmount());
		auditTableVO.setAuditMonthAmount(auditTable.getAuditMonthAmount());
		auditTableVO.setAuditResult(auditTable.getAuditResult());
		auditTableVO.setAuditSystem(auditTable.getAuditSystem());
		auditTableVO.setBlackInfo(auditTable.getBlackInfo());
		auditTableVO.setBlackList(auditTable.getBlackList());
		auditTableVO.setBusinessTime(auditTable.getBusinessTime());
		auditTableVO.setCompAddStatus(auditTable.getCompAddStatus());
		auditTableVO.setCompanyInfo(auditTable.getCompanyInfo());
		auditTableVO.setCompanyName(auditTable.getCompanyName());
		auditTableVO.setCompCreateDate(auditTable.getCompCreateDate());
		auditTableVO.setCompRegAmount(auditTable.getCompRegAmount());
		auditTableVO.setCourtCompinfo(auditTable.getCourtCompinfo());
		auditTableVO.setCourtPersonInfo(auditTable.getCourtPersonInfo());
		auditTableVO.setCreatedTime(auditTable.getCreatedTime());
		auditTableVO.setCreator(auditTable.getCreator());
		auditTableVO.setCreatorId(auditTable.getCreatorId());
		auditTableVO.setEvaluationOverall(auditTable.getEvaluationOverall());
		auditTableVO.setId(auditTable.getId());
		auditTableVO.setLoanId(auditTable.getLoanId());
		auditTableVO.setMajorBusiness(auditTable.getMajorBusiness());
		auditTableVO.setModifiedTime(auditTable.getModifiedTime());
		auditTableVO.setModifier(auditTable.getModifier());
		auditTableVO.setModifierId(auditTable.getModifierId());
		auditTableVO.setNowInfo(auditTable.getNowInfo());
		auditTableVO.setOtherAmount(auditTable.getOtherAmount());
		auditTableVO.setPassInfo(auditTable.getPassInfo());
		auditTableVO.setPersonid(auditTable.getPersonid());
		auditTableVO.setRatioOfInvestments(auditTable.getRatioOfInvestments());
		auditTableVO.setRentDate(auditTable.getRentDate());
		auditTableVO.setVersion(auditTable.getVersion());
		auditTableVO.setCreditAmount(auditTable.getCreditAmount());
		auditTableVO.setAuditResultFinal(auditTable.getAuditResultFinal());
		auditTableVO.setIncomePerMonth(auditTable.getIncomePerMonth());
		return auditTableVO;
	}
	
	/**
	 * 
	 * <pre>
	 * VO转为Model类型
	 * </pre>
	 *
	 * @param personBankVO
	 * @return
	 */
	public static AuditTable transferVO2Model (AuditTableVO auditTableVO) {
		if (auditTableVO == null) {
			return null;
		}
		AuditTable auditTable = new AuditTable();
		auditTable.setAuditAmount(auditTableVO.getAuditAmount());
		auditTable.setAuditMonthAmount(auditTableVO.getAuditMonthAmount());
		auditTable.setAuditResult(auditTableVO.getAuditResult());
		auditTable.setAuditSystem(auditTableVO.getAuditSystem());
		auditTable.setBlackInfo(auditTableVO.getBlackInfo());
		auditTable.setBlackList(auditTableVO.getBlackList());
		auditTable.setBusinessTime(auditTableVO.getBusinessTime());
		auditTable.setCompAddStatus(auditTableVO.getCompAddStatus());
		auditTable.setCompanyInfo(auditTableVO.getCompanyInfo());
		auditTable.setCompanyName(auditTableVO.getCompanyName());
		auditTable.setCompCreateDate(auditTableVO.getCompCreateDate());
		auditTable.setCompRegAmount(auditTableVO.getCompRegAmount());
		auditTable.setCourtCompinfo(auditTableVO.getCourtCompinfo());
		auditTable.setCourtPersonInfo(auditTableVO.getCourtPersonInfo());
		auditTable.setCreatedTime(auditTableVO.getCreatedTime());
		auditTable.setCreator(auditTableVO.getCreator());
		auditTable.setCreatorId(auditTableVO.getCreatorId());
		auditTable.setEvaluationOverall(auditTableVO.getEvaluationOverall());
		auditTable.setId(auditTableVO.getId());
		auditTable.setLoanId(auditTableVO.getLoanId());
		auditTable.setMajorBusiness(auditTableVO.getMajorBusiness());
		auditTable.setModifiedTime(auditTableVO.getModifiedTime());
		auditTable.setModifier(auditTableVO.getModifier());
		auditTable.setModifierId(auditTableVO.getModifierId());
		auditTable.setNowInfo(auditTableVO.getNowInfo());
		auditTable.setOtherAmount(auditTableVO.getOtherAmount());
		auditTable.setPassInfo(auditTableVO.getPassInfo());
		auditTable.setPersonid(auditTableVO.getPersonid());
		auditTable.setRatioOfInvestments(auditTableVO.getRatioOfInvestments());
		auditTable.setRentDate(auditTableVO.getRentDate());
		auditTable.setVersion(auditTableVO.getVersion());
		auditTable.setCreditAmount(auditTableVO.getAuditAmount());
		auditTable.setAuditResultFinal(auditTableVO.getAuditResultFinal());
		auditTable.setIncomePerMonth(auditTableVO.getIncomePerMonth());
		return auditTable;
	}
}
