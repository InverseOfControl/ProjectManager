package com.ezendai.credit2.system.model;

import java.util.Date;

import com.ezendai.credit2.framework.model.BaseModel;

/**
 * 
 * <pre>
 * 系统工作日志Model
 * </pre>
 *
 * @author zhangshihai
 * @version $Id: SysJobLog.java, v 0.1 2014年9月4日 上午11:39:43 zhangshihai Exp $
 */
public class SysJobLog extends BaseModel {

	private static final long serialVersionUID = -3532833102919942061L;

	/** 名字 */
	private String name;
	
	/** 执行IP */
	private String runIP;

	/** 开始时间 */
	private Date startTime;

	/** 结束时间 */
	private Date endTime;

	/** 处理结果状态(1:成功;2:部分成功;3:失败) */
	private Integer resultState;

	/** 处理条数 */
	private Long handleNum;

	/** 处理成功条数 */
	private Long successNum;

	/** 错误信息 */
	private String errorMessage;

	/** 备注 */
	private String remark;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getResultState() {
		return resultState;
	}

	public void setResultState(Integer resultState) {
		this.resultState = resultState;
	}

	public String getRunIP() {
		return runIP;
	}

	public void setRunIP(String runIP) {
		this.runIP = runIP;
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
}
