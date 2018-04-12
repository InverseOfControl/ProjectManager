package com.ezendai.credit2.apply.vo;

import java.util.Date;
import java.util.List;

import com.ezendai.credit2.framework.vo.BaseVO;

public class AttachmentDetailVO extends BaseVO {  
	/**  */
	private static final long serialVersionUID = -6119753331136717181L;
	/** 附件头信息id */
	private Long attachmentId;
	/** 附件原始名字 */
    private String originalName;
    /** 附件真实名字 */
    private String name;
    /** 附件大小 */
    private Long fileSize;
    /** 附件后缀 */
    private String suffix;
    /** 创建用户ID */
    private Long creatorId;
    /** 创建用户 */
    private String creator;
    /** 创建时间 */
    private Date createdTime;
    /** 更新用户ID */
    private Long modifierId;
    /** 更新用户 */
    private String modifier;
    /** 更新时间 */
    private Date modifiedTime;
    /** 备注 */
    private String remark;
    /** 版本 */
    private Long version;
    
    private List<Long> idList;
    /** 是否删除 1 已经删除  0 未删除 */
  	private Integer isDeleted;	
  

	public Long getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Long attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

	public List<Long> getIdList() {
		return idList;
	}

	public void setIdList(List<Long> idList) {
		this.idList = idList;
	}
	  public Integer getIsDeleted() {
			return isDeleted;
		}

		public void setIsDeleted(Integer isDeleted) {
			this.isDeleted = isDeleted;
		}
}