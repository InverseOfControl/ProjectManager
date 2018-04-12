package com.ezendai.credit2.apply.controller; 
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezendai.credit2.apply.model.ChannelPlan;
import com.ezendai.credit2.apply.model.ChannelPlanCheck;
import com.ezendai.credit2.apply.service.ChannelPlanCheckService;
import com.ezendai.credit2.apply.service.ChannelPlanService;
import com.ezendai.credit2.apply.vo.ChannelPlanCheckVO;
import com.ezendai.credit2.apply.vo.ChannelPlanVO;
import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.framework.model.UserSession;
import com.ezendai.credit2.framework.util.BeanUtil;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.system.controller.BaseController;
import com.ezendai.credit2.system.model.SysLog;
import com.ezendai.credit2.system.service.SysLogService;

@Controller
@RequestMapping("/reply/replyData")
public class ChannelPlanCheckController extends BaseController {
	@Autowired
	private ChannelPlanCheckService checkService;
	@Autowired
	private ChannelPlanService planService;
	
	@Autowired
	private SysLogService sysLogService;
	
	private static final Logger logger = Logger.getLogger(ChannelPlanCheckController.class);
	 
	@RequestMapping("/list")
	public String init(HttpServletRequest request) {
		/*设置数据字典*/
		setDataDictionaryArr(new String[] { EnumConstants.LOAN_STATUS, EnumConstants.PRODUCT_TYPE, EnumConstants.CONTRACT_SRC });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
	 	return "apply/reply/replyDateList"; 
	/*	return "/finance/financialAudit/financialAuditList";*/
	}
 
	 
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getreplyDataPage2")
	@ResponseBody
	public Map getFinacialAuditPage2(ChannelPlanCheckVO vo, int rows, int page) {
		Pager p = new Pager();
		p.setPage(page);
		p.setRows(rows);
		p.setSidx("ch.CREATED_TIME");
		p.setSort("desc");
		vo.setPager(p);
		Pager pager = checkService.checkFindWithPG(vo);
	 	List<ChannelPlanCheck> checkList = pager.getResultList(); 
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("total", pager.getTotalCount());
		result.put("rows", checkList);
		return result;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getreplyDataPage")
	@ResponseBody
	public Map getFinacialAuditPage(ChannelPlanCheckVO vo, int rows, int page) {
		Pager p = new Pager();
		p.setPage(page);
		p.setRows(rows);
		p.setSidx("ch.CREATED_TIME");
		p.setSort("desc");
		vo.setPager(p);
		Pager pager = checkService.checkFindWithPG(vo);
	 	List<ChannelPlanCheck> checkList = pager.getResultList(); 
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("total", pager.getTotalCount());
		result.put("rows", checkList);
		return result;
	}
	@RequestMapping("/seReplyEdit/{id}")
	public String seReplyEdit(@PathVariable("id") String id,HttpServletRequest request,ModelMap map) throws Exception{
		/*设置数据字典*/
	/*	setDataDictionaryArr(new String[] { EnumConstants.PRODUCT_STATUS});
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());*/
		/*List<BaseArea> allCitys = baseAreaService.getAllCitys();
		model.addAttribute("cityList",allCitys);*/
		ChannelPlanCheck check=	checkService.getReplyById(Long.valueOf(id));
		map.put("check", check);
		return "apply/reply/seReplyEdit";
	}
		@RequestMapping("/replyEditSave")
		@ResponseBody
		public Map<String, Object> productEditSave(ChannelPlanCheckVO vo) throws Exception{
			Map< String, Object> result = new HashMap<String, Object>();
			//edit
			try {
				//新增待审核
				ChannelPlan plan=new ChannelPlan();
 				ChannelPlanVO planvo=new ChannelPlanVO();
				ChannelPlanCheck check=	checkService.getReplyById(Long.valueOf(vo.getId()));
				UserSession user = ApplicationContext.getUser();
				check.setOperatorId(user.getId());
				check.setModifierId((user.getId()));
				check.setModifier((user.getLoginName()));
				SysLog sysLog = new SysLog();
				sysLog.setOptModule(EnumConstants.OptionModule.SCHEME_MANAGE
						.getValue());
			
				sysLog.setRemark("方案ID:" + check.getPlan_id());
				
			 	if(vo.getApproverState()==3){
					if(check.getApproverState()==0){
						check.setApproverState(3);
						BeanUtil.copyProperties(planvo, check);
						planvo.setId(check.getPlan_id());
						planvo.setPlanState("00");
						checkService.updateAndUpdatePlan(vo,planvo);
					}else if(check.getApproverState()==1){
						check.setApproverState(3);
						BeanUtil.copyProperties(planvo, check);
						planvo.setId(check.getPlan_id());
						planvo.setPlanState("00");
						checkService.updateAndUpdatePlan(vo, planvo);
					}else if(check.getApproverState()==2){
						check.setApproverState(3);
						BeanUtil.copyProperties(planvo, check);
						planvo.setId(check.getPlan_id());
						planvo.setPlanState("00");
					/*	checkService.deleteAnddeletePlan(vo, planvo);*/
						checkService.updateAndUpdatePlan(vo, planvo);
					}
					result.put("flag", true);
					result.put("date", check.getStartDate());
					sysLog.setOptType(EnumConstants.OptionType.SCHEME_APPROVE.getValue());
				}else if(vo.getApproverState()==4){
					if(check.getApproverState()==0){
						check.setApproverState(4);
						checkService.updateReplyStatus(vo);
					}else if(check.getApproverState()==1){
						check.setApproverState(4);
						BeanUtil.copyProperties(planvo, check);
						planvo.setId(check.getPlan_id());
						planvo.setPlanState("00");
						checkService.updateAndUpdatePlan(vo, planvo);
					}else if(check.getApproverState()==2){
						check.setApproverState(4);
						BeanUtil.copyProperties(planvo, check);
						planvo.setId(check.getPlan_id());
						planvo.setPlanState("00");
						checkService.updateAndUpdatePlan(vo, planvo);	
					} 
					sysLog.setOptType(EnumConstants.OptionType.SCHEME_REFUSE.getValue());
				} 
			 	sysLogService.insert(sysLog);
				result.put("success", true);
			} catch (Exception e) {
				logger.error("产品编辑异常", e);
				result.put("success", false);
				result.put("msg", e.getMessage());
			}
			return result;
		}
	 
}
