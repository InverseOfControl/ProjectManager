package com.ezendai.credit2.after.view;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <pre>
 * 还款录入
 * </pre>
 *
 * @author chenqi
 * @version $Id: RepayEntry.java, v 0.1 2014年12月15日 下午4:14:26 chenqi Exp $
 */
public class RepayEntry implements Serializable {

	/**  */
	private static final long serialVersionUID = 5582456604053180167L;
	/** 借款Id */
	private Long loanId;
	/** 客户姓名*/
	private String personName;
	/** 营业部名字*/
	private String salesDeptName;
	/**产品ID*/
	private Long productId;
	/** 产品类型 */
	private Integer productType;
	/** 客户身份证*/
	private String personIdnum;
	/** 合同金额 */
	private BigDecimal pactMoney;
	/** 还款周期*/
	private Long repayPeriod;
	/** 借款状态 */
	private Integer loanStatus;
	/**提前还款  */
	private String isOneTimeRepayment;
	/** 减免金额*/
	private BigDecimal reliefOfFine;
	/** 操作 */
	private String operations;

	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getSalesDeptName() {
		return salesDeptName;
	}

	public void setSalesDeptName(String salesDeptName) {
		this.salesDeptName = salesDeptName;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getPersonIdnum() {
		return personIdnum;
	}

	public void setPersonIdnum(String personIdnum) {
		this.personIdnum = personIdnum;
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

	public Integer getLoanStatus() {
		return loanStatus;
	}

	public void setLoanStatus(Integer loanStatus) {
		this.loanStatus = loanStatus;
	}

	public String getIsOneTimeRepayment() {
		return isOneTimeRepayment;
	}

	public void setIsOneTimeRepayment(String isOneTimeRepayment) {
		this.isOneTimeRepayment = isOneTimeRepayment;
	}

	public BigDecimal getReliefOfFine() {
		return reliefOfFine;
	}

	public void setReliefOfFine(BigDecimal reliefOfFine) {
		this.reliefOfFine = reliefOfFine;
	}

	public String getOperations() {
		return operations;
	}

	public void setOperations(String operations) {
		this.operations = operations;
	}

	public Integer getProductType() {
		return productType;
	}

	public void setProductType(Integer productType) {
		this.productType = productType;
	}
	
}
