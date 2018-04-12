package com.ezendai.credit2.apply.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezendai.credit2.apply.assembler.ChannelPlanAssembler;
import com.ezendai.credit2.apply.model.ChannelPlan;
import com.ezendai.credit2.apply.model.ChannelPlanCheck;
import com.ezendai.credit2.apply.service.ChannelPlanCheckService;
import com.ezendai.credit2.apply.service.ChannelPlanService;
import com.ezendai.credit2.apply.vo.ChannelPlanCheckVO;
import com.ezendai.credit2.apply.vo.ChannelPlanVO;
import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.framework.model.UserSession;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.system.controller.BaseController;
import com.ezendai.credit2.system.model.SysGroupUser;
import com.ezendai.credit2.system.model.SysLog;
import com.ezendai.credit2.system.service.SysGroupUserService;
import com.ezendai.credit2.system.service.SysLogService;
import com.ezendai.credit2.system.vo.SysGroupUserVO;

@Controller
@RequestMapping("/channelPlan")
public class ChannelPlanController extends BaseController {

	@Autowired
	ChannelPlanService channelPlanService;
	@Autowired
	ChannelPlanCheckService channelPlanCheckService;
	@Autowired
	private SysLogService sysLogService;
	@Autowired
	private SysGroupUserService sysGroupUserService;

	protected static final String GRID_ENUM_JSON = "gridEnumJson";

	/**
	 * <pre>
	 * 变更管理主界面
	 * </pre>
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/changeChannelPlan")
	public String changeManagerList(HttpServletRequest request) {
		setDataDictionaryArr(new String[] { EnumConstants.PRODUCT_SUB_TYPE });
		SysGroupUserVO sysGroupUserVO =new SysGroupUserVO();
		sysGroupUserVO.setUserId(ApplicationContext.getUser().getId());
		SysGroupUser sysGroupUser = sysGroupUserService.findByVO(sysGroupUserVO);
		request.setAttribute("groupId", sysGroupUser.getGroupId());

		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		return "apply/channelPlan";
	}

	/**
	 * 查询方案数据
	 * 
	 * @author liuhy
	 * @return
	 */
	@RequestMapping("/getSearchJson")
	@ResponseBody
	@SuppressWarnings(value = { "rawtypes" })
	public Map getSearchJson(ChannelPlanCheckVO channelPlanCheckVO, int rows,
			int page) {
		Pager pager = new Pager();
		pager.setRows(rows);
		pager.setPage(page);
		pager.setSidx("ID");
		pager.setSort("ASC");
		channelPlanCheckVO.setPager(pager);
		// 查询
		pager = channelPlanCheckService.checkFindWithPG(channelPlanCheckVO);
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("total", pager.getTotalCount());
		result.put("rows", pager.getResultList());
		return result;
	}

	/**
	 * 查询方案数据
	 * 
	 * @author liuhy
	 * @return
	 */
	@RequestMapping("/getSearchJsonAll")
	@ResponseBody
	public List<ChannelPlanCheck> getSearchJsonAll(
			ChannelPlanCheckVO channelPlanCheckVO) {
		// 查询
		List<ChannelPlanCheck> listChannelPlanCheck = channelPlanCheckService
				.findListByVo(channelPlanCheckVO);
		 
		return listChannelPlanCheck;
	}

	/**
	 * 新增方案信息
	 * 
	 * @author liuhy
	 * @return
	 */
	@RequestMapping("/saveChannelPlanInfo")
	@ResponseBody
	@SuppressWarnings(value = { "rawtypes", "unchecked" })
	public Map saveChannelPlanInfo(ChannelPlanVO channelPlanVO) {
		Map map = new HashMap();
		boolean isSuccess = true;
		String msg = "";
		long msgId=0;
		try {
			if (channelPlanVO != null) {
				if (ApplicationContext.getUser() != null
						&& ApplicationContext.getUser().getId() != null) {
					UserSession user = ApplicationContext.getUser();
					channelPlanVO.setPlanState("00");// 00,未锁定
					channelPlanVO.setPlanType("00");// 00，助学贷
					channelPlanVO.setOperatorId(user.getId());
					channelPlanVO.setIsDeleted(0);
					ChannelPlan channelPlan = ChannelPlanAssembler
							.transferVO2Model(channelPlanVO);
					ChannelPlanCheckVO channelPlanCheckVO = ChannelPlanAssembler
							.transferVO2CheckVO(channelPlanVO);
					if (0 == channelPlanService
							.isExistCount(channelPlanCheckVO)) {
						channelPlanCheckVO.setApproverState(1);// 1,待复核
						channelPlanService.insertChannelPlan(channelPlan,
								channelPlanCheckVO);
						msgId=channelPlanCheckVO.getId();
						msg = "新增方案成功";
					} else {
						isSuccess = false;
						msg = "该方案已存在";
					}

				}
			} else {
				isSuccess = false;
				msg = "新增方案失败";
			}
		} catch (Exception ex) {
			isSuccess = false;
			msg = "新增方案失败";
			ex.printStackTrace();
		}
		// 插入系统日志
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.SCHEME_MANAGE
				.getValue());
		sysLog.setOptType(EnumConstants.OptionType.CREATE_SCHEME.getValue());
		sysLog.setRemark(msg);
		if(isSuccess){
			sysLog.setRemark(msg+",方案ID:"+msgId);
		}
		sysLogService.insert(sysLog);
		
		map.put("isSuccess", isSuccess);
		map.put("msg", msg);
		return map;
	}

	/**
	 * 检查方案信息是否存在
	 * 
	 * @author liuhy
	 * @return
	 */
	@RequestMapping("/checkChannelPlanInfo")
	@ResponseBody
	@SuppressWarnings(value = { "rawtypes", "unchecked" })
	public Map checkChannelPlanInfo(ChannelPlanVO channelPlanVO) {
		Map map = new HashMap();
		boolean isSuccess = true;
		String msg = "";
		try {
			if (channelPlanVO != null) {
				ChannelPlanCheckVO channelPlanCheckVO = ChannelPlanAssembler
						.transferVO2CheckVO(channelPlanVO);
				ChannelPlanCheckVO channelPlanCheckVOCode=new ChannelPlanCheckVO();
				channelPlanCheckVOCode.setCode(channelPlanCheckVO.getCode());
				ChannelPlanCheckVO channelPlanCheckVOName=new ChannelPlanCheckVO();
				channelPlanCheckVOName.setName(channelPlanCheckVO.getName());
				if (0 != channelPlanService.isExistCount(channelPlanCheckVOCode)) {
					isSuccess = false;
					msg = "该方案代码已存在";
				}else if (0 != channelPlanService.isExistCount(channelPlanCheckVOName)) {
					isSuccess = false;
					msg = "该方案名称已存在";
				}
			} else {
				isSuccess = false;
				msg = "新增失败";
			}
		} catch (Exception ex) {
			isSuccess = false;
			msg = "新增失败";
			ex.printStackTrace();
		}
		map.put("isSuccess", isSuccess);
		map.put("msg", msg);
		return map;
	}

	/**
	 * 加载方案信息
	 * 
	 * @author liuhy
	 * @return
	 */
	@RequestMapping("/loadChannelPlanInfo")
	@ResponseBody
	@SuppressWarnings(value = { "rawtypes", "unchecked" })
	public Map loadChannelPlanInfo(long id) {
		Map map = new HashMap();
		String msg = "";
		boolean isSuccess = true;
		try {
			ChannelPlanCheck channelPlanCheck = channelPlanCheckService
					.getReplyById(id);
			if (channelPlanCheck != null) {
				map.put("channelPlan", channelPlanCheck);
				msg = "查询成功";
			} else {
				isSuccess = false;
				msg = "初始化失败";
			}
		} catch (Exception ex) {
			isSuccess = false;
			msg = "初始化失败";
			ex.printStackTrace();
		}
		map.put("isSuccess", isSuccess);
		map.put("msg", msg);
		return map;
	}

	/**
	 * 删除方案信息
	 * 
	 * @author liuhy
	 * @return
	 */
	@RequestMapping("/deleteChannelPlanInfo")
	@ResponseBody
	@SuppressWarnings(value = { "rawtypes", "unchecked" })
	public Map deleteChannelPlanInfo(long id) {
		Map map = new HashMap();
		String msg = "";
		boolean isSuccess = true;
		try {
			ChannelPlanCheck channelPlanCheck = channelPlanCheckService
					.getReplyById(id);
			if (channelPlanCheck != null) {
				ChannelPlanVO channelPlanVO = new ChannelPlanVO();
				channelPlanVO.setId(channelPlanCheck.getPlan_id());
				channelPlanVO.setPlanState("00");
				ChannelPlanCheckVO channelPlanCheckVO = new ChannelPlanCheckVO();
				channelPlanCheckVO.setId(channelPlanCheck.getId());
				channelPlanService.deleteChannelPlan(channelPlanVO,
						channelPlanCheckVO);
				msg = "删除方案成功";
			} else {
				isSuccess = false;
				msg = "无该方案";
			}

		} catch (Exception ex) {
			isSuccess = false;
			msg = "删除方案失败";
			ex.printStackTrace();
		}
		// 插入系统日志
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.SCHEME_MANAGE
				.getValue());
		sysLog.setOptType(EnumConstants.OptionType.DELETE_SCHEME.getValue());
		sysLog.setRemark(msg+",方案ID:"+id);
		sysLogService.insert(sysLog);
		
		map.put("isSuccess", isSuccess);
		map.put("msg", msg);
		return map;
	}

	/**
	 * 编辑方案信息
	 * 
	 * @author liuhy
	 * @return
	 */
	@RequestMapping("/editChannelPlanInfo")
	@ResponseBody
	@SuppressWarnings(value = { "rawtypes", "unchecked" })
	public Map editChannelPlanInfo(ChannelPlanCheckVO channelPlanCheckVO) {
		Map map = new HashMap();
		String msg = "";
		boolean isSuccess = true;
		try {
			if (channelPlanCheckVO != null) {
				ChannelPlanVO channelPlanVO = ChannelPlanAssembler
						.transferCheckVO2VO(channelPlanCheckVO);
				channelPlanVO.setPlanState("00");//
				if (channelPlanCheckVO.getApproverState() != null) {
					if (channelPlanCheckVO.getApproverState() == 4
							|| channelPlanCheckVO.getApproverState() == 2) {
						channelPlanCheckVO.setApproverState(2);
					}
				}
				channelPlanService.updateChannelPlan(channelPlanVO,
						channelPlanCheckVO);
				msg = "编辑方案成功";
			} else {
				isSuccess = false;
				msg = "编辑方案失败";
			}
		} catch (Exception ex) {
			isSuccess = false;
			msg = "编辑方案失败";
			ex.printStackTrace();
		}
		// 插入系统日志
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.SCHEME_MANAGE
				.getValue());
		sysLog.setOptType(EnumConstants.OptionType.EDIT_SCHEME.getValue());
		sysLog.setRemark(msg+",方案ID:"+channelPlanCheckVO.getId());
		sysLogService.insert(sysLog);
		
		map.put("isSuccess", isSuccess);
		map.put("msg", msg);
		return map;
	}

}
