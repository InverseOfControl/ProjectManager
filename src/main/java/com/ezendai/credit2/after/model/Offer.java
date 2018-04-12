package com.ezendai.credit2.after.model;

import java.math.BigDecimal;
import java.util.Date;

import com.ezendai.credit2.apply.model.Bank;
import com.ezendai.credit2.apply.model.BankAccount;
import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.model.Person;
import com.ezendai.credit2.audit.model.PersonBank;
import com.ezendai.credit2.framework.model.BaseModel;
import com.ezendai.credit2.master.model.BusinessCompany;

/**
 * <pre>
 * 报盘表
 * </pre>
 * @author 陈忠星
 * @Description:
 */
public class Offer extends BaseModel {
	/**	 */
	private static final long serialVersionUID = 6457717107430032027L;
	/** 借款ID */
	private Long loanId;
	/** 借款人ID */
	private Long personId;
	/** 借款人姓名 */
	private String personName;
	/** 借款人账户类型 */
	private Integer accountType;
	/** 银行账号 */
	private String bankAccount;
	/** 银行名称 */
	private String bankName;
	/** 有无担保 */
	private Integer hasGuarantee;
	/** 担保人ID */
	private Long guaranteeId;
	/** 担保人姓名 */
	private String guaranteeName;
	/** 担保人银行账号 */
	private String guaranteeBankAccount;
	/** 担保人银行名称 */
	private String guaranteeBankName;
	/** 应收金额 */
	private BigDecimal receivableAmount;
	/** 报盘金额 */
	private BigDecimal offerAmount;
	/** 发送报盘时间 */
	private Date sendDate;
	/** 划扣状态 */
	private Integer status;
	/** 客服ID */
	private Long tellerId;
	/** 营业网点ID */
	private Long salesDeptId;
	/** 备注 */
	private String remark;
	/** 第三方类型 */
	private Integer tppType;
	/** 借款信息 */
	private Loan loan;
	/** 借款人信息 */
	private Person person;
	/** 回盘信息 */
	private TppCallBackData tppCallBackData;
	/** 银行信息 */
	private Bank bank;
	/** 客户和银行账户 */
	private PersonBank personBank;
	/** 银行账户信息 */
	private BankAccount bankAcct;
	/**是否有担保人*/
	private Integer  isGuarantee;
	
	/**报盘类型 0定时 1实时**/
	private Integer offerType;
	/**回盘金额*/
	private BigDecimal successAmount;
	
	/**产品类别*/
	private Integer  productId;
	
	/**本方账户信息*/
	private BusinessCompany businessCompany;
	
	/**放款营业部*/
	private String salesDeptName;

	public String getSalesDeptName() {
		return salesDeptName;
	}

	public void setSalesDeptName(String salesDeptName) {
		this.salesDeptName = salesDeptName;
	}

	public Integer getOfferType() {
		return offerType;
	}

	public void setOfferType(Integer offerType) {
		this.offerType = offerType;
	}

	public BigDecimal getSuccessAmount() {
		return successAmount;
	}

	public void setSuccessAmount(BigDecimal successAmount) {
		this.successAmount = successAmount;
	}

	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
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

	public Integer getAccountType() {
		return accountType;
	}

	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public Integer getHasGuarantee() {
		return hasGuarantee;
	}

	public void setHasGuarantee(Integer hasGuarantee) {
		this.hasGuarantee = hasGuarantee;
	}

	public Long getGuaranteeId() {
		return guaranteeId;
	}

	public void setGuaranteeId(Long guaranteeId) {
		this.guaranteeId = guaranteeId;
	}

	public String getGuaranteeName() {
		return guaranteeName;
	}

	public void setGuaranteeName(String guaranteeName) {
		this.guaranteeName = guaranteeName;
	}

	public String getGuaranteeBankAccount() {
		return guaranteeBankAccount;
	}

	public void setGuaranteeBankAccount(String guaranteeBankAccount) {
		this.guaranteeBankAccount = guaranteeBankAccount;
	}

	public String getGuaranteeBankName() {
		return guaranteeBankName;
	}

	public void setGuaranteeBankName(String guaranteeBankName) {
		this.guaranteeBankName = guaranteeBankName;
	}

	public BigDecimal getReceivableAmount() {
		return receivableAmount;
	}

	public void setReceivableAmount(BigDecimal receivableAmount) {
		this.receivableAmount = receivableAmount;
	}

	public BigDecimal getOfferAmount() {
		return offerAmount;
	}

	public void setOfferAmount(BigDecimal offerAmount) {
		this.offerAmount = offerAmount;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getTellerId() {
		return tellerId;
	}

	public void setTellerId(Long tellerId) {
		this.tellerId = tellerId;
	}

	public Long getSalesDeptId() {
		return salesDeptId;
	}

	public void setSalesDeptId(Long salesDeptId) {
		this.salesDeptId = salesDeptId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Loan getLoan() {
		return loan;
	}

	public void setLoan(Loan loan) {
		this.loan = loan;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public TppCallBackData getTppCallBackData() {
		return tppCallBackData;
	}

	public void setTppCallBackData(TppCallBackData tppCallBackData) {
		this.tppCallBackData = tppCallBackData;
	}

	public Integer getTppType() {
		return tppType;
	}

	public void setTppType(Integer tppType) {
		this.tppType = tppType;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public PersonBank getPersonBank() {
		return personBank;
	}

	public void setPersonBank(PersonBank personBank) {
		this.personBank = personBank;
	}

	public BankAccount getBankAcct() {
		return bankAcct;
	}

	public void setBankAcct(BankAccount bankAcct) {
		this.bankAcct = bankAcct;
	}

	public Integer getIsGuarantee() {
		return isGuarantee;
	}

	public void setIsGuarantee(Integer isGuarantee) {
		this.isGuarantee = isGuarantee;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public BusinessCompany getBusinessCompany() {
		return businessCompany;
	}

	public void setBusinessCompany(BusinessCompany businessCompany) {
		this.businessCompany = businessCompany;
	}
	
}