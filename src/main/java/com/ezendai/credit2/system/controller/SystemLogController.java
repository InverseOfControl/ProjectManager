package com.ezendai.credit2.system.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.master.model.SysEnumerate;
import com.ezendai.credit2.master.vo.SysEnumerateVO;
import com.ezendai.credit2.system.model.SysUser;
import com.ezendai.credit2.system.service.SysLogService;
import com.ezendai.credit2.system.vo.SysLogVO;
import com.ezendai.credit2.system.vo.SysUserVO;

@Controller
@RequestMapping("/systemLog")
public class SystemLogController extends BaseController{
	@Autowired
	private SysLogService sysLogService;

	/**
	 * 系统日志主界面
	 *	
	 * @return
	 */
	@RequestMapping("/sysLogMain")
	public String sysLogMain(Model model,HttpServletRequest request) {		
		/*设置数据字典*/
		setDataDictionaryArr(new String[] {EnumConstants.OPTION_MODULE ,EnumConstants.OPTION_TYPE});
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		return "system/systemLog";
	}
	/**
	 * 
	 * <pre>
	 * 系统日志列表 
	 * </pre>
	 *
	 * @return
	 */
	@RequestMapping("/getSysLogPage")
	@ResponseBody
	public Map getSysLogPage(SysLogVO sysLogVo,int rows, int page){
		Pager p = new Pager();
		p.setPage(page);
		p.setRows(rows);		
		sysLogVo.setPager(p);
		//没有选择结束时间，设置当前时间为结束时间
		if(sysLogVo.getEndTime()==null){
			sysLogVo.setEndTime(new Date());
		}
		Pager pager = sysLogService.findSysLogByParam(sysLogVo);
	
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("total", pager.getTotalCount());
		result.put("rows", pager.getResultList());
		
		return result;
		
	}
	/***
	 * 
	 * <pre>
	 * 显示操作模块 
	 * 根据OPTION_MODULE类型的Code为前缀,查询OPTION_TYPE类型的List
	 * </pre>
	 *
	 * @return
	 */
	@RequestMapping("/findSysEnumerateListByType")
	@ResponseBody
	public List<SysEnumerate> findSysEnumerateListByType(){
		List<SysEnumerate> sysEnumerateList= sysLogService.findSysEnumerateListByType(EnumConstants.OPTION_MODULE);
		return sysEnumerateList;
	}
	/***
	 * 
	 * <pre>
	 * 根据操作模块显示操作类型  
	 * </pre>
	 *
	 * @return
	 */
	@RequestMapping("/findOptionTypeListByOptionModule")
	@ResponseBody
	public List<SysEnumerate> findOptionTypeListByOptionModule(SysEnumerateVO sysEnumerateVo){
		sysEnumerateVo.setEnumType(EnumConstants.OPTION_TYPE);
		sysEnumerateVo.setParentId(Long.parseLong(sysEnumerateVo.getParentIdStr()));
		List<SysEnumerate> sysEnumerateList= sysLogService.findOptionTypeListByOptionModule(sysEnumerateVo);
		 
		return sysEnumerateList;
	}
	/**
	 * 
	 * <pre>
	 * 获取所有用户
	 * </pre>
	 *
	 * @return
	 */
	@RequestMapping("/findAllUser")
	@ResponseBody
	public  List<SysUser> findAllUser(){
		SysUserVO sysUserVo=new SysUserVO();
		sysUserVo.setIsDeleted(0);
		List<SysUser> sysUserList = sysLogService.findAllSysUser(sysUserVo);
		return sysUserList;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}

}
