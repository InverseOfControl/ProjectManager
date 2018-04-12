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

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezendai.credit2.after.service.RepayService;
import com.ezendai.credit2.after.service.SpecialRepaymentService;
import com.ezendai.credit2.after.view.RepaymentInAdvance;
import com.ezendai.credit2.apply.model.Extension;
import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.model.Product;
import com.ezendai.credit2.apply.service.ExtensionService;
import com.ezendai.credit2.apply.service.LoanExtensionService;
import com.ezendai.credit2.apply.service.LoanService;
import com.ezendai.credit2.apply.service.ProductService;
import com.ezendai.credit2.apply.vo.LoanVO;
import com.ezendai.credit2.audit.model.RepaymentPlan;
import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.framework.util.DateUtil;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.constant.BizConstants;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.master.service.AreaService;
import com.ezendai.credit2.master.vo.BaseAreaVO;
import com.ezendai.credit2.system.controller.BaseController;
import com.ezendai.credit2.system.model.SysParameter;
import com.ezendai.credit2.system.model.SysUser;
import com.ezendai.credit2.system.service.SysParameterService;
import com.ezendai.credit2.system.service.SysUserService;

/**
 * <pre>
 * 提前扣款&一次性结清
 * </pre>
 * @author zhangshihai
 * @Description:
 */
@Controller
@RequestMapping("/after/repayInAdvance")
public class RepayInAdvanceController extends BaseController {
	
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
	private LoanService loanService;
	@Autowired
	private ExtensionService extensionService;
	@Autowired
	private SpecialRepaymentService specialRepaymentService;
	@Autowired
	private RepayService repayService;
	@Autowired
	private LoanExtensionService loanExtensionService;
	@Autowired
	private SysParameterService sysParameterService;

	@RequestMapping("/repayInAdvanceMain")
	public String repayInAdvanceList(HttpServletRequest request) {
		setDataDictionaryArr(new String[] { EnumConstants.PRODUCT_ID, EnumConstants.PRODUCT_SUB_TYPE, EnumConstants.LOAN_STATUS });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		return "after/repayInAdvance/repayInAdvance";
	}

	/**
	 * 
	 * <pre>
	 * 获取该用户所在营业部所有客户经理
	 * </pre>
	 *
	 * @return
	 */
	@RequestMapping("/getCrmList")
	@ResponseBody
	public List<SysUser> getCrmList() {
		SysUser all = new SysUser();
		all.setId(0L);
		all.setName("全部");
		Long userId = ApplicationContext.getUser().getId();
		SysUser sysUser = sysUserService.get(userId);
		// 获取该门店所有客户经理
		List<SysUser> sysUserLists = sysUserService.getAllCrmsInSalesDept(sysUser.getDataPermission());
		// 客户经理集合
		List<SysUser> crmList = new ArrayList<SysUser>();
		// 将"全部"放入集合
		crmList.add(all);
		// 将客户经理放入集合
		crmList.addAll(1, sysUserLists);

		return crmList;
	}

	/**
	 * 
	 * <pre>
	 * 获取提前还款列表
	 * </pre>
	 *
	 * @param loanVO
	 * @param rows
	 * @param page
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getRepayInAdvancePage")
	@ResponseBody
	public Map<String, Object> getRepayInAdvancePage(LoanVO loanVO, int rows, int page) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		List<RepaymentInAdvance> repaymentInAdvanceList = new ArrayList<RepaymentInAdvance>();
		BaseAreaVO baseAreaVo = new BaseAreaVO();
		if (StringUtils.isNotEmpty(loanVO.getPersonFuzzyName()) || StringUtils.isNotEmpty(loanVO.getPersonIdnum())
				|| StringUtils.isNotEmpty(loanVO.getPersonMobilePhone())
				||(loanVO.getRepayDay()!=null&&loanVO.getRepayDay()!="")
				||(loanVO.getCityId()!=null&&loanVO.getCityId().toString()!="")) {
			Long userId = ApplicationContext.getUser().getId();
			Integer userType = ApplicationContext.getUser().getUserType();
			SysUser sysUser = sysUserService.get(userId);
			if (userType.compareTo(EnumConstants.UserType.CUSTOMER_SERVICE.getValue()) == 0) {
				// 当前用户只能看到自己作为管理客服的单子
//				loanVO.setManagerId(userId);
			}
			List<Integer> statusList = new ArrayList<Integer>();
			statusList.add(EnumConstants.LoanStatus.正常.getValue());
			statusList.add(EnumConstants.LoanStatus.逾期.getValue());
			statusList.add(EnumConstants.LoanStatus.坏账.getValue());
			loanVO.setStatusList(statusList);
			// 当前用户营业网点判断
			if ("admin".equals(ApplicationContext.getUser().getLoginName())||
					ApplicationContext.getUser().getUserType().compareTo(EnumConstants.UserType.AUDIT.getValue())==0){
				loanVO.setSalesDeptId(null);
			} else {
				
				List<Long> canBrowseSalesDeptList = sysUserService.getSalesDeptIdByUserId(userId);
				//如果是刘娜，则重新获取营业网点ID集合
				// isAddOtherDepts 用于判断该用户是否拥有操作其他营业网点的权限
				  if(EnumConstants.IsAddOtherDepts.isTrue.getValue().equals(String.valueOf(sysUser.getIsAddOtherDepts()))){
					baseAreaVo.setUserId(userId);
					baseAreaVo.setIdentifier(BizConstants.CREDIT2_SALESDEPARTMENT);
					canBrowseSalesDeptList = null;
					//获取所有的营业网点ID
					canBrowseSalesDeptList = areaService.getDeptsByUserIdAndDeptsTypes(baseAreaVo);
				}
				loanVO.setSalesDeptIdList(canBrowseSalesDeptList);
			}
			// 判断借款类型
			if ("admin".equals(ApplicationContext.getUser().getLoginName())) {
				loanVO.setProductId(null);
			} else {
				List<Product> productList = productService.findProductTypeByUserId(userId);
				Integer productType = productList.get(0).getProductType();
				loanVO.setProductType(productType);
			}

			// 客户经理选择"全部"
			if (loanVO.getCrmId() != null && loanVO.getCrmId().compareTo(0L) == 0) {
				loanVO.setCrmId(null);
			}

			Pager p = new Pager();
			p.setRows(rows);
			p.setPage(page);
			p.setSidx("AUDIT_DATE");
			p.setSort("ASC");
			loanVO.setPager(p);

			Pager loanPager = loanService.specialRepaymentFindWithPG(loanVO);
			List<Loan> loanList = loanPager.getResultList();
			RepaymentInAdvance repaymentInAdvance;
			for (Loan loan : loanList) {
				repaymentInAdvance = new RepaymentInAdvance();
				repaymentInAdvance.setLoanId(loan.getId());
				if (loan.getPerson() != null) {
					repaymentInAdvance.setName(loan.getPerson().getName());
					repaymentInAdvance.setIdNum(loan.getPerson().getIdnum());
				}
				repaymentInAdvance.setProductId(loan.getProductId());
				repaymentInAdvance.setProductType(loan.getProductType().longValue());
				repaymentInAdvance.setProductSubType(loan.getLoanType());
				repaymentInAdvance.setPactMoney(loan.getPactMoney());
				repaymentInAdvance.setTime(loan.getTime());
				repaymentInAdvance.setStatus(loan.getStatus());
				repaymentInAdvance.setExtensionTime(loan.getExtensionTime());
				setExtRepaymentInAdvance(loan.getId(), repaymentInAdvance);
				// 如果该笔借款申请了"提前扣款"
				if (specialRepaymentService.isInAdvanceRepayment(loan.getId())) {
					if (currUserIsProposer(loan.getId(), EnumConstants.SpecialRepaymentType.DEDUCT_IN_ADVANCE.getValue())) {
						repaymentInAdvance.setOperations("|提前扣款取消");
					} else {
						repaymentInAdvance.setOperations("|不可操作");
					}
				}
				// 如果该笔借款申请了"一次性结清"
				else if (specialRepaymentService.isOneTimeRepayment(loan.getId())) {
					if (currUserIsProposer(loan.getId(), EnumConstants.SpecialRepaymentType.ONE_TIME_REPAYMENT.getValue())) {
						repaymentInAdvance.setOperations("|一次性结清取消");
					} else {
						repaymentInAdvance.setOperations("|不可操作");
					}
				}
				// 如果该笔借款既没有申请"提前扣款"，也没有申请"一次性结清",页面操作显示"提前扣款  一次性结清"
				else {
					// 展期的话
					if(loan.getExtensionTime().compareTo(0) > 0) {
						Long preExtensionId = loanExtensionService.getPreExtensionId(loan.getId(), loan.getExtensionTime());
						if(!loanService.isCloseOut(preExtensionId)) {
							repaymentInAdvance.setOperations("|不可操作");
						} else {
							repaymentInAdvance.setOperations("|提前扣款|一次性结清");
						}
					} else {
						repaymentInAdvance.setOperations("|提前扣款|一次性结清");
					}
				}
				repaymentInAdvanceList.add(repaymentInAdvance);
			}
			result.put("total", loanPager.getTotalCount());
			result.put("rows", repaymentInAdvanceList);
		} else {
			result.put("total", 0);
			result.put("rows", repaymentInAdvanceList);
		}
		return result;
	}

	/**
	 * <pre>
	 *  设置提前还款需要调用其他接口的一些
	 * </pre>
	 *
	 * @param loanId
	 * @param repaymentInAdvance
	 */
	private void setExtRepaymentInAdvance(Long loanId, RepaymentInAdvance repaymentInAdvance) {
		//设置挂账金额,期末预收
		BigDecimal accAmount = repayService.getAccAmount(loanId);
		repaymentInAdvance.setAccAmount(accAmount);

		Date nowDate = DateUtil.getToday();
		List<RepaymentPlan> repaymentPlanList = repayService.getAllInterestOrLoan(nowDate, loanId);
		if (repaymentPlanList != null && repaymentPlanList.size() > 0) {
			//逾期应还
			BigDecimal overdueAmount = repayService.getOverdueAmount(repaymentPlanList, nowDate);
			repaymentInAdvance.setOverdueAmount(overdueAmount);

			//罚息,逾期违约金
			BigDecimal fine = repayService.getFine(repaymentPlanList, nowDate);
			repaymentInAdvance.setFine(fine);
			//逾期应还总额
			repaymentInAdvance.setOverdueAllAmount(overdueAmount.add(fine));
			//一次性结清
			BigDecimal onetimeRepaymentAmount = repayService.getOnetimeRepaymentAmount(repaymentPlanList, nowDate);
			repaymentInAdvance.setOneTimeRepayment(onetimeRepaymentAmount);
			//下期应还
			BigDecimal nextRepayAmount = repayService.getNextRepayAmount(repaymentPlanList, nowDate);
			repaymentInAdvance.setCurrRepayAmount(nextRepayAmount);
		} else {
			repaymentInAdvance.setOverdueAmount(BigDecimal.ZERO);
			repaymentInAdvance.setFine(BigDecimal.ZERO);
			repaymentInAdvance.setOverdueAllAmount(BigDecimal.ZERO);
			repaymentInAdvance.setOneTimeRepayment(BigDecimal.ZERO);
			repaymentInAdvance.setCurrRepayAmount(BigDecimal.ZERO);
		}
	}
	
	/**
	 * 
	 * <pre>
	 * 判断该loanId指定的loan或extension是否有展期
	 * </pre>
	 *
	 * @param loanId
	 * @return
	 */
	@RequestMapping("/hasExtension")
	@ResponseBody
	public boolean hasExtension(Long loanId) {
		Extension e;
		Loan loan = loanService.get(loanId);
		if(loan != null) {
			e = extensionService.getExtensionByLoanId(loanId);
		} else {
			e = extensionService.getNextExtension(loanId);
		}
		if(e != null) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * <pre>
	 * 提交【提前扣款】
	 * </pre>
	 *
	 * @param loanVO
	 * @return
	 */
	@RequestMapping("/submitRepayInAdvance")
	@ResponseBody
	public String submitRepayInAdvance(Long loanId) {
		return specialRepaymentService.submitRepayInAdvance(loanId);
	}

	/**
	 * 
	 * <pre>
	 * 取消【提前扣款】
	 * </pre>
	 *
	 * @param loanVO
	 * @return
	 */
	@RequestMapping("/cancelRepayInAdvance")
	@ResponseBody
	public String cancelRepayInAdvance(Long loanId) {
		return specialRepaymentService.cancelRepayInAdvance(loanId);
	}

	/**
	 * 
	 * <pre>
	 * 提交【一次性结清】
	 * </pre>
	 *
	 * @param loanVO
	 * @return
	 */
	@RequestMapping("/submitRepayOneTime")
	@ResponseBody
	public String submitRepayOneTime(Long loanId) {
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
		Loan loan = loanService.get(loanId);
		if (loan != null) {
			if(loan.getCreatedTime().after(date)&&loan.getLoanType().equals(EnumConstants.ProductCarType.CIRCULATE.getValue())){
				return specialRepaymentService.submitRepayOneTimeNew(loanId);
			} else {
				return specialRepaymentService.submitRepayOneTime(loanId);
			}
		} else {
			Extension extension = extensionService.get(loanId);
			if (extension != null) {
				if(extension.getCreatedTime().after(date)&&extension.getLoanType().equals(EnumConstants.ProductCarType.CIRCULATE.getValue())){
					return specialRepaymentService.submitRepayOneTimeNew(loanId);
				} else {
					return specialRepaymentService.submitRepayOneTime(loanId);
				}
			}
		}
		return "该笔借款不存在！";
	}

	/**
	 * 
	 * <pre>
	 * 取消【一次性还款】
	 * </pre>
	 *
	 * @param loanVO
	 * @return
	 */
	@RequestMapping("/cancelRepayOneTime")
	@ResponseBody
	public String cancelRepayOneTime(Long loanId) {
		return specialRepaymentService.cancelRepayOneTime(loanId);
	}

	/**
	 * 
	 * <pre>
	 * 判断当前用户是否是该借款特殊申请的申请人或者该申请人所在营业部的经理、副理或者admin或者事业部的
	 * </pre>
	 *
	 * @param loanId
	 * @param type
	 * @return true 是	false 否
	 */
	private boolean currUserIsProposer(Long loanId, Integer type) {
		Integer userType = ApplicationContext.getUser().getUserType();
		// 当前用户是admin
		if (userType.compareTo(EnumConstants.UserType.SYSTEM_ADMIN.getValue()) == 0||userType.compareTo(EnumConstants.UserType.AUDIT.getValue()) == 0) {
			return true;
		}
		// 获取特殊申请的申请人ID
		Long proposerId = specialRepaymentService.getProposerID(loanId, type);
		// 申请人是admin
		if (proposerId != null && proposerId.compareTo(1L) == 0) {
			return false;
		}
		// 获取当前登陆用户ID
		Long userId = ApplicationContext.getUser().getId();
		// 当前用户是该特殊申请的申请人
		if (proposerId != null && proposerId.compareTo(userId) == 0) {
			return true;
		}
		// 当前用户是门店经理或副理
		if (userType.compareTo(EnumConstants.UserType.STORE_MANAGER.getValue()) == 0
				|| userType.compareTo(EnumConstants.UserType.STORE_ASSISTANT_MANAGER.getValue()) == 0) {
			return true;
		}
		return false;
	}
	
	@RequestMapping("/plRepayInAdvanceMain")
	public String plRepayInAdvanceList(HttpServletRequest request) {
		setDataDictionaryArr(new String[] { EnumConstants.PRODUCT_ID, EnumConstants.PRODUCT_SUB_TYPE, EnumConstants.LOAN_STATUS });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		return "after/repayInAdvance/plRepayInAdvance";
	}
	
	/**
	 * 
	 * <pre>
	 * 批量提交【提前扣款】
	 * </pre>
	 *
	 * @param loanVO
	 * @return
	 */
	@RequestMapping("/plSubmitRepayInAdvance")
	@ResponseBody
	public String plSubmitRepayInAdvance(Long loanId) {
		return specialRepaymentService.plSubmitRepayInAdvance(loanId);
	}

	/**
	 * 
	 * <pre>
	 * 批量取消【提前扣款】
	 * </pre>
	 *
	 * @param loanVO
	 * @return
	 */
	@RequestMapping("/plCancelRepayInAdvance")
	@ResponseBody
	public String plCancelRepayInAdvance(Long loanId) {
		return specialRepaymentService.plCancelRepayInAdvance(loanId);
	}

	/**
	 * 
	 * <pre>
	 * 批量提交【一次性结清】
	 * </pre>
	 *
	 * @param loanVO
	 * @return
	 */
	@RequestMapping("/plSubmitRepayOneTime")
	@ResponseBody
	public String plSubmitRepayOneTime(Long loanId) {
		return specialRepaymentService.plSubmitRepayOneTime(loanId);
	}

	/**
	 * 
	 * <pre>
	 * 批量取消【一次性还款】
	 * </pre>
	 *
	 * @param loanVO
	 * @return
	 */
	@RequestMapping("/plCancelRepayOneTime")
	@ResponseBody
	public String plCancelRepayOneTime(Long loanId) {
		return specialRepaymentService.plCancelRepayOneTime(loanId);
	}
	
	/**
	 * 
	 * <pre>
	 * 获取提前还款列表(批量还款页面)
	 * </pre>
	 *
	 * @param loanVO
	 * @param rows
	 * @param page
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getRepayInAdvancePlPage")
	@ResponseBody
	public Map<String, Object> getRepayInAdvancePlPage(LoanVO loanVO, int rows, int page) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		List<RepaymentInAdvance> repaymentInAdvanceList = new ArrayList<RepaymentInAdvance>();
		BaseAreaVO baseAreaVo = new BaseAreaVO();
		if ((loanVO.getRepayDay()!=null&&loanVO.getRepayDay()!="")
				||(loanVO.getCityId()!=null&&loanVO.getCityId().toString()!="")) {
			Long userId = ApplicationContext.getUser().getId();
			Integer userType = ApplicationContext.getUser().getUserType();
			SysUser sysUser = sysUserService.get(userId);
			if (userType.compareTo(EnumConstants.UserType.CUSTOMER_SERVICE.getValue()) == 0) {
				// 当前用户只能看到自己作为管理客服的单子
//				loanVO.setManagerId(userId);
			}
			List<Integer> statusList = new ArrayList<Integer>();
			statusList.add(EnumConstants.LoanStatus.正常.getValue());
			statusList.add(EnumConstants.LoanStatus.逾期.getValue());
			statusList.add(EnumConstants.LoanStatus.坏账.getValue());
			loanVO.setStatusList(statusList);
			// 当前用户营业网点判断
			if ("admin".equals(ApplicationContext.getUser().getLoginName())||
					ApplicationContext.getUser().getUserType().compareTo(EnumConstants.UserType.AUDIT.getValue())==0){
				loanVO.setSalesDeptId(null);
			} else {
				
				List<Long> canBrowseSalesDeptList = sysUserService.getSalesDeptIdByUserId(userId);
				//如果是刘娜，则重新获取营业网点ID集合
				// isAddOtherDepts 用于判断该用户是否拥有操作其他营业网点的权限
				  if(EnumConstants.IsAddOtherDepts.isTrue.getValue().equals(String.valueOf(sysUser.getIsAddOtherDepts()))){
					baseAreaVo.setUserId(userId);
					baseAreaVo.setIdentifier(BizConstants.CREDIT2_SALESDEPARTMENT);
					canBrowseSalesDeptList = null;
					//获取所有的营业网点ID
					canBrowseSalesDeptList = areaService.getDeptsByUserIdAndDeptsTypes(baseAreaVo);
				}
				loanVO.setSalesDeptIdList(canBrowseSalesDeptList);
			}
			// 判断借款类型
			if ("admin".equals(ApplicationContext.getUser().getLoginName())) {
				loanVO.setProductId(null);
			} else {
				List<Product> productList = productService.findProductTypeByUserId(userId);
				Integer productType = productList.get(0).getProductType();
				loanVO.setProductType(productType);
			}

			// 客户经理选择"全部"
			if (loanVO.getCrmId() != null && loanVO.getCrmId().compareTo(0L) == 0) {
				loanVO.setCrmId(null);
			}

			Pager p = new Pager();
			p.setRows(rows);
			p.setPage(page);
			p.setSidx("AUDIT_DATE");
			p.setSort("ASC");
			loanVO.setPager(p);

			Pager loanPager = loanService.specialRepaymentFindWithPG(loanVO);
			List<Loan> loanList = loanPager.getResultList();
			RepaymentInAdvance repaymentInAdvance;
			for (Loan loan : loanList) {
				repaymentInAdvance = new RepaymentInAdvance();
				repaymentInAdvance.setLoanId(loan.getId());
				if (loan.getPerson() != null) {
					repaymentInAdvance.setName(loan.getPerson().getName());
					repaymentInAdvance.setIdNum(loan.getPerson().getIdnum());
				}
				repaymentInAdvance.setProductId(loan.getProductId());
				repaymentInAdvance.setProductType(loan.getProductType().longValue());
				repaymentInAdvance.setProductSubType(loan.getLoanType());
				repaymentInAdvance.setPactMoney(loan.getPactMoney());
				repaymentInAdvance.setTime(loan.getTime());
				repaymentInAdvance.setStatus(loan.getStatus());
				repaymentInAdvance.setExtensionTime(loan.getExtensionTime());
				setExtRepaymentInAdvance(loan.getId(), repaymentInAdvance);
				// 如果该笔借款申请了"提前扣款"
				if (specialRepaymentService.isInAdvanceRepayment(loan.getId())) {
					if (currUserIsProposer(loan.getId(), EnumConstants.SpecialRepaymentType.DEDUCT_IN_ADVANCE.getValue())) {
						repaymentInAdvance.setOperations("|提前扣款取消");
					} else {
						repaymentInAdvance.setOperations("|不可操作");
					}
				}
				// 如果该笔借款申请了"一次性结清"
				else if (specialRepaymentService.isOneTimeRepayment(loan.getId())) {
					if (currUserIsProposer(loan.getId(), EnumConstants.SpecialRepaymentType.ONE_TIME_REPAYMENT.getValue())) {
						repaymentInAdvance.setOperations("|一次性结清取消");
					} else {
						repaymentInAdvance.setOperations("|不可操作");
					}
				}
				// 如果该笔借款既没有申请"提前扣款"，也没有申请"一次性结清",页面操作显示"提前扣款  一次性结清"
				else {
					// 展期的话
					if(loan.getExtensionTime().compareTo(0) > 0) {
						Long preExtensionId = loanExtensionService.getPreExtensionId(loan.getId(), loan.getExtensionTime());
						if(!loanService.isCloseOut(preExtensionId)) {
							repaymentInAdvance.setOperations("|不可操作");
						} else {
							repaymentInAdvance.setOperations("|提前扣款|一次性结清");
						}
					} else {
						repaymentInAdvance.setOperations("|提前扣款|一次性结清");
					}
				}
				repaymentInAdvanceList.add(repaymentInAdvance);
			}
			result.put("total", loanPager.getTotalCount());
			result.put("rows", repaymentInAdvanceList);
		} else {
			result.put("total", 0);
			result.put("rows", repaymentInAdvanceList);
		}
		return result;
	}

}


