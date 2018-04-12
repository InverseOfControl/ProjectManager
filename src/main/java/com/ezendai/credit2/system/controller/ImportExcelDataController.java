package com.ezendai.credit2.system.controller; 
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ezendai.credit2.apply.model.ChannelPlanCheck;
import com.ezendai.credit2.apply.model.Company;
import com.ezendai.credit2.apply.model.Contacter;
import com.ezendai.credit2.apply.model.CreditHistory;
import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.model.Person;
import com.ezendai.credit2.audit.model.Contract;
import com.ezendai.credit2.audit.model.PersonBank;
import com.ezendai.credit2.audit.model.RepaymentPlan;
import com.ezendai.credit2.audit.service.FirstApproveService;
import com.ezendai.credit2.finance.model.Ledger;
import com.ezendai.credit2.finance.model.RepayInfo;
import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.framework.model.UserSession;
import com.ezendai.credit2.framework.util.StringUtil;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.system.controller.BaseController;
import com.ezendai.credit2.system.model.Organ;
import com.ezendai.credit2.system.model.OrganBankAccount;
import com.ezendai.credit2.system.model.OrganSalesDepartment;
import com.ezendai.credit2.system.model.OrganSalesManager;
import com.ezendai.credit2.system.service.ExcelAnalyticalService;

@Controller
@RequestMapping("/importExcel/importExcelMain")
public class ImportExcelDataController extends BaseController {
	@Autowired
	private ExcelAnalyticalService excelService;
	@Autowired
	private FirstApproveService firstService;
	private static final Logger logger = Logger.getLogger(ImportExcelDataController.class);
	@RequestMapping("/importData")
	@ResponseBody
	public String init(HttpServletRequest request,HttpServletResponse response,@RequestParam(value = "filename", required = false) MultipartFile file,HttpServletRequest request1, ModelMap modelMap) {
		/*设置数据字典*/
		String name=file.getOriginalFilename();
		Map<String, Object> map= excelService.Analytical(name, file);
		List<Organ>  organList=(List<Organ>) map.get("orgList");
		List<OrganSalesDepartment>  organSalesDepartmentList=(List<OrganSalesDepartment>) map.get("orgSalesList");
		List<OrganBankAccount>  organBankAccountList=(List<OrganBankAccount>) map.get("orgBankList");
		List<OrganSalesManager>  organSalesManagerList=(List<OrganSalesManager>) map.get("orgManagerList");
		List<ChannelPlanCheck>  channelPlanCheckList=(List<ChannelPlanCheck>) map.get("channelList");
		ModelMap model = new ModelMap();
		ModelAndView mv = new ModelAndView();
		String fileName="download-generation.sql";
        response.reset();// 
        response.setHeader("Content-disposition", "attachment; filename="+fileName);// 设定输出文件头   
        response.setContentType("text/x-plain");// 定义输出类型 
        try {
            ServletOutputStream out = response.getOutputStream();
            String path = System.getProperty("java.io.tmpdir") + "\\poem.txt";
            File files = new File(path);
            FileOutputStream fos = new FileOutputStream(files);   
            Writer writer = new OutputStreamWriter(fos, "GBK");   
            String text=null;
        	UserSession user=	ApplicationContext.getUser();
            //生成插入机构表sql  creator_id,creator,created_time,version
        	writer.write("--delete organ sql \n");   
        	writer.write("truncate table organ ;\n");   
          	writer.write("--insert organ sql \n");   
            for(Organ organ  :organList){
            	if(organ.getCode().equals("057115001"))continue;
            	String str=	dateToStr(organ.getSignedDate());
            	String tel=null;
            	if(!StringUtil.isEmpty(organ.getLegalTel())){
            		tel= "'" +organ.getLegalTel()+"'";  
            	}
            	text = "insert into organ(id,code,address,tel,name,post_code,legal_representative,legal_representative_id,legal_tel,signed_date ,margin,status,creator_id,creator,created_time,version)"+
            			"values(SEQ_ORGAN_ID.nextval,'" +organ.getCode()+"','"+organ.getAddress()+"','"+organ.getTel()+"','"+
            				organ.getName()+"','"+organ.getPostCode()+"','"+organ.getLegalRepresentative()+"','"+organ.getLegalRepresentativeId()+"',"+
            				tel+",to_date("+str+",'yyyymmdd'),"+organ.getMargin()+",1,"+user.getId()+",'"+user.getLoginName()+"',sysdate,1)";
            	writer.write(text+";\n");    
            };
            //生成插入机构门店表sql
        	writer.write("--delete organ_sales_department sql \n");   
        	writer.write("truncate table organ_sales_department ;\n");   
          	writer.write("--insert organ_sales_department sql \n");   
            for(OrganSalesDepartment osd  :organSalesDepartmentList){
            	if(osd.getOrganCode().equals("057115001"))continue;
            	text = "insert into ORGAN_SALES_DEPARTMENT(id,organ_id,sales_dept_id,version)values(SEQ_ORGAN_SALES_DEPARTMENT_ID.nextval,(select id from organ where code ='"+
            			osd.getOrganCode()+"'),(select id from base_area where name='"+osd.getAreaName()+"'),1)";
            		writer.write(text+";\n");   
            }
            //生成插入银行账户表  机构银行表sql
        	writer.write("--delete bank_account  organ_bank  sql \n");   
        	writer.write("delete from bank_account where card_type is not null ;\n");   
        	writer.write("truncate table organ_bank ;\n");   
          	writer.write("--insert bank_account sql,bank_account sql \n");   
            for(OrganBankAccount   oba  :organBankAccountList){
            	if(oba.getOrganCode().equals("057115001"))continue;
            	text ="insert into bank_account(id,bank_id,account,account_name,branch_name,bank_name,card_type,status,creator_id,creator,created_time,version)values(seq_bank_account_id.nextval,(select id from bank where bank_name='"+oba.getBankName()+"'),'"+
            			oba.getAccount()+"','"+oba.getAccountName()+"','"+oba.getBranchName()+"','"+oba.getBankName()+"',"+oba.getCardType()+",1,"+user.getId()+",'"+user.getLoginName()+"',sysdate,1)";
            	writer.write(text+";\n");   
            	text = "insert into organ_bank(id,organ_id,bank_account_id,version)values(SEQ_ORGAN_BANK_ID.nextval,(select id from organ where code = '"+
            			oba.getOrganCode()+"'),(select id from bank_account where account='"+oba.getAccount()+"'),1)";
            		writer.write(text+";\n");   
            }
            //生成插入机构客户经理关系表sql
          /*	writer.write("--insert organ_sales_manager sql \n");   
            for(OrganSalesManager osm  :organSalesManagerList){
            	
            	int count= firstService.selectSysUserCount(osm.getCode());
            	String condition="";
            	if(count>1){
            	 	 condition="and is_deleted=0  ";
            	}else {
            		 condition="and 1=1  ";
            	}
            	text ="insert into organ_sales_manager (id,organ_id,code,sales_manager,user_id)values(SEQ_ORGAN_SALES_MANAGER_ID.nextval,(select id from organ where code ='"+
            			osm.getOrgCode()+"'),'"+osm.getCode()+"','"+osm.getSalesManager()+"',(select id from sys_user where  code='"+osm.getCode()+"' "+condition+"))";
            	writer.write(text+";\n");   
            }*/
            //生成插入方案表 方案批复表sql   、、OPERATOR_ID APPROVER_ID
         
        	writer.write("--delete bank_account  organ_bank  sql \n");   
        	writer.write("truncate table  CHANNEL_PLAN ;\n");
        	writer.write("truncate table CHANNEL_PLAN_CHECK ;\n");
         	writer.write("--insert CHANNEL_PLAN_CHECK sql,CHANNEL_PLAN sql \n"); 
            for(ChannelPlanCheck   cpc  :channelPlanCheckList){
            	if(cpc.getOrgCode().equals("057115001"))continue;
            	String strEnd=	dateToStr(cpc.getEndDate());
            	String strStart= dateToStr(cpc.getStartDate());
            	String memo=null;
            	if(!StringUtil.isEmpty(cpc.getSendBackMemo())){
            		memo= "'" +cpc.getSendBackMemo()+"'";  
            	}
            	text ="insert into CHANNEL_PLAN (id,code,name,END_DATE,MARGIN,ORGANIZATION_ID,ORG_REPAY_TERM,"+
            		   "PACT_MONEY,PLAN_TYPE,RATE_SUM,REQUEST_MONEY,RETURNETERM1,RETURNETERM2,SEND_BACK_MEMO,START_DATE,"+
            		   "TIME,TO_TERM1,TO_TERM2,ACTUAL_RATE,ORG_FEE_STATE,RETURN_TYPE,PLAN_STATE,IS_DELETED,OPERATOR_ID, creator_id,creator,created_time,version)values(SEQ_CHANNEL_PLAN_ID.nextval,'"+
            		   cpc.getCode()+"','"+cpc.getName()+"',to_date("+strEnd+",'yyyymmdd'),"+cpc.getMargin()+",(select id from organ where code ='"+cpc.getOrgCode()+"'),"+cpc.getOrgRepayTerm()+","+
            		   cpc.getPactMoney()+",'"+cpc.getPlanType()+"',"+cpc.getRateSum()+","+cpc.getRequestMoney()+","+cpc.getReturneterm1()+","+cpc.getReturneterm2()+","+
            		   memo+",to_date("+strStart+",'yyyymmdd'),"+cpc.getTime()+","+cpc.getToTerm1()+","+cpc.getToTerm2()+","+cpc.getActualRate()+",'"+
            		   cpc.getOrgFeeState()+"',"+cpc.getReturnType()+",'00',0,"+user.getId()+","+user.getId()+",'"+user.getLoginName()+"',sysdate,1)";
            	writer.write(text+";\n");   
            	text ="insert into CHANNEL_PLAN_CHECK (id,code,name,END_DATE,MARGIN,ORGANIZATION_ID,ORG_REPAY_TERM,"+
             		   "PACT_MONEY,PLAN_TYPE,RATE_SUM,REQUEST_MONEY,RETURNETERM1,RETURNETERM2,SEND_BACK_MEMO,START_DATE,"+
             		   "TIME,TO_TERM1,TO_TERM2,ACTUAL_RATE,ORG_FEE_STATE,RETURN_TYPE,PLAN_ID,APPROVER_STATE,IS_DELETED,OPERATOR_ID, creator_id,creator,created_time)values(SEQ_CHANNEL_PLAN_CHECK_ID.nextval,'"+
             		   cpc.getCode()+"','"+cpc.getName()+"',to_date("+strEnd+",'yyyymmdd'),"+cpc.getMargin()+",(select id from organ where code ='"+cpc.getOrgCode()+"'),"+cpc.getOrgRepayTerm()+","+
             		   cpc.getPactMoney()+",'"+cpc.getPlanType()+"',"+cpc.getRateSum()+","+cpc.getRequestMoney()+","+cpc.getReturneterm1()+","+cpc.getReturneterm2()+","+
             		   memo+",to_date("+strStart+",'yyyymmdd'),"+cpc.getTime()+","+cpc.getToTerm1()+","+cpc.getToTerm2()+","+cpc.getActualRate()+",'"+
             		   cpc.getOrgFeeState()+"',"+cpc.getReturnType()+
             		   	",(select id from CHANNEL_PLAN where code='"+cpc.getCode()+"'),"+cpc.getApproverState()+",0,"+user.getId()+","+user.getId()+",'"+user.getLoginName()+"',sysdate)";
             	writer.write(text+";\n"); 
            }
            writer.close();   
            fos.close();  
            FileInputStream fis = new java.io.FileInputStream(files);
            ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream(4096);
            byte[] cache = new byte[4096];
            for (int offset = fis.read(cache); offset != -1; offset = fis.read(cache)) {
                    byteOutputStream.write(cache, 0, offset);
            }
            byte[] bt = null;
            bt = byteOutputStream.toByteArray();               
            out.write(bt);
            out.flush();
            out.close();
            fis.close();
            if(files.exists()){
                files.delete();
            }            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
	 	return null; 
	 
	}
	
	
	@RequestMapping("/importDataLoan")
	@ResponseBody
	public String initLoan(HttpServletRequest request,HttpServletResponse response,@RequestParam(value = "filename", required = false) MultipartFile file,HttpServletRequest request1, ModelMap modelMap) {
		/*设置数据字典*/
		String name=file.getOriginalFilename();
		Map<String, Object> map= excelService.AnalyticalLoan(name, file);
//		List<Company>  getCompanyList=(List<Company>) map.get("companyList");
		List<Person> getPersonList=(List<Person>) map.get("personList");
		List<Loan> getLoanList=(List<Loan>) map.get("loanList");
		List<Contacter> getContacterList=(List<Contacter>) map.get("contacterList");
		List<CreditHistory> getCreditHistoryList=(List<CreditHistory>) map.get("creditHistoryList");
//		List<Contract> getContractList=(List<Contract>) map.get("contractList");
		List<RepaymentPlan> getRepaymentPlanList=(List<RepaymentPlan>) map.get("repaymentPlanList");
		List<PersonBank> getPersonBankList=(List<PersonBank>) map.get("personBankList");
		List<RepayInfo> getRepayInfoList=(List<RepayInfo>) map.get("repayInfoList");
		List<Ledger>  getLedgerList=(List<Ledger>) map.get("ledgerList");
		ModelMap model = new ModelMap();
		ModelAndView mv = new ModelAndView();
		String fileName="DownloadLoanSql.sql";
        response.reset();// 
        response.setHeader("Content-disposition", "attachment; filename="+fileName);// 设定输出文件头   
        response.setContentType("text/x-plain");// 定义输出类型 
        try {
            ServletOutputStream out = response.getOutputStream();
            String path = System.getProperty("java.io.tmpdir") + "\\poem.txt";
            File files = new File(path);
            FileOutputStream fos = new FileOutputStream(files);   
            Writer writer = new OutputStreamWriter(fos, "GBK");   
            String text=null;
            String empty="";
        	UserSession user=	ApplicationContext.getUser();

            //生成插入企业表sql  creator_id,creator,created_time,version
          	writer.write("--insert company sql \n");   
            for(Person person  :getPersonList){
            	text="merge into COMPANY c using dual on  (c.name='"+person.getCompanyImp().getName()+"' and c.CATEGORY='"+person.getCompanyImp().getCategory()+"' and c.ADDRESS='"+person.getCompanyImp().getAddress()+"' and c.PHONE='"+person.getCompanyImp().getPhone()+"') when not matched then"+
            			" insert (ID, NAME, "
            			+ " CATEGORY, ADDRESS, PHONE, "
            			+ "ZIP_CODE,  "
            			+ "CREATOR_ID, CREATOR, CREATED_TIME,  VERSION"
            			+ " )"
            			+ "values (SEQ_COMPANY_ID.nextval,'"+(person.getCompanyImp().getName()!=null?person.getCompanyImp().getName():empty) +"','"+(person.getCompanyImp().getCategory()!=null?person.getCompanyImp().getCategory():empty)+"','"+(person.getCompanyImp().getAddress()!=null?person.getCompanyImp().getAddress():empty)+"'"
            			+",'"+(person.getCompanyImp().getPhone()!=null?person.getCompanyImp().getPhone():empty)+"','"+(person.getCompanyImp().getZipCode()!=null?person.getCompanyImp().getZipCode():empty)+"'"
            			+","+user.getId()+",'"+user.getLoginName()+"',sysdate,151"
            			+")"
            			;
            			

            	writer.write(text+";\n");    
            };
//            //生成插入客户表sql
          	writer.write("--insert person sql \n");   
            for(Person person  :getPersonList){
            	text="merge into PERSON p using dual on (p.IDNUM='"+person.getIdnum()+"' and p.PRODUCT_TYPE=1)"
            			+ "	when not matched then insert  (ID, NAME, SEX, PERSON_NO, IDNUM, MARRIED, IDENTIFIER, "
            			+ "EDUCATION_LEVEL, HAS_CHILDREN, CHILDREN_NUM,  "
            			+ "ZIP_CODE, WITNESS, OTHER_INCOME, PAY_DATE, WORK_NATURE, JOB, "
            			+ "DEPT_NAME, EXT, PLACE_DOMICILE, ADDRESS, MOBILE_PHONE, EMAIL,"
            			+ " HOME_PHONE, HOUSE_ESTATE_TYPE, RENT_PER_MONTH, LIVE_TYPE, "
            			+ "HAS_HOUSE_LOAN, HOUSE_ESTATE_ADDRESS, INCOME_PER_MONTH, "
            			+ "CREATOR_ID, CREATOR, CREATED_TIME,  "
            			+ "COMPANY_ID, WORK_THAT_DEPT, WORK_THAT_POSITION, WORK_THAT_TELL, "
            			+ "PERSON_TYPE, COMPANY_TYPE, VERSION, PROFESSION_TYPE, MAX_REPAY_AMOUNT, "
            			+ "CHILDREN_SCHOOL, PRIVATE_ENTERPRISE_TYPE, FOUNDED_DATE, BUSINESS_PLACE, "
            			+ "TOTAL_EMPLOYEES, RATIO_OF_INVESTMENTS, MONTH_OF_PROFIT,  "
            			+ "CUR_DEBTBALANCE, MONTH_REPAY_AMOUNT, LIVING_WITH, INCOME_SOURCE,PRODUCT_TYPE)"
            			+"values (SEQ_PERSON_ID.nextval,'"+(person.getName()!=null?person.getName():empty)+"','"+(person.getSex()!=null?person.getSex():empty)+"','"+person.getPersonNo()+""
            			+"','"+person.getIdnum()+"',"+person.getMarried()+",'"+person.getIdentifier()+"','"+person.getEducationLevel()+""
            			+"',"+person.getHasChildren()+","+person.getChildrenNum()+",'"+(person.getZipCode()!=null?person.getZipCode():empty)+""
            			+"','"+(person.getWitness()!=null?person.getWitness():empty)+"',"+person.getOtherIncome()+","+person.getPayDate()+",'"+(person.getWorkNature()!=null?person.getWorkNature():empty)+""
            			+"','"+(person.getJob()!=null?person.getJob():empty)+"','"+person.getDeptName()+"','"+(person.getExt()!=null?person.getExt():empty)+""
            			+"','"+person.getPlaceDomicile()+"','"+person.getAddress()+"','"+(person.getMobilePhone()!=null?person.getMobilePhone():empty)+""
            			+"','"+(person.getEmail()!=null?person.getEmail():empty)+"','"+(person.getHomePhone()!=null?person.getHomePhone():empty)+"','"+(person.getHouseEstateType()!=null?person.getHouseEstateType():empty)+""
            			+"',"+person.getRentPerMonth()+",'"+(person.getLiveType()!=null?person.getLiveType():empty)+"',"+(person.getHasHouseLoan())+""
            			+",'"+(person.getHouseEstateAddress()!=null?person.getHouseEstateAddress():empty)+"',"+(person.getIncomePerMonth())+""
            			+","+user.getId()+",'"+user.getLoginName()+"',sysdate"
            			+",(select a.id from COMPANY a where a.name='"+person.getCompanyImp().getName()+"' and a.CATEGORY='"+person.getCompanyImp().getCategory()+"' and a.ADDRESS='"+person.getCompanyImp().getAddress()+"' and a.PHONE='"+person.getCompanyImp().getPhone()+"' and rownum=1)"
            					+ ",'"+(person.getWorkThatDept()!=null?person.getWorkThatDept():empty)+"','"+(person.getWorkThatPosition()!=null?person.getWorkThatPosition():empty)+""
            			+"','"+(person.getWorkThatTell()!=null?person.getWorkThatTell():empty)+"',"+(person.getPersonType())+","+(person.getCompanyType())+""
            			+",151,'"+(person.getProfessionType()!=null?person.getProfessionType():empty)+"',"+(person.getMaxRepayAmount()!=null?person.getMaxRepayAmount():null)+",'"+(person.getChildrenSchool()!=null?person.getChildrenSchool():empty)+""
            			+"','"+(person.getPrivateEnterpriseType()!=null?person.getPrivateEnterpriseType():empty)+"','"+(person.getFoundedDate()!=null?person.getFoundedDate():empty)+"',"+(person.getBusinessPlace())+""
            			+","+(person.getTotalEmployees())+","+(person.getRatioOfInvestments())+","+(person.getMonthOfProfit())+""
            			+","+(person.getPersonDebtBalance())+","+(person.getMonthRepayAmount())+",'"+(person.getLivingWith()!=null?person.getLivingWith():empty)+""
            			+"','"+(person.getIncomeSource()!=null?person.getIncomeSource():empty)+"',1"
            			+")"
            			;
            	writer.write(text+";\n");   
            	
            }
            //生成插入贷款表
          	writer.write("--insert loan sql \n");   
            for(Loan   loan  :getLoanList){
            		text="insert into LOAN (ID, VERSION, PERSON_ID, PRODUCT_ID, RISK,"
            				+ " PACT_MONEY, TIME, REQUEST_TIME, AUDIT_TIME, SALES_TEAM_ID, RESIDUAL_PACT_MONEY, "
            				+ "MONTH_RATE,  CONTRACT_NO, GRANT_ACCOUNT_ID,"
            				+ " CURR_NUM, GRANT_MONEY, REQUEST_DATE, SIGN_DATE, AUDIT_DATE, AUDIT_MONEY, REQUEST_MONEY,"
            				+ " RETURN_DATE, START_REPAY_DATE, END_REPAY_DATE, GRANT_DATE, CUSTOMER_SOURCE, CRM_ID, SERVICE_ID, ASSESSOR_ID,MANAGER_ID,"
            				+ "  FIRSTTRIAL_ID,SALES_DEPT_ID, PURPOSE, STATUS, "
            				+ "CREATOR,  CREATOR_ID,CREATED_TIME,"
            				+ " REMARK, SUBMIT_DATE, CONTRACT_SRC, "
            				+ "CONTRACT_CONFIRM_DATE, CONTRACT_BACK_DATE, CONTRACT_CREATED_TIME, "
            				+ "FINANCE_AUDIT_TIME,  ASSESSMENT,"
            				+ " CONSULT, B_MANAGE, C_MANAGE, REPAYMENT_METHOD, RESIDUAL_TIME, "
            				+ "  ORGAN_ID, SCHEME_ID,PRODUCT_TYPE,PRODUCT_CHANNEL_ID)"+
            				"values (SEQ_LOAN_ID.nextval,151,(select p.id from person p where p.idnum='"+loan.getPerson().getIdnum()+"'),8,"+loan.getRisk()+""
            				+","+loan.getPactMoney()+","+loan.getAuditTime()+","+loan.getRequestTime()+","+loan.getAuditTime()+""
            				+",(select st.id from BASE_AREA st where st.full_name='"+loan.getSalesTeam().getFullName()+"' and st.identifier='zdsys.SalesTeam' ),"+loan.getResidualPactMoney()+","+loan.getMonthRate()+""
            				+",'"+(loan.getContractNo()!=null?loan.getContractNo():empty)+"',(select ga.id from bank_account ga where ga.account_name='"+loan.getGrantAccount().getAccountName()+"' and ga.card_type ='"+loan.getGrantAccount().getCardType()+"' and rownum=1),"+loan.getCurrNum()+""
            				+","+loan.getGrantMoney()+",to_date("+(loan.getRequestDate()!=null?dateToStr(loan.getRequestDate()):null)+",'yyyymmdd'),to_date("+(loan.getSignDate()!=null?dateToStr(loan.getSignDate()):null)+",'yyyymmdd'),to_date("+(loan.getAuditDate()!=null?dateToStr(loan.getAuditDate()):null)+",'yyyymmdd')"
            				+","+loan.getAuditMoney()+","+loan.getRequestMoney()+","+loan.getReturnDate()+",to_date("+(loan.getStartRepayDate()!=null?dateToStr(loan.getStartRepayDate()):null)+",'yyyymmdd')"
            				+",to_date("+(loan.getEndRepayDate()!=null?dateToStr(loan.getEndRepayDate()):null)+",'yyyymmdd'),to_date("+(loan.getGrantDate()!=null?dateToStr(loan.getGrantDate()):null)+",'yyyymmdd'),'"+(loan.getCustomerSource()!=null?loan.getCustomerSource():empty)+"',\n"
            				+ "(case when (select count(1) from Sys_User suas where suas.code='"+(loan.getCrm()!=null?loan.getCrm().getLoginName():empty)+"'  and suas.is_deleted=0)>0 then (select id from Sys_User suas where suas.code='"+(loan.getCrm()!=null?loan.getCrm().getLoginName():empty)+"' and suas.is_deleted=0) else 11 end)," 
            				+ "(case when (select count(1) from Sys_User suas where suas.code='"+(loan.getService()!=null?loan.getService().getLoginName():empty)+"'  and suas.is_deleted=0)>0 then (select id from Sys_User suas where suas.code='"+(loan.getService()!=null?loan.getService().getLoginName():empty)+"' and suas.is_deleted=0) else 2 end)," 
            				+ "(case when (select count(1) from Sys_User suas where suas.code='"+(loan.getService()!=null?loan.getService().getLoginName():empty)+"'  and suas.is_deleted=0)>0 then (select id from Sys_User suas where suas.code='"+(loan.getService()!=null?loan.getService().getLoginName():empty)+"' and suas.is_deleted=0) else 2 end)," 
            				+ "(case when (select count(1) from Sys_User suas where suas.code='"+(loan.getService()!=null?loan.getService().getLoginName():empty)+"'  and suas.is_deleted=0)>0 then (select id from Sys_User suas where suas.code='"+(loan.getService()!=null?loan.getService().getLoginName():empty)+"' and suas.is_deleted=0) else 2 end)," 
            				+ "(select suas.id from Sys_User suas where suas.code='"+(loan.getAssessor()!=null?loan.getAssessor().getLoginName():empty)+"'  and suas.is_deleted=0),"
            				+ "(select sd.id from BASE_AREA sd where sd.full_name='"+loan.getSalesDept().getFullName()+""
            				+"' and sd.identifier='zdsys.SalesDepartment' ),'"+(loan.getPurpose()!=null?loan.getPurpose():empty)+"',"+loan.getStatus()+""
            				+",'"+user.getLoginName()+"',"+user.getId()+",sysdate"
            				+",'"+(loan.getRemark()!=null?loan.getRemark():empty)+"',to_date("+(loan.getRequestDate()!=null?dateToStr(loan.getRequestDate()):null)+",'yyyymmdd'),"+loan.getContractSrc()+",'"+(loan.getContractConfirmDate()!=null?loan.getContractConfirmDate():empty)+""
            				+"',to_date("+(loan.getContractBackDate()!=null?dateToStr(loan.getContractBackDate()):null)+",'yyyymmdd'),to_date("+(loan.getContractCreatedTime()!=null?dateToStr(loan.getContractCreatedTime()):null)+",'yyyymmdd'),to_date("+(loan.getFinanceAuditTime()!=null?dateToStr(loan.getFinanceAuditTime()):null)+",'yyyymmdd'),"+loan.getAssessment()+""
            				+","+loan.getConsult()+","+loan.getbManage()+","+loan.getcManage()+","+loan.getRepaymentMethod()+","+loan.getResidualTime()+""
//            				+",(select ms.id from Sys_User ms where ms.code='"+loan.getManageService().getLoginName()+"')"
//            				+ ",(select bd.id from Sys_User bd where bd.code='"+loan.getBizDirector().getLoginName()+"')"
            				+ ",(select id from Organ where code='"+(loan.getOrgan()!=null?loan.getOrgan().getCode():empty)+"')"
            				+",(select id from channel_plan_check where name ='"+(loan.getChannelPlanCheck()!=null?loan.getChannelPlanCheck().getName():empty)+"' and rownum=1),1,3"
            				+")"
            				;
            				
            		writer.write(text+";\n");   
            }
            //生成插入联系人表
            writer.write("--insert contacter sql \n");
            for(Contacter   contacter  :getContacterList){
            	text="insert into CONTACTER (ID, NAME, RELATIONSHIP, MOBILE_PHONE, HOME_PHONE, WORK_UNIT, "
            			+ "HAD_KNOWN, BORROWER_ID, ADDRESS, CREATOR_ID, CREATOR, CREATED_TIME, VERSION, LOAN_ID, TITLE)"+
            			"values (SEQ_CONTACTER_ID.nextval,'"+(contacter.getName()!=null?contacter.getName():empty)+"','"+(contacter.getRelationship()!=null?contacter.getRelationship():empty)+""
            			+"','"+(contacter.getMobilePhone()!=null?contacter.getMobilePhone():empty)+"','"+(contacter.getHomePhone()!=null?contacter.getHomePhone():empty)+"','"+(contacter.getWorkUnit()!=null?contacter.getWorkUnit():empty)+"',"+contacter.getHadKnown()+""
            			+",(case when (select count(1) from Loan la where la.contract_no='"+contacter.getLoan().getContractNo()+"') > 0 then (select la.person_id from Loan la where la.contract_no='"+contacter.getLoan().getContractNo()+"') else (select br.id from Person br where br.name='"+contacter.getBorrower().getName()+"' and rownum=1) end)"
            			+ ",'"+(contacter.getAddress()!=null?contacter.getAddress():empty)+""
            			+"',"+user.getId()+",'"+user.getLoginName()+"',sysdate,151"
            			+",(case when (select count(1) from Loan la where la.contract_no='"+contacter.getLoan().getContractNo()+"') > 0 then (select la.id from Loan la where la.contract_no='"+contacter.getLoan().getContractNo()+"') else (select la.id from Loan la where la.status < 60 and la.person_id = (select br.id from Person br where br.name='"+contacter.getBorrower().getName()+"' and rownum=1) and rownum=1) end)"
            			+",'"+(contacter.getTitle()!=null?contacter.getTitle():empty)+"'"
            			+")"
            			;
            	writer.write(text+";\n");   
            }
            //生成插入信贷历史表
            writer.write("--insert creditHistory sql \n");
            for(CreditHistory   creditHistory  :getCreditHistoryList){
            	text="insert into CREDIT_HISTORY (ID, PERSON_ID, HAS_CREDIT_CARD, CARD_NUM, TOTAL_AMOUNT, "
            			+ "OVERDRAW_AMOUNT, VERSION, "
            			+ "CREATOR_ID, CREATOR, CREATED_TIME, "
            			+ " LOAN_ID, CREDIT_CHANNEL, AMOUNT, GRANT_DATE, MONTHLY_PAYMENTS, OVERDUE_INFO)"+
            			"values (SEQ_CREDIT_HISTORY.nextval,(select la.person_id from loan la where la.contract_no='"+creditHistory.getLoan().getContractNo()+""
            			+"'),"+creditHistory.getHasCreditCard()+","+creditHistory.getCardNum()+","+creditHistory.getTotalAmount()+","+creditHistory.getOverdrawAmount()+""
            			+",151,"+user.getId()+",'"+user.getLoginName()+"',sysdate"
            			+",(select loa.id from loan loa where loa.contract_no='"+creditHistory.getLoan().getContractNo()+"'),'"+(creditHistory.getHistoryLoanChannel()!=null?creditHistory.getHistoryLoanChannel():empty)+"',"+creditHistory.getHistoryAmount()+",to_date("+(creditHistory.getHistoryGrantDate()!=null?dateToStr(creditHistory.getHistoryGrantDate()):null)+",'yyyymmdd'),"+creditHistory.getHistoryMonPay()+""
            			+",'"+(creditHistory.getHistoryOverdue()!=null?creditHistory.getHistoryOverdue():empty)+"'"
            			+")"
            			;
            	writer.write(text+";\n");  
            }
            
            //生成插入合同表
//            writer.write("--insert contract sql \n");
//            for(Contract   contract  :getContractList){
//            	text="insert into CONTRACT (ID, LOAN_ID, CONTRACT_NO, SIGN_DATE, TYPE, CONTRACT_NAME, "
//            			+ "CITY_NAME, AREA_NAME, PERSON_NAME, ID_NUM, ADDRESS, ZIP_CODE, EMAIL, PAY_AMOUNT,"
//            			+ " PURPOSE, PACT_MONEY, MONTH_INTEREST_AMOUNT, TIMES, START_REPAY_DATE, END_REPAY_DATE,"
//            			+ " BANK_ACCOUNT_NAME, BANK_ACCOUNT_NUM, OPENING_BANK, ASSESSMENT_FEES, MANAGE_FEES, "
//            			+ "TTP_MANAGE_FEES, CONTACT, MONTH_AMOUNT, GUARANTEE_NAME, LOAN_AGREE_NUM, "
//            			+"CREATOR_ID, CREATOR, CREATED_TIME,"
//            			+ " VERSION, REPAY_DATE)"+
//            			"values (SEQ_CONTRACT_ID.nextval,(select loa.id from loan loa where loa.contract_no='"+contract.getLoan().getContractNo()+""
//            			+"'),'"+(contract.getContractNo()!=null?contract.getContractNo():empty)+"',to_date("+dateToStr(contract.getSignDate())+",'yyyymmdd'),"+contract.getType()+",'"+(contract.getContractName()!=null?contract.getContractName():empty)+""
//            			+"','"+(contract.getCityName()!=null?contract.getCityName():empty)+"','"+(contract.getAreaName()!=null?contract.getAreaName():empty)+"','"+(contract.getPersonName()!=null?contract.getPersonName():empty)+"','"+(contract.getIdNum()!=null?contract.getIdNum():empty)+""
//            			+"','"+(contract.getAddress()!=null?contract.getAddress():empty)+"','"+(contract.getZipCode()!=null?contract.getZipCode():empty)+"','"+(contract.getEmail()!=null?contract.getEmail():empty)+"',"+contract.getPayAmount()+""
//            			+",'"+(contract.getPurpose()!=null?contract.getPurpose():empty)+"',"+contract.getPactMoney()+","+contract.getMonthInterestAmount()+","+contract.getTimes()+""
//            			+",to_date("+dateToStr(contract.getStartRepayDate())+",'yyyymmdd'),to_date("+dateToStr(contract.getEndRepayDate())+",'yyyymmdd')"
//            			+",'"+(contract.getBankAccountName()!=null?contract.getBankAccountName():empty)+"','"+(contract.getBankAccountNum()!=null?contract.getBankAccountNum():empty)+"','"+(contract.getOpeningBank()!=null?contract.getOpeningBank():empty)+"',"+contract.getAssessmentFees()+""
//            			+","+contract.getManageFees()+","+contract.getTtpManageFees()+",'"+(contract.getContact()!=null?contract.getContact():empty)+"',"+contract.getMonthAmount()+""
//            			+",'"+(contract.getGuaranteeName()!=null?contract.getGuaranteeName():empty)+"','"+(contract.getLoanAgreeNum()!=null?contract.getLoanAgreeNum():empty)+"'"
//            			+","+user.getId()+",'"+user.getLoginName()+"',sysdate,151"
//            			+",'"+(contract.getRepayDate()!=null?contract.getRepayDate():empty)+"'"
//            			+")"
//            			;
//            	writer.write(text+";\n");  
//            }
            
            //生成插入还款表
            writer.write("--insert repaymentPlan sql \n");
            for(RepaymentPlan   repaymentPlan  :getRepaymentPlanList){
            	text="insert into REPAYMENT_PLAN (ID, LOAN_ID, STATUS, REPAY_AMOUNT, CUR_NUM,  OUTSTANDING,  REMAINING_PRINCIPAL, PENALTY,  ONE_TIME_REPAYMENT_AMOUNT, FACT_RETURNDATE,"
            			+ " DEFICIT, INTEREST_AMT, REPAY_DAY, VERSION, PENALTY_DATE ,CUR_REMAINING_INTEREST_AMT ,CUR_REMAINING_PRINCIPAL , PRINCIPAL_AMT ,AVERAGE_CAPITAL)"+
            			"values (SEQ_REPAYMENT_PLAN_ID.nextval,(select id from loan  where contract_no='"+repaymentPlan.getLoan().getContractNo()+""
            			+"'),"+repaymentPlan.getStatus()+","+repaymentPlan.getRepayAmount()+","+repaymentPlan.getCurNum()+""
            			+","+repaymentPlan.getOutstanding()+","+repaymentPlan.getRemainingPrincipal()+","+repaymentPlan.getPenalty()+""
            			+","+repaymentPlan.getOneTimeRepaymentAmount()+",to_date("+(repaymentPlan.getFactReturnDate()!=null?dateToStr(repaymentPlan.getFactReturnDate()):null)+",'yyyymmdd'),"+repaymentPlan.getDeficit()+""
            			+","+repaymentPlan.getInterestAmt()+",to_date("+(repaymentPlan.getRepayDay()!=null?dateToStr(repaymentPlan.getRepayDay()):null)+",'yyyymmdd'),151,to_date("+(repaymentPlan.getPenaltyDate()!=null?dateToStr(repaymentPlan.getPenaltyDate()):null)+",'yyyymmdd'),"+repaymentPlan.getCurRemainingInterestAmt()+","+repaymentPlan.getCurRemainingPrincipal()+","+repaymentPlan.getRepayAmount().subtract(repaymentPlan.getInterestAmt())+""
            			+","+repaymentPlan.getRepayAmount()+")"
            			;
            	writer.write(text+";\n");  
            }
            
            
            //生成客户银行账户表
            writer.write("--insert BANK_ACCOUNT sql \n");
            for(PersonBank   personBank  :getPersonBankList){
            	text="insert into BANK_ACCOUNT (ID, BANK_ID, ACCOUNT, STATUS, BRANCH_NAME, BANK_NAME, CREATOR_ID, CREATOR, CREATED_TIME, VERSION)"+
            			"values (SEQ_BANK_ACCOUNT_ID.nextval,"
            			+"(select id from BANK where bank_name='"+personBank.getBankName()+"'),"
            			+"'"+(personBank.getAccount()!=null?personBank.getAccount():empty)+"',1,'"+(personBank.getBranchName()!=null?personBank.getBranchName():empty)+"','"+personBank.getBankName()+"'"
            			+","+user.getId()+",'"+user.getLoginName()+"',sysdate,151"
            			+")"
            			;
            			
            	writer.write(text+";\n");
            }
            
            
            
            //生成客户银行信息表
            writer.write("--insert personBank sql \n");
            for(PersonBank   personBank  :getPersonBankList){
            	text="insert into PERSON_BANK (ID, PERSON_ID, BANK_ACCOUNT_ID, LOAN_ID,VERSION)"+
            			"values (SEQ_PERSON_BANK_ID.nextval,(select id from person where idnum='"+personBank.getPersonIdnum()+"'),"
            			+"(select id from BANK_ACCOUNT where account='"+personBank.getAccount()+"' and bank_name='"+personBank.getBankName()+"' and branch_name='"+personBank.getBranchName()+"' and rownum=1),";
            	text=text+"(select id from loan  where contract_no='"+personBank.getLoan().getContractNo()+"'),151)";
            			
            	writer.write(text+";\n");
            }
            
            //生成总账分账表
            writer.write("--insert ledger sql \n");
            for(Ledger   ledger  :getLedgerList){
            	text="insert into LEDGER (ID, VERSION, ACCOUNT_ID,  CASH, INTEREST_RECEIVABLE, "
            			+ "FINE_RECEIVABLE, OTHER_RECEIVALE, LOAN_AMOUNT, INTEREST_PAYABLE, FINE_PAYABLE, "
            			+ "OTHER_PAYABLE, OVERDUE_INTEREST_INCOME, CONSULT_INCOME, MANAGE_INCOME, ASSESSMENT_FEE_INCOME, "
            			+ "PENALTY_INCOME, OTHER_INCOME, NONBUSINESS_INCOME, INTEREST_EXPENSE, CONSULT_EXPENSE, ASSESSMENT_FEE_EXPENSE,"
            			+ " MANAGE_EXPENSE, PENALTY_EXPENSE, OTHER_EXPENSE, NONBUSINESS_EXPENSE, CREATOR_ID, CREATOR, CREATED_TIME,"
            			+ "REMARK, OVERDUE_INTEREST_EXPENSE, INTEREST_INCOME,TYPE)"
            			+"values (SEQ_LEDGER_ID.nextval,151,(select id from loan  where contract_no='"+ledger.getLoan().getContractNo()+"')"
            			+","+ledger.getCash()+","+ledger.getInterestReceivable()+","+ledger.getFineReceivable()+","+ledger.getOtherReceivable()+""
            			+","+ledger.getLoanAmount()+","+ledger.getInterestPayable()+","+ledger.getFinePayable()+","+ledger.getOtherPayable()+""
            			+","+ledger.getOverdueInterestIncome()+","+ledger.getConsultIncome()+","+ledger.getManageIncome()+","+ledger.getAssessmentFeeIncome()+""
            			+","+ledger.getPenaltyIncome()+","+ledger.getOtherIncome()+","+ledger.getNonbusinessIncome()+","+ledger.getInterestExpense()+""
            			+","+ledger.getConsultExpense()+","+ledger.getAssessmentFeeExpense()+","+ledger.getManageExpense()+","+ledger.getPenaltyExpense()+""
            			+","+ledger.getOtherExpense()+","+ledger.getNonbusinessExpense()+""
            			+","+user.getId()+",'"+user.getLoginName()+"',sysdate"
            			+",'"+(ledger.getRemark()!=null?ledger.getRemark():empty)+"',"+ledger.getOverdueInterestExpense()+","+ledger.getInterestIncome()+",2"
            			+")"
            			;
            	writer.write(text+";\n");
            }
            
            //生成放还款表
            writer.write("--insert repayInfo sql \n");           
            for(RepayInfo   repayInfo  :getRepayInfoList){
            	text="insert into REPAY_INFO (ID, ACCOUNT_ID, TRADE_TIME, TRADE_CODE, TRADE_AMOUNT, "
            			+ " TRADE_NO,  TELLER,  TERM, "
            			+ " SALESDEPARTMENT_ID, VERSION, CREATOR_ID, CREATOR, CREATED_TIME, REMARK,PAY_TYPE)"+
            			"values (SEQ_REPAY_INFO_ID.nextval,(select id from loan  where contract_no='"+repayInfo.getLoan().getContractNo()+"')"
            			+",to_date("+(repayInfo.getTradeTime()!=null?dateToStr(repayInfo.getTradeTime()):null)+",'yyyymmdd'),'"+(repayInfo.getTradeCode()!=null?repayInfo.getTradeCode():empty)+"',"+repayInfo.getTradeAmount()+",'"+(repayInfo.getTradeNo()!=null?repayInfo.getTradeNo():empty)+"'"
            			+",(select sucr.id from Sys_User sucr where sucr.code='"+repayInfo.getTellUser().getLoginName()+"' and sucr.is_deleted=0),"+repayInfo.getTerm()+""
            			+",(select sd.id from BASE_AREA sd where sd.full_name='"+repayInfo.getSalesdepartment().getFullName()+"' and sd.identifier='zdsys.SalesDepartment'),151"
            			+","+user.getId()+",'"+user.getLoginName()+"',sysdate"
            			+",'"+(repayInfo.getRemark()!=null?repayInfo.getRemark():empty)+"',"+repayInfo.getPayType()+""
            			+")"
            			;
            	writer.write(text+";\n");
            }

            
            writer.close();   
            fos.close();  
            FileInputStream fis = new java.io.FileInputStream(files);
            ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream(4096);
            byte[] cache = new byte[4096];
            for (int offset = fis.read(cache); offset != -1; offset = fis.read(cache)) {
                    byteOutputStream.write(cache, 0, offset);
            }
            byte[] bt = null;
            bt = byteOutputStream.toByteArray();               
            out.write(bt);
            out.flush();
            out.close();
            fis.close();
            if(files.exists()){
                files.delete();
            }            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
	 	return null; 
	 
	}
	
	

	@RequestMapping("/deleteDataLoan")
	@ResponseBody
	public String deleteLoan(HttpServletRequest request,HttpServletResponse response,@RequestParam(value = "filename", required = false) MultipartFile file,HttpServletRequest request1, ModelMap modelMap) {
		/*设置数据字典*/
		String fileName="DeleteLoanSql.sql";
        response.reset();// 
        response.setHeader("Content-disposition", "attachment; filename="+fileName);// 设定输出文件头   
        response.setContentType("text/x-plain");// 定义输出类型 
        try {
            ServletOutputStream out = response.getOutputStream();
            String path = System.getProperty("java.io.tmpdir") + "\\poem.txt";
            File files = new File(path);
            FileOutputStream fos = new FileOutputStream(files);   
            Writer writer = new OutputStreamWriter(fos, "GBK");   
			
			
			//清库用sql
		    writer.write("--delete sql \n");
		    writer.write("delete from company where version=151 ;\n");
		    writer.write("delete from person where version=151 ;\n");
		    writer.write("delete from loan where version=151 ;\n");
		    writer.write("delete from contacter where version=151 ;\n");
		    writer.write("delete from credit_History where version=151 ;\n");
		    writer.write("delete from contract where version=151 ;\n");
		    writer.write("delete from repayment_Plan where version=151 ;\n");
		    writer.write("delete from person_Bank where version=151 ;\n");
		    writer.write("delete from ledger where version=151 ;\n");
		    writer.write("delete from repay_Info where version=151 ;\n");
		    writer.write("delete from BANK_ACCOUNT where version=151 ;\n");
		    
		    writer.close();   
		    fos.close();  
		    FileInputStream fis = new java.io.FileInputStream(files);
		    ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream(4096);
		    byte[] cache = new byte[4096];
		    for (int offset = fis.read(cache); offset != -1; offset = fis.read(cache)) {
		            byteOutputStream.write(cache, 0, offset);
		    }
		    byte[] bt = null;
		    bt = byteOutputStream.toByteArray();               
		    out.write(bt);
		    out.flush();
		    out.close();
		    fis.close();
		    if(files.exists()){
		        files.delete();
		    }            
		} catch (Exception e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		
			return null; 
		
		}
	
	@RequestMapping("/toUI")
	public String toApproveTab(HttpServletRequest request) {
		/*设置数据字典*/
		setDataDictionaryArr(new String[] { EnumConstants.LOAN_STATUS, EnumConstants.PRODUCT_TYPE, EnumConstants.CONTRACT_SRC });
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
	 	return "system/importExcel/importExcel"; 
	 
	}
	
	
	public String dateToStr(Date date){
		  SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		   String str = format.format(date);
		   return str;
	}
	 
}
	
