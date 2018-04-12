package com.ezendai.credit2.after.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezendai.credit2.after.model.CasesPerson;
import com.ezendai.credit2.after.model.CollectionCasesRecord;
import com.ezendai.credit2.after.model.CollectionCasesTask;
import com.ezendai.credit2.after.model.CollectionCreateCases;
import com.ezendai.credit2.after.service.CollectionCreateCasesService;
import com.ezendai.credit2.after.service.RepayService;
import com.ezendai.credit2.after.service.SpecialRepaymentService;
import com.ezendai.credit2.after.vo.CollectionCreateCasesVO;
import com.ezendai.credit2.after.vo.CollectionTaskVO;
import com.ezendai.credit2.after.vo.OverdueReceivablesCaseVO;
import com.ezendai.credit2.after.vo.RepayEntryDetailsVO;
import com.ezendai.credit2.apply.model.Company;
import com.ezendai.credit2.apply.model.Contacter;
import com.ezendai.credit2.apply.model.Extension;
import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.model.Person;
import com.ezendai.credit2.apply.model.Product;
import com.ezendai.credit2.apply.service.ContacterService;
import com.ezendai.credit2.apply.service.ExtensionService;
import com.ezendai.credit2.apply.service.LoanService;
import com.ezendai.credit2.apply.service.PersonCompanyService;
import com.ezendai.credit2.apply.service.PersonService;
import com.ezendai.credit2.apply.service.ProductService;
import com.ezendai.credit2.apply.vo.ContacterVO;
import com.ezendai.credit2.apply.vo.LoanVO;
import com.ezendai.credit2.audit.model.PersonContacterAdditional;
import com.ezendai.credit2.audit.model.RepaymentPlan;
import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.framework.model.UserSession;
import com.ezendai.credit2.framework.util.DateUtil;
import com.ezendai.credit2.framework.util.FileUtil;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.constant.BizConstants;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.master.enumerate.EnumConstants.UserType;
import com.ezendai.credit2.master.service.AreaService;
import com.ezendai.credit2.master.service.BaseAreaService;
import com.ezendai.credit2.master.vo.BaseAreaVO;
import com.ezendai.credit2.system.Credit2Properties;
import com.ezendai.credit2.system.controller.BaseController;
import com.ezendai.credit2.system.enumerate.SysParameterEnum;
import com.ezendai.credit2.system.model.SysLog;
import com.ezendai.credit2.system.model.SysParameter;
import com.ezendai.credit2.system.model.SysUser;
import com.ezendai.credit2.system.service.SysLogService;
import com.ezendai.credit2.system.service.SysParameterService;
import com.ezendai.credit2.system.service.SysUserService;
import com.ezendai.credit2.system.vo.SysUserVO;

@Controller
@RequestMapping("/CollectionManagerCases/Main")
public class CollectionManagerCasesController extends BaseController {

	/**
	 * 区域service
	 */
	@Autowired
	private AreaService areaService;
	@Autowired
	private CollectionCreateCasesService createCasesService;
	@Autowired
	private ProductService productService;

	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private PersonService personService;
	@Autowired
	private LoanService loanService;
	@Autowired
	private ExtensionService extensionService;
	@Autowired
	private SpecialRepaymentService specialRepaymentService;
	@Autowired
	private RepayService repayService;
	@Autowired
	private ContacterService contacterService;
	@Autowired
	private PersonCompanyService personCompanyService;
	@Autowired
	private SysParameterService sysParameterService;
	@Autowired
	private SysLogService sysLogService;
	@Autowired
	private BaseAreaService baseAreaService;
	@Autowired
	private Credit2Properties credit2Properties;
	private static final Logger logger = Logger
			.getLogger(CollectionManagerCasesController.class);

	@RequestMapping("/init")
	public String init(HttpServletRequest request, ModelMap modelMap) {
		/* 设置数据字典 */
		setDataDictionaryArr(new String[] { EnumConstants.LOAN_STATUS,
				EnumConstants.PRODUCT_TYPE, EnumConstants.CONTRACT_SRC });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		UserSession user = ApplicationContext.getUser();
		String optFlag = "1";
		if (user.getUserType().equals(3)) {
			optFlag = "0";
		}
		modelMap.put("optFlag", optFlag);
		return "after/collectionManagerCases/collectionManagerCasesMain";
		/* return "/finance/financialAudit/financialAuditList"; */
	}

	@RequestMapping("/casesManager")
	public String casesManager(HttpServletRequest request, ModelMap modelMap) {
		/* 设置数据字典 */
		Long id = Long.parseLong(request.getParameter("casesId"));
		Long loanId = Long.parseLong(request.getParameter("loanId"));

		setDataDictionaryArr(new String[] { EnumConstants.LOAN_STATUS,
				EnumConstants.PRODUCT_TYPE, EnumConstants.CONTRACT_SRC });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		UserSession user = ApplicationContext.getUser();
		CollectionCreateCases cc = createCasesService
				.selectLoanInfoByCaseId(id);
		CollectionCreateCases info = createCasesService.selectCaseInfo(id);
		Product p = productService.get(Long.valueOf((info.getProductType())));
		info.setProductName(p.getProductName());
		RepayEntryDetailsVO rd = viewEdit(loanId);
		if (info.getCaseState().equals("5")) {
			rd.setOverdueStartDate(null);
			rd.setRepayAllAmount(new BigDecimal(0));
			rd.setRepayAmount(new BigDecimal(0));
			rd.setOverduePrincipalInterestSum(null);
			rd.setFine(null);
		}
		if (info.getCaseState().equals("6")) {
			rd.setOverdueStartDate(null);
			rd.setRepayAllAmount(new BigDecimal(0));
			rd.setRepayAmount(new BigDecimal(0));
			rd.setOverduePrincipalInterestSum(null);
			rd.setFine(null);
		}
		/*
		 * if(info.getCaseState().equals("7")){ rd.setOverdueStartDate(null);
		 * rd.setRepayAllAmount(new BigDecimal(0)); rd.setRepayAmount(new
		 * BigDecimal(0)); rd.setOverduePrincipalInterestSum(null);
		 * rd.setFine(null); }
		 */
		if (cc.getStatus() == 150) {
			rd.setOverdueStartDate(null);
			rd.setRepayAllAmount(new BigDecimal(0));
			rd.setRepayAmount(new BigDecimal(0));
			rd.setOverduePrincipalInterestSum(null);
			rd.setFine(null);
		}
		if (cc.getStatus() == 130) {
			rd.setOverdueStartDate(null);
			rd.setRepayAllAmount(new BigDecimal(0));
			rd.setRepayAmount(new BigDecimal(0));
			rd.setOverduePrincipalInterestSum(null);
			rd.setFine(null);
		}

		modelMap.put("cc", cc);
		modelMap.put("info", info);
		modelMap.put("rd", rd);
		String optFlag = "1";
		if (user.getUserType().equals(3)) {
			optFlag = "0";
		}
		modelMap.put("optFlag", optFlag);
		return "after/collectionManagerCases/casesManager";
		/* return "/finance/financialAudit/financialAuditList"; */
	}

	// 进入外访电核记录页面
	@RequestMapping("/casesRecordUI/{taskId}")
	public String casesRecordUI(@PathVariable("taskId") String taskId,
			HttpServletRequest request, ModelMap modelMap) {
		/* 设置数据字典 */
		Long id = Long.parseLong(taskId);
		Long loanId = Long.parseLong(request.getParameter("loanId"));
		setDataDictionaryArr(new String[] { EnumConstants.LOAN_STATUS,
				EnumConstants.PRODUCT_TYPE, EnumConstants.CONTRACT_SRC });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		UserSession user = ApplicationContext.getUser();
		CollectionCasesTask task = createCasesService.selectTaskById(id);
		modelMap.put("taskId", id);
		modelMap.put("loanId", loanId);
		modelMap.put("task", task);
		return "after/collectionManagerCases/casesRecordUI";
		/* return "/finance/financialAudit/financialAuditList"; */
	}

	// 联系人页面
	@RequestMapping("/contactPersonUI/{cid}/{part}/{subordinate}")
	public String contactPersonUI(@PathVariable("cid") String cid,
			@PathVariable("part") String part,
			@PathVariable("subordinate") String subordinate,
			HttpServletRequest request, ModelMap modelMap) {
		/* 设置数据字典 */
		Long id = Long.parseLong(cid);
		setDataDictionaryArr(new String[] { EnumConstants.LOAN_STATUS,
				EnumConstants.PRODUCT_TYPE, EnumConstants.CONTRACT_SRC });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		UserSession user = ApplicationContext.getUser();
		Person p = null;
		Company c = null;
		Contacter contact = null;
		if (subordinate.equals("1")) {
			if (part.equals("1")) {
				p = personService.get(id);
				c = personCompanyService.getCompanyById(p.getCompanyId());
				modelMap.put("person", p);
			} else if (part.equals("2")) {
				contact = contacterService.get(id);
				Person perN = personService.get(contact.getBorrowerId());
				modelMap.put("personName", perN.getName());
				modelMap.put("person", perN);
			}
			modelMap.put("cid", id);
			modelMap.put("contact", contact);
			modelMap.put("company", c);
			modelMap.put("part", part);
			modelMap.put("subordinate", subordinate);
			return "after/collectionManagerCases/contactPerson";
		} else {
			CasesPerson cpmap = createCasesService
					.selectCollectionContacerById(Long.parseLong(cid));
			String personId = request.getParameter("personId");
			p = personService.get(Long.parseLong(personId));
			modelMap.put("person", p);
			modelMap.put("cp", cpmap);
			return "after/collectionManagerCases/contactPersonUpdateUI";
		}

		/* return "/finance/financialAudit/financialAuditList"; */
	}

	// 新增联系人页面
	@RequestMapping("/addContactPersonUI/{personId}")
	public String contactPersonUI(@PathVariable("personId") String personId,
			HttpServletRequest request, ModelMap modelMap) {
		/* 设置数据字典 */
		Long id = Long.parseLong(personId);
		setDataDictionaryArr(new String[] { EnumConstants.LOAN_STATUS,
				EnumConstants.PRODUCT_TYPE, EnumConstants.CONTRACT_SRC });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		UserSession user = ApplicationContext.getUser();
		Person p = null;
		Company c = null;
		Contacter contact = null;
		p = personService.get(id);
		modelMap.put("cid", id);
		modelMap.put("contact", contact);
		modelMap.put("person", p);
		return "after/collectionManagerCases/addContactPerson";
		/* return "/finance/financialAudit/financialAuditList"; */
	}

	// 更新联系人
	@RequestMapping("/addContactPerson")
	@ResponseBody
	public String updateContacter(CasesPerson cp, HttpServletRequest request,
			ModelMap modelMap) {
		cp.setPart(2);
		UserSession user = ApplicationContext.getUser();
		cp.setCreatedTime(new Date());
		cp.setCreator(user.getLoginName());
		cp.setCreatorId(user.getId());
		createCasesService.insertCollectionContacer(cp);
		return "";
	}

	// 更新联系人

	@RequestMapping("/updateContacter")
	@ResponseBody
	public String updateContacter(ContacterVO vo, HttpServletRequest request,
			ModelMap modelMap) {
		contacterService.update(vo);
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.COLLECTION_CASE_MANAGE
				.getValue());
		sysLog.setOptType(EnumConstants.OptionType.COLLECTION_EDIT_CONTACTS
				.getValue());
		// sysLog.setRemark("借款ID   "+vo.getLoanId().toString());
		sysLogService.insert(sysLog);
		return "";
	}

	@RequestMapping("/updatePersonContacter")
	@ResponseBody
	public String updatePersonContacter(CasesPerson cp,
			HttpServletRequest request, ModelMap modelMap) {
		UserSession user = ApplicationContext.getUser();
		if (cp.getSubordinate().equals("1")) {
			cp.setRelationId(cp.getId());
			cp.setCreatedTime(new Date());
			cp.setCreator(user.getLoginName());
			cp.setCreatorId(user.getId());
			Long id = createCasesService.insertCollectionContacer(cp);
			cp.setId(id);
			createCasesService.updatePersonContacterAdditional(cp);
		} else {
			cp.setModifierId(cp.getId());
			cp.setModifiedTime(new Date());
			cp.setModifier(user.getLoginName());
			createCasesService.updateCollectionContacer(cp);
		}
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.COLLECTION_CASE_MANAGE
				.getValue());
		sysLog.setOptType(EnumConstants.OptionType.COLLECTION_EDIT_CONTACTS
				.getValue());
		return "";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/collectionList")
	@ResponseBody
	public Map collectionList(CollectionCreateCasesVO vo, int rows, int page) {
		Pager p = new Pager();
		p.setPage(page);
		p.setRows(rows);
		vo.setPager(p);
		UserSession user = ApplicationContext.getUser();
		List<Product> products = productService.findProductTypeByUserId(user
				.getId());
		List<Product> productsid = productService.findProductsByUserId(user
				.getId());
		List<Long> productsL = new ArrayList<Long>();
		for (Product pr : productsid) {
			productsL.add(pr.getId());
		}
		vo.setProductTypeList(productsL);
		SysParameter overDueManagerCodeSys = sysParameterService
				.getByCode(SysParameterEnum.OVERDUE_RECEIVABLES_MANAGER.name());
		String overdueManagerCode = overDueManagerCodeSys.getParameterValue();
		SysUser sysUser = sysUserService.get(user.getId());
		if (sysUser.getDataPermission().length() > 2) {
			// 根据用户ID获取营业网点的ID
			List<Long> canBrowseSalesDeptList = sysUserService
					.getSalesDeptIdByUserId(user.getId());
			// 如果是刘娜，则重新获取营业网点ID集合
			if (EnumConstants.IsAddOtherDepts.isTrue.getValue().equals(String.valueOf(sysUser.getIsAddOtherDepts()))) {
				BaseAreaVO baseAreaVo = new BaseAreaVO();
				baseAreaVo.setUserId(user.getId());
				baseAreaVo.setIdentifier(BizConstants.CREDIT2_SALESDEPARTMENT);
				canBrowseSalesDeptList = null;
				// 获取所有的营业网点ID
				canBrowseSalesDeptList = areaService
						.getDeptsByUserIdAndDeptsTypes(baseAreaVo);
			}
			vo.setSalesDeptIdList(canBrowseSalesDeptList);
			if (user.getUserType().equals(12)) {
				List<Long> areaId = new ArrayList<Long>();
				areaId = sysUserService.getSalesDeptIdByUserIdAndDeptType(user
						.getId());
				vo.setSalesDeptIdList(areaId);
			}
		}
		if (products.get(0).getProductType() == (long) EnumConstants.ProductList.CAR_LOAN
				.getValue()
				|| products.get(0).getProductType() == (long) EnumConstants.ProductList.CAR_NEW_LOAN
						.getValue()) {// 车贷事业部显示所有车贷营业部
			vo.setDeptSerch("2222");
		} else if (user.getUserType().equals(
				EnumConstants.UserType.SYSTEM_ADMIN.getValue())
				|| user.getUserType() == EnumConstants.UserType.FINANCE
						.getValue()) {// admin和财务账号显示车贷和企业贷营业部
			vo.setDeptSerch("2222");
		} else if (user.getLoginName().equals(overdueManagerCode)) {
			vo.setDeptSerch("1111");
		} else if (user.getLoginName().equals("testCollectors1")) {
			vo.setDeptSerch("2222");
		} else if (user.getLoginName().length() >= 4) {
			if (user.getLoginName().substring(0, 4).equals("test")) {
				vo.setDeptSerch("2222");
			} else {
				if (user.getUserType().equals(11)) {
					vo.setDeptSerch("2222");
				} else {
					vo.setDeptSerch("1111");
				}
			}

		} else {
			vo.setDeptSerch("1111");
		}
		Pager pager = createCasesService.findManagerCasesWithPG(vo);
		List<CollectionCreateCases> casesList = pager.getResultList();
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("total", pager.getTotalCount());
		result.put("rows", casesList);
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/collectionTaskList")
	@ResponseBody
	public Map collectionTaskList(CollectionCreateCasesVO vo, int rows, int page) {
		Pager p = new Pager();
		p.setPage(page);
		p.setRows(rows);
		vo.setPager(p);
		Pager pager = createCasesService.findCollectionCasesTaskWithPG(vo);
		List<CollectionCasesTask> casesList = pager.getResultList();
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("total", pager.getTotalCount());
		result.put("rows", casesList);
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/collectionPersonList")
	@ResponseBody
	public Map collectionPersonList(CollectionCreateCasesVO vo, int rows,
			int page) {
		// 查询出从表所有与person有关联系人
		Pager pall = new Pager();
		pall.setPage(page);
		pall.setRows(1000);
		CasesPerson cpall = new CasesPerson();
		cpall.setPager(pall);
		cpall.setPersonId(vo.getPersonId());
		Pager pagerall = createCasesService.findCollectionContacerWithPG(cpall);
		List<CasesPerson> deleList = new ArrayList<CasesPerson>();
		List<CasesPerson> allList = pagerall.getResultList();
		// 查询出主表的所有联系人
		Pager p = new Pager();
		p.setPage(page);
		p.setRows(1000);
		vo.setPager(p);
		Pager pager = createCasesService.findPersonWithPG(vo);
		List<CasesPerson> zhuList = pager.getResultList();
		// 过滤掉所有重复数据
		for (CasesPerson per : allList) {
			for (CasesPerson person : zhuList) {
				if (person.getId().equals(per.getRelationId())
						&& person.getPart().equals(per.getPart())) {
					deleList.add(person);
				}
			}
		}

		zhuList.removeAll(deleList);
		// 总集合
		allList.addAll(zhuList);
		// 计算 起数
		int start = 0;
		if (page > 1) {
			start = rows * (page - 1);
		}
		// 计算 终止数
		int end = 0;
		end = start + rows;
		// 分页list
		List<CasesPerson> resultList = new ArrayList<CasesPerson>();

		for (int index = start; index < end; index++) {
			if (index >= allList.size()) {
				break;
			}
			resultList.add(allList.get(index));
		}
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("total", allList.size());
		result.put("rows", resultList);
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/collectionRecordList")
	@ResponseBody
	public Map collectionRecordList(CollectionCreateCasesVO vo, int rows,
			int page) {
		Pager p = new Pager();
		p.setPage(page);
		p.setRows(rows);
		vo.setPager(p);
		Pager pager = createCasesService.findCasesRecordWithPG(vo);
		List<CollectionCasesRecord> casesList = pager.getResultList();
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("total", pager.getTotalCount());
		result.put("rows", casesList);
		return result;
	}

	@RequestMapping("/searchLoan/{id}")
	public String searchLoan(@PathVariable("id") String id,
			HttpServletRequest request, ModelMap map) throws Exception {
		/* 设置数据字典 */
		/*
		 * setDataDictionaryArr(new String[] { EnumConstants.PRODUCT_STATUS});
		 * request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		 */
		/*
		 * List<BaseArea> allCitys = baseAreaService.getAllCitys();
		 * model.addAttribute("cityList",allCitys);
		 */
		CollectionCreateCases loan = createCasesService
				.selectLoanInfoByLoanId(Long.valueOf(id));
		map.put("loan", loan);
		return "after/collectionCreateCases/addCases";
	}

	@RequestMapping("/casesAdd")
	@ResponseBody
	public Map<String, Object> productEditSave(OverdueReceivablesCaseVO vo)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		// edit
		try {
			// 新增待审核
			UserSession user = ApplicationContext.getUser();
			vo.setCreatedTime(new Date());
			vo.setCreator(user.getLoginName());
			vo.setCreatorId(user.getId());
			vo.setCaseState(2);
			vo.setCaseType(1);
			vo.setCaseFrom(2);
			vo.setTransferDate(new Date());
			createCasesService.insert(vo);
			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		return result;
	}

	@RequestMapping("/updateCaseInfo")
	@ResponseBody
	public Map<String, Object> updateCaseInfo(CollectionCreateCasesVO vo)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		// edit
		try {

			UserSession user = ApplicationContext.getUser();
			vo.setModifiedTime(new Date());
			vo.setModifier(user.getLoginName());
			vo.setModifierId(user.getId());
			createCasesService.updateCaseInfo(vo);
			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.MANUAL_CRATE_CASE
				.getValue());
		sysLog.setOptType(EnumConstants.OptionType.COLLECTION_SAVE_CASE
				.getValue());
		// sysLog.setRemark("借款ID   "+vo.getLoanId().toString());
		sysLogService.insert(sysLog);
		return result;
	}

	// 案件分派
	@RequestMapping(value = "/casesDispatch/{ids}/{opid}")
	@ResponseBody
	public String ipsDelete(@PathVariable("ids") String[] ids,
			@PathVariable("opid") String opid, HttpServletRequest request,
			ModelMap modelMap) {
		List<Long> longid = new ArrayList<Long>();
		for (String id : ids) {
			longid.add(Long.parseLong(id));

		}
		UserSession user = ApplicationContext.getUser();
		CollectionCreateCasesVO vo = new CollectionCreateCasesVO();
		vo.setIdList(longid);
		vo.setOperatorId(Long.parseLong(opid));
		vo.setModifiedTime(new Date());
		vo.setModifier(user.getLoginName());
		vo.setModifierId(user.getId());
		createCasesService.dispatchCases(vo);
		for (int i = 0; i < ids.length; i++) {
			Long id = Long.parseLong(ids[i]);
			CollectionCasesTask cct = createCasesService.assignmentByLoanId(id);
			/*
			 * if(cct!=null){ CollectionTaskVO cctRequest=new
			 * CollectionTaskVO(); cctRequest.setId(cct.getId());
			 * cctRequest.setTaskState(1); cctRequest.setTaskStartDate(new
			 * Date()); cctRequest.setOperatorId(Long.parseLong(opid));
			 * createCasesService.assignmentCaseInfo(cctRequest);
			 * 
			 * }else{
			 */
			CollectionCreateCases vv = createCasesService.selectLoanId(id);
			RepayEntryDetailsVO rvo = viewEdit(vv.getLoanId());
			CollectionTaskVO tvo = new CollectionTaskVO();
			/*
			 * if(rvo.getFineDay()>30){
			 * 
			 * }else{ tvo.setTaskType(1); }
			 */
			tvo.setTaskType(2);
			tvo.setCaseId(id);
			tvo.setTaskState(1);
			tvo.setTaskStartDate(new Date());
			tvo.setOperatorId(Long.parseLong(opid));

			tvo.setModifiedTime(new Date());
			tvo.setModifier(user.getLoginName());
			tvo.setModifierId(user.getId());
			createCasesService.insertTask(tvo);
			SysLog sysLog = new SysLog();
			sysLog.setOptModule(EnumConstants.OptionModule.COLLECTION_CASE_MANAGE
					.getValue());
			if (ids.length > 1) {
				sysLog.setOptType(EnumConstants.OptionType.COLLECTION_BATCH_ASSIGNMENT
						.getValue());
			} else {
				sysLog.setOptType(EnumConstants.OptionType.COLLECTION_ASSIGNMENT
						.getValue());
			}
			// sysLog.setRemark("借款ID   "+vo.getLoanId().toString());
			sysLogService.insert(sysLog);
			/* } */
		}

		return null;
	}

	// 结案
	@RequestMapping(value = "/casesClosed/{ids}/{closedType}")
	@ResponseBody
	public String casesClosed(@PathVariable("ids") String[] ids,
			@PathVariable("closedType") String closedType,
			HttpServletRequest request, ModelMap modelMap) {
		List<Long> longid = new ArrayList<Long>();
		for (String id : ids) {
			longid.add(Long.parseLong(id));

		}
		UserSession user = ApplicationContext.getUser();
		CollectionCreateCasesVO vo = new CollectionCreateCasesVO();
		vo.setIdList(longid);
		vo.setClosedType(Integer.parseInt(closedType));
		vo.setModifierId(user.getId());
		vo.setModifiedTime(new Date());
		vo.setModifier(user.getLoginName());
		SysLog sysLog = new SysLog();
		if (Integer.parseInt(closedType) == 6
				|| Integer.parseInt(closedType) == 7) {
			vo.setCaseEndDate(new Date());
			CollectionTaskVO tvo = new CollectionTaskVO();
			tvo.setModifierId(user.getId());
			tvo.setModifiedTime(new Date());
			tvo.setModifier(user.getLoginName());
			if (Integer.parseInt(closedType) == 6) {
				tvo.setCaseId(Long.parseLong(ids[0]));
				tvo.setTaskEndDate(new Date());
				tvo.setTaskState(5);

				// createCasesService.updateTask(tvo);
				sysLog.setOptType(EnumConstants.OptionType.COLLECTION_CLOSE_ALL
						.getValue());

			}
			if (Integer.parseInt(closedType) == 7) {
				for (Long id : longid) {
					CollectionCreateCases ccL = createCasesService
							.selectLoanId(id);
					LoanVO lvo = new LoanVO();
					lvo.setId(ccL.getLoanId());
					lvo.setStatus(160);
					loanService.update(lvo);
					sysLog.setOptType(EnumConstants.OptionType.COLLECTION_CLOSE_BAD
							.getValue());
				}
				tvo.setCaseId(Long.parseLong(ids[0]));
				tvo.setTaskEndDate(new Date());
				tvo.setTaskState(4);
				// createCasesService.updateTask(tvo);
			}

		}
		vo.setOperatorId(getManagerOperatorId());
		vo.setCaseEndDate(new Date());
		createCasesService.casesClosed(vo);
		sysLog.setOptModule(EnumConstants.OptionModule.COLLECTION_CASE_MANAGE
				.getValue());
		sysLogService.insert(sysLog);
		return null;
	}

	/***
	 * 
	 * <pre>
	 * 加载催收员
	 * </pre>
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("/getFirstTrials")
	@ResponseBody
	public List<SysUser> getFirstTrials() {
		SysUserVO sysUserVo = new SysUserVO();
		sysUserVo.setUserType(UserType.COLLECTORS.getValue());
		List<SysUser> userList = sysUserService.AssessorList(sysUserVo);
		UserSession user = ApplicationContext.getUser();
		List<SysUser> index = new ArrayList<SysUser>();
		SysParameter overDueManagerCodeSys = sysParameterService
				.getByCode(SysParameterEnum.OVERDUE_RECEIVABLES_MANAGER.name());
		String overdueManagerCode = overDueManagerCodeSys.getParameterValue();
		/*
		 * if(user.getLoginName().equals(overdueManagerCode)||user.getUserType().
		 * equals(10)){ for(int i=0;i<userList.size();i++) { SysUser
		 * s=userList.get(i);
		 * if(s.getLoginName().substring(0,4).equals("test")){ index=i+""; } } }
		 */
		if (user.getLoginName().substring(0, 4).equals("test")) {
			for (int i = 0; i < userList.size(); i++) {
				SysUser s = userList.get(i);
				if (!(s.getLoginName().substring(0, 4).equals("test"))) {
					index.add(s);
				}
			}
		} else {
			if (!(user.getUserType().equals(EnumConstants.UserType.SYSTEM_ADMIN
					.getValue()))) {
				for (int i = 0; i < userList.size(); i++) {
					SysUser s = userList.get(i);
					if (s.getLoginName().substring(0, 4).equals("test")) {
						index.add(s);
					}
				}
			}
		}

		userList.removeAll(index);

		SysUser sysUser = new SysUser();
		sysUser.setId(null);
		sysUser.setName("----请选择分派对象----");
		userList.add(0, sysUser);
		return userList;
	}

	@RequestMapping("/getTrialsCombox")
	@ResponseBody
	public List<SysUser> getTrialsCombox() {
		SysUserVO sysUserVo = new SysUserVO();
		sysUserVo.setUserType(UserType.COLLECTORS.getValue());
		List<SysUser> userList = new ArrayList();
		UserSession user = ApplicationContext.getUser();
		SysUser sysUserTemp = sysUserService.get(user.getId());
		SysParameter overDueManagerCodeSys = sysParameterService
				.getByCode(SysParameterEnum.OVERDUE_RECEIVABLES_MANAGER.name());
		String overdueManagerCode = overDueManagerCodeSys.getParameterValue();
		if (user.getLoginName().equals(overdueManagerCode)
				|| user.getUserType().equals(10)) {
			userList = sysUserService.araeAssessorList(sysUserVo);
		} else if (user.getLoginName().equals("testCollectors1")) {
			userList = sysUserService.araeTestAssessorList(sysUserVo);
		} else if (user.getUserType().equals(
				EnumConstants.UserType.SYSTEM_ADMIN.getValue())) {
			userList = sysUserService.araeAssessorList(sysUserVo);
			List<SysUser> userList2 = sysUserService
					.araeTestAssessorList(sysUserVo);
			userList.addAll(userList2);
		} else if (user.getUserType().equals(2)) {
			// 自己
			SysUser sysUser = new SysUser();
			sysUser.setId(user.getId());
			sysUser.setName(user.getName());
			userList.add(sysUser);
			// 催收员
			SysUserVO sysUserV = new SysUserVO();
			List<Integer> utL = new ArrayList<Integer>();
			utL.add(UserType.COLLECTORS.getValue());
			sysUserV.setUserTypeList(utL);
			List<SysUser> userList1 = sysUserService.findListByVO(sysUserV);
			userList.addAll(userList1);
			// 所有该门店下的客服
			//1.获取用户下的营业网点id(deptIds)
			List<Long> canBrowseSalesDeptList = sysUserService
					.getSalesDeptIdByUserId(user.getId());
			// 如果是刘娜，则重新获取营业网点ID集合
			if (EnumConstants.IsAddOtherDepts.isTrue.getValue().equals(String.valueOf(sysUserTemp.getIsAddOtherDepts()))) {
				BaseAreaVO baseAreaVo = new BaseAreaVO();
				baseAreaVo.setUserId(user.getId());
				baseAreaVo.setIdentifier(BizConstants.CREDIT2_SALESDEPARTMENT);
				canBrowseSalesDeptList = null;
				// 获取所有的营业网点ID
				canBrowseSalesDeptList = areaService
						.getDeptsByUserIdAndDeptsTypes(baseAreaVo);
			}
			SysUserVO sysUserVo2 = new SysUserVO();
			sysUserVo2.setAreaIdList(canBrowseSalesDeptList);
			utL.clear();
			utL.add(UserType.CUSTOMER_SERVICE.getValue());
			sysUserVo2.setUserTypeList(utL);
			List<SysUser> userList2 = sysUserService.findListByVO(sysUserVo2);
			userList.addAll(userList2);
			List<SysUser> index = new ArrayList<SysUser>();
			//将测试的数据过滤
			for (int i = 0; i < userList.size(); i++) {
				SysUser s = userList.get(i);
				if (s.getLoginName() != null) {
					if (s.getLoginName().length() > 4) {
						if ((s.getLoginName().substring(0, 4).equals("test"))) {
							index.add(s);
						}
					}
				}
			}
			userList.removeAll(index);

		} else if (user.getUserType().equals(12)) {
			List<Long> areaId = new ArrayList<Long>();
			areaId = sysUserService.getSalesDeptIdByUserIdAndDeptType(user
					.getId());
			List<Integer> utL = new ArrayList<Integer>();
			SysUserVO sysUserV = new SysUserVO();
			utL.add(UserType.COLLECTORS.getValue());
			sysUserV.setUserTypeList(utL);
			// 催收员
			List<SysUser> userListC = sysUserService.findListByVO(sysUserV);
			// 客服
			SysUserVO sysUserVo2 = new SysUserVO();
			sysUserVo2.setAreaIdList(areaId);
			utL.clear();
			utL.add(UserType.CUSTOMER_SERVICE.getValue());
			sysUserVo2.setUserTypeList(utL);
			List<SysUser> userListK = sysUserService.findListByVO(sysUserVo2);
			// 门店经理
			utL.clear();
			utL.add(UserType.STORE_MANAGER.getValue());
			sysUserVo2.setUserTypeList(utL);
			List<SysUser> userListM = sysUserService.findListByVO(sysUserVo2);
			// 门店副理
			utL.clear();
			utL.add(UserType.STORE_ASSISTANT_MANAGER.getValue());
			sysUserVo2.setUserTypeList(utL);
			List<SysUser> userListF = sysUserService.findListByVO(sysUserVo2);
			userList.addAll(userListC);
			userList.addAll(userListK);
			userList.addAll(userListM);
			userList.addAll(userListF);

			List<SysUser> index = new ArrayList<SysUser>();
			for (int i = 0; i < userList.size(); i++) {
				SysUser s = userList.get(i);
				if (s.getLoginName().length() > 4) {
					if ((s.getLoginName().substring(0, 4).equals("test"))) {
						index.add(s);
					}
				}
			}
			userList.removeAll(index);
		}

		else {
			SysUser sysUser = new SysUser();
			sysUser.setId(user.getId());
			sysUser.setName(user.getName());
			// userList.add(sysUser);
			if (user.getUserType().intValue() == 6) {
				SysUserVO sv = new SysUserVO();
				SysUser u = sysUserService.get(user.getId());
				sv.setAreaId(u.getAreaId());
				List<Integer> userTypeList = new ArrayList<Integer>();
				userTypeList.add(u.getUserType());
				sv.setUserTypeList(userTypeList);
				List<SysUser> ulist = sysUserService.findListByVO(sv);
				userList.addAll(ulist);
			} else {
				userList.add(sysUser);
			}

		}
		SysUser sysUser = new SysUser();
		sysUser.setId(null);
		sysUser.setName("全部");
		userList.add(0, sysUser);
		return userList;
	}

	// 查询联系人借款人额外电话地址信息
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/personContacterList")
	@ResponseBody
	public Map personContacterList(PersonContacterAdditional pca, int rows,
			int page) {
		Pager p = new Pager();
		p.setPage(page);
		p.setRows(rows);
		pca.setPager(p);
		Pager pager = createCasesService.findPersonContactAdditionalnMap(pca);
		List<PersonContacterAdditional> pcaList = pager.getResultList();
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("total", pager.getTotalCount());
		result.put("rows", pcaList);
		return result;
	}

	// 添加联系人借款人额外电话地址信息
	@RequestMapping("/saveAdditional")
	@ResponseBody
	public Map<String, Object> saveAdditional(PersonContacterAdditional pca)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		// edit
		try {
			// 新增待审核
			UserSession user = ApplicationContext.getUser();
			pca.setCreatedTime(new Date());
			pca.setCreator(user.getLoginName());
			pca.setCreatorId(user.getId());

			createCasesService.insertPersonContacterAdditional(pca);
			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		return result;
	}

	@RequestMapping("/seImageUploadView/{loanId}")
	public String seImageUploadView(@PathVariable("loanId") String loanId,
			Model model) {
		model.addAttribute("loanId", loanId);
		Loan loan = loanService.get(Long.parseLong(loanId));
		if (loan != null) {
			Long personId = loan.getPersonId();
			Long productId = Long.valueOf(loan.getProductType());
			model.addAttribute("personId", personId);
			model.addAttribute("productId", productId);
			model.addAttribute("userType", ApplicationContext.getUser()
					.getUserType());
			Person person = personService.get(personId);
			if (person != null) {
				model.addAttribute("personName", person.getName());
			}
		}
		model.addAttribute("optModule",
				EnumConstants.OptionModule.APPLY_LOAN.getValue());
		SysParameter sysParameter = sysParameterService
				.getByCode(SysParameterEnum.VALID_ATTACHMENT.name());
		String fileSizeLimit = "2";
		String fileQueueLimit = "40";
		if (sysParameter == null
				|| StringUtils.isEmpty(sysParameter.getParameterValue())) {
			model.addAttribute("fileSizeLimit", fileSizeLimit);
			model.addAttribute("fileQueueLimit", fileQueueLimit);
		} else {
			String[] attachmentArray = sysParameter.getParameterValue().split(
					";");
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
		return "after/collectionManagerCases/imageUploadView";
	}

	@RequestMapping("/seImageUploadViewManager/{loanId}")
	public String seImageUploadViewManager(
			@PathVariable("loanId") String loanId, Model model) {
		model.addAttribute("loanId", loanId);
		Loan loan = loanService.get(Long.parseLong(loanId));
		if (loan != null) {
			Long personId = loan.getPersonId();
			Long productId = Long.valueOf(loan.getProductType());
			model.addAttribute("personId", personId);
			model.addAttribute("productId", productId);
			model.addAttribute("userType", ApplicationContext.getUser()
					.getUserType());
			Person person = personService.get(personId);
			if (person != null) {
				model.addAttribute("personName", person.getName());
			}
		}
		model.addAttribute("optModule",
				EnumConstants.OptionModule.APPLY_LOAN.getValue());
		SysParameter sysParameter = sysParameterService
				.getByCode(SysParameterEnum.VALID_ATTACHMENT.name());
		String fileSizeLimit = "2";
		String fileQueueLimit = "40";
		if (sysParameter == null
				|| StringUtils.isEmpty(sysParameter.getParameterValue())) {
			model.addAttribute("fileSizeLimit", fileSizeLimit);
			model.addAttribute("fileQueueLimit", fileQueueLimit);
		} else {
			String[] attachmentArray = sysParameter.getParameterValue().split(
					";");
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
		return "after/collectionManagerCases/imageUploadViewManager";
	}

	// delete
	@RequestMapping("/deletePersonContacterAdditional/{id}")
	@ResponseBody
	public Map<String, Object> deletePersonContacterAdditional(
			@PathVariable("id") String id) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		// edit
		try {
			createCasesService.deletePersonContacterAdditional(Long
					.parseLong(id));
			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		/*
		 * SysLog sysLog = new SysLog();
		 * sysLog.setOptModule(EnumConstants.OptionModule
		 * .COLLECTION_TASK_MANAGE.getValue());
		 * sysLog.setOptType(EnumConstants.OptionType
		 * .COLLECTION_DELETE_RECORD.getValue());
		 * //sysLog.setRemark("借款ID   "+vo.getLoanId().toString());
		 * sysLogService.insert(sysLog);
		 */
		return result;
	}

	// 计算逾期信息
	public RepayEntryDetailsVO viewEdit(Long loanId) {
		Date nowDate = DateUtil.getToday();
		Loan loan = loanService.get(loanId);
		Person person;
		if (loan != null) {
			person = personService.get(loan.getPersonId());
		} else {
			Extension extension = extensionService.get(loanId);
			person = personService.get(extension.getPersonId());
		}
		String name = person.getName();
		String idnum = person.getIdnum();
		String mobilePhone = person.getMobilePhone();

		RepayEntryDetailsVO repayEntryDetailsVO = new RepayEntryDetailsVO();
		repayEntryDetailsVO.setLoanId(loanId);
		repayEntryDetailsVO.setPersonName(name);
		repayEntryDetailsVO.setPersonIdnum(idnum);
		repayEntryDetailsVO.setPersonMobilePhone(mobilePhone);
		List<RepaymentPlan> repaymentPlanList = repayService
				.getAllInterestOrLoan(nowDate, loanId);
		BigDecimal accAmount = repayService.getAccAmount(loanId);
		// 设置挂账金额,期末预收
		repayEntryDetailsVO.setAccAmount(accAmount);
		Integer currTerm = repayService.getCurrTerm(repaymentPlanList, nowDate);
		// 判断如果该期期数为0,则从已结清的还款计划表中获取
		if (currTerm.compareTo(0) == 0) {
			List<RepaymentPlan> repaymentPlanSettleList = repayService
					.getAllInterestOrLoanBySettle(nowDate, loanId);
			currTerm = repayService.getCurrTerm(repaymentPlanSettleList,
					nowDate);
			int size = repaymentPlanSettleList.size();
			if (size > 0) {
				Date repayDay = repaymentPlanSettleList.get(size - 1)
						.getRepayDay();
				// 设置当期还款日
				repayEntryDetailsVO.setCurRepayDate(DateUtil
						.defaultFormatDay(repayDay));
			}
		}
		repayEntryDetailsVO.setCurrTerm(currTerm);
		// 设置当前期数
		BigDecimal reliefOfFine = specialRepaymentService.getReliefOfFine(
				nowDate, loanId);
		// 设置减免金额
		repayEntryDetailsVO.setReliefOfFine(reliefOfFine);
		// 设置还款日期
		repayEntryDetailsVO.setNowDate(DateUtil.defaultFormatDay(nowDate));
		BigDecimal fine = repayService.getFine(repaymentPlanList, nowDate);
		// 设置罚息
		repayEntryDetailsVO.setFine(fine);
		BigDecimal overdueAmount = repayService.getOverdueAmount(
				repaymentPlanList, nowDate);
		// 逾期本金
		BigDecimal overduePrincipal = repayService.getOverduePrincipal(
				repaymentPlanList, nowDate);
		// 逾期利息
		BigDecimal overdueInterest = repayService.getOverdueInterest(
				repaymentPlanList, nowDate);
		BigDecimal overdueAllAmount = overdueAmount.add(fine);
		repayEntryDetailsVO
				.setOverduePrincipalInterestSum(new BigDecimal(overduePrincipal
						.doubleValue() + overdueInterest.doubleValue()));
		// 设置逾期应金额
		repayEntryDetailsVO.setOverdueAmount(overdueAmount);

		// BigDecimal repayAllAmount =
		// currRepayAmount.add(overdueAllAmount).subtract(accAmount);
		// 应还总额（包含当期)
		// repayEntryDetailsVO.setRepayAllAmount(repayAllAmount);

		BigDecimal repayAmount = overdueAllAmount;
		// 如果结果小于0,则返回0
		if (repayAmount.compareTo(BigDecimal.ZERO) == -1) {
			repayEntryDetailsVO.setRepayAmount(BigDecimal.ZERO);
		} else {
			// 应还总额（不含当期)
			repayEntryDetailsVO.setRepayAmount(repayAmount);
		}
		if (repaymentPlanList != null && repaymentPlanList.size() > 0) {
			int size = repaymentPlanList.size();
			Integer overdueTermCount = repayService.getOverdueTermCount(
					repaymentPlanList, nowDate);
			// 设置逾期总数
			repayEntryDetailsVO.setOverdueTerm(overdueTermCount);
			// 如果逾期期数大于等于1
			if (overdueTermCount >= 1) {
				// 设置逾期起始日
				repayEntryDetailsVO.setOverdueStartDate(DateUtil
						.defaultFormatDay(repaymentPlanList.get(0)
								.getRepayDay()));
			}
			int overdueFineDay = repayService.getOverdueFineDay(
					repaymentPlanList, nowDate);
			// 设置罚息天数
			repayEntryDetailsVO.setFineDay(overdueFineDay);

			if (overdueFineDay >= 1) {
				// 设置罚息起算日
				repayEntryDetailsVO.setFineDate(DateUtil
						.defaultFormatDay(DateUtil
								.getDateBefore(overdueFineDay)));
			}
			Date repayDay = repaymentPlanList.get(size - 1).getRepayDay();
			// 设置当期还款日
			repayEntryDetailsVO.setCurRepayDate(DateUtil
					.defaultFormatDay(repayDay));

		}
		boolean oneTimeRepayment = specialRepaymentService
				.isOneTimeRepayment(loanId);
		BigDecimal repayAllAmount = BigDecimal.ZERO;
		if (oneTimeRepayment) {
			// 一次性结清
			BigDecimal onetimeRepaymentAmount = repayService
					.getOnetimeRepaymentAmount(repaymentPlanList, nowDate);
			repayEntryDetailsVO
					.setOnetimeRepaymentAmount(onetimeRepaymentAmount);
			repayEntryDetailsVO.setCurrAmountLabel("一次性还款金额");
			repayEntryDetailsVO.setCurrAmount(onetimeRepaymentAmount);
			// 应还总额 =一次性结清 + 逾期应还 - 期末预收
			repayAllAmount = onetimeRepaymentAmount.add(overdueAllAmount);

		} else {
			BigDecimal nextRepayAmount = repayService.getNextRepayAmount(
					repaymentPlanList, nowDate);
			repayEntryDetailsVO.setCurrAmountLabel("当期应还总额");
			// 判断如果没有逾期的话则取当期还款金额，有逾期金额的话当期还款金额为逾期应还总额
			// if (overdueAllAmount.compareTo(BigDecimal.ZERO) == 0) {
			// repayEntryDetailsVO.setCurrAmount(currRepayAmount);
			// } else {
			// repayEntryDetailsVO.setCurrAmount(overdueAllAmount);
			// }
			repayEntryDetailsVO.setCurrAmount(nextRepayAmount);
			// 应还总额（包含当期） = 当期应还总额+应还总额（不含当期）
			if (repayEntryDetailsVO.getCurrAmount() != null) {
				repayAllAmount = repayAmount.add(repayEntryDetailsVO
						.getCurrAmount());
			}
		}
		// 如果结果小于0,则返回0
		if (repayAllAmount.compareTo(BigDecimal.ZERO) == -1) {
			repayEntryDetailsVO.setRepayAllAmount(BigDecimal.ZERO);
		} else {
			repayEntryDetailsVO.setRepayAllAmount(repayAllAmount);
		}
		return repayEntryDetailsVO;
	}

	public Long getManagerOperatorId() {
		UserSession user = ApplicationContext.getUser();
		CasesPerson cp = null;
		if (user.getLoginName().length() >= 4) {
			if (user.getLoginName().substring(0, 4).equals("test")) {
				// 获取test主管ID
				cp = createCasesService
						.selectCollectionManager("testCollectors1");
			} else {
				cp = createCasesService.selectCollectionManager("00227758");
			}
		} else {
			cp = createCasesService.selectCollectionManager("00227758");
		}
		return cp.getId();
	}

	@RequestMapping(value = "/exportExcel/{ids}")
	@ResponseBody
	public String init(@PathVariable("ids") String[] ids,
			HttpServletRequest request, HttpServletResponse response) {

		try {
			OutputStream os;
			String fileName = "催收案件导出" + getDate() + ".xlsx";
			String downloadPath = credit2Properties.getDownloadPath();
			File file = new File(downloadPath + File.separator + "report"
					+ File.separator + "collectionCases");
			if (!file.exists()) {// 不存在则创建该目录
				file.mkdirs();
			}
			os = new FileOutputStream(downloadPath + File.separator + "report"
					+ File.separator + "collectionCases" + File.separator
					+ fileName.trim().toString());
			createCasesService.excelExport(ids, os);
			String filePath = downloadPath + File.separator + "report"
					+ File.separator + "collectionCases" + File.separator
					+ fileName;
			// 下载Excel文件到客户端
			if (FileUtil.downLoadFile(filePath, response, fileName, "xlsx")) {
				// 导出成功后删除导出的文件
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	public static String getDate() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");// 设置日期格式
		return df.format(new Date());// new Date()为获取当前系统时间
	}
}
