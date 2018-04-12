package com.ezendai.credit2.master.model;

import com.ezendai.credit2.framework.model.BaseModel;

/**
 *工作地点信息实体类
 * Author: kimi
 * Date: 14-6-24
 * Time: 上午9:48
 */
public class WorkPlaceInfo extends BaseModel {

	/**  */
	private static final long serialVersionUID = 7133831182606790877L;
	
	private Long id;
	/**  服务电话 */
	private String tel;
	/**  城市编号 */
	private String cityNo;
	/** 区域名称 */
	private String zoneCode;
	/**  区号 */
	private String site;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getCityNo() {
		return cityNo;
	}

	public void setCityNo(String cityNo) {
		this.cityNo = cityNo;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getZoneCode() {
		return zoneCode;
	}

	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}

}
