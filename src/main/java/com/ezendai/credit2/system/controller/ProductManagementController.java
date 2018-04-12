package com.ezendai.credit2.system.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezendai.credit2.apply.assembler.ProductAssembler;
import com.ezendai.credit2.apply.assembler.ProductDetailAssembler;
import com.ezendai.credit2.apply.model.BusinessLog;
import com.ezendai.credit2.apply.model.Product;
import com.ezendai.credit2.apply.model.ProductDetail;
import com.ezendai.credit2.apply.service.BusinessLogService;
import com.ezendai.credit2.apply.service.ProductDetailService;
import com.ezendai.credit2.apply.service.ProductService;
import com.ezendai.credit2.apply.vo.ProductDetailVO;
import com.ezendai.credit2.apply.vo.ProductVO;
import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.framework.constant.Constants;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.master.model.BaseArea;
import com.ezendai.credit2.master.service.BaseAreaService;
import com.ezendai.credit2.report.service.RepaymentReportService;
import com.ezendai.credit2.system.Credit2Properties;
import com.ezendai.credit2.system.assembler.ProductManagementAssembler;
import com.ezendai.credit2.system.model.BaseAreaProduct;
import com.ezendai.credit2.system.service.BaseAreaProductService;
import com.ezendai.credit2.system.service.SysParameterService;
import com.ezendai.credit2.system.vo.BaseAreaProductVO;
import com.ezendai.credit2.system.vo.ProductManagementVO;

/*
 * author:00226557
 */
@Controller
@RequestMapping("/product/management")
public class ProductManagementController  extends BaseController{
    @Autowired
    private BaseAreaService baseAreaService;
    @Autowired
    private RepaymentReportService repaymentReportService;
    @Autowired
    private SysParameterService sysParameterService;
    @Autowired
	private Credit2Properties credit2Properties;
    @Autowired
    private ProductService productService;
    @Autowired
    private BaseAreaProductService baseAreaProductService;
    @Autowired
    private ProductDetailService productDetailService;
    @Autowired
    private BusinessLogService businessLogService;
    
    private static final Logger logger = Logger.getLogger(ProductManagementController.class);
    
    
	@RequestMapping("/list")
	public String list(HttpServletRequest request,HttpServletResponse respose) {
		/*设置数据字典*/
		setDataDictionaryArr(new String[] { EnumConstants.PRODUCT_TYPE, EnumConstants.PRODUCT_STATUS});
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		return "system/productList";
	}
  
	@SuppressWarnings({"rawtypes","unchecked"})
	@RequestMapping("/list.json")
	@ResponseBody
	public Map list(@RequestParam(value = Constants.PAGE_NUMBER_NAME, defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page,
					@RequestParam(value = Constants.PAGE_ROWS_NAME, defaultValue = Constants.DEFAULT_PAGE_ROWS) int rows, @ModelAttribute("vo") ProductVO vo, HttpServletRequest request) throws Exception{
	
		//用户
		Long userId = ApplicationContext.getUser().getId();
		List<Product> productList = new ArrayList<Product>();
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		Pager pager = null;
		pager = productService.findWithPgByUserId(userId, rows, page);
		productList = pager.getResultList();
		result.put("total", pager.getTotalCount());
		result.put("rows", productList);
		return result;
	}

	@RequestMapping("/productAdd")
	public String productAdd(HttpServletRequest request,Model model) throws Exception{
		/*设置数据字典*/
		setDataDictionaryArr(new String[] { EnumConstants.PRODUCT_TYPE});
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		List<BaseArea> allCitys = baseAreaService.getAllCitys();
		model.addAttribute("cityList",allCitys);
		return "system/productAdd";
	}
	
	@RequestMapping("/getProductDetails")
	@ResponseBody
	public ProductManagementVO getProductDetails(Long productId,HttpServletRequest request) throws Exception{
		ProductManagementVO productManagementVO = new ProductManagementVO();
		Product product = productService.get(productId);
		ProductDetailVO productDetailVO = new ProductDetailVO();
		productDetailVO.setProductId(Long.valueOf(productId));
		List<ProductDetail> productDetailList = productDetailService.findListByVO(productDetailVO);
		BaseAreaProductVO  baseAreaProductVO = new BaseAreaProductVO();
		baseAreaProductVO.setProductId(productId.intValue());
		List<BaseAreaProduct> baseAreaProductList = baseAreaProductService.findListByVO(baseAreaProductVO);
		ProductManagementAssembler.transferProduct2VO(product, productManagementVO);
		ProductManagementAssembler.transferProductDetailList2VO(productDetailList, productManagementVO,productService);
		productManagementVO.setBaseAreaProductList(baseAreaProductList);
		return productManagementVO;
	}
	
	@RequestMapping("/carProductView")
	public String carProductView(HttpServletRequest request,Model model) throws Exception{
		setDataDictionaryArr(new String[] { EnumConstants.PRODUCT_STATUS});
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		List<BaseArea> allCitys = baseAreaService.getAllCitys();
		model.addAttribute("cityList",allCitys);
		return "system/carProductView";
	}
	
	@RequestMapping("/seProductView")
	public String seProductView(HttpServletRequest request,Model model) throws Exception{
		/*设置数据字典*/
		setDataDictionaryArr(new String[] { EnumConstants.PRODUCT_STATUS});
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		List<BaseArea> allCitys = baseAreaService.getAllCitys();
		model.addAttribute("cityList",allCitys);
		return "system/seProductView";
	}
	
	@RequestMapping("/carProductEdit")
	public String carProductEdit(HttpServletRequest request,Model model) throws Exception{
		setDataDictionaryArr(new String[] { EnumConstants.PRODUCT_STATUS});
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		List<BaseArea> allCitys = baseAreaService.getAllCitys();
		model.addAttribute("cityList",allCitys);
		return "system/carProductEdit";
	}
	
	@RequestMapping("/seProductEdit")
	public String seProductEdit(HttpServletRequest request,Model model) throws Exception{
		/*设置数据字典*/
		setDataDictionaryArr(new String[] { EnumConstants.PRODUCT_STATUS});
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		List<BaseArea> allCitys = baseAreaService.getAllCitys();
		model.addAttribute("cityList",allCitys);
		return "system/seProductEdit";
	}
	
	@RequestMapping("/productEditSave")
	@ResponseBody
	@Transactional
	public Map<String, Object> productEditSave(ProductManagementVO vo) throws Exception{
		Map< String, Object> result = new HashMap<String, Object>();
		//edit
		try {
			Product product = ProductManagementAssembler.transferVO2ProductModel(vo);
			Long productId=product.getId();
			ProductVO productVO = ProductAssembler.transferModel2VO(product);
			
			List<ProductDetail> productDetailList  = ProductManagementAssembler.transferVO2ProductDetailList(vo);
			
			List<BaseAreaProduct> baseAreaProductList  = ProductManagementAssembler.transferVO2BaseAreaProductList(vo);
			if(productVO != null){
				productService.update(productVO);
			}
			for(ProductDetail productDetail: productDetailList){
				ProductDetailVO productDetailVO =  ProductDetailAssembler.transferModel2VO(productDetail);
				//before update need retrieve product detail id
				ProductDetailVO oldProdDetail = new ProductDetailVO();
				oldProdDetail.setProductId(productId);
				oldProdDetail.setCarProductType(productDetailVO.getCarProductType());
				oldProdDetail.setTerm(productDetailVO.getTerm());
				ProductDetail newProdDetail = productDetailService.getProductDetailByVO(oldProdDetail);
				//get productDetail id
				productDetailVO.setId(newProdDetail.getId());
				productDetailService.update(productDetailVO);
			}
			//delete old records
			baseAreaProductService.deleteByProductId(productId);
			//insert new record
			for(BaseAreaProduct baseAreaProduct: baseAreaProductList){
				baseAreaProduct.setProductId(productId.intValue());
				baseAreaProductService.insert(baseAreaProduct);
			}
			Integer status = product.getStatus();
			BusinessLog businessLog  = new BusinessLog();
			businessLog.setLoanId(-1L);
			businessLog.setMessage("[编辑产品][产品名称:" + product.getProductName() + "]" + "[更改产品状态"
									+ status + "]");
			if(EnumConstants.ProductStatus.VALID.getValue().equals(status)){
				businessLog.setFlowStatus(92);
			}else{
				businessLog.setFlowStatus(93);
			}
			businessLogService.insert(businessLog);		
			result.put("success", true);
		} catch (Exception e) {
			logger.error("产品编辑异常", e);
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		return result;
	}
	
	@RequestMapping("/productSave")
	@ResponseBody
	@Transactional
	public Map<String, Object> productSave(ProductManagementVO vo) throws Exception{
		Map< String, Object> result = new HashMap<String, Object>();
		//save
		try {
			boolean exist = false;
			Product product = ProductManagementAssembler.transferVO2ProductModel(vo);
			List<ProductDetail> productDetailList  = ProductManagementAssembler.transferVO2ProductDetailList(vo);
			List<BaseAreaProduct> baseAreaProductList  = ProductManagementAssembler.transferVO2BaseAreaProductList(vo);
			Product productNew = null;
			if(product != null){
				exist = productService.existsByProductName(product.getProductName());
				if(exist){
					result.put("success", false);
					result.put("msg", "系统已存在相应的产品名称");
					return result;
				}
				productNew = productService.insert(product);
			}
			for(ProductDetail productDetail: productDetailList){
				productDetail.setProductId(productNew.getId());
				productDetailService.insert(productDetail);
			}
			for(BaseAreaProduct baseAreaProduct: baseAreaProductList){
				baseAreaProduct.setProductId(productNew.getId().intValue());
				baseAreaProductService.insert(baseAreaProduct);
			}
			BusinessLog businessLog  = new BusinessLog();
			businessLog.setLoanId(-1L);
			businessLog.setMessage("[新加产品][产品名称:"+product.getProductName()+"]");
			businessLog.setFlowStatus(91);
			businessLogService.insert(businessLog);		
			result.put("success", true);
		} catch (Exception e) {
			logger.error("产品添加异常", e);
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		return result;
	}
	
	@RequestMapping("/deleteProduct")
	@ResponseBody
	@Transactional
	public Map<String, Object> deleteProduct(Long productId) throws Exception{
		Map< String, Object> result = new HashMap<String, Object>();
		//delete
		try {
			productService.deleteByIdLogically(productId);
			BaseAreaProductVO  baseAreaProductVO = new BaseAreaProductVO();
			baseAreaProductVO.setProductId(productId.intValue());
			List<BaseAreaProduct> baseAreaProductList = baseAreaProductService.findListByVO(baseAreaProductVO);
			for(BaseAreaProduct baseAreaProduct: baseAreaProductList){
				baseAreaProductService.deleteById(baseAreaProduct.getId());
			}
			BusinessLog businessLog  = new BusinessLog();
			businessLog.setLoanId(-1L);
			businessLog.setMessage("[删除产品][产品ID:"+productId+"]");
			businessLog.setFlowStatus(94);
			businessLogService.insert(businessLog);		
			result.put("success", true);
		} catch (Exception e) {
			logger.error("产品添加异常", e);
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		return result;
	}

}
