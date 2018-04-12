package com.ezendai.credit2.audit.controller; 
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
 
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezendai.credit2.apply.model.BusinessLog;
import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.model.Person;
import com.ezendai.credit2.apply.service.BankAccountService;
import com.ezendai.credit2.apply.service.BusinessLogService;
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
import com.ezendai.credit2.master.service.BlacklistService;
import com.ezendai.credit2.master.vo.BlacklistVO;
import com.ezendai.credit2.system.controller.BaseController;
import com.ezendai.credit2.system.model.SysLog;
import com.ezendai.credit2.system.service.SysLogService;
@Controller
@RequestMapping("/finalApprove/finalApproveData")
public class FinalApproveController extends BaseController {
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
	
	private static final Logger logger = Logger.getLogger(FinalApproveController.class);
	 
	 
	@RequestMapping("/init")
	public String init(HttpServletRequest request, ModelMap modelMap) {
		/*设置数据字典*/
		setDataDictionaryArr(new String[] { EnumConstants.LOAN_STATUS, EnumConstants.PRODUCT_TYPE, EnumConstants.CONTRACT_SRC,EnumConstants.REPAYMENT_PLAN_STATE });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		UserSession user=	ApplicationContext.getUser();
		String acceptAudit= firstService.getAcceptAudit(user.getId());
		modelMap.put("acceptAudit", acceptAudit);
		request.setAttribute("userLoginName", user.getLoginName());
	 	return "audit/finalApprove/finalApproveMain"; 
	/*	return "/finance/financialAudit/financialAuditList";*/
	}
	@RequestMapping("/toApproveTab")
	public String toApproveTab(HttpServletRequest request) {
		/*设置数据字典*/
		setDataDictionaryArr(new String[] { EnumConstants.LOAN_STATUS, EnumConstants.PRODUCT_TYPE, EnumConstants.CONTRACT_SRC,EnumConstants.REPAYMENT_PLAN_STATE  });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
	 	return "audit/finalApprove/approveList"; 
	 
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
			statusList.add(EnumConstants.LoanStatus.终审中.getValue());
			statusList.add(EnumConstants.LoanStatus.初审重提.getValue());
			vo.setStatusList(statusList);
		}else{
			if(vo.getStatus() ==0){
				statusList.add(EnumConstants.LoanStatus.终审中.getValue());
				statusList.add(EnumConstants.LoanStatus.初审重提.getValue());
				
				/**审批中*/
				statusList.add(EnumConstants.LoanStatus.审批中.getValue());
				/**终审中*/
				statusList.add(EnumConstants.LoanStatus.终审中.getValue());
				/**终审拒绝*/
				statusList.add(EnumConstants.LoanStatus.终审拒绝.getValue());
				/**初审拒绝*/
				statusList.add(EnumConstants.LoanStatus.初审拒绝.getValue());
				/**初审退回*/
				statusList.add(EnumConstants.LoanStatus.初审退回.getValue());
				/**终审退回门店*/
				statusList.add(EnumConstants.LoanStatus.终审退回门店.getValue());
				/**终审退回初审*/
				statusList.add(EnumConstants.LoanStatus.终审退回初审.getValue());
				/**拒绝*/
				statusList.add(EnumConstants.LoanStatus.拒绝.getValue());
				/**展期拒绝*/
				statusList.add(EnumConstants.LoanStatus.展期拒绝.getValue());
				/**初审重提*/
				statusList.add(EnumConstants.LoanStatus.初审重提.getValue());
				/** 信审退回 */
				statusList.add(EnumConstants.LoanStatus.信审退回.getValue());
				/**有条件同意*/
				statusList.add(EnumConstants.LoanStatus.有条件同意.getValue());
				/**审贷会决议 退回门店*/
				statusList.add(EnumConstants.LoanStatus.退回门店.getValue());
				/**展期退回门店*/
				statusList.add(EnumConstants.LoanStatus.展期退回门店.getValue());
				/**信审拒绝*/
				statusList.add(EnumConstants.LoanStatus.信审拒绝.getValue());
				/**合同签订*/
				statusList.add(EnumConstants.LoanStatus.合同签订.getValue());
				/**展期合同签订*/
				statusList.add(EnumConstants.LoanStatus.展期合同签订.getValue());
				/**合同确认*/
				statusList.add(EnumConstants.LoanStatus.合同确认.getValue());
				/**展期合同确认*/
				statusList.add(EnumConstants.LoanStatus.展期合同确认.getValue());
				/**合同确认退回*/
				statusList.add(EnumConstants.LoanStatus.合同确认退回.getValue());
				/**展期合同确认退回*/
				statusList.add(EnumConstants.LoanStatus.展期合同确认退回.getValue());
				/**财务审核*/
				statusList.add(EnumConstants.LoanStatus.财务审核.getValue());
				/**财务审核退回*/
				statusList.add(EnumConstants.LoanStatus.财务审核退回.getValue());
				/**财务放款*/
				statusList.add(EnumConstants.LoanStatus.财务放款.getValue());
				/**财务放款退回*/
				statusList.add(EnumConstants.LoanStatus.财务放款退回.getValue());
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
				/**取消*/
				statusList.add(EnumConstants.LoanStatus.取消.getValue());
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
	
	
	@RequestMapping(value = "/finalApproveCheck")
	@ResponseBody
	public Map finalApproveCheck(BusinessLogVO businessLogVO ,HttpServletRequest request) {	
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
	
}
	
