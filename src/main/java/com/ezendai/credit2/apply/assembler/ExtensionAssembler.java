/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.apply.assembler;

import com.ezendai.credit2.apply.model.Extension;
import com.ezendai.credit2.apply.vo.ExtensionVO;

/**
 * <pre>
 * Extension的Model与VO转换
 * </pre>
 *
 * @author zhangshihai
 * @version $Id: ExtensionAssembler.java, v 0.1 2015年3月10日 下午1:25:29 00221921 Exp $
 */
public class ExtensionAssembler {

	/**
	 * 
	 * <pre>
	 * Model转VO
	 * </pre>
	 *
	 * @param extension
	 * @return
	 */
	public static ExtensionVO transferModel2VO(Extension extension) {
		if(extension == null) {
			return null;
		}
		
		ExtensionVO extensionVO = new ExtensionVO();
		extensionVO.setId(extension.getId());
		extensionVO.setPersonId(extension.getPersonId());
		extensionVO.setVersion(extension.getVersion());
		extensionVO.setPersonId(extension.getPersonId());
		extensionVO.setPactMoney(extension.getPactMoney());
		extensionVO.setTime(extension.getTime());
		extensionVO.setCurrNum(extension.getCurrNum());
		extensionVO.setGrantMoney(extension.getGrantMoney());
		extensionVO.setRequestDate(extension.getRequestDate());
		extensionVO.setContractNo(extension.getContractNo());
		extensionVO.setSignDate(extension.getSignDate());
		extensionVO.setAuditDate(extension.getAuditDate());
		extensionVO.setAuditMoney(extension.getAuditMoney());
		extensionVO.setRequestMoney(extension.getRequestMoney());
		extensionVO.setReturnDate(extension.getReturnDate());
		extensionVO.setStartRepayDate(extension.getStartRepayDate());
		extensionVO.setEndRepayDate(extension.getEndRepayDate());
		extensionVO.setServiceId(extension.getServiceId());
		extensionVO.setCrmId(extension.getCrmId());
		extensionVO.setBizDirectorId(extension.getBizDirectorId());
		extensionVO.setAssessorId(extension.getAssessorId());
		extensionVO.setResidualPactMoney(extension.getResidualPactMoney());
		extensionVO.setResidualTime(extension.getResidualTime());
		extensionVO.setGrantDate(extension.getGrantDate());
		extensionVO.setContractConfirmDate(extension.getContractConfirmDate());
		extensionVO.setContractBackDate(extension.getContractBackDate());
		extensionVO.setSubmitDate(extension.getSubmitDate());
		extensionVO.setContractCreatedTime(extension.getContractCreatedTime());
		extensionVO.setStatus(extension.getStatus());
		extensionVO.setRemark(extension.getRemark());
		extensionVO.setCreator(extension.getCreator());
		extensionVO.setCreatorId(extension.getCreatorId());
		extensionVO.setCreatedTime(extension.getCreatedTime());
		extensionVO.setModifier(extension.getModifier());
		extensionVO.setModifierId(extension.getModifierId());
		extensionVO.setModifiedTime(extension.getModifiedTime());
		extensionVO.setAuditTime(extension.getAuditTime());
		extensionVO.setExtensionTime(extension.getExtensionTime());
		extensionVO.setGrantAccountId(extension.getGrantAccountId());
		extensionVO.setRepayAccountId(extension.getRepayAccountId());
		extensionVO.setProductId(extension.getProductId());
		extensionVO.setLoanType(extension.getLoanType());
		extensionVO.setSalesDeptId(extension.getSalesDeptId());
		extensionVO.setSalesTeamId(extension.getSalesTeamId());
		extensionVO.setContractSrc(extension.getContractSrc());
		extensionVO.setAssessment(extension.getAssessment());
		extensionVO.setConsult(extension.getConsult());
		extensionVO.setbManage(extension.getbManage());
		extensionVO.setcManage(extension.getcManage());
		extensionVO.setRisk(extension.getRisk());
		extensionVO.setManagerId(extension.getManagerId());
		extensionVO.setProductType(extension.getProductType());
		return extensionVO;
	}
	
	/**
	 * 
	 * <pre>
	 * VO转Model
	 * </pre>
	 *
	 * @param extensionVO
	 * @return
	 */
	public static Extension transferVO2Model(ExtensionVO extensionVO) {
		if(extensionVO == null) {
			return null;
		}
		
		Extension extension = new Extension();
		extension.setId(extensionVO.getId());
		extension.setPersonId(extensionVO.getPersonId());
		extension.setVersion(extensionVO.getVersion());
		extension.setPersonId(extensionVO.getPersonId());
		extension.setPactMoney(extensionVO.getPactMoney());
		extension.setTime(extensionVO.getTime());
		extension.setCurrNum(extensionVO.getCurrNum());
		extension.setGrantMoney(extensionVO.getGrantMoney());
		extension.setRequestDate(extensionVO.getRequestDate());
		extension.setContractNo(extensionVO.getContractNo());
		extension.setSignDate(extensionVO.getSignDate());
		extension.setAuditDate(extensionVO.getAuditDate());
		extension.setAuditMoney(extensionVO.getAuditMoney());
		extension.setRequestMoney(extensionVO.getRequestMoney());
		extension.setReturnDate(extensionVO.getReturnDate());
		extension.setStartRepayDate(extensionVO.getStartRepayDate());
		extension.setEndRepayDate(extensionVO.getEndRepayDate());
		extension.setServiceId(extensionVO.getServiceId());
		extension.setCrmId(extensionVO.getCrmId());
		extension.setBizDirectorId(extensionVO.getBizDirectorId());
		extension.setAssessorId(extensionVO.getAssessorId());
		extension.setResidualPactMoney(extensionVO.getResidualPactMoney());
		extension.setResidualTime(extensionVO.getResidualTime());
		extension.setGrantDate(extensionVO.getGrantDate());
		extension.setContractConfirmDate(extensionVO.getContractConfirmDate());
		extension.setContractBackDate(extensionVO.getContractBackDate());
		extension.setSubmitDate(extensionVO.getSubmitDate());
		extension.setContractCreatedTime(extensionVO.getContractCreatedTime());
		extension.setStatus(extensionVO.getStatus());
		extension.setRemark(extensionVO.getRemark());
		extension.setCreator(extensionVO.getCreator());
		extension.setCreatorId(extensionVO.getCreatorId());
		extension.setCreatedTime(extensionVO.getCreatedTime());
		extension.setModifier(extensionVO.getModifier());
		extension.setModifierId(extensionVO.getModifierId());
		extension.setModifiedTime(extensionVO.getModifiedTime());
		extension.setAuditTime(extensionVO.getAuditTime());
		extension.setExtensionTime(extensionVO.getExtensionTime());
		extension.setGrantAccountId(extensionVO.getGrantAccountId());
		extension.setRepayAccountId(extensionVO.getRepayAccountId());
		extension.setProductId(extensionVO.getProductId());
		extension.setLoanType(extensionVO.getLoanType());
		extension.setSalesDeptId(extensionVO.getSalesDeptId());
		extension.setSalesTeamId(extensionVO.getSalesTeamId());
		extension.setContractSrc(extensionVO.getContractSrc());
		extension.setAssessment(extensionVO.getAssessment());
		extension.setConsult(extensionVO.getConsult());
		extension.setbManage(extensionVO.getbManage());
		extension.setcManage(extensionVO.getcManage());
		extension.setRisk(extensionVO.getRisk());
		extension.setManagerId(extensionVO.getManagerId());
		extension.setProductType(extensionVO.getProductType());
		return extension;
	}
}
