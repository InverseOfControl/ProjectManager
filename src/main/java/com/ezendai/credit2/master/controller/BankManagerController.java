package com.ezendai.credit2.master.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.framework.exception.BusinessException;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.master.model.BankManager;
import com.ezendai.credit2.master.service.BankManagerService;
import com.ezendai.credit2.master.vo.BankManagerVO;
import com.ezendai.credit2.system.controller.BaseController;
import com.ezendai.credit2.system.service.SysLogService;

/**
 * 
 * @Description: 银行信息
 * @author 张宜超
 * @date 2016年1月25日
 */

@Controller
@RequestMapping("/bankManager")
public class BankManagerController extends BaseController{
	
	private static final Logger log =Logger.getLogger(BankManagerController.class);

	@Autowired
	private BankManagerService bankService ;
	
	@Autowired
	private SysLogService sysLogService;
	
	
	
	/**
	 * 
	 * @Description: 银行list初始化
	 * @param @param request
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author 张宜超
	 * @date 2016年1月25日
	 */
	@RequestMapping("/bankInit")
	public String bankInit(HttpServletRequest request,ModelMap modelMap){
		log.info("|------初始化银行列表页面......");
		Long userId = ApplicationContext.getUser().getId();
		Integer userType = ApplicationContext.getUser().getUserType();
		
		request.setAttribute("userId", userId);
		request.setAttribute("userType", userType);
		setDataDictionaryArr(new String[] { EnumConstants.PRODUCT_ID,EnumConstants.PRODUCT_TYPE, EnumConstants.LOAN_STATUS, EnumConstants.CONTRACT_SRC });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		modelMap.put("banType", "");
		return "/master/bank/bankList";     
	}
	
	
	/**
	 * 
	 * @Description: 查询符合条件的银行list
	 * @param @param request
	 * @param @param modelMap
	 * @param @return   
	 * @return List<Bank>  
	 * @throws
	 * @author 张宜超
	 * @date 2016年1月25日
	 */
	@RequestMapping("/getBankList")
	@ResponseBody
	public Map<String,Object> showBankList(HttpServletRequest request,ModelMap modelMap,int rows, int page){
		log.info("|------开始获取银行列表.......");
		
		Map<String,Object> result = new HashMap<String,Object>();
		
		Long userId = ApplicationContext.getUser().getId();
		Integer userType = ApplicationContext.getUser().getUserType();
		
		request.setAttribute("userId", userId);
		request.setAttribute("userType", userType);
		setDataDictionaryArr(new String[] { EnumConstants.PRODUCT_ID,EnumConstants.PRODUCT_TYPE, EnumConstants.LOAN_STATUS, EnumConstants.CONTRACT_SRC });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		
		
		BankManagerVO vo = null;
		vo = validateBank(request);
		Pager pager = new Pager();
		pager.setRows(rows);
		pager.setPage(page);
		vo.setPager(pager);  
		//log.info("rows == " + rows + " ,page == " + page);
		log.info("bankName == " + vo.getBankName() +", bankCode == " + vo.getBankCode() + ", bankType == " + vo.getBankType());
		Pager bankList = bankService.getBankList(vo);
		
		if(null != bankList && bankList.getResultList() != null){
			log.info("bank list size is " + bankList.getResultList().size());
			result.put("total", bankList.getTotalCount());
			result.put("rows", bankList.getResultList());
		}else{
			log.info("bank list size is 0");
		}
		log.info("|------结束获取银行列表.......");
		return result;
	}
	


	/**  
	 * 
	 * @Description: 获取单个银行信息
	 * @param @param request
	 * @param @param modelMap
	 * @param @param id
	 * @param @return   
	 * @return Bank  
	 * @throws
	 * @author 张宜超
	 * @date 2016年1月25日
	 */
	@RequestMapping("/getBank")
	@ResponseBody
	public Map<String, Object> showBank(@RequestParam Long id){
		log.info("|------开始获取单个银行信息.......");
		Map<String,Object> result = new HashMap<String,Object>();
		boolean isSuccess = false;
		String msg = "";
		if(null == id){
			isSuccess = false;
			msg = "记录不存在,或已被删除";
		}
		BankManager bank = bankService.getBank(id);
		if(null == bank){
			isSuccess = false;
			msg = "记录不存在";
			log.info("没查询到数据，可能已被删除！");
		}else{
			log.info("信息查询成功！");
			isSuccess = true;
		}
		result.put("isSuccess", isSuccess);
		result.put("bank", bank);
		result.put("msg", msg);
		log.info("|------结束获取单个银行信息.......");
		return result;
	}
	
	/**
	 * 
	 * @Description: 添加一条银行信息
	 * @param @param request
	 * @param @return   
	 * @return Map<String, Object>  
	 * @throws
	 * @author 张宜超
	 * @date 2016年1月25日
	 */
	@RequestMapping(value="/addBank",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> addBank(BankManagerVO vo){
		log.info("|------开始添加一条银行信息.......");
		Map<String,Object> map = new HashMap<String,Object>();
		
		map = validateBankInfo("add",vo);
		
		if(map!=null && map.get("isSuccess")!=null && map.get("isSuccess").equals(false)){
			return map;
		}
		map.clear();
		boolean isSuccess = true;
		String msg = "";
		try {
			bankService.addBank(vo); 
				msg = "新增成功";
			log.info("添加成功！");
		} catch(BusinessException ex) {
			isSuccess = false;
			msg = "新增失败";
			log.info("添加失败！");
		} catch(Exception ex) {
			isSuccess = false;
			msg = ex.getMessage();
			log.info("添加失败！");
		}
		map.put("isSuccess", isSuccess);
		map.put("msg", msg);
		
		log.info("|------结束添加一条银行信息.......");
		return map;
	}
	
	/**
	 * 
	 * @Description: 更新银行信息
	 * @param @param request
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author 张宜超
	 * @date 2016年1月25日
	 */
	@RequestMapping(value="/updateBank",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> updateBank(BankManagerVO vo){
		log.info("|------开始更新一条银行信息.......");
		Map<String,Object> map = new HashMap<String,Object>();
		
		map = validateBankInfo("update",vo);
		
		if(map!=null && map.get("isSuccess")!=null && map.get("isSuccess").equals(false)){
			return map;
		}
		map.clear();
		boolean isSuccess = true;
		String msg = "";
		try {
			log.info("id = " +vo.getId()+" bankName = "+vo.getBankName()+" bankType = "+vo.getBankType());
			//mybatis 0跟null顾虑条件一样 前端用js可去掉此处
			if(null != vo.getBankType() && vo.getBankType() ==0){
				vo.setBankType(10);
			}
			int ucount = bankService.updateBank(vo);
			if(ucount > 0){
				msg = "修改成功";
			}
		} catch(BusinessException ex) {
			log.info("信息更新失败！");
			isSuccess = false;
			msg = "修改失败";
		} catch(Exception ex) {
			log.info("信息更新失败！");
			isSuccess = false;
			msg = ex.getMessage();
		}
		map.put("isSuccess", isSuccess);
		map.put("msg", msg);
		
		log.info("|------结束更新一条银行信息.......");
		return map;
	}
	
	@RequestMapping(value="/deleteBank")
	@ResponseBody
	public Map<String,Object> deleteBank(@RequestParam Long id){
		log.info("|------准备删除一条银行信息.......");
		Map<String,Object> map = new HashMap<String,Object>();
		boolean isSuccess = true;
		//String msg = "";
		try{
			
			bankService.deleteBank(id);
			log.info("删除成功");
			map.put("isSuccess", isSuccess);
			map.put("msg", "删除成功");
		}catch(Exception e){
			log.info(e.getMessage());
			map.put("isSuccess", false);
			map.put("msg", "删除失败");
		}
		log.info("|------结束删除一条银行信息.......");
		return map;
	}
	
	/**
	 * 
	 * @Description: 获取并组装参数
	 * @param @param request
	 * @param @return   
	 * @return BankVO  
	 * @throws
	 * @author 张宜超
	 * @date 2016年1月25日
	 */
	public BankManagerVO validateBank(HttpServletRequest request){
		log.info("|------获取并组装参数.......");
		BankManagerVO vo = new BankManagerVO();
		String reqBankName = request.getParameter("bankName");
		String reqBankCode = request.getParameter("bankCode");
		String reqTppBankCode = request.getParameter("tppBankCode");
		String reqTppType  = request.getParameter("tppType");
		String reqBankType = request.getParameter("bankType");
		
		Integer tppType = null;
		if(null != reqTppType && reqTppType != ""){
			tppType = Integer.parseInt(reqTppType);
		}
		
		Integer bankType = null;
		if(null != reqBankType && reqBankType != ""){
			bankType = Integer.parseInt(reqBankType.toString());
		}
		
		
		if(null != reqBankName && reqBankName != ""){
			vo.setBankName(reqBankName);
		}
		
		if(null != reqBankCode && reqBankCode != ""){
			vo.setBankCode(reqBankCode);
		}
		
		if(null != reqTppBankCode && reqTppBankCode !=""){
			vo.setTppBankCode(reqTppBankCode);
		}
		
		if(null != tppType ){
			vo.setTppType(tppType);
		}
		
		
		if(null != bankType){
			vo.setBankType(bankType);
		}
		log.info("|------组装参数结束......");
		return vo;
	}
	
	/**
	 * 
	 * @Description: 判断数据是否已存在
	 * @param @param vo
	 * @param @return   
	 * @return Map<String,Object>  
	 * @throws
	 * @author 张宜超
	 * @date 2016年1月25日
	 */
	public Map<String, Object> validateBankInfo(String mark,BankManagerVO vo){
		log.info("|------检验数据是否存在.......");
		Map<String,Object> result = new HashMap<String,Object>();
		
		BankManager bkm = new BankManager();
		if("update".equals(mark)){
			//获取更新id的更新前的数据信息
			bkm = bankService.getBank(vo.getId());
			//validateVO.setId(vo.getId());
		}
		BankManagerVO validateVO = new BankManagerVO();
		validateVO.setBankName(vo.getBankName());
		//查询同名称银行
		List<BankManager> bkList = new ArrayList<BankManager>();
		bkList = bankService.getBankByConditions(validateVO);
		
		if("update".equals(mark) ){
			if(bkList != null && bkList.size() > 0){
				for(int i = 0; i< bkList.size(); i++){
					if(!bkm.getId().equals(bkList.get(i).getId())){
						
						log.info("系统已存在该银行名称!");
						result.put("isSuccess", false);
						result.put("msg", "系统已存在该银行名称!");
						return result;
					}
				}
			}
		}else{
			if(bkList !=null && bkList.size() > 0){
				log.info("系统已存在该银行名称!");
				result.put("isSuccess", false);
				result.put("msg", "系统已存在该银行名称!");
				return result;
			}
		}
		
		validateVO.setBankName(null);
		if(bkList != null && bkList.size() > 0){
			
			bkList = null;
		}
		//result.clear();
		
		validateVO.setBankCode(vo.getBankCode());
		bkList = bankService.getBankByConditions(validateVO);
		
		if("update".equals(mark)){
			if(bkList != null && bkList.size() > 0){
				for(int i = 0; i< bkList.size(); i++){
					if(!bkm.getId().equals(bkList.get(i).getId())){
						log.info("系统已存在该银行代码!");
						result.put("isSuccess", false);
						result.put("msg", "系统已存在该银行代码!");
						return result;
					}
				}
			}
		}else{
			if(bkList !=null && bkList.size() > 0){
				log.info("系统已存在该银行代码!");
				result.put("isSuccess", false);
				result.put("msg", "系统已存在该银行代码!");
				return result;
			}
		}
		
		validateVO.setBankCode(null);
		//result.clear();
		if(bkList != null && bkList.size() > 0){
			
			bkList = null;
		}
		
		validateVO.setTppBankCode(vo.getTppBankCode());
		bkList = bankService.getBankByConditions(validateVO);
		
		if("update".equals(mark)){
			if(bkList != null && bkList.size() > 0){
				for(int i = 0; i< bkList.size(); i++){
					if(!bkm.getId().equals(bkList.get(i).getId())){
						log.info("系统已存在该TPP银行代码!");
						result.put("isSuccess", false);
						result.put("msg", "系统已存在该TPP银行代码!");
						return result;
					}
				}
			}
		}else{
			if(bkList !=null && bkList.size() > 0){
				log.info("系统已存在该TPP银行代码!");
				result.put("isSuccess", false);
				result.put("msg", "系统已存在该TPP银行代码!");
				return result;
			}
		}
		
		//result.clear();
		log.info("|------校验数据结束......");
		return result;
	}
}
