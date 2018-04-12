package com.ezendai.credit2.audit.vo;

import java.util.List;

import com.ezendai.credit2.framework.vo.BaseVO;

public class PersonBankVO extends BaseVO {
	private static final long serialVersionUID = -5602504929009826427L;

	/** 客户ID */
	private Long personId;
	
	/** 银行ID */
	private Long bankId;

	/** 银行账户ID */
	private Long bankAccountId;
	
	/**借款id*/
	private Long loanId;
	
	/** 借款人姓名 */
	private String personName;
	
	/** 借款人身份证 */
	private String personIdnum;
	
	/** 所属网点 */
	private Long salesDeptId;
	/** 所属网点列表 */
	private List<Long> salesDeptIdList;
	
	/** 借款类型ID */
	private Integer productType;
	
	/** 贷款状态 */
	private Integer status;
	
	private List<Integer> statusList;
	
	private List<Long> productIdList;
	
	private List<Integer> productTypeList;
	
	private List<Long> personIdList;
	
	private List<Long> loanIdList;
	
	private Integer accountAuthType;
	
	private String hasTppType;

	public Long getPersonId() {
		return personId;
	}

	public Integer getProductType() {
		return productType;
	}

	public void setProductType(Integer productType) {
		this.productType = productType;
	}

	public List<Long> getPersonIdList() {
		return personIdList;
	}

	public List<Integer> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<Integer> statusList) {
		this.statusList = statusList;
	}

	public Integer getStatus() {
		return status;
	}

	public List<Integer> getProductTypeList() {
		return productTypeList;
	}

	public void setProductTypeList(List<Integer> productTypeList) {
		this.productTypeList = productTypeList;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<Long> getProductIdList() {
		return productIdList;
	}

	public void setProductIdList(List<Long> productIdList) {
		this.productIdList = productIdList;
	}

	public Long getSalesDeptId() {
		return salesDeptId;
	}

	public void setSalesDeptId(Long salesDeptId) {
		this.salesDeptId = salesDeptId;
	}

	public List<Long> getLoanIdList() {
		return loanIdList;
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

	public void setLoanIdList(List<Long> loanIdList) {
		this.loanIdList = loanIdList;
	}

	public void setPersonIdList(List<Long> personIdList) {
		this.personIdList = personIdList;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
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

	public List<Long> getSalesDeptIdList() {
		return salesDeptIdList;
	}

	public void setSalesDeptIdList(List<Long> salesDeptIdList) {
		this.salesDeptIdList = salesDeptIdList;
	}

	public Integer getAccountAuthType() {
		return accountAuthType;
	}

	public void setAccountAuthType(Integer accountAuthType) {
		this.accountAuthType = accountAuthType;
	}

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public String getHasTppType() {
		return hasTppType;
	}

	public void setHasTppType(String hasTppType) {
		this.hasTppType = hasTppType;
	}

}
