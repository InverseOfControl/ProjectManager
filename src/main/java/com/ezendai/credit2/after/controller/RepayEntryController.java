package com.ezendai.credit2.after.controller;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezendai.credit2.after.service.RepayEntryService;
import com.ezendai.credit2.after.service.SpecialRepaymentService;
import com.ezendai.credit2.after.view.RepayEntry;
import com.ezendai.credit2.after.vo.RepayEntryDetailsVO;
import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.service.LoanService;
import com.ezendai.credit2.apply.vo.LoanVO;
import com.ezendai.credit2.framework.constant.Constants;
import com.ezendai.credit2.framework.exception.ExcelException;
import com.ezendai.credit2.framework.util.CommonUtil;
import com.ezendai.credit2.framework.util.DateUtil;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.system.controller.BaseController;
import com.ezendai.credit2.system.enumerate.SysParameterEnum;
import com.ezendai.credit2.system.service.SysParameterService;

/**
 * <pre>
 * 还款录入
 * </pre>
 *
 * @author chenqi
 * @version $Id: RepayEntryController.java, v 0.1 2014年12月11日 上午9:28:49 chenqi Exp $
 */
@Controller
@RequestMapping("/after/repayEntry")
public class RepayEntryController extends BaseController {

	@Autowired
	private RepayEntryService repayEntryService;
	@Autowired
	private LoanService loanService;
	@Autowired
	private SpecialRepaymentService specialRepaymentService;
	@Autowired
	private SysParameterService sysParameterService;
	
	private static final Logger logger = Logger.getLogger(RepayEntryController.class);
	
	
	@RequestMapping("/saveRepay")
	@ResponseBody
	public String saveRepay(RepayEntryDetailsVO repayEntryDetailsVO) {
		return repayEntryService.saveRepay(repayEntryDetailsVO);
	}

	@RequestMapping("/upload")
	@ResponseBody
	public String batchUpload(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = repayEntryService.batchUpload(request, response);
		String result = resultMap.get("ErrorMsg").toString();
		if (!StringUtils.isEmpty(result)) {
			throw new ExcelException(resultMap.get("ErrorMsg").toString());
		}
		return null;
	}

	//打开车贷编辑
	@RequestMapping("/repayEntryModify")
	public String carLoanModify() {
		return "after/repayEntry/repayEntryModify";
	}

	@RequestMapping("/list")
	public String list(HttpServletRequest request) {
		setDataDictionaryArr(new String[] { EnumConstants.PRODUCT_ID, EnumConstants.LOAN_STATUS });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		return "after/repayEntry/repayEntryList";
	}

	@RequestMapping("/repayEdit")
	@ResponseBody
	public RepayEntryDetailsVO viewEdit(Long loanId) {
		return repayEntryService.viewEdit(loanId);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/list.json")
	@ResponseBody
	public Map list(@RequestParam(value = Constants.PAGE_NUMBER_NAME, defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page,
					@RequestParam(value = Constants.PAGE_ROWS_NAME, defaultValue = Constants.DEFAULT_PAGE_ROWS) int pageSize, @ModelAttribute("vo") LoanVO vo, HttpServletRequest request) {
		//Long userId = ApplicationContext.getUser().getId();
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		List<RepayEntry> repayEntryList = new ArrayList<RepayEntry>();

		if (!StringUtils.isEmpty(vo.getPersonIdnum()) || !StringUtils.isEmpty(vo.getPersonFuzzyName())) {

			List<Integer> statusList = new ArrayList<Integer>();
			statusList.add(EnumConstants.LoanStatus.正常.getValue());
			statusList.add(EnumConstants.LoanStatus.逾期.getValue());
			vo.setStatusList(statusList);

			Pager p = new Pager();
			p.setPage(page);
			p.setRows(pageSize);
			p.setSidx("SIGN_DATE");
			p.setSort("ASC");
			vo.setPager(p);
			Pager pager = loanService.findRepayEntryWithPG(vo);
			List<Loan> loanList = pager.getResultList();
			for (Loan loan : loanList) {
				RepayEntry repayEntry = new RepayEntry();
				repayEntry.setLoanId(loan.getId());
				if (loan.getSalesDept() != null) {
					repayEntry.setSalesDeptName(loan.getSalesDept().getName());
				}
				if (loan.getPerson() != null) {
					repayEntry.setPersonName(loan.getPerson().getName());
					repayEntry.setPersonIdnum(loan.getPerson().getIdnum());
				}
				repayEntry.setProductId(loan.getProductId());
				repayEntry.setProductType(loan.getProductType());
				repayEntry.setPactMoney(loan.getPactMoney());
				repayEntry.setRepayPeriod(loan.getTime());
				repayEntry.setLoanStatus(loan.getStatus());
				repayEntry.setOperations("还款");
				setExtRepayEntry(loan.getId(), vo, repayEntry, repayEntryList);
			}
			result.put("total", pager.getTotalCount());
			result.put("rows", repayEntryList);
		} 
		else
		{
			result.put("total", -1);
			result.put("rows", repayEntryList);
		}
		return result;
	}

	private void setExtRepayEntry(Long loanId, LoanVO vo, RepayEntry repayEntry, List<RepayEntry> repayEntryList) {
		boolean oneTimeRepayment = specialRepaymentService.isOneTimeRepayment(loanId);
		if (oneTimeRepayment) {
			repayEntry.setIsOneTimeRepayment("已申请");
		} else {
			repayEntry.setIsOneTimeRepayment("未申请");
		}
		Date nowDate = DateUtil.getToday();
		BigDecimal reliefOfFine = specialRepaymentService.getReliefOfFine(nowDate, loanId);
		repayEntry.setReliefOfFine(reliefOfFine);
		repayEntryList.add(repayEntry);
	}
	
	@ResponseBody
	@RequestMapping(value = "/importRepayData", method = RequestMethod.POST)
	public String importBusinessData(HttpServletRequest request, HttpServletResponse response) {
		String result = "";
		try {
			boolean ipValidationCheck = sysParameterService.ipValidationCheck(SysParameterEnum.CREDIT_IP.name(), CommonUtil.getIp(request));
			//判断发起请求的地址,防止恶意请求
			if (!ipValidationCheck) {
				result= "发起请求的IP地址错误!";
				response.addHeader("result", URLEncoder.encode(result, "utf-8"));
				return result;
			}
			Map<String, Object> resultMap = repayEntryService.batchUpload(request, response);
			result = resultMap.get("ErrorMsg").toString();	
			if(!StringUtils.isEmpty(result))
			{
				response.addHeader("result", URLEncoder.encode(result, "utf-8"));
			}
		}
		catch (Exception e) {
			logger.error("调用 importRepayData异常 : ", e);
			if (e != null) {
				result = e.getMessage();
			}
		}
		logger.info("importRepayData result: " + result);
		return result;
	}
}
