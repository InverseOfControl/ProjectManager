package com.ezendai.credit2.report.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezendai.credit2.apply.model.Extension;
import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.model.Product;
import com.ezendai.credit2.apply.service.ExtensionService;
import com.ezendai.credit2.apply.service.GuaranteeService;
import com.ezendai.credit2.apply.service.LoanExtensionService;
import com.ezendai.credit2.apply.service.LoanService;
import com.ezendai.credit2.apply.service.ProductService;
import com.ezendai.credit2.apply.vo.LoanVO;
import com.ezendai.credit2.audit.model.RepaymentPlan;
import com.ezendai.credit2.audit.service.ContractService;
import com.ezendai.credit2.audit.service.RepaymentPlanService;
import com.ezendai.credit2.framework.util.StringUtil;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.master.model.BaseArea;
import com.ezendai.credit2.master.model.WorkPlaceInfo;
import com.ezendai.credit2.master.service.BaseAreaService;
import com.ezendai.credit2.master.service.WorkPlaceInfoService;
import com.ezendai.credit2.report.model.RpDetail;
import com.ezendai.credit2.report.model.Smallrp;
import com.ezendai.credit2.report.service.RpDetailService;
import com.ezendai.credit2.report.service.SmallrpService;
import com.ezendai.credit2.system.enumerate.SysParameterEnum;
import com.ezendai.credit2.system.model.SysLog;
import com.ezendai.credit2.system.model.SysParameter;
import com.ezendai.credit2.system.service.SysLogService;
import com.ezendai.credit2.system.service.SysParameterService;
import com.ibm.icu.util.Calendar;

/*
 * author:zongwl
 */
@Controller
@RequestMapping("/report")
public class ReportController {
	@Autowired
	private LoanExtensionService loanExtensionService;

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
	private ExtensionService extensionService;
	@Autowired
	private LoanService loanService;
	@Autowired
	private BaseAreaService baseAreaService;
	@Autowired
	private WorkPlaceInfoService workPlaceInfoService;
	@Autowired
	private ContractService contractService;
	@Autowired
	private SysParameterService sysParameterService;
	
		
	private static final Logger logger = Logger.getLogger(ReportController.class);

	@Autowired
	private GuaranteeService guaranteeService;

	@RequestMapping("/report1")
	public String reportOne() {
		return "report/report1";
	}

	@RequestMapping("/report2")
	public String reportTwo() {
		return "report/report2";
	}

	@RequestMapping("/report3")
	public String reportThree() {
		return "report/report3";
	}

	@RequestMapping("/reportFileShow")
	public String reportFileShow() {
		return "report/reportFileShow";
	}

	@RequestMapping("/report21")
	public String report21() {
		return "report/carCredit/test1";
	}

	@RequestMapping("/report22")
	public String report22() {
		return "report/carCredit/test2";
	}

	@RequestMapping("/report23")
	public String report23() {
		return "report/carCredit/test3";
	}

	@RequestMapping("/report24")
	public String report24() {
		return "report/carCredit/test4";
	}

	@RequestMapping("/dbhtsfr")
	public String reportDbhtsfr(String contractNo, Long guaranteeId, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("contractNo", contractNo);
		map.put("type", EnumConstants.ContractType.PEANUTS_NATURAL_LEGAL.getValue());
		String legalGuarantee = guaranteeService.get(guaranteeId).getName();
		map.put("legalGuarantee", legalGuarantee);
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		Smallrp smallrp = smallrpService.getSmallrpByContractNo(map);
		model.addAttribute("contractNo", smallrp.getContractNo() + "-005");
		model.addAttribute("personName", smallrp.getPersonName());
		model.addAttribute("idNum", smallrp.getIdNum());
		model.addAttribute("contractNoJKXY", smallrp.getContractNo() + "-001");
		model.addAttribute("startYear", splitDayString(smallrp.getSignDate(), "year"));
		model.addAttribute("startMonth", splitDayString(smallrp.getSignDate(), "month"));
		model.addAttribute("startDay", splitDayString(smallrp.getSignDate(), "day"));
		model.addAttribute("endYear", splitDayString(smallrp.getEndRepayDate(), "year"));
		model.addAttribute("endMonth", splitDayString(smallrp.getEndRepayDate(), "month"));
		model.addAttribute("endDay", splitDayString(smallrp.getEndRepayDate(), "day"));
		model.addAttribute("quotDay", getQuot(ft.format(smallrp.getEndRepayDate()), ft.format(smallrp.getSignDate())) + 1);
		model.addAttribute("payAmountBig", NumberToChinese(smallrp.getPactMoney()));
		model.addAttribute("payAmount", smallrp.getPactMoney());
		model.addAttribute("legalGuarantee", smallrp.getLegalGuarantee());
		// 插入系统日志
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.CONTRACT.getValue());
		sysLog.setOptType(EnumConstants.OptionType.CONTRACT_DETAIL.getValue());
		sysLog.setRemark("合同号" + contractNo);
		sysLogService.insert(sysLog);
		return "report/dbhtsfr";
	}

	@RequestMapping("/dbhtszrr")
	public String reportDbhtszrr(String contractNo, Long guaranteeId, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("contractNo", contractNo);
		map.put("type", EnumConstants.ContractType.PEANUTS_NATURAL_GUARANTEE.getValue());
		String guaranteeName = guaranteeService.get(guaranteeId).getName();
		map.put("guaranteeName", guaranteeName);
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		Smallrp smallrp = smallrpService.getSmallrpByContractNo(map);
		model.addAttribute("contractNo", smallrp.getContractNo() + "-005");
		model.addAttribute("personName", smallrp.getPersonName());
		model.addAttribute("idNum", smallrp.getIdNum());
		model.addAttribute("contractNoJKXY", smallrp.getContractNo() + "-001");
		model.addAttribute("startYear", splitDayString(smallrp.getSignDate(), "year"));
		model.addAttribute("startMonth", splitDayString(smallrp.getSignDate(), "month"));
		model.addAttribute("startDay", splitDayString(smallrp.getSignDate(), "day"));
		model.addAttribute("endYear", splitDayString(smallrp.getEndRepayDate(), "year"));
		model.addAttribute("endMonth", splitDayString(smallrp.getEndRepayDate(), "month"));
		model.addAttribute("endDay", splitDayString(smallrp.getEndRepayDate(), "day"));
		model.addAttribute("quotDay", getQuot(ft.format(smallrp.getEndRepayDate()), ft.format(smallrp.getSignDate())) + 1);
		model.addAttribute("payAmountBig", NumberToChinese(smallrp.getPactMoney()));
		model.addAttribute("payAmount", smallrp.getPactMoney());
		model.addAttribute("guaranteeName", smallrp.getGuaranteeName());
		model.addAttribute("guaranteeIdNum", smallrp.getGuaranteeIdNum());
		// 插入系统日志
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.CONTRACT.getValue());
		sysLog.setOptType(EnumConstants.OptionType.CONTRACT_DETAIL.getValue());
		sysLog.setRemark("合同号" + contractNo);
		sysLogService.insert(sysLog);
		return "report/dbhtszrr";
	}

	@RequestMapping("/hktxh")
	public String reportHktxh(String contractNo, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("contractNo", contractNo);
		map.put("type", EnumConstants.ContractType.PEANUTS_REPAYMENT_FUND.getValue());
		Smallrp smallrp = smallrpService.getSmallrpByContractNo(map);
		List<RpDetail> rpDetailList = rpDetailService.getListByLoanId(smallrp.getLoanId());
		model.addAttribute("contractNo", smallrp.getContractNo() + "-003");
		model.addAttribute("personName", smallrp.getPersonName());
		model.addAttribute("monthInterestAmount", smallrp.getMonthInterestAmount().add(smallrp.getManageFees()));
		BigDecimal allAmt = new BigDecimal(0);
		if (rpDetailList != null && rpDetailList.size() > 0) {
			for (int i = 0; i < rpDetailList.size(); i++) {
				RpDetail rpDetail = rpDetailList.get(i);
				allAmt = allAmt.add(rpDetail.getInterestAmt());
				// allAmt+=rpDetail.getInterestAmt();
			}
		}
		model.addAttribute("yu1", (smallrp.getPactMoney().add(allAmt)).multiply(new BigDecimal(String.valueOf(0.0005))));
		model.addAttribute("yu15", (smallrp.getPactMoney().add(allAmt)).multiply(new BigDecimal(String.valueOf(0.0005)).multiply(new BigDecimal(String.valueOf(15)))));
		model.addAttribute("rpDetailList", rpDetailList);
		model.addAttribute("repayDate", smallrp.getRepayDate());
		// TODO
		// 根据借款ID，查询门店电话
		String workPlaceInfoTel = getWorkPlaceTel(smallrp.getLoanId());
		model.addAttribute("businessTell", workPlaceInfoTel);
		// TODO
		// 插入系统日志
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.CONTRACT.getValue());
		sysLog.setOptType(EnumConstants.OptionType.CONTRACT_DETAIL.getValue());
		sysLog.setRemark("合同号" + contractNo);
		sysLogService.insert(sysLog);
		return "report/hktxh";
	}

	/**
	 * <pre>
	 * 根据借款ID，获取营业部电话
	 * </pre>
	 * 
	 * @param loanId
	 * @return
	 */
	public String getWorkPlaceTel(Long loanId) {
		Loan loan = loanService.get(loanId);
		if (loan != null) {
			// 获取该借款所属区域
			BaseArea baseArea = baseAreaService.get(loan.getSalesDeptId());
			if (baseArea != null) {
				// 获取该区域营业部地址
				WorkPlaceInfo workPlaceInfo = workPlaceInfoService.loadOneWorkPlaceInfoById(baseArea.getWorkPlaceInfoId());
				if (workPlaceInfo != null) {
					// 返回营业部电话
					return workPlaceInfo.getTel();
				}
			}
		}
		return null;
	}

	@RequestMapping("/wtkksqsdbr")
	public String reportWtkksqsdbr(String contractNo, Long guaranteeId, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("contractNo", contractNo);
		map.put("type", EnumConstants.ContractType.PEANUTS_ENTRUST_GUARANTEE.getValue());
		String guaranteeName = guaranteeService.get(guaranteeId).getName();
		map.put("guaranteeName", guaranteeName);
		Smallrp smallrp = smallrpService.getSmallrpByContractNo(map);
		model.addAttribute("contractNo", smallrp.getContractNo() + "-006");
		model.addAttribute("contractNoDBS", smallrp.getContractNo() + "-005");
		model.addAttribute("personName", smallrp.getPersonName());
		model.addAttribute("idNum", smallrp.getIdNum());
		model.addAttribute("year", splitDayString(smallrp.getSignDate(), "year"));
		model.addAttribute("month", splitDayString(smallrp.getSignDate(), "month"));
		model.addAttribute("day", splitDayString(smallrp.getSignDate(), "day"));
		model.addAttribute("bankAccountName", smallrp.getBankAccountName());
		model.addAttribute("bankAccountNum", smallrp.getBankAccountNum());
		model.addAttribute("openingBank", smallrp.getOpeningBank() + "       " + smallrp.getBankBranchName());
		model.addAttribute("tell", smallrp.getContact());
		model.addAttribute("guaranteeName", smallrp.getGuaranteeName());
		model.addAttribute("guaranteeIdNum", smallrp.getGuaranteeIdNum());
		// 插入系统日志
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.CONTRACT.getValue());
		sysLog.setOptType(EnumConstants.OptionType.CONTRACT_DETAIL.getValue());
		sysLog.setRemark("合同号" + contractNo);
		sysLogService.insert(sysLog);
		return "report/wtkksqsdbr";
	}

	@RequestMapping("/wtkksqs")
	public String reportWtkksqs(String contractNo, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("contractNo", contractNo);
		map.put("type", EnumConstants.ContractType.PEANUTS_ENTRUST.getValue());
		Smallrp smallrp = smallrpService.getSmallrpByContractNo(map);
		model.addAttribute("contractNo", smallrp.getContractNo() + "-002");
		model.addAttribute("personName", smallrp.getPersonName());
		model.addAttribute("idNum", smallrp.getIdNum());
		model.addAttribute("year", splitDayString(smallrp.getSignDate(), "year"));
		model.addAttribute("month", splitDayString(smallrp.getSignDate(), "month"));
		model.addAttribute("day", splitDayString(smallrp.getSignDate(), "day"));
		model.addAttribute("bankAccountName", smallrp.getBankAccountName());
		model.addAttribute("bankAccountNum", smallrp.getBankAccountNum());
		model.addAttribute("openingBank", smallrp.getOpeningBank() + "       " + smallrp.getBankBranchName());
		model.addAttribute("tell", smallrp.getContact());
		// 插入系统日志
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.CONTRACT.getValue());
		sysLog.setOptType(EnumConstants.OptionType.CONTRACT_DETAIL.getValue());
		sysLog.setRemark("合同号" + contractNo);
		sysLogService.insert(sysLog);
		return "report/wtkksqs";
	}

	@RequestMapping("/car_wtkksqs")
	public String car_wtkksqs(String contractNo, Model model) {
		// contractNo="ZDB12014011044429";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("contractNo", contractNo);
		map.put("type", EnumConstants.ContractType.CAR_ENTRUST.getValue());
		Smallrp smallrp = smallrpService.queryCarCreditByContractNo(map);
		model.addAttribute("contractNo", smallrp.getContractNo());
		model.addAttribute("personName", smallrp.getPersonName());
		model.addAttribute("idNum", smallrp.getIdNum());
		model.addAttribute("year", splitDayString(smallrp.getSignDate(), "year"));
		model.addAttribute("month", splitDayString(smallrp.getSignDate(), "month"));
		model.addAttribute("day", splitDayString(smallrp.getSignDate(), "day"));
		// model.addAttribute("businessCompanyName",smallrp.getBusinessCompanyName());
		model.addAttribute("agreementNo", smallrp.getContractNo());
		model.addAttribute("personName", smallrp.getPersonName());
		model.addAttribute("openingBank", smallrp.getOpeningBank().concat(smallrp.getBankBranchName()));
		model.addAttribute("bankBranchName", smallrp.getBankBranchName());
		model.addAttribute("bankAccountNum", smallrp.getBankAccountNum());
		model.addAttribute("contactNo", smallrp.getContact());
		// 插入系统日志
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.CONTRACT.getValue());
		sysLog.setOptType(EnumConstants.OptionType.CONTRACT_DETAIL.getValue());
		sysLog.setRemark("合同号" + contractNo);
		sysLogService.insert(sysLog);
		return "report/car_wtkksqs";
	}

	@RequestMapping("/car_hkwxtsh")
	public String car_hkwxtsh(String contractNo, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("contractNo", contractNo);
		map.put("type", EnumConstants.ContractType.CAR_REPAYMENT_FUND.getValue());
		Smallrp smallrp = smallrpService.queryCarCreditByContractNo(map);
		//车贷流通类判断，
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
		Loan loanValidate = loanService.get(Long.valueOf(smallrp.getLoanId()));
		if (loanValidate != null) {
//			Date1.after(Date2),当Date1大于Date2时
			if(loanValidate.getCreatedTime().after(date)&&loanValidate.getLoanType().equals(EnumConstants.ProductCarType.CIRCULATE.getValue())){
				model.addAttribute("carCirSixTream", "1");
			} else {
				model.addAttribute("carCirSixTream", "0");
			}
		}
		
		
		List<RepaymentPlan> plans = repaymentPlanService.queryRepaymentPlans(smallrp.getLoanId());
		model.addAttribute("contractNo", smallrp.getContractNo());
		model.addAttribute("personName", smallrp.getPersonName());
		model.addAttribute("list", plans);
		model.addAttribute("repayDate", smallrp.getRepayDate());
		SysParameter ownCardName = sysParameterService.getByCode(SysParameterEnum.OWN_CARD_NAME.name());
		model.addAttribute("cardName", ownCardName.getParameterValue());
		SysParameter ownCardNum = sysParameterService.getByCode(SysParameterEnum.OWN_CARD_NUM.name());
		model.addAttribute("cardNum", ownCardNum.getParameterValue());
		SysParameter ownCardBank = sysParameterService.getByCode(SysParameterEnum.OWN_CARD_BANK.name());
		model.addAttribute("cardBank", ownCardBank.getParameterValue());
		// 根据借款ID，查询门店电话
		String workPlaceInfoTel = getWorkPlaceTel(smallrp.getLoanId());
		model.addAttribute("businessTell", workPlaceInfoTel);
		BigDecimal contractMoney = smallrp.getPactMoney();
		
		
		//获取罚息利率开始----
		LoanVO loanVO =new LoanVO();
		loanVO.setContractNo(contractNo);
		List<Loan> loanList = loanService.findListByVO(loanVO);
		
		//设置罚息费率初始值
		BigDecimal overdueInterestRate = new BigDecimal(0.004);
		if(loanList !=null && loanList.size()>0){
			Loan loan = loanList.get(0);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			//设置时间为2016-09-01 00:00:00
			String dateStr = sysParameterService.getByCode("CAR_FINE_NEW_RULE_EXECUTE_TIME").getParameterValue();
			try {
				//如果借款创建时间是2016.9.1后以后，则费率为新费率，如果是之前，则费率是旧费率
				if (StringUtils.isNotEmpty(dateStr)) {
					if (loan != null && loan.getCreatedTime() != null) {
						Product product = productService.get(loan
								.getProductId());
						if (product != null) {
							overdueInterestRate = product
									.getOverdueInterestRate();
							if (loan.getCreatedTime().getTime()
									- sdf.parse(dateStr).getTime() >= 0) {
								//如果是2016年9月1日之后创建的数据，则采用新费率
								overdueInterestRate = product
										.getOverdueInterestRateCar();

							}
						} else {
							if (loan.getCreatedTime().getTime()
									- sdf.parse(dateStr).getTime() >= 0) {
								overdueInterestRate = new BigDecimal(0.005);
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//获取罚息利率结束---
		contractMoney = contractMoney.multiply(overdueInterestRate);
		
		// 逾期一天,若罚息金额小于100元罚息则为100元
		model.addAttribute("over1", contractMoney.compareTo(new BigDecimal(100))==-1 ? new BigDecimal(100) :  contractMoney);
		// 逾期十五天,若罚息金额小于100元罚息则为100元
		model.addAttribute("over15", contractMoney.multiply(new BigDecimal(15)).compareTo(new BigDecimal(100))==-1 ? new BigDecimal(100) :  contractMoney.multiply(new BigDecimal(15)));
		// 插入系统日志
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.CONTRACT.getValue());
		sysLog.setOptType(EnumConstants.OptionType.CONTRACT_DETAIL.getValue());
		sysLog.setRemark("合同号" + contractNo);
		sysLogService.insert(sysLog);
		return "report/car_hkwxtsh";
	}

	@RequestMapping("/car_csfymx")
	public String car_csfymx(String contractNo, Long productId, Model model) {
		// 合同编号
		model.addAttribute("contractNo", contractNo);
		Product product = productService.get(productId);
		LoanVO loanVO =new LoanVO();
		loanVO.setContractNo(contractNo);
		loanVO.setProductId(productId);
		List<Loan> loanList = loanService.findListByVO(loanVO);
		//设置罚息费率初始值
		BigDecimal overdueInterestRate = new BigDecimal(0.4);
		if(loanList !=null && loanList.size()>0){
			Loan loan = loanList.get(0);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			//设置时间为2016-09-01 00:00:00
			String dateStr = sysParameterService.getByCode("CAR_FINE_NEW_RULE_EXECUTE_TIME").getParameterValue();
			
			
			//如果借款创建时间是2016.9.1后以后，则费率为新费率，如果是之前，则费率是旧费率
			try {
				//如果是2016年9月1日之后创建的数据，则采用新费率
				if (StringUtils.isNotEmpty(dateStr)) {
					if (loan != null && loan.getCreatedTime() != null) {
						if (product != null) {
							overdueInterestRate = product
									.getOverdueInterestRate().multiply(
											new BigDecimal(100));
							if (loan.getCreatedTime().getTime()
									- sdf.parse(dateStr).getTime() >= 0) {
								overdueInterestRate = product
										.getOverdueInterestRateCar().multiply(
												new BigDecimal(100));

							}
						} else {
							if (loan.getCreatedTime().getTime()
									- sdf.parse(dateStr).getTime() >= 0) {
								overdueInterestRate = new BigDecimal(0.5);
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		// 逾期罚息
		model.addAttribute("overdueInterestRate", overdueInterestRate);
		
		// 插入系统日志
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.CONTRACT.getValue());
		sysLog.setOptType(EnumConstants.OptionType.CONTRACT_DETAIL.getValue());
		sysLog.setRemark("合同号" + contractNo);
		sysLogService.insert(sysLog);
		return "report/car_csfymx";
	}

	// 个人借款咨询服务风险基金协议
	@RequestMapping("/reportOne")
	public @ResponseBody List getReportOneJSON(String contractNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("contractNo", contractNo);
		map.put("type", EnumConstants.ContractType.PEANUTS_PERSON_LOAN.getValue());
		Smallrp smallrp = smallrpService.getSmallrpByContractNo(map);
		List list = new ArrayList();
		if (smallrp != null) {
			list.add(smallrp.getContractNo() + "-004");
			list.add(splitDayString(smallrp.getSignDate(), "year"));
			list.add(splitDayString(smallrp.getSignDate(), "month"));
			list.add(splitDayString(smallrp.getSignDate(), "day"));
			list.add(smallrp.getCityName());
			list.add(smallrp.getAreaName());
			list.add(smallrp.getPersonName());
			list.add(smallrp.getIdNum());
			list.add(smallrp.getAddress());
			list.add(smallrp.getEmail());
			list.add(NumberToChinese(smallrp.getPayAmount()));
			list.add(smallrp.getPayAmount().setScale(2, BigDecimal.ROUND_DOWN).toString());
		}
		// 插入系统日志
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.CONTRACT.getValue());
		sysLog.setOptType(EnumConstants.OptionType.CONTRACT_DETAIL.getValue());
		sysLog.setRemark("合同号" + contractNo);
		sysLogService.insert(sysLog);
		return list;
	}

	// 借款协议
	@RequestMapping("/reportTwo")
	public @ResponseBody List getReportTwoJSON(String contractNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("contractNo", contractNo);
		map.put("type", EnumConstants.ContractType.PEANUTS_LOAN.getValue());
		Smallrp smallrp = smallrpService.getSmallrpByContractNo(map);
		List list = new ArrayList();
		list.add(smallrp.getContractNo() + "-001");
		list.add(splitDayString(smallrp.getSignDate(), "year"));
		list.add(splitDayString(smallrp.getSignDate(), "month"));
		list.add(splitDayString(smallrp.getSignDate(), "day"));
		list.add(smallrp.getCityName());
		list.add(smallrp.getAreaName());
		list.add(smallrp.getPersonName());
		list.add(smallrp.getIdNum());
		list.add(smallrp.getAddress());
		list.add(smallrp.getZipCode());
		list.add(smallrp.getPurpose());
		list.add(NumberToChineseOther(smallrp.getPactMoney()));
		list.add(getMoneyNumAt(smallrp.getPactMoney(), 6));
		list.add(getMoneyNumAt(smallrp.getPactMoney(), 5));
		list.add(getMoneyNumAt(smallrp.getPactMoney(), 4));
		list.add(getMoneyNumAt(smallrp.getPactMoney(), 3));
		list.add(getMoneyNumAt(smallrp.getPactMoney(), 2));
		list.add(getMoneyNumAt(smallrp.getPactMoney(), 1));
		list.add(getMoneyNumAt(smallrp.getPactMoney(), -1));
		list.add(getMoneyNumAt(smallrp.getPactMoney(), -2));
		list.add(NumberToChineseOther(smallrp.getMonthInterestAmount()));
		list.add(getMoneyNumAt(smallrp.getMonthInterestAmount(), 6));
		list.add(getMoneyNumAt(smallrp.getMonthInterestAmount(), 5));
		list.add(getMoneyNumAt(smallrp.getMonthInterestAmount(), 4));
		list.add(getMoneyNumAt(smallrp.getMonthInterestAmount(), 3));
		list.add(getMoneyNumAt(smallrp.getMonthInterestAmount(), 2));
		list.add(getMoneyNumAt(smallrp.getMonthInterestAmount(), 1));
		list.add(getMoneyNumAt(smallrp.getMonthInterestAmount(), -1));
		list.add(getMoneyNumAt(smallrp.getMonthInterestAmount(), -2));
		list.add(smallrp.getTimes());
		list.add(smallrp.getRepayDate());
		list.add(splitDayString(smallrp.getStartRepayDate(), "year"));
		list.add(splitDayString(smallrp.getStartRepayDate(), "month"));
		list.add(splitDayString(smallrp.getStartRepayDate(), "day"));
		list.add(splitDayString(smallrp.getEndRepayDate(), "year"));
		list.add(splitDayString(smallrp.getEndRepayDate(), "month"));
		list.add(splitDayString(smallrp.getEndRepayDate(), "day"));
		list.add(smallrp.getBankAccountName());
		list.add(smallrp.getBankAccountNum());
		list.add(smallrp.getOpeningBank() + smallrp.getBankBranchName());
		// 插入系统日志
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.CONTRACT.getValue());
		sysLog.setOptType(EnumConstants.OptionType.CONTRACT_DETAIL.getValue());
		sysLog.setRemark("合同号" + contractNo);
		sysLogService.insert(sysLog);
		return list;
	}

	// 个人借款咨询服务协议
	@RequestMapping("/reportThree")
	public @ResponseBody List getReportThreeJSON(String contractNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("contractNo", contractNo);
		map.put("type", EnumConstants.ContractType.PEANUTS_REPAYMENT.getValue());
		Smallrp smallrp = smallrpService.getSmallrpByContractNo(map);
		List list = new ArrayList();
		if (smallrp != null) {
			list.add(smallrp.getContractNo());
			list.add(splitDayString(smallrp.getSignDate(), "year"));
			list.add(splitDayString(smallrp.getSignDate(), "month"));
			list.add(splitDayString(smallrp.getSignDate(), "day"));
			list.add(smallrp.getCityName());
			list.add(smallrp.getAreaName());
			list.add(smallrp.getPersonName());
			list.add(smallrp.getIdNum());
			list.add(smallrp.getAddress());
			list.add(smallrp.getEmail());
			list.add(smallrp.getPactMoney().setScale(2, BigDecimal.ROUND_DOWN).toString());
			DecimalFormat df = new DecimalFormat("0");
			// double dd =
			// Double.valueOf(df.format(smallrp.getPactMoney()/100));
			BigDecimal dd = smallrp.getPactMoney().divide(new BigDecimal(100));
			list.add(NumberToChinese(dd));
			list.add(dd.setScale(0, BigDecimal.ROUND_UP).setScale(2, BigDecimal.ROUND_DOWN).toString());
			list.add(NumberToChinese(smallrp.getAssessmentFees()));
			list.add(smallrp.getAssessmentFees().setScale(2, BigDecimal.ROUND_DOWN).toString());
			list.add(NumberToChinese(smallrp.getManageFees()));
			list.add(smallrp.getManageFees().setScale(2, BigDecimal.ROUND_DOWN).toString());
			list.add(NumberToChinese(smallrp.getTtpManageFees()));
			list.add(smallrp.getTtpManageFees().setScale(2, BigDecimal.ROUND_DOWN).toString());
			list.add(smallrp.getBankAccountName());
			list.add(smallrp.getOpeningBank() + smallrp.getBankBranchName());
			list.add(smallrp.getBankAccountNum());
		}
		// 插入系统日志
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.CONTRACT.getValue());
		sysLog.setOptType(EnumConstants.OptionType.CONTRACT_DETAIL.getValue());
		sysLog.setRemark("合同号" + contractNo);
		sysLogService.insert(sysLog);
		return list;
	}

	/**
	 * 001-个人借款咨询服务协议(抵押)(套打)
	 *
	 * @return
	 */
	@RequestMapping("/car1")
	public @ResponseBody List car1(@RequestParam("contractNo") String contractNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("contractNo", contractNo);
		map.put("type", EnumConstants.ContractType.CAR_REPAYMENT.getValue());
		Smallrp smallrp = smallrpService.queryCarCreditByContractNo(map);
		LoanVO loanvo=new LoanVO();
		loanvo.setContractNo(contractNo);
		Loan loan = loanService.get(loanvo);
		SysParameter parameter=	sysParameterService.getByCodeNoCache(EnumConstants.CAR_FINE_NEW_RULE_EXECUTE_TIME);
		 SimpleDateFormat sdftime = new SimpleDateFormat("yyyy-MM-dd");                
		 Date date=new Date();
		try {
			date = sdftime.parse(parameter.getParameterValue());
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		List list = new ArrayList();
		if (smallrp != null) {
			list.add(smallrp.getContractNo());
			list.add(splitDayString(smallrp.getSignDate(), "year"));
			list.add(splitDayString(smallrp.getSignDate(), "month"));
			list.add(splitDayString(smallrp.getSignDate(), "day"));
			list.add(smallrp.getCityName());
			list.add(smallrp.getAreaName());
			list.add(smallrp.getPersonName());
			list.add(smallrp.getIdNum());
			list.add(smallrp.getAddress());
			list.add(smallrp.getEmail());
			// list.add(smallrp.getBusinessCompanyName());
			// list.add(smallrp.getBusinessAddress());
			// list.add(smallrp.getZipCode());
			// 借款金额
			list.add(smallrp.getPactMoney().setScale(2, BigDecimal.ROUND_DOWN).toString());
			List returnList = new ArrayList();
			List valueList = new ArrayList();

			List<RepaymentPlan> repaymentPlans = repaymentPlanService.queryRepaymentPlans(smallrp.getLoanId());
			// 前期费用,取合同表
			StringBuffer sb = new StringBuffer();
			StringBuffer sb2 = new StringBuffer();
			// sb.append(splitDayString(smallrp.getSignDate(),
			// "year")).append("-").append(splitDayString(smallrp.getSignDate(),
			// "month"))
			// .append("-").append(splitDayString(smallrp.getSignDate(),
			// "day")).append("        ");
			SimpleDateFormat ft = new SimpleDateFormat("yyyy年MM月dd日");
			valueList.add(ft.format(smallrp.getSignDate()));
			BigDecimal referRate;
			BigDecimal evalRate;
			BigDecimal managePart0Fee, managePart1Fee;
			
			Product product = productService.get(loan.getProductId());
			
			
			int end = 0;
			if (smallrp.getTimes() <= 4) {
				// 短期
				if(loan.getCreatedTime().after(date)&&loan.getLoanType().equals(EnumConstants.ProductCarType.CIRCULATE.getValue())){
					referRate = loan.getConsult();
					evalRate = loan.getAssessment();
					managePart0Fee = new BigDecimal(0);
					managePart1Fee = loan.getcManage();
					BigDecimal ser=	new BigDecimal(4);
					BigDecimal serviceFee=ser.add(referRate).add(evalRate).add(evalRate).add(managePart1Fee);
				}else{
					BigDecimal serviceFee = smallrp.getPreparatoryAmount().subtract(smallrp.getRaskAmount());
					referRate = serviceFee.divide(new BigDecimal(4), 2, BigDecimal.ROUND_HALF_UP);
					evalRate = serviceFee.divide(new BigDecimal(4), 2, BigDecimal.ROUND_HALF_UP);
					managePart0Fee = new BigDecimal(0);
					managePart1Fee = serviceFee.subtract(referRate).subtract(evalRate);
				}
				
				// 后期费用
				end = 1;
			} else if(loan.getCreatedTime().after(date)
					&&("002".equals(product.getProductCode()))
					&&loan.getLoanType().equals(EnumConstants.ProductCarType.CIRCULATE.getValue())
					&&loan.getAuditTime().compareTo(6) == 0){
				referRate = loan.getConsult();
				evalRate = loan.getAssessment();
				managePart0Fee = new BigDecimal(0);
				managePart1Fee = loan.getcManage();
				BigDecimal ser=	new BigDecimal(4);
				BigDecimal serviceFee=ser.add(referRate).add(evalRate).add(evalRate).add(managePart1Fee);
				
				// 后期费用
				end = 1;
			} else {
				// 长期
				// TODO
				referRate = smallrp.getAuditMoney().multiply(new BigDecimal(String.valueOf(0.015)));
				evalRate = smallrp.getAuditMoney().multiply(new BigDecimal(String.valueOf(0.015)));
				managePart0Fee = new BigDecimal(0);
				managePart1Fee = new BigDecimal(0);
			}
			// sb.append(referRate.setScale(2,BigDecimal.ROUND_DOWN).toString()).append("            ");
			// sb.append(evalRate.setScale(2,BigDecimal.ROUND_DOWN).toString()).append("           ");
			// sb.append(managePart0Fee.setScale(2,BigDecimal.ROUND_DOWN).toString()).append("           ");
			// sb.append(managePart1Fee.setScale(2,BigDecimal.ROUND_DOWN).toString()).append("            ");
			// sb.append(referRate.add(evalRate).add(managePart0Fee).add(managePart1Fee).setScale(2,BigDecimal.ROUND_DOWN).toString());
			valueList.add(referRate.setScale(2, BigDecimal.ROUND_DOWN).toString());
			valueList.add(evalRate.setScale(2, BigDecimal.ROUND_DOWN).toString());
			valueList.add(managePart0Fee.setScale(2, BigDecimal.ROUND_DOWN).toString());
			valueList.add(managePart1Fee.setScale(2, BigDecimal.ROUND_DOWN).toString());
			valueList.add(referRate.add(evalRate).add(managePart0Fee).add(managePart1Fee).setScale(2, BigDecimal.ROUND_DOWN).toString());
			// list.add(sb.toString());
			returnList.add(valueList);

			for (int i = 0; i < repaymentPlans.size() - end; i++) {
				// sb2.append(splitDayString(repaymentPlans.get(i).getRepayDay(),
				// "year") + "-" +
				// splitDayString(repaymentPlans.get(i).getRepayDay(), "month")
				// + "-" +
				// splitDayString(repaymentPlans.get(i).getRepayDay(), "day") +
				// "        ");
				valueList = new ArrayList();
				valueList.add(ft.format(repaymentPlans.get(i).getRepayDay()));

				referRate = repaymentPlans.get(i).getReferRate() == null ? new BigDecimal(0) : repaymentPlans.get(i).getReferRate();
				evalRate = repaymentPlans.get(i).getEvalRate() == null ? new BigDecimal(0) : repaymentPlans.get(i).getEvalRate();
				managePart0Fee = repaymentPlans.get(i).getManagePart0Fee();
				managePart1Fee = repaymentPlans.get(i).getManagePart1Fee();

				// sb2.append(referRate.setScale(2,BigDecimal.ROUND_DOWN).toString()
				// + "            ");
				// sb2.append(evalRate.setScale(2,BigDecimal.ROUND_DOWN).toString()
				// + "           ");
				// sb2.append(managePart0Fee.setScale(2,BigDecimal.ROUND_DOWN).toString()
				// + "           ");
				// sb2.append(managePart1Fee.setScale(2,BigDecimal.ROUND_DOWN).toString()
				// + "            ");
				// sb2.append(referRate.add(evalRate).add(managePart0Fee).add(managePart1Fee).setScale(2,BigDecimal.ROUND_DOWN).toString());

				valueList.add(referRate.setScale(2, BigDecimal.ROUND_DOWN).toString());
				valueList.add(evalRate.setScale(2, BigDecimal.ROUND_DOWN).toString());
				valueList.add(managePart0Fee.setScale(2, BigDecimal.ROUND_DOWN).toString());
				valueList.add(managePart1Fee.setScale(2, BigDecimal.ROUND_DOWN).toString());
				valueList.add(referRate.add(evalRate).add(managePart0Fee).add(managePart1Fee).setScale(2, BigDecimal.ROUND_DOWN).toString());
				// sb2.append("\r\n");
				returnList.add(valueList);
			}
			// list.add(sb2.toString());
			list.add(returnList);

			list.add(smallrp.getPersonName());
			list.add(smallrp.getOpeningBank().concat(smallrp.getBankBranchName()));
			list.add(smallrp.getBankAccountNum());
		}
		// 插入系统日志
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.CONTRACT.getValue());
		sysLog.setOptType(EnumConstants.OptionType.CONTRACT_DETAIL.getValue());
		sysLog.setRemark("合同号" + contractNo);
		sysLogService.insert(sysLog);
		return list;
	}

	/**
	 * 002-借款抵押协议（抵押）(套打)
	 *
	 * @return
	 */
	@RequestMapping("/car2")
	public @ResponseBody List car2(@RequestParam("contractNo") String contractNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("contractNo", contractNo);
		map.put("type", EnumConstants.ContractType.CAR_LOAN.getValue());
		Smallrp smallrp = smallrpService.queryCarCreditByContractNo(map);
		List list = new ArrayList();
		List returnList = new ArrayList();
		List valueList = new ArrayList();
		if (smallrp != null) {
			list.add(smallrp.getContractNo());
			list.add(splitDayString(smallrp.getSignDate(), "year"));
			list.add(splitDayString(smallrp.getSignDate(), "month"));
			list.add(splitDayString(smallrp.getSignDate(), "day"));
			list.add(smallrp.getCityName());
			list.add(smallrp.getAreaName());
			list.add(smallrp.getPersonName());
			list.add(smallrp.getIdNum());
			list.add(smallrp.getAddress());
			list.add(smallrp.getContact());
			list.add(smallrp.getEmail());
			list.add(smallrp.getBusinessCompanyName());
			list.add(smallrp.getBusinessAddress());
			list.add(smallrp.getTell());
			list.add(smallrp.getPurpose());
			list.add(NumberToChinese(smallrp.getPactMoney()));
			list.add(smallrp.getPactMoney().setScale(2).toString());
			// list.add(getMoneyNumAt(smallrp.getPactMoney(), 7));
			// list.add(getMoneyNumAt(smallrp.getPactMoney(), 6));
			// list.add(getMoneyNumAt(smallrp.getPactMoney(), 5));
			// list.add(getMoneyNumAt(smallrp.getPactMoney(), 4));
			// list.add(getMoneyNumAt(smallrp.getPactMoney(), 3));
			// list.add(getMoneyNumAt(smallrp.getPactMoney(), 2));
			// list.add(getMoneyNumAt(smallrp.getPactMoney(), 1));
			// list.add(getMoneyNumAt(smallrp.getPactMoney(), -1));
			// list.add(getMoneyNumAt(smallrp.getPactMoney(), -2));
			// 总利息数额
			list.add(NumberToChinese(smallrp.getTotalAmount().setScale(2)));
			list.add(smallrp.getTotalAmount().setScale(2).toString());
			// list.add(getMoneyNumAt(smallrp.getTotalAmount(), 7));
			// list.add(getMoneyNumAt(smallrp.getTotalAmount(), 6));
			// list.add(getMoneyNumAt(smallrp.getTotalAmount(), 5));
			// list.add(getMoneyNumAt(smallrp.getTotalAmount(), 4));
			// list.add(getMoneyNumAt(smallrp.getTotalAmount(), 3));
			// list.add(getMoneyNumAt(smallrp.getTotalAmount(), 2));
			// list.add(getMoneyNumAt(smallrp.getTotalAmount(), 1));
			// list.add(getMoneyNumAt(smallrp.getTotalAmount(), -1));
			// list.add(getMoneyNumAt(smallrp.getTotalAmount(), -2));
			// 实际到账金额 = 合同金额-前期费用
			BigDecimal pactMoney = smallrp.getPactMoney().setScale(2);
			BigDecimal preparatoryAmount = smallrp.getPreparatoryAmount().setScale(2);

			list.add(NumberToChinese(pactMoney.subtract(preparatoryAmount)));
			list.add(pactMoney.subtract(preparatoryAmount).setScale(2).toString());
			// 借款期限
			list.add(String.valueOf(smallrp.getTimes()));
			list.add(splitDayString(smallrp.getSignDate(), "year"));
			list.add(splitDayString(smallrp.getSignDate(), "month"));
			list.add(splitDayString(smallrp.getSignDate(), "day"));
			list.add(splitDayString(smallrp.getEndRepayDate(), "year"));
			list.add(splitDayString(smallrp.getEndRepayDate(), "month"));
			list.add(splitDayString(smallrp.getEndRepayDate(), "day"));
			list.add(smallrp.getBankAccountName());
			list.add(smallrp.getBankAccountNum());
			list.add(smallrp.getOpeningBank());
			List<RepaymentPlan> plans = repaymentPlanService.queryRepaymentPlans(smallrp.getLoanId());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
			StringBuffer sb = new StringBuffer();
			for (RepaymentPlan plan : plans) {
				valueList = new ArrayList();
				// 还款日期
				// sb.append(sdf.format(plan.getRepayDay()));
				valueList.add(sdf.format(plan.getRepayDay()));
				// sb.append("               ");
				// 应还本金
				if (plan.getPrincipalAmt() == null) {
					plan.setPrincipalAmt(new BigDecimal(0));
				}
				// sb.append(plan.getPrincipalAmt().setScale(2,BigDecimal.ROUND_DOWN).toString());
				valueList.add(plan.getPrincipalAmt().setScale(2, BigDecimal.ROUND_DOWN).toString());

				// sb.append("               ");
				// 利息
				// sb.append(plan.getInterestAmt().setScale(2,BigDecimal.ROUND_DOWN).toString());
				valueList.add(plan.getInterestAmt().setScale(2, BigDecimal.ROUND_DOWN).toString());
				// sb.append("               ");
				// 合计
				// sb.append(plan.getPrincipalAmt().add(plan.getInterestAmt()).setScale(2,BigDecimal.ROUND_DOWN).toString());
				// sb.append("\r\n");
				valueList.add(plan.getPrincipalAmt().add(plan.getInterestAmt()).setScale(2, BigDecimal.ROUND_DOWN).toString());
				returnList.add(valueList);
			}
			list.add(returnList);
		}
		// 插入系统日志
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.CONTRACT.getValue());
		sysLog.setOptType(EnumConstants.OptionType.CONTRACT_DETAIL.getValue());
		sysLog.setRemark("合同号" + contractNo);
		sysLogService.insert(sysLog);
		return list;
	}

	/**
	 * 003-个人借款咨询服务风险基金协议(套打)
	 *
	 * @return
	 */
	@RequestMapping("/car3")
	public @ResponseBody List car3(@RequestParam("contractNo") String contractNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("contractNo", contractNo);
		map.put("type", EnumConstants.ContractType.CAR_PERSON_LOAN.getValue());
		Smallrp smallrp = smallrpService.queryCarCreditByContractNo(map);
		List list = new ArrayList();
		List returnList = new ArrayList();
		List valueList = new ArrayList();
		if (smallrp != null) {
			list.add(smallrp.getContractNo());
			list.add(splitDayString(smallrp.getSignDate(), "year"));
			list.add(splitDayString(smallrp.getSignDate(), "month"));
			list.add(splitDayString(smallrp.getSignDate(), "day"));
			list.add(smallrp.getCityName());
			list.add(smallrp.getAreaName());
			list.add(smallrp.getPersonName());
			list.add(smallrp.getIdNum());
			list.add(smallrp.getAddress());
			list.add(smallrp.getEmail());
			int times = smallrp.getTimes();// 期数
			List<RepaymentPlan> plans = repaymentPlanService.queryRepaymentPlans(smallrp.getLoanId());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
			
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
			Long loanId = Long.valueOf(smallrp.getLoanId());
			Loan loan = loanService.get(loanId);
			Product product = productService.get(loan.getProductId());
			
			
			
			//车贷 老产品 流通类 存在3期和6期
			if (times <= 4 ) {

				// 前期风险金取第一期
				// list.add("\t".concat(sdf.format(smallrp.getSignDate())).concat("                              ").concat(smallrp.getRaskAmount().setScale(2,BigDecimal.ROUND_DOWN).toString()));
				valueList.add(sdf.format(smallrp.getSignDate()));
				valueList.add(smallrp.getRaskAmount().setScale(2, BigDecimal.ROUND_DOWN).toString());
				returnList.add(valueList);
				// 中期风险金
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < plans.size() - 1; i++) {
					valueList = new ArrayList();
					// sb.append("\t".concat(sdf.format(plans.get(i).getRepayDay())));
					// sb.append("                              ");
					// sb.append(plans.get(i).getRisk().setScale(2,BigDecimal.ROUND_DOWN).toString());
					// sb.append("\r\n");
					valueList.add(sdf.format(plans.get(i).getRepayDay()));
					valueList.add(plans.get(i).getRisk().setScale(2, BigDecimal.ROUND_DOWN).toString());
					returnList.add(valueList);
				}
				list.add(returnList);
			} else if(loan.getCreatedTime().after(date)
					&&("002".equals(product.getProductCode()))
					&&loan.getLoanType().equals(EnumConstants.ProductCarType.CIRCULATE.getValue())
					&&loan.getAuditTime().compareTo(6) == 0){
				// 前期风险金取第一期
				// list.add("\t".concat(sdf.format(smallrp.getSignDate())).concat("                              ").concat(smallrp.getRaskAmount().setScale(2,BigDecimal.ROUND_DOWN).toString()));
				valueList.add(sdf.format(smallrp.getSignDate()));
				valueList.add(smallrp.getRaskAmount().setScale(2, BigDecimal.ROUND_DOWN).toString());
				returnList.add(valueList);
				// 中期风险金
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < plans.size() - 1; i++) {
					valueList = new ArrayList();
					// sb.append("\t".concat(sdf.format(plans.get(i).getRepayDay())));
					// sb.append("                              ");
					// sb.append(plans.get(i).getRisk().setScale(2,BigDecimal.ROUND_DOWN).toString());
					// sb.append("\r\n");
					valueList.add(sdf.format(plans.get(i).getRepayDay()));
					valueList.add(plans.get(i).getRisk().setScale(2, BigDecimal.ROUND_DOWN).toString());
					returnList.add(valueList);
				}
				list.add(returnList);
				
			}else {
				// 长期-
				BigDecimal result = smallrp.getRaskAmount();
				// StringBuffer temp=new StringBuffer();
				// temp.append("\t").append(sdf.format(plans.get(0).getRepayDay())).append("                              ").append(result.setScale(2,BigDecimal.ROUND_DOWN).toString());
				// list.add(temp.toString());
				// list.add("");
				valueList.add(sdf.format(smallrp.getSignDate()));
				valueList.add(result.setScale(2, BigDecimal.ROUND_DOWN).toString());
				returnList.add(valueList);
				list.add(returnList);
			}
		}
		// 插入系统日志
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.CONTRACT.getValue());
		sysLog.setOptType(EnumConstants.OptionType.CONTRACT_DETAIL.getValue());
		sysLog.setRemark("合同号" + contractNo);
		sysLogService.insert(sysLog);
		return list;
	}

	/**
	 * 004-车辆处置特别授权委托书(套打)
	 *
	 * @return
	 */
	@RequestMapping("/car4")
	public @ResponseBody List car4(@RequestParam("contractNo") String contractNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("contractNo", contractNo);
		map.put("type", EnumConstants.ContractType.CAR_VEHICLE.getValue());
		Smallrp smallrp = smallrpService.queryCarCreditByContractNo(map);
		ArrayList<String> list = new ArrayList<String>();
		if (smallrp != null) {
			list.add(smallrp.getContractNo());
			list.add(smallrp.getPersonName());
			list.add(smallrp.getIdNum());
			list.add(smallrp.getIsMarried().toString());
			list.add(smallrp.getBrand());
			list.add(smallrp.getPlateNumber());
			list.add(smallrp.getFrameNumber());
			list.add(smallrp.getLoanAgreeNum());
		}
		// 插入系统日志
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.CONTRACT.getValue());
		sysLog.setOptType(EnumConstants.OptionType.CONTRACT_DETAIL.getValue());
		sysLog.setRemark("合同号" + contractNo);
		sysLogService.insert(sysLog);
		return list;
	}

	/**
	 * 008-抵押借款展期协议(套打)
	 * 
	 * @return
	 */
	@RequestMapping("/loanExtensionAgreement")
	@ResponseBody
	public List loanExtensionAgreement(@RequestParam("extensionId") Long extensionId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("extensionId", extensionId);
		map.put("type", EnumConstants.ContractType.CAR_LOAN_EXT.getValue());// TBD
		Smallrp smallrp = smallrpService.queryCarCreditByExtensionId(map);
		List list = new ArrayList();
		List returnList = new ArrayList();
		List valueList = new ArrayList();
		List chargesList = new ArrayList();
		List chargeList = new ArrayList();
		if (smallrp != null) {
			list.add(smallrp.getContractNo());// TBD
			list.add(smallrp.getLoanAgreeNum());
			list.add(splitDayString(smallrp.getStartRepayDate(), "year"));
			list.add(splitDayString(smallrp.getStartRepayDate(), "month"));
			list.add(splitDayString(smallrp.getStartRepayDate(), "day"));// 最后一个还款日
			list.add(smallrp.getCityName());
			list.add(smallrp.getAreaName());
			list.add(smallrp.getPersonName());
			list.add(smallrp.getIdNum());
			list.add(smallrp.getAddress());// line-9
			// list.add(smallrp.getEmail()); //
			list.add(splitDayString(smallrp.getSignDate(), "year")); // 原签约日期
			list.add(splitDayString(smallrp.getSignDate(), "month"));
			list.add(splitDayString(smallrp.getSignDate(), "day"));
			list.add(NumberToChinese(smallrp.getOrgPactMoney()));// 原金额
			list.add(smallrp.getOrgPactMoney());// 原金额
			// 合同金额
			list.add(NumberToChinese(smallrp.getPactMoney()));// 15
			list.add(smallrp.getPactMoney().setScale(2).toString());
			// 总利息数额
			list.add(NumberToChinese(smallrp.getTotalAmount().setScale(2)));
			list.add(smallrp.getTotalAmount().setScale(2).toString());
			// 最后一个还款日
			list.add(splitDayString(smallrp.getEndRepayDate(), "year"));// 19
			list.add(splitDayString(smallrp.getEndRepayDate(), "month"));
			list.add(splitDayString(smallrp.getEndRepayDate(), "day"));
			list.add(smallrp.getBankAccountName()); // 甲方专用账号
			list.add(smallrp.getBankAccountNum());
			list.add(smallrp.getOpeningBank() + smallrp.getBankBranchName());// 24
			List<RepaymentPlan> plans = repaymentPlanService.queryRepaymentPlans(smallrp.getLoanId());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
			Extension ex = extensionService.get(smallrp.getLoanId());
			chargeList.add(sdf.format(smallrp.getStartRepayDate()));
			chargeList.add(ex.getConsult().setScale(2, BigDecimal.ROUND_DOWN).toString());
			chargeList.add(ex.getAssessment().setScale(2, BigDecimal.ROUND_DOWN).toString());
			chargeList.add(ex.getbManage().setScale(2, BigDecimal.ROUND_DOWN).toString());
			chargeList.add(ex.getcManage().setScale(2, BigDecimal.ROUND_DOWN).toString());
			chargeList.add(ex.getConsult().add(ex.getAssessment()).add(ex.getbManage()).add(ex.getcManage()).setScale(2, BigDecimal.ROUND_DOWN).toString());
			chargesList.add(chargeList);
			for (int i = 0; i < plans.size(); i++) {
				RepaymentPlan plan = plans.get(i);
				valueList = new ArrayList();
				// 还款日期
				valueList.add(sdf.format(plan.getRepayDay()));
				// 应还本金
				if (plan.getPrincipalAmt() == null) {
					plan.setPrincipalAmt(new BigDecimal(0));
				}
				valueList.add(plan.getPrincipalAmt().setScale(2, BigDecimal.ROUND_DOWN).toString());
				// 利息
				valueList.add(plan.getInterestAmt().setScale(2, BigDecimal.ROUND_DOWN).toString());
				// 合计
				valueList.add(plan.getPrincipalAmt().add(plan.getInterestAmt()).setScale(2, BigDecimal.ROUND_DOWN).toString());
				returnList.add(valueList);
				// 前期费用
				if (i < plans.size() - 1) {
					chargeList = new ArrayList();
					chargeList.add(sdf.format(plan.getRepayDay()));
					chargeList.add(plan.getReferRate().setScale(2, BigDecimal.ROUND_DOWN).toString());
					chargeList.add(plan.getEvalRate().setScale(2, BigDecimal.ROUND_DOWN).toString());
					chargeList.add(plan.getManagePart0Fee().setScale(2, BigDecimal.ROUND_DOWN).toString());
					chargeList.add(plan.getManagePart1Fee().setScale(2, BigDecimal.ROUND_DOWN).toString());
					chargeList.add(plan.getReferRate().add(plan.getEvalRate()).add(plan.getManagePart0Fee()).add(plan.getManagePart1Fee()).setScale(2, BigDecimal.ROUND_DOWN).toString());
					chargesList.add(chargeList);
				}
			}
			list.add(returnList);
			list.add(chargesList);
		}
		// 插入系统日志
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.CONTRACT.getValue());
		sysLog.setOptType(EnumConstants.OptionType.CONTRACT_DETAIL.getValue());// TBD
		sysLog.setRemark("展期借款ID: " + extensionId);
		sysLogService.insert(sysLog);
		return list;
	}

	/**
	 * 009 个人借款咨询服务风险基金协议(展期)
	 *
	 * @return
	 */
	@RequestMapping("/loanFundRiskAgreement")
	public @ResponseBody List loanFundRiskAgreement(@RequestParam("extensionId") Long extensionId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("extensionId", extensionId);
		map.put("type", EnumConstants.ContractType.CAR_PERSON_LOAN_EXT.getValue());
		Smallrp smallrp = smallrpService.queryCarCreditByExtensionId(map);
		List list = new ArrayList();
		List returnList = new ArrayList();
		List valueList = new ArrayList();
		if (smallrp != null) {
			list.add(smallrp.getContractNo());
			list.add(smallrp.getLoanAgreeNum());
			list.add(splitDayString(smallrp.getStartRepayDate(), "year"));
			list.add(splitDayString(smallrp.getStartRepayDate(), "month"));
			list.add(splitDayString(smallrp.getStartRepayDate(), "day"));
			list.add(smallrp.getCityName());
			list.add(smallrp.getAreaName());
			list.add(smallrp.getPersonName());// 7
			list.add(smallrp.getIdNum());
			list.add(smallrp.getAddress());
			list.add(smallrp.getEmail());
			List<RepaymentPlan> plans = repaymentPlanService.queryRepaymentPlans(smallrp.getLoanId());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
			// 前期风险金取第一期
			valueList.add(sdf.format(smallrp.getStartRepayDate()));
			valueList.add(smallrp.getRaskAmount().setScale(2, BigDecimal.ROUND_DOWN).toString()); // TBD
			returnList.add(valueList);
			// 中期风险金
			for (int i = 0; i < plans.size() - 1; i++) {
				valueList = new ArrayList();
				valueList.add(sdf.format(plans.get(i).getRepayDay()));
				valueList.add(plans.get(i).getRisk().setScale(2, BigDecimal.ROUND_DOWN).toString());
				returnList.add(valueList);
			}
			list.add(returnList);
		}
		// 插入系统日志
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.CONTRACT.getValue());
		sysLog.setOptType(EnumConstants.OptionType.CONTRACT_DETAIL.getValue());// TBD
		sysLog.setRemark("展期借款ID: " + extensionId);
		sysLogService.insert(sysLog);
		return list;
	}

	/**
	 * 
	 * <pre>
	 * 010 还款温馨提示函(展期)
	 * </pre>
	 *
	 * @param extensionId
	 * @return
	 */
	@RequestMapping("/carRepaymentNotice")
	public String carRepaymentNotice(Long extensionId, Model model) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("extensionId", extensionId);
		map.put("type", EnumConstants.ContractType.CAR_REPAYMENT_FUND_EXT.getValue());
		Smallrp smallrp = smallrpService.queryCarCreditByExtensionId(map);
		List<RepaymentPlan> plans = repaymentPlanService.queryRepaymentPlans(smallrp.getLoanId());
		model.addAttribute("contractNo", generateExtensionNo(smallrp.getContractNo(), smallrp.getLoanAgreeNum(), 10));
		model.addAttribute("personName", smallrp.getPersonName());
		model.addAttribute("list", plans);
		model.addAttribute("repayDate", smallrp.getRepayDate());
		SysParameter ownCardName = sysParameterService.getByCode(SysParameterEnum.OWN_CARD_NAME.name());
		model.addAttribute("cardName", ownCardName.getParameterValue());
		SysParameter ownCardNum = sysParameterService.getByCode(SysParameterEnum.OWN_CARD_NUM.name());
		model.addAttribute("cardNum", ownCardNum.getParameterValue());
		SysParameter ownCardBank = sysParameterService.getByCode(SysParameterEnum.OWN_CARD_BANK.name());
		model.addAttribute("cardBank", ownCardBank.getParameterValue());

		model.addAttribute("businessTell", StringUtil.notNullString(smallrp.getTell()));

		BigDecimal contractMoney = smallrp.getPactMoney();
		
		//获取罚息利率开始----
		//设置罚息费率初始值
		BigDecimal overdueInterestRate = new BigDecimal(0.004);
			//获取展期
			Long id = loanExtensionService.getLoanIdByExtensionId(smallrp.getLoanId());
			Loan loan = loanService.get(id);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			//设置时间为2016-09-01 00:00:00
			String dateStr = sysParameterService.getByCode("CAR_FINE_NEW_RULE_EXECUTE_TIME").getParameterValue();
			try {
				//如果借款创建时间是2016.9.1后以后，则费率为新费率，如果是之前，则费率是旧费率
				if (StringUtils.isNotEmpty(dateStr)) {
					if (loan != null && loan.getCreatedTime() != null) {
						Product product = productService.get(loan
								.getProductId());
						if (product != null) {
							overdueInterestRate = product
									.getOverdueInterestRate();
							if (loan.getCreatedTime().getTime()
									- sdf.parse(dateStr).getTime() >= 0) {
								//如果是2016年9月1日之后创建的数据，则采用新费率
								overdueInterestRate = product
										.getOverdueInterestRateCar();

							}
						} else {
							if (loan.getCreatedTime().getTime()
									- sdf.parse(dateStr).getTime() >= 0) {
								overdueInterestRate = new BigDecimal(0.005);
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}				
		contractMoney = contractMoney.multiply(overdueInterestRate);
		// 逾期一天,若罚息金额小于100元罚息则为100元
		model.addAttribute("over1", contractMoney.compareTo(new BigDecimal(100))==-1 ? new BigDecimal(100) :  contractMoney);
		// 逾期十五天,若罚息金额小于100元罚息则为100元
		model.addAttribute("over15", contractMoney.multiply(new BigDecimal(15)).compareTo(new BigDecimal(100))==-1 ? new BigDecimal(100) :  contractMoney.multiply(new BigDecimal(15)));
		// 插入系统日志
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.CONTRACT.getValue());
		sysLog.setOptType(EnumConstants.OptionType.CONTRACT_DETAIL.getValue());
		sysLog.setRemark("展期借款ID: " + extensionId);
		sysLogService.insert(sysLog);
		return "report/carRepaymentNotice";
	}

	/**
	 * <pre>
	 * 011 委托扣款授权书（无风险基金的描述)
	 * </pre>
	 * 
	 * @return
	 */
	@RequestMapping("/carDeductionNotice")
	public String carDeductionNotice(Long extensionId, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("extensionId", extensionId);
		map.put("type", EnumConstants.ContractType.CAR_ENTRUST_EXT.getValue());
		Smallrp smallrp = smallrpService.queryCarCreditByExtensionId(map);

		model.addAttribute("contractNo", generateExtensionNo(smallrp.getContractNo(), smallrp.getLoanAgreeNum(), 11));
		model.addAttribute("personName", smallrp.getPersonName());
		model.addAttribute("idNum", smallrp.getIdNum());
		model.addAttribute("year", splitDayString(smallrp.getSignDate(), "year"));
		model.addAttribute("month", splitDayString(smallrp.getSignDate(), "month"));
		model.addAttribute("day", splitDayString(smallrp.getSignDate(), "day"));
		model.addAttribute("agreementNo", smallrp.getContractNo());

		model.addAttribute("yearExt", splitDayString(smallrp.getStartRepayDate(), "year"));
		model.addAttribute("monthExt", splitDayString(smallrp.getStartRepayDate(), "month"));
		model.addAttribute("dayExt", splitDayString(smallrp.getStartRepayDate(), "day"));

		model.addAttribute("openingBank", smallrp.getOpeningBank().concat(smallrp.getBankBranchName()));
		model.addAttribute("bankBranchName", smallrp.getBankBranchName());
		model.addAttribute("bankAccountNum", smallrp.getBankAccountNum());
		model.addAttribute("contactNo", smallrp.getContact());
		// 插入系统日志
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.CONTRACT.getValue());
		sysLog.setOptType(EnumConstants.OptionType.CONTRACT_DETAIL.getValue());
		sysLog.setRemark("展期借款ID: " + extensionId);
		sysLogService.insert(sysLog);
		return "report/carDeductionNotice";
	}
	
		/*
	 * 计算时间间隔
	 */
	public long getQuot(String time1, String time2) {
		long quot = 0;
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date1 = ft.parse(time1);
			Date date2 = ft.parse(time2);
			quot = date1.getTime() - date2.getTime();
			quot = quot / 1000 / 60 / 60 / 24;
		} catch (Exception e) {
			logger.error("计算时间间隔异常: ", e);
		}
		return quot;
	}

	/*
	 * 取具体位置数字
	 */
	public String getMoneyNumAt(BigDecimal money, int i) {
		String m = String.format("%.2f", money);
		String p = m.substring(0, m.indexOf("."));
		String e = m.substring(m.indexOf(".") + 1, m.indexOf(".") + 3);
		String returnString = "";
		if (i > 0) {
			if (i > p.length() + 1)
				returnString = "";
			// if(i==p.length()+1) returnString="￥";
			if (i < p.length() + 1)
				returnString = p.substring(p.length() - i, p.length() - i + 1);
		} else {
			if (i == -1)
				returnString = e.substring(0, 1);
			if (i == -2)
				returnString = e.substring(1, 2);
		}
		return returnString;
	}

	/*
	 * 分解时间
	 */
	public String splitDayString(Date date, String dayType) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dayString = sdf.format(date);
		String[] dayStringArray = dayString.split("-");
		String returnString = "";
		if (dayType.equals("year")) {
			returnString = dayStringArray[0];
		} else if (dayType.equals("month")) {
			returnString = dayStringArray[1];
		} else if (dayType.equals("day")) {
			returnString = dayStringArray[2];
		}
		return returnString;
	}
	/*
	 * 将小写的人民币转化成大写
	 */
	public String NumberToChinese(BigDecimal number) {
		StringBuffer chineseNumber = new StringBuffer();
		String[] num = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
		String[] unit = { "分", "角", "元", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "万" };
		// String tempNumber = String.valueOf(Math.round((number * 100)));
		String tempNumber = String.valueOf(number.multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP));
		int tempNumberLength = tempNumber.length();
		if ("0".equals(tempNumber)) {
			// return "零元整";
		}
		if (tempNumberLength > 15) {
			try {
				throw new Exception("超出转化范围.");
			} catch (Exception e) {
				logger.error("小写的人民币转化成大写异常: ", e);
			}
		}
		boolean preReadZero = true; // 前面的字符是否读零
		for (int i = tempNumberLength; i > 0; i--) {
			if ((tempNumberLength - i + 2) % 4 == 0) {
				// 如果在（圆,万,亿,万）位上的四个数都为零,如果标志为未读零,则只读零,如果标志为已读零,则略过这四位
				if (i - 4 >= 0 && "0000".equals(tempNumber.substring(i - 4, i))) {
					if (!preReadZero) {
						chineseNumber.insert(0, "零");
						preReadZero = true;
					}
					i -= 3; // 下面还有一个i--
					continue;
				}
				// 如果当前位在（圆,万,亿,万）位上,则设置标志为已读零（即重置读零标志）
				preReadZero = true;
			}
			Integer digit = Integer.parseInt(tempNumber.substring(i - 1, i));
			if (digit == 0) {
				// 如果当前位是零并且标志为未读零,则读零,并设置标志为已读零
				if (!preReadZero) {
					chineseNumber.insert(0, "零");
					preReadZero = true;
				}
				// 如果当前位是零并且在（圆,万,亿,万）位上,则读出（圆,万,亿,万）
				if ((tempNumberLength - i + 2) % 4 == 0) {
					chineseNumber.insert(0, unit[tempNumberLength - i]);
				}
			}
			// 如果当前位不为零,则读出此位,并且设置标志为未读零
			else {
				chineseNumber.insert(0, num[digit] + unit[tempNumberLength - i]);
				preReadZero = false;
			}
		}
		// 如果分角两位上的值都为零,则添加一个"整"字
		if (tempNumberLength - 2 >= 0 && "00".equals(tempNumber.substring(tempNumberLength - 2, tempNumberLength))) {
			// chineseNumber.append("整");
		}
		String returnStr = chineseNumber.toString();
		if (returnStr.endsWith("元")) {
			returnStr = returnStr.substring(0, returnStr.length() - 1);
		}
		return returnStr;
	}
	//
	// //截取double类型小数点后两位
	// public String getFormatDouble(double valueD){
	// String resultStr="";
	// valueD=valueD*100;
	// int a=(int) valueD;
	// double resultValue=(double)a;
	// DecimalFormat df = new DecimalFormat("0.00");
	// resultStr = df.format(resultValue/100);
	// return resultStr;
	// }

	// public String getFormatDoubleWJL(double valueD){
	// String resultStr="";
	// DecimalFormat df = new DecimalFormat("0.00");
	// valueD=Double.parseDouble(df.format(valueD));
	// valueD=valueD*100;
	// int a=(int) valueD;
	// double resultValue=(double)a;
	// resultStr = df.format(resultValue/100);
	// return resultStr;
	// }
	//
	/*
	 * 将小写的人民币转化成大写
	 */
	public String NumberToChineseOther(BigDecimal number) {
		StringBuffer chineseNumber = new StringBuffer();
		String[] num = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
		String[] unit = { "分", "角", "元", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "万" };
		String tempNumber = String.valueOf(number.multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP));
		// String tempNumber = String.valueOf(Math.round((number * 100)));
		int tempNumberLength = tempNumber.length();
		if ("0".equals(tempNumber)) {
			return "零元整";
		}
		if (tempNumberLength > 15) {
			try {
				throw new Exception("超出转化范围.");
			} catch (Exception e) {
				logger.error("小写的人民币转化成大写异常: ", e);
			}
		}
		boolean preReadZero = true; // 前面的字符是否读零
		for (int i = tempNumberLength; i > 0; i--) {
			if ((tempNumberLength - i + 2) % 4 == 0) {
				// 如果在（圆,万,亿,万）位上的四个数都为零,如果标志为未读零,则只读零,如果标志为已读零,则略过这四位
				if (i - 4 >= 0 && "0000".equals(tempNumber.substring(i - 4, i))) {
					if (!preReadZero) {
						chineseNumber.insert(0, "零");
						preReadZero = true;
					}
					i -= 3; // 下面还有一个i--
					continue;
				}
				// 如果当前位在（圆,万,亿,万）位上,则设置标志为已读零（即重置读零标志）
				preReadZero = true;
			}
			Integer digit = Integer.parseInt(tempNumber.substring(i - 1, i));
			if (digit == 0) {
				// 如果当前位是零并且标志为未读零,则读零,并设置标志为已读零
				if (!preReadZero) {
					chineseNumber.insert(0, "零");
					preReadZero = true;
				}
				// 如果当前位是零并且在（圆,万,亿,万）位上,则读出（圆,万,亿,万）
				if ((tempNumberLength - i + 2) % 4 == 0) {
					chineseNumber.insert(0, unit[tempNumberLength - i]);
				}
			}
			// 如果当前位不为零,则读出此位,并且设置标志为未读零
			else {
				chineseNumber.insert(0, num[digit] + unit[tempNumberLength - i]);
				preReadZero = false;
			}
		}
		// 如果分角两位上的值都为零,则添加一个"整"字
		if (tempNumberLength - 2 >= 0 && "00".equals(tempNumber.substring(tempNumberLength - 2, tempNumberLength))) {
			chineseNumber.append("整");
		}
		return chineseNumber.toString();
	}

	/**
	 * 
	 * <pre>
	 * // 展期合同编号--现有代码只支持展23次
	 * </pre>
	 *
	 * @param oldNo
	 * @param agreeNum
	 * @param type
	 * @return
	 */
	private String generateExtensionNo(String oldNo, String agreeNum, int type) {
		if (StringUtil.isEmpty(oldNo)) {
			return oldNo;
		} else if (StringUtil.isEmpty(agreeNum)) {
			return oldNo;
		}
		// 展期合同编号--现有代码只支持展23次
		int num = type + 4 * (Integer.parseInt(agreeNum) - 1);
		String seq = "00" + num;
		if (seq.length() > 3) {
			seq = seq.substring(1, 4);
		}
		String contractNo = oldNo + "-" + seq;
		return contractNo;
	}
	public  String change(double v) {  
		String UNIT = "万千佰拾亿千佰拾万千佰拾元角分";  
		String DIGIT = "零壹贰叁肆伍陆柒捌玖"; 
		double MAX_VALUE = 9999999999999.99D;  
	 if (v < 0 || v > MAX_VALUE){  
	     return "参数非法!";  
	 }  
	 long l = Math.round(v * 100);  
	 if (l == 0){  
	     return "零元整";  
	 }  
	 String strValue = l + "";  
	 // i用来控制数  
	 int i = 0;  
	 // j用来控制单位  
	 int j = UNIT.length() - strValue.length();  
	 String rs = "";  
	 boolean isZero = false;  
	 for (; i < strValue.length(); i++, j++) {  
	  char ch = strValue.charAt(i);  
	  if (ch == '0') {  
	   isZero = true;  
	   if (UNIT.charAt(j) == '亿' || UNIT.charAt(j) == '万' || UNIT.charAt(j) == '元') {  
	    rs = rs + UNIT.charAt(j);  
	    isZero = false;  
	   }  
	  } else {  
	   if (isZero) {  
	    rs = rs + "零";  
	    isZero = false;  
	   }  
	   rs = rs + DIGIT.charAt(ch - '0') + UNIT.charAt(j);  
	  }  
	 }  
	 if (!rs.endsWith("分")) {  
	  rs = rs + "整";  
	 }  
	 rs = rs.replaceAll("亿万", "亿");  
	 return rs;  
	} 
}
