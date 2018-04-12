package com.ezendai.credit2.system.model;

import java.util.Date;

import com.ezendai.credit2.framework.model.BaseModel;
import com.ezendai.credit2.master.model.BaseArea;

public class SysUser extends BaseModel {
	private static final long serialVersionUID = 8248204248949537912L;

	/** 销售团队ID */
	private Long salesTeamId;
	/** 账号 */
	private String loginName;
	/** 名字 */
	private String name;
	/** 邮件 */
	private String email;
	/** 电话 */
	private String telephone;
	/** 手机 */
	private String cellphone;
	/** 登录重试次数 */
	private Integer loginRetry;
	/** 登录密码 */
	private String signPassword;
	/** 登录密码过期日期 */
	private Date signPasswordExpireDate;
	/** 上次登录时间 */
	private Date lastLoginTime;
	/** 上次登录IP地址 */
	private String lastLoginIp;
	/** 用户类型 */
	private Integer userType;
	/** 是否逻辑删除 */
	private Integer isDeleted;
	/** 状态 */
	private Integer status;
	/** 员工编号 */
	private String code;
	/** 数据权限 */
	private String dataPermission;
	/** 全名字 */
	private String fullName;
	/** 区域ID */
	private Long areaId;
	/** 区域信息 */
	private BaseArea baseArea;
	/**初审员是否开启接单*/
	private Integer acceptAuditTask;
	
	/**
	 * 是否增加其他网点 0否1是
	 */
	private Long isAddOtherDepts;
	
	
	public Long getIsAddOtherDepts() {
		return isAddOtherDepts;
	}

	public void setIsAddOtherDepts(Long isAddOtherDepts) {
		this.isAddOtherDepts = isAddOtherDepts;
	}

	public Integer getAcceptAuditTask() {
		return acceptAuditTask;
	}

	public void setAcceptAuditTask(Integer acceptAuditTask) {
		this.acceptAuditTask = acceptAuditTask;
	}

	@Deprecated
	public Long getSalesTeamId() {
		return salesTeamId;
	}

	@Deprecated
	public void setSalesTeamId(Long salesTeamId) {
		this.salesTeamId = salesTeamId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public Integer getLoginRetry() {
		return loginRetry;
	}

	public void setLoginRetry(Integer loginRetry) {
		this.loginRetry = loginRetry;
	}

	public String getSignPassword() {
		return signPassword;
	}

	public void setSignPassword(String signPassword) {
		this.signPassword = signPassword;
	}

	public Date getSignPasswordExpireDate() {
		return signPasswordExpireDate;
	}

	public void setSignPasswordExpireDate(Date signPasswordExpireDate) {
		this.signPasswordExpireDate = signPasswordExpireDate;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public String getDataPermission() {
		return dataPermission;
	}

	public void setDataPermission(String dataPermission) {
		this.dataPermission = dataPermission;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public BaseArea getBaseArea() {
		return baseArea;
	}

	public void setBaseArea(BaseArea baseArea) {
		this.baseArea = baseArea;
	}

	@Deprecated
	public Long getAreaId() {
		return areaId;
	}

	@Deprecated
	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}
	
	@Override
	public int hashCode() {
		return code.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj != null && this.getClass() == obj.getClass()) {
			SysUser sys = (SysUser) obj;
			if(sys.getCode() != null) {
				return code.equals(sys.getCode());
			}
		}
		return false;
	}

}
