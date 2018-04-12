package com.ezendai.credit2.system.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.framework.constant.Constants;
import com.ezendai.credit2.framework.exception.BusinessException;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.system.model.SysLog;
import com.ezendai.credit2.system.service.SysLogService;
import com.ezendai.credit2.system.service.SysUserService;

@Controller
@RequestMapping("/system")
public class SystemController {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysLogService sysLogService;

	@RequestMapping("/modifyPwdInfo")
	public String modifyPwdInfo(Model model) {
		return "system/modifyPwdInfo";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/modifyPwd")
	@ResponseBody
	public Map modifyPwd(HttpServletRequest request, String original, String reset) {
		Map result = new HashMap();
		original = original + Constants.PWD_SUFFIX;
		reset = reset + Constants.PWD_SUFFIX;
		int resetPassword = sysUserService.resetPassword(ApplicationContext.getUser().getId(), original, reset);
		if (resetPassword == 1) {
			result.put("success", true);
			SysLog sysLog = new SysLog();
			sysLog.setOptModule(EnumConstants.OptionModule.UPDATE_PASSWORD.getValue());//操作模块为系统管理
			sysLog.setOptType(EnumConstants.OptionType.UPDATE_PASSWORD.getValue());//操作类型为更新密码 
			sysLogService.insert(sysLog);
		} else {
			throw new BusinessException("密码更新失败。");
		}
		return result;
	}
}
