/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.after.assembler;

import com.ezendai.credit2.after.model.BusinessAccount;
import com.ezendai.credit2.after.vo.BusinessAccountVO;

/**
 * <pre>
 * 对公还款Model与VO互转
 * </pre>
 *
 * @author zhangshihai
 * @version $Id: BusinessAccountAssembler.java, v 0.1 2014年12月11日 上午11:04:21 00221921 Exp $
 */
public class BusinessAccountAssembler {
	public static BusinessAccountVO transferModel2VO(BusinessAccount businessAccount) {
		if(businessAccount == null) {
			return null;
		}
		
		BusinessAccountVO businessAccountVO = new BusinessAccountVO();
		businessAccountVO.setId(businessAccount.getId());
		businessAccountVO.setLoanId(businessAccount.getLoanId());
		businessAccountVO.setFirstAccount(businessAccount.getFirstAccount());
		businessAccountVO.setSecondAccount(businessAccount.getSecondAccount());
		businessAccountVO.setSecondBank(businessAccount.getSecondBank());
		businessAccountVO.setSecondCompany(businessAccount.getSecondCompany());
		businessAccountVO.setRepayDate(businessAccount.getRepayDate());
		businessAccountVO.setRepayTime(businessAccount.getRepayTime());
		businessAccountVO.setAmount(businessAccount.getAmount());
		businessAccountVO.setVoucherNo(businessAccount.getVoucherNo());
		businessAccountVO.setType(businessAccount.getType());
		businessAccountVO.setPurpose(businessAccount.getPurpose());
		businessAccountVO.setRemark(businessAccount.getRemark());
		businessAccountVO.setComments(businessAccount.getComments());
		businessAccountVO.setRecTime(businessAccount.getRecTime());
		businessAccountVO.setRecOperatorId(businessAccount.getRecOperatorId());
		businessAccountVO.setStatus(businessAccount.getStatus());
		businessAccountVO.setVersion(businessAccount.getVersion());
		businessAccountVO.setCreatorId(businessAccount.getCreatorId());
		businessAccountVO.setCreator(businessAccount.getCreator());
		businessAccountVO.setCreatedTime(businessAccount.getCreatedTime());
		businessAccountVO.setModifierId(businessAccount.getModifierId());
		businessAccountVO.setModifier(businessAccount.getModifier());
		businessAccountVO.setModifiedTime(businessAccount.getModifiedTime());
		
		return businessAccountVO;
	}
	
	
	public static BusinessAccount transferVO2Model(BusinessAccountVO businessAccountVO) {
		if(businessAccountVO == null) {
			return null;
		}
		
		BusinessAccount businessAccount = new BusinessAccount();
		businessAccount.setId(businessAccountVO.getId());
		businessAccount.setLoanId(businessAccountVO.getLoanId());
		businessAccount.setFirstAccount(businessAccountVO.getFirstAccount());
		businessAccount.setSecondAccount(businessAccountVO.getSecondAccount());
		businessAccount.setSecondBank(businessAccountVO.getSecondBank());
		businessAccount.setSecondCompany(businessAccountVO.getSecondCompany());
		businessAccount.setRepayDate(businessAccountVO.getRepayDate());
		businessAccount.setRepayTime(businessAccountVO.getRepayTime());
		businessAccount.setAmount(businessAccountVO.getAmount());
		businessAccount.setVoucherNo(businessAccountVO.getVoucherNo());
		businessAccount.setType(businessAccountVO.getType());
		businessAccount.setPurpose(businessAccountVO.getPurpose());
		businessAccount.setRemark(businessAccountVO.getRemark());
		businessAccount.setComments(businessAccountVO.getComments());
		businessAccount.setRecTime(businessAccountVO.getRecTime());
		businessAccount.setRecOperatorId(businessAccountVO.getRecOperatorId());
		businessAccount.setStatus(businessAccountVO.getStatus());
		businessAccount.setVersion(businessAccountVO.getVersion());
		businessAccount.setCreatorId(businessAccountVO.getCreatorId());
		businessAccount.setCreator(businessAccountVO.getCreator());
		businessAccount.setCreatedTime(businessAccountVO.getCreatedTime());
		businessAccount.setModifierId(businessAccountVO.getModifierId());
		businessAccount.setModifier(businessAccountVO.getModifier());
		businessAccount.setModifiedTime(businessAccountVO.getModifiedTime());
		
		return businessAccount;
	}
}
