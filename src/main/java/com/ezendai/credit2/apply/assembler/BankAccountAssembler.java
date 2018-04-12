package com.ezendai.credit2.apply.assembler;

import com.ezendai.credit2.apply.model.BankAccount;
import com.ezendai.credit2.apply.vo.BankAccountVO;

/**
 * 
 * <pre>
 * 
 * </pre>
 *
 * @author zhangshihai
 * @version $Id: BankAccountAssembler.java, v 0.1 2014年7月30日 上午11:09:51 zhangshihai Exp $
 */
public class BankAccountAssembler {
	
	/**
	 * 
	 * <pre>
	 * Model转VO
	 * </pre>
	 *
	 * @param bank
	 * @return
	 */
	public static BankAccountVO transferModel2VO (BankAccount bankAccount) {
		if (bankAccount == null) {
			return null;
		}
		
		BankAccountVO bankAccountVO = new BankAccountVO();
		bankAccountVO.setId(bankAccount.getId());
		bankAccountVO.setBank(bankAccount.getBank());
		bankAccountVO.setAccount(bankAccount.getAccount());
		bankAccountVO.setBankName(bankAccount.getBankName());
		bankAccountVO.setBranchName(bankAccount.getBranchName());
		bankAccountVO.setStatus(bankAccount.getStatus());
		bankAccountVO.setCreator(bankAccount.getCreator());
		bankAccountVO.setCreatorId(bankAccount.getCreatorId());
		bankAccountVO.setCreatedTime(bankAccount.getCreatedTime());
		bankAccountVO.setModifier(bankAccount.getModifier());
		bankAccountVO.setModifierId(bankAccount.getModifierId());
		bankAccountVO.setModifiedTime(bankAccount.getModifiedTime());
		bankAccountVO.setVersion(bankAccount.getVersion());
		bankAccountVO.setAccountName(bankAccount.getAccountName());
		bankAccountVO.setCardType(bankAccount.getCardType());
		bankAccountVO.setAccountAuthType(bankAccount.getAccountAuthType());
		return bankAccountVO;
	}
	
	/**
	 * <pre>
	 * vo对象转换成model对象
	 * </pre>
	 *
	 * @param vo
	 * @return
	 */
	public static BankAccount transferVO2Model(BankAccountVO bankAccountVO) {
		if (bankAccountVO == null) {
			return null;
		}
		BankAccount bankAccount = new BankAccount();
		bankAccount.setId(bankAccountVO.getId());
		bankAccount.setBankId(bankAccountVO.getBank().getId());
		bankAccount.setAccount(bankAccountVO.getAccount());
		bankAccount.setBank(bankAccountVO.getBank());
		bankAccount.setBankName(bankAccountVO.getBankName());
		bankAccount.setBranchName(bankAccountVO.getBranchName());
		bankAccount.setStatus(bankAccountVO.getStatus());
		bankAccount.setCreator(bankAccountVO.getCreator());
		bankAccount.setCreatorId(bankAccountVO.getCreatorId());
		bankAccount.setCreatedTime(bankAccountVO.getCreatedTime());
		bankAccount.setModifier(bankAccountVO.getModifier());
		bankAccount.setModifierId(bankAccountVO.getModifierId());
		bankAccount.setModifiedTime(bankAccountVO.getModifiedTime());
		bankAccount.setVersion(bankAccountVO.getVersion());
		bankAccount.setAccountName(bankAccountVO.getAccountName());
		bankAccount.setCardType(bankAccountVO.getCardType());
		bankAccount.setAccountAuthType(bankAccountVO.getAccountAuthType());
		return bankAccount;
	}
}
