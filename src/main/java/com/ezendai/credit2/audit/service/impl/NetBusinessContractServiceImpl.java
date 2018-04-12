/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.audit.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezendai.credit2.apply.model.Bank;
import com.ezendai.credit2.apply.model.BankAccount;
import com.ezendai.credit2.apply.model.Guarantee;
import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.model.Person;
import com.ezendai.credit2.apply.service.BankService;
import com.ezendai.credit2.apply.service.ProductService;
import com.ezendai.credit2.apply.vo.LoanVO;
import com.ezendai.credit2.audit.model.Contract;
import com.ezendai.credit2.audit.service.ContractService;
import com.ezendai.credit2.audit.service.NetBusinessContractService;
import com.ezendai.credit2.audit.vo.GenerateContractVO;
import com.ezendai.credit2.audit.vo.RepaymentPlanVO;
import com.ezendai.credit2.framework.util.DateUtil;
import com.ezendai.credit2.master.enumerate.EnumConstants;


/**   
*    
* 项目名称：credit2-main   
* 类名称：NetBusinessContractServiceImpl   
* 类描述：用来生成网商贷合同   
* 创建人：Administrator   
* 创建时间：2015年8月18日 下午4:48:29   
* 修改人：Administrator   
* 修改时间：2015年8月18日 下午4:48:29   
* 修改备注：   
* @version    
*    
*/
@Service
public class NetBusinessContractServiceImpl implements NetBusinessContractService{

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

	/**
	 * @param loan
	 * @param person
	 * @param contractNo
	 * @see com.ezendai.credit2.audit.service.NetBusinessContractService#createNetBusinessPersonLoanContract(com.ezendai.credit2.apply.model.Loan,
	 *      com.ezendai.credit2.apply.model.Person, java.lang.String)
	 */
	@Override
	public void createNetBusinessPersonLoanContract(Loan loan, Person person, String contractNo) {
		Contract contract = new Contract();
		contract.setLoanId(loan.getId());
		contract.setContractNo(contractNo);
		contract.setType(EnumConstants.ContractType.CITY_WIDE_PERSON_LOAN.getValue());
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
	 * @see com.ezendai.credit2.audit.service.NetBusinessContractService#createNetBusinessLoanContract(com.ezendai.credit2.apply.model.Loan,
	 *      com.ezendai.credit2.apply.model.Person,
	 *      com.ezendai.credit2.audit.vo.RepaymentPlanVO, java.util.Date,
	 *      java.util.Date, com.ezendai.credit2.audit.vo.GenerateContractVO,
	 *      java.lang.String)
	 */
	@Override
	public void createNetBusinessLoanContract(Loan loan, Person person, RepaymentPlanVO repaymentPlanVO, Date endRepayDate, Date startRepayDate, GenerateContractVO generateContractVO, String contractNo) {
		Contract contract = new Contract();
		contract.setLoanId(loan.getId());
		contract.setContractNo(contractNo);
		contract.setType(EnumConstants.ContractType.CITY_WIDE_LOAN.getValue());
		contract.setSignDate(new Date());
		contract.setCityName(cityName);
		contract.setAreaName(areaName);
		contract.setContractName("借款协议");
		contract.setPersonName(person.getName());
		contract.setIdNum(person.getIdnum());
		contract.setAddress(person.getAddress());
		contract.setZipCode(person.getZipCode());
		contract.setPurpose(loan.getPurpose());
		contract.setPactMoney(loan.getAuditMoney());
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
	 * @see com.ezendai.credit2.audit.service.NetBusinessContractService#createNetBusinessRepaymentContract(com.ezendai.credit2.apply.model.Loan,
	 *      com.ezendai.credit2.apply.model.Person,
	 *      com.ezendai.credit2.audit.vo.RepaymentPlanVO, java.lang.String,
	 *      java.math.BigDecimal, com.ezendai.credit2.apply.vo.LoanVO,
	 *      java.math.BigDecimal)
	 */
	@Override
	public void createNetBusinessRepaymentContract(Loan loan, Person person, RepaymentPlanVO repaymentPlanVO, String contractNo, BigDecimal referRate, LoanVO loanVO,GenerateContractVO generateContractVO) {
		Contract contract = new Contract();
		contract.setLoanId(loan.getId());
		contract.setContractNo(contractNo);
		contract.setType(EnumConstants.ContractType.CITY_WIDE_REPAYMENT.getValue());
		contract.setSignDate(new Date());
		contract.setCityName(cityName);
		contract.setAreaName(areaName);
		contract.setContractName("个人借款咨询服务协议");
		contract.setPersonName(person.getName());
		contract.setIdNum(person.getIdnum());
		contract.setAddress(person.getAddress());
		contract.setPactMoney(loan.getAuditMoney());
		contract.setEmail(person.getEmail());
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
	 * @see com.ezendai.credit2.audit.service.NetBusinessContractService#createNetBusinessEntrustContract(com.ezendai.credit2.apply.model.Loan,
	 *      com.ezendai.credit2.apply.model.Person,
	 *      com.ezendai.credit2.audit.vo.GenerateContractVO, java.lang.String)
	 */
	@Override
	public void createNetBusinessEntrustContract(Loan loan, Person person, GenerateContractVO generateContractVO, String contractNo) {
		Contract contract = new Contract();
		contract.setLoanId(loan.getId());
		contract.setContractNo(contractNo);
		contract.setType(EnumConstants.ContractType.CITY_WIDE_ENTRUST.getValue());
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
	 * @see com.ezendai.credit2.audit.service.NetBusinessContractService#createNetBusinessRepaymentFundContract(com.ezendai.credit2.apply.model.Loan,
	 *      com.ezendai.credit2.apply.model.Person,
	 *      com.ezendai.credit2.audit.vo.RepaymentPlanVO, java.lang.String,
	 *      java.util.Date)
	 */
	@Override
	public void createNetBusinessRepaymentFundContract(Loan loan, Person person, RepaymentPlanVO repaymentPlanVO, String contractNo, Date startRepayDate) {
		Contract contract = new Contract();
		contract.setLoanId(loan.getId());
		contract.setContractNo(contractNo);
		contract.setSignDate(new Date());
		contract.setType(EnumConstants.ContractType.CITY_WIDE_REPAYMENT_FUND.getValue());
		contract.setContractName("还款提醒函 ");
		contract.setPersonName(person.getName());
		contract.setMonthAmount(repaymentPlanVO.getRepayAmount());
		contract.setMonthInterestAmount(repaymentPlanVO.getAverageCapital());
		contract.setTimes(loan.getAuditTime());
		contract.setPactMoney(loan.getAuditMoney());
		contract.setRepayDate(String.valueOf(DateUtil.getDayOfMonth(startRepayDate)));
		contractService.insertSelective(contract);
	}

	/**
	 * @param loan
	 * @param person
	 * @param endRepayDate
	 * @param startRepayDate
	 * @param contractNo
	 * @param guarantee
	 * @see com.ezendai.credit2.audit.service.NetBusinessContractService#createNetBusinessNaturalGuaranteeContract(com.ezendai.credit2.apply.model.Loan,
	 *      com.ezendai.credit2.apply.model.Person, java.util.Date,
	 *      java.util.Date, java.lang.String,
	 *      com.ezendai.credit2.apply.model.Guarantee)
	 */
	@Override
	public void createNetBusinessNaturalGuaranteeContract(Loan loan, Person person, Date endRepayDate, Date startRepayDate, String contractNo, Guarantee guarantee) {
		Contract contract = new Contract();
		contract.setLoanId(loan.getId());
		contract.setContractNo(contractNo);
		contract.setType(EnumConstants.ContractType.CITY_WIDE_NATURAL_GUARANTEE.getValue());
		contract.setSignDate(new Date());
		contract.setContractName("担保合同书（自然人担保）");
		contract.setPersonName(person.getName());
		contract.setIdNum(person.getIdnum());
		StringBuffer loanAgreeNum = new StringBuffer();
		loanAgreeNum.append(contractNo).append("-005");
		contract.setLoanAgreeNum(loanAgreeNum.toString());
		contract.setStartRepayDate(startRepayDate);
		contract.setEndRepayDate(endRepayDate);
		contract.setPayAmount(loan.getAuditMoney());
		contract.setGuaranteeName(guarantee.getName());
		contract.setGuaranteeIdNum(guarantee.getIdnum());
		contractService.insertSelective(contract);
	}

	/**
	 * @param loan
	 * @param person
	 * @param guarantBankAccount
	 * @param contractNo
	 * @param guarantee
	 * @see com.ezendai.credit2.audit.service.NetBusinessContractService#createNetBusinessEntrustGuaranteeContract(com.ezendai.credit2.apply.model.Loan,
	 *      com.ezendai.credit2.apply.model.Person,
	 *      com.ezendai.credit2.apply.model.BankAccount, java.lang.String,
	 *      com.ezendai.credit2.apply.model.Guarantee)
	 */
	@Override
	public void createNetBusinessEntrustGuaranteeContract(Loan loan, Person person, BankAccount generateContractVO, String contractNo, Guarantee guarantee) {
		Contract contract = new Contract();
		contract.setLoanId(loan.getId());
		contract.setContractNo(contractNo);
		contract.setSignDate(new Date());
		contract.setType(EnumConstants.ContractType.CITY_WIDE_ENTRUST_GUARANTEE.getValue());
		contract.setContractName("委托扣款授权书（担保人）");
		contract.setPersonName(person.getName());
		contract.setIdNum(person.getIdnum());
		contract.setGuaranteeName(guarantee.getName());
		contract.setGuaranteeIdNum(guarantee.getIdnum());
		contract.setBankAccountName(guarantee.getName());
		contract.setBankAccountNum(generateContractVO.getAccount());
		contract.setOpeningBank(generateContractVO.getBankName());
		contract.setBankBranchName(generateContractVO.getBranchName());
		contract.setContact(guarantee.getMobilePhone());
		contractService.insertSelective(contract);
	}

	/**
	 *
	 * 
	 * @param loan
	 * @param person
	 * @param startRepayDate
	 * @param contractNo
	 * @param guaranteeName
	 */
	@Transactional
	public void createNetBusinessNaturalLegalContract(Loan loan, Person person, Date endRepayDate, Date startRepayDate, String contractNo, String guaranteeName) {
		Contract contract = new Contract();
		contract.setLoanId(loan.getId());
		contract.setContractNo(contractNo);
		contract.setType(EnumConstants.ContractType.CITY_WIDE_NATURAL_LEGAL.getValue());
		contract.setSignDate(new Date());
		contract.setContractName("担保合同书（法人担保）");
		contract.setPersonName(person.getName());
		contract.setIdNum(person.getIdnum());
		StringBuffer loanAgreeNum = new StringBuffer();
		loanAgreeNum.append(contractNo).append("-007");
		contract.setLoanAgreeNum(loanAgreeNum.toString());
		contract.setStartRepayDate(startRepayDate);
		contract.setEndRepayDate(endRepayDate);
		contract.setPactMoney(loan.getAuditMoney());
		contract.setLegalGuarantee(guaranteeName);
		contractService.insertSelective(contract);
	}
}
