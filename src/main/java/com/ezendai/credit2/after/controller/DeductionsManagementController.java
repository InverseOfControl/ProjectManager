package com.ezendai.credit2.after.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.CopyUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.ezendai.credit2.after.expdata.common.TPPConstants;
import com.ezendai.credit2.after.model.Offer;
import com.ezendai.credit2.after.service.DeductionsManagementService;
import com.ezendai.credit2.after.service.OfferService;
import com.ezendai.credit2.after.service.RepayService;
import com.ezendai.credit2.after.service.SpecialRepaymentService;
import com.ezendai.credit2.after.vo.OfferVO;
import com.ezendai.credit2.after.vo.SpecialRepaymentVO;
import com.ezendai.credit2.apply.model.BankAccount;
import com.ezendai.credit2.apply.model.ChannelPlanCheck;
import com.ezendai.credit2.apply.model.Guarantee;
import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.model.Person;
import com.ezendai.credit2.apply.model.Product;
import com.ezendai.credit2.apply.service.BankAccountService;
import com.ezendai.credit2.apply.service.ChannelPlanCheckService;
import com.ezendai.credit2.apply.service.LoanService;
import com.ezendai.credit2.apply.service.PersonService;
import com.ezendai.credit2.apply.service.ProductService;
import com.ezendai.credit2.apply.vo.LoanVO;
import com.ezendai.credit2.apply.vo.ProductVO;
import com.ezendai.credit2.audit.model.PersonBank;
import com.ezendai.credit2.audit.model.RepaymentPlan;
import com.ezendai.credit2.audit.service.PersonBankService;
import com.ezendai.credit2.audit.service.RepaymentPlanService;
import com.ezendai.credit2.audit.vo.PersonBankVO;
import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.framework.exception.BusinessException;
import com.ezendai.credit2.framework.model.UserSession;
import com.ezendai.credit2.framework.util.CollectionUtil;
import com.ezendai.credit2.framework.util.DateUtil;
import com.ezendai.credit2.framework.util.FileUtil;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.framework.util.StringUtil;
import com.ezendai.credit2.master.constant.BizConstants;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.master.enumerate.EnumConstants.system;
import com.ezendai.credit2.master.model.BaseArea;
import com.ezendai.credit2.master.model.OfferManager;
import com.ezendai.credit2.master.model.SysEnumerate;
import com.ezendai.credit2.master.service.AreaService;
import com.ezendai.credit2.master.service.BaseAreaService;
import com.ezendai.credit2.master.service.OfferManagerServcie;
import com.ezendai.credit2.master.service.SysEnumerateService;
import com.ezendai.credit2.master.vo.BaseAreaVO;
import com.ezendai.credit2.system.Credit2Properties;
import com.ezendai.credit2.system.controller.BaseController;
import com.ezendai.credit2.system.enumerate.JobConstants;
import com.ezendai.credit2.system.enumerate.SysParameterEnum;
import com.ezendai.credit2.system.model.SysGroup;
import com.ezendai.credit2.system.model.SysLog;
import com.ezendai.credit2.system.model.SysParameter;
import com.ezendai.credit2.system.model.SysUser;
import com.ezendai.credit2.system.service.SysGroupService;
import com.ezendai.credit2.system.service.SysLogService;
import com.ezendai.credit2.system.service.SysParameterService;
import com.ezendai.credit2.system.service.SysUserService;
import com.ezendai.credit2.system.vo.SysGroupVO;
import com.zendaimoney.thirdpp.common.enums.BizSys;
import com.zendaimoney.thirdpp.common.enums.ThirdType;
import com.zendaimoney.thirdpp.common.vo.Response;
import com.zendaimoney.thirdpp.trade.pub.service.IBrokerTradeService;
import com.zendaimoney.thirdpp.trade.pub.vo.req.biz.CollectReqVo;
import com.zendaimoney.thirdpp.trade.pub.vo.req.biz.RequestVo;

/***
 * 划扣管理
 * @author 陈忠星
 *
 */
@Controller
@RequestMapping("/after/deductionsManagement")
public class DeductionsManagementController extends BaseController {
	/**
	 * 分组service
	 */
	@Autowired
	private SysGroupService sysGroupService;
	/**
	 *区域service
	 */
	@Autowired
	private AreaService areaService;

	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private ProductService productService;
	@Autowired
	private Credit2Properties credit2Properties;
	@Autowired
	private DeductionsManagementService deductionsManagementService;
	@Autowired
	private SysParameterService sysParameterService;
	@Autowired
	private LoanService loanService;
	@Autowired
	private OfferService offerService;
	@Autowired
	private PersonBankService personBankService;
	@Autowired
	private BankAccountService bankAccountService;
	@Autowired
	private RepayService repayService;
	@Autowired
	private ChannelPlanCheckService channelPlanCheckService;
	@Autowired
	private PersonService personService;
	@Autowired
    private IBrokerTradeService brokerTradeConsumer;
	@Autowired
	private SysLogService sysLogService;
	@Autowired
	private SpecialRepaymentService specialRepaymentService;
	@Autowired
	private SysEnumerateService sysEnumerateService;
	@Autowired
	private BaseAreaService baseAreaService;
	@Autowired
	private RepaymentPlanService repaymentPlanService;
	
	
	private static final Logger logger = Logger.getLogger(DeductionsManagementController.class);

	@RequestMapping("/list")
	public String init(HttpServletRequest request) {
		try {
			//查找用户类型
			Long userId = ApplicationContext.getUser().getId();
			UserSession user = ApplicationContext.getUser();
			SysUser sysUser = sysUserService.get(userId);
			request.setAttribute("userType",
					EnumConstants.UserType.FINANCE.getValue());
			List<Product> productsList = productService
					.findProductTypeByUserId(userId);
			if (sysUser.getUserType().equals(
					EnumConstants.UserType.CUSTOMER_SERVICE.getValue())
					|| sysUser.getUserType().equals(
							EnumConstants.UserType.STORE_ASSISTANT_MANAGER
									.getValue())
					|| sysUser.getUserType().equals(
							EnumConstants.UserType.STORE_MANAGER.getValue())) {// 客服6 ，经理2，副理3 

				request.setAttribute("userType",
						EnumConstants.UserType.STORE_ASSISTANT_MANAGER
								.getValue());
				//用户所属的产品类型
				for (Product product : productsList) {
					if (product.getProductType().compareTo(
							EnumConstants.ProductType.SE_LOAN.getValue()) == 0) {
						request.setAttribute("userType",
								EnumConstants.UserType.CUSTOMER_SERVICE
										.getValue());
					}
				}
			} else if (sysUser.getUserType().equals(
					EnumConstants.UserType.AUDIT.getValue())) {//事业部 7

				request.setAttribute("userType",
						EnumConstants.UserType.AUDIT.getValue());

			} else if (sysUser.getUserType().equals(
					EnumConstants.UserType.SYSTEM_ADMIN.getValue())) {

				request.setAttribute("userType",
						EnumConstants.UserType.SYSTEM_ADMIN.getValue());
			}
			//设置code值到request中
			request.setAttribute("code", sysUser.getCode());
			//得到车贷拥有操作实时划扣按钮的员工，从缓存中获取数据
			String carLoanEditNO = sysParameterService.getByCode(
					"CAR_LOAN_EDIT_EMPLNO").getParameterValue();
			//得到企贷拥有操作实时划扣按钮的员工,从缓存中获取数据
			String enterpriseLoanEditNo = sysParameterService.getByCode(
					"ENTERPRISE_LOAN_EDIT_EMPLNO").getParameterValue();
			boolean ifEditCarLoan = false;
			//员工是否拥有操作实时划扣的权限(车贷)
			if (carLoanEditNO != null || enterpriseLoanEditNo != null) {
				//先置为false
				ifEditCarLoan = false;
				if (carLoanEditNO.contains(user.getLoginName())
						|| enterpriseLoanEditNo.contains(user.getLoginName())) {
					ifEditCarLoan = true;
				}
			}
			if (user.getLoginName().equals("admin")) {
				ifEditCarLoan = true;
			}
			request.setAttribute("ifEditCarLoan", ifEditCarLoan);
			//设置生成报盘的开始结束时间 为当天 0时0分0秒到24点  结束时间为当天23:59:59
			request.setAttribute("startDate",
					DateUtil.defaultFormatDay(DateUtil.getToday()));
			request.setAttribute("endDate",
					DateUtil.defaultFormatDay(DateUtil.getToday()));
			//设置参数用来判断用户是否是王丽园所在组/朱雪娇所在组的成员
			SysGroupVO sysGroupVO = new SysGroupVO();
			sysGroupVO.setCode(sysUser.getCode());
			
			List<SysGroup> sysGroupList = sysGroupService.findGroupByUserId(userId);
			request.setAttribute("financeGroup","");
			if(sysGroupList != null && sysGroupList.size()>0){
				for(SysGroup sysgroup : sysGroupList){
					if(sysgroup.getId().equals(EnumConstants.DeductionsPower.isFinanceAuditDraw.getValue())
							||sysgroup.getId().equals(EnumConstants.DeductionsPower.isBusaccountPenalty.getValue())){
						request.setAttribute("financeGroup",sysgroup.getId());
						break;
					}
				}
			}
			
			
			// 设置数据字典 
			setDataDictionaryArr(new String[] { EnumConstants.OFFER_STATE,
					EnumConstants.HAS_GUARANTEE, EnumConstants.PRODUCT_ID,
					EnumConstants.TPP_TYPE });
			request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/after/deductionsManagement/deductionsManagementList";
	}
	
	@RequestMapping("/listImmediately")
	public String initImmediately(HttpServletRequest request) {
		//查找用户类型
//		Long userId = ApplicationContext.getUser().getId();
//		SysUser sysUser = sysUserService.get(userId);
//		request.setAttribute("userType", EnumConstants.UserType.FINANCE.getValue());
//		List<Product> productsList = productService.findProductTypeByUserId(userId);
		//设置生成报盘的开始结束时间 为当天 0时0分0秒到24点  结束时间为当天23:59:59
		request.setAttribute("startDate",DateUtil.defaultFormatDate(DateUtil.getToday()));
		request.setAttribute("endDate", DateUtil.defaultFormatDate(DateUtil.getEndTime()));
		// 设置数据字典 
		setDataDictionaryArr(new String[] { EnumConstants.LOAN_STATUS, 
											EnumConstants.PRODUCT_ID,EnumConstants.REPAYMENT_PLAN_STATE });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		return "/after/deductionsManagement/deductionsImmediately";
	}
	
	/***
	 * 
	 * <pre>
	 * 实时划扣管理查询列表
	 * </pre>
	 *
	 * @param loanVo
	 * @param rows
	 * @param page
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getDeductionsImmediatelyPage")
	@ResponseBody
	public Map getDeductionsImmediatelyPage(LoanVO loanVo, int rows, int page) {
		if(loanVo!=null){
			if(loanVo.getPersonName() !=null && loanVo.getPersonIdnum()!=null && !loanVo.getPersonName().equals("")  && !loanVo.getPersonIdnum().equals("")){
			//查找用户类型
			Long userId = ApplicationContext.getUser().getId();
			List<Product> productsList = productService.findProductsByUserId(userId);
			List<Long> productIds = new ArrayList<Long>();
			SysUser sysUser = sysUserService.get(userId);
			Integer userType = sysUser.getUserType();
			//事业部
			if (EnumConstants.UserType.AUDIT.getValue().equals(userType)) { // 事业部
				if (CollectionUtil.isNotEmpty(productsList)) {
					for (Product product : productsList) {
						productIds.add(product.getId());// 添加该用户所属的产品类型
					}
					loanVo.setProductIdList(productIds);
				}
			}else if (EnumConstants.UserType.CUSTOMER_SERVICE.getValue().equals(userType) 
						||EnumConstants.UserType.STORE_ASSISTANT_MANAGER.getValue().equals(userType)
						||EnumConstants.UserType.STORE_MANAGER.getValue().equals(userType)) {// 客服6 ，经理2，副理3
				List<Long> userSalesDeptList = sysUserService.getSalesDeptIdByUserId(userId);
				//如果是刘娜，则重新获取营业网点ID集合
				// isAddOtherDepts 用于判断该用户是否拥有操作其他营业网点的权限
				  if(EnumConstants.IsAddOtherDepts.isTrue.getValue().equals(String.valueOf(sysUser.getIsAddOtherDepts()))){
					BaseAreaVO baseAreaVo = new BaseAreaVO();
					baseAreaVo.setUserId(userId);
					baseAreaVo.setIdentifier(BizConstants.CREDIT2_SALESDEPARTMENT);
					userSalesDeptList = null;
					//获取所有的营业网点ID
					userSalesDeptList = areaService.getDeptsByUserIdAndDeptsTypes(baseAreaVo);
				}
				
				if (CollectionUtil.isNotEmpty(productsList)) {
					for (Product product : productsList) {
						productIds.add(product.getId());// 添加该用户所属的产品类型
					}
					loanVo.setProductIdList(productIds);
				}
				// 所在的营业网点
				loanVo.setSalesDeptIdList(userSalesDeptList);
			}else if(sysUser.getUserType().equals(EnumConstants.UserType.SYSTEM_ADMIN.getValue()) ){//系统管理员
	//			productIds.add(1L);
	//			productIds.add(2L);
	//			offerVo.setProductIds(productIds);
				loanVo.setSalesDeptId(null);
			}//财务sysUser.getUserType()==8	
			else {
				if (CollectionUtil.isNotEmpty(productsList)) {
					for (Product product : productsList) {
						productIds.add(product.getId());// 添加该用户所属的产品类型
					}
					loanVo.setProductIdList(productIds);
				}
			}
			if (loanVo.getStatus() != null && loanVo.getStatus() == 0) {//全部
				List<Integer> statusList=new ArrayList<Integer>();
				statusList.add(EnumConstants.LoanStatus.正常.getValue());
				statusList.add(EnumConstants.LoanStatus.逾期.getValue());
				loanVo.setStatusList(statusList);
			} else if (loanVo.getStatus() == null) {//默认为未报盘
				List<Integer> statusList=new ArrayList<Integer>();
				statusList.add(EnumConstants.LoanStatus.正常.getValue());
				statusList.add(EnumConstants.LoanStatus.逾期.getValue());
				loanVo.setStatusList(statusList);
			}
			
//			if (loanVo.getCreatedTimeStart() == null) {//开始时间默认为当天00:00:00			
//				loanVo.setCreatedTimeStart(DateUtil.getToday());
//			}else{
//				loanVo.setCreatedTimeStart(DateUtil.formatDate(loanVo.getCreatedTimeStart()));
//			}
//			if(loanVo.getC() == null){//结束时间默认为当天23:59:59						
//				loanVo.setCreatedTimeEnd(DateUtil.getEndTime());
//			}else{
//				loanVo.setCreatedTimeEnd(DateUtil.getEndTime(loanVo.getCreatedTimeStart()));
//			}
//			if(loanVo.getSendDateStart()!=null){
//				loanVo.setSendDateStart(DateUtil.formatDate(loanVo.getSendDateStart()));
//			}
//			if(loanVo.getSendDateEnd()!=null){
//				loanVo.setSendDateEnd(DateUtil.getEndTime(loanVo.getSendDateEnd()));
//			}
			Pager p = new Pager();
			p.setPage(page);
			p.setRows(rows);
			p.setSidx("ID");
			p.setSort("DESC");
			loanVo.setPager(p);
			
			Pager pager = loanService.findWithPGUnionExtension(loanVo);
			List<Loan> loans = pager.getResultList();
		    
			
			List<ListedLoan> loanList = new ArrayList<ListedLoan>();
			
			for (Loan loan : loans) {
				OfferVO of=new OfferVO();
				of.setLoanId(loan.getId());
				String Info=getLoanIdOfferCount(of);
				String operations = null;
				if (loanService.canApprove(loan, userId))
					operations = "签批";
				if (loanService.canExtensionApprove(loan))
					operations = "展期签批";
				ProductVO productVO = new ProductVO();
				productVO.setId(loan.getProductId());
				Product product = productService.findListByVO(productVO).get(0);
				loanList.add(new ListedLoan(loan.getId(),loan.getLoanId(), loan.getStatus(), loan.getRequestDate(), loan.getPerson().getId(), loan.getPerson().getPersonNo(), loan.getPerson().getName(), loan.getPerson()
					.getIdnum(), loan.getProductType(),loan.getProductId(),loan.getPactMoney(),loan.getTime(), loan.getSalesDept().getId(), loan.getSalesDept().getName(), loan.getSubmitDate(), (operations != null ? operations.toString() : null), loan
					.getService().getName(), loan.getCrm().getName(), loan.getAssessor().getName(), loan.getRequestMoney(),loan.getExtensionTime(),product.getProductCode(),loan.getAuditMoney(),product.getProductName(),loan.getPerson()));
			}
			
			
			Map<String, Object> result = new LinkedHashMap<String, Object>();
			result.put("total", pager.getTotalCount());
			result.put("rows", loanList);
			return result;
			}else{
				Map<String, Object> result = new LinkedHashMap<String, Object>();
				result.put("total", 0);
				result.put("rows", new ArrayList());
				return result;
			}
		}
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("total", 0);
		result.put("rows",  new ArrayList());
		return result;
	}
	
	/***
	 * 
	 * <pre>
	 * 实时划扣管理查询列表(批量)
	 * </pre>
	 *
	 * @param loanVo
	 * @param rows
	 * @param page
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getPlDeductionsImmediatelyPage")
	@ResponseBody
	public Map getPlDeductionsImmediatelyPage(LoanVO loanVo, int rows, int page) {
		/*if(loanVo!=null){
			if(loanVo.getPersonName() !=null && loanVo.getPersonIdnum()!=null && !loanVo.getPersonName().equals("")  && !loanVo.getPersonIdnum().equals("")){*/
			//查找用户类型
			Long userId = ApplicationContext.getUser().getId();
			List<Product> productsList = productService.findProductsByUserId(userId);
			List<Long> productIds = new ArrayList<Long>();
			SysUser sysUser = sysUserService.get(userId);
			Integer userType = sysUser.getUserType();
			//事业部
			if (EnumConstants.UserType.AUDIT.getValue().equals(userType)) { // 事业部
				if (CollectionUtil.isNotEmpty(productsList)) {
					for (Product product : productsList) {
						productIds.add(product.getId());// 添加该用户所属的产品类型
					}
					loanVo.setProductIdList(productIds);
				}
			}else if (EnumConstants.UserType.CUSTOMER_SERVICE.getValue().equals(userType) 
						||EnumConstants.UserType.STORE_ASSISTANT_MANAGER.getValue().equals(userType)
						||EnumConstants.UserType.STORE_MANAGER.getValue().equals(userType)) {// 客服6 ，经理2，副理3
				List<Long> userSalesDeptList = sysUserService.getSalesDeptIdByUserId(userId);
				//如果是刘娜，则重新获取营业网点ID集合
				// isAddOtherDepts 用于判断该用户是否拥有操作其他营业网点的权限
				  if(EnumConstants.IsAddOtherDepts.isTrue.getValue().equals(String.valueOf(sysUser.getIsAddOtherDepts()))){
					BaseAreaVO baseAreaVo = new BaseAreaVO();
					baseAreaVo.setUserId(userId);
					baseAreaVo.setIdentifier(BizConstants.CREDIT2_SALESDEPARTMENT);
					userSalesDeptList = null;
					//获取所有的营业网点ID
					userSalesDeptList = areaService.getDeptsByUserIdAndDeptsTypes(baseAreaVo);
				}
				
				if (CollectionUtil.isNotEmpty(productsList)) {
					for (Product product : productsList) {
						productIds.add(product.getId());// 添加该用户所属的产品类型
					}
					loanVo.setProductIdList(productIds);
				}
				// 所在的营业网点
				loanVo.setSalesDeptIdList(userSalesDeptList);
			}else if(sysUser.getUserType().equals(EnumConstants.UserType.SYSTEM_ADMIN.getValue()) ){//系统管理员
	//			productIds.add(1L);
	//			productIds.add(2L);
	//			offerVo.setProductIds(productIds);
				loanVo.setSalesDeptId(null);
			}//财务sysUser.getUserType()==8	
			else {
				if (CollectionUtil.isNotEmpty(productsList)) {
					for (Product product : productsList) {
						productIds.add(product.getId());// 添加该用户所属的产品类型
					}
					loanVo.setProductIdList(productIds);
				}
			}
			if (loanVo.getStatus() != null && loanVo.getStatus() == 0) {//全部
				List<Integer> statusList=new ArrayList<Integer>();
				statusList.add(EnumConstants.LoanStatus.正常.getValue());
				statusList.add(EnumConstants.LoanStatus.逾期.getValue());
				loanVo.setStatusList(statusList);
			} else if (loanVo.getStatus() == null) {//默认为未报盘
				List<Integer> statusList=new ArrayList<Integer>();
				statusList.add(EnumConstants.LoanStatus.正常.getValue());
				statusList.add(EnumConstants.LoanStatus.逾期.getValue());
				loanVo.setStatusList(statusList);
			}
			
//			if (loanVo.getCreatedTimeStart() == null) {//开始时间默认为当天00:00:00			
//				loanVo.setCreatedTimeStart(DateUtil.getToday());
//			}else{
//				loanVo.setCreatedTimeStart(DateUtil.formatDate(loanVo.getCreatedTimeStart()));
//			}
//			if(loanVo.getC() == null){//结束时间默认为当天23:59:59						
//				loanVo.setCreatedTimeEnd(DateUtil.getEndTime());
//			}else{
//				loanVo.setCreatedTimeEnd(DateUtil.getEndTime(loanVo.getCreatedTimeStart()));
//			}
//			if(loanVo.getSendDateStart()!=null){
//				loanVo.setSendDateStart(DateUtil.formatDate(loanVo.getSendDateStart()));
//			}
//			if(loanVo.getSendDateEnd()!=null){
//				loanVo.setSendDateEnd(DateUtil.getEndTime(loanVo.getSendDateEnd()));
//			}
			Pager p = new Pager();
			p.setPage(page);
			p.setRows(rows);
			p.setSidx("ID");
			p.setSort("DESC");
			loanVo.setPager(p);
			loanVo.setPl("pl");
			
			Pager pager = loanService.findWithPGUnionExtension(loanVo);
			List<Loan> loans = pager.getResultList();
		    
			
			List<plListedLoan> loanList = new ArrayList<plListedLoan>();
			
			for (Loan loan : loans) {
				OfferVO of=new OfferVO();
				of.setLoanId(loan.getId());
				of.setStatus(10);
				String info=getLoanIdOfferCount(of);
				String operations = null;
				if (loanService.canApprove(loan, userId))
					operations = "签批";
				if (loanService.canExtensionApprove(loan))
					operations = "展期签批";
				ProductVO productVO = new ProductVO();
				productVO.setId(loan.getProductId());
				Product product = productService.findListByVO(productVO).get(0);
				loanList.add(new plListedLoan(loan.getId(),loan.getLoanId(), loan.getStatus(), loan.getRequestDate(), loan.getPerson().getId(), loan.getPerson().getPersonNo(), loan.getPerson().getName(), loan.getPerson()
					.getIdnum(), loan.getProductType(),loan.getProductId(),loan.getPactMoney(),loan.getTime(), loan.getSalesDept().getId(), loan.getSalesDept().getName(), loan.getSubmitDate(), (operations != null ? operations.toString() : null), loan
					.getService().getName(), loan.getCrm().getName(), loan.getAssessor().getName(), loan.getRequestMoney(),loan.getExtensionTime(),product.getProductCode(),loan.getAuditMoney(),product.getProductName(),loan.getPerson(),info));
			}
			
			
			Map<String, Object> result = new LinkedHashMap<String, Object>();
			result.put("total", pager.getTotalCount());
			result.put("rows", loanList);
			return result;
			/*}else{
				Map<String, Object> result = new LinkedHashMap<String, Object>();
				result.put("total", 0);
				result.put("rows", new ArrayList());
				return result;
			}*/
		/*}
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("total", 0);
		result.put("rows",  new ArrayList());
		return result;*/
	}
	/***
	 * 
	 * <pre>
	 * 划扣管理查询列表
	 * </pre>
	 *
	 * @param offerVo
	 * @param rows
	 * @param page
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getDeductionsManagementPage")
	@ResponseBody
	public Map getDeductionsManagementPage(HttpServletRequest request,OfferVO offerVo, int rows, int page) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String plFlag=request.getParameter("plFlag");
		try {
			//用于初始化列表查询条件
			if(StringUtils.isNotEmpty(offerVo.getStartStrDate())){
					offerVo.setSendDateStart(sdf.parse(offerVo.getStartStrDate()));
			}
			if(StringUtils.isNotEmpty(offerVo.getEndStrDate())){
				offerVo.setSendDateEnd(sdf.parse(offerVo.getEndStrDate()));
			}
			if(StringUtils.isNotEmpty(offerVo.getStartCreatStrDate())){
				offerVo.setCreatedTimeStart(sdf.parse(offerVo.getStartCreatStrDate()));
			}
			if(StringUtils.isNotEmpty(offerVo.getEndCreatStrDate())){
				offerVo.setCreatedTimeEnd(sdf.parse(offerVo.getEndCreatStrDate()));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//查找用户类型
		Long userId = ApplicationContext.getUser().getId();
		BaseAreaVO baseAreaVo = new BaseAreaVO();
		List<Product> productsList = productService.findProductsByUserId(userId);
		List<Long> productIds = new ArrayList<Long>();
		SysUser sysUser = sysUserService.get(userId);
		Integer userType = sysUser.getUserType();
		//事业部
		if (EnumConstants.UserType.AUDIT.getValue().equals(userType)) { // 事业部
			if (CollectionUtil.isNotEmpty(productsList)) {
				for (Product product : productsList) {
					productIds.add(product.getId());// 添加该用户所属的产品类型
				}
				offerVo.setProductIds(productIds);
			}
		}else if (EnumConstants.UserType.CUSTOMER_SERVICE.getValue().equals(userType) 
					||EnumConstants.UserType.STORE_ASSISTANT_MANAGER.getValue().equals(userType)
					||EnumConstants.UserType.STORE_MANAGER.getValue().equals(userType)) {// 客服6 ，经理2，副理3
			List<Long> userSalesDeptList = sysUserService.getSalesDeptIdByUserId(userId);
			//如果是刘娜，则重新获取营业网点ID集合
			// isAddOtherDepts 用于判断该用户是否拥有操作其他营业网点的权限
			  if(EnumConstants.IsAddOtherDepts.isTrue.getValue().equals(String.valueOf(sysUser.getIsAddOtherDepts()))){
				baseAreaVo.setUserId(userId);
				baseAreaVo.setIdentifier(BizConstants.CREDIT2_SALESDEPARTMENT);
				userSalesDeptList = null;
				//获取所有的营业网点ID
				userSalesDeptList = areaService.getDeptsByUserIdAndDeptsTypes(baseAreaVo);
			}
			
			
			
			if (CollectionUtil.isNotEmpty(productsList)) {
				for (Product product : productsList) {
					productIds.add(product.getId());// 添加该用户所属的产品类型
				}
				offerVo.setProductIds(productIds);
			}
			// 所在的营业网点
			offerVo.setDeptList(userSalesDeptList);
		}else if(sysUser.getUserType().equals(EnumConstants.UserType.SYSTEM_ADMIN.getValue()) ){//系统管理员
//			productIds.add(1L);
//			productIds.add(2L);
//			offerVo.setProductIds(productIds);
			offerVo.setSalesDeptId(null);
		}//财务sysUser.getUserType()==8	
		else{
			if (CollectionUtil.isNotEmpty(productsList)) {
				for (Product product : productsList) {
					productIds.add(product.getId());// 添加该用户所属的产品类型
				}
				offerVo.setProductIds(productIds);
			}
		}
		if (offerVo.getStatus() != null && offerVo.getStatus() == 0) {//全部
			offerVo.setStatus(null);
		} else if (offerVo.getStatus() == null) {//默认为未报盘
			offerVo.setStatus(EnumConstants.OfferState.NOT_OFFER.getValue());
		}
		//产品类别
		if (offerVo.getProductId() != null && offerVo.getProductId() == 0) {//全部
			offerVo.setProductId(null);
		} 
		//划扣通道
		if(offerVo.getTppType()!=null && offerVo.getTppType()==0){
			offerVo.setTppType(null);
		}
		if (offerVo.getHasGuarantee() != null && offerVo.getHasGuarantee() == 0) {//是否担保
			offerVo.setHasGuarantee(null);
		}
		if (offerVo.getReturnCode()!= null && offerVo.getReturnCode().equals("all")) {//划扣结果
			offerVo.setReturnCode(null);
		}
		//结果为默认
		if(offerVo.getReturnCode() != null && offerVo.getReturnCode().equals("default")){
			List<String> returnCodeList=new ArrayList<String>();
			returnCodeList.add(TPPConstants.HANDLE_SUCCESS_CODE);
			returnCodeList.add(TPPConstants.HANDLE_PARTSUCCESS_CODE);
			offerVo.setReturnCodeList(returnCodeList);
			offerVo.setReturnCode(null);
		}
		if(offerVo.getCreatedTimeStart() != null && !"".equals(offerVo.getCreatedTimeStart())){
			offerVo.setCreatedTimeStart(DateUtil.formatDate(offerVo.getCreatedTimeStart()));
		}
		 if(offerVo.getCreatedTimeEnd() != null && !"".equals(offerVo.getCreatedTimeEnd())){
			offerVo.setCreatedTimeEnd(DateUtil.getEndTime(offerVo.getCreatedTimeEnd()));
		}
		if(offerVo.getSendDateStart()!=null){
			offerVo.setSendDateStart(DateUtil.formatDate(offerVo.getSendDateStart()));
		}
		if(offerVo.getSendDateEnd()!=null){
			offerVo.setSendDateEnd(DateUtil.getEndTime(offerVo.getSendDateEnd()));
		}
		Pager p = new Pager();
		p.setPage(page);
		p.setRows(rows);
		p.setSidx("ID");
		p.setSort("DESC");
		offerVo.setPager(p);
		offerVo.setPlFlag(plFlag);
		Pager pager = deductionsManagementService.findWithPg(offerVo);
		List<Offer> offerList = pager.getResultList();
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		//是否有担保人
		if(CollectionUtil.isNotEmpty(offerList)){
			for(Offer offer:offerList){
				if(deductionsManagementService.hasGuarantee(offer)){
					//有担保人
					offer.setIsGuarantee(EnumConstants.HasGuarantee.HAVE.getValue());
				}else{
					offer.setIsGuarantee(EnumConstants.HasGuarantee.NO.getValue());	
				}
				BaseArea area = new BaseArea();
				area = this.baseAreaService.get(offer.getSalesDeptId());
				if(null != area) {
					offer.setSalesDeptName(area.getName());
				}
			}
		}
			result.put("total",pager.getTotalCount());
			result.put("rows", offerList);			
		return result;
	}

	@RequestMapping("/findCountByParams")
	@ResponseBody
	public String findCountByParams(OfferVO offerVo) throws UnsupportedEncodingException {
		if(offerVo.getPersonName()!=null){			
			offerVo.setPersonName(java.net.URLDecoder.decode(offerVo.getPersonName(), "UTF-8"));
		}
		if(offerVo.getBankName()!=null){			
			offerVo.setBankName(java.net.URLDecoder.decode(offerVo.getBankName(), "UTF-8"));
		}
		if(offerVo.getFailReasonType()!=null){			
			offerVo.setFailReasonType(java.net.URLDecoder.decode(offerVo.getFailReasonType(), "UTF-8"));
		}
		
		//用户类型
		Integer userType = ApplicationContext.getUser().getUserType();
		//不是admin 和财务
		if(!userType.equals(EnumConstants.UserType.SYSTEM_ADMIN.getValue())&&!userType.equals(EnumConstants.UserType.FINANCE.getValue())){
			return "您没有权限导出";
		}	
		Long userId = ApplicationContext.getUser().getId();
		List<Product> productsList = productService.findProductsByUserId(userId);
		List<Long> productIds = new ArrayList<Long>();
		SysUser sysUser = sysUserService.get(userId);
		//事业部
		if (EnumConstants.UserType.AUDIT.getValue().equals(userType)) { // 事业部
			if (CollectionUtil.isNotEmpty(productsList)) {
				for (Product product : productsList) {
					productIds.add(product.getId());// 添加该用户所属的产品类型
				}
				offerVo.setProductIds(productIds);
			}
		}else if (EnumConstants.UserType.CUSTOMER_SERVICE.getValue().equals(userType) 
					||EnumConstants.UserType.STORE_ASSISTANT_MANAGER.getValue().equals(userType)
					||EnumConstants.UserType.STORE_MANAGER.getValue().equals(userType)) {// 客服6 ，经理2，副理3
			List<Long> userSalesDeptList = sysUserService.getSalesDeptIdByUserId(userId);
			
			if (CollectionUtil.isNotEmpty(productsList)) {
				for (Product product : productsList) {
					productIds.add(product.getId());// 添加该用户所属的产品类型
				}
				offerVo.setProductIds(productIds);
			}
			// 所在的营业网点
			offerVo.setDeptList(userSalesDeptList);
		}else if(sysUser.getUserType().equals(EnumConstants.UserType.SYSTEM_ADMIN.getValue()) ){//系统管理员
//			productIds.add(1L);
//			productIds.add(2L);
//			offerVo.setProductIds(productIds);
			offerVo.setSalesDeptId(null);
		}//财务sysUser.getUserType()==8	
		else {
			if (CollectionUtil.isNotEmpty(productsList)) {
				for (Product product : productsList) {
					productIds.add(product.getId());// 添加该用户所属的产品类型
				}
				offerVo.setProductIds(productIds);
			}
		}
		if (offerVo.getStatus() != null && offerVo.getStatus() == 0) {//全部
			offerVo.setStatus(null);
		} else if (offerVo.getStatus() == null) {//默认为未报盘
			offerVo.setStatus(EnumConstants.OfferState.NOT_OFFER.getValue());
		}
		
		if (offerVo.getHasGuarantee() != null && offerVo.getHasGuarantee() == 0) {//是否担保
			offerVo.setHasGuarantee(null);
		}
		//结果为所有
		if(offerVo.getReturnCode() != null && offerVo.getReturnCode().equals("all")){
			offerVo.setReturnCode(null);
		}	
		//结果为默认
		if(offerVo.getReturnCode() != null && offerVo.getReturnCode().equals("default")){
			List<String> returnCodeList=new ArrayList<String>();
			returnCodeList.add(TPPConstants.HANDLE_SUCCESS_CODE);
			returnCodeList.add(TPPConstants.HANDLE_PARTSUCCESS_CODE);
			offerVo.setReturnCodeList(returnCodeList);
			offerVo.setReturnCode(null);
		}
		//产品类别
				if (offerVo.getProductId() != null && offerVo.getProductId() == 0) {//全部
					offerVo.setProductId(null);
				} 
				//报盘类型为全部
				if(offerVo.getOfferType() != null && offerVo.getOfferType().equals(99)){
					offerVo.setOfferType(null);
		}
		//结果为成功的
//		offerVo.setReturnCode(TPPConstants.HANDLE_SUCCESS_CODE);
		if(offerVo.getCreatedTimeStart() != null && !"".equals(offerVo.getCreatedTimeStart())){
			offerVo.setCreatedTimeStart(DateUtil.formatDate(offerVo.getCreatedTimeStart()));
		}
		if(offerVo.getCreatedTimeEnd() != null && !"".equals(offerVo.getCreatedTimeEnd())){
			offerVo.setCreatedTimeEnd(DateUtil.getEndTime(offerVo.getCreatedTimeEnd()));
		}				
			
		
		if(offerVo.getSendDateStart()!=null){
			offerVo.setSendDateStart(DateUtil.formatDate(offerVo.getSendDateStart()));
		}
		if(offerVo.getSendDateEnd()!=null){
			offerVo.setSendDateEnd(DateUtil.getEndTime(offerVo.getSendDateEnd()));
		}
		List<Offer> offerList = deductionsManagementService.findListByOfferVO(offerVo);
		if (CollectionUtil.isNullOrEmpty(offerList)) {
			return "没有查询出数据";
		} else {
			SysParameter sysParameter = sysParameterService
				.getByCode(SysParameterEnum.EXCEL_EXPORT_MAX_NO.name());
			if (sysParameter != null) {
				if (offerList.size() > Integer.parseInt(sysParameter.getParameterValue())) {
					return "excel导出条数过多！";
				}
			}
		}

		return "success";
	}
	
	/**
	 * 获取划扣失败数据数量
	 * @param offerVo
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/findFailCountByParams")
	@ResponseBody
	public String findFailCountByParams(OfferVO offerVo) throws UnsupportedEncodingException {
		
		//用户类型
		Integer userType = ApplicationContext.getUser().getUserType();
		//不是admin
		if(!userType.equals(EnumConstants.UserType.SYSTEM_ADMIN.getValue())){
			return "您没有权限导出";
		}	
		Long userId = ApplicationContext.getUser().getId();
		List<Product> productsList = productService.findProductsByUserId(userId);
		List<Long> productIds = new ArrayList<Long>();
		SysUser sysUser = sysUserService.get(userId);
		//事业部
		if (EnumConstants.UserType.AUDIT.getValue().equals(userType)) { // 事业部
			if (CollectionUtil.isNotEmpty(productsList)) {
				for (Product product : productsList) {
					productIds.add(product.getId());// 添加该用户所属的产品类型
				}
				offerVo.setProductIds(productIds);
			}
		}else if (EnumConstants.UserType.CUSTOMER_SERVICE.getValue().equals(userType) 
					||EnumConstants.UserType.STORE_ASSISTANT_MANAGER.getValue().equals(userType)
					||EnumConstants.UserType.STORE_MANAGER.getValue().equals(userType)) {// 客服6 ，经理2，副理3
			List<Long> userSalesDeptList = sysUserService.getSalesDeptIdByUserId(userId);
			
			if (CollectionUtil.isNotEmpty(productsList)) {
				for (Product product : productsList) {
					productIds.add(product.getId());// 添加该用户所属的产品类型
				}
				offerVo.setProductIds(productIds);
			}
			// 所在的营业网点
			offerVo.setDeptList(userSalesDeptList);
		}else if(sysUser.getUserType().equals(EnumConstants.UserType.SYSTEM_ADMIN.getValue()) ){//系统管理员
			offerVo.setSalesDeptId(null);
		}
		else {
			if (CollectionUtil.isNotEmpty(productsList)) {
				for (Product product : productsList) {
					productIds.add(product.getId());// 添加该用户所属的产品类型
				}
				offerVo.setProductIds(productIds);
			}
		}
		//生成时间
		if(offerVo.getCreatedTimeStart() != null && !"".equals(offerVo.getCreatedTimeStart())){
			offerVo.setCreatedTimeStart(DateUtil.formatDate(offerVo.getCreatedTimeStart()));
		}
		 if(offerVo.getCreatedTimeEnd() != null && !"".equals(offerVo.getCreatedTimeEnd())){
			offerVo.setCreatedTimeEnd(DateUtil.getEndTime(offerVo.getCreatedTimeEnd()));
		}
		List<Offer> offerList = deductionsManagementService.findListByOfferVO(offerVo);
		if (CollectionUtil.isNullOrEmpty(offerList)) {
			return "没有查询出数据";
		} else {
			SysParameter sysParameter = sysParameterService
				.getByCode(SysParameterEnum.EXCEL_EXPORT_MAX_NO.name());
			if (sysParameter != null) {
				if (offerList.size() > Integer.parseInt(sysParameter.getParameterValue())) {
					return "excel导出条数过多！";
				}
			}
		}

		return "success";
	}
	
	
	

	/***
	 * 导出Excel
	 * @param offerVo
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/exportExcel")
	@ResponseBody
	public String exportExcel(OfferVO offerVo, HttpServletResponse response) throws UnsupportedEncodingException {
		if(offerVo.getPersonName()!=null){			
			offerVo.setPersonName(java.net.URLDecoder.decode(offerVo.getPersonName(), "UTF-8"));
		}
		if(offerVo.getBankName()!=null){			
			offerVo.setBankName(java.net.URLDecoder.decode(offerVo.getBankName(), "UTF-8"));
		}
		if(offerVo.getFailReasonType()!=null){			
			offerVo.setFailReasonType(java.net.URLDecoder.decode(offerVo.getFailReasonType(), "UTF-8"));
		}
		
		//用户类型
		Integer userType = ApplicationContext.getUser().getUserType();
		if(!userType.equals(EnumConstants.UserType.SYSTEM_ADMIN.getValue())&&!userType.equals(EnumConstants.UserType.FINANCE.getValue())){
			return "您没有权限导出";
		}	
		Long userId = ApplicationContext.getUser().getId();
		List<Product> productsList = productService.findProductsByUserId(userId);
		List<Long> productIds = new ArrayList<Long>();
		SysUser sysUser = sysUserService.get(userId);
		//事业部
		if (EnumConstants.UserType.AUDIT.getValue().equals(userType)) { // 事业部
			if (CollectionUtil.isNotEmpty(productsList)) {
				for (Product product : productsList) {
					productIds.add(product.getId());// 添加该用户所属的产品类型
				}
				offerVo.setProductIds(productIds);
			}
		}else if (EnumConstants.UserType.CUSTOMER_SERVICE.getValue().equals(userType) 
					||EnumConstants.UserType.STORE_ASSISTANT_MANAGER.getValue().equals(userType)
					||EnumConstants.UserType.STORE_MANAGER.getValue().equals(userType)) {// 客服6 ，经理2，副理3
			List<Long> userSalesDeptList = sysUserService.getSalesDeptIdByUserId(userId);
			
			if (CollectionUtil.isNotEmpty(productsList)) {
				for (Product product : productsList) {
					productIds.add(product.getId());// 添加该用户所属的产品类型
				}
				offerVo.setProductIds(productIds);
			}
			// 所在的营业网点
			offerVo.setDeptList(userSalesDeptList);
		}else if(sysUser.getUserType().equals(EnumConstants.UserType.SYSTEM_ADMIN.getValue()) ){//系统管理员
//			productIds.add(1L);
//			productIds.add(2L);
//			offerVo.setProductIds(productIds);
			offerVo.setSalesDeptId(null);
		}//财务sysUser.getUserType()==8	
		else {
			if (CollectionUtil.isNotEmpty(productsList)) {
				for (Product product : productsList) {
					productIds.add(product.getId());// 添加该用户所属的产品类型
				}
				offerVo.setProductIds(productIds);
			}
		}
		offerVo.setReturnCodeList(null);
		//结果为所有
		if(offerVo.getReturnCode() != null && offerVo.getReturnCode().equals("all")){
			offerVo.setReturnCode(null);
		}
		//结果为默认
		if(offerVo.getReturnCode() != null && offerVo.getReturnCode().equals("default")){
			List<String> returnCodeList=new ArrayList<String>();
			returnCodeList.add(TPPConstants.HANDLE_SUCCESS_CODE);
			returnCodeList.add(TPPConstants.HANDLE_PARTSUCCESS_CODE);
			offerVo.setReturnCodeList(returnCodeList);
			offerVo.setReturnCode(null);
		}
		//产品类别
				if (offerVo.getProductId() != null && offerVo.getProductId() == 0) {//全部
					offerVo.setProductId(null);
				} 
				
		//报盘类型为全部
		if(offerVo.getOfferType() != null && offerVo.getOfferType().equals(99)){
					offerVo.setOfferType(null);
		}
		//结果为成功的
//		offerVo.setReturnCode(TPPConstants.HANDLE_SUCCESS_CODE);
		if(offerVo.getCreatedTimeStart() != null && !"".equals(offerVo.getCreatedTimeStart())){
			offerVo.setCreatedTimeStart(DateUtil.formatDate(offerVo.getCreatedTimeStart()));
		}
		 if(offerVo.getCreatedTimeEnd() != null && !"".equals(offerVo.getCreatedTimeEnd())){
			offerVo.setCreatedTimeEnd(DateUtil.getEndTime(offerVo.getCreatedTimeEnd()));
		}
		if(offerVo.getSendDateStart()!=null){
			offerVo.setSendDateStart(DateUtil.formatDate(offerVo.getSendDateStart()));
		}
		if(offerVo.getSendDateEnd()!=null){
			offerVo.setSendDateEnd(DateUtil.getEndTime(offerVo.getSendDateEnd()));
		}
		if (offerVo.getHasGuarantee() != null && offerVo.getHasGuarantee() == 0) {//是否担保
			offerVo.setHasGuarantee(null);
		}
		if (offerVo.getStatus() != null && offerVo.getStatus() == 0) {//全部
			offerVo.setStatus(null);
		}else if (offerVo.getStatus() == null) {
			offerVo.setStatus(EnumConstants.OfferState.NOT_OFFER.getValue());
		}
		List<Offer> offerList = deductionsManagementService.findListByOfferVO(offerVo);
		for(Offer offer : offerList) {
			BaseArea area = new BaseArea();
			area = this.baseAreaService.get(offer.getSalesDeptId());
			if(null != area) {
				offer.setSalesDeptName(area.getName());
			}
		}
	
		//文件名 
		String fileName = "回盘结果导出"+DateUtil.getDate(new Date(), "yy-MM-dd") + ".xlsx";
		
		String downloadPath = credit2Properties.getDownloadPath();
		/*File downloadFile= new File(downloadPath);
		if (!downloadFile.exists()) {//不存在download目录则创建该目录
			downloadFile.mkdir();
		}	*/	
		File file = new File(downloadPath + File.separator + "offer");
		if (!file.exists()) {//不存在则创建该目录
			file.mkdir();
		}
		OutputStream os;
		try {
			os = new FileOutputStream(downloadPath + File.separator + "offer" + File.separator
										+ fileName.trim().toString());
			//生成Excel文件			
			deductionsManagementService.exportExcel(offerList, "回盘导出结果", os);
		} catch (FileNotFoundException e) {
			logger.error("生成Excel文件出错",  e);
			throw new BusinessException("生成Excel文件出错!");			
		}
		//下载Excel文件的路径
		String filePath = downloadPath + File.separator + "offer" + File.separator + fileName;
		try {
			//下载Excel文件到客户端
			if (FileUtil.downLoadFile(filePath, response, fileName, "xlsx")) {
				//导出成功后删除导出的文件
				FileUtil.deleteFile(filePath);
				return "success";
			} else {
				return "下载Excel出错!";
			}
		} catch (Exception e) {
			logger.error("下载Excel出错!", e);
			throw new BusinessException("下载Excel出错!");
		}		
	}
	
	
	/***
	 * 导出划扣失败数据
	 * @param offerVo
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/exportFailExcel")
	@ResponseBody
	public String exportFailExcel(OfferVO offerVo, HttpServletResponse response) throws UnsupportedEncodingException {
		
		//用户类型
		Integer userType = ApplicationContext.getUser().getUserType();
		//不是admin
		if(!userType.equals(EnumConstants.UserType.SYSTEM_ADMIN.getValue())){
			return "您没有权限导出";
		}	
		Long userId = ApplicationContext.getUser().getId();
		List<Product> productsList = productService.findProductsByUserId(userId);
		List<Long> productIds = new ArrayList<Long>();
		SysUser sysUser = sysUserService.get(userId);
		//事业部
		if (EnumConstants.UserType.AUDIT.getValue().equals(userType)) { // 事业部
			if (CollectionUtil.isNotEmpty(productsList)) {
				for (Product product : productsList) {
					productIds.add(product.getId());// 添加该用户所属的产品类型
				}
				offerVo.setProductIds(productIds);
			}
		}else if (EnumConstants.UserType.CUSTOMER_SERVICE.getValue().equals(userType) 
					||EnumConstants.UserType.STORE_ASSISTANT_MANAGER.getValue().equals(userType)
					||EnumConstants.UserType.STORE_MANAGER.getValue().equals(userType)) {// 客服6 ，经理2，副理3
			List<Long> userSalesDeptList = sysUserService.getSalesDeptIdByUserId(userId);
			
			if (CollectionUtil.isNotEmpty(productsList)) {
				for (Product product : productsList) {
					productIds.add(product.getId());// 添加该用户所属的产品类型
				}
				offerVo.setProductIds(productIds);
			}
			// 所在的营业网点
			offerVo.setDeptList(userSalesDeptList);
		}else if(sysUser.getUserType().equals(EnumConstants.UserType.SYSTEM_ADMIN.getValue()) ){//系统管理员
			offerVo.setSalesDeptId(null);
		}
		else {
			if (CollectionUtil.isNotEmpty(productsList)) {
				for (Product product : productsList) {
					productIds.add(product.getId());// 添加该用户所属的产品类型
				}
				offerVo.setProductIds(productIds);
			}
		}
		//生成时间
		if(offerVo.getCreatedTimeStart() != null && !"".equals(offerVo.getCreatedTimeStart())){
			offerVo.setCreatedTimeStart(DateUtil.formatDate(offerVo.getCreatedTimeStart()));
		}
		 if(offerVo.getCreatedTimeEnd() != null && !"".equals(offerVo.getCreatedTimeEnd())){
			offerVo.setCreatedTimeEnd(DateUtil.getEndTime(offerVo.getCreatedTimeEnd()));
		}		
		List<Offer> offerList = deductionsManagementService.findListByOfferVO(offerVo);		
		//文件名 
		String fileName = "划扣结果"+DateUtil.getDate(new Date(), "yy-MM-dd") + ".xlsx";
		
		String downloadPath = credit2Properties.getDownloadPath();
		/*File downloadFile= new File(downloadPath);
		if (!downloadFile.exists()) {//不存在download目录则创建该目录
			downloadFile.mkdir();
		}	*/	
		File file = new File(downloadPath + File.separator + "offer");
		if (!file.exists()) {//不存在则创建该目录
			file.mkdir();
		}
		OutputStream os;
		try {
			os = new FileOutputStream(downloadPath + File.separator + "offer" + File.separator
										+ fileName.trim().toString());
			//生成Excel文件			
			deductionsManagementService.exportFailExcel(offerList, "划扣失败结果", os);
		} catch (FileNotFoundException e) {
			logger.error("生成Excel文件出错",  e);
			throw new BusinessException("生成Excel文件出错!");			
		}
		//下载Excel文件的路径
		String filePath = downloadPath + File.separator + "offer" + File.separator + fileName;
		try {
			//下载Excel文件到客户端
			if (FileUtil.downLoadFile(filePath, response, fileName, "xlsx")) {
				//导出成功后删除导出的文件
				FileUtil.deleteFile(filePath);
				return "success";
			} else {
				return "下载Excel出错!";
			}
		} catch (Exception e) {
			logger.error("下载Excel出错!", e);
			throw new BusinessException("下载Excel出错!");
		}		
	}
	
	/***
	 * 是否可进行实时报盘
	 * @return
	 */
	@RequestMapping("/toCurrentOffer")
	@ResponseBody
	public String toCurrentOffer(Long loanId) {
		if (loanId != null) {
			OfferVO offerVo=new OfferVO();
			offerVo.setLoanId(loanId);
			offerVo.setOfferType(EnumConstants.OfferType.CURRENT_OFFER.getValue());
			offerVo.setCreatedTimeStart(DateUtil.getToday());
			offerVo.setCreatedTimeEnd(DateUtil.getEndTime());
			List<Offer> offerList = deductionsManagementService.findListByOfferVO(offerVo);	
			OfferVO offerVo2=new OfferVO();
			offerVo2.setLoanId(loanId);
			List<Integer> statusList = new ArrayList<Integer>();
			statusList.add(EnumConstants.OfferState.NOT_OFFER.getValue());
			statusList.add(EnumConstants.OfferState.HAS_TO_OFFER.getValue());
			offerVo2.setStatusList(statusList);
			List<Offer> offerList2  = deductionsManagementService.findListByOfferVO(offerVo2);	
			if(checkDate()){
				return "定时报盘划扣前后15分钟内及8:00前和21:00后都不可做实时报盘操作!";
			}
			if(offerList2.size() > 0){
				return "该笔交易已生成报盘记录!";
			}
			SpecialRepaymentVO specialRepaymentVO = new SpecialRepaymentVO();
			List<Integer> typeList = new ArrayList<Integer>();
			typeList.add(EnumConstants.SpecialRepaymentType.REFUSE_OFFER.getValue());
			specialRepaymentVO.setStatus(EnumConstants.SpecialRepaymentStatus.APPLY.getValue());
			specialRepaymentVO.setTypeList(typeList);
			specialRepaymentVO.setLoanId(loanId);
			Pager specialRepaymentPager=specialRepaymentService.findListByVOWihtBaseExtend(specialRepaymentVO);
			if(specialRepaymentPager.getTotalCount()==1){
				return "该借款数据已经停止报盘";
			}
			SysParameter currentOfferTimes=sysParameterService.getByCode(SysParameterEnum.CURRENT_OFFER_TIMES.name());
			if(offerList.size() <= Integer.parseInt(currentOfferTimes.getParameterValue())){
				return "success";
			}else{
				return "该笔交易已生成实时报盘"+(Integer.parseInt(currentOfferTimes.getParameterValue())+1)+"次!";
			}
		} else {
			return "主键为空!";
		}
	}
	
	/***
	 * 添加实时报盘
	 * @return
	 */
	@RequestMapping("/addCurrentOffer")
	@ResponseBody
	public String addCurrentOffer(Offer offer) {
		if (offer != null) {
			String creditAuth = sysParameterService.getByCodeNoCache("TPP_ACCOUNT_CREDIT").getParameterValue();
			LoanVO loanVO=new LoanVO();
			loanVO.setId(offer.getLoanId());
			loanVO.setPersonId(offer.getPersonId());
			List<Loan> loan=loanService.findListByVOUnionExtension(loanVO);
			if(loan.size()==1){
				if(loan.get(0).getProductId().equals(EnumConstants.ProductList.STUDENT_LOAN.getValue())){
					Date nowDate = DateUtil.getToday();
					ChannelPlanCheck channelPlanCheck=channelPlanCheckService.getReplyById(loan.get(0).getSchemeID());
					List<RepaymentPlan> repaymentPlanList = repayService.getAllInterestOrLoan(nowDate, loan.get(0).getId());
					if(repaymentPlanList.size()==0){
						return "应报金额小于0或者等于零,不可生成报盘!";
					}
					//当前期限
					Integer currTerm = repayService.getCurrTerm(repaymentPlanList, nowDate);
					if(channelPlanCheck.getOrgRepayTerm().compareTo(currTerm)>=0){
						return "该阶段为机构还款不可做实时划扣";
					}
				}
				offer.setTellerId(loan.get(0).getServiceId());
				offer.setSalesDeptId(loan.get(0).getSalesDeptId());
				offer.setAccountType(BizConstants.ACCT_TYPE);
				offer.setStatus(EnumConstants.OfferState.NOT_OFFER.getValue());
				offer.setOfferType(EnumConstants.OfferType.CURRENT_OFFER.getValue());
				PersonBankVO personBankVO = new PersonBankVO();
				personBankVO.setPersonId(offer.getPersonId());
				personBankVO.setLoanId(offer.getLoanId());
				PersonBank personBank = personBankService.getPersonBank(personBankVO);
				if (personBank == null) {
					String esg = "Can't find the person bank record via personId:" + offer.getPersonId() + " loanId: "
									+ offer.getLoanId();
					logger.error(esg);
					return esg;
				}
				BankAccount bankAccount = bankAccountService.getBankAccount(personBank.getBankAccountId());
				offer.setBankAccount(bankAccount.getAccount());
				offer.setBankName(bankAccount.getBankName());
				BigDecimal amount = repayService.getOfferAmount(DateUtil.getToday(), offer.getLoanId());
				logger.info("用来生成报盘的loan Id: "+offer.getLoanId()+",获取的报盘金额: "+amount);
				offer.setReceivableAmount(amount); //TBD
				offer.setOfferAmount(amount);//TBD
				offer.setTppType(bankAccount.getBank().getTppType());
				offer.setHasGuarantee(EnumConstants.HasGuarantee.NO.getValue());//默认无担保
				offer.setRemark("未报盘");
				if(amount.compareTo(new BigDecimal("0")) <= 0){
					return "应报金额小于0或者等于零,不可生成报盘!"; //若得到的应报金额小于0或者等于零的时候，将不会生成报盘记录
				}
			
				offerService.insert(offer);
				
				SysLog sysLog = new SysLog();
				sysLog.setOptModule(EnumConstants.OptionModule.DEDUCTIONS_MANAGEMENT.getValue());
				sysLog.setOptType(EnumConstants.OptionType.DEDUCTIONS_GENE.getValue());
				sysLog.setRemark("loanId :" + offer.getLoanId().toString() + ", offerId :" + offer.getId().toString());
				// 插入系统日志
				sysLogService.insert(sysLog);
				return "success";
			}else{
				return "交易异常";
			}
		}
		return "参数为空";
	}
	

	
	/***
	 * 是否可进行实时报盘(批量)
	 * @return
	 */
	@RequestMapping("/plToCurrentOffer")
	@ResponseBody
	public String plToCurrentOffer(Long loanId) {
		if (loanId != null) {
			OfferVO offerVo=new OfferVO();
			offerVo.setLoanId(loanId);
			offerVo.setOfferType(EnumConstants.OfferType.CURRENT_OFFER.getValue());
			offerVo.setCreatedTimeStart(DateUtil.getToday());
			offerVo.setCreatedTimeEnd(DateUtil.getEndTime());
			List<Offer> offerList = deductionsManagementService.findListByOfferVO(offerVo);	
			OfferVO offerVo2=new OfferVO();
			offerVo2.setLoanId(loanId);
			List<Integer> statusList = new ArrayList<Integer>();
			statusList.add(EnumConstants.OfferState.NOT_OFFER.getValue());
			statusList.add(EnumConstants.OfferState.HAS_TO_OFFER.getValue());
			offerVo2.setStatusList(statusList);
			List<Offer> offerList2  = deductionsManagementService.findListByOfferVO(offerVo2);	
			if(checkDate()){
				return "借款编号["+loanId+"]定时报盘划扣前后15分钟内及8:00前和21:00后都不可做实时报盘操作!";
			}
			if(offerList2.size() > 0){
				return "借款编号["+loanId+"]该笔交易已生成报盘记录!";
			}
			SpecialRepaymentVO specialRepaymentVO = new SpecialRepaymentVO();
			List<Integer> typeList = new ArrayList<Integer>();
			typeList.add(EnumConstants.SpecialRepaymentType.REFUSE_OFFER.getValue());
			specialRepaymentVO.setStatus(EnumConstants.SpecialRepaymentStatus.APPLY.getValue());
			specialRepaymentVO.setTypeList(typeList);
			specialRepaymentVO.setLoanId(loanId);
			Pager specialRepaymentPager=specialRepaymentService.findListByVOWihtBaseExtend(specialRepaymentVO);
			if(specialRepaymentPager.getTotalCount()==1){
				return "借款编号["+loanId+"]该借款数据已经停止报盘";
			}
			SysParameter currentOfferTimes=sysParameterService.getByCode(SysParameterEnum.CURRENT_OFFER_TIMES.name());
			if(offerList.size() <= Integer.parseInt(currentOfferTimes.getParameterValue())){
				return "success";
			}else{
				return "借款编号["+loanId+"]该笔交易已生成实时报盘"+(Integer.parseInt(currentOfferTimes.getParameterValue())+1)+"次!";
			}
		} else {
			return "主键为空!";
		}
	}
	/***
	 * 添加实时报盘
	 * @return
	 */
	@RequestMapping("/plAddCurrentOffer")
	@ResponseBody
	public String plAddCurrentOffer(Offer offer) {
		if (offer != null) {
			String creditAuth = sysParameterService.getByCodeNoCache("TPP_ACCOUNT_CREDIT").getParameterValue();
			LoanVO loanVO=new LoanVO();
			loanVO.setId(offer.getLoanId());
			loanVO.setPersonId(offer.getPersonId());
			List<Loan> loan=loanService.findListByVOUnionExtension(loanVO);
			if(loan.size()==1){
				if(loan.get(0).getProductId().equals(EnumConstants.ProductList.STUDENT_LOAN.getValue())){
					Date nowDate = DateUtil.getToday();
					ChannelPlanCheck channelPlanCheck=channelPlanCheckService.getReplyById(loan.get(0).getSchemeID());
					List<RepaymentPlan> repaymentPlanList = repayService.getAllInterestOrLoan(nowDate, loan.get(0).getId());
					if(repaymentPlanList.size()==0){
						return "借款编号["+loanVO.getId()+"]应报金额小于0或者等于零,不可生成报盘!";
					}
					//当前期限
					Integer currTerm = repayService.getCurrTerm(repaymentPlanList, nowDate);
					if(channelPlanCheck.getOrgRepayTerm().compareTo(currTerm)>=0){
						return "借款编号["+loanVO.getId()+"]该阶段为机构还款不可做实时划扣";
					}
				}
				offer.setTellerId(loan.get(0).getServiceId());
				offer.setSalesDeptId(loan.get(0).getSalesDeptId());
				offer.setAccountType(BizConstants.ACCT_TYPE);
				offer.setStatus(EnumConstants.OfferState.NOT_OFFER.getValue());
				offer.setOfferType(EnumConstants.OfferType.CURRENT_OFFER.getValue());
				PersonBankVO personBankVO = new PersonBankVO();
				personBankVO.setPersonId(offer.getPersonId());
				personBankVO.setLoanId(offer.getLoanId());
				PersonBank personBank = personBankService.getPersonBank(personBankVO);
				if (personBank == null) {
					String esg = "Can't find the person bank record via personId:" + offer.getPersonId() + " loanId: "
									+ offer.getLoanId();
					logger.error(esg);
					return esg;
				}
				BankAccount bankAccount = bankAccountService.getBankAccount(personBank.getBankAccountId());
				offer.setBankAccount(bankAccount.getAccount());
				offer.setBankName(bankAccount.getBankName());
				BigDecimal amount = repayService.getOfferAmount(DateUtil.getToday(), offer.getLoanId());
				logger.info("用来生成报盘的loan Id: "+offer.getLoanId()+",获取的报盘金额: "+amount);
				offer.setReceivableAmount(amount); //TBD
				offer.setOfferAmount(amount);//TBD
				offer.setTppType(bankAccount.getBank().getTppType());
				offer.setHasGuarantee(EnumConstants.HasGuarantee.NO.getValue());//默认无担保
				offer.setRemark("未报盘");
				if(amount.compareTo(new BigDecimal("0")) <= 0){
					return "借款编号["+loanVO.getId()+"]应报金额小于0或者等于零,不可生成报盘!"; //若得到的应报金额小于0或者等于零的时候，将不会生成报盘记录
				}
			
				offerService.insert(offer);
				
				SysLog sysLog = new SysLog();
				sysLog.setOptModule(EnumConstants.OptionModule.PL_DEDUCTIONS_MANAGEMENT.getValue());
				sysLog.setOptType(EnumConstants.OptionType.PL_DEDUCTIONS_GENE.getValue());
				sysLog.setRemark("loanId :" + offer.getLoanId().toString() + ", offerId :" + offer.getId().toString()+",批量划扣管理 实时报盘 生成 ");
				// 插入系统日志
				sysLogService.insert(sysLog);
				return "借款编号["+loanVO.getId()+"]提交成功!";
			}else{
				return "借款编号["+loanVO.getId()+"]交易异常!";
			}
		}
		return "参数为空";
	}
	
	/**
	 * 
	 * <pre>
	 * 发送相应的数据到TPP
	 * </pre>
	 *
	 * @param offerId
	 * @return String
	 * @throws UnknownHostException
	 */
	@RequestMapping("/toSendTpp")
	@ResponseBody
	public String sendOfferData(Long offerId) throws UnknownHostException {
//		logger.info(JobConstants.SEND_OFFER+"->开始处理的offerId: "+offer.getId());
		if(offerId!= null){
			OfferVO offerVO=new OfferVO();
			offerVO.setId(offerId);
			List<Offer> offerList=offerService.findListByVo(offerVO);
			if(offerList == null){
				return "没有报盘记录!";
			}
			Offer offer=offerList.get(0);
			if(!offer.getOfferType().equals(EnumConstants.OfferType.CURRENT_OFFER.getValue())){
				return "该笔交易不是实时报盘!";
			}
			if(offer.getStatus().equals(EnumConstants.OfferState.HAS_TO_OFFER.getValue())){
				return "该笔交易已报盘!";
			}
			
			//构造对象主体
			RequestVo requestVo = buildRequestVo();
			//构成报盘信息
			if (BizConstants.Allinpay.equals(offer.getTppType().toString())) { //通联异步转账
				buildBizTransferRequestVo(offer, requestVo,ThirdType.ALLINPAY.getCode());
			} else if (BizConstants.Fuiou.equals(offer.getTppType().toString())) {
				buildBizTransferRequestVo(offer, requestVo,ThirdType.FUIOUPAY.getCode());
			}else if(BizConstants.ChinaUnion.equals(offer.getTppType().toString())){
				buildBizTransferRequestVo(offer, requestVo,ThirdType.SHUNIONPAY.getCode());
			}else if(BizConstants.KJTpay.equals(offer.getTppType().toString())){
				buildBizTransferRequestVo(offer, requestVo,ThirdType.KJTPAY.getCode());
			}
			// 调用代收接口
			try {
				OfferVO  updateVO = new OfferVO();
				updateVO.setId(offerId);
				updateVO.setSendDate(new Date());
				updateVO.setStatus(EnumConstants.OfferState.HAS_TO_OFFER.getValue());
				updateVO.setRemark("已报盘");
				int result = offerService.update(updateVO);
				if(result == 0){
					logger.error("报盘Id: " + updateVO.getId() + "更新报盘记录状态失败!");
					return "更新报盘记录状态失败!";
				}
				Response response = brokerTradeConsumer.asynCollect(requestVo);
				
				String msgCode = response.getCode(); 
				logger.info(JobConstants.SEND_OFFER+"->发送给TPP的offerId: "+offer.getId()+",TPP返回的代码是: "+msgCode);
				logger.info(JobConstants.SEND_OFFER+"->发送给TPP的offerId: "+offer.getId()+",TPP返回的内容是: "+response.getMsg());
				SysLog sysLog = new SysLog();
				sysLog.setOptModule(EnumConstants.OptionModule.DEDUCTIONS_MANAGEMENT.getValue());
				sysLog.setOptType(EnumConstants.OptionType.DEDUCTIONS_SEND.getValue());
				sysLog.setRemark("loanId :" + offer.getLoanId().toString() + ", offerId :" + offer.getId().toString());
				// 插入系统日志
				sysLogService.insert(sysLog);
				if(TPPConstants.HANDLE_SUCCESS_CODE.equals(msgCode)){ //TBD--Success
					return "success";
				}else{
					OfferVO  updateVO2 = new OfferVO();
					updateVO2.setId(offerId);
					updateVO2.setSendDate(new Date());
					updateVO2.setStatus(EnumConstants.OfferState.NOT_OFFER.getValue());
					updateVO2.setRemark("未报盘,TPP返回的内容是: "+response.getMsg());
					int result2 = offerService.update(updateVO2);
					if(result2 == 0){
						logger.error("报盘Id: " + updateVO2.getId() + "更新报盘记录状态失败!");
						return "更新报盘记录状态失败!";
					}
				}
			} catch (Exception e) { 
				OfferVO  updateVO = new OfferVO();
				updateVO.setId(offerId);
				updateVO.setSendDate(new Date());
				updateVO.setStatus(EnumConstants.OfferState.NOT_OFFER.getValue());
				updateVO.setRemark("未报盘");
				int result = offerService.update(updateVO);
				if(result == 0){
					logger.error("报盘Id: " + updateVO.getId() + "更新报盘记录状态失败!");
					return "更新报盘记录状态失败!";
				}
				logger.error("[实时任务->"+JobConstants.SEND_OFFER+"]出现异常", e);
				return "失败原因: " + e;
			}
		}
		return "报盘失败";
	}
	
	/**
	 * 
	 * <pre>
	 * 批量发送相应的数据到TPP
	 * </pre>
	 *
	 * @param offerId
	 * @return String
	 * @throws UnknownHostException
	 */
	@RequestMapping("/plToSendTpp")
	@ResponseBody
	public String plSendOfferData(Long offerId) throws UnknownHostException {
//		logger.info(JobConstants.SEND_OFFER+"->开始处理的offerId: "+offer.getId());
		if(offerId!= null){
			OfferVO offerVO=new OfferVO();
			offerVO.setId(offerId);
			List<Offer> offerList=offerService.findListByVo(offerVO);
			if(offerList == null){
				return "没有报盘记录!";
			}
			Offer offer=offerList.get(0);
			if(!offer.getOfferType().equals(EnumConstants.OfferType.CURRENT_OFFER.getValue())){
				return "该笔交易不是实时报盘!";
			}
			if(offer.getStatus().equals(EnumConstants.OfferState.HAS_TO_OFFER.getValue())){
				return "该笔交易已报盘!";
			}
			
			//构造对象主体
			RequestVo requestVo = buildRequestVo();
			//构成报盘信息
			if (BizConstants.Allinpay.equals(offer.getTppType().toString())) { //通联异步转账
				buildBizTransferRequestVo(offer, requestVo,ThirdType.ALLINPAY.getCode());
			} else if (BizConstants.Fuiou.equals(offer.getTppType().toString())) {
				buildBizTransferRequestVo(offer, requestVo,ThirdType.FUIOUPAY.getCode());
			}else if(BizConstants.ChinaUnion.equals(offer.getTppType().toString())){
				buildBizTransferRequestVo(offer, requestVo,ThirdType.SHUNIONPAY.getCode());
			}else if(BizConstants.KJTpay.equals(offer.getTppType().toString())){
				buildBizTransferRequestVo(offer, requestVo,ThirdType.KJTPAY.getCode());
			}
			// 调用代收接口
			try {
				OfferVO  updateVO = new OfferVO();
				updateVO.setId(offerId);
				updateVO.setSendDate(new Date());
				updateVO.setStatus(EnumConstants.OfferState.HAS_TO_OFFER.getValue());
				updateVO.setRemark("已报盘");
				int result = offerService.update(updateVO);
				if(result == 0){
					logger.error("报盘Id: " + updateVO.getId() + "更新报盘记录状态失败!");
					return "更新报盘记录状态失败!";
				}
				Response response = brokerTradeConsumer.asynCollect(requestVo);
				
				String msgCode = response.getCode(); 
				logger.info(JobConstants.SEND_OFFER+"->发送给TPP的offerId: "+offer.getId()+",TPP返回的代码是: "+msgCode);
				logger.info(JobConstants.SEND_OFFER+"->发送给TPP的offerId: "+offer.getId()+",TPP返回的内容是: "+response.getMsg());
				SysLog sysLog = new SysLog();
				sysLog.setOptModule(EnumConstants.OptionModule.PL_DEDUCTIONS_MANAGEMENT.getValue());
				sysLog.setOptType(EnumConstants.OptionType.PL_DEDUCTIONS_SEND.getValue());
				sysLog.setRemark("loanId :" + offer.getLoanId().toString() + ", offerId :" + offer.getId().toString()+",批量实时划扣");
				// 插入系统日志
				sysLogService.insert(sysLog);
				if(TPPConstants.HANDLE_SUCCESS_CODE.equals(msgCode)){ //TBD--Success
					return "success";
				}else{
					OfferVO  updateVO2 = new OfferVO();
					updateVO2.setId(offerId);
					updateVO2.setSendDate(new Date());
					updateVO2.setStatus(EnumConstants.OfferState.NOT_OFFER.getValue());
					updateVO2.setRemark("未报盘,TPP返回的内容是: "+response.getMsg());
					int result2 = offerService.update(updateVO2);
					if(result2 == 0){
						logger.error("报盘Id: " + updateVO2.getId() + "更新报盘记录状态失败!");
						return "更新报盘记录状态失败!";
					}
				}
			} catch (Exception e) { 
				OfferVO  updateVO = new OfferVO();
				updateVO.setId(offerId);
				updateVO.setSendDate(new Date());
				updateVO.setStatus(EnumConstants.OfferState.NOT_OFFER.getValue());
				updateVO.setRemark("未报盘");
				int result = offerService.update(updateVO);
				if(result == 0){
					logger.error("报盘Id: " + updateVO.getId() + "更新报盘记录状态失败!");
					return "更新报盘记录状态失败!";
				}
				logger.error("[实时任务->"+JobConstants.SEND_OFFER+"]出现异常", e);
				return "失败原因: " + e;
			}
		}
		return "报盘失败";
	}

	/***
	 * 是否可修改金额
	 * @return
	 */
	@RequestMapping("/toEditMoney")
	@ResponseBody
	public Offer toEditMoney(Long id) {
		if (id != null) {
			Offer offer = deductionsManagementService.getOfferById(id);
			if(offer.getOfferType().equals(EnumConstants.OfferType.CURRENT_OFFER.getValue())&&offer.getStatus().equals(EnumConstants.OfferState.NOT_OFFER.getValue())){
				return offer;
			}else if(offer.getOfferType().equals(EnumConstants.OfferType.NORMAL_OFFER.getValue())&&checkGuarateeDate()){
				return offer;
			}else{
				return null;
			}
		} else {
			return null;
		}

	}

	/***
	 * 修改金额
	 * @return
	 */
	@RequestMapping("/editMoney")
	@ResponseBody
	public String editMoney(OfferVO offerVo) {
		if (offerVo != null && offerVo.getId()!=null) {
			Offer offer = deductionsManagementService.getOfferById(offerVo.getId());
			if(offer.getOfferType().equals(EnumConstants.OfferType.CURRENT_OFFER.getValue())&&offer.getStatus().equals(EnumConstants.OfferState.NOT_OFFER.getValue())){
				SysLog sysLog = new SysLog();
				sysLog.setOptModule(EnumConstants.OptionModule.DEDUCTIONS_MANAGEMENT.getValue());
				sysLog.setOptType(EnumConstants.OptionType.DEDUCTIONS_MONEY_EDIT.getValue());
				sysLog.setRemark("OfferId:"+Long.toString(offer.getId())+",修改实时报盘金额为+"+offerVo.getOfferAmount());
				// 插入系统日志
				sysLogService.insert(sysLog);
				if (deductionsManagementService.updateOfferAmount(offerVo) == 0) {
					return "error";
				} else {
					return "success";
				}
			}else if(offer.getOfferType().equals(EnumConstants.OfferType.NORMAL_OFFER.getValue())&&checkGuarateeDate()){
				SysLog sysLog = new SysLog();
				sysLog.setOptModule(EnumConstants.OptionModule.DEDUCTIONS_MANAGEMENT.getValue());
				sysLog.setOptType(EnumConstants.OptionType.DEDUCTIONS_MONEY_EDIT.getValue());
				sysLog.setRemark("OfferId:"+Long.toString(offer.getId())+",修改定时报盘金额为+"+offerVo.getOfferAmount());
				// 插入系统日志
				sysLogService.insert(sysLog);
				if (deductionsManagementService.updateOfferAmount(offerVo) == 0) {
					return "error";
				} else {
					return "success";
				}
			}else{
				return "error";
			}
		} else {
			return "error";
		}

	}
	
	
	/***
	 * 取消实时报盘
	 * @return
	 */
	@RequestMapping("/cannelCurrentOffer")
	@ResponseBody
	public String cannelCurrentOffer(OfferVO offerVo) {
		if (offerVo != null && offerVo.getId()!=null) {
			Offer offer = deductionsManagementService.getOfferById(offerVo.getId());
			if(offer.getOfferType().equals(EnumConstants.OfferType.CURRENT_OFFER.getValue())&&offer.getStatus().equals(EnumConstants.OfferState.NOT_OFFER.getValue())){
				SysLog sysLog = new SysLog();
				sysLog.setOptModule(EnumConstants.OptionModule.DEDUCTIONS_MANAGEMENT.getValue());
				sysLog.setOptType(EnumConstants.OptionType.DEDUCTIONS_MONEY_CANNEL.getValue());
				sysLog.setRemark("OfferId:"+Long.toString(offer.getId())+",取消实时报盘");
				// 插入系统日志
				sysLogService.insert(sysLog);
				offerVo.setStatus(EnumConstants.OfferState.FAIL_OFFER.getValue());
				if (deductionsManagementService.updateOfferAmount(offerVo) == 0) {
					return "error";
				} else {
					return "success";
				}
			}else{
				return "该笔报盘不可取消";
			}
		} else {
			return "error";
		}
	}

	/***
	 * 批量取消实时报盘
	 * @return
	 */
	@RequestMapping("/plCannelCurrentOffer")
	@ResponseBody
	public String plCannelCurrentOffer(OfferVO offerVo) {
		if (offerVo != null && offerVo.getId()!=null) {
			Offer offer = deductionsManagementService.getOfferById(offerVo.getId());
			if(offer.getOfferType().equals(EnumConstants.OfferType.CURRENT_OFFER.getValue())&&offer.getStatus().equals(EnumConstants.OfferState.NOT_OFFER.getValue())){
				SysLog sysLog = new SysLog();
				sysLog.setOptModule(EnumConstants.OptionModule.PL_DEDUCTIONS_MANAGEMENT.getValue());
				sysLog.setOptType(EnumConstants.OptionType.PL_DEDUCTIONS_MONEY_CANNEL.getValue());
				sysLog.setRemark("OfferId:"+Long.toString(offer.getId())+",批量取消实时报盘");
				// 插入系统日志
				sysLogService.insert(sysLog);
				offerVo.setStatus(EnumConstants.OfferState.FAIL_OFFER.getValue());
				if (deductionsManagementService.updateOfferAmount(offerVo) == 0) {
					return "error";
				} else {
					return "success";
				}
			}else{
				return "该笔报盘不可取消";
			}
		} else {
			return "error";
		}
	}
	/***
	 * 显示担保代扣借款人信息
	 * @return
	 */
	@RequestMapping("/toEditGuaratee")
	@ResponseBody
	public Offer toEditGuaratee(Long id) {
		if (checkGuarateeDate() && id != null) {
			Offer offer = deductionsManagementService.getOfferById(id);
			return offer;
		} else {
			return null;
		}

	}

	/***
	 * 显示担保代人信息
	 * @return
	 */
	@RequestMapping("/toGuaratee")
	@ResponseBody
	public List<Guarantee> toGuaratee(OfferVO offerVo) {
		if (checkGuarateeDate()) {
			List<Guarantee> guaranteeList = deductionsManagementService
				.findGuaranteeListByOfferVO(offerVo);
			return guaranteeList;
		} else {
			return null;
		}

	}

	/***
	 * 显示担保代人银行账户信息
	 * @return
	 */
	@RequestMapping("/toGuarateeBankAccount")
	@ResponseBody
	public BankAccount toGuarateeBankAccount(Long bankAccountId) {
		if (checkGuarateeDate() && bankAccountId != null) {
			BankAccount bankAccount = deductionsManagementService
				.getGuarateeBankAccountByBankAccountId(bankAccountId);
			return bankAccount;
		} else {
			return null;
		}

	}
	
	

	/***
	 * 担保代扣
	 * @return
	 */
	@RequestMapping("/editGuaratee")
	@ResponseBody
	public String editGuaratee(OfferVO offerVo) {
		if (checkGuarateeDate()) {
			Offer offer = deductionsManagementService.getOfferById(offerVo.getId());
			SysLog sysLog = new SysLog();
			sysLog.setOptModule(EnumConstants.OptionModule.DEDUCTIONS_MANAGEMENT.getValue());
			sysLog.setOptType(EnumConstants.OptionType.DEDUCTIONS_GUARATEE_EDIT.getValue());
			sysLog.setRemark("OfferId:"+Long.toString(offer.getId()));
			// 插入系统日志
			sysLogService.insert(sysLog);
			int result = deductionsManagementService.updateGuaratee(offerVo);
			if (result == 0) {
				return "error";
			} else {
				return "success";
			}
		} else {
			return "error";
		}

	}

	/***
	 * 校验担保日期是否合法
	 * @return true 合法  false  不合法
	 */
	private boolean checkGuarateeDate() {
		Calendar calendar = Calendar.getInstance();
		//生成报盘的时间
		Calendar guarateeCalendar1 = Calendar.getInstance();
		guarateeCalendar1.set(Calendar.HOUR_OF_DAY, 1);
		guarateeCalendar1.set(Calendar.MINUTE, 20);
		Calendar guarateeCalendar2 = Calendar.getInstance();
		guarateeCalendar2.set(Calendar.HOUR_OF_DAY, 10);
		guarateeCalendar2.set(Calendar.MINUTE, 20);
		Calendar guarateeCalendar3 = Calendar.getInstance();
		guarateeCalendar3.set(Calendar.HOUR_OF_DAY, 13);
		guarateeCalendar3.set(Calendar.MINUTE, 20);
		Calendar guarateeCalendar4 = Calendar.getInstance();
		guarateeCalendar4.set(Calendar.HOUR_OF_DAY, 16);
		guarateeCalendar4.set(Calendar.MINUTE, 20);
		Calendar guarateeCalendar5 = Calendar.getInstance();
		guarateeCalendar5.set(Calendar.HOUR_OF_DAY, 19);
		guarateeCalendar5.set(Calendar.MINUTE, 20);		//相差的分钟数
		long difference1 = (guarateeCalendar1.getTimeInMillis() - calendar.getTimeInMillis()) / 60000;
		long difference2 = (guarateeCalendar2.getTimeInMillis() - calendar.getTimeInMillis()) / 60000;
		long difference3 = (guarateeCalendar3.getTimeInMillis() - calendar.getTimeInMillis()) / 60000;
		long difference4 = (guarateeCalendar4.getTimeInMillis() - calendar.getTimeInMillis()) / 60000;
		long difference5 = (guarateeCalendar5.getTimeInMillis() - calendar.getTimeInMillis()) / 60000;

		if (difference1 > 0 && difference1 < 45 || difference2 > 0 && difference2 < 45
			|| difference3 > 0 && difference3 < 45 || difference4 > 0 && difference4 < 45 || difference5 > 0 && difference5 < 45) {
			return true;
		} else {
			return false;
		}
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}
	
	
	class ListedLoan {
		
		public ListedLoan(Long id,Long loanId, Integer status, Date requestDate, Long personId, String personNo, String personName, String personIdnum,  Integer productType,Long productId,BigDecimal pactMoney,Long time, Long salesDeptCode,
				String salesDeptName, Date submitDate, String operations, String serviceName, String managerName, String assessorName, BigDecimal requestMoney,Integer extensionTime,String productCode,BigDecimal auditMoney,String productName,Person person) {
		super();
		this.id =  id;
		this.loanId = loanId;
		this.status = status;
		this.requestDate = requestDate;
		this.personId = personId;
		this.personNo = personNo;
		this.personName = personName;
		this.personIdnum = personIdnum;
		this.productId = productId;
		this.salesDeptCode = salesDeptCode;
		this.salesDeptName = salesDeptName;
		this.submitDate = submitDate;
		this.operations = operations;
		this.serviceName = serviceName;
		this.managerName = managerName;
		this.assessorName = assessorName;
		this.requestMoney = requestMoney;
		this.extensionTime = extensionTime;
		this.productCode=productCode;
		this.auditMoney = auditMoney;
		this.procutName=productName;
		this.pactMoney = pactMoney;
		this.time=time;
		this.person=person;
		this.productType=productType;
		}
				
		private Long id;
		private Long loanId;
		private Integer status;
		private Date requestDate;
		private Long personId;
		private String personNo;
		private String personName;
		private String personIdnum;
		private Long productId;
		private Long salesDeptCode;
		private String salesDeptName;
		private String operations;
		private Date submitDate;
		private String serviceName;
		private String managerName;
		private String assessorName;
		private BigDecimal requestMoney;
		/** 展期第几次次数*/
		private Integer extensionTime;
		private String productCode;
		private BigDecimal auditMoney;
		private String procutName;
		private Long time;
		private BigDecimal pactMoney;
		private Person person;
		private Integer productType;



		public String getProcutName() {
			return procutName;
		}



		public void setProcutName(String procutName) {
			this.procutName = procutName;
		}



		public BigDecimal getAuditMoney() {
			return auditMoney;
		}



		public void setAuditMoney(BigDecimal auditMoney) {
			this.auditMoney = auditMoney;
		}



		public String getProductCode() {
			return productCode;
		}

		public void setProductCode(String productCode) {
			this.productCode = productCode;
		}
		public Integer getExtensionTime() {
			return extensionTime;
		}
		public void setExtensionTime(Integer extensionTime) {
			this.extensionTime = extensionTime;
		}
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Long getLoanId() {
			return loanId;
		}

		public void setLoanId(Long loanId) {
			this.loanId = loanId;
		}

		public Integer getStatus() {
			return status;
		}

		public void setStatus(Integer status) {
			this.status = status;
		}

		public Date getRequestDate() {
			return requestDate;
		}

		public void setRequestDate(Date requestDate) {
			this.requestDate = requestDate;
		}

		public Long getPersonId() {
			return personId;
		}

		public void setPersonId(Long personId) {
			this.personId = personId;
		}

		public String getPersonNo() {
			return personNo;
		}

		public void setPersonNo(String personNo) {
			this.personNo = personNo;
		}

		public String getPersonName() {
			return personName;
		}

		public void setPersonName(String personName) {
			this.personName = personName;
		}

		public String getPersonIdnum() {
			return personIdnum;
		}

		public void setPersonIdnum(String personIdnum) {
			this.personIdnum = personIdnum;
		}

		public Long getProductId() {
			return productId;
		}

		public void setProductId(Long productId) {
			this.productId = productId;
		}

		public Long getSalesDeptCode() {
			return salesDeptCode;
		}

		public void setSalesDeptCode(Long salesDeptCode) {
			this.salesDeptCode = salesDeptCode;
		}

		public String getSalesDeptName() {
			return salesDeptName;
		}

		public void setSalesDeptName(String salesDeptName) {
			this.salesDeptName = salesDeptName;
		}

		public String getOperations() {
			return operations;
		}

		public void setOperations(String operations) {
			this.operations = operations;
		}

		public Date getSubmitDate() {
			return submitDate;
		}

		public void setSubmitDate(Date submitDate) {
			this.submitDate = submitDate;
		}

		public String getServiceName() {
			return serviceName;
		}

		public void setServiceName(String serviceName) {
			this.serviceName = serviceName;
		}

		public String getManagerName() {
			return managerName;
		}

		public void setManagerName(String managerName) {
			this.managerName = managerName;
		}

		public String getAssessorName() {
			return assessorName;
		}

		public void setAssessorName(String assessorName) {
			this.assessorName = assessorName;
		}

		public BigDecimal getRequestMoney() {
			return requestMoney;
		}

		public void setRequestMoney(BigDecimal requestMoney) {
			this.requestMoney = requestMoney;
		}



		public Long getTime() {
			return time;
		}



		public void setTime(Long time) {
			this.time = time;
		}



		public BigDecimal getPactMoney() {
			return pactMoney;
		}



		public void setPactMoney(BigDecimal pactMoney) {
			this.pactMoney = pactMoney;
		}



		public Person getPerson() {
			return person;
		}



		public void setPerson(Person person) {
			this.person = person;
		}



		public Integer getProductType() {
			return productType;
		}



		public void setProductType(Integer productType) {
			this.productType = productType;
		}

	};
	
	
class plListedLoan {
		
		public plListedLoan(Long id,Long loanId, Integer status, Date requestDate, Long personId, String personNo, String personName, String personIdnum,  Integer productType,Long productId,BigDecimal pactMoney,Long time, Long salesDeptCode,
				String salesDeptName, Date submitDate, String operations, String serviceName, String managerName, String assessorName, BigDecimal requestMoney,Integer extensionTime,String productCode,BigDecimal auditMoney,String productName,Person person,String info) {
		super();
		this.id =  id;
		this.loanId = loanId;
		this.status = status;
		this.requestDate = requestDate;
		this.personId = personId;
		this.personNo = personNo;
		this.personName = personName;
		this.personIdnum = personIdnum;
		this.productId = productId;
		this.salesDeptCode = salesDeptCode;
		this.salesDeptName = salesDeptName;
		this.submitDate = submitDate;
		this.operations = operations;
		this.serviceName = serviceName;
		this.managerName = managerName;
		this.assessorName = assessorName;
		this.requestMoney = requestMoney;
		this.extensionTime = extensionTime;
		this.productCode=productCode;
		this.auditMoney = auditMoney;
		this.procutName=productName;
		this.pactMoney = pactMoney;
		this.time=time;
		this.person=person;
		this.productType=productType;
		this.info=info;
		}
				
		private Long id;
		private Long loanId;
		private Integer status;
		private Date requestDate;
		private Long personId;
		private String personNo;
		private String personName;
		private String personIdnum;
		private Long productId;
		private Long salesDeptCode;
		private String salesDeptName;
		private String operations;
		private Date submitDate;
		private String serviceName;
		private String managerName;
		private String assessorName;
		private BigDecimal requestMoney;
		/** 展期第几次次数*/
		private Integer extensionTime;
		private String productCode;
		private BigDecimal auditMoney;
		private String procutName;
		private Long time;
		private BigDecimal pactMoney;
		private Person person;
		private Integer productType;
		private String info;

		public String getInfo() {
			return info;
		}



		public void setInfo(String info) {
			this.info = info;
		}

		public String getProcutName() {
			return procutName;
		}



		public void setProcutName(String procutName) {
			this.procutName = procutName;
		}



		public BigDecimal getAuditMoney() {
			return auditMoney;
		}



		public void setAuditMoney(BigDecimal auditMoney) {
			this.auditMoney = auditMoney;
		}



		public String getProductCode() {
			return productCode;
		}

		public void setProductCode(String productCode) {
			this.productCode = productCode;
		}
		public Integer getExtensionTime() {
			return extensionTime;
		}
		public void setExtensionTime(Integer extensionTime) {
			this.extensionTime = extensionTime;
		}
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Long getLoanId() {
			return loanId;
		}

		public void setLoanId(Long loanId) {
			this.loanId = loanId;
		}

		public Integer getStatus() {
			return status;
		}

		public void setStatus(Integer status) {
			this.status = status;
		}

		public Date getRequestDate() {
			return requestDate;
		}

		public void setRequestDate(Date requestDate) {
			this.requestDate = requestDate;
		}

		public Long getPersonId() {
			return personId;
		}

		public void setPersonId(Long personId) {
			this.personId = personId;
		}

		public String getPersonNo() {
			return personNo;
		}

		public void setPersonNo(String personNo) {
			this.personNo = personNo;
		}

		public String getPersonName() {
			return personName;
		}

		public void setPersonName(String personName) {
			this.personName = personName;
		}

		public String getPersonIdnum() {
			return personIdnum;
		}

		public void setPersonIdnum(String personIdnum) {
			this.personIdnum = personIdnum;
		}

		public Long getProductId() {
			return productId;
		}

		public void setProductId(Long productId) {
			this.productId = productId;
		}

		public Long getSalesDeptCode() {
			return salesDeptCode;
		}

		public void setSalesDeptCode(Long salesDeptCode) {
			this.salesDeptCode = salesDeptCode;
		}

		public String getSalesDeptName() {
			return salesDeptName;
		}

		public void setSalesDeptName(String salesDeptName) {
			this.salesDeptName = salesDeptName;
		}

		public String getOperations() {
			return operations;
		}

		public void setOperations(String operations) {
			this.operations = operations;
		}

		public Date getSubmitDate() {
			return submitDate;
		}

		public void setSubmitDate(Date submitDate) {
			this.submitDate = submitDate;
		}

		public String getServiceName() {
			return serviceName;
		}

		public void setServiceName(String serviceName) {
			this.serviceName = serviceName;
		}

		public String getManagerName() {
			return managerName;
		}

		public void setManagerName(String managerName) {
			this.managerName = managerName;
		}

		public String getAssessorName() {
			return assessorName;
		}

		public void setAssessorName(String assessorName) {
			this.assessorName = assessorName;
		}

		public BigDecimal getRequestMoney() {
			return requestMoney;
		}

		public void setRequestMoney(BigDecimal requestMoney) {
			this.requestMoney = requestMoney;
		}



		public Long getTime() {
			return time;
		}



		public void setTime(Long time) {
			this.time = time;
		}



		public BigDecimal getPactMoney() {
			return pactMoney;
		}



		public void setPactMoney(BigDecimal pactMoney) {
			this.pactMoney = pactMoney;
		}



		public Person getPerson() {
			return person;
		}



		public void setPerson(Person person) {
			this.person = person;
		}



		public Integer getProductType() {
			return productType;
		}



		public void setProductType(Integer productType) {
			this.productType = productType;
		}

	};
	
	@SuppressWarnings("deprecation")
	public boolean checkDate(){
		List<OfferManager> list = offerManagerService.getOfferListByStatus();
		if(list!=null&&list.size()>0){
			for(OfferManager om : list){
				int before = om.getBeforeDay();
				int after = om.getAfterDay();
				String geneTime[] = om.getGenerateTime().split(":");
				String sendTime[] = om.getSendTime().split(":");
				Date date = new Date();
				for(int i=0;i<=before;i++){//前几天
					//8点之前
					Calendar cal8 = Calendar.getInstance();
					cal8.set(Calendar.DATE,om.getDay());
					cal8.add(Calendar.DATE,-i);
					Date now8 = cal8.getTime();
					cal8.set(Calendar.HOUR_OF_DAY, 8);
					cal8.set(Calendar.MINUTE,  0);
					if(now8.getTime()-cal8.getTime().getTime()<0){
						return true;
					}
					
					//21点之后
					Calendar cal21 = Calendar.getInstance();
					cal21.set(Calendar.DATE,om.getDay());
					cal21.add(Calendar.DATE,-i);
					Date now21 = cal21.getTime();
					cal21.set(Calendar.HOUR_OF_DAY, 21);
					cal21.set(Calendar.MINUTE,  0);
					if(now21.getTime()-cal21.getTime().getTime()>0){
						return true;
					}
					
					//生成时间前后15分钟   非当天跳出本次如果是当天验证是否在前后15分钟
					Calendar genBefore = Calendar.getInstance();
					genBefore.set(Calendar.DATE,om.getDay());
					genBefore.add(Calendar.MONTH,1);
					genBefore.add(Calendar.DATE,-i);
					Date nowBefore = genBefore.getTime();
					genBefore.set(Calendar.HOUR_OF_DAY, Integer.parseInt(geneTime[0]));
					genBefore.set(Calendar.MINUTE,  (Integer.parseInt(geneTime[1])-16));
					
					Calendar genAfter = Calendar.getInstance();
					genAfter.set(Calendar.DATE,om.getDay());
					genAfter.add(Calendar.MONTH,1);
					genAfter.add(Calendar.DATE,-i);
					Date nowAfter = genAfter.getTime();
					genAfter.set(Calendar.HOUR_OF_DAY, Integer.parseInt(geneTime[0]));
					genAfter.set(Calendar.MINUTE,  (Integer.parseInt(geneTime[1])+15));
					
					if(date.getDate()!=genBefore.get(Calendar.DATE)){
						continue;
					}else{
 						if(nowBefore.getTime()-genBefore.getTime().getTime()>0&&nowAfter.getTime()-genAfter.getTime().getTime()<=0){
							return true;
						}
					}
					
					//发送时间前后15分钟   非当天跳出本次如果是当天验证是否在前后15分钟
					Calendar sendBefore = Calendar.getInstance();
					sendBefore.add(Calendar.MONTH,1);
					sendBefore.set(Calendar.DATE,om.getDay());
					sendBefore.add(Calendar.DATE,-i);
					Date nowSBefore = sendBefore.getTime();
					sendBefore.set(Calendar.HOUR_OF_DAY, Integer.parseInt(sendTime[0]));
					sendBefore.set(Calendar.MINUTE,  (Integer.parseInt(sendTime[1])-16));
					
					Calendar sendAfter = Calendar.getInstance();
					sendAfter.set(Calendar.DATE,om.getDay());
					sendAfter.add(Calendar.MONTH,1);
					sendAfter.add(Calendar.DATE,-i);
					Date nowSAfter = sendAfter.getTime();
					sendAfter.set(Calendar.HOUR_OF_DAY, Integer.parseInt(sendTime[0]));
					sendAfter.set(Calendar.MINUTE,  (Integer.parseInt(sendTime[1])+15));
					
					if(date.getDate()!=sendAfter.get(Calendar.DATE)){
						continue;
					}else{
						if(nowSBefore.getTime()-sendBefore.getTime().getTime()>0&&nowSAfter.getTime()-sendAfter.getTime().getTime()<=0){
							return true;
						}
					}
					
				}
				for(int i=0;i<=after;i++){//后几天
					//8点之前
					Calendar cal8 = Calendar.getInstance();
					cal8.set(Calendar.DATE,om.getDay());
					cal8.add(Calendar.DATE,i);
					Date now8 = cal8.getTime();
					cal8.set(Calendar.HOUR_OF_DAY, 8);
					cal8.set(Calendar.MINUTE,  0);
					if(now8.getTime()-cal8.getTime().getTime()<0){
						return true;
					}
					
					//21点之后
					Calendar cal21 = Calendar.getInstance();
					cal8.set(Calendar.DATE,om.getDay());
					cal21.add(Calendar.DATE,i);
					Date now21 = cal21.getTime();
					cal21.set(Calendar.HOUR_OF_DAY, 21);
					cal21.set(Calendar.MINUTE,  0);
					if(now21.getTime()-cal21.getTime().getTime()>0){
						return true;
					}
					
					//生成时间前后15分钟   非当天跳出本次如果是当天验证是否在前后15分钟
					Calendar genBefore = Calendar.getInstance();
					genBefore.set(Calendar.DATE,om.getDay());
					genBefore.add(Calendar.MONTH,1);
					genBefore.add(Calendar.DATE,i);
					Date nowBefore = genBefore.getTime();
					genBefore.set(Calendar.HOUR_OF_DAY, Integer.parseInt(geneTime[0]));
					genBefore.set(Calendar.MINUTE,  (Integer.parseInt(geneTime[1])-16));
					
					Calendar genAfter = Calendar.getInstance();
					genAfter.set(Calendar.DATE,om.getDay());
					genAfter.add(Calendar.MONTH,1);
					genAfter.add(Calendar.DATE,i);
					Date nowAfter = genAfter.getTime();
					genAfter.set(Calendar.HOUR_OF_DAY, Integer.parseInt(geneTime[0]));
					genAfter.set(Calendar.MINUTE,  (Integer.parseInt(geneTime[1])+15));

					
					if(date.getDate()!=genBefore.get(Calendar.DATE)){
						continue;
					}else{
						if(nowBefore.getTime()-genBefore.getTime().getTime()>0&&nowAfter.getTime()-genAfter.getTime().getTime()<=0){
							return true;
						}
					}
					
					//发送时间前后15分钟   非当天跳出本次如果是当天验证是否在前后15分钟
					Calendar sendBefore = Calendar.getInstance();
					sendBefore.set(Calendar.DATE,om.getDay());
					sendBefore.add(Calendar.MONTH,1);
					sendBefore.add(Calendar.DATE,i);
					Date nowSBefore = sendBefore.getTime();
					sendBefore.set(Calendar.HOUR_OF_DAY, Integer.parseInt(sendTime[0]));
					sendBefore.set(Calendar.MINUTE,  (Integer.parseInt(sendTime[1])-16));
					
					Calendar sendAfter = Calendar.getInstance();
					sendAfter.set(Calendar.DATE,om.getDay());
					sendAfter.add(Calendar.MONTH,1);
					sendAfter.add(Calendar.DATE,i);
					Date nowSAfter = sendAfter.getTime();
					sendAfter.set(Calendar.HOUR_OF_DAY, Integer.parseInt(sendTime[0]));
					sendAfter.set(Calendar.MINUTE,  (Integer.parseInt(sendTime[1])+15));
					
					if(date.getDate()!=genBefore.get(Calendar.DATE)){
						continue;
					}else{
						if(nowSBefore.getTime()-sendBefore.getTime().getTime()>0&&nowSAfter.getTime()-sendAfter.getTime().getTime()<=0){
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	

	
    /**
     * 
     * <pre>
     * 构建BizTransferRequestVo详细信息
     * </pre>
     *
     * @param offer
     * @param requestVo
     * @param tppType TODO
     */
	private void buildBizTransferRequestVo(Offer offer, RequestVo requestVo, String tppType) {
		CollectReqVo collectReqVo = null;
		if(offer.getBusinessCompany()!=null && offer.getBusinessCompany().getBusinessCode()!=null){
			requestVo.setInfoCategoryCode(offer.getBusinessCompany().getBusinessCode().trim());//信息类别编码
		}
		SysParameter infoCategoryCodeParameter=sysParameterService.getByCode(SysParameterEnum.INFO_CATEGORY_CODE_REALTIME.name());
		List<RepaymentPlan>  reList=repaymentPlanService.queryRepaymentPlans(offer.getLoanId());
		RepaymentPlan repayPlan=null;
		if(reList.size()>0 || reList!=null){
			 repayPlan=reList.get(reList.size()-1);
		}
		requestVo.setInfoCategoryCode(infoCategoryCodeParameter.getParameterValue());
		for (int i = 1; i <= 1; i++) {
			collectReqVo = new CollectReqVo();
			collectReqVo.setBizSysAccountNo("");
			collectReqVo.setZengdaiAccountNo("10000001");
			collectReqVo.setPayerName(offer.getPersonName());// 付款方姓名
			collectReqVo.setPayerBankCardNo(offer.getBankAccount());// 付款方银行卡号
			collectReqVo.setPayerBankCardType("1");// 付款方银行卡类型 1.借记卡，2信用卡
			collectReqVo.setPayerIdType("0");// 付款方证件类型
			collectReqVo.setPayerId(offer.getPerson().getIdnum());// 付款方证件号
			collectReqVo.setPayerBankCode(offer.getBank().getTppBankCode());// 付款方银行编码
			collectReqVo.setPayerSubBankCode("");// 付款方银行支行行号
			collectReqVo.setPayerMobile(offer.getPerson().getMobilePhone());//付款方式手机号
			collectReqVo.setCurrency("0");// 币种(0人民币)
			collectReqVo.setAmount(offer.getOfferAmount());// 金额
			collectReqVo.setBizFlow(offer.getId().toString());// 业务流水号
			SysParameter accValue=sysParameterService.getByCode(SysParameterEnum.OFFER_SPLIT_SINGLE.name());
			logger.info("查询当前借款最后一期的的还款状态："+repayPlan.getStatus()+"还款金额"+repayPlan.getOneTimeRepaymentAmount()+","+repayPlan.getDeficit()+"借款id"+repayPlan.getLoanId());
			if(!StringUtil.isEmpty(accValue.getParameterValue())){
				if(accValue.getParameterValue().equals("0")){
					if(repayPlan.getStatus().equals(EnumConstants.RepaymentPlanState.NOT_REPAYMENT.getValue()) && repayPlan.getOneTimeRepaymentAmount().compareTo(repayPlan.getDeficit())==1){
						collectReqVo.setIsNeedSpilt(0);
					}else{
						collectReqVo.setIsNeedSpilt(1); //报盘是否需要拆单(限额超过后)0不需要拆单1需要拆单	
					}	
				}else{
					collectReqVo.setIsNeedSpilt(1); //报盘是否需要拆单(限额超过后)0不需要拆单1需要拆单		
				}
			}else{
				if(repayPlan.getStatus().equals(EnumConstants.RepaymentPlanState.NOT_REPAYMENT.getValue()) && repayPlan.getOneTimeRepaymentAmount().compareTo(repayPlan.getDeficit())==1){
					collectReqVo.setIsNeedSpilt(0);
				}else{
					collectReqVo.setIsNeedSpilt(1); //报盘是否需要拆单(限额超过后)0不需要拆单1需要拆单	
				}
			}
			//collectReqVo.setIsNeedSpilt(1); //报盘是否需要拆单(限额超过后)0不需要拆单1需要拆单
			if (ThirdType.ALLINPAY.getCode().equals(tppType)) {
				SysParameter accountNo=sysParameterService.getByCode(SysParameterEnum.TPP_ACCOUNT_NO.name());
				SysParameter accountName=sysParameterService.getByCode(SysParameterEnum.TPP_ACCOUNT_NAME.name());
				if(!StringUtil.isEmpty(accountNo.getParameterValue())){
					collectReqVo.setReceiverAccountNo(accountNo.getParameterValue()); // 收款方账户编号
				}
				if(!StringUtil.isEmpty(accountNo.getParameterValue())){
					collectReqVo.setReveiverAccountName(accountName.getParameterValue());// 收款方姓名
				}
				collectReqVo.setPaySysNo(ThirdType.ALLINPAY.getCode());
			}else if(ThirdType.FUIOUPAY.getCode().equals(tppType)){
				SysParameter accountNo=sysParameterService.getByCode(SysParameterEnum.TPP_ACCOUNT_NO.name());
				SysParameter accountName=sysParameterService.getByCode(SysParameterEnum.TPP_ACCOUNT_NAME.name());
				if(!StringUtil.isEmpty(accountNo.getParameterValue())){
					collectReqVo.setReceiverAccountNo(accountNo.getParameterValue()); // 收款方账户编号
				}
				if(!StringUtil.isEmpty(accountNo.getParameterValue())){
					collectReqVo.setReveiverAccountName(accountName.getParameterValue());// 收款方姓名
				}
				collectReqVo.setPaySysNo(ThirdType.FUIOUPAY.getCode());
			}else if(ThirdType.SHUNIONPAY.getCode().equals(tppType)){
				SysParameter accountNo=sysParameterService.getByCode(SysParameterEnum.TPP_ACCOUNT_NO.name());
				SysParameter accountName=sysParameterService.getByCode(SysParameterEnum.TPP_ACCOUNT_NAME.name());
				if(!StringUtil.isEmpty(accountNo.getParameterValue())){
					collectReqVo.setReceiverAccountNo(accountNo.getParameterValue()); // 收款方账户编号
				}
				if(!StringUtil.isEmpty(accountNo.getParameterValue())){
					collectReqVo.setReveiverAccountName(accountName.getParameterValue());// 收款方姓名
				}
				collectReqVo.setPaySysNo(ThirdType.SHUNIONPAY.getCode());
			}else if(ThirdType.YONGYOUUNIONPAY.getCode().equals(tppType)){
				SysParameter accountNo=sysParameterService.getByCode(SysParameterEnum.TPP_ACCOUNT_NO_YONGYOU.name());
				SysParameter accountName=sysParameterService.getByCode(SysParameterEnum.TPP_ACCOUNT_NAME.name());
				if(!StringUtil.isEmpty(accountNo.getParameterValue())){
					collectReqVo.setReceiverAccountNo(accountNo.getParameterValue()); // 收款方账户编号
				}
				if(!StringUtil.isEmpty(accountNo.getParameterValue())){
					collectReqVo.setReveiverAccountName(accountName.getParameterValue());// 收款方姓名
				}
				collectReqVo.setPaySysNo(ThirdType.YONGYOUUNIONPAY.getCode());
			}
			else if(ThirdType.KJTPAY.getCode().equals(tppType)){
				SysParameter accountNo=sysParameterService.getByCode(SysParameterEnum.TPP_ACCOUNT_NO_YONGYOU.name());
				SysParameter accountName=sysParameterService.getByCode(SysParameterEnum.TPP_ACCOUNT_NAME.name());
				if(!StringUtil.isEmpty(accountNo.getParameterValue())){
					collectReqVo.setReceiverAccountNo(accountNo.getParameterValue()); // 收款方账户编号
				}
				if(!StringUtil.isEmpty(accountNo.getParameterValue())){
					collectReqVo.setReveiverAccountName(accountName.getParameterValue());// 收款方姓名
				}
				collectReqVo.setPaySysNo(ThirdType.KJTPAY.getCode());
			}
			requestVo.getList().add(collectReqVo);
		}
	}
	
    /**
     * 
     * <pre>
     * 构造RequestVo
     * </pre>
     *
     * @param tppType
     * @return
     * @throws UnknownHostException
     */
	private RequestVo buildRequestVo() throws UnknownHostException {
		RequestVo requestVo = new RequestVo();
		SysParameter infoCategoryCodeParameter=sysParameterService.getByCode(SysParameterEnum.INFO_CATEGORY_CODE.name());
		requestVo.setBizSysNo(BizSys.ZENDAI_2018_SYS.getCode());//业务系统编码
		requestVo.setInfoCategoryCode(infoCategoryCodeParameter.getParameterValue());//信息类别编码
		requestVo.setRemark("");
		requestVo.setSpare1("");
		requestVo.setSpare2("");
		return requestVo;
	}
	
	
	/***
	 * 
	 * <pre>
	 * 获取TPP_TYPE类型
	 * </pre>
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("/getTppType")
	@ResponseBody
	public List<SysEnumerate> getTppType() {
		List<SysEnumerate> sysEnumList = sysEnumerateService.findSysEnumerateListByType(EnumConstants.TPP_TYPE);
		return sysEnumList;
	}
	
	
	
	/***
	 * 修改渠道
	 * @return
	 */
	@RequestMapping("/changeOfferTpp")
	@ResponseBody
	public String changeOfferTpp(OfferVO offerVo) {
		if (offerVo != null && offerVo.getIdList()!=null && offerVo.getTppType()!=null) {
				List<Long> idList=new ArrayList<Long>();
				idList=offerVo.getIdList();
				offerVo.setIdList(idList);
				SysLog sysLog = new SysLog();
				sysLog.setOptModule(EnumConstants.OptionModule.DEDUCTIONS_MANAGEMENT.getValue());
				sysLog.setOptType(EnumConstants.OptionType.DEDUCTIONS_CHANGE_TPP.getValue());
				sysLog.setRemark("OfferId:"+offerVo.getIdList().toString()+",变更渠道为"+offerVo.getTppType());
				// 插入系统日志
				sysLogService.insert(sysLog);
				try{
					if (deductionsManagementService.updateOfferTpp(offerVo) == 0) {
						return "未找到要更新的数据";
					} else {
						return "success";
					}
				}catch(Exception e){
					return e.getMessage();
				}
		} else {
			return "请检查数据";
		}
	}
	
	@Autowired
	private OfferManagerServcie offerManagerService;
	
	
	@RequestMapping("/plList")
	public String plInit(HttpServletRequest request) {
		try {
			//查找用户类型
			Long userId = ApplicationContext.getUser().getId();
			UserSession user = ApplicationContext.getUser();
			SysUser sysUser = sysUserService.get(userId);
			request.setAttribute("userType",
					EnumConstants.UserType.FINANCE.getValue());
			List<Product> productsList = productService
					.findProductTypeByUserId(userId);
			if (sysUser.getUserType().equals(
					EnumConstants.UserType.CUSTOMER_SERVICE.getValue())
					|| sysUser.getUserType().equals(
							EnumConstants.UserType.STORE_ASSISTANT_MANAGER
									.getValue())
					|| sysUser.getUserType().equals(
							EnumConstants.UserType.STORE_MANAGER.getValue())) {// 客服6 ，经理2，副理3 

				request.setAttribute("userType",
						EnumConstants.UserType.STORE_ASSISTANT_MANAGER
								.getValue());
				//用户所属的产品类型
				for (Product product : productsList) {
					if (product.getProductType().compareTo(
							EnumConstants.ProductType.SE_LOAN.getValue()) == 0) {
						request.setAttribute("userType",
								EnumConstants.UserType.CUSTOMER_SERVICE
										.getValue());
					}
				}
			} else if (sysUser.getUserType().equals(
					EnumConstants.UserType.AUDIT.getValue())) {//事业部 7

				request.setAttribute("userType",
						EnumConstants.UserType.AUDIT.getValue());

			} else if (sysUser.getUserType().equals(
					EnumConstants.UserType.SYSTEM_ADMIN.getValue())) {

				request.setAttribute("userType",
						EnumConstants.UserType.SYSTEM_ADMIN.getValue());
			}
			//设置code值到request中
			request.setAttribute("code", sysUser.getCode());
			//得到车贷拥有操作实时划扣按钮的员工，从缓存中获取数据
			String carLoanEditNO = sysParameterService.getByCode(
					"CAR_LOAN_EDIT_EMPLNO").getParameterValue();
			//得到企贷拥有操作实时划扣按钮的员工,从缓存中获取数据
			String enterpriseLoanEditNo = sysParameterService.getByCode(
					"ENTERPRISE_LOAN_EDIT_EMPLNO").getParameterValue();
			boolean ifEditCarLoan = false;
			//员工是否拥有操作实时划扣的权限(车贷)
			if (carLoanEditNO != null || enterpriseLoanEditNo != null) {
				//先置为false
				ifEditCarLoan = false;
				if (carLoanEditNO.contains(user.getLoginName())
						|| enterpriseLoanEditNo.contains(user.getLoginName())) {
					ifEditCarLoan = true;
				}
			}
			if (user.getLoginName().equals("admin")) {
				ifEditCarLoan = true;
			}
			request.setAttribute("ifEditCarLoan", ifEditCarLoan);
			//设置生成报盘的开始结束时间 为当天 0时0分0秒到24点  结束时间为当天23:59:59
			request.setAttribute("startDate",
					DateUtil.defaultFormatDay(DateUtil.getToday()));
			request.setAttribute("endDate",
					DateUtil.defaultFormatDay(DateUtil.getToday()));
			//设置参数用来判断用户是否是王丽园所在组/朱雪娇所在组的成员
			SysGroupVO sysGroupVO = new SysGroupVO();
			sysGroupVO.setCode(sysUser.getCode());
			
			List<SysGroup> sysGroupList = sysGroupService.findGroupByUserId(userId);
			request.setAttribute("financeGroup","");
			if(sysGroupList != null && sysGroupList.size()>0){
				for(SysGroup sysgroup : sysGroupList){
					if(sysgroup.getId().equals(EnumConstants.DeductionsPower.isFinanceAuditDraw.getValue())
							||sysgroup.getId().equals(EnumConstants.DeductionsPower.isBusaccountPenalty.getValue())){
						request.setAttribute("financeGroup",sysgroup.getId());
						break;
					}
				}
			}
			
			
			// 设置数据字典 
			setDataDictionaryArr(new String[] { EnumConstants.OFFER_STATE,
					EnumConstants.HAS_GUARANTEE, EnumConstants.PRODUCT_ID,
					EnumConstants.TPP_TYPE });
			request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/after/deductionsManagement/plDeductionsManagementList";
	}
	
	@RequestMapping("/plListImmediately")
	public String plInitImmediately(HttpServletRequest request) {
		//查找用户类型
//		Long userId = ApplicationContext.getUser().getId();
//		SysUser sysUser = sysUserService.get(userId);
//		request.setAttribute("userType", EnumConstants.UserType.FINANCE.getValue());
//		List<Product> productsList = productService.findProductTypeByUserId(userId);
		//设置生成报盘的开始结束时间 为当天 0时0分0秒到24点  结束时间为当天23:59:59
		request.setAttribute("startDate",DateUtil.defaultFormatDate(DateUtil.getToday()));
		request.setAttribute("endDate", DateUtil.defaultFormatDate(DateUtil.getEndTime()));
		// 设置数据字典 
		setDataDictionaryArr(new String[] { EnumConstants.LOAN_STATUS, 
											EnumConstants.PRODUCT_ID,EnumConstants.REPAYMENT_PLAN_STATE });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		return "/after/deductionsManagement/plDeductionsImmediately";
	}
	
	
	/**
	 * 
	 * <pre>
	 * 获取当前用户所在的城市,通常只有一个可返回的城市，
	 * 如果用户是admin，则可以返回所有城市的
	 * </pre>
	 *
	 * @return
	 */
	@RequestMapping("/getCurCity")
	@ResponseBody
	public List<BaseArea> getCurCity() {
		List<BaseArea> citys = new ArrayList<BaseArea>();
		//Long userId = ApplicationContext.getUser().getId();
		Integer userType = ApplicationContext.getUser().getUserType();
		if (userType == null)
			return null;
		// 判断当时admin账户或者是 催收权限的用户类型的时候城市取所有的
		if (userType.equals(EnumConstants.UserType.SYSTEM_ADMIN.getValue()) || userType.equals(EnumConstants.UserType.AUDIT.getValue())) {
			BaseAreaVO baseAreaVO = new BaseAreaVO();
			baseAreaVO.setIdentifier(BizConstants.CREDIT2_CITY);
			List<BaseArea> allCitys = baseAreaService.findListByVo(baseAreaVO);
//			BaseArea baseArea = new BaseArea();
//			baseArea.setId(null);
//			baseArea.setName("全部");
//			allCitys.add(0, baseArea);
			return allCitys;
		}
		BaseArea curCity = sysUserService.getCurCity();
		if (curCity != null) {
			citys.add(curCity);
		}
		return null;
	}
	
	@RequestMapping("/getLoanIdOfferCount")
	@ResponseBody
	public String getLoanIdOfferCount(OfferVO offerVo){
		Integer count=offerService.count(offerVo);
	if(count>0){
		return "已生成";
	}
	return "未生成";
	}
	
}
