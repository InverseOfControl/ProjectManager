package com.ezendai.credit2.rule.service;

import java.util.List;

import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.rule.model.LoanRule;
import com.ezendai.credit2.rule.vo.LoanRuleVO;

/**
 * 
 * <pre>
 * 特殊签单规则设置业务逻辑
 * </pre>
 *
 * @author zhangshihai
 * @version $Id: SignLoanService.java, v 0.1 2014年8月22日 下午1:54:08 zhangshihai Exp $
 */
public interface SignLoanRuleService {
	
	/**
	 * 
	 * <pre>
	 * 根据参数，查询签单规则列表
	 * </pre>
	 *
	 * @param loanRuleVO
	 * @return
	 */
	Pager findSignLoanListByParam(LoanRuleVO loanRuleVO);
	
	/**
	 * 
	 * <pre>
	 * 根据规则ID，获取规则对象信息
	 * </pre>
	 *
	 * @param ruleId
	 * @return
	 */
	LoanRule get(Long ruleId);
	
	/**
	 * 
	 * <pre>
	 * 新建特殊签单规则
	 * </pre>
	 *
	 * @param loanRule
	 * @return
	 */
	String insert(LoanRule loanRule);
	
	/**
	 * 
	 * <pre>
	 * 编辑特殊签单规则对象
	 * </pre>
	 *
	 * @param loanRuleVO
	 * @return
	 */
	String update(LoanRuleVO loanRuleVO);
	
	
	/**
	 * 
	 * <pre>
	 * 删除特殊签单规则对象
	 * </pre>
	 *
	 * @param loanRuleVO
	 */
	public void deleteSignLoanRule(LoanRuleVO loanRuleVO);
	
	/**
	 * 
	 * <pre>
	 * 查询与参数VO相同的对象
	 * </pre>
	 *
	 * @param loanRuleVO
	 * @return
	 */
	public List<LoanRule> findRuleListByVO(LoanRuleVO loanRuleVO);
	
	/**
	 * 
	 * <pre>
	 * 根据当前日期，获取签单规则
	 * </pre>
	 *
	 * @return
	 */
	public List<LoanRule> findSignRuleByLoanRuleVo(LoanRuleVO loanRuleVO);
	
	/**
	 * 
	 * <pre>
	 * 设置签单规则的有效性
	 * </pre>
	 *
	 * @param loanRuleVO
	 * @return
	 */
	public String setSignLoanRule(LoanRuleVO loanRuleVO);
	
	/**
	 * <pre>
	 * 将过期规则设为失效
	 * </pre>
	 * @param date
	 * @return
	 */
	public int invalidRule(LoanRuleVO loanRuleVO);
	
	/**
	 * 获取符合条件的规则总数
	 * @param loanRuleVO
	 * @return
	 */
	public int getCountByVO(LoanRuleVO loanRuleVO);
}
