/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.report.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ezendai.credit2.apply.model.BankAccount;
import com.ezendai.credit2.apply.model.ChannelPlanCheck;
import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.model.Product;
import com.ezendai.credit2.apply.service.BankAccountService;
import com.ezendai.credit2.apply.service.ChannelPlanCheckService;
import com.ezendai.credit2.apply.service.LoanService;
import com.ezendai.credit2.apply.service.ProductService;
import com.ezendai.credit2.audit.model.RepaymentPlan;
import com.ezendai.credit2.audit.service.RepaymentPlanService;
import com.ezendai.credit2.framework.util.DateUtil;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.report.model.RpDetail;
import com.ezendai.credit2.report.model.Smallrp;
import com.ezendai.credit2.report.service.RpDetailService;
import com.ezendai.credit2.report.service.SmallrpService;
import com.ezendai.credit2.system.enumerate.SysParameterEnum;
import com.ezendai.credit2.system.model.Organ;
import com.ezendai.credit2.system.model.SysLog;
import com.ezendai.credit2.system.model.SysParameter;
import com.ezendai.credit2.system.service.OrganBankService;
import com.ezendai.credit2.system.service.OrganService;
import com.ezendai.credit2.system.service.SysLogService;
import com.ezendai.credit2.system.service.SysParameterService;

/**
 * 
 * 项目名称：credit2-main 类名称：StudentLoanReportController 类描述： 创建人：liboyan
 * 创建时间：2015年10月14日 上午9:36:56 修改人：liboyan 修改时间：2015年10月14日 上午9:36:56 修改备注：
 * 
 * @version
 * 
 */
@Controller
@RequestMapping("/studentLoanReport")
public class StudentLoanReportController extends ReportController {

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
	private BankAccountService bankAccountService;
	@Autowired
	private OrganService organService;
	@Autowired
	private OrganBankService organBankService;
	@Autowired
	private ChannelPlanCheckService channelPlanCheckService;
	@Autowired
	private SysParameterService sysParameterService;
	

	private final String TEL = "4008216888";

	/**
	 * 助学贷-个人借款咨询服务风险基金协议
	 *
	 * @return
	 */
	@RequestMapping("/printStudentLoanPersonLoanContract")
	public String printStudentLoanPersonLoanContract(String contractNo, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("contractNo", contractNo);
		map.put("type", EnumConstants.ContractType.STUDENT_PERSON_LOAN.getValue());
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
			model.addAttribute("payAmount", (loan.getRisk().setScale(2,BigDecimal.ROUND_DOWN).toString()));
		}
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.CONTRACT.getValue());
		sysLog.setOptType(EnumConstants.OptionType.CONTRACT_DETAIL.getValue());
		sysLog.setRemark("合同号" + contractNo);
		sysLogService.insert(sysLog);
		if(isNewContract(smallrp)){
			return "report/studentLoanNew/studentLoan_fxjxy";
		}else{
			return "report/studentLoan/studentLoan_fxjxy";
		}
	}

	/**
	 * 助学贷-借款协议
	 *
	 * @return
	 */
	@RequestMapping("/printStudentLoanContract")
	public String printStudentLoanContract(String contractNo, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("contractNo", contractNo);
		map.put("type", EnumConstants.ContractType.STUDENT_LAN.getValue());
		Smallrp smallrp = smallrpService.getSmallrpByContractNo(map);
		Loan loan = loanService.get(smallrp.getLoanId());
		ChannelPlanCheck channelPlanCheck = channelPlanCheckService.getReplyById(loan.getSchemeID());
		List<RpDetail> rpDetailList = rpDetailService.getListByLoanId(smallrp.getLoanId());
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
			model.addAttribute("pactMoney", change(Double.valueOf(loan.getPactMoney().toString())));
			model.addAttribute("yi", getMoneyNumAt(loan.getPactMoney(), 9));
			model.addAttribute("qianWan", getMoneyNumAt(loan.getPactMoney(), 8));
			model.addAttribute("baiWan", getMoneyNumAt(loan.getPactMoney(), 7));
			model.addAttribute("shiWan", getMoneyNumAt(loan.getPactMoney(), 6));
			model.addAttribute("wan", getMoneyNumAt(loan.getPactMoney(), 5));
			model.addAttribute("qian", (getMoneyNumAt(loan.getPactMoney(), 4)));
			model.addAttribute("bai", (getMoneyNumAt(loan.getPactMoney(), 3)));
			model.addAttribute("shi", (getMoneyNumAt(loan.getPactMoney(), 2)));
			model.addAttribute("yuan", getMoneyNumAt(loan.getPactMoney(), 1));
			model.addAttribute("jiao", getMoneyNumAt(loan.getPactMoney(), -1));
			model.addAttribute("fen", getMoneyNumAt(loan.getPactMoney(), -2));

			boolean faig = false;
			// 第一阶段
			BigDecimal monthInterestAmount1 = rpDetailList.get(0).getRepayAmount();
			model.addAttribute("monthInterestAmount1", change(Double.valueOf(monthInterestAmount1.toString())));
			model.addAttribute("yi1", (getMoneyNumAt(monthInterestAmount1, 9)));
			model.addAttribute("qianWan1", (getMoneyNumAt(monthInterestAmount1, 8)));
			model.addAttribute("baiWan1", (getMoneyNumAt(monthInterestAmount1, 7)));
			model.addAttribute("shiWan1", (getMoneyNumAt(monthInterestAmount1, 6)));
			model.addAttribute("wan1", (getMoneyNumAt(monthInterestAmount1, 5)));
			model.addAttribute("qian1", (getMoneyNumAt(monthInterestAmount1, 4)));
			model.addAttribute("bai1", (getMoneyNumAt(monthInterestAmount1, 3)));
			model.addAttribute("shi1", (getMoneyNumAt(monthInterestAmount1, 2)));
			model.addAttribute("yuan1", (getMoneyNumAt(monthInterestAmount1, 1)));
			model.addAttribute("jiao1", (getMoneyNumAt(monthInterestAmount1, -1)));
			model.addAttribute("fen1", (getMoneyNumAt(monthInterestAmount1, -2)));
			model.addAttribute("times1", (channelPlanCheck.getToTerm1()));
			// 第二阶段
			if (loan.getRequestTime()-channelPlanCheck.getToTerm1() > 0)
				faig = true;
			BigDecimal monthInterestAmount2 = rpDetailList.get(rpDetailList.size()-1).getRepayAmount();
			model.addAttribute("faig", faig);
			model.addAttribute("monthInterestAmount2", faig ? change(Double.valueOf(monthInterestAmount2.toString())) : "");
			model.addAttribute("yi2", faig ? getMoneyNumAt(monthInterestAmount2, 9) : "");
			model.addAttribute("qianWan2", faig ? getMoneyNumAt(monthInterestAmount2, 8) : "");
			model.addAttribute("baiWan2", faig ? getMoneyNumAt(monthInterestAmount2, 7) : "");
			model.addAttribute("shiWan2", faig ? getMoneyNumAt(monthInterestAmount2, 6) : "");
			model.addAttribute("wan2", faig ? getMoneyNumAt(monthInterestAmount2, 5) : "");
			model.addAttribute("qian2", faig ? getMoneyNumAt(monthInterestAmount2, 4) : "");
			model.addAttribute("bai2", faig ? getMoneyNumAt(monthInterestAmount2, 3) : "");
			model.addAttribute("shi2", faig ? getMoneyNumAt(monthInterestAmount2, 2) : "");
			model.addAttribute("yuan2", faig ? getMoneyNumAt(monthInterestAmount2, 1) : "");
			model.addAttribute("jiao2", faig ? getMoneyNumAt(monthInterestAmount2, -1) : "");
			model.addAttribute("fen2", faig ? getMoneyNumAt(monthInterestAmount2, -2) : "");
			model.addAttribute("times2", faig ? channelPlanCheck.getToTerm2() : "");
			model.addAttribute("repayDate", smallrp.getRepayDate());
			model.addAttribute("repayDate2", faig ? smallrp.getRepayDate() : "");
			// 第一阶段还款起止日期
			Integer year = Integer.valueOf(splitDayString(smallrp.getStartRepayDate(), "year"));
			Integer month = Integer.valueOf(splitDayString(smallrp.getStartRepayDate(), "month"));
			Integer day = Integer.valueOf(splitDayString(smallrp.getStartRepayDate(), "day"));
			Integer months = channelPlanCheck.getToTerm1();
			if (months == 1)
				model.addAttribute("date", "");
			else
				model.addAttribute("date", faig ? "至&nbsp;&nbsp;" + getDateAfter(months - 1, year, month, day) : "");

			model.addAttribute("date2", faig ? getDateAfter(months, year, month, day) : "");
			model.addAttribute("repayYear", (splitDayString(smallrp.getStartRepayDate(), "year")));
			model.addAttribute("repayMonth", (splitDayString(smallrp.getStartRepayDate(), "month")));
			model.addAttribute("repayDay", (splitDayString(smallrp.getStartRepayDate(), "day")));
			model.addAttribute("repayYear2", (splitDayString(smallrp.getStartRepayDate(), "year")));
			model.addAttribute("repayMonth2", (splitDayString(smallrp.getStartRepayDate(), "month")));
			model.addAttribute("repayDay2", (splitDayString(smallrp.getStartRepayDate(), "day")));
			model.addAttribute("endrepayYear", (splitDayString(smallrp.getEndRepayDate(), "year")));
			model.addAttribute("endrepayMonth", (splitDayString(smallrp.getEndRepayDate(), "month")));
			model.addAttribute("endrepayDay", (splitDayString(smallrp.getEndRepayDate(), "day")));
			if (smallrp.getBankAccountName() != null)
				model.addAttribute("bankAccountName", smallrp.getBankAccountName());
			else
				model.addAttribute("bankAccountName", "");
			if (smallrp.getBankAccountNum() != null)
				model.addAttribute("bankAccountNum", smallrp.getBankAccountNum());
			else
				model.addAttribute("bankAccountNum", "");
			if (smallrp.getOpeningBank() != null)
				model.addAttribute("openingBank", smallrp.getOpeningBank());
			else
				model.addAttribute("openingBank", "");
			if (smallrp.getBankBranchName() != null)
				model.addAttribute("branchName", smallrp.getBankBranchName());
			else
				model.addAttribute("branchName", "");
			// 机构银行账号信息
			BankAccount bankAccount = bankAccountService.get(loan.getGrantAccountId());
			if (bankAccount != null && bankAccount.getAccountName() != null)
				model.addAttribute("organBankAccountName", bankAccount.getAccountName());
			else
				model.addAttribute("organBankAccountName", "");
			if (bankAccount != null && bankAccount.getAccount() != null)
				model.addAttribute("organBankAccountNum", bankAccount.getAccount());
			else
				model.addAttribute("organBankAccountNum", "");
			if (bankAccount != null && bankAccount.getBankName() != null)
				model.addAttribute("organOpeningBank", bankAccount.getBankName());
			else
				model.addAttribute("organOpeningBank", "");
			if (bankAccount != null && bankAccount.getBranchName() != null)
				model.addAttribute("organBranchName", bankAccount.getBranchName());
			else
				model.addAttribute("organBranchName", "");
		}
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.CONTRACT.getValue());
		sysLog.setOptType(EnumConstants.OptionType.CONTRACT_DETAIL.getValue());
		sysLog.setRemark("合同号" + contractNo);
		sysLogService.insert(sysLog);
		if(isNewContract(smallrp)){
			return "report/studentLoanNew/studentLoan_jkxy";
		}else{
			return "report/studentLoan/studentLoan_jkxy";
		}
	}

	/**
	 * 助学贷-个人借款咨询服务协议
	 *
	 * @return
	 */
	@RequestMapping("/printStudentLoanRepaymentContract")
	public String printStudentLoanRepaymentContract(String contractNo, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("contractNo", contractNo);
		map.put("type", EnumConstants.ContractType.STUDENT_REPAYMENT.getValue());
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
			Loan loan = loanService.get(smallrp.getLoanId());
			model.addAttribute("pactMoneyMax", change(Double.valueOf(loan.getPactMoney().toString())));
			model.addAttribute("pactMoney", loan.getPactMoney().setScale(2, BigDecimal.ROUND_DOWN).toString());
			// 评估费
			model.addAttribute("assessmentFeesMax", change(Double.valueOf(loan.getAssessment().toString())));
			model.addAttribute("assessmentFees", loan.getAssessment().setScale(2, BigDecimal.ROUND_DOWN).toString());
			double bmanageFees = Double.valueOf(loan.getbManage().toString());
			model.addAttribute("bmanageFeesMax", change(bmanageFees));
			model.addAttribute("bmanageFees", loan.getbManage().setScale(2, BigDecimal.ROUND_DOWN).toString());
			double cmanageFees = Double.valueOf(loan.getcManage().toString());
			model.addAttribute("cmanageFeesMax", change(cmanageFees));
			model.addAttribute("cmanageFees", loan.getcManage().setScale(2, BigDecimal.ROUND_DOWN).toString());
			// 管理费合计
			model.addAttribute("sumManageFeesMax", change(cmanageFees + bmanageFees));
			model.addAttribute("sumManageFees", cmanageFees + bmanageFees);
			// 递减费
			model.addAttribute("diffRefund", smallrp.getDiffRefund().setScale(2, BigDecimal.ROUND_DOWN).toString());
			// 咨询费
			double consultingFeeRate = Double.valueOf(loan.getConsult().toString());
			model.addAttribute("consultingFeeRate", consultingFeeRate);
			model.addAttribute("consultingFeeRateMax", change(Double.valueOf(loan.getConsult().toString())));
			// 合计费用
			if (loan.getServiceCharges() != null) {
				model.addAttribute("total_cost", loan.getServiceCharges().setScale(2, BigDecimal.ROUND_DOWN).toString());
				model.addAttribute("total_costMax", change(Double.valueOf(loan.getServiceCharges().toString())));
			} else {
				model.addAttribute("total_cost", "");
				model.addAttribute("total_costMax", "");
			}
			RepaymentPlan byLoanIdAndCurNum = repaymentPlanService.getByLoanIdAndCurNum(loan.getId(), loan.getAuditTime() - loan.getResidualTime() + 1);
			// 退费
			if(byLoanIdAndCurNum != null)
				model.addAttribute("refund", byLoanIdAndCurNum.getRefund().setScale(2, BigDecimal.ROUND_DOWN).toString());
			else//最后一期
				model.addAttribute("refund", "0.00");
			if (smallrp.getBankAccountName() != null)
				model.addAttribute("bankAccountName", smallrp.getBankAccountName());
			else
				model.addAttribute("bankAccountName", "");
			if (smallrp.getBankAccountNum() != null)
				model.addAttribute("bankAccountNum", smallrp.getBankAccountNum());
			else
				model.addAttribute("bankAccountNum", "");
			if (smallrp.getOpeningBank() != null)
				model.addAttribute("openingBank", smallrp.getOpeningBank());
			else
				model.addAttribute("openingBank", "");
			if (smallrp.getBankBranchName() != null)
				model.addAttribute("branchName", smallrp.getBankBranchName());
			else
				model.addAttribute("branchName", "");

			// 机构银行账号信息
				BankAccount bankAccount = bankAccountService.get(loan.getGrantAccountId());
				if (bankAccount != null && bankAccount.getAccountName() != null)
					model.addAttribute("organBankAccountName", bankAccount.getAccountName());
				else
					model.addAttribute("organBankAccountName", "");
				if (bankAccount != null && bankAccount.getAccount() != null)
					model.addAttribute("organBankAccountNum", bankAccount.getAccount());
				else
					model.addAttribute("organBankAccountNum", "");
				if (bankAccount != null && bankAccount.getBankName() != null)
					model.addAttribute("organOpeningBank", bankAccount.getBankName());
				else
					model.addAttribute("organOpeningBank", "");
				if (bankAccount != null && bankAccount.getBranchName() != null)
					model.addAttribute("organBranchName", bankAccount.getBranchName());
				else
					model.addAttribute("organBranchName", "");
		}
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.CONTRACT.getValue());
		sysLog.setOptType(EnumConstants.OptionType.CONTRACT_DETAIL.getValue());
		sysLog.setRemark("合同号" + contractNo);
		sysLogService.insert(sysLog);
		if(isNewContract(smallrp)){
			return "report/studentLoanNew/studentLoan_fwxy";
		}else{
			return "report/studentLoan/studentLoan_fwxy";
		}
	}

	/**
	 * 助学贷->委托扣款授权书
	 *
	 * @return
	 */
	@RequestMapping("/printStudentLoanEntrustContract")
	public String printStudentLoanEntrustContract(String contractNo, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("contractNo", contractNo);
		map.put("type", EnumConstants.ContractType.STUDENT_ENTRUST_GUARANTEE.getValue());
		Smallrp smallrp = smallrpService.getSmallrpByContractNo(map);
		model.addAttribute("contractNo", smallrp.getContractNo() + "-002");
		model.addAttribute("contractNnum", smallrp.getContractNo() + "-001");
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
		
		if(isNewContract(smallrp)){
			return "report/studentLoanNew/studentLoan_wtkksqs";
		}else{
			return "report/studentLoan/studentLoan_wtkksqs";
		}
	}

	/**
	 * 助学贷-还款温馨提示函
	 *
	 * @return
	 */
	@RequestMapping("/printStudentLoanRepaymentFundContract")
	public String printStudentLoanRepaymentFundContract(String contractNo, Long productId, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("contractNo", contractNo);
		map.put("type", EnumConstants.ContractType.STUDENT_REPAYMENT_FUND.getValue());
		Smallrp smallrp = smallrpService.getSmallrpByContractNo(map);
		List<RpDetail> rpDetailList = rpDetailService.getListByLoanId(smallrp.getLoanId());
		model.addAttribute("contractNo", smallrp.getContractNo() + "-003");
		model.addAttribute("personName", smallrp.getPersonName());
		model.addAttribute("monthAmount", smallrp.getMonthAmount());
		SysParameter ownCardName = sysParameterService.getByCode(SysParameterEnum.OWN_CARD_NAME.name());
		model.addAttribute("cardName", ownCardName.getParameterValue());
		SysParameter ownCardNum = sysParameterService.getByCode(SysParameterEnum.OWN_CARD_NUM.name());
		model.addAttribute("cardNum", ownCardNum.getParameterValue());
		SysParameter ownCardBank = sysParameterService.getByCode(SysParameterEnum.OWN_CARD_BANK.name());
		model.addAttribute("cardBank", ownCardBank.getParameterValue());
		Product product = productService.get(productId);
		BigDecimal overdueInterestRateproduct = product.getOverdueInterestRate();
		BigDecimal sum = new BigDecimal(0);
		for (RpDetail rpDetail : rpDetailList) {
			sum = sum.add(rpDetail.getRepayAmount());
		}
		BigDecimal oneDay = sum.multiply(overdueInterestRateproduct);
		model.addAttribute("yu1", oneDay.setScale(2, BigDecimal.ROUND_HALF_UP));
		model.addAttribute("yu15", oneDay.multiply(new BigDecimal(String.valueOf(15))).setScale(2, BigDecimal.ROUND_HALF_UP));
		model.addAttribute("rpDetailList", rpDetailList);
		model.addAttribute("repayDate", smallrp.getRepayDate());
		model.addAttribute("businessTell", TEL);
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.CONTRACT.getValue());
		sysLog.setOptType(EnumConstants.OptionType.CONTRACT_DETAIL.getValue());
		sysLog.setRemark("合同号" + contractNo);
		sysLogService.insert(sysLog);
		
		if(isNewContract(smallrp)){
			return "report/studentLoanNew/studentLoan_hkwxtsh";
		}else{
			return "report/studentLoan/studentLoan_hkwxtsh";
		}
	}

	public String getDateAfter(int months, Integer year, Integer moth, Integer day) {
		Calendar c = new GregorianCalendar(year, moth, day);
		c.add(Calendar.MONTH, months - 1);
		Date date = c.getTime();
		return DateUtil.defaultFormatDay(date);
	}
	
	private boolean isNewContract(Smallrp smallrp){
		SysParameter eduNewContract=sysParameterService.getByCode(SysParameterEnum.EDU_NEW_CONTRACT.name());
		SysParameter eduNewContractTime = sysParameterService.getByCode(SysParameterEnum.EDU_NEW_CONTRACT_TIME.name()); 
		try {
			if(eduNewContract != null && eduNewContractTime != null&& 
					eduNewContract.getParameterValue().equals("1") && smallrp.getModifiedTime().after(DateUtil.strToDateTime(eduNewContractTime.getParameterValue()))){
				return true;
			}else{
				return false;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
