package com.ezendai.credit2.sign.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezendai.credit2.system.controller.BaseController;

@Controller
@RequestMapping("/demo")
public class DemoController extends BaseController {
	@RequestMapping("/list")
	public String carLoanDetail() {
		return "apply/demo";
	}
	
	/**
	 * 为了不在原有的方法中添加代码，我们把对接捞财宝的代码单独编码
	 */
	@ResponseBody
	@RequestMapping("/execute")
	public String execute(String id) {
		return "操作成功";
	}
}
