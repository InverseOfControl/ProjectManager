package com.ezendai.credit2.master.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.framework.exception.BusinessException;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.model.BusinessCompany;
import com.ezendai.credit2.master.model.ProductManager;
import com.ezendai.credit2.master.service.BusinessCompanyService;
import com.ezendai.credit2.master.vo.BaseAreaVO;
import com.ezendai.credit2.master.vo.BusinessCompanyVO;
import com.ezendai.credit2.master.vo.ProductManagerVO;


/**
 * 本方账户配置
 * @author 00237489
 * @return
 */
@Controller
@RequestMapping("/businessCompany")
public class BusinessCompanyController {
	
	private static final Logger log =Logger.getLogger(BusinessCompanyController.class);

	@Autowired
	BusinessCompanyService businessCompanyService;
	
	/**
	 * 进入本方账户配置页面
	 * @author 00237489
	 * @return
	 */
	@RequestMapping("/main")
	public String main(HttpServletRequest request) {
		request.setAttribute("userType", ApplicationContext.getUser().getUserType());
		return "/master/businessCompany/businessCompany";
	}
	
	/**
	 * 查询数据
	 * @author 00237489
	 * @return
	 */
	@RequestMapping("/getSearchJson")
	@ResponseBody
	public Map getSearchJson(BusinessCompanyVO businessCompanyVO,int rows, int page) {
		Pager pager = new Pager();
		pager.setRows(rows);
		pager.setPage(page);
		pager.setSidx("ID");
		pager.setSort("ASC");
		businessCompanyVO.setPager(pager);
		
		//查询
		pager = businessCompanyService.findWithPG(businessCompanyVO);
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("total", pager.getTotalCount());
		result.put("rows", pager.getResultList());
		return result;
	}
	
	
	/**
	 * 
	 * @Description: 获取一条数据信息
	 * @param @param id
	 * @param @return   
	 * @return Map<String,Object>  
	 * @throws
	 * @author 00237489
	 * @date 2016年5月5日
	 */
	@RequestMapping("/getBusinessCompany")
	@ResponseBody
	public Map<String, Object> getBusinessCompany(@RequestParam Long id){
		log.info("|-----开始获取一条本方账号.....");
		boolean success = true;
		String msg = "";
		Map<String,Object> map = new HashMap<String,Object>();
		
		if(null == id){
			success = false;
			msg = "记录不存在,或已被删除";
			map.put("success", success);
			map.put("msg", msg);
			log.info("|-----结束获取一条本方账号.....");
			return map;
		}
		BusinessCompany spm = businessCompanyService.get(id);
		if(null == spm){
			success = false;
			msg = "记录不存在";
			log.info("没查询到数据，可能已被删除！");
		}else{
			log.info("信息查询成功！");
			success = true;
		}
		map.put("success", success);
		map.put("businessCompany", spm);
		map.put("msg", msg);
		log.info("|-----结束获取一条本方账号.....");
		return map;
	}
	
	/**
	 * 
	 * @Description: 删除一条数据信息
	 * @param @param id
	 * @param @return   
	 * @return Map<String,Object>  
	 * @throws
	 * @author 00237489
	 * @date 2016年5月5日
	 */
	@RequestMapping("/deleteBusinessCompany")
	@ResponseBody
	public Map<String, Object> deleteBusinessCompany(@RequestParam Long id){
		log.info("|-----开始删除一条本方账号.....");
		boolean success = true;
		String msg = "";
		Map<String,Object> map = new HashMap<String,Object>();
		
		if(null == id){
			success = false;
			msg = "记录不存在,或已被删除";
		}
		try{
			businessCompanyService.deleteById(id);
		 	log.info("删除成功！");
			success = true;
		}catch(Exception e){
			success = false;
			msg = e.getMessage();
			log.info(e.getMessage());
		}
		map.put("success", success);
		map.put("msg", msg);
		log.info("|-----结束删除一条本方账号.....");
		return map;
	}
	
	
	/**
	 * 
	 * @Description: 添加一条本方账户数据
	 * @param @param vo
	 * @param @return   
	 * @return Map<String,Object>  
	 * @throws
	 * @author 00237489
	 * @date 2016年5月3日
	 */
	@RequestMapping(value="/addBusinessCompany",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> addBusinessCompany(BusinessCompany vo){
		log.info("|-----开始添加一条本方账号.....");
		Map<String,Object> map = new HashMap<String,Object>();
		boolean success = true;
		map = validateAddBusinessCompanyInfo(vo);
		if(map!=null && map.get("success")!=null && map.get("success").equals(false)){
			return map;
		}
		map.clear();
		String msg = "";
		try {
			vo.setStatus(1);
			vo.setType(1);
			businessCompanyService.insert(vo); 
				msg = "新增成功";
				log.info("添加成功！");
				
		} catch(BusinessException ex) {
			success = false;
			msg = "新增失败";
			log.info(ex);
		} catch(Exception ex) {
			success = false;
			msg = ex.getMessage();
			log.info("添加失败！");
		}
		map.put("success", success);
		map.put("msg", msg);
		
		log.info("|-----结束添加一条本方账号.....");
		return map;
	}

	
	
	/**
	 * 
	 * @Description: 校验添加本方账户数据
	 * @param @param vo
	 * @param @return   
	 * @return Map<String,Object>  
	 * @throws
	 * @author 00237489
	 * @date 2016年5月3日
	 */
	private Map<String, Object> validateAddBusinessCompanyInfo(
			BusinessCompany vo) {
		// TODO Auto-generated method stub
		log.info("|------检验数据是否存在.......");
		Map<String,Object> result = new HashMap<String,Object>();
		BusinessCompanyVO bcManager = new BusinessCompanyVO();
		bcManager.setAccount(vo.getName());
		bcManager.setAccount(vo.getAccount());
		//用于比对数据 mark update/add
		BusinessCompany businessCompany=businessCompanyService.get(bcManager);
		if(businessCompany!=null){
			log.info("系统已存在该本方账号!");
			result.put("success", false);
			result.put("msg", "系统已存在该本方账号!");
			return result;
		}
		log.info("|------校验数据结束......");
		return result;
	}
	
	/**
	 * 
	 * @Description: 修改一条本方账户数据
	 * @param @param vo
	 * @param @return   
	 * @return Map<String,Object>  
	 * @throws
	 * @author 00237489
	 * @date 2016年5月3日
	 */
	@RequestMapping(value="/updateBusinessCompany",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> updateBusinessCompany(BusinessCompanyVO vo){
		log.info("|-----开始修改一条本方账号.....");
		Map<String,Object> map = new HashMap<String,Object>();
		boolean success = true;
		String msg = "";
		if(vo==null){
			success = false;
			msg = "修改失败";
			map.put("success", success);
			map.put("msg", msg);
			return map;
		}
		try {
			int i=businessCompanyService.update(vo); 
			if(i==1){
				msg = "修改成功";
				log.info("修改成功！");
			}else{
				success = false;
				msg = "修改失败";
			}
		} catch(BusinessException ex) {
			success = false;
			msg = "修改失败";
			log.info(ex);
		} catch(Exception ex) {
			success = false;
			msg = ex.getMessage();
			log.info("修改失败！");
		}
		map.put("success", success);
		map.put("msg", msg);
		
		log.info("|-----结束修改一条本方账号.....");
		return map;
	}
}
