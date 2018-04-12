package com.ezendai.credit2.audit.controller; 
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
 
import org.springframework.stereotype.Controller;
 
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezendai.credit2.apply.model.BusinessLog;
import com.ezendai.credit2.apply.model.ChannelPlanCheck;
import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.model.Person;
import com.ezendai.credit2.apply.service.BankAccountService;
import com.ezendai.credit2.apply.service.BusinessLogService;
import com.ezendai.credit2.apply.service.ChannelPlanCheckService;
import com.ezendai.credit2.apply.service.LoanService;
import com.ezendai.credit2.apply.service.PersonService;
import com.ezendai.credit2.apply.vo.BusinessLogVO;
import com.ezendai.credit2.apply.vo.LoanVO;
import com.ezendai.credit2.audit.model.FirstApprove;
import com.ezendai.credit2.audit.model.PersonBank;
import com.ezendai.credit2.audit.service.ContractService;
import com.ezendai.credit2.audit.service.FirstApproveService;
import com.ezendai.credit2.audit.service.PersonBankService;
import com.ezendai.credit2.audit.service.RepaymentPlanService;
import com.ezendai.credit2.audit.vo.FirstApproveVO;
import com.ezendai.credit2.audit.vo.PersonBankVO;
import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.framework.model.UserSession;
import com.ezendai.credit2.framework.util.CollectionUtil;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.framework.util.StringUtil;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.master.model.BaseArea;
import com.ezendai.credit2.master.service.BlacklistService;
import com.ezendai.credit2.master.service.SalesDepartmentService;
import com.ezendai.credit2.master.vo.BlacklistVO;
import com.ezendai.credit2.system.controller.BaseController;
import com.ezendai.credit2.system.enumerate.SysParameterEnum;
import com.ezendai.credit2.system.model.SysLog;
import com.ezendai.credit2.system.model.SysParameter;
import com.ezendai.credit2.system.model.SysUser;
import com.ezendai.credit2.system.service.SysLogService;
import com.ezendai.credit2.system.service.SysParameterService;
import com.ezendai.credit2.system.service.SysUserService;
@Controller
@RequestMapping("/approvePhone/approvePhoneData")
public class ApprovePhoneController extends BaseController {
	@Autowired
	private FirstApproveService firstService;
	@Autowired
	private LoanService loanService;
	@Autowired
	private BusinessLogService businessLogService;
	@Autowired
	private SysLogService sysLogService;
	@Autowired
	private RepaymentPlanService repaymentPlanService;
	@Autowired
	private ContractService contractService;
	@Autowired
	private BankAccountService bankAccountService;
	@Autowired
	private PersonBankService personBankService;
	@Autowired
	private PersonService personService;
	@Autowired
	private BlacklistService blacklistService;
	@Autowired
	private SysParameterService sysParameterService;
	@Autowired
	private SalesDepartmentService salesDeptService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private ChannelPlanCheckService channelPlanCheckService;
	
	private static final Logger logger = Logger.getLogger(ApprovePhoneController.class);
	 
	 
	@RequestMapping("/init")
	public String init(HttpServletRequest request, ModelMap modelMap) {
		/*设置数据字典*/
		setDataDictionaryArr(new String[] { EnumConstants.LOAN_STATUS, EnumConstants.PRODUCT_TYPE, EnumConstants.CONTRACT_SRC,EnumConstants.REPAYMENT_PLAN_STATE });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		UserSession user=	ApplicationContext.getUser();
		String acceptAudit= firstService.getAcceptAudit(user.getId());
		modelMap.put("acceptAudit", acceptAudit);
		request.setAttribute("userLoginName", user.getLoginName());
	 	return "audit/approvePhone/approvePhoneMain"; 
	/*	return "/finance/financialAudit/financialAuditList";*/
	}
	@RequestMapping("/toApproveTab")
	public String toApproveTab(HttpServletRequest request) {
		/*设置数据字典*/
		setDataDictionaryArr(new String[] { EnumConstants.LOAN_STATUS, EnumConstants.PRODUCT_TYPE, EnumConstants.CONTRACT_SRC,EnumConstants.REPAYMENT_PLAN_STATE  });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
	 	return "audit/approvePhone/approveList"; 
	 
	}
	 
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/listData")
	@ResponseBody
	public Map list( FirstApproveVO vo, int rows, int page) throws ParseException {
		Pager p = new Pager();
		p.setPage(page);
		p.setRows(rows);
		p.setSidx("request_date");
		p.setSort("asc");
		vo.setPager(p);
		List<Integer> statusList = new ArrayList<Integer>();
		if(StringUtil.isEmpty(vo.getSearchFlag())){			
			/**逾期*/
			statusList.add(EnumConstants.LoanStatus.逾期.getValue());
			vo.setStatusList(statusList);
		}else{
			if(vo.getStatus() ==0){
				/**正常*/
				statusList.add(EnumConstants.LoanStatus.正常.getValue());
				/**逾期*/
				statusList.add(EnumConstants.LoanStatus.逾期.getValue());
				/**预结清*/
				statusList.add(EnumConstants.LoanStatus.预结清.getValue());
				/**结清*/
				statusList.add(EnumConstants.LoanStatus.结清.getValue());
				/**坏账*/
				statusList.add(EnumConstants.LoanStatus.坏账.getValue());
				vo.setStatusList(statusList);
			}else{
			statusList.add(vo.getStatus());
			vo.setStatusList(statusList);
			}

		}
		if(!StringUtil.isEmpty(vo.getStartDate())){
			 
			vo.setStartDate(vo.getStartDate()+" 00:00:00");
		}
		if(!StringUtil.isEmpty(vo.getEndDate())){
			vo.setEndDate((vo.getEndDate()+" 23:59:59"));
		}
		if(vo.getAuditDateEndEdu()!=null){
		Calendar cal = Calendar.getInstance();
		cal.setTime(vo.getAuditDateEndEdu());
		cal.add(Calendar.DAY_OF_MONTH, 1);  
		cal.add(Calendar.MILLISECOND, -1);  
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	vo.setAuditDateEndEdu(formatDate.parse(format.format(cal.getTime())));
		}
		vo.setProductType(EnumConstants.ProductType.SE_LOAN.getValue());
		vo.setProductId(EnumConstants.ProductList.STUDENT_LOAN.getValue());
		Pager pager = firstService.findFirstApproveWithPG(vo);
	 	List<FirstApprove> approveList = pager.getResultList(); 
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("total", pager.getTotalCount());
		result.put("rows", approveList);
		return result;
	}
 
	 
	@RequestMapping(value = "/updateAcceptAudit/{status}",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String infoAppSave(@PathVariable("status") String status ,HttpServletRequest request, ModelMap modelMap) {		 
			JSONObject jb=new JSONObject();
			UserSession user=	ApplicationContext.getUser();
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("acceptAudit", status);
			map.put("id", user.getId());
			firstService.updateAcceptAudit(map);
			String acceptAudit= firstService.getAcceptAudit(user.getId());
			jb.put("status", acceptAudit);
			return jb.toString();
		} 
	
	/**
	 * 变更初审人员并更新状态
	 * @param loanVO
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/changeToFirst")
	@ResponseBody
	public Map changeToFirst(LoanVO loanVO ,HttpServletRequest request) {	
		String msg="";
		boolean isSuccess=false;
		try{
			loanVO.setStatus(EnumConstants.LoanStatus.初审中.getValue());
			loanVO.setRecordEmpty("empty");
			Loan loan=loanService.get(loanVO.getId());
		     // 已经生成过合同
	        if (StringUtils.isNotEmpty(loan.getContractNo())) {
	            // 删除还款计划
	            repaymentPlanService.deleteRepaymentPlanByLoanId(loan.getId());
	            // 删除合同信息
	            contractService.deleteContractByLoanId(loan.getId());
	            // 删除银行账户信息和客户银行关联
	            PersonBankVO personBankVO = new PersonBankVO();
	            personBankVO.setPersonId(loan.getPersonId());
	            personBankVO.setLoanId(loan.getId());
	            List<PersonBank> personBankList = personBankService.findPersonBankList(personBankVO);
	            if (CollectionUtil.isNotEmpty(personBankList)) {
	                for (PersonBank personBank : personBankList) {
	                    Long bankAccountId = personBank.getBankAccountId();
	                    bankAccountService.deleteById(bankAccountId);
	                    personBankService.deletePersonBank(personBank.getId());
	                }
	            }
	        }
	        if(loan.getStatus().equals(EnumConstants.LoanStatus.初审拒绝.getValue())
	        	||loan.getStatus().equals(EnumConstants.LoanStatus.终审拒绝.getValue())	
	        	||loan.getStatus().equals(EnumConstants.LoanStatus.信审拒绝.getValue())	
	        	||loan.getStatus().equals(EnumConstants.LoanStatus.拒绝.getValue())	
	        		){
	        	Person person = personService.get(loan.getPersonId());
	        	BlacklistVO blacklistVO = new BlacklistVO();
	        	blacklistVO.setIdnum(person.getIdnum());
	        	blacklistVO.setLoanType(loan.getLoanType());
	        	blacklistService.updateLimitDays(blacklistVO);
	        }
	    	int i=loanService.update(loanVO);
			//插入系统日志
			SysLog sysLog = new SysLog();
			sysLog.setOptModule(EnumConstants.OptionModule.FINAL_TRIAL.getValue());
			sysLog.setOptType(EnumConstants.OptionType.CHANGE_TO_FIRST.getValue());
			sysLog.setRemark("借款ID   "+loanVO.getId().toString());
			sysLogService.insert(sysLog);
			if(i > 0 ){
					isSuccess=true;
					msg="变更成功";
					BusinessLog businessLog = new BusinessLog();
					businessLog.setFlowStatus(EnumConstants.BusinessLogStatus.CHANGE_TO_FIRST.getValue());
					businessLog.setLoanId(loanVO.getId());
					businessLog.setMessage("系统取消交易变更到初审状态,借款ID:"+loanVO.getId());
					businessLogService.insert(businessLog);
				}else{
					isSuccess=false;
					msg="无可变更项";
				}
			}catch(Exception e){
				isSuccess=false;
				msg="变更失败";
		}
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("success", isSuccess);
		result.put("msg", msg);
		return result;
	} 
	
	
	@RequestMapping(value = "/approvePhoneCheck")
	@ResponseBody
	public Map approvePhoneCheck(BusinessLogVO businessLogVO ,HttpServletRequest request) {	
		String msg="";
		boolean isSuccess=false;
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		try{
			List<BusinessLog> businessLogList=businessLogService.getLogByVO(businessLogVO);
			if(businessLogList.size()!=0){
				isSuccess=true;
				msg="成功";
				result.put("businessLog", businessLogList.get(0));
			}else{
				isSuccess=false;
				msg="无可变更项";
			}
			}catch(Exception e){
				isSuccess=false;
				msg="无可变更项";
		}
		
		result.put("success", isSuccess);
		result.put("msg", msg);
		return result;
	} 
	
	
	@RequestMapping("/approvePhoneDetailsScan/{loanId}&{inType}")
	public String approvePhoneDetailsScan(@PathVariable("loanId") String loanId,@PathVariable("inType") String inType,Model model) {
		model.addAttribute("loanId", loanId);
		model.addAttribute("inType",inType);
		Loan loan = loanService.get(Long.parseLong(loanId));
		if (loan != null) {
			Long personId = loan.getPersonId();
			Long productId = Long.valueOf(loan.getProductType());
			LoanVO loanVO=new LoanVO();
			loanVO.setPersonId(personId);
			loanVO.setProductId(loan.getProductId());
			loanVO.setProductType(loan.getProductType());
			loanVO.setStatus(EnumConstants.LoanStatus.结清.getValue());
			List<Loan> loanList=loanService.findListByVO(loanVO);
			if(loanList.size() != 0){
				model.addAttribute("settleType", EnumConstants.SettleTypeStatus.YES.getValue());
			}else{
				model.addAttribute("settleType", EnumConstants.SettleTypeStatus.NO.getValue());
			}
			model.addAttribute("personId", personId);
			model.addAttribute("productId", productId);
			if(loan.getSchemeID() != null){
				ChannelPlanCheck channelPlanCheck = channelPlanCheckService.getReplyById(loan.getSchemeID());
				if(channelPlanCheck!=null){
				model.addAttribute("channelPlan",channelPlanCheck);
					if(channelPlanCheck.getActualRate()!=null){
						model.addAttribute("actualRate",channelPlanCheck.getActualRate());
					}else{
						model.addAttribute("actualRate",new BigDecimal(0));
					}
				}else{
					channelPlanCheck = new ChannelPlanCheck();
					channelPlanCheck.setRequestMoney(new BigDecimal(0));
					channelPlanCheck.setTime(0);
					channelPlanCheck.setActualRate(new BigDecimal(0));
					model.addAttribute("channelPlan",channelPlanCheck);
					model.addAttribute("actualRate",new BigDecimal(0));
				}
			}else{
				ChannelPlanCheck channelPlanCheck = new ChannelPlanCheck();
				channelPlanCheck.setRequestMoney(new BigDecimal(0));
				channelPlanCheck.setTime(0);
				channelPlanCheck.setActualRate(new BigDecimal(0));
				model.addAttribute("channelPlan",channelPlanCheck);
				model.addAttribute("actualRate",new BigDecimal(0));
			}
			model.addAttribute("loan", loan);
			if(loan.getRemark() != null){
				model.addAttribute("remark",loan.getRemark());
			}else{
				model.addAttribute("remark","");
			}
			SysUser sysUserCrm =sysUserService.get(loan.getCrmId());
			if(sysUserCrm != null){
				model.addAttribute("crmName", sysUserCrm.getName());
			}
			BaseArea baseAreaSalesDept=salesDeptService.loadOneBaseAreaById(loan.getSalesDeptId());
			if(baseAreaSalesDept != null){
				model.addAttribute("salesDeptName",baseAreaSalesDept.getName());
			}
			Person person = personService.get(personId);
			if (person != null) {
				model.addAttribute("personName", person.getName());
				model.addAttribute("personDetails", person);
				model.addAttribute("personIdum",person.getIdnum());
			}
		}
		model.addAttribute("optModule", EnumConstants.OptionModule.APPLY_LOAN.getValue());
		SysParameter sysParameter = sysParameterService.getByCode(SysParameterEnum.VALID_ATTACHMENT.name());
		String fileSizeLimit = "2";
		String fileQueueLimit = "40";
		if (sysParameter == null || StringUtils.isEmpty(sysParameter.getParameterValue())) {
			model.addAttribute("fileSizeLimit", fileSizeLimit);
			model.addAttribute("fileQueueLimit", fileQueueLimit);
		} else {
			String[] attachmentArray = sysParameter.getParameterValue().split(";");
			if (attachmentArray != null) {
				if (!StringUtils.isEmpty(attachmentArray[0])) {
					fileSizeLimit = attachmentArray[0];
				}
				if (!StringUtils.isEmpty(attachmentArray[1])) {
					fileQueueLimit = attachmentArray[1];
				}
			}
			model.addAttribute("fileSizeLimit", fileSizeLimit);
			model.addAttribute("fileQueueLimit", fileQueueLimit);
		}
		return "audit/approvePhone/approvePhoneDetailsScan";
	}
	
}
	

