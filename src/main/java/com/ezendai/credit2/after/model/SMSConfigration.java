package com.ezendai.credit2.after.model;

import com.ezendai.credit2.framework.model.BaseModel;

public class SMSConfigration extends BaseModel{

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = -76187316778245457L;

	private String cityId;//城市ID
	private String cityName;//城市名称
	private String servicePhone;//服务电话号码
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getServicePhone() {
		return servicePhone;
	}
	public void setServicePhone(String servicePhone) {
		this.servicePhone = servicePhone;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
