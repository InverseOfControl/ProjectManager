package com.ezendai.credit2.after.model;

 
import com.ezendai.credit2.framework.model.BaseModel;

public class TaskAllocationRule extends BaseModel {
	 
	 
	 
	 
	 
	private static final long serialVersionUID = -5200601342962759617L;
	
 
	  
	    
	private String userName;
	private String userLoginName;
	private Integer num;
	private Long salesDeptId;
	private Long ruleId;
	private String salesDeptName; 
	private String taskId;
	private Integer status;
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	private String ruleStrId;
	public String getRuleStrId() {
		return ruleStrId;
	}
	public void setRuleStrId(String ruleStrId) {
		this.ruleStrId = ruleStrId;
	}
	public String getSalesDeptName() {
		return salesDeptName;
	}
	public void setSalesDeptName(String salesDeptName) {
		this.salesDeptName = salesDeptName;
	}
	public Long getRuleId() {
		return ruleId;
	}
	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}
	public Long getSalesDeptId() {
		return salesDeptId;
	}
	public void setSalesDeptId(Long salesDeptId) {
		this.salesDeptId = salesDeptId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserLoginName() {
		return userLoginName;
	}
	public void setUserLoginName(String userLoginName) {
		this.userLoginName = userLoginName;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}

	
}