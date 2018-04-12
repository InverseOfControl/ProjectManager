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
import com.ezendai.credit2.framework.util.BeanUtil;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.master.model.ProductDetailManager;
import com.ezendai.credit2.master.model.ProductTypes;
import com.ezendai.credit2.master.service.ProductDetailManagerServcie;
import com.ezendai.credit2.master.service.ProductManagerServcie;
import com.ezendai.credit2.master.vo.ProductDetailManagerVO;
import com.ezendai.credit2.system.controller.BaseController;
import com.ezendai.credit2.system.model.SysLog;
import com.ezendai.credit2.system.service.SysLogService;

@RequestMapping("/productDetailManager")
@Controller
public class ProductDetailManagerController extends BaseController{

	private static final Logger log =Logger.getLogger(ProductDetailManagerController.class);
	
	@Autowired
	private SysLogService sysLogService;
	
	@Autowired
	private ProductDetailManagerServcie pmService;
	//刷新缓存
	@Autowired
	private CacheService cacheService;
	@Autowired
	private ProductManagerServcie productService;
	
	/**memcached key 加上一些开始字符串，防止key冲突*/
//	private static final String PARAMETER_START = "CREDIT2_SYSPARA_";
	
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
	@RequestMapping("/productDetailManagerInit")
	public String productDetailManagerInit(HttpServletRequest request){
		log.info("|-----初始化产品详情.....");
		Long userId = ApplicationContext.getUser().getId();
		Integer userType = ApplicationContext.getUser().getUserType();
		
		request.setAttribute("userId", userId);
		request.setAttribute("userType", userType);
		setDataDictionaryArr(new String[] { EnumConstants.PRODUCT_ID,EnumConstants.PRODUCT_TYPE, EnumConstants.LOAN_STATUS, EnumConstants.CONTRACT_SRC });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		return "/master/product/productDetailManagerList";
	}
	
	@RequestMapping("/getProductType")
	@ResponseBody
	public List<ProductTypes> getProductByCurrUser() {
		Long userId = ApplicationContext.getUser().getId();
		List<ProductTypes> productType = productService.selectProductsByUserId(userId);
		ProductTypes product =new ProductTypes();
		product.setProductType(null);
		 product.setProductTypeName("全部");
		productType.add(0, product);
		return productType;
	}
	/**
	 * 
	 * @Description: 查询数据详情列表
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
	@RequestMapping("/productDetailManagerList")
	@ResponseBody
	public Map<String,Object> showProductDetailManagerList(HttpServletRequest request,ModelMap modelMap,int rows, int page){
		log.info("|-----开始查询数据详情列表.....");

		Map<String,Object> result = new HashMap<String,Object>();
		Long userId = ApplicationContext.getUser().getId();
		Integer userType = ApplicationContext.getUser().getUserType();
		
		request.setAttribute("userId", userId);
		request.setAttribute("userType", userType);
		setDataDictionaryArr(new String[] { EnumConstants.PRODUCT_ID,EnumConstants.PRODUCT_TYPE, EnumConstants.LOAN_STATUS, EnumConstants.CONTRACT_SRC });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		
		ProductDetailManagerVO vo = new ProductDetailManagerVO();
		vo = validateProductDetailManager(request);
		Pager pager = new Pager();
		pager.setRows(rows);
		pager.setPage(page);
		vo.setPager(pager);
		
		Pager sysparamList = pmService.getProductDetailList(vo);
		if(null != sysparamList && sysparamList.getResultList() != null){
			log.info("productDetailManager list size is " + sysparamList.getResultList().size());
			result.put("total", sysparamList.getTotalCount());
			result.put("rows", sysparamList.getResultList());
		}else{
			log.info("bank list size is 0");
		}
		log.info("|-----结束查询数据详情列表.....");
		
		return result;
	}
	
	/**
	 * 
	 * @Description: 获取一条数据详情信息
	 * @param @param id
	 * @param @return   
	 * @return Map<String,Object>  
	 * @throws
	 * @author 张宜超
	 * @date 2016年2月1日
	 */
	@RequestMapping("/getProductDetailManager")
	@ResponseBody
	public Map<String, Object> showProductDetailManager(@RequestParam Long id){
		log.info("|-----开始获取一条产品详情.....");
		boolean success = true;
		String msg = "";
		Map<String,Object> map = new HashMap<String,Object>();
		
		if(null == id){
			success = false;
			msg = "记录不存在,或已被删除";
		}
		ProductDetailManager spm = pmService.getProductDetail(id);
		if(null == spm){
			success = false;
			msg = "记录不存在";
			log.info("没查询到数据详情，可能已被删除！");
		}else{
			log.info("信息查询成功！");
			success = true;
		}
		map.put("success", success);
		map.put("productDetailManager", spm);
		map.put("msg", msg);
		log.info("|-----结束获取一条产品详情.....");
		return map;
	}
	
	
	/**
	 * 组装产品参数
	 */
	public ProductDetailManagerVO validateProductDetailManager(HttpServletRequest request){
		log.info("|------获取并组装产品.......");
		
		String rproductId = request.getParameter("productId");
		String rcarProductType = request.getParameter("carProductType");
		String rsumRate = request.getParameter("sumRate");
		String rterm = request.getParameter("term");
		String rlowerLimit = request.getParameter("lowerLimit");
		String rupperLimit = request.getParameter("upperLimit");
		String rstatus = request.getParameter("status");
		String rremark = request.getParameter("remark");
		String rversion = request.getParameter("version");
		String ryearRate = request.getParameter("yearRate");
		String rmemberType = request.getParameter("memberType");
		String rriskRate = request.getParameter("riskRate");
		String rmonthRate = request.getParameter("monthRate");
		String rthirdFeeRate = request.getParameter("thirdFeeRate");
		
		DecimalFormat df = new DecimalFormat("0.0000");
		ProductDetailManagerVO vo = new ProductDetailManagerVO();
		if(rproductId !=null && rproductId != ""){
			vo.setProductId(Long.parseLong(rproductId));
		}
		if(rcarProductType !=null && rcarProductType != ""){
			vo.setCarProductType(Integer.parseInt(rcarProductType));
		}
		if(rsumRate !=null && rsumRate != ""){
			String ssumRate = df.format(rsumRate);
			vo.setSumRate(new BigDecimal(ssumRate));
		}
		if(rterm !=null && rterm != ""){
			vo.setTerm(Integer.parseInt(rterm));
		}
		if(rlowerLimit !=null && rlowerLimit != ""){
			String slowerLimit = df.format(rlowerLimit);
			vo.setLowerLimit(new BigDecimal(slowerLimit));
		}
		if(rupperLimit !=null && rupperLimit != ""){
			String supperLimit = df.format(rupperLimit);
			vo.setSumRate(new BigDecimal(supperLimit));
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
		if(ryearRate !=null && ryearRate != ""){
			String syearRate = df.format(ryearRate);
			vo.setYearRate(new BigDecimal(syearRate));
		}
		if(rmemberType !=null && rmemberType != ""){
			vo.setMemberType(Integer.parseInt(rmemberType));
		}
		if(rriskRate !=null && rriskRate != ""){
			String sriskRate = df.format(rriskRate);
			vo.setRiskRate(new BigDecimal(sriskRate));
		}
		if(rmonthRate !=null && rmonthRate != ""){
			String smonthRate = df.format(rmonthRate);
			vo.setMonthRate(new BigDecimal(smonthRate));
		}
		if(rthirdFeeRate !=null && rthirdFeeRate != ""){
			String sthirdFeeRate = df.format(rthirdFeeRate);
			vo.setThirdFeeRate(new BigDecimal(sthirdFeeRate));
		}
		
		log.info("|------组装产品详情结束......");
		return vo;
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
	@RequestMapping(value="/addProductDetailManager",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> addProductManager(ProductDetailManagerVO vo){
		log.info("|-----开始新增产品明细.....");
		Map<String,Object> map = new HashMap<String,Object>();
		boolean success = Boolean.TRUE;
		String msg = "";
		ProductDetailManager pdm = null;
		try {
			ProductDetailManager product = new ProductDetailManager();
			BeanUtil.copyProperties(product, vo);
			pdm = pmService.addProductDetail(product); 
			if(pdm !=null){
				msg = "新增成功";
				log.info("添加成功！");
				
				//插入系统日志  
				sysLogService.insert(new SysLog(EnumConstants.OptionModule.PRODUCT_DETAIL_MANAGER.getValue(), 
						EnumConstants.OptionType.ADD_PRODUCT_DETAIL.getValue() ,"产品明细新增成功 ID："+ pdm.getId()));
			}else{
				msg = "新增失败";
				log.info("添加失败！");
			}
		} catch(BusinessException ex) {
			success = false;
			msg = "新增失败";
			log.info(ex);
		} catch(Exception ex) {
			ex.printStackTrace();
			success = false;
			msg = "添加失败！";
			log.info(ex.getMessage());
		}
		map.put("success", success);
		map.put("msg", msg);
		
		log.info("|-----结束新增产品明细.....");
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
	@RequestMapping(value="/updateProductDetailManager",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> updateProductDetailManager(ProductDetailManagerVO vo){
		log.info("|-----开始修改产品明细.....");
		Map<String,Object> map = new HashMap<String,Object>();
		boolean success = Boolean.TRUE;
		String msg = "";
		try {
			int count = pmService.updateProductDetail(vo); 
			if(count > 0){
				msg = "修改成功";
				log.info("修改成功！");
				
				//插入系统日志  
				sysLogService.insert(new SysLog(EnumConstants.OptionModule.PRODUCT_DETAIL_MANAGER.getValue(), 
						EnumConstants.OptionType.UPDATE_PRODUCT_DETAIL.getValue() ,"产品明细修改成功 ID："+ vo.getId()));
			}else{
				msg = "修改失败";
				log.info("修改失败！");
			}
		} catch(BusinessException ex) {
			success = false;
			msg = "修改失败";
			log.info(ex);
		} catch(Exception ex) {
			ex.printStackTrace();
			success = false;
			msg = "修改失败！";
			log.info(ex.getMessage());
		}
		map.put("success", success);
		map.put("msg", msg);
		
		log.info("|-----结束修改产品明细.....");
		return map;
	}
}
