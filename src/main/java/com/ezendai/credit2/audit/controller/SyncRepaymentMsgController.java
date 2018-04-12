package com.ezendai.credit2.audit.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.vo.LoanVO;
import com.ezendai.credit2.audit.service.SyncRepaymentMsgService;
import com.ezendai.credit2.audit.vo.SyncRepaymentMsgVO;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.system.controller.BaseController;

@Controller
@RequestMapping("/repaymentMsg")
public class SyncRepaymentMsgController extends BaseController{
	
	@Autowired
	SyncRepaymentMsgService syncRepaymentMsgService;
	
	@RequestMapping("/initRepaymentMsg")
	public String initRepaymentMsg(HttpServletRequest request){
		setDataDictionaryArr(new String[] {EnumConstants.PRODUCT_ID, EnumConstants.PRODUCT_TYPE,EnumConstants.REPAYMENT_PLAN_STATE,EnumConstants.SEND_STATUS });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		return "audit/repaymentMsgLog/repaymentMsg";
	}

	
	@SuppressWarnings("unchecked")
	@RequestMapping("/repaymentMsgList")
	@ResponseBody
	public Map getRepaymentMsgList(SyncRepaymentMsgVO syncRepaymentMsgVO,
			int rows, int page,
			Model model) {
		Pager pager=new Pager();
		Pager p=new Pager();
		p.setRows(rows);
		p.setPage(page);	
		p.setSidx("BUILD_DATE");
		p.setSort("DESC");
		syncRepaymentMsgVO.setPager(p);
		if(syncRepaymentMsgVO.getStatus()==null){
			List<Integer> statusList=new ArrayList<Integer>();
			statusList.add(0);
			statusList.add(1);
			statusList.add(2);
			statusList.add(3);
			statusList.add(4);
			syncRepaymentMsgVO.setStatusList(statusList);
		}
		pager=syncRepaymentMsgService.getRepaymentMsgListWithPg(syncRepaymentMsgVO);
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		List<Loan>	loanList=pager.getResultList();
		result.put("total", pager.getTotalCount());
		result.put("rows",loanList);
		return result;
	}
}
