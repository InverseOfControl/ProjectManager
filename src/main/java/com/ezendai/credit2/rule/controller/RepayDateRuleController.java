package com.ezendai.credit2.rule.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.rule.model.LoanRule;
import com.ezendai.credit2.rule.service.RepayDateRuleService;
import com.ezendai.credit2.rule.vo.LoanRuleVO;
import com.ezendai.credit2.system.controller.BaseController;


/**
 * 
 * <pre>
 * 到期还款日规则
 * </pre>
 *
 * @author chenzx
 * @version $Id: RuleController.java, v 0.1 2014年8月22日 上午9:25:54 HQ-AT6 Exp $
 */
@Controller
@RequestMapping("/repayDateRule")
public class RepayDateRuleController extends BaseController {
	
	@Autowired
	private RepayDateRuleService repayDateRuleService;

	
	
	@RequestMapping("/repayDateRuleMain")
	public String repayDateRuleMain(Model model,HttpServletRequest request) {	
		/*设置数据字典*/
		setDataDictionaryArr(new String[] { EnumConstants.PRODUCT_TYPE,
											EnumConstants.PRODUCT_SUB_TYPE,
											EnumConstants.CONTRACT_SRC,
											EnumConstants.REPAY_DATE_RULE,
											EnumConstants.IS_EXECUTED });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		return "rule/repayDate/repayDateRuleList";
	}
	/***
	 * 
	 * <pre>
	 * 根据参数查询列表
	 * </pre>
	 *
	 * @param loanRuleVO
	 * @param rows
	 * @param page
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/getRepayDateRulePage")
	@ResponseBody
	public Map getRepayDateRulePage(LoanRuleVO loanRuleVO,int rows, int page){
		Pager p = new Pager();
		p.setPage(page);
		p.setRows(rows);		
		loanRuleVO.setPager(p);	
		loanRuleVO.setRuleType(EnumConstants.RuleType.REPAYDATE_RULE.getValue());
		loanRuleVO.setIsDeleted(EnumConstants.YesOrNo.NO.getValue());
		Pager pager = repayDateRuleService.findSysRuleByParam(loanRuleVO);	
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("total", pager.getTotalCount());
		result.put("rows", pager.getResultList());		
		return result;
		
	}
	/***
	 * 
	 * <pre>
	 * 新增还款规则
	 * </pre>
	 *
	 * @param loanRule
	 * @return
	 */
	@RequestMapping("/addRepayDateRule")
	@ResponseBody 	
	public String addRepayDateRule(LoanRule loanRule){
		return repayDateRuleService.insert(loanRule);
	}
	/***
	 * 
	 * <pre>
	 * 根据ID获取规则
	 * </pre>
	 *
	 * @param ruleId
	 * @return
	 */
	@RequestMapping("/toEditRepayDateRule")
	@ResponseBody  
	public LoanRule toEditRepayDateRule(Long ruleId){
		return repayDateRuleService.get(ruleId);
	}
	/***
	 * 
	 * <pre>
	 * 更新还款日规则
	 * </pre>
	 *
	 * @param loanRule
	 * @return
	 */
	@RequestMapping("/updateRepayDateRule")
	@ResponseBody
	public String updateRepayDateRule(LoanRuleVO LoanRuleVo) {
		repayDateRuleService.update(LoanRuleVo);
		return "success";
	}
	/***
	 * 
	 * <pre>
	 * 设置还款日规则
	 * </pre>
	 *
	 * @param loanRule
	 * @return
	 */
	@RequestMapping("/configRepayDateRule")
	@ResponseBody
	public String configRepayDateRule(LoanRuleVO LoanRuleVo) {
		repayDateRuleService.configRepayDateRule(LoanRuleVo);
		return "success";
	}
	/***
	 * 
	 * <pre>
	 * 删除还款规则
	 * </pre>
	 *
	 * @param loanRule
	 * @return
	 */
	@RequestMapping("/deleteRepayDateRule")
	@ResponseBody
	public String deleteRepayDateRule(LoanRuleVO LoanRuleVo) {
		repayDateRuleService.deleteByIdList(LoanRuleVo);
		return "success";
	}
}
