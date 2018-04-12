package com.ezendai.credit2.apply.assembler;

import com.ezendai.credit2.apply.model.Bank;
import com.ezendai.credit2.apply.vo.BankVO;

/**
 * 
 * <pre>
 * 银行VO与Model转换
 * </pre>
 *
 * @author zhangshihai
 * @version $Id: BankAssembler.java, v 0.1 2014年7月30日 上午10:50:23 zhangshihai Exp $
 */
public class BankAssembler {

	/**
	 * 
	 * <pre>
	 * Model类型转为VO类型
	 * </pre>
	 *
	 * @param bank
	 * @return
	 */
	public static BankVO transferModel2VO (Bank bank) {
		if (bank == null) {
			return null;
		}
		
		BankVO bankVO = new BankVO();
		bankVO.setId(bank.getId());
		bankVO.setBankName(bank.getBankName());
		bankVO.setBankCode(bank.getBankCode());
		bankVO.setCreator(bank.getCreator());
		bankVO.setCreatorId(bank.getCreatorId());
		bankVO.setCreatedTime(bank.getCreatedTime());
		bankVO.setModifier(bank.getModifier());
		bankVO.setModifierId(bank.getModifierId());
		bankVO.setModifiedTime(bank.getModifiedTime());
		bankVO.setVersion(bank.getVersion());
		bankVO.setBankType(bank.getBankType());
		return bankVO;
	}
	
	/**
	 * 
	 * <pre>
	 * VO类型转为Model类型
	 * </pre>
	 *
	 * @param bankVO
	 * @return
	 */
	public static Bank transferVO2Model (BankVO bankVO) {
		if (bankVO == null) {
			return null;
		}
		
		Bank bank = new Bank();
		bank.setId(bankVO.getId());
		bank.setBankName(bankVO.getBankName());
		bank.setBankCode(bankVO.getBankCode());
		bank.setCreator(bankVO.getCreator());
		bank.setCreatorId(bankVO.getCreatorId());
		bank.setCreatedTime(bankVO.getCreatedTime());
		bank.setModifier(bankVO.getModifier());
		bank.setModifierId(bankVO.getModifierId());
		bank.setModifiedTime(bankVO.getModifiedTime());
		bank.setVersion(bankVO.getVersion());
		bank.setBankType(bankVO.getBankType());
		return bank;
	}
}
