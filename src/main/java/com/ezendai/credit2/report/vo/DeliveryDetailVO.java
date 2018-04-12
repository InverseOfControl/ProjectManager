package com.ezendai.credit2.report.vo;

import java.util.Date;
import java.util.List;

import com.ezendai.credit2.framework.vo.BaseVO;

/**   
*    
* 项目名称：credit2-report   
* 类名称：DeliveryDetailVO   
* 类描述：   
* 创建人：liboyan   
* 创建时间：2015年11月6日 上午10:06:43   
* 修改人：liboyan   
* 修改时间：2015年11月6日 上午10:06:43   
* 修改备注：   
* @version    
*    
*/
public class DeliveryDetailVO extends BaseVO {


	private static final long serialVersionUID = 1L;
	
	/**营业部ID*/
	private Long salesDeptId;
	
	/**借款类型*/
	private Integer productType;
	
	/**产品ID*/
	private Integer productId;
	
	/**首次申请日期，开始日期*/
	private Date requestDateStart;
	
	/**结束日期*/
	private Date requestDateEnd;
	
	/**借款状态*/
	private Integer  status;

	private List<Integer> statusList;
	
	private List<Integer> productIdList;
	
	private List<Long> salesDeptIdList;
	
	/**车贷营业部*/
	private String carLoanDept;
	
	/**小企业贷营业部*/
	private String  seLoanDept;
	
	public List<Integer> getProductIdList() {
		return productIdList;
	}
	public void setProductIdList(List<Integer> productIdList) {
		this.productIdList = productIdList;
	}
	public List<Integer> getStatusList() {
		return statusList;
	}
	public void setStatusList(List<Integer> statusList) {
		this.statusList = statusList;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long getSalesDeptId() {
		return salesDeptId;
	}
	public void setSalesDeptId(Long salesDeptId) {
		this.salesDeptId = salesDeptId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Date getRequestDateStart() {
		return requestDateStart;
	}
	public void setRequestDateStart(Date requestDateStart) {
		this.requestDateStart = requestDateStart;
	}
	public Date getRequestDateEnd() {
		return requestDateEnd;
	}
	public void setRequestDateEnd(Date requestDateEnd) {
		this.requestDateEnd = requestDateEnd;
	}
	public Integer getProductType() {
		return productType;
	}
	public void setProductType(Integer productType) {
		this.productType = productType;
	}
	public String getCarLoanDept() {
		return carLoanDept;
	}
	public void setCarLoanDept(String carLoanDept) {
		this.carLoanDept = carLoanDept;
	}
	public String getSeLoanDept() {
		return seLoanDept;
	}
	public void setSeLoanDept(String seLoanDept) {
		this.seLoanDept = seLoanDept;
	}
	public List<Long> getSalesDeptIdList() {
		return salesDeptIdList;
	}
	public void setSalesDeptIdList(List<Long> salesDeptIdList) {
		this.salesDeptIdList = salesDeptIdList;
	}




	
	
	
	
}
