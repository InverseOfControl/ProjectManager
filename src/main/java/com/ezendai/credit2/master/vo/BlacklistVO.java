package com.ezendai.credit2.master.vo;

import java.util.Date;

import com.ezendai.credit2.framework.vo.BaseVO;

/**
 * 拒绝原因VO模型
 * 
 * @author chenqi
 * @version 1.0, 2014-08-28
 * @since 1.0
 */
public class BlacklistVO extends BaseVO {

	private static final long serialVersionUID = 6575925924126685755L;
	/** 姓名*/
	private String name;
	/** 身份证号码*/
	private String idnum;
	/** 拒绝时间*/
	private Date rejectTime;
	/**手机号码*/
	private String mphone;
	/**固定电话*/
	private String tel;
	/**销售团队id*/
	private Long salesDepartmentId;
	/**贷款类型*/
	private Integer loanType;
	/**限制提交天数*/
	private Integer limitDays;
	/**公司名称*/
	private String company;
	/**来源*/
	private Integer comeFrom;
	/**拒绝原因id*/
	private Long rejectReasonId;
	/**是否逻辑删除*/
	private Integer isDeleted;
	/**备注*/
	private String remark;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdnum() {
		return idnum;
	}

	public void setIdnum(String idnum) {
		this.idnum = idnum;
	}

	public Date getRejectTime() {
		return rejectTime;
	}

	public void setRejectTime(Date rejectTime) {
		this.rejectTime = rejectTime;
	}

	public String getMphone() {
		return mphone;
	}

	public void setMphone(String mphone) {
		this.mphone = mphone;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Long getSalesDepartmentId() {
		return salesDepartmentId;
	}

	public void setSalesDepartmentId(Long salesDepartmentId) {
		this.salesDepartmentId = salesDepartmentId;
	}

	public Integer getLoanType() {
		return loanType;
	}

	public void setLoanType(Integer loanType) {
		this.loanType = loanType;
	}

	public Integer getLimitDays() {
		return limitDays;
	}

	public void setLimitDays(Integer limitDays) {
		this.limitDays = limitDays;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public Integer getComeFrom() {
		return comeFrom;
	}

	public void setComeFrom(Integer comeFrom) {
		this.comeFrom = comeFrom;
	}

	public Long getRejectReasonId() {
		return rejectReasonId;
	}

	public void setRejectReasonId(Long rejectReasonId) {
		this.rejectReasonId = rejectReasonId;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
