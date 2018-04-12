package com.ezendai.credit2.report.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.framework.util.StringUtil;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.report.service.OverdueMsgLogService;
import com.ezendai.credit2.report.vo.OverdueMsgLogVO;
import com.ezendai.credit2.system.controller.BaseController;

@Controller
@RequestMapping("/report/overdueMsg")
public class OverdueMsgLogController extends BaseController {

	private static final String OVERDUE_MSG_LOG_PAGE = "report/overdueMsg/overdueMsgLogList";

	@Autowired
	private OverdueMsgLogService overdueMsgLogService;

	/**
	 * 逾期短信日志初始化页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/overdueMsgLogPage")
	public String initPage(HttpServletRequest request) {
		setDataDictionaryArr(new String[] { EnumConstants.PRODUCT_ID, EnumConstants.LOAN_STATUS,
				EnumConstants.REPAYMENT_PLAN_STATE });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		return OVERDUE_MSG_LOG_PAGE;
	}

	/**
	 * 查询逾期发送短信日志
	 * 
	 * @param OverdueMsgLogVO
	 * @param rows
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/getOverdueMsgLogList")
	@ResponseBody
	public Map<String, Object> getOverdueMsgLogList(OverdueMsgLogVO OverdueMsgLogVO, int rows, int page) {
		Map<String, Object> map = null;
		if(OverdueMsgLogVO!=null){
				Pager pager = new Pager();
				pager.setPage(page);
				pager.setRows(rows);
				pager.setSidx("BUILD_DATE");
				pager.setSort("desc");
				OverdueMsgLogVO.setPager(pager);
				pager = overdueMsgLogService.findWithPG(OverdueMsgLogVO);
				map = new HashMap<String, Object>();
				map.put("total", pager.getTotalCount());
				map.put("rows", pager.getResultList());		
		}
		
		return map;
	}
}
