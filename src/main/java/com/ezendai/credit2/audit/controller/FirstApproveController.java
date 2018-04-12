package com.ezendai.credit2.audit.controller; 
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
 
import org.springframework.stereotype.Controller;
 
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezendai.credit2.audit.model.FirstApprove;
import com.ezendai.credit2.audit.service.FirstApproveService;
import com.ezendai.credit2.audit.vo.FirstApproveVO;
import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.framework.model.UserSession;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.framework.util.StringUtil;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.system.controller.BaseController;
@Controller
@RequestMapping("/FirstApprove/FirstApproveData")
public class FirstApproveController extends BaseController {
	@Autowired
	private FirstApproveService firstService;
	 
	
	private static final Logger logger = Logger.getLogger(FirstApproveController.class);
	 
	 
	@RequestMapping("/init")
	public String init(HttpServletRequest request, ModelMap modelMap) {
		/*设置数据字典*/
		setDataDictionaryArr(new String[] { EnumConstants.LOAN_STATUS, EnumConstants.PRODUCT_TYPE, EnumConstants.CONTRACT_SRC });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		UserSession user=	ApplicationContext.getUser();
		String acceptAudit= firstService.getAcceptAudit(user.getId());
		modelMap.put("acceptAudit", acceptAudit);
		String loginName=user.getName();
		modelMap.put("loginName", loginName);
	 	return "audit/firstApprove/firstApproveMain"; 
	/*	return "/finance/financialAudit/financialAuditList";*/
	}
	@RequestMapping("/toApproveTab")
	public String toApproveTab(HttpServletRequest request) {
		/*设置数据字典*/
		setDataDictionaryArr(new String[] { EnumConstants.LOAN_STATUS, EnumConstants.PRODUCT_TYPE, EnumConstants.CONTRACT_SRC });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
	 	return "audit/firstApprove/approveList"; 
	 
	}
	@RequestMapping("/toHangTab")
	public String toHangTab(HttpServletRequest request) {
		/*设置数据字典*/
		setDataDictionaryArr(new String[] { EnumConstants.LOAN_STATUS, EnumConstants.PRODUCT_TYPE, EnumConstants.CONTRACT_SRC });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		
	 	return "audit/firstApprove/hangList"; 
	 
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/listData")
	@ResponseBody
	public Map list(FirstApproveVO vo, int rows, int page) throws ParseException {
		Pager p = new Pager();
		p.setPage(page);
		p.setRows(rows);
		p.setSidx("request_date");
		p.setSort("asc");
		vo.setPager(p);
		List<Integer> statusList = new ArrayList<Integer>();
		if(StringUtil.isEmpty(vo.getSearchFlag())){			
			statusList.add(EnumConstants.LoanStatus.终审退回初审.getValue());
			statusList.add(EnumConstants.LoanStatus.初审中.getValue());
			statusList.add(EnumConstants.LoanStatus.门店重提.getValue());
			
//			statusList.add(EnumConstants.LoanStatus.初审中.getValue());
//			/**审批中*/
//			statusList.add(EnumConstants.LoanStatus.审批中.getValue());
//			/**终审中*/
//			statusList.add(EnumConstants.LoanStatus.终审中.getValue());
//			/**初审拒绝*/
//			statusList.add(EnumConstants.LoanStatus.初审拒绝.getValue());
//			/*终审拒绝*/
//			statusList.add(EnumConstants.LoanStatus.终审拒绝.getValue());
//			/**初审退回*/
//			statusList.add(EnumConstants.LoanStatus.初审退回.getValue());
//			/**终审退回门店*/
//			statusList.add(EnumConstants.LoanStatus.终审退回门店.getValue());
//			/**终审退回初审*/
//			statusList.add(EnumConstants.LoanStatus.终审退回初审.getValue());
//			/**拒绝*/
//			statusList.add(EnumConstants.LoanStatus.拒绝.getValue());
//			/**展期拒绝*/
//			statusList.add(EnumConstants.LoanStatus.展期拒绝.getValue());
//			/**门店重提*/
//			statusList.add(EnumConstants.LoanStatus.门店重提.getValue());
//			/**初审重提*/
//			statusList.add(EnumConstants.LoanStatus.初审重提.getValue());
//			/** 信审退回 */
//			statusList.add(EnumConstants.LoanStatus.信审退回.getValue());
//			/**有条件同意*/
//			statusList.add(EnumConstants.LoanStatus.有条件同意.getValue());
//			/**审贷会决议 退回门店*/
//			statusList.add(EnumConstants.LoanStatus.退回门店.getValue());
//			/**展期退回门店*/
//			statusList.add(EnumConstants.LoanStatus.展期退回门店.getValue());
//			/**信审拒绝*/
//			statusList.add(EnumConstants.LoanStatus.信审拒绝.getValue());
//			/**合同签订*/
//			statusList.add(EnumConstants.LoanStatus.合同签订.getValue());
//			/**展期合同签订*/
//			statusList.add(EnumConstants.LoanStatus.展期合同签订.getValue());
//			/**合同确认*/
//			statusList.add(EnumConstants.LoanStatus.合同确认.getValue());
//			/**展期合同确认*/
//			statusList.add(EnumConstants.LoanStatus.展期合同确认.getValue());
//			/**合同确认退回*/
//			statusList.add(EnumConstants.LoanStatus.合同确认退回.getValue());
//			/**展期合同确认退回*/
//			statusList.add(EnumConstants.LoanStatus.展期合同确认退回.getValue());
//			/**财务审核*/
//			statusList.add(EnumConstants.LoanStatus.财务审核.getValue());
//			/**财务审核退回*/
//			statusList.add(EnumConstants.LoanStatus.财务审核退回.getValue());
//			/**财务放款*/
//			statusList.add(EnumConstants.LoanStatus.财务放款.getValue());
//			/**财务放款退回*/
//			statusList.add(EnumConstants.LoanStatus.财务放款退回.getValue());
//			/**正常*/
//			statusList.add(EnumConstants.LoanStatus.正常.getValue());
//			/**逾期*/
//			statusList.add(EnumConstants.LoanStatus.逾期.getValue());
//			/**预结清*/
//			statusList.add(EnumConstants.LoanStatus.预结清.getValue());
//			/**结清*/
//			statusList.add(EnumConstants.LoanStatus.结清.getValue());
//			/**坏账*/
//			statusList.add(EnumConstants.LoanStatus.坏账.getValue());
//			/**取消*/
//			statusList.add(EnumConstants.LoanStatus.取消.getValue());
			vo.setStatusList(statusList);
		}else{
			if(vo.getStatus()!=null){
				if(vo.getStatus() ==0){
					statusList.add(EnumConstants.LoanStatus.初审中.getValue());
					/**审批中*/
					statusList.add(EnumConstants.LoanStatus.审批中.getValue());
					/**终审中*/
					statusList.add(EnumConstants.LoanStatus.终审中.getValue());
					/**初审拒绝*/
					statusList.add(EnumConstants.LoanStatus.初审拒绝.getValue());
					/*终审拒绝*/
					statusList.add(EnumConstants.LoanStatus.终审拒绝.getValue());
					/**初审退回*/
					statusList.add(EnumConstants.LoanStatus.初审退回.getValue());
					/**终审退回门店*/
					statusList.add(EnumConstants.LoanStatus.终审退回门店.getValue());
					/**终审退回初审*/
					statusList.add(EnumConstants.LoanStatus.终审退回初审.getValue());
					/**拒绝*/
					statusList.add(EnumConstants.LoanStatus.拒绝.getValue());
					/**展期拒绝*/
					statusList.add(EnumConstants.LoanStatus.展期拒绝.getValue());
					/**门店重提*/
					statusList.add(EnumConstants.LoanStatus.门店重提.getValue());
					/**初审重提*/
					statusList.add(EnumConstants.LoanStatus.初审重提.getValue());
					/** 信审退回 */
					statusList.add(EnumConstants.LoanStatus.信审退回.getValue());
					/**有条件同意*/
					statusList.add(EnumConstants.LoanStatus.有条件同意.getValue());
					/**审贷会决议 退回门店*/
					statusList.add(EnumConstants.LoanStatus.退回门店.getValue());
					/**展期退回门店*/
					statusList.add(EnumConstants.LoanStatus.展期退回门店.getValue());
					/**信审拒绝*/
					statusList.add(EnumConstants.LoanStatus.信审拒绝.getValue());
					/**合同签订*/
					statusList.add(EnumConstants.LoanStatus.合同签订.getValue());
					/**展期合同签订*/
					statusList.add(EnumConstants.LoanStatus.展期合同签订.getValue());
					/**合同确认*/
					statusList.add(EnumConstants.LoanStatus.合同确认.getValue());
					/**展期合同确认*/
					statusList.add(EnumConstants.LoanStatus.展期合同确认.getValue());
					/**合同确认退回*/
					statusList.add(EnumConstants.LoanStatus.合同确认退回.getValue());
					/**展期合同确认退回*/
					statusList.add(EnumConstants.LoanStatus.展期合同确认退回.getValue());
					/**财务审核*/
					statusList.add(EnumConstants.LoanStatus.财务审核.getValue());
					/**财务审核退回*/
					statusList.add(EnumConstants.LoanStatus.财务审核退回.getValue());
					/**财务放款*/
					statusList.add(EnumConstants.LoanStatus.财务放款.getValue());
					/**财务放款退回*/
					statusList.add(EnumConstants.LoanStatus.财务放款退回.getValue());
					/**正常*/
					statusList.add(EnumConstants.LoanStatus.正常.getValue());
					/**逾期*/
					statusList.add(EnumConstants.LoanStatus.逾期.getValue());
					/**预结清*/
					statusList.add(EnumConstants.LoanStatus.预结清.getValue());
					/**结清*/
					statusList.add(EnumConstants.LoanStatus.结清.getValue());
					/**坏账*/
					statusList.add(EnumConstants.LoanStatus.坏账.getValue());
					/**取消*/
					statusList.add(EnumConstants.LoanStatus.取消.getValue());
					vo.setStatusList(statusList);
				}else{
					statusList.add(vo.getStatus());
					vo.setStatusList(statusList);
				}
			}
			
		}
		if(!StringUtil.isEmpty(vo.getStartDate())){
			 
			vo.setStartDate(vo.getStartDate()+" 00:00:00");
		}
		if(!StringUtil.isEmpty(vo.getEndDate())){
			vo.setEndDate((vo.getEndDate()+" 23:59:59"));
		}
		if(vo.getAuditDateEndEdu()!=null){
		Calendar cal = Calendar.getInstance();
		cal.setTime(vo.getAuditDateEndEdu());
		cal.add(Calendar.DAY_OF_MONTH, 1);  
		cal.add(Calendar.MILLISECOND, -1);  
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	vo.setAuditDateEndEdu(formatDate.parse(format.format(cal.getTime())));
		}
		vo.setProductType(EnumConstants.ProductType.SE_LOAN.getValue());
		vo.setProductId(EnumConstants.ProductList.STUDENT_LOAN.getValue());
		UserSession user=	ApplicationContext.getUser();
		vo.setFirsttrialId(user.getId());
		if("admin".equals(user.getLoginName())){
			vo.setFirsttrialId(null);
		}
		Pager pager = firstService.findFirstApproveWithPG(vo);
	 	List<FirstApprove> approveList = pager.getResultList(); 
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("total", pager.getTotalCount());
		result.put("rows", approveList);
		return result;
	}
 
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/HangListData")
	@ResponseBody
	public Map HangListData( FirstApproveVO vo, int rows, int page) {
		Pager p = new Pager();
		p.setPage(page);
		p.setRows(rows);
		p.setSidx("request_date");
		p.setSort("asc");
		vo.setPager(p);
		List<Integer> statusList = new ArrayList<Integer>();
			statusList.add(EnumConstants.LoanStatus.初审挂起.getValue());
			vo.setStatusList(statusList);
			if(!StringUtil.isEmpty(vo.getStartDate())){
				vo.setStartDate(vo.getStartDate()+" 00:00:00");
			}
			if(!StringUtil.isEmpty(vo.getEndDate())){
				vo.setEndDate((vo.getEndDate()+" 23:59:59"));
			}
			if(vo.getAuditDateEndEdu()!=null){
				Calendar cal = Calendar.getInstance();
				cal.setTime(vo.getAuditDateEndEdu());
				cal.add(Calendar.DAY_OF_MONTH, 1);  
				cal.add(Calendar.MILLISECOND, -1);  
		    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    	SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    	try {
					vo.setAuditDateEndEdu(formatDate.parse(format.format(cal.getTime())));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
		vo.setProductType(EnumConstants.ProductType.SE_LOAN.getValue());
		vo.setProductId(EnumConstants.ProductList.STUDENT_LOAN.getValue());
		UserSession user=	ApplicationContext.getUser();
		vo.setFirsttrialId(user.getId());
		if("admin".equals(user.getLoginName())){
			vo.setFirsttrialId(null);
		}
		Pager pager = firstService.findFirstApproveWithPG(vo);
	 	List<FirstApprove> approveList = pager.getResultList(); 
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("total", pager.getTotalCount());
		result.put("rows", approveList);
		return result;
	}
	@RequestMapping(value = "/updateAcceptAudit/{status}",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String infoAppSave(@PathVariable("status") String status ,HttpServletRequest request, ModelMap modelMap) {		 
			JSONObject jb=new JSONObject();
			UserSession user=	ApplicationContext.getUser();
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("acceptAudit", status);
			map.put("id", user.getId());
			firstService.updateAcceptAudit(map);
			String acceptAudit= firstService.getAcceptAudit(user.getId());
			jb.put("status", acceptAudit);
			return jb.toString();
		} 
}
	
