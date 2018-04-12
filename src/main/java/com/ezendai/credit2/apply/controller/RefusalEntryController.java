package com.ezendai.credit2.apply.controller;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezendai.credit2.apply.assembler.CompanyAssembler;
import com.ezendai.credit2.apply.assembler.LoanAssembler;
import com.ezendai.credit2.apply.assembler.PersonAssembler;
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
import com.ezendai.credit2.apply.model.Person;
import com.ezendai.credit2.apply.model.Product;
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
import com.ezendai.credit2.apply.vo.BusinessLogVO;
import com.ezendai.credit2.apply.vo.CompanyVO;
import com.ezendai.credit2.apply.vo.LoanDetailsVO;
import com.ezendai.credit2.apply.vo.LoanVO;
import com.ezendai.credit2.apply.vo.PersonVO;
import com.ezendai.credit2.apply.vo.VehicleVO;
import com.ezendai.credit2.audit.model.ApproveResult;
import com.ezendai.credit2.audit.service.ApproveResultService;
import com.ezendai.credit2.audit.vo.ApproveResultVO;
import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.framework.constant.Constants;
import com.ezendai.credit2.framework.exception.BusinessException;
import com.ezendai.credit2.framework.model.UserSession;
import com.ezendai.credit2.framework.util.CollectionUtil;
import com.ezendai.credit2.framework.util.DateUtil;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.framework.util.StringUtil;
import com.ezendai.credit2.master.constant.BizConstants;
import com.ezendai.credit2.master.enumerate.EnumConstants;
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
import com.ezendai.credit2.system.vo.SysGroupUserVO;
import com.ezendai.credit2.system.vo.SysUserVO;

/**
 * <pre>
 *拒单录入
 * </pre>
 *
 *  
 */
@Controller
@RequestMapping("/RefusalEntry")
public class RefusalEntryController extends BaseController {

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

	//表单提交时间类型自动转换
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		CustomDateEditor dateEditor = new CustomDateEditor(fmt, true);
		binder.registerCustomEditor(Date.class, dateEditor);
	}
	
	@RequestMapping("/init")
	public String viewList(HttpServletRequest request) {
		/* 设置数据字典 */
		setDataDictionaryArr(new String[] { EnumConstants.LOAN_STATUS, EnumConstants.PRODUCT_TYPE, EnumConstants.HAVE_HOUSE_STATUS,EnumConstants.REPAYMENT_PLAN_STATE });
		request.setAttribute("userType", ApplicationContext.getUser().getUserType());
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		SysGroupUserVO sysGroupUserVO =new SysGroupUserVO();
		sysGroupUserVO.setUserId(ApplicationContext.getUser().getId());
		SysGroupUser sysGroupUser = sysGroupUserService.findByVO(sysGroupUserVO);
		request.setAttribute("groupId", sysGroupUser.getGroupId());
		return "apply/RefusalEntry/applyList";
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
		loanVO.setStatus(EnumConstants.LoanStatus.拒绝.getValue());//查询拒单状态
		
		// 确定查询的产品类型
		List<Integer> qualifiedProductIds = new ArrayList<Integer>();
		List<Integer> canBrowseproductIds = new ArrayList<Integer>();
		if ("admin".equals(ApplicationContext.getUser().getLoginName())) {
			qualifiedProductIds.add(2);
		} else {
			// 车贷或小企贷
			List<Product> products = productService.findProductTypeByUserId(userId);
		
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
			//loanVO.setSalesTeamId(dept.getId());
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
			operations.append("|附件");
			operations.append("|编辑");
			/*if (loanService.canEdit(loan, userId)) {
				
			}*/
			populateLoanInfo(loan);
			loan.setOperations(operations.toString());
		}
 
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("total", loanPager.getTotalCount());
		result.put("rows", loanList);
		return result;
	}
	@RequestMapping("/toCarLoanDetail")
	@ResponseBody
	public LoanDetailsVO toCarLoanDetail(Long loanId, String flag) {
		LoanDetailsVO loanDetailsVo = new LoanDetailsVO();
		Loan loan = loanService.get(loanId);
		Person person = personService.get(loan.getPersonId());
		Product product = productService.get(loan.getProductId());
		SysUser crm = sysUserService.get(loan.getCrmId());
		SysUser director = sysUserService.getBizDirectorByCrm(crm);
		SysUser service = sysUserService.get(loan.getServiceId());
		BaseArea salesDept = salesDeptService.loadOneBaseAreaById(loan.getSalesDeptId());
		loanDetailsVo.setLoan(loan);
		loanDetailsVo.setPerson(person);
		loanDetailsVo.setProduct(product);
		loanDetailsVo.setCrm(crm);
		loanDetailsVo.setDirector(director);
		loanDetailsVo.setService(service);
		loanDetailsVo.setSalesDept(salesDept);
		// 插入系统日志
		 
		return loanDetailsVo;
	}
	// 打开车贷查看
		@RequestMapping("/carLoanDetail")
		public String carLoanDetail() {
			return "apply/RefusalEntry/carLoan/carLoanDetail";
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
			SysUser biz=new SysUser() ;
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
				service = sysUserService.get(loan.getServiceId());
				loanDetailsVo.setCrm(crm);
				salesDept = baseAreaService.get(loan.getSalesDeptId());
				if(loan.getBizDirectorId()!=null){
				 biz = sysUserService.get(loan.getBizDirectorId());}
			    loanDetailsVo.setDirector(biz);
			}
			loanDetailsVo.setService(service);
			loanDetailsVo.setSalesDept(salesDept);
		 
			// 车贷
			if (product.getProductType().compareTo(EnumConstants.ProductType.CAR_LOAN.getValue()) == 0) {
				Person person = personService.getPersonByIdNum(idnum, product.getProductType());
				if (person != null) {
			 
					List<Guarantee> guaranteeList = null;
					List<Vehicle> vehicleList = null;
					List<Contacter> contacterList = null;
					if (loan != null) {// 新增
						contacterList = contacterService.getContacterListByBorrowerId(person.getId(), loan.getId());
//						guaranteeList = guaranteeService.findListByPersonId(person.getId(), loan.getId());
						vehicleList = vehicleService.getVehicleListByPersonId(person.getId(), loan.getId());
						creditHistoryList = creditHistoryService.getCreditHistoryByPersonId(person.getId(), loan.getId());
					}else{
						Loan latestLoan = loanService.getLatestLoanByperson(person.getId());
						contacterList =  contacterService.getContacterListByBorrowerId(person.getId(), latestLoan.getId());
						vehicleList = vehicleService.getVehicleListByPersonId(person.getId(), latestLoan.getId());
						creditHistoryList = creditHistoryService.getCreditHistoryByPersonId(person.getId(), latestLoan.getId());
					}
					loanDetailsVo.setPerson(person);
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
			 
			vehicle.setRemark(null);// 备注设置为空
		 
			person = loanDetailVOconvertCarPerson(loanDetailsVo, person);
			PersonVO personVO = PersonAssembler.transferModel2VO(person);
			personVO.setIdentifier(EnumConstants.ProductType.CAR_LOAN_STRING.getStrValue());
			personService.update(personVO);
		 
 

			// 更新贷款信息
			loan.setId(loanDetailsVo.getLoanId());
			if (loanDetailsVo.getLoanTypes() != null) {
				loan.setLoanType(loanDetailsVo.getLoanTypes());
			}
			loan.setAssessorId(loanDetailsVo.getAssessorName());
			loan.setCrmId(loanDetailsVo.getManagerName());
			loan.setRequestDate(loanDetailsVo.getCarRequestDate());
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
		 
		SysUserVO sysUserVo = new SysUserVO();
		sysUserVo.setCode(loanDetailsVo.getPersonNo());
		BaseArea dept = null;
		/*List<SysUser> candicatesSysUser = sysUserService.findListByVO(sysUserVo);
		vehicle.setRemark(null);// 备注设置为空
		// 判断客户经理输入是否正确
		if (candicatesSysUser == null || candicatesSysUser.size() < 1) {
			result = "客户经理编号和姓名不正确";
			return result;
		}*/

		// 判断c下客户，经理，复核人员在不在一个营业网点
		/*if (loan.getServiceId() != null && loanDetailsVo.getManagerName() != null && loanDetailsVo.getAssessorName() != null) {
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
		}*/
		// 销量团队
		dept = sysUserService.getBaseAreaByUserId(loanDetailsVo.getManagerName(), BizConstants.CREDIT2_SALESTEAM);
		// baseAreaService.
		// 获取客户经理的id
		loan.setCrmId(loanDetailsVo.getManagerName());
		SysUser crm = sysUserService.get(loanDetailsVo.getManagerName());
		// 获取业务主任的ID
	/*	SysUser bizDirector = sysUserService.getBizDirectorByCrm(crm);
		if (bizDirector != null) {
			loan.setBizDirectorId(bizDirector.getId());
		}*/
		// 设置审核人员ID
	//	loan.setAssessorId(loanDetailsVo.getAssessorName());
		// 销售团队ID
		if(dept != null){
			loan.setSalesTeamId(dept.getId());
			
		}
		// 设置管理客服，默认为签约客服
		loan.setManagerId(loan.getServiceId());

		
		// 客户信息
		person = loanDetailVOconvertCarPerson(loanDetailsVo, person);
		// 判断该人是否存在
		PersonVO personVo = new PersonVO();
		personVo.setIdnum(person.getIdnum());
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
				//	oldPersonVO.setName(p.getPersonNo());
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
			person.setSex("0"); 
			person.setMarried(0l);
			person.setIdentifier("车贷");
			person.setEducationLevel("0"); ;
			person.setAddress("0"); 
			person.setMobilePhone("0"); 
			person.setName(	loanDetailsVo.getCarPersonName());
			person = personService.insert(person);
			
		}

		loan.setRequestDate(loanDetailsVo.getCarRequestDate());

		// 车辆信息
		// 查询该车是否有进行中的债权
		
	 
		// 保存贷款信息
		loan.setPersonId(person.getId());
		// 新增 贷款信息
		loan.setStatus(EnumConstants.LoanStatus.拒绝.getValue());
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
		loan.setRequestMoney(new BigDecimal(0));
		loan = loanService.insert(loan);
		 
	 
		//
		// 插入系统日志
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.REFUSAL_ENTRY.getValue());
		sysLog.setOptType(EnumConstants.OptionType.CREATE_LOAN.getValue());
		sysLog.setRemark("借款ID   " + loan.getId().toString());
		sysLogService.insert(sysLog);
		
		// 插入业务日志
					BusinessLog businessLog = new BusinessLog();
					businessLog.setFlowStatus(EnumConstants.BusinessLogStatus.REFUSAL_ENTRY_CREATE.getValue());
					businessLog.setLoanId(loan.getId());
					businessLog.setMessage("拒单录入创建");
					businessLogService.insert(businessLog);
		return "success";

	}
	/***
	 * 
	 * <pre>
	 * 判断是否拒单录入
	 * </pre>
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("/isRefusal")
	@ResponseBody
	public String isRefusal(HttpServletRequest request) {
		String loanId=request.getParameter("loanId");
		BusinessLogVO businessLogVO=new BusinessLogVO();
		businessLogVO.setLoanId(Long.parseLong(loanId));
		businessLogVO.setFlowStatus(EnumConstants.BusinessLogStatus.REFUSAL_ENTRY_CREATE.getValue());
		List<BusinessLog> list=  businessLogService.getLogByVO(businessLogVO);
		if(list!=null &&list.size()>0){
			return "1";
		}else{
			return "0";
		}
		
	}
		// 拒单录入编辑
		@RequestMapping("/toCarLoanUpdate")
		public String carLoanUpdate(HttpServletRequest request) {
				ApplicationContext.getUser().getUserType();
				request.setAttribute("carRequestDate",  DateUtil.defaultFormatDay(DateUtil.getToday()));
				return "apply/RefusalEntry/carLoan/carLoanModifi";
		}
		// 拒单录入
		@RequestMapping("/carLoanAdd")
		public String carLoanAdd(HttpServletRequest request) {
			ApplicationContext.getUser().getUserType();
			request.setAttribute("carRequestDate",  DateUtil.defaultFormatDay(DateUtil.getToday()));
			return "apply/RefusalEntry/carLoan/carLoanAdd";
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
	/***
	 * 
	 * <pre>
	 * 显示该营业网点下的业务主任
	 * </pre>
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("/getBizDirectorByUser")
	@ResponseBody
	public List<SysUser> getServicesInCurSalesDeptByProductId(Long productId,Long userId) {
			userId = ApplicationContext.getUser().getId();
			SysUser	user= sysUserService.get(userId);
			List<SysUser> services = sysUserService.getBizDirectorByUser(user);
	 
		 
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
	@RequestMapping("/getBizDirectorByUserEdit")
	@ResponseBody
	public List<SysUser> getBizDirectorByUserEdit(Long productId,Long userId,Long bizId) {
			userId = ApplicationContext.getUser().getId();
			SysUser	user= sysUserService.get(userId);
			List<SysUser> services = sysUserService.getBizDirectorByUser(user);
			
			 
			SysUser remove=null;
			SysUser  biz=	sysUserService.get(bizId);
			
			if(biz!=null){
				if(services != null){
					  for(SysUser u:services){
						  if(u.getId().equals(bizId)){
							  remove=u;
						  }
					  };
					  services.remove(remove);
					  services.add(biz);
					return services;
				}else{
					services=new ArrayList<SysUser>();
					 services.add(0, biz);
					 return services;
				}
			}
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
			person.setName(loanDetailsVo.getCarPersonName());
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
			List<Product> productRemove=new ArrayList<Product>();
			for(Product p:productType){
				if(!p.getProductType().equals(2)){
					productRemove.add(p);
				}
			}
			productType.removeAll(productRemove);
			 Product product =new Product();
			 product.setId(null);
			 product.setProductName("全部");
			productType.add(0, product);
			return productType;
		}
		
		//附件
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
			model.addAttribute("optModule", EnumConstants.OptionModule.REFUSAL_ENTRY.getValue());
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
			return "apply/RefusalEntry/carLoan/imageUploadView";
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
			
			if (userType.equals( EnumConstants.UserType.SYSTEM_ADMIN.getValue()) || EnumConstants.UserType.AUDIT.getValue() == userType) {
				BaseAreaVO baseAreaVO = new BaseAreaVO();
				baseAreaVO.setIdentifier(BizConstants.CREDIT2_SALESDEPARTMENT);
			
				List<BaseArea> allSalesDepts = baseAreaService.findListByVo(baseAreaVO);
				BaseArea baseArea = new BaseArea();
				
				List<BaseArea>  delSalesDept = baseAreaService.getSeloanDept();
				List<BaseArea>  removeList = new ArrayList<BaseArea>();
				for(BaseArea baAll:allSalesDepts){
					for(BaseArea del:delSalesDept){
						if(baAll.getId()!=null&&del.getId()!=null){
						if(baAll.getId().equals(del.getId())){removeList.add(baAll);};
						}
					}
				}
				allSalesDepts.removeAll(removeList);
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
			UserSession user = ApplicationContext.getUser();
			if(seLoanUser){
				 curSalesDept = baseAreaService.getSeloanDept();
			}else{			
				curSalesDept = sysUserService.getCurSalesDept();
			}
			if (curSalesDept != null) {
			
				salesDepts.addAll(curSalesDept);
			}
			List<BaseArea> deleteDept=new  ArrayList<BaseArea>();
			curSalesDept = baseAreaService.getSeloanDept();
			for(BaseArea del:salesDepts){
				if(del.getId()!=null){
					if(del.getId().equals(1111l)){
						deleteDept.add(del);
					}
					if(!(user.getLoginName().substring(0,4).equals("test"))){
						if(del.getId().equals(2222l)){
							deleteDept.add(del);
						}
					}
				}
			}
			for(BaseArea baAll:salesDepts){
				for(BaseArea del:curSalesDept){
					if(baAll.getId()!=null&&del.getId()!=null){
					if(baAll.getId().equals(del.getId())){deleteDept.add(baAll);};
					}
				}
			}
			salesDepts.removeAll(deleteDept);
			return salesDepts;
		}

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
}
