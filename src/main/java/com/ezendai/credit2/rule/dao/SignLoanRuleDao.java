package com.ezendai.credit2.rule.dao;

import com.ezendai.credit2.framework.dao.BaseDao;
import com.ezendai.credit2.rule.model.LoanRule;
import com.ezendai.credit2.rule.vo.LoanRuleVO;

public interface SignLoanRuleDao extends BaseDao<LoanRule> {
	/**
	 * <pre>
	 * 将规则设为失效
	 * </pre>
	 * @param date
	 * @return
	 */
	int invalidRule(LoanRuleVO loanRuleVO);
	
	/**
	 * 根据特定VO,查询想死对象总数
	 * @param loanRuleVO
	 * @return
	 */
	int getCountByVO(LoanRuleVO loanRuleVO);
}
