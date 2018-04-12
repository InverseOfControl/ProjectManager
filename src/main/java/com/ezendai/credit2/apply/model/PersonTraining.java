package com.ezendai.credit2.apply.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ezendai.credit2.framework.model.BaseModel;

public class PersonTraining extends BaseModel {

	private static final long serialVersionUID = -5326344663309181966L;
	
	private Long id;
	private Long loanId;
	private String personIdnum;
	private String course;
	private String period;
	private Date entranceTime;
	private String schoolDistrict;
	private String entranceDate;
	

	
	
	public String getEntranceDate() {
		return entranceDate;
	}
	public void setEntranceDate(String entranceDate) throws ParseException {
		this.entranceDate = entranceDate;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM"); 
		this.entranceTime = sdf.parse(entranceDate);
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getLoanId() {
		return loanId;
	}
	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}
	

	public String getPersonIdnum() {
		return personIdnum;
	}
	public void setPersonIdnum(String personIdnum) {
		this.personIdnum = personIdnum;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public Date getEntranceTime() {
		return entranceTime;
	}
	public void setEntranceTime(Date entranceTime) {
		this.entranceTime = entranceTime;
	}
	public String getSchoolDistrict() {
		return schoolDistrict;
	}
	public void setSchoolDistrict(String schoolDistrict) {
		this.schoolDistrict = schoolDistrict;
	}
	
	
	

	
	

	

}
