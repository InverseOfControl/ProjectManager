package com.ezendai.credit2.after.controller;

import java.util.ArrayList;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezendai.credit2.after.service.SMSConfigrationService;
import com.ezendai.credit2.after.vo.SMSConfigrationVO;
import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.framework.model.UserSession;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.system.controller.BaseController;
import com.ezendai.credit2.system.model.SysUser;
import com.ezendai.credit2.system.service.SysUserService;

@Controller
@RequestMapping("/after/smsConfiguration")
public class SMSConfigurationController extends BaseController {
	@Autowired
	private SMSConfigrationService configrationService;
	private static final String CTIY_SERVICE_PHONE_PAGE = "after/sms/smsConfigration";
	@Autowired
	private SysUserService sysUserService;

	/**
	 * 城市客服服务电话初始化页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/smsConfigurationPage")
	public String initCityServicePhone(HttpServletRequest request) {
		setDataDictionaryArr(new String[] { EnumConstants.PRODUCT_ID, EnumConstants.LOAN_STATUS,
				EnumConstants.REPAYMENT_PLAN_STATE });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		return CTIY_SERVICE_PHONE_PAGE;
	}

	@RequestMapping(value = "/getCityList")
	@ResponseBody
	public List<SMSConfigrationVO> getCityList(HttpServletRequest request) {
		SMSConfigrationVO configrationVO = new SMSConfigrationVO();
		configrationVO.setCityName("全部");
		List<SMSConfigrationVO> getCitys = configrationService.getCityList();
		getCitys.add(configrationVO);
		Collections.reverse(getCitys);
		return getCitys;
	}

	/**
	 * 获取城市列表信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getSmsConfigrationList")
	@ResponseBody
	public Map<String, Object> getSmsConfigrationList(SMSConfigrationVO configrationVO, int rows, int page) {
		Map<String, Object> resultMap = null;	
			if (configrationVO == null) {
				resultMap = new HashMap<String, Object>();
				resultMap.put("total", 0);
				resultMap.put("rows", new ArrayList<Integer>());
				return resultMap;
			}
			if ("全部".equals(configrationVO.getCityName())) {
				configrationVO.setCityId(null);
			}
			resultMap = new HashMap<String, Object>();
			Pager pager = new Pager();
			pager.setPage(page);
			pager.setRows(rows);
			pager.setSidx("ID");
			pager.setSort("ASC");
			configrationVO.setPager(pager);
			pager = configrationService.getSmsConfigrationList(configrationVO);
			resultMap.put("total", pager.getTotalCount());
			resultMap.put("rows", pager.getResultList());	
		return resultMap;
	}

	/**
	 * 批量修改城市服务电话
	 * 
	 * @param request
	 */
	@RequestMapping(value = "/plUpdCityPhone")
	@ResponseBody
	public Map<String, Object> plUpdCityPhone(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String cityIds = request.getParameter("ids");
			String servicePhone = request.getParameter("servicePhone");
			if ("".equals(cityIds) || "".equals(servicePhone)) {
				resultMap.put("flag", 0);
				return resultMap;
			}
			Map<String, Object> plUpdMap = new HashMap<String, Object>();
			List<String> cityIdList=strZhuanList(cityIds);
			plUpdMap.put("cityIdList", cityIdList);
			plUpdMap.put("servicePhone", servicePhone);
			
			int result = configrationService.addCityPhone(cityIdList,servicePhone);
			if (result > 0) {
				resultMap.put("flag", 1);
				resultMap.put("susCount", result);
			}
		} catch (Exception e) {
			resultMap.put("flag", -1);
			return resultMap;
		}
		return resultMap;
	}
	
	public List<String> strZhuanList(String str){
		List<String> strList=new ArrayList<String>();;
		if(str.indexOf(",")!=-1){
			String arr[]=str.split(",");
			for (String value : arr) {
				strList.add(value);
			}
		}else{
			strList.add(str);
		}
		return strList;
	}
}
