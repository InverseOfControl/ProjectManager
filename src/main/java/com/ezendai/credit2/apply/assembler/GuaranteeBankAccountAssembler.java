package com.ezendai.credit2.apply.assembler;

import com.ezendai.credit2.apply.model.GuaranteeBankAccount;
import com.ezendai.credit2.apply.vo.GuaranteeBankAccountVO;

/**
 * 
 * <pre>
 * 担保人银行账户 VO/Model转换
 * </pre>
 *
 * @author zhangshihai
 * @version $Id: GuaranteeBankAccountAssembler.java, v 0.1 2014年7月31日 上午9:21:41 zhangshihai Exp $
 */
public class GuaranteeBankAccountAssembler {
	
	/**
	 * 
	 * <pre>
	 * Model转为VO
	 * </pre>
	 *
	 * @return
	 */
	public static GuaranteeBankAccountVO transferModel2VO (GuaranteeBankAccount guaranteeBankAccount) {
		if (guaranteeBankAccount == null) {
			return null;
		}
		
		GuaranteeBankAccountVO guaranteeBankAccountVO = new GuaranteeBankAccountVO();
		guaranteeBankAccountVO.setId(guaranteeBankAccount.getId());
		guaranteeBankAccountVO.setBankId(guaranteeBankAccount.getBankId());
		guaranteeBankAccountVO.setGuarantee(guaranteeBankAccount.getGuarantee());
		guaranteeBankAccountVO.setBranchName(guaranteeBankAccount.getBranchName());
		guaranteeBankAccountVO.setBankName(guaranteeBankAccount.getBankName());
		guaranteeBankAccountVO.setAccount(guaranteeBankAccount.getAccount());
		guaranteeBankAccountVO.setStatus(guaranteeBankAccount.getStatus());
		guaranteeBankAccountVO.setCreator(guaranteeBankAccount.getCreator());
		guaranteeBankAccountVO.setCreatorId(guaranteeBankAccount.getCreatorId());
		guaranteeBankAccountVO.setCreatedTime(guaranteeBankAccount.getCreatedTime());
		guaranteeBankAccountVO.setModifier(guaranteeBankAccount.getModifier());
		guaranteeBankAccountVO.setModifierId(guaranteeBankAccount.getModifierId());
		guaranteeBankAccountVO.setModifiedTime(guaranteeBankAccount.getModifiedTime());
		guaranteeBankAccountVO.setVersion(guaranteeBankAccount.getVersion());
		
		return guaranteeBankAccountVO;
	}
	
	/**
	 * 
	 * <pre>
	 * VO转为Model
	 * </pre>
	 *
	 * @param guaranteeBankAccountVO
	 * @return
	 */
	public static GuaranteeBankAccount transferVO2Model (GuaranteeBankAccountVO guaranteeBankAccountVO) {
		if (guaranteeBankAccountVO == null) {
			return null;
		}
		
		GuaranteeBankAccount guaranteeBankAccount = new GuaranteeBankAccount();
		guaranteeBankAccount.setId(guaranteeBankAccountVO.getId());
		guaranteeBankAccount.setBankId(guaranteeBankAccountVO.getBankId());
		guaranteeBankAccount.setGuarantee(guaranteeBankAccountVO.getGuarantee());
		guaranteeBankAccount.setBranchName(guaranteeBankAccountVO.getBranchName());
		guaranteeBankAccount.setBankName(guaranteeBankAccountVO.getBankName());
		guaranteeBankAccount.setAccount(guaranteeBankAccountVO.getAccount());
		guaranteeBankAccount.setStatus(guaranteeBankAccountVO.getStatus());
		guaranteeBankAccount.setCreator(guaranteeBankAccountVO.getCreator());
		guaranteeBankAccount.setCreatorId(guaranteeBankAccountVO.getCreatorId());
		guaranteeBankAccount.setCreatedTime(guaranteeBankAccountVO.getCreatedTime());
		guaranteeBankAccount.setModifier(guaranteeBankAccountVO.getModifier());
		guaranteeBankAccount.setModifierId(guaranteeBankAccountVO.getModifierId());
		guaranteeBankAccount.setModifiedTime(guaranteeBankAccountVO.getModifiedTime());
		guaranteeBankAccount.setVersion(guaranteeBankAccountVO.getVersion());
		
		return guaranteeBankAccount;
	}

}
