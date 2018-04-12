package com.ezendai.credit2.after.vo;

import com.ezendai.credit2.framework.vo.BaseVO;
/**
 * 城市服务电话实体类
 * @author YM10161
 *
 */
public class SMSConfigrationVO extends BaseVO{

	/**
	 * 实现序列化
	 */
	private static final long serialVersionUID = 6620819306912462051L;

	private String cityName;//城市名称
	private String cityId;//城市ID
	private String servicePhone;//服务电话号码
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
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
