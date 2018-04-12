package com.ezendai.credit2.system.controller;

import java.util.ArrayList;
import java.util.Date;
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

import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.framework.cache.CacheService;
import com.ezendai.credit2.framework.constant.Constants;
import com.ezendai.credit2.framework.model.UserSession;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.master.model.SysEnumerate;
import com.ezendai.credit2.master.service.SysEnumerateService;
import com.ezendai.credit2.system.model.SysLog;
import com.ezendai.credit2.system.model.SysUser;
import com.ezendai.credit2.system.model.TextResource;
import com.ezendai.credit2.system.service.SysLogService;
import com.ezendai.credit2.system.service.SysUserService;
import com.ezendai.credit2.system.service.TextResourcesService;
import com.ezendai.credit2.system.vo.TextResourceVO;

@Controller
@RequestMapping("/TextResource/main")
public class TextResourceController extends BaseController {
	@Autowired
	private SysLogService sysLogService;
	@Autowired
	private SysUserService sysUserService;
	/**
	 * 获取数据字典的service
	 */
	@Autowired
	private SysEnumerateService sysEnumerateService;
	private static final Logger logger = Logger
			.getLogger(TextResourceController.class);

	@Autowired
	private TextResourcesService textResourcesService;
	@Autowired
	private CacheService cacheService;

	@RequestMapping("/toUI")
	public String init(HttpServletRequest request) {
		setDataDictionaryArr(new String[] { EnumConstants.LOAN_STATUS,
				EnumConstants.TEXT_RESOURCES_TYPE,
				EnumConstants.TEXT_RESOURCES_LANGUAGE });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		request.setAttribute(
				TEXT_RESOURCE_JSON,
				getTextTypeJson(new Integer[] { EnumConstants.TextResourceType.TABLE_TITLE
						.getValue() }));
		return "system/textResource/textResourceList";

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/searchPage")
	@ResponseBody
	public Map searchPage(TextResourceVO vo, int rows, int page) {
		Pager p = new Pager();
		p.setPage(page);
		p.setRows(rows);
		// p.setSidx("ch.CREATED_TIME");
		// p.setSort("desc");
		vo.setPager(p);
		Pager pager = textResourcesService.findTextResourcesWithPG(vo);
		List<TextResource> checkList = pager.getResultList();
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("total", pager.getTotalCount());
		result.put("rows", checkList);
		return result;
	}

	// 刷新缓存
	@RequestMapping(value = "/refresh/{type}")
	@ResponseBody
	public String refreshCache(@PathVariable("type") String type,
			HttpServletRequest request, ModelMap modelMap) {
		Integer.parseInt(type);
		List<TextResource> list = textResourcesService
				.findTextResourcesByType(Integer.parseInt(type));
		cacheService.data2Cache(type + "time", new Date(),
				Constants.SESSION_EXPIRE);
		cacheService.data2Cache(type, list, Constants.SESSION_EXPIRE);
		return null;
	}

	/**
	 * 获取文字资源数据字典/获取文字语言数据字典
	 * 
	 * @param type
	 *            TEXT_RESOURCES_TYPE 文字资源 TEXT_RESOURCES_LANGUAGE 文字语言TEXT_RESOURCES_ISAVALIABILITY是否可用
	 * @return
	 */
	@RequestMapping(value = "findResourcesData")
	@ResponseBody
	public Object findResourcesType(String type) {
		List<SysEnumerate> sysEnumList  = sysEnumerateService.findSysEnumerateListByType(type);
		// 如果传入的是是否可用下拉框，则从枚举中获取值
		if (EnumConstants.IsAvailability.isAvaliablityType.getValue().equals(
				type)) {
			sysEnumList = new ArrayList<SysEnumerate>();;
			SysEnumerate sysEnumerate1 = new SysEnumerate();
			sysEnumerate1.setEnumCode(Integer
					.parseInt(EnumConstants.IsAvailability.isTrueCode
							.getValue()));
			sysEnumerate1.setEnumValue(EnumConstants.IsAvailability.isTrue
					.getValue());
			SysEnumerate sysEnumerate2 = new SysEnumerate();
			sysEnumerate2.setEnumCode(Integer
					.parseInt(EnumConstants.IsAvailability.isFalseCode
							.getValue()));
			sysEnumerate2.setEnumValue(EnumConstants.IsAvailability.isFalse
					.getValue());
			sysEnumList.add(sysEnumerate1);
			sysEnumList.add(sysEnumerate2);

		}

		return sysEnumList;
	}
	
	
	/**
	 * 新增文字资源
	 * @param baseAreaVO
	 * @return
	 */
	@RequestMapping("/insertTextResource")
	@ResponseBody
	public Object insertTextResource(TextResource textResource) {
		Map<String,String> map = new HashMap<String,String>();
		String msg = "";
		//设置版本号
		textResource.setVersion(1L);
		try {
			Long id = textResourcesService.insertTextResource(textResource);
			
			
				msg = "新增成功";
				// 插入系统日志
				SysLog sysLog = new SysLog();
				sysLog.setOptModule(EnumConstants.OptionModule.TEXTRESOURCE_MANAGER.getValue());
				sysLog.setOptType(EnumConstants.OptionType.TEXTRESOURCE_ADD.getValue());
				sysLog.setRemark("新增成功！"+"   ID:"+id);
				sysLogService.insert(sysLog);
		} catch(Exception e) {
			msg ="新增失败";
			e.printStackTrace();
		}
		map.put("msg", msg);
		return map;
	}
	
	/**
	 * 修改文字资源
	 * @param baseAreaVO
	 * @return
	 */
	@RequestMapping("/updateTextResource")
	@ResponseBody
	public Object updateTextResource(TextResource textResource) {
		Map<String,String> map = new HashMap<String,String>();
		String msg = "";
		try {
			textResourcesService.updateTextResource(textResource);
				msg = "修改成功";
				// 插入系统日志
				SysLog sysLog = new SysLog();
				sysLog.setOptModule(EnumConstants.OptionModule.TEXTRESOURCE_MANAGER.getValue());
				sysLog.setOptType(EnumConstants.OptionType.TEXTRESOURCE_EDIT.getValue());
				sysLog.setRemark("修改成功！"+"   ID："+textResource.getId());
				sysLogService.insert(sysLog);
	
		} catch(Exception e) {
			e.printStackTrace();
			msg = "修改失败";
		}
		map.put("msg", msg);
		return map;
	}
	
	
	/**
	 * 判断类型与唯一标识符联合唯一
	 * @param baseAreaVO
	 * @return
	 */
	@RequestMapping("/findTexResourceByCondition")
	@ResponseBody
	public Object findTexResourceByCondition(TextResourceVO textResourceVO) {
		Map<String,Boolean> map = new HashMap<String,Boolean>();
		Boolean flag = false;
		//设置版本号
		try {
			List<TextResource> list = textResourcesService.findTextResourceWithCondition(textResourceVO);
			if(list !=null && list.size()>0){
				flag = false;
			}else{
				flag = true;
			}
	
		} catch(Exception e) {
			e.printStackTrace();
			flag = null;
			
		}
		map.put("msg", flag);
		return map;
	}
	
	/**
	 * 根据ID获取数据
	 * @param baseAreaVO
	 * @return
	 */
	@RequestMapping("/findTextResourcesById")
	@ResponseBody
	public Object findTextResourcesById(Long id) {
		TextResource resource = null;
		//设置版本号
		try {
			resource = textResourcesService.findTextResourcesById(id);
	
		} catch(Exception e) {
			e.printStackTrace();
			
		}
		return resource;
	}
	
	
}
