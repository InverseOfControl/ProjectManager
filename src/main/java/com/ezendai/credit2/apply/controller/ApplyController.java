package com.ezendai.credit2.apply.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.SocketException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ezendai.credit2.common.util.HttpUtils;
import com.ezendai.credit2.sign.lcb.service.ZonganRiskService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ezendai.credit2.apply.assembler.CompanyAssembler;
import com.ezendai.credit2.apply.assembler.GuaranteeAssembler;
import com.ezendai.credit2.apply.assembler.LoanAssembler;
import com.ezendai.credit2.apply.assembler.PersonAssembler;
import com.ezendai.credit2.apply.assembler.PersonTrainingAssembler;
import com.ezendai.credit2.apply.assembler.VehicleAssembler;
import com.ezendai.credit2.apply.model.BusinessLog;
import com.ezendai.credit2.apply.model.ChannelPlanCheck;
import com.ezendai.credit2.apply.model.Company;
import com.ezendai.credit2.apply.model.Contacter;
import com.ezendai.credit2.apply.model.CreditHistory;
import com.ezendai.credit2.apply.model.EduLoanImage;
import com.ezendai.credit2.apply.model.Extension;
import com.ezendai.credit2.apply.model.Guarantee;
import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.model.LoanExtension;
import com.ezendai.credit2.apply.model.Person;
import com.ezendai.credit2.apply.model.PersonTraining;
import com.ezendai.credit2.apply.model.Product;
import com.ezendai.credit2.apply.model.ProductDetail;
import com.ezendai.credit2.apply.model.Vehicle;
import com.ezendai.credit2.apply.service.BusinessLogService;
import com.ezendai.credit2.apply.service.ChannelPlanCheckService;
import com.ezendai.credit2.apply.service.ContacterService;
import com.ezendai.credit2.apply.service.CreditHistoryService;
import com.ezendai.credit2.apply.service.EduLoanImageService;
import com.ezendai.credit2.apply.service.ExtensionService;
import com.ezendai.credit2.apply.service.GuaranteeService;
import com.ezendai.credit2.apply.service.LoanExtensionService;
import com.ezendai.credit2.apply.service.LoanService;
import com.ezendai.credit2.apply.service.PersonCompanyService;
import com.ezendai.credit2.apply.service.PersonService;
import com.ezendai.credit2.apply.service.PersonTrainingService;
import com.ezendai.credit2.apply.service.ProductDetailService;
import com.ezendai.credit2.apply.service.ProductService;
import com.ezendai.credit2.apply.service.VehicleService;
import com.ezendai.credit2.apply.util.FTPUtil;
import com.ezendai.credit2.apply.util.FileDownUtils;
import com.ezendai.credit2.apply.vo.BusinessLogVO;
import com.ezendai.credit2.apply.vo.CompanyVO;
import com.ezendai.credit2.apply.vo.ExtensionVO;
import com.ezendai.credit2.apply.vo.GuaranteeVO;
import com.ezendai.credit2.apply.vo.LoanDetailsVO;
import com.ezendai.credit2.apply.vo.LoanExtensionVO;
import com.ezendai.credit2.apply.vo.LoanVO;
import com.ezendai.credit2.apply.vo.PersonTrainingVO;
import com.ezendai.credit2.apply.vo.PersonVO;
import com.ezendai.credit2.apply.vo.ProductDetailVO;
import com.ezendai.credit2.apply.vo.VehicleVO;
import com.ezendai.credit2.audit.model.ApproveResult;
import com.ezendai.credit2.audit.model.BusinessLogView;
import com.ezendai.credit2.audit.service.ApproveResultService;
import com.ezendai.credit2.audit.vo.ApproveResultVO;
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
import com.ezendai.credit2.master.enumerate.EnumConstants.LoanStatus;
import com.ezendai.credit2.master.enumerate.EnumConstants.LoanStatusByStore;
import com.ezendai.credit2.master.enumerate.EnumConstants.SerialNum;
import com.ezendai.credit2.master.model.BaseArea;
import com.ezendai.credit2.master.service.BaseAreaService;
import com.ezendai.credit2.master.service.BlacklistService;
import com.ezendai.credit2.master.service.SalesDepartmentService;
import com.ezendai.credit2.master.vo.BaseAreaVO;
import com.ezendai.credit2.system.Credit2Properties;
import com.ezendai.credit2.system.controller.BaseController;
import com.ezendai.credit2.system.enumerate.SysParameterEnum;
import com.ezendai.credit2.system.model.Organ;
import com.ezendai.credit2.system.model.OrganSalesDepartment;
import com.ezendai.credit2.system.model.OrganSalesManager;
import com.ezendai.credit2.system.model.SysGroupUser;
import com.ezendai.credit2.system.model.SysLog;
import com.ezendai.credit2.system.model.SysParameter;
import com.ezendai.credit2.system.model.SysUser;
import com.ezendai.credit2.system.service.OrganSalesDepartmentService;
import com.ezendai.credit2.system.service.OrganSalesManagerService;
import com.ezendai.credit2.system.service.OrganService;
import com.ezendai.credit2.system.service.SysGroupUserService;
import com.ezendai.credit2.system.service.SysLogService;
import com.ezendai.credit2.system.service.SysParameterService;
import com.ezendai.credit2.system.service.SysUserService;
import com.ezendai.credit2.system.vo.OrganSalesDepartmentVO;
import com.ezendai.credit2.system.vo.OrganSalesManagerVO;
import com.ezendai.credit2.system.vo.SysGroupUserVO;
import com.ezendai.credit2.system.vo.SysUserVO;

@Controller
@RequestMapping("/apply")
public class ApplyController extends BaseController {

	@Autowired
	private VehicleService vehicleService;
	@Autowired
	private ApproveResultService approveResultService;
	@Autowired
	private ProductService productService;
	@Autowired
	private PersonService personService;
	@Autowired
	private PersonCompanyService personCompanyService;
	@Autowired
	private ContacterService contacterService;
	@Autowired
	private GuaranteeService guaranteeService;
	@Autowired
	private LoanService loanService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private CreditHistoryService creditHistoryService;
	@Autowired
	private SalesDepartmentService salesDeptService;
	@Autowired
	private BaseAreaService baseAreaService;
	@Autowired
	private SysLogService sysLogService;
	@Autowired
	private BlacklistService blacklistService;
	@Autowired
	private SysParameterService sysParameterService;
	@Autowired
	private ExtensionService extensionService;
	@Autowired
	private LoanExtensionService loanExtensionService;
	@Autowired
	private ProductDetailService productDetailService;
	@Autowired
	private BusinessLogService businessLogService;
	
	@Autowired
	private PersonTrainingService personTrainingService;
	
	@Autowired
	private OrganSalesDepartmentService organSalesDepartmentService;
	@Autowired
	private OrganService organService;
	
	@Autowired
	ChannelPlanCheckService channelPlanCheckService;
	@Autowired
	OrganSalesManagerService organSalesManagerService;
	@Autowired
	EduLoanImageService eduLoanImageService ;
	@Autowired
	private Credit2Properties credit2Properties;
	@Autowired
	private SysGroupUserService  sysGroupUserService;
	private static final Logger logger = Logger.getLogger(ApplyController.class);

	@Autowired
	private ZonganRiskService zonganRiskService;

    @Value("${hexinInfo}")
    private String hexinInfo;

	public String getHexinInfo() {
		return hexinInfo;
	}

	public void setHexinInfo(String hexinInfo) {
		this.hexinInfo = hexinInfo;
	}

	/**
	 * 贷款申请主界面
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/applyMain")
	public String viewList(HttpServletRequest request) {
		/* 设置数据字典 */
		setDataDictionaryArr(new String[] { EnumConstants.LOAN_STATUS, EnumConstants.PRODUCT_TYPE, EnumConstants.HAVE_HOUSE_STATUS,EnumConstants.REPAYMENT_PLAN_STATE });
		request.setAttribute("userType", ApplicationContext.getUser().getUserType());
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		SysGroupUserVO sysGroupUserVO =new SysGroupUserVO();
		sysGroupUserVO.setUserId(ApplicationContext.getUser().getId());
		List<SysGroupUser> sysGroupUser = sysGroupUserService.findByVOTwo(sysGroupUserVO);
		if(null!=sysGroupUser&&sysGroupUser.size()>0){
			request.setAttribute("groupId", sysGroupUser.get(0).getGroupId());
		}
		for(int i=0;i<sysGroupUser.size();i++){
			if(sysGroupUser.get(i).getGroupId()==55){
				request.setAttribute("groupId", 55);
			}
		}
		
		return "apply/applyList";
	}

	// 打开车贷新增
	@RequestMapping("/carLoanAdd")
	public String carLoanAdd(HttpServletRequest request) {
		ApplicationContext.getUser().getUserType();
		request.setAttribute("carRequestDate",  DateUtil.defaultFormatDay(DateUtil.getToday()));
		return "apply/carLoan/carLoanAdd";
	}

	// 打开车贷查看
	@RequestMapping("/carLoanDetail")
	public String carLoanDetail() {
		return "apply/carLoan/carLoanDetail";
	}

	// 打开车贷展期查看
	@RequestMapping("/carLoanExtensionDetail")
	public String carLoanExtensionDetail() {
		return "apply/carLoan/carLoanExtensionDetail";
	}

	// 打开车贷编辑
	@RequestMapping("/carLoanModify")
	public String carLoanModify() {
		return "apply/carLoan/carLoanModify";
	}

	// 打开小企业贷新增
	@RequestMapping("/seLoanAdd")
	public String seLoanAdd() {
		return "apply/seLoan/seLoanAdd";
	}

	// 打开小企业贷编辑
	@RequestMapping("/seLoanModify")
	public String seLoanModify() {
		return "apply/seLoan/seLoanModify";
	}

	// 打开小企业查看
	@RequestMapping("/seLoanDetail")
	public String seLoanDetail() {
		return "apply/seLoan/seLoanDetail";
	}

	
	//打开小企业贷同城贷新增
	@RequestMapping("/seLoanCityWideLoanAdd")
	public String seLoanCityWideLoanAdd() {
		return "apply/seLoan/seLoanCityWideLoanAdd";
	}

	//打开小企业贷同城贷编辑
	@RequestMapping("/seLoanCityWideLoanModify")
	public String seLoanCityWideLoanModify() {
		return "apply/seLoan/seLoanCityWideLoanModify";
	}

	//打开小企业同城贷查看
	@RequestMapping("/seLoanCityWideLoanDetail")
	public String seLoanCityWideLoanDetail() {
		return "apply/seLoan/seLoanCityWideLoanDetail";
	}
	
	@RequestMapping("/getLoanOne")
	@ResponseBody
	public Loan getLoanOne(LoanVO loanVO){
		Loan loan = loanService.get(loanVO);
		return loan;
	}
	
	/***
	 * 
	 * <pre>
	 * 显示列表
	 * </pre>
	 *
	 * @param model
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/getLoanPage")
	@ResponseBody
	public Map viewList(LoanVO loanVO, int rows, int page) {
		Long userId = ApplicationContext.getUser().getId();
		/*
		 * List<Integer> statusList = new ArrayList<Integer>(); if
		 * (loanVO.getStatus() == null) {//默认查询的状态
		 * statusList.add(EnumConstants.LoanStatus.新建.getValue());
		 * statusList.add(EnumConstants.LoanStatus.展期申请中.getValue());
		 * statusList.add(EnumConstants.LoanStatus.有条件同意.getValue());
		 * statusList.add(EnumConstants.LoanStatus.退回门店.getValue());
		 * statusList.add(EnumConstants.LoanStatus.展期退回门店.getValue());
		 * loanVO.setStatusList(statusList); } else
		 */

		if (loanVO.getStatus() != null && loanVO.getStatus() == 0) {// 选择所有查询的状态
			loanVO.setStatus(null);
		}

		// 判断是否展期
		if (loanVO.getExtensionTime() != null && loanVO.getExtensionTime().compareTo(0) > 0) {
			loanVO.setIsExtension(1);
		}
		// 确定查询的产品类型
		List<Integer> qualifiedProductIds = new ArrayList<Integer>();
		if ("admin".equals(ApplicationContext.getUser().getLoginName())) {
			loanVO.setProductTypeList(null);
		} else {
			// 车贷或小企贷
			List<Product> products = productService.findProductTypeByUserId(userId);
			List<Integer> canBrowseproductIds = new ArrayList<Integer>();
			for (Product product : products) {
				canBrowseproductIds.add(product.getProductType());
			}
			if (canBrowseproductIds.size() < 1) {
				return null;
			}
			if (loanVO.getSelectedProductId() != null) {
				if (canBrowseproductIds.contains(loanVO.getSelectedProductId().intValue())) {
					qualifiedProductIds.add(loanVO.getSelectedProductId().intValue());
				} else {
					return null;
				}
			} else {
				qualifiedProductIds.addAll(canBrowseproductIds);
			}
		}
		loanVO.setProductTypeList(qualifiedProductIds);

		// 判断网点类型是否合法
		if ("admin".equals(ApplicationContext.getUser().getLoginName())) {
			loanVO.setProductIdList(null);
		}
		Integer userType = ApplicationContext.getUser().getUserType();
		//客服经理，查询自己的单子
		if(EnumConstants.UserType.SALES_MANAGER.getValue().equals(userType)){
			loanVO.setCrmId(ApplicationContext.getUser().getId());
		// 如果是业务主任,只是查询自己的名下及下属的数据
		}else if (EnumConstants.UserType.BUSINESS_DIRECTOR.getValue().equals(userType)) {
			BaseArea dept = sysUserService.getBaseAreaByUserId(userId, BizConstants.CREDIT2_SALESTEAM);
			loanVO.setSalesTeamId(dept.getId());
		} else if (EnumConstants.UserType.STORE_MANAGER.getValue().equals(userType) || EnumConstants.UserType.STORE_ASSISTANT_MANAGER.getValue().equals(userType)
				|| EnumConstants.UserType.CUSTOMER_SERVICE.getValue().equals(userType)) {
			// 门店经理,门店副理,客服查询所拥有网点的数据
			List<Long> canBrowseSalesDeptList = sysUserService.getSalesDeptIdByUserId(userId);
			if (canBrowseSalesDeptList == null) {
				return null;
			} else if (loanVO.getSalesDeptId() == null) {
				loanVO.setSalesDeptIdList(canBrowseSalesDeptList);
			}
		}
		Pager p = new Pager();
		p.setRows(rows);
		p.setPage(page);
		p.setSidx("ID");
		p.setSort("DESC");
		loanVO.setPager(p);
		
//		ArrayList<Integer> selectStatusList = new ArrayList<Integer>();
		
		
		
//		ArrayList<Integer> userTypeCtrlList = new ArrayList<Integer>();
//		userTypeCtrlList.add(EnumConstants.UserType.SALES_MANAGER.getValue());
//		userTypeCtrlList.add(EnumConstants.UserType.CUSTOMER_SERVICE.getValue());
//		userTypeCtrlList.add(EnumConstants.UserType.STORE_ASSISTANT_MANAGER.getValue());
//		userTypeCtrlList.add(EnumConstants.UserType.STORE_MANAGER.getValue());
//		userTypeCtrlList.add(EnumConstants.UserType.BUSINESS_DIRECTOR.getValue());
		populateLoanStatusList(loanVO, userType);
		Pager loanPager = loanService.findWithPGUnionExtension(loanVO);
		
		filterLoanListResult(loanVO, userType, loanPager);
		
		
		

		List<Loan> loanList = loanPager.getResultList();
		for (Loan loan : loanList) {

			StringBuffer operations = new StringBuffer();
			if (!"admin".equals(ApplicationContext.getUser().getLoginName())) {
				if (loanService.canEdit(loan, userId)) {
					operations.append("|编辑");
				}
				if (loanService.canSubmit(loan, userId)) {
					operations.append("|提交");
				}
				if (loanService.canExtensionSubmit(loan)) {
					operations.append("|展期提交");
				}
				if (loanService.canExtension(loan)) {
					operations.append("|申请展期");
				}
				if (loanService.canCancelLoan(loan)) {
					operations.append("|借款取消");
				}
				if (loanService.canRestoreLoan(loan,userId)) {
					operations.append("|恢复");
				}
				operations.append("|签约补充资料");
			}
			if (ApplicationContext.getUser().getUserType() == 4) {

				operations.append("");

			}else if(loan.getProductId().equals(EnumConstants.ProductList.STUDENT_LOAN.getValue())){
				if(loan.getContractNo() != null){
					List<EduLoanImage> eduLoanImages = eduLoanImageService.findByContractNo(loan.getContractNo());
					if(eduLoanImages!=null && eduLoanImages.size()>0){
						operations.append("|下载附件");
					}else{
						operations.append("|附件");
					}
				}else{
					
					operations.append("|附件");
				}
				
			}else {

				operations.append("|附件");

			}
			
			populateLoanInfo(loan);
			loan.setOperations(operations.toString());
			//查找取消中日志有自动取消的字段，进行标记
			if(loan.getStatus().equals(EnumConstants.LoanStatus.取消.getValue())) {
				//如果是取消的合同
				BusinessLogVO  vo = new BusinessLogVO ();
				vo.setLoanId(loan.getId());
				List<BusinessLogView> logResultList = this.businessLogService.findWihtPgService(vo);
				BusinessLogView lastVo = logResultList.get(logResultList.size()-1);
				if(lastVo.getFlowStatus().equals(EnumConstants.BusinessLogStatus.CANCEL_EXTENSION.getValue())) {
					//是展期取消
					String message = lastVo.getMessage();
					if(message.contains("设置自动取消")) {
						loan.setIsAutoCancel("1");
					}else {
						loan.setIsAutoCancel("0");
					}
				}
			}
			
		}

		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("total", loanPager.getTotalCount());
		result.put("rows", loanList);
		return result;
	}

	private void filterLoanListResult(LoanVO loanVO, Integer userType,
			Pager loanPager) {
		if( EnumConstants.UserType.AUDIT.getValue() !=  userType  
				&& EnumConstants.LoanStatus.初审待分配.getValue() ==  loanVO.getStatus()
				&& EnumConstants.LoanStatus.初审中.getValue() ==  loanVO.getStatus()
				&& EnumConstants.LoanStatus.初审挂起.getValue() ==  loanVO.getStatus()
				&& EnumConstants.LoanStatus.终审中.getValue() ==  loanVO.getStatus()
				&& EnumConstants.LoanStatus.终审退回初审.getValue() ==  loanVO.getStatus()
				&& EnumConstants.LoanStatus.初审重提.getValue() ==  loanVO.getStatus()				
				&& EnumConstants.LoanStatus.初审退回.getValue() ==  loanVO.getStatus()
				&& EnumConstants.LoanStatus.终审退回门店.getValue() ==  loanVO.getStatus()
				&& EnumConstants.LoanStatus.初审拒绝.getValue() ==  loanVO.getStatus()				
				&& EnumConstants.LoanStatus.终审拒绝.getValue() ==  loanVO.getStatus()	
		){
			 
			loanPager.setResultList(new ArrayList<Loan>());
			 
		}
	}

	private void populateLoanStatusList(LoanVO loanVO, Integer userType) {
		if( EnumConstants.UserType.AUDIT.getValue() !=  userType  && EnumConstants.LoanStatus.审核中.getValue() ==  loanVO.getStatus()){
			 
			   List<Integer> statusList = new ArrayList<Integer>(); 
			   statusList.add(EnumConstants.LoanStatus.初审待分配.getValue());
			   statusList.add(EnumConstants.LoanStatus.初审中.getValue());
			   statusList.add(EnumConstants.LoanStatus.初审挂起.getValue());
			   statusList.add(EnumConstants.LoanStatus.终审中.getValue());
			  statusList.add(EnumConstants.LoanStatus.终审退回初审.getValue());
			  statusList.add(EnumConstants.LoanStatus.初审重提.getValue());
			  loanVO.setStatus(null);
			   loanVO.setStatusList(statusList);  
			 
		}else if(EnumConstants.UserType.AUDIT.getValue() !=  userType  && EnumConstants.LoanStatus.信审退回.getValue() ==  loanVO.getStatus()){
			  List<Integer> statusList = new ArrayList<Integer>(); 
			   statusList.add(EnumConstants.LoanStatus.初审退回.getValue());
			   statusList.add(EnumConstants.LoanStatus.终审退回门店.getValue());
			   
			   loanVO.setStatus(null);			  
			   loanVO.setStatusList(statusList);  
		}else if(EnumConstants.UserType.AUDIT.getValue() !=  userType  && EnumConstants.LoanStatus.信审拒绝.getValue() ==  loanVO.getStatus()){
			  List<Integer> statusList = new ArrayList<Integer>(); 
			   statusList.add(EnumConstants.LoanStatus.初审拒绝.getValue());
			   statusList.add(EnumConstants.LoanStatus.终审拒绝.getValue());
			  
			   
			   loanVO.setStatus(null);
			   loanVO.setStatusList(statusList);  
		}
	}

	/**
	 * 
	 * <pre>
	 * 获取当前用户所在城市下所有门店
	 * </pre>
	 *
	 * @return
	 */
	@RequestMapping("/getSalesDeptsInCurCity")
	@ResponseBody
	public List<BaseArea> getSalesDeptsInCurCity() {
		Long userId = ApplicationContext.getUser().getId();
		return sysUserService.getSalesDeptByUserId(userId);
	}
	

	/**
	 * 
	 * <pre>
	 * 获取指定用户 的门店
	 * </pre>
	 *
	 * @return
	 */
	@RequestMapping("/getSalesDepts")
	@ResponseBody
	public List<BaseArea> getSalesDepts(Long userId) {
//		Long userId = ApplicationContext.getUser().getId();
		return sysUserService.getSalesDeptByUserId(userId);
	}

	/**
	 * 
	 * <pre>
	 * 获取借款状态信息列表
	 * </pre>
	 *
	 * @author zhangshihai
	 * @return
	 */
	@RequestMapping("/getLoanStatusList")
	@ResponseBody
	public List<LoanStatusDetail> getLoanStatusList() {
		Integer userType = ApplicationContext.getUser().getUserType();
		
		List<LoanStatusDetail> loanStatusList = new ArrayList<LoanStatusDetail>();
		loanStatusList.add(new LoanStatusDetail("所有", 0));
		if (userType.equals(EnumConstants.UserType.APPROVE.getValue()) || userType.equals(EnumConstants.UserType.AUDIT.getValue())) {//总部,信审
			LoanStatus[] statusList = LoanStatus.values();
			for (LoanStatus loanStatus : statusList) {
				loanStatusList.add(new LoanStatusDetail(loanStatus.name(), loanStatus.getValue()));
			}
		}
		else
		{
			LoanStatusByStore[] statusList = LoanStatusByStore.values();
			for (LoanStatusByStore loanStatusByStore : statusList) {
				loanStatusList.add(new LoanStatusDetail(loanStatusByStore.name(),loanStatusByStore.getValue()));
			}
		}
		return loanStatusList;
	}

	public static class LoanStatusDetail {
		private String name;
		private Integer value;

		/**
		 * @param name
		 * @param value
		 */
		public LoanStatusDetail(String name, Integer value) {
			super();
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Integer getValue() {
			return value;
		}

		public void setValue(Integer value) {
			this.value = value;
		}
	}

	@RequestMapping("/submit")
	@ResponseBody
	public String submit(LoanVO loanVO) {
		Long userId = null;
		String result = "success";// 返回消息
		if (ApplicationContext.getUser() != null) {
			userId = ApplicationContext.getUser().getId();
		}
		Loan loan = loanService.get(loanVO);
		Product product = productService.get(loan.getProductId());
		// 小企业贷有条件同意 必须选择担保人
		if (product.getProductType().compareTo(EnumConstants.ProductType.SE_LOAN.getValue()) == 0 
				&& loan.getStatus().compareTo(EnumConstants.LoanStatus.有条件同意.getValue()) == 0){
//				&& loan.getProductId().compareTo(EnumConstants.ProductList.CITY_WIDE_POS_LOAN.getValue())==0 
//				|| loan.getProductId().compareTo(EnumConstants.ProductList.CITY_WIDE_SE_LOAN.getValue())==0) {
			GuaranteeVO guaranteeVO = new GuaranteeVO();
			guaranteeVO.setPersonId(loan.getPersonId());
			guaranteeVO.setLoanId(loan.getId());
//			guaranteeVO.setFlag(EnumConstants.YesOrNo.YES.getValue());
			List<Guarantee> guaranteeList = guaranteeService.findListByVo(guaranteeVO);
//			if (CollectionUtil.isNullOrEmpty(guaranteeList)) {
//				return "请指定担保人";
//			}
//			// 校验担保人是否合法
			result = checkGuarantee(guaranteeList,loan);
//			if(!"success".equals(result)) return result;
//			if ("没有选中担保人".equals(result)) {
//				return result;
//			}
//			loan.get
			if ("success".equals(result)) {
				loanService.submit(loanVO, userId);
			}else{
				return result;
			}
		} else {
			loanService.submit(loanVO, userId);
		}
		return result;
	}

	

	
	
	@RequestMapping("/extensionSubmit")
	@ResponseBody
	public String extensionSubmit(ExtensionVO extensionVO) {
		String result = "success";// 返回消息
		loanService.extensionSubmit(extensionVO);
		return result;
	}

	/**
	 * 
	 * <pre>
	 * 申请展期
	 * </pre>
	 *
	 * @param extensionTime
	 * @param loanId
	 * @return
	 */
	@RequestMapping("/applyExtension")
	@ResponseBody
	public Object applyExtension(LoanVO loanVO) {
		Map<String,String> map = new HashMap<String,String>();
		if (loanVO != null) {
			map = loanService.createdExtension(loanVO);
		}
		return map;
	}
		

	/***
	 * 跳转到编辑页面
	 * 
	 * <pre>
	 * 
	 * </pre>
	 *
	 * @return
	 */
	@RequestMapping("/viewEdit")
	@ResponseBody
	public LoanDetailsVO viewEdit(Long loanId, String personType, Long productId, String idnum) {
		LoanDetailsVO loanDetailsVo = new LoanDetailsVO();
		loanDetailsVo.setUserId(ApplicationContext.getUser().getId());
		loanDetailsVo.setPersonType(personType);
		loanDetailsVo.setIdnum(idnum);
		Product product = productService.get(productId);
		if (product != null) {
			loanDetailsVo.setProductTypeId(product.getProductType());
			loanDetailsVo.setLoanType(product.getProductTypeName());
			loanDetailsVo.setProductId(product.getId());
			loanDetailsVo.setProductName(product.getProductName());
			loanDetailsVo.setProduct(product);
		}
		SysUser crm = null;// 客户经理
		SysUser assessor = null;// 复核人员
		BaseArea salesDept = null;
		SysUser service = null;// 客服
		Loan loan = null;
		List<CreditHistory> creditHistoryList = null;
		service = sysUserService.get(ApplicationContext.getUser().getId());
		// salesDept =
		// sysUserService.getSalesDeptByUserId(ApplicationContext.getUser().getId());
		if (loanId == null) {// 新增 状态下
			crm = sysUserService.get(ApplicationContext.getUser().getId());
			loanDetailsVo.setCrm(crm);
		} else {// 编辑状态
			loan = loanService.get(loanId);
			loanDetailsVo.setLoanId(loan.getId());
			loanDetailsVo.setLoan(loan);
			crm = sysUserService.get(loan.getCrmId());
			assessor = sysUserService.get(loan.getAssessorId());
			service = sysUserService.get(loan.getServiceId());
			loanDetailsVo.setCrm(crm);
			loanDetailsVo.setAssessor(assessor);
			salesDept = baseAreaService.get(loan.getSalesDeptId());
		}
		loanDetailsVo.setService(service);
		loanDetailsVo.setSalesDept(salesDept);
		// 小企业贷
		if (product.getProductType().compareTo(EnumConstants.ProductType.SE_LOAN.getValue()) == 0) {
			Person person = personService.getPersonByIdNum(idnum.trim().toString(), product.getProductType());
			if (person != null) {
				List<Contacter> contacterList = null;
				List<Guarantee> guaranteeList = null;
				loanDetailsVo.setCrm(crm);
				Company company = personCompanyService.getCompanyById(person.getCompanyId());
				if (loan != null) {// 新增
					crm = sysUserService.get(loan.getCrmId());
					contacterList = contacterService.getContacterListByBorrowerId(person.getId(), loan.getId());
					guaranteeList = guaranteeService.findListByPersonId(person.getId(), loan.getId());
					
					creditHistoryList = creditHistoryService.getCreditHistoryByPersonId(person.getId(), loan.getId());
					populatePersonTraining(loanDetailsVo, loan, person);
				}else{
					Loan latestLoan = loanService.getLatestLoanByperson(person.getId());
					contacterList =  contacterService.getContacterListByBorrowerId(person.getId(), latestLoan.getId());
					guaranteeList = guaranteeService.findListByPersonId(person.getId(), latestLoan.getId());
				}
				
				populatePerson(loanDetailsVo,person);
				loanDetailsVo.setPerson(person);
				loanDetailsVo.setCompany(company);
				loanDetailsVo.setContacterList(contacterList);
				loanDetailsVo.setGuaranteeList(guaranteeList);
				populateCreditHistory(loanDetailsVo, creditHistoryList);
				

			}
			loanDetailsVo.setOrganList(getOrganInfoByDeptId(loanDetailsVo.getService().getAreaId()));
		}

		// 车贷
		if (product.getProductType().compareTo(EnumConstants.ProductType.CAR_LOAN.getValue()) == 0) {
			Person person = personService.getPersonByIdNum(idnum, product.getProductType());
			if (person != null) {
				Company company = personCompanyService.getCompanyById(person.getCompanyId());
				List<Guarantee> guaranteeList = null;
				List<Vehicle> vehicleList = null;
				List<Contacter> contacterList = null;
				if (loan != null) {// 新增
					contacterList = contacterService.getContacterListByBorrowerId(person.getId(), loan.getId());
//					guaranteeList = guaranteeService.findListByPersonId(person.getId(), loan.getId());
					vehicleList = vehicleService.getVehicleListByPersonId(person.getId(), loan.getId());
					creditHistoryList = creditHistoryService.getCreditHistoryByPersonId(person.getId(), loan.getId());
				}else{
					Loan latestLoan = loanService.getLatestLoanByperson(person.getId());
					contacterList =  contacterService.getContacterListByBorrowerId(person.getId(), latestLoan.getId());
					vehicleList = vehicleService.getVehicleListByPersonId(person.getId(), latestLoan.getId());
					creditHistoryList = creditHistoryService.getCreditHistoryByPersonId(person.getId(), latestLoan.getId());
				}
				loanDetailsVo.setPerson(person);
				loanDetailsVo.setCompany(company);
				loanDetailsVo.setCompanyId(company.getId());
				loanDetailsVo.setContacterList(contacterList);
				loanDetailsVo.setGuaranteeList(guaranteeList);

				if (CollectionUtil.isNotEmpty(creditHistoryList)) {
					loanDetailsVo.setCreditHistory(creditHistoryList.get(0));
				}

				if (CollectionUtil.isNotEmpty(vehicleList)) {
					loanDetailsVo.setVehicle(vehicleList.get(0));
					loanDetailsVo.setVehicleId(vehicleList.get(0).getId());
				}
			}
		}
		return loanDetailsVo;
	}

	private void populateCreditHistory(LoanDetailsVO loanDetailsVo,
			List<CreditHistory> creditHistoryList) {
		if (CollectionUtil.isNotEmpty(creditHistoryList)) {
			if(creditHistoryList.get(0).getHasCreditCard() == 1){
				if(creditHistoryList.get(0).getCardNum() != null && creditHistoryList.get(0).getCardNum() == 0) 
					creditHistoryList.get(0).setCardNum(null);
				if(creditHistoryList.get(0).getTotalAmount() != null && creditHistoryList.get(0).getTotalAmount().compareTo(new BigDecimal(0)) ==0) 
					creditHistoryList.get(0).setTotalAmount(null);
				if(creditHistoryList.get(0).getOverdrawAmount() != null && creditHistoryList.get(0).getOverdrawAmount().compareTo(new BigDecimal(0)) == 0) 
					creditHistoryList.get(0).setOverdrawAmount(null);
				
				creditHistoryList.get(0).setHasCreditCardText("有");
			}else{
				creditHistoryList.get(0).setHasCreditCardText("无");
				creditHistoryList.get(0).setCardNum(null);
				creditHistoryList.get(0).setTotalAmount(null);
				creditHistoryList.get(0).setOverdrawAmount(null);
				
			}
			loanDetailsVo.setCreditHistory(creditHistoryList.get(0));
		}
	}

	/**
	 * 
	 * <pre>
	 * 跳转到车贷新增页面
	 * </pre>
	 *
	 * @param loanId
	 * @param personType
	 * @param productId
	 * @param loanType
	 * @param idnum
	 * @return
	 */
	@RequestMapping("/toAddCarLoan")
	@ResponseBody
	public LoanDetailsVO toAddCarLoan(Long productId, String idnum) {
		LoanDetailsVO loanDetailsVo = new LoanDetailsVO();
		// loanDetailsVo.setPersonType(personType);
		loanDetailsVo.setIdnum(idnum);
		Product product = productService.get(productId);
		if (product != null) {
			loanDetailsVo.setLoanType(product.getProductTypeName());
			loanDetailsVo.setProductId(product.getId());
		}
		BaseArea salesDept = null;
		SysUser service = null;// 客服
		service = sysUserService.get(ApplicationContext.getUser().getId());
		// TODO
		// salesDept =
		// sysUserService.getSalesDeptByUserId(ApplicationContext.getUser().getId());
		loanDetailsVo.setSalesDept(salesDept);
		loanDetailsVo.setService(service);
		// 如果为老客户，则需显示该客户的信息
		Person person = personService.getPersonByIdNum(idnum, product.getProductType());
		if (person != null) {
			loanDetailsVo.setIdnum(idnum);
			LoanVO loanVO = new LoanVO();
			loanVO.setPersonId(person.getId());
			Loan loan = loanService.get(loanVO);
			Company company = personCompanyService.getCompanyById(person.getCompanyId());
			List<Contacter> contacterList = contacterService.getContacterListByBorrowerId(person.getId(), loan.getId());
			List<Guarantee> guaranteeList = guaranteeService.findListByPersonId(person.getId(), loan.getId());
			loanDetailsVo.setLoan(loan);
			loanDetailsVo.setLoanId(loan.getId());
			loanDetailsVo.setPerson(person);
			loanDetailsVo.setCompany(company);
			loanDetailsVo.setCompanyId(company.getId());
			loanDetailsVo.setContacterList(contacterList);
			loanDetailsVo.setGuaranteeList(guaranteeList);
			List<Vehicle> vehicleList = vehicleService.getVehicleListByPersonId(person.getId(), loan.getId());
			List<CreditHistory> creditHistoryList = creditHistoryService.getCreditHistoryByPersonId(person.getId(), loan.getId());
			if (CollectionUtil.isNotEmpty(creditHistoryList)) {
				loanDetailsVo.setCreditHistory(creditHistoryList.get(0));
			}
			if (vehicleList.size() > 0) {
				loanDetailsVo.setVehicle(vehicleList.get(0));
				loanDetailsVo.setVehicleId(vehicleList.get(0).getId());
			}
		}

		return loanDetailsVo;
	}

	/**
	 * 
	 * <pre>
	 * 跳转编辑车贷页面
	 * </pre>
	 *
	 * @param loanId
	 * @param personType
	 * @param productId
	 * @param loanType
	 * @param idnum
	 * @return
	 */
	@RequestMapping("/toModifyCarLoan")
	@ResponseBody
	public LoanDetailsVO toModifyCarLoan(Long loanId, String personType, Long productId, String loanType, String idnum) {
		LoanDetailsVO loanDetailsVo = new LoanDetailsVO();
		loanDetailsVo.setPersonType(personType);
		loanDetailsVo.setIdnum(idnum);
		Product product = productService.get(productId);
		if (product != null) {
			loanDetailsVo.setLoanType(product.getProductTypeName());
			loanDetailsVo.setProductId(product.getId());
		}
		SysUser crm = null;// 客户经理
		SysUser assessor = null;// 复核人员
		BaseArea salesDept = null;
		SysUser service = null;// 客服
		// salesDept =
		// sysUserService.getSalesDeptByUserId(ApplicationContext.getUser().getId());
		// 编辑状态
		Loan loan = loanService.get(loanId);
		service = sysUserService.get(loan.getServiceId());
		salesDept = baseAreaService.get(loan.getSalesDeptId());
		Person person = personService.getPersonByIdNum(idnum, product.getProductType());
		loanDetailsVo.setIdnum(idnum);
		LoanVO loanVO = new LoanVO();
		loanVO.setPersonId(person.getId());
		Company company = personCompanyService.getCompanyById(person.getCompanyId());
		List<Contacter> contacterList = contacterService.getContacterListByBorrowerId(person.getId(), loan.getId());
		List<Guarantee> guaranteeList = guaranteeService.findListByPersonId(person.getId(), loan.getId());
		crm = sysUserService.get(loan.getCrmId());
		assessor = sysUserService.get(loan.getAssessorId());
		loanDetailsVo.setSalesDept(salesDept);
		loanDetailsVo.setCrm(crm);
		loanDetailsVo.setService(service);
		loanDetailsVo.setAssessor(assessor);
		loanDetailsVo.setLoan(loan);
		loanDetailsVo.setLoanId(loan.getId());
		loanDetailsVo.setPerson(person);
		loanDetailsVo.setCompany(company);
		loanDetailsVo.setCompanyId(company.getId());
		loanDetailsVo.setContacterList(contacterList);
		loanDetailsVo.setGuaranteeList(guaranteeList);
		List<Vehicle> vehicleList = vehicleService.getVehicleListByPersonId(person.getId(), loan.getId());
		List<CreditHistory> creditHistoryList = creditHistoryService.getCreditHistoryByPersonId(person.getId(), loan.getId());
		if (CollectionUtil.isNotEmpty(creditHistoryList)) {
			loanDetailsVo.setCreditHistory(creditHistoryList.get(0));
		}
		if (vehicleList.size() > 0) {
			loanDetailsVo.setVehicle(vehicleList.get(0));
			loanDetailsVo.setVehicleId(vehicleList.get(0).getId());
		}
		return loanDetailsVo;
	}

	/**
	 * 
	 * <pre>
	 * 跳转到小企业新增页面
	 * </pre>
	 *
	 * @param loanId
	 * @param personType
	 * @param productId
	 * @param loanType
	 * @param idnum
	 * @return
	 */
	@RequestMapping("/toAddSELoan")
	@ResponseBody
	public LoanDetailsVO toAddSELoan(Long productId, String idnum) {
		LoanDetailsVO loanDetailsVo = new LoanDetailsVO();
		// loanDetailsVo.setPersonType(personType);
		loanDetailsVo.setIdnum(idnum);
		Product product = productService.get(productId);
		if (product != null) {
			loanDetailsVo.setLoanType(product.getProductTypeName());
			loanDetailsVo.setProductId(product.getId());
		}
		BaseArea salesDept = null;
		SysUser service = null;// 客服
		service = sysUserService.get(ApplicationContext.getUser().getId());
		// TODO
		// salesDept =
		// sysUserService.getSalesDeptByUserId(ApplicationContext.getUser().getId());
		loanDetailsVo.setSalesDept(salesDept);
		loanDetailsVo.setService(service);
		// 如果为老客户，则需显示该客户的信息
		Person person = personService.getPersonByIdNum(idnum, product.getProductType());
		if (person != null) {
			loanDetailsVo.setIdnum(idnum);
			LoanVO loanVO = new LoanVO();
			loanVO.setPersonId(person.getId());
			Loan loan = loanService.get(loanVO);
			Company company = personCompanyService.getCompanyById(person.getCompanyId());
			List<Contacter> contacterList = contacterService.getContacterListByBorrowerId(person.getId(), loan.getId());
			List<Guarantee> guaranteeList = guaranteeService.findListByPersonId(person.getId(), loan.getId());
			loanDetailsVo.setLoan(loan);
			loanDetailsVo.setLoanId(loan.getId());
			loanDetailsVo.setPerson(person);
			loanDetailsVo.setCompany(company);
			loanDetailsVo.setCompanyId(company.getId());
			loanDetailsVo.setContacterList(contacterList);
			loanDetailsVo.setGuaranteeList(guaranteeList);
			List<Vehicle> vehicleList = vehicleService.getVehicleListByPersonId(person.getId(), loan.getId());
			List<CreditHistory> creditHistoryList = creditHistoryService.getCreditHistoryByPersonId(person.getId(), loan.getId());
			if (CollectionUtil.isNotEmpty(creditHistoryList)) {
				loanDetailsVo.setCreditHistory(creditHistoryList.get(0));
			}
			if (vehicleList.size() > 0) {
				loanDetailsVo.setVehicle(vehicleList.get(0));
				loanDetailsVo.setVehicleId(vehicleList.get(0).getId());
			}
		}

		return loanDetailsVo;
	}

	/**
	 * 
	 * <pre>
	 * 跳转小企业贷编辑页面
	 * </pre>
	 *
	 * @param loanId
	 * @param personType
	 * @param productId
	 * @param loanType
	 * @param idnum
	 * @return
	 */
	@RequestMapping("/toModifySELoan")
	@ResponseBody
	public LoanDetailsVO toModifySELoan(Long loanId, String personType, Long productId, String loanType, String idnum) {
		LoanDetailsVO loanDetailsVo = new LoanDetailsVO();
		loanDetailsVo.setPersonType(personType);
		loanDetailsVo.setIdnum(idnum);
		Product product = productService.get(productId);
		if (product != null) {
			loanDetailsVo.setLoanType(product.getProductTypeName());
			loanDetailsVo.setProductId(product.getId());
		}
		SysUser crm = null;// 客户经理
		SysUser assessor = null;// 复核人员
		BaseArea salesDept = null;
		SysUser service = null;// 客服
		service = sysUserService.get(ApplicationContext.getUser().getId());
		// salesDept =
		// sysUserService.getSalesDeptByUserId(ApplicationContext.getUser().getId());
		loanDetailsVo.setSalesDept(salesDept);
		// 编辑状态
		Loan loan = loanService.get(loanId);
		service = sysUserService.get(loan.getServiceId());
		salesDept = baseAreaService.get(loan.getSalesDeptId());
		Person person = personService.getPersonByIdNum(idnum, product.getProductType());
		loanDetailsVo.setIdnum(idnum);
		LoanVO loanVO = new LoanVO();
		loanVO.setPersonId(person.getId());
		Company company = personCompanyService.getCompanyById(person.getCompanyId());
		List<Contacter> contacterList = contacterService.getContacterListByBorrowerId(person.getId(), loan.getId());
		List<Guarantee> guaranteeList = guaranteeService.findListByPersonId(person.getId(), loan.getId());
		crm = sysUserService.get(loan.getCrmId());
		assessor = sysUserService.get(loan.getAssessorId());
		
	
		
		loanDetailsVo.setSalesDept(salesDept);
		loanDetailsVo.setCrm(crm);
		loanDetailsVo.setService(service);
		loanDetailsVo.setAssessor(assessor);
		loanDetailsVo.setLoan(loan);
		loanDetailsVo.setLoanId(loan.getId());
		loanDetailsVo.setPerson(person);
		loanDetailsVo.setCompany(company);
		loanDetailsVo.setCompanyId(company.getId());
		loanDetailsVo.setContacterList(contacterList);
		loanDetailsVo.setGuaranteeList(guaranteeList);
		List<Vehicle> vehicleList = vehicleService.getVehicleListByPersonId(person.getId(), loan.getId());
		List<CreditHistory> creditHistoryList = creditHistoryService.getCreditHistoryByPersonId(person.getId(), loan.getId());
		if (CollectionUtil.isNotEmpty(creditHistoryList)) {
			loanDetailsVo.setCreditHistory(creditHistoryList.get(0));
		}
		List<ApproveResult> approveResultList = approveResultService.getApproveResultByLoanId(loanId);
		loanDetailsVo.setApproveResultList(approveResultList);
		if (vehicleList.size() > 0) {
			loanDetailsVo.setVehicle(vehicleList.get(0));
			loanDetailsVo.setVehicleId(vehicleList.get(0).getId());
		}
		return loanDetailsVo;
	}

	/**
	 * 
	 * <pre>
	 * 查看车贷
	 * </pre>
	 *
	 * @param loanId
	 * @return
	 */
	@RequestMapping("/toCarLoanDetail")
	@ResponseBody
	public LoanDetailsVO toCarLoanDetail(Long loanId, String flag) {
		LoanDetailsVO loanDetailsVo = new LoanDetailsVO();
		Loan loan = loanService.get(loanId);
		Person person = personService.get(loan.getPersonId());
		Product product = productService.get(loan.getProductId());
		Company company=null;
		if(person.getCompanyId()!=null){
		 company = personCompanyService.getCompanyById(person.getCompanyId());
		}
		SysUser crm = sysUserService.get(loan.getCrmId());
		SysUser director = sysUserService.getBizDirectorByCrm(crm);
		SysUser service = sysUserService.get(loan.getServiceId());
		SysUser assessor = null;
		if(loan.getAssessorId()!=null){
			assessor =sysUserService.get(loan.getAssessorId());
		}
		BaseArea salesDept = salesDeptService.loadOneBaseAreaById(loan.getSalesDeptId());
		List<Contacter> contacterList = contacterService.getContacterListByBorrowerId(person.getId(), loan.getId());
		List<Vehicle> vehicleList = vehicleService.getVehicleListByPersonId(person.getId(), loan.getId());
		List<CreditHistory> creditHistoryList = creditHistoryService.getCreditHistoryByPersonId(person.getId(), loan.getId());
		if (CollectionUtil.isNotEmpty(creditHistoryList)) {
			loanDetailsVo.setCreditHistory(creditHistoryList.get(0));
		}
		ApproveResultVO approveResultVO = new ApproveResultVO();
		approveResultVO.setLoanId(loanId);
		if ("audit".equals(flag)) {
			List<Integer> statusList = new ArrayList<Integer>();
			statusList.add(EnumConstants.ApproveResultState.APPROVAL.getValue());// 同意
			statusList.add(EnumConstants.ApproveResultState.CONDITIONAL_APPROVAL.getValue());// 有条件同意
			statusList.add(EnumConstants.ApproveResultState.REFUSE_TO.getValue());// 拒绝
			statusList.add(EnumConstants.ApproveResultState.RETURN.getValue());// 退回门店
			approveResultVO.setStatusList(statusList);
		}
		List<ApproveResult> approveResultList = approveResultService.findListByVo(approveResultVO);
		loanDetailsVo.setApproveResultList(approveResultList);
		loanDetailsVo.setLoan(loan);
		loanDetailsVo.setPerson(person);
		loanDetailsVo.setProduct(product);
		loanDetailsVo.setCompany(company);
		loanDetailsVo.setCrm(crm);
		loanDetailsVo.setDirector(director);
		loanDetailsVo.setService(service);
		loanDetailsVo.setAssessor(assessor);
		loanDetailsVo.setSalesDept(salesDept);
		loanDetailsVo.setContacterList(contacterList);
		loanDetailsVo.setVehicle((vehicleList != null && vehicleList.size() > 0) ? vehicleList.get(0) : null);
		// 插入系统日志
		SysLog sysLog = new SysLog();
		if ("apply".equals(flag)) {
			sysLog.setOptModule(EnumConstants.OptionModule.APPLY_LOAN.getValue());
			sysLog.setOptType(EnumConstants.OptionType.APPLY_DETAIL.getValue());
		} else if ("audit".equals(flag)) {
			sysLog.setOptModule(EnumConstants.OptionModule.AUDIT_LOAN.getValue());
			sysLog.setOptType(EnumConstants.OptionType.AUDIT_DETAIL.getValue());
		} else if ("contract".equals(flag)) {
			sysLog.setOptModule(EnumConstants.OptionModule.CONTRACT.getValue());
			sysLog.setOptType(EnumConstants.OptionType.CONTRACT_DETAIL.getValue());
		} else if ("contractConfirm".equals(flag)) {
			sysLog.setOptModule(EnumConstants.OptionModule.CONTRACT_CONFIRM.getValue());
			sysLog.setOptType(EnumConstants.OptionType.CONTRACT_CONFIRM_DETAIL.getValue());
		}
		sysLog.setRemark("借款ID   " + loan.getId().toString());
		sysLogService.insert(sysLog);
		return loanDetailsVo;
	}

	/**
	 * 
	 * <pre>
	 * 查看展期车贷
	 * </pre>
	 *
	 * @param loanId
	 * @return
	 */
	@RequestMapping("/toCarExtensionLoanDetail")
	@ResponseBody
	public LoanDetailsVO toCarExtensionLoanDetail(Long loanId, String flag) {
		LoanDetailsVO loanDetailsVo = new LoanDetailsVO();
		Extension extension = extensionService.get(loanId);

		LoanExtensionVO loanExtensionVO = new LoanExtensionVO();
		loanExtensionVO.setExtensionId(loanId);

		// 展期的申请金额获取
		List<LoanExtension> loanExtensionList = loanExtensionService.findListByVo(loanExtensionVO);
		if (loanExtensionList.size() == 1) {
			Loan loan = loanService.get(loanExtensionList.get(0).getLoanId());
			if (extension.getRequestMoney() != null) {
				loan.setRequestMoney(extension.getRequestMoney());
				loan.setLoanId(extension.getId());
			}
			Person person = personService.get(extension.getPersonId());
			Product product = productService.get(extension.getProductId());
			Company company = personCompanyService.getCompanyById(person.getCompanyId());
			SysUser crm = sysUserService.get(extension.getCrmId());
			SysUser service = sysUserService.get(extension.getServiceId());
			BaseArea salesDept = salesDeptService.loadOneBaseAreaById(extension.getSalesDeptId());
			List<Contacter> contacterList = contacterService.getContacterListByBorrowerId(person.getId(), loan.getId());
			List<Vehicle> vehicleList = vehicleService.getVehicleListByPersonId(person.getId(), loan.getId());
			List<CreditHistory> creditHistoryList = creditHistoryService.getCreditHistoryByPersonId(person.getId(), loan.getId());
			if (CollectionUtil.isNotEmpty(creditHistoryList)) {
				loanDetailsVo.setCreditHistory(creditHistoryList.get(0));
			}
			ApproveResultVO approveResultVO = new ApproveResultVO();
			approveResultVO.setLoanId(loanId);
			if ("audit".equals(flag)) {
				List<Integer> statusList = new ArrayList<Integer>();
				statusList.add(EnumConstants.ApproveResultState.APPROVAL.getValue());// 同意
				statusList.add(EnumConstants.ApproveResultState.CONDITIONAL_APPROVAL.getValue());// 有条件同意
				statusList.add(EnumConstants.ApproveResultState.REFUSE_TO.getValue());// 拒绝
				statusList.add(EnumConstants.ApproveResultState.RETURN.getValue());// 退回门店
				approveResultVO.setStatusList(statusList);
			}
			List<ApproveResult> approveResultList = approveResultService.findListByVo(approveResultVO);
			loanDetailsVo.setApproveResultList(approveResultList);
			loanDetailsVo.setLoan(loan);
			loanDetailsVo.setPerson(person);
			loanDetailsVo.setProduct(product);
			loanDetailsVo.setCompany(company);
			loanDetailsVo.setCrm(crm);
			loanDetailsVo.setService(service);
			loanDetailsVo.setSalesDept(salesDept);
			loanDetailsVo.setContacterList(contacterList);
			loanDetailsVo.setVehicle((vehicleList != null && vehicleList.size() > 0) ? vehicleList.get(0) : null);
			// 插入系统日志
			SysLog sysLog = new SysLog();
			if ("apply".equals(flag)) {
				sysLog.setOptModule(EnumConstants.OptionModule.APPLY_LOAN.getValue());
				sysLog.setOptType(EnumConstants.OptionType.APPLY_DETAIL.getValue());
			} else if ("audit".equals(flag)) {
				sysLog.setOptModule(EnumConstants.OptionModule.AUDIT_LOAN.getValue());
				sysLog.setOptType(EnumConstants.OptionType.AUDIT_DETAIL.getValue());
			} else if ("contract".equals(flag)) {
				sysLog.setOptModule(EnumConstants.OptionModule.CONTRACT.getValue());
				sysLog.setOptType(EnumConstants.OptionType.CONTRACT_DETAIL.getValue());
			} else if ("contractConfirm".equals(flag)) {
				sysLog.setOptModule(EnumConstants.OptionModule.CONTRACT_CONFIRM.getValue());
				sysLog.setOptType(EnumConstants.OptionType.CONTRACT_CONFIRM_DETAIL.getValue());
			}
			sysLog.setRemark("展期借款ID   " + loanId.toString());
			sysLogService.insert(sysLog);

		}
		return loanDetailsVo;
	}

	/**
	 * 
	 * <pre>
	 * 查看小企业贷
	 * </pre>
	 *
	 * @param loanId
	 * @return
	 */
	@RequestMapping("/toSELoanDetail")
	@ResponseBody
	public LoanDetailsVO toSELoanDetail(Long loanId, String flag) {
		LoanDetailsVO loanDetailsVo = new LoanDetailsVO();

		Loan loan = loanService.get(loanId);
		Person person = personService.get(loan.getPersonId());
		Product product = productService.get(loan.getProductId());
		Company company = personCompanyService.getCompanyById(person.getCompanyId());
		SysUser crm = sysUserService.get(loan.getCrmId());
		SysUser service = sysUserService.get(loan.getServiceId());
		SysUser assessor = sysUserService.get(loan.getAssessorId());
		BaseArea salesDept = salesDeptService.loadOneBaseAreaById(loan.getSalesDeptId());
		List<Contacter> contacterList = contacterService.getContacterListByBorrowerId(person.getId(), loan.getId());
		List<Guarantee> guaranteeList = guaranteeService.findListByPersonId(person.getId(), loan.getId());
		List<Vehicle> vehicleList = vehicleService.getVehicleListByPersonId(person.getId(), loan.getId());
		
		
		ApproveResultVO approveResultVO = new ApproveResultVO();
		approveResultVO.setLoanId(loanId);
		if ("audit".equals(flag)) {
			List<Integer> statusList = new ArrayList<Integer>();
			statusList.add(EnumConstants.ApproveResultState.APPROVAL.getValue());// 同意
			statusList.add(EnumConstants.ApproveResultState.CONDITIONAL_APPROVAL.getValue());// 有条件同意
			statusList.add(EnumConstants.ApproveResultState.REFUSE_TO.getValue());// 拒绝
			statusList.add(EnumConstants.ApproveResultState.RETURN.getValue());// 退回门店
			approveResultVO.setStatusList(statusList);
		}
		List<ApproveResult> approveResultList = approveResultService.findListByVo(approveResultVO);
		
		// 填充文本信息
		populateCompany(company);
		populateLoanInfo(loan);
		populatePerson(loanDetailsVo,person);
		populatePersonTraining(loanDetailsVo, loan, person);
		
		List<CreditHistory> creditHistoryList = creditHistoryService.getCreditHistoryByPersonId(person.getId(), loan.getId());
		
		populateCreditHistory(loanDetailsVo, creditHistoryList);
		
		loanDetailsVo.setApproveResultList(approveResultList);
		loanDetailsVo.setLoan(loan);
		loanDetailsVo.setPerson(person);
		loanDetailsVo.setProduct(product);
		loanDetailsVo.setCompany(company);
		loanDetailsVo.setCrm(crm);
		loanDetailsVo.setService(service);
		loanDetailsVo.setAssessor(assessor);
		loanDetailsVo.setSalesDept(salesDept);
		loanDetailsVo.setContacterList(contacterList);
		loanDetailsVo.setGuaranteeList(guaranteeList);
		loanDetailsVo.setVehicle((vehicleList != null && vehicleList.size() > 0) ? vehicleList.get(0) : null);
		if (loan.getHasHouse() == null) {
			loanDetailsVo.setHasHouseString("");
		} else {
			if (loan.getHasHouse().compareTo(EnumConstants.YesOrNo.YES.getValue()) == 0) {
				loanDetailsVo.setHasHouseString("有房");
			} else {
				loanDetailsVo.setHasHouseString("无房");
			}
		}
		// 插入系统日志
		SysLog sysLog = new SysLog();
		if ("apply".equals(flag)) {
			sysLog.setOptModule(EnumConstants.OptionModule.APPLY_LOAN.getValue());
			sysLog.setOptType(EnumConstants.OptionType.APPLY_DETAIL.getValue());
		} else if ("audit".equals(flag)) {
			sysLog.setOptModule(EnumConstants.OptionModule.AUDIT_LOAN.getValue());
			sysLog.setOptType(EnumConstants.OptionType.AUDIT_DETAIL.getValue());
		} else if ("contract".equals(flag)) {
			sysLog.setOptModule(EnumConstants.OptionModule.CONTRACT.getValue());
			sysLog.setOptType(EnumConstants.OptionType.CONTRACT_DETAIL.getValue());
		} else if ("contractConfirm".equals(flag)) {
			sysLog.setOptModule(EnumConstants.OptionModule.CONTRACT_CONFIRM.getValue());
			sysLog.setOptType(EnumConstants.OptionType.CONTRACT_CONFIRM_DETAIL.getValue());
		}

		sysLog.setRemark("借款ID   " + loan.getId().toString());
		sysLogService.insert(sysLog);
		return loanDetailsVo;
	}

	/**
	 * 
	 * <pre>
	 * 新增车贷
	 * </pre>
	 *
	 * @param loanDetailsVo
	 * @param creditHistory
	 * @param vehicle
	 * @param person
	 * @param company
	 * @param loan
	 * @return
	 */
	@RequestMapping("/addCarLoan")
	@ResponseBody
	@Transactional
	public String addCarLoan(LoanDetailsVO loanDetailsVo, CreditHistory creditHistory, Vehicle vehicle, Person person, Company company, Loan loan) {
		String result = null;// 返回消息
		// 对申请金额进行验证
		BigDecimal requestMoney = loan.getRequestMoney();
		if (requestMoney == null) {
			return result = "申请金额不能为空";
		} else {
			if (requestMoney.compareTo(new BigDecimal(1000)) < 0) {
				return result = "申请金额1000元以上";
			}
		}
		SysUserVO sysUserVo = new SysUserVO();
		sysUserVo.setCode(loanDetailsVo.getPersonNo());
		BaseArea dept = null;
		List<SysUser> candicatesSysUser = sysUserService.findListByVO(sysUserVo);
		vehicle.setRemark(null);// 备注设置为空
		// 判断客户经理输入是否正确
		if (candicatesSysUser == null || candicatesSysUser.size() < 1) {
			result = "客户经理编号和姓名不正确";
			return result;
		}

		// 判断c下客户，经理，复核人员在不在一个营业网点
		if (loan.getServiceId() != null && loanDetailsVo.getManagerName() != null && loanDetailsVo.getAssessorName() != null) {
			if (!loanService.checkManager(loan.getServiceId(), loanDetailsVo.getManagerName(), loanDetailsVo.getAssessorName())) {
				logger.info("传入检查的客服: " + loan.getServiceId() + ", 客户经理: " + loanDetailsVo.getManagerName() + ",复核人员: " + loanDetailsVo.getAssessorName());
				result = "客服，客户经理，复核人员在不在一个营业网点";
				return result;
			} else {
				// 销量团队
				dept = sysUserService.getBaseAreaByUserId(loanDetailsVo.getManagerName(), BizConstants.CREDIT2_SALESTEAM);
				// baseAreaService.
				// 获取客户经理的id
				loan.setCrmId(loanDetailsVo.getManagerName());
				SysUser crm = sysUserService.get(loanDetailsVo.getManagerName());
				// 获取业务主任的ID
				SysUser bizDirector = sysUserService.getBizDirectorByCrm(crm);
				if (bizDirector != null) {
					loan.setBizDirectorId(bizDirector.getId());
				}
				// 设置审核人员ID
				loan.setAssessorId(loanDetailsVo.getAssessorName());
				// 销售团队ID
				if(dept != null){
					loan.setSalesTeamId(dept.getId());
					
				}
				// 设置管理客服，默认为签约客服
				loan.setManagerId(loan.getServiceId());
			}
		} else {
			result = "客服，客户经理，复核人员在不在一个营业网点";
			return result;
		}

		company.setAddress(loanDetailsVo.getCarCompanyAddress());
		company.setName(loanDetailsVo.getCarCompanyName());
		// 公司信息
		company = personCompanyService.insert(company);
		// 客户信息
		person = loanDetailVOconvertCarPerson(loanDetailsVo, person);
		if(null != company.getId()) {
			loanDetailsVo.setCompanyId(company.getId());
			person.setCompanyId(company.getId());
		}
		//职业类型默认值
		person.setProfessionType("其他");
		// 判断该人是否存在
		PersonVO personVo = new PersonVO();
		personVo.setIdnum(person.getIdnum());
		personVo.setProductType(EnumConstants.ProductType.CAR_LOAN.getValue());
		List<Person> oldPersonList = personService.findListByVo(personVo);
		boolean isOldPerson = false;
		// 该人已存在
		if (CollectionUtil.isNotEmpty(oldPersonList)) {
			for (Person p : oldPersonList) {
				// Product product = productService.get(p.getProductId());
				if (EnumConstants.ProductType.CAR_LOAN.getValue().compareTo(p.getProductType()) == 0) {
					// 该用户车贷存在，更新
					PersonVO oldPersonVO = PersonAssembler.transferModel2VO(person);
					oldPersonVO.setPersonNo(p.getPersonNo());
					oldPersonVO.setId(p.getId());
					// 更新就OK了					
					personService.update(oldPersonVO);
					isOldPerson = true;
					person.setId(p.getId()); //set the personId
					break;
				}
			}
			if (!isOldPerson) {
				person.setPersonNo(oldPersonList.get(0).getPersonNo());
			}
		}
		if (!isOldPerson) {
			person.setIdentifier(EnumConstants.ProductType.CAR_LOAN_STRING.getStrValue());
			person.setProductType(loanDetailsVo.getProductTypeId());
			if (StringUtil.isEmpty(person.getPersonNo())) {
				person.setPersonNo(loanService.getPersonNoByType(SerialNum.C2, loan.getSalesDeptId()));
			}
			person.setCompanyId(company.getId());
			person = personService.insert(person);
		}else{
			PersonVO personNewVo = new PersonVO();
			personNewVo.setIdnum(person.getIdnum());
			personNewVo.setCompanyId(company.getId());
			personNewVo.setIdentifier(EnumConstants.ProductType.CAR_LOAN_STRING.getStrValue());
			personNewVo.setProductType(loanDetailsVo.getProductTypeId());
			List<Person> newPersonList = personService.findListByVo(personVo);
			LoanVO loanVO=new LoanVO();
			loanVO.setPersonId(newPersonList.get(0).getId());
			loanVO.setProductType(EnumConstants.ProductType.CAR_LOAN.getValue());
			loanVO.setStatus(EnumConstants.LoanStatus.新建.getValue());
			Loan loanCredit=loanService.get(loanVO);
			if(loanCredit != null){
				//如果借款已存在，则去校验新增的贷款信息的车架号是否也已经存在，如果不存在则可以录入贷款，如果存在则不可以录入
				VehicleVO vehicleVo = new VehicleVO();
				vehicleVo.setFrameNumber(vehicle.getFrameNumber());
				List<Vehicle> vehicleList = vehicleService.findListByVo(vehicleVo);
				if(CollectionUtil.isNotEmpty(vehicleList)){
					return "借款已生成";
				}
			}
		}

		loan.setRequestDate(loanDetailsVo.getCarRequestDate());

		// 车辆信息
		// 查询该车是否有进行中的债权
		VehicleVO vehicleVo = new VehicleVO();
		vehicleVo.setFrameNumber(vehicle.getFrameNumber());
		List<Vehicle> vehicleList = vehicleService.findListByVo(vehicleVo);
		if (CollectionUtil.isNotEmpty(vehicleList)) {
			for (Vehicle v : vehicleList) {
				Loan oldLoan = loanService.get(v.getLoanId());
				Extension extension = extensionService.getExtensionByLoanId(v.getLoanId());
					 
					 if (extension != null && (extension.getStatus().compareTo(EnumConstants.LoanStatus.取消.getValue()) != 0) && (extension.getStatus().compareTo(EnumConstants.LoanStatus.拒绝.getValue()) != 0)
								&& (extension.getStatus().compareTo(EnumConstants.LoanStatus.结清.getValue()) != 0 && (extension.getStatus().compareTo(EnumConstants.LoanStatus.预结清.getValue()) != 0))) {
							return ("该用户有尚未完成的展期贷款!");
						}
					 
					// 可重复借款的条件: 结清 || 拒绝 || 取消 ||预结清
					if (oldLoan != null && (oldLoan.getStatus().compareTo(EnumConstants.LoanStatus.取消.getValue()) != 0) && (oldLoan.getStatus().compareTo(EnumConstants.LoanStatus.拒绝.getValue()) != 0)
							&& (oldLoan.getStatus().compareTo(EnumConstants.LoanStatus.结清.getValue()) != 0 && (oldLoan.getStatus().compareTo(EnumConstants.LoanStatus.预结清.getValue()) != 0))) {
						return ("该用户有尚未完成的贷款!");
					}
					
			}
		}
		vehicleVo = VehicleAssembler.transferModel2VO(vehicle);
		vehicleVo.setPersonId(person.getId());
		vehicle = vehicleService.insert(vehicleVo);
		loan.setVehicleId(vehicle.getId());

		
		// 保存贷款信息
		loan.setPersonId(person.getId());
		// 新增 贷款信息
		loan.setStatus(EnumConstants.LoanStatus.新建.getValue());
		if (loanDetailsVo.getLoanTypes() != null) {
			loan.setLoanType(loanDetailsVo.getLoanTypes());
		}
		// ProductType代表车贷或小企贷
		if (loanDetailsVo.getProductTypeId() != null) {
			loan.setProductType(loanDetailsVo.getProductTypeId());
		}
		if (loanDetailsVo.getProductId() != null) {
			loan.setProductId(loanDetailsVo.getProductId());
		}
		//后台默认值 借款用途：资金周转    职业类型：其他
		loan.setPurpose("资金周转");
		loan = loanService.insert(loan);
		// 更新车辆表
		VehicleVO updateVehicleVo = new VehicleVO();
		updateVehicleVo.setId(vehicle.getId());
		updateVehicleVo.setLoanId(loan.getId());
		vehicleService.update(updateVehicleVo);
		// 信用卡信息
		if (creditHistory.getHasCreditCard() != null) {
			creditHistory.setPersonId(person.getId());
			creditHistory.setId(loanDetailsVo.getCreditHistoryId());
			creditHistory.setLoanId(loan.getId());
			creditHistoryService.insertCreditHistory(creditHistory);
		}
		// 新增联系人
		if (loanDetailsVo.getContacterList() != null) {
			for (Contacter contacters : loanDetailsVo.getContacterList()) {
				// 增加personId
				if (StringUtils.isNotBlank(contacters.getName())) {
					contacters.setBorrowerId(person.getId());
					contacters.setLoanId(loan.getId());
					contacterService.insert(contacters);
				}
			}
		}
		//
		// 插入系统日志
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.APPLY_LOAN.getValue());
		sysLog.setOptType(EnumConstants.OptionType.CREATE_LOAN.getValue());
		sysLog.setRemark("借款ID   " + loan.getId().toString());
		sysLogService.insert(sysLog);
		return "success";

	}

	/**
	 * 
	 * <pre>
	 * 编辑车贷
	 * </pre>
	 *
	 * @param loanDetailsVo
	 * @param creditHistory
	 * @param vehicle
	 * @param person
	 * @param company
	 * @param loan
	 * @return
	 */
	@RequestMapping("/modifyCarLoan")
	@ResponseBody
	@Transactional
	public String modifyCarLoan(LoanDetailsVO loanDetailsVo, CreditHistory creditHistory, Vehicle vehicle, Person person, Company company, Loan loan) {
		UserSession user = ApplicationContext.getUser();
		//loanDetailsVo.isModify 0没有变化    1变化了
		if(loanDetailsVo.getIsModify().equals("0")){
			//什么都不用操作
		}else{//变化了
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("id",loanDetailsVo.getLoanId());
			map.put("name", user.getLoginName());
			map.put("idname", user.getId());
			loanService.updatePushLcbReturnFraud(map);
		}
		
		
		// 更新单位信息
		if (loanDetailsVo.getCompanyId() != null) {
			company.setId(loanDetailsVo.getCompanyId());
			company.setAddress(loanDetailsVo.getCarCompanyAddress());
			company.setName(loanDetailsVo.getCarCompanyName());
			CompanyVO companyVo = CompanyAssembler.transferModel2VO(company);
			personCompanyService.update(companyVo);
		}
		vehicle.setRemark(null);// 备注设置为空
		// 更新车辆信息
		if (loanDetailsVo.getVehicleId() != null) {
			vehicle.setId(loanDetailsVo.getVehicleId());
			// 检查车架号是否已存在
			VehicleVO vehicleVO = VehicleAssembler.transferModel2VO(vehicle);
			String frameNumber = vehicleVO.getFrameNumber();
			VehicleVO vo = new VehicleVO();
			vo.setFrameNumber(frameNumber);
			List<Vehicle> vehicleList = vehicleService.findListByVo(vo);
			if (CollectionUtil.isNotEmpty(vehicleList)) {
				for (Vehicle v : vehicleList) {
					if (v.getLoanId() != null) {
						Loan oldLoan = loanService.get(v.getLoanId());
						if (oldLoan.getId().compareTo(loanDetailsVo.getLoanId()) == 0) {
							continue;
						}
						if (Integer.valueOf(oldLoan.getStatus()).compareTo(EnumConstants.LoanStatus.正常.getValue()) < 0
								&& Integer.valueOf(oldLoan.getStatus()).compareTo(EnumConstants.LoanStatus.拒绝.getValue()) != 0) {
							throw new BusinessException("该车有尚未完成的贷款!");
						}
					}
				}
			}
			vehicleVO.setLoanId(loanDetailsVo.getLoanId());
			vehicleService.update(vehicleVO);
		}
		person = loanDetailVOconvertCarPerson(loanDetailsVo, person);
		PersonVO personVO = PersonAssembler.transferModel2VO(person);
		personVO.setIdentifier(EnumConstants.ProductType.CAR_LOAN_STRING.getStrValue());
		personService.update(personVO);
		if (creditHistory.getHasCreditCard().compareTo(0L) == 0) { //没有选择信用卡
			creditHistory.setCardNum(0);
			creditHistory.setTotalAmount(BigDecimal.ZERO);
			creditHistory.setOverdrawAmount(BigDecimal.ZERO);
		} else {//选择信用卡,相关字段如果没有输入值,设置为0,并且此值在页面显示为空
			if (creditHistory.getCardNum() == null) {
				creditHistory.setCardNum(0);
			}
			if (creditHistory.getTotalAmount() == null) {
				creditHistory.setTotalAmount(BigDecimal.ZERO);
			}
			if (creditHistory.getOverdrawAmount() == null) {
				creditHistory.setOverdrawAmount(BigDecimal.ZERO);
			}
		}
		creditHistory.setLoanId(loanDetailsVo.getLoanId());
		creditHistory.setPersonId(person.getId());
		creditHistoryService.updateCreditHistoryByPersonId(creditHistory);

		// 更新联系人
		for (Contacter contacters : loanDetailsVo.getContacterList()) {
			// 增加personId
			if (StringUtils.isNotBlank(contacters.getName())) {
				contacters.setLoanId(loanDetailsVo.getLoanId());
				if (contacters.getId() != null) {
					contacters.setModifierId(user.getId());
					contacters.setModifier(user.getName());
					contacters.setModifiedTime(DateUtil.getTodayHHmmss());
					contacters.setBorrowerId(person.getId());
					contacterService.updateContacter(contacters);
				} else {
					contacters.setBorrowerId(person.getId());
					contacterService.insert(contacters);
				}
			}
		}

		// 更新贷款信息
		loan.setId(loanDetailsVo.getLoanId());
		if (loanDetailsVo.getLoanTypes() != null) {
			loan.setLoanType(loanDetailsVo.getLoanTypes());
		}
		loan.setAssessorId(loanDetailsVo.getAssessorName());
		loan.setCrmId(loanDetailsVo.getManagerName());
		SysUser crm = sysUserService.get(loanDetailsVo.getManagerName());
		SysUser bizDirector = sysUserService.getBizDirectorByCrm(crm);
		// 销量团队
		BaseArea dept = sysUserService.getBaseAreaByUserId(loanDetailsVo.getManagerName(), BizConstants.CREDIT2_SALESTEAM);
		if(dept != null && dept.getId() != null){
			
			loan.setSalesTeamId(dept.getId());
		}
		if (bizDirector != null) {
			loan.setBizDirectorId(bizDirector.getId());
		}
		LoanVO loanVoForUpdate = LoanAssembler.transferModel2VO(loan);
		loanService.update(loanVoForUpdate);
		// 插入系统日志
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.APPLY_LOAN.getValue());
		sysLog.setOptType(EnumConstants.OptionType.EDIT_LOAN.getValue());
		sysLog.setRemark("借款ID   " + loanVoForUpdate.getId().toString());
		sysLogService.insert(sysLog);
		return "success";

	}

	/**
	 * 
	 * <pre>
	 * 新增小企业贷
	 * </pre>
	 *
	 * @param loanDetailsVo
	 * @param creditHistory
	 * @param vehicle
	 * @param person
	 * @param company
	 * @param loan
	 * @return
	 */
	@RequestMapping("/addSELoan")
	@ResponseBody
	@Transactional
	public String addSELoan(LoanDetailsVO loanDetailsVo, CreditHistory creditHistory, Vehicle vehicle, Person person, Company company, Loan loan) {
		String result = null;// 返回消息
		// 判断该客户是否能重复申请
		checkLoanStatus(loanDetailsVo.getPersonIdnum(),loanDetailsVo.getProductTypeId().longValue());
		// 对申请金额进行验证
		BigDecimal requestMoney = loan.getRequestMoney();
		if (requestMoney == null) {
			return result = "申请金额不能为空";
		} ;
//		else if(EnumConstants.ProductList.CITY_WIDE_POS_LOAN.getValue().compareTo(loanDetailsVo.getProductId()) != 0  && loanDetailsVo.getProductId().compareTo(EnumConstants.ProductList.CITY_WIDE_SE_LOAN.getValue())!=0 ){
//			if (requestMoney.compareTo(new BigDecimal(100000)) < 0 || requestMoney.compareTo(new BigDecimal(500000)) > 0) {
//				return result = "申请金额必须在10万~50万之间";
//			}
//		}

		SysUserVO sysUserVo = new SysUserVO();
		sysUserVo.setCode(loanDetailsVo.getPersonNo());
		BaseArea dept = null;
		List<SysUser> candicatesSysUser = sysUserService.findListByVO(sysUserVo);
		// 客服和复核人员不能同一个人
		if (loan.getServiceId().compareTo(loanDetailsVo.getAssessorName()) == 0) {
			return result = "客服和复核人员不能同一个人";
		}
		// 判断客户经理输入是否正确
		if (candicatesSysUser == null || candicatesSysUser.size() < 1) {
			result = "客户经理编号和姓名不正确";
			return result;
		}

		// 判断c下客服，客户经理，复核人员在不在一个营业网点
		if (loan.getServiceId() != null && loanDetailsVo.getManagerName() != null && loanDetailsVo.getAssessorName() != null) {
			if (!loanService.checkManager(loan.getServiceId(), loanDetailsVo.getManagerName(), loanDetailsVo.getAssessorName())) {
				result = "客服，客户经理，复核人员应在一个营业网点";
				return result;
			} else {
				// 销量团队
				dept = sysUserService.getBaseAreaByUserId(loanDetailsVo.getManagerName(), BizConstants.CREDIT2_SALESTEAM);
				// baseAreaService.
				// 获取客户经理的id
				loan.setCrmId(loanDetailsVo.getManagerName());
				// loan.setUserId(loanDetailsVo.getManagerName());
				// 设置审核人员ID
				loan.setAssessorId(loanDetailsVo.getAssessorName());
				// 销售团队ID
				if(dept != null){
					loan.setSalesTeamId(dept.getId());
					
				}
				// 设置管理客服，默认为签约客服
				loan.setManagerId(loan.getServiceId());
			}
		} else {
			result = "客服，客户经理，复核人员应在一个营业网点";
			return result;

		}
		company.setAddress(loanDetailsVo.getCompanyAddress());
		company.setZipCode(loanDetailsVo.getCompanyZipCode());
		company.setName(loanDetailsVo.getCompanyName());
		// 公司信息
		company = personCompanyService.insert(company);
		// 个人信息
		person = loanDetailVOconvertPerson(loanDetailsVo, person);
		// 判断该人是否存在
		PersonVO personVo = new PersonVO();
		personVo.setIdnum(person.getIdnum());
		personVo.setProductType(EnumConstants.ProductType.SE_LOAN.getValue());
		List<Person> oldPersonList = personService.findListByVo(personVo);
		boolean isOldPerson = false;
		// 该人已存在
		if (CollectionUtil.isNotEmpty(oldPersonList)) {
			for (Person p : oldPersonList) {
				// Product product = productService.get(p.getProductId());
				if (EnumConstants.ProductType.SE_LOAN.getValue().compareTo(p.getProductType()) == 0) {
					// 该用户小企业贷存在，更新
					PersonVO oldPersonVO = PersonAssembler.transferModel2VO(person);
					oldPersonVO.setPersonNo(p.getPersonNo());
					oldPersonVO.setId(p.getId());
					// 更新就OK了
					oldPersonVO.setCompanyId(company.getId());
					personService.update(oldPersonVO);
					isOldPerson = true;
					break;
				}
			}
			if (!isOldPerson) {
				person.setPersonNo(oldPersonList.get(0).getPersonNo());
			}
		}
		if (!isOldPerson) {
			person.setIdentifier(EnumConstants.ProductType.SE_LOAN_STRING.getStrValue());
			person.setProductType(loanDetailsVo.getProductTypeId());
			if (StringUtil.isEmpty(person.getPersonNo())) {
				person.setPersonNo(loanService.getPersonNoByType(SerialNum.C1, loan.getSalesDeptId()));
			}
			person.setCompanyId(company.getId());
			person = personService.insert(person);
		}else{
			PersonVO personNewVo = new PersonVO();
			personNewVo.setIdnum(person.getIdnum());
			personNewVo.setCompanyId(company.getId());
			personNewVo.setIdentifier(EnumConstants.ProductType.SE_LOAN_STRING.getStrValue());
			personNewVo.setProductType(loanDetailsVo.getProductTypeId());
			List<Person> newPersonList = personService.findListByVo(personVo);
			LoanVO loanVO=new LoanVO();
			loanVO.setPersonId(newPersonList.get(0).getId());
			loanVO.setProductType(EnumConstants.ProductType.SE_LOAN.getValue());
			loanVO.setStatus(EnumConstants.LoanStatus.新建.getValue());
			Loan loanCredit=loanService.get(loanVO);
			if(loanCredit != null){
				return "借款已生成";
			}
		}

		// 保存贷款信息
		loan.setPersonId(person.getId());
		// 新增 贷款信息
		loan.setStatus(EnumConstants.LoanStatus.新建.getValue());
		if (vehicle.getBrand() != null) {
			loan.setVehicleId(vehicle.getId());
		}
		if (loanDetailsVo.getLoanTypes() != null) {
			loan.setLoanType(loanDetailsVo.getLoanTypes());
		}
		if (loanDetailsVo.getProductTypeId() != null) {
			loan.setProductType(loanDetailsVo.getProductTypeId());
		}
		if (loanDetailsVo.getProductId() != null) {
			loan.setProductId(loanDetailsVo.getProductId());
		}
		if(loanDetailsVo.getSourceOfRepay() != null){
			loan.setSourceOfRepay(loanDetailsVo.getSourceOfRepay());
		}
		if(loanDetailsVo.getProductId().compareTo(EnumConstants.ProductList.CITY_WIDE_POS_LOAN.getValue())==0
				|| loanDetailsVo.getProductId().compareTo(EnumConstants.ProductList.CITY_WIDE_SE_LOAN.getValue())==0){
			loan.setProductChannelID(EnumConstants.ProductChannel.CITY_WIDE_ID.getChannelId());
		}
		if(loanDetailsVo.getProductId().compareTo(EnumConstants.ProductList.WS_SE_LOAN.getValue())==0){
			loan.setProductChannelID(EnumConstants.ProductChannel.WS_SE_LOAN_ID.getChannelId());
		}
		loan = loanService.insert(loan);

		// 新增担保人
		if (loanDetailsVo.getGuaranteeList() != null) {
			for (Guarantee guarantee : loanDetailsVo.getGuaranteeList()) {
				if (guarantee.getGuaranteeType() != null) {
					// 该担保人为选中的担保人
					if (guarantee.getCheck() != null) {
						if (guarantee.getCheck() == 1) {
							guarantee.setFlag(EnumConstants.YesOrNo.YES.getValue());
						} else {
							guarantee.setIsFlagNull(true);
						}
					}
					guarantee.setPersonId(person.getId());
					guarantee.setLoanId(loan.getId());
					guaranteeService.insert(guarantee);
				}
			}
		}

		// 新增联系人
		if (loanDetailsVo.getContacterList() != null) {
			for (Contacter contacters : loanDetailsVo.getContacterList()) {
				// 增加personId
				if (StringUtils.isNotBlank(contacters.getName())) {
					contacters.setLoanId(loan.getId());
					contacters.setBorrowerId(person.getId());
					contacterService.insert(contacters);
				}
			}
		}

		// 插入系统日志
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.APPLY_LOAN.getValue());
		sysLog.setOptType(EnumConstants.OptionType.CREATE_LOAN.getValue());
		sysLog.setRemark("借款ID   " + loan.getId().toString());
		sysLogService.insert(sysLog);
		return "success";

	}
	/**
	 * 
	 * <pre>
	 * 新增小企业助学贷
	 * </pre>
	 *
	 * @param loanDetailsVo
	 * @param creditHistory
	 * @param vehicle
	 * @param person
	 * @param company
	 * @param loan
	 * @return
	 */
	@RequestMapping("/addEduLoan")
	@ResponseBody
	@Transactional
	public String addEduLoan(LoanDetailsVO loanDetailsVo, CreditHistory creditHistory, Vehicle vehicle, Person person, Company company, Loan loan,PersonTraining personTraining) {
		
		String result = null;// 返回消息
		// 判断该客户是否能重复申请
		checkLoanStatus(loanDetailsVo.getPersonIdnum(),loanDetailsVo.getProductTypeId().longValue());
		// 对申请金额进行验证
		BigDecimal requestMoney = loan.getRequestMoney();
		if (requestMoney == null) {
			return result = "申请金额不能为空";
		} ;
//		else if(EnumConstants.ProductList.CITY_WIDE_POS_LOAN.getValue().compareTo(loanDetailsVo.getProductId()) != 0  && loanDetailsVo.getProductId().compareTo(EnumConstants.ProductList.CITY_WIDE_SE_LOAN.getValue())!=0 ){
//			if (requestMoney.compareTo(new BigDecimal(100000)) < 0 || requestMoney.compareTo(new BigDecimal(500000)) > 0) {
//				return result = "申请金额必须在10万~50万之间";
//			}
//		}
//		if(true)
//			return null;
		SysUserVO sysUserVo = new SysUserVO();
		sysUserVo.setCode(loanDetailsVo.getPersonNo());
		BaseArea dept = null;
		List<SysUser> candicatesSysUser = sysUserService.findListByVO(sysUserVo);
		// 客服和复核人员不能同一个人
//		if (loan.getServiceId().compareTo(loanDetailsVo.getAssessorName()) == 0) {
//			return result = "客服和复核人员不能同一个人";
//		}
		// 判断客户经理输入是否正确
		if (candicatesSysUser == null || candicatesSysUser.size() < 1) {
			result = "客户经理编号和姓名不正确";
			return result;
		}

		// 判断c下客服，客户经理，复核人员在不在一个营业网点
		if (loan.getServiceId() != null && loanDetailsVo.getManagerName() != null && loanDetailsVo.getAssessorName() != null) {
			 
				// 销量团队
				dept = sysUserService.getBaseAreaByUserId(loanDetailsVo.getManagerName(), BizConstants.CREDIT2_SALESTEAM);
				// baseAreaService.
				// 获取客户经理的id
				loan.setCrmId(loanDetailsVo.getManagerName());
				// loan.setUserId(loanDetailsVo.getManagerName());
				// 设置审核人员ID
				loan.setAssessorId(loanDetailsVo.getAssessorName());
				// 销售团队ID
				if(dept != null){
					
					loan.setSalesTeamId(dept.getId());
				}
				// 设置管理客服，默认为签约客服
				loan.setManagerId(loan.getServiceId());
			 
		} else {
			result = "客服，客户经理，复核人员不能为空";
			return result;

		}
		company.setAddress(loanDetailsVo.getCompanyAddress());
		company.setZipCode(loanDetailsVo.getCompanyZipCode());
		company.setName(loanDetailsVo.getCompanyName());
		// 公司信息
		company = personCompanyService.insert(company);
		transformPersonObjFieldToStr(loanDetailsVo, person);
		
		
		person = loanDetailVOconvertPerson(loanDetailsVo, person);
		// 判断该人是否存在
		PersonVO personVo = new PersonVO();
		personVo.setIdnum(person.getIdnum());
		personVo.setProductType(EnumConstants.ProductType.SE_LOAN.getValue());
		List<Person> oldPersonList = personService.findListByVo(personVo);
		boolean isOldPerson = false;
		// 该人已存在
		if (CollectionUtil.isNotEmpty(oldPersonList)) {
			for (Person p : oldPersonList) {
				// Product product = productService.get(p.getProductId());
				if (EnumConstants.ProductType.SE_LOAN.getValue().compareTo(p.getProductType()) == 0) {
					// 该用户小企业贷存在，更新
					PersonVO oldPersonVO = PersonAssembler.transferModel2VO(person);
					oldPersonVO.setPersonNo(p.getPersonNo());
					oldPersonVO.setId(p.getId());
					// 更新就OK了
					oldPersonVO.setCompanyId(company.getId());
					personService.update(oldPersonVO);
					isOldPerson = true;
					break;
				}
			}
			if (!isOldPerson) {
				person.setPersonNo(oldPersonList.get(0).getPersonNo());
			}
		}
		if (!isOldPerson) {
			person.setIdentifier(EnumConstants.ProductType.SE_LOAN_STRING.getStrValue());
			person.setProductType(EnumConstants.ProductType.SE_LOAN.getValue());
			if (StringUtil.isEmpty(person.getPersonNo())) {
				person.setPersonNo(loanService.getPersonNoByType(SerialNum.C1, loan.getSalesDeptId()));
			}
			person.setCompanyId(company.getId());
			person = personService.insert(person);
		}else{
			PersonVO personNewVo = new PersonVO();
			personNewVo.setIdnum(person.getIdnum());
			personNewVo.setCompanyId(company.getId());
			personNewVo.setIdentifier(EnumConstants.ProductType.SE_LOAN_STRING.getStrValue());
			personNewVo.setProductType(loanDetailsVo.getProductTypeId());
			List<Person> newPersonList = personService.findListByVo(personVo);
			LoanVO loanVO=new LoanVO();
			loanVO.setPersonId(newPersonList.get(0).getId());
			loanVO.setProductType(EnumConstants.ProductType.SE_LOAN.getValue());
			loanVO.setStatus(EnumConstants.LoanStatus.新建.getValue());
			Loan loanCredit=loanService.get(loanVO);
			if(loanCredit != null){
				return "借款已生成";
			}
		}

		// 保存贷款信息
		loan.setPersonId(person.getId());
		loan.setRequestTime(Long.valueOf(loanDetailsVo.getRequestTimeValue()));
		// 新增 贷款信息
		loan.setStatus(EnumConstants.LoanStatus.新建.getValue());
		if (vehicle.getBrand() != null) {
			loan.setVehicleId(vehicle.getId());
		}
		if (loanDetailsVo.getLoanTypes() != null) {
			loan.setLoanType(loanDetailsVo.getLoanTypes());
		}
		if (loanDetailsVo.getProductTypeId() != null) {
			loan.setProductType(loanDetailsVo.getProductTypeId());
		}
		if (loanDetailsVo.getProductId() != null) {
			loan.setProductId(loanDetailsVo.getProductId());
		}
		if(loanDetailsVo.getSourceOfRepay() != null){
			loan.setSourceOfRepay(loanDetailsVo.getSourceOfRepay());
		}
//		if(loanDetailsVo.getProductId().compareTo(EnumConstants.ProductList.CITY_WIDE_POS_LOAN.getValue())==0
//				|| loanDetailsVo.getProductId().compareTo(EnumConstants.ProductList.CITY_WIDE_SE_LOAN.getValue())==0){
//			loan.setProductChannelID(EnumConstants.ProductChannel.CITY_WIDE_ID.getChannelId());
//		}
//		if(loanDetailsVo.getProductId().compareTo(EnumConstants.ProductList.WS_SE_LOAN.getValue())==0){
//			loan.setProductChannelID(EnumConstants.ProductChannel.WS_SE_LOAN_ID.getChannelId());
//		}
		loan.setProductChannelID(EnumConstants.ProductChannel.EDU_LOAN_ID.getChannelId());
		loan = loanService.insert(loan);
		personTraining.setLoanId(loan.getId());
		personTrainingService.insert(personTraining);
		// 信用卡信息
		if (creditHistory.getHasCreditCard() != null) {
			creditHistory.setPersonId(person.getId());
			creditHistory.setId(loanDetailsVo.getCreditHistoryId());
			creditHistory.setLoanId(loan.getId());
			creditHistoryService.insertCreditHistory(creditHistory);
		}

	

		// 新增联系人
		if (loanDetailsVo.getContacterList() != null) {
			for (Contacter contacters : loanDetailsVo.getContacterList()) {
				// 增加personId
				if (StringUtils.isNotBlank(contacters.getName())) {
					contacters.setLoanId(loan.getId());
					contacters.setBorrowerId(person.getId());
					contacterService.insert(contacters);
				}
			}
		}

		// 插入系统日志
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.APPLY_LOAN.getValue());
		sysLog.setOptType(EnumConstants.OptionType.CREATE_LOAN.getValue());
		sysLog.setRemark("借款ID   " + loan.getId().toString());
		sysLogService.insert(sysLog);
		return "success";

	}

	private void transformPersonObjFieldToStr(LoanDetailsVO loanDetailsVo,
			Person person) {
		Map<String,String> livingWithMap = new HashMap<String,String>();
		if(loanDetailsVo.getLivingWithType() != null){
			// 个人信息
			
			
			livingWithMap.put(loanDetailsVo.getLivingWithType(), loanDetailsVo.getLivingWithNum());
			if(loanDetailsVo.getLivingWithType().equals("其他")== true){			
				livingWithMap.put("customize", loanDetailsVo.getLivingWithOtherText());
			
			}
			person.setLivingWith(JSON.toJSONString(livingWithMap)); 
		}
		
		
		
		Map<String,String> incomeSourceMap = new HashMap<String,String>();
		if(loanDetailsVo.getSalaryIncome() != null){
			incomeSourceMap.put("salaryIncome",loanDetailsVo.getSalaryIncome());	
			
		}else{
			incomeSourceMap.put("salaryIncome"," ");
		}
		if(loanDetailsVo.getRentIncome() != null){
			incomeSourceMap.put("rentIncome",loanDetailsVo.getRentIncome());	
			
		}else{
			incomeSourceMap.put("rentIncome"," ");
		}
		if(loanDetailsVo.getOtherIncomeAmount()!= null){
			incomeSourceMap.put("otherIncomeAmount",loanDetailsVo.getOtherIncomeAmount());	
			
		}else{
			incomeSourceMap.put("otherIncomeAmount"," ");	
		}
		
		if(incomeSourceMap.size()>0){
			
			person.setIncomeSource(JSON.toJSONString(incomeSourceMap));
		}
	}

	/**
	 * 
	 * <pre>
	 * 编辑小企业贷
	 * </pre>
	 *
	 * @param loanDetailsVo
	 * @param creditHistory
	 * @param vehicle
	 * @param person
	 * @param company
	 * @param loan
	 * @return
	 */
	@RequestMapping("/modifySELoan")
	@ResponseBody
	@Transactional
	public String modifySELoan(LoanDetailsVO loanDetailsVo, CreditHistory creditHistory, Vehicle vehicle, Person person, Company company, Loan loan,PersonTrainingVO personTrainingVO) {

		UserSession user = ApplicationContext.getUser();

		// 更新单位信息
		if (loanDetailsVo.getCompanyId() != null) {
			company.setId(loanDetailsVo.getCompanyId());
			company.setAddress(loanDetailsVo.getCompanyAddress());
			company.setZipCode(loanDetailsVo.getCompanyZipCode());
			company.setName(loanDetailsVo.getCompanyName());
			CompanyVO companyVo = CompanyAssembler.transferModel2VO(company);
			personCompanyService.update(companyVo);
		}

		person = loanDetailVOconvertPerson(loanDetailsVo, person);
		
		transformPersonObjFieldToStr(loanDetailsVo, person);
		
		PersonVO personVO = PersonAssembler.transferModel2VO(person);
		personVO.setIdentifier(EnumConstants.ProductType.SE_LOAN_STRING.getStrValue());
		
		if(personVO.getLiveType() == null){
			personVO.setLiveType(" ");
		}
		personService.update(personVO);
		if(loanDetailsVo.getProductId().compareTo(EnumConstants.ProductList.STUDENT_LOAN.getValue())==0 ){
			
			personTrainingVO.setLoanId(loanDetailsVo.getLoanId());
			personTrainingVO.setPersonIdnum(loanDetailsVo.getPersonIdnum());	
			List<PersonTraining> trainingList = personTrainingService.findListByVo(personTrainingVO);
			if(trainingList.size()>0){
				
				personTrainingService.update(personTrainingVO);
			}else{
				PersonTraining personTraining = PersonTrainingAssembler.transferVO2Model(personTrainingVO);
				personTrainingService.insert(personTraining);
			}
			
			
			creditHistory.setLoanId(loanDetailsVo.getLoanId());
			creditHistory.setPersonId(person.getId());
			creditHistoryService.updateCreditHistoryByPersonId(creditHistory);
		}
		

		// 更新联系人
		for (Contacter contacters : loanDetailsVo.getContacterList()) {
			if(StringUtils.isNotBlank(contacters.getName())){
				contacters.setLoanId(loanDetailsVo.getLoanId());
				if (contacters.getId() != null) {
					contacters.setModifierId(user.getId());
					contacters.setModifier(user.getName());
					contacters.setModifiedTime(DateUtil.getTodayHHmmss());
					contacters.setBorrowerId(person.getId());
					contacterService.updateContacter(contacters);
				} else {
					contacters.setBorrowerId(person.getId());
					contacterService.insert(contacters);
				}
				
			}else{
				if (contacters.getId() != null ){
					contacterService.delete(contacters.getId());
					
				}
			}
		}

		// 更新担保人
		if (loanDetailsVo.getGuaranteeList() != null) {
			for (Guarantee guarantee : loanDetailsVo.getGuaranteeList()) {
				guarantee.setLoanId(loanDetailsVo.getLoanId());
				if (guarantee.getId() != null && guarantee.getName() != null) {
					guarantee.setModifierId(user.getId());
					guarantee.setModifier(user.getName());
					guarantee.setModifiedTime(DateUtil.getTodayHHmmss());
					guarantee.setPersonId(person.getId());
					// 该担保人为选中的担保人
					if (guarantee.getCheck() != null) {
						if (guarantee.getCheck() == 1) {
							guarantee.setFlag(EnumConstants.YesOrNo.YES.getValue());
						} else {
							guarantee.setIsFlagNull(true);
						}
					} else {
						guarantee.setIsFlagNull(true);
					}
					GuaranteeVO guaranteeVO = GuaranteeAssembler.transferModel2VO(guarantee);
					guaranteeService.update(guaranteeVO);
				} else if (guarantee.getGuaranteeType() != null) {
					guarantee.setPersonId(person.getId());
					// 该担保人为选中的担保人
					if (guarantee.getCheck() != null) {
						if (guarantee.getCheck() == 1) {
							guarantee.setFlag(EnumConstants.YesOrNo.YES.getValue());
						} else {
							guarantee.setIsFlagNull(true);
						}
					} else {
						guarantee.setIsFlagNull(true);
					}
					guaranteeService.insert(guarantee);
				}
			}
		}
		// 更新贷款信息
		loan.setId(loanDetailsVo.getLoanId());
		// 销量团队
		BaseArea dept = sysUserService.getBaseAreaByUserId(loanDetailsVo.getManagerName(), BizConstants.CREDIT2_SALESTEAM);
		loan.setSalesTeamId(dept.getId());
		if (loanDetailsVo.getLoanTypes() != null) {
			loan.setLoanType(loanDetailsVo.getLoanTypes());
		}
		
		if(loanDetailsVo.getRequestTimeValue() !=null){
			loan.setRequestTime(Long.valueOf(loanDetailsVo.getRequestTimeValue()));
		}
	
		LoanVO loanVoForUpdate = LoanAssembler.transferModel2VO(loan);
		loanService.update(loanVoForUpdate);
		// 插入系统日志
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.APPLY_LOAN.getValue());
		sysLog.setOptType(EnumConstants.OptionType.EDIT_LOAN.getValue());
		if(loan.getStatus().equals(EnumConstants.LoanStatus.终审中.getValue())
				||loan.getStatus().equals(EnumConstants.LoanStatus.初审重提.getValue())
				){
			sysLog.setOptModule(EnumConstants.OptionModule.FINAL_TRIAL.getValue());
			sysLog.setOptType(EnumConstants.OptionType.FINAL_TRIAL_CUST_INFO_EDIT.getValue());
			
		}else if(loan.getStatus().equals(EnumConstants.LoanStatus.初审中.getValue())
				||loan.getStatus().equals(EnumConstants.LoanStatus.初审拒绝.getValue())
				||loan.getStatus().equals(EnumConstants.LoanStatus.初审挂起.getValue())
				||loan.getStatus().equals(EnumConstants.LoanStatus.初审退回.getValue())
				||loan.getStatus().equals(EnumConstants.LoanStatus.终审退回初审.getValue())
				||loan.getStatus().equals(EnumConstants.LoanStatus.终审退回门店.getValue())
				||loan.getStatus().equals(EnumConstants.LoanStatus.门店重提.getValue())
				||loan.getStatus().equals(EnumConstants.LoanStatus.终审拒绝.getValue())
				){
			sysLog.setOptModule(EnumConstants.OptionModule.FIRST_TRIAL.getValue());
			sysLog.setOptType(EnumConstants.OptionType.FIRST_TRIAL_CUST_INFO_EDIT.getValue());
			
		}
		sysLog.setRemark("借款ID   " + loanVoForUpdate.getId().toString());
		sysLogService.insert(sysLog);
		return "success";
	}

	private void checkLoanStatus(String idNum, Long productId) {
		// 判断只有小企业贷需要验证
		if (productId.compareTo(Long.valueOf(EnumConstants.BlacklistLoanType.SE_LOAN.getValue())) == 0) {
			// 查询该用户是否还有未完成的贷款
			PersonVO personVO = new PersonVO();
			personVO.setProductType(EnumConstants.ProductType.SE_LOAN.getValue());
			personVO.setIdnum(idNum);
			List<Person> findPersonListByVo = personService.findListByVo(personVO);
			for (int i = 0; i < findPersonListByVo.size(); i++) {
				Person person = findPersonListByVo.get(i);
				LoanVO loanVO = new LoanVO();
				loanVO.setPersonId(person.getId());
				List<Loan> loanList = loanService.findListByVO(loanVO);
				if (CollectionUtil.isNotEmpty(loanList)) {
					for (Loan loan : loanList) {
							if ((loan.getStatus().compareTo(EnumConstants.LoanStatus.取消.getValue()) != 0) && (loan.getStatus().compareTo(EnumConstants.LoanStatus.拒绝.getValue()) != 0)
									&& (loan.getStatus().compareTo(EnumConstants.LoanStatus.结清.getValue()) != 0)) {
								throw new BusinessException("该用户有尚未完成的贷款!");
							}
						}
				}
			}
		}

	}

	/**
	 * 
	 * <pre>
	 * 判断是新用户还是老用户
	 * </pre>
	 *
	 * @return
	 */
	@RequestMapping("/findUserByIdnum")
	@ResponseBody
	public LoanDetailsVO findUserByIdnum(LoanDetailsVO loanDetailsVo) {
		PersonVO personVO = new PersonVO();
		// 产品信息ID
		personVO.setProductType(loanDetailsVo.getProductTypeId());
		personVO.setIdnum(loanDetailsVo.getIdnum());
		List<Person> personList = personService.findListByVo(personVO);
		// not judge old or new customer,check loan status and blacklist
		checkLoanStatus(loanDetailsVo.getIdnum(), loanDetailsVo.getProductTypeId().longValue());
		blacklistService.checkBlacklist(loanDetailsVo);
		// 老用户
		if (CollectionUtil.isNotEmpty(personList)) {
			// Person person = personList.get(0);
			loanDetailsVo.setPersonType("老客户");
		} else {
			loanDetailsVo.setPersonType("新客户");
		}
		return loanDetailsVo;
	}

	/***
	 * 
	 * <pre>
	 * 显示每个员工的产品类型
	 * </pre>
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("/getProductType")
	@ResponseBody
	public List<Product> getProductByCurrUser() {
		Long userId = ApplicationContext.getUser().getId();
		List<Product> productType = productService.findProductsByUserId(userId);
		 Product product =new Product();
		 product.setId(null);
		 product.setProductName("全部");
		productType.add(0, product);
		return productType;
	}
	
	/***
	 * 
	 * <pre>
	 * 显示每个员工的产品类型,不需要全部
	 * </pre>
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("/getProductByCurrUserNoAll")
	@ResponseBody
	public List<Product> getProductByCurrUserNoAll() {
		Long userId = ApplicationContext.getUser().getId();
		List<Product> productType = productService.findProductsByUserId(userId);
		return productType;
	}
	
	/***
	 * 
	 * <pre>
	 * 显示每个员工的产品类型  去掉小企业贷
	 * </pre>
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("/getProductTypeRemovemallBusiness")
	@ResponseBody
	public List<Product> getProductTypeRemovemallBusiness() {
		Long userId = ApplicationContext.getUser().getId();
		List<Product> productType = productService.findProductsByUserId(userId);
		Product rep=null;
			for(Product p:productType){
				if(p.getId()==(Long.valueOf((EnumConstants.ProductType.SE_LOAN.getValue())))){	rep=p;};
			
			}
		 productType.remove(rep);
		 Product product =new Product();
		 product.setId(null);
		 product.setProductName("全部");
		productType.add(0, product);
		return productType;
	}
	/***
	 * 
	 * <pre>
	 * 显示每个员工的贷款类型
	 * </pre>
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("/getLoanType")
	@ResponseBody
	public List<Product> getLoanTypeByCurrUser() {
		Long userId = ApplicationContext.getUser().getId();
		List<Product> loanType = productService.findProductTypeByUserId(userId);
		return loanType;
	}

	/***
	 * 
	 * <pre>
	 * 通过productId得到相应产品的期数
	 * </pre>
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("/getProductTermsByProductId")
	@ResponseBody
	public List<ProductDetail> getProductTermsByProductId(Long productId) {
		List<ProductDetail> productDetailList = productDetailService.findTermsByProductId(productId);
		return productDetailList;
	}
	
	/***
	 * 
	 * <pre>
	 * 通过参数得到相应产品的可用期数
	 * </pre>
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("/findListByVO")
	@ResponseBody
	public List<ProductDetail> findListByVO(ProductDetailVO productDetailVO) {
		productDetailVO.setStatus(1);
		List<ProductDetail> productDetailList = productDetailService.findListByVO(productDetailVO);
		for (ProductDetail productDetail : productDetailList) {
			productDetail.setTermName(productDetail.getTerm()+"期");
		}
		return productDetailList;
	}

	/**
	 * 
	 * <pre>
	 *       显示每个员工可操作的所有产品
	 * </pre>
	 *
	 * @return
	 */
	@RequestMapping("/getProducts")
	@ResponseBody
	public List<Product> getProductsByCurrUser() {
		Long userId = ApplicationContext.getUser().getId();

		List<Product> products = productService.findProductsByUserId(userId);
		return products;
	}
	
	
	/**
	 * 
	 * <pre>
	 * 显示每个员工可操作的所有产品(带全部)
	 * </pre>
	 *
	 * @return
	 */
	@RequestMapping("/getProductsAll")
	@ResponseBody
	public List<Product> getProductsByCurrUserAll() {
		Long userId = ApplicationContext.getUser().getId();

		List<Product> products = productService.findProductsByUserId(userId);
		Product product = new Product();
		product.setId(null);
		product.setProductName("全部");
		products.add(0,product);
		return products;
	}
	
	/***
	 * 
	 * <pre>
	 * 显示每个员工的贷款类型(带全部)
	 * </pre>
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("/getLoanTypeAll")
	@ResponseBody
	public List<Product> getLoanTypeByCurrUserAll() {
		Long userId = ApplicationContext.getUser().getId();
		List<Product> loanType = productService.findProductTypeByUserId(userId);
		Product product = new Product();
		product.setProductType(null);
		product.setProductTypeName("全部");
		loanType.add(0,product);
		return loanType;
	}

	/***
	 * 
	 * <pre>
	 * 显示该营业网点下的所有经理
	 * </pre>
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("/findManager")
	@ResponseBody
	public List<SysUser> findManager() {
		Long userId = ApplicationContext.getUser().getId();
		SysUser sysUser = sysUserService.get(userId);
		List<SysUser> sysUserLists = sysUserService.getAllCrmsInSalesDept(sysUser.getDataPermission());
		return sysUserLists;
	}

	/***
	 * 
	 * <pre>
	 * 显示该营业网点下的客服经理（除了当前的客服经理）
	 * </pre>
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("/getServicesInCurSalesDeptByProductId")
	@ResponseBody
	public List<SysUser> getServicesInCurSalesDeptByProductId(Long productId,Long userId) {
		
		if( userId == null){
			
			userId = ApplicationContext.getUser().getId();
		}
		List<SysUser> services = sysUserService.getServicesInCurSalesDeptByProductId(userId, productId);
		// 去除当前的客服经理
		if (services == null)
			return new ArrayList<SysUser>();

//		Long curUserId = ApplicationContext.getUser().getId();
//		SysUser excludeService = null;
//		for (SysUser service : services){
//			if (service.getId().compareTo(curUserId) == 0)
//				excludeService = service;
//			
//			if(userId != null && service.getId().compareTo(userId) == 0)
//				excludeService = service;
//			
//		}
//		if (excludeService != null)
//			services.remove(excludeService);

		return services;
	}

	/**
	 * 
	 * <pre>
	 * 
	 * 显示当前用户所在营业网点下的可以做某个产品的客户经理
	 * </pre>
	 *
	 */
	@RequestMapping("/getCrmsInCurSalesDeptByProductId")
	@ResponseBody
	public List<SysUser> getCrmsInCurSalesDeptByProductId(Long productId) {
		Long userId = ApplicationContext.getUser().getId();

		List<SysUser> sysUserList = sysUserService.getCrmsInSalesDeptByProductId(userId, productId);
		if (sysUserList.size() > 0) {
			for (SysUser sysUser : sysUserList) {
				sysUser.setName(sysUser.getName() + "  " + sysUser.getCode());
			}
		}

		return sysUserList;

	}

	@RequestMapping("/getCrmsInSalesDeptByProductIdAndSalesDeptId")
	@ResponseBody
	public List<SysUser> getCrmsInSalesDeptByProductIdAndSalesDeptId(Long salesDeptId, Long productId,Long organID) {
		
		List<SysUser> managerList = new ArrayList<SysUser>();
		if(productId == 8 ){
			if(organID != null){
				OrganSalesDepartmentVO organSalesDepartmentVO = new OrganSalesDepartmentVO();
				organSalesDepartmentVO.setOrganId(organID);
				List<OrganSalesDepartment> organSalesDepartmentList = organSalesDepartmentService.findListByVo(organSalesDepartmentVO);
				for (OrganSalesDepartment organSalesDepartment : organSalesDepartmentList) {				
					List<SysUser> sysUserList = sysUserService.getCrmsInSalesDeptByProductIdAndSalesDeptId(organSalesDepartment.getSalesDeptId(), productId);
					managerList.addAll(sysUserList);	
				}
				OrganSalesManagerVO organSalesManagerVO = new OrganSalesManagerVO();
				organSalesManagerVO.setOrganId(organID);				
				List<OrganSalesManager> organSalesManagerList = organSalesManagerService.findListByVo(organSalesManagerVO);	
				for( OrganSalesManager  organSalesManager: organSalesManagerList){
					SysUser sysUser = sysUserService.getSysUserByCode(organSalesManager.getCode());
					managerList.add(sysUser);
				}
			}
		}else if(salesDeptId != null && (productId != null && productId != 0)) {			
			List<SysUser> sysUserList = sysUserService.getCrmsInSalesDeptByProductIdAndSalesDeptId(salesDeptId, productId);			
			managerList.addAll(sysUserList);			
			
		}else if(salesDeptId != null && (productId != null && productId == 0)) {
			List<SysUser> sysUserList1 = sysUserService.getCrmsInSalesDeptByProductIdAndSalesDeptId(salesDeptId, 2l);	
			List<SysUser> sysUserList2 = sysUserService.getCrmsInSalesDeptByProductIdAndSalesDeptId(salesDeptId, 4l);
			if(sysUserList1.containsAll(sysUserList2)) {
				managerList.addAll(sysUserList1);		
			}else {
				managerList.addAll(sysUserList1);
				for(SysUser user : sysUserList2) {
					if(!sysUserList1.contains(user)) {
						managerList.add(user);
					}
				}
			}
		}
		
		if (managerList.size() > 0) {
			for (SysUser sysUser : managerList) {
				sysUser.setName(sysUser.getName() + "  " + sysUser.getCode());				
				
			}
		}

		return managerList;
	}
	
	@RequestMapping("/getCrmsInSalesDeptByProductIdAndSalesDeptIdHaveAll")
	@ResponseBody
	public List<SysUser> getCrmsInSalesDeptByProductIdAndSalesDeptIdHaveAll(Long salesDeptId, Long productId,Long organID) {
		List<SysUser> managerList = new ArrayList<SysUser>();
		List<BaseArea> listArea = getCurrentSaleDepts();
		for(BaseArea a : listArea) {
			if(a.getId() == null) continue;
			List<SysUser> sysUserList1 = sysUserService.getCrmsInSalesDeptByProductIdAndSalesDeptId(a.getId(), 2l);
			if(!managerList.containsAll(sysUserList1)) {
				managerList.addAll(sysUserList1);		
			}else {
				for(SysUser s: sysUserList1) {
					if(!managerList.contains(s)) {
						managerList.add(s);
					}
				}
			}
		}
		if (managerList.size() > 0) {
			for (SysUser sysUser : managerList) {
				sysUser.setName(sysUser.getName() + "  " + sysUser.getCode());				
			}
		}
		SysUser userAll = new SysUser();
		userAll.setCode("");
		userAll.setName("全部");
		managerList.add(0, userAll);
		return managerList;
	}


	/**
	 * 
	 * <pre>
	 * 获取当前用户所在的营业网点,通常只有一个可返回的营业网点，
	 * 如果用户是admin，则可以返回所有营业网点的信息
	 * </pre>
	 *
	 * @return
	 */
	@RequestMapping("/getCurSalesDept")
	@ResponseBody
	public List<BaseArea> getCurSalesDept() {
		List<BaseArea> salesDepts = new ArrayList<BaseArea>();
		UserSession curUser = ApplicationContext.getUser();
		Integer userType = curUser.getUserType();
		List<Product> enableProdList = productService.findProductsByUserId(curUser.getId());
		if (userType == null)
			return null;
		if(userType.equals(EnumConstants.UserType.CHANNEL_SPECIALIST.getValue())){
			
			List<BaseArea> seloanSalesDepts = baseAreaService.getSeloanDept();
		
			
			return seloanSalesDepts;
			
		}
		
		if (userType == EnumConstants.UserType.SYSTEM_ADMIN.getValue() || EnumConstants.UserType.AUDIT.getValue() == userType) {
			BaseAreaVO baseAreaVO = new BaseAreaVO();
			baseAreaVO.setIdentifier(BizConstants.CREDIT2_SALESDEPARTMENT);
		
			List<BaseArea> allSalesDepts = baseAreaService.findListByVo(baseAreaVO);
			BaseArea baseArea = new BaseArea();
			baseArea.setId(null);
			baseArea.setName("全部");
			allSalesDepts.add(0, baseArea);
			return allSalesDepts;
		}
		
		boolean seLoanUser = false;
		List<BaseArea> curSalesDept = new ArrayList<BaseArea>();
		for(Product prod:enableProdList){
			if(prod.getId().compareTo(EnumConstants.ProductList.CAR_LOAN.getValue())==0
					||prod.getId().compareTo(EnumConstants.ProductList.CAR_NEW_LOAN.getValue())==0){					
			}else{
				seLoanUser = true;
				break;
			}
			
		}
		if(seLoanUser){
			 curSalesDept = baseAreaService.getSeloanDept();
		}else{			
			curSalesDept = sysUserService.getCurSalesDept();
		}
		if (curSalesDept != null) {
		
			salesDepts.addAll(curSalesDept);
		}
		return salesDepts;
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
		// 判断当时admin账户或者是审核权限的用户类型的时候城市取所有的
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
		return citys;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	/**
	 * 
	 * <pre>
	 * 获取当前用户
	 * </pre>
	 *
	 * @return
	 */
	@RequestMapping("/getCurUser")
	@ResponseBody
	public SysUser getCurUser() {
		if (ApplicationContext.getUser() == null)
			return null;

		Long curUserId = ApplicationContext.getUser().getId();

		SysUser curUser = sysUserService.get(curUserId);

		return curUser;
	}

	@RequestMapping("/getBaseAreaById")
	@ResponseBody
	public BaseArea getBaseAreaById(Long id) {
		return baseAreaService.get(id);
	}

	@RequestMapping("/getSysUserById")
	@ResponseBody
	public SysUser getSysUserById(Long id) {
		return sysUserService.get(id);
	}

	// 车贷数据
	private Person loanDetailVOconvertCarPerson(LoanDetailsVO loanDetailsVo, Person person) {

		person.setMobilePhone(loanDetailsVo.getCarPersonMobilePhone());
		person.setEmail(loanDetailsVo.getCarPersoneEmail());
		person.setHasChildren(loanDetailsVo.getCarPersonHasChildren());
		person.setAddress(loanDetailsVo.getCarPersonAddress());
		person.setHomePhone(loanDetailsVo.getCarPersonHomePhone());
		person.setZipCode(loanDetailsVo.getCarPersonZipCode());
		person.setRentPerMonth(loanDetailsVo.getCarRentPerMonth());

		person.setId(loanDetailsVo.getPersonId());
		person.setName(loanDetailsVo.getCarPersonName().replaceAll(" ",""));
		person.setSex(loanDetailsVo.getCarPersonSex());
		person.setIdnum(loanDetailsVo.getCarPersonIdnum());
		person.setMarried(loanDetailsVo.getCarPersonMarried());
		person.setEducationLevel(loanDetailsVo.getCarPersonEducationLevel());
		person.setIncomePerMonth(loanDetailsVo.getCarIncomePerMonth());
		person.setRentPerMonth(loanDetailsVo.getCarRentPerMonth());
		person.setCompanyType(loanDetailsVo.getCarCompanyType());
		person.setFoundedDate(loanDetailsVo.getCarFoundedDate());
		return person;
	}

	// 小企业贷数据
	private Person loanDetailVOconvertPerson(LoanDetailsVO loanDetailsVo, Person person) {
		person.setId(loanDetailsVo.getPersonId());
		person.setName(loanDetailsVo.getPersonName().replaceAll(" ",""));
		person.setSex(loanDetailsVo.getPersonSex());
		person.setIdnum(loanDetailsVo.getPersonIdnum());
		person.setMarried(loanDetailsVo.getPersonMarried());
		person.setEducationLevel(loanDetailsVo.getPersonEducationLevel());
		person.setHasChildren(loanDetailsVo.getPersonHasChildren());
		person.setZipCode(loanDetailsVo.getPersonZipCode());
		person.setAddress(loanDetailsVo.getPersonAddress());
		person.setMobilePhone(loanDetailsVo.getPersonMobilePhone());
		person.setEmail(loanDetailsVo.getPersoneEmail());
		person.setHomePhone(loanDetailsVo.getPersonHomePhone());
		person.setIncomePerMonth(loanDetailsVo.getPersonIncomePerMonth());

		return person;
	}
	
	

	@RequestMapping("/seImageUploadView/{loanId}")
	public String seImageUploadView(@PathVariable("loanId") String loanId, Model model) {
		model.addAttribute("loanId", loanId);
		Loan loan = loanService.get(Long.parseLong(loanId));
		if (loan != null) {
			Long personId = loan.getPersonId();
			Long productId = Long.valueOf(loan.getProductType());
			model.addAttribute("personId", personId);
			model.addAttribute("productId", productId);
			Person person = personService.get(personId);
			if (person != null) {
				model.addAttribute("personName", person.getName());
			}
		}
		model.addAttribute("optModule", EnumConstants.OptionModule.APPLY_LOAN.getValue());
		SysParameter sysParameter = sysParameterService.getByCode(SysParameterEnum.VALID_ATTACHMENT.name());
		String fileSizeLimit = "2";
		String fileQueueLimit = "40";
		if (sysParameter == null || StringUtils.isEmpty(sysParameter.getParameterValue())) {
			model.addAttribute("fileSizeLimit", fileSizeLimit);
			model.addAttribute("fileQueueLimit", fileQueueLimit);
		} else {
			String[] attachmentArray = sysParameter.getParameterValue().split(";");
			if (attachmentArray != null) {
				if (!StringUtils.isEmpty(attachmentArray[0])) {
					fileSizeLimit = attachmentArray[0];
				}
				if (!StringUtils.isEmpty(attachmentArray[1])) {
					fileQueueLimit = attachmentArray[1];
				}
			}
			model.addAttribute("fileSizeLimit", fileSizeLimit);
			model.addAttribute("fileQueueLimit", fileQueueLimit);
		}
		return "apply/seLoan/imageUploadView";
	}

	@RequestMapping("/carImageUploadView/{loanId}")
	public String carImageUploadView(@PathVariable("loanId") String loanId, Model model) {
		model.addAttribute("loanId", loanId);
		Loan loan = loanService.get(Long.parseLong(loanId));
		if (loan != null) {
			Long personId = loan.getPersonId();
			Long productId = Long.valueOf(loan.getProductType());
			model.addAttribute("personId", personId);
			model.addAttribute("productId", productId);
			Person person = personService.get(personId);
			if (person != null) {
				model.addAttribute("personName", person.getName());
			}
		}
		model.addAttribute("optModule", EnumConstants.OptionModule.APPLY_LOAN.getValue());
		SysParameter sysParameter = sysParameterService.getByCode(SysParameterEnum.VALID_ATTACHMENT.name());
		String fileSizeLimit = "2";
		String fileQueueLimit = "40";
		if (sysParameter == null || StringUtils.isEmpty(sysParameter.getParameterValue())) {
			model.addAttribute("fileSizeLimit", fileSizeLimit);
			model.addAttribute("fileQueueLimit", fileQueueLimit);
		} else {
			String[] attachmentArray = sysParameter.getParameterValue().split(";");
			if (attachmentArray != null) {
				if (!StringUtils.isEmpty(attachmentArray[0])) {
					fileSizeLimit = attachmentArray[0];
				}
				if (!StringUtils.isEmpty(attachmentArray[1])) {
					fileQueueLimit = attachmentArray[1];
				}
			}
			model.addAttribute("fileSizeLimit", fileSizeLimit);
			model.addAttribute("fileQueueLimit", fileQueueLimit);
		}
		return "apply/carLoan/imageUploadView";
	}

	@RequestMapping("/carExtensionImageUploadView/{loanId}")
	public String carExtensionImageUploadView(@PathVariable("loanId") String loanId, Model model) {
		model.addAttribute("loanId", loanId);
		Extension extension = extensionService.get(Long.parseLong(loanId));
		if (extension != null) {
			Long personId = extension.getPersonId();
			Long productId = Long.valueOf(extension.getProductType());
			model.addAttribute("personId", personId);
			model.addAttribute("productId", productId);
			Person person = personService.get(personId);
			if (person != null) {
				model.addAttribute("personName", person.getName());
			}
		}
		model.addAttribute("optModule", EnumConstants.OptionModule.APPLY_LOAN.getValue());
		SysParameter sysParameter = sysParameterService.getByCode(SysParameterEnum.VALID_ATTACHMENT.name());
		String fileSizeLimit = "2";
		String fileQueueLimit = "40";
		if (sysParameter == null || StringUtils.isEmpty(sysParameter.getParameterValue())) {
			model.addAttribute("fileSizeLimit", fileSizeLimit);
			model.addAttribute("fileQueueLimit", fileQueueLimit);
		} else {
			String[] attachmentArray = sysParameter.getParameterValue().split(";");
			if (attachmentArray != null) {
				if (!StringUtils.isEmpty(attachmentArray[0])) {
					fileSizeLimit = attachmentArray[0];
				}
				if (!StringUtils.isEmpty(attachmentArray[1])) {
					fileQueueLimit = attachmentArray[1];
				}
			}
			model.addAttribute("fileSizeLimit", fileSizeLimit);
			model.addAttribute("fileQueueLimit", fileQueueLimit);
		}
		return "apply/carLoan/imageUploadView";
	}

	/**
	 * <pre>
	 * 担保人检验
	 * </pre>
	 * 
	 * @author zhangshihai
	 * @param guaranteeList
	 * @param loan 
	 * @return
	 */
	public String checkGuarantee(List<Guarantee> guaranteeList, Loan loan) {
//		int sum = 0;
//		int loanGuaSum = 0;
//		if(loan.getLegalGuaranteeCname1() !=null){
//			loanGuaSum +=1;
//		}if(loan.getLegalGuaranteeCname2() !=null){
//			loanGuaSum +=1;
//		}if(loan.getNaturalGuaranteeName1() !=null){
//			loanGuaSum +=1;
//		}if(loan.getNaturalGuaranteeName2() !=null){
//			loanGuaSum +=1;
//		};
		ArrayList<String> guaList = new ArrayList<String>();
		if(loan.getLegalGuaranteeCname1()!=null)
			guaList.add(loan.getLegalGuaranteeCname1());
		if(loan.getLegalGuaranteeCname2()!=null)
			guaList.add(loan.getLegalGuaranteeCname2());
		if(loan.getNaturalGuaranteeName1()!=null)
		guaList.add(loan.getNaturalGuaranteeName1());
		if(loan.getNaturalGuaranteeName2()!=null)
			guaList.add(loan.getNaturalGuaranteeName2());
		
		String[] guaNames = null; 
		if(loan.getGuaranteeName() != null){
			guaNames =  loan.getGuaranteeName().split(",");
			for (int i = 0; i < guaNames.length; i++) {
				if(guaNames[i] != null)
					guaList.add(guaNames[i]);
			};
			
			
		}
		
//		loan.getNaturalGuaranteeName1();
//		loan.getNaturalGuaranteeName2();
		// 担保人为空
//		if (CollectionUtil.isNullOrEmpty(guaranteeList)) {
//			return "请填写担保人";
//		}
		for (Guarantee guarantee : guaranteeList) {
			// 选中的个数
//			if (guarantee.getFlag() != null) {
//				sum += 1;
//			}
//			if (CommonUtil.checkObjectIsNotEmpty(guarantee)) {
//				return "请填写担保人";
//			}
			Integer guaranteeType = guarantee.getGuaranteeType();
//			if (!CommonUtil.checkObjectIsNotEmpty(guaranteeType)) {
//				return "请选择担保人类型";
//			}
//			if (StringUtils.isBlank(guarantee.getCompanyFullName())) {
//				return "请填写企业全称";
//			}
//			if (StringUtils.isBlank(guarantee.getCompanyAddress())) {
//				return "请填写企业地址";
//			}
//			if (StringUtils.isBlank(guarantee.getZipCode())) {
//				return "请填写邮政编码";
//			}
			// 担保人为自然人
			if (guaranteeType.compareTo(EnumConstants.GuaranteeType.NATURAL_GUARANTEE.getValue()) == 0 && guaList.contains(guarantee.getName())) {
//				if (StringUtils.isBlank(guarantee.getName())) {
//					return "请填写担保人姓名";
//				}
				if (StringUtils.isBlank(guarantee.getIdnum())) {
					return "请填写担保人身份证号码";
				}
//				if (!CommonUtil.checkObjectIsNotEmpty(guarantee.getHasChildren())) {
//					return "请填写担保人子女";
//				}
//				if (!CommonUtil.checkObjectIsNotEmpty(guarantee.getMarried())) {
//					return "请选择担保人婚姻状况";
//				}
//				if (StringUtils.isBlank(guarantee.getEducationLevel())) {
//					return "请选择担保人最高学历";
//				}
//				if (StringUtils.isBlank(guarantee.getAddress())) {
//					return "请填写担保人住宅地址";
//				}
				if (StringUtils.isBlank(guarantee.getMobilePhone())) {
					return "请填写担保人手机号码";
				}
//				if (StringUtils.isBlank(guarantee.getEmail())) {
//					return "请填写担保人常用邮箱";
//				}
			}
		}
//
//		if (sum == 0 && loanGuaSum != 0) {
//			return "没有选中担保人,请先编辑指定担保人";
//		}
		return "success";
	}

	/**
	 * 
	 * <pre>
	 * 申请展期
	 * </pre>
	 *
	 * @param extensionTime
	 * @param loanId
	 * @return
	 */
	@RequestMapping("/cancelLoan")
	@ResponseBody
	public String cancelLoan(Long loanId,String LoanCancelSelect,String beizhu) {
		if (loanId != null) {
			LoanVO loanVO = new LoanVO();
			loanVO.setId(loanId);
			loanVO.setStatus(EnumConstants.LoanStatus.取消.getValue());
			loanService.update(loanVO);
			// 插入业务日志
			BusinessLog businessLog = new BusinessLog();
			businessLog.setFlowStatus(EnumConstants.BusinessLogStatus.CONTRACT_CANCELLED.getValue());
			businessLog.setLoanId(loanId);
			String messageStr="借款取消";
			if(null!=LoanCancelSelect){
				messageStr=messageStr+",取消原因:"+LoanCancelSelect+",备注:"+beizhu;
			}
			businessLog.setMessage(messageStr);
			businessLogService.insert(businessLog);
			return "success";
		} else {
			return "false";
		}
	}

	// 获取静态页面
	@RequestMapping("/getHtml")
	public String getHtml(String handler,String productCode) {
		return "apply/"+productCode+handler;
	}
	
	/**
	 * 填充前端公司文本信息
	 * @param company
	 */
	private void populateCompany(Company company) {
		if(company.getMemberType()!=null){
			if(EnumConstants.MemberType.FREE.getValue().compareTo(company.getMemberType())==0){
				company.setMemberTypeText(EnumConstants.MemberType.FREE.getText());
			}else if(EnumConstants.MemberType.HALF_YEAR.getValue().compareTo(company.getMemberType())==0){
				company.setMemberTypeText(EnumConstants.MemberType.HALF_YEAR.getText());
				
			}else if(EnumConstants.MemberType.MORE_THAN_HALF_YEAR.getValue().compareTo(company.getMemberType())==0){
				company.setMemberTypeText(EnumConstants.MemberType.MORE_THAN_HALF_YEAR.getText());
				
			}
			
		}
		
	};
	/**
	 * 填充前端公司文本信息
	 * @param company
	 */
	private void populateLoanInfo(Loan loan) {
		loan.setCurUserType(ApplicationContext.getUser().getUserType());
		if(loan.getAuditMemberType()!=null){
			if(EnumConstants.MemberType.FREE.getValue().compareTo(loan.getAuditMemberType().longValue())==0){
				loan.setAuditMemberTypeText(EnumConstants.MemberType.FREE.getText());
			}else if(EnumConstants.MemberType.HALF_YEAR.getValue().compareTo(loan.getAuditMemberType().longValue())==0){
				loan.setAuditMemberTypeText(EnumConstants.MemberType.HALF_YEAR.getText());
				
			}else if(EnumConstants.MemberType.MORE_THAN_HALF_YEAR.getValue().compareTo(loan.getAuditMemberType().longValue())==0){
				loan.setAuditMemberTypeText(EnumConstants.MemberType.MORE_THAN_HALF_YEAR.getText());
				
			}
			
		}
		
		if(loan.getOrganID() != null){
			Organ organ = organService.get(loan.getOrganID());
			if(organ!=null)
			{
				loan.setOrgan(organ);
				loan.setOrganCode(organ.getCode());
				loan.setOrganName(organ.getName());
			}
			
			
			
		}
		if(loan.getSchemeID() != null){
			ChannelPlanCheck channelPlanCheck = channelPlanCheckService.getReplyById(loan.getSchemeID());
			if(channelPlanCheck!= null){
				loan.setChannelPlanCheck(channelPlanCheck);	
				
				loan.setChannelPlanName(channelPlanCheck.getName());
			}
		}
		
		
		
		
	}
	
	/**
	 * 填充前端公司文本信息
	 * @param loanDetailsVo 
	 * @param company
	 */
	@SuppressWarnings("unchecked")
	private void populatePerson(LoanDetailsVO loanDetailsVo, Person person) {
		if(person.getLivingWith()!=null){
			String livingWith = person.getLivingWith();
			Map<String,String> livingWithMap = (Map<String,String>)JSON.parse(livingWith);
//			Boolean flag = false;
			 for (String key : livingWithMap.keySet()) { 
				
			 		if(key.equals("customize") != true){
			 			loanDetailsVo.setLivingWithType(key);
			 			loanDetailsVo.setLivingWithNum(livingWithMap.get(key));			 
 				 		
			 		}else{
			 			loanDetailsVo.setLivingWithOtherText(livingWithMap.get(key));
			 		}
					
				 
			       
			  } 
		}
		
		if(person.getIncomeSource()!=null){
			String incomeSource = person.getIncomeSource();
			Map<String,String> incomeSourceMap = (Map<String,String>)JSON.parse(incomeSource);
			 for (String key : incomeSourceMap.keySet()) { 
				 
				 
				 if(key.equals("salaryIncome")){
						loanDetailsVo.setSalaryIncome(incomeSourceMap.get(key));	
						
				 }else if(key.equals("rentIncome")){
						loanDetailsVo.setRentIncome(incomeSourceMap.get(key));	
						
				 }else if(key.equals("otherIncomeAmount")){
						loanDetailsVo.setOtherIncomeAmount(incomeSourceMap.get(key));	
						
				 }
						 
			       
			  } 
		}
		
		if(person.getChildrenNum() != null && 0 == person.getChildrenNum() ){
			person.setChildrenNum(null);
			
		}
		if(person.getRentPerMonth() != null && new BigDecimal(0).compareTo(person.getRentPerMonth()) == 0  ){
			person.setRentPerMonth(null);
			
		}	
	 
	}
	
	/**
	 * 添加客户培训信息
	 * @param loanDetailsVo
	 * @param loan
	 * @param person
	 */
	private void populatePersonTraining(LoanDetailsVO loanDetailsVo,Loan loan,Person person){
		
		PersonTrainingVO personTrainingVO = new PersonTrainingVO();
		personTrainingVO.setLoanId(loan.getId());
		personTrainingVO.setPersonIdnum(person.getIdnum());
		List<PersonTraining> personTrainingList = personTrainingService.findListByVo(personTrainingVO );
		
		if (CollectionUtil.isNotEmpty(personTrainingList)) {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM"); 
			try {
				if(	personTrainingList.get(0).getEntranceTime() != null){
					
					personTrainingList.get(0).setEntranceDate(sdf.format(personTrainingList.get(0).getEntranceTime()));
				}
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
			
			
			
			loanDetailsVo.setPersonTraining(personTrainingList.get(0));
		}
	}
	
	
	
	
	
	/**
	 * 
	 * <pre>
	 * 获取当前用户可以操作的机构信息
	 * </pre>
	 * @param AreaId 
	 *
	 * @return
	 */
	
	private List<Organ> getOrganInfoByDeptId(Long AreaId) {
//		List<BaseArea> salesDeptList = getCurSalesDept();
//		
		OrganSalesDepartmentVO organSalesDepartmentVO = new OrganSalesDepartmentVO();
		organSalesDepartmentVO.setSalesDeptId(Long.valueOf(AreaId));
		List<OrganSalesDepartment> organSalesDepartmentList = organSalesDepartmentService.findListByVo(organSalesDepartmentVO);
		List<Organ> organList = new ArrayList<Organ>();
		
		
	
		for(OrganSalesDepartment organSalesDepartment:organSalesDepartmentList){
			
			Organ organ = organService.get(organSalesDepartment.getOrganId());
			organList.add(organ);
		}
	
				
		return organList;
	}
	
	@RequestMapping("downloadAttachment")
	@ResponseBody
	public String downloadAttachment(LoanVO loanVO, HttpServletRequest request, HttpServletResponse response) throws SocketException, IOException {
		String downloadPath = credit2Properties.getDownloadPath();
		
		String ftpRemotePath = sysParameterService.getByCode("FTP_REMOTEPATH").getParameterValue();
		String ftpHost = sysParameterService.getByCode("FTP_HOST").getParameterValue();
		String ftpPort = sysParameterService.getByCode("FTP_PORT").getParameterValue();
		String ftpUserName = sysParameterService.getByCode("FTP_USERNAME").getParameterValue();
		String ftpPassWord = sysParameterService.getByCode("FTP_PASSWORD").getParameterValue();
		logger.info("downloadAttachment:"+"FTP_REMOTEPATH:"+ftpRemotePath+"FTP_HOST:"+ftpHost+"FTP_PORT:"+ftpPort+"FTP_USERNAME:"+ftpUserName+"FTP_PASSWORD:"+ftpPassWord);
		
		File localFtpFiles = new File(downloadPath + File.separator + "eduLoan"+File.separator+"oldEduLoan");
		
		
		FTPUtil ftpUtil = new FTPUtil(); 
		ftpUtil.connectServer(ftpHost, ftpPort, ftpUserName, ftpPassWord, ftpRemotePath);
		
		 String dateStr = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		List<EduLoanImage> eduLoanImages = eduLoanImageService.findByContractNo(loanVO.getContractNo());
		String curPath=null;
		int fileCount = 0;
		
		
		for(EduLoanImage eduLoanImage:eduLoanImages){
			if (!localFtpFiles.exists()) { 
				localFtpFiles.mkdirs();
			}
			
				curPath = eduLoanImage.getFilePath().substring(0,eduLoanImage.getFilePath().lastIndexOf("/")+1);
				String curFileName = eduLoanImage.getFilePath().substring(eduLoanImage.getFilePath().lastIndexOf("/")+1,eduLoanImage.getFilePath().length());
				ftpUtil.changeDirectory(eduLoanImage.getFilePath().substring(0,eduLoanImage.getFilePath().lastIndexOf("/")+1));
				List<String> fileNameList = ftpUtil.getFileList(eduLoanImage.getFilePath().substring(0,eduLoanImage.getFilePath().lastIndexOf("/")+1));
				
				for (String fileName : fileNameList) {
					String ftpFile=downloadPath + File.separator + "eduLoan"+File.separator+"oldEduLoan"+File.separator+eduLoanImage.getContractNo()+"_"+fileName.trim().replace(" ", "").toString();
					if(curFileName.equals(fileName)){
						ftpUtil.download(fileName, ftpFile);
						fileCount++;
					}else{
						continue;
					}
					
				}
			
		}
		ftpUtil.closeServer();
		 String zipFilePath = downloadPath + File.separator + "eduLoan"+File.separator+"zip"+File.separator + loanVO.getContractNo()+"_"+dateStr + ".zip";
			
		 String zipTargetFilePath=downloadPath + File.separator + "eduLoan"+File.separator+"oldEduLoan";//压缩文件路径
		 
         File zipFileFolder = new File(downloadPath + File.separator + "eduLoan"+File.separator+"zip");
        if (!zipFileFolder.exists()) { 
        	zipFileFolder.mkdirs();
        }
        FileDownUtils.zipFiles(zipTargetFilePath, zipFilePath);
        FileDownUtils.deleteDir(localFtpFiles);
        
        File zipFile = new File(zipFilePath);
        // 取得文件名。
        String fileName = zipFile.getName();
   
        FileUtil.downloadFile(zipFile, true, response, fileName);
        return "success";
	}
    
	/**
	 * 
	 * <pre>
	 * 展期下拉框
	 * </pre>
	 *
	 * @author 
	 * @return
	 */
	@RequestMapping("/getExtensionTimeList")
	@ResponseBody
	public List<ExtensionTime> getExtensionTimeList() {
		//Integer userType = ApplicationContext.getUser().getUserType();
		List<ExtensionTime> extensionTimeList = new ArrayList<ExtensionTime>();
		extensionTimeList.add(new ExtensionTime("所有", -1));
		extensionTimeList.add(new ExtensionTime("无", 0));
		Integer time=	loanExtensionService.maxExtensionTime();
		if(time!=null){
			for(int i=1 ;i<=time;i++){
				extensionTimeList.add(new ExtensionTime(i+"", i));
			}
		}
		return extensionTimeList;
	}
	
//	/**
//	 * 查找合同是否
//	 */
//	@RequestMapping("/LoanIsAutoCancle")
//	@ResponseBody
//	public Map<String,Object> loanIsAutoCancel(long loanId){
//		Loan l = new Loan();
//		l.setLoanId(loanId);
//		l.setStatus(EnumConstants.LoanStatus.取消.getValue());
//		
//		Map<String,Object> result = new HashMap<String,Object>();
//	}

	public static class ExtensionTime {
		private String name;
		private Integer value;

		/**
		 * @param name
		 * @param value
		 */
		public ExtensionTime(String name, Integer value) {
			super();
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Integer getValue() {
			return value;
		}

		public void setValue(Integer value) {
			this.value = value;
		}
	}
	
	@SuppressWarnings("unused")
	private List<BaseArea> getCurrentSaleDepts() {
		List<BaseArea> salesDepts = new ArrayList<BaseArea>();
		UserSession curUser = ApplicationContext.getUser();
		Integer userType = curUser.getUserType();
		List<Product> enableProdList = productService.findProductsByUserId(curUser.getId());
		if (userType == null)
			return null;
		if(userType.equals(EnumConstants.UserType.CHANNEL_SPECIALIST.getValue())){
			
			List<BaseArea> seloanSalesDepts = baseAreaService.getSeloanDept();
		
			
			return seloanSalesDepts;
			
		}
		
		if (userType == EnumConstants.UserType.SYSTEM_ADMIN.getValue() || EnumConstants.UserType.AUDIT.getValue() == userType) {
			BaseAreaVO baseAreaVO = new BaseAreaVO();
			baseAreaVO.setIdentifier(BizConstants.CREDIT2_SALESDEPARTMENT);
		
			List<BaseArea> allSalesDepts = baseAreaService.findListByVo(baseAreaVO);
			BaseArea baseArea = new BaseArea();
			baseArea.setId(null);
			baseArea.setName("全部");
			allSalesDepts.add(0, baseArea);
			return allSalesDepts;
		}
		
		boolean seLoanUser = false;
		List<BaseArea> curSalesDept = new ArrayList<BaseArea>();
		for(Product prod:enableProdList){
			if(prod.getId().compareTo(EnumConstants.ProductList.CAR_LOAN.getValue())==0
					||prod.getId().compareTo(EnumConstants.ProductList.CAR_NEW_LOAN.getValue())==0){					
			}else{
				seLoanUser = true;
				break;
			}
			
		}
		if(seLoanUser){
			 curSalesDept = baseAreaService.getSeloanDept();
		}else{			
			curSalesDept = sysUserService.getCurSalesDept();
		}
		if (curSalesDept != null) {
		
			salesDepts.addAll(curSalesDept);
		}
		return salesDepts;
	}
	
	
	
	
	@RequestMapping("/getlcbzaFraud")
	@ResponseBody
	public JSONObject getlcbzaFraud(String id,String personName,String mobilePhoneLoan,String personIdnum) {
		
		return new JSONObject();
	}
	
	
	@RequestMapping("/getIdNoTwo")
	@ResponseBody
	public String getIdNoTwo(String id) {
		Integer countSize=vehicleService.getIdNoTwo(id);
		if(countSize>0){
			return "success";
		}else{
			return "error";
		}
		
	}
	
	@RequestMapping("/lcbJudgePushStandard")
	@ResponseBody
	public JSONObject lcbJudgePushStandard(Long loanId,String LoanCancelSelect) {
		JSONObject json=new JSONObject();
		json.put("repCode", "000000");
		return json;
	}


	@RequestMapping("/loanQuota")
	public String loanQuota(){
		return "apply/loanQuota";
	}

	/**
	 * 根据身份证号查询借款余额
	 * @param name 姓名
	 * @param idNo 身份证
	 * @return
	 */
	@RequestMapping("/loanQuotaQuery")
	@ResponseBody
	public JSONObject loanQuotaQuery(String name, String idNo){
		return loanService.findResidualPactMoney(name, idNo);
	}

}
