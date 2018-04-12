package com.ezendai.credit2.after.assembler;

import com.ezendai.credit2.after.model.SpecialRepayment;
import com.ezendai.credit2.after.vo.SpecialRepaymentVO;

/**
 * <pre>
 * 特殊还款表Model与VO转换	
 * </pre>
 * 
 * @author zhangshihai
 * @Description:TODO
 */
public class SpecialRepaymentAssembler {
	public static SpecialRepaymentVO transferModel2VO(SpecialRepayment specialRepayment) {
		if(specialRepayment == null) {
			return null;
		}
		
		SpecialRepaymentVO specialRepaymentVO = new SpecialRepaymentVO();
		specialRepaymentVO.setId(specialRepayment.getId());
		specialRepaymentVO.setLoanId(specialRepayment.getLoanId());
		specialRepaymentVO.setRequestDate(specialRepayment.getRequestDate());
		specialRepaymentVO.setType(specialRepayment.getType());
		specialRepaymentVO.setStatus(specialRepayment.getStatus());
		specialRepaymentVO.setProposer(specialRepayment.getProposer());
		specialRepaymentVO.setAmount(specialRepayment.getAmount());
		specialRepaymentVO.setRemark(specialRepayment.getRemark());
		specialRepaymentVO.setVersion(specialRepayment.getVersion());
		specialRepaymentVO.setCreatorId(specialRepayment.getCreatorId());
		specialRepaymentVO.setCreator(specialRepayment.getCreator());
		specialRepaymentVO.setCreatedTime(specialRepayment.getCreatedTime());
		specialRepaymentVO.setModifierId(specialRepayment.getModifierId());
		specialRepaymentVO.setModifier(specialRepayment.getModifier());
		specialRepaymentVO.setModifiedTime(specialRepayment.getModifiedTime());
		specialRepaymentVO.setSalesDeptId(specialRepayment.getSalesDeptId());
		specialRepaymentVO.setNeedOffer(specialRepayment.getNeedOffer());
		specialRepaymentVO.setExtensionFlag(specialRepayment.getExtensionFlag());
		return specialRepaymentVO;
	}
	
	public static SpecialRepayment transferVO2Model(SpecialRepaymentVO specialRepaymentVO) {
		if(specialRepaymentVO == null) {
			return null;
		}
		
		SpecialRepayment specialRepayment = new SpecialRepayment();
		specialRepayment.setId(specialRepaymentVO.getId());
		specialRepayment.setLoanId(specialRepaymentVO.getLoanId());
		specialRepayment.setRequestDate(specialRepaymentVO.getRequestDate());
		specialRepayment.setType(specialRepaymentVO.getType());
		specialRepayment.setStatus(specialRepaymentVO.getStatus());
		specialRepayment.setProposer(specialRepaymentVO.getProposer());
		specialRepayment.setAmount(specialRepaymentVO.getAmount());
		specialRepayment.setRemark(specialRepaymentVO.getRemark());
		specialRepayment.setVersion(specialRepaymentVO.getVersion());
		specialRepayment.setCreatorId(specialRepaymentVO.getCreatorId());
		specialRepayment.setCreator(specialRepaymentVO.getCreator());
		specialRepayment.setCreatedTime(specialRepaymentVO.getCreatedTime());
		specialRepayment.setModifierId(specialRepaymentVO.getModifierId());
		specialRepayment.setModifier(specialRepaymentVO.getModifier());
		specialRepayment.setModifiedTime(specialRepaymentVO.getModifiedTime());
		specialRepayment.setSalesDeptId(specialRepaymentVO.getSalesDeptId());
		specialRepayment.setNeedOffer(specialRepaymentVO.getNeedOffer());
		specialRepayment.setExtensionFlag(specialRepaymentVO.getExtensionFlag());
		return specialRepayment;
	}
	
}
