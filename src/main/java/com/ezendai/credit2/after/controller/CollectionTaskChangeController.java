package com.ezendai.credit2.after.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezendai.credit2.after.model.CasesPerson;
import com.ezendai.credit2.after.model.CollectionCreateCases;
import com.ezendai.credit2.after.service.CollectionCreateCasesService;
import com.ezendai.credit2.after.service.RepayService;
import com.ezendai.credit2.after.service.SpecialRepaymentService;
import com.ezendai.credit2.after.vo.CollectionCreateCasesVO;
import com.ezendai.credit2.after.vo.CollectionTaskVO;
import com.ezendai.credit2.after.vo.RepayEntryDetailsVO;
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
import com.ezendai.credit2.audit.model.RepaymentPlan;
import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.framework.model.UserSession;
import com.ezendai.credit2.framework.util.DateUtil;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.constant.BizConstants;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.master.enumerate.EnumConstants.UserType;
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
@RequestMapping("/collectionTaskChange/Main")
public class CollectionTaskChangeController extends BaseController {
	
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
			.getLogger(CollectionTaskChangeController.class);

	@RequestMapping("/init")
	public String init(HttpServletRequest request, ModelMap modelMap) {
		/* 设置数据字典 */
		setDataDictionaryArr(new String[] { EnumConstants.LOAN_STATUS,
				EnumConstants.PRODUCT_TYPE, EnumConstants.CONTRACT_SRC });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		UserSession user = ApplicationContext.getUser();
		if (user.getUserType() == 2) {
			modelMap.put("flag", "1");
		} else {
			modelMap.put("flag", "0");
		}

		return "after/collectionTaskChange/collectionManagerCasesMain";
		/* return "/finance/financialAudit/financialAuditList"; */
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/collectionTaskManagerList")
	@ResponseBody
	public Map collectionTaskManagerList(CollectionCreateCasesVO vo, int rows,
			int page) {
		Pager p = new Pager();
		p.setPage(page);
		p.setRows(rows);
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
			// 如果是刘娜，则重新获取营业网点ID集合
			// isAddOtherDepts 用于判断该用户是否拥有操作其他营业网点的权限
			  if(EnumConstants.IsAddOtherDepts.isTrue.getValue().equals(String.valueOf(sysUser.getIsAddOtherDepts()))){
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
					|| user.getUserType().equals(2)
					|| user.getUserType().equals(12)) {
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
			}
			// vo.setOperatorIdList(ll);
		}
		vo.setCaseState("1");
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
			}/*
			 * if(cc.getCaseState().equals("7")){ cc.setOverdueStartDate(null);
			 * cc.setRepayAllAmount(null);
			 * cc.setOverduePrincipalInterestSum(null); cc.setFine(null); }
			 */
		}

		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("total", pager.getTotalCount());
		result.put("rows", casesList);
		return result;
	}

	// 任务变更
	@RequestMapping(value = "/taskChange/{ids}/{opid}")
	@ResponseBody
	public String ipsDelete(@PathVariable("ids") String[] ids,
			@PathVariable("opid") String opid, HttpServletRequest request,
			ModelMap modelMap) {
		UserSession user = ApplicationContext.getUser();
		CollectionCreateCasesVO vo = new CollectionCreateCasesVO();
		CollectionTaskVO tvo = new CollectionTaskVO();
		vo.setOperatorId(Long.parseLong(opid));
		vo.setModifiedTime(new Date());
		vo.setModifier(user.getLoginName());
		vo.setModifierId(user.getId());
		tvo.setOperatorId(Long.parseLong(opid));
		tvo.setModifiedTime(new Date());
		tvo.setModifier(user.getLoginName());
		tvo.setModifierId(user.getId());
		for (int i = 0; i < ids.length; i++) {
			String id = ids[i];
			String[] idArr = id.split("-");
			Long cid = Long.parseLong(idArr[0]);
			Long tid = Long.parseLong(idArr[1]);
			vo.setId(cid);
			tvo.setId(tid);
			createCasesService.casesChange(vo);
			createCasesService.taskChange(tvo);
		}

		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.COLLECTION_TASK_CHANGE
				.getValue());
		sysLog.setOptType(EnumConstants.OptionType.COLLECTION_TASK_CHANGE
				.getValue());
		// sysLog.setRemark("借款ID   "+vo.getLoanId().toString());
		sysLogService.insert(sysLog);
		return null;
	}

	@RequestMapping("/getFirstTrials")
	@ResponseBody
	public List<SysUser> getFirstTrials() {
		SysUserVO sysUserVo = new SysUserVO();
		sysUserVo.setUserType(UserType.COLLECTORS.getValue());
		List<SysUser> userList = sysUserService.AssessorList(sysUserVo);
		UserSession user = ApplicationContext.getUser();
		if (user.getLoginName().substring(0, 4).equals("test")) {
			userList = sysUserService.araeTestCustomerServiceList(sysUserVo);
		} else {
			userList = sysUserService.araeCustomerServiceList(sysUserVo);
		}
		SysUser sysUser = new SysUser();
		sysUser.setId(null);
		sysUser.setName("--请选择分派对象--");
		userList.add(0, sysUser);
		return userList;
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
