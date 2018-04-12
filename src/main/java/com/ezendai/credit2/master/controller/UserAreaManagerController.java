package com.ezendai.credit2.master.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.model.UserAreaManager;
import com.ezendai.credit2.master.service.UserAreaManagerService;
import com.ezendai.credit2.master.vo.UserAreaManagerVO;
import com.ezendai.credit2.system.controller.BaseController;
/**
 * 用户地区管理controller(用于一个用户可以管理多个地区的数据)
 * @author cj
 *2016-07-13
 */
@RequestMapping("/userAreaManager")
@Controller
public class UserAreaManagerController extends BaseController{

	private static final Logger log =Logger.getLogger(UserAreaManagerController.class);
	@Autowired
	private UserAreaManagerService userAreaManagerService;
	
	
	/**
	 * 
	 * @Description: 初始化加载list页面
	 * @param @param request
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author cj
	 * @date 2016年2月1日
	 */
	@RequestMapping("/userAreaManagerInit")
	public String sysParameterInit(HttpServletRequest request){
		return "/master/userAreaManager/userAreaManagerList";
	}
	
	
	
	/**
	 * 
	 * @Description: 查询数据列表
	 * @param @param request
	 * @param @param modelMap
	 * @param @param rows
	 * @param @param page
	 * @param @return   
	 * @return Map<String,Object>  
	 * @throws
	 * @author cj
	 * @date 2016年7月13日
	 */
	@RequestMapping("/userAreaManagerList")
	@ResponseBody
	public Object userAreaManagerList(HttpServletRequest request,int rows, int page,UserAreaManagerVO vo){
		ModelMap model = new ModelMap();
		//log.info("|-----开始查询数据列表.....");
		Pager pager = new Pager();
		pager.setRows(rows);
		pager.setPage(page);
		vo.setPager(pager);
		
		pager = userAreaManagerService.getUserAreaManagerList(vo);
		
		
		//Pager sysparamList = spmService.getSysParameterList(vo);
//		if(null != sysparamList && sysparamList.getResultList() != null){
//			log.info("sysparameter list size is " + sysparamList.getResultList().size());
//			result.put("total", sysparamList.getTotalCount());
//			result.put("rows", sysparamList.getResultList());
////		}else{
//			log.info("bank list size is 0");
//		}
//		log.info("|-----结束查询数据列表.....");
		model.addAttribute("total", pager.getTotalCount());
		model.addAttribute("rows", pager.getResultList());
		
		return model;
	}
	
	/**
	 * 新增用户地区管理
	 * @param baseAreaVO
	 * @return
	 */
	@RequestMapping("/insertUserAreaManager")
	@ResponseBody
	public Object insertUserAreaManager(UserAreaManager userAreaManager) {
		Map<String,String> map = new HashMap<String,String>();
		String msg = "";
		try {
			
			Long id = userAreaManagerService.insertUserAreaManager(userAreaManager);
			
			
				msg = "新增成功";
//				// 插入系统日志
//				SysLog sysLog = new SysLog();
//				sysLog.setOptModule(EnumConstants.OptionModule.TEXTRESOURCE_MANAGER.getValue());
//				sysLog.setOptType(EnumConstants.OptionType.TEXTRESOURCE_ADD.getValue());
//				sysLog.setRemark("新增成功！"+"   ID:"+id);
//				sysLogService.insert(sysLog);
		} catch(Exception e) {
			msg ="新增失败";
			e.printStackTrace();
		}
		map.put("msg", msg);
		return map;
	}
	
	
	/**
	 * 修改用户地区管理
	 * @param baseAreaVO
	 * @return
	 */
	@RequestMapping("/updateUserAreaManager")
	@ResponseBody
	public Object updateUserAreaManager(UserAreaManagerVO vo) {
		Map<String,String> map = new HashMap<String,String>();
		String msg = "";
		try {
			
			int id = userAreaManagerService.updateUserAreaManager(vo);
			
			
				msg = "修改成功";
//				// 插入系统日志
//				SysLog sysLog = new SysLog();
//				sysLog.setOptModule(EnumConstants.OptionModule.TEXTRESOURCE_MANAGER.getValue());
//				sysLog.setOptType(EnumConstants.OptionType.TEXTRESOURCE_ADD.getValue());
//				sysLog.setRemark("新增成功！"+"   ID:"+id);
//				sysLogService.insert(sysLog);
		} catch(Exception e) {
			msg ="修改失败";
			e.printStackTrace();
		}
		map.put("msg", msg);
		return map;
	}
	
	@RequestMapping("/findUserAreaManagerById")
	@ResponseBody
	public Object findUserAreaManagerById(Long id) {
		UserAreaManagerVO userAreaManagerVO = null;
		try {
			
			userAreaManagerVO = userAreaManagerService.findUserAreaManagerById(id);
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return userAreaManagerVO;
	}
	
	
}
