package com.ezendai.credit2.apply.assembler;

import com.ezendai.credit2.apply.model.Guarantee;
import com.ezendai.credit2.apply.vo.GuaranteeVO;

/**
 * 
 * <pre>
 * 担保人  VO/Model类型转换
 * </pre>
 *
 * @author zhangshihai
 * @version $Id: GuaranteeAssembler.java, v 0.1 2014年7月25日 下午4:53:23 zhangshihai Exp $
 */
public class GuaranteeAssembler {

	/**
	 * 
	 * <pre>
	 * Model类型转换为VO类型
	 * </pre>
	 *
	 * @param guarantee
	 * @return
	 */
	public static GuaranteeVO transferModel2VO(Guarantee guarantee) {
		if (guarantee == null) {
			return null;
		}

		GuaranteeVO guaranteeVO = new GuaranteeVO();
		guaranteeVO.setId(guarantee.getId());
		guaranteeVO.setLoanId(guarantee.getLoanId());
		guaranteeVO.setLoan(guarantee.getLoan());
		guaranteeVO.setName(guarantee.getName());
		guaranteeVO.setGuaranteeType(guarantee.getGuaranteeType());
		guaranteeVO.setSex(guarantee.getSex());
		guaranteeVO.setIdnum(guarantee.getIdnum());
		guaranteeVO.setMarried(guarantee.getMarried());
		guaranteeVO.setEducationLevel(guarantee.getEducationLevel());
		guaranteeVO.setHasChildren(guarantee.getHasChildren());
		guaranteeVO.setZipCode(guarantee.getZipCode());
		guaranteeVO.setAddress(guarantee.getAddress());
		guaranteeVO.setMobilePhone(guarantee.getMobilePhone());
		guaranteeVO.setEmail(guarantee.getEmail());
		guaranteeVO.setHomePhone(guarantee.getHomePhone());
		guaranteeVO.setCompanyFullName(guarantee.getCompanyFullName());
		guaranteeVO.setCompanyPhone(guarantee.getCompanyPhone());
		guaranteeVO.setCompanyAddress(guarantee.getCompanyAddress());
		guaranteeVO.setPerson(guarantee.getPerson());
		guaranteeVO.setPersonId(guarantee.getPersonId());
		guaranteeVO.setBankAccountId(guarantee.getBankAccountId());
		guaranteeVO.setCreatorId(guarantee.getCreatorId());
		guaranteeVO.setCreator(guarantee.getCreator());
		guaranteeVO.setCreatedTime(guarantee.getCreatedTime());
		guaranteeVO.setModifierId(guarantee.getModifierId());
		guaranteeVO.setModifier(guarantee.getModifier());
		guaranteeVO.setModifiedTime(guarantee.getModifiedTime());
		guaranteeVO.setVersion(guarantee.getVersion());
		guaranteeVO.setFlag(guarantee.getFlag());
		guaranteeVO.setIsFlagNull(guarantee.getIsFlagNull());
		return guaranteeVO;
	}

	/**
	 * 
	 * <pre>
	 * VO转为Model
	 * </pre>
	 *
	 * @param guarantee
	 * @return
	 */
	public static Guarantee transferVO2Model(GuaranteeVO guaranteeVO) {
		if (guaranteeVO == null) {
			return null;
		}

		Guarantee guarantee = new Guarantee();
		guarantee.setId(guaranteeVO.getId());
		guarantee.setLoanId(guaranteeVO.getLoanId());
		guarantee.setName(guaranteeVO.getName());
		guarantee.setGuaranteeType(guaranteeVO.getGuaranteeType());
		guarantee.setSex(guaranteeVO.getSex());
		guarantee.setIdnum(guaranteeVO.getIdnum());
		guarantee.setMarried(guaranteeVO.getMarried());
		guarantee.setEducationLevel(guaranteeVO.getEducationLevel());
		guarantee.setHasChildren(guaranteeVO.getHasChildren());
		guarantee.setZipCode(guaranteeVO.getZipCode());
		guarantee.setAddress(guaranteeVO.getAddress());
		guarantee.setMobilePhone(guaranteeVO.getMobilePhone());
		guarantee.setEmail(guaranteeVO.getEmail());
		guarantee.setHomePhone(guaranteeVO.getHomePhone());
		guarantee.setCompanyFullName(guaranteeVO.getCompanyFullName());
		guarantee.setCompanyPhone(guaranteeVO.getCompanyPhone());
		guarantee.setCompanyAddress(guaranteeVO.getCompanyAddress());
		guarantee.setPerson(guaranteeVO.getPerson());
		guarantee.setPersonId(guaranteeVO.getPersonId());
		guarantee.setBankAccountId(guaranteeVO.getBankAccountId());
		guarantee.setCreatorId(guaranteeVO.getCreatorId());
		guarantee.setCreator(guaranteeVO.getCreator());
		guarantee.setCreatedTime(guaranteeVO.getCreatedTime());
		guarantee.setModifierId(guaranteeVO.getModifierId());
		guarantee.setModifier(guaranteeVO.getModifier());
		guarantee.setModifiedTime(guaranteeVO.getModifiedTime());
		guarantee.setVersion(guaranteeVO.getVersion());
		guarantee.setFlag(guaranteeVO.getFlag());
		guarantee.setIsFlagNull(guaranteeVO.getIsFlagNull());
		return guarantee;
	}
}
