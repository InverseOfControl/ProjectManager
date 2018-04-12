/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.audit.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.apply.model.Bank;
import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.model.Person;
import com.ezendai.credit2.apply.service.BankService;
import com.ezendai.credit2.apply.service.ProductService;
import com.ezendai.credit2.apply.vo.LoanVO;
import com.ezendai.credit2.audit.model.Contract;
import com.ezendai.credit2.audit.service.ContractService;
import com.ezendai.credit2.audit.service.StudentLoanContractService;
import com.ezendai.credit2.audit.vo.GenerateContractVO;
import com.ezendai.credit2.audit.vo.RepaymentPlanVO;
import com.ezendai.credit2.framework.util.DateUtil;
import com.ezendai.credit2.master.enumerate.EnumConstants;



/**   
*    
* 项目名称：credit2-main   
* 类名称：StudentLoanContractServiceImpl   
* 类描述：   
* 创建人：liboyan   
* 创建时间：2015年10月14日 上午9:38:08   
* 修改人：liboyan   
* 修改时间：2015年10月14日 上午9:38:08   
* 修改备注：   
* @version    
*    
*/
@Service
public class StudentLoanContractServiceImpl implements StudentLoanContractService{

	@Autowired
	private ContractService contractService;
	@Autowired
	private BankService bankService;
	@Autowired
	private ProductService productService;

	/** 城市名 */
	private final String cityName = "上海";
	/** 区域名 */
	private final String areaName = "浦东新";
	

	/* (non-Javadoc)
	 * @see com.ezendai.credit2.audit.service.StudentLoanContractService#createStudentLoanPersonLoanContract(com.ezendai.credit2.apply.model.Loan, com.ezendai.credit2.apply.model.Person, java.lang.String)
	 */
	@Override
	public void createStudentLoanPersonLoanContract(Loan loan, Person person, String contractNo) {
		Contract contract = new Contract();
		contract.setLoanId(loan.getId());
		contract.setContractNo(contractNo);
		contract.setType(EnumConstants.ContractType.STUDENT_PERSON_LOAN.getValue());
		contract.setSignDate(new Date());
		contract.setCityName(cityName);
		contract.setAreaName(areaName);
		contract.setContractName("个人借款咨询服务风险基金协议");
		contract.setPersonName(person.getName());
		contract.setIdNum(person.getIdnum());
		contract.setAddress(person.getAddress());
		contract.setEmail(person.getEmail());
		contractService.insertSelective(contract);
	}


	/**
	 * @param loan
	 * @param person
	 * @param repaymentPlanVO
	 * @param endRepayDate
	 * @param startRepayDate
	 * @param generateContractVO
	 * @param contractNo
	 * @see com.ezendai.credit2.audit.service.StudentLoanContractService#createStudentLoanLoanContract(com.ezendai.credit2.apply.model.Loan,
	 *      com.ezendai.credit2.apply.model.Person,
	 *      com.ezendai.credit2.audit.vo.RepaymentPlanVO, java.util.Date,
	 *      java.util.Date, com.ezendai.credit2.audit.vo.GenerateContractVO,
	 *      java.lang.String)
	 */
	@Override
	public void createStudentLoanContract(Loan loan, Person person, RepaymentPlanVO repaymentPlanVO, Date endRepayDate, Date startRepayDate, GenerateContractVO generateContractVO, String contractNo) {
		Contract contract = new Contract();
		contract.setLoanId(loan.getId());
		contract.setContractNo(contractNo);
		contract.setType(EnumConstants.ContractType.STUDENT_LAN.getValue());
		contract.setSignDate(new Date());
		contract.setCityName(cityName);
		contract.setAreaName(areaName);
		contract.setContractName("借款协议");
		contract.setPersonName(person.getName());
		contract.setIdNum(person.getIdnum());
		contract.setAddress(person.getAddress());
		contract.setZipCode(person.getZipCode());
		contract.setPurpose(loan.getPurpose());
		contract.setPactMoney(loan.getPactMoney());
		contract.setMonthInterestAmount(repaymentPlanVO.getAverageCapital());
		contract.setTimes(loan.getAuditTime());
		contract.setStartRepayDate(startRepayDate);
		contract.setEndRepayDate(endRepayDate);
		contract.setBankAccountName(person.getName());
		contract.setBankAccountNum(generateContractVO.getBankAccount());
		Bank bank = bankService.get(Long.valueOf(generateContractVO.getBank()));
		contract.setOpeningBank(bank.getBankName());
		contract.setBankBranchName(generateContractVO.getBankBranch());
		contract.setRepayDate(String.valueOf(DateUtil.getDayOfMonth(startRepayDate)));
		contractService.insertSelective(contract);
	}

	/**
	 * @param loan
	 * @param person
	 * @param repaymentPlanVO
	 * @param contractNo
	 * @param consult
	 * @param loanVO
	 * @param diffRefund
	 * @see com.ezendai.credit2.audit.service.StudentLoanContractService#createStudentLoanRepaymentContract(com.ezendai.credit2.apply.model.Loan,
	 *      com.ezendai.credit2.apply.model.Person,
	 *      com.ezendai.credit2.audit.vo.RepaymentPlanVO, java.lang.String,
	 *      java.math.BigDecimal, com.ezendai.credit2.apply.vo.LoanVO,
	 *      java.math.BigDecimal)
	 */
	@Override
	public void createStudentLoanRepaymentContract(Loan loan, BigDecimal diffRefund,Person person, RepaymentPlanVO repaymentPlanVO, String contractNo, BigDecimal referRate, LoanVO loanVO,GenerateContractVO generateContractVO) {
		Contract contract = new Contract();
		contract.setLoanId(loan.getId());
		contract.setContractNo(contractNo);
		contract.setType(EnumConstants.ContractType.STUDENT_REPAYMENT.getValue());
		contract.setSignDate(new Date());
		contract.setCityName(cityName);
		contract.setAreaName(areaName);
		contract.setContractName("个人借款咨询服务协议");
		contract.setPersonName(person.getName());
		contract.setIdNum(person.getIdnum());
		contract.setAddress(person.getAddress());
		contract.setPactMoney(loan.getAuditMoney());
		contract.setEmail(person.getEmail());
		contract.setDiffRefund(diffRefund);
		contract.setMonthInterestAmount(repaymentPlanVO.getRepayAmount());	
		contract.setBankAccountName(person.getName());
		contract.setBankAccountNum(generateContractVO.getBankAccount());
		Bank bank = bankService.get(Long.valueOf(generateContractVO.getBank()));
		contract.setOpeningBank(bank.getBankName());
		contract.setBankBranchName(generateContractVO.getBankBranch());
		contractService.insertSelective(contract);
	}

	/**
	 * @param loan
	 * @param person
	 * @param generateContractVO
	 * @param contractNo
	 * @see com.ezendai.credit2.audit.service.StudentLoanContractService#createStudentLoanEntrustContract(com.ezendai.credit2.apply.model.Loan,
	 *      com.ezendai.credit2.apply.model.Person,
	 *      com.ezendai.credit2.audit.vo.GenerateContractVO, java.lang.String)
	 */
	@Override
	public void createStudentLoanEntrustContract(Loan loan, Person person, GenerateContractVO generateContractVO, String contractNo) {
		Contract contract = new Contract();
		contract.setLoanId(loan.getId());
		contract.setContractNo(contractNo);
		contract.setType(EnumConstants.ContractType.STUDENT_ENTRUST_GUARANTEE.getValue());
		contract.setSignDate(new Date());
		contract.setContractName("委托扣款授权书");
		contract.setPersonName(person.getName());
		contract.setIdNum(person.getIdnum());
		contract.setBankAccountName(person.getName());
		contract.setBankAccountNum(generateContractVO.getBankAccount());
		Bank bank = bankService.get(Long.valueOf(generateContractVO.getBank()));
		contract.setOpeningBank(bank.getBankName());
		contract.setBankBranchName(generateContractVO.getBankBranch());
		contract.setContact(person.getMobilePhone());
		contractService.insertSelective(contract);
	}

	/**
	 * @param loan
	 * @param person
	 * @param repaymentPlanVO
	 * @param contractNo
	 * @param startRepayDate
	 * @see com.ezendai.credit2.audit.service.StudentLoanContractService#createStudentLoanRepaymentFundContract(com.ezendai.credit2.apply.model.Loan,
	 *      com.ezendai.credit2.apply.model.Person,
	 *      com.ezendai.credit2.audit.vo.RepaymentPlanVO, java.lang.String,
	 *      java.util.Date)
	 */
	@Override
	public void createStudentLoanRepaymentFundContract(Loan loan, Person person, RepaymentPlanVO repaymentPlanVO, String contractNo, Date startRepayDate) {
		Contract contract = new Contract();
		contract.setLoanId(loan.getId());
		contract.setContractNo(contractNo);
		contract.setSignDate(new Date());
		contract.setType(EnumConstants.ContractType.STUDENT_REPAYMENT_FUND.getValue());
		contract.setContractName("还款提醒函 ");
		contract.setPersonName(person.getName());
		contract.setMonthAmount(repaymentPlanVO.getRepayAmount());
		contract.setMonthInterestAmount(repaymentPlanVO.getAverageCapital());
		contract.setTimes(loan.getAuditTime());
		contract.setPactMoney(loan.getAuditMoney());
		contract.setRepayDate(String.valueOf(DateUtil.getDayOfMonth(startRepayDate)));
		contractService.insertSelective(contract);
	}
}
