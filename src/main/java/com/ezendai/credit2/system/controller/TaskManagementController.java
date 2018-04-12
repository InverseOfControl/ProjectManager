package com.ezendai.credit2.system.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezendai.credit2.framework.constant.Constants;
import com.ezendai.credit2.framework.listener.CookieManager;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.system.controller.BaseController;
import com.ezendai.credit2.system.model.SysLog;
import com.ezendai.credit2.system.model.SysUser;
import com.ezendai.credit2.system.model.TaskManagement;
import com.ezendai.credit2.system.service.SysJobLogService;
import com.ezendai.credit2.system.service.SysLogService;
import com.ezendai.credit2.system.service.SysUserService;
import com.ezendai.credit2.system.service.TaskManagementService;
import com.ezendai.credit2.system.vo.SysJobLogVO;
import com.ezendai.credit2.system.vo.TaskManagementVO;
import com.ibm.icu.text.SimpleDateFormat;

@Controller
@RequestMapping("/taskManagement")
public class TaskManagementController extends BaseController {

	/**
	 * 利用Spring自动注入
	 */
	@Autowired
	private TaskManagementService taskManagementService;
	@Autowired
	private SysJobLogService sysJobLogService;
	@Autowired
	private SysLogService sysLogService;
	@Autowired
	private SysUserService sysUserService;

	@RequestMapping("/loading")
	public String into(HttpServletRequest request) {
		setDataDictionaryArr(
				new String[] { EnumConstants.TRADE_TYPE, EnumConstants.TRADE_KIND, EnumConstants.ACCOUNT_TITLE });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		return "system/taskManagement";
	}

	// 查询
	@RequestMapping("/pageerTask")
	@ResponseBody
	public Map<String, Object> blackList(TaskManagementVO taskManagementVO, int rows, int page) {
		Pager p = new Pager();
		p.setRows(rows);
		p.setPage(page);
		taskManagementVO.setPager(p);
		p.setSidx("ID");
		p.setSort("ASC");
		Pager flowByVOList = taskManagementService.getBlackList(taskManagementVO);
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("total", flowByVOList.getTotalCount());
		result.put("rows", flowByVOList.getResultList());
		return result;
	}

	// 按名称去sysJobLog查询
	@RequestMapping("/sysJobLogList/{taskName}")
	@ResponseBody
	public Map<String, Object> sysJobLogList(@PathVariable("taskName") String taskName, int rows, int page,
			HttpServletRequest request) {
		SysJobLogVO sysJobLogVO = new SysJobLogVO();
		// 名字
		taskName = encodeStr(taskName);
		sysJobLogVO.setName(taskName);
		// 处理结果状态
		String resultState = request.getParameter("resultState");
		if (resultState != null && resultState != "") {
			// 有效状态
			String effectiveState = "";
			// 无效状态
			String invalidState = "";
			String[] str = resultState.split(",");
			int j = 0;
			int x = 0;
			for (int i = 0; i < str.length; i++) {
				// 是不是全部
				if (str[i].equals("0")) {
					invalidState += str[i];
					x++;
				} else {
					// 是不是全部
					if (x == 0) {
						// 是不是最后最后一次
						if (j == str.length - 1) {
							effectiveState = effectiveState + str[i];
						} else {
							effectiveState = effectiveState + str[i] + ",";
						}
					}
				}
				j++;
			}
			sysJobLogVO.setEffectiveState(effectiveState);
			sysJobLogVO.setInvalidState(invalidState);
		} else {
			sysJobLogVO.setEffectiveState("3");
		}
		Pager p = new Pager();
		p.setRows(rows);
		p.setPage(page);
		sysJobLogVO.setPager(p);
		p.setSidx("ID");
		p.setSort("ASC");
		p = sysJobLogService.findSysJobLogWithPG(sysJobLogVO);
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("total", p.getTotalCount());
		result.put("rows", p.getResultList());
		return result;
	}

	// 加载定时任务
	@RequestMapping("/loadTaskManagement")
	@ResponseBody
	@SuppressWarnings(value = { "rawtypes", "unchecked" })
	public Map loadSysUserInfo(long id) {
		Map map = new HashMap();
		String msg = "";
		boolean isSuccess = true;
		TaskManagement taskManagement = taskManagementService.get(id);
		if (taskManagement != null) {
			map.put("taskManagement", taskManagement);
		} else {
			isSuccess = false;
			msg = "记录不存在";
		}
		map.put("isSuccess", isSuccess);
		map.put("msg", msg);
		return map;
	}

	// 保存定时任务
	@RequestMapping("/saveTaskManagement")
	@ResponseBody
	public Map<Object, Object> saveSysUserInfo(@RequestParam(value = "id") Integer id,
			@RequestParam(value = "taskName") String taskName, @RequestParam(value = "className") String className,
			@RequestParam(value = "executionDate") String executionDate,
			@RequestParam(value = "executionTime") String executionTime,
			@RequestParam(value = "taskDetailed") String taskDetailed, HttpServletRequest request,
			HttpServletResponse response) {
		Map<Object, Object> map = new HashMap<Object, Object>();

		String msg = "";
		boolean isSuccess = true;

		// 时间格式
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

		// 获取登录的用户名
		SysUser sysUser = getSysUser(request, response);
		try {
			// 增加
			if (id == null) {
				TaskManagement taskManagement = new TaskManagement();
				taskManagement.setTaskName(taskName);
				taskManagement.setClassName(className);
				taskManagement.setExecutionDate(executionDate);

				Date date = sdf.parse(executionTime);

				taskManagement.setExecutionTime(date);
				taskManagement.setTaskDetailed(taskDetailed);
				taskManagement.setIsDeleted((long) 0);

				taskManagement.setCreatorId(sysUser.getId());
				taskManagement.setCreator(sysUser.getName());
				taskManagement.setCreatedTime(new Date());

				taskManagementService.insertTaskManagement(taskManagement);

				msg = "新增成功";

				// 插入系统日志
				SysLog sysLog = new SysLog();
				sysLog.setOptModule(EnumConstants.OptionModule.TASK_MANAGEMENT.getValue());
				sysLog.setOptType(EnumConstants.OptionType.TASK_MANAGEMENT_INSERT.getValue());
				sysLog.setRemark(
						"任务名称：" + taskManagement.getTaskName() + "   " + "类名：" + taskManagement.getClassName());
				sysLogService.insert(sysLog);
			} else {
				// 修改
				TaskManagementVO taskManagementVO = new TaskManagementVO();
				taskManagementVO.setId((long) id);
				taskManagementVO.setTaskName(taskName);
				taskManagementVO.setClassName(className);
				taskManagementVO.setExecutionDate(executionDate);

				Date date = sdf.parse(executionTime);

				taskManagementVO.setExecutionTime(date);
				taskManagementVO.setTaskDetailed(taskDetailed);

				taskManagementVO.setModifierId(sysUser.getId());
				taskManagementVO.setModifier(sysUser.getName());
				taskManagementVO.setModifiedTime(new Date());

				taskManagementService.updateTaskManagement(taskManagementVO);

				msg = "修改成功";

				// 插入系统日志
				SysLog sysLog = new SysLog();
				sysLog.setOptModule(EnumConstants.OptionModule.TASK_MANAGEMENT.getValue());
				sysLog.setOptType(EnumConstants.OptionType.TASK_MANAGEMENT_CHANGES.getValue());
				sysLog.setRemark(
						"任务名称：" + taskManagementVO.getTaskName() + "   " + "类名：" + taskManagementVO.getClassName());
				sysLogService.insert(sysLog);
			}
		} catch (Exception ex) {
			isSuccess = false;
			msg = ex.getMessage();
		}
		map.put("isSuccess", isSuccess);
		map.put("msg", msg);
		return map;
	}

	// 删除定时任务
	@RequestMapping("/deletedTaskManagement")
	@ResponseBody
	public Map deletedUser(@RequestParam(value = "id") long id, HttpServletRequest request,
			HttpServletResponse response) {
		Map map = new HashMap();
		boolean isSuccess = true;
		String msg = "";

		// 获取登录的用户名
		SysUser sysUser = getSysUser(request, response);
		try {
			TaskManagement taskManagement = taskManagementService.get(id);
			if (taskManagement != null) {
				TaskManagementVO taskManagementVO = new TaskManagementVO();
				taskManagementVO.setId(id);
				taskManagementVO.setIsDeleted((long) 1);

				taskManagementVO.setModifierId(sysUser.getId());
				taskManagementVO.setModifier(sysUser.getName());
				taskManagementVO.setModifiedTime(new Date());
				// 将状态改为以删除
				taskManagementService.updateTaskManagement(taskManagementVO);
				msg = "删除成功";
				// 插入系统日志
				SysLog sysLog = new SysLog();
				sysLog.setOptModule(EnumConstants.OptionModule.TASK_MANAGEMENT.getValue());
				sysLog.setOptType(EnumConstants.OptionType.DELETE_TASK_MANAGEMENT.getValue());
				sysLog.setRemark(
						"任务名称：" + taskManagement.getTaskName() + "   " + "类名：" + taskManagement.getClassName());
				sysLogService.insert(sysLog);
			} else {
				isSuccess = false;
				msg = "定时任务不存在";
			}
		} catch (Exception ex) {
			isSuccess = false;
			msg = ex.getMessage();
		}
		map.put("isSuccess", isSuccess);
		map.put("msg", msg);
		return map;
	}

	// 获取登录信息
	public SysUser getSysUser(HttpServletRequest request, HttpServletResponse response) {
		CookieManager cookieManager = new CookieManager(request, response);
		String name = cookieManager.getCookieValue(Constants.CREDIT2_LOGIN_COOKIE_NAME);
		SysUser sysUser = sysUserService.getSysUserByLoginName(name);
		return sysUser;
	}

	// 转格式
	public static String encodeStr(String str) {
		try {
			return new String(str.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
}
