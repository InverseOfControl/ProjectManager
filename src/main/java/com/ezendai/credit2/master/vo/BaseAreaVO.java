package com.ezendai.credit2.master.vo;

import com.ezendai.credit2.framework.vo.BaseVO;

import java.util.List;

/**
 * Author: kimi
 * Date: 14-6-24
 * Time: 下午1:50
 */
public class BaseAreaVO extends BaseVO {

	private static final long serialVersionUID = -875350506708841846L;
	/**
	 * 用户ID
	 */
	
	private Long userId;
	
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
	
	/** 区域编号--模糊查询 */
	private String matchCode;

	/** 部门类型(3.小企业贷款 4.车贷) */
	private Integer deptType;

	private List<String> areaList;

	private List<Long> idList;
		
	private Integer isDeptPerson;
	
	private Integer isDeleted;
	
	

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

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

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getSalesDeptId() {
		return salesDeptId;
	}

	public void setSalesDeptId(String salesDeptId) {
		this.salesDeptId = salesDeptId;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public Long getWorkPlaceInfoId() {
		return workPlaceInfoId;
	}

	public void setWorkPlaceInfoId(Long workPlaceInfoId) {
		this.workPlaceInfoId = workPlaceInfoId;
	}

	public List<String> getAreaList() {
		return areaList;
	}

	public void setAreaList(List<String> areaList) {
		this.areaList = areaList;
	}

	public String getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}

	public Integer getDeptType() {
		return deptType;
	}

	public void setDeptType(Integer deptType) {
		this.deptType = deptType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getServiceTel() {
		return serviceTel;
	}

	public void setServiceTel(String serviceTel) {
		this.serviceTel = serviceTel;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public List<Long> getIdList() {
		return idList;
	}

	public void setIdList(List<Long> idList) {
		this.idList = idList;
	}

	public String getMatchCode() {
		return matchCode;
	}

	public void setMatchCode(String matchCode) {
		this.matchCode = matchCode;
	}
	public Integer getIsDeptPerson() {
		return isDeptPerson;
	}

	public void setIsDeptPerson(Integer isDeptPerson) {
		this.isDeptPerson = isDeptPerson;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

}
