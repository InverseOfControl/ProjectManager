package com.ezendai.credit2.after.model;

import java.math.BigDecimal;

import com.ezendai.credit2.framework.model.BaseModel;
import com.ezendai.credit2.framework.util.Pager;

public class CustomerManager extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1846808722322254013L;
	private Pager pager;
	private String personName;
	private String idNum;
	private String productType;
	private Integer productId;
	private BigDecimal cash;
	private String contractNo;
	private Integer loanId;
	private Integer personId;
	private Integer extenId;
	private Integer extensionTime;
	private String productName;
	

	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Integer getLoanId() {
		return loanId;
	}
	public void setLoanId(Integer loanId) {
		this.loanId = loanId;
	}
	public Integer getPersonId() {
		return personId;
	}
	public void setPersonId(Integer personId) {
		this.personId = personId;
	}
	public Integer getExtenId() {
		return extenId;
	}
	public void setExtenId(Integer extenId) {
		this.extenId = extenId;
	}
	public Integer getExtensionTime() {
		return extensionTime;
	}
	public void setExtensionTime(Integer extensionTime) {
		this.extensionTime = extensionTime;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Pager getPager() {
		return pager;
	}
	public void setPager(Pager pager) {
		this.pager = pager;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	
	public String getIdNum() {
		return idNum;
	}
	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public BigDecimal getCash() {
		return cash;
	}
	public void setCash(BigDecimal cash) {
		this.cash = cash;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	
}
