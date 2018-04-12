package com.ezendai.credit2.apply.model;

import com.ezendai.credit2.framework.model.BaseModel;
import com.ezendai.credit2.system.model.SysUser;

public class Contacter extends BaseModel {
	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	private static final long serialVersionUID = -6967027017046109306L;

	/** 姓名 */
	private String name;

	/** 关系 */
	private String relationship;

	/** 手机号码 */
	private String mobilePhone;

	/** 固定电话 */
	private String homePhone;

	/** 地址 */
	private String address;

	/** 工作单位 */
	private String workUnit;

	/** 知晓贷款 */
	private Integer hadKnown;

	/** 借贷人 */
	private Long borrowerId;
	
	private Long loanId;
	
	/** 职务头衔*/
	private String title;
	
	/** 借贷人 */
	private SysUser borrower;
	/** 借贷 */
	private Loan loan;
	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getWorkUnit() {
		return workUnit;
	}

	public void setWorkUnit(String workUnit) {
		this.workUnit = workUnit;
	}

	public Integer getHadKnown() {
		return hadKnown;
	}

	public void setHadKnown(Integer hadKnown) {
		this.hadKnown = hadKnown;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getBorrowerId() {
		return borrowerId;
	}

	public void setBorrowerId(Long borrowerId) {
		this.borrowerId = borrowerId;
	}

	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public SysUser getBorrower() {
		return borrower;
	}

	public void setBorrower(SysUser borrower) {
		this.borrower = borrower;
	}

	public Loan getLoan() {
		return loan;
	}

	public void setLoan(Loan loan) {
		this.loan = loan;
	}
	
}