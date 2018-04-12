package com.ezendai.credit2.audit.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezendai.credit2.apply.model.Extension;
import com.ezendai.credit2.apply.model.Person;
import com.ezendai.credit2.apply.service.ExtensionService;
import com.ezendai.credit2.audit.model.RepaymentPlan;
import com.ezendai.credit2.audit.service.RepaymentPlanService;
import com.ezendai.credit2.audit.vo.RepaymentPlanVO;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.system.controller.BaseController;

@Controller
@RequestMapping("/repaymentPlan")
public class RepaymentPlanController extends BaseController {
	
	@Autowired
	RepaymentPlanService repaymentPlanService;
	@Autowired
	ExtensionService extensionService;
	
	/**
	 * <pre>
	 * 变更管理主界面
	 * </pre>
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/repaymentPlanLedger/{loanId}")
	public String repaymentPlanLedger(HttpServletRequest request,@PathVariable("loanId") String loanId, Model model) {
		model.addAttribute("loanId", loanId);
		setDataDictionaryArr(new String[] { EnumConstants.REPAYMENT_PLAN_STATE });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		return "finance/loanLedger";
	}
	
	@RequestMapping("/repaymentPlanLedgerList/{loanId}")
	@ResponseBody
	public List<RepaymentPlan> repaymentPlanLedgerList(@PathVariable("loanId") String loanId,
			Model model) {
			RepaymentPlanVO repaymentPlanVo= new RepaymentPlanVO();
			repaymentPlanVo.setLoanId(Long.parseLong(loanId));
			List<RepaymentPlan> repaymentPlanList=repaymentPlanService.getRepaymentPlanExtends(repaymentPlanVo);
		return repaymentPlanList;
	}
	
	@RequestMapping("/repaymentPlanLedgerExtensionList/{loanId}")
	@ResponseBody
	public List<RepaymentPlan> repaymentPlanLedgerExtensionList(@PathVariable("loanId") String loanId,
			Model model) {
		List<RepaymentPlan> repaymentPlanList=new ArrayList<RepaymentPlan>();
			Extension extension = extensionService.get(Long.parseLong(loanId));
			if (extension != null) {
				RepaymentPlanVO repaymentPlanVo= new RepaymentPlanVO();
				repaymentPlanVo.setLoanId(extension.getId());
				repaymentPlanList=repaymentPlanService.getRepaymentPlanExtends(repaymentPlanVo);
			}
		return repaymentPlanList;
	}

}
