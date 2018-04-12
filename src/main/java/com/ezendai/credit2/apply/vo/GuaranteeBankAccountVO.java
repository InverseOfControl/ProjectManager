package com.ezendai.credit2.apply.vo;

import com.ezendai.credit2.framework.vo.BaseVO;

/**
 * 
 * <pre>
 * 担保人银行账户
 * </pre>
 *
 * @author zhangshihai
 * @version $Id: GuaranteeBankAccountVO.java, v 0.1 2014年7月31日 上午9:18:47 zhangshihai Exp $
 */
public class GuaranteeBankAccountVO 
						extends BaseVO{

	private static final long serialVersionUID = 3966622533775840055L;
	
	private Long bankId;

	private Long guarantee;

	private String branchName;

	private String bankName;

	private String account;

	private Integer status;

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public Long getGuarantee() {
		return guarantee;
	}

	public void setGuarantee(Long guarantee) {
		this.guarantee = guarantee;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
