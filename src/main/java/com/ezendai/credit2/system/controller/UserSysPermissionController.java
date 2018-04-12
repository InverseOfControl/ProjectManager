package com.ezendai.credit2.system.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezendai.credit2.apply.vo.PageAttributeVO;
import com.ezendai.credit2.apply.vo.PageMenuVO;
import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.master.constant.WebConstants;
import com.ezendai.credit2.system.model.SysPermission;
import com.ezendai.credit2.system.service.SysUserService;

@Controller
public class UserSysPermissionController {

	@Autowired
	private SysUserService sysUserService;

	@RequestMapping("/json/west.json")
	@ResponseBody
	public Object west(HttpServletRequest request) {
		List<PageMenuVO> list = new ArrayList<PageMenuVO>();		
		
		List<SysPermission> sysPermissionList = sysUserService.findPermissionListBySysUserId(ApplicationContext.getUser().getId());
		for (SysPermission e : sysPermissionList) {
			PageMenuVO vo = new PageMenuVO();
			if (NumberUtils.LONG_ZERO.compareTo(e.getParentId()) == 0) {
				vo.setText(e.getName());
				PageAttributeVO attr = new PageAttributeVO();
				attr.setSrc(e.getUrl());
				vo.setAttributes(attr);
				list.add(vo);
			}
			for (SysPermission ee : sysPermissionList) {
				if (e.getId().compareTo(ee.getParentId()) == 0) {
					PageMenuVO sub = new PageMenuVO();
					sub.setText(ee.getName());
					PageAttributeVO subAttr = new PageAttributeVO();
					// if contextPath is empty 
					if (StringUtils.isEmpty(WebConstants.contextPath)) {
						subAttr.setSrc(ee.getUrl());
					} else {
						subAttr.setSrc(WebConstants.contextPath + "/" + ee.getUrl());
					}
					sub.setAttributes(subAttr);
					vo.getChildren().add(sub);
				}
			}
		}
		return list;
	}

	@RequestMapping("/home.html")
	public String home() {
		return "home";
	}

}
