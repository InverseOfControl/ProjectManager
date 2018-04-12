package com.ezendai.credit2.audit.controller; 
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezendai.credit2.audit.model.PhoneVerification;
import com.ezendai.credit2.audit.service.PhoneVerificationService;
import com.ezendai.credit2.audit.vo.PhoneVerificationVO;
import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.framework.model.UserSession;
import com.ezendai.credit2.framework.util.StringUtil;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.system.controller.BaseController;
import com.ezendai.credit2.system.model.SysLog;
import com.ezendai.credit2.system.service.SysLogService;
@Controller
@RequestMapping("/phoneVerification/phoneVerificationMain")
public class PhoneVerificationController extends BaseController {
 
	 
	@Autowired
	private PhoneVerificationService phoneService;
	

	@Autowired
	private SysLogService sysLogService;
	
	private static final Logger logger = Logger.getLogger(PhoneVerificationController.class);
	
	private static String betype="0";
	private static String staticLoanID=null;
	 
	@RequestMapping("/init/{loanId}/{type}")
	public String init(@PathVariable("loanId") String loanId,@PathVariable("type") String type,HttpServletRequest request, ModelMap modelMap) {
		/*设置数据字典*/
		setDataDictionaryArr(new String[] { EnumConstants.LOAN_STATUS, EnumConstants.PRODUCT_TYPE, EnumConstants.CONTRACT_SRC });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		if(type.equals("1")){
			betype="1";
		}
		else if(type.equals("2")){
			betype="2";
		}
		staticLoanID=loanId;
		long id=	Long.parseLong(loanId);
		List<PhoneVerification> pvl=phoneService.getColleagueInfo(id);
		PhoneVerification pv=	phoneService.getPersonInfo(id);
		PhoneVerification pv2=null;	 
		PhoneVerification pv3=null;
		PhoneVerification pv4=null;	 
		PhoneVerification pv5=null;	 
		for(int i=0;i<pvl.size();i++){
			if(i==0){
				pv2=pvl.get(i);
			}else if(i==1){
				pv3=pvl.get(i);
			}else if(i==2){
				pv4=pvl.get(i);
			}else if(i==3){
				pv5=pvl.get(i);
			}
		}
		modelMap.put("pv2", pv2);
		modelMap.put("pv3", pv3);
		modelMap.put("pv4", pv4);
		modelMap.put("pv5", pv5);
		/*
		if(pv3!=null){
			pv.setColleagueName(pv3.getColleagueName());
			pv.setColleaguePhone(pv3.getColleaguePhone());
		}
		if(pv2!=null){
			pv.setParentName(pv2.getParentName());
			pv.setParentPhone(pv2.getParentPhone());
		}*/
		PhoneVerificationVO  pvo=new PhoneVerificationVO();
		pvo.setLoanId(id);
		pvo.setTelType(1);;
		List<PhoneVerification>	 type1= phoneService.getTelInquiryByType(pvo);
		pvo.setTelType(2);
		List<PhoneVerification>	 type2= phoneService.getTelInquiryByType(pvo);
		pvo.setTelType(3);
		List<PhoneVerification>	 type3= phoneService.getTelInquiryByType(pvo);
		pvo.setTelType(4);
		List<PhoneVerification>	 type4= phoneService.getTelInquiryByType(pvo);
		pvo.setTelType(5);
		List<PhoneVerification>	 type5= phoneService.getTelInquiryByType(pvo);
		pvo.setTelType(6);
		List<PhoneVerification>	 type6= phoneService.getTelInquiryByType(pvo);
		modelMap.put("pv", pv);
		modelMap.put("type1", type1);
		modelMap.put("type2", type2);
		modelMap.put("type3", type3);
		modelMap.put("type4", type4);
		modelMap.put("type5", type5);
		modelMap.put("type6", type6);
	 	return "audit/phoneVerification/phoneVerification"; 
	
	}
	
	@RequestMapping("/initScan/{loanId}/{type}")
	public String initScan(@PathVariable("loanId") String loanId,@PathVariable("type") String type,HttpServletRequest request, ModelMap modelMap) {
		/*设置数据字典*/
		setDataDictionaryArr(new String[] { EnumConstants.LOAN_STATUS, EnumConstants.PRODUCT_TYPE, EnumConstants.CONTRACT_SRC });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		if(type.equals("1")){
			betype="1";
		}
		else if(type.equals("2")){
			betype="2";
		}
		staticLoanID=loanId;
		long id=	Long.parseLong(loanId);
		List<PhoneVerification> pvl=phoneService.getColleagueInfo(id);
		PhoneVerification pv=	phoneService.getPersonInfo(id);
		PhoneVerification pv2=null;	 
		PhoneVerification pv3=null;
		PhoneVerification pv4=null;	 
		PhoneVerification pv5=null;	 
		for(int i=0;i<pvl.size();i++){
			if(i==0){
				pv2=pvl.get(i);
			}else if(i==1){
				pv3=pvl.get(i);
			}else if(i==2){
				pv4=pvl.get(i);
			}else if(i==3){
				pv5=pvl.get(i);
			}
		}
		modelMap.put("pv2", pv2);
		modelMap.put("pv3", pv3);
		modelMap.put("pv4", pv4);
		modelMap.put("pv5", pv5);
		/*
		if(pv3!=null){
			pv.setColleagueName(pv3.getColleagueName());
			pv.setColleaguePhone(pv3.getColleaguePhone());
		}
		if(pv2!=null){
			pv.setParentName(pv2.getParentName());
			pv.setParentPhone(pv2.getParentPhone());
		}*/
		PhoneVerificationVO  pvo=new PhoneVerificationVO();
		pvo.setLoanId(id);
		pvo.setTelType(1);;
		List<PhoneVerification>	 type1= phoneService.getTelInquiryByType(pvo);
		pvo.setTelType(2);
		List<PhoneVerification>	 type2= phoneService.getTelInquiryByType(pvo);
		pvo.setTelType(3);
		List<PhoneVerification>	 type3= phoneService.getTelInquiryByType(pvo);
		pvo.setTelType(4);
		List<PhoneVerification>	 type4= phoneService.getTelInquiryByType(pvo);
		pvo.setTelType(5);
		List<PhoneVerification>	 type5= phoneService.getTelInquiryByType(pvo);
		pvo.setTelType(6);
		List<PhoneVerification>	 type6= phoneService.getTelInquiryByType(pvo);
		modelMap.put("pv", pv);
		modelMap.put("type1", type1);
		modelMap.put("type2", type2);
		modelMap.put("type3", type3);
		modelMap.put("type4", type4);
		modelMap.put("type5", type5);
		modelMap.put("type6", type6);
	 	return "audit/phoneVerification/phoneVerificationScan"; 
	
	}
	
	
	@RequestMapping(value = "/telSave")
	@ResponseBody
	public String telSave(HttpServletRequest request, ModelMap modelMap){
		String name = request.getParameter("name");
		String company = request.getParameter("company");
		String loanId = request.getParameter("loanId");
		String telType = request.getParameter("telType");
		String tel = request.getParameter("tel");
		String relation = request.getParameter("relation");
		String content = request.getParameter("content");
		String inquiryTime = request.getParameter("inquiryTime");
		PhoneVerification pv=new PhoneVerification();
		pv.setName(name);
		UserSession user = ApplicationContext.getUser();
		pv.setOperatorId(user.getId());
		 
		pv.setCompany(company);
		long id=	Long.parseLong(loanId);
		pv.setLoanId(id);
		pv.setTelType(Integer.parseInt(telType));
		pv.setRelation(relation);
		pv.setContent(content);
		pv.setTel(tel);
		pv.setInquiryTimeStr(inquiryTime);
		pv.setCreatorId(user.getId());
		pv.setCreator(user.getName());
		SysLog sysLog = new SysLog();
		if(betype.equals("1")){
			sysLog.setOptModule(EnumConstants.OptionModule.FIRST_TRIAL
					.getValue());
			sysLog.setOptType(EnumConstants.OptionType.FIRST_TRIAL_PHONE_CHECK_EDIT.getValue());
			}else if(betype.equals("2")){
				sysLog.setOptModule(EnumConstants.OptionModule.FINAL_TRIAL
						.getValue());
				sysLog.setOptType(EnumConstants.OptionType.FINAL_TRIAL_PHONE_CHECK_EDIT.getValue());
			}
		sysLog.setRemark("借款ID   " +staticLoanID);
		sysLogService.insert(sysLog);
		long pid=phoneService.insertTelInquiry(pv);
		JSONObject jb=new JSONObject();
		jb.put("id", pid);
		return jb.toString();	
	}
	
	@RequestMapping(value = "/telUpdate")
	@ResponseBody
	public String telUpdate(HttpServletRequest request, ModelMap modelMap){
		String relation = request.getParameter("relation");
		String content = request.getParameter("content");
		String id = request.getParameter("id");
		PhoneVerification pv=new PhoneVerification();
		UserSession user = ApplicationContext.getUser();
		pv.setOperatorId(user.getId());
		pv.setModifier(user.getName());
		pv.setModifierId(user.getId());
		if(!StringUtil.isEmpty(content)){
			pv.setContent(content);
		}
		if(!StringUtil.isEmpty(relation)){
			pv.setRelation(relation);
		}
		long tid=	Long.parseLong(id);
		SysLog sysLog = new SysLog();
		if(betype.equals("1")){
		sysLog.setOptModule(EnumConstants.OptionModule.FIRST_TRIAL
				.getValue());
		sysLog.setOptType(EnumConstants.OptionType.FIRST_TRIAL_PHONE_CHECK_EDIT.getValue());
		}else if(betype.equals("2")){
			sysLog.setOptModule(EnumConstants.OptionModule.FINAL_TRIAL
					.getValue());
			sysLog.setOptType(EnumConstants.OptionType.FINAL_TRIAL_PHONE_CHECK_EDIT.getValue());
		}
		sysLog.setRemark("借款ID：   " + staticLoanID);
		sysLogService.insert(sysLog);
		pv.setId(tid);
		phoneService.updateTelInquiry(pv);
		JSONObject jb=new JSONObject();
		return jb.toString();	
	}
	@RequestMapping(value = "/telDel")
	@ResponseBody
	public String telDel(HttpServletRequest request, ModelMap modelMap){
		String id = request.getParameter("id");
		long tid=	Long.parseLong(id);
		PhoneVerification pv=new PhoneVerification();
		pv.setId(tid);
		UserSession user = ApplicationContext.getUser();
		pv.setOperatorId(user.getId());
		pv.setModifier(user.getName());
		pv.setModifierId(user.getId());
		SysLog sysLog = new SysLog();
		if(betype.equals("1")){
			sysLog.setOptModule(EnumConstants.OptionModule.FIRST_TRIAL
					.getValue());
			sysLog.setOptType(EnumConstants.OptionType.FIRST_TRIAL_PHONE_CHECK_EDIT.getValue());
			}else if(betype.equals("2")){
				sysLog.setOptModule(EnumConstants.OptionModule.FINAL_TRIAL
						.getValue());
				sysLog.setOptType(EnumConstants.OptionType.FINAL_TRIAL_PHONE_CHECK_EDIT.getValue());
			}
		sysLog.setRemark("电核记录删除, 电核ID：   " + tid+"借款ID：   " + staticLoanID);
		sysLogService.insert(sysLog);
		phoneService.deleteTelInquiry(pv);
		JSONObject jb=new JSONObject();
		return jb.toString();	
	}
	@RequestMapping(value = "/updatePerson")
	@ResponseBody
	public String updatePerson(HttpServletRequest request, ModelMap modelMap){
		String type  = request.getParameter("type");
		String id  = request.getParameter("id");
		long pid=	Long.parseLong(id);
		Integer pType =Integer.parseInt(type);
		PhoneVerification pv=new PhoneVerification();
		UserSession user = ApplicationContext.getUser();
		pv.setOperatorId(user.getId());
		if(pType==1){
			    pv.setHomeAddress(request.getParameter("info"));
			    pv.setPersonId(pid); 
			    phoneService.updateHomeAddres(pv);
			
		}else if(pType==2){
			
		    	pv.setScName(request.getParameter("info"));
		    	 pv.setCompanyId(pid);
			   phoneService.updateCompanyInfo(pv);
		}else if(pType==3){
				pv.setScAddress(request.getParameter("info"));
				 pv.setCompanyId(pid);
			    phoneService.updateCompanyInfo(pv);
		}
		 
		JSONObject jb=new JSONObject();
		return jb.toString();	
	}
	
}
	
