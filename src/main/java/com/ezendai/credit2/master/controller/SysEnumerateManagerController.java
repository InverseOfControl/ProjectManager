package com.ezendai.credit2.master.controller;

import java.util.ArrayList;
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
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.master.model.SysEnumerateManager;
import com.ezendai.credit2.master.service.SysEnumerateManagerService;
import com.ezendai.credit2.master.vo.SysEnumerateManagerVO;
import com.ezendai.credit2.system.controller.BaseController;
import com.ezendai.credit2.system.service.SysLogService;

/**
 * 
 * @Description: 枚举信息
 * @author 张宜超
 * @date 2016年2月2日
 */

@Controller
@RequestMapping("/sysEnumerate")
public class SysEnumerateManagerController extends BaseController{
	
	private static final Logger log =Logger.getLogger(SysEnumerateManagerController.class);

	@Autowired
	private SysEnumerateManagerService SysEnumerateService ;
	
	@Autowired
	private SysLogService sysLogService;
	
	
	
	/**
	 * 
	 * @Description: 枚举list初始化
	 * @param @param request
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author 张宜超
	 * @date 2016年2月2日
	 */
	@RequestMapping("/sysEnumerateInit")
	public String SysEnumerateInit(HttpServletRequest request,ModelMap modelMap){
		log.info("|------初始化枚举列表页面......");
		Long userId = ApplicationContext.getUser().getId();
		Integer userType = ApplicationContext.getUser().getUserType();
		
		request.setAttribute("userId", userId);
		request.setAttribute("userType", userType);
		setDataDictionaryArr(new String[] { EnumConstants.PRODUCT_ID,EnumConstants.PRODUCT_TYPE, EnumConstants.LOAN_STATUS, EnumConstants.CONTRACT_SRC });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		modelMap.put("banType", "");
		return "/master/sysEnumerate/sysEnumerateList";     
	}
	
	
	/**
	 * 
	 * @Description: 查询符合条件的枚举list
	 * @param @param request
	 * @param @param modelMap
	 * @param @return   
	 * @return List<SysEnumerate>  
	 * @throws
	 * @author 张宜超
	 * @date 2016年2月2日
	 */
	@RequestMapping("/getSysEnumerateList")
	@ResponseBody
	public Map<String,Object> showSysEnumerateList(HttpServletRequest request,ModelMap modelMap,int rows, int page){
		log.info("|------开始获取枚举列表.......");
		
		Map<String,Object> result = new HashMap<String,Object>();
		
		Long userId = ApplicationContext.getUser().getId();
		Integer userType = ApplicationContext.getUser().getUserType();
		
		request.setAttribute("userId", userId);
		request.setAttribute("userType", userType);
		setDataDictionaryArr(new String[] { EnumConstants.PRODUCT_ID,EnumConstants.PRODUCT_TYPE, EnumConstants.LOAN_STATUS, EnumConstants.CONTRACT_SRC });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		
		
		SysEnumerateManagerVO vo = null;
		vo = validateSysEnumerate(request);
		Pager pager = new Pager();
		pager.setRows(rows);
		pager.setPage(page);
		vo.setPager(pager);  
		//log.info("rows == " + rows + " ,page == " + page);
		log.info("获取枚举列表...");
		Pager SysEnumerateList = SysEnumerateService.getSysEnumerateList(vo);
		
		if(null != SysEnumerateList && SysEnumerateList.getResultList() != null){
			log.info("SysEnumerate list size is " + SysEnumerateList.getResultList().size());
			result.put("total", SysEnumerateList.getTotalCount());
			result.put("rows", SysEnumerateList.getResultList());
		}else{
			log.info("SysEnumerate list size is 0");
		}
		log.info("|------结束获取枚举列表.......");
		return result;
	}
	


	/**  
	 * 
	 * @Description: 获取单个枚举信息
	 * @param @param request
	 * @param @param modelMap
	 * @param @param id
	 * @param @return   
	 * @return SysEnumerate  
	 * @throws
	 * @author 张宜超
	 * @date 2016年2月2日
	 */
	@RequestMapping("/getSysEnumerate")
	@ResponseBody
	public Map<String, Object> showSysEnumerate(@RequestParam Long id){
		log.info("|------开始获取单个枚举信息.......");
		Map<String,Object> result = new HashMap<String,Object>();
		boolean isSuccess = false;
		String msg = "";
		if(null == id){
			isSuccess = false;
			msg = "记录不存在,或已被删除";
		}
		SysEnumerateManager SysEnumerate = SysEnumerateService.getSysEnumerate(id);
		if(null == SysEnumerate){
			isSuccess = false;
			msg = "记录不存在";
			log.info("没查询到数据，可能已被删除！");
		}else{
			log.info("信息查询成功！");
			isSuccess = true;
		}
		result.put("isSuccess", isSuccess);
		result.put("SysEnumerate", SysEnumerate);
		result.put("msg", msg);
		log.info("|------结束获取单个枚举信息.......");
		return result;
	}
	
	/**
	 * 
	 * @Description: 添加一条枚举信息
	 * @param @param request
	 * @param @return   
	 * @return Map<String, Object>  
	 * @throws
	 * @author 张宜超
	 * @date 2016年2月2日
	 */
	@RequestMapping(value="/addSysEnumerate",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> addSysEnumerate(SysEnumerateManagerVO vo){
		log.info("|------开始添加一条枚举信息.......");
		Map<String,Object> map = new HashMap<String,Object>();
		
		map = validateSysEnumerateInfo("add",vo);
		
		if(map!=null && map.get("isSuccess")!=null && map.get("isSuccess").equals(false)){
			return map;
		}
		map.clear();
		boolean isSuccess = true;
		String msg = "";
		try {
			SysEnumerateService.addSysEnumerate(vo); 
				msg = "新增成功";
			log.info("添加成功！");
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
		
		log.info("|------结束添加一条枚举信息.......");
		return map;
	}
	
	/**
	 * 
	 * @Description: 更新枚举信息
	 * @param @param request
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author 张宜超
	 * @date 2016年2月2日
	 */
	@RequestMapping(value="/updateSysEnumerate",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> updateSysEnumerate(SysEnumerateManagerVO vo){
		log.info("|------开始更新一条枚举信息.......");
		Map<String,Object> map = new HashMap<String,Object>();
		
		map = validateSysEnumerateInfo("update",vo);
		
		if(map!=null && map.get("isSuccess")!=null && map.get("isSuccess").equals(false)){
			return map;
		}
		map.clear();
		boolean isSuccess = true;
		String msg = "";
		try {
			int ucount = SysEnumerateService.updateSysEnumerate(vo);
			if(ucount > 0){
				msg = "修改成功";
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
		
		log.info("|------结束更新一条枚举信息.......");
		return map;
	}
	
	/**
	 * 
	 * @Description: 删除一条枚举数据
	 * @param @param id
	 * @param @return   
	 * @return Map<String,Object>  
	 * @throws
	 * @author 张宜超
	 * @date 2016年2月17日
	 */
	@RequestMapping(value="/deleteEnumerate")
	@ResponseBody
	public Map<String,Object> deleteEnumerate(@RequestParam Long id){
		log.info("|------准备删除一条枚举信息.......");
		Map<String,Object> map = new HashMap<String,Object>();
		boolean isSuccess = true;
		//String msg = "";
		try{
			
			SysEnumerateService.deleteSysEnumerate(id);
			log.info("删除成功");
			map.put("isSuccess", isSuccess);
			map.put("msg", "删除成功");
		}catch(Exception e){
			log.info(e.getMessage());
			map.put("isSuccess", false);
			map.put("msg", "删除失败");
		}
		log.info("|------结束删除一条枚举信息.......");
		return map;
	}
	/**
	 * 
	 * @Description: 获取并组装参数
	 * @param @param request
	 * @param @return   
	 * @return SysEnumerateVO  
	 * @throws
	 * @author 张宜超
	 * @date 2016年2月2日
	 */
	public SysEnumerateManagerVO validateSysEnumerate(HttpServletRequest request){
		log.info("|------获取并组装参数.......");
		SysEnumerateManagerVO vo = new SysEnumerateManagerVO();
		String reqenumType = request.getParameter("enumType");
		String reqenumCode = request.getParameter("enumCode");
		String reqenumValue = request.getParameter("enumValue");
		String reqversion  = request.getParameter("enumversion");
		
		if(null != reqenumType && reqenumType != ""){
			vo.setEnumType(reqenumType);
		}
		
		if(null != reqenumCode && reqenumCode != ""){
			Integer sysenumCode = null;
			sysenumCode = Integer.parseInt(reqenumCode);
			vo.setEnumCode(sysenumCode);
		}
		
		if(null != reqenumValue && reqenumValue != ""){
			vo.setEnumType(reqenumValue);
		}
		
		if(null != reqversion && reqversion !=""){
			Integer version = null;
			version = Integer.parseInt(reqversion);
			vo.setEnumversion(version);
		}
		
		
		log.info("|------组装参数结束......");
		return vo;
	}
	
	/**
	 * 
	 * @Description: 判断数据是否已存在
	 * @param @param vo
	 * @param @return   
	 * @return Map<String,Object>  
	 * @throws
	 * @author 张宜超
	 * @date 2016年2月2日
	 */
	public Map<String, Object> validateSysEnumerateInfo(String mark,SysEnumerateManagerVO vo){
		log.info("|------检验数据是否存在.......");
		Map<String,Object> result = new HashMap<String,Object>();
		
		SysEnumerateManager bkm = new SysEnumerateManager();
		if("update".equals(mark)){
			//获取更新id的更新前的数据信息
			bkm = SysEnumerateService.getSysEnumerate(vo.getId());
			//validateVO.setId(vo.getId());
		}
		SysEnumerateManagerVO validateVO = new SysEnumerateManagerVO();
		validateVO.setEnumCode(vo.getEnumCode());
		//查询同code枚举
		List<SysEnumerateManager> bkList = new ArrayList<SysEnumerateManager>();
		bkList = SysEnumerateService.getSysEnumerateByConditions(validateVO);
		
		if("update".equals(mark) ){
			if(bkList != null && bkList.size() > 0){
				for(int i = 0; i< bkList.size(); i++){
					if(!bkm.getId().equals(bkList.get(i).getId())){
						
						log.info("系统已存在该数据代码!");
						result.put("isSuccess", false);
						result.put("msg", "系统已存在该数据代码!");
						return result;
					}
				}
			}
		}else{
			if(bkList !=null && bkList.size() > 0){
				log.info("系统已存在该数据代码!");
				result.put("isSuccess", false);
				result.put("msg", "系统已存在该数据代码!");
				return result;
			}
		}
		
		validateVO.setEnumCode(null);
		if(bkList != null && bkList.size() > 0){
			
			bkList = null;
		}
		//result.clear();
		
		validateVO.setEnumValue(vo.getEnumValue());
		bkList = SysEnumerateService.getSysEnumerateByConditions(validateVO);
		
		if("update".equals(mark)){
			if(bkList != null && bkList.size() > 0){
				for(int i = 0; i< bkList.size(); i++){
					if(!bkm.getId().equals(bkList.get(i).getId())){
						log.info("系统已存在该数据值!");
						result.put("isSuccess", false);
						result.put("msg", "系统已存在该数据值!");
						return result;
					}
				}
			}
		}else{
			if(bkList !=null && bkList.size() > 0){
				log.info("系统已存在该数据值!");
				result.put("isSuccess", false);
				result.put("msg", "系统已存在该数据值!");
				return result;
			}
		}
		
		validateVO.setEnumValue(null);;
		//result.clear();
		if(bkList != null && bkList.size() > 0){
			
			bkList = null;
		}
		log.info("|------校验数据结束......");
		return result;
	}
}
