package com.ezendai.credit2.master.vo;

import com.ezendai.credit2.framework.vo.BaseVO;

 
public class ComprehensiveSearchVO extends BaseVO {
	/**	 */
	private static final long serialVersionUID = -457242451952353074L;
	 
	

	private String personName;
	private String tel;
	private String idNum;
	private Integer status;
	private String crmId;
	private Integer productType;
	private Integer salesDeptId;
	private Integer extensionTime;
	
	public Integer getExtensionTime() {
		return extensionTime;
	}
	public void setExtensionTime(Integer extensionTime) {
		this.extensionTime = extensionTime;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getIdNum() {
		return idNum;
	}
	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getCrmId() {
		return crmId;
	}
	public void setCrmId(String crmId) {
		this.crmId = crmId;
	}
	public Integer getProductType() {
		return productType;
	}
	public void setProductType(Integer productType) {
		this.productType = productType;
	}
	public Integer getSalesDeptId() {
		return salesDeptId;
	}
	public void setSalesDeptId(Integer salesDeptId) {
		this.salesDeptId = salesDeptId;
	}
}