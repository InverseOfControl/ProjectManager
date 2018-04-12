package com.ezendai.credit2.audit.controller;

import java.math.BigDecimal;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ezendai.credit2.apply.vo.ProductVO;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezendai.credit2.apply.controller.ApplyController.LoanStatusDetail;
import com.ezendai.credit2.apply.model.Extension;
import com.ezendai.credit2.apply.model.Guarantee;
import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.model.Person;
import com.ezendai.credit2.apply.model.Product;
import com.ezendai.credit2.apply.service.ExtensionService;
import com.ezendai.credit2.apply.service.GuaranteeService;
import com.ezendai.credit2.apply.service.LoanService;
import com.ezendai.credit2.apply.service.PersonService;
import com.ezendai.credit2.apply.service.ProductService;
import com.ezendai.credit2.apply.vo.GuaranteeVO;
import com.ezendai.credit2.apply.vo.LoanVO;
import com.ezendai.credit2.audit.model.ApproveResult;
import com.ezendai.credit2.audit.service.ApproveResultService;
import com.ezendai.credit2.audit.service.AuditService;
import com.ezendai.credit2.audit.vo.ApproveResultVO;
import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.framework.util.StringUtil;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.master.enumerate.EnumConstants.LoanStatus;
import com.ezendai.credit2.master.model.BaseArea;
import com.ezendai.credit2.master.service.BaseAreaService;
import com.ezendai.credit2.sign.lcb.service.ZonganRiskService;
import com.ezendai.credit2.system.controller.BaseController;
import com.google.gson.Gson;

/**
 * 信审管理
 * <pre>
 * 
 * </pre>
 *
 * @author HQ-AT6
 * @version $Id: AuditController.java, v 0.1 2014年6月25日 下午4:03:30 HQ-AT6 Exp $
 */
@Controller
@RequestMapping("/audit")
public class AuditController extends BaseController {
	private static final Logger logger = Logger.getLogger(AuditController.class);
	private Gson gson = new Gson();
	@Autowired
	private AuditService auditService;
	@Autowired
	private ProductService productService;
	@Autowired
	private GuaranteeService guaranteeService;
	@Autowired
	private LoanService loanService;
	@Autowired
	private BaseAreaService baseAreaService;
	@Autowired
	private ApproveResultService approveResultService;
	@Autowired
	private PersonService personService;
	@Autowired
	private ExtensionService extensionService;
	
	
	/**
	 * 
	 * 审批主界面
	 *
	 * @return
	 */
	@RequestMapping("/auditMain")
	public String viewList(HttpServletRequest request,ModelMap modelMap) {
		/*设置数据字典*/
		setDataDictionaryArr(new String[] { EnumConstants.APPROVE_RESULT_STATE, EnumConstants.LOAN_STATUS, EnumConstants.PRODUCT_TYPE,EnumConstants.REPAYMENT_PLAN_STATE });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		String idnum=request.getParameter("idnum");
		if(!StringUtil.isEmpty(idnum)){
			modelMap.put("isJump", 1);
		}
		modelMap.put("idnum",idnum);
		return "audit/auditList";
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
	@SuppressWarnings("unchecked")
	@RequestMapping("/getLoanPage")
	@ResponseBody
	public Object viewList(LoanVO loanVO, int rows, int page) {
		Long userId = ApplicationContext.getUser().getId();
		List<Integer> statusList = new ArrayList<Integer>();
		if(loanVO.getFlag()==null){
		if (loanVO.getStatus() == null) {
			statusList.add(EnumConstants.LoanStatus.审批中.getValue());
			statusList.add(EnumConstants.LoanStatus.展期签批中.getValue());
			loanVO.setStatusList(statusList);
		} else if (loanVO.getStatus() == 0) {
			loanVO.setStatus(null);
		}
		}else{
			loanVO.setStatus(null);
		}	
		//判断是否展期
		if(loanVO.getExtensionTime()!=null&& loanVO.getExtensionTime().compareTo(0)>0)
		{
			loanVO.setIsExtension(1);
		}
		
		
		// 确定查询的产品类型
		List<Product> products = productService.findProductTypeByUserId(userId);
		List<Integer> canBrowseproductIds = new ArrayList<Integer>();
		for (Product product : products)
			canBrowseproductIds.add(product.getProductType());
		if (canBrowseproductIds.size() < 1)
			return null;

		List<Integer> qualifiedProductIds = new ArrayList<Integer>();
		if (loanVO.getSelectedProductId() != null) {
			if (canBrowseproductIds.contains(loanVO.getSelectedProductId().intValue()))
				qualifiedProductIds.add(loanVO.getSelectedProductId().intValue());
			else
				return null;
		} else {
			qualifiedProductIds.addAll(canBrowseproductIds);
		}
		loanVO.setProductTypeList(qualifiedProductIds);

		loanVO.getPager().setRows(rows);
		loanVO.getPager().setPage(page);
		loanVO.getPager().setSidx("decode(Submit_Date,null,Created_Time,Submit_Date)");
		loanVO.getPager().setSort("DESC");
		Pager loanPager = loanService.findWithPGUnionExtension(loanVO);

		List<Loan> loans = loanPager.getResultList();

		List<ListedLoan> loanList = new ArrayList<ListedLoan>();

		for (Loan loan : loans) {
			String operations = null;
			if (loanService.canApprove(loan, userId))
				operations = "签批";
			if (loanService.canExtensionApprove(loan))
				operations = "展期签批";
			ProductVO productVO = new ProductVO();
			productVO.setId(loan.getProductId());
			Product product = productService.findListByVO(productVO).get(0);
			loanList.add(new ListedLoan(loan.getId(),loan.getLoanId(), loan.getStatus(), loan.getRequestDate(), loan.getPerson().getId(), loan.getPerson().getPersonNo(), loan.getPerson().getName(), loan.getPerson()
				.getIdnum(), loan.getProductType(), loan.getSalesDept().getId(), loan.getSalesDept().getName(), loan.getSubmitDate(), (operations != null ? operations.toString() : null), loan
				.getService().getName(), loan.getCrm().getName(), (loan.getAssessor() != null ? loan.getAssessor().getName() : ""), loan.getRequestMoney(),loan.getExtensionTime(),product.getProductCode(),loan.getAuditMoney(),product.getProductName()));
		}
		ResultSet set = new ResultSet();
		set.setRows(loanList);
		set.setTotal(loanPager.getTotalCount());
		return set;
	}

	class ResultSet {
		private List<ListedLoan> rows;
		private long total;

		public List<ListedLoan> getRows() {
			return rows;
		}

		public void setRows(List<ListedLoan> rows) {
			this.rows = rows;
		}

		public long getTotal() {
			return total;
		}

		public void setTotal(long total) {
			this.total = total;
		}
	}

	class ListedLoan {
		
		public ListedLoan(Long id,Long loanId, Integer status, Date requestDate, Long personId, String personNo, String personName, String personIdnum, Integer productId, Long salesDeptCode,
				String salesDeptName, Date submitDate, String operations, String serviceName, String managerName, String assessorName, BigDecimal requestMoney,Integer extensionTime,String productCode,BigDecimal auditMoney,String productName) {
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
		}
				
		private Long id;
		private Long loanId;
		private Integer status;
		private Date requestDate;
		private Long personId;
		private String personNo;
		private String personName;
		private String personIdnum;
		private Integer productId;
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

		public Integer getProductId() {
			return productId;
		}

		public void setProductId(Integer productId) {
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

	};

	/**
	 * 签批
	 * @throws UnknownHostException 
	 * 
	 */
	@RequestMapping("/approve")
	@ResponseBody
	public void approve(ApproveResultVO approveVO) {
		Long userId = null;
		if (ApplicationContext.getUser() != null && ApplicationContext.getUser().getId() != null)
			userId = ApplicationContext.getUser().getId();
		auditService.approve(approveVO, userId);
	}

	
	/**
	 * <pre>
	 * 展期签批
	 * </pre>
	 *
	 * @param approveVO
	 */
	@RequestMapping("/extensionApprove")
	@ResponseBody
	public void extensionApprove(ApproveResultVO approveVO) {
	
		auditService.extensionApprove(approveVO);
	}

	/**
	 * 
	 * <pre>
	 * 获取所有营业网点信息
	 * </pre>
	 *
	 * @return
	 */
	@RequestMapping("/getAllSalesDepts")
	@ResponseBody
	public List<BaseArea> getAllSalesDepts() {
		List<BaseArea> allSalesDepts = baseAreaService.getAllSalesDepts();
		BaseArea baseArea = new BaseArea();
		baseArea.setId(null);
		baseArea.setName("全部");
		allSalesDepts.add(0, baseArea);
		return allSalesDepts;
	}

	/**
	 * 
	 * <pre>
	 * 获取所有城市
	 * </pre>
	 *
	 * @return
	 */
	@RequestMapping("/getAllCitys")
	@ResponseBody
	public List<BaseArea> getAllCitys() {
		List<BaseArea> allCitys = baseAreaService.getAllCitys();
		BaseArea baseArea = new BaseArea();
		baseArea.setId(null);
		baseArea.setName("全部");
		allCitys.add(0, baseArea);
		return allCitys;
	}

	/**
	 * 
	 * <pre>
	 * 根据cityId选出对应的营业部
	 * </pre>
	 *
	 * @return
	 */
	@RequestMapping("/getSalesDeptFrCityId")
	@ResponseBody
	public List<BaseArea> getSalesDeptFrCityId(String cityId) {
		return baseAreaService.getSalesDeptFrCityId(cityId);
	}

	@RequestMapping("/getGuaranteesByPersonId")
	@ResponseBody
	public List<Guarantee> getGuaranteesByPersonId(Long personId) {
		GuaranteeVO guaranteeVO = new GuaranteeVO();
		guaranteeVO.setPersonId(personId);
		return guaranteeService.findListByVo(guaranteeVO);
	}
	/**
	 * 
	 * <pre>
	 * 获取借款状态信息列表
	 * </pre>
	 *@author zhangshihai
	 * @return
	 */
	@RequestMapping("/getLoanStatusList")
	@ResponseBody
	public List<LoanStatusDetail> getLoanStatusList() {
		LoanStatus[] statusList = LoanStatus.values();
		List<LoanStatusDetail> loanStatusList = new ArrayList<LoanStatusDetail>();

		LoanStatusDetail loanStatusDetail = null;
		loanStatusDetail = new LoanStatusDetail("所有", 0);
		loanStatusList.add(loanStatusDetail);
		for (LoanStatus loanStatus : statusList) {
			loanStatusDetail = new LoanStatusDetail(loanStatus.name(), loanStatus.getValue());
			loanStatusList.add(loanStatusDetail);
		}

		return loanStatusList;
	}

	/**
	 * <pre>
	 * 根据借款ID和审批状态获取相关管的审批记录
	 * </pre>
	 *
	 * @param loanId
	 * @param state
	 * @return
	 */
	@RequestMapping("/findApproveResultByLoanIdAndState")
	@ResponseBody
	public ApproveResult findApproveResultByLoanIdAndStatus(Long loanId, Integer state) {
		ApproveResultVO approveResultVO = new ApproveResultVO();
		approveResultVO.setLoanId(loanId);
		approveResultVO.setState(state);
		List<ApproveResult> approveResult = approveResultService.findListByVo(approveResultVO);
		if (approveResult != null && approveResult.size() == 1) {
			return approveResult.get(0);
		}
		return null;
	}

	@RequestMapping("/imageUploadView/{loanId}")
	public String imageUploadView(@PathVariable("loanId") Long loanId, Model model) {
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
		model.addAttribute("optModule", EnumConstants.OptionModule.AUDIT_LOAN.getValue());
		return "audit/imageUploadView";
	}
	@RequestMapping("/extensionImageUploadView/{loanId}")
	public String extensionImageUploadView(@PathVariable("loanId") Long loanId, Model model) {
		model.addAttribute("loanId", loanId);
		Extension extension = extensionService.get(loanId);
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
		model.addAttribute("optModule", EnumConstants.OptionModule.AUDIT_LOAN.getValue());
		return "audit/imageUploadView";
	}
	
	
	/***
	 * 
	 * <pre>
	 * 获取众安返回的结果
	 * </pre>
	 *
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getZongAnReturnData")
	@ResponseBody
	public Map<String,Object> getZongAnReturnData(String loanId) {
		Map<String,Object> map=auditService.getZongAnReturnData(loanId);
		return map;
	}
	
	
	
	
	/***
	 * 
	 * <pre>
	 * 判断合同金额是否大于20W
	 * </pre>
	 *
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/contractManeyOverProof")
	@ResponseBody
	public Map<String,Object> contractManeyOverProof(String name,String idNo,String loanId,String agreeMoneyInput,String agreeTimeComb) {
		System.out.println("-------------****--------------");
		logger.info("***********************************"+name+","+idNo+","+loanId);
		
		Map<String,Object> map=auditService.contractManeyOverProof(name,idNo,loanId,agreeMoneyInput,agreeTimeComb);
		return map;
	}
}
