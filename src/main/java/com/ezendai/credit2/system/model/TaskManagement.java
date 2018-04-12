package com.ezendai.credit2.system.model;

import java.util.Date;

import com.ezendai.credit2.framework.model.BaseModel;

/**
 * 定时任务管理表模型
 * 
 * @author lizhuang
 * @Date 2016-10-20
 * @Time 下午 5:40
 */
public class TaskManagement extends BaseModel {

	private static final long serialVersionUID = 1L;

	/** ID */
	private Long id;
	/** 任务名称 */
	private String taskName;
	/** 类名 */
	private String className;
	/** 日期 */
	private String executionDate;
	/** 时间 */
	private Date executionTime;
	/** 任务详述 */
	private String taskDetailed;

	/** 是否逻辑删除 */
	private Long isDeleted;
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
	/** 版本 */
	private Long version;

	/** 最近一次执行时间开始时间 */
	private Date startTime;
	/** 处理结果状态(1:成功;2:部分成功;3:失败) */
	private Integer resultState;
	/** 用时(s) */
	private Long when;
	/** 处理条数 */
	private Long handleNum;
	/** 处理条数成功 */
	private Long successNum;
	/** 错误信息 */
	private String errorMessage;
	/** 备注 */
	private String remark;
	/** 运行机器IP */
	private String runIp;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getExecutionDate() {
		return executionDate;
	}

	public void setExecutionDate(String executionDate) {
		this.executionDate = executionDate;
	}

	public Date getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(Date executionTime) {
		this.executionTime = executionTime;
	}

	public String getTaskDetailed() {
		return taskDetailed;
	}

	public void setTaskDetailed(String taskDetailed) {
		this.taskDetailed = taskDetailed;
	}

	public Long getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Long isDeleted) {
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

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Integer getResultState() {
		return resultState;
	}

	public void setResultState(Integer resultState) {
		this.resultState = resultState;
	}

	public Long getWhen() {
		return when;
	}

	public void setWhen(Long when) {
		this.when = when;
	}

	public Long getHandleNum() {
		return handleNum;
	}

	public void setHandleNum(Long handleNum) {
		this.handleNum = handleNum;
	}

	public Long getSuccessNum() {
		return successNum;
	}

	public void setSuccessNum(Long successNum) {
		this.successNum = successNum;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRunIp() {
		return runIp;
	}

	public void setRunIp(String runIp) {
		this.runIp = runIp;
	}
}
