package com.ezendai.credit2.audit.model;

import java.math.BigDecimal;

import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.framework.model.BaseModel;

/**
 * 
 * @author liyuepeng
 *
 */
public class PersonBank extends BaseModel {
	private static final long serialVersionUID = 2779843724216731214L;

	/** 客户ID */
	private Long personId;
	/** 银行ID */
	private Long bankId;
	/** 银行账户ID */
	private Long bankAccountId;
	/**借款id*/
	private Long loanId;
	/** 开户人姓名 */
	private String personName;
	/** 开户人身份证号 */
	private String personIdnum;
	/** 银行卡号 */
	private String account;
	/** 银行名称 */
	private String bankName;
	/** 分行名称 */
	private String branchName;
	/** 产品类型 */
	private Integer productType;
	/** 合同金额 */
	private BigDecimal pactMoney;
	/** 借款状态 */
	private Integer loanStatus;
	/** 操作 */
	private String operation;
	
	private Integer accountAuthType;
	
	private Loan loan;
	
	private Long version;
	
	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	
	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getPersonIdnum() {
		return personIdnum;
	}

	public void setPersonIdnum(String personIdnum) {
		this.personIdnum = personIdnum;
	}

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getOperation() {
		return operation;
	}

	public Integer getProductType() {
		return productType;
	}

	public void setProductType(Integer productType) {
		this.productType = productType;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public BigDecimal getPactMoney() {
		return pactMoney;
	}

	public void setPactMoney(BigDecimal pactMoney) {
		this.pactMoney = pactMoney;
	}

	public Integer getLoanStatus() {
		return loanStatus;
	}

	public void setLoanStatus(Integer loanStatus) {
		this.loanStatus = loanStatus;
	}

	public Long getBankAccountId() {
		return bankAccountId;
	}

	public void setBankAccountId(Long bankAccountId) {
		this.bankAccountId = bankAccountId;
	}

	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public Loan getLoan() {
		return loan;
	}

	public void setLoan(Loan loan) {
		this.loan = loan;
	}

	public Integer getAccountAuthType() {
		return accountAuthType;
	}

	public void setAccountAuthType(Integer accountAuthType) {
		this.accountAuthType = accountAuthType;
	}

}
