package com.ezendai.credit2.audit.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ezendai.credit2.sign.lcb.service.ContractGenerateService;
import com.ezendai.credit2.sign.util.DwrUtil;

@Controller
public class DwrController {
	private static final Logger logger = LoggerFactory.getLogger(DwrController.class);
	
	/**
	 * description:电子签章签名成功之后的回调地址
	 * autor:ym10159
	 * date:2018年1月29日 上午11:28:03
	 */
	@RequestMapping(value="/signName/eleSignCallback",method=RequestMethod.GET)
	public void eleSignCallback(HttpServletRequest request, HttpServletResponse response) {
		String loanId = ObjectUtils.toString(request.getParameter("loanId"));
		logger.info("电子签章回调车贷开始，loanId="+loanId);
		
		DwrUtil t =  new DwrUtil();
		List<String> args = new ArrayList<String>();
		args.add(loanId);
		t.invokeJavascriptFunction("jsEleSignCallback",args);
	}
	
	/**
	 * description:捞财宝电子签章成功之后的回调地址
	 * autor:ym10159
	 * date:2018年1月29日 上午11:28:43
	 */
	@RequestMapping(value="/signName/lcbEleSignCallback",method=RequestMethod.GET)
	public void lcbEleSignCallback(HttpServletRequest request, HttpServletResponse response) {
		String loanId = ObjectUtils.toString(request.getParameter("loanId"));
		logger.info("捞财宝电子签章回调车贷开始，loanId="+loanId);
		
		DwrUtil t =  new DwrUtil();
		List<String> args = new ArrayList<String>();
		args.add(loanId);
		t.invokeJavascriptFunction("jsLcbEleSignCallback",args);
	}
}
