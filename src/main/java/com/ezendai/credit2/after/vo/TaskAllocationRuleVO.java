package com.ezendai.credit2.after.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.ezendai.credit2.framework.vo.BaseVO;

/**
 * 
 * @author zhuyiguo
 * @version $Id: LoanVO.java, v 0.1 2014年6月26日 上午9:13:51 zhuyiguo Exp $
 */
public class TaskAllocationRuleVO  extends BaseVO {

	private static final long serialVersionUID = -5200601342962759617L;
	 
	
	private String name;
	
	
	private Long salesDeptId;
	
	private List<Long> salesDeptIdList;
	 
	private String startDate;
	public String getStartDate() {
		return startDate;
	}


	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}


	public List<Long> getSalesDeptIdList() {
		return salesDeptIdList;
	}


	public void setSalesDeptIdList(List<Long> salesDeptIdList) {
		this.salesDeptIdList = salesDeptIdList;
	}


	public Long getSalesDeptId() {
		return salesDeptId;
	}


	public void setSalesDeptId(Long salesDeptId) {
		this.salesDeptId = salesDeptId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	

 
}
