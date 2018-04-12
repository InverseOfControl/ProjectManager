package com.ezendai.credit2.rule.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezendai.credit2.framework.util.DateUtil;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.rule.model.LoanRule;
import com.ezendai.credit2.rule.service.SignLoanRuleService;
import com.ezendai.credit2.rule.vo.LoanRuleVO;
import com.ezendai.credit2.system.controller.BaseController;

/**
 * 
 * <pre>
 * 特殊签单规则
 * </pre>
 *
 * @author zhangshihai
 * @version $Id: SignLoanRuleController.java, v 0.1 2014年8月22日 下午1:28:13 zhangshihai Exp $
 */
@Controller
@RequestMapping("/signLoanRule")
public class SignLoanRuleController extends BaseController {

	@Autowired
	private SignLoanRuleService signLoanRuleService;
	
	/**
	 * 
	 * <pre>
	 * 特殊签单设置主界面
	 * </pre>
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/signLoanRuleMain")
	public String viewList(HttpServletRequest request) {
		/*设置数据字典*/
		setDataDictionaryArr(new String[] {EnumConstants.PRODUCT_TYPE,EnumConstants.PRODUCT_SUB_TYPE,EnumConstants.IS_EXECUTED});
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		return "rule/signLoan/signLoanList";
	}

	/**
	 * 
	 * <pre>
	 * 根据查询条件，查出符合条件的签单规则列表
	 * </pre>
	 *
	 * @param loanRuleVO
	 * @param rows
	 * @param page
	 * @return
	 */
	@RequestMapping("/getSignLoanRulePage")
	@ResponseBody
	public Object viewList(LoanRuleVO loanRuleVO, int rows, int page) {
		Pager pager = new Pager();
		pager.setRows(rows);
		pager.setPage(page);

		loanRuleVO.setPager(pager);
		loanRuleVO.setIsDeleted(EnumConstants.YesOrNo.NO.getValue());
		loanRuleVO.setRuleType(EnumConstants.RuleType.SIGN_RULE.getValue());

		Pager signLoanRulePage = signLoanRuleService.findSignLoanListByParam(loanRuleVO);

		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("total", signLoanRulePage.getTotalCount());
		result.put("rows", signLoanRulePage.getResultList());

		return result;
	}

	/**
	 * 
	 * <pre>
	 * 判断当前日期是否适合操作
	 * </pre>
	 *
	 * @return
	 */
	@RequestMapping("/checkDate")
	@ResponseBody
	public boolean checkDate() {
		boolean flag = false;
		Calendar calendar = Calendar.getInstance();
		// 获取当月的天数
		int dayOfMonth = DateUtil.getDayOfMonth();
		// 判断今日是当月的几号
		int today = calendar.get(Calendar.DAY_OF_MONTH);
		if(today >= 13 && today <= 15) {
			// 月中，可操作
			flag = true;
		} else if ((dayOfMonth - today) < 3) {
			// 月末，可操作
			flag = true;
		}
		return flag;
	}
	
	
	/**
	 * 
	 * <pre>
	 * 新建特殊签单规则
	 * </pre>
	 *
	 * @param loanRule
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/addSignLoanRule")
	@ResponseBody
	public String addSignLoanRule(LoanRule loanRule) throws ParseException {
		// 判断当前日期是否可操作
		boolean validDateFlg = checkDate();
		if(!validDateFlg) {
			return "今日不可操作，可操作日为当月13~15号，以及月末前三天";
		}

		return signLoanRuleService.insert(loanRule);
	}

	/**
	 * 
	 * <pre>
	 * 根据ID值，查出签单规则对象，返回给规则编辑页面
	 * </pre>
	 *
	 * @param ruleId
	 * @return
	 */
	@RequestMapping("/toEditSignLoanRule")
	@ResponseBody
	public LoanRule toEditSignLoanRule(Long ruleId) {
		LoanRule loanRule = signLoanRuleService.get(ruleId);
		return loanRule;
	}

	/**
	 * 
	 * <pre>
	 * 修改特殊签单规则对象
	 * </pre>
	 *
	 * @param loanRuleVO
	 * @return
	 */
	@RequestMapping("/editSignLoanRule")
	@ResponseBody
	public String updateSignLoanRule(LoanRuleVO loanRuleVO) {
		// 判断当前日期是否可操作
		boolean validDateFlag = checkDate();
		if(!validDateFlag) {
			return "今日不可操作，可操作日为当月13~15号，以及月末前三天";
		}

		return signLoanRuleService.update(loanRuleVO);
	}
	
	/**
	 * 
	 * <pre>
	 * 根据ID值，删除指定签单规则对象
	 * </pre>
	 *
	 * @return
	 */
	@RequestMapping("/deleteSignLoanRule")
	@ResponseBody
	public String deleteSignLoanRule(LoanRuleVO loanRuleVO) {
		signLoanRuleService.deleteSignLoanRule(loanRuleVO);
		return "success";
	}
	
	/**
	 * 
	 * <pre>
	 * 设置特殊签单的有效性
	 * </pre>
	 *
	 * @param loanRuleVO
	 * @return
	 */
	@RequestMapping("/setSignLoanRule")
	@ResponseBody
	public String setSignLoanRule(LoanRuleVO loanRuleVO) {
		return signLoanRuleService.setSignLoanRule(loanRuleVO);
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

}
