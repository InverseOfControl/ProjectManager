package com.ezendai.credit2.system.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezendai.credit2.system.service.SysUserService;

@Controller
@Scope("singleton")
@RequestMapping("/healthCheck")
public class HealthCheckController {

	@Autowired
	private SysUserService sysUserService;

	@RequestMapping(value = "/verify",produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String testConnection() throws IOException {
		try {
			sysUserService.testConnection();
			return "OK";
		} catch (Exception e) {
			return "FAIL";
		}
	}
}
