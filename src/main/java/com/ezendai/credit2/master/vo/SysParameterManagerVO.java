package com.ezendai.credit2.master.vo;

import java.util.Date;

import com.ezendai.credit2.framework.vo.BaseVO;

public class SysParameterManagerVO extends BaseVO{

	private static final long serialVersionUID = 2995799489990661725L;

	private Long id;

	/** 属性标识 **/
	private String code;

	/** 属性名 **/
	private String name;

	/** 属性值 **/
	private String parameterValue;

	/** 输入类型（1:文本框，2:下拉选择框，值为“是/否”） 现写死为1**/
	private Integer inputType;

	/** 备注 **/
	private String remark;

	/** 创建者id **/
	private Long creatorId;

	/** 创建者 **/
	private String creator;

	/** 创建时间 **/
	private Date createdTime;
	
	/** 创建时间 **/
	private Date createdTimeStart;
	
	/** 创建时间 **/
	private Date createdTimeEnd;

	/** 修改者id **/
	private Long modifierId;

	/** 修改者 **/
	private String modifier;
	
	/** 修改时间 **/
	private Date modifiedTime;

	/** 修改时间 **/
	private Date modifiedTimeStart;
	
	/** 修改时间 **/
	private Date modifiedTimeEnd;

	/** 版本 默认1**/
	private Integer spmversion;
	
	/** 是否可用标志**/
	private Integer isDisabled;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParameterValue() {
		return parameterValue;
	}

	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}

	public Integer getInputType() {
		return inputType;
	}

	public void setInputType(Integer inputType) {
		this.inputType = inputType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}


	public Long getModifierId() {
		return modifierId;
	}

	public void setModifierId(Long modifierId) {
		this.modifierId = modifierId;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	

	public Integer getSpmversion() {
		return spmversion;
	}

	public void setSpmversion(Integer spmversion) {
		if(spmversion == null){
			this.spmversion = 1;
		}
		this.spmversion = spmversion;
	}

	public Integer getIsDisabled() {
		return isDisabled;
	}

	public void setIsDisabled(Integer isDisabled) {
		this.isDisabled = isDisabled;
	}

	public Date getCreatedTimeStart() {
		return createdTimeStart;
	}

	public void setCreatedTimeStart(Date createdTimeStart) {
		this.createdTimeStart = createdTimeStart;
	}

	public Date getCreatedTimeEnd() {
		return createdTimeEnd;
	}

	public void setCreatedTimeEnd(Date createdTimeEnd) {
		this.createdTimeEnd = createdTimeEnd;
	}

	public Date getModifiedTimeStart() {
		return modifiedTimeStart;
	}

	public void setModifiedTimeStart(Date modifiedTimeStart) {
		this.modifiedTimeStart = modifiedTimeStart;
	}

	public Date getModifiedTimeEnd() {
		return modifiedTimeEnd;
	}

	public void setModifiedTimeEnd(Date modifiedTimeEnd) {
		this.modifiedTimeEnd = modifiedTimeEnd;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	
	
	
}
