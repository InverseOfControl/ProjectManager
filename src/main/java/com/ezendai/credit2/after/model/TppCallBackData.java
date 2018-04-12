package com.ezendai.credit2.after.model;

import java.math.BigDecimal;
import java.util.Date;

import com.ezendai.credit2.framework.model.BaseModel;

/**
 * <pre>
 * 回盘表
 * </pre>
 * @author 陈忠星
 * @Description:
 */
public class TppCallBackData extends BaseModel {
	/**	 */
	private static final long serialVersionUID = -8356965495818356073L;
	/** 报盘ID */
	private Long offerId;
	/** TPP系统请求编号 */
	private String requestCode;
	/** 返回代码 */
	private String returnCode;
	/** 返回信息 */
	private String returnInfo;
	/** 处理状态 */
	private Integer handleState;
	/** 接收时间 */
	private Date receiveTime;
	/** 处理时间 */
	private Date handleTime;
	/** 备注 */
	private String remark;
	
	/** 成功金额*/
	private BigDecimal successAmount;
	/** 交易流水号*/
	private Long tradeFlow;
	/** 失败原因*/
	private String failReason;
	/** 任务id*/
	private Long taskId;

	
	
	public BigDecimal getSuccessAmount() {
		return successAmount;
	}

	public void setSuccessAmount(BigDecimal successAmount) {
		this.successAmount = successAmount;
	}

	public Long getTradeFlow() {
		return tradeFlow;
	}

	public void setTradeFlow(Long tradeFlow) {
		this.tradeFlow = tradeFlow;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Long getOfferId() {
		return offerId;
	}

	public void setOfferId(Long offerId) {
		this.offerId = offerId;
	}

	public String getRequestCode() {
		return requestCode;
	}

	public void setRequestCode(String requestCode) {
		this.requestCode = requestCode;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnInfo() {
		return returnInfo;
	}

	public void setReturnInfo(String returnInfo) {
		this.returnInfo = returnInfo;
	}

	public Integer getHandleState() {
		return handleState;
	}

	public void setHandleState(Integer handleState) {
		this.handleState = handleState;
	}

	public Date getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	public Date getHandleTime() {
		return handleTime;
	}

	public void setHandleTime(Date handleTime) {
		this.handleTime = handleTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}