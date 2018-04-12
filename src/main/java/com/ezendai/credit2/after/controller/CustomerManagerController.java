/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.after.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezendai.credit2.after.service.AfterLoanService;
import com.ezendai.credit2.after.service.CustomerManagerService;
import com.ezendai.credit2.after.vo.CustomerManagerVO;
import com.ezendai.credit2.after.vo.RepayEntryDetailsVO;
import com.ezendai.credit2.apply.model.BankAccount;
import com.ezendai.credit2.apply.model.Extension;
import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.model.Person;
import com.ezendai.credit2.apply.model.Product;
import com.ezendai.credit2.apply.service.BankAccountService;
import com.ezendai.credit2.apply.service.BankService;
import com.ezendai.credit2.apply.service.ExtensionService;
import com.ezendai.credit2.apply.service.LoanService;
import com.ezendai.credit2.apply.service.PersonService;
import com.ezendai.credit2.apply.service.ProductService;
import com.ezendai.credit2.audit.model.PersonBank;
import com.ezendai.credit2.audit.model.RepaymentPlan;
import com.ezendai.credit2.audit.service.PersonBankService;
import com.ezendai.credit2.audit.service.RepaymentPlanService;
import com.ezendai.credit2.audit.vo.PersonBankVO;
import com.ezendai.credit2.audit.vo.RepaymentPlanVO;
import com.ezendai.credit2.finance.model.Flow;
import com.ezendai.credit2.finance.model.Ledger;
import com.ezendai.credit2.finance.model.RepayInfo;
import com.ezendai.credit2.finance.service.FlowService;
import com.ezendai.credit2.finance.service.LedgerService;
import com.ezendai.credit2.finance.service.RepayInfoService;
import com.ezendai.credit2.finance.vo.FlowVO;
import com.ezendai.credit2.finance.vo.RepayInfoVO;
import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.framework.exception.BusinessException;
import com.ezendai.credit2.framework.util.DateUtil;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.master.model.AccountCard;
import com.ezendai.credit2.master.model.BaseArea;
import com.ezendai.credit2.master.model.LoanDetails;
import com.ezendai.credit2.master.service.BaseAreaService;
import com.ezendai.credit2.master.service.SyncLoanService;
import com.ezendai.credit2.master.service.SysEnumerateService;
import com.ezendai.credit2.system.controller.BaseController;
import com.ezendai.credit2.system.model.SysLog;
import com.ezendai.credit2.system.model.SysUser;
import com.ezendai.credit2.system.service.SysLogService;
import com.ezendai.credit2.system.service.SysUserService;

/**
 * <pre>
 * 账户管理
 * </pre>
 *
 * @author rencq
 */
@Controller
@RequestMapping("/after/customerManager")
public class CustomerManagerController extends BaseController{
	@Autowired
	private CustomerManagerService customerManagerService;
	@Autowired
	private LoanService loanService;
	@Autowired
	private PersonService personService;
	@Autowired
	private ProductService productService;
	@Autowired
	private BaseAreaService baseAreaService;
	@Autowired
	private SysEnumerateService sysEnumerateService;
	@Autowired
	private BankAccountService bankService;
	@Autowired
	private BankService bank2Service;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private ExtensionService extensionService;
	@Autowired
	private SyncLoanService syncLoanService;
	@Autowired
	private PersonBankService personBankService;
	@Autowired
	private RepayInfoService repayInfoService;
	@Autowired
	private FlowService flowService;
	@Autowired
	private RepaymentPlanService repaymentPlanService;
	@Autowired
	private AfterLoanService afterLoanService;
	@Autowired
	private SysLogService sysLogService;
	@Autowired
	private LedgerService ledgerService;
	
	private static final Logger log =Logger.getLogger(CustomerManagerController.class);
	
	@RequestMapping("/initMain")
	public String init(HttpServletRequest request) {
		setDataDictionaryArr(new String[] { EnumConstants.PRODUCT_ID,EnumConstants.LOAN_STATUS ,EnumConstants.REPAYMENT_PLAN_STATE});
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		return "after/customerManager/customerList";
	}
	
	/**
	 * 
	 * <pre>
	 * 获取账户信息
	 * </pre>
	 *
	 * @param loanVO
	 * @param rows
	 * @param page
	 * @return
	 */
	@RequestMapping("/getCustomerList")
	@ResponseBody
	public Map<String, Object> getPenaltyReducePage(CustomerManagerVO cvo, int rows, int page) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		Pager pager = new Pager();
		pager.setPage(page);
		pager.setRows(rows);
		pager.setSort("ASC");
		cvo.setPager(pager);
		pager = customerManagerService.getCustomerManagerList(cvo);
		result.put("total", pager.getTotalCount());
		result.put("rows", pager.getResultList());
		return result;
	}
	
	@RequestMapping("/openTabMain")
	public String openTabMain(HttpServletRequest request,ModelMap modelMap){
		setDataDictionaryArr(new String[] { EnumConstants.PRODUCT_ID,EnumConstants.PRODUCT_TYPE, EnumConstants.LOAN_STATUS, EnumConstants.CONTRACT_SRC  ,EnumConstants.REPAYMENT_PLAN_STATE });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		modelMap.put("loanId", request.getParameter("loanId"));
		modelMap.put("extenId", request.getParameter("extenId"));
		modelMap.put("extensionTime", request.getParameter("extensionTime"));
		return "after/customerManager/openTabMain";
	}
	
	@RequestMapping("/loanDetails")
	public String loanDetails(HttpServletRequest request,ModelMap modelMap){
		setDataDictionaryArr(new String[] { EnumConstants.PRODUCT_ID,EnumConstants.PRODUCT_TYPE, EnumConstants.LOAN_STATUS, EnumConstants.CONTRACT_SRC ,EnumConstants.REPAYMENT_PLAN_STATE });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		Loan loan=new Loan();
		 
		String extensionTime= request.getParameter("extensionTime");
		Long loanId=Long.parseLong(request.getParameter("loanId"));
		  Product product=null;
		  Person person=null;	
		  BaseArea baseArea=null;
		  String enumstr=null;
			SysUser crm=null;
			SysUser service=null;
			LoanDetails ld=new LoanDetails();
			  loan=	loanService.get(loanId);
		if(extensionTime.equals("0")){
		
			
			  product=	productService.get(loan.getProductId());
			  baseArea=	baseAreaService.get(loan.getSalesDeptId());
			  person=	personService.get(loan.getPersonId());
			  enumstr=sysEnumerateService.findEnumValue("LOAN_STATUS", loan.getStatus());
			  crm=	sysUserService.get(loan.getCrmId());
			  service=	sysUserService.get(loan.getServiceId());
			 
				if(loan.getContractSrc()!=null){
					if(loan.getContractSrc().equals(EnumConstants.ContractSrc.P2P.getValue())){ld.setContractSource("证大P2P");}else{ld.setContractSource("证大爱特");};
				}
				ld.setAuditMoney(loan.getAuditMoney()); 
				ld.setRequestMoney(loan.getRequestMoney()); 
				ld.setTime(loan.getTime());
				ld.setRequestDate(loan.getRequestDate());
		}else{
			Long extenId=Long.parseLong(request.getParameter("extenId"));
			Extension ex=  extensionService.get(extenId);
			product = productService.get(ex.getProductId());
			person = personService.get(ex.getPersonId());
			baseArea = baseAreaService.get(ex.getSalesDeptId());
			enumstr = sysEnumerateService.findEnumValue("LOAN_STATUS", ex.getStatus());
			crm = sysUserService.get(ex.getCrmId());
			service = sysUserService.get(ex.getServiceId());
			 
			ld.setTime(ex.getTime()==null?null:ex.getTime().longValue());
		
			 
			if(ex.getContractSrc()!=null){
				if(ex.getContractSrc().equals(EnumConstants.ContractSrc.P2P.getValue())){ld.setContractSource("证大P2P");}else{ld.setContractSource("证大爱特");};
			}
			ld.setAuditMoney(ex.getAuditMoney()); 
			ld.setRequestMoney(ex.getRequestMoney()); 
			//ld.setRequestTime(ex.getRequestTime()); 
		 
			ld.setRequestDate(ex.getRequestDate());
			//ld.setPurpose(ex.getPurpose());
		}	
		
//		SyncLoan syncLoan=	syncLoanService.findSyncLoanByLoanId(loanId);
	    PersonBankVO	personBankVO=new PersonBankVO();
	    personBankVO.setPersonId(loan.getPersonId());
	    personBankVO.setLoanId(loan.getLoanId());
		PersonBank personBank=	personBankService.getPersonBank(personBankVO);
		BankAccount bank=null;
		if(personBank!=null){
			bank=	bankService.get(personBank.getBankAccountId());
		}
		
		ld.setGantBank(bank==null?"":bank.getBankName()+"   "+bank.getAccount());
		if(extensionTime.equals("0")){
			ld.setRepayBank(ld.getGantBank());
		}
	
		if (EnumConstants.ProductList.STUDENT_LOAN.getValue().equals(loan.getProductId())) {
			if(loan.getGrantAccountId() != null){
				BankAccount bankAccount = bankService.getBankAccountDetails(loan.getGrantAccountId());
				ld.setGantBank(bankAccount==null?"":bankAccount.getBankName()+"   "+bankAccount.getAccount());
			}
		}
					
		ld.setRequestTime(loan.getRequestTime()); 
	
		ld.setPurpose(loan.getPurpose());
	 
		ld.setPersonName(person.getName());
		if(person.getSex().equals(EnumConstants.Sex.男.getValue())){ld.setSex("男");}else{ld.setSex("女");};
		ld.setIdNum(person.getIdnum());
	
		ld.setStatusStr(enumstr);
		ld.setMaxRepayAmount(person.getIncomePerMonth() ==null ?null:person.getIncomePerMonth().doubleValue());
	
		ld.setSalesDept(baseArea.getName()); 
		ld.setPrudentName(product.getProductName());
		ld.setCrmName(crm ==null ? null:crm.getName());
		ld.setServiceName(service ==null ? null: service.getName());
		modelMap.put("ld", ld);
		return "after/customerManager/loanDetails";     
	}
	
	@RequestMapping("/repaymentSummaryInfo")
	public String repaymentSummaryInfo(HttpServletRequest request,ModelMap modelMap){
		Long loanId=Long.parseLong(request.getParameter("loanId"));
		Loan loan=	loanService.get(loanId);
		Person person=	personService.get(loan.getPersonId());
		RepayEntryDetailsVO re=	customerManagerService.viewEdit(loanId);
		modelMap.put("person", person);
		modelMap.put("re", re);
		return "after/customerManager/repaymentSummaryInfo";     
	}
	
	@RequestMapping("/repaymentPLanListUi")
	public String repaymentPLanListUi(HttpServletRequest request,ModelMap modelMap){
		Long loanId=Long.parseLong(request.getParameter("loanId"));
		String extenId= request.getParameter("extenId");
		modelMap.put("loanId", loanId);
		modelMap.put("extenId", extenId);
		 
		setDataDictionaryArr(new String[] { EnumConstants.PRODUCT_ID,EnumConstants.PRODUCT_TYPE, EnumConstants.LOAN_STATUS, EnumConstants.REPAYMENT_PLAN_STATE });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		return "after/customerManager/repaymentPlanListUi";     
	}
	
	@RequestMapping("/accountCardMainUi")
	public String accountCardMainUi(HttpServletRequest request,ModelMap modelMap){
		Long loanId=Long.parseLong(request.getParameter("loanId"));
		String extensionTime= request.getParameter("extensionTime");
		modelMap.put("loanId", loanId);
		modelMap.put("extensionTime", extensionTime);
		setDataDictionaryArr(new String[] { EnumConstants.PRODUCT_ID,EnumConstants.PRODUCT_TYPE, EnumConstants.ACCOUNT_TITLE, EnumConstants.TRADE_TYPE });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		return "after/customerManager/accountCardMainList";     
	}
	
	@RequestMapping("/accountCardMainList")
	@ResponseBody
	public Map<String,Object> accountCardMainList(HttpServletRequest request,ModelMap modelMap){
		Long loanId=Long.parseLong(request.getParameter("loanId"));
		String extensionTime= request.getParameter("extensionTime");
		List<AccountCard>  acLisrt= new ArrayList<AccountCard>();
		if(extensionTime.equals("0")){
		RepayInfoVO repayInfoVo=new RepayInfoVO();
		repayInfoVo.setAccountId(loanId);
		List<RepayInfo>  riLisrt=repayInfoService.findListByVO(repayInfoVo);
		for(RepayInfo ri:riLisrt){
			AccountCard ac=new AccountCard();
			BeanUtils.copyProperties(ri, ac);
			FlowVO  fvo=new FlowVO();
			fvo.setdOrC("C"); //C
			fvo.setAccountId(ri.getAccountId());
			fvo.setTradeNo(ri.getTradeNo());
			fvo.setAccountTitle("111");
			Flow f=flowService.getFlowByVO(fvo);
			Ledger legder = ledgerService.getLedgerByAccountId(loanId);
			//期初余额
			if("4003".equals(ri.getTradeCode())){
				ac.setInitialBalance(legder==null?null:legder.getCash()==null?new BigDecimal(0):legder.getCash().add(f==null?new BigDecimal(0):f.getTradeAmount()));
			}else{
				ac.setInitialBalance(f==null ? null:	f.getTradeAmount());
			}
			fvo.setdOrC("D");
			//期末余额
			Flow f2=flowService.getFlowByVO(fvo);
			if("4003".equals(ri.getTradeCode())){
				ac.setEndingBalance(legder==null?new BigDecimal(0):legder.getCash());
			}else{
				ac.setEndingBalance(f2==null ? null:f2.getTradeAmount());
			}
			//收入
			if("4003".equals(ri.getTradeCode())){
				ac.setIncome(new BigDecimal(0));
			}else{
				ac.setIncome(ri.getTradeAmount());
			}
			//支出
			if("4003".equals(ri.getTradeCode())){
				ac.setExpenditure(ri.getTradeAmount());
			}else{
				ac.setExpenditure(new BigDecimal(ri.getTradeAmount().doubleValue()+(f==null ? 0.00 :f.getTradeAmount().doubleValue())-(f2==null ? 0.00 :f2.getTradeAmount().doubleValue())));
			}
			if(ac.getExpenditure().doubleValue()<0){
				ac.setExpenditure(new BigDecimal(0));
			}
			if(ac.getTradeCode().equals("4001")){
				ac.setIncome(null);
				ac.setEndingBalance(null);
				ac.setInitialBalance(null);
			}
			if(ac.getPayType().equals(EnumConstants.TradeType.RISK.getValue())){
				ac.setIncome(null);
				ac.setEndingBalance(null);
				ac.setInitialBalance(null);
			}
			acLisrt.add(ac);
			}
		}
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("total",acLisrt==null ? 0:acLisrt.size());
		result.put("rows", acLisrt);
		return result;        
	}
	
	@RequestMapping("/accountCardFlow")
	@ResponseBody
	public Map<String,Object> accountCardFlow(HttpServletRequest request,ModelMap modelMap){
		Long loanId=Long.parseLong(request.getParameter("loanId"));
		String tradeNo=request.getParameter("tradeNo");
//		String payType=request.getParameter("payType");
		FlowVO flowVO=new FlowVO();
		flowVO.setAccountId(loanId);
		flowVO.setTradeNo(tradeNo);
		
		List<Flow>  flowList=flowService.getListFlowByVO(flowVO);
		flowVO.setAccountId(null);
		flowVO.setOppAccount(loanId);
		List<Flow>  jmflowList=flowService.getListFlowByVO(flowVO);
		flowList.addAll(jmflowList);
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("total",flowList==null ? 0:flowList.size());
		result.put("rows", flowList);
		return result;     
	}
	
	@RequestMapping("/repaymentPLanList")
	@ResponseBody
	public Map<String,Object> repaymentPLanList(HttpServletRequest request,ModelMap modelMap){
		Long loanId=Long.parseLong(request.getParameter("loanId"));
		RepaymentPlanVO repaymentPlanVO=new RepaymentPlanVO();
		repaymentPlanVO.setLoanId(loanId);
		List<RepaymentPlan>  rpLisrt=repaymentPlanService.findListByVO(repaymentPlanVO);
		 String extenId=request.getParameter("extenId");
		if (!extenId.equals("null")) {
			RepaymentPlanVO repaymentPlanVo= new RepaymentPlanVO();
			repaymentPlanVo.setLoanId(Long.parseLong(extenId));
			rpLisrt=repaymentPlanService.getRepaymentPlanExtends(repaymentPlanVo);
		} 
		
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("total",rpLisrt==null ? 0:rpLisrt.size());
		result.put("rows", rpLisrt);
		return result;     
	}
	
	/**
	 * 
	 * @Description:提现操作
	 * @param 
	 * @return List<RefusedReason>  
	 * @throws
	 * @author rencq
	 */
	@RequestMapping(value="/tiXianMethod",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> tiXianMethod(RepayInfoVO vo){
		log.info(".......提现操作开始.......");
		Map<String,Object> map = new HashMap<String,Object>();
		Long userId = ApplicationContext.getUser().getId();
		String userName = ApplicationContext.getUser().getName();
		Loan loan = loanService.get(vo.getAccountId());
		String tradeNo = repayInfoService.generatePayTradeNo();
		int version = 1;
		boolean isSuccess = true;
		String msg = "";
		//封装REPAY_INFO
		RepayInfo repayInfo = new RepayInfo();
		if(vo != null){
			repayInfo.setAccountId(vo.getAccountId()); //借款ID
			repayInfo.setTradeAmount(vo.getTradeAmount()); //账户余额-提现金额
			repayInfo.setTradeTime(new Date()); //交易时间
			repayInfo.setTradeKind(EnumConstants.TradeKind.NORMAL_TRADE.getValue());
			repayInfo.setPayType(1);	//交易码    1-现金
			repayInfo.setTradeCode("4003"); //交易种类
			repayInfo.setRemark(vo.getRemark()); //备注
			repayInfo.setTradeNo(tradeNo); //交易流水号
			repayInfo.setTeller(userId); //柜员号
			repayInfo.setVersion((long)version); //版本号
			repayInfo.setSalesdepartmentId(loan.getSalesDeptId()); //营业网点ID
			repayInfo.setCreatorId(userId);
			repayInfo.setCreator(userName);
			repayInfo.setCreatedTime(new Date());
			repayInfo.setCash(vo.getCash());
		}
		boolean repayDealResult;
		Extension extension = null;
		try {
			if (loan != null) {
				repayInfo.setTerm(loan.getTime() - loan.getResidualTime() + 1);
				repayDealResult = customerManagerService.repayDeal(repayInfo);
			} else {
				extension = extensionService.get(vo.getAccountId());
				repayInfo.setTerm((long) (extension.getTime() - extension.getResidualTime() + 1));
				// 展期
				repayDealResult = customerManagerService.repayDealExtension(repayInfo);
			}
			SysLog sysLog = new SysLog();
			sysLog.setOptModule(EnumConstants.OptionModule.WITHDRAW_CASH.getValue());
			sysLog.setOptType(EnumConstants.OptionType.WITH_DRAW_CASH.getValue());
			sysLog.setRemark("提现操作:" + vo.getAccountId() + ",TradeAmount:" + vo.getTradeAmount() + ",TradeTime:" + DateUtil.defaultFormatDate(new Date())
								+ ",TradeKind:" + EnumConstants.TradeKind.NORMAL_TRADE.getValue() + ",PayType:" + repayInfo.getPayType() + ",Remark:" + repayInfo.getRemark()
								+ ",TradeNo:" + tradeNo + ",Teller:" + ApplicationContext.getUser().getId() + ",TradeCode:" + repayInfo.getTradeCode()
								+ ",repayDealResult:" + repayDealResult);
			sysLogService.insert(sysLog);
			if(repayDealResult){
				msg = "提现成功";
				log.info("提现成功！");
			}else{
				msg = "提现失败";
				log.info("提现失败！");
			}

		} catch(BusinessException ex) {
			isSuccess = false;
			msg = "提现失败";
			log.info("提现失败！");
		} catch(Exception ex) {
			isSuccess = false;
			msg = ex.getMessage();
			log.info("提现失败！");
		}
		map.put("isSuccess", isSuccess);
		map.put("msg", msg);
		
		log.info("------提现操作结束.......");
		return map;
	}
}
