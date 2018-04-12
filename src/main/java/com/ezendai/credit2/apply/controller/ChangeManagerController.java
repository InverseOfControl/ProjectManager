package com.ezendai.credit2.apply.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.model.Product;
import com.ezendai.credit2.apply.service.LoanService;
import com.ezendai.credit2.apply.service.ProductService;
import com.ezendai.credit2.apply.service.SysProductUserService;
import com.ezendai.credit2.apply.vo.LoanVO;
import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.framework.model.UserSession;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.system.controller.BaseController;
import com.ezendai.credit2.system.model.SysUser;
import com.ezendai.credit2.system.service.SysUserService;

@Controller
@RequestMapping("/changeManager")
public class ChangeManagerController extends BaseController{
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysProductUserService sysProductService;
	@Autowired
	private ProductService productService;
	@Autowired
	private LoanService loanService;
	
	/**
	 * <pre>
	 * 变更管理主界面
	 * </pre>
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/changeManagerMain")
	public String changeManagerList(HttpServletRequest request) {
		setDataDictionaryArr(new String[] {EnumConstants.PRODUCT_SUB_TYPE});
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		return "apply/changeManagerList";
	}
	
	/**
	 * <pre>
	 * 判断当前用户是小企业贷或车贷
	 * </pre>
	 * @return
	 */
	@RequestMapping("/checkProductType")
	@ResponseBody
	public String checkProductType() {
		String productType = "";
		if ("admin".equals(ApplicationContext.getUser().getLoginName())) {
			return "admin";
		}
		Long userId = ApplicationContext.getUser().getId();
		List<Long> productIdList = sysProductService.getProductIdByUserId(userId);
		Integer userType = ApplicationContext.getUser().getUserType();
		// 如果是经理或副理,只有一个productId
		if (userType.compareTo(EnumConstants.UserType.STORE_MANAGER.getValue()) == 0
				|| userType.compareTo(EnumConstants.UserType.STORE_ASSISTANT_MANAGER.getValue()) == 0) {
			Long productId = productIdList.get(0);
			Product product = productService.get(productId);
			// 如果是小企业贷
			if(product.getProductType().compareTo(EnumConstants.ProductType.SE_LOAN.getValue()) ==0) {
				productType = "seLoan";
				return productType;
			}// 如果是车贷
			else if(product.getProductType().compareTo(EnumConstants.ProductType.CAR_LOAN.getValue()) ==0) {
				productType = "carLoan";
				return productType;
			}
		}
		return productType;
	}
	
	/**
	 * <pre>
	 * 获取该用户所在营业店的所有客服列表
	 * </pre>
	 * @return
	 */
	@RequestMapping("/getManagerList")
	@ResponseBody
	public List<SysUser> getManagerServiceList(String flage) {
		List<SysUser> managerServiceList = new ArrayList<SysUser>();
		// 需要显示"全部"
		if(flage.equals("all")) {
		SysUser allUser = new SysUser();
		allUser.setId(0L);
		allUser.setName("全部");
		managerServiceList.add(allUser);
		}
		// 如果是管理员登陆，看到的管理客服列表为空(所有客服都显示的话，数量太多，没有意义);
		if ("admin".equals(ApplicationContext.getUser().getLoginName())) {
			return managerServiceList;
		}
//		// 获取当前用户所在营业部及相应Code
//		BaseArea salesDept = sysUserService.getCurSalesDept();
//		String salesDeptCode = salesDept.getCode();
		// 获取当前登陆用户ID及所有产品类型
		UserSession userSession = ApplicationContext.getUser();
		List<Long> productIdList = sysProductService.getProductIdByUserId(userSession.getId());
		// 根据当前用户DataPermission与ProductId获取其所在营业部所有客服（包括经理与副理）
		List<SysUser> servicesList = sysUserService.getServicesInCurSalesDeptByProductIdList(userSession.getId(), productIdList);
		for (SysUser user : servicesList) {
			// 如果usertype为客服,则将其加入客服集合
			if(user.getUserType().compareTo(EnumConstants.UserType.CUSTOMER_SERVICE.getValue()) == 0) {
				managerServiceList.add(user);
			}
		}
		
		return managerServiceList;
	}
	
	/**
	 *<pre>
	 *显示列表
	 *</pre>
	 * @param loanVO
	 * @param rows
	 * @param page
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getManagerPage")
	@ResponseBody
	public Map<String, Object> getManagerPage(LoanVO loanVO, int rows, int page) {
		Long userId = ApplicationContext.getUser().getId();
		
		// 当前用户营业网点判断
		if ("admin".equals(ApplicationContext.getUser().getLoginName())) {
			loanVO.setSalesDeptId(null);
		} else {
			List<Long> salesDeptList = sysUserService.getSalesDeptIdByUserId(userId);
			loanVO.setSalesDeptIdList(salesDeptList);
		}
		
		 // 判断当前用户借款类型、借款子类型
		if ("admin".equals(ApplicationContext.getUser().getLoginName())) {
			loanVO.setProductIdList(null);
			loanVO.setLoanType(null);
		} else {
			List<Product> products = productService.findProductTypeByUserId(userId);
			Integer productType = products.get(0).getProductType();
			// 当前用户为小企业贷
			if(productType.compareTo(EnumConstants.ProductType.SE_LOAN.getValue()) == 0) {
				loanVO.setProductType(EnumConstants.ProductType.SE_LOAN.getValue());
				loanVO.setLoanType(null);
			}
			// 当前用户为车贷且子类型选择"全部"
			else if(productType.compareTo(EnumConstants.ProductType.CAR_LOAN.getValue()) == 0
					&& loanVO.getLoanType() != null && loanVO.getLoanType().compareTo(0) == 0) {
					loanVO.setLoanType(null);
			}
		}
		
		// 管理客服选择"全部"
		if(loanVO.getManagerId() != null && loanVO.getManagerId().compareTo(0L) == 0) {
			loanVO.setManagerId(null);
		}
		
		Pager p = new Pager();
		p.setRows(rows);
		p.setPage(page);
		p.setSidx("NVL(AUDIT_DATE,CREATED_TIME)");
		p.setSort("ASC");
		loanVO.setPager(p);
		
		Pager loanPager = loanService.changeManageFindWithPG(loanVO);
		List<Loan> loanList = loanPager.getResultList();
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("total", loanPager.getTotalCount());
		result.put("rows", loanList);
		return result;
	}
	
	/**
	 * <pre>
	 * 变更管理客服
	 * </pre>
	 * @param loanVO
	 * @return
	 */
	@RequestMapping("/doChange")
	@ResponseBody
	public String changeManage(LoanVO loanVO){
		return loanService.updateByIdList(loanVO);
	}
}
