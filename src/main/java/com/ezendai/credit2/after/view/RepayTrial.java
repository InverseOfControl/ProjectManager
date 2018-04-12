package com.ezendai.credit2.after.view;

import java.io.Serializable;
import java.math.BigDecimal;

import com.ezendai.credit2.apply.model.Product;

/**
 * <pre>
 * 还款试算查询返回结果的实体类
 * </pre>
 *
 * @author chenqi
 * @version $Id: RepayTrial.java, v 0.1 2014年12月3日 上午9:13:43 chenqi Exp $
 */
public class RepayTrial implements Serializable {

	private static final long serialVersionUID = 7922206431183474987L;
	/** 借款Id */
	private Long loanId;
	/** 管理客服姓名*/
	private String manageServiceName;

	/** 客户姓名*/
	private String personName;
	/** 客户身份证*/
	private String personIdnum;
	/** 客户手机号码*/
	private String personMobilePhone;
	/**产品ID*/
	private Long productId;
	/** 产品类型 */
	private Integer productType;
	/**借款子类型*/
	private Integer loanType;
	/**银行账号*/
	private String bankAccount;
	/**开户行*/
	private String bankName;
	/** 合同金额 */
	private BigDecimal pactMoney;
	/** 还款周期*/
	private Long repayPeriod;
	/** 当前期数*/
	private Integer currTerm;
	/** 当前期数文本*/
	private String currTermText;
	/** 逾期期数*/
	private String overdueTerm;

	/** 当期应还 */
	private BigDecimal currAmount;
	/** 当期本金 */
	private BigDecimal currPrincipal;
	/** 当期利息 */
	private BigDecimal currInterest;
	/** 当期管理费 */
	private BigDecimal currManageFee;
	/** 逾期应还 */
	private BigDecimal overdueAmount;
	/** 一次性结清 */
	private BigDecimal oneTimeRepayment;
	/** 挂账金额*/
	private BigDecimal accAmount;
	/** 罚息,逾期违约金*/
	private BigDecimal fine;
	/** 提前还款违约金*/
	private BigDecimal penalty;
	/** 应还总额 */
	private BigDecimal repayAllAmount;
	/** 逾期应还总额=逾期应还 +罚息 */
	private BigDecimal overdueAllAmount;
	
	/** 展期第几次次数*/
	private Integer extensionTime;
	
	private Product product;
	
	

	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public BigDecimal getOverdueAllAmount() {
		return overdueAllAmount;
	}
	public void setOverdueAllAmount(BigDecimal overdueAllAmount) {
		this.overdueAllAmount = overdueAllAmount;
	}
	public BigDecimal getRepayAllAmount() {
		return repayAllAmount;
	}
	public void setRepayAllAmount(BigDecimal repayAllAmount) {
		this.repayAllAmount = repayAllAmount;
	}
	public String getOverdueTerm() {
		return overdueTerm;
	}

	public void setOverdueTerm(String overdueTerm) {
		this.overdueTerm = overdueTerm;
	}

	public BigDecimal getPenalty() {
		return penalty;
	}

	public void setPenalty(BigDecimal penalty) {
		this.penalty = penalty;
	}

	public BigDecimal getFine() {
		return fine;
	}

	public void setFine(BigDecimal fine) {
		this.fine = fine;
	}

	public BigDecimal getCurrManageFee() {
		return currManageFee;
	}

	public void setCurrManageFee(BigDecimal currManageFee) {
		this.currManageFee = currManageFee;
	}

	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public BigDecimal getCurrAmount() {
		return currAmount;
	}

	public void setCurrAmount(BigDecimal currAmount) {
		this.currAmount = currAmount;
	}

	public BigDecimal getCurrPrincipal() {
		return currPrincipal;
	}

	public void setCurrPrincipal(BigDecimal currPrincipal) {
		this.currPrincipal = currPrincipal;
	}

	public BigDecimal getCurrInterest() {
		return currInterest;
	}

	public void setCurrInterest(BigDecimal currInterest) {
		this.currInterest = currInterest;
	}

	public BigDecimal getOverdueAmount() {
		return overdueAmount;
	}

	public void setOverdueAmount(BigDecimal overdueAmount) {
		this.overdueAmount = overdueAmount;
	}

	public BigDecimal getOneTimeRepayment() {
		return oneTimeRepayment;
	}

	public void setOneTimeRepayment(BigDecimal oneTimeRepayment) {
		this.oneTimeRepayment = oneTimeRepayment;
	}

	public BigDecimal getAccAmount() {
		return accAmount;
	}

	public void setAccAmount(BigDecimal accAmount) {
		this.accAmount = accAmount;
	}

	public String getManageServiceName() {
		return manageServiceName;
	}

	public void setManageServiceName(String manageServiceName) {
		this.manageServiceName = manageServiceName;
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

	public String getPersonMobilePhone() {
		return personMobilePhone;
	}

	public void setPersonMobilePhone(String personMobilePhone) {
		this.personMobilePhone = personMobilePhone;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Integer getLoanType() {
		return loanType;
	}

	public void setLoanType(Integer loanType) {
		this.loanType = loanType;
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

	public BigDecimal getPactMoney() {
		return pactMoney;
	}

	public void setPactMoney(BigDecimal pactMoney) {
		this.pactMoney = pactMoney;
	}

	public Long getRepayPeriod() {
		return repayPeriod;
	}

	public void setRepayPeriod(Long repayPeriod) {
		this.repayPeriod = repayPeriod;
	}

	public Integer getCurrTerm() {
		return currTerm;
	}

	public void setCurrTerm(Integer currTerm) {
		this.currTerm = currTerm;
	}
	public String getCurrTermText() {
		return currTermText;
	}
	public void setCurrTermText(String currTermText) {
		this.currTermText = currTermText;
	}
	public Integer getExtensionTime() {
		return extensionTime;
	}
	public void setExtensionTime(Integer extensionTime) {
		this.extensionTime = extensionTime;
	}
	public Integer getProductType() {
		return productType;
	}
	public void setProductType(Integer productType) {
		this.productType = productType;
	}

}
