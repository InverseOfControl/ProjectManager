package com.ezendai.credit2.system.vo;

import com.ezendai.credit2.framework.vo.BaseVO;

/***
 * 
 * <pre>
 * </pre>
 *
 * @author HQ-AT6
 * @version $Id: SysLog.java, v 0.1 2014年7月22日 下午3:38:21 HQ-AT6 Exp $
 */
public class OrganSalesDepartmentVO extends BaseVO {
	
	/**  */
	private static final long serialVersionUID = -2543852808564991431L;
	
	private Long organId;
	private Long salesDeptId;
	
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