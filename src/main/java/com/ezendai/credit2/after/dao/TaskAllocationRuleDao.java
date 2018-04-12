package com.ezendai.credit2.after.dao;

import java.util.List;

import com.ezendai.credit2.after.model.LateDetails;
import com.ezendai.credit2.after.model.TaskAllocationRule;
import com.ezendai.credit2.after.vo.TaskAllocationRuleVO;
import com.ezendai.credit2.framework.dao.BaseDao;
import com.ezendai.credit2.framework.util.Pager;

public interface TaskAllocationRuleDao extends BaseDao<TaskAllocationRule> {
	
	 
	/**
	 * <pre>
	 * 查询规则
	 * @param returnDate
	 * @return
	 */
	Pager TaskAllocationRuleWithPG(TaskAllocationRuleVO ruleVO);
	void insertTaskAllocationRule (TaskAllocationRule rule);
	void updateTaskAllocationRule (TaskAllocationRule rule);
 
	List<LateDetails> getLateDetailsList(TaskAllocationRuleVO ruleVO);
	
	List<TaskAllocationRule> getTaskAllocationRuleBySalesDeptId(TaskAllocationRuleVO ruleVO);
	
	List<TaskAllocationRule> getCollectorsBySalesDeptId(TaskAllocationRuleVO ruleVO);
	List<TaskAllocationRule> getAllEffectiveBySalesDeptId(TaskAllocationRuleVO ruleVO);
	
	int getLateDetailsListCount(TaskAllocationRuleVO ruleVO);
	
}
