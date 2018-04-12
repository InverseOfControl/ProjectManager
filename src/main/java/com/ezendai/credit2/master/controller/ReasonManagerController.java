package com.ezendai.credit2.master.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.framework.exception.BusinessException;
import com.ezendai.credit2.framework.util.DateUtil;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.master.model.RefusedReason;
import com.ezendai.credit2.master.service.ReasonManagerService;
import com.ezendai.credit2.master.vo.RefusedReasonVO;
import com.ezendai.credit2.system.controller.BaseController;
import com.ezendai.credit2.system.model.SysLog;
import com.ezendai.credit2.system.service.SysLogService;


@Controller
@RequestMapping("/reasonManager")
public class ReasonManagerController  extends BaseController{
	private static final Logger log =Logger.getLogger(BankManagerController.class);
	
	@Autowired
	private ReasonManagerService reasonService;
	@Autowired
	private SysLogService sysLogService;
	
	/**
	 * 
	 * @Description: 拒绝原因list初始化
	 * @param request
	 * @return String  
	 * @throws
	 * @author rencq
	 * @date 2016年8月8日
	 */
	@RequestMapping("/reasonInit")
	public String bankInit(HttpServletRequest request,ModelMap modelMap){
		log.info("|------初始化银行列表页面......");
		Long userId = ApplicationContext.getUser().getId();
		Integer userType = ApplicationContext.getUser().getUserType();
		
		request.setAttribute("userId", userId);
		request.setAttribute("userType", userType);
		setDataDictionaryArr(new String[] { EnumConstants.PRODUCT_ID,EnumConstants.PRODUCT_TYPE, EnumConstants.LOAN_STATUS, EnumConstants.CONTRACT_SRC });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		modelMap.put("banType", "");
		return "/master/refusedReason/refusedReasonList";
	}
	
	/**
	 * 
	 * @Description: 查询所有父原因list，用于填充父原因下拉列表
	 * @param 
	 * @return List<RefusedReason>  
	 * @throws
	 * @author rencq
	 */
	@RequestMapping("/findAllParentReasonList")
	@ResponseBody
	public List<RefusedReason> findAllParentReasonList(){
		List<RefusedReason> list = new ArrayList<RefusedReason>();
		list = reasonService.findAllParentReasonList();
		RefusedReason reason0 = new RefusedReason();
		reason0.setId(null);
		reason0.setReason("无");
		list.add(0, reason0);
		return list;
	} 
	
	/**
	 * 
	 * @Description: 查询拒绝原因list
	 * @param @param request
	 * @param @param modelMap
	 * @param @return   
	 * @return List<Bank>  
	 * @throws
	 * @author rencq
	 */
	@RequestMapping("/getReasonList")
	@ResponseBody
	public Map<String,Object> showReasonList(HttpServletRequest request,ModelMap modelMap,int rows, int page){
		log.info("|------开始获取拒绝原因列表.......");
		
		Map<String,Object> result = new HashMap<String,Object>();
		
		Long userId = ApplicationContext.getUser().getId();
		Integer userType = ApplicationContext.getUser().getUserType();
		
		request.setAttribute("userId", userId);
		request.setAttribute("userType", userType);
		setDataDictionaryArr(new String[] { EnumConstants.PRODUCT_ID,EnumConstants.PRODUCT_TYPE, EnumConstants.LOAN_STATUS, EnumConstants.CONTRACT_SRC });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		String reason = request.getParameter("reason");
		
		RefusedReasonVO vo = new RefusedReasonVO();
		if(reason != null && !"".equals(reason)){
			vo.setReason(reason.trim());
		}else{
			vo.setReason("");
		}
		Pager pager = new Pager();
		pager.setRows(rows);
		pager.setPage(page);
		vo.setPager(pager);
		log.info("分页查询入参：reason == " + reason);
		Pager reasonList = reasonService.getReasonList(vo);
		
		if(null != reasonList && reasonList.getResultList() != null){
			log.info("bank list size is " + reasonList.getResultList().size());
			result.put("total", reasonList.getTotalCount());
			result.put("rows", reasonList.getResultList());
		}else{
			log.info("reason list size is 0");
		}
		log.info("|------结束获取拒绝原因列表.......");
		return result;
	}
	
	/**
	 * 
	 * @Description: 新增拒绝原因
	 * @param 
	 * @return List<RefusedReason>  
	 * @throws
	 * @author rencq
	 */
	@RequestMapping(value="/addReason",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> addBank(RefusedReasonVO vo){
		log.info(".......开始添加一条拒绝原因信息.......");
		Map<String,Object> map = new HashMap<String,Object>();
		Long userId = ApplicationContext.getUser().getId();
		String userName = ApplicationContext.getUser().getName();
		int version = 1;
		boolean isSuccess = true;
		String msg = "";
		if(vo != null){
			vo.setVersion((long)version);
			vo.setCreatorId(userId);
			vo.setCreator(userName);
			vo.setCreatedTime(DateUtil.getTodayHHmmss());
			vo.setType(vo.getParentId()==null?"1":"2");
			vo.setIsDeleted(0);//默认有效
			vo.setLoanType(2);
		}
		try {
			reasonService.addReason(vo); 
				msg = "新增成功";
			log.info("添加成功！");
			SysLog sysLog = new SysLog();
			sysLog.setOptModule(EnumConstants.OptionModule.REFUSED_REASON_MANAGER.getValue());
			sysLog.setOptType(EnumConstants.OptionType.ADD_REFUSED_REASON.getValue());
			sysLog.setRemark("新增拒绝原因: ID:"+vo.getId()+"reason:" + vo.getReason()+ ",type:" + vo.getType()+ ",isDeleted:" + vo.getIsDeleted()+ ",ParentReason:" 
			+ vo.getParentReason()+ ",parentId:" + vo.getParentId());
			sysLogService.insert(sysLog);
		} catch(BusinessException ex) {
			isSuccess = false;
			msg = "新增失败";
			log.info("添加失败！");
		} catch(Exception ex) {
			isSuccess = false;
			msg = ex.getMessage();
			log.info("添加失败！");
		}
		map.put("isSuccess", isSuccess);
		map.put("msg", msg);
		
		log.info("------结束添加一条拒绝原因信息.......");
		return map;
	}
	
	/**
	 * 
	 * @Description: 根据id查询拒绝原因信息
	 * @param 
	 * @return List<RefusedReason>  
	 * @throws
	 * @author rencq
	 */
	@RequestMapping("/getReasonById")
	@ResponseBody
	public Map<String, Object> getReasonById(@RequestParam Long id){
		log.info("|------开始获取单个原因信息.......");
		Map<String,Object> result = new HashMap<String,Object>();
		boolean isSuccess = false;
		String msg = "";
		if(null == id){
			isSuccess = false;
			msg = "记录不存在,或已被删除";
		}
		RefusedReason reason = reasonService.getReasonById(id);
		if(null == reason){
			isSuccess = false;
			msg = "记录不存在";
			log.info("没查询到数据，可能已被删除！");
		}else{
			log.info("信息查询成功！");
			isSuccess = true;
		}
		result.put("isSuccess", isSuccess);
		result.put("reason", reason);
		result.put("msg", msg);
		log.info("|------结束获取单个原因信息.......");
		return result;
	}
	
	/**
	 * 
	 * @Description: 更新拒绝原因信息
	 * @param @param request
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author rencq
	 * @date 2016年1月25日
	 */
	@RequestMapping(value="/updateReason",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> updateReason(RefusedReasonVO vo){
		log.info("|------开始更新一条拒绝原因信息.......");
		Map<String,Object> map = new HashMap<String,Object>();
		Long userId = ApplicationContext.getUser().getId();
		String userName = ApplicationContext.getUser().getName();
		if(map!=null && map.get("isSuccess")!=null && map.get("isSuccess").equals(false)){
			return map;
		}
		map.clear();
		boolean isSuccess = true;
		String msg = "";
		try {
			log.info("id = " +vo.getId()+" reason = "+vo.getReason());
			if(vo != null){
				Long version = vo.getVersion();
				vo.setVersion(version==null?1:version++);
				if(version == null){
					vo.setVersion((long)1);
				}else{
					vo.setVersion(version+1);
				}
				vo.setModifier(userName);
				vo.setModifierId(userId);
				vo.setModifiedTime(DateUtil.getTodayHHmmss());
				if(vo.getParentId() == null){
					vo.setType("1");
				}else{
					vo.setType("2");
				}
			}
			int ucount = reasonService.updateReason(vo);
			if(ucount > 0){
				msg = "修改成功";
				SysLog sysLog = new SysLog();
				sysLog.setOptModule(EnumConstants.OptionModule.REFUSED_REASON_MANAGER.getValue());
				sysLog.setOptType(EnumConstants.OptionType.UP_REFUSED_REASON.getValue());
				sysLog.setRemark("被修改的拒绝原因ID:"+vo.getId());
				sysLogService.insert(sysLog);
			}
		} catch(BusinessException ex) {
			log.info("信息更新失败！");
			isSuccess = false;
			msg = "修改失败";
		} catch(Exception ex) {
			log.info("信息更新失败！");
			isSuccess = false;
			msg = ex.getMessage();
		}
		map.put("isSuccess", isSuccess);
		map.put("msg", msg);
		
		log.info("|------结束更新一条拒绝原因信息.......");
		return map;
	}
	
	/**
	 * 
	 * @Description: 禁用/启用
	 * @param id
	 * @return int  
	 * @throws
	 * @author rencq
	 */
	@RequestMapping(value="/disableReason")
	@ResponseBody
	public Map<String,Object> disableReason(@RequestParam Long id,@RequestParam int isDeleted){
		log.info("|------准备禁用/启用一条银行信息.......");
		Map<String,Object> map = new HashMap<String,Object>();
		RefusedReasonVO reason = new RefusedReasonVO();
		String msg="";
		boolean isSuccess = true;
		reason.setId(id);
		if(isDeleted == 0){
			reason.setIsDeleted(1); //禁用
			msg="禁用";
		}else{
			reason.setIsDeleted(0);	//启用
			msg="启用";
		}
		try{
			reasonService.disableReason(reason);
			log.info("禁用/启用成功");
			map.put("isSuccess", isSuccess);
			map.put("msg", msg+"成功");
			SysLog sysLog = new SysLog();
			sysLog.setOptModule(EnumConstants.OptionModule.REFUSED_REASON_MANAGER.getValue());
			sysLog.setOptType(EnumConstants.OptionType.DISABLE_REFUSED_REASON.getValue());
			sysLog.setRemark("ID:"+reason.getId()+"，操作类型："+msg);
			sysLogService.insert(sysLog);
		}catch(Exception e){
			log.info(e.getMessage());
			map.put("isSuccess", false);
			map.put("msg", msg+"失败");
		}
		log.info("|------结束禁用/启用一条原因信息.......");
		return map;
	}

}
