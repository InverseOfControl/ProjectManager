package com.ezendai.credit2.audit.service;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.transaction.annotation.Transactional;

import com.ezendai.credit2.apply.model.BankAccount;
import com.ezendai.credit2.apply.model.Guarantee;
import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.model.Person;
import com.ezendai.credit2.apply.vo.LoanVO;
import com.ezendai.credit2.audit.vo.GenerateContractVO;
import com.ezendai.credit2.audit.vo.RepaymentPlanVO;

/**
 * 
 * <pre>
 * 用来生成同城小微贷和同城POS合同
 * </pre>
 *
 * @author 00226557
 * @version $Id: CarExtensionService.java, v 0.1 2015年3月25日 下午1:30:58 00226557 Exp $
 */

public interface CityWideContractService {
	/**
	 * 同城POS贷小微贷-个人借款咨询服务风险基金协议合同
	 * 
	 * @param loan
	 * @param person
	 * @param contractNo
	 * @return
	 */
	@Transactional
	public void createCityWidePersonLoanContract(Loan loan, Person person, String contractNo);
	
	/**
	 * 同城POS贷小微贷-借款协议合同
	 * 
	 * @param loan
	 * @param person
	 * @param repaymentPlanVO
	 * @param endRepayDate
	 * @param startRepayDate
	 * @param personBankAccount 
	 * @param contractNo
	 */
	@Transactional
	public void createCityWideLoanContract(Loan loan, Person person,
											RepaymentPlanVO repaymentPlanVO, Date endRepayDate,
											Date startRepayDate,
											GenerateContractVO generateContractVO, String contractNo);
	/**
	 * 同城POS贷小微贷-个人借款咨询服务协议合同
	 * 
	 * @param loan
	 * @param person
	 * @param repaymentPlanVO
	 * @param contractNo
	 * @param referRate
	 */
	@Transactional
	public void createCityWideRepaymentContract(Loan loan, Person person,
												RepaymentPlanVO repaymentPlanVO, String contractNo,
												BigDecimal consult, LoanVO loanVO,
												BigDecimal diffRefund,	GenerateContractVO generateContractVO);
	/**
	 * 同城POS贷小微贷-委托扣款授权书合同
	 * 
	 * @param loan
	 * @param person
	 * @param personBankAccount
	 * @param contractNo
	 */
	@Transactional
	public void createCityWideEntrustContract(Loan loan, Person person,
												GenerateContractVO generateContractVO,
												String contractNo);
	/**
	 * 同城POS贷小微贷-还款提醒函 合同
	 * 
	 * @param loan
	 * @param person
	 * @param repaymentPlanVO
	 * @param contractNo
	 * @param startRepayDate
	 * 
	 */
	@Transactional
	public void createCityWideRepaymentFundContract(Loan loan, Person person,
													RepaymentPlanVO repaymentPlanVO,
													String contractNo, Date startRepayDate);
	/**
	 * 同城POS贷小微贷-担保合同书（自然人担保）
	 * 
	 * @param loan
	 * @param person
	 * @param endRepayDate
	 * @param startRepayDate
	 * @param contractNo
	 * @param guarantee
	 */
	@Transactional
	public void createCityWideNaturalGuaranteeContract(Loan loan, Person person, Date endRepayDate,
														Date startRepayDate, String contractNo,
														Guarantee guarantee);
	/**
	 * 同城POS贷小微贷-委托扣款授权书合同(担保人)
	 * 
	 * @param loan
	 * @param person
	 * @param guarantBankAccount
	 * @param contractNo
	 * @param guarantee
	 */
	@Transactional
	public void createCityWideEntrustGuaranteeContract(Loan loan, Person person,
														BankAccount guarantBankAccount,
														String contractNo, Guarantee guarantee);

	/**
	 * 同城POS贷小微贷-担保合同书（法人担保）
	 * 
	 * @param loan
	 * @param person
	 * @param startRepayDate
	 * @param contractNo
	 * @param guaranteeName
	 */
	@Transactional
	public void createCityWideNaturalLegalContract(Loan loan, Person person, Date endRepayDate,
													Date startRepayDate, String contractNo,
													String guaranteeName);
}
