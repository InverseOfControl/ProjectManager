package com.ezendai.credit2.rule.dao.impl;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;
import com.ezendai.credit2.rule.dao.SignLoanRuleDao;
import com.ezendai.credit2.rule.model.LoanRule;
import com.ezendai.credit2.rule.vo.LoanRuleVO;

/**
 * 
 * <pre>
 * 特殊签单规则设置的Dao实现
 * </pre>
 *
 * @author zhangshihai
 * @version $Id: LoanRuleDaoImpl.java, v 0.1 2014年8月22日 下午12:34:42 zhangshihai Exp $
 */
@Repository
public class SignLoanRuleDaoImpl extends BaseDaoImpl<LoanRule> implements SignLoanRuleDao {

	@Override
	public int invalidRule(LoanRuleVO loanRuleVO) {
		return getSqlSession().update(getIbatisMapperNameSpace() + ".invalidRule", loanRuleVO);
	}

	@Override
	public int getCountByVO(LoanRuleVO loanRuleVO) {
		Object count = getSqlSession().selectOne(getIbatisMapperNameSpace() + ".ruleCount", loanRuleVO);
		return Integer.parseInt(count.toString());
	}

}
