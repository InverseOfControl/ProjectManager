/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.finance.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
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

import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
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

import com.ezendai.credit2.apply.model.BankAccount;
import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.model.Person;
import com.ezendai.credit2.apply.model.Product;
import com.ezendai.credit2.apply.service.BankAccountService;
import com.ezendai.credit2.apply.service.LoanService;
import com.ezendai.credit2.apply.service.PersonService;
import com.ezendai.credit2.apply.service.ProductService;
import com.ezendai.credit2.apply.vo.LoanVO;
import com.ezendai.credit2.apply.vo.ProductVO;
import com.ezendai.credit2.audit.model.ApproveResult;
import com.ezendai.credit2.common.util.ExportExcel;
import com.ezendai.credit2.common.util.PoiExportExcel;
import com.ezendai.credit2.finance.service.FinanceService;
import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.framework.constant.Constants;
import com.ezendai.credit2.framework.exception.BusinessException;
import com.ezendai.credit2.framework.util.CollectionUtil;
import com.ezendai.credit2.framework.util.DateUtil;
import com.ezendai.credit2.framework.util.FileUtil;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.master.service.SysEnumerateService;
import com.ezendai.credit2.system.Credit2Properties;
import com.ezendai.credit2.system.controller.BaseController;
import com.ezendai.credit2.system.enumerate.SysParameterEnum;
import com.ezendai.credit2.system.model.SysParameter;
import com.ezendai.credit2.system.model.SysUser;
import com.ezendai.credit2.system.service.OrganBankService;
import com.ezendai.credit2.system.service.OrganService;
import com.ezendai.credit2.system.service.SysParameterService;
import com.ezendai.credit2.system.service.SysUserService;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author liyuepeng
 * @version $Id: FinancialGrantController.java, v 0.1 2014-9-10 下午03:20:38
 *          liyuepeng Exp $
 */
@Controller
@RequestMapping("/financialGrant")
public class FinancialGrantController extends BaseController {

	@Autowired
	private FinanceService financeService;

	@Autowired
	private ProductService productService;

	@Autowired
	private LoanService loanService;

	@Autowired
	private PersonService personService;

	@Autowired
	private SysParameterService sysParameterService;

	@Autowired
	private Credit2Properties credit2Properties;
	@Autowired
	private SysEnumerateService sysEnumerateService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private BankAccountService bankAccountService;

	@Autowired
	private OrganService organService;

	@Autowired
	private OrganBankService organBankService;

	private static final Logger logger = Logger.getLogger(FinancialGrantController.class);

	/***
	 * 
	 * <pre>
	 * 显示每个员工的贷款类型
	 * </pre>
	 *
	 * @return
	 */
	@RequestMapping("/getLoanType")
	@ResponseBody
	public List<Product> getLoanTypeByCurrUser() {
		Long userId = ApplicationContext.getUser().getId();
		if ("admin".equals(ApplicationContext.getUser().getLoginName())) {
			return productService.findListByVO(new ProductVO());
		}

		List<Product> loanType = productService.findProductTypeByUserId(userId);

		return loanType;
	}

	@RequestMapping("/list")
	public String init(HttpServletRequest request) {
		/* 设置数据字典 */
		Integer userType = ApplicationContext.getUser().getUserType();
		request.setAttribute("userType", userType);
		setDataDictionaryArr(new String[] { EnumConstants.PRODUCT_ID, EnumConstants.PRODUCT_TYPE, EnumConstants.LOAN_STATUS, EnumConstants.CONTRACT_SRC });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		return "finance/financialGrantList";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/list.json")
	@ResponseBody
	public Map list(@RequestParam(value = Constants.PAGE_NUMBER_NAME, defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = Constants.PAGE_ROWS_NAME, defaultValue = Constants.DEFAULT_PAGE_ROWS) int pageSize, @ModelAttribute("vo") LoanVO vo, HttpServletRequest request) {

		Long userId = ApplicationContext.getUser().getId();
		Integer userType = ApplicationContext.getUser().getUserType();
		if (vo.getStatus() == null) {
			vo.setStatus(EnumConstants.LoanStatus.财务放款.getValue());
		} else if (vo.getStatus() == 0) {
			List<Integer> statusList = new ArrayList<Integer>();
			statusList.add(EnumConstants.LoanStatus.财务放款.getValue());
			statusList.add(EnumConstants.LoanStatus.财务放款退回.getValue());
			statusList.add(EnumConstants.LoanStatus.正常.getValue());
			vo.setStatusList(statusList);
			vo.setStatus(null);
		}
		// 当传空值的时候传15天前的日期
//		if (vo.getFinanceAuditTimeStart() == null) {
//			vo.setFinanceAuditTimeStart(DateUtil.getDateBefore(15));
//		}
		// 合同来源为"全选"
		if (vo.getContractSrc() != null && vo.getContractSrc() == 0) {
			vo.setContractSrc(null);
		}

		// 确定查询的产品类型
		List<Product> products = productService.findProductsByUserId(userId);
		List<Long> canBrowseproductIds = new ArrayList<Long>();
		for (Product product : products) {
			canBrowseproductIds.add(product.getId());
		}
		if (canBrowseproductIds.size() < 1) {
			return null;
		}

		List<Long> qualifiedProductIds = new ArrayList<Long>();
		if (vo.getSelectedProductId() != null) {
			if (canBrowseproductIds.contains(Long.valueOf(vo.getSelectedProductId()))) {
				qualifiedProductIds.add(Long.valueOf(vo.getSelectedProductId()));
			} else {
				return null;
			}
			vo.setSelectedProductId(null);
		} else {
			qualifiedProductIds.addAll(canBrowseproductIds);
		}

		vo.setProductIdList(qualifiedProductIds);
		Pager p = new Pager();
		p.setPage(page);
		p.setRows(pageSize);
		p.setSidx("loan.FINANCE_AUDIT_TIME");
		p.setSort("ASC");
		vo.setPager(p);
		if (EnumConstants.UserType.STORE_MANAGER.getValue().equals(userType) || EnumConstants.UserType.CUSTOMER_SERVICE.getValue().equals(userType)) {
			// 门店经理,客服查询所拥有网点的数据
			List<Long> canBrowseSalesDeptList = sysUserService.getSalesDeptIdByUserId(userId);
			if (canBrowseSalesDeptList == null) {
				return null;
			} else if (vo.getSalesDeptId() == null) {
				vo.setSalesDeptIdList(canBrowseSalesDeptList);
			}
		}
		Pager pager = loanService.findWithPg(vo);
		List<Loan> loanList = pager.getResultList();
		for (Loan loan : loanList) {
			SysUser crm = sysUserService.get(loan.getCrmId());
			SysUser director = sysUserService.getBizDirectorByCrm(crm);
			if(director == null){
			    loan.setBizDirector(new SysUser());
			}else {
				loan.setBizDirector(director);
			}
//			if (loan.getBizDirector() == null) {
//				//TODO
//				SysUser biz = sysUserService.getBizDirectorByCrm(loan.getCrm());
//				if (biz == null || EnumConstants.ProductType.SE_LOAN.getValue().equals(loan.getProductType())) {
//					SysUser nbiz = new SysUser();
//					nbiz.setName("空");
//					nbiz.setCode("空");
//					loan.setBizDirector(nbiz);
//				} else {
//					loan.setBizDirector(biz);
//				}
//			}
		}
		if (loanList != null) {
			for (int i = 0; i < loanList.size(); i++) {
				Loan loan = loanList.get(i);
				if (EnumConstants.ProductList.STUDENT_LOAN.getValue().equals(loan.getProductId())) {
					if(loan.getGrantAccountId() != null){
						BankAccount bankAccount = bankAccountService.getBankAccountDetails(loan.getGrantAccountId());
						loanList.get(i).setGrantAccount(bankAccount);
					}
				}
			}
		}
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("total", pager.getTotalCount());
		result.put("rows", loanList);
		return result;
	}

	/***
	 * 
	 * <pre>
	 * 财务批量放款
	 * </pre>
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/financialMakeLoans")
	@ResponseBody
	public Map financialMakeLoans(LoanVO loanVo) {
		Map result = new HashMap();
		List<Long> loanIdList = loanVo.getIdList();
		StringBuffer stringBuffer = new StringBuffer("财务放款失败的借款人身份证号为：");
		boolean isShow = false;
		for (Long loanId : loanIdList) {
			// 校验loan状态（STATE）是否是 财务放款（110）
			Loan loan = loanService.get(loanId);
			try {
				if (loan != null) {
					if (loan.getStatus().compareTo(EnumConstants.LoanStatus.财务放款.getValue()) != 0) {
						throw new BusinessException("当前借款状态不是财务放款！");
					}
					// 财务放款
					financeService.makeLoan(loan);
				}
			} catch (Exception e) {
				String idNum = personService.get(loan.getPersonId()).getIdnum();
				stringBuffer.append(idNum).append(",");
				isShow = true;
				logger.error("放款:" + loanId + ",失败：" + e.getMessage(), e);
			}
		}
		if (isShow) {
			stringBuffer = stringBuffer.deleteCharAt(stringBuffer.length() - 1);
			result.put("success", false);
			result.put("msg", stringBuffer.toString());
		} else {
			result.put("success", true);
		}
		return result;
	}

	/***
	 * 
	 * <pre>
	 * 财务放款
	 * </pre>
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/financialMakeLoan")
	@ResponseBody
	public Map financialMakeLoan(Long loanId) {
		Map result = new HashMap();
		// 校验loan状态（STATE）是否是 财务放款（110）
		Loan loan = loanService.get(loanId);
		try {
			if (loan != null) {
				if (loan.getStatus().compareTo(EnumConstants.LoanStatus.财务放款.getValue()) != 0) {
					throw new BusinessException("当前借款状态不是财务放款！");
				}
				// 财务放款
				financeService.makeLoan(loan);
				result.put("success", true);
			}
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", "财务放款失败！");
			logger.error("放款:" + loanId + ",失败：" + e.getMessage(), e);
		}
		return result;
	}

	/***
	 * 
	 * <pre>
	 * 财务放款退回
	 * </pre>
	 *
	 * @param approveResult
	 * @return
	 */
	@RequestMapping("/financialReturn")
	@ResponseBody
	public JSONObject financialReturn(ApproveResult approveResult) {

		JSONObject result = new JSONObject();
		result.put("repCode", "000000");
		try {
			financeService.financeReturn(approveResult, EnumConstants.LoanBorrowStatus.终止借款.getValue());
		} catch (Exception e) {
			result.put("repCode", "100000");
			result.put("repMsg", e.getMessage());
		}

		return result;
	}

	@RequestMapping("/imageUploadView/{loanId}")
	public String imageUploadView(@PathVariable("loanId") Long loanId, Model model) {
		model.addAttribute("loanId", loanId);
		Loan loan = loanService.get(loanId);
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
		model.addAttribute("optModule", EnumConstants.OptionModule.FINANCE_REPAY.getValue());
		return "finance/imageUploadView";
	}

	@RequestMapping("checkExportNum")
	@ResponseBody
	public String checkExportNum(LoanVO loanVo, HttpServletRequest request, HttpServletResponse response) {
		try {
			if (loanVo.getPersonName() != null) {
				loanVo.setPersonName(java.net.URLDecoder.decode(loanVo.getPersonName(), "UTF-8"));
			}
		} catch (UnsupportedEncodingException e1) {
			logger.error("", e1);
		}

		if (loanVo.getStatus() == null) {
			loanVo.setStatus(EnumConstants.LoanStatus.财务放款.getValue());
		} else if (loanVo.getStatus() == 0) {
			List<Integer> statusList = new ArrayList<Integer>();
			statusList.add(EnumConstants.LoanStatus.财务放款.getValue());
			statusList.add(EnumConstants.LoanStatus.财务放款退回.getValue());
			statusList.add(EnumConstants.LoanStatus.正常.getValue());
			loanVo.setStatusList(statusList);
			loanVo.setStatus(null);
		}
/*		// 当传空值的时候传15天前的日期
		if (loanVo.getFinanceAuditTimeStart() == null) {
			loanVo.setFinanceAuditTimeStart(DateUtil.getDateBefore(15));
		}*/
		// 合同来源为"全选"
		if (loanVo.getContractSrc() != null && loanVo.getContractSrc() == 0) {
			loanVo.setContractSrc(null);
		}
		Long userId = ApplicationContext.getUser().getId();
		// 确定查询的产品类型
		List<Product> products = productService.findProductTypeByUserId(userId);
		List<Integer> canBrowseproductIds = new ArrayList<Integer>();
		for (Product product : products) {
			canBrowseproductIds.add(product.getProductType());
		}

		List<Integer> qualifiedProductIds = new ArrayList<Integer>();
		if (loanVo.getSelectedProductId() != null) {
			if (canBrowseproductIds.contains(loanVo.getSelectedProductId().intValue())) {
				qualifiedProductIds.add(loanVo.getSelectedProductId().intValue());
			}
		} else {
			qualifiedProductIds.addAll(canBrowseproductIds);
		}

		// loanVo.setProductIdList(qualifiedProductIds);
		loanVo.setProductTypeList(qualifiedProductIds);
		Integer count = loanService.getCountByLoanVO(loanVo);
		if (count.compareTo(0) == 0) {
			return "没有可以符合条件的数据导出！";
		}
		SysParameter sysParameter = sysParameterService.getByCode(SysParameterEnum.EXCEL_EXPORT_MAX_NO.name());
		if (sysParameter != null) {
			if (count.compareTo(Integer.valueOf(sysParameter.getParameterValue())) > 0) {
				return "excel导出条数过多！";
			}
		}
		return "success";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("export")
	@ResponseBody
	public String export(LoanVO loanVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			if (loanVo.getPersonName() != null) {
				loanVo.setPersonName(java.net.URLDecoder.decode(loanVo.getPersonName(), "UTF-8"));
			}
		} catch (UnsupportedEncodingException e1) {
			logger.error("", e1);
		}

		if (loanVo.getStatus() == null) {
			loanVo.setStatus(EnumConstants.LoanStatus.财务放款.getValue());
		} else if (loanVo.getStatus() == 0) {
			List<Integer> statusList = new ArrayList<Integer>();
			statusList.add(EnumConstants.LoanStatus.财务放款.getValue());
			statusList.add(EnumConstants.LoanStatus.财务放款退回.getValue());
			statusList.add(EnumConstants.LoanStatus.正常.getValue());
			loanVo.setStatusList(statusList);
			loanVo.setStatus(null);
		}
/*		// 当传空值的时候传15天前的日期
		if (loanVo.getFinanceGrantTimeStart() == null) {
			loanVo.setFinanceGrantTimeStart(DateUtil.getDateBefore(15));
		}*/
		// 合同来源为"全选"
		if (loanVo.getContractSrc() != null && loanVo.getContractSrc() == 0) {
			loanVo.setContractSrc(null);
		}
		Long userId = ApplicationContext.getUser().getId();
		// 确定查询的产品类型
		List<Product> products = productService.findProductTypeByUserId(userId);
		List<Integer> canBrowseproductIds = new ArrayList<Integer>();
		for (Product product : products) {
			canBrowseproductIds.add(product.getProductType());
		}

		List<Integer> qualifiedProductIds = new ArrayList<Integer>();
		if (loanVo.getSelectedProductId() != null) {
			if (canBrowseproductIds.contains(loanVo.getSelectedProductId().intValue())) {
				qualifiedProductIds.add(loanVo.getSelectedProductId().intValue());
			}
		} else {
			qualifiedProductIds.addAll(canBrowseproductIds);
		}
		loanVo.setProductTypeList(qualifiedProductIds);
		Integer userType = ApplicationContext.getUser().getUserType();
		if (EnumConstants.UserType.STORE_MANAGER.getValue().equals(userType) || EnumConstants.UserType.CUSTOMER_SERVICE.getValue().equals(userType)) {
			// 门店经理,客服查询所拥有网点的数据
			List<Long> canBrowseSalesDeptList = sysUserService.getSalesDeptIdByUserId(userId);
			if (canBrowseSalesDeptList == null) {
				return null;
			} else if (loanVo.getSalesDeptId() == null) {
				loanVo.setSalesDeptIdList(canBrowseSalesDeptList);
			}
		}
		List<Loan> loanList = loanService.findListByVOExtension(loanVo);
		//车贷如果业务主任为空，可以根据客服经理查找业务主任，小企业贷则赋空
		for (Loan loan : loanList) {
			SysUser crm = sysUserService.get(loan.getCrmId());
			SysUser director = sysUserService.getBizDirectorByCrm(crm);
			if(director == null){
				SysUser nbiz = new SysUser();
				nbiz.setName("");
				nbiz.setCode("");
				loan.setBizDirector(nbiz);
			}else {
				loan.setBizDirector(director);
			}
//			if (loan.getBizDirector() == null) {
//				SysUser biz = sysUserService.getBizDirectorByCrm(loan.getCrm());
//				if (biz == null || EnumConstants.ProductType.SE_LOAN.getValue().equals(loan.getProductType())) {
//					SysUser nbiz = new SysUser();
//					nbiz.setName("空");
//					nbiz.setCode("空");
//					loan.setBizDirector(nbiz);
//				} else {
//					loan.setBizDirector(biz);
//				}
//			}else {
//				SysUser crm = sysUserService.get(loan.getCrmId());
//				SysUser director = sysUserService.getBizDirectorByCrm(crm);
//				SysUser loanDirector = loan.getBizDirector();
//				if(loanDirector.getId().compareTo(director.getId()) != 0){
//					//如果Loan表中存的和查询出来的不一致，则使用查询出来的业务主任
//					loan.setBizDirector(director);
//				}
//			}
		}
		if (loanList != null) {
			for (int i = 0; i < loanList.size(); i++) {
				Loan loan = loanList.get(i);
				if (EnumConstants.ProductList.STUDENT_LOAN.getValue().equals(loan.getProductId())) {
					if(loan.getGrantAccountId() != null){
						BankAccount bankAccount = bankAccountService.getBankAccountDetails(loan.getGrantAccountId());
						loanList.get(i).setGrantAccount(bankAccount);
					}
				}
			}
		}
		String dates = DateUtil.getDate(new Date(), "yy-MM-dd");
		// 文件名
		String fileName = "财务放款列表" + dates + ".xlsx";
		String fileName2 = "FinancialLending" + dates + ".xlsx";
		String downloadPath = credit2Properties.getDownloadPath();
		File file = new File(downloadPath + File.separator + "finance");
		if (!file.exists()) {// 不存在则创建该目录
			file.mkdirs();
		}
		OutputStream os;
		try {
			os = new FileOutputStream(downloadPath + File.separator + "finance" + File.separator + fileName2.trim().toString());
			// 生成Excel文件
			exportExcel(loanList, os);
		} catch (FileNotFoundException e) {
			logger.info("财务放款导出excel出错：" + e.getMessage());
			return e.getMessage();
		}
		// 下载Escel文件的路径
		String filePath = downloadPath + File.separator + "finance" + File.separator + fileName2;
		try {
			// 下载Excel文件到客户端
			if (FileUtil.downLoadFile(filePath, response, fileName, "xlsx")) {
				// 导出成功后删除导出的文件
				FileUtil.deleteFile(filePath);
			} else {
				return "导出excel出错";
			}
		} catch (Exception e) {
			logger.error("财务放款导出excel出错：", e);
			return "导出excel出错";
		}
		return "success";
	}

	/***
	 * 
	 * <pre>
	 * 包装查询的数据后导出Excel
	 * </pre>
	 *
	 */
	private void exportExcel(List<Loan> loanList, OutputStream os) {

		List<ExportExcel> exportExcelList = new ArrayList<ExportExcel>();
		ExportExcel exportExcel = new ExportExcel();
		exportExcel.setSheet("财务放款");
		// 数据行
		List<String[]> rows = new ArrayList<String[]>();
		String[] column = new String[34];
		// 列头
		column[0] = new String("营业部");
		column[1] = new String("产品类型");
		column[2] = new String("借款类型");
		column[3] = new String("子类型");
		column[4] = new String("客户经理工号");
		column[5] = new String("客户经理 ");
		column[6] = new String("业务主任工号");
		column[7] = new String("业务主任");
		column[8] = new String("姓名");
		column[9] = new String("证件号码");
		column[10] = new String("机构名称");
		column[11] = new String("放款户名");
		column[12] = new String("放款银行");
		column[13] = new String("放款支行");
		column[14] = new String("放款账号");
		column[15] = new String("合同金额");
		column[16] = new String("审批金额");
		column[17] = new String("划款金额");
		column[18] = new String("借款期限");
		column[19] = new String("还款方式");
		column[20] = new String("还款日");
		column[21] = new String("还款起始日");
		column[22] = new String("还款止日");
		column[23] = new String("风险金");
		column[24] = new String("咨询费");
		column[25] = new String("评估费");
		column[26] = new String("乙方管理费");
		column[27] = new String("丙方管理费");
		column[28] = new String("利息");
		column[29] = new String("费用合计");
		column[30] = new String("签约日期");
		column[31] = new String("放款日期");
		column[32] = new String("网点类型");
		column[33] = new String("合同来源");
		rows.add(column);
		if (CollectionUtil.isNotEmpty(loanList)) {
			// 每一行要显示的内容
			for (Loan loan : loanList) {
				String[] row = new String[34];
				BigDecimal costs = new BigDecimal(0);// 费用合计
				row[0] = new String(loan.getSalesDept() != null ? loan.getSalesDept().getName() : "");
				if (EnumConstants.ProductList.CITY_WIDE_POS_LOAN.getValue().equals(loan.getProductId())) {
					row[1] = new String("同城POS");
				} else if (EnumConstants.ProductList.CITY_WIDE_SE_LOAN.getValue().equals(loan.getProductId())) {
					row[1] = new String("同城小微贷");
				} else if (EnumConstants.ProductList.STUDENT_LOAN.getValue().equals(loan.getProductId())) {
					row[1] = new String("助学贷");
				} else if (EnumConstants.ProductList.WS_SE_LOAN.getValue().equals(loan.getProductId())) {
					row[1] = new String("网商贷");
				} else if (EnumConstants.ProductList.CAR_LOAN.getValue().equals(loan.getProductId())) {
					row[1] = new String("车贷");
				} else if (EnumConstants.ProductList.CAR_NEW_LOAN.getValue().equals(loan.getProductId())) {
					row[1] = new String("车贷新产品");
				} else if (EnumConstants.ProductList.SE_LOAN.getValue().equals(loan.getProductId())) {
					row[1] = new String("小企业贷");
				} else {
					row[1] = new String("");
				}
				if (EnumConstants.ProductType.SE_LOAN.getValue().equals(loan.getProductType())) {
					row[2] = new String("小企业贷");
				} else if (EnumConstants.ProductType.CAR_LOAN.getValue().equals(loan.getProductType())) {
					row[2] = new String("车贷");
				}
				if (loan.getLoanType() != null && loan.getLoanType() == 1) {
					row[3] = new String("移交类");
				} else if (loan.getLoanType() != null && loan.getLoanType() == 2) {
					row[3] = new String("流通类");
				} else {
					row[3] = new String("");
				}
				row[4] = new String(loan.getCrm() == null ? "" : (loan.getCrm().getCode() != null ? loan.getCrm().getCode() : ""));
				row[5] = new String(loan.getCrm() == null ? "" : (loan.getCrm().getName() != null ? loan.getCrm().getName() : ""));
				row[6] = new String (loan.getBizDirector() != null ? loan.getBizDirector().getCode() : "");
				row[7] = new String (loan.getBizDirector() != null ? loan.getBizDirector().getName() : "");
				row[8] = new String(loan.getPerson() != null ? loan.getPerson().getName() : "");
				row[9] = new String(loan.getPerson() != null ? loan.getPerson().getIdnum() : "");
				row[10] = new String( loan.getOrgan() != null ? loan.getOrgan().getName() : "");
				BankAccount bankAccount = null;
				if (loan.getGrantAccountId() != null) {

					bankAccount = bankAccountService.get(loan.getGrantAccountId());
				}
				if (bankAccount != null) {
					if (bankAccount.getAccountName() != null)
						row[11] = new String(bankAccount.getAccountName());
					else
						row[11] = new String(loan.getPerson().getName());
				} else {
					row[11] = new String(loan.getPerson().getName());
				}
				//row[11] = new String(loan.getGrantAccountId() != null ? (loan.getGrantAccount() != null ? (loan.getGrantAccount().getAccountName() != null ? loan.getGrantAccount().getAccountName() : loan.getPerson().getName()) : "") : "");
				row[12] = new String(loan.getGrantAccountId() != null ? (loan.getGrantAccount() != null ? loan.getGrantAccount().getBankName() : "") : "");
				row[13] = new String(loan.getGrantAccountId() != null ? (loan.getGrantAccount() != null ? loan.getGrantAccount().getBranchName() : "") : "");
				row[14] = new String(loan.getGrantAccountId() != null ? (loan.getGrantAccount() != null ? loan.getGrantAccount().getAccount().trim() : "") : "");
				row[15] = new String(loan.getPactMoney() != null ? loan.getPactMoney().toString() : "");
				row[16] = new String(loan.getAuditMoney() != null ? loan.getAuditMoney().toString() : "");
				row[17] = new String(loan.getGrantMoney() != null ? loan.getGrantMoney().toString() : "");
				row[18] = new String(loan.getAuditTime() != null ? loan.getAuditTime().toString() : "");
				String temp = null;
				if(loan.getRepaymentMethod() != null)
					temp =sysEnumerateService.findEnumValue("REPAYMENT_METHOD", loan.getRepaymentMethod());
				row[19] = temp != null ? temp : "";
				row[20] = loan.getReturnDate() != null ? loan.getReturnDate().toString() : "";
				row[21] = new String(loan.getStartRepayDate() != null ? DateUtil.getDate(loan.getStartRepayDate(), "yyyy-MM-dd") : "");
				row[22] = new String(loan.getEndRepayDate() != null ? DateUtil.getDate(loan.getEndRepayDate(), "yyyy-MM-dd") : "");
				if (loan.getRisk() != null) {
					costs = costs.add(loan.getRisk());
					row[23] = new String(loan.getRisk().toString());
				} else {
					row[23] = new String("");
				}
				if (loan.getConsult() != null) {
					costs = costs.add(loan.getConsult());
					row[24] = new String(loan.getConsult().toString());
				} else {
					row[24] = new String("");
				}

				if (loan.getAssessment() != null) {
					costs = costs.add(loan.getAssessment());
					row[25] = new String(loan.getAssessment().toString());
				} else {
					row[25] = new String("");
				}
				if (loan.getbManage() != null) {
					costs = costs.add(loan.getbManage());
					row[26] = new String(loan.getbManage().toString());
				} else {
					row[26] = new String("");
				}
				if (loan.getcManage() != null) {
					costs = costs.add(loan.getcManage());
					row[27] = new String(loan.getcManage().toString());
				} else {
					row[27] = new String("");
				}
				if (loan.getProphaseInterest() != null) {
					//column[23] = new String("风险金");
					//column[24] = new String("咨询费");
					//column[25] = new String("评估费");
					//column[26] = new String("乙方管理费");
					//column[27] = new String("丙方管理费");
					//column[28] = new String("利息");
					//column[29] = new String("费用合计");
					//费用合计改为（23+24+25+26+27），不含利息
					//costs = costs.add(loan.getProphaseInterest());
					row[28] = new String(loan.getProphaseInterest().toString());
				} else {
					row[28] = new String("0");
				}
					row[29] = new String(costs.toString());
				if (loan.getSignDate() != null) {
					row[30] = new String(DateUtil.getDate(loan.getSignDate(), "yyyy-MM-dd"));
				} else {
					row[30] = new String("");
				}
					row[31] = new String(loan.getGrantDate() != null ? DateUtil.getDate(loan.getGrantDate(), "yyyy-MM-dd") : "");
				if ((loan.getProductType()).equals(1)) {
					row[32] = new String("小企业贷");
				} else if ((loan.getProductType()).equals(2)) {
					row[32] = new String("车贷");
				}
				if (loan.getContractSrc() != null && loan.getContractSrc() == 1) {
					row[33] = new String("证大P2P");
				} else if (loan.getContractSrc() != null && loan.getContractSrc() == 2) {
					row[33] = new String("证大爱特");
				} else {
					row[33] = new String("");
				}

				rows.add(row);
			}
		}
		exportExcel.setRows(rows);
		exportExcelList.add(exportExcel);
		// 生成Excel文件
		PoiExportExcel.poiWriteExcel_To2007(exportExcelList, os);
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
}
