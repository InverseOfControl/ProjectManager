/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.after.service.impl;


import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.after.service.RepayService;
import com.ezendai.credit2.after.service.SpecialRepaymentService;
import com.ezendai.credit2.after.vo.RepayEntryDetailsVO;
import com.ezendai.credit2.apply.model.Bank;
import com.ezendai.credit2.apply.model.BankAccount;
import com.ezendai.credit2.apply.model.Company;
import com.ezendai.credit2.apply.model.Extension;
import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.model.Person;
import com.ezendai.credit2.apply.model.Product;
import com.ezendai.credit2.apply.service.BankAccountService;
import com.ezendai.credit2.apply.service.BankService;
import com.ezendai.credit2.apply.service.ExtensionService;
import com.ezendai.credit2.apply.service.LoanService;
import com.ezendai.credit2.apply.service.PersonCompanyService;
import com.ezendai.credit2.apply.service.PersonService;
import com.ezendai.credit2.after.dao.CollectionCreateCasesDao;
 
import com.ezendai.credit2.after.model.CasesPerson;
import com.ezendai.credit2.after.model.CollectionCasesRecord;
import com.ezendai.credit2.after.model.CollectionCasesTask;
import com.ezendai.credit2.after.model.CollectionCreateCases;
import com.ezendai.credit2.after.model.CollectionExcel;
import com.ezendai.credit2.audit.model.PersonBank;
import com.ezendai.credit2.audit.model.PersonContacterAdditional;
import com.ezendai.credit2.audit.model.RepaymentPlan;
import com.ezendai.credit2.after.service.CollectionCreateCasesService;
 
import com.ezendai.credit2.audit.service.PersonBankService;
import com.ezendai.credit2.after.vo.CollectionCreateCasesVO;
 
import com.ezendai.credit2.after.vo.CollectionTaskVO;
import com.ezendai.credit2.after.vo.OverdueReceivablesCaseVO;
import com.ezendai.credit2.audit.vo.PersonBankVO;
import com.ezendai.credit2.common.util.ExportExcel;
import com.ezendai.credit2.framework.util.DateUtil;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.framework.util.StringUtil;
import com.ezendai.credit2.master.enumerate.EnumConstants;
 

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author  
 * @version   
 */
@Service
public class CollectionCreateCasesServiceImpl implements CollectionCreateCasesService {

	@Autowired
	private CollectionCreateCasesDao collectionDao;
	@Autowired
	private PersonBankService personBankService;
	@Autowired
	private LoanService loanService;
	@Autowired
	private PersonService personService;
	@Autowired
	private RepayService repayService;
	@Autowired
	private SpecialRepaymentService specialRepaymentService;
	@Autowired
	private ExtensionService extensionService;
	@Autowired
	private PersonCompanyService personCompanyService;
	@Autowired
	private BankAccountService bankAccountService;
	@Autowired
	private BankService bankService;
	private BigDecimal overduePrincipal;
	private BigDecimal overdueInterest;
	private BigDecimal penalty;
	private int titleSize;//联系信息 字段长度
	private int titleSize2;//委案前其他还款信息
	@Override
	public Pager findCollectionCreateCasesWithPG(CollectionCreateCasesVO vo) {
		// TODO Auto-generated method stub
		return collectionDao.findCollectionCreateCasesWithPG(vo);
	}

	@Override
	public void insert(OverdueReceivablesCaseVO overdueReceivablesCase) {
		// TODO Auto-generated method stub
		collectionDao.insert(overdueReceivablesCase);
	}

	@Override
	public CollectionCreateCases selectLoanInfoByLoanId(Long id) {
		// TODO Auto-generated method stub
		return  collectionDao.selectLoanInfoByLoanId(id);
	}

	@Override
	public Pager findManagerCasesWithPG(CollectionCreateCasesVO vo) {
		// TODO Auto-generated method stub
		return collectionDao.findManagerCasesWithPG(vo);
	}

	@Override
	public void dispatchCases(CollectionCreateCasesVO vo) {
		// TODO Auto-generated method stub
		  collectionDao.dispatchCases(vo);
	}

	@Override
	public void casesClosed(CollectionCreateCasesVO vo) {
		// TODO Auto-generated method stub
		 collectionDao.casesClosed(vo);
	}

	@Override
	public Pager findCollectionCasesTaskWithPG(CollectionCreateCasesVO vo) {
		// TODO Auto-generated method stub
		return collectionDao.findCollectionCasesTaskWithPG(vo);
	}

	@Override
	public Pager findPersonWithPG(CollectionCreateCasesVO vo) {
		// TODO Auto-generated method stub
		return	collectionDao.findPersonWithPG(vo) ;
	}

	@Override
	public Pager findCasesRecordWithPG(CollectionCreateCasesVO vo) {
		// TODO Auto-generated method stub
		return collectionDao.findCasesRecordWithPG(vo);
	}

	@Override
	public CollectionCasesTask selectTaskById(Long id) {
		// TODO Auto-generated method stub
		return collectionDao.selectTaskById(id);
	}

	@Override
	public void updateCaseInfo(CollectionCreateCasesVO vo) {
		// TODO Auto-generated method stub
		collectionDao.updateCaseInfo(vo);
	}

	@Override
	public CollectionCreateCases selectCaseInfo(Long id) {
		// TODO Auto-generated method stub
		return 	collectionDao.selectCaseInfo(id);
	}

	@Override
	public void insertTask(CollectionTaskVO vo) {
		// TODO Auto-generated method stub
		collectionDao.insertTask(vo);
	}

	@Override
	public void updateTask(CollectionTaskVO vo) {
		// TODO Auto-generated method stub
		collectionDao.updateTask(vo);
	}

	@Override
	public void insertRecord(CollectionCasesRecord cr) {
		// TODO Auto-generated method stub
		collectionDao.insertRecord(cr);
	}

	@Override
	public CollectionCasesRecord selectRecordById(Long id) {
		// TODO Auto-generated method stub
		return	collectionDao.selectRecordById(id);
	}

	@Override
	public void updateTaskById(CollectionTaskVO vo) {
		// TODO Auto-generated method stub
		collectionDao.updateTaskById(vo);
	}

	@Override
	public Pager findManagerTaskWithPG(CollectionCreateCasesVO vo) {
		// TODO Auto-generated method stub
		return collectionDao.findManagerTaskWithPG(vo);
	}

	@Override
	public void updateRecord(CollectionCasesRecord cr) {
		// TODO Auto-generated method stub
		collectionDao.updateRecord(cr);
	}

	@Override
	public void deleteRecord(Long id) {
		// TODO Auto-generated method stub
		collectionDao.deleteRecord(id);
	}

	@Override
	public CasesPerson selectCollectionManager(String loginName){
		// TODO Auto-generated method stub
		return collectionDao.selectCollectionManager(loginName);
	}

	@Override
	public  List<CasesPerson>  seleCtcontactsTel(CasesPerson cp) {
		// TODO Auto-generated method stub
		return collectionDao.seleCtcontactsTel(cp);
	}

	@Override
	public  List<CasesPerson>  selePersonTel(CasesPerson cp) {
		// TODO Auto-generated method stub
		return collectionDao.selePersonTel(cp);
	}

	@Override
	public CollectionCreateCases selectLoanId(Long id) {
		// TODO Auto-generated method stub
		return   collectionDao.selectLoanId(id) ;
	}

	@Override
	public CollectionCasesTask seleTaskById(Long id) {
		// TODO Auto-generated method stub
		return collectionDao.seleTaskById(id);
	}

	@Override
	public CollectionCreateCases selectLoanInfoById(Long id) {
		// TODO Auto-generated method stub
		return collectionDao.selectLoanInfoById(id);
	}

	@Override
	public void assignmentCaseInfo(CollectionTaskVO vo) {
		// TODO Auto-generated method stub
		  collectionDao.assignmentCaseInfo(vo);
	}

	@Override
	public CollectionCasesTask assignmentByLoanId(Long id) {
		// TODO Auto-generated method stub
		return   collectionDao.assignmentByLoanId(id);
	}

	@Override
	public CollectionCreateCases selectLoanInfoByCaseId(Long id) {
		// TODO Auto-generated method stub
		return   collectionDao.selectLoanInfoByCaseId(id);
	}

	@Override
	public Pager findPersonContactAdditionalnMap(PersonContacterAdditional pca) {
		// TODO Auto-generated method stub
		return collectionDao.findPersonContactAdditionalnMap(pca);
	}

	@Override
	public void insertPersonContacterAdditional(PersonContacterAdditional pca) {
		// TODO Auto-generated method stub
		  collectionDao.insertPersonContacterAdditional(pca);
	}

	@Override
	public void deletePersonContacterAdditional(Long id) {
		// TODO Auto-generated method stub
		  collectionDao.deletePersonContacterAdditional(id);
	}

	@Override
	public void casesChange(CollectionCreateCasesVO vo) {
		// TODO Auto-generated method stub
		  collectionDao.casesChange(vo);
	}

	@Override
	public void taskChange(CollectionTaskVO vo) {
		// TODO Auto-generated method stub
		 collectionDao.taskChange(vo);
	}

	@Override
	public Pager findCollectionContacerWithPG(CasesPerson cp) {
		// TODO Auto-generated method stub
		return collectionDao.findCollectionContacerWithPG(cp);
	}

	@Override
	public Long insertCollectionContacer(CasesPerson cp) {
		// TODO Auto-generated method stub
		return   collectionDao.insertCollectionContacer(cp);
	}

	@Override
	public void updateCollectionContacer(CasesPerson cp) {
		// TODO Auto-generated method stub
		  collectionDao.updateCollectionContacer(cp);
	}

	@Override
	public CasesPerson selectCollectionContacerById(Long id) {
		// TODO Auto-generated method stub
		return  collectionDao.selectCollectionContacerById(id);
	}

	@Override
	public void updatePersonContacterAdditional(CasesPerson cp) {
		// TODO Auto-generated method stub
		 collectionDao.updatePersonContacterAdditional(cp);
	}
	@Override
	public CollectionCreateCases selectLastAmountAndDate(Long loanId) {
		// TODO Auto-generated method stub
		return collectionDao.selectLastAmountAndDate(loanId);
	}
	//导出报表
	@Override
	public void excelExport(String[] loanIdArr,OutputStream os) {
		List<CollectionExcel> ceList;
		try {
			titleSize=6;
			titleSize2=0;
			int arrlist1=0;//借款人最大的手机List size
			int arrlist2=0;//借款人最大的联系人List size
     		int arrlist3=0;//借款人最大的还款List size
			ceList = searchExcelData(loanIdArr);
			for(CollectionExcel ce:ceList){
				if(ce.getPhoneList()!=null){
					if(ce.getPhoneList().size()>arrlist1){
						arrlist1=ce.getPhoneList().size();
					}
				}
				if(ce.getContactserList()!=null){
					if(ce.getContactserList().size()>arrlist2){
						arrlist2=ce.getContactserList().size();
					}
				}
				if(ce.getRequestTime()!=null){
					if(Integer.parseInt(ce.getRequestTime())>arrlist3){
						arrlist3=Integer.parseInt(ce.getRequestTime());
					}
				}
			}
			
			titleSize+=arrlist1-1+arrlist2*3;
			titleSize2=arrlist3*2;
			//创建header
			String[] headers = new String[22+titleSize+titleSize2];
			headers[0] = new String("债务总额（元）");
			headers[1] = new String("逾期时间（天）");
			headers[2] = new String("开户行");
			headers[3] = new String("提现卡号");
			headers[4] = new String("抵押类型");
			headers[5] = new String("借款期限");
			headers[6] = new String("合同金额（元）");
			headers[7] = new String("逾期起始日期");
			headers[8] = new String("剩余本金（元）");
			headers[9] = new String("逾期本金（元）");
			headers[10] = new String("逾期利息（元）");
			headers[11] = new String("违约金（元）");
			headers[12] = new String("罚息（元）");
			headers[13] = new String("债务人姓名");
			headers[14] = new String("所在地（城市）");
			headers[15] = new String("身份证号");
			headers[16] = new String("性别");
			headers[17] = new String("婚姻状况");
			headers[18] = new String("年龄");
			for(int i=0;i<arrlist1;i++){
				headers[18+i+1] = new String("债务人手机号"+(i+1));
			}
			//** 			 						
			headers[18+arrlist1+1] = new String("家庭住址");
			headers[18+arrlist1+2] = new String("户籍住址");
			headers[18+arrlist1+3] = new String("工作单位");
			headers[18+arrlist1+4] = new String("单位地址");
			headers[18+arrlist1+5] = new String("单位电话");
			for(int i=0;i<arrlist2;i++){
				 
				headers[18+arrlist1+5+1+i*3] = new String("相关联系人"+(i+1)+"姓名");
				headers[18+arrlist1+5 +2+i*3] = new String("相关联系人"+(i+1)+"关系");
				headers[18+arrlist1+5 +3+i*3] = new String("相关联系人"+(i+1)+"电话");
			}
			headers[18+arrlist1+5+(arrlist2*3)+1] = new String("委案前末次还款时间");
			headers[18+arrlist1+5+(arrlist2*3)+2] = new String("委案前末次还款金额（元）");
			for(int i=0;i<arrlist3;i++){
				headers[18+arrlist1+5+(arrlist2*3)+2+1+i*2] = new String("还款时间"+(i+1));
				headers[18+arrlist1+5+(arrlist2*3)+2+2+i*2] = new String("还款金额"+(i+1));
			}
			//headers[18+arrlist1+5+(arrlist2*3)+2+(arrlist3*2)+1] = new String("备注");
			if (ceList.size()>0) {
				List<String[]> rows = new ArrayList<String[]>();
				for(CollectionExcel ce:ceList){
					String[] row = new String[22+titleSize+titleSize2];
					//*  	   	年龄	*债务人手机号1	债务人手机号2	*家庭住址	户籍住址	工作单位	单位地址	单位电话	相关联系人1姓名	相关联系人1关系	相关联系人1电话	相关联系人2姓名	相关联系人2关系	相关联系人2电话	委案前末次还款时间	委案前末次还款金额（元）	还款时间1	还款金额1（元）	还款时间2	还款金额2（元）								
					row[0] = new String(ce.getRepayAllAmount()==null ?"":ce.getRepayAllAmount());
					row[1] = new String(ce.getOverdueNum() ==null ? "": ce.getOverdueNum());
					row[2] = new String(ce.getBankName() ==null ? "":ce.getBankName());
					row[3] = new String(ce.getCardNum() ==null ? "": ce.getCardNum());
					row[4] = new String("");//抵押类型
					row[5] = new String(ce.getRequestTime()==null ? "":  ce.getRequestTime());
					row[6] = new String(ce.getContractAmount()==null ? "":  ce.getContractAmount());
					row[7] = new String(ce.getOverdueStartDate()==null ? "":  ce.getOverdueStartDate());
					row[8] = new String(ce.getResidualPactMoney()==null ? "":  ce.getResidualPactMoney());
					row[9] = new String(ce.getPrincipal()==null ? "":  ce.getPrincipal());
					row[10] = new String(ce.getInterest()==null ? "":  ce.getInterest());
					row[11] = new String(ce.getFine()==null ? "":  ce.getFine());
					row[12] = new String(ce.getRepayInterest()==null ? "":  ce.getRepayInterest());
					row[13] = new String(ce.getName()==null ? "":  ce.getName());
					row[14] = new String("");//所在地
					row[15] = new String(ce.getIdNum()==null ? "":  ce.getIdNum());
					row[16] = new String(ce.getSex()==null ? "":  ce.getSex());
					row[17] = new String(ce.getMarried()==null ? "":  ce.getMarried());
					row[18] = new String(ce.getAge()==null ? "":  ce.getAge());//年龄
					if(ce.getPhoneList()!=null){
							List<PersonContacterAdditional> list=	ce.getPhoneList();
							 for(int i=0;i<list.size();i++){
								 PersonContacterAdditional pca=list.get(i);
								 row[18+i+1] = new String(pca.getAdditionalTel());
							 };
					}
					row[18+arrlist1+1] = new String(ce.getAddress()==null ? "":  ce.getAddress());
					row[18+arrlist1+2] = new String(ce.getPlaceDomicile()==null ? "":  ce.getPlaceDomicile());
					row[18+arrlist1+3] = new String(ce.getCompany()==null ? "":  ce.getCompany());
					row[18+arrlist1+4] = new String(ce.getCompanyAddress()==null ? "":  ce.getCompanyAddress());
					row[18+arrlist1+5] = new String(ce.getCompanyTel()==null ? "":  ce.getCompanyTel());
					if(ce.getContactserList()!=null){
						List<CasesPerson> list=	ce.getContactserList();
						 for(int i=0;i<list.size();i++){
							 CasesPerson cp=list.get(i);
							 row[18+arrlist1+5+1+i*3] = new String(cp.getName()==null ? "":  cp.getName());
							 row[18+arrlist1+5+2+i*3] = new String(cp.getRelationShip()==null ? "":  cp.getRelationShip());
							 row[18+arrlist1+5+3+i*3] = new String(cp.getMobilePhone()==null ? "":  cp.getMobilePhone());
						 };
						 
				}
					row[18+arrlist1+5+(arrlist2*3)+1] = new String(ce.getLastFactReturnDate()==null ? "":  ce.getLastFactReturnDate());
					row[18+arrlist1+5+(arrlist2*3)+2] = new String(ce.getLastRepayAmount()==null ? "":  ce.getLastRepayAmount());
					if(ce.getRpList()!=null){
						List<RepaymentPlan> list=	ce.getRpList();
						 for(int i=0;i<list.size();i++){
							 RepaymentPlan rp=list.get(i);
							 row[18+arrlist1+5+(arrlist2*3)+2+1+i*2] = new String(dateToStr(rp.getFactReturnDate()));
							 row[18+arrlist1+5+(arrlist2*3)+2+2+i*2] = new String(rp.getRepayAmount().toString());
						 };
						 
				}
					row[18+arrlist1+5+(arrlist2*3)+2+(arrlist3*2)+1] = new String("");
					rows.add(row);
					
				}					
				 //				
				ExportExcel exportExcel = new ExportExcel();
				exportExcel.setRows(rows);
				List<ExportExcel> exportExcelList=new ArrayList<ExportExcel>();
				exportExcelList.add(exportExcel);
				poiWriteExcel_To2007(headers, os, exportExcelList);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	
	}
	//导出报表所需数据
	public List<CollectionExcel> searchExcelData(String[] loanIdArr) throws ParseException{
		List<CollectionExcel> ceList=new ArrayList<CollectionExcel>();
		Pager sendPage=new Pager();
		sendPage.setPage(1);
		sendPage.setRows(1000);
		for(int i=0;i<loanIdArr.length;i++){
		CollectionExcel ce=new CollectionExcel();
		Long loanId =Long.parseLong(	loanIdArr[i]);
		
		//查出借款信息
		Loan loan =loanService.get(loanId);
		RepayEntryDetailsVO repay = viewEdit( loanId);
		if(loan.getStatus()==140){
			ce.setRepayAllAmount(repay.getRepayAmount().toString());
		}else{
			ce.setRepayAllAmount(repay.getRepayAllAmount().toString());
		}
		ce.setOverdueNum(repay.getFineDay()+"");
		//查出借款人信息
		Person person =personService.get(loan.getPersonId());
        //查出公司信息
		Company com=	personCompanyService.getCompanyById(person.getCompanyId());
		//查出借款人银行信息
		PersonBankVO personBankVo=	new PersonBankVO();
		personBankVo.setPersonId(loan.getPersonId());
		PersonBank personBank=	personBankService.getPersonBank(personBankVo);
		BankAccount ba=	bankAccountService.get(personBank.getBankAccountId());
		 
		ce.setBankName(ba.getBankName());
		ce.setCardNum(ba.getAccount());
		ce.setRequestTime(loan.getTime().toString());
		ce.setContractAmount(loan.getPactMoney().toString());
		ce.setOverdueStartDate(repay.getOverdueStartDate());
		ce.setResidualPactMoney(loan.getResidualPactMoney().toString());
	    ce.setPrincipal( overduePrincipal.toString());
	    ce.setInterest( overdueInterest.toString());
	    ce.setFine( penalty.toString());
	    ce.setRepayInterest( repay.getFine().toString());
	    ce.setName(person.getName());
	    ce.setIdNum(person.getIdnum());
	    if(person.getIdnum()!=null){
	    	String age=	person.getIdnum().substring(6, 10);
	    	Calendar a=Calendar.getInstance();
	    	int year=	a.get(Calendar.YEAR);//得到年
	    	/*	int ageInt=Integer.parseInt(age) ;
	    		 ce.setAge(year-ageInt+"");*/
	    }
	    if(person.getSex().equals(EnumConstants.Sex.男.getValue())){ce.setSex("男");}else{ce.setSex("女");};
	    if(person.getMarried().equals(0L)){
	    	ce.setMarried("未婚");
	    }else if(person.getMarried().equals(1L)){
	    	ce.setMarried("已婚");
	    }else if(person.getMarried().equals(2L)){
	    	ce.setMarried("离异");
	    }else if(person.getMarried().equals(3L)){
	    	ce.setMarried("再婚");
	    }else if(person.getMarried().equals(4L)){
	    	ce.setMarried("丧偶");
	    }else if(person.getMarried().equals(5L)){
	    	ce.setMarried("其他");
	    }
	    ce.setAddress(person.getAddress());
	    ce.setPlaceDomicile(person.getPlaceDomicile());
	    ce.setCompany(com.getPlatform());
	    ce.setCompanyAddress(com.getAddress());
	    ce.setCompanyTel(com.getPhone());
	    CollectionCreateCases ccc= selectLastAmountAndDate(loanId);
	    if(ccc!=null){
	    ce.setLastRepayAmount(ccc.getSbAmount().toString());
	    ce.setLastFactReturnDate(dateToStr(ccc.getSbDate()));
	    }
		//查询出借款人手机号
		PersonContacterAdditional pca =new PersonContacterAdditional();
		pca.setAdditionalType(1);
		pca.setRelationId(loan.getPersonId());
		pca.setPager(sendPage);
		Pager pcaPager=findPersonContactAdditionalnMap(pca);
		List<PersonContacterAdditional>  pcaList=pcaPager.getResultList();
		//去掉重复的手机号
		PersonContacterAdditional removePca=null;
		for(PersonContacterAdditional f:pcaList){
			if(f.getAdditionalTel().equals(person.getMobilePhone())){
				removePca=f;
			}
		}
		pcaList.remove(removePca);
		PersonContacterAdditional pcanew=new PersonContacterAdditional();
		pcanew.setAdditionalTel(person.getMobilePhone());
		pcaList.add(pcanew);
		ce.setPhoneList(pcaList);
		//查询出联系人
		CasesPerson cpall=new CasesPerson();
		cpall.setPager(sendPage);
		cpall.setPersonId(loan.getPersonId());
		Pager pagerall = findCollectionContacerWithPG(cpall);
		List<CasesPerson> deleList=new ArrayList<CasesPerson>();
		List<CasesPerson> allList = pagerall.getResultList(); 
		//查询出主表的所有联系人
		CollectionCreateCasesVO vo =new CollectionCreateCasesVO();
		vo.setPager(sendPage);
		vo.setPersonId(loan.getPersonId());
		vo.setLoanId(loanId);
		
		Pager pager = findPersonWithPG(vo);
		List<CasesPerson> zhuList = pager.getResultList(); 
		//过滤掉所有重复数据
		for(CasesPerson per:zhuList){
			for(CasesPerson cp:allList){
				if(per.getId().equals(cp.getRelationId())&&per.getPart().equals(cp.getPart())){
					deleList.add(per);
				}
				
			}
			if(per.getName().equals(person.getName())){
				deleList.add(per);
			}
		}
		zhuList.removeAll(deleList);
		//总集合
		allList.addAll(zhuList);
		ce.setContactserList(allList);
		List<RepaymentPlan> rpList=repayService.getAllInterestOrLoanBySettle(new Date(), loanId);
		ce.setRpList(rpList);
		ceList.add(ce);
		};
		return ceList;
	}
	
		//导出报表
		public  void poiWriteExcel_To2007(String[] headers, OutputStream os,List<ExportExcel> exportExcelList) {
			XSSFWorkbook xssf_w_book = new XSSFWorkbook();
			XSSFRow xssf_w_row = null;//创建一行
			XSSFCell xssf_w_cell = null;//创建每个单元格
			XSSFSheet sheet = xssf_w_book.createSheet();
			//列头样式
			XSSFCellStyle head_cellStyle = xssf_w_book.createCellStyle();//创建一个单元格样式
			XSSFFont head_font = xssf_w_book.createFont();
			head_font.setFontName("宋体");//设置头部字体为宋体
			head_font.setBoldweight(Font.BOLDWEIGHT_BOLD); //粗体
			head_cellStyle.setFont(head_font);//单元格样式使用字体
			head_cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);//单元格边线为细线
			head_cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			head_cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
			head_cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
			head_cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//左右居中       
			head_cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//上下居中 
			//行显示样式
			XSSFCellStyle cellStyle_CN = xssf_w_book.createCellStyle();//创建数据单元格样式(数据库数据样式)
			 	cellStyle_CN.setBorderBottom(XSSFCellStyle.BORDER_THIN);//单元格边线为细线
				cellStyle_CN.setBorderLeft(XSSFCellStyle.BORDER_THIN);
				cellStyle_CN.setBorderRight(XSSFCellStyle.BORDER_THIN);
				cellStyle_CN.setBorderTop(XSSFCellStyle.BORDER_THIN); 
				cellStyle_CN.setAlignment(XSSFCellStyle.ALIGN_CENTER);//左右居中       
				cellStyle_CN.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//上下居中 
			cellStyle_CN.setWrapText(false);//不换行显示
			//标题样式
			XSSFCellStyle titleStyle_CN = xssf_w_book.createCellStyle();//创建数据单元格样式(数据库数据样式)
			titleStyle_CN.setBorderBottom(XSSFCellStyle.BORDER_THIN);//单元格边线为细线
			titleStyle_CN.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			titleStyle_CN.setBorderRight(XSSFCellStyle.BORDER_DOUBLE);
			titleStyle_CN.setBorderTop(XSSFCellStyle.BORDER_THIN); 
			titleStyle_CN.setAlignment(XSSFCellStyle.ALIGN_CENTER);//左右居中       
			titleStyle_CN.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//上下居中 
			titleStyle_CN.setWrapText(false);//不换行显示
			//加标题
			XSSFRow titleRow = sheet.createRow((short) 0);
		//	titleRow.setHeight((short)2000); 
		 	XSSFCell titlecell = titleRow.createCell(0);
			titlecell.setCellValue("逾期信息");
			titlecell.setCellStyle(titleStyle_CN);
			titlecell.setCellType(XSSFCell.CELL_TYPE_STRING);
			sheet.addMergedRegion(new CellRangeAddress(0,0,0,12  ));
			sheet.getNumMergedRegions() ;
			
			XSSFCell titlecell2 = titleRow.createCell(13);
			titlecell2.setCellValue("基本信息");
			titlecell2.setCellStyle(titleStyle_CN);
			titlecell2.setCellType(XSSFCell.CELL_TYPE_STRING);
		 	sheet.addMergedRegion(new CellRangeAddress(0,0,13,18  ));
			sheet.getNumMergedRegions() ; 
			
			XSSFCell titlecell3 = titleRow.createCell(19);
			titlecell3.setCellValue("联系信息");
			titlecell3.setCellStyle(titleStyle_CN);
			titlecell3.setCellType(XSSFCell.CELL_TYPE_STRING);
			sheet.addMergedRegion(new CellRangeAddress(0,0,19,19+titleSize-1));
			sheet.getNumMergedRegions() ;
			
			XSSFCell titlecell4 = titleRow.createCell(19+titleSize);
			titlecell4.setCellValue("委案前末次还款信息");
			titlecell4.setCellStyle(titleStyle_CN);
			titlecell4.setCellType(XSSFCell.CELL_TYPE_STRING);
			sheet.addMergedRegion(new CellRangeAddress(0,0,19+titleSize,19+titleSize+1  ));
			sheet.getNumMergedRegions() ;
			if(titleSize2>0){
		    XSSFCell titlecell5 = titleRow.createCell(19+titleSize+2);
			titlecell5.setCellValue("委案前其他还款信息");
			titlecell5.setCellStyle(titleStyle_CN);
			titlecell5.setCellType(XSSFCell.CELL_TYPE_STRING);
			sheet.addMergedRegion(new CellRangeAddress(0,0,19+titleSize+2 ,19+titleSize+2+titleSize2 -1));
			sheet.getNumMergedRegions() ;}
			XSSFCell titlecell6 = titleRow.createCell(19+titleSize+2+titleSize2);
			titlecell6.setCellValue("备注");
			titlecell6.setCellStyle(titleStyle_CN);
			titlecell6.setCellType(XSSFCell.CELL_TYPE_STRING);
			sheet.addMergedRegion(new CellRangeAddress(0,1,19+titleSize+2+titleSize2,19+titleSize+2+titleSize2 ));
			sheet.getNumMergedRegions() ;
			XSSFRow headerRow = sheet.createRow((short) 1);
			for (int i = 0; i < headers.length; i++) {
	 			XSSFCell cell = headerRow.createCell(i);
	 			cell.setCellStyle(head_cellStyle);
	 			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
	 			cell.setCellValue(headers[i]);
	 		}
			for (int i = 0; i < 1; i++) {
				if(exportExcelList.size()>0){	
					 int rowLength =exportExcelList.get(0).getRows().size();
					//遍历行
					for (int j = 0; j < rowLength; j++) {
						//创建行
						xssf_w_row = sheet.createRow(2+j);
					
						for (int a = 0; a < exportExcelList.get(i).getRows().get(j).length; a++) {
								//创建单元格
								xssf_w_cell = xssf_w_row.createCell(a);
								//数据显示单元格样式设置
								xssf_w_cell.setCellStyle(cellStyle_CN);
								//xssf_w_sheet.autoSizeColumn(a, true);
								//列头以及显示的数据	j 第几行 a  第几个单元格	 如果为数字	
								xssf_w_cell.setCellValue(exportExcelList.get(i).getRows().get(j)[a]);
						}
					} 
				 }
				for (int k = 0; k < headers.length; k++) {
					//创建单元格
					sheet.autoSizeColumn(k);
				}
			}
			sheet.setColumnWidth((short) 19+titleSize+2+titleSize2, (short) 5000);
			try {
				xssf_w_book.write(os);
				os.flush();
				os.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		//计算逾期信息
				public RepayEntryDetailsVO viewEdit(Long loanId) {
					Date nowDate = DateUtil.getToday();
					Loan loan = loanService.get(loanId);
					Person person;
					if (loan != null) {
						person = personService.get(loan.getPersonId());
					} else {
						Extension extension = extensionService.get(loanId);
						person = personService.get(extension.getPersonId());
					}
					String name = person.getName();
					String idnum = person.getIdnum();
					String mobilePhone = person.getMobilePhone();
					RepayEntryDetailsVO repayEntryDetailsVO = new RepayEntryDetailsVO();
					repayEntryDetailsVO.setLoanId(loanId);
					repayEntryDetailsVO.setPersonName(name);
					repayEntryDetailsVO.setPersonIdnum(idnum);
					repayEntryDetailsVO.setPersonMobilePhone(mobilePhone);
					List<RepaymentPlan> repaymentPlanList = repayService.getAllInterestOrLoan(nowDate, loanId);
					BigDecimal accAmount = repayService.getAccAmount(loanId);
					//设置挂账金额,期末预收
					repayEntryDetailsVO.setAccAmount(accAmount);
					Integer currTerm = repayService.getCurrTerm(repaymentPlanList, nowDate);
					//判断如果该期期数为0,则从已结清的还款计划表中获取
					if (currTerm.compareTo(0) == 0) {
						List<RepaymentPlan> repaymentPlanSettleList = repayService.getAllInterestOrLoanBySettle(nowDate, loanId);
						currTerm = repayService.getCurrTerm(repaymentPlanSettleList, nowDate);
						int size = repaymentPlanSettleList.size();
						if (size > 0) {
							Date repayDay = repaymentPlanSettleList.get(size - 1).getRepayDay();
							//设置当期还款日
							repayEntryDetailsVO.setCurRepayDate(DateUtil.defaultFormatDay(repayDay));
						}
					}
					repayEntryDetailsVO.setCurrTerm(currTerm);
					//设置当前期数
					BigDecimal reliefOfFine = specialRepaymentService.getReliefOfFine(nowDate, loanId);
					//设置减免金额
					repayEntryDetailsVO.setReliefOfFine(reliefOfFine);
					//设置还款日期
					repayEntryDetailsVO.setNowDate(DateUtil.defaultFormatDay(nowDate));
					BigDecimal fine = repayService.getFine(repaymentPlanList, nowDate);
					//设置罚息
					repayEntryDetailsVO.setFine(fine);
					BigDecimal overdueAmount = repayService.getOverdueAmount(repaymentPlanList, nowDate);
					//逾期本金
					  overduePrincipal = repayService.getOverduePrincipal(repaymentPlanList, nowDate);
					//逾期利息
					  overdueInterest = repayService.getOverdueInterest(repaymentPlanList, nowDate);
					//违约金
					    penalty=repayService.getPenalty( repaymentPlanList,  nowDate);
					   
					BigDecimal overdueAllAmount = overdueAmount.add(fine);
					repayEntryDetailsVO.setOverduePrincipalInterestSum(new BigDecimal(overduePrincipal.doubleValue()+overdueInterest.doubleValue()));
					//设置逾期应金额
					repayEntryDetailsVO.setOverdueAmount(overdueAmount);
					 
				/*	BigDecimal repayAllAmount = currRepayAmount.add(overdueAllAmount).subtract(accAmount);
					//应还总额（包含当期)
					repayEntryDetailsVO.setRepayAllAmount(repayAllAmount);*/

					BigDecimal repayAmount = overdueAllAmount;
					//如果结果小于0,则返回0
					if (repayAmount.compareTo(BigDecimal.ZERO) == -1) {
						repayEntryDetailsVO.setRepayAmount(BigDecimal.ZERO);
					} else {
						//应还总额（不含当期)
						repayEntryDetailsVO.setRepayAmount(repayAmount);
					}
					if (repaymentPlanList != null && repaymentPlanList.size() > 0) {
						int size = repaymentPlanList.size();
						Integer overdueTermCount = repayService.getOverdueTermCount(repaymentPlanList, nowDate);
						//设置逾期总数
						repayEntryDetailsVO.setOverdueTerm(overdueTermCount);
						//如果逾期期数大于等于1 
						if (overdueTermCount >= 1) {
							//设置逾期起始日
							repayEntryDetailsVO.setOverdueStartDate(DateUtil.defaultFormatDay(repaymentPlanList.get(0).getRepayDay()));
						}
						int overdueFineDay = repayService.getOverdueFineDay(repaymentPlanList, nowDate);
						//设置罚息天数
						repayEntryDetailsVO.setFineDay(overdueFineDay);

						if (overdueFineDay >= 1) {
							//设置罚息起算日
							repayEntryDetailsVO.setFineDate(DateUtil.defaultFormatDay(DateUtil.getDateBefore(overdueFineDay)));
						}
						Date repayDay = repaymentPlanList.get(size - 1).getRepayDay();
						//设置当期还款日
						repayEntryDetailsVO.setCurRepayDate(DateUtil.defaultFormatDay(repayDay));

					}
					boolean oneTimeRepayment = specialRepaymentService.isOneTimeRepayment(loanId);
					BigDecimal repayAllAmount = BigDecimal.ZERO;
					if (oneTimeRepayment) {
						//一次性结清
						BigDecimal onetimeRepaymentAmount = repayService.getOnetimeRepaymentAmount(repaymentPlanList, nowDate);
						repayEntryDetailsVO.setOnetimeRepaymentAmount(onetimeRepaymentAmount);
						repayEntryDetailsVO.setCurrAmountLabel("一次性还款金额");
						repayEntryDetailsVO.setCurrAmount(onetimeRepaymentAmount);
						//应还总额 =一次性结清 + 逾期应还 - 期末预收
						repayAllAmount = onetimeRepaymentAmount.add(overdueAllAmount);

					} else {
						BigDecimal nextRepayAmount = repayService.getNextRepayAmount(repaymentPlanList, nowDate);
						repayEntryDetailsVO.setCurrAmountLabel("当期应还总额");
						//判断如果没有逾期的话则取当期还款金额，有逾期金额的话当期还款金额为逾期应还总额
						//			if (overdueAllAmount.compareTo(BigDecimal.ZERO) == 0) {
						//				repayEntryDetailsVO.setCurrAmount(currRepayAmount);
						//			} else {
						//				repayEntryDetailsVO.setCurrAmount(overdueAllAmount);
						//			}
						repayEntryDetailsVO.setCurrAmount(nextRepayAmount);
						//应还总额（包含当期） = 当期应还总额+应还总额（不含当期）
						if (repayEntryDetailsVO.getCurrAmount() != null) {
							repayAllAmount = repayAmount.add(repayEntryDetailsVO.getCurrAmount());
						}
					}
					//如果结果小于0,则返回0
					if (repayAllAmount.compareTo(BigDecimal.ZERO) == -1) {
						repayEntryDetailsVO.setRepayAllAmount(BigDecimal.ZERO);
					} else {
						repayEntryDetailsVO.setRepayAllAmount(repayAllAmount);
					}
					return repayEntryDetailsVO;
				}
		public static String dateToStr(Date date) throws ParseException{
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟  
			String str = sdf.format(date); 
			return str;
		}
		
}
