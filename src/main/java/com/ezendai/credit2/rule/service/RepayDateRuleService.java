package com.ezendai.credit2.rule.service;

import java.util.List;

import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.rule.model.LoanRule;
import com.ezendai.credit2.rule.vo.LoanRuleVO;


/**
 * 
 * <pre>
 * 还款日规则
 * </pre>
 *
 * @author chenzx
 * @version $Id: DueDateRuleService.java, v 0.1 2014年8月22日 下午1:46:23 HQ-AT6 Exp $
 */
public interface RepayDateRuleService {
	/**
	 * 
	 * <pre>
	 * 根据参数分页查询规则
	 * </pre>
	 *
	 * @param loanRuleVO
	 * @return
	 */
	Pager findSysRuleByParam(LoanRuleVO loanRuleVO);
	/***
	 * 保存还款规则
	 * @param loanRule
	 * @return
	 * @see com.ezendai.credit2.rule.service.DueDateRuleService#insert(com.ezendai.credit2.rule.model.LoanRule)
	 */
	String insert(LoanRule loanRule);
	/***
	 * 
	 * <pre>
	 * 根据ID获取规则
	 * </pre>
	 *
	 * @param ruleId
	 * @return
	 */
	LoanRule get(Long ruleId);
	/***
	 * 
	 * <pre>
	 * 更新规则
	 * </pre>
	 *
	 * @param LoanRuleVo
	 * @return
	 */
	int update(LoanRuleVO LoanRuleVo);
	/***
	 * 
	 * <pre>
	 * 根据List<Long> idList 删除规则
	 * </pre>
	 *
	 * @param LoanRuleVo
	 * @return
	 */
	void deleteByIdList(LoanRuleVO LoanRuleVo);
	/***
	 * 
	 * <pre>
	 * 根据参数查询规则
	 * </pre>
	 *
	 * @param loanRuleVo
	 * @return
	 */
	List<LoanRule> findRuleByVO(LoanRuleVO loanRuleVo);
	/***
	 * 
	 * <pre>
	 * 根据参数查询规则
	 * </pre>
	 *  状态为 未删除 可执行
	 * @param loanRuleVo
	 * @return
	 * @throws Exception 
	 */
	List<LoanRule> findRuleByParams(LoanRuleVO loanRuleVo) ;
	/***
	 * 
	 * <pre>
	 * 设置还款日规则
	 * </pre>
	 *
	 * @param LoanRuleVo
	 * @return
	 */
	/** 
	 * @param LoanRuleVo
	 * @return
	 * @see com.ezendai.credit2.rule.service.DueDateRuleService#update(com.ezendai.credit2.rule.vo.LoanRuleVO)
	 */
	
	int configRepayDateRule(LoanRuleVO LoanRuleVo);
	
}
