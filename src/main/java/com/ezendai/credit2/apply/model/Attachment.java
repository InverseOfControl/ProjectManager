package com.ezendai.credit2.apply.model;

import java.util.Date;

import com.ezendai.credit2.framework.model.BaseModel;

public class Attachment extends BaseModel {
	private static final long serialVersionUID = 1864842669247031340L;

	/** 客户ID */
	private Long personId;

	/** 担保人ID */
	private Long guaranteeId;

	/** 标题 */
	private String title;

	/** 附件类型 */
	private String category;

	/** 文件大小 */
	private Long fileSize;

	/** 文件后缀 */
	private String suffix;

	/** 文件路径 */
	private String filePath;

	/** 上传时间 */
	private Date uploadDate;

	/** 操作人员 */
	private String operator;

	/** 操作备注 */
	private String remark;
	/** 分类名 */
	private String classifyName;
	/** 分类层级 */
	private Integer type;
	/**借款ID */
	private Long loanId;

	//是否删除 1 已经删除  0 未删除
	private Integer isDeleted;
	//创建用户ID
	private Long creatorId;
	//创建用户
	private String creator;
	//创建时间
	private Date createdTime;
	//更新用户ID
	private Long modifierId;
	//更新用户
	private String modifier;
	//更新时间
	private Date modifiedTime;

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public Long getGuaranteeId() {
		return guaranteeId;
	}

	public void setGuaranteeId(Long guaranteeId) {
		this.guaranteeId = guaranteeId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getClassifyName() {
		return classifyName;
	}

	public void setClassifyName(String classifyName) {
		this.classifyName = classifyName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
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

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
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

	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
}