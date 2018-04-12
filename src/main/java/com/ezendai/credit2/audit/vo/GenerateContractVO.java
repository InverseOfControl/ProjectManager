package com.ezendai.credit2.audit.vo;

import java.util.Date;
import java.util.List;

import com.ezendai.credit2.apply.model.BankAccount;
import com.ezendai.credit2.framework.vo.BaseVO;

public class GenerateContractVO extends BaseVO {

	private static final long serialVersionUID = -4018640375598469991L;
	
	private String personId;
	
	private String naturalGuaranteeId1;	
	private String naturalGuaranteeId2;
	private String naturalGuaranteeId3;
	private String naturalGuaranteeId4;
	
	private String legalGuaranteeId1;
	
	private String legalGuaranteeId2;
	
	private String loanId;
	
	private String accountName;
	
	private String bankAccount;
	
	private String bank;
	
	private String bankBranch;
	
	private String naturalGuaranteeName1;
	
	private String naturalGuaranteeBankAccount1;
	
	private String naturalGuaranteeBank1;
	
	private String naturalGuaranteeBankBranch1;
	
	private String naturalGuaranteeName2;
	
	private String naturalGuaranteeBankAccount2;
	
	private String naturalGuaranteeBank2;
	
	private String naturalGuaranteeBankBranch2;
	
	
	
	private String naturalGuaranteeName3;
	
	private String naturalGuaranteeBankAccount3;
	
	private String naturalGuaranteeBank3;
	
	private String naturalGuaranteeBankBranch3;
	
	
	
	private String naturalGuaranteeName4;
	
	private String naturalGuaranteeBankAccount4;
	
	private String naturalGuaranteeBank4;
	
	private String naturalGuaranteeBankBranch4;
	
	
	
	
	private String legalGuaranteeName1;
	
	private String legalGuaranteeBankAccount1;
	
	private String legalGuaranteeBank1;
	
	private String legalGuaranteeBankBranch1;
	
	private String legalGuaranteeName2;
	
	private String legalGuaranteeBankAccount2;
	
	private String legalGuaranteeBank2;
	
	private String legalGuaranteeBankBranch2;
	
	/** 是否已经签约：1：是；0：否，用于判断是否生成合同 */
	private boolean isSign;
	
	/** 是否已经签约：1：是；0：否，用于有担保人 */
	private boolean isHasNaturalGuarantee1;
	
	private boolean isHasNaturalGuarantee2;
	private boolean isHasNaturalGuarantee3;
	private boolean isHasNaturalGuarantee4;
	
	
	
	
	private boolean isHasLegalGuarantee1;
	
	private boolean isHasLegalGuarantee2;
	
	/** 是否是助学贷 */
	private boolean isEduAudit; 
	/** 是否机构还款 */
	private boolean isOrganPay; 
	
	private Integer contractSrc;
	/** 产品类型 */
	private Integer productType;
	/** 产品类型 */
	private Date contractCreatedDate;
	/** 产品信息ID */
	private Long productId;
	
	private Long organBankId;
	
	private List<BankAccount> bankAccountList;
	
	private Integer accountAuthType;
	
	/**捞财宝注册、登录、实名、绑卡节点标识*/
	private String lcbStatusNode;
	/**注册捞财宝验证码*/
	private String lcbVerifyCode;
	
	public String getLcbStatusNode() {
		return lcbStatusNode;
	}
	
	public void setLcbStatusNode(String lcbStatusNode) {
		this.lcbStatusNode = lcbStatusNode;
	}
	
	public String getLcbVerifyCode() {
		return lcbVerifyCode;
	}
	
	public void setLcbVerifyCode(String lcbVerifyCode) {
		this.lcbVerifyCode = lcbVerifyCode;
	}
	
	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getBankBranch() {
		return bankBranch;
	}

	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	public boolean isSign() {
		return isSign;
	}

	public void setSign(boolean isSign) {
		this.isSign = isSign;
	}

	public Integer getContractSrc() {
		return contractSrc;
	}

	public void setContractSrc(Integer contractSrc) {
		this.contractSrc = contractSrc;
	}

	public String getNaturalGuaranteeName1() {
		return naturalGuaranteeName1;
	}

	public void setNaturalGuaranteeName1(String naturalGuaranteeName1) {
		this.naturalGuaranteeName1 = naturalGuaranteeName1;
	}

	public String getNaturalGuaranteeBankAccount1() {
		return naturalGuaranteeBankAccount1;
	}

	public void setNaturalGuaranteeBankAccount1(String naturalGuaranteeBankAccount1) {
		this.naturalGuaranteeBankAccount1 = naturalGuaranteeBankAccount1;
	}

	public String getNaturalGuaranteeBank1() {
		return naturalGuaranteeBank1;
	}

	public void setNaturalGuaranteeBank1(String naturalGuaranteeBank1) {
		this.naturalGuaranteeBank1 = naturalGuaranteeBank1;
	}

	public String getNaturalGuaranteeBankBranch1() {
		return naturalGuaranteeBankBranch1;
	}

	public void setNaturalGuaranteeBankBranch1(String naturalGuaranteeBankBranch1) {
		this.naturalGuaranteeBankBranch1 = naturalGuaranteeBankBranch1;
	}

	public String getNaturalGuaranteeName2() {
		return naturalGuaranteeName2;
	}

	public void setNaturalGuaranteeName2(String naturalGuaranteeName2) {
		this.naturalGuaranteeName2 = naturalGuaranteeName2;
	}

	public String getNaturalGuaranteeBankAccount2() {
		return naturalGuaranteeBankAccount2;
	}

	public void setNaturalGuaranteeBankAccount2(String naturalGuaranteeBankAccount2) {
		this.naturalGuaranteeBankAccount2 = naturalGuaranteeBankAccount2;
	}

	public String getNaturalGuaranteeBank2() {
		return naturalGuaranteeBank2;
	}

	public void setNaturalGuaranteeBank2(String naturalGuaranteeBank2) {
		this.naturalGuaranteeBank2 = naturalGuaranteeBank2;
	}

	public String getNaturalGuaranteeBankBranch2() {
		return naturalGuaranteeBankBranch2;
	}

	public void setNaturalGuaranteeBankBranch2(String naturalGuaranteeBankBranch2) {
		this.naturalGuaranteeBankBranch2 = naturalGuaranteeBankBranch2;
	}

	public String getLegalGuaranteeName1() {
		return legalGuaranteeName1;
	}

	public void setLegalGuaranteeName1(String legalGuaranteeName1) {
		this.legalGuaranteeName1 = legalGuaranteeName1;
	}

	public String getLegalGuaranteeBankAccount1() {
		return legalGuaranteeBankAccount1;
	}

	public void setLegalGuaranteeBankAccount1(String legalGuaranteeBankAccount1) {
		this.legalGuaranteeBankAccount1 = legalGuaranteeBankAccount1;
	}

	public String getLegalGuaranteeBank1() {
		return legalGuaranteeBank1;
	}

	public void setLegalGuaranteeBank1(String legalGuaranteeBank1) {
		this.legalGuaranteeBank1 = legalGuaranteeBank1;
	}

	public String getLegalGuaranteeBankBranch1() {
		return legalGuaranteeBankBranch1;
	}

	public void setLegalGuaranteeBankBranch1(String legalGuaranteeBankBranch1) {
		this.legalGuaranteeBankBranch1 = legalGuaranteeBankBranch1;
	}

	public String getLegalGuaranteeName2() {
		return legalGuaranteeName2;
	}

	public void setLegalGuaranteeName2(String legalGuaranteeName2) {
		this.legalGuaranteeName2 = legalGuaranteeName2;
	}

	public String getLegalGuaranteeBankAccount2() {
		return legalGuaranteeBankAccount2;
	}

	public void setLegalGuaranteeBankAccount2(String legalGuaranteeBankAccount2) {
		this.legalGuaranteeBankAccount2 = legalGuaranteeBankAccount2;
	}

	public String getLegalGuaranteeBank2() {
		return legalGuaranteeBank2;
	}

	public void setLegalGuaranteeBank2(String legalGuaranteeBank2) {
		this.legalGuaranteeBank2 = legalGuaranteeBank2;
	}

	public String getLegalGuaranteeBankBranch2() {
		return legalGuaranteeBankBranch2;
	}

	public void setLegalGuaranteeBankBranch2(String legalGuaranteeBankBranch2) {
		this.legalGuaranteeBankBranch2 = legalGuaranteeBankBranch2;
	}

	public String getNaturalGuaranteeId1() {
		return naturalGuaranteeId1;
	}

	public void setNaturalGuaranteeId1(String naturalGuaranteeId1) {
		this.naturalGuaranteeId1 = naturalGuaranteeId1;
	}

	public String getNaturalGuaranteeId2() {
		return naturalGuaranteeId2;
	}

	public void setNaturalGuaranteeId2(String naturalGuaranteeId2) {
		this.naturalGuaranteeId2 = naturalGuaranteeId2;
	}

	public String getLegalGuaranteeId1() {
		return legalGuaranteeId1;
	}

	public void setLegalGuaranteeId1(String legalGuaranteeId1) {
		this.legalGuaranteeId1 = legalGuaranteeId1;
	}

	public String getLegalGuaranteeId2() {
		return legalGuaranteeId2;
	}

	public void setLegalGuaranteeId2(String legalGuaranteeId2) {
		this.legalGuaranteeId2 = legalGuaranteeId2;
	}

	public boolean isHasNaturalGuarantee1() {
		return isHasNaturalGuarantee1;
	}

	public void setHasNaturalGuarantee1(boolean isHasNaturalGuarantee1) {
		this.isHasNaturalGuarantee1 = isHasNaturalGuarantee1;
	}

	public boolean isHasNaturalGuarantee2() {
		return isHasNaturalGuarantee2;
	}

	public void setHasNaturalGuarantee2(boolean isHasNaturalGuarantee2) {
		this.isHasNaturalGuarantee2 = isHasNaturalGuarantee2;
	}

	public boolean isHasLegalGuarantee1() {
		return isHasLegalGuarantee1;
	}

	public void setHasLegalGuarantee1(boolean isHasLegalGuarantee1) {
		this.isHasLegalGuarantee1 = isHasLegalGuarantee1;
	}

	public boolean isHasLegalGuarantee2() {
		return isHasLegalGuarantee2;
	}

	public void setHasLegalGuarantee2(boolean isHasLegalGuarantee2) {
		this.isHasLegalGuarantee2 = isHasLegalGuarantee2;
	}

	public Integer getProductType() {
		return productType;
	}

	public void setProductType(Integer productType) {
		this.productType = productType;
	}

	public Date getContractCreatedDate() {
		return contractCreatedDate;
	}

	public void setContractCreatedDate(Date contractCreatedDate) {
		this.contractCreatedDate = contractCreatedDate;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getNaturalGuaranteeId3() {
		return naturalGuaranteeId3;
	}

	public void setNaturalGuaranteeId3(String naturalGuaranteeId3) {
		this.naturalGuaranteeId3 = naturalGuaranteeId3;
	}

	public String getNaturalGuaranteeId4() {
		return naturalGuaranteeId4;
	}

	public void setNaturalGuaranteeId4(String naturalGuaranteeId4) {
		this.naturalGuaranteeId4 = naturalGuaranteeId4;
	}

	public String getNaturalGuaranteeName3() {
		return naturalGuaranteeName3;
	}

	public void setNaturalGuaranteeName3(String naturalGuaranteeName3) {
		this.naturalGuaranteeName3 = naturalGuaranteeName3;
	}

	public String getNaturalGuaranteeBankAccount3() {
		return naturalGuaranteeBankAccount3;
	}

	public void setNaturalGuaranteeBankAccount3(String naturalGuaranteeBankAccount3) {
		this.naturalGuaranteeBankAccount3 = naturalGuaranteeBankAccount3;
	}

	public String getNaturalGuaranteeBank3() {
		return naturalGuaranteeBank3;
	}

	public void setNaturalGuaranteeBank3(String naturalGuaranteeBank3) {
		this.naturalGuaranteeBank3 = naturalGuaranteeBank3;
	}

	public String getNaturalGuaranteeBankBranch3() {
		return naturalGuaranteeBankBranch3;
	}

	public void setNaturalGuaranteeBankBranch3(String naturalGuaranteeBankBranch3) {
		this.naturalGuaranteeBankBranch3 = naturalGuaranteeBankBranch3;
	}

	public String getNaturalGuaranteeName4() {
		return naturalGuaranteeName4;
	}

	public void setNaturalGuaranteeName4(String naturalGuaranteeName4) {
		this.naturalGuaranteeName4 = naturalGuaranteeName4;
	}

	public String getNaturalGuaranteeBankAccount4() {
		return naturalGuaranteeBankAccount4;
	}

	public void setNaturalGuaranteeBankAccount4(String naturalGuaranteeBankAccount4) {
		this.naturalGuaranteeBankAccount4 = naturalGuaranteeBankAccount4;
	}

	public String getNaturalGuaranteeBank4() {
		return naturalGuaranteeBank4;
	}

	public void setNaturalGuaranteeBank4(String naturalGuaranteeBank4) {
		this.naturalGuaranteeBank4 = naturalGuaranteeBank4;
	}

	public String getNaturalGuaranteeBankBranch4() {
		return naturalGuaranteeBankBranch4;
	}

	public void setNaturalGuaranteeBankBranch4(String naturalGuaranteeBankBranch4) {
		this.naturalGuaranteeBankBranch4 = naturalGuaranteeBankBranch4;
	}

	public boolean isHasNaturalGuarantee3() {
		return isHasNaturalGuarantee3;
	}

	public void setHasNaturalGuarantee3(boolean isHasNaturalGuarantee3) {
		this.isHasNaturalGuarantee3 = isHasNaturalGuarantee3;
	}

	public boolean isHasNaturalGuarantee4() {
		return isHasNaturalGuarantee4;
	}

	public void setHasNaturalGuarantee4(boolean isHasNaturalGuarantee4) {
		this.isHasNaturalGuarantee4 = isHasNaturalGuarantee4;
	}

	public Long getOrganBankId() {
		return organBankId;
	}

	public void setOrganBankId(Long organBankId) {
		this.organBankId = organBankId;
	}

	public boolean isEduAudit() {
		return isEduAudit;
	}

	public void setEduAudit(boolean isEduAudit) {
		this.isEduAudit = isEduAudit;
	}

	public List<BankAccount> getBankAccountList() {
		return bankAccountList;
	}

	public void setBankAccountList(List<BankAccount> bankAccountList) {
		this.bankAccountList = bankAccountList;
	}

	public boolean isOrganPay() {
		return isOrganPay;
	}

	public void setOrganPay(boolean isOrganPay) {
		this.isOrganPay = isOrganPay;
	}

	public Integer getAccountAuthType() {
		return accountAuthType;
	}

	public void setAccountAuthType(Integer accountAuthType) {
		this.accountAuthType = accountAuthType;
	}
	
	
	

	
}
