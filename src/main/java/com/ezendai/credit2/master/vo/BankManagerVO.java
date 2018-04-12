package com.ezendai.credit2.master.vo;

import java.math.BigDecimal;

import com.ezendai.credit2.framework.vo.BaseVO;

/**
 * 
 * @Description: 银行VO前端传参数用
 * @author 张宜超
 * @date 2016年1月25日
 */
public class BankManagerVO extends BaseVO{

	private static final long serialVersionUID = -1660655899365975555L;

	/**主键**/
	private Long id;
	
	/**银行名称**/
	private String bankName;

	/**银行代码**/
	private String bankCode;

	/**tpp银行代码**/
	private String tppBankCode;

	/**tpp类型  10通联划扣    20富有划扣    30银联划扣   **/
	private Integer tppType;
	
	

	/**银行类型  0国内银行   1国外银行  mybatis 0跟null顾虑条件一样**/
	private Integer bankType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getTppBankCode() {
		return tppBankCode;
	}

	public void setTppBankCode(String tppBankCode) {
		this.tppBankCode = tppBankCode;
	}

	public Integer getTppType() {
		return tppType;
	}

	public void setTppType(Integer tppType) {
		this.tppType = tppType;
	}

	public Integer getBankType() {
		return bankType;
	}

	public void setBankType(Integer bankType) {
		this.bankType = bankType;
	}

	
	
	
}
