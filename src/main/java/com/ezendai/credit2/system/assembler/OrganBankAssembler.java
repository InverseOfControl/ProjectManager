package com.ezendai.credit2.system.assembler;

import com.ezendai.credit2.system.model.Organ;
import com.ezendai.credit2.system.model.OrganBank;
import com.ezendai.credit2.system.vo.OrganBankVO;
import com.ezendai.credit2.system.vo.OrganVO;

/**
 * 
 * <pre>
 * 	VO/Model转换
 * </pre>
 *
 * @version $Id: SysUserAssembler.java, v 0.1 2014年8月1日 下午2:49:11 zhangshihai Exp $
 */
public class OrganBankAssembler {

	/**
	 * 
	 * <pre>
	 * Model转为VO
	 * </pre>
	 *
	 * @param sysUser
	 * @return
	 */
	public static OrganBankVO transferModel2VO (OrganBank organBank) {
		if (organBank == null) {
			return null;
		}
		OrganBankVO organBankVO = new OrganBankVO();
		organBankVO.setOrganId(organBank.getOrganId());
		organBankVO.setBankAccountId(organBank.getBankAccountId());
		
		return organBankVO;
	}
	
	/**
	 * 
	 * <pre>
	 * VO转为Model
	 * </pre>
	 *
	 * @param sysUserVO
	 * @return
	 */
	public static OrganBank transferVO2Model (OrganBankVO organBankVO) {
		if (organBankVO == null) {
			return null;
		}
		OrganBank organBank = new OrganBank();
		organBank.setOrganId(organBankVO.getOrganId());
		organBank.setBankAccountId(organBankVO.getBankAccountId());
		return organBank;
	}
}
