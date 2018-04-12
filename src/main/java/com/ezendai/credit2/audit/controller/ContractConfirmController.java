package com.ezendai.credit2.audit.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
import com.ezendai.credit2.audit.service.ContractConfirmService;
import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.framework.constant.Constants;
import com.ezendai.credit2.framework.util.DateUtil;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.system.controller.BaseController;
import com.ezendai.credit2.system.enumerate.SysParameterEnum;
import com.ezendai.credit2.system.model.SysParameter;
import com.ezendai.credit2.system.service.SysParameterService;
import com.ezendai.credit2.system.service.SysUserService;

@Controller
@RequestMapping("/audit/contractConfirm")
public class ContractConfirmController extends BaseController {
	@Autowired
	private LoanService loanService;

	@Autowired
	private GuaranteeService guaranteeService;

	@Autowired
	private PersonService personService;

	@Autowired
	private ProductService productService;

	@Autowired
	private ContractConfirmService contractConfirmService;

	@Autowired
	private SysParameterService sysParameterService;
	
	@Autowired
	private ExtensionService extensionService;
	
	@Autowired SysUserService sysUserService;

	@RequestMapping("/imageUploadView/{loanId}")
	public String imageUploadView(@PathVariable("loanId") String loanId, Model model) {
		model.addAttribute("loanId", loanId);
		Loan loan = loanService.get(Long.parseLong(loanId));
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
		model.addAttribute("optModule", EnumConstants.OptionModule.CONTRACT_CONFIRM.getValue());
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
		return "audit/contractConfirm/imageUploadView";
	}
	
	@RequestMapping("/extensionImageUploadView/{loanId}")
	public String extensionImageUploadView(@PathVariable("loanId") String loanId, Model model) {
		model.addAttribute("loanId", loanId);
		Extension extension = extensionService.get(Long.valueOf(loanId));
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
		model.addAttribute("optModule", EnumConstants.OptionModule.CONTRACT_CONFIRM.getValue());
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
		return "audit/contractConfirm/imageUploadView";
	}

	/**
	* 
	* <pre>
	*合同确认退回
	* </pre>
	*
	* @return
	*/
	@RequestMapping("/submitRefuse")
	@ResponseBody
	public JSONObject submitRefuse(Long loanId, String reason, Long refuseSecondReasonId, Integer extensionTime) {
		JSONObject result = new JSONObject();
		result.put("repCode", "000000");
		try {
			contractConfirmService.refuse(loanId, reason, refuseSecondReasonId, extensionTime);
		} catch (Exception e) {
			result.put("repCode", "100000");
			result.put("repMsg", e.getMessage());
		}
		return result;
	}

	/**
	* 
	* <pre>
	*合同 确认 
	* </pre>
	*
	* @return
	*/
	@RequestMapping("/submitContractConfirm")
	@ResponseBody
	public JSONObject submitContractConfirm(Long loanId, Integer extensionTime) {
		JSONObject result = new JSONObject();
		result.put("repCode", "000000");

		try {
			if(extensionTime.compareTo(0) == 0) {
                contractConfirmService.confirm(loanId);
            } else {
                contractConfirmService.extensionConfirm(loanId);
            }
		} catch (Exception e) {
			result.put("repCode", "100000");
			result.put("repMsg", e.getMessage());
		}

		return result;
	}

	@RequestMapping("/list")
	public String init(HttpServletRequest request) {
		/*设置数据字典*/
		setDataDictionaryArr(new String[] { EnumConstants.PRODUCT_TYPE });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		return "audit/contractConfirm/contractConfirmList";
	}

	@RequestMapping("/getLoanStatusList")
	@ResponseBody
	public List<LoanStatusDetail> getLoanStatusList() {
		List<LoanStatusDetail> list = new ArrayList<LoanStatusDetail>();
		list.add(new LoanStatusDetail("全部", 0));
		list.add(new LoanStatusDetail("合同确认", EnumConstants.LoanStatus.合同确认.getValue()));
		list.add(new LoanStatusDetail("展期合同确认", EnumConstants.LoanStatus.展期合同确认.getValue()));
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/list.json")
	@ResponseBody
	public Map list(@RequestParam(value = Constants.PAGE_NUMBER_NAME, defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = Constants.PAGE_ROWS_NAME, defaultValue = Constants.DEFAULT_PAGE_ROWS) int pageSize,
			@ModelAttribute("vo") LoanVO vo, HttpServletRequest request) {
		Long userId = ApplicationContext.getUser().getId();
		// 默认显示全部
		if (vo.getStatus() == null || vo.getStatus() == 0) {
			List<Integer> statusList = new ArrayList<Integer>();
			statusList.add(EnumConstants.LoanStatus.合同确认.getValue());
			statusList.add(EnumConstants.LoanStatus.展期合同确认.getValue());
			vo.setStatusList(statusList);
				vo.setStatus(null);
		}
		// 判断是否展期
		if (vo.getExtensionTime() != null
				&& vo.getExtensionTime().compareTo(0) > 0) {
			vo.setIsExtension(1);
		}
		// 当传空值的时候传15天前的日期
		if (vo.getSignDateStart() == null) {
			Date signDateStart = DateUtil.getDateBefore(15);
			vo.setSignDateStart(signDateStart);
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
			if (canBrowseproductIds.contains(vo.getSelectedProductId().intValue()))
				qualifiedProductIds.add(vo.getSelectedProductId().intValue());
			else
				return null;
		} else {
			qualifiedProductIds.addAll(canBrowseproductIds);
		}
		vo.setProductTypeList(qualifiedProductIds);

		Pager p = new Pager();
		p.setPage(page);
		p.setRows(pageSize);
		p.setSidx("ID");
		p.setSort("DESC");
		vo.setPager(p);
		Integer userType = ApplicationContext.getUser().getUserType();
		 if (EnumConstants.UserType.STORE_MANAGER.getValue().equals(userType) || EnumConstants.UserType.STORE_ASSISTANT_MANAGER.getValue().equals(userType)) {
				// 门店经理,门店副理,查询当前网点的数据
				List<Long> canBrowseSalesDeptList = sysUserService.getSalesDeptIdByUserId(userId);
				if (canBrowseSalesDeptList == null) {
					return null;
				} else if (vo.getSalesDeptId() == null) {
					vo.setSalesDeptIdList(canBrowseSalesDeptList);
				}
		 }
		Pager pager = loanService.findWithPGUnionExtension(vo);
		List<Loan> loanList = pager.getResultList();
		List<ContractConfirmLoanList> loanListList = new ArrayList<ContractConfirmLoanList>();
		for (Loan loan : loanList) {
				GuaranteeVO guaranteeVO = new GuaranteeVO();
				guaranteeVO.setPersonId(loan.getPersonId());
				guaranteeVO.setLoan(loan);
				guaranteeVO.setFlag(EnumConstants.YesOrNo.YES.getValue());
				List<Guarantee> guaranteeList = guaranteeService.findListByVo(guaranteeVO);
				// Guarantee guarantee = guaranteeService.get(guaranteeVO);
				// 得到可以对贷款的所有操作——日志，附件，合同签约/提交
				StringBuilder operations = new StringBuilder("日志");
				operations.append("|" + "附件");
				operations.append("|" + "合同确认");
				operations.append("|" + "退回");
				Date startRepayDate = loan.getStartRepayDate();
				int dayOfMonth = 0;
				if (startRepayDate != null) {
					dayOfMonth = DateUtil.getDayOfMonth(startRepayDate);
				}
				loanListList.add(new ContractConfirmLoanList(loan.getId(), loan.getPerson().getName(), loan.getPerson().getIdnum(),loan.getService().getName(), loan.getCrm().getName(),
						loan.getAuditMoney(), loan.getAuditTime(), loan.getContractNo(), guaranteeList,loan.getProductId(), loan.getProductType().longValue(), loan.getAssessor().getName(),dayOfMonth, operations.toString(), loan.getExtensionTime(),loan.getProduct()));
		}
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("total", pager.getTotalCount());
		result.put("rows", loanListList);
		return result;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

}

class ContractConfirmLoanList {
	public ContractConfirmLoanList(Long loanId, String name, String idnum, String serviceName, String crmName, BigDecimal auditMoney, Integer time, String contractNo, List<Guarantee> guaranteeList,
									Long productId,	Long productType, String assessorName, Integer repayDay, String operations, Integer extensionTime,Product product) {
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
		this.productType =  productType;
		this.assessorName = assessorName;
		this.repayDay = repayDay;
		this.operations = operations;
		this.extensionTime = extensionTime;
		this.product=product;
	}

	Long loanId;
	String name;
	String idnum;
	String serviceName;
	String crmName;
	BigDecimal auditMoney;
	Integer time;
	String contractNo;
    List<Guarantee>  guaranteeList;
	Long productId;
	Long productType;
	String assessorName;
	Integer repayDay;
	String operations;
	Integer extensionTime;
	Product product;
	public Integer getExtensionTime() {
		return extensionTime;
	}

	public void setExtensionTime(Integer extensionTime) {
		this.extensionTime = extensionTime;
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

	public String getAssessorName() {
		return assessorName;
	}

	public void setAssessorName(String assessorName) {
		this.assessorName = assessorName;
	}

	public Integer getRepayDay() {
		return repayDay;
	}

	public void setRepayDay(Integer repayDay) {
		this.repayDay = repayDay;
	}

	public String getOperations() {
		return operations;
	}

	public void setOperations(String operations) {
		this.operations = operations;
	}
	public Long getProductType() {
		return productType;
	}
	public void setProductType(Long productType) {
		this.productType = productType;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
}