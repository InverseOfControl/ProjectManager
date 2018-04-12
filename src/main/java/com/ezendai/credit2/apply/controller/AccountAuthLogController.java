package com.ezendai.credit2.apply.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezendai.credit2.apply.model.AccountAuthLog;
import com.ezendai.credit2.apply.model.BusinessLog;
import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.service.AccountAuthLogService;
import com.ezendai.credit2.apply.service.BusinessLogService;
import com.ezendai.credit2.apply.vo.AccountAuthLogVO;
import com.ezendai.credit2.apply.vo.BusinessLogVO;
import com.ezendai.credit2.audit.vo.SyncRepaymentMsgLogVO;
import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.system.controller.BaseController;

@Controller
@RequestMapping("/apply/accountAuthLog")
public class AccountAuthLogController  extends BaseController {

	@Autowired
	private AccountAuthLogService accountAuthLogService;
	
	@RequestMapping("/initAccountAuthLog")
	public String initRepaymentMsgLog(HttpServletRequest request){
		setDataDictionaryArr(new String[] {EnumConstants.PRODUCT_ID, EnumConstants.PRODUCT_TYPE,EnumConstants.REPAYMENT_PLAN_STATE,EnumConstants.SEND_STATUS });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		return "apply/accountAuthLog/accountAuthLog";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/accountAuthLogList")
	@ResponseBody
	public Map getAccountAuthLogList(AccountAuthLogVO accountAuthLogVO,
			int rows, int page,
			Model model) {
		Pager pager=new Pager();
		Pager p=new Pager();
		p.setRows(rows);
		p.setPage(page);
		p.setSidx("SEND_TIME");
		p.setSort("DESC");
		accountAuthLogVO.setPager(p);
		pager=accountAuthLogService.findWithPg(accountAuthLogVO);
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		List<AccountAuthLog>	accountAuthLogList=pager.getResultList();
		result.put("total", pager.getTotalCount());
		result.put("rows",accountAuthLogList);
		return result;
	}

}
