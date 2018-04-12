package com.ezendai.credit2.master.controller;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ezendai.credit2.framework.util.BeanUtil;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.master.model.OfferManager;
import com.ezendai.credit2.master.service.OfferManagerServcie;
import com.ezendai.credit2.master.vo.OfferManagerVO;
import com.ezendai.credit2.system.controller.BaseController;
import com.ezendai.credit2.system.model.SysLog;
import com.ezendai.credit2.system.service.SysLogService;

@RequestMapping("/offerManager")
@Controller
public class OfferManagerController extends BaseController{

	private static final Logger log =Logger.getLogger(OfferManagerController.class);
	
	private static final String SUCCESS = "success";
	
	@Autowired
	private SysLogService sysLogService;
	
	@Autowired
	private OfferManagerServcie offerService;
	
	
	/**
	 * 
	 * @Description: 初始化加载list页面
	 * @param @return   
	 * @return String  
	 * @author 徐安龙
	 * @date 2016年8月1日
	 */
	@RequestMapping("/offerManagerList")
	public String offerManagerInit(){
		return "/master/offerManager/offerManagerList";
	}
	
	/**
	 * 
	 * @Description: 查询数据列表
	 * @param @param request
	 * @param @param modelMap
	 * @param @param rows
	 * @param @param page
	 * @param @return   
	 * @return Map<String,Object>  
	 * @throws
	 * @author 徐安龙
	 * @date 2016年8月2日
	 */
	@RequestMapping("/offerManagerListData")
	@ResponseBody
	public Map<String,Object> showOfferManagerList(HttpServletRequest request,int rows, int page,Integer offerDay){
		Map<String,Object> result = new HashMap<String,Object>();
		OfferManagerVO vo = new OfferManagerVO();
		Pager pager = new Pager();
		pager.setRows(rows);
		pager.setPage(page);
		vo.setPager(pager);
		if(offerDay!=null){
			vo.setOfferDay(offerDay);
		}
		Pager sysparamList = offerService.getOfferManagerList(vo);
		if(null != sysparamList && sysparamList.getResultList() != null){
			result.put("total", sysparamList.getTotalCount());
			result.put("rows", sysparamList.getResultList());
		}else{
			result.put("total", 0);
			result.put("rows", null);
		}
		return result;
	}
	
	/**
	 * 
	 * @Description: 获取一条数据详情信息
	 * @param @param id
	 * @param @return   
	 * @return Map<String,Object>  
	 * @throws
	 * @author 徐安龙
	 * @date 2016年8月3日
	 */
	@RequestMapping("/getOfferManager")
	@ResponseBody
	public Map<String, Object> showOfferManager(@RequestParam Long id){
		log.info("|-----报盘详情查询开始.....");
		boolean success = true;
		String msg = "";
		Map<String,Object> map = new HashMap<String,Object>();
		
		if(null == id){
			success = false;
			msg = "记录不存在,或已被删除";
		}
		OfferManager om = offerService.getOfferManagerDetail(id);
		if(null == om){
			success = false;
			msg = "记录不存在";
			log.info("没查询到数据详情，可能已被删除！");
		}else{
			log.info("信息查询成功！");
			success = true;
		}
		map.put("success", success);
		map.put("offerManager", om);
		map.put("msg", msg);
		log.info("|-----报盘详情查询结束.....");
		return map;
	}
	
	
	
	/**
	 * 
	 * @Description: 新增
	 * @param @param vo
	 * @param @return   
	 * @return Map<String,Object>  
	 * @throws
	 * @author 徐安龙
	 * @date 2016年7月11日
	 */
	@RequestMapping(value="/addOfferManager",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> addOfferManager(OfferManagerVO vo){
		log.info("|-----新增报盘开始.....");
		Map<String,Object> map = new HashMap<String,Object>();
		boolean success = Boolean.TRUE;
		String msg = "";
		try {
			OfferManager offer = new OfferManager();
			BeanUtil.copyProperties(offer, vo);
			String code = offerService.addJob(offer,"add");
			if(code !=null&&code.equals(SUCCESS)){
				msg = "新增成功";
				log.info("添加成功！");
			}else{
				msg = "新增失败";
				log.info("添加失败！");
			}
		}catch(Exception ex) {
			ex.printStackTrace();
			success = false;
			msg = "添加失败！";
			log.info(ex.getMessage());
		}
		map.put("success", success);
		map.put("msg", msg);
		
		log.info("|-----新增报盘结束.....");
		return map;
	}
	
	/**
	 * 
	 * @Description: 修改
	 * @param @param vo
	 * @param @return   
	 * @return Map<String,Object>  
	 * @throws
	 * @author 徐安龙
	 * @date 2016年7月13日
	 */
	@RequestMapping(value="/updateOfferManager",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> updateOfferManager(OfferManagerVO vo){
		log.info("|-----报盘修改开始.....");
		Map<String,Object> map = new HashMap<String,Object>();
		boolean success = Boolean.TRUE;
		String msg = "";
		try {
			String code = offerService.updateJob(vo);
			if(code!=null&&code.equals(SUCCESS)){
				msg = "修改成功";
				log.info("修改成功！");
				
			}else{
				msg = "修改失败";
				log.info("修改失败！");
				throw new Exception("Job动态报盘修改失败");
			}
		} catch(Exception ex) {
			success = false;
			msg = "修改失败！";
			log.info(ex.getMessage());
		}finally{
			//插入系统日志  
			sysLogService.insert(new SysLog(EnumConstants.OptionModule.OFFER_MANAGER.getValue(), 
					EnumConstants.OptionType.UPDATE_OFFER_MANAGER.getValue() ,"报盘修改成功 ID："+ vo.getId()));
		}
		map.put("success", success);
		map.put("msg", msg);
		
		log.info("|-----报盘修改结束.....");
		return map;
	}
	
	
	
	/**
	 * 启用和禁用
	 * @param id
	 * @param status
	 * @return
	 */
	@RequestMapping(value="/setStatus")
	@ResponseBody
	public Map<String, Object> setStatus(Long id, Integer status) {
		Map<String,Object> map = new HashMap<String,Object>();
		boolean success = Boolean.FALSE;
		String msg = "操作失败";
		try {
			String code = "";
			if(status.equals(1)){
				code = offerService.addJob(offerService.getOfferManagerDetail(id),"start");
			}else{
				code = offerService.removeJob(offerService.getOfferManagerDetail(id));
			}
			if(code.equals(SUCCESS)){
				msg = "操作成功";
				success = Boolean.TRUE;
				OfferManagerVO vo = new OfferManagerVO();
				OfferManager om = offerService.getOfferManagerDetail(id);
				BeanUtil.copyProperties(vo,om);
				vo.setStatus(status);
				offerService.updateOfferManager(vo);
			}
			map.put("success", success);
			map.put("msg", msg);
			return map;
		} catch (Exception e) {
			map.put("success", success);
			map.put("msg", msg);
			return map;
		}
	}
}
