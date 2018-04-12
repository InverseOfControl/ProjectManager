package com.ezendai.credit2.audit.assembler;

import com.ezendai.credit2.audit.model.PersonBank;
import com.ezendai.credit2.audit.vo.PersonBankVO;

/**
 * 
 * <pre>
 * 客户银行账号信息 VO/Model转换
 * </pre>
 *
 * @author zhangshihai
 * @version $Id: PersonBankAssembler.java, v 0.1 2014年8月1日 上午10:47:53 zhangshihai Exp $
 */
public class PersonBankAssembler {

	/**
	 * 
	 * <pre>
	 * Model转为VO类型
	 * </pre>
	 *
	 * @param personBank
	 * @return
	 */
	public static PersonBankVO transferModel2VO (PersonBank personBank) {
		if (personBank == null) {
			return null;
		}
		
		PersonBankVO personBankVO = new PersonBankVO();
		personBankVO.setId(personBank.getId());
		personBankVO.setPersonId(personBank.getPersonId());
		personBankVO.setBankAccountId(personBank.getBankAccountId());
		personBankVO.setCreator(personBank.getCreator());
		personBankVO.setCreatorId(personBank.getCreatorId());
		personBankVO.setCreatedTime(personBank.getCreatedTime());
		personBankVO.setModifier(personBank.getModifier());
		personBankVO.setModifierId(personBank.getModifierId());
		personBankVO.setModifiedTime(personBank.getModifiedTime());
		personBankVO.setVersion(personBank.getVersion());
		personBankVO.setLoanId(personBank.getLoanId());
		personBankVO.setAccountAuthType(personBank.getAccountAuthType());
		return personBankVO;
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
	public static PersonBank transferVO2Model (PersonBankVO personBankVO) {
		if (personBankVO == null) {
			return null;
		}
		
		PersonBank personBank = new PersonBank();
		personBank.setId(personBankVO.getId());
		personBank.setPersonId(personBankVO.getPersonId());
		personBank.setBankAccountId(personBankVO.getBankAccountId());
		personBank.setCreator(personBankVO.getCreator());
		personBank.setCreatorId(personBankVO.getCreatorId());
		personBank.setCreatedTime(personBankVO.getCreatedTime());
		personBank.setModifier(personBankVO.getModifier());
		personBank.setModifierId(personBankVO.getModifierId());
		personBank.setModifiedTime(personBankVO.getModifiedTime());
		personBank.setVersion(personBankVO.getVersion());
		personBank.setLoanId(personBankVO.getLoanId());
		personBank.setAccountAuthType(personBankVO.getAccountAuthType());
		return personBank;
	}
}
