package com.ezendai.credit2.after.model;

 
import java.math.BigDecimal;
import java.util.Date;

 

import com.ezendai.credit2.framework.model.BaseModel;

public class CollectionCasesRecord extends BaseModel {
	 
	 
	 
	 
	 
	private static final long serialVersionUID = -5200601342962759617L;
	private Long taskId;
	private Integer recordType;
	private String recordAddress;
	private String recordContent;
	private String recordTel;
	private String optname;
	private String recordMemo;
	private String recordName;
	private Date recordStartDate;
	private Long operaorId;
	private Date recordEndDate;
	public String getRecordStartDateStr() {
		return recordStartDateStr;
	}
	public void setRecordStartDateStr(String recordStartDateStr) {
		this.recordStartDateStr = recordStartDateStr;
	}
	public String getRecordEndDateStr() {
		return recordEndDateStr;
	}
	public void setRecordEndDateStr(String recordEndDateStr) {
		this.recordEndDateStr = recordEndDateStr;
	}
	private String recordStartDateStr;
	private String recordEndDateStr;
	public String getOptname() {
		return optname;
	}
	public void setOptname(String optname) {
		this.optname = optname;
	}
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	 
	public Integer getRecordType() {
		return recordType;
	}
	public void setRecordType(Integer recordType) {
		this.recordType = recordType;
	}
	public String getRecordAddress() {
		return recordAddress;
	}
	public void setRecordAddress(String recordAddress) {
		this.recordAddress = recordAddress;
	}
	public String getRecordContent() {
		return recordContent;
	}
	public void setRecordContent(String recordContent) {
		this.recordContent = recordContent;
	}
	public String getRecordTel() {
		return recordTel;
	}
	public void setRecordTel(String recordTel) {
		this.recordTel = recordTel;
	}
	public String getRecordMemo() {
		return recordMemo;
	}
	public void setRecordMemo(String recordMemo) {
		this.recordMemo = recordMemo;
	}
	public String getRecordName() {
		return recordName;
	}
	public void setRecordName(String recordName) {
		this.recordName = recordName;
	}
	public Date getRecordStartDate() {
		return recordStartDate;
	}
	public void setRecordStartDate(Date recordStartDate) {
		this.recordStartDate = recordStartDate;
	}
	public Long getOperaorId() {
		return operaorId;
	}
	public void setOperaorId(Long operaorId) {
		this.operaorId = operaorId;
	}
	public Date getRecordEndDate() {
		return recordEndDate;
	}
	public void setRecordEndDate(Date recordEndDate) {
		this.recordEndDate = recordEndDate;
	}

}