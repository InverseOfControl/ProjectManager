package com.ezendai.credit2.system.vo;

import java.util.Date;
import java.util.List;

import com.ezendai.credit2.framework.vo.BaseVO;

public class SysUserVO extends BaseVO {

	private static final long serialVersionUID = -3564129658682780303L;

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
	/** 区域ID */
	private List<Long> areaIdList;
	/** 用户类型List */
	private List<Integer> userTypeList;
	/**是否开启接单*/
	private Integer acceptAuditTask;
	/***/
	private List<Integer> productIdList;
	/***/
	private String matchDataPermission;
	/**产品List*/
	private  Integer[]  productList;
	/**分组List*/
	private Integer[]  groupList;
	


	public Integer[] getProductList() {
		return productList;
	}

	public void setProductList(Integer[] productList) {
		this.productList = productList;
	}

	public Integer[] getGroupList() {
		return groupList;
	}

	public void setGroupList(Integer[] groupList) {
		this.groupList = groupList;
	}

	public String getMatchDataPermission() {
		return matchDataPermission;
	}

	public void setMatchDataPermission(String matchDataPermission) {
		this.matchDataPermission = matchDataPermission;
	}

	public Integer getAcceptAuditTask() {
		return acceptAuditTask;
	}

	public void setAcceptAuditTask(Integer acceptAuditTask) {
		this.acceptAuditTask = acceptAuditTask;
	}

	public Long getSalesTeamId() {
		return salesTeamId;
	}

	public void setSalesTeamId(Long salesTeamId) {
		this.salesTeamId = salesTeamId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
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

	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	public List<Integer> getUserTypeList() {
		return userTypeList;
	}

	public void setUserTypeList(List<Integer> userTypeList) {
		this.userTypeList = userTypeList;
	}

	public List<Long> getAreaIdList() {
		return areaIdList;
	}

	public void setAreaIdList(List<Long> areaIdList) {
		this.areaIdList = areaIdList;
	}

	public List<Integer> getProductIdList() {
		return productIdList;
	}

	public void setProductIdList(List<Integer> productIdList) {
		this.productIdList = productIdList;
	}

}
