package com.ezendai.credit2.after.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezendai.credit2.after.service.RepayService;
import com.ezendai.credit2.after.view.RepayTrial;
import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.model.Product;
import com.ezendai.credit2.apply.service.LoanService;
import com.ezendai.credit2.apply.service.ProductService;
import com.ezendai.credit2.apply.vo.LoanVO;
import com.ezendai.credit2.audit.model.RepaymentPlan;
import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.framework.constant.Constants;
import com.ezendai.credit2.framework.util.DateUtil;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.framework.vo.BaseVO;
import com.ezendai.credit2.master.constant.BizConstants;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.master.service.AreaService;
import com.ezendai.credit2.master.vo.BaseAreaVO;
import com.ezendai.credit2.system.controller.BaseController;
import com.ezendai.credit2.system.model.SysUser;
import com.ezendai.credit2.system.service.SysUserService;

@Controller
@RequestMapping("/after/repayTrial")
public class RepayTrialController extends BaseController {
	@Autowired
	private AreaService areaService;
	@Autowired
	private LoanService loanService;

	@Autowired
	private ProductService productService;

	@Autowired
	private RepayService repayService;
	@Autowired
	private SysUserService sysUserService;

	@RequestMapping("/list")
	public String list(HttpServletRequest request) {
		/*设置数据字典*/
		setDataDictionaryArr(new String[] { EnumConstants.PRODUCT_ID, EnumConstants.PRODUCT_SUB_TYPE ,EnumConstants.REPAYMENT_PLAN_STATE });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		request.setAttribute("today", DateUtil.defaultFormatDay(DateUtil.getToday()));
		return "after/trial/repayTrialList";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/list.json")
	@ResponseBody
	public Map list(@RequestParam(value = Constants.PAGE_NUMBER_NAME, defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page,
					@RequestParam(value = Constants.PAGE_ROWS_NAME, defaultValue = Constants.DEFAULT_PAGE_ROWS) int pageSize, @ModelAttribute("vo") LoanVO vo, HttpServletRequest request) {
		Long userId = ApplicationContext.getUser().getId();
		SysUser sysUser = sysUserService.get(userId);
		BaseAreaVO baseAreaVo = new BaseAreaVO();
		
		List<Integer> statusList = new ArrayList<Integer>();
		statusList.add(EnumConstants.LoanStatus.正常.getValue());
		statusList.add(EnumConstants.LoanStatus.逾期.getValue());
		statusList.add(EnumConstants.LoanStatus.坏账.getValue());
		vo.setStatusList(statusList);
		//当传空值的时候传当天日期
		if (vo.getRepayDate() == null) {
			vo.setRepayDate(DateUtil.getToday());
		}
		//当传空值的时候传当天日期
		if (vo.getRepaymentType() == null) {
			vo.setRepaymentType(EnumConstants.RepaymentType.NORMAL.getValue());
		}
		//判断是否展期
		if(vo.getExtensionTime()!=null&& vo.getExtensionTime().compareTo(0)>0)
		{
			vo.setIsExtension(1);
		}
		// 确定查询的产品类型
		List<Product> products = productService.findProductsByUserId(userId);
		List<Integer> canBrowseproductIds = new ArrayList<Integer>();
		for (Product product : products)
			canBrowseproductIds.add(product.getProductType());
		if (canBrowseproductIds.size() < 1)
			return null;

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
		Integer userType = ApplicationContext.getUser().getUserType();
		if (userType != null && userType.compareTo(EnumConstants.UserType.CUSTOMER_SERVICE.getValue()) == 0) {
			baseAreaVo.setUserId(userId);
			//获取所有的营业网点ID
//			vo.setManagerId(userId);
			
			List<Long> canBrowseSalesDeptList = sysUserService.getSalesDeptIdByUserId(userId);
			if (canBrowseSalesDeptList == null) {
				return null;
			} else if (vo.getSalesDeptId() == null) {
				vo.setSalesDeptIdList(canBrowseSalesDeptList);
			} 
		} else if (userType != null && (userType.compareTo(EnumConstants.UserType.STORE_MANAGER.getValue()) == 0 || userType.compareTo(EnumConstants.UserType.STORE_ASSISTANT_MANAGER.getValue()) == 0)) {
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
			if (canBrowseSalesDeptList == null) {
				return null;
			} else if (vo.getSalesDeptId() == null) {
				vo.setSalesDeptIdList(canBrowseSalesDeptList);
			} 
		}
		Pager p = new Pager();
		p.setPage(page);
		p.setRows(pageSize);
		p.setSidx("SIGN_DATE");
		p.setSort("ASC");
		vo.setPager(p);
		Pager pager = loanService.findRepayTrialWithPG(vo);
		List<Loan> loanList = pager.getResultList();
		List<RepayTrial> repayTrialList = new ArrayList<RepayTrial>();
		for (Loan loan : loanList) {

			RepayTrial repayTrial = new RepayTrial();
			repayTrial.setLoanId(loan.getId());
			if (loan.getManageService() != null) {
				repayTrial.setManageServiceName(loan.getManageService().getName());
			}
			if (loan.getPerson() != null) {
				repayTrial.setPersonName(loan.getPerson().getName());
				repayTrial.setPersonIdnum(loan.getPerson().getIdnum());
				repayTrial.setPersonMobilePhone(loan.getPerson().getMobilePhone());
			}
			repayTrial.setProductId(loan.getProductId());
			repayTrial.setProductType(loan.getProductType());
			if (loan.getGrantAccount() != null) {
				//银行账号
				repayTrial.setBankAccount(loan.getGrantAccount().getAccount());
				//开户行
				repayTrial.setBankName(loan.getGrantAccount().getBankName());
			}
			repayTrial.setLoanType(loan.getLoanType());
			repayTrial.setPactMoney(loan.getPactMoney());
			repayTrial.setRepayPeriod(loan.getTime());
			repayTrial.setExtensionTime(loan.getExtensionTime());
			setExtRepayTrial(loan.getId(), vo, repayTrial, repayTrialList);

		}
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("total", pager.getTotalCount());
		result.put("rows", repayTrialList);
		return result;
	}

	/**
	 * <pre>
	 * 设置还款试算需要调用其他接口的一些
	 * </pre>
	 *
	 * @param loanId
	 * @param vo
	 * @param repayTrial
	 * @param repayTrialList
	 */
	private void setExtRepayTrial(Long loanId, LoanVO vo, RepayTrial repayTrial, List<RepayTrial> repayTrialList) {
		List<RepaymentPlan> repaymentPlanList = repayService.getAllInterestOrLoan(vo.getRepayDate(), loanId);
		//设置挂账金额,期末预收
		BigDecimal accAmount = repayService.getAccAmount(loanId);
		repayTrial.setAccAmount(accAmount);
		//设置当前期数
		Integer currTerm = repayService.getCurrTerm(repaymentPlanList, vo.getRepayDate());
		if (currTerm != 0) {
			repayTrial.setCurrTermText(String.valueOf(currTerm));
		} else {
			repayTrial.setCurrTermText("无");
		}
		if (repaymentPlanList != null && repaymentPlanList.size() > 0) {
			Integer repaymentType = vo.getRepaymentType();
			//逾期应还
			BigDecimal overdueAmount = repayService.getOverdueAmount(repaymentPlanList, vo.getRepayDate());
			//罚息,逾期违约金
			BigDecimal fine = repayService.getFine(repaymentPlanList, vo.getRepayDate());
			repayTrial.setFine(fine);
			//逾期应还总额
			BigDecimal overdueAllAmount = overdueAmount.add(fine);
			repayTrial.setOverdueAllAmount(overdueAllAmount);
			//正常还款
			if (EnumConstants.RepaymentType.NORMAL.getValue().equals(repaymentType)) {
				//只有还款日当期应还才显示有值
				BigDecimal currDeficitForRepayDay = repayService.getCurrDeficitForRepayDay(repaymentPlanList, vo.getRepayDate());
				//当期应还
				repayTrial.setCurrAmount(currDeficitForRepayDay);
				//一次性结清违约金
				repayTrial.setPenalty(BigDecimal.ZERO);

				BigDecimal currRepayAmount = BigDecimal.ZERO;

				List<RepaymentPlan> repaymentPlanAllList = repayService.getAllInterestOrLoan(loanId);
				int size = repaymentPlanAllList.size();
				if (size > 0) {
					RepaymentPlan repaymentPlan = repaymentPlanAllList.get(size - 1);
					Date lastTermRepayDay = repaymentPlan.getRepayDay();
					//如果最后一期的还款日在查询传入的还款日期后面
					if (vo.getRepayDate() != null && !vo.getRepayDate().after(lastTermRepayDay)) {
						currRepayAmount = repayService.getCurrRepayAmount(repaymentPlanList, vo.getRepayDate());
					}
				}
				//应还总额= 当期应还 + 逾期应还 - 期末预收
				BigDecimal repayAllAmount = currRepayAmount.add(overdueAllAmount).subtract(accAmount);
				if (repayAllAmount.compareTo(BigDecimal.ZERO) == -1) {
					repayAllAmount = BigDecimal.ZERO;
				}
				repayTrial.setRepayAllAmount(repayAllAmount);
			}
			//一次性还款
			else if (EnumConstants.RepaymentType.ONE_TIME_REPAYMENT.getValue().equals(repaymentType)) {
				//一次性结清
				BigDecimal onetimeRepaymentAmount = repayService.getOnetimeRepaymentAmount(repaymentPlanList, vo.getRepayDate());
				repayTrial.setOneTimeRepayment(onetimeRepaymentAmount);
				//一次性结清违约金
				BigDecimal penalty = repayService.getPenalty(repaymentPlanList, vo.getRepayDate());
				repayTrial.setPenalty(penalty);
				//应还总额 =一次性结清 + 逾期应还 - 期末预收
				BigDecimal repayAllAmount = onetimeRepaymentAmount.add(overdueAllAmount).subtract(accAmount);
				if (repayAllAmount.compareTo(BigDecimal.ZERO) == -1) {
					repayAllAmount = BigDecimal.ZERO;
				}
				repayTrial.setRepayAllAmount(repayAllAmount);
			}
			repayTrialList.add(repayTrial);
		}
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

}
