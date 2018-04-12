package com.ezendai.credit2.audit.model;



import java.util.Date;

import com.ezendai.credit2.framework.model.BaseModel;

/**   
*    
* 项目名称：credit2-main   
* 类名称：LoanTotal   
* 类描述：   此类用于记录初审员工作量
* 创建人：liboyan   
* 创建时间：2015年11月2日 上午11:34:06   
* 修改人：liboyan   
* 修改时间：2015年11月2日 上午11:34:06   
* 修改备注：   
* @version    
*    
*/
public class LoanTotal extends BaseModel {
	private static final long serialVersionUID = 8248204248949537912L;
	/**初审员ID*/
	private Long firstTrialId;
	/**初审员姓名*/
	private String name;
	/**已处理*/
	private Long settled;
	/**未处理*/
	private Long untreated;
	/**当前日期*/
	private Date toDate;

	
	
	public java.util.Date getToDate() {
		return toDate;
	}
	public void setToDate(java.util.Date toDate) {
		this.toDate = toDate;
	}
	public Long getSettled() {
		return settled;
	}
	public void setSettled(Long settled) {
		this.settled = settled;
	}
	public Long getUntreated() {
		return untreated;
	}
	public void setUntreated(Long untreated) {
		this.untreated = untreated;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getFirstTrialId() {
		return firstTrialId;
	}
	public void setFirstTrialId(Long firstTrialId) {
		this.firstTrialId = firstTrialId;
	}
	
	
}
