/**
 * 
 */
package com.ezendai.credit2.apply.assembler;

import java.math.BigDecimal;

import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.vo.LoanVO;

/***
 * LoanAssembler
 *
 * @author liyuepeng
 * 2014-7-24 下午06:05:06 
 *
 */
public class LoanAssembler {

	/**
	 * <pre>
	 * Model对象转换成Vo对象
	 * </pre>
	 *
	 * @param vo
	 * @return
	 */
	public static LoanVO transferModel2VO(Loan loan) {
		if (loan == null) {
			return null;
		}

		LoanVO loanVo = new LoanVO();
		loanVo.setAssessorId(loan.getAssessorId());
		loanVo.setAuditDate(loan.getAuditDate());
		loanVo.setAuditMoney(loan.getAuditMoney());
		loanVo.setAuditTime(loan.getAuditTime());
		loanVo.setContractNo(loan.getContractNo());
		loanVo.setCrmId(loan.getCrmId());
		loanVo.setBizDirectorId(loan.getBizDirectorId());
		loanVo.setCurrNum(loan.getCurrNum());
		loanVo.setCustomerSource(loan.getCustomerSource());
		loanVo.setEndRepayDate(loan.getEndRepayDate());
		loanVo.setGrantAccountId(loan.getGrantAccountId());
		loanVo.setGrantDate(loan.getGrantDate());
		loanVo.setGrantMoney(loan.getGrantMoney());
		loanVo.setGuaranteeName(loan.getGuaranteeName());
		loanVo.setId(loan.getId());
		loanVo.setLoanType(loan.getLoanType());
		loanVo.setMonthRate(loan.getMonthRate());
		loanVo.setPactMoney(loan.getPactMoney());
		loanVo.setPersonId(loan.getPersonId());
		if (loan.getPerson() != null) {
			loanVo.setPersonIdnum(loan.getPerson().getIdnum());
			loanVo.setPersonName(loan.getPerson().getName());
		}
		loanVo.setProductId(loan.getProductId());
		if (loan.getProduct() != null) {
			loanVo.setProductCode(loan.getProduct().getProductCode());
			loanVo.setProductName(loan.getProduct().getProductName());
		}
		loanVo.setPurpose(loan.getPurpose());
		loanVo.setRemark(loan.getRemark());
		loanVo.setRepayAccountId(loan.getRepayAccountId());
		loanVo.setRequestDate(loan.getRequestDate());
		loanVo.setRequestMoney(loan.getRequestMoney());
		loanVo.setRequestTime(loan.getRequestTime());
		loanVo.setResidualPactMoney(loan.getResidualPactMoney());
		loanVo.setReturnDate(loan.getReturnDate());
		loanVo.setRisk(loan.getRisk());
		loanVo.setSalesDeptId(loan.getSalesDeptId());
		if (loan.getSalesDept() != null) {
			loanVo.setSalesDeptCode(loan.getSalesDept().getCode());
			loanVo.setSalesDeptName(loan.getSalesDept().getName());
		}
		loanVo.setSalesTeamId(loan.getSalesTeamId());
		loanVo.setServiceId(loan.getServiceId());
		loanVo.setSignDate(loan.getSignDate());
		loanVo.setStartRepayDate(loan.getStartRepayDate());
		loanVo.setStatus(loan.getStatus());
		loanVo.setTime(loan.getTime());
		if (loan.getUser() != null) {
			loanVo.setUserId(loan.getUser().getId());
		}
		loanVo.setVehicleId(loan.getVehicleId());
		loanVo.setCreator(loan.getCreator());
		loanVo.setCreatorId(loan.getCreatorId());
		loanVo.setCreatedTime(loan.getCreatedTime());
		loanVo.setModifier(loan.getModifier());
		loanVo.setModifierId(loan.getModifierId());
		loanVo.setModifiedTime(loan.getModifiedTime());
		loanVo.setVersion(loan.getVersion());
		loanVo.setId(loan.getId());
		loanVo.setSubmitDate(loan.getSubmitDate());
		loanVo.setHasHouse(loan.getHasHouse());
		loanVo.setContractSrc(loan.getContractSrc());
		loanVo.setRepaymentMethod(loan.getRepaymentMethod());
		loanVo.setResidualTime(loan.getResidualTime());
		loanVo.setManagerId(loan.getManagerId());
		loanVo.setProductType(loan.getProductType());
		loanVo.setFlag(loan.getFlag());
		loanVo.setThirdFee(loan.getThirdFee());
		loanVo.setSourceOfRepay(loan.getSourceOfRepay());
		loanVo.setAuditMemberType(loan.getAuditMemberType());
		loanVo.setOrganID(loan.getOrganID());
		loanVo.setSchemeID(loan.getSchemeID());
		loanVo.setOrganCode(loan.getOrganCode());
		loanVo.setServiceCharges(loan.getServiceCharges());

		return loanVo;
	}
	
	/**
	 * 
	 * <pre>
	 * VO转Model类型
	 * </pre>
	 *
	 * @param loanVO
	 * @return
	 */
	public static Loan transferVO2Model (LoanVO loanVO) {
		if (loanVO == null) {
			return null;
		}
		
		Loan loan = new Loan();
		loan.setId(loanVO.getId());
		loan.setPersonId(loanVO.getPersonId());
		loan.setVehicleId(loanVO.getVehicleId());
		loan.setProductId(loanVO.getProductId());
		loan.setRisk(loanVO.getRisk());
		loan.setPactMoney(loanVO.getPactMoney());
		loan.setTime(loanVO.getTime());
		loan.setRequestTime(loanVO.getRequestTime());
		loan.setSalesTeamId(loan.getSalesTeamId());
		loan.setResidualPactMoney(loanVO.getResidualPactMoney());
		loan.setMonthRate(loanVO.getMonthRate());
		loan.setGrantAccountId(loanVO.getGrantAccountId());
		loan.setLoanType(loanVO.getLoanType());
		loan.setRepayAccountId(loanVO.getRepayAccountId());
		loan.setCurrNum(loanVO.getCurrNum());
		loan.setGrantMoney(loanVO.getGrantMoney());
		loan.setRequestDate(loanVO.getRequestDate());
		loan.setSignDate(loanVO.getSignDate());
		loan.setAuditDate(loanVO.getAuditDate());
		loan.setAuditMoney(loanVO.getAuditMoney());
		loan.setRequestMoney(loanVO.getRequestMoney());
		loan.setReturnDate(loanVO.getReturnDate());
		loan.setStartRepayDate(loanVO.getStartRepayDate());
		loan.setEndRepayDate(loanVO.getEndRepayDate());
		loan.setGrantDate(loanVO.getGrantDate());
		loan.setCustomerSource(loanVO.getCustomerSource());
		loan.setServiceId(loanVO.getServiceId());
		loan.setCrmId(loanVO.getCrmId());
		loan.setBizDirectorId(loanVO.getBizDirectorId());
		loan.setAssessorId(loanVO.getAssessorId());
		loan.setSalesDeptId(loanVO.getSalesDeptId());
		loan.setPurpose(loanVO.getPurpose());
		loan.setStatus(loanVO.getStatus());
		loan.setRemark(loanVO.getRemark());
		loan.setAuditTime(loanVO.getAuditTime());
		loan.setGuaranteeName(loanVO.getGuaranteeName());
		loan.setContractNo(loanVO.getContractNo());
		loan.setCreator(loanVO.getCreator());
		loan.setCreatorId(loanVO.getCreatorId());
		loan.setCreatedTime(loanVO.getCreatedTime());
		loan.setModifier(loanVO.getModifier());
		loan.setModifierId(loanVO.getModifierId());
		loan.setModifiedTime(loanVO.getModifiedTime());
		loan.setVersion(loanVO.getVersion());
		loan.setSubmitDate(loanVO.getSubmitDate());
		loan.setHasHouse(loanVO.getHasHouse());
		loan.setContractSrc(loanVO.getContractSrc());
		loan.setRepaymentMethod(loanVO.getRepaymentMethod());
		loan.setResidualTime(loanVO.getResidualTime());
		loan.setManagerId(loanVO.getManagerId());
		loan.setProductType(loanVO.getProductType());
		loan.setFlag(loanVO.getFlag());
		loan.setThirdFee(loanVO.getThirdFee());
		loan.setSourceOfRepay(loanVO.getSourceOfRepay());
		loan.setAuditMemberType(loanVO.getAuditMemberType());
		loan.setOrganID(loanVO.getOrganID());
		loan.setSchemeID(loanVO.getSchemeID());
		loan.setOrganCode(loanVO.getOrganCode());
		loan.setServiceCharges(loanVO.getServiceCharges());
		return loan;
	}
}
