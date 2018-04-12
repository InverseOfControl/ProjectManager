package com.ezendai.credit2.master.controller;

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
import com.ezendai.credit2.framework.cache.CacheService;
import com.ezendai.credit2.framework.exception.BusinessException;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.master.model.SysParameterManager;
import com.ezendai.credit2.master.service.SysParameterManagerService;
import com.ezendai.credit2.master.vo.SysParameterManagerVO;
import com.ezendai.credit2.system.controller.BaseController;
import com.ezendai.credit2.system.model.SysParameter;
import com.ezendai.credit2.system.service.SysLogService;

@RequestMapping("/sysParameter")
@Controller
public class SysParameterController extends BaseController{

	private static final Logger log =Logger.getLogger(SysParameterController.class);
	
	@Autowired
	private SysLogService sysLogService;
	
	@Autowired
	private SysParameterManagerService spmService;
	//刷新缓存
	@Autowired
	private CacheService cacheService;
	
	/**memcached key 加上一些开始字符串，防止key冲突*/
	private static final String PARAMETER_START = "CREDIT2_SYSPARA_";
	
	/**
	 * 
	 * @Description: 初始化加载list页面
	 * @param @param request
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author 张宜超
	 * @date 2016年2月1日
	 */
	@RequestMapping("/sysParameterInit")
	public String sysParameterInit(HttpServletRequest request){
		log.info("|-----初始化系统参数.....");
		Long userId = ApplicationContext.getUser().getId();
		Integer userType = ApplicationContext.getUser().getUserType();
		
		request.setAttribute("userId", userId);
		request.setAttribute("userType", userType);
		setDataDictionaryArr(new String[] { EnumConstants.PRODUCT_ID,EnumConstants.PRODUCT_TYPE, EnumConstants.LOAN_STATUS, EnumConstants.CONTRACT_SRC });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		return "/master/sysParameter/sysParameterList";
	}
	
	/**
	 * 刷新数据
	 */
	@RequestMapping("/sysParameterFlush")
	@ResponseBody
	public Map<String,Object> sysParameterFlush(@RequestParam Long id,HttpServletRequest request){
		Map<String,Object> map = new HashMap<String,Object>();
		log.info("|-----刷新参数id为" + id + "的缓存......");
		String rpageNo = request.getParameter("pageNo");
		SysParameterManager spam = spmService.getSysParameter(id);
		SysParameter spmt = new SysParameter();
		if(spam != null){
			spmt.setCode(spam.getCode());
			spmt.setCreatedTime(spam.getCreatedTime());
			spmt.setCreator(spam.getCreator());
			spmt.setCreatorId(spam.getCreatorId());
			spmt.setId(spam.getId());
			spmt.setInputType(spam.getInputType());
			spmt.setIsDisabled(spam.getIsDisabled());
			spmt.setModifiedTime(spam.getModifiedTime());
			spmt.setModifier(spam.getModifier());
			spmt.setModifierId(spam.getModifierId());
			spmt.setName(spam.getName());
			spmt.setParameterValue(spam.getParameterValue());
			spmt.setRemark(spam.getRemark());
			spmt.setVersion(spam.getVersion());
			cacheService.removeData(PARAMETER_START + spmt.getCode());
			cacheService.data2Cache(PARAMETER_START + spmt.getCode(), spmt);
			map.put("success", true);
			map.put("msg", "刷新成功");
			log.info("缓存刷新成功！");
		}else{
			log.info("数据异常！");
			map.put("success", false);
			map.put("msg", "刷新失败！");
		}
		return map;
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
	 * @author 张宜超
	 * @date 2016年2月1日
	 */
	@RequestMapping("/sysParameterList")
	@ResponseBody
	public Map<String,Object> showBankList(HttpServletRequest request,ModelMap modelMap,int rows, int page){
		log.info("|-----开始查询数据列表.....");

		Map<String,Object> result = new HashMap<String,Object>();
		Long userId = ApplicationContext.getUser().getId();
		Integer userType = ApplicationContext.getUser().getUserType();
		
		request.setAttribute("userId", userId);
		request.setAttribute("userType", userType);
		setDataDictionaryArr(new String[] { EnumConstants.PRODUCT_ID,EnumConstants.PRODUCT_TYPE, EnumConstants.LOAN_STATUS, EnumConstants.CONTRACT_SRC });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		
		SysParameterManagerVO vo = new SysParameterManagerVO();
		vo = validateSysParameter(request);
		Pager pager = new Pager();
		pager.setRows(rows);
		pager.setPage(page);
		vo.setPager(pager);
		
		Pager sysparamList = spmService.getSysParameterList(vo);
		if(null != sysparamList && sysparamList.getResultList() != null){
			log.info("sysparameter list size is " + sysparamList.getResultList().size());
			result.put("total", sysparamList.getTotalCount());
			result.put("rows", sysparamList.getResultList());
		}else{
			log.info("bank list size is 0");
		}
		log.info("|-----结束查询数据列表.....");
		
		return result;
	}
	
	/**
	 * 
	 * @Description: 获取一条数据信息
	 * @param @param id
	 * @param @return   
	 * @return Map<String,Object>  
	 * @throws
	 * @author 张宜超
	 * @date 2016年2月1日
	 */
	@RequestMapping("/getSysParameter")
	@ResponseBody
	public Map<String, Object> showSysParameter(@RequestParam Long id){
		log.info("|-----开始获取一条系统参数.....");
		boolean success = true;
		String msg = "";
		Map<String,Object> map = new HashMap<String,Object>();
		
		if(null == id){
			success = false;
			msg = "记录不存在,或已被删除";
		}
		SysParameterManager spm = spmService.getSysParameter(id);
		if(null == spm){
			success = false;
			msg = "记录不存在";
			log.info("没查询到数据，可能已被删除！");
		}else{
			log.info("信息查询成功！");
			success = true;
		}
		map.put("success", success);
		map.put("sysParameter", spm);
		map.put("msg", msg);
		log.info("|-----结束获取一条系统参数.....");
		return map;
	}
	
	/**
	 * 
	 * @Description: 添加一条系统参数数据
	 * @param @param vo
	 * @param @return   
	 * @return Map<String,Object>  
	 * @throws
	 * @author 张宜超
	 * @date 2016年2月1日
	 */
	@RequestMapping(value="/addSysParameter",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> addSysParameter(SysParameterManagerVO vo){
		log.info("|-----开始添加一条系统参数.....");
		Map<String,Object> map = new HashMap<String,Object>();
		boolean success = true;
		map = validateSysParameterInfo("add",vo);
		
		if(map!=null && map.get("success")!=null && map.get("success").equals(false)){
			return map;
		}
		map.clear();
		String msg = "";
		try {
			spmService.addSysParameter(vo); 
				msg = "新增成功";
			log.info("添加成功！");
		} catch(BusinessException ex) {
			success = false;
			msg = "新增失败";
			log.info("添加失败！");
		} catch(Exception ex) {
			success = false;
			msg = ex.getMessage();
			log.info("添加失败！");
		}
		map.put("success", success);
		map.put("msg", msg);
		
		
		log.info("|-----结束添加一条系统参数.....");
		return map;
	}
	
	/**
	 * 
	 * @Description: 修改一条数据
	 * @param @param vo
	 * @param @return   
	 * @return Map<String,Object>  
	 * @throws
	 * @author 张宜超
	 * @date 2016年2月1日
	 */
	@RequestMapping(value="/updateSysParameter",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> updateSysParameter(SysParameterManagerVO vo){
		log.info("|-----开始更新一条系统参数.....");
		Map<String,Object> map = new HashMap<String,Object>();
		map = validateSysParameterInfo("update",vo);
		
		if(map!=null && map.get("success")!=null && map.get("success").equals(false)){
			return map;
		}
		map.clear();
		boolean success = true;
		String msg = "";
		try {
			int ucount = spmService.updateSysParameter(vo);
			if(ucount > 0){
				msg = "修改成功";
			}
		} catch(BusinessException ex) {
			log.info("信息更新失败！");
			success = false;
			msg = "修改失败";
		} catch(Exception ex) {
			log.info("信息更新失败！");
			success = false;
			msg = ex.getMessage();
		}
		map.put("success", success);
		map.put("msg", msg);
		log.info("|-----结束更新一条系统参数.....");
		return map;
	}
	
	/**
	 * 
	 * @Description: 删除一条参数信息
	 * @param @param id
	 * @param @return   
	 * @return Map<String,Object>  
	 * @throws
	 * @author 张宜超
	 * @date 2016年2月17日
	 */
	@RequestMapping(value="/deleteParameter")
	@ResponseBody
	public Map<String,Object> deleteParameter(@RequestParam Long id){
		log.info("|------准备删除一条参数信息.......");
		Map<String,Object> map = new HashMap<String,Object>();
		boolean isSuccess = true;
		//String msg = "";
		try{
			
			spmService.deleteSysParameter(id);
			log.info("删除成功");
			map.put("isSuccess", isSuccess);
			map.put("msg", "删除成功");
		}catch(Exception e){
			log.info(e.getMessage());
			map.put("isSuccess", false);
			map.put("msg", "删除失败");
		}
		log.info("|------结束删除一条参数信息.......");
		return map;
	}
	
	/**
	 * 组装参数
	 */
	public SysParameterManagerVO validateSysParameter(HttpServletRequest request){
		log.info("|------获取并组装参数.......");
		
		String reqcode = request.getParameter("code");
		String reqname = request.getParameter("name");
		String reqparameterValue = request.getParameter("parameterValue");
		String reqremark = request.getParameter("remark");
		String reqversion = request.getParameter("version");
		String reqisDisabled = request.getParameter("isDisabled");
		
		SysParameterManagerVO vo = new SysParameterManagerVO();
		if(reqcode !=null && reqcode != ""){
			vo.setCode(reqcode);
		}
		
		if(reqname != null && reqname != ""){
			vo.setName(reqname);
		}
		
		if(reqparameterValue != null && reqparameterValue != ""){
			vo.setParameterValue(reqparameterValue);
		}
		
		vo.setInputType(1);
		
		if(reqremark !=null && reqremark != ""){
			vo.setRemark(reqremark);
		}
		
		
		
		if(reqversion != null){
			Integer spmversion = Integer.parseInt(reqversion);
			vo.setSpmversion(spmversion);
		}else{
			vo.setSpmversion(1);
		}
		
		if(reqisDisabled != null){
			Integer isDisabled = Integer.parseInt(reqisDisabled);
			vo.setIsDisabled(isDisabled);
		}
		
		log.info("|------组装参数结束......");
		return vo;
	}
	
	/**
	 * 
	 * @Description: 校验数据
	 * @param @param mark
	 * @param @param vo
	 * @param @return   
	 * @return Map<String,Object>  
	 * @throws
	 * @author 张宜超
	 * @date 2016年2月1日
	 */
	public Map<String, Object> validateSysParameterInfo(String mark,SysParameterManagerVO vo){
		log.info("|------检验数据是否存在.......");
		Map<String,Object> result = new HashMap<String,Object>();
		SysParameterManager spManager = new SysParameterManager();
		//用于比对数据
		if("update".equals(mark)){
			spManager = spmService.getSysParameter(vo.getId());
		}
		
		SysParameterManagerVO spmVO = new SysParameterManagerVO();
		spmVO.setCode(vo.getCode());
		List<SysParameterManager> spmList = spmService.getSysParameterByConditions(spmVO);
		
		if(spmList != null && spmList.size() > 0){
			if("update".equals(mark)){
				for(int i = 0; i < spmList.size(); i++){
					if(!spManager.getId().equals(spmList.get(i).getId())){
						log.info("系统已存在该代码!");
						result.put("success", false);
						result.put("msg", "系统已存在该代码!");
						return result;
					}
				}
			}else{
				log.info("系统已存在该代码!");
				result.put("success", false);
				result.put("msg", "系统已存在该代码!");
				return result;
			}
		}
		
		spmVO.setCode(null);
		if(spmList != null && spmList.size() > 0){
			spmList = null;
		}
		
		spmVO.setName(vo.getName());
		spmList = spmService.getSysParameterByConditions(spmVO);
		if(spmList != null && spmList.size() > 0){
			if("update".equals(mark)){
				for(int i = 0; i < spmList.size(); i++){
					if(!spManager.getId().equals(spmList.get(i).getId())){
						log.info("系统已存在该名称!");
						result.put("success", false);
						result.put("msg", "系统已存在该名称!");
						return result;
					}
				}
			}else{
				log.info("系统已存在该名称!");
				result.put("success", false);
				result.put("msg", "系统已存在该名称!");
				return result;
			}
		}
		
		spmVO.setName(null);
		if(spmList != null && spmList.size() > 0){
			spmList = null;
		}
		
		/*spmVO.setParameterValue(vo.getParameterValue());
		spmList = spmService.getSysParameterByConditions(spmVO);
		if(spmList != null && spmList.size() > 0){
			if("update".equals(mark)){
				for(int i = 0; i < spmList.size(); i++){
					if(!spManager.getId().equals(spmList.get(i).getId())){
						log.info("系统已存在该参数值!");
						result.put("success", false);
						result.put("msg", "系统已存在该参数值!");
						return result;
					}
				}
			}else{
				log.info("系统已存在该参数值!");
				result.put("success", false);
				result.put("msg", "系统已存在该参数值!");
				return result;
			}
		}*/
		log.info("|------校验数据结束......");
		return result;
	}
}
