package com.ezendai.credit2.master.vo;

import com.ezendai.credit2.framework.vo.BaseVO;

/**
 * 
 * @Description: 报盘管理
 * @author 徐安龙
 * @date 2016年7月28日
 */
public class OfferManagerVO extends BaseVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**报盘日期**/
	private Integer day;

	/**前几天  默认0**/
	private Integer beforeDay = 0;
	
	/**后几天  默认0**/
	private Integer afterDay = 0;

	/**生成报盘时间**/
	private String generateTime;
	
	/**发送报盘时间**/
	private String sendTime;

	/**  状态（1-->启用 0-->禁用）   **/
	private Integer status = 1;
	
	private String remark;
	
	private Integer offerDay;

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public Integer getBeforeDay() {
		return beforeDay;
	}

	public void setBeforeDay(Integer beforeDay) {
		this.beforeDay = beforeDay;
	}

	public Integer getAfterDay() {
		return afterDay;
	}

	public void setAfterDay(Integer afterDay) {
		this.afterDay = afterDay;
	}

	public String getGenerateTime() {
		return generateTime;
	}

	public void setGenerateTime(String generateTime) {
		this.generateTime = generateTime;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getOfferDay() {
		return offerDay;
	}

	public void setOfferDay(Integer offerDay) {
		this.offerDay = offerDay;
	}

}
