package com.ezendai.credit2.audit.controller;

import java.math.BigDecimal;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezendai.credit2.apply.model.BusinessLog;
import com.ezendai.credit2.apply.model.ChannelPlanCheck;
import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.model.Person;
import com.ezendai.credit2.apply.service.BusinessLogService;
import com.ezendai.credit2.apply.service.ChannelPlanCheckService;
import com.ezendai.credit2.apply.service.LoanService;
import com.ezendai.credit2.apply.service.PersonService;
import com.ezendai.credit2.apply.vo.LoanVO;
import com.ezendai.credit2.audit.service.EduCreditAuditDetailsService;
import com.ezendai.credit2.audit.vo.ApproveResultVO;
import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.master.enumerate.EnumConstants.LoanStatus;
import com.ezendai.credit2.master.model.BaseArea;
import com.ezendai.credit2.master.service.SalesDepartmentService;
import com.ezendai.credit2.system.controller.BaseController;
import com.ezendai.credit2.system.enumerate.SysParameterEnum;
import com.ezendai.credit2.system.model.SysLog;
import com.ezendai.credit2.system.model.SysParameter;
import com.ezendai.credit2.system.model.SysUser;
import com.ezendai.credit2.system.service.SysLogService;
import com.ezendai.credit2.system.service.SysParameterService;
import com.ezendai.credit2.system.service.SysUserService;

@Controller
@RequestMapping("/audit/eduLoan")
public class EduCreditAuditDetailsController extends BaseController {
	@Autowired
	private LoanService loanService;
	@Autowired
	private PersonService personService;
	@Autowired
	private SysParameterService sysParameterService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SalesDepartmentService salesDeptService;
	@Autowired
	private ChannelPlanCheckService channelPlanCheckService;
	@Autowired
	private EduCreditAuditDetailsService eduCreditAuditDetailsService;
	@Autowired
	private BusinessLogService businessLogService;
	
	@Autowired
	private SysLogService sysLogService;
	
	@RequestMapping("/eduCreditAuditDetails/{loanId}")
	public String eduCreditAuditDetails(@PathVariable("loanId") String loanId, Model model) {
		model.addAttribute("loanId", loanId);
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
		return "audit/eduLoan/eduCreditAuditDetails";
	}
	
	@RequestMapping("/eduCreditAuditDetailsLApprove/{loanId}")
	public String eduCreditAuditDetailsLApprove(@PathVariable("loanId") String loanId, Model model) {
		model.addAttribute("loanId", loanId);
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
		return "audit/eduLoan/eduCreditAuditDetailsLastApprove";
	}
	
	
	@RequestMapping("/eduCreditAuditDetailsScan/{loanId}&{inType}")
	public String eduCreditAuditDetailsScan(@PathVariable("loanId") String loanId,@PathVariable("inType") String inType,Model model) {
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
		return "audit/eduLoan/eduCreditAuditDetailsScan";
	}
	
	@RequestMapping("/hangUp")
	@ResponseBody
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map eduCreditAuditHangUp(LoanVO loanVO) {
		Map map=new HashMap();
		String msg = "";
		boolean isSuccess = true;
		try {
			if (loanVO != null) {
				String rem=loanVO.getRemark();
				loanVO.setStatus(LoanStatus.初审挂起.getValue());
				loanVO.setRemark(rem);
				loanService.update(loanVO);
				msg = "编辑成功";
				BusinessLog businessLog = new BusinessLog();
				businessLog
						.setFlowStatus(EnumConstants.BusinessLogStatus.TRIAL_HANGUP
								.getValue());
				businessLog.setLoanId(loanVO.getId());
				businessLog.setMessage("挂起原因:"+ rem);
				businessLogService.insert(businessLog);
				
				// 插入系统日志
				SysLog sysLog = new SysLog();
				sysLog.setOptModule(EnumConstants.OptionModule.FIRST_TRIAL
						.getValue());
				sysLog.setOptType(EnumConstants.OptionType.FIRST_TRIAL_HANGUP.getValue());
				sysLog.setRemark("初审挂起 借款ID:"+loanVO.getId());
				sysLogService.insert(sysLog);
			} else {
				isSuccess = false;
				msg = "编辑失败";
			}
		} catch (Exception ex) {
			isSuccess = false;
			msg = "编辑失败";
			ex.printStackTrace();
		}
		map.put("isSuccess", isSuccess);
		map.put("msg", msg);
		return map;
	}

	@RequestMapping("/isHangUp")
	@ResponseBody
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map isEduCreditAuditHangUp(LoanVO loanVO) {
		Map map=new HashMap();
	 
		try {
			if (loanVO != null) {
				Loan lvo=	loanService.get(loanVO.getId());
				if(lvo.getStatus().equals(LoanStatus.初审挂起.getValue())){
					map.put("isFlag", true);
					return map;
				}else{
					
					map.put("isFlag", false);
					return map;
				}
				 
			} 
		} catch (Exception ex) {
			 
			ex.printStackTrace();
		}
	 
		return map;
		
	}
	
	/**
	 * 签批
	 * @throws UnknownHostException 
	 * 
	 */
	@RequestMapping("/approve")
	@ResponseBody
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map approve(ApproveResultVO approveVO) {
		Long userId = null;
		Map map=new HashMap();
		String msg = "";
		boolean isSuccess = true;
		if (ApplicationContext.getUser() != null && ApplicationContext.getUser().getId() != null){
			userId = ApplicationContext.getUser().getId();
		}
		try {
			if (approveVO != null) {
				eduCreditAuditDetailsService.approve(approveVO, userId);
				msg = "审核成功";
			}else {
				isSuccess = false;
				msg = "审核成功";
			}
		}catch (Exception ex) {
			isSuccess = false;
			msg = "审核失败";
			ex.printStackTrace();
		}
		map.put("isSuccess", isSuccess);
		map.put("msg", msg);
		return map;
	}
	
	/**
	 * 客户信息
	 *    
	 * 
	 */
	@RequestMapping("/toEditCustomerInfo")
	
	public String toEditCustomerInfo() {
		
		return "audit/eduLoan/eduCustomerModify";
		
	}
	
	
	/**
	 * 客户信息
	 *    
	 * 
	 */
	@RequestMapping("/toEditCustomerInfoScan")
	
	public String toEditCustomerInfoScan() {
		
		return "audit/eduLoan/eduCustomerModifyScan";
		
	}
}
