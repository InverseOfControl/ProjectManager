package com.ezendai.credit2.audit.service;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.transaction.annotation.Transactional;

import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.model.Person;
import com.ezendai.credit2.apply.vo.LoanVO;
import com.ezendai.credit2.audit.vo.GenerateContractVO;
import com.ezendai.credit2.audit.vo.RepaymentPlanVO;


/**   
*    
* 项目名称：credit2-main   
* 类名称：StudentLoanContractService   
* 类描述：   
* 创建人：liboyan   
* 创建时间：2015年10月14日 上午9:49:06   
* 修改人：liboyan   
* 修改时间：2015年10月14日 上午9:49:06   
* 修改备注：   
* @version    
*    
*/
public interface StudentLoanContractService {
	
	
	/**
	 *  助学贷-个人借款咨询服务风险基金协议
	 * @param loan
	 * @param person
	 * @param contractNo
	 */
	public void createStudentLoanPersonLoanContract(Loan loan, Person person, String contractNo);
	
	/**
	 * 助学贷-借款协议合同
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
	public void createStudentLoanContract(Loan loan, Person person,
											RepaymentPlanVO repaymentPlanVO, Date endRepayDate,
											Date startRepayDate,
											GenerateContractVO generateContractVO, String contractNo);
	/**
	 * 助学贷-个人借款咨询服务协议合同
	 * 
	 * @param loan
	 * @param person
	 * @param repaymentPlanVO
	 * @param contractNo
	 * 
	 */
	@Transactional
	public void createStudentLoanRepaymentContract(Loan loan,BigDecimal diffRefund, Person person,
												RepaymentPlanVO repaymentPlanVO, String contractNo,
												BigDecimal consult, LoanVO loanVO,GenerateContractVO generateContractVO);
	/**
	 * 助学贷-委托扣款授权书合同
	 * 
	 * @param loan
	 * @param person
	 * @param personBankAccount
	 * @param contractNo
	 */
	@Transactional
	public void createStudentLoanEntrustContract(Loan loan, Person person,
												GenerateContractVO generateContractVO,
												String contractNo);
	/**
	 * 助学贷-还款提醒函 合同
	 * 
	 * @param loan
	 * @param person
	 * @param repaymentPlanVO
	 * @param contractNo
	 * @param startRepayDate
	 * 
	 */
	@Transactional
	public void createStudentLoanRepaymentFundContract(Loan loan, Person person,
													RepaymentPlanVO repaymentPlanVO,
													String contractNo, Date startRepayDate);
}
