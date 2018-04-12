/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.report.controller;

import java.math.BigDecimal;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.model.Product;
import com.ezendai.credit2.apply.service.GuaranteeService;
import com.ezendai.credit2.apply.service.LoanService;
import com.ezendai.credit2.apply.service.ProductService;
import com.ezendai.credit2.audit.service.RepaymentPlanService;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.report.model.RpDetail;
import com.ezendai.credit2.report.model.Smallrp;
import com.ezendai.credit2.report.service.RpDetailService;
import com.ezendai.credit2.report.service.SmallrpService;
import com.ezendai.credit2.system.model.SysLog;
import com.ezendai.credit2.system.service.SysLogService;


/**   
*    
* 项目名称：credit2-main   
* 类名称：NetBusinessReportController   
* 类描述：   网商贷合同打印
* 创建人：Administrator   
* 创建时间：2015年8月18日 下午4:50:25   
* 修改人：Administrator   
* 修改时间：2015年8月18日 下午4:50:25   
* 修改备注：   
* @version    
*    
*/
@Controller
@RequestMapping("/netBusinessReport")
public class NetBusinessReportController extends ReportController {

	@Autowired
	private ProductService productService;
	@Autowired
	private SmallrpService smallrpService;
	@Autowired
	private RepaymentPlanService repaymentPlanService;
	@Autowired
	private RpDetailService rpDetailService;
	@Autowired
	private SysLogService sysLogService;
	@Autowired
	private LoanService loanService;
	@Autowired
	private GuaranteeService guaranteeService;

	private final String TEL = "400-821-6888";

	/**
	 * 网商贷-个人借款咨询服务风险基金协议
	 *
	 * @return
	 */
	@RequestMapping("/printNetBusinessPersonLoanContract")
	public String printNetBusinessPersonLoanContract(String contractNo, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("contractNo", contractNo);
		map.put("type", EnumConstants.ContractType.CITY_WIDE_PERSON_LOAN.getValue());
		Smallrp smallrp = smallrpService.getSmallrpByContractNo(map);
		if (smallrp != null) {
			model.addAttribute("contractNo", smallrp.getContractNo() + "-004");
			model.addAttribute("year", splitDayString(smallrp.getSignDate(), "year"));
			model.addAttribute("month", splitDayString(smallrp.getSignDate(), "month"));
			model.addAttribute("day", splitDayString(smallrp.getSignDate(), "day"));
			model.addAttribute("cityName", smallrp.getCityName());
			model.addAttribute("areaName", smallrp.getAreaName());
			model.addAttribute("personName", smallrp.getPersonName());
			model.addAttribute("idNum", smallrp.getIdNum());
			model.addAttribute("address", smallrp.getAddress());
			if (smallrp.getEmail() != null) {
				model.addAttribute("email", smallrp.getEmail().toString());
			} else {
				model.addAttribute("email", " ");
			}
			Loan loan = loanService.get(smallrp.getLoanId());
			model.addAttribute("payAmountMax", change(Double.valueOf(loan.getRisk().toString())));
			model.addAttribute("payAmount", (loan.getRisk()));
		}
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.CONTRACT.getValue());
		sysLog.setOptType(EnumConstants.OptionType.CONTRACT_DETAIL.getValue());
		sysLog.setRemark("合同号" + contractNo);
		sysLogService.insert(sysLog);
		return "report/netBusiness/netBusiness_fxjxy";
	}

	/**
	 * 网商贷-借款协议
	 *
	 * @return
	 */
	@RequestMapping("/printNetBusinessLoanContract")
	public String printNetBusinessLoanContract(String contractNo, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("contractNo", contractNo);
		map.put("type", EnumConstants.ContractType.CITY_WIDE_LOAN.getValue());
		Smallrp smallrp = smallrpService.getSmallrpByContractNo(map);
		if (smallrp != null) {
			model.addAttribute("contractNo", smallrp.getContractNo() + "-001");
			model.addAttribute("year", splitDayString(smallrp.getSignDate(), "year"));
			model.addAttribute("month", splitDayString(smallrp.getSignDate(), "month"));
			model.addAttribute("day", splitDayString(smallrp.getSignDate(), "day"));
			model.addAttribute("cityName", smallrp.getCityName());
			model.addAttribute("areaName", smallrp.getAreaName());
			model.addAttribute("personName", smallrp.getPersonName());
			model.addAttribute("idNum", smallrp.getIdNum());
			model.addAttribute("address", smallrp.getAddress());
			if (smallrp.getZipCode() != null) {
				model.addAttribute("zipCode", smallrp.getZipCode());
			} else {
				model.addAttribute("zipCode", " ");
			}
			model.addAttribute("purpose", smallrp.getPurpose());
			model.addAttribute("pactMoney", change(Double.valueOf(smallrp.getPactMoney().toString())));
			model.addAttribute("yi", getMoneyNumAt(smallrp.getPactMoney(), 9));
			model.addAttribute("qianWan", getMoneyNumAt(smallrp.getPactMoney(), 8));
			model.addAttribute("baiWan", getMoneyNumAt(smallrp.getPactMoney(), 7));
			model.addAttribute("shiWan", getMoneyNumAt(smallrp.getPactMoney(), 6));
			model.addAttribute("wan", getMoneyNumAt(smallrp.getPactMoney(), 5));
			model.addAttribute("qian", (getMoneyNumAt(smallrp.getPactMoney(), 4)));
			model.addAttribute("bai", (getMoneyNumAt(smallrp.getPactMoney(), 3)));
			model.addAttribute("shi", (getMoneyNumAt(smallrp.getPactMoney(), 2)));
			String moneyNumAt3 = getMoneyNumAt(smallrp.getPactMoney(), 1);
			model.addAttribute("yuan", moneyNumAt3);
			String moneyNumAt = getMoneyNumAt(smallrp.getPactMoney(), -1);
			model.addAttribute("jiao", moneyNumAt);
			String moneyNumAt2 = getMoneyNumAt(smallrp.getPactMoney(), -2);
			model.addAttribute("fen", moneyNumAt2);
			// 月偿还本息数额
			BigDecimal monthInterestAmount = smallrp.getMonthInterestAmount();
			model.addAttribute("monthInterestAmount", change(Double.valueOf(monthInterestAmount.toString())));
			model.addAttribute("yi2", (getMoneyNumAt(monthInterestAmount, 9)));
			model.addAttribute("qianWan2", (getMoneyNumAt(monthInterestAmount, 8)));
			model.addAttribute("baiWan2", (getMoneyNumAt(monthInterestAmount, 7)));
			model.addAttribute("shiWan2", (getMoneyNumAt(monthInterestAmount, 6)));
			model.addAttribute("wan2", (getMoneyNumAt(monthInterestAmount, 5)));
			model.addAttribute("qian2", (getMoneyNumAt(monthInterestAmount, 4)));
			model.addAttribute("bai2", (getMoneyNumAt(monthInterestAmount, 3)));
			model.addAttribute("shi2", (getMoneyNumAt(monthInterestAmount, 2)));
			model.addAttribute("yuan2", (getMoneyNumAt(monthInterestAmount, 1)));
			model.addAttribute("jiao2", (getMoneyNumAt(monthInterestAmount, -1)));
			model.addAttribute("fen2", (getMoneyNumAt(monthInterestAmount, -2)));
			model.addAttribute("times", (smallrp.getTimes()));
			model.addAttribute("repayDate", (smallrp.getRepayDate()));
			model.addAttribute("repayYear", (splitDayString(smallrp.getStartRepayDate(), "year")));
			model.addAttribute("repayMonth", (splitDayString(smallrp.getStartRepayDate(), "month")));
			model.addAttribute("repayDay", (splitDayString(smallrp.getStartRepayDate(), "day")));
			model.addAttribute("endrepayYear", (splitDayString(smallrp.getEndRepayDate(), "year")));
			model.addAttribute("endrepayMonth", (splitDayString(smallrp.getEndRepayDate(), "month")));
			model.addAttribute("endrepayDay", (splitDayString(smallrp.getEndRepayDate(), "day")));
			model.addAttribute("bankAccountName", (smallrp.getBankAccountName()));
			model.addAttribute("bankaccountNum", (smallrp.getBankAccountNum()));
			model.addAttribute("bankBranchName", (smallrp.getOpeningBank() + smallrp.getBankBranchName()));
		}
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.CONTRACT.getValue());
		sysLog.setOptType(EnumConstants.OptionType.CONTRACT_DETAIL.getValue());
		sysLog.setRemark("合同号" + contractNo);
		sysLogService.insert(sysLog);
		return "report/netBusiness/netBusiness_jkxy";
	}

	/**
	 * 网商贷-个人借款咨询服务协议
	 *
	 * @return
	 */
	@RequestMapping("/printNetBusinessRepaymentContract")
	public String printNetBusinessRepaymentContract(String contractNo, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("contractNo", contractNo);
		map.put("type", EnumConstants.ContractType.CITY_WIDE_REPAYMENT.getValue());
		Smallrp smallrp = smallrpService.getSmallrpByContractNo(map);

		if (smallrp != null) {
			model.addAttribute("contractNo", smallrp.getContractNo());
			model.addAttribute("year", splitDayString(smallrp.getSignDate(), "year"));
			model.addAttribute("month", splitDayString(smallrp.getSignDate(), "month"));
			model.addAttribute("day", splitDayString(smallrp.getSignDate(), "day"));
			model.addAttribute("cityName", smallrp.getCityName());
			model.addAttribute("areaName", smallrp.getAreaName());
			model.addAttribute("personName", smallrp.getPersonName());
			model.addAttribute("idNum", smallrp.getIdNum());
			model.addAttribute("address", smallrp.getAddress());
			if (smallrp.getEmail() != null) {
				model.addAttribute("email", smallrp.getEmail().toString());
			} else {
				model.addAttribute("email", " ");
			}
			model.addAttribute("pactMoney", smallrp.getPactMoney().setScale(2, BigDecimal.ROUND_DOWN).toString());
			model.addAttribute("pactMoneyMax", change(Double.valueOf(smallrp.getPactMoney().toString())));
			Loan loan = loanService.get(smallrp.getLoanId());
			model.addAttribute("assessmentFeesMax", change(Double.valueOf(loan.getAssessment().toString())));
			model.addAttribute("assessmentFees", loan.getAssessment().setScale(2, BigDecimal.ROUND_DOWN).toString());
			model.addAttribute("manageFeesMax", change(Double.valueOf(loan.getbManage().toString())));
			model.addAttribute("manageFees", loan.getbManage().setScale(2, BigDecimal.ROUND_DOWN).toString());
			// 咨询费
			double consultingFeeRate = Double.valueOf(loan.getConsult().toString());
			model.addAttribute("consultingFeeRate", consultingFeeRate);
			model.addAttribute("consultingFeeRateMax", change(Double.valueOf(loan.getConsult().toString())));
			if(smallrp.getBankAccountName() !=null)
				model.addAttribute("bankAccountName", smallrp.getBankAccountName());
			else
				model.addAttribute("bankAccountName", "");
			if(smallrp.getBankAccountNum() !=null)
				model.addAttribute("bankAccountNum", smallrp.getBankAccountNum());
			else
				model.addAttribute("bankAccountNum", "");
			if(smallrp.getOpeningBank() !=null)
				model.addAttribute("openingBank", smallrp.getOpeningBank());
			else
				model.addAttribute("openingBank","");
			if(smallrp.getBankBranchName() !=null)
				model.addAttribute("branchName",smallrp.getBankBranchName());
			else
				model.addAttribute("branchName","");
		}
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.CONTRACT.getValue());
		sysLog.setOptType(EnumConstants.OptionType.CONTRACT_DETAIL.getValue());
		sysLog.setRemark("合同号" + contractNo);
		sysLogService.insert(sysLog);

		return "report/netBusiness/netBusiness_fwxy";
	}

	/**
	 * 网商贷->委托扣款授权书
	 *
	 * @return
	 */
	@RequestMapping("/printNetBusinessEntrustContract")
	public String printNetBusinessEntrustContract(String contractNo, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("contractNo", contractNo);
		map.put("type", EnumConstants.ContractType.CITY_WIDE_ENTRUST.getValue());
		Smallrp smallrp = smallrpService.getSmallrpByContractNo(map);
		model.addAttribute("contractNo", smallrp.getContractNo() + "-002");
		model.addAttribute("personName", smallrp.getPersonName());
		model.addAttribute("idNum", smallrp.getIdNum());
		model.addAttribute("year", splitDayString(smallrp.getSignDate(), "year"));
		model.addAttribute("month", splitDayString(smallrp.getSignDate(), "month"));
		model.addAttribute("day", splitDayString(smallrp.getSignDate(), "day"));
		model.addAttribute("bankAccountName", smallrp.getBankAccountName());
		model.addAttribute("bankAccountNum", smallrp.getBankAccountNum());
		model.addAttribute("openingBank", smallrp.getOpeningBank());
		model.addAttribute("branchName", smallrp.getBankBranchName());
		model.addAttribute("tell", smallrp.getContact());
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.CONTRACT.getValue());
		sysLog.setOptType(EnumConstants.OptionType.CONTRACT_DETAIL.getValue());
		sysLog.setRemark("合同号" + contractNo);
		sysLogService.insert(sysLog);
		return "report/netBusiness/netBusiness_wtkksqs";
	}

	/**
	 * 网商贷-还款温馨提示函
	 *
	 * @return
	 */
	@RequestMapping("/printNetBusinessRepaymentFundContract")
	public String printNetBusinessRepaymentFundContract(String contractNo, Long productId, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("contractNo", contractNo);
		map.put("type", EnumConstants.ContractType.CITY_WIDE_REPAYMENT_FUND.getValue());
		Smallrp smallrp = smallrpService.getSmallrpByContractNo(map);
		List<RpDetail> rpDetailList = rpDetailService.getListByLoanId(smallrp.getLoanId());
		model.addAttribute("contractNo", smallrp.getContractNo() + "-003");
		model.addAttribute("personName", smallrp.getPersonName());
		BigDecimal monthInterestAmount = smallrp.getMonthInterestAmount();
		model.addAttribute("monthAmount", smallrp.getMonthAmount());
		Product product = productService.get(productId);
		BigDecimal overdueInterestRateproduct = product.getOverdueInterestRate();
		BigDecimal sum = monthInterestAmount.multiply(new BigDecimal(smallrp.getTimes()));
		BigDecimal oneDay = sum.multiply(overdueInterestRateproduct);
		model.addAttribute("yu1", (oneDay));
		model.addAttribute("yu15", (oneDay.multiply(new BigDecimal(String.valueOf(15)))));
		model.addAttribute("rpDetailList", rpDetailList);
		model.addAttribute("repayDate", smallrp.getRepayDate());
		model.addAttribute("businessTell", TEL);
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.CONTRACT.getValue());
		sysLog.setOptType(EnumConstants.OptionType.CONTRACT_DETAIL.getValue());
		sysLog.setRemark("合同号" + contractNo);
		sysLogService.insert(sysLog);
		return "report/netBusiness/netBusiness_hkwxtsh";
	}

	/**
	 * 网商贷-担保合同自然人
	 * */
	@RequestMapping("/printNetBusinessNaturalGuaranteeContract")
	public String printNetBusinessNaturalGuaranteeContract(String contractNo, Long guaranteeId, Model model) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("contractNo", contractNo);
		map.put("type", EnumConstants.ContractType.CITY_WIDE_NATURAL_GUARANTEE.getValue());
		String guaranteeName = guaranteeService.get(guaranteeId).getName();
		map.put("guaranteeName", guaranteeName);
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		Smallrp smallrp = smallrpService.getSmallrpByContractNo(map);
		;
		model.addAttribute("contractNo", smallrp.getContractNo());
		model.addAttribute("personName", smallrp.getPersonName());
		model.addAttribute("idNum", smallrp.getIdNum());
		String contractNoJKXY = smallrp.getContractNo().substring(0, smallrp.getContractNo().length() - 3);
		model.addAttribute("contractNoJKXY", contractNoJKXY + "001");
		model.addAttribute("startYear", splitDayString(smallrp.getSignDate(), "year"));
		model.addAttribute("startMonth", splitDayString(smallrp.getSignDate(), "month"));
		model.addAttribute("startDay", splitDayString(smallrp.getSignDate(), "day"));
		model.addAttribute("endYear", splitDayString(smallrp.getEndRepayDate(), "year"));
		model.addAttribute("endMonth", splitDayString(smallrp.getEndRepayDate(), "month"));
		model.addAttribute("endDay", splitDayString(smallrp.getEndRepayDate(), "day"));
		model.addAttribute("quotDay", getQuot(ft.format(smallrp.getEndRepayDate()), ft.format(smallrp.getSignDate())) + 1);
		model.addAttribute("payAmountBig", change(Double.valueOf(smallrp.getPayAmount().toString())));
		model.addAttribute("payAmount", smallrp.getPayAmount());
		model.addAttribute("guaranteeName", smallrp.getGuaranteeName());
		model.addAttribute("guaranteeIdNum", smallrp.getGuaranteeIdNum());
		// 插入系统日志
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.CONTRACT.getValue());
		sysLog.setOptType(EnumConstants.OptionType.CONTRACT_DETAIL.getValue());
		sysLog.setRemark("合同号" + contractNo);
		sysLogService.insert(sysLog);
		return "report/netBusiness/netBusiness_dbhtszrr";
	}

	/**
	 * 网商贷-担保授权书
	 * */
	@RequestMapping("/printNetBusinessEntrustGuaranteeContract")
	public String printNetBusinessEntrustGuaranteeContract(String contractNo, Long guaranteeId, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("contractNo", contractNo);
		map.put("type", EnumConstants.ContractType.CITY_WIDE_ENTRUST_GUARANTEE.getValue());
		String guaranteeName = guaranteeService.get(guaranteeId).getName();
		map.put("guaranteeName", guaranteeName);
		Smallrp smallrp = smallrpService.getSmallrpByContractNo(map);
		model.addAttribute("contractNo", smallrp.getContractNo());
		String contractNum = smallrp.getContractNo();
		String  s=contractNum.substring(contractNum.length() - 3);
		int substr=Integer.valueOf(s)-1;
		DecimalFormat df=new DecimalFormat("-000");
		String contractNoDBS = contractNum.substring(0, contractNum.length() - 4);
		model.addAttribute("contractNoDBS", contractNoDBS + df.format(substr));
		model.addAttribute("personName", smallrp.getPersonName());
		model.addAttribute("idNum", smallrp.getIdNum());
		model.addAttribute("year", splitDayString(smallrp.getSignDate(), "year"));
		model.addAttribute("month", splitDayString(smallrp.getSignDate(), "month"));
		model.addAttribute("day", splitDayString(smallrp.getSignDate(), "day"));
		model.addAttribute("bankAccountName", smallrp.getBankAccountName());
		model.addAttribute("bankAccountNum", smallrp.getBankAccountNum());
		model.addAttribute("openingBank", smallrp.getOpeningBank());
		model.addAttribute("branchName", smallrp.getBankBranchName());
		model.addAttribute("tell", smallrp.getContact());
		model.addAttribute("guaranteeName", smallrp.getGuaranteeName());
		model.addAttribute("guaranteeIdNum", smallrp.getGuaranteeIdNum());
		// 插入系统日志
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.CONTRACT.getValue());
		sysLog.setOptType(EnumConstants.OptionType.CONTRACT_DETAIL.getValue());
		sysLog.setRemark("合同号" + contractNo);
		sysLogService.insert(sysLog);
		return "report/netBusiness/netBusiness_wtkksqsdbr";
	}

	/**
	 * 网商贷-法人担保合同
	 * */
	@RequestMapping("/printNetBusinessNaturalLegalContract")
	public String printNetBusinessNaturalLegalContract(String contractNo, Long guaranteeId, Model model) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("contractNo", contractNo);
		map.put("type", EnumConstants.ContractType.CITY_WIDE_NATURAL_LEGAL.getValue());
		String legalGuarantee = guaranteeService.get(guaranteeId).getName();
		map.put("legalGuarantee", legalGuarantee);
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		Smallrp smallrp = smallrpService.getSmallrpByContractNo(map);
		model.addAttribute("contractNo", smallrp.getContractNo());
		model.addAttribute("personName", smallrp.getPersonName());
		model.addAttribute("idNum", smallrp.getIdNum());
		String contractNoJKXY = smallrp.getContractNo().substring(0, smallrp.getContractNo().length() - 3);
		model.addAttribute("contractNoJKXY", contractNoJKXY + "001");
		model.addAttribute("startYear", splitDayString(smallrp.getSignDate(), "year"));
		model.addAttribute("startMonth", splitDayString(smallrp.getSignDate(), "month"));
		model.addAttribute("startDay", splitDayString(smallrp.getSignDate(), "day"));
		model.addAttribute("endYear", splitDayString(smallrp.getEndRepayDate(), "year"));
		model.addAttribute("endMonth", splitDayString(smallrp.getEndRepayDate(), "month"));
		model.addAttribute("endDay", splitDayString(smallrp.getEndRepayDate(), "day"));
		model.addAttribute("quotDay", getQuot(ft.format(smallrp.getEndRepayDate()), ft.format(smallrp.getSignDate())) + 1);
		model.addAttribute("payAmountBig", change(Double.valueOf(smallrp.getPactMoney().toString())));
		model.addAttribute("payAmount", smallrp.getPactMoney());
		model.addAttribute("legalGuarantee", smallrp.getLegalGuarantee());
		// 插入系统日志
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.CONTRACT.getValue());
		sysLog.setOptType(EnumConstants.OptionType.CONTRACT_DETAIL.getValue());
		sysLog.setRemark("合同号" + contractNo);
		sysLogService.insert(sysLog);
		return "report/netBusiness/netBusiness_dbhtsfr";
	}
}
