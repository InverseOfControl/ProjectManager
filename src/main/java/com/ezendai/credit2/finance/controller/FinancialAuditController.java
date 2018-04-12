package com.ezendai.credit2.finance.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezendai.credit2.apply.model.BankAccount;
import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.model.Person;
import com.ezendai.credit2.apply.service.BankAccountService;
import com.ezendai.credit2.apply.service.LoanService;
import com.ezendai.credit2.apply.service.PersonService;
import com.ezendai.credit2.apply.vo.LoanVO;
import com.ezendai.credit2.audit.model.ApproveResult;
import com.ezendai.credit2.finance.service.FinanceService;
import com.ezendai.credit2.finance.service.FinancialAuditService;
import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.framework.exception.BusinessException;
import com.ezendai.credit2.framework.util.CollectionUtil;
import com.ezendai.credit2.framework.util.DateUtil;
import com.ezendai.credit2.framework.util.FileUtil;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.system.Credit2Properties;
import com.ezendai.credit2.system.controller.BaseController;
import com.ezendai.credit2.system.enumerate.SysParameterEnum;
import com.ezendai.credit2.system.model.SysLog;
import com.ezendai.credit2.system.model.SysParameter;
import com.ezendai.credit2.system.service.SysLogService;
import com.ezendai.credit2.system.service.SysParameterService;

@Controller
@RequestMapping("/finance/financialAudit")
public class FinancialAuditController extends BaseController {

	@Autowired
	private FinancialAuditService financialAuditService;

	@Autowired
	private LoanService loanService;
	@Autowired
	private PersonService personService;
	@Autowired
	private Credit2Properties credit2Properties;
	@Autowired
	private SysParameterService sysParameterService;
	@Autowired
	private BankAccountService bankAccountService;
	@Autowired
	private FinanceService financeService;
	private static final Logger logger = Logger.getLogger(FinancialAuditController.class);
	@Autowired
	private SysLogService sysLogService;
	@RequestMapping("/list")
	public String init(HttpServletRequest request) {
		/*设置数据字典*/
		Integer userType = ApplicationContext.getUser().getUserType();
		request.setAttribute("userType", userType);
		setDataDictionaryArr(new String[] {EnumConstants.PRODUCT_ID, EnumConstants.LOAN_STATUS, EnumConstants.PRODUCT_TYPE, EnumConstants.CONTRACT_SRC ,EnumConstants.REPAYMENT_PLAN_STATE});
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		return "/finance/financialAudit/financialAuditList";
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getFinacialAuditPage")
	@ResponseBody
	public Map getFinacialAuditPage(LoanVO vo, int rows, int page) {
		if (vo.getStatus() == null) {//状态默认为财务审核
			vo.setStatus(EnumConstants.LoanStatus.财务审核.getValue());
		} else if (vo.getStatus() == 0) {//全选下查询的状态类型
			List<Integer> statusList = new ArrayList<Integer>();
			statusList.add(EnumConstants.LoanStatus.财务审核.getValue());
			statusList.add(EnumConstants.LoanStatus.财务审核退回.getValue());
			statusList.add(EnumConstants.LoanStatus.财务放款.getValue());
			vo.setStatusList(statusList);
			vo.setStatus(null);
		}
		//当传空值的时候传15天前的日期 
		if (vo.getContractConfirmStartDate() == null) {
			vo.setContractConfirmStartDate(DateUtil.getDateBefore(15));
		}
		//合同来源为"全部"
		if(vo.getContractSrc() != null && vo.getContractSrc()==0){
			vo.setContractSrc(null);
		}
		Pager p = new Pager();
		p.setPage(page);
		p.setRows(rows);
		p.setSidx("LOAN.CONTRACT_CONFIRM_DATE");
		p.setSort("ASC");
		vo.setPager(p);
		
		
		if(vo.getSelectedProductId() != null){
			vo.setProductId(vo.getSelectedProductId());
			vo.setSelectedProductId(null);
			
			
		}
		Pager pager = loanService.findWithPg(vo);
		List<Loan> loanList = pager.getResultList();
		if(loanList != null){
			for(int i = 0;i < loanList.size();i++){
				Loan loan=loanList.get(i);
				if(EnumConstants.ProductList.STUDENT_LOAN.getValue().equals(loan.getProductId())){
					BankAccount bankAccount=bankAccountService.getBankAccountDetails(loan.getGrantAccountId());
					loanList.get(i).setGrantAccount(bankAccount);;
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
	 * 财务批量审核
	 * </pre>
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/financialAuditLoans")
	@ResponseBody
	public Map financialAuditLoans(LoanVO loanVo) {
		Map result = new HashMap();
		List<Long> loanIdList = loanVo.getIdList();
		StringBuffer stringBuffer = new StringBuffer("财务审核失败的借款人身份证号为：");
		boolean isShow = false;
		for (Long loanId : loanIdList) {
			// 校验loan状态（STATE）是否是 财务放款（90）
			Loan loan = loanService.get(loanId);
			try {
				if (loan != null) {
					if (loan.getStatus().compareTo(EnumConstants.LoanStatus.财务审核.getValue()) != 0) {
						throw new BusinessException("当前借款状态不是财务审核！");
					}
					// 财务审核
					financialAuditService.financialAudit(loanId);
				}
			} catch (Exception e) {
				String idNum = personService.get(loan.getPersonId()).getIdnum();
				stringBuffer.append(idNum).append(",");
				isShow = true;
				logger.error("审核:" + loanId + ",失败：" + e.getMessage(), e);
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
	 * 财务审核
	 * </pre>
	 *
	 * @param loanId
	 * @return
	 */
	@RequestMapping("/financialAudit")
	@ResponseBody
	public String financialAudit(Long loanId) {
		return financialAuditService.financialAudit(loanId);
	}

	/***
	 * 
	 * <pre>
	 * 财务退回
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
			financialAuditService.financeReturn(approveResult, EnumConstants.LoanBorrowStatus.终止借款.getValue());
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
			Long productId =  Long.valueOf(loan.getProductType());
			model.addAttribute("personId", personId);
			model.addAttribute("productId", productId);
			Person person = personService.get(personId);
			if (person != null) {
				model.addAttribute("personName", person.getName());
			}
		}
		model.addAttribute("optModule", EnumConstants.OptionModule.FINANCE_AUDIT.getValue());
		return "finance/financialAudit/imageUploadView";
	}

	/***
	 * 
	 * <pre>
	 * 查询可导出Excel的条数
	 * </pre>
	 *
	 * @param loanVo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findCountByParams")
	@ResponseBody
	public String findCountByParams(LoanVO loanVo) {
		try {
			if(loanVo.getPersonName()!=null){			
			    loanVo.setPersonName(java.net.URLDecoder.decode(loanVo.getPersonName(), "UTF-8"));
			}
		} catch (UnsupportedEncodingException e1) {
			logger.error("", e1);
		}
		if (loanVo.getStatus() == null) {//状态默认为财务审核
			loanVo.setStatus(EnumConstants.LoanStatus.财务审核.getValue());
		} else if (loanVo.getStatus() == 0) {//全选下查询的状态类型
			List<Integer> statusList = new ArrayList<Integer>();
			statusList.add(EnumConstants.LoanStatus.财务审核.getValue());
			statusList.add(EnumConstants.LoanStatus.财务审核退回.getValue());
			statusList.add(EnumConstants.LoanStatus.财务放款.getValue());
			loanVo.setStatusList(statusList);
			loanVo.setStatus(null);
		}
		//当传空值的时候传15天前的日期 
		if (loanVo.getContractConfirmStartDate() == null) {
			loanVo.setContractConfirmStartDate(DateUtil.getDateBefore(15));
		}
		// 合同来源为"全部"
		if (loanVo.getContractSrc() != null && loanVo.getContractSrc() == 0) {
			loanVo.setContractSrc(null);
		}
		List<Loan> loanList = loanService.findWithPg(loanVo).getResultList();		
		if (CollectionUtil.isNullOrEmpty(loanList)) {
			return "没有查询出数据";
		}
		SysParameter sysParameter = sysParameterService.getByCode(SysParameterEnum.EXCEL_EXPORT_MAX_NO.name());
		if (sysParameter != null) {
			if (loanList.size()>Integer.parseInt(sysParameter.getParameterValue())) {
				return "excel导出条数过多！";
			}
		}
		return "success";

	}

	/***
	 * 
	 * <pre>
	 * 导出Excel
	 * </pre>
	 *
	 * @param loanVo
	 * @return
	 *
	 */
	@SuppressWarnings({ "unchecked" })
	@RequestMapping("/exportExcel")
	@ResponseBody
	public String exportExcel(LoanVO loanVo, HttpServletResponse response) {
		try {
			if(loanVo.getPersonName()!=null){			
			    loanVo.setPersonName(java.net.URLDecoder.decode(loanVo.getPersonName(), "UTF-8"));
			}
		} catch (UnsupportedEncodingException e1) {
			logger.error("", e1);
		}
		if (loanVo.getStatus() == null) {//默认状态为财务审核
			List<Integer> statusList = new ArrayList<Integer>();
			statusList.add(EnumConstants.LoanStatus.财务审核.getValue());
			loanVo.setStatusList(statusList);
		} else if (loanVo.getStatus() == 0) {//全选下查询的状态类型
			List<Integer> statusList = new ArrayList<Integer>();
			statusList.add(EnumConstants.LoanStatus.财务审核.getValue());
			statusList.add(EnumConstants.LoanStatus.财务审核退回.getValue());
			statusList.add(EnumConstants.LoanStatus.财务放款.getValue());
			loanVo.setStatusList(statusList);
			loanVo.setStatus(null);
		}
		// 当传空值的时候传15天前的日期
		if (loanVo.getContractConfirmStartDate() == null) {
			loanVo.setContractConfirmStartDate(DateUtil.getDateBefore(15));
		}
		// 合同来源为"全部"
		if (loanVo.getContractSrc() != null && loanVo.getContractSrc() == 0) {
			loanVo.setContractSrc(null);
		}
		
		Pager pager = loanService.findWithPg(loanVo);
		List<Loan> loanList = pager.getResultList();
		if(loanList != null){
			for(int i = 0;i < loanList.size();i++){
				Loan loan=loanList.get(i);
				if(EnumConstants.ProductList.STUDENT_LOAN.getValue().equals(loan.getProductId())){
					BankAccount bankAccount=bankAccountService.getBankAccountDetails(loan.getGrantAccountId());
					loanList.get(i).setGrantAccount(bankAccount);;
				}
			}
		}
		String dates = DateUtil.getDate(new Date(), "yy-MM-dd");
		//文件名 
		String fileName = "财务审核列表" + dates + ".xlsx";
		String downloadPath = credit2Properties.getDownloadPath();
		File file = new File(downloadPath + File.separator + "finance");
		if (!file.exists()) {//不存在则创建该目录
			file.mkdir();
		}
		OutputStream os;
		try {
			os = new FileOutputStream(downloadPath + File.separator
					+ "finance" + File.separator
					+ fileName.trim().toString());
			//生成Excel文件			
			financialAuditService.exportExcel(loanList,"财务审核", os);
		} catch (FileNotFoundException e) {
			logger.info("生成Excel文件出错"+e.getMessage ());
			e.printStackTrace();
		}
		//下载Escel文件的路径
		String filePath = downloadPath + File.separator + "finance" + File.separator + fileName;
		try {
			//下载Excel文件到客户端
			if (FileUtil.downLoadFile(filePath, response, fileName, "xlsx")) {
				//导出成功后删除导出的文件
				FileUtil.deleteFile(filePath);
				//插入系统日志  
				SysLog sysLog = new SysLog();			
				sysLog.setOptModule(EnumConstants.OptionModule.FINANCE_AUDIT.getValue());
				sysLog.setOptType(EnumConstants.OptionType.FINANCE_AUDIT_EXPORT.getValue());
				sysLog.setRemark("财务审核导出xcel");
				sysLogService.insert(sysLog);
				return "success";
			} else {
				return "下载Excel出错!";
			}
		} catch (Exception e) {
			logger.error("下载Excel出错!",e);
		}
		return "success";

	}


	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
}
