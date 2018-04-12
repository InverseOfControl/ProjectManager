package com.ezendai.credit2.after.controller;

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

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.log4j.chainsaw.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezendai.credit2.after.service.RepayService;
import com.ezendai.credit2.after.service.SpecialRepaymentService;
import com.ezendai.credit2.after.vo.RepayEntryDetailsVO;
import com.ezendai.credit2.apply.model.ChannelPlan;
import com.ezendai.credit2.apply.model.ChannelPlanCheck;
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
import com.ezendai.credit2.apply.vo.ChannelPlanCheckVO;
import com.ezendai.credit2.apply.vo.ChannelPlanVO;
import com.ezendai.credit2.apply.vo.CompanyVO;
import com.ezendai.credit2.apply.vo.ContacterVO;
import com.ezendai.credit2.apply.vo.PersonVO;
import com.ezendai.credit2.after.model.CasesPerson;
import com.ezendai.credit2.after.model.CollectionCasesRecord;
import com.ezendai.credit2.after.model.CollectionCasesTask;
import com.ezendai.credit2.after.model.CollectionCreateCases;
import com.ezendai.credit2.audit.model.FirstApprove;
import com.ezendai.credit2.audit.model.RepaymentPlan;
import com.ezendai.credit2.after.service.CollectionCreateCasesService;
import com.ezendai.credit2.audit.service.FirstApproveService;
import com.ezendai.credit2.after.vo.CollectionCreateCasesVO;
import com.ezendai.credit2.after.vo.CollectionTaskVO;
import com.ezendai.credit2.audit.vo.FirstApproveVO;
import com.ezendai.credit2.after.vo.OverdueReceivablesCaseVO;
import com.ezendai.credit2.audit.vo.PersonCompanyVO;
import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.framework.model.UserSession;
import com.ezendai.credit2.framework.util.BeanUtil;
import com.ezendai.credit2.framework.util.DateUtil;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.framework.util.StringUtil;
import com.ezendai.credit2.master.constant.BizConstants;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.master.enumerate.EnumConstants.UserType;
import com.ezendai.credit2.master.model.BaseArea;
import com.ezendai.credit2.master.service.AreaService;
import com.ezendai.credit2.master.service.BaseAreaService;
import com.ezendai.credit2.master.vo.BaseAreaVO;
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
@RequestMapping("/collectionTask/Main")
public class CollectionTaskController extends BaseController {
	/**
	 *区域service
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
	private static final Logger logger = Logger
			.getLogger(CollectionTaskController.class);

	@RequestMapping("/init")
	public String init(HttpServletRequest request, ModelMap modelMap) {
		/* 设置数据字典 */
		setDataDictionaryArr(new String[] { EnumConstants.LOAN_STATUS,
				EnumConstants.PRODUCT_TYPE, EnumConstants.CONTRACT_SRC });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		UserSession user = ApplicationContext.getUser();
		return "after/collectionTask/collectionManagerCasesMain";
		/* return "/finance/financialAudit/financialAuditList"; */
	}

	@RequestMapping("/casesManager")
	public String casesManager(HttpServletRequest request, ModelMap modelMap) {
		/* 设置数据字典 */
		Long id = Long.parseLong(request.getParameter("casesId"));
		Long loanId = Long.parseLong(request.getParameter("loanId"));
		Long tid = Long.parseLong(request.getParameter("tid"));
		setDataDictionaryArr(new String[] { EnumConstants.LOAN_STATUS,
				EnumConstants.PRODUCT_TYPE, EnumConstants.CONTRACT_SRC });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		UserSession user = ApplicationContext.getUser();
		CollectionCreateCases cc = createCasesService
				.selectLoanInfoByCaseId(id);
		CollectionCreateCases info = createCasesService.selectCaseInfo(id);
		CollectionCasesTask task = createCasesService.seleTaskById(tid);
		Product p = productService.get(Long.valueOf((info.getProductType())));
		info.setProductName(p.getProductName());
		RepayEntryDetailsVO rd = viewEdit(loanId);
		UserSession u = ApplicationContext.getUser();
		SysUser su = sysUserService.get(task.getOperaorId());
		// 判断登录人是否对这笔作业有操作权限
		String optFlag = "0";

		CollectionCasesTask tasko = createCasesService.assignmentByLoanId(id);
		/*
		 * 只有进行中的任务可以进行操作
		 */if (tasko.getTaskState().equals(1)) {
			 //只有业务主任、客服、审核人员、催收员有操作权限
			if (u.getUserType() == 4 || u.getUserType() == 7
					|| u.getUserType() == 11 || u.getUserType() == 6) {
				if (tasko.getOperaorId().equals(u.getId())) {
					optFlag = "1";
				} else {
					if (u.getUserType() == 6) {
						SysUser userOper = sysUserService.get(u.getId());
						if (userOper.getAreaId().intValue() == su.getAreaId()
								.intValue()
								&& su.getUserType().intValue() == userOper
										.getUserType().intValue()) {
							optFlag = "1";
						}
					}
					if (su.getLoginName().substring(0, 4).equals("test")) {
						if (u.getLoginName().equals("testCollectors1")) {
							optFlag = "1";
						}
					} else {
						SysParameter overDueManagerCodeSys = sysParameterService
								.getByCode(SysParameterEnum.OVERDUE_RECEIVABLES_MANAGER
										.name());
						String overdueManagerCode = overDueManagerCodeSys
								.getParameterValue();
						if (u.getLoginName().equals(overdueManagerCode)) {
							optFlag = "1";
						}
					}
				}
			}
		}

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
		modelMap.put("optFlag", optFlag);
		modelMap.put("info", info);
		modelMap.put("rd", rd);
		modelMap.put("tid", tid);
		modelMap.put("tid", tid);
		modelMap.put("userType", u.getUserType());
		modelMap.put("task", task);
		return "after/collectionTask/casesManager";
		/* return "/finance/financialAudit/financialAuditList"; */
	}

	// 进入外访电核记录页面
	@RequestMapping("/casesRecordUI/{taskId}")
	public String casesRecordUI(@PathVariable("taskId") String taskId,
			HttpServletRequest request, ModelMap modelMap) {
		/* 设置数据字典 */
		Long id = Long.parseLong(taskId);
		String personId = request.getParameter("personId");
		String loanId = request.getParameter("loanId");
		setDataDictionaryArr(new String[] { EnumConstants.LOAN_STATUS,
				EnumConstants.PRODUCT_TYPE, EnumConstants.CONTRACT_SRC });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		UserSession user = ApplicationContext.getUser();
		CollectionCasesTask task = createCasesService.selectTaskById(id);
		modelMap.put("taskId", id);
		modelMap.put("personId", personId);
		modelMap.put("loanId", loanId);
		modelMap.put("task", task);
		return "after/collectionTask/casesRecordUI";
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
			return "after/collectionTask/contactPerson";
		} else {
			CasesPerson cpmap = createCasesService
					.selectCollectionContacerById(Long.parseLong(cid));
			String personId = request.getParameter("personId");
			p = personService.get(Long.parseLong(personId));
			modelMap.put("person", p);
			modelMap.put("cp", cpmap);
			return "after/collectionTask/contactPersonUpdateUI";
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
		sysLog.setOptModule(EnumConstants.OptionModule.COLLECTION_TASK_MANAGE
				.getValue());
		sysLog.setOptType(EnumConstants.OptionType.COLLECTION_TASK_EDIT_CONTACTS
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

	/*
	 * @RequestMapping("/updatePerson")
	 * 
	 * @ResponseBody public String updatePerson( PersonCompanyVO
	 * vo,HttpServletRequest request, ModelMap modelMap) { PersonVO pcvo=new
	 * PersonVO(); BeanUtil.copyProperties(pcvo, vo); CompanyVO cvo=new
	 * CompanyVO(); cvo.setId(vo.getCompanyId());
	 * cvo.setName(vo.getCompanyName()); cvo.setPhone(vo.getCompanyPhone());
	 * personService.update(pcvo); personCompanyService.update(cvo); SysLog
	 * sysLog = new SysLog();
	 * sysLog.setOptModule(EnumConstants.OptionModule.COLLECTION_TASK_MANAGE
	 * .getValue());
	 * sysLog.setOptType(EnumConstants.OptionType.COLLECTION_TASK_EDIT_CONTACTS
	 * .getValue()); // sysLog.setRemark("借款ID   "+vo.getLoanId().toString());
	 * sysLogService.insert(sysLog); return ""; }
	 */

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/collectionList")
	@ResponseBody
	public Map collectionList(CollectionCreateCasesVO vo, int rows, int page) {
		Pager p = new Pager();
		p.setPage(page);
		p.setRows(rows);
		vo.setPager(p);
		Pager pager = createCasesService.findManagerCasesWithPG(vo);
		List<CollectionCreateCases> casesList = pager.getResultList();
		for (CollectionCreateCases cc : casesList) {
			RepayEntryDetailsVO rvo = viewEdit(cc.getLoanId());
			cc.setOverdueStartDate(rvo.getOverdueStartDate());
			cc.setOverduePrincipalInterestSum(rvo
					.getOverduePrincipalInterestSum());
			cc.setRepayAllAmount(rvo.getRepayAllAmount());
			cc.setFine(rvo.getFine());
		}

		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("total", pager.getTotalCount());
		result.put("rows", casesList);
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/collectionTaskManagerList")
	@ResponseBody
	public Map collectionTaskManagerList(CollectionCreateCasesVO vo, int rows,
			int page) {
		Pager p = new Pager();
		p.setPage(page);
		p.setRows(rows);
		if (vo.getSort() != null) {
			if (vo.getSort().equals("overdueStartDate")) {
				vo.setSort("overdue_start_date");
			} else if (vo.getSort().equals("deptName")) {
				vo.setSort("dept_name");
				vo.setPinyin("paixu");
			} else if (vo.getSort().equals("productName")) {
				vo.setSort("product_name");
				vo.setPinyin("paixu");
			}
		}
		p.setSidx(vo.getSort());
		p.setSort(vo.getOrder());
		vo.setPager(p);
		UserSession user = ApplicationContext.getUser();
		SysParameter overDueManagerCodeSys = sysParameterService
				.getByCode(SysParameterEnum.OVERDUE_RECEIVABLES_MANAGER.name());
		String overdueManagerCode = overDueManagerCodeSys.getParameterValue();
		// operatorIdList
		List<Product> products = productService.findProductTypeByUserId(user
				.getId());
		SysUser sysUser = sysUserService.get(user.getId());
		if (sysUser.getDataPermission().length() > 2) {
			// 根据用户ID获取营业网点的ID
			List<Long> canBrowseSalesDeptList = sysUserService
					.getSalesDeptIdByUserId(user.getId());
			/**
			 * 根据areaId和userType来确定用户是否是某一个经理下的客服如：
			         如果是上海事业部的客服（刘娜下的客服人员），则可以查看的营业部网点为上海、沈阳、青岛三个营业网点
			 */
			//查询出这个经理的相关信息
			SysUserVO sysUserVo = new SysUserVO();
			sysUserVo.setAreaId(sysUser.getAreaId());
			sysUserVo.setUserType(2);
			List<SysUser> sysManager = sysUserService.findListByVO(sysUserVo);
			
			Long userId = user.getId();
			boolean flag = false;
			if(sysManager !=null && sysManager.size()>0){
				//到userbaseArea表中查询这个经理是否有操作其他营业网点的权限
				BaseAreaVO baseAreaVo = new BaseAreaVO();
				baseAreaVo.setUserId(sysManager.get(0).getId());
				baseAreaVo.setIdentifier(BizConstants.CREDIT2_SALESDEPARTMENT);
				List<Long> tempList = null;
				//如果结果有值，证明这个经理有操作其他网点的权限，则将useId置成这个经理的userid
				tempList = areaService
						.getDeptsByUserIdAndDeptsTypes(baseAreaVo);
				if(tempList !=null &&tempList.size()>0){
					flag = true;
					//重置ID
					userId = sysManager.get(0).getId();
				}
			}
			
			
			// 如果是刘娜，则重新获取营业网点ID集合
			// isAddOtherDepts 用于判断该用户是否拥有操作其他营业网点的权限
			if(EnumConstants.IsAddOtherDepts.isTrue.getValue().equals(String.valueOf(sysUser.getIsAddOtherDepts()))||flag){
				BaseAreaVO baseAreaVo = new BaseAreaVO();
				baseAreaVo.setUserId(userId);
				baseAreaVo.setIdentifier(BizConstants.CREDIT2_SALESDEPARTMENT);
				canBrowseSalesDeptList = null;
				// 获取所有的营业网点ID
				canBrowseSalesDeptList = areaService
						.getDeptsByUserIdAndDeptsTypes(baseAreaVo);
			}
				vo.setSalesDeptIdList(canBrowseSalesDeptList);
				if (user.getUserType().equals(12)) {
					List<Long> areaId = new ArrayList<Long>();
					areaId = sysUserService
							.getSalesDeptIdByUserIdAndDeptType(user.getId());
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
		List<SysUser> userList = new ArrayList();
		if (vo.getOperatorId() == null) {
			List<Long> ll = new ArrayList<Long>();
			if (user.getLoginName().equals(overdueManagerCode)
					|| user.getUserType().equals(10)
					|| user.getUserType().equals(2)) {
				SysUserVO sysUserVo = new SysUserVO();
				sysUserVo.setUserType(UserType.COLLECTORS.getValue());
				userList = sysUserService.araeAssessorList(sysUserVo);
				for (SysUser su : userList) {
					ll.add(su.getId());
				}
				// vo.setOperatorId(user.getId());
			} else if (user.getLoginName().equals("testCollectors1")) {
				SysUserVO sysUserVo = new SysUserVO();
				sysUserVo.setUserType(UserType.COLLECTORS.getValue());
				userList = sysUserService.araeTestAssessorList(sysUserVo);
				for (SysUser su : userList) {
					ll.add(su.getId());
				}
			} else if (user.getUserType().equals(
					EnumConstants.UserType.SYSTEM_ADMIN.getValue())) {
				SysUserVO sysUserVo = new SysUserVO();
				sysUserVo.setUserType(UserType.COLLECTORS.getValue());
				userList = sysUserService.araeAssessorList(sysUserVo);
				for (SysUser su : userList) {
					ll.add(su.getId());
				}
				List<SysUser> userList2 = sysUserService
						.araeTestAssessorList(sysUserVo);
				for (SysUser su : userList2) {
					ll.add(su.getId());
				}
			} else {

				ll.add(user.getId());
				if (user.getUserType().intValue() == 6) {
					SysUserVO sv = new SysUserVO();

					SysUser u = sysUserService.get(user.getId());
					sv.setAreaId(u.getAreaId());
					List<Integer> userTypeList = new ArrayList<Integer>();
					userTypeList.add(u.getUserType());
					sv.setUserTypeList(userTypeList);
					List<SysUser> ulist = sysUserService.findListByVO(sv);
					for (SysUser su : ulist) {
						ll.add(su.getId());
					}
				}
			}
			vo.setOperatorIdList(ll);
		}

		Pager pager = createCasesService.findManagerTaskWithPG(vo);
		List<CollectionCreateCases> casesList = pager.getResultList();
		for (CollectionCreateCases cc : casesList) {
			RepayEntryDetailsVO rvo = viewEdit(cc.getLoanId());
			cc.setOverdueStartDate(rvo.getOverdueStartDate());
			cc.setOverduePrincipalInterestSum(rvo
					.getOverduePrincipalInterestSum());
			if (rvo.getFineDay() > 0) {
				cc.setRepayAllAmount(rvo.getRepayAmount());
			} else {
				cc.setRepayAllAmount(rvo.getRepayAllAmount());
			}

			cc.setFine(rvo.getFine());
			if (cc.getStatus() == 140) {

				cc.setRepayAllAmount(rvo.getRepayAmount());

			}
			if (cc.getStatus() == 130) {
				cc.setOverdueStartDate(null);
				cc.setRepayAllAmount(null);
				cc.setOverduePrincipalInterestSum(null);
				cc.setFine(null);
			}
			if (cc.getStatus() == 150) {
				cc.setOverdueStartDate(null);
				cc.setRepayAllAmount(null);
				cc.setOverduePrincipalInterestSum(null);
				cc.setFine(null);
			}
			if (cc.getCaseState().equals("5")) {
				cc.setOverdueStartDate(null);
				cc.setRepayAllAmount(null);
				cc.setOverduePrincipalInterestSum(null);
				cc.setFine(null);
			}
			if (cc.getCaseState().equals("6")) {
				cc.setOverdueStartDate(null);
				cc.setRepayAllAmount(null);
				cc.setOverduePrincipalInterestSum(null);
				cc.setFine(null);
			}
			if (cc.getCaseState().equals("7")) {
				cc.setOverdueStartDate(null);
				cc.setRepayAllAmount(null);
				cc.setOverduePrincipalInterestSum(null);
				cc.setFine(null);
			}
		}

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
		// 总集合 zhuList
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
			createCasesService.updateCaseInfo(vo);
			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
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
		CollectionCreateCasesVO vo = new CollectionCreateCasesVO();
		vo.setIdList(longid);
		vo.setOperatorId(Long.parseLong(opid));
		createCasesService.dispatchCases(vo);
		return null;
	}

	// 结案
	@RequestMapping(value = "/casesClosed/{ids}/{closedType}")
	@ResponseBody
	public String casesClosed(@PathVariable("ids") String[] ids,
			@PathVariable("closedType") String closedType,
			HttpServletRequest request, ModelMap modelMap) {
		List<Long> longid = new ArrayList<Long>();
		String tid = request.getParameter("tid");
		for (String id : ids) {
			longid.add(Long.parseLong(id));

		}
		UserSession user = ApplicationContext.getUser();
		CollectionCreateCasesVO vo = new CollectionCreateCasesVO();
		vo.setIdList(longid);
		vo.setClosedType(Integer.parseInt(closedType));
		CollectionTaskVO tvo = new CollectionTaskVO();
		vo.setModifierId(user.getId());
		vo.setModifiedTime(new Date());
		vo.setModifier(user.getLoginName());
		tvo.setModifierId(user.getId());
		tvo.setModifiedTime(new Date());
		tvo.setModifier(user.getLoginName());
		SysLog sysLog = new SysLog();
		if (closedType.equals("6")) {
			tvo.setId(Long.parseLong(tid));
			// tvo.setCaseId(Long.parseLong(ids[0]));
			tvo.setTaskEndDate(new Date());
			tvo.setTaskState(5);
			createCasesService.updateTask(tvo);
			vo.setCaseEndDate(new Date());
			sysLog.setOptType(EnumConstants.OptionType.COLLECTION_SUBMIT_CLOSE_ALL
					.getValue());
		}
		if (closedType.equals("2")) {
			tvo.setId(Long.parseLong(tid));
			// tvo.setCaseId(Long.parseLong(ids[0]));
			CollectionCreateCasesVO cvo = new CollectionCreateCasesVO();
			// tvo.setOperatorId(getManagerOperatorId());
			// tvo.setTaskType(2);
			tvo.setTaskState(3);
			tvo.setTaskEndDate(new Date());
			createCasesService.updateTask(tvo);
			cvo.setId(Long.parseLong(ids[0]));
			cvo.setCaseFrom("2");
			cvo.setCaseState("2");
			cvo.setTransferDate(new Date());
			cvo.setModifierId(user.getId());
			cvo.setModifiedTime(new Date());
			cvo.setModifier(user.getLoginName());
			createCasesService.updateCaseInfo(cvo);
			// 查询催收主管
			vo.setOperatorId(getManagerOperatorId());
			sysLog.setOptType(EnumConstants.OptionType.COLLECTION_SUBMIT_ABNORMAL
					.getValue());
		}
		if (closedType.equals("5")) {
			tvo.setId(Long.parseLong(tid));
			vo.setOperatorId(getManagerOperatorId());
			// tvo.setOperatorId(getManagerOperatorId());
			// tvo.setCaseId(Long.parseLong(ids[0]));
			tvo.setTaskEndDate(new Date());
			tvo.setTaskState(5);
			createCasesService.updateTask(tvo);
			vo.setOperatorId(getManagerOperatorId());
			sysLog.setOptType(EnumConstants.OptionType.COLLECTION_SUBMIT_TASK_ALL
					.getValue());
		}
		if (closedType.equals("4")) {
			tvo.setId(Long.parseLong(tid));
			// tvo.setCaseId(Long.parseLong(ids[0]));
			tvo.setTaskEndDate(new Date());
			tvo.setTaskState(4);
			createCasesService.updateTask(tvo);

			vo.setOperatorId(getManagerOperatorId());
			sysLog.setOptType(EnumConstants.OptionType.COLLECTION_SUBMIT_TASK_PART
					.getValue());
		}
		// vo.setCaseEndDate(new Date());
		createCasesService.casesClosed(vo);

		sysLog.setOptModule(EnumConstants.OptionModule.COLLECTION_TASK_MANAGE
				.getValue());

		// sysLog.setRemark("借款ID   "+vo.getLoanId().toString());
		sysLogService.insert(sysLog);
		return null;
	}

	// 保存task
	@RequestMapping("/taskSave")
	@ResponseBody
	public Map<String, Object> taskSave(CollectionTaskVO vo) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		// edit
		try {
			// 新增待审核
			UserSession user = ApplicationContext.getUser();
			vo.setModifiedTime(new Date());
			vo.setModifier(user.getLoginName());
			vo.setModifierId(user.getId());
			createCasesService.updateTaskById(vo);
			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.COLLECTION_TASK_MANAGE
				.getValue());
		sysLog.setOptType(EnumConstants.OptionType.COLLECTION_TASK_SAVE
				.getValue());
		// sysLog.setRemark("借款ID   "+vo.getLoanId().toString());
		sysLogService.insert(sysLog);
		return result;
	}

	// delete record
	@RequestMapping("/deleteRecord/{id}")
	@ResponseBody
	public Map<String, Object> deleteRecord(@PathVariable("id") String id)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		// edit
		try {
			createCasesService.deleteRecord(Long.parseLong(id));
			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.COLLECTION_TASK_MANAGE
				.getValue());
		sysLog.setOptType(EnumConstants.OptionType.COLLECTION_DELETE_RECORD
				.getValue());
		// sysLog.setRemark("借款ID   "+vo.getLoanId().toString());
		sysLogService.insert(sysLog);
		return result;
	}

	// 保存record
	@RequestMapping("/saveRecord")
	@ResponseBody
	public Map<String, Object> saveRecord(CollectionCasesRecord cr,
			HttpServletRequest request) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		// edit
		try {
			String type = request.getParameter("type");
			if (type.equals("1")) {
				UserSession user = ApplicationContext.getUser();
				cr.setCreatedTime(new Date());
				cr.setCreator(user.getLoginName());
				cr.setCreatorId(user.getId());
				createCasesService.insertRecord(cr);
				result.put("success", true);
			} else {
				UserSession user = ApplicationContext.getUser();
				cr.setModifiedTime(new Date());
				cr.setModifier(user.getLoginName());
				cr.setModifierId(user.getId());
				createCasesService.updateRecord(cr);
				result.put("success", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.COLLECTION_TASK_MANAGE
				.getValue());
		sysLog.setOptType(EnumConstants.OptionType.COLLECTION_UPDATE_RECORD
				.getValue());
		// sysLog.setRemark("借款ID   "+vo.getLoanId().toString());
		sysLogService.insert(sysLog);
		return result;
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
		List<SysUser> userList = new ArrayList<SysUser>();
		UserSession user = ApplicationContext.getUser();
		SysParameter overDueManagerCodeSys = sysParameterService
				.getByCode(SysParameterEnum.OVERDUE_RECEIVABLES_MANAGER.name());
		String overdueManagerCode = overDueManagerCodeSys.getParameterValue();
		if (user.getUserType().equals(12)) {
			SysUser su = new SysUser();
			su.setId(user.getId());
			su.setName((user.getName()));
			userList.add(su);
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
			// userList.addAll(userListC);
			userList.addAll(userListK);
			// userList.addAll(userListM);
			// userList.addAll(userListF);
			List<SysUser> index = new ArrayList<SysUser>();
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
		} else if (user.getLoginName().equals(overdueManagerCode)
				|| user.getUserType().equals(10)) {
			userList = sysUserService.araeAssessorList(sysUserVo);
		} else {
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
			// userList.addAll(userList1);
			// 所有该门店下的客服
			SysUser su = sysUserService.get(user.getId());
			SysUserVO sysUserVo2 = new SysUserVO();
			List<Long> areaId = new ArrayList<Long>();
			areaId.add(su.getAreaId());
			sysUserVo2.setAreaIdList(areaId);
			utL.clear();
			utL.add(UserType.CUSTOMER_SERVICE.getValue());
			sysUserVo2.setUserTypeList(utL);
			List<SysUser> userList2 = sysUserService.findListByVO(sysUserVo2);
			userList.addAll(userList2);
			List<SysUser> index = new ArrayList<SysUser>();
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
		}
		SysUser sysUser = new SysUser();
		sysUser.setId(null);
		sysUser.setName("----请选择分派对象----");
		userList.add(0, sysUser);
		return userList;
	}

	// 加载联系人各自手机号
	@RequestMapping("/getTelList")
	@ResponseBody
	public List<CasesPerson> getTelList(HttpServletRequest request, ModelMap map) {
		String part = request.getParameter("part");
		String id = request.getParameter("id");
		String subordinate = request.getParameter("subordinate");
		String type = request.getParameter("type");
		CasesPerson cp = new CasesPerson();
		cp.setId(Long.parseLong(id));
		cp.setPart(Integer.parseInt(part));
		cp.setSubordinate(subordinate);
		List<CasesPerson> pl;
		List<CasesPerson> delpl = new ArrayList<CasesPerson>();
		if (part.equals("1")) {
			pl = (List<CasesPerson>) createCasesService.selePersonTel(cp);
		} else {
			pl = (List<CasesPerson>) createCasesService.seleCtcontactsTel(cp);
		}
		for (CasesPerson cpr : pl) {
			if (type.equals("1")) {
				if (cpr == null) {
					delpl.add(cpr);
					continue;
				}
				if (StringUtil.isEmpty(cpr.getMobilePhone())) {
					delpl.add(cpr);
				}
			} else if (type.equals("2")) {
				if (cpr == null) {
					delpl.add(cpr);
					continue;
				}
				if (StringUtil.isEmpty(cpr.getAddress())) {
					delpl.add(cpr);
				}
			}
		}
		pl.removeAll(delpl);
		return pl;
	}

	// 增加 record
	@RequestMapping("/addRecord")
	public String addRecord(HttpServletRequest request, ModelMap map)
			throws Exception {
		/* 设置数据字典 */
		String type = request.getParameter("type");
		CollectionCasesRecord re = new CollectionCasesRecord();
		map.put("type", type);
		map.put("re", re);
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.COLLECTION_TASK_MANAGE
				.getValue());
		sysLog.setOptType(EnumConstants.OptionType.COLLECTION_ADD_RECORD
				.getValue());
		// sysLog.setRemark("借款ID   "+vo.getLoanId().toString());
		sysLogService.insert(sysLog);
		return "after/collectionTask/addRecord";
	}

	// 修改record页面
	@RequestMapping("/updateRecordUI/{rid}")
	public String updateRecordUI(@PathVariable("rid") String rid,
			HttpServletRequest request, ModelMap map) throws Exception {
		/* 设置数据字典 */
		String type = request.getParameter("type");
		CollectionCasesRecord re = createCasesService.selectRecordById(Long
				.parseLong(rid));
		re.getRecordEndDate();
		re.getRecordStartDate();
		if (re.getRecordEndDate() != null) {
			re.setRecordEndDateStr(dateToStr(re.getRecordEndDate()));
		}
		if (re.getRecordStartDate() != null) {
			re.setRecordStartDateStr(dateToStr(re.getRecordStartDate()));
		}

		map.put("type", type);
		map.put("re", re);
		return "after/collectionTask/addRecord";
	}

	// 获取联系人下拉框
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getPersonCombobox")
	@ResponseBody
	public List<CasesPerson> getPersonCombobox(HttpServletRequest request) {
		CollectionCreateCasesVO vo = new CollectionCreateCasesVO();
		Pager p = new Pager();
		p.setPage(1);
		p.setRows(1000);
		vo.setPager(p);
		String loanId = request.getParameter("loanId");
		String personId = request.getParameter("personId");
		vo.setPersonId(Long.parseLong(personId));
		vo.setLoanId(Long.parseLong(loanId));
		Pager pager = createCasesService.findPersonWithPG(vo);
		List<CasesPerson> casesList = pager.getResultList();
		CasesPerson cpall = new CasesPerson();
		cpall.setPager(p);
		cpall.setPersonId(vo.getPersonId());
		Pager pager2 = createCasesService.findCollectionContacerWithPG(cpall);
		List<CasesPerson> deleList = new ArrayList<CasesPerson>();
		List<CasesPerson> allList = pager2.getResultList();
		// 查询出主表的所有联系人

		// 过滤掉所有重复数据
		for (CasesPerson per : casesList) {
			for (CasesPerson person : allList) {
				if (per.getId().equals(person.getRelationId())
						&& per.getPart().equals(person.getPart())) {
					deleList.add(per);
				}
			}
		}

		casesList.removeAll(deleList);
		// 总集合
		allList.addAll(casesList);

		for (CasesPerson cp : allList) {
			cp.setMemo(cp.getId() + "-" + cp.getPart());
		}
		/*
		 * Map<String, Object> result = new LinkedHashMap<String, Object>(); for
		 * (CasesPerson cp: casesList ){ result.put("value",
		 * cp.getId()+"-"+cp.getPart()); result.put("text", cp.getName()); }
		 */
		return allList;
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

		/*
		 * BigDecimal repayAllAmount =
		 * currRepayAmount.add(overdueAllAmount).subtract(accAmount);
		 * //应还总额（包含当期) repayEntryDetailsVO.setRepayAllAmount(repayAllAmount);
		 */

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

	public static String dateToStr(Date date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 小写的mm表示的是分钟
		String str = sdf.format(date);
		return str;
	}
}
