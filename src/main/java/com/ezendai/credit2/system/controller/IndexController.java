package com.ezendai.credit2.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.framework.util.DateUtil;
import com.ezendai.credit2.system.CodeVersionProperties;

@Controller
public class IndexController {
	@Autowired
	private CodeVersionProperties codeVersionProperties;

	/**
	 * 索引
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model) {
		model.addAttribute("today", DateUtil.getDate(DateUtil.getTodayHHmmss(), "yyyy年MM月dd日 E"));
		if (ApplicationContext.getUser() != null) {
			model.addAttribute("username", ApplicationContext.getUser().getName());
		}
		String tagVersion = codeVersionProperties.getTagVersion();
		model.addAttribute("tagVersion", tagVersion);
		return "index";
	}
}
