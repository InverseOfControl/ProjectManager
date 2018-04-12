package com.ezendai.credit2.after.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.after.dao.TaskAllocationRuleDao;
import com.ezendai.credit2.after.model.LateDetails;
import com.ezendai.credit2.after.model.TaskAllocationRule;
import com.ezendai.credit2.after.vo.CollectionCreateCasesVO;
import com.ezendai.credit2.after.vo.TaskAllocationRuleVO;
import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;
import com.ezendai.credit2.framework.util.BeanUtil;
import com.ezendai.credit2.framework.util.Pager;

/***
 * <pre>
 * 
 * 
 * </pre>
 *
 * @author  
 * @version  
 */
@Repository
public class TaskAllocationRuleDaoImpl extends BaseDaoImpl<TaskAllocationRule> implements TaskAllocationRuleDao {

 
	 
	@Override
	public Pager TaskAllocationRuleWithPG(TaskAllocationRuleVO ruleVO) {
		// TODO Auto-generated method stub
		Object count = getSqlSession().selectOne(getIbatisMapperNameSpace() + ".taskAllocationRuleCount", ruleVO);
		int totalCount = Integer.parseInt(count.toString());
		Pager pg = ruleVO.getPager();
		pg.setTotalCount(totalCount);
		pg.calStart();
		List rstList = null;
		try {
			rstList = getSqlSession().selectList(getIbatisMapperNameSpace() + ".taskAllocationRuleWithPG", ruleVO);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		BeanUtil.copyProperties(pg, ruleVO);
		pg.setTotalCount(totalCount);
		pg.setResultList(rstList);
		 return pg;
	}

	@Override
	public void insertTaskAllocationRule(TaskAllocationRule rule) {
		// TODO Auto-generated method stub
		getSqlSession().insert(getIbatisMapperNameSpace() + ".insertTaskAllocationRule", rule);
	}

	@Override
	public void updateTaskAllocationRule(TaskAllocationRule rule) {
		// TODO Auto-generated method stub
		getSqlSession().update(getIbatisMapperNameSpace() + ".updateTaskAllocationRule", rule);
	}

	@Override
	public List<LateDetails> getLateDetailsList(
			TaskAllocationRuleVO ruleVO) {
		// TODO Auto-generated method stub
		return getSqlSession().selectList(getIbatisMapperNameSpace() + ".getLateDetailsList", ruleVO);
	}

	@Override
	public List<TaskAllocationRule> getTaskAllocationRuleBySalesDeptId(
			TaskAllocationRuleVO ruleVO) {
		// TODO Auto-generated method stub
		return getSqlSession().selectList(getIbatisMapperNameSpace() + ".getTaskAllocationRuleBySalesDeptId", ruleVO);
	}

	@Override
	public List<TaskAllocationRule> getCollectorsBySalesDeptId(
			TaskAllocationRuleVO ruleVO) {
		// TODO Auto-generated method stub
		return getSqlSession().selectList(getIbatisMapperNameSpace() + ".getCollectorsBySalesDeptId", ruleVO);
	}

	@Override
	public int getLateDetailsListCount(TaskAllocationRuleVO ruleVO) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne(getIbatisMapperNameSpace() + ".getLateDetailsListCount", ruleVO);
	}

	@Override
	public List<TaskAllocationRule> getAllEffectiveBySalesDeptId(
			TaskAllocationRuleVO ruleVO) {
		// TODO Auto-generated method stub
		return getSqlSession().selectList(getIbatisMapperNameSpace() + ".getAllEffectiveBySalesDeptId", ruleVO);
	}
	 
	 
}