package com.ezendai.credit2.system.vo;

import java.util.Date;

import com.ezendai.credit2.framework.vo.BaseVO;

/**
 * 
 * @author lizhuang
 * @Date 2016-10-20
 * @Time 下午 5:25
 */

public class TaskManagementVO extends BaseVO {

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

	public Long getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Long isDeleted) {
		this.isDeleted = isDeleted;
	}

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
}