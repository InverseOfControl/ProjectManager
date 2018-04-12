package com.ezendai.credit2.apply.assembler;

import com.ezendai.credit2.apply.model.Contacter;
import com.ezendai.credit2.apply.vo.ContacterVO;

/**
 * 
 * <pre>
 * 联系人 VO与Model转换
 * </pre>
 *
 * @author zhangshihai
 * @version $Id: ContacterAssembler.java, v 0.1 2014年7月30日 下午3:36:31 zhangshihai Exp $
 */
public class ContacterAssembler {
	
	/**
	 * 
	 * <pre>
	 * Model转为VO
	 * </pre>
	 *
	 * @param contacter
	 * @return
	 */
	public static ContacterVO transferModel2VO (Contacter contacter) {
		if (contacter == null) {
			return null;
		}
		
		ContacterVO contacterVO = new ContacterVO();
		contacterVO.setId(contacter.getId());
		contacterVO.setName(contacter.getName());
		contacterVO.setRelationship(contacter.getRelationship());
		contacterVO.setMobilePhone(contacter.getMobilePhone());
		contacterVO.setHomePhone(contacter.getHomePhone());
		contacterVO.setAddress(contacter.getAddress());
		contacterVO.setWorkUnit(contacter.getWorkUnit());
		contacterVO.setHadKnown(contacter.getHadKnown());
		contacterVO.setBorrowerId(contacter.getBorrowerId());
		contacterVO.setCreator(contacter.getCreator());
		contacterVO.setCreatorId(contacter.getCreatorId());
		contacterVO.setCreatedTime(contacter.getCreatedTime());
		contacterVO.setModifier(contacter.getModifier());
		contacterVO.setModifierId(contacter.getModifierId());
		contacterVO.setModifiedTime(contacter.getModifiedTime());
		contacterVO.setVersion(contacter.getVersion());
		contacterVO.setLoanId(contacter.getLoanId());
		contacterVO.setTitle(contacter.getTitle());
		return contacterVO;
	}

	/**
	 * 
	 * <pre>
	 * VO转为Model
	 * </pre>
	 *
	 * @param contacterVO
	 * @return
	 */
	public static Contacter transferVO2Model (ContacterVO contacterVO) {
		if (contacterVO == null) {
			return null;
		}
		
		Contacter contacter = new Contacter();
		contacter.setId(contacterVO.getId());
		contacter.setName(contacterVO.getName());
		contacter.setRelationship(contacterVO.getRelationship());
		contacter.setMobilePhone(contacterVO.getMobilePhone());
		contacter.setHomePhone(contacterVO.getHomePhone());
		contacter.setAddress(contacterVO.getAddress());
		contacter.setWorkUnit(contacterVO.getWorkUnit());
		contacter.setHadKnown(contacterVO.getHadKnown());
		contacter.setBorrowerId(contacterVO.getBorrowerId());
		contacter.setCreator(contacterVO.getCreator());
		contacter.setCreatorId(contacterVO.getCreatorId());
		contacter.setCreatedTime(contacterVO.getCreatedTime());
		contacter.setModifier(contacterVO.getModifier());
		contacter.setModifierId(contacterVO.getModifierId());
		contacter.setModifiedTime(contacterVO.getModifiedTime());
		contacter.setVersion(contacterVO.getVersion());
		contacter.setLoanId(contacterVO.getLoanId());
		contacter.setTitle(contacterVO.getTitle());
		return contacter;
	}
}
