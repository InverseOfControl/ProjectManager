package com.ezendai.credit2.master.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
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
import com.ezendai.credit2.framework.cache.CacheService;
import com.ezendai.credit2.framework.exception.BusinessException;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.master.model.ProductManager;
import com.ezendai.credit2.master.service.ProductManagerServcie;
import com.ezendai.credit2.master.vo.ProductManagerVO;
import com.ezendai.credit2.system.controller.BaseController;
import com.ezendai.credit2.system.service.SysLogService;

/**
 * 
 * @Description: 产品列表
 * @author 张宜超
 * @date 2016年3月9日
 */
@RequestMapping("/productManager")
@Controller
public class ProductManagerController extends BaseController{

	private static final Logger log =Logger.getLogger(ProductManagerController.class);
	
	@Autowired
	private SysLogService sysLogService;
	
	@Autowired
	private ProductManagerServcie pmService;
	//刷新缓存
	@Autowired
	private CacheService cacheService;
	
	/**memcached key 加上一些开始字符串，防止key冲突*/
	private static final String PARAMETER_START = "CREDIT2_SYSPARA_";
	
	/**
	 * 
	 * @Description: 初始化加载list页面
	 * @param @param request
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author 张宜超
	 * @date 2016年2月1日
	 */
	@RequestMapping("/productManagerInit")
	public String productManagerInit(HttpServletRequest request){
		log.info("|-----初始化产品信息.....");
		Long userId = ApplicationContext.getUser().getId();
		Integer userType = ApplicationContext.getUser().getUserType();
		
		request.setAttribute("userId", userId);
		request.setAttribute("userType", userType);
		setDataDictionaryArr(new String[] { EnumConstants.PRODUCT_ID,EnumConstants.PRODUCT_TYPE, EnumConstants.LOAN_STATUS, EnumConstants.CONTRACT_SRC });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		return "/master/product/productManagerList";
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
	 * @author 张宜超
	 * @date 2016年2月1日
	 */
	@RequestMapping("/productManagerList")
	@ResponseBody
	public Map<String,Object> showProductManagerList(HttpServletRequest request,ModelMap modelMap,int rows, int page){
		log.info("|-----开始查询数据列表.....");

		Map<String,Object> result = new HashMap<String,Object>();
		Long userId = ApplicationContext.getUser().getId();
		Integer userType = ApplicationContext.getUser().getUserType();
		
		request.setAttribute("userId", userId);
		request.setAttribute("userType", userType);
		setDataDictionaryArr(new String[] { EnumConstants.PRODUCT_ID,EnumConstants.PRODUCT_TYPE, EnumConstants.LOAN_STATUS, EnumConstants.CONTRACT_SRC });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		
		ProductManagerVO vo = new ProductManagerVO();
		vo = validateProductManager(request);
		Pager pager = new Pager();
		pager.setRows(rows);
		pager.setPage(page);
		vo.setPager(pager);
		log.info("查询产品列表分页信息....");
		Pager sysparamList = pmService.getProductList(vo);
		if(null != sysparamList && sysparamList.getResultList() != null){
			log.info("productManager list size is " + sysparamList.getResultList().size());
			result.put("total", sysparamList.getTotalCount());
			result.put("rows", sysparamList.getResultList());
		}else{
			log.info("bank list size is 0");
		}
		log.info("|-----结束查询数据列表.....");
		
		return result;
	}
	
	/**
	 * 
	 * @Description: 获取一条数据信息
	 * @param @param id
	 * @param @return   
	 * @return Map<String,Object>  
	 * @throws
	 * @author 张宜超
	 * @date 2016年2月1日
	 */
	@RequestMapping("/getProductManager")
	@ResponseBody
	public Map<String, Object> showProductManager(@RequestParam Long id){
		log.info("|-----开始获取一条产品信息.....");
		boolean success = true;
		String msg = "";
		Map<String,Object> map = new HashMap<String,Object>();
		
		if(null == id){
			success = false;
			msg = "记录不存在,或已被删除";
		}
		ProductManager spm = pmService.getProduct(id);
		if(null == spm){
			success = false;
			msg = "记录不存在";
			log.info("没查询到数据，可能已被删除！");
		}else{
			log.info("信息查询成功！");
			success = true;
		}
		map.put("success", success);
		map.put("productManager", spm);
		map.put("msg", msg);
		log.info("|-----结束获取一条产品信息.....");
		return map;
	}
	
	/**
	 * 
	 * @Description: 添加一条系统参数数据
	 * @param @param vo
	 * @param @return   
	 * @return Map<String,Object>  
	 * @throws
	 * @author 张宜超
	 * @date 2016年2月1日
	 */
	@RequestMapping(value="/addProductManager",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> addProductManager(ProductManagerVO vo){
		log.info("|-----开始添加一条产品.....");
		Map<String,Object> map = new HashMap<String,Object>();
		boolean success = true;
		map = validateProductManagerInfo("add",vo);
		
		if(map!=null && map.get("success")!=null && map.get("success").equals(false)){
			return map;
		}
		map.clear();
		String msg = "";
		try {
			int count = pmService.addProduct(vo); 
			if(count > 0){
				msg = "新增成功";
				log.info("添加成功！");
				
			}else{
				msg = "新增失败";
				log.info("添加失败！");
			}
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
		
		
		log.info("|-----结束添加一条产品.....");
		return map;
	}
	
	/**
	 * 
	 * @Description: 修改一条数据
	 * @param @param vo
	 * @param @return   
	 * @return Map<String,Object>  
	 * @throws
	 * @author 张宜超
	 * @date 2016年2月1日
	 */
	@RequestMapping(value="/updateProductManager",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> updateProductManager(ProductManagerVO vo){
		log.info("|-----开始更新一条系统参数.....");
		Map<String,Object> map = new HashMap<String,Object>();
		map = validateProductManagerInfo("update",vo);
		
		if(map!=null && map.get("success")!=null && map.get("success").equals(false)){
			return map;
		}
		map.clear();
		boolean success = true;
		String msg = "";
		try {
			log.info(" 调用service更新数据....");
			int ucount = pmService.updateProduct(vo);
			if(ucount > 0){
				msg = "修改成功";
			}
		} catch(BusinessException ex) {
			log.info("信息更新失败！");
			success = false;
			msg = "修改失败";
		} catch(Exception ex) {
			log.info("信息更新失败！");
			success = false;
			msg = ex.getMessage();
		}
		map.put("success", success);
		map.put("msg", msg);
		log.info("|-----结束更新一条系统参数.....");
		return map;
	}
	
	
	/**
	 * 组装产品
	 */
	public ProductManagerVO validateProductManager(HttpServletRequest request){
		log.info("|------获取并组装产品.......");
		
		String rproductCode = request.getParameter("productCode");
		String rproductName = request.getParameter("productName");
		String rconsultingFeeRate = request.getParameter("consultingFeeRate");
		String rmanagePartRate = request.getParameter("managePartRate");
		String rmanageFeeRate = request.getParameter("manageFeeRate");
		String roverdueInterestRate = request.getParameter("overdueInterestRate");
		String rriskRate = request.getParameter("riskRate");
		String rassessmentFeeRate = request.getParameter("assessmentFeeRate");
		String rrate = request.getParameter("rate");
		String rstatus = request.getParameter("status");
		String rremark = request.getParameter("remark");
		String rversion = request.getParameter("version");
		String rproductType = request.getParameter("productType");
		String rproductTypeName = request.getParameter("productTypeName");
		String rthirdFeeRate = request.getParameter("thirdFeeRate");
		String rmonthRate = request.getParameter("monthRate");
		String rpenaltyRate = request.getParameter("penaltyRate");
		String rproductChannelId = request.getParameter("productChannelId");
		String rproductChannelName = request.getParameter("productChannelName");
		String ryearRate = request.getParameter("yearRate");
		
		DecimalFormat df = new DecimalFormat("0.0000");
		ProductManagerVO vo = new ProductManagerVO();
		if(rproductCode !=null && rproductCode != ""){
			vo.setProductCode(rproductCode);
		}
		if(rproductName !=null && rproductName != ""){
			vo.setProductName(rproductName);
		}
		if(rconsultingFeeRate !=null && rconsultingFeeRate != ""){
			String sconsultingFeeRate = df.format(rconsultingFeeRate);
			vo.setConsultingFeeRate(new BigDecimal(sconsultingFeeRate));
		}
		if(rmanagePartRate !=null && rmanagePartRate != ""){
			String smanagePartRate = df.format(rmanagePartRate);
			vo.setManagePartRate(new BigDecimal(smanagePartRate));
		}
		if(rmanageFeeRate !=null && rmanageFeeRate != ""){
			String smanageFeeRate = df.format(rmanageFeeRate);
			vo.setManageFeeRate(new BigDecimal(smanageFeeRate));
		}
		if(roverdueInterestRate !=null && roverdueInterestRate != ""){
			String soverdueInterestRate = df.format(roverdueInterestRate);
			vo.setOverdueInterestRate(new BigDecimal(soverdueInterestRate));
		}
		if(rriskRate !=null && rriskRate != ""){
			String sriskRate = df.format(rriskRate);
			vo.setRiskRate(new BigDecimal(sriskRate));
		}
		if(rassessmentFeeRate !=null && rassessmentFeeRate != ""){
			String sassessmentFeeRate = df.format(rassessmentFeeRate);
			vo.setAssessmentFeeRate(new BigDecimal(sassessmentFeeRate));
		}
		if(rrate !=null && rrate != ""){
			String srate = df.format(rrate);
			vo.setRate(new BigDecimal(srate));
		}
		if(rstatus !=null && rstatus != ""){
			vo.setStatus(Integer.parseInt(rstatus));
		}
		if(rremark !=null && rremark != ""){
			vo.setRemark(rremark);
		}
		if(rversion !=null && rversion != ""){
			vo.setVersion(Long.parseLong(rversion));
		}
		if(rproductType !=null && rproductType != ""){
			vo.setProductType(Integer.parseInt(rproductType));
		}
		if(rproductTypeName !=null && rproductTypeName != ""){
			vo.setProductTypeName(rproductTypeName);
		}
		if(rthirdFeeRate !=null && rthirdFeeRate != ""){
			String sthirdFeeRate = df.format(rthirdFeeRate);
			vo.setThirdFeeRate(new BigDecimal(sthirdFeeRate));
		}
		if(rmonthRate !=null && rmonthRate != ""){
			String smonthRate = df.format(rmonthRate);
			vo.setMonthRate(new BigDecimal(smonthRate));
		}
		if(rpenaltyRate !=null && rpenaltyRate != ""){
			String spenaltyRate = df.format(rpenaltyRate);
			vo.setPenaltyRate(new BigDecimal(spenaltyRate));
		}
		if(rproductChannelId !=null && rproductChannelId != ""){
			vo.setProductChannelId(Integer.parseInt(rproductChannelId));
		}
		if(rproductChannelName !=null && rproductChannelName != ""){
			vo.setProductChannelName(rproductChannelName);
		}
		if(ryearRate !=null && ryearRate != ""){
			String syearRate = df.format(ryearRate);
			vo.setYearRate(new BigDecimal(syearRate));
		}
		
		log.info("|------组装产品结束......");
		return vo;
	}
	
	/**
	 * 
	 * @Description: 校验数据
	 * @param @param mark
	 * @param @param vo
	 * @param @return   
	 * @return Map<String,Object>  
	 * @throws
	 * @author 张宜超
	 * @date 2016年2月1日
	 */
	public Map<String, Object> validateProductManagerInfo(String mark,ProductManagerVO vo){
		log.info("|------检验数据是否存在.......");
		Map<String,Object> result = new HashMap<String,Object>();
		ProductManager spManager = new ProductManager();
		//用于比对数据 mark update/add
		if("update".equals(mark)){ 
			spManager = pmService.getProduct(vo.getId());
		}
		
		ProductManagerVO spmVO = new ProductManagerVO();
		spmVO.setProductCode(vo.getProductCode());
		List<ProductManager> spmList = pmService.getProductByConditions(spmVO);
		
		if(spmList != null && spmList.size() > 0){
			if("update".equals(mark)){
				for(int i = 0; i < spmList.size(); i++){
					if(!spManager.getId().equals(spmList.get(i).getId())){
						log.info("系统已存在该产品编号!");
						result.put("success", false);
						result.put("msg", "系统已存在该产品编号!");
						return result;
					}
				}
			}else{ //add判断有无此数据即可
				log.info("系统已存在该产品编号!");
				result.put("success", false);
				result.put("msg", "系统已存在该产品编号!");
				return result;
			}
		}
		
		spmVO.setProductCode(null);
		if(spmList != null && spmList.size() > 0){
			spmList = null;
		}
		
		spmVO.setProductName(vo.getProductName());
		spmList = pmService.getProductByConditions(spmVO);
		if(spmList != null && spmList.size() > 0){
			if("update".equals(mark)){
				for(int i = 0; i < spmList.size(); i++){
					if(!spManager.getId().equals(spmList.get(i).getId())){
						log.info("系统已存在该产品名称!");
						result.put("success", false);
						result.put("msg", "系统已存在该产品名称!");
						return result;
					}
				}
			}else{
				log.info("系统已存在该产品名称!");
				result.put("success", false);
				result.put("msg", "系统已存在该产品名称!");
				return result;
			}
		}
		
		spmVO.setProductName(null);
		if(spmList != null && spmList.size() > 0){
			spmList = null;
		}
		
		log.info("|------校验数据结束......");
		return result;
	}
}
