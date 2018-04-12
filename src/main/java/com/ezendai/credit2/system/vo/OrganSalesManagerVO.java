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
public class OrganSalesManagerVO extends BaseVO {
	
	/**  */
	private static final long serialVersionUID = -5832912648948772403L;
	
	private Long organId;
	private Long userId;
	private String salesManager;
	private String code;
	
	public Long getOrganId() {
		return organId;
	}
	public void setOrganId(Long organId) {
		this.organId = organId;
	}
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getSalesManager() {
		return salesManager;
	}
	public void setSalesManager(String salesManager) {
		this.salesManager = salesManager;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
}