package com.ezendai.credit2.system.model;

import com.ezendai.credit2.framework.model.BaseModel;

/***
 * 
 * <pre>
 * </pre>
 *
 * @author HQ-AT6
 * @version $Id: SysLog.java, v 0.1 2014年7月22日 下午3:38:21 HQ-AT6 Exp $
 */
public class OrganSalesDepartment extends BaseModel {
	
	/**  */
	private static final long serialVersionUID = -2543852808564991431L;
	
	private Long organId;
	private Long salesDeptId;
	
	

	private String organCode;//内部机构代码
	private String areaName;//营业网点名称
	public String getOrganCode() {
		return organCode;
	}
	public void setOrganCode(String organCode) {
		this.organCode = organCode;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	
	public Long getOrganId() {
		return organId;
	}
	public void setOrganId(Long organId) {
		this.organId = organId;
	}
	public Long getSalesDeptId() {
		return salesDeptId;
	}
	public void setSalesDeptId(Long salesDeptId) {
		this.salesDeptId = salesDeptId;
	}
}