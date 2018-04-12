package com.ezendai.credit2.rule.assembler;

import com.ezendai.credit2.rule.model.LoanRule;
import com.ezendai.credit2.rule.vo.LoanRuleVO;

/**
 * 
 * <pre>
 * 借款规则（还款日规则/特殊签单规则）的Model/VO转换
 * </pre>
 *
 * @author zhangshihai
 * @version $Id: LoanRuleAssembler.java, v 0.1 2014年8月22日 上午11:33:46 zhangshihai Exp $
 */
public class LoanRuleAssembler {

	/**
	 * 
	 * <pre>
	 * Model转为VO
	 * </pre>
	 *
	 * @param loanRule
	 * @return
	 */
	public static LoanRuleVO transferModel2VO (LoanRule loanRule) {
		if (loanRule == null) {
			return null;
		}
		
		LoanRuleVO loanRuleVO = new LoanRuleVO();
		loanRuleVO.setId(loanRule.getId());
		loanRuleVO.setName(loanRule.getName());
		loanRuleVO.setRuleType(loanRule.getRuleType());
		loanRuleVO.setProductType(loanRule.getProductType());
		loanRuleVO.setProductSubtype(loanRule.getProductSubtype());
		loanRuleVO.setContractSrc(loanRule.getContractSrc());
		loanRuleVO.setRepaydateRule(loanRule.getRepaydateRule());
		loanRuleVO.setOverdueDate(loanRule.getOverdueDate());
		loanRuleVO.setIsExecuted(loanRule.getIsExecuted());
		loanRuleVO.setExecuteTime(loanRule.getExecuteTime());
		loanRuleVO.setIsDeleted(loanRule.getIsDeleted());
		loanRuleVO.setCreator(loanRule.getCreator());
		loanRuleVO.setCreatorId(loanRule.getCreatorId());
		loanRuleVO.setCreatedTime(loanRule.getCreatedTime());
		loanRuleVO.setModifier(loanRule.getModifier());
		loanRuleVO.setModifierId(loanRule.getModifierId());
		loanRuleVO.setModifiedTime(loanRule.getModifiedTime());
		loanRuleVO.setVersion(loanRule.getVersion());
		loanRuleVO.setProductSubtypeNull(loanRule.getProductSubtypeNull());
		loanRuleVO.setExecuteTimeNull(loanRule.getExecuteTimeNull());
		return loanRuleVO;
	}
	
	/**
	 * 
	 * <pre>
	 * VO转为Model
	 * </pre>
	 *
	 * @param loanRuleVO
	 * @return
	 */
	public static LoanRule transferVO2Model (LoanRuleVO loanRuleVO) {
		if (loanRuleVO == null) {
			return null;
		}
		
		LoanRule loanRule = new LoanRule();
		loanRule.setId(loanRuleVO.getId());
		loanRule.setName(loanRuleVO.getName());
		loanRule.setRuleType(loanRuleVO.getRuleType());
		loanRule.setProductType(loanRuleVO.getProductType());
		loanRule.setProductSubtype(loanRuleVO.getProductSubtype());
		loanRule.setContractSrc(loanRuleVO.getContractSrc());
		loanRule.setRepaydateRule(loanRuleVO.getRepaydateRule());
		loanRule.setOverdueDate(loanRuleVO.getOverdueDate());
		loanRule.setIsExecuted(loanRuleVO.getIsExecuted());
		loanRule.setExecuteTime(loanRuleVO.getExecuteTime());
		loanRule.setIsDeleted(loanRuleVO.getIsDeleted());
		loanRule.setCreator(loanRuleVO.getCreator());
		loanRule.setCreatorId(loanRuleVO.getCreatorId());
		loanRule.setCreatedTime(loanRuleVO.getCreatedTime());
		loanRule.setModifier(loanRuleVO.getModifier());
		loanRule.setModifierId(loanRuleVO.getModifierId());
		loanRule.setModifiedTime(loanRuleVO.getModifiedTime());
		loanRule.setVersion(loanRuleVO.getVersion());
		loanRule.setProductSubtypeNull(loanRuleVO.getProductSubtypeNull());
		loanRule.setExecuteTimeNull(loanRuleVO.getExecuteTimeNull());		
		return loanRule;
	}
	
}
