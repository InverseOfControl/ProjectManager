package com.ezendai.credit2.audit.assembler;

import com.ezendai.credit2.audit.model.Contract;
import com.ezendai.credit2.audit.vo.ContractVO;

/**
 * 
 * <pre>
 * 合同 VO/Model类型转换
 * </pre>
 *
 * @author zhangshihai
 * @version $Id: ContractAssembler.java, v 0.1 2014年7月31日 下午3:57:28 zhangshihai Exp $
 */
public class ContractAssembler {

	/**
	 * 
	 * <pre>
	 * Model转VO
	 * </pre>
	 *
	 * @param contract
	 * @return
	 */
	public static ContractVO transferModel2VO (Contract contract) {
		if (contract == null) {
			return null;
		}
		
		ContractVO contractVO = new ContractVO();
		contractVO.setId(contract.getId());
		contractVO.setLoanId(contract.getLoanId());
		contractVO.setContractNo(contract.getContractNo());
		contractVO.setSignDate(contract.getSignDate());
		contractVO.setContractName(contract.getContractName());
		contractVO.setType(contract.getType());
		contractVO.setAreaName(contract.getAreaName());
		contractVO.setCityName(contract.getCityName());
		contractVO.setPersonName(contract.getPersonName());
		contractVO.setIdNum(contract.getIdNum());
		contractVO.setAddress(contract.getAddress());
		contractVO.setZipCode(contract.getZipCode());
		contractVO.setEmail(contract.getEmail());
		contractVO.setPayAmount(contract.getPayAmount());
		contractVO.setPurpose(contract.getPurpose());
		contractVO.setPactMoney(contract.getPactMoney());
		contractVO.setMonthInterestAmount(contract.getMonthInterestAmount());
		contractVO.setTimes(contract.getTimes());
		contractVO.setStartRepayDate(contract.getStartRepayDate());
		contractVO.setEndRepayDate(contract.getEndRepayDate());
		contractVO.setBankAccountName(contract.getBankAccountName());
		contractVO.setBankAccountNum(contract.getBankAccountNum());
		contractVO.setOpeningBank(contract.getOpeningBank());
		contractVO.setAssessmentFees(contract.getAssessmentFees());
		contractVO.setManageFees(contract.getManageFees());
		contractVO.setTtpManageFees(contract.getTtpManageFees());
		contractVO.setContact(contract.getContact());
		contractVO.setMonthAmount(contract.getMonthAmount());
		contractVO.setGuaranteeName(contract.getGuaranteeName());
		contractVO.setGuaranteeIdNum(contract.getGuaranteeIdNum());
		contractVO.setLegalGuarantee(contract.getLegalGuarantee());
		contractVO.setBusinessCompanyName(contract.getBusinessCompanyName());
		contractVO.setPreparatoryAmount(contract.getPreparatoryAmount());
		contractVO.setTell(contract.getTell());
		contractVO.setTotalAmount(contract.getTotalAmount());
		contractVO.setPrincipalAmount(contract.getPrincipalAmount());
		contractVO.setRaskAmount(contract.getRaskAmount());
		contractVO.setPlateNumber(contract.getPlateNumber());
		contractVO.setFrameNumber(contract.getFrameNumber());
		contractVO.setLender(contract.getLender());
		contractVO.setBankBranchName(contract.getBankBranchName());
		contractVO.setIsMarried(contract.getIsMarried());
		contractVO.setBusinessAddress(contract.getBusinessAddress());
		contractVO.setReferRate(contract.getReferRate());
		contractVO.setRepayDate(contract.getRepayDate());
		contractVO.setLoanAgreeNum(contract.getLoanAgreeNum());
		contractVO.setTerritory(contract.getTerritory());
		contractVO.setBrand(contract.getBrand());
		contractVO.setCreator(contract.getCreator());
		contractVO.setCreatorId(contract.getCreatorId());
		contractVO.setCreatedTime(contract.getCreatedTime());
		contractVO.setModifier(contract.getModifier());
		contractVO.setModifierId(contract.getModifierId());
		contractVO.setModifiedTime(contract.getModifiedTime());
		contractVO.setVersion(contract.getVersion());
		contractVO.setAuditMoney(contract.getAuditMoney());
		
		return contractVO;
	} 
	
	/**
	 * 
	 * <pre>
	 * VO转Model
	 * </pre>
	 *
	 * @param contractVO
	 * @return
	 */
	public static Contract transferVO2Model (ContractVO contractVO) {
		if (contractVO == null) {
			return null;
		}
		
		Contract contract = new Contract();
		contract.setId(contractVO.getId());
		contract.setLoanId(contractVO.getLoanId());
		contract.setContractNo(contractVO.getContractNo());
		contract.setSignDate(contractVO.getSignDate());
		contract.setContractName(contractVO.getContractName());
		contract.setType(contractVO.getType());
		contract.setAreaName(contractVO.getAreaName());
		contract.setCityName(contractVO.getCityName());
		contract.setPersonName(contractVO.getPersonName());
		contract.setIdNum(contractVO.getIdNum());
		contract.setAddress(contractVO.getAddress());
		contract.setZipCode(contractVO.getZipCode());
		contract.setEmail(contractVO.getEmail());
		contract.setPayAmount(contractVO.getPayAmount());
		contract.setPurpose(contractVO.getPurpose());
		contract.setPactMoney(contractVO.getPactMoney());
		contract.setMonthInterestAmount(contractVO.getMonthInterestAmount());
		contract.setTimes(contractVO.getTimes());
		contract.setStartRepayDate(contractVO.getStartRepayDate());
		contract.setEndRepayDate(contractVO.getEndRepayDate());
		contract.setBankAccountName(contractVO.getBankAccountName());
		contract.setBankAccountNum(contractVO.getBankAccountNum());
		contract.setOpeningBank(contractVO.getOpeningBank());
		contract.setAssessmentFees(contractVO.getAssessmentFees());
		contract.setManageFees(contractVO.getManageFees());
		contract.setTtpManageFees(contractVO.getTtpManageFees());
		contract.setContact(contractVO.getContact());
		contract.setMonthAmount(contractVO.getMonthAmount());
		contract.setGuaranteeName(contractVO.getGuaranteeName());
		contract.setGuaranteeIdNum(contractVO.getGuaranteeIdNum());
		contract.setLegalGuarantee(contractVO.getLegalGuarantee());
		contract.setBusinessCompanyName(contractVO.getBusinessCompanyName());
		contract.setPreparatoryAmount(contractVO.getPreparatoryAmount());
		contract.setTell(contractVO.getTell());
		contract.setTotalAmount(contractVO.getTotalAmount());
		contract.setPrincipalAmount(contractVO.getPrincipalAmount());
		contract.setRaskAmount(contractVO.getRaskAmount());
		contract.setPlateNumber(contractVO.getPlateNumber());
		contract.setFrameNumber(contractVO.getFrameNumber());
		contract.setLender(contractVO.getLender());
		contract.setBankBranchName(contractVO.getBankBranchName());
		contract.setIsMarried(contractVO.getIsMarried());
		contract.setBusinessAddress(contractVO.getBusinessAddress());
		contract.setReferRate(contractVO.getReferRate());
		contract.setRepayDate(contractVO.getRepayDate());
		contract.setLoanAgreeNum(contractVO.getLoanAgreeNum());
		contract.setTerritory(contractVO.getTerritory());
		contract.setBrand(contractVO.getBrand());
		contract.setCreator(contractVO.getCreator());
		contract.setCreatorId(contractVO.getCreatorId());
		contract.setCreatedTime(contractVO.getCreatedTime());
		contract.setModifier(contractVO.getModifier());
		contract.setModifierId(contractVO.getModifierId());
		contract.setModifiedTime(contractVO.getModifiedTime());
		contract.setVersion(contractVO.getVersion());
		contract.setAuditMoney(contractVO.getAuditMoney());
		return contract;
	}
}
