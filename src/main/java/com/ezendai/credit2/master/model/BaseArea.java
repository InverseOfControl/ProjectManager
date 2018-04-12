package com.ezendai.credit2.master.model;

import com.ezendai.credit2.framework.model.BaseModel;

/**
 *基础区域信息实体类
 * Author: kimi
 * Date: 14-6-23
 * Time: 下午4:57
 */
public class BaseArea extends BaseModel {

	private static final long serialVersionUID = -668444156095857158L;
	private Long id;
	/** 区域编号 */
	private String code;

	/** 区域全称 */
	private String fullName;

	/** 区域名称 */
	private String name;

	/** 类型 */
	private String identifier;

	/** 所属城市 */
	private String cityId;

	/** 父区域 */
	private String areaId;

	/** 服务电话 */
	private String serviceTel;

	/** 销售部门 */
	private String salesDeptId;

	/** 公司编号 */
	private Long companyId;

	/** 工作地点信息编号 */
	private Long workPlaceInfoId;

	/** 部门编号 */
	private String deptNo;

	/** 备注 */
	private String remark;

	/** 逻辑删除标志 */
	private Integer deleted;

	/** 部门类型(3.小企业贷款 4.车贷) */
	private Integer deptType;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getServiceTel() {
		return serviceTel;
	}

	public void setServiceTel(String serviceTel) {
		this.serviceTel = serviceTel;
	}

	public String getSalesDeptId() {
		return salesDeptId;
	}

	public void setSalesDeptId(String salesDeptId) {
		this.salesDeptId = salesDeptId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getWorkPlaceInfoId() {
		return workPlaceInfoId;
	}

	public void setWorkPlaceInfoId(Long workPlaceInfoId) {
		this.workPlaceInfoId = workPlaceInfoId;
	}

	public String getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public Integer getDeptType() {
		return deptType;
	}

	public void setDeptType(Integer deptType) {
		this.deptType = deptType;
	}
}
