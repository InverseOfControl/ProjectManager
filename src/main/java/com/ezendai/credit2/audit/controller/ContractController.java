package com.ezendai.credit2.audit.controller;

import java.io.IOException;
import java.math.BigDecimal;
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

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ezendai.credit2.apply.controller.ApplyController.LoanStatusDetail;
import com.ezendai.credit2.apply.model.AccountAuthLog;
import com.ezendai.credit2.apply.model.Bank;
import com.ezendai.credit2.apply.model.BankAccount;
import com.ezendai.credit2.apply.model.ChannelPlanCheck;
import com.ezendai.credit2.apply.model.Extension;
import com.ezendai.credit2.apply.model.Guarantee;
import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.model.LoanExtension;
import com.ezendai.credit2.apply.model.Person;
import com.ezendai.credit2.apply.model.Product;
import com.ezendai.credit2.apply.service.AccountAuthLogService;
import com.ezendai.credit2.apply.service.BankAccountService;
import com.ezendai.credit2.apply.service.BankService;
import com.ezendai.credit2.apply.service.ChannelPlanCheckService;
import com.ezendai.credit2.apply.service.ExtensionService;
import com.ezendai.credit2.apply.service.GuaranteeService;
import com.ezendai.credit2.apply.service.LoanExtensionService;
import com.ezendai.credit2.apply.service.LoanService;
import com.ezendai.credit2.apply.service.PersonService;
import com.ezendai.credit2.apply.service.ProductService;
import com.ezendai.credit2.apply.vo.BankAccountVO;
import com.ezendai.credit2.apply.vo.GuaranteeVO;
import com.ezendai.credit2.apply.vo.LoanExtensionVO;
import com.ezendai.credit2.apply.vo.LoanVO;
import com.ezendai.credit2.audit.model.PersonBank;
import com.ezendai.credit2.audit.model.RepaymentPlan;
import com.ezendai.credit2.audit.service.AuditService;
import com.ezendai.credit2.audit.service.CityWideContractAuditService;
import com.ezendai.credit2.audit.service.ContractService;
import com.ezendai.credit2.audit.service.EduCreditAuditService;
import com.ezendai.credit2.audit.service.NetBusinessAuditService;
import com.ezendai.credit2.audit.service.PersonBankService;
import com.ezendai.credit2.audit.service.RepaymentPlanService;
import com.ezendai.credit2.audit.vo.GenerateContractVO;
import com.ezendai.credit2.audit.vo.PersonBankVO;
import com.ezendai.credit2.finance.model.Ledger;
import com.ezendai.credit2.finance.service.LedgerService;
import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.framework.constant.Constants;
import com.ezendai.credit2.framework.util.CollectionUtil;
import com.ezendai.credit2.framework.util.DateUtil;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.framework.util.StringUtil;
import com.ezendai.credit2.master.constant.BizConstants;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.master.model.SysEnumerate;
import com.ezendai.credit2.master.service.SysEnumerateService;
import com.ezendai.credit2.sign.lcb.model.LcbModel;
import com.ezendai.credit2.sign.lcb.service.EleSignatureService;
import com.ezendai.credit2.system.controller.BaseController;
import com.ezendai.credit2.system.enumerate.SysParameterEnum;
import com.ezendai.credit2.system.model.SysParameter;
import com.ezendai.credit2.system.service.OrganBankService;
import com.ezendai.credit2.system.service.SysParameterService;
import com.ezendai.credit2.system.service.SysUserService;
import com.ezendai.credit2.system.vo.OrganBankVO;
import com.zendaimoney.thirdpp.common.enums.BizSys;
import com.zendaimoney.thirdpp.common.vo.Response;
import com.zendaimoney.thirdpp.trade.pub.service.IAuthService;
import com.zendaimoney.thirdpp.trade.pub.vo.req.query.BankCardAuthReqVo;

@Controller
@RequestMapping("/audit/contract")
public class ContractController extends BaseController {

	@Autowired
	private AuditService auditService;

	@Autowired
	private ContractService contractService;

	@Autowired
	private LoanService loanService;

	@Autowired
	private GuaranteeService guaranteeService;

	@Autowired
	private PersonService personService;

	@Autowired
	private ProductService productService;

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private SysEnumerateService sysEnumerateService;
	
	
	@Autowired
	private SysParameterService sysParameterService;

	
	@Autowired
	private ExtensionService extensionService;
	
	@Autowired
	private LoanExtensionService loanExtensionService;
	
	
	@Autowired
	private PersonBankService  personBankService;
	

	@Autowired
	private BankAccountService  bankAccountService;
	@Autowired
	private RepaymentPlanService  repaymentPlanService;
	@Autowired
	private LedgerService  ledgerService;
	@Autowired
	private CityWideContractAuditService  CityWideContractAuditService;
	@Autowired
	private NetBusinessAuditService netBusinessAuditService;
	
	@Autowired
	private EduCreditAuditService eduCreditAuditService;
	
	@Autowired
	private OrganBankService organBankService;
	
	@Autowired
	private ChannelPlanCheckService channelPlanCheckService;
	
	@Autowired
	private BankService bankService;
	
	@Autowired
	private IAuthService iAuthService;
	
	@Autowired
	private AccountAuthLogService accountAuthLogService;
	
	@Autowired
	private EleSignatureService eleSignatureService;   
	
	private static final Logger logger = Logger.getLogger(ContractController.class);
	
	@RequestMapping("/list")
	public String init(HttpServletRequest request) {
		/*设置数据字典*/
		setDataDictionaryArr(new String[] { EnumConstants.PRODUCT_TYPE, EnumConstants.LOAN_STATUS , EnumConstants.HAVE_HOUSE_STATUS});
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		Integer userType = ApplicationContext.getUser().getUserType();
		request.setAttribute("userType", userType);
		return "audit/contract/contractList";
	}

	/**
	 * 
	 * <pre>
	 * 
	 * </pre>
	 *
	 * @return
	 */
	@RequestMapping("/getLoanStatusList")
	@ResponseBody
	public List<LoanStatusDetail> getLoanStatusList() {
		List<LoanStatusDetail> list = new ArrayList<LoanStatusDetail>();
		list.add(new LoanStatusDetail("全部", 0));
		list.add(new LoanStatusDetail("合同签订", EnumConstants.LoanStatus.合同签订.getValue()));
		list.add(new LoanStatusDetail("展期合同签订", EnumConstants.LoanStatus.展期合同签订.getValue()));
		list.add(new LoanStatusDetail("合同确认退回", EnumConstants.LoanStatus.合同确认退回.getValue()));
		list.add(new LoanStatusDetail("展期合同确认退回", EnumConstants.LoanStatus.展期合同确认退回.getValue()));
		list.add(new LoanStatusDetail("财务审核退回", EnumConstants.LoanStatus.财务审核退回.getValue()));
		list.add(new LoanStatusDetail("财务放款退回", EnumConstants.LoanStatus.财务放款退回.getValue()));
		return list;
	}

	/**
	 * 
	 * <pre>
	 * 
	 * </pre>
	 *
	 * @return
	 */
	@RequestMapping("/getContractSrcList")
	@ResponseBody
	public List<SysEnumerate> getContractSrcList() {
		List<SysEnumerate> list = sysEnumerateService.findSysEnumerateListByType("CONTRACT_SRC");
		
		List<SysEnumerate> filterList = new ArrayList<>();
		for (SysEnumerate sysEnumerate : list) {
			if(sysEnumerate.getEnumValue().equals("证大爱特")){
				filterList.add(sysEnumerate);
			}
		}
		
		return filterList;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/list.json")
	@ResponseBody
	public Map list(@RequestParam(value = Constants.PAGE_NUMBER_NAME, defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page,
					@RequestParam(value = Constants.PAGE_ROWS_NAME, defaultValue = Constants.DEFAULT_PAGE_ROWS) int pageSize, @ModelAttribute("vo") LoanVO vo, HttpServletRequest request) {
		Long userId = ApplicationContext.getUser().getId();
		Integer userType = ApplicationContext.getUser().getUserType();
		List<Integer> statusList = new ArrayList<Integer>();
		if (vo.getStatus() == null) {
			statusList.add(EnumConstants.LoanStatus.合同签订.getValue());
			statusList.add(EnumConstants.LoanStatus.展期合同签订.getValue());
			vo.setStatusList(statusList);
		} else if (vo.getStatus() == 0) {
			statusList.add(EnumConstants.LoanStatus.合同签订.getValue());
			statusList.add(EnumConstants.LoanStatus.展期合同签订.getValue());
			statusList.add(EnumConstants.LoanStatus.合同确认.getValue());
			statusList.add(EnumConstants.LoanStatus.合同确认退回.getValue());
			statusList.add(EnumConstants.LoanStatus.展期合同确认.getValue());
			statusList.add(EnumConstants.LoanStatus.展期合同确认退回.getValue());
			statusList.add(EnumConstants.LoanStatus.财务审核.getValue());
			statusList.add(EnumConstants.LoanStatus.财务审核退回.getValue());
			statusList.add(EnumConstants.LoanStatus.财务放款.getValue());
			statusList.add(EnumConstants.LoanStatus.财务放款退回.getValue());
			statusList.add(EnumConstants.LoanStatus.正常.getValue());
			statusList.add(EnumConstants.LoanStatus.逾期.getValue());
			statusList.add(EnumConstants.LoanStatus.预结清.getValue());
			statusList.add(EnumConstants.LoanStatus.结清.getValue());
			statusList.add(EnumConstants.LoanStatus.坏账.getValue());
			statusList.add(EnumConstants.LoanStatus.取消.getValue());
			vo.setStatusList(statusList);
			vo.setStatus(null);
		}
		
		//判断是否展期
		if(vo.getExtensionTime()!=null&& vo.getExtensionTime().compareTo(0)>0)
		{
			vo.setIsExtension(1);
		}
				
		if (vo.getAuditDateStart() == null) {
			vo.setAuditDateStart(DateUtil.getDateBefore(15));
		}
		// 确定查询的产品类型
		List<Product> products = productService.findProductTypeByUserId(userId);
		List<Integer> canBrowseproductIds = new ArrayList<Integer>();
		for (Product product : products) {
			canBrowseproductIds.add(product.getProductType());
		}
		if (canBrowseproductIds.size() < 1) {
			return null;
		}

		List<Integer> qualifiedProductIds = new ArrayList<Integer>();
		if (vo.getSelectedProductId() != null) {
			if (canBrowseproductIds.contains(vo.getSelectedProductId().intValue())) {
				qualifiedProductIds.add(vo.getSelectedProductId().intValue());
			} else {
				return null;
			}
		} else {
			qualifiedProductIds.addAll(canBrowseproductIds);
		}

		vo.setProductTypeList(qualifiedProductIds);
		// 判断网点类型是否合法
		if ("admin".equals(ApplicationContext.getUser().getLoginName())) {
			vo.setProductIdList(null);
		} 
		//门店经理,门店副理,客服查询所拥有网点的数据
		if (EnumConstants.UserType.STORE_MANAGER.getValue().equals(userType)
				|| EnumConstants.UserType.STORE_ASSISTANT_MANAGER.getValue().equals(userType)
				|| EnumConstants.UserType.CUSTOMER_SERVICE.getValue().equals(userType)) {
			List<Long> canBrowseSalesDeptList = sysUserService.getSalesDeptIdByUserId(userId);
			if (canBrowseSalesDeptList == null) {
				return null;
			} else if (vo.getSalesDeptId() == null) {
				vo.setSalesDeptIdList(canBrowseSalesDeptList);
			} 
		} 

		Pager p = new Pager();
		p.setPage(page);
		p.setRows(pageSize);
		p.setSidx("AUDIT_DATE");
		p.setSort("ASC");
		vo.setPager(p);
		Pager pager = loanService.findWithPGUnionExtension(vo);
		List<Loan> loanList = pager.getResultList();
		List<LoanList> loanListList = new ArrayList<LoanList>();
		for (Loan loan : loanList) {
			GuaranteeVO guaranteeVO = new GuaranteeVO();
			guaranteeVO.setPersonId(loan.getPersonId());
			guaranteeVO.setLoan(loan);
			guaranteeVO.setFlag(EnumConstants.YesOrNo.YES.getValue());
			List<Guarantee> guaranteeList = guaranteeService.findListByVo(guaranteeVO);
			// 得到可以对贷款的所有操作——日志，附件，合同签约/提交
			StringBuilder operations = new StringBuilder("日志");
			if (loanService.canContractSign(loan, userId)||loanService.canExtensionContractSign(loan))
				operations.append("|" + "合同签约");
			if (loanService.canContractSubmit(loan, userId) ||loanService.canExtensionContractSubmit(loan))
				operations.append("|" + "提交");
			operations.append("|" + "附件");

			loanListList.add(new LoanList(loan.getId(),loan.getLoanId(), loan.getPerson().getName(), loan.getPerson().getIdnum(), loan.getService().getName(), loan.getCrm().getName(), loan.getAuditMoney(), loan
				.getAuditTime(), loan.getContractNo(), guaranteeList,loan.getProductId(),loan.getProductType().longValue(), loan.getStatus(), loan.getSubmitDate(), loan.getAssessor().getName(), loan.getRequestDate(),
				loan.getAuditDate(), operations.toString(), loan.getHasHouse(),loan.getExtensionTime(),loan.getProduct()));
		}
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("total", pager.getTotalCount());
		result.put("rows", loanListList);
		return result;
	}
	
	@RequestMapping("/initGenerateExtensionContract/{loanId}")
	public String initGenerateExtensionContract(@PathVariable String loanId, ModelMap modelMap) {
		if (StringUtils.isBlank(loanId)) {
			return "audit/contract/generateExtensionContract";
		}
		//是否验证银行卡，到参数表总查询，0否1是
		String isverificationBank = sysParameterService.getByCodeNoCache("IS_VERIFICATION_BANK_CARD").getParameterValue();
		
		GenerateContractVO vo = new GenerateContractVO();
		Extension extension = extensionService.get(Long.valueOf(loanId));
		if (extension == null) {
			throw new RuntimeException("展期借款id:" + loanId + "不存在。");
		}
		LoanExtensionVO loanExtensionVO=new LoanExtensionVO();
		loanExtensionVO.setExtensionId(Long.valueOf(loanId));
		List<LoanExtension> loanExtensionList = loanExtensionService.findListByVo(loanExtensionVO);
		if(loanExtensionList!=null &&loanExtensionList.size()>0)
		{
			Long origLoanId = loanExtensionList.get(0).getLoanId();
			Loan loan = loanService.get(origLoanId);
			Long personId = loan.getPersonId();
			Person person = personService.get(personId);
			vo.setAccountName(person.getName());
			
			PersonBankVO personBankVO=new PersonBankVO();
			personBankVO.setLoanId(origLoanId);
			personBankVO.setPersonId(personId);
			List<PersonBank> personBankList = personBankService.findPersonBankList(personBankVO);
			if(personBankList!=null && personBankList.size()==1)
			{
				Long bankAccountId = personBankList.get(0).getBankAccountId();
				BankAccount bankAccount = bankAccountService.get(bankAccountId);
				if(bankAccount!=null)
				{
					//开户网点
					String branchName = bankAccount.getBranchName();
					//银行卡号
					String account = bankAccount.getAccount();
					//开户行
					Long bankId = bankAccount.getBank().getId();
					vo.setBankBranch(branchName);
					vo.setBankAccount(account);
					vo.setBank(String.valueOf(bankId));
				}
			}
		}
		String contractNo = extension.getContractNo();
		if (StringUtils.isNotEmpty(contractNo)) {
			vo.setSign(true);
		} else {
			vo.setSign(false);
		}
		vo.setLoanId(loanId);
		modelMap.put("vo", vo);
		modelMap.put("isverificationBank", isverificationBank);
		return "audit/contract/generateExtensionContract";
	}
	
	@RequestMapping("/validationGenerateExtension")
	@ResponseBody
	public Map<String, String> validationGenerateExtension(Long extensionId) {
		Map<String, String> result = new HashMap<String, String>();
		Extension ext = extensionService.get(extensionId);
		Long preLoanId = loanExtensionService.getPreExtensionId(extensionId, ext.getExtensionTime());
		List<RepaymentPlan> repaymentPlanList  = repaymentPlanService.queryRepaymentPlans(preLoanId);
		if(CollectionUtil.isNotEmpty(repaymentPlanList)){
			RepaymentPlan lastRepaymentPlan = repaymentPlanList.get(repaymentPlanList.size()-1);
			Ledger ledger = ledgerService.getLedgerByAccountId(preLoanId);
			Date today = DateUtil.getToday();
			if (today.before(lastRepaymentPlan.getRepayDay())){
				if(ledger.getCash().compareTo(lastRepaymentPlan.getRepayAmount()) >= 0){
					result.put("success", "0");
				}else{
					result.put("success", "1");
					result.put("msg", "上一个借款最后一期还款没有扣款成功,无法生成展期合同!");
				}
			} else if (today.after(lastRepaymentPlan.getRepayDay())
						|| today.compareTo(lastRepaymentPlan.getRepayDay()) == 0) {
				if (EnumConstants.RepaymentPlanState.SETTLE.getValue().equals(
					lastRepaymentPlan.getStatus())
					|| ledger.getCash().compareTo(lastRepaymentPlan.getRepayAmount()) >= 0) {
					result.put("success", "0");
				} else {
					result.put("success", "1");
					result.put("msg", "上一个借款最后一期还款没有扣款成功,无法生成展期合同!");
				}
			}
		}
		
		return result;
	}
	@RequestMapping("/initGenerateContract/{loanId}")
	public String initGenerateContract(@PathVariable String loanId, ModelMap modelMap) {
		
		if (StringUtils.isBlank(loanId)) {
			return "audit/contract/generateContract";
		}
		//是否验证银行卡，到参数表总查询，0否1是
		String isverificationBank = sysParameterService.getByCodeNoCache("IS_VERIFICATION_BANK_CARD").getParameterValue();
		// TODO 根据客户和担保人查询银行信息
		GenerateContractVO vo = new GenerateContractVO();
		Loan loan = loanService.get(Long.valueOf(loanId));
		if (loan == null) {
			throw new RuntimeException("贷款id:" + loanId + "不存在。");
		}
		Long personId = loan.getPersonId();
		Person person = personService.get(personId);
		vo.setAccountName(person.getName());
		String contractNo = loan.getContractNo();
		if (StringUtils.isNotEmpty(contractNo)) {
			vo.setSign(true);
		} else {
			vo.setSign(false);
		}
		long productId = loan.getProductId();
		if(productId != 0){
			if(EnumConstants.ProductList.STUDENT_LOAN.getValue().equals(productId)){
				vo.setEduAudit(true);
				long planId = loan.getSchemeID();
				if(planId != 0){
					ChannelPlanCheck channelPlanCheck=channelPlanCheckService.getReplyById(planId);
					if(channelPlanCheck != null){
						if(EnumConstants.ReturnTypeStatus.前低后高.getValue().compareTo(channelPlanCheck.getReturnType().longValue()) == 0 && channelPlanCheck.getOrgRepayTerm().intValue() != 0){
							vo.setOrganPay(true);
						}
					}
				}
			}else{
				vo.setEduAudit(false);
				vo.setOrganPay(false);
			}
		}
		
		vo.setLoanId(loanId);
		vo.setProductType(loan.getProductType());
		vo.setProductId(loan.getProductId());
		GuaranteeVO guaranteeVO = new GuaranteeVO();
		Loan queryLoan = new Loan();
		queryLoan.setId(Long.valueOf(loanId));
		guaranteeVO.setLoan(queryLoan);
		guaranteeVO.setFlag(EnumConstants.YesOrNo.YES.getValue());
		List<Guarantee> guaranteeList = guaranteeService.findListByVo(guaranteeVO);
		if (CollectionUtil.isNotEmpty(guaranteeList)) {
			for (Guarantee g : guaranteeList) {
				// 自然人
				if (g.getGuaranteeType().compareTo(
						EnumConstants.GuaranteeType.NATURAL_GUARANTEE.getValue()) == 0) {
					if (StringUtil.isEmpty(vo.getNaturalGuaranteeId1())) {
						vo.setHasNaturalGuarantee1(true);
						vo.setNaturalGuaranteeName1(g.getName());
						vo.setNaturalGuaranteeId1(g.getId().toString());
					} else if(StringUtil.isEmpty(vo.getNaturalGuaranteeId2())) {
						vo.setHasNaturalGuarantee2(true);
						vo.setNaturalGuaranteeName2(g.getName());
						vo.setNaturalGuaranteeId2(g.getId().toString());
					}else if(StringUtil.isEmpty(vo.getNaturalGuaranteeId3())){
						vo.setHasNaturalGuarantee3(true);
						vo.setNaturalGuaranteeName3(g.getName());
						vo.setNaturalGuaranteeId3(g.getId().toString());
					}else if(StringUtil.isEmpty(vo.getNaturalGuaranteeId4())){
						vo.setHasNaturalGuarantee4(true);
						vo.setNaturalGuaranteeName4(g.getName());
						vo.setNaturalGuaranteeId4(g.getId().toString());
					}
				} else {
					//法人
					if (StringUtil.isEmpty(vo.getLegalGuaranteeId1())) {
						vo.setHasLegalGuarantee1(true);
						vo.setLegalGuaranteeName1(g.getName());
						vo.setLegalGuaranteeId1(g.getId().toString());
					} else {
						vo.setHasLegalGuarantee2(true);
						vo.setLegalGuaranteeName2(g.getName());
						vo.setLegalGuaranteeId2(g.getId().toString());
					}
				}
			}
		}
		Date defaultDate = DateUtil.getToday();
		modelMap.put("vo", vo);
		modelMap.put("defaultDate", defaultDate);
		modelMap.put("isverificationBank", isverificationBank);
		return "audit/contract/generateContract";
	}
	
	/**
	 * description:获取捞财宝注册、登录、实名、绑卡状态
	 * autor:ym10159
	 * date:2018年1月12日 下午5:52:28
	 */
	@RequestMapping(value="/getLcbStatus")
	@ResponseBody
	public LcbModel getLcbStatus(GenerateContractVO vo) {
		return contractService.getPersonInfoByLoanId(ObjectUtils.toString(vo.getLoanId()));
	}
	
	@RequestMapping(value="/getLcbStatusById")
	@ResponseBody
	public LcbModel getLcbStatusById(String loanId) {
		return contractService.getPersonInfoByLoanId(loanId);
	}
	
	/**
	 * description:跳转到捞财宝注册页
	 * autor:ym10159
	 * date:2018年1月8日 上午9:26:11
	 */
	@RequestMapping(value="/toLcbRegisterPage",method=RequestMethod.GET)
	public String toLcbRegisterPage(HttpServletRequest request) {
		return "audit/contract/lcbRegister"; 
	}
	
	/**
	 * description:跳转到捞财宝登录页
	 * autor:ym10159
	 * date:2018年2月5日 上午10:15:19
	 */
	@RequestMapping(value="/toLcbLoginPage",method=RequestMethod.GET)
	public String toLcbLoginPage(HttpServletRequest request) {
		return "audit/contract/lcbLogin"; 
	}
	
	/**
	 * description:生成合同前置校验
	 * autor:ym10159
	 * date:2018年1月22日 下午2:30:06
	 */
	@RequestMapping("/generateContractPre")
	@ResponseBody
	public Map<String,Object> generateContractPre(@ModelAttribute("vo") GenerateContractVO vo) {
		Map<String,Object> resultMap = new HashMap<>();
		resultMap.put("repCode", "000000");
		return resultMap;
	}
	
	/**
	 * description:签约合同前置校验
	 * autor:ym10159
	 * date:2018年1月22日 下午2:30:06
	 */
	@RequestMapping("/signContractPre")
	@ResponseBody
	public Map<String,Object> signContractPre(@ModelAttribute("vo") GenerateContractVO vo) {
		Map<String,Object> resultMap = new HashMap<>();
		resultMap.put("repCode", "000000");
		return resultMap;
	}
	
	/**
	 * description:打开电子签章页面
	 * autor:ym10159
	 * date:2018年1月25日 上午11:59:21
	 */
	@RequestMapping(value="/openEleSignPage",method=RequestMethod.GET)
	public void openEleSignPage(HttpServletRequest request, HttpServletResponse response) {
		String loanId = request.getParameter("loanId");
		String url = eleSignatureService.getSignNamePageUrl(loanId);
		try {
			response.sendRedirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * description:打开捞财宝电子签章页面
	 * autor:ym10159
	 * date:2018年1月25日 上午11:59:21
	 */
	@RequestMapping(value="/openLcbEleSignPage",method=RequestMethod.GET)
	public void openLcbEleSignPage(HttpServletRequest request, HttpServletResponse response) {
		String loanId = request.getParameter("loanId");
		String url = eleSignatureService.getLcbSignNamePageUrl(loanId);
		if(StringUtils.isNotBlank(url)){
			try {
				response.sendRedirect(url);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/generateContract")
	@ResponseBody
	public Map generateContract(@ModelAttribute("vo") GenerateContractVO vo) {
		Map result = new HashMap();
		try {
			SysParameter accountCredit=sysParameterService.getByCode(SysParameterEnum.TPP_ACCOUNT_CREDIT.name());
			//若为银联进行银行卡验证
			Bank bank = bankService.get(Long.valueOf(vo.getBank()));
			if(bank.getTppType().toString().equals(BizConstants.ChinaUnion) && accountCredit.getParameterValue().equals("0")){//银联的交易
				Date sendTime=new Date();
				BankCardAuthReqVo bankCardAuthReqVo=generateRequestVo(vo);
				Response response =  iAuthService.bankCardAuth(bankCardAuthReqVo);
				//记录银行卡验证信息
				AccountAuthLog accountAuthLog = new AccountAuthLog();
				accountAuthLog.setBankId(Long.valueOf(vo.getBank()));
				accountAuthLog.setCardNo(vo.getBankAccount());
				accountAuthLog.setOperatorId(ApplicationContext.getUser().getId());
				accountAuthLog.setSendMsg(JSONObject.toJSONString(bankCardAuthReqVo));
				accountAuthLog.setReturnCode(response.getCode());
				accountAuthLog.setReturnMsg(response.getMsg());
				accountAuthLog.setSendTime(sendTime);
				accountAuthLog.setName(bankCardAuthReqVo.getRealName());
				accountAuthLog.setLoanId(Long.valueOf(vo.getLoanId()));
				accountAuthLogService.insert(accountAuthLog);
				if(!response.getCode().equals("000000")){
					result.put("success", false);
					result.put("msg", response.getMsg());
					return result;
				}
				vo.setAccountAuthType(1);
			}else{
				vo.setAccountAuthType(0);
			}
			if (auditService.isCreate(Long.valueOf(vo.getLoanId()))) {
				if (EnumConstants.ProductList.CITY_WIDE_POS_LOAN.getValue().equals(vo.getProductId())
					|| EnumConstants.ProductList.CITY_WIDE_SE_LOAN.getValue().equals(vo.getProductId())) {
					CityWideContractAuditService.createdContract(vo);
				}else if(EnumConstants.ProductList.WS_SE_LOAN.getValue().equals(vo.getProductId())){
					netBusinessAuditService.createdContract(vo);
				}else if(EnumConstants.ProductList.STUDENT_LOAN.getValue().equals(vo.getProductId())){
					eduCreditAuditService.createdContract(vo);
				}
				else {
					// 新计算规则启用参数
					SysParameter parameter=	sysParameterService.getByCode(EnumConstants.CAR_FINE_NEW_CAL_EXECUTE_TIME);
				 
					 SimpleDateFormat sdftime = new SimpleDateFormat("yyyy-MM-dd");                
					 Date date=new Date();
					try {
						date = sdftime.parse(parameter.getParameterValue());
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} 
					Long loanId = Long.valueOf(vo.getLoanId());
					Loan loan = loanService.get(loanId);
					if (loan != null) {
//						Date1.after(Date2),当Date1大于Date2时
						if(loan.getCreatedTime().after(date)){
							auditService.createdContractNew(vo);
						} else {
							auditService.createdContract(vo);
						}
					}
				}
				result.put("success", true);
			} else {
				result.put("success", false);
				result.put("msg", "无法生成合同，流程状态不符合。");
			}
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", e.getMessage());
			logger.error("生成合同异常: ", e);
		}
		return result;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/generateExtensionContract")
	@ResponseBody
	public Map generateExtensionContract(@ModelAttribute("vo") GenerateContractVO vo) {
		Map result = new HashMap();
		try {
			SysParameter accountCredit=sysParameterService.getByCode(SysParameterEnum.TPP_ACCOUNT_CREDIT.name());
			//若为银联进行银行卡验证
			Bank bank = bankService.get(Long.valueOf(vo.getBank()));
			if(bank.getTppType().toString().equals(BizConstants.ChinaUnion) && accountCredit.getParameterValue().equals("0")){//银联的交易
				Date sendTime=new Date();
				BankCardAuthReqVo bankCardAuthReqVo=generateRequestVo(vo);
				Response response =  iAuthService.bankCardAuth(bankCardAuthReqVo);
				//记录银行卡验证信息
				AccountAuthLog accountAuthLog = new AccountAuthLog();
				accountAuthLog.setBankId(Long.valueOf(vo.getBank()));
				accountAuthLog.setCardNo(vo.getBankAccount());
				accountAuthLog.setOperatorId(ApplicationContext.getUser().getId());
				accountAuthLog.setSendMsg(JSONObject.toJSONString(bankCardAuthReqVo));
				accountAuthLog.setReturnCode(response.getCode());
				accountAuthLog.setReturnMsg(response.getMsg());
				accountAuthLog.setSendTime(sendTime);
				accountAuthLog.setName(bankCardAuthReqVo.getRealName());
				accountAuthLog.setLoanId(Long.valueOf(vo.getLoanId()));
				accountAuthLogService.insert(accountAuthLog);
				if(!response.getCode().equals("000000")){
					result.put("success", false);
					result.put("msg", response.getMsg());
					return result;
				}
				vo.setAccountAuthType(1);
			}else{
				vo.setAccountAuthType(0);
			}
			if (auditService.isCreateExtension(Long.valueOf(vo.getLoanId()))) {
				// 新计算规则启用参数
				SysParameter parameter=	sysParameterService.getByCode(EnumConstants.CAR_FINE_NEW_CAL_EXECUTE_TIME);
			 
				 SimpleDateFormat sdftime = new SimpleDateFormat("yyyy-MM-dd");                
				 Date date=new Date();
				try {
					date = sdftime.parse(parameter.getParameterValue());
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
				Long extensionId = Long.valueOf(vo.getLoanId());
				Long oldLoanId = loanExtensionService.getLoanIdByExtensionId(extensionId);
				Loan loan=loanService.get(oldLoanId);
				if (loan != null) {
					if(loan.getCreatedTime().after(date)&&loan.getLoanType().equals(EnumConstants.ProductCarType.CIRCULATE.getValue())){
						auditService.createdExtensionContractNew(vo);
					} else {
						auditService.createdExtensionContract(vo);
					}
					result.put("success", true);
				} else {
					result.put("success", false);
					result.put("msg", "改借款不存在!");
				}
			} else {
				result.put("success", false);
				result.put("msg", "无法生成合同，流程状态不符合。");
			}
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", e.getMessage());
			logger.error("生成合同异常", e);
		}
		return result;
	}
	/***
	 * 	
	 * <pre>
	 * 合同签订
	 * </pre>
	 *
	 * @return
	 */
	@RequestMapping("/signContract")
	@ResponseBody
	public String signContract(Long loanId) {
//		Response response=sendAuthAccountByLoanId(loanId);
//		if(response!=null && response.getCode().equals("000000")){
		//Loan loan = this.loanService.get(loanId);
	//	if(loan.getStatus() < EnumConstants.LoanStatus.合同确认.getValue()) {
			contractService.signContract(loanId);
			return "success";
		//}
	//	return "false";
//		}else if(response!=null && !response.getCode().equals("000000")){
//		return response.getMsg();
	}
	
	/***
	 * 	
	 * <pre>
	 * 合同签订
	 * </pre>
	 *
	 * @return
	 */
	@RequestMapping("/signContractByStatus")
	@ResponseBody
	public Map<String,Object> signContractByStatus(Long loanId) {
//		Response response=sendAuthAccountByLoanId(loanId);
//		if(response!=null && response.getCode().equals("000000")){
		Loan loan = this.loanService.get(loanId);
		//String loanStatus = this.sysEnumerateService.findEnumValue(EnumConstants.LOAN_STATUS, loan.getStatus());
		Map<String,Object> map = new HashMap<String,Object>();
		if(loan.getStatus() < EnumConstants.LoanStatus.合同确认.getValue()) {
			contractService.signContract(loanId);
			map.put("isSuccess", true);
			return map;
		}
		map.put("isSuccess", false);
		map.put("loanStatus", this.sysEnumerateService.findEnumValue(EnumConstants.LOAN_STATUS, loan.getStatus()));
		return map;
//		}else if(response!=null && !response.getCode().equals("000000")){
//		return response.getMsg();
	}
	/***
	 * 	
	 * <pre>
	 * 展期合同签订
	 * </pre>
	 *
	 * @return
	 */
	@RequestMapping("/signExtensionContract")
	@ResponseBody
	public String signExtensionContract(Long loanId) {
//		Response response=sendAuthAccountByLoanId(loanId);
//		if(response!=null && response.getCode().equals("000000")){
		contractService.signExtensionContract(loanId);
		return "success";
//		}else if(response!=null && !response.getCode().equals("000000")){
//		return response.getMsg();
//	}else{
//		return "false";
//	}
	}
	
	/***
	 * 	
	 * <pre>
	 * 展期合同签订
	 * </pre>
	 *
	 * @return
	 */
	@RequestMapping("/signExtensionContractByStatus")
	@ResponseBody
	public Map<String,Object> signExtensionContractByStatus(Long loanId) {
		Extension extension = this.extensionService.get(loanId);
		Map<String,Object> map = new HashMap<String,Object>();
		if(extension.getStatus() < EnumConstants.LoanStatus.展期合同确认.getValue()) {
			contractService.signExtensionContract(loanId);
			map.put("isSuccess", true);
			return map;
		}
		map.put("isSuccess", false);
		map.put("extensionStatus", this.sysEnumerateService.findEnumValue(EnumConstants.LOAN_STATUS, extension.getStatus()));
		return map;
	}
	
	
	
	
	/**
	 * 
	 * <pre>
	 * 提交合同
	 * </pre>
	 *
	 * @return
	 */
	@RequestMapping("/submitContract")
	@ResponseBody
	public String submitContract(Long loanId) {
			contractService.submitContract(loanId);
			return "success";
	}
	/**
	 * 
	 * <pre>
	 * 提交展期合同
	 * </pre>
	 *
	 * @return
	 */
	@RequestMapping("/submitExtensionContract")
	@ResponseBody
	public String submitExtensionContract(Long loanId) {
			contractService.submitExtensionContract(loanId);
			return "success";
	}

	@RequestMapping("/imageUploadView/{loanId}")
	public String imageUploadView(@PathVariable Long loanId, Model model) {
		model.addAttribute("loanId", loanId);
		Loan loan = loanService.get(loanId);
		if (loan != null) {
			Long personId = loan.getPersonId();
			Long productId =  Long.valueOf(loan.getProductType());
			model.addAttribute("personId", personId);
			model.addAttribute("productId", productId);

			Person person = personService.get(personId);
			if (person != null) {
				model.addAttribute("personName", person.getName());
			}
		}
		model.addAttribute("optModule", EnumConstants.OptionModule.CONTRACT.getValue());
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
		return "audit/contract/imageUploadView";
	}
	
	
	@RequestMapping("/extensionImageUploadView/{loanId}")
	public String extensionImageUploadView(@PathVariable Long loanId, Model model) {
		model.addAttribute("loanId", loanId);
		Extension extension = extensionService.get(loanId);
		if (extension != null) {
			Long personId = extension.getPersonId();
			Long productId =  Long.valueOf(extension.getProductType());
			model.addAttribute("personId", personId);
			model.addAttribute("productId", productId);

			Person person = personService.get(personId);
			if (person != null) {
				model.addAttribute("personName", person.getName());
			}
		}
		model.addAttribute("optModule", EnumConstants.OptionModule.CONTRACT.getValue());
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
		return "audit/contract/imageUploadView";
	}
	
	@RequestMapping("/getBankAccountList/{loanId}")
	@ResponseBody
	public List<BankAccount> getBankAccountList(@PathVariable Long loanId){
		Loan loan = loanService.get(loanId);
		long organId = loan.getOrganID();
		List<BankAccount> bankAccountList=new ArrayList<BankAccount>();
		if(organId != 0){
			OrganBankVO organBankVO=new OrganBankVO();
			organBankVO.setOrganId(organId);
			bankAccountList = organBankService.findBankAccountListByVo(organBankVO);
		}
		return bankAccountList;
		
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
     * 构建BankCardAuthReqVo详细信息
     * 银行卡验证
     * </pre>
     *
     * @param vo
     * @return bankCardAuthReqVo
     */
	private BankCardAuthReqVo generateRequestVo(GenerateContractVO vo){
		Long loanId = Long.valueOf(vo.getLoanId());
		LoanVO loanVO=new LoanVO();
		loanVO.setId(loanId);
		List<Loan> loan = loanService.findListByVOUnionExtension(loanVO);
		Person person = personService.get(loan.get(0).getPersonId());
		BankCardAuthReqVo bankCardAuthReqVo=new BankCardAuthReqVo();
//		SysParameter infoCategoryCodeParameter=sysParameterService.getByCode(SysParameterEnum.INFO_CATEGORY_CODE.name());
		bankCardAuthReqVo.setBizSysNo(BizSys.ZENDAI_2018_SYS.getCode());//业务系统编码
//		bankCardAuthReqVo.setInfoCategoryCode(infoCategoryCodeParameter.getParameterValue());//信息类别编码
		bankCardAuthReqVo.setInfoCategoryCode("10001");
		bankCardAuthReqVo.setBankCardType("1");//银行卡类型
		bankCardAuthReqVo.setBankCardNo(vo.getBankAccount());//银行卡号
		bankCardAuthReqVo.setIdType("0");//证件类型
		bankCardAuthReqVo.setIdNo(person.getIdnum());//证件号
		bankCardAuthReqVo.setRealName(person.getName());//客户名
		return bankCardAuthReqVo;
	}
	
	
	
	
	
    /**
     * 
     * <pre>
     * 发送BankCardAuthReqVo信息
     * 银行卡验证
     * </pre>
     *
     * @param vo
     * @return bankCardAuthReqVo
     */
	@SuppressWarnings("unused")
	private Response sendAuthAccountByLoanId(Long loanId){
		SysParameter accountCredit=sysParameterService.getByCode(SysParameterEnum.TPP_ACCOUNT_CREDIT.name());
		LoanVO loanVO=new LoanVO();
		loanVO.setId(loanId);
		List<Loan> loan = loanService.findListByVOUnionExtension(loanVO);
		PersonBankVO personBankVO=new PersonBankVO();
		personBankVO.setLoanId(loanId);
		personBankVO.setPersonId(loan.get(0).getPersonId());
		List<PersonBank> personBank=personBankService.findListByVOExtension(personBankVO);
		BankCardAuthReqVo bankCardAuthReqVo=null;
		if(personBank!=null && personBank.size()!=0){
		bankCardAuthReqVo=new BankCardAuthReqVo();
//		SysParameter infoCategoryCodeParameter=sysParameterService.getByCode(SysParameterEnum.INFO_CATEGORY_CODE.name());
		bankCardAuthReqVo.setBizSysNo(BizSys.ZENDAI_2018_SYS.getCode());//业务系统编码
//		bankCardAuthReqVo.setInfoCategoryCode(infoCategoryCodeParameter.getParameterValue());//信息类别编码
		bankCardAuthReqVo.setInfoCategoryCode("10001");
		bankCardAuthReqVo.setBankCardType("1");//银行卡类型
		bankCardAuthReqVo.setBankCardNo(personBank.get(0).getAccount());//银行卡号
		bankCardAuthReqVo.setIdType("0");//证件类型
		bankCardAuthReqVo.setIdNo(personBank.get(0).getPersonIdnum());//证件号
		bankCardAuthReqVo.setRealName(personBank.get(0).getPersonName());//客户名
		}else{
			return null;
		}
		///若为银联进行银行卡验证
		Bank bank = bankService.get(Long.valueOf(personBank.get(0).getBankId()));
		if(bank.getTppType().toString().equals(BizConstants.ChinaUnion) && accountCredit.getParameterValue().equals("0")){//银联的交易
			Date sendTime=new Date();
			Response response =  iAuthService.bankCardAuth(bankCardAuthReqVo);
			//记录银行卡验证信息
			AccountAuthLog accountAuthLog = new AccountAuthLog();
			accountAuthLog.setBankId(bank.getId());
			accountAuthLog.setCardNo(personBank.get(0).getAccount());
			accountAuthLog.setOperatorId(ApplicationContext.getUser().getId());
			accountAuthLog.setSendMsg(JSONObject.toJSONString(bankCardAuthReqVo));
			accountAuthLog.setReturnCode(response.getCode());
			accountAuthLog.setReturnMsg(response.getMsg());
			accountAuthLog.setSendTime(sendTime);
			accountAuthLogService.insert(accountAuthLog);
			if(response.getCode().equals("000000")){
		        BankAccountVO personBankAccount = new BankAccountVO();
		        personBankAccount.setId(personBank.get(0).getBankAccountId());
		        personBankAccount.setAccountAuthType(1);
		        bankAccountService.update(personBankAccount);
		        return response;
			}else{
				return response;
			}
		}
		return null;
	}
}




class LoanList {
	public LoanList(Long id,Long loanId, String name, String idnum, String serviceName, String crmName, BigDecimal auditMoney, Integer time, String contractNo, List<Guarantee> guaranteeList, Long productId,Long productType,
					Integer status, Date submitDate, String assessorName, Date requestDate, Date auditDate, String operations, Integer hasHouse,Integer extensionTime,Product product) {
		this.id =  id;
		this.loanId = loanId;
		this.name = name;
		this.idnum = idnum;
		this.serviceName = serviceName;
		this.crmName = crmName;
		this.auditMoney = auditMoney;
		this.time = time;
		this.contractNo = contractNo;
		this.guaranteeList = guaranteeList;
		this.productId = productId;
		this.productType=productType;
		this.status = status;
		this.submitDate = submitDate;
		this.assessorName = assessorName;
		this.requestDate = requestDate;
		this.auditDate = auditDate;
		this.operations = operations;
		this.hasHouse = hasHouse;
		this.extensionTime = extensionTime;
		this.product=product;
	}

	Long id;
	Long loanId;
	String name;
	String idnum;
	String serviceName;
	String crmName;
	BigDecimal auditMoney;
	Integer time;
	String contractNo;
	List<Guarantee> guaranteeList;
	Long productId;
	Long productType;
	Integer status;
	Date submitDate;
	String assessorName;
	Date requestDate;
	Date auditDate;
	String operations;
	Product product;
	

	// TODO
	Integer hasHouse;
	/** 展期第几次次数*/
    Integer extensionTime;
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
	public Long getProductType() {
		return productType;
	}
	public void setProductType(Long productType) {
		this.productType = productType;
	}
	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdnum() {
		return idnum;
	}

	public Integer getHasHouse() {
		return hasHouse;
	}

	public void setHasHouse(Integer hasHouse) {
		this.hasHouse = hasHouse;
	}

	public void setIdnum(String idnum) {
		this.idnum = idnum;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getCrmName() {
		return crmName;
	}

	public void setCrmName(String crmName) {
		this.crmName = crmName;
	}

	public BigDecimal getAuditMoney() {
		return auditMoney;
	}

	public void setAuditMoney(BigDecimal auditMoney) {
		this.auditMoney = auditMoney;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public List<Guarantee> getGuaranteeList() {
		return guaranteeList;
	}

	public void setGuaranteeList(List<Guarantee> guaranteeList) {
		this.guaranteeList = guaranteeList;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getAssessorName() {
		return assessorName;
	}

	public void setAssessorName(String assessorName) {
		this.assessorName = assessorName;
	}

	public Date getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public String getOperations() {
		return operations;
	}

	public void setOperations(String operations) {
		this.operations = operations;
	}

	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
}