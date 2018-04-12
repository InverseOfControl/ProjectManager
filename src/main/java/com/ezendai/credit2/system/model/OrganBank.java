package com.ezendai.credit2.system.model;

import com.ezendai.credit2.apply.model.BankAccount;
import com.ezendai.credit2.framework.model.BaseModel;

/***
 * 
 * <pre>
 * </pre>
 *
 * @author HQ-AT6
 * @version $Id: SysLog.java, v 0.1 2014年7月22日 下午3:38:21 HQ-AT6 Exp $
 */
public class OrganBank extends BaseModel {
	
	/**  */
	private static final long serialVersionUID = 6243157281865043048L;
	
	private Long organId;
	private Long bankAccountId;
	private BankAccount bankAccount;

	public Long getOrganId() {
		return organId;
	}
	public void setOrganId(Long organId) {
		this.organId = organId;
	}
	public Long getBankAccountId() {
		return bankAccountId;
	}
	public void setBankAccountId(Long bankAccountId) {
		this.bankAccountId = bankAccountId;
	}
	public BankAccount getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(BankAccount bankAccount) {
		this.bankAccount = bankAccount;
	}
	
}