package com.ezendai.credit2.report.model;

import java.math.BigDecimal;
import java.util.Date;

import com.ezendai.credit2.apply.model.Person;
import com.ezendai.credit2.apply.model.Product;
import com.ezendai.credit2.framework.model.BaseModel;
import com.ezendai.credit2.master.model.BaseArea;
import com.ezendai.credit2.system.model.Organ;
import com.ezendai.credit2.system.model.SysUser;

/**   
*    
* 项目名称：credit2-report   
* 类名称：DeliveryDetail   
* 类描述：   
* 创建人：liboyan   
* 创建时间：2015年11月6日 上午10:15:00   
* 修改人：liboyan   
* 修改时间：2015年11月6日 上午10:15:00   
* 修改备注：   
* @version    
*    
*/
public class DeliveryDetail extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long personId;
	private Long organId;
	private Long salesTeamId;
	private Long salesDeptId;
	private Long bizDirectorId;
	private Long crmId;
	private Long serviceId;
	private Integer   status;
	
	/**营业部*/
	private  BaseArea  salesDept;
	/**客户经理*/
	private SysUser   crm;
	/**首次申请日期*/
	private Date requestDate;
	/**客户*/
	private Person  person;
	/**借款类型*/
	private Integer productType;
	/**产品ID*/
	private Long productId;
	/**机构*/
	private Organ  organ;
	/**申请金额*/
	private BigDecimal  requestMoney;
	/**申请期数*/
	private Long requestTime;
	/**业务主任*/
	private SysUser bizDirector;
	/**销售团队*/
	private  BaseArea  salesTeam;
	private Product product;
	/**客服专员*/
	private SysUser  service;
	/**最后申请日期*/
	private Date lastRequestDate;
	/**首次申请日期，开始日期*/
	private Date requestDateStart;
	/**结束日期*/
	private Date requestDateEnd;
	public Date getRequestDateStart() {
		return requestDateStart;
	}
	public void setRequestDateStart(Date requestDateStart) {
		this.requestDateStart = requestDateStart;
	}
	public Date getRequestDateEnd() {
		return requestDateEnd;
	}
	public void setRequestDateEnd(Date requestDateEnd) {
		this.requestDateEnd = requestDateEnd;
	}
	public BaseArea getSalesDept() {
		return salesDept;
	}
	public void setSalesDept(BaseArea salesDept) {
		this.salesDept = salesDept;
	}
	public SysUser getCrm() {
		return crm;
	}
	public void setCrm(SysUser crm) {
		this.crm = crm;
	}

	public Date getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}

	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Organ getOrgan() {
		return organ;
	}
	public void setOrgan(Organ organ) {
		this.organ = organ;
	}
	public BigDecimal getRequestMoney() {
		return requestMoney;
	}
	public void setRequestMoney(BigDecimal requestMoney) {
		this.requestMoney = requestMoney;
	}

	public Long getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(Long requestTime) {
		this.requestTime = requestTime;
	}

	public SysUser getBizDirector() {
		return bizDirector;
	}
	public void setBizDirector(SysUser bizDirector) {
		this.bizDirector = bizDirector;
	}
	public BaseArea getSalesTeam() {
		return salesTeam;
	}
	public void setSalesTeam(BaseArea salesTeam) {
		this.salesTeam = salesTeam;
	}
	public SysUser getService() {
		return service;
	}
	public void setService(SysUser service) {
		this.service = service;
	}
	public Date getLastRequestDate() {
		return lastRequestDate;
	}
	public void setLastRequestDate(Date lastRequestDate) {
		this.lastRequestDate = lastRequestDate;
	}
	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	public Long getOrganId() {
		return organId;
	}
	public void setOrganId(Long organId) {
		this.organId = organId;
	}
	public Long getSalesTeamId() {
		return salesTeamId;
	}
	public void setSalesTeamId(Long salesTeamId) {
		this.salesTeamId = salesTeamId;
	}
	public Long getSalesDeptId() {
		return salesDeptId;
	}
	public void setSalesDeptId(Long salesDeptId) {
		this.salesDeptId = salesDeptId;
	}
	public Long getBizDirectorId() {
		return bizDirectorId;
	}
	public void setBizDirectorId(Long bizDirectorId) {
		this.bizDirectorId = bizDirectorId;
	}
	public Long getCrmId() {
		return crmId;
	}
	public void setCrmId(Long crmId) {
		this.crmId = crmId;
	}
	public Long getServiceId() {
		return serviceId;
	}
	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Integer getProductType() {
		return productType;
	}
	public void setProductType(Integer productType) {
		this.productType = productType;
	}

	
	
	
	
}
