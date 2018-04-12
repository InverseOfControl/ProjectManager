package com.ezendai.credit2.audit.controller;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.ezendai.credit2.apply.model.Company;
import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.model.Person;
import com.ezendai.credit2.apply.service.ChannelPlanCheckService;
import com.ezendai.credit2.apply.service.LoanService;
import com.ezendai.credit2.apply.service.PersonCompanyService;
import com.ezendai.credit2.apply.service.PersonService;
import com.ezendai.credit2.audit.model.AuditTable;
import com.ezendai.credit2.audit.model.AuditTableSeqlist;
import com.ezendai.credit2.audit.service.EduCreditAuditDetailsService;
import com.ezendai.credit2.audit.service.EduCreditAuditTableSeqService;
import com.ezendai.credit2.audit.service.EduCreditAuditTableService;
import com.ezendai.credit2.audit.vo.AuditTableSeqlistVO;
import com.ezendai.credit2.audit.vo.AuditTableVO;
import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.framework.model.UserSession;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.master.model.BaseArea;
import com.ezendai.credit2.master.service.SalesDepartmentService;
import com.ezendai.credit2.system.controller.BaseController;
import com.ezendai.credit2.system.model.SysLog;
import com.ezendai.credit2.system.model.SysUser;
import com.ezendai.credit2.system.service.SysLogService;
import com.ezendai.credit2.system.service.SysParameterService;
import com.ezendai.credit2.system.service.SysUserService;

@Controller
@RequestMapping("/audit/eduLoanList")
public class EduCreditAuditTableController extends BaseController {
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
	private EduCreditAuditTableService eduCreditAuditTableService;
	@Autowired
	private EduCreditAuditTableSeqService eduCreditAuditTableSeqService;
	@Autowired
	private PersonCompanyService personCompanyService;
	
	@Autowired
	private SysLogService sysLogService;

	@RequestMapping("/eduCreditAuditList/{loanId}")
	public String eduCreditAuditList(@PathVariable("loanId") String loanId,
			Model model) {
		model.addAttribute("loanId", loanId);
		Loan loan = loanService.get(Long.parseLong(loanId));
		if (loan != null) {
			Long personId = loan.getPersonId();
			Long productId = Long.valueOf(loan.getProductType());
			model.addAttribute("personId", personId);
			model.addAttribute("productId", productId);
			model.addAttribute("loan", loan);
			SysUser sysUserCrm = sysUserService.get(loan.getCrmId());
			if (sysUserCrm != null) {
				model.addAttribute("crmName", sysUserCrm.getName());
			}
			BaseArea baseAreaSalesDept = salesDeptService
					.loadOneBaseAreaById(loan.getSalesDeptId());
			if (baseAreaSalesDept != null) {
				model.addAttribute("salesDeptName", baseAreaSalesDept.getName());
			}
			if (ApplicationContext.getUser() != null
					&& ApplicationContext.getUser().getId() != null) {
				UserSession user = ApplicationContext.getUser();
				model.addAttribute("auditName", user.getName());
			}
			Person person = personService.get(personId);
			if (person != null) {
				model.addAttribute("personName", person.getName());
				String IDCardNum = person.getIdnum();
				if(IDCardNum != null){
				int year = 0, month = 0, day = 0, idLength = person.getIdnum()
						.length();
				if (idLength == 18) {
					year = Integer.parseInt(IDCardNum.substring(6, 10));
					month = Integer.parseInt(IDCardNum.substring(10, 12));
					day = Integer.parseInt(IDCardNum.substring(12, 14));
				} else if (idLength == 15) {
					year = Integer.parseInt(IDCardNum.substring(6, 8)) + 1900;
					month = Integer.parseInt(IDCardNum.substring(8, 10));
					day = Integer.parseInt(IDCardNum.substring(10, 12));
				}
				Calendar cal1 = Calendar.getInstance();
				Calendar today = Calendar.getInstance();
				cal1.set(year, month, day);
				int m = (today.get(today.MONTH)) - (cal1.get(cal1.MONTH));
				int y = (today.get(today.YEAR)) - (cal1.get(cal1.YEAR));
				int tureAge = (y * 12 + m) / 12;
				model.addAttribute("age", tureAge);
				}else{
					model.addAttribute("age", 0);
				}
				model.addAttribute("personDetails", person);
				if(person.getPlaceDomicile()!=null){
					model.addAttribute("placeDomicile", person.getPlaceDomicile());
				}else{
					model.addAttribute("placeDomicile", "空");
				}
				if(person.getLiveType()!=null){
					model.addAttribute("liveType", person.getLiveType());
				}else{
					model.addAttribute("liveType", "空");
				}
			}
		}
		return "audit/eduLoan/eduCreditAuditTable";
	}

	@RequestMapping("/eduCreditAuditListLApprove/{loanId}")
	public String eduCreditAuditListLApprove(@PathVariable("loanId") String loanId,
			Model model) {
		model.addAttribute("loanId", loanId);
		Loan loan = loanService.get(Long.parseLong(loanId));
		if (loan != null) {
			Long personId = loan.getPersonId();
			Long productId = Long.valueOf(loan.getProductType());
			model.addAttribute("personId", personId);
			model.addAttribute("productId", productId);
			model.addAttribute("loan", loan);
			SysUser sysUserCrm = sysUserService.get(loan.getCrmId());
			if (sysUserCrm != null) {
				model.addAttribute("crmName", sysUserCrm.getName());
			}
			BaseArea baseAreaSalesDept = salesDeptService
					.loadOneBaseAreaById(loan.getSalesDeptId());
			if (baseAreaSalesDept != null) {
				model.addAttribute("salesDeptName", baseAreaSalesDept.getName());
			}
			if (ApplicationContext.getUser() != null
					&& ApplicationContext.getUser().getId() != null) {
				UserSession user = ApplicationContext.getUser();
				model.addAttribute("auditName", user.getName());
			}
			Person person = personService.get(personId);
			if (person != null) {
				model.addAttribute("personName", person.getName());
				String IDCardNum = person.getIdnum();
				if(IDCardNum != null){
				int year = 0, month = 0, day = 0, idLength = person.getIdnum()
						.length();
				if (idLength == 18) {
					year = Integer.parseInt(IDCardNum.substring(6, 10));
					month = Integer.parseInt(IDCardNum.substring(10, 12));
					day = Integer.parseInt(IDCardNum.substring(12, 14));
				} else if (idLength == 15) {
					year = Integer.parseInt(IDCardNum.substring(6, 8)) + 1900;
					month = Integer.parseInt(IDCardNum.substring(8, 10));
					day = Integer.parseInt(IDCardNum.substring(10, 12));
				}
				Calendar cal1 = Calendar.getInstance();
				Calendar today = Calendar.getInstance();
				cal1.set(year, month, day);
				int m = (today.get(today.MONTH)) - (cal1.get(cal1.MONTH));
				int y = (today.get(today.YEAR)) - (cal1.get(cal1.YEAR));
				int tureAge = (y * 12 + m) / 12;
				model.addAttribute("age", tureAge);
				}else{
					model.addAttribute("age", 0);
				}
				model.addAttribute("personDetails", person);
				if(person.getPlaceDomicile()!=null){
					model.addAttribute("placeDomicile", person.getPlaceDomicile());
				}else{
					model.addAttribute("placeDomicile", "空");
				}
				if(person.getLiveType()!=null){
					model.addAttribute("liveType", person.getLiveType());
				}else{
					model.addAttribute("liveType", "空");
				}
			}
		}
		return "audit/eduLoan/eduCreditAuditTableLastApprove";
	}
	
	@RequestMapping("/eduCreditAuditListScan/{loanId}&{inType}")
	public String eduCreditAuditListScan(@PathVariable("loanId") String loanId,@PathVariable("inType") String inType,
			Model model) {
		model.addAttribute("loanId", loanId);
		model.addAttribute("inType", inType);
		Loan loan = loanService.get(Long.parseLong(loanId));
		if (loan != null) {
			Long personId = loan.getPersonId();
			Long productId = Long.valueOf(loan.getProductType());
			model.addAttribute("personId", personId);
			model.addAttribute("productId", productId);
			model.addAttribute("loan", loan);
			SysUser sysUserCrm = sysUserService.get(loan.getCrmId());
			if (sysUserCrm != null) {
				model.addAttribute("crmName", sysUserCrm.getName());
			}
			BaseArea baseAreaSalesDept = salesDeptService
					.loadOneBaseAreaById(loan.getSalesDeptId());
			if (baseAreaSalesDept != null) {
				model.addAttribute("salesDeptName", baseAreaSalesDept.getName());
			}
			if (ApplicationContext.getUser() != null
					&& ApplicationContext.getUser().getId() != null) {
				UserSession user = ApplicationContext.getUser();
				model.addAttribute("auditName", user.getName());
			}
			Person person = personService.get(personId);
			if (person != null) {
				model.addAttribute("personName", person.getName());
				String IDCardNum = person.getIdnum();
				if(IDCardNum != null){
				int year = 0, month = 0, day = 0, idLength = person.getIdnum()
						.length();
				if (idLength == 18) {
					year = Integer.parseInt(IDCardNum.substring(6, 10));
					month = Integer.parseInt(IDCardNum.substring(10, 12));
					day = Integer.parseInt(IDCardNum.substring(12, 14));
				} else if (idLength == 15) {
					year = Integer.parseInt(IDCardNum.substring(6, 8)) + 1900;
					month = Integer.parseInt(IDCardNum.substring(8, 10));
					day = Integer.parseInt(IDCardNum.substring(10, 12));
				}
				Calendar cal1 = Calendar.getInstance();
				Calendar today = Calendar.getInstance();
				cal1.set(year, month, day);
				int m = (today.get(today.MONTH)) - (cal1.get(cal1.MONTH));
				int y = (today.get(today.YEAR)) - (cal1.get(cal1.YEAR));
				int tureAge = (y * 12 + m) / 12;
				model.addAttribute("age", tureAge);
				}else{
					model.addAttribute("age", 0);
				}
				model.addAttribute("personDetails", person);
				if(person.getPlaceDomicile()!=null){
					model.addAttribute("placeDomicile", person.getPlaceDomicile());
				}else{
					model.addAttribute("placeDomicile", "空");
				}
				if(person.getLiveType()!=null){
					model.addAttribute("liveType", person.getLiveType());
				}else{
					model.addAttribute("liveType", "空");
				}
			}
		}
		return "audit/eduLoan/eduCreditAuditTableScan";
	}
	
	
	/**
	 * 
	 * 保存审核意见表
	 * @author liuhy
	 * @return
	 */
	@RequestMapping("/saveAuditTable")
	@ResponseBody
	@SuppressWarnings(value = { "rawtypes", "unchecked" })
	public Map saveAuditTable(AuditTable auditTable) {
		Map map = new HashMap();
		boolean isSuccess = true;
		String msg = "";
		try {
			if (auditTable != null) {
				eduCreditAuditTableService.insertOrUpdateAuditTable(auditTable);
				msg = "操作成功";
			} else {
				isSuccess = false;
				msg = "操作失败";
			}
		} catch (Exception ex) {
			isSuccess = false;
			msg = "操作失败";
			ex.printStackTrace();
		}
		
		// 插入系统日志
		SysLog sysLog = new SysLog();
		if(auditTable != null){
		Loan loan = loanService.get(auditTable.getLoanId());
		if(loan.getStatus().equals(EnumConstants.LoanStatus.终审中.getValue())
				||loan.getStatus().equals(EnumConstants.LoanStatus.初审重提.getValue())
				){
			sysLog.setOptModule(EnumConstants.OptionModule.FINAL_TRIAL
					.getValue());
			sysLog.setOptType(EnumConstants.OptionType.FINAL_TRIAL_AUDIT_SUGGEST_EDIT.getValue());
			sysLog.setRemark("终审意见表编辑 借款ID:"+loan.getId());
		}else{
			sysLog.setOptModule(EnumConstants.OptionModule.FIRST_TRIAL
					.getValue());
			sysLog.setOptType(EnumConstants.OptionType.FIRST_TRIAL_AUDIT_SUGGEST_EDIT.getValue());
			sysLog.setRemark("初审意见表编辑 借款ID:"+loan.getId());
			}
		}
		sysLogService.insert(sysLog);
		
		map.put("isSuccess", isSuccess);
		map.put("msg", msg);
		return map;
	}
	
	/**
	 * 
	 * @author liuhy
	 * @return
	 */
	@RequestMapping("/editAuditTable")
	@ResponseBody
	@SuppressWarnings(value = { "rawtypes", "unchecked" })
	public Map editAuditTable(AuditTableVO auditTableVO) {
		Map map = new HashMap();
		String msg = "";
		boolean isSuccess = true;
		try {
			if (auditTableVO != null) {
				eduCreditAuditTableService.updateAuditTable(auditTableVO);
				msg = "操作成功";
			} else {
				isSuccess = false;
				msg = "操作失败";
			}
		} catch (Exception ex) {
			isSuccess = false;
			msg = "操作失败";
			ex.printStackTrace();
		}
		// 插入系统日志
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.AUDIT_LOAN
				.getValue());
		if(auditTableVO!=null){
				Loan loan = loanService.get(auditTableVO.getLoanId());
				if(loan.getStatus().equals(EnumConstants.LoanStatus.终审中.getValue())
						||loan.getStatus().equals(EnumConstants.LoanStatus.门店重提.getValue())
						||loan.getStatus().equals(EnumConstants.LoanStatus.初审重提.getValue())
						){
					sysLog.setOptModule(EnumConstants.OptionModule.FINAL_TRIAL
							.getValue());
					sysLog.setOptType(EnumConstants.OptionType.FINAL_TRIAL_AUDIT_SUGGEST_EDIT.getValue());
					sysLog.setRemark("终审意见表编辑 借款ID:"+loan.getId());
				}else{
					sysLog.setOptModule(EnumConstants.OptionModule.FIRST_TRIAL
							.getValue());
					sysLog.setOptType(EnumConstants.OptionType.FIRST_TRIAL_AUDIT_SUGGEST_EDIT.getValue());
					sysLog.setRemark("初审意见表编辑 借款ID:"+loan.getId());
				}
		}
		sysLogService.insert(sysLog);
		map.put("isSuccess", isSuccess);
		map.put("msg", msg);
		return map;
	}
	

	/**
	 * 加载审核意见表
	 * 
	 * @author liuhy
	 * @return
	 */
	@RequestMapping("/loadAuditTableInfo")
	@ResponseBody
	@SuppressWarnings(value = { "rawtypes", "unchecked" })
	public Map loadAuditTableInfo(long id) {
		Map map = new HashMap();
		String msg = "";
		boolean isSuccess = true;
		try {
			AuditTable auditTable=eduCreditAuditTableService.getByLoanId(id);
			if(auditTable == null){
				auditTable=new AuditTable();
			}
			Loan loan = loanService.get(id);
			Person person=personService.get(loan.getPersonId());
			if(person != null){
				Company personCompany=personCompanyService.getCompanyById(person.getCompanyId());
				if(personCompany != null){
					if(personCompany.getMajorBusiness() != null){
						auditTable.setMajorBusiness(personCompany.getMajorBusiness());
					}
					if(personCompany.getFoundedDate() != null){
						auditTable.setCompCreateDate(personCompany.getFoundedDate());
					}
					if(personCompany.getName() != null && !personCompany.getName().equals("")){
						auditTable.setCompanyName(personCompany.getName());
					}
					if(person.getRatioOfInvestments() != null){
						auditTable.setRatioOfInvestments(new BigDecimal(person.getRatioOfInvestments()));
					}
					if(person.getMonthRepayAmount() != null){
						auditTable.setAuditMonthAmount(new BigDecimal(person.getMonthRepayAmount()));
					}
				}
			}
			if (auditTable != null) {
				map.put("auditTable", auditTable);
			} 
		} catch (Exception ex) {
			isSuccess = false;
			msg = "初始化失败";
			ex.printStackTrace();
		}
		map.put("isSuccess", isSuccess);
		map.put("msg", msg);
		return map;
	}
	
	/**
	 * 
	 * @author liuhy
	 * @return
	 */
	@RequestMapping("/saveAuditTableSeq")
	@ResponseBody
	@SuppressWarnings(value = { "rawtypes", "unchecked" })
	public Map saveAuditTableSeq(AuditTableSeqlistVO auditTableSeqlistVO) {
		Map map = new HashMap();
		boolean isSuccess = true;
		String msg = "";
		try {
			if (auditTableSeqlistVO != null) {
				List<AuditTableSeqlistVO> data = JSON.parseArray(auditTableSeqlistVO.getAuditTableList(),AuditTableSeqlistVO.class);
				eduCreditAuditTableSeqService.insertOrUpdateAuditTableSeq(data);
			} else {
				isSuccess = false;
				msg = "操作失败";
			}
		} catch (Exception ex) {
			isSuccess = false;
			msg = "操作失败";
			ex.printStackTrace();
		}
		map.put("isSuccess", isSuccess);
		map.put("msg", msg);
		return map;
	}
	
	
	/**
	 * 
	 * @author liuhy
	 * @return
	 */
	@RequestMapping("/delAuditTableSeq")
	@ResponseBody
	@SuppressWarnings(value = { "rawtypes", "unchecked" })
	public Map delAuditTableSeq(AuditTableSeqlistVO auditTableSeqlistVO) {
		Map map = new HashMap();
		boolean isSuccess = true;
		String msg = "";
		try {
			if (auditTableSeqlistVO != null) {
				List<AuditTableSeqlistVO> data = JSON.parseArray(auditTableSeqlistVO.getAuditTableList(),AuditTableSeqlistVO.class);
				if(data.size()!=0){
						eduCreditAuditTableSeqService.delAuditTableSeq(data);
				}
			} else {
				isSuccess = false;
				msg = "操作失败";
			}
		} catch (Exception ex) {
			isSuccess = false;
			msg = "操作失败";
			ex.printStackTrace();
		}
		map.put("isSuccess", isSuccess);
		map.put("msg", msg);
		return map;
	}
	
	/**
	 * 加载审核意见表list
	 * 
	 * @author liuhy
	 * @return
	 */
	@RequestMapping("/loadAuditTableListInfo")
	@ResponseBody
	@SuppressWarnings(value = { "rawtypes", "unchecked" })
	public Map loadAuditTableListInfo(long loanId) {
		Map map = new HashMap();
		String msg = "";
		boolean isSuccess = true;
		try {
			List<AuditTableSeqlist> auditTableList=eduCreditAuditTableSeqService.findByLoanId(loanId);
			if(auditTableList!=null){
			map.put("auditTableList", auditTableList);
			}
		} catch (Exception ex) {
			isSuccess = false;
			msg = "初始化失败";
			ex.printStackTrace();
		}
		map.put("isSuccess", isSuccess);
		map.put("msg", msg);
		return map;
	}
	
}
