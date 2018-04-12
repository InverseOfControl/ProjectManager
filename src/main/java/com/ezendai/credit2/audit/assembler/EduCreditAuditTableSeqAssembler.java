package com.ezendai.credit2.audit.assembler;

import com.ezendai.credit2.audit.model.AuditTableSeqlist;
import com.ezendai.credit2.audit.vo.AuditTableSeqlistVO;

public class EduCreditAuditTableSeqAssembler {

	/**
	 * 
	 * <pre>
	 * Model转为VO类型
	 * </pre>
	 *
	 * @param personBank
	 * @return
	 */
	public static AuditTableSeqlistVO transferModel2VO (AuditTableSeqlist auditTableSeqlist) {
		if (auditTableSeqlist == null) {
			return null;
		}
		AuditTableSeqlistVO auditTableSeqlistVO = new AuditTableSeqlistVO();
		auditTableSeqlistVO.setCreatedTime(auditTableSeqlist.getCreatedTime());
		auditTableSeqlistVO.setCreator(auditTableSeqlist.getCreator());
		auditTableSeqlistVO.setCreatorId(auditTableSeqlist.getCreatorId());
		auditTableSeqlistVO.setId(auditTableSeqlist.getId());
		auditTableSeqlistVO.setLoanId(auditTableSeqlist.getLoanId());
		auditTableSeqlistVO.setModifiedTime(auditTableSeqlist.getModifiedTime());
		auditTableSeqlistVO.setModifier(auditTableSeqlist.getModifier());
		auditTableSeqlistVO.setModifierId(auditTableSeqlist.getModifierId());
		auditTableSeqlistVO.setMonthAmountFive(auditTableSeqlist.getMonthAmountFive());
		auditTableSeqlistVO.setMonthAmountFour(auditTableSeqlist.getMonthAmountFour());
		auditTableSeqlistVO.setMonthAmountOne(auditTableSeqlist.getMonthAmountOne());
		auditTableSeqlistVO.setMonthAmountSec(auditTableSeqlist.getMonthAmountSec());
		auditTableSeqlistVO.setMonthAmountSix(auditTableSeqlist.getMonthAmountSix());
		auditTableSeqlistVO.setMonthAmountThr(auditTableSeqlist.getMonthAmountThr());
		auditTableSeqlistVO.setSeqFive(auditTableSeqlist.getSeqFive());
		auditTableSeqlistVO.setSeqFour(auditTableSeqlist.getSeqFour());
		auditTableSeqlistVO.setSeqOne(auditTableSeqlist.getSeqOne());
		auditTableSeqlistVO.setSeqSec(auditTableSeqlist.getSeqSec());
		auditTableSeqlistVO.setSeqSix(auditTableSeqlist.getSeqSix());
		auditTableSeqlistVO.setSeqThr(auditTableSeqlist.getSeqThr());
		auditTableSeqlistVO.setType(auditTableSeqlist.getType());
		auditTableSeqlistVO.setVersion(auditTableSeqlist.getVersion());
		return auditTableSeqlistVO;
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
	public static AuditTableSeqlist transferVO2Model (AuditTableSeqlistVO auditTableSeqlistVO) {
		if (auditTableSeqlistVO == null) {
			return null;
		}
		AuditTableSeqlist auditTableSeqlist = new AuditTableSeqlist();
		auditTableSeqlist.setCreatedTime(auditTableSeqlistVO.getCreatedTime());
		auditTableSeqlist.setCreator(auditTableSeqlistVO.getCreator());
		auditTableSeqlist.setCreatorId(auditTableSeqlistVO.getCreatorId());
		auditTableSeqlist.setId(auditTableSeqlistVO.getId());
		auditTableSeqlist.setLoanId(auditTableSeqlistVO.getLoanId());
		auditTableSeqlist.setModifiedTime(auditTableSeqlistVO.getModifiedTime());
		auditTableSeqlist.setModifier(auditTableSeqlistVO.getModifier());
		auditTableSeqlist.setModifierId(auditTableSeqlistVO.getModifierId());
		auditTableSeqlist.setMonthAmountFive(auditTableSeqlistVO.getMonthAmountFive());
		auditTableSeqlist.setMonthAmountFour(auditTableSeqlistVO.getMonthAmountFour());
		auditTableSeqlist.setMonthAmountOne(auditTableSeqlistVO.getMonthAmountOne());
		auditTableSeqlist.setMonthAmountSec(auditTableSeqlistVO.getMonthAmountSec());
		auditTableSeqlist.setMonthAmountSix(auditTableSeqlistVO.getMonthAmountSix());
		auditTableSeqlist.setMonthAmountThr(auditTableSeqlistVO.getMonthAmountThr());
		auditTableSeqlist.setSeqFive(auditTableSeqlistVO.getSeqFive());
		auditTableSeqlist.setSeqFour(auditTableSeqlistVO.getSeqFour());
		auditTableSeqlist.setSeqOne(auditTableSeqlistVO.getSeqOne());
		auditTableSeqlist.setSeqSec(auditTableSeqlistVO.getSeqSec());
		auditTableSeqlist.setSeqSix(auditTableSeqlistVO.getSeqSix());
		auditTableSeqlist.setSeqThr(auditTableSeqlistVO.getSeqThr());
		auditTableSeqlist.setType(auditTableSeqlistVO.getType());
		auditTableSeqlist.setVersion(auditTableSeqlistVO.getVersion());
		return auditTableSeqlist;
	}
}
