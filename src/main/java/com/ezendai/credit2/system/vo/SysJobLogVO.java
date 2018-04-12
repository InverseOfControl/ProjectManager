package com.ezendai.credit2.system.vo;

import java.util.Date;

import com.ezendai.credit2.framework.vo.BaseVO;

/**
 * 
 * <pre>
 * 系统工作日志VO
 * </pre>
 *
 * @author zhangshihai
 * @version $Id: SysJobLogVO.java, v 0.1 2014年9月4日 上午11:51:13 zhangshihai Exp $
 */
/**
 * @author YM10116
 *
 */
public class SysJobLogVO extends BaseVO {

	private static final long serialVersionUID = -1154324331646487509L;

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

	/** 成功处理条数 */
	private Long successNum;

	/** 错误信息 */
	private String errorMessage;

	/** 备注 */
	private String remark;
	// 有效状态
	private String effectiveState;
	// 无效状态
	private String invalidState;

	public String getEffectiveState() {
		return effectiveState;
	}

	public void setEffectiveState(String effectiveState) {
		this.effectiveState = effectiveState;
	}

	public String getInvalidState() {
		return invalidState;
	}

	public void setInvalidState(String invalidState) {
		this.invalidState = invalidState;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRunIP() {
		return runIP;
	}

	public void setRunIP(String runIP) {
		this.runIP = runIP;
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
