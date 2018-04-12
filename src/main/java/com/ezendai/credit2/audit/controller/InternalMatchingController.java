package com.ezendai.credit2.audit.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezendai.credit2.apply.model.BusinessLog;
import com.ezendai.credit2.apply.model.ChannelPlanCheck;
import com.ezendai.credit2.apply.model.Company;
import com.ezendai.credit2.apply.model.Contacter;
import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.model.Person;
import com.ezendai.credit2.apply.model.Product;
import com.ezendai.credit2.apply.service.BusinessLogService;
import com.ezendai.credit2.apply.service.ChannelPlanCheckService;
import com.ezendai.credit2.apply.service.ContacterService;
import com.ezendai.credit2.apply.service.CreditHistoryService;
import com.ezendai.credit2.apply.service.EduLoanImageService;
import com.ezendai.credit2.apply.service.ExtensionService;
import com.ezendai.credit2.apply.service.GuaranteeService;
import com.ezendai.credit2.apply.service.LoanExtensionService;
import com.ezendai.credit2.apply.service.LoanService;
import com.ezendai.credit2.apply.service.PersonCompanyService;
import com.ezendai.credit2.apply.service.PersonService;
import com.ezendai.credit2.apply.service.PersonTrainingService;
import com.ezendai.credit2.apply.service.ProductDetailService;
import com.ezendai.credit2.apply.service.ProductService;
import com.ezendai.credit2.apply.service.VehicleService;
import com.ezendai.credit2.apply.vo.BusinessLogVO;
import com.ezendai.credit2.apply.vo.ContacterVO;
import com.ezendai.credit2.apply.vo.LoanVO;
import com.ezendai.credit2.after.model.CasesPerson;
import com.ezendai.credit2.audit.model.LoanHistory;
import com.ezendai.credit2.audit.service.ApproveResultService;
import com.ezendai.credit2.audit.service.InternalMatchingService;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.master.service.BaseAreaService;
import com.ezendai.credit2.master.service.BlacklistService;
import com.ezendai.credit2.master.service.CompanyService;
import com.ezendai.credit2.master.service.SalesDepartmentService;
import com.ezendai.credit2.system.Credit2Properties;
import com.ezendai.credit2.system.controller.BaseController;
import com.ezendai.credit2.system.service.OrganSalesDepartmentService;
import com.ezendai.credit2.system.service.OrganSalesManagerService;
import com.ezendai.credit2.system.service.OrganService;
import com.ezendai.credit2.system.service.SysGroupUserService;
import com.ezendai.credit2.system.service.SysLogService;
import com.ezendai.credit2.system.service.SysParameterService;
import com.ezendai.credit2.system.service.SysUserService;

/**
 * <pre>
 *内部匹配
 * </pre>
 *
 *   
 */
@Controller
@RequestMapping("/intrenalMatching")
public class InternalMatchingController extends BaseController {

	@Autowired
	private VehicleService vehicleService;
	@Autowired
	private ApproveResultService approveResultService;
	@Autowired
	private ProductService productService;
	@Autowired
	private PersonService personService;
	@Autowired
	private PersonCompanyService personCompanyService;
	@Autowired
	private ContacterService contacterService;
	@Autowired
	private GuaranteeService guaranteeService;
	@Autowired
	private LoanService loanService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private CreditHistoryService creditHistoryService;
	@Autowired
	private SalesDepartmentService salesDeptService;
	@Autowired
	private BaseAreaService baseAreaService;
	@Autowired
	private SysLogService sysLogService;
	@Autowired
	private BlacklistService blacklistService;
	@Autowired
	private SysParameterService sysParameterService;
	@Autowired
	private ExtensionService extensionService;
	@Autowired
	private LoanExtensionService loanExtensionService;
	@Autowired
	private ProductDetailService productDetailService;
	@Autowired
	private BusinessLogService businessLogService;
	
	@Autowired
	private PersonTrainingService personTrainingService;
	
	@Autowired
	private OrganSalesDepartmentService organSalesDepartmentService;
	@Autowired
	private OrganService organService;
	
	@Autowired
	ChannelPlanCheckService channelPlanCheckService;
	@Autowired
	OrganSalesManagerService organSalesManagerService;
	@Autowired
	EduLoanImageService eduLoanImageService ;
	@Autowired
	private Credit2Properties credit2Properties;
	@Autowired
	private SysGroupUserService  sysGroupUserService;
	@Autowired
	private InternalMatchingService  matchingService;


	private static final Logger logger = Logger.getLogger(InternalMatchingController.class);

	//表单提交时间类型自动转换
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		CustomDateEditor dateEditor = new CustomDateEditor(fmt, true);
		binder.registerCustomEditor(Date.class, dateEditor);
	}
	
	@RequestMapping("/init")
	public String viewList(HttpServletRequest request,ModelMap modelMap) {
		/* 设置数据字典 */
		setDataDictionaryArr(new String[] { EnumConstants.LOAN_STATUS, EnumConstants.PRODUCT_TYPE, EnumConstants.HAVE_HOUSE_STATUS,EnumConstants.REPAYMENT_PLAN_STATE });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		String loanId=request.getParameter("loanId");
		Loan l= loanService.get(Long.parseLong(loanId));
		ChannelPlanCheck channelPlanCheck = channelPlanCheckService.getReplyById(l.getSchemeID());
		LoanVO lvo=new LoanVO();
		lvo.setPersonId(l.getPersonId());
		Person p=personService.get(l.getPersonId());
		Product pro=	productService.get(l.getProductId());
		modelMap.put("person", p);
		modelMap.put("loan", l);
		modelMap.put("product",pro);
		modelMap.put("actualRate",channelPlanCheck.getActualRate());
		return "audit/internalMatching/InternalMatchingMain";
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/loanHistoryList/{personId}")
	@ResponseBody
	public Map loanHistoryList(@PathVariable("personId") String personId,int rows, int page,HttpServletRequest request) {
		LoanHistory lh=new LoanHistory();
		lh.setPersonId(Long.parseLong(personId));
		String loanId=request.getParameter("loanId");
		lh.setLoanId(Long.parseLong(loanId));
		Pager p = new Pager();
		p.setPage(page);
		p.setRows(rows);
		lh.setPager(p);
		Pager pager = matchingService.findLoanHistoryWithPG(lh);
	 	List<LoanHistory> lhList = pager.getResultList(); 
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("total", pager.getTotalCount());
		result.put("rows", lhList);
		return result;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/matchingList")
	@ResponseBody
	public Map matchingList(int rows, int page,HttpServletRequest request) {
		List<LoanHistory> allList=new  ArrayList<LoanHistory>();
	
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		
		String loanId=request.getParameter("loanId");
		
		// 根据personid 获取手机号 和企业电话
		String personId=request.getParameter("personId");
		Person per=	personService.get(Long.parseLong(personId));
		String personPhone=per.getMobilePhone();
		Company company=personCompanyService.getCompanyById(per.getCompanyId());
		String companyTel=company.getPhone();
		/*ContacterVO contacterVo=new ContacterVO();
		contacterVo.setMobilePhone(personPhone);
		List<Contacter> contacterList=contacterService.findListByVo(contacterVo);*/
		// 联系人-手机  匹配信息
		LoanHistory lh1=new LoanHistory(); 
		lh1.setMatchIngPhone(personPhone);
		lh1.setPersonId(Long.parseLong(personId));
		lh1.setLoanId(Long.parseLong(loanId));
		if(personPhone!=null){
		List<LoanHistory>	optionConPhone= matchingService.selectOptionContacterPhone(lh1);
	 
		for(LoanHistory optioncon:optionConPhone){
			optioncon.setMatchIngPhone(personPhone);
			optioncon.setRequestDataOption("手机");
			optioncon.setRequestCorrespondingMsg(per.getName());
			optioncon.setMatchingDataOption("联系人-手机");
			
		}
		allList.addAll(optionConPhone);}
		// 手机  匹配信息
		LoanHistory lh2=new LoanHistory(); 
		lh2.setMatchIngPhone(personPhone);
		lh2.setPersonId(Long.parseLong(personId));
		lh2.setLoanId(Long.parseLong(loanId));
		if(personPhone!=null){
		List<LoanHistory>	optionPerPhone= matchingService.selectOptionPersonPhone(lh2);
		for(LoanHistory optioncon:optionPerPhone){
			optioncon.setMatchIngPhone(personPhone);
			optioncon.setRequestDataOption("手机");
			optioncon.setRequestCorrespondingMsg(per.getName());
			optioncon.setMatchingDataOption("手机");
			
		}
		allList.addAll(optionPerPhone);}
		
			//企业电话    匹配项 家庭电话
				LoanHistory lh3=new LoanHistory(); 
				if(company.getPhone()!=null){
				lh3.setMatchIngPhone(company.getPhone());
				lh3.setPersonId(Long.parseLong(personId));
				lh3.setLoanId(Long.parseLong(loanId));
				List<LoanHistory>	optionHomeTel= matchingService.selectOptionHomePhone(lh3);
				for(LoanHistory optioncon:optionHomeTel){
					optioncon.setMatchIngPhone(company.getPhone());
					optioncon.setRequestDataOption("企业电话");
					optioncon.setRequestCorrespondingMsg(per.getAddress());
					optioncon.setMatchingDataOption("家庭电话");
					
				}
				allList.addAll(optionHomeTel);
				}
				//企业电话    匹配项  企业电话
				LoanHistory lh4=new LoanHistory(); 
				if(company.getPhone()!=null){
				lh4.setMatchIngPhone(company.getPhone());
				lh4.setPersonId(Long.parseLong(personId));
				lh4.setLoanId(Long.parseLong(loanId));
				List<LoanHistory>	optionCompanyTel= matchingService.selectOptionCompanyPhone(lh4);
				for(LoanHistory optioncon:optionCompanyTel){
					optioncon.setMatchIngPhone(company.getPhone());
					optioncon.setRequestDataOption("企业电话");
					optioncon.setRequestCorrespondingMsg(company.getName());
					optioncon.setMatchingDataOption("企业电话");
				}
				allList.addAll(optionCompanyTel);		
				}
				
				//计算 起数
				int start =0;
				if(page>1){
					start=rows*(page-1);
				}
				//计算 终止数
				int end =0;
					end =start+rows;
				//分页list
				List<LoanHistory> resultList=new ArrayList<LoanHistory>();
				
				for (int index=start;index<end;index++){
					if(index>=allList.size()){
						break;
					}
					resultList.add(allList.get(index));
				}		
				
		result.put("total", allList.size());
		result.put("rows", resultList);
		return result;
	}

	 
	/***
	 * 
	 * <pre>
	 * 判断是否拒单录入
	 * </pre>
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("/isRefusal")
	@ResponseBody
	public String isRefusal(HttpServletRequest request) {
		String loanId=request.getParameter("loanId");
		BusinessLogVO businessLogVO=new BusinessLogVO();
		businessLogVO.setLoanId(Long.parseLong(loanId));
		businessLogVO.setFlowStatus(EnumConstants.BusinessLogStatus.REFUSAL_ENTRY_CREATE.getValue());
		List<BusinessLog> list=  businessLogService.getLogByVO(businessLogVO);
		if(list!=null &&list.size()>0){
			return "1";
		}else{
			return "0";
		}
		
	}
	
}
