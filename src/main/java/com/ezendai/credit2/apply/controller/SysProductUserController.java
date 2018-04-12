package com.ezendai.credit2.apply.controller;

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

import com.ezendai.credit2.apply.service.SysProductUserService;
import com.ezendai.credit2.apply.vo.SysProductUserVO;
import com.ezendai.credit2.system.model.SysUser;
import com.ezendai.credit2.system.service.SysUserService;

/**
 * 
 * @author Ivan
 *
 */
@Controller
@RequestMapping("/sysProductUser")
public class SysProductUserController {
	
	/**
	 * 利用Spring自动注入
	 */
	@Autowired
	private SysProductUserService sysProductUserService;
	
	@Autowired
	private SysUserService sysUserService;
	
	
	/**
	 * 变更员工与产品组绑定关系
	 * @author Ivan
	 * @return
	 */
	@RequestMapping("/modifyProductUser")
	@ResponseBody
	@SuppressWarnings("rawtypes")
	public Map modifyProductUser(@RequestParam(value="userId")Long userId,@RequestParam(value="products")String products) {
		Map map = new HashMap();
		
		boolean isSuccess = true;
		String msg = "";
		try {
			
			SysProductUserVO sysProductUserVO = new SysProductUserVO();
			sysProductUserVO.setUserId(userId);
			SysUser sysUser = sysUserService.get(userId);
			if (sysUser != null) {
				//查询员工已绑定的产品组
				List<Long> productList1 = sysProductUserService.getProductIdByUserId(userId);
				List<Long> productList11 = (List<Long>) ((ArrayList)productList1).clone();
				
				//本次期望绑定的组
				List<Long> productList2 = new ArrayList<Long>();
				if (StringUtils.isNotBlank(products)) {
					for (int i=0;i<products.split(",").length;i++) {
						productList2.add(Long.parseLong(products.split(",")[i]));
					}
				}
				
				//返回已解绑的权限组
				productList1.removeAll(productList2);
				sysProductUserVO.setRemoveProductIdList(productList1);
				//返回本次需要绑定的权限组
				productList2.removeAll(productList11);
				sysProductUserVO.setAddProductIdList(productList2);
				
				sysProductUserService.modifyProductUserMap(sysProductUserVO);
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