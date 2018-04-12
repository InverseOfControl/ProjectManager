package com.ezendai.credit2.audit.service;

import java.math.BigDecimal;

import org.springframework.transaction.annotation.Transactional;

import com.ezendai.credit2.apply.model.BankAccount;
import com.ezendai.credit2.apply.model.Extension;
import com.ezendai.credit2.apply.model.Person;
import com.ezendai.credit2.apply.vo.ExtensionVO;

/**
 * 
 * <pre>
 * 用来生成展期合同协议
 * </pre>
 *
 * @author 00226557
 * @version $Id: CarExtensionService.java, v 0.1 2015年3月25日 下午1:30:58 00226557 Exp $
 */

public interface CarExtensionService {

	/**
	 * 生成车贷展期-008-抵押借款展期协议
	 */
	@Transactional
	public void createCarLoanContract(Extension extension,ExtensionVO extensionVO, Person person,  BankAccount personBankAccount, BigDecimal totalInterestAmt,
										 String salesDepartmentAddress);
	/**
	 * <pre>
	 * 生成车贷展期-009 个人借款咨询服务风险基金协议(展期)
	 * </pre>
	 *
	 */
	@Transactional
	public void createCarPersonLoanContract(Extension extension,ExtensionVO extensionVO, Person person, BigDecimal risk, String salesDepartmentAddress);
	
	/**
	 * 生成车贷展期-010 还款温馨提示函(展期) 
	 * 
	 */
	@Transactional
	public void createCarRepaymentFundContract(Extension extension,ExtensionVO extensionVO, Person person,  
												String salesDepartmentAddress);
	
	/**
	 * 生成车贷展期-011 委托扣款授权书（无风险基金的描述)
	 * 
	 */
	@Transactional
	public void createCarEntrustContract(Extension extension,ExtensionVO extensionVO, Person person,  BankAccount personBankAccount);
}
