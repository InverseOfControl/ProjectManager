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
* 项目名称：credit2-main   
* 类名称：ContractService   
* 类描述：用来生成网商贷合同
* 创建人：Administrator   
* 创建时间：2015年8月18日 下午5:53:36   
* 修改人：Administrator   
* 修改时间：2015年8月18日 下午5:53:36   
* 修改备注：   
* @version    
*    
*/
public interface NetBusinessContractService {
	/**
	 * 网商贷-个人借款咨询服务风险基金协议合同
	 * 
	 * @param loan
	 * @param person
	 * @param contractNo
	 * @return
	 */
	@Transactional
	public void createNetBusinessPersonLoanContract(Loan loan, Person person, String contractNo);
	
	/**
	 * 网商贷-借款协议合同
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
	public void createNetBusinessLoanContract(Loan loan, Person person,
											RepaymentPlanVO repaymentPlanVO, Date endRepayDate,
											Date startRepayDate,
											GenerateContractVO generateContractVO, String contractNo);
	/**
	 * 网商贷-个人借款咨询服务协议合同
	 * 
	 * @param loan
	 * @param person
	 * @param repaymentPlanVO
	 * @param contractNo
	 * 
	 */
	@Transactional
	public void createNetBusinessRepaymentContract(Loan loan, Person person,
												RepaymentPlanVO repaymentPlanVO, String contractNo,
												BigDecimal consult, LoanVO loanVO,GenerateContractVO generateContractVO);
	/**
	 * 网商贷-委托扣款授权书合同
	 * 
	 * @param loan
	 * @param person
	 * @param personBankAccount
	 * @param contractNo
	 */
	@Transactional
	public void createNetBusinessEntrustContract(Loan loan, Person person,
												GenerateContractVO generateContractVO,
												String contractNo);
	/**
	 * 网商贷-还款提醒函 合同
	 * 
	 * @param loan
	 * @param person
	 * @param repaymentPlanVO
	 * @param contractNo
	 * @param startRepayDate
	 * 
	 */
	@Transactional
	public void createNetBusinessRepaymentFundContract(Loan loan, Person person,
													RepaymentPlanVO repaymentPlanVO,
													String contractNo, Date startRepayDate);
	/**
	 * 网商贷-担保合同书（自然人担保）
	 * 
	 * @param loan
	 * @param person
	 * @param endRepayDate
	 * @param startRepayDate
	 * @param contractNo
	 * @param guarantee
	 */
	@Transactional
	public void createNetBusinessNaturalGuaranteeContract(Loan loan, Person person, Date endRepayDate,
														Date startRepayDate, String contractNo,
														Guarantee guarantee);
	/**
	 * 网商贷-委托扣款授权书合同(担保人)
	 * 
	 * @param loan
	 * @param person
	 * @param guarantBankAccount
	 * @param contractNo
	 * @param guarantee
	 */
	@Transactional
	public void createNetBusinessEntrustGuaranteeContract(Loan loan, Person person,
														BankAccount guarantBankAccount,
														String contractNo, Guarantee guarantee);

	/**
	 * 网商贷-担保合同书（法人担保）
	 * 
	 * @param loan
	 * @param person
	 * @param startRepayDate
	 * @param contractNo
	 * @param guaranteeName
	 */
	@Transactional
	public void createNetBusinessNaturalLegalContract(Loan loan, Person person, Date endRepayDate,
													Date startRepayDate, String contractNo,
													String guaranteeName);
}
