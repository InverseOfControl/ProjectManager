package com.ezendai.credit2.report.vo;

import com.ezendai.credit2.framework.vo.BaseVO;

public class OverdueMsgLogVO extends BaseVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -109030546681370383L;

    private String name;

    private String idNum;

    private Integer status;


    private String buildStartDate;
    
    private String buildEndDate;


    private String sendStartDate;
    
    private String sendEndDate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdNum() {
		return idNum;
	}

	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getBuildStartDate() {
		return buildStartDate;
	}

	public void setBuildStartDate(String buildStartDate) {
		this.buildStartDate = buildStartDate;
	}

	public String getBuildEndDate() {
		return buildEndDate;
	}

	public void setBuildEndDate(String buildEndDate) {
		this.buildEndDate = buildEndDate;
	}

	public String getSendStartDate() {
		return sendStartDate;
	}

	public void setSendStartDate(String sendStartDate) {
		this.sendStartDate = sendStartDate;
	}

	public String getSendEndDate() {
		return sendEndDate;
	}

	public void setSendEndDate(String sendEndDate) {
		this.sendEndDate = sendEndDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}
