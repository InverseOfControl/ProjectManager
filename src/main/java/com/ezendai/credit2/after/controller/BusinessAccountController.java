/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.after.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezendai.credit2.after.dao.BusinessAccountDao;
import com.ezendai.credit2.after.model.BusinessAccount;
import com.ezendai.credit2.after.service.BusinessAccountService;
import com.ezendai.credit2.after.service.BusinessAccountUnifieDataService;
import com.ezendai.credit2.after.vo.BusinessAccountVO;
import com.ezendai.credit2.apply.model.ChannelPlanCheck;
import com.ezendai.credit2.apply.model.Extension;
import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.model.Person;
import com.ezendai.credit2.apply.model.Product;
import com.ezendai.credit2.apply.service.ExtensionService;
import com.ezendai.credit2.apply.service.LoanService;
import com.ezendai.credit2.apply.service.PersonService;
import com.ezendai.credit2.apply.service.ProductService;
import com.ezendai.credit2.apply.vo.LoanDetailsVO;
import com.ezendai.credit2.apply.vo.LoanVO;
import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.framework.exception.BusinessException;
import com.ezendai.credit2.framework.exception.ExcelException;
import com.ezendai.credit2.framework.util.CollectionUtil;
import com.ezendai.credit2.framework.util.CommonUtil;
import com.ezendai.credit2.framework.util.DateUtil;
import com.ezendai.credit2.framework.util.FileUtil;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.framework.util.StringUtil;
import com.ezendai.credit2.master.constant.BizConstants;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.master.model.SpecBusinessLog;
import com.ezendai.credit2.master.service.AreaService;
import com.ezendai.credit2.master.service.SpecBusinessLogService;
import com.ezendai.credit2.master.vo.BaseAreaVO;
import com.ezendai.credit2.master.vo.SpecBusinessLogVO;
import com.ezendai.credit2.system.Credit2Properties;
import com.ezendai.credit2.system.controller.BaseController;
import com.ezendai.credit2.system.enumerate.SysParameterEnum;
import com.ezendai.credit2.system.model.SysLog;
import com.ezendai.credit2.system.model.SysParameter;
import com.ezendai.credit2.system.model.SysUser;
import com.ezendai.credit2.system.service.SysLogService;
import com.ezendai.credit2.system.service.SysParameterService;
import com.ezendai.credit2.system.service.SysUserService;
import com.ezendai.credit2.system.vo.SysParameterVO;

/**
 * <pre>
 * 对公还款
 * </pre>
 *
 * @author 陈忠星
 * @version $Id: BusinessAccount.java, v 0.1 2014年12月15日 上午9:39:29 00219930 Exp $
 */
@Controller
@RequestMapping("/after/businessAccount")
public class BusinessAccountController extends BaseController {
	
	/**
	 *区域service
	 */
	@Autowired
	private AreaService areaService;

	@Autowired
	private BusinessAccountService businessAccountService;
	@Autowired
	private BusinessAccountUnifieDataService businessAccountUnifieDataService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private ProductService productService;
	@Autowired
	private SysParameterService sysParameterService;
	@Autowired
	private SpecBusinessLogService specBusinessLogService;
	@Autowired
	private SysLogService sysLogService;
	@Autowired
	private Credit2Properties credit2Properties;
	@Autowired
	private LoanService loanService;
	@Autowired
	private ExtensionService extensionService;
	@Autowired
	private PersonService personService;
	@Autowired
	private BusinessAccountDao businessAccountDao;


	private static final String NOT_RECEIVE_TIME = "当前时间不可领取， 领取时间为每天9:00--16:30";

	private static final String UNDO_TIME = "当前时间不可撤销， 撤销时间为每天9:00--16:30";
	
	private static final String NOT_EXPORT_TIME = "对公领取导出时间须在下午四点半以后";
	
	private static final String NOT_RECEIVE_TIME_NEW = "当前时间不可领取， 领取时间为每天";

	private static final String UNDO_TIME_NEW = "当前时间不可撤销， 撤销时间为每天";

	private static final String NOT_EXPORT_TIME_NEW = "对公领取导出时间须在";

	private static final String RECEIVE = "已经被领取";

	private static final Logger logger = Logger.getLogger(BusinessAccountController.class);

	@RequestMapping("/list")
	public String init(HttpServletRequest request) {
		//查找用户类型
		Integer userType = ApplicationContext.getUser().getUserType();
		Long loginUserId = ApplicationContext.getUser().getId();
		
		SysUser sysUser = sysUserService.get(loginUserId);
		if (sysUser == null)
			return null;
		request.setAttribute("userType", userType);
		request.setAttribute("loginUserId", loginUserId);

		//经理副理的的产品类型和营业网点
		if (EnumConstants.UserType.STORE_MANAGER.getValue().equals(userType) || EnumConstants.UserType.STORE_ASSISTANT_MANAGER.getValue().equals(userType)) {
			List<Product> productsList = productService.findProductTypeByUserId(loginUserId);
			List<Long> userSalesDeptList = sysUserService.getSalesDeptIdByUserId(loginUserId);
			BaseAreaVO baseAreaVo = new BaseAreaVO();
			if(EnumConstants.IsAddOtherDepts.isTrue.getValue().equals(String.valueOf(sysUser.getIsAddOtherDepts()))){
				baseAreaVo.setUserId(loginUserId);
				baseAreaVo.setIdentifier(BizConstants.CREDIT2_SALESDEPARTMENT);
				userSalesDeptList = null;
				//获取所有的营业网点ID
				userSalesDeptList = areaService.getDeptsByUserIdAndDeptsTypes(baseAreaVo);
			}
			//所属的产品类型
			if (CollectionUtil.isNotEmpty(productsList)) {
				request.setAttribute("productType", productsList.get(0).getProductType());
			}
			// 所在的营业网点
			request.setAttribute("salesDept", String.valueOf(userSalesDeptList));
		} else {
			request.setAttribute("productType", "productType");
			request.setAttribute("salesDept", "salesDept");
		}

		// 设置数据字典 
		setDataDictionaryArr(new String[] { EnumConstants.LOAN_STATUS, EnumConstants.BUSINESS_ACCOUNT_STATUS, EnumConstants.BUSINESS_ACCOUNT_FLOW_STATUS, EnumConstants.BORROW_OR_LOAN ,EnumConstants.PRODUCT_ID});
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		return "/after/businessAccount/businessAccountList";
	}
	
	
	/**
	 * 
	 * <pre>
	 * 获取领取时间
	 * </pre>
	 *
	 * @param id
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getRecTime")
	@ResponseBody
	public Map getRecTime() {
		Map map = new HashMap();
		String msg = "";
		boolean isSuccess = false;
		try {
			String start="";
			String end="";
			SysParameter receiveTimeStart = sysParameterService.getByCode(SysParameterEnum.BIZ_ACCT_RECEIVE_TIME.name());
			if (receiveTimeStart == null || StringUtil.isBlank(receiveTimeStart.getParameterValue())) {
				start="";
				end="";
			}
			start = StringUtil.substringBefore(receiveTimeStart.getParameterValue(), ";");
			end = StringUtil.substringAfter(receiveTimeStart.getParameterValue(), ";");
			BusinessAccountVO businessAccountVO=new BusinessAccountVO();
			businessAccountVO.setRecStartTime(start);
			businessAccountVO.setRecEndTime(end);
			map.put("businessAccount", businessAccountVO);
			msg = "查询成功";
			isSuccess = true;
		} catch (Exception ex) {
			isSuccess = false;
			msg = "初始化失败";
			ex.printStackTrace();
		}
		map.put("isSuccess", isSuccess);
		map.put("msg", msg);
		return map;
	}
	
	
	/**
	 * 
	 * <pre>
	 * 修改领取时间
	 * </pre>
	 *
	 * @param 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/editRecTime")
	@ResponseBody
	public Map editRecTime(BusinessAccountVO businessAccountVO) {
		Map map = new HashMap();
		String msg = "";
		boolean isSuccess = false;
		try {
			if(businessAccountVO!=null && businessAccountVO.getRecStartTime()!=null && businessAccountVO.getRecEndTime()!=null){
				String parameterValue=businessAccountVO.getRecStartTime().trim()+";"+businessAccountVO.getRecEndTime().trim();
				SysParameterVO sysParameterVo=new SysParameterVO();
				sysParameterVo.setCode(SysParameterEnum.BIZ_ACCT_RECEIVE_TIME.name());
				sysParameterVo.setParameterValue(parameterValue);
				sysParameterService.updateByCodeAndRefreshCache(sysParameterVo);
				msg = "修改领取时间成功";
				isSuccess = true;
			}else{
				isSuccess = false;
				msg = "修改领取时间失败";
			}
		} catch (Exception ex) {
			isSuccess = false;
			msg = "初始化失败";
			ex.printStackTrace();
		}
		map.put("isSuccess", isSuccess);
		map.put("msg", msg);
		return map;
	}
	
	
	
	/**
	 * 
	 * <pre>
	 * 对公还款信息
	 * </pre>
	 *
	 * @param id
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getBusinessAccountById")
	@ResponseBody
	public Map getBusinessAccountById(long id) {
		Map map = new HashMap();
		String msg = "";
		boolean isSuccess = false;
		try {
			BusinessAccount businessAccount=businessAccountService.getById(id);
			 if (businessAccount != null) {
				map.put("businessAccount", businessAccount);
				msg = "查询成功";
				isSuccess = true;
			} else {
				isSuccess = false;
				msg = "初始化失败";
			}
		} catch (Exception ex) {
			isSuccess = false;
			msg = "初始化失败";
			ex.printStackTrace();
		}
		map.put("isSuccess", isSuccess);
		map.put("msg", msg);
		return map;
	}
	
	
	/**
	 * 
	 * <pre>
	 * 对公还款信息编辑
	 * </pre>
	 *
	 * @param businessAccountVO
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/editBusinessAccount")
	@ResponseBody
	public Map editBusinessAccount(BusinessAccountVO businessAccountVO) {
		Map map = new HashMap();
		String msg = "";
		boolean isSuccess = false;
		try {
			if(businessAccountVO!=null){
				if(businessAccountVO.getStatus()!=null && businessAccountVO.getStatus().compareTo(EnumConstants.BusinessAccountStatus.NORECEIVE.getValue())==0){
					businessAccountVO.setRecOperatorIdIsNull(true);
				}else{
					businessAccountVO.setRecOperatorIdIsNull(false);
				}
				int i=businessAccountService.updateBusinessAccountStatus(businessAccountVO);
				if (i == 1) {
					msg = "修改成功";
					isSuccess = true;
				} else {
					isSuccess = false;
					msg = "修改失败";
				}
			}else {
				isSuccess = false;
				msg = "修改失败";
			}
		} catch (Exception ex) {
			isSuccess = false;
			msg = "修改失败";
			ex.printStackTrace();
		}
		map.put("isSuccess", isSuccess);
		map.put("msg", msg);
		return map;
	}
	
	
	/**
	 * 
	 * <pre>
	 * 删除对公还款信息
	 * </pre>
	 *
	 * @param id
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/deleteBusinessAccountById")
	@ResponseBody
	public Map deleteBusinessAccountById(long id) {
		Map map = new HashMap();
		String msg = "";
		boolean isSuccess = false;
		try {
			businessAccountService.deleteById(id);
			msg = "删除成功";
			isSuccess = true;
		} catch (Exception ex) {
			isSuccess = false;
			msg = "删除失败";
			ex.printStackTrace();
		}
		map.put("isSuccess", isSuccess);
		map.put("msg", msg);
		return map;
	}

	/**
	 * 
	 * <pre>
	 * 对公还款列表
	 * </pre>
	 *
	 * @param businessAccountVo
	 * @param rows
	 * @param page
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getBusinessAccountPage")
	@ResponseBody
	public Map getBusinessAccountPage(BusinessAccountVO businessAccountVo, int rows, int page) {

		//默认状态为未认领
		if (businessAccountVo.getStatus() == null) {
			businessAccountVo.setStatus(EnumConstants.BusinessAccountStatus.NORECEIVE.getValue());
		}
		//状态选择全部
		if (businessAccountVo.getStatus() != null && businessAccountVo.getStatus() == 0) {
			businessAccountVo.setStatus(null);
		}
		if (businessAccountVo.getRecOperatorNo() != null) {
			SysUser sysUser = sysUserService.getSysUserByLoginName(businessAccountVo.getRecOperatorNo());
			if(sysUser!=null)
			{
				businessAccountVo.setRecOperatorId(sysUser.getId());
			}
		}
		Pager p = new Pager();
		p.setPage(page);
		p.setRows(rows);
		p.setSidx("REPAY_DATE  DESC,  REPAY_TIME");
		p.setSort("DESC");
		businessAccountVo.setPager(p);

		Pager pager = businessAccountService.findWithPg(businessAccountVo);
		List<BusinessAccount> businessAccountList = pager.getResultList();

		for (BusinessAccount businessAccount : businessAccountList) {
			if (businessAccount.getRecOperatorId() != null) {
				SysUser sysUser = sysUserService.get(businessAccount.getRecOperatorId());
				businessAccount.setSysUser(sysUser);
			}
			if (businessAccount.getLoanId() != null) {
				Loan loan = loanService.get(businessAccount.getLoanId());
				businessAccount.setLoan(loan);
				if(loan==null)
				{
					Loan loanNew=new Loan();
					Extension extension = extensionService.get(businessAccount.getLoanId());
					loanNew.setProductType(extension.getProductType());
					loanNew.setSalesDeptId(extension.getSalesDeptId());
					businessAccount.setLoan(loanNew);
					Person person = personService.get(extension.getPersonId());
					if(person!=null)
					{
						businessAccount.setPersonName(person.getName());
					}
				}
				else
				{
					Person person = personService.get(loan.getPersonId());
					if(person!=null)
					{
						businessAccount.setPersonName(person.getName());
					}
				}
			
				
			}
		}
		Map<String, Object> result = new LinkedHashMap<String, Object>();

		result.put("total", pager.getTotalCount());
		result.put("rows", businessAccountList);
		return result;

	}

	/**
	 * 
	 * <pre>
	 * 领取借款列表
	 * </pre>
	 *
	 * @param loanVo
	 * @param rows
	 * @param page
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getReceivePage")
	@ResponseBody
	public Map getReceivePage(LoanVO loanVo, Integer rows, Integer page) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		
		Long userId = ApplicationContext.getUser().getId();
		List<Product> productsList = productService.findProductTypeByUserId(userId);
		List<Integer> productTypes = new ArrayList<Integer>();
		SysUser sysUser = sysUserService.get(userId);
		Integer userType = ApplicationContext.getUser().getUserType();

		if (userType.equals(EnumConstants.UserType.CUSTOMER_SERVICE.getValue()) || userType.equals(EnumConstants.UserType.STORE_ASSISTANT_MANAGER.getValue())
			|| userType.equals(EnumConstants.UserType.STORE_MANAGER.getValue())) {// 客服 ,经理，副理
			if (CollectionUtil.isNotEmpty(productsList)) {
				for (Product product : productsList) {
					productTypes.add(product.getProductType());// 添加该用户所属的产品类型
				}
				loanVo.setProductTypeList(productTypes);
			}
			
			List<Long> canBrowseSalesDeptList = sysUserService.getSalesDeptIdByUserId(userId);
		    
		    if(EnumConstants.IsAddOtherDepts.isTrue.getValue().equals(String.valueOf(sysUser.getIsAddOtherDepts()))){
		    	BaseAreaVO baseAreaVo = new BaseAreaVO();
				baseAreaVo.setUserId(userId);
				baseAreaVo.setIdentifier(BizConstants.CREDIT2_SALESDEPARTMENT);
				canBrowseSalesDeptList = null;
				//获取所有的营业网点ID
				canBrowseSalesDeptList = areaService.getDeptsByUserIdAndDeptsTypes(baseAreaVo);
			}
		    // 所在的营业网点
		    loanVo.setSalesDeptIdList(canBrowseSalesDeptList);
		    
		    
		} else if (sysUser.getUserType().equals(EnumConstants.UserType.SYSTEM_ADMIN.getValue())) {//系统管理员
			loanVo.setSalesDeptId(null);
		}
		//判断是否展期
		if (loanVo.getExtensionTime() != null && loanVo.getExtensionTime().compareTo(0) > 0) {
			loanVo.setIsExtension(1);
		}
		List<Integer> statusList = new ArrayList<Integer>();
		statusList.add(EnumConstants.LoanStatus.正常.getValue());
		statusList.add(EnumConstants.LoanStatus.逾期.getValue());

		loanVo.setStatusList(statusList);
		Pager p = new Pager();
		p.setPage(page);
		p.setRows(rows);
		p.setSidx("ID");
		p.setSort("DESC");
		loanVo.setPager(p);

		Pager pager = businessAccountService.findBusinessLoanListWithPg(loanVo);
		List<Loan> loanList = pager.getResultList();

		result.put("total", pager.getTotalCount());
		result.put("rows", loanList);
		return result;

	}

	/***
	 * 
	 * <pre>
	 * 跳转到认领列表
	 * </pre>
	 *
	 * @param businessAccountVo
	 * @return
	 */
	@RequestMapping("/toReceiveList")
	@ResponseBody
	public String toReceiveList(BusinessAccountVO businessAccountVo) {
		if (checkReceiveTime()) {
//			return NOT_RECEIVE_TIME;
			String start="";
			String end="";
			SysParameter receiveTimeStart = sysParameterService.getByCode(SysParameterEnum.BIZ_ACCT_RECEIVE_TIME.name());
			if (receiveTimeStart == null || StringUtil.isBlank(receiveTimeStart.getParameterValue())) {
				start="";
				end="";
			}
			start = StringUtil.substringBefore(receiveTimeStart.getParameterValue(), ";");
			end = StringUtil.substringAfter(receiveTimeStart.getParameterValue(), ";");
			return NOT_RECEIVE_TIME_NEW+start+"--"+end;
		}
		if (businessAccountService.receive(businessAccountVo.getId())) {
			return "success";
		} else {
			return RECEIVE;
		}
	}
	
	

	/***
	 * 
	 * <pre>
	 * 日志列表
	 * </pre>
	 *
	 * @param loanVo
	 * @param rows
	 * @param page
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getLogsPage")
	@ResponseBody
	public Map getLogsPage(SpecBusinessLogVO specBusinessLogVo, Integer rows, Integer page) {

		Map<String, Object> result = new LinkedHashMap<String, Object>();

		Pager p = new Pager();
		p.setPage(page);
		p.setRows(rows);
		p.setSidx("SPEC_BUSINESS_LOG.ID");
		p.setSort("DESC");
		specBusinessLogVo.setPager(p);
		//类型为对公还款
		specBusinessLogVo.setKeyType(EnumConstants.SpecBusinessLogStatus.BUSINESS_ACCOUNT.getValue());
		
		Pager pager = businessAccountService.findBusinessLogsListWithPg(specBusinessLogVo);
		List<SpecBusinessLog> specBusinessLogList = pager.getResultList();
		for(SpecBusinessLog specBusinessLog:specBusinessLogList){
			if(specBusinessLog.getMessage().equals("已导出")){
				specBusinessLog.setCreator("财务");
			}
		}
		result.put("total", pager.getTotalCount());
		result.put("rows", specBusinessLogList);
		return result;
	}

	/***
	 * 
	 * <pre>
	 * 跳转到小企业贷款详细信息页面
	 * </pre>
	 *
	 * @param businessAccountVo
	 * @return
	 */
	@RequestMapping("/toBusinessLoanDetail")
	public String toBusinessLoanDetail() {
		return "after/businessAccount/businessLoanDetail";
	}
	/**
	 * 同城贷产品详细页面
	 * 
	 */
	@RequestMapping("/towsLoanDetail")
	public String towsLoanDetail() {
		return "after/businessAccount/wsLoanDetail";
	}
	/**
	 * 同城贷产品详细页面
	 * 
	 */
	@RequestMapping("/toCityWidBusinessLoanDetail")
	public String toCityWidBusinessLoanDetail() {
		return "after/businessAccount/cityWideBusinessLoanDetail";
	}
	
	/**
	 * 助学贷产品详细页面
	 * 
	 */
	@RequestMapping("/toEduLoanDetail")
	public String toEduLoanDetail() {
		return "after/businessAccount/eduLoanDetail";
	}
	
	/***
	 * 
	 * <pre>
	 * 跳转到车贷贷款详细信息页面
	 * </pre>
	 *
	 * @param businessAccountVo
	 * @return
	 */
	@RequestMapping("/toCarLoanDetail")
	public String toCarLoanDetail() {
		return "after/businessAccount/carLoanDetail";
	}

	/***
	 * 
	 * <pre>
	 * 跳转到车贷贷款详细信息页面
	 * </pre>
	 *
	 * @param businessAccountVo
	 * @return
	 */
	@RequestMapping("/toCarLoanExtensionDetail")
	public String toCarLoanExtensionDetail() {
		return "after/businessAccount/carLoanExtensionDetail";
	}

	/***
	 * 
	 * <pre>
	 * 显示车贷贷款信息
	 * </pre>
	 *
	 * @param businessAccountVo
	 * @return
	 */
	@RequestMapping("/carLoanDetail")
	@ResponseBody
	public LoanDetailsVO carLoanDetail(Long loanId, String flag) {
		return businessAccountService.toCarLoanDetail(loanId, flag);
	}

	/***
	 * 
	 * <pre>
	 * 显示小企业贷款
	 * </pre>
	 *
	 * @param businessAccountVo
	 * @return
	 */
	@RequestMapping("/businessLoanDetail")
	@ResponseBody
	public LoanDetailsVO businessLoanDetail(Long loanId, String flag) {
		return businessAccountService.toBusinessLoanDetail(loanId, flag);
	}

	/***
	 * 
	 * <pre>
	 * 显示车贷展期贷款信息
	 * </pre>
	 *
	 * @param businessAccountVo
	 * @return
	 */
	@RequestMapping("/carLoanExtensionDetail")
	@ResponseBody
	public LoanDetailsVO carLoanExtensionDetail(Long loanId) {
		return businessAccountService.toCarLoanExtensionDetail(loanId);
	}

	/***
	 * 
	 * <pre>
	 * 客服撤销操作
	 * </pre>
	 *
	 * @param businessAccountVo
	 * @return
	 */
	@RequestMapping("/undo")
	@ResponseBody
	public String undo(BusinessAccountVO businessAccountVo) {
		if (checkReceiveTime()) {
//			return UNDO_TIME;
			String start="";
			String end="";
			SysParameter receiveTimeStart = sysParameterService.getByCode(SysParameterEnum.BIZ_ACCT_RECEIVE_TIME.name());
			if (receiveTimeStart == null || StringUtil.isBlank(receiveTimeStart.getParameterValue())) {
				start="";
				end="";
			}
			start = StringUtil.substringBefore(receiveTimeStart.getParameterValue(), ";");
			end = StringUtil.substringAfter(receiveTimeStart.getParameterValue(), ";");
			return UNDO_TIME_NEW+start+"--"+end;
		}
		// 清空 领取人ID，loanId  领取时间
		businessAccountVo.setRecOperatorIdIsNull(true);
		//String result = businessAccountService.confirmReceiveCancel(businessAccountVo);
		String result = businessAccountUnifieDataService.confirmReceiveCancel(businessAccountVo);
		if (result.equals("success")) {
			return "success";
		} else {
			return result;
		}

	}

	/***
	 * 
	 * <pre>
	 * 财务撤销操作
	 * </pre>
	 *
	 * @param businessAccountVo
	 * @return
	 */
	@RequestMapping("/financeUndo")
	@ResponseBody
	public String financeUndo(BusinessAccountVO businessAccountVo) {
		//String result = businessAccountService.withoutClaimCancel(businessAccountVo);
		String result = businessAccountUnifieDataService.withoutClaimCancel(businessAccountVo);
		if (result.equals("success")) {
			return "success";
		} else {
			return result;
		}

	}

	/**
	 * 
	 * <pre>
	 * 批量导入
	 * </pre>
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/upload")
	@ResponseBody
	public String upload(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = businessAccountService.batchUpload(request, response);
		String result = resultMap.get("ErrorMsg").toString();
		if (!StringUtils.isEmpty(result)) {
			throw new ExcelException(resultMap.get("ErrorMsg").toString());
		}
		return null;
	}

	/***
	 * 
	 * <pre>
	 * 无需认领
	 * </pre>
	 *
	 * @param businessAccountVo
	 * @return
	 */
	@RequestMapping("/noReceive")
	@ResponseBody
	public String noReceive(BusinessAccountVO businessAccountVo) {
		//String result = businessAccountService.withoutClaim(businessAccountVo);
		String result = businessAccountUnifieDataService.withoutClaim(businessAccountVo);
		if (result.equals("success")) {
			return "success";
		} else {
			return result;
		}
	}

	/***
	 * 
	 * <pre>
	 * 确认领取
	 * </pre>
	 *
	 * @param businessAccountVo
	 * @return
	 */
	@RequestMapping("/receive")
	@ResponseBody
	public String receive(BusinessAccountVO businessAccountVo) {
		if (checkReceiveTime()) {
//			return NOT_RECEIVE_TIME;
			String start="";
			String end="";
			SysParameter receiveTimeStart = sysParameterService.getByCode(SysParameterEnum.BIZ_ACCT_RECEIVE_TIME.name());
			if (receiveTimeStart == null || StringUtil.isBlank(receiveTimeStart.getParameterValue())) {
				start="";
				end="";
			}
			start = StringUtil.substringBefore(receiveTimeStart.getParameterValue(), ";");
			end = StringUtil.substringAfter(receiveTimeStart.getParameterValue(), ";");
			return NOT_RECEIVE_TIME_NEW+start+"--"+end;
		}
		businessAccountVo.setRepayDate(DateUtil.formatDate(businessAccountVo.getRepayDate()));
		//领取人
		businessAccountVo.setRecOperatorId(ApplicationContext.getUser().getId());
		//String result = businessAccountService.confirmReceive(businessAccountVo);
		String result = businessAccountUnifieDataService.confirmReceive(businessAccountVo);
		if (result.equals("success")) {
			return "success";
		} else {
			return result;
		}
	}

	//	/***
	//	 * 
	//	 * <pre>
	//	 * 需要导出excel的条数
	//	 * </pre>
	//	 *
	//	 * @param businessAccountVo
	//	 * @return
	//	 */
	//	@RequestMapping("/findReceiveCount")
	//	@ResponseBody
	//	public String findReceiveCount() {
	//		//检查导出时间
	//		if (checkExportTime()) {
	//			return NOT_EXPORT_TIME;
	//		}
	//
	//		BusinessAccountVO businessAccountVo = new BusinessAccountVO();
	//		businessAccountVo.setStatus(EnumConstants.BusinessAccountStatus.RECEIVE.getValue());
	//		int resultCount = businessAccountService.receiveResultCount(businessAccountVo);
	//		if (resultCount <= 0) {
	//			return NO_CORRESPONDING_RECORD;
	//		} else {
	//			SysParameter sysParameter = sysParameterService.getByCode(SysParameterEnum.EXCEL_EXPORT_MAX_NO.name());
	//			if (sysParameter != null) {
	//				if (resultCount > Integer.parseInt(sysParameter.getParameterValue())) {
	//					return "excel导出条数过多！";
	//				}
	//			}
	//
	//		}
	//		return "success";
	//	}

	/**
	 * 
	 * <pre>
	 * 检查领取时间
	 * </pre>
	 *
	 * @return
	 */
	private boolean checkReceiveTime() {

		SysParameter receiveTimeStart = sysParameterService.getByCode(SysParameterEnum.BIZ_ACCT_RECEIVE_TIME.name());
		if (receiveTimeStart == null || StringUtil.isBlank(receiveTimeStart.getParameterValue())) {
			return false;
		}
		String start = StringUtil.substringBefore(receiveTimeStart.getParameterValue(), ";");
		String end = StringUtil.substringAfter(receiveTimeStart.getParameterValue(), ";");

		int starthour = Integer.parseInt(StringUtil.substringBefore(start, ":"));
		int startMinute = Integer.parseInt(StringUtil.substringAfter(start, ":"));

		//9:00~16:30 之间才可以领取		
		int endHour = Integer.parseInt(StringUtil.substringBefore(end, ":"));
		int endMinute = Integer.parseInt(StringUtil.substringAfter(end, ":"));

		Calendar dayStart = Calendar.getInstance();
		dayStart.set(Calendar.HOUR_OF_DAY, starthour);
		dayStart.set(Calendar.MINUTE, startMinute);
		dayStart.set(Calendar.SECOND, 00);

		Calendar dayEnd = Calendar.getInstance();
		dayEnd.set(Calendar.HOUR_OF_DAY, endHour);
		dayEnd.set(Calendar.MINUTE, endMinute);
		dayEnd.set(Calendar.SECOND, 00);
		Date nowDate = new Date();
		if (nowDate.after(dayStart.getTime()) && nowDate.before(dayEnd.getTime())) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 
	 * <pre>
	 * 检查导出时间
	 * </pre>
	 *
	 * @return false不在导出时间范围内
	 */
	private boolean checkExportTime() {
		Calendar dayEnd = Calendar.getInstance();
		SysParameter receiveTimeStart = sysParameterService.getByCode(SysParameterEnum.BIZ_ACCT_EXPORT_TIME.name());
		if (receiveTimeStart == null || StringUtil.isBlank(receiveTimeStart.getParameterValue())) {
			return false;
		}
		String end = StringUtil.substringBefore(receiveTimeStart.getParameterValue(), ";");


		//9:00~16:30 之间才可以领取		
		int endHour = Integer.parseInt(StringUtil.substringBefore(end, ":"));
		int endMinute = Integer.parseInt(StringUtil.substringAfter(end, ":"));
		dayEnd.set(Calendar.HOUR_OF_DAY, endHour);
		dayEnd.set(Calendar.MINUTE, endMinute);
		dayEnd.set(Calendar.SECOND, 00);
		if (new Date().before(dayEnd.getTime())) {
			return true;
		} else {
			return false;
		}
	}

	//	/***
	//	 * 导出Excel	
	//	 * @param response
	//	 * @return
	//	 */
	//	@RequestMapping("/exportExcel")
	//	@ResponseBody
	//	@Transactional
	//	public String exportExcel(HttpServletResponse response) {
	//
	//		//检查导出时间
	//		if (checkExportTime()) {
	//			return NOT_EXPORT_TIME;
	//		}
	//
	//		BusinessAccountVO businessAccountVo = new BusinessAccountVO();
	//		int resultCount = businessAccountService.receiveResultCount(businessAccountVo);
	//		if (resultCount <= 0) {
	//			return NO_CORRESPONDING_RECORD;
	//		} else {
	//			SysParameter sysParameter = sysParameterService.getByCode(SysParameterEnum.EXCEL_EXPORT_MAX_NO.name());
	//			if (sysParameter != null) {
	//				if (resultCount > Integer.parseInt(sysParameter.getParameterValue())) {
	//					return "excel导出条数过多！";
	//				}
	//			}
	//		}
	//		//已经认领的
	//		businessAccountVo.setStatus(EnumConstants.BusinessAccountStatus.RECEIVE.getValue());
	//		//查询出认领结果
	//		List<BusinessAccount> businessAccountList = businessAccountService.findReceiveResult(businessAccountVo);
	//		//文件名 
	//		String fileName = "认领结果导出" + DateUtil.getDate(new Date(), "yyyy-MM-dd") + ".xlsx";
	//
	//		String downloadPath = credit2Properties.getDownloadPath();
	//		File file = new File(downloadPath + File.separator + "businessAccount");
	//		if (!file.exists()) {//不存在则创建该目录
	//			file.mkdir();
	//		}
	//		OutputStream os;
	//		try {
	//			os = new FileOutputStream(downloadPath + File.separator + "businessAccount" + File.separator + fileName.trim().toString());
	//			//生成Excel文件			
	//			businessAccountService.exportReceiveResult(businessAccountList, "认领结果", os);
	//
	//		} catch (FileNotFoundException e) {
	//			logger.error("生成Excel文件出错", e);
	//			throw new BusinessException("生成Excel文件出错!");
	//		}
	//		//下载Excel文件的路径
	//		String filePath = downloadPath + File.separator + "businessAccount" + File.separator + fileName;
	//		try {
	//			//下载Excel文件到客户端
	//			if (FileUtil.downLoadFile(filePath, response, fileName, "xlsx")) {
	//				//更新为已导出状态
	//				updateBusinessAccount(businessAccountList);
	//				//创建日志
	//				insertLogs(businessAccountList);
	//				//导出成功后删除导出的文件
	//				FileUtil.deleteFile(filePath);
	//
	//				return "success";
	//			} else {
	//				return "下载Excel出错!";
	//			}
	//		} catch (Exception e) {
	//			logger.error("下载Excel出错!", e);
	//			throw new BusinessException("下载Excel出错!");
	//		}
	//
	//	}

	//	//更新为已导出状态
	//	private void updateBusinessAccount(List<BusinessAccount> businessAccountList) {
	//		for (BusinessAccount businessAccount : businessAccountList) {
	//			BusinessAccountVO businessVo = new BusinessAccountVO();
	//			businessVo.setId(businessAccount.getId());
	//			businessVo.setStatus(EnumConstants.BusinessAccountStatus.EXPORT.getValue());
	//			businessAccountService.updateBusinessAccountStatus(businessVo);
	//		}
	//	}
	//
	//	//记录特殊业务日志 记录系统日志
	//	private void insertLogs(List<BusinessAccount> businessAccountList) {
	//		//记录特殊业务日志
	//		for (BusinessAccount businessAccount : businessAccountList) {
	//			SpecBusinessLog specBusinessLog = new SpecBusinessLog();
	//			specBusinessLog.setKeyId(businessAccount.getId());
	//			specBusinessLog.setKeyType(EnumConstants.SpecBusinessLogStatus.BUSINESS_ACCOUNT.getValue());
	//			specBusinessLog.setMessage("已导出");
	//			specBusinessLog.setFlowStatus(EnumConstants.BusinessAccountFlowStatus.EXPORT.getValue());
	//			specBusinessLogService.insert(specBusinessLog);
	//		}
	//		//记录系统日志
	//		SysLog sysLog = new SysLog();
	//		sysLog.setOptModule(EnumConstants.OptionModule.BUSINESS_ACCOUNT.getValue());
	//		sysLog.setOptType(EnumConstants.OptionType.IMPORT_HAS_RECEIVED.getValue());
	//		sysLogService.insert(sysLog);
	//	}

	@ResponseBody
	@RequestMapping(value = "/updateBusinessAccountStatus", method = RequestMethod.POST)
	public String updateBusinessAccountStatus(HttpServletRequest request, HttpServletResponse response) {
		String result = "";
		try {
			logger.info("secondCompany:"+request.getParameter("secondCompany"));
			boolean ipValidationCheck = sysParameterService.ipValidationCheck(SysParameterEnum.CREDIT_IP.name(), CommonUtil.getIp(request));
			//判断发起请求的地址,防止恶意请求
			if (!ipValidationCheck) {
				return "Request IP Address is wrong";
			}
			logger.info("updateBusinessAccountStatus repayDate:" + request.getParameter("repayDate")+ ",repayTime:" +request.getParameter("repayTime") + ",secondCompany:"
					+ URLDecoder.decode(request.getParameter("secondCompany"), "utf-8") + ",amount:" +request.getParameter("amount")+ ",type:" +request.getParameter("type"));

			//result = businessAccountService.updateBusinessAccountStatus(request);
			result = businessAccountUnifieDataService.updateBusinessAccountStatus(request);
			logger.info("updateBusinessAccountStatus result:" + result);
		} catch (Exception e) {
			logger.error("调用 updateBusinessAccountStatus异常 : ", e);
			if (e != null) {
				result = e.getMessage();
			}
		}

		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/importBusinessData",produces="text/html;charset=UTF-8", method = RequestMethod.POST)
	public String importBusinessData(HttpServletRequest request, HttpServletResponse response) {
		String result = "";
		try {
			boolean ipValidationCheck = sysParameterService.ipValidationCheck(SysParameterEnum.CREDIT_IP.name(), CommonUtil.getIp(request));
			//判断发起请求的地址,防止恶意请求
			if (!ipValidationCheck) {
				result = "发起请求的IP地址错误!";
				response.addHeader("result", URLEncoder.encode(result, "utf-8"));
				return result;
			}
			Map<String, Object> resultMap = businessAccountService.batchUpload(request, response);
			result = resultMap.get("ErrorMsg").toString();
			if (!StringUtils.isEmpty(result)) {
				response.addHeader("result", URLEncoder.encode(result, "utf-8"));
			}
		} catch (Exception e) {
			logger.error("调用 importBusinessData异常 : ", e);
			if (e != null) {
				result = e.getMessage();
			}
		}
		logger.info("importBusinessData result: " + result);

		return result;
	}
	

	@ResponseBody
	@RequestMapping(value = "/exportReceiveData", method = RequestMethod.POST)
	public String exportReceiveData(HttpServletRequest request, HttpServletResponse response) {
		String result = "";
		try {
			boolean ipValidationCheck = sysParameterService.ipValidationCheck(SysParameterEnum.CREDIT_IP.name(), CommonUtil.getIp(request));
			//判断发起请求的地址,防止恶意请求
			if (!ipValidationCheck) {
				result = "发起请求的IP地址错误!";
				response.addHeader("result", URLEncoder.encode(result, "utf-8"));
				return result;
			}
			//检查导出时间
			if (checkExportTime()) {
				result = NOT_EXPORT_TIME;
//				String start="";
//				String end="";
//				SysParameter receiveTimeStart = sysParameterService.getByCode(SysParameterEnum.BIZ_ACCT_RECEIVE_TIME.name());
//				if (receiveTimeStart == null || StringUtil.isBlank(receiveTimeStart.getParameterValue())) {
//					start="";
//					end="";
//				}
//				start = StringUtil.substringBefore(receiveTimeStart.getParameterValue(), ";");
//				end = StringUtil.substringAfter(receiveTimeStart.getParameterValue(), ";");
//				result= NOT_EXPORT_TIME_NEW+end+"以后";
				response.addHeader("result", URLEncoder.encode(result, "utf-8"));
				return result;
			}
			result = businessAccountService.exportReceiveData(request, response);
			if (!StringUtils.isEmpty(result)) {
				response.addHeader("result", URLEncoder.encode(result, "utf-8"));
			}
		} catch (Exception e) {
			logger.error("调用 exportReceiveData异常 : ", e);
			if (e != null) {
				result = e.getMessage();
			}
		}
		logger.info("exportReceiveData result: " + result);
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/exportReceiveDataLocal")
	public String exportReceiveDataLocal(HttpServletRequest request, HttpServletResponse response) {
		String result = "";
		try {
//			boolean ipValidationCheck = sysParameterService.ipValidationCheck(SysParameterEnum.CREDIT_IP.name(), CommonUtil.getIp(request));
//			//判断发起请求的地址,防止恶意请求
//			if (!ipValidationCheck) {
//				result = "发起请求的IP地址错误!";
//				response.addHeader("result", URLEncoder.encode(result, "utf-8"));
//				return result;
//			}
			//检查导出时间
			if (checkExportTime()) {
				result = NOT_EXPORT_TIME;
//				String start="";
//				String end="";
//				SysParameter receiveTimeStart = sysParameterService.getByCode(SysParameterEnum.BIZ_ACCT_RECEIVE_TIME.name());
//				if (receiveTimeStart == null || StringUtil.isBlank(receiveTimeStart.getParameterValue())) {
//					start="";
//					end="";
//				}
//				start = StringUtil.substringBefore(receiveTimeStart.getParameterValue(), ";");
//				end = StringUtil.substringAfter(receiveTimeStart.getParameterValue(), ";");
//				result= NOT_EXPORT_TIME_NEW+end+"以后";
				response.addHeader("result", URLEncoder.encode(result, "utf-8"));
				return result;
			}
			
			
			try {
				BusinessAccountVO businessAccountVo = new BusinessAccountVO();
				businessAccountVo.setStatus(EnumConstants.BusinessAccountStatus.RECEIVE.getValue());
				//已经认领的
				List<BusinessAccount> businessAccountList = businessAccountDao.findListByVo(businessAccountVo);
				int resultCount = businessAccountList.size();
				logger.info("resultCount:" + resultCount);
				if (resultCount <= 0) {
					result = "没有已领取导出记录!";
					return result;
				} else {
					SysParameter sysParameter = sysParameterService.getByCode(SysParameterEnum.EXCEL_EXPORT_MAX_NO.name());
					if (sysParameter != null) {
						if (resultCount > Integer.parseInt(sysParameter.getParameterValue())) {
							result = "Excel导出数据过多!";
							return result;
						}
					}
				}

				//文件名 
				String fileName = "认领结果导出" + DateUtil.getDate(new Date(), "yyyy-MM-dd") + ".xlsx";
				logger.info("fileName:" + fileName);
				String downloadPath = credit2Properties.getDownloadPath();
				File file = new File(downloadPath + File.separator + "businessAccount");
				if (!file.exists()) {//不存在则创建该目录
					file.mkdir();
				}
				OutputStream os = new FileOutputStream(downloadPath + File.separator + "businessAccount" + File.separator + fileName.trim().toString());
				//生成Excel文件			
				businessAccountService.exportReceiveResult(businessAccountList, "认领结果", os);
				logger.info("exportReceiveResult finished");
				//下载Excel文件的路径
				String filePath = downloadPath + File.separator + "businessAccount" + File.separator + fileName;
				
				//下载Excel文件到客户端
				if (FileUtil.downLoadFile(filePath, response, fileName, "xlsx")) {
					//更新为已导出状态
					updateBusinessAccount(businessAccountList);
					//创建日志
					insertLogs(businessAccountList);
					//导出成功后删除导出的文件
					FileUtil.deleteFile(filePath);
					result="success";
				} else {
					result = "Excel 下载出错!";
				}

			} catch (Exception ex) {
				logger.error("exportReceiveData :" + ex);
				result = "exportReceiveData Error";
			}
			
		} catch (Exception e) {
			logger.error("调用 exportReceiveData异常 : ", e);
			if (e != null) {
				result = e.getMessage();
			}
		}
		logger.info("exportReceiveData result: " + result);
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/checkExportReceiveDataLocal")
	public String checkExportReceiveDataLocal(HttpServletRequest request, HttpServletResponse response) {
		String result = "";
		try {
//			boolean ipValidationCheck = sysParameterService.ipValidationCheck(SysParameterEnum.CREDIT_IP.name(), CommonUtil.getIp(request));
//			//判断发起请求的地址,防止恶意请求
//			if (!ipValidationCheck) {
//				result = "发起请求的IP地址错误!";
//				response.addHeader("result", URLEncoder.encode(result, "utf-8"));
//				return result;
//			}
			//检查导出时间
			if (checkExportTime()) {
				result = NOT_EXPORT_TIME;
//				String start="";
//				String end="";
//				SysParameter receiveTimeStart = sysParameterService.getByCode(SysParameterEnum.BIZ_ACCT_RECEIVE_TIME.name());
//				if (receiveTimeStart == null || StringUtil.isBlank(receiveTimeStart.getParameterValue())) {
//					start="";
//					end="";
//				}
//				start = StringUtil.substringBefore(receiveTimeStart.getParameterValue(), ";");
//				end = StringUtil.substringAfter(receiveTimeStart.getParameterValue(), ";");
//				result= NOT_EXPORT_TIME_NEW+end+"以后";
				response.addHeader("result", URLEncoder.encode(result, "utf-8"));
				return result;
			}
			
			
			try {
				BusinessAccountVO businessAccountVo = new BusinessAccountVO();
				businessAccountVo.setStatus(EnumConstants.BusinessAccountStatus.RECEIVE.getValue());
				//已经认领的
				List<BusinessAccount> businessAccountList = businessAccountDao.findListByVo(businessAccountVo);
				int resultCount = businessAccountList.size();
				logger.info("resultCount:" + resultCount);
				if (resultCount <= 0) {
					result = "没有已领取导出记录!";
					return result;
				} else {
					SysParameter sysParameter = sysParameterService.getByCode(SysParameterEnum.EXCEL_EXPORT_MAX_NO.name());
					if (sysParameter != null) {
						if (resultCount > Integer.parseInt(sysParameter.getParameterValue())) {
							result = "Excel导出数据过多!";
							return result;
						}
					}
				}
				result="success";

			} catch (Exception ex) {
				logger.error("exportReceiveData :" + ex);
				result = "exportReceiveData Error";
			}
		
		} catch (Exception e) {
			logger.error("调用 exportReceiveData异常 : ", e);
			if (e != null) {
				result = e.getMessage();
			}
		}
		logger.info("exportReceiveData result: " + result);
		return result;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}
	

	//更新为已导出状态
	private void updateBusinessAccount(List<BusinessAccount> businessAccountList) {
		for (BusinessAccount businessAccount : businessAccountList) {
			BusinessAccountVO businessVo = new BusinessAccountVO();
			businessVo.setId(businessAccount.getId());
			businessVo.setStatus(EnumConstants.BusinessAccountStatus.EXPORT.getValue());
			businessAccountService.updateBusinessAccountStatus(businessVo);
		}
	}

	//记录特殊业务日志 记录系统日志
	private void insertLogs(List<BusinessAccount> businessAccountList) {
		//记录特殊业务日志
		for (BusinessAccount businessAccount : businessAccountList) {
			SpecBusinessLog specBusinessLog = new SpecBusinessLog();
			specBusinessLog.setKeyId(businessAccount.getId());
			specBusinessLog.setKeyType(EnumConstants.SpecBusinessLogStatus.BUSINESS_ACCOUNT.getValue());
			specBusinessLog.setMessage("已导出");
			specBusinessLog.setFlowStatus(EnumConstants.BusinessAccountFlowStatus.EXPORT.getValue());
			specBusinessLogService.insert(specBusinessLog);
		} 
		//记录系统日志
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.BUSINESS_ACCOUNT.getValue());
		sysLog.setOptType(EnumConstants.OptionType.IMPORT_HAS_RECEIVED.getValue());
		sysLogService.insert(sysLog);
	}
	
}
