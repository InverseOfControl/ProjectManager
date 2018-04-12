package com.ezendai.credit2.system.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezendai.credit2.system.model.SysGroupUser;
import com.ezendai.credit2.system.model.SysUser;
import com.ezendai.credit2.system.service.SysGroupUserService;
import com.ezendai.credit2.system.service.SysUserService;
import com.ezendai.credit2.system.vo.SysGroupUserVO;

/**
 * 
 * @author Ivan
 *
 */
@Controller
@RequestMapping("/sysGroupUser")
public class sysGroupUserController {
	
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysGroupUserService sysGroupUserService;
	
	/**
	 * 变更员工与产品组绑定关系
	 * @author Ivan
	 * @param sysUserVO 前台参数对象
	 * @return
	 */
	@RequestMapping("/modifyGroupUser")
	@ResponseBody
	@SuppressWarnings("rawtypes")
	public Map modifyGroupUser(@RequestParam(value="userId")Long userId,@RequestParam(value="groups")String groups) {
		Map map = new HashMap();
		List<SysGroupUser> groupsUser = null;
		boolean isSuccess = true;
		String msg = "";
		try {
			SysGroupUserVO sysGroupUserVO = new SysGroupUserVO();
			sysGroupUserVO.setUserId(userId);
			SysUser sysUser = sysUserService.get(userId);
			if (sysUser != null) {
				//查询员工已绑定的权限组
				groupsUser = sysUserService.queryMyGroupList(userId);
				//已绑定的组
				List<Long> groupList1 = new ArrayList<Long>();
				for (int i=0;i<groupsUser.size();i++) {
					groupList1.add(groupsUser.get(i).getGroupId());
				}
				List<Long> groupList11 = (List<Long>) ((ArrayList)groupList1).clone();
				
				//本次期望绑定的组
				List<Long> groupList2 = new ArrayList<Long>();
				if (StringUtils.isNotBlank(groups)) {
					for (int i=0;i<groups.split(",").length;i++) {
						groupList2.add(Long.parseLong(groups.split(",")[i]));
					}
				}
				
				//返回已解绑的权限组
				groupList1.removeAll(groupList2);
				sysGroupUserVO.setRemoveGroupIdList(groupList1);
				//返回本次需要绑定的权限组
				groupList2.removeAll(groupList11);
				sysGroupUserVO.setAddGroupIdList(groupList2);
				
				sysGroupUserService.modifyGroupUserMap(sysGroupUserVO);
				msg = "操作成功";
			} else {
				isSuccess = false;
				msg = "客户不存在";
			}
		} catch(Exception ex) {
			isSuccess = false;
			msg = ex.getMessage();
			ex.printStackTrace();
		}
		map.put("isSuccess", isSuccess);
		map.put("msg", msg);
		return map;
	}
}