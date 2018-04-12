package com.ezendai.credit2.master.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.udf.UDFFinder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezendai.credit2.after.service.RepayEntryService;
import com.ezendai.credit2.after.vo.RepayEntryDetailsVO;
import com.ezendai.credit2.apply.model.Bank;
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
import com.ezendai.credit2.finance.model.RepayInfo;
import com.ezendai.credit2.finance.service.FlowService;
import com.ezendai.credit2.finance.service.RepayInfoService;
import com.ezendai.credit2.finance.vo.FlowVO;
import com.ezendai.credit2.finance.vo.RepayInfoVO;
import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.framework.model.UserSession;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.dao.SysEnumerateDao;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.master.model.AccountCard;
import com.ezendai.credit2.master.model.BaseArea;
import com.ezendai.credit2.master.model.ComprehensiveSearch;
import com.ezendai.credit2.master.model.LoanDetails;
import com.ezendai.credit2.master.model.SyncLoan;
import com.ezendai.credit2.master.service.BaseAreaService;
import com.ezendai.credit2.master.service.ComprehensiveSearchService;
import com.ezendai.credit2.master.service.SyncLoanService;
import com.ezendai.credit2.master.service.SysEnumerateService;
import com.ezendai.credit2.master.vo.ComprehensiveSearchVO;
import com.ezendai.credit2.report.service.SmallrpService;
import com.ezendai.credit2.system.controller.BaseController;
import com.ezendai.credit2.system.model.SysUser;
import com.ezendai.credit2.system.service.SysLogService;
import com.ezendai.credit2.system.service.SysUserService;

 
@Controller
@RequestMapping("/comprehensiveSearch")
public class ComprehensiveSearchController extends BaseController{
	
	private static final Logger log =Logger.getLogger(ComprehensiveSearchController.class);

	 

	@Autowired
	private ComprehensiveSearchService comprehensiveSearchService ;
	@Autowired
	private SysLogService sysLogService;
	@Autowired
	private LoanService loanService;
	@Autowired
	private PersonService personService;
	@Autowired
	private ProductService productService;
	@Autowired
	private SyncLoanService syncLoanService;
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
	private RepayEntryService repayEntryService;
	@Autowired
	private RepaymentPlanService repaymentPlanService;
	@Autowired
	private RepayInfoService repayInfoService;
	@Autowired
	private FlowService flowService;
	@Autowired
	private ExtensionService extensionService;
	@Autowired
	private PersonBankService personBankService;
	@Autowired
	private SmallrpService smallrpService;
	@RequestMapping("/main")
	public String main(HttpServletRequest request,ModelMap modelMap){
		setDataDictionaryArr(new String[] { EnumConstants.PRODUCT_ID,EnumConstants.PRODUCT_TYPE, EnumConstants.LOAN_STATUS, EnumConstants.CONTRACT_SRC ,EnumConstants.REPAYMENT_PLAN_STATE });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		request.setAttribute(TEXT_RESOURCE_JSON, getTextTypeJson(new Integer[] {EnumConstants.TextResourceType.TABLE_TITLE.getValue(),EnumConstants.TextResourceType.FUNCTION_OPERATION.getValue()}));
		return "master/comprehensiveSearch/comprehensiveMain";     
	}
	
	@RequestMapping("/openTabMain")
	public String openTabMain(HttpServletRequest request,ModelMap modelMap){
		setDataDictionaryArr(new String[] { EnumConstants.PRODUCT_ID,EnumConstants.PRODUCT_TYPE, EnumConstants.LOAN_STATUS, EnumConstants.CONTRACT_SRC  ,EnumConstants.REPAYMENT_PLAN_STATE });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		modelMap.put("loanId", request.getParameter("loanId"));
		modelMap.put("extenId", request.getParameter("extenId"));
		modelMap.put("extensionTime", request.getParameter("extensionTime"));
		return "master/comprehensiveSearch/openTabMain";     
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
			 product=	productService.get(ex.getProductId());
			 person=	personService.get(ex.getPersonId());
			 baseArea=	baseAreaService.get(ex.getSalesDeptId());
			enumstr=sysEnumerateService.findEnumValue("LOAN_STATUS", ex.getStatus());
			crm=	sysUserService.get(ex.getCrmId());
			service=	sysUserService.get(ex.getServiceId());
			 
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
		
	 
	
				SyncLoan syncLoan=	syncLoanService.findSyncLoanByLoanId(loanId);
		 
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
		return "master/comprehensiveSearch/loanDetails";     
	}
	@RequestMapping("/repaymentSummaryInfo")
	public String repaymentSummaryInfo(HttpServletRequest request,ModelMap modelMap){
		Long loanId=Long.parseLong(request.getParameter("loanId"));
		Loan loan=	loanService.get(loanId);
		Person person=	personService.get(loan.getPersonId());
		RepayEntryDetailsVO re=	comprehensiveSearchService.viewEdit(loanId);
		modelMap.put("person", person);
		modelMap.put("re", re);
		return "master/comprehensiveSearch/repaymentSummaryInfo";     
	}
	@RequestMapping("/repaymentPLanListUi")
	public String repaymentPLanListUi(HttpServletRequest request,ModelMap modelMap){
		Long loanId=Long.parseLong(request.getParameter("loanId"));
		String extenId= request.getParameter("extenId");
		modelMap.put("loanId", loanId);
		modelMap.put("extenId", extenId);
		 
		setDataDictionaryArr(new String[] { EnumConstants.PRODUCT_ID,EnumConstants.PRODUCT_TYPE, EnumConstants.LOAN_STATUS, EnumConstants.REPAYMENT_PLAN_STATE });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		return "master/comprehensiveSearch/repaymentPlanListUi";     
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
	@RequestMapping("/accountCardMainUi")
	public String accountCardMainUi(HttpServletRequest request,ModelMap modelMap){
		Long loanId=Long.parseLong(request.getParameter("loanId"));
		String extensionTime= request.getParameter("extensionTime");
		String extenId = request.getParameter("extenId");
		if(extenId != null) {
			modelMap.put("extenId", extenId);
		}else {
			//如果非展期则使用000000代替
			modelMap.put("extenId", "000000");
		}
		modelMap.put("loanId", loanId);
		modelMap.put("extensionTime", extensionTime);
		setDataDictionaryArr(new String[] { EnumConstants.PRODUCT_ID,EnumConstants.PRODUCT_TYPE, EnumConstants.ACCOUNT_TITLE, EnumConstants.TRADE_TYPE });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		return "master/comprehensiveSearch/accountCardMainList";     
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
			/*if(ri.getPayType().equals(EnumConstants.TradeType.ON_TICK.getValue())||ri.getPayType().equals(EnumConstants.TradeType.RISK.getValue())){
				ac.setInitialBalance(ri.getTradeAmount());
				ac.setExpenditure(ri.getTradeAmount());
			}else{
				if(rp.getRepayDay().before(ri.getTradeTime())){
					ac.setIncome(ri.getTradeAmount());
					ac.setExpenditure(ri.getTradeAmount());
				}else{
					ac.setIncome(ri.getTradeAmount());
					ac.setEndingBalance(ri.getTradeAmount());
				}
				
			}*/
			FlowVO  fvo=new FlowVO();
			fvo.setdOrC("C");
			fvo.setAccountId(ri.getAccountId());
			fvo.setTradeNo(ri.getTradeNo());
			fvo.setAccountTitle("111");
			//期初余额
			Flow f=flowService.getFlowByVO(fvo);
			ac.setInitialBalance(f==null ? null:	f.getTradeAmount());
			fvo.setdOrC("D");
			//期末余额
			Flow f2=flowService.getFlowByVO(fvo);
			ac.setEndingBalance(f2==null ? null:f2.getTradeAmount());
			//收入
			ac.setIncome(ri.getTradeAmount());
			//支出
			ac.setExpenditure(new BigDecimal(ri.getTradeAmount().doubleValue()+(f==null ? 0.00 :f.getTradeAmount().doubleValue())-(f2==null ? 0.00 :f2.getTradeAmount().doubleValue())));
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
	} else {
		Long extenId = Long.parseLong(request.getParameter("extenId"));
		RepayInfoVO repayInfoVo=new RepayInfoVO();
		repayInfoVo.setAccountId(extenId);
		List<RepayInfo>  riLisrt=repayInfoService.findListByVO(repayInfoVo);
		for(RepayInfo ri:riLisrt){
			AccountCard ac=new AccountCard();
			BeanUtils.copyProperties(ri, ac);
			/*if(ri.getPayType().equals(EnumConstants.TradeType.ON_TICK.getValue())||ri.getPayType().equals(EnumConstants.TradeType.RISK.getValue())){
				ac.setInitialBalance(ri.getTradeAmount());
				ac.setExpenditure(ri.getTradeAmount());
			}else{
				if(rp.getRepayDay().before(ri.getTradeTime())){
					ac.setIncome(ri.getTradeAmount());
					ac.setExpenditure(ri.getTradeAmount());
				}else{
					ac.setIncome(ri.getTradeAmount());
					ac.setEndingBalance(ri.getTradeAmount());
				}
				
			}*/
			FlowVO  fvo=new FlowVO();
			fvo.setdOrC("C");
			fvo.setAccountId(ri.getAccountId());
			fvo.setTradeNo(ri.getTradeNo());
			fvo.setAccountTitle("111");
			//期初余额
			Flow f=flowService.getFlowByVO(fvo);
			ac.setInitialBalance(f==null ? null:	f.getTradeAmount());
			fvo.setdOrC("D");
			//期末余额
			Flow f2=flowService.getFlowByVO(fvo);
			ac.setEndingBalance(f2==null ? null:f2.getTradeAmount());
			//收入
			ac.setIncome(ri.getTradeAmount());
			//支出
			ac.setExpenditure(new BigDecimal(ri.getTradeAmount().doubleValue()+(f==null ? 0.00 :f.getTradeAmount().doubleValue())-(f2==null ? 0.00 :f2.getTradeAmount().doubleValue())));
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
		String payType=request.getParameter("payType");
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
	@RequestMapping("/getComprehensiveSearchList")
	@ResponseBody
	public Map<String,Object> getComprehensiveSearchList(ComprehensiveSearchVO vo,HttpServletRequest request,int rows, int page){
		UserSession user = ApplicationContext.getUser();
		if(vo.getExtensionTime() != null && vo.getExtensionTime().compareTo(0) == 0) {
			vo.setExtensionTime(-2);
		}
		Pager p = new Pager();
		p.setPage(page);
		p.setRows(rows);
		vo.setPager(p);
		Pager pager = comprehensiveSearchService.getComprehensiveSearchList(vo);
	 	List<ComprehensiveSearch> checkList = pager.getResultList(); 
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("total", pager.getTotalCount());
		result.put("rows", checkList);
		return result;
	}
	


	 
}
