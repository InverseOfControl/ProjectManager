package com.ezendai.credit2.system.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.framework.cache.CacheService;
import com.ezendai.credit2.framework.constant.Constants;
import com.ezendai.credit2.framework.listener.CookieManager;
import com.ezendai.credit2.framework.util.CommonUtil;
import com.ezendai.credit2.framework.util.DateUtil;
import com.ezendai.credit2.framework.util.MD5Util;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.system.model.SysLog;
import com.ezendai.credit2.system.model.SysUser;
import com.ezendai.credit2.system.service.LoginService;
import com.ezendai.credit2.system.service.SysLogService;
import com.ezendai.credit2.system.service.SysUserService;
import com.ezendai.credit2.system.vo.SysUserVO;

@Controller
@RequestMapping("/system")
public class LoginController {

	private static  final  Logger   logger =Logger.getLogger(LoginController.class);
	
	@Autowired
	private LoginService loginService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private CacheService cacheService;
	@Autowired
	private SysLogService sysLogService;

	/**
	 * 跳转登录
	 * 
	 * @return
	 */
	@RequestMapping("/sign")
	public String sign(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Model model) {
		CookieManager cookieManager = new CookieManager(httpServletRequest, httpServletResponse);
		String loginName = cookieManager.getCookieValue(Constants.CREDIT2_LOGIN_COOKIE_NAME);
		model.addAttribute("loginName", loginName);
		model.addAttribute("versionYear", DateUtil.getYear());
		return "system/login";
	}

	/**
	 * 跳转登录
	 * 
	 * @return
	 */
	@RequestMapping("/login")
	@ResponseBody
	public Map<String, String> login(HttpServletRequest request, HttpServletResponse response, String username, String password) {

		Map<String, String> result = new HashMap<String, String>();
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			result.put("success", "0");
			result.put("message", "用户名或者密码为空。");
			logger.info("用户名或者密码为空。");
			return result;
		}
		// 验证对应的账户是否存在
		SysUser sysUser = loginService.getSysUserByLoginName(username);
		if (sysUser == null) {
			result.put("success", "0");
			result.put("message", "用户名不存在。");
			logger.info("用户名不存在。");
			return result;
		}
		Boolean isOk = false;
		// 验证密码
		password = MD5Util.md5Hex(password + Constants.PWD_SUFFIX);
		if (sysUser.getSignPassword().equals(password)) {
			isOk = true;
			logger.info("密码校验正确。");
		} else {
			isOk = false;
			logger.info("密码校验错误。");
		}
		// 验证通过时
		if (isOk) {
			CookieManager cookieManager = new CookieManager(request, response);
			String memcachedKey = this.sysUserService.session2Cache(sysUser.getId(), CommonUtil.getIp(request));
			// 设置会话过期
			cookieManager.setCookie(Constants.CREDIT2_USERINFO_COOKIE_NAME, memcachedKey, Constants.DOMAIN_NAME, -1);
			// 设置登录名3天有效
			cookieManager.setCookie(Constants.CREDIT2_LOGIN_COOKIE_NAME, username, Constants.DOMAIN_NAME, 3 * 60 * 60 * 24);
			result.put("success", "1");
			// 登录日志
			SysLog sysLog = new SysLog();
			sysLog.setOptModule(EnumConstants.OptionModule.SYSTEM_MANAGE.getValue());//操作模块为系统管理
			sysLog.setOptType(EnumConstants.OptionType.LOGIN_IN.getValue());//操作类型为登录 
			sysLogService.insert(sysLog);
			// 登录错误次数清0,记录登录时间
			SysUserVO sysUserTemp = new SysUserVO();
			sysUserTemp.setId(sysUser.getId());
			sysUserTemp.setLastLoginTime(new Date());
			sysUserTemp.setLoginRetry(0);
			sysUserTemp.setLastLoginIp(CommonUtil.getIp(request));
			loginService.updateSysUser(sysUserTemp);
			logger.info("登陆日志记录:成功。");

		} else {
			result.put("success", "0");
			result.put("message", "用户名或者密码错误。");
			// 记录登录错误次数
			SysUserVO sysUserTemp = new SysUserVO();
			sysUserTemp.setId(sysUser.getId());
			sysUserTemp.setLoginRetry(sysUser.getLoginRetry() + 1);
			sysUserTemp.setLastLoginIp(CommonUtil.getIp(request));
			loginService.updateSysUser(sysUserTemp);
			logger.info("登陆日志记录:用户名或者密码错误。");
		}

		return result;
	}

	@RequestMapping("/loginout")
	public String logout(HttpServletRequest request, HttpServletResponse response, Model model) {

		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.SYSTEM_MANAGE.getValue());//操作模块为系统管理
		sysLog.setOptType(EnumConstants.OptionType.LOGIN_OUT.getValue());//操作类型为退出
		sysLogService.insert(sysLog);
		if (ApplicationContext.getUser() != null) {
			CookieManager cookieManager = new CookieManager(request, response);
			String cookieValue = cookieManager.getCookieValue(Constants.CREDIT2_USERINFO_COOKIE_NAME);
			// 删除memcache缓存
			cacheService.removeData(cookieValue);
			// 删除系统的本地session
			ApplicationContext.clear();
			// 删除用户cookie
			cookieManager.setCookie(Constants.CREDIT2_USERINFO_COOKIE_NAME, null, Constants.DOMAIN_NAME, 0);
		}
		response.setHeader("progma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		CookieManager cookieManager = new CookieManager(request, response);
		String loginName = cookieManager.getCookieValue(Constants.CREDIT2_LOGIN_COOKIE_NAME);
		model.addAttribute("loginName", loginName);
		model.addAttribute("versionYear", DateUtil.getYear());
		return "system/login";
	}
}
