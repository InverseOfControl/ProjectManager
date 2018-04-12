/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.system.service.impl;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ezendai.credit2.apply.model.BankAccount;
import com.ezendai.credit2.apply.model.ChannelPlanCheck;
import com.ezendai.credit2.apply.model.Company;
import com.ezendai.credit2.apply.model.Contacter;
import com.ezendai.credit2.apply.model.CreditHistory;
import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.model.Person;
import com.ezendai.credit2.audit.model.Contract;
import com.ezendai.credit2.audit.model.PersonBank;
import com.ezendai.credit2.audit.model.RepaymentPlan;
import com.ezendai.credit2.finance.model.Ledger;
import com.ezendai.credit2.finance.model.RepayInfo;
import com.ezendai.credit2.framework.util.StringUtil;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.master.model.BaseArea;
import com.ezendai.credit2.master.service.CompanyService;
import com.ezendai.credit2.system.model.Organ;
import com.ezendai.credit2.system.model.OrganBankAccount;
import com.ezendai.credit2.system.model.OrganSalesDepartment;
import com.ezendai.credit2.system.model.OrganSalesManager;
import com.ezendai.credit2.system.model.SysUser;
import com.ezendai.credit2.system.service.ExcelAnalyticalService;




/**

 */
@Service
public class ExcelAnalyticalServiceImpl implements ExcelAnalyticalService {
	
	
	@Autowired
	private CompanyService companyService;

	//总行数
	private int totalRows = 0;  
	//总条数
	private int totalCells = 0; 
	@Override
	public Map<String, Object> Analytical(String fileName, MultipartFile Mfile) {
		InputStream is = null;  
		boolean isExcel2003 = true;
		if(isExcel2007(fileName)){
			isExcel2003 = false;  
		}
		Map<String, Object> map=new HashMap<String, Object>();
		try {
			is = (Mfile.getInputStream());
			/** 根据版本选择创建Workbook的方式 */
			Workbook wb = null;
			if(isExcel2003){
				wb = new HSSFWorkbook(is); 
			}
			else{
				wb = new XSSFWorkbook(is); 
			}
			List<Organ>  getOrganList=getOrganList(wb, Mfile);
			List<OrganSalesDepartment>  getOrganSalesDepartmentList=getOrganSalesDepartmentList(wb, Mfile);
			List<OrganBankAccount>  getOrganBankAccountList=getOrganBankAccountList(wb, Mfile);
			List<OrganSalesManager>  getOrganSalesManagerList=getOrganSalesManagerList(wb, Mfile);
			List<ChannelPlanCheck>  getChannelPlanCheckList=getChannelPlanCheckList(wb, Mfile);
			map.put("orgList", getOrganList);
			map.put("orgSalesList", getOrganSalesDepartmentList);
			map.put("orgBankList", getOrganBankAccountList);
			map.put("orgManagerList", getOrganSalesManagerList);
			map.put("channelList", getChannelPlanCheckList);
		} catch (Exception e) {
			e.printStackTrace();  
		}

		return map;
	}
	
	//excel导入loan相关信息
	@Override
	public Map<String, Object> AnalyticalLoan(String fileName, MultipartFile Mfile) {
		InputStream is = null;  
		boolean isExcel2003 = true;
		if(isExcel2007(fileName)){
			isExcel2003 = false;  
		}
		Map<String, Object> map=new HashMap<String, Object>();
		try {
			is = (Mfile.getInputStream());
			/** 根据版本选择创建Workbook的方式 */
			Workbook wb = null;
			if(isExcel2003){
				wb = new HSSFWorkbook(is); 
			}
			else{
				wb = new XSSFWorkbook(is); 
			}
//			List<Company>  getCompanyList=getCompanyList(wb, Mfile);
			List<Person> getPersonList=getPersonList(wb, Mfile);
			List<Loan> getLoanList=getLoanList(wb, Mfile);
			List<Contacter> getContacterList=getContacterList(wb, Mfile);
			List<CreditHistory> getCreditHistoryList=getCreditHistoryList(wb, Mfile);
//			List<Contract> getContractList=getContractList(wb, Mfile);
			List<RepaymentPlan> getRepaymentPlanList=getRepaymentPlanList(wb, Mfile);
			List<PersonBank> getPersonBankList=getPersonBankList(wb, Mfile);
			List<RepayInfo> getRepayInfoList=getRepayInfoList(wb, Mfile);
			List<Ledger> getLedgerList=getLedgerList(wb, Mfile);
//			map.put("companyList", getCompanyList);
			map.put("personList", getPersonList);
			map.put("loanList", getLoanList);
			map.put("contacterList", getContacterList);
			map.put("creditHistoryList", getCreditHistoryList);
			map.put("repaymentPlanList", getRepaymentPlanList);
//			map.put("contractList", getContractList);
			map.put("personBankList", getPersonBankList);
			map.put("repayInfoList", getRepayInfoList);
			map.put("ledgerList", getLedgerList);
			
		} catch (Exception e) {
			e.printStackTrace();  
		}

		return map;
	}

	//获取机构信息List
	public 	List<Organ>  getOrganList(Workbook wb ,MultipartFile Mfile) {
		List<Organ> orgList=new ArrayList<Organ>();
		try {
			//第一个sheet 获取机构信息的sheet
			Sheet sheet=wb.getSheetAt(0);
			//得到Excel的行数
			this.totalRows=sheet.getPhysicalNumberOfRows();
			for(int r=1;r<totalRows;r++)
			{
				Row row = sheet.getRow(r);
				if (row == null) continue;
			 	if(isBlankRow(row)) continue;
				if(null != row && row.getFirstCellNum() >-1){
				int rowNum=row.getLastCellNum();		
				Organ organ=new Organ();
				//循环Excel的列
				for(int c = 0; c <rowNum; c++)
				{ 
					String count=null;
					Cell cell = row.getCell(c); 
					if (null != cell){
						//第一列<机构内部编码>
						if(c==0){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								organ.setCode(cell.getStringCellValue());
							}
							//第二列<机构地址>	
						}else if(c==1){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								organ.setAddress(cell.getStringCellValue());
							}
							//第三列<机构电话>		
						}else if(c==2){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								organ.setTel(cell.getStringCellValue());
							}
							//第四列<机构名称>		
						}else if(c==3){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								organ.setName(cell.getStringCellValue());
							}
							//第五列<邮政编码>		
						}else if(c==4){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								organ.setPostCode(cell.getStringCellValue());
							}
							/*if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {  
								 DecimalFormat df = new DecimalFormat("0");  
							     String strCell = df.format(cell.getNumericCellValue()); 
							 	organ.setPostCode(strCell);
							}*/
							//第六列<法人姓名>		
						}else if(c==5){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								organ.setLegalRepresentative(cell.getStringCellValue());
							}
							//第七列<法人省份证>		
						}else if(c==6){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								organ.setLegalRepresentativeId(cell.getStringCellValue());
							}
						/*	if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {  
								 DecimalFormat df = new DecimalFormat("0");  
							     String strCell = df.format(cell.getNumericCellValue()); 
								organ.setLegalRepresentativeId(strCell);
							}else if(cell.getCellType() == Cell.CELL_TYPE_STRING){
								organ.setLegalRepresentativeId(cell.getStringCellValue());
							}*/
							//第八列<法人联系电话>		
						}else if(c==7){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								organ.setLegalTel(cell.getStringCellValue());
							}
							//第九列<签约日期>		
						}else if(c==8){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								 organ.setSignedDate(strToDate(cell.getStringCellValue()));
							}
						/*	if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {  
								 DecimalFormat df = new DecimalFormat("0");  
							     String strCell = df.format(cell.getNumericCellValue()); 
								 organ.setSignedDate(strToDate(strCell));
							}*/
							//第十列<保证金>		
						}else if(c==9){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								BigDecimal decimalToStr=new BigDecimal(cell.getStringCellValue());
								organ.setMargin(decimalToStr);
							}
							//第十一列<机构等级>		
						}else if(c==10){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								organ.setOrgLevel(cell.getStringCellValue());
							}
						}

					}

				}	
				orgList.add(organ);
			} 
			}
		} catch (Exception e) {
			e.printStackTrace();  
		}
		return orgList;

	}
	//获取机构门店List
	public 	List<OrganSalesDepartment>  getOrganSalesDepartmentList(Workbook wb ,MultipartFile Mfile) {
		List<OrganSalesDepartment> orgDepartmentList=new ArrayList<OrganSalesDepartment>();
		try {
			//第二个sheet 获取机构门店的sheet
			Sheet sheet=wb.getSheetAt(1);
			//得到Excel的行数
			this.totalRows=sheet.getPhysicalNumberOfRows();
			for(int r=1;r<totalRows;r++)
			{
				Row row = sheet.getRow(r);
				if (row == null) continue;
				if(isBlankRow(row)) continue;
				int rowNum=row.getLastCellNum();		
				OrganSalesDepartment organSalesDepartment=new OrganSalesDepartment();
				//循环Excel的列
				for(int c = 0; c <rowNum; c++)
				{ 
					String count=null;
					Cell cell = row.getCell(c); 
					if (null != cell){
						//第一列<机构内部编码>
						if(c==0){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								organSalesDepartment.setOrganCode(cell.getStringCellValue());
							}
							//第二列<营业网点名称>	
						}else if(c==1){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								organSalesDepartment.setAreaName(cell.getStringCellValue());
							}

						}
					}
				}
				orgDepartmentList.add(organSalesDepartment);
			} 
		} catch (Exception e) {
			e.printStackTrace();  
		}
		return orgDepartmentList;

	}
	//获取机构银行账户List
	public 	List<OrganBankAccount>  getOrganBankAccountList(Workbook wb ,MultipartFile Mfile) {
		List<OrganBankAccount> organBankAccountList=new ArrayList<OrganBankAccount>();
		try {
			//第三个sheet 获取机构银行账户的sheet
			Sheet sheet=wb.getSheetAt(2);
			//得到Excel的行数
			this.totalRows=sheet.getPhysicalNumberOfRows();
			for(int r=1;r<totalRows;r++)
			{
				Row row = sheet.getRow(r);
				if (row == null) continue;
				if(isBlankRow(row)) continue;
				int rowNum=row.getLastCellNum();		
				OrganBankAccount organBankAccount=new OrganBankAccount();
				//循环Excel的列
				for(int c = 0; c <rowNum; c++)
				{ 
					String count=null;
					Cell cell = row.getCell(c); 
					if (null != cell){
						//第一列<机构内部编码>
						if(c==0){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								organBankAccount.setOrganCode(cell.getStringCellValue());
							}
							//第二列<账户>	
						}else if(c==1){
						
							if(cell.getCellType()==Cell.CELL_TYPE_STRING){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								organBankAccount.setAccount(cell.getStringCellValue());
							}
							
								/*if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {  
									 DecimalFormat df = new DecimalFormat("0");  
								     String strCell = df.format(cell.getNumericCellValue()); 
								 	 organBankAccount.setAccount(strCell);
								}*/

							//第三列<银行名称>	
						}else if(c==2){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								organBankAccount.setBranchName(cell.getStringCellValue());
							}
							//第四列<总行名称>	
						}else if(c==3){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								organBankAccount.setBankName(cell.getStringCellValue());
							}

							//第五列<户名>	
						}else if(c==4){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								organBankAccount.setAccountName(cell.getStringCellValue());
							}
							//第六列<卡类型>	
						}else if(c==5){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								if(cell.getStringCellValue().equals("对公账户")){
									organBankAccount.setCardType("1");
								}else if(cell.getStringCellValue().equals("对私账户")){
									organBankAccount.setCardType("2");
								}
								
							}
						}
					}
				}
				organBankAccountList.add(organBankAccount);
			} 
		} catch (Exception e) {
			e.printStackTrace();  
		}
		return organBankAccountList;

	}

	//获取机构客户经理List
	public 	List<OrganSalesManager>  getOrganSalesManagerList(Workbook wb ,MultipartFile Mfile) {
		List<OrganSalesManager> organSalesManagerList=new ArrayList<OrganSalesManager>();
		try {
			//第四个sheet 获取机构客户经理的sheet
			Sheet sheet=wb.getSheetAt(3);
			//得到Excel的行数
			this.totalRows=sheet.getPhysicalNumberOfRows();
			for(int r=1;r<totalRows;r++)
			{
				Row row = sheet.getRow(r);
				if (row == null) continue;
				if(isBlankRow(row)) continue;
				int rowNum=row.getLastCellNum();		
				OrganSalesManager organSalesManager=new OrganSalesManager();
				//循环Excel的列
				for(int c = 0; c <rowNum; c++)
				{ 
					String count=null;
					Cell cell = row.getCell(c); 
					if (null != cell){
						//第一列<机构内部编码>
						if(c==0){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								organSalesManager.setOrgCode(cell.getStringCellValue());
							}
							//第二列<客户经理>	
						}else if(c==1){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								organSalesManager.setSalesManager(cell.getStringCellValue());
							}

							//第三列<工号>	
						}else if(c==2){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								organSalesManager.setCode(cell.getStringCellValue());
							}

						}
					}
				
				} 
				organSalesManagerList.add(organSalesManager);
			}
		} catch (Exception e) {
			e.printStackTrace();  
		}
		return organSalesManagerList;

	}
		//获取方案批复List
		public 	List<ChannelPlanCheck>  getChannelPlanCheckList(Workbook wb ,MultipartFile Mfile) {
			List<ChannelPlanCheck> channelPlanCheckList=new ArrayList<ChannelPlanCheck>();
			try {
				//第四个sheet 获取机构客户经理的sheet
				Sheet sheet=wb.getSheetAt(4);
				//得到Excel的行数
				this.totalRows=sheet.getPhysicalNumberOfRows();
				for(int r=1;r<totalRows;r++)
				{
					Row row = sheet.getRow(r);
					if (row == null) continue;
					if(isBlankRow(row)) continue;
					if(null != row && row.getFirstCellNum() >-1){
					int rowNum=row.getLastCellNum();		
					ChannelPlanCheck channelPlanCheck=new ChannelPlanCheck();
					//循环Excel的列
					for(int c = 0; c <rowNum; c++)
					{ 
						
						 
						String count=null;
						Cell cell = row.getCell(c); 
						if (null != cell){
							//第一列<方案代码>
							if(c==0){
								if(cell.getCellType()==Cell.CELL_TYPE_STRING){
									cell.setCellType(Cell.CELL_TYPE_STRING);
									channelPlanCheck.setCode(cell.getStringCellValue());
								}
								if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {  
									 DecimalFormat df = new DecimalFormat("0");  
								     String strCell = df.format(cell.getNumericCellValue()); 
								 	 channelPlanCheck.setCode(strCell);
								}
								//第二列<方案名称>	
							}else if(c==1){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									channelPlanCheck.setName(cell.getStringCellValue());
								}

								//第三列<停售日期>	
							}else if(c==2){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									channelPlanCheck.setEndDate(strToDate(cell.getStringCellValue()));
								}
								/*if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {  
									 DecimalFormat df = new DecimalFormat("0");  
								     String strCell = df.format(cell.getNumericCellValue()); 
								 	channelPlanCheck.setEndDate(strToDate(strCell));
								}*/
								//第四列<保证金>	
							}else if(c==3){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									BigDecimal strTobigdecimal=new BigDecimal(cell.getStringCellValue());
									channelPlanCheck.setMargin(strTobigdecimal);
								}

								//第五列<机构内部编号>	
							}else if(c==4){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									channelPlanCheck.setOrgCode(cell.getStringCellValue());
								}
								//第六列<机构还款期限>	
							}else if(c==5){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									 
									channelPlanCheck.setOrgRepayTerm(Integer.valueOf(cell.getStringCellValue()));
								}
								/*if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {  
									 DecimalFormat df = new DecimalFormat("0");  
								     String strCell = df.format(cell.getNumericCellValue()); 
								 	channelPlanCheck.setOrgRepayTerm(Integer.valueOf(strCell));
								}*/
								//第七列<审核状态>	
							}else if(c==6){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									channelPlanCheck.setApproverState(Integer.valueOf(cell.getStringCellValue()));
								}
								/*if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {  
									 DecimalFormat df = new DecimalFormat("0");  
								     String strCell = df.format(cell.getNumericCellValue()); 
								 	channelPlanCheck.setApproverState(Integer.valueOf(strCell));
								}*/

								//第八列<合同金额>	
							}else if(c==7){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									BigDecimal strTobigdecimal=new BigDecimal(cell.getStringCellValue());
									channelPlanCheck.setPactMoney(strTobigdecimal);
								}

								//第九列<方案类型>	
							}else if(c==8){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									channelPlanCheck.setPlanType(cell.getStringCellValue());
								}

								//第十列<费用合计>	
							}else if(c==9){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									BigDecimal strTobigdecimal=new BigDecimal(cell.getStringCellValue());
									channelPlanCheck.setRateSum(strTobigdecimal);
								}

								//第十一列<申请金额>	
							}else if(c==10){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									BigDecimal strTobigdecimal=new BigDecimal(cell.getStringCellValue());
									channelPlanCheck.setRequestMoney(strTobigdecimal);
								}

								//第十二列<一期还款金额>	
							}else if(c==11){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									BigDecimal strTobigdecimal=new BigDecimal(cell.getStringCellValue());
									channelPlanCheck.setReturneterm1(strTobigdecimal);
								}

								//第十三列<二期还款金额>	
							}else if(c==12){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									BigDecimal strTobigdecimal=new BigDecimal(cell.getStringCellValue());
									channelPlanCheck.setReturneterm2(strTobigdecimal);
								}

								//第十四列<退回原因>	
							}else if(c==13){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									channelPlanCheck.setSendBackMemo(cell.getStringCellValue());
								}

								//第十五列<开售日期>	
							}else if(c==14){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									channelPlanCheck.setStartDate(strToDate(cell.getStringCellValue()));
								}
								/*if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {  
									 DecimalFormat df = new DecimalFormat("0");  
								     String strCell = df.format(cell.getNumericCellValue()); 
								 	channelPlanCheck.setStartDate(strToDate(strCell));
								}*/
								//第十六列<申请期数>	
							}else if(c==15){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									channelPlanCheck.setTime(Integer.valueOf(cell.getStringCellValue()));
								}
								/*if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {  
									 DecimalFormat df = new DecimalFormat("0");  
								     String strCell = df.format(cell.getNumericCellValue()); 
								 	channelPlanCheck.setTime(Integer.valueOf(strCell));
								}*/	
								//第十七列<一期止月>	
							}else if(c==16){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									channelPlanCheck.setToTerm1(Integer.valueOf(cell.getStringCellValue()));
								}
								
								/*if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {  
									 DecimalFormat df = new DecimalFormat("0");  
								     String strCell = df.format(cell.getNumericCellValue()); 
								 	channelPlanCheck.setToTerm1(Integer.valueOf(strCell));
								}*/
								//第十八列<二期止月>	
							}else if(c==17){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									channelPlanCheck.setToTerm2(Integer.valueOf(cell.getStringCellValue()));
								}
								/*if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {  
									 DecimalFormat df = new DecimalFormat("0");  
								     String strCell = df.format(cell.getNumericCellValue()); 
								 	channelPlanCheck.setToTerm2(Integer.valueOf(strCell));
								}*/
								//第十九列<月综合费率>	
							}else if(c==18){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									BigDecimal strTobigdecimal=new BigDecimal(cell.getStringCellValue());
									channelPlanCheck.setActualRate(strTobigdecimal);
								}

								//第二十列<机构是否承担服务费>	
							}else if(c==19){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									channelPlanCheck.setOrgFeeState( cell.getStringCellValue());
								}

								//第二十一列<还款类型>	
							}else if(c==20){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(cell.getCellType() == Cell.CELL_TYPE_STRING) {  
									channelPlanCheck.setReturnType(Integer.valueOf(cell.getStringCellValue()));
								}
						
							/*	if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {  
									 DecimalFormat df = new DecimalFormat("0");  
								     String strCell = df.format(cell.getNumericCellValue()); 
								 	channelPlanCheck.setReturnType(Integer.valueOf(strCell));
								}*/
							}
							
						}
					
					} 
					channelPlanCheckList.add(channelPlanCheck);
				}
				}
			} catch (Exception e) {
				e.printStackTrace();  
			}
			return channelPlanCheckList;

		}
		
		//获取企业信息List
		public 	List<Company>  getCompanyList(Workbook wb ,MultipartFile Mfile) {
			List<Company> companyList=new ArrayList<Company>();
			try {
				Sheet sheet=wb.getSheetAt(0);
				//得到Excel的行数
				this.totalRows=sheet.getPhysicalNumberOfRows();
				for(int r=1;r<totalRows;r++)
				{
					Row row = sheet.getRow(r);
					if (row == null) continue;
				 	if(isBlankRow(row)) continue;
					if(null != row && row.getFirstCellNum() >-1){
					int rowNum=row.getLastCellNum();		
					Company company=new Company();
					//循环Excel的列
					for(int c = 0; c <rowNum; c++)
					{ 
						String count=null;
						Cell cell = row.getCell(c); 
						if (null != cell){
							int i=0;
							//第一列<企业全称>
							if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									company.setName(cell.getStringCellValue());
								}
								//第二列<所属行业>	
							}else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									company.setIndustryInvolved(cell.getStringCellValue());
								}
								//第三列<法人代表>		
							}else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									company.setLegalRepresentative(cell.getStringCellValue());
								}
								//第四列<法人代表身份证号>		
							}else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									company.setLegalRepresentativeId(cell.getStringCellValue());
								}
								//第五列<近年营业额>		
							}else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									company.setIncomePerMonth( Long.parseLong(cell.getStringCellValue()));
								}
								//第六列<成立时间>		
							}else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									company.setFoundedDate(strToDate(cell.getStringCellValue()));
								}
								//第七列<企业类型>		
							}else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									company.setCategory(cell.getStringCellValue());
								}
								//第八列<企业地址>		
							}else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									company.setAddress(cell.getStringCellValue());
								}
								//第九列<平均年利率>		
							}else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									company.setAvgProfitPerYear(Long.parseLong(cell.getStringCellValue()));
								}
								//第十列<企业电话>		
							}else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									company.setPhone(cell.getStringCellValue());
								}
								//第十一列<邮政编码>		
							}else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									company.setZipCode(cell.getStringCellValue());
								}
							}
								//第十二列<经营场所>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									company.setOperationSite(cell.getStringCellValue());
								}
							}
								//第十三列<主营业务>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									company.setMajorBusiness(cell.getStringCellValue());
								}
							}
								//第十四列<员工人数>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									company.setEmployeesNumber(Long.parseLong(cell.getStringCellValue()));
								}
							}
								//第十五列<员工工资支出>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									company.setEmployeesWagesPerMonth(Long.parseLong(cell.getStringCellValue()));
								}
							}		
								//第十六列<收单机构>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									company.setPosAcquirer(cell.getStringCellValue());
								}
							}
								//第十七列<月均交易量构>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									company.setPosCapitavolume(Long.parseLong(cell.getStringCellValue()));
								}
							}	
								//第十八列<月营业额>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									company.setMonthTurnOver(Long.parseLong(cell.getStringCellValue()));
								}
							}
								//第十九列<所属平台>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									company.setPlatform(cell.getStringCellValue());
								}
							}
								//第二十列<会员类型>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									company.setMemberType(Long.parseLong(cell.getStringCellValue()));
								}
							}
								//第二十列<注册日期>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									company.setRegDate(strToDate(cell.getStringCellValue()));
								}
							}							
						}
					}	
					companyList.add(company);
					} 
				}
			} catch (Exception e) {
				e.printStackTrace();  
			}
			return companyList;

		}
		
		//获取客户信息List
		public 	List<Person>  getPersonList(Workbook wb ,MultipartFile Mfile) {
			List<Person> personList=new ArrayList<Person>();
			try {
				Sheet sheet=wb.getSheetAt(1);
				//得到Excel的行数
				this.totalRows=sheet.getPhysicalNumberOfRows();
				for(int r=1;r<totalRows;r++)
				{
					Row row = sheet.getRow(r);
					if (row == null) continue;
				 	if(isBlankRow(row)) continue;
					if(null != row && row.getFirstCellNum() >-1){
					int rowNum=row.getLastCellNum();		
					Person person=new Person();
					Company companyImp=new Company();
					
					//循环Excel的列
					for(int c = 0; c <rowNum; c++)
					{ 
						String count=null;
						Cell cell = row.getCell(c); 
						if (null != cell){
							int i=0;
							
								//第一列<姓名>
							if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									person.setName(cell.getStringCellValue());
								}else{
									person.setName("");
								}
								//第二列<性别>	
							}else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									person.setSex(cell.getStringCellValue());
								}else{
									person.setSex("");
								}
								//第三列<客户编号>		
							}else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									person.setPersonNo(cell.getStringCellValue());
								}else{
									person.setPersonNo("");
								}
								//第四列<身份证号>		
							}else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									person.setIdnum(cell.getStringCellValue());
								}else{
									person.setIdnum("");
								}
								//第五列<婚姻状况>		
							}else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									if(cell.getStringCellValue().trim().equals("已婚")){
										person.setMarried(Long.parseLong(EnumConstants.Married.MARRIED.getValue()));
									}else if(cell.getStringCellValue().trim().equals("离异")){
										person.setMarried(Long.parseLong(EnumConstants.Married.DIVORCE.getValue()));
									}else if(cell.getStringCellValue().trim().equals("其它")){
										person.setMarried(Long.parseLong(EnumConstants.Married.OTHER.getValue()));
									}else if(cell.getStringCellValue().trim().equals("再婚")){
										person.setMarried(Long.parseLong(EnumConstants.Married.REMARRIAGE.getValue()));
									}else if(cell.getStringCellValue().trim().equals("未婚")){
										person.setMarried(Long.parseLong(EnumConstants.Married.UNMARRIED.getValue()));
									}else if(cell.getStringCellValue().trim().equals("丧偶")){
										person.setMarried(Long.parseLong(EnumConstants.Married.WIDOWED.getValue()));
									}else{
										person.setMarried(Long.parseLong(EnumConstants.Married.OTHER.getValue()));
									}
								}
								//第六列<类别>
							}
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									person.setIdentifier(cell.getStringCellValue());
								}else{
									person.setIdentifier("");
								}
								//第七列<最高学历>		
							}else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									person.setEducationLevel(cell.getStringCellValue());
								}else{
									person.setEducationLevel("");
								}
								//第八列<有无子女>		
							}else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									if(cell.getStringCellValue().trim().equals("1")){
										person.setHasChildren(EnumConstants.YesOrNo.YES.getValue().longValue());
									}else if(cell.getStringCellValue().trim().equals("0")){
										person.setHasChildren(EnumConstants.YesOrNo.NO.getValue().longValue());
									}
								}
								//第九列<子女数目>		
							}else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									person.setChildrenNum(Integer.parseInt(cell.getStringCellValue()));
								}
								//第十列<户籍地址>		
							}else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									person.setPlaceDomicile(cell.getStringCellValue());
								}else{
									person.setPlaceDomicile("");
								}
								//第十一列<邮政编码>		
							}
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									person.setZipCode(cell.getStringCellValue());
								}else{
									person.setZipCode("");
								}
							}
								//第十二列<工作证明人>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									person.setWitness(cell.getStringCellValue());
								}else{
									person.setWitness("");
								}
							}
								//第十三列<其他收入>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									person.setOtherIncome(new BigDecimal(cell.getStringCellValue()));
								}
							}
								//第十四列<月发薪日>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									person.setPayDate(Long.parseLong(cell.getStringCellValue()));
								}
							}
								//第十五列<工作性质>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									person.setWorkNature(cell.getStringCellValue().trim());
								}else{
									person.setWorkNature("");
								}
							}		
								//第十六列<职务>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									person.setJob(cell.getStringCellValue());
								}else{
									person.setJob("");
								}
							}
								//第十七列<部门>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									person.setDeptName(cell.getStringCellValue());
								}else{
									person.setDeptName("");
								}
							}	
								//第十八列<固话分机>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									person.setExt(cell.getStringCellValue());
								}else{
									person.setExt("");
								}
							}
								//第十九列<住宅地址>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									person.setAddress(cell.getStringCellValue());
								}else{
									person.setAddress("");
								}
							}
								//第二十列<手机号码>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									person.setMobilePhone(cell.getStringCellValue());
								}else{
									person.setMobilePhone("");
								}
							}
								//第二十一列<常用邮箱>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									person.setEmail(cell.getStringCellValue());
								}else{
									person.setEmail("");
								}
							}			
								//第二十二列<住宅电话>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									person.setHomePhone(cell.getStringCellValue());
								}else{
									person.setHomePhone("");
								}
							}	
								//第二十三列<房产类型>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									person.setHouseEstateType(cell.getStringCellValue());
								}else{
									person.setHouseEstateType("");
								}
							}
								//第二十四列<每月租金>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									person.setRentPerMonth(new BigDecimal(cell.getStringCellValue()));
								}
							}	
								//第二十五列<居住类型>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									person.setLiveType(cell.getStringCellValue());
								}else{
									person.setLiveType("");
								}
							}	
								//第二十六列<房贷>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									person.setHasHouseLoan(Long.parseLong(cell.getStringCellValue()));
								}
							}	
								//第二十七列<房产地址>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									person.setHouseEstateAddress(cell.getStringCellValue());
								}else{
									person.setHouseEstateAddress("");
								}
							}
								//第二十八列<月平均收入>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									person.setIncomePerMonth(new BigDecimal(cell.getStringCellValue()));
								}
							}
								//第二十九列<企业全称>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									companyImp.setName(cell.getStringCellValue());
								}else{
									companyImp.setName("");
								}
							}
								//第三十列<企业地址>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									companyImp.setAddress(cell.getStringCellValue());								
								}else{
									companyImp.setAddress("");
								}
							}	//第三十一列<企业邮编>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									companyImp.setZipCode(cell.getStringCellValue());
								}else{
									companyImp.setZipCode("");
								}
							}	//第三十二列<企业电话>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									companyImp.setPhone(cell.getStringCellValue());
								}else{
									companyImp.setPhone("");
								}
							}	//第三十三列<企业类型>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									companyImp.setCategory(cell.getStringCellValue());
								}else{
									companyImp.setCategory("");
								}
							}
								//第三十四列<工作证明人工作部门>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									person.setWorkThatDept(cell.getStringCellValue());
								}else{
									person.setWorkThatDept("");
								}
							}
								//第三十五列<工作证明人职务>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									person.setWorkThatPosition(cell.getStringCellValue());
								}else{
									person.setWorkThatPosition("");
								}
							}
								//第三十六列<工作证明人电话>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									person.setWorkThatTell(cell.getStringCellValue());
								}else{
									person.setWorkThatTell("");
								}
							}
								//第三十七列<客户类型>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									person.setPersonType(Integer.parseInt(cell.getStringCellValue()));
								}
							}
								//第三十八列<单位性质>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									person.setCompanyType(Integer.parseInt(cell.getStringCellValue()));
								}
							}
								//第三十九列<职业类型>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									person.setProfessionType(cell.getStringCellValue());
								}else{
									person.setProfessionType("");
								}
							}
								//四十列<可接受最高还款额>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									person.setMaxRepayAmount(new BigDecimal(cell.getStringCellValue()));
								}
							}
								//第四十一列<子女在读学校>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									person.setChildrenSchool(cell.getStringCellValue());
								}else{
									person.setChildrenSchool("");
								}
							}
								//第四十二列<私营企业类型>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									person.setPrivateEnterpriseType(cell.getStringCellValue());
								}else{
									person.setPrivateEnterpriseType("");
								}
							}
								//第四十三列<公司成立时间>	
							else if(c==i++){
								if (0 == cell.getCellType()) {
									//判断是否为日期类型
									if(HSSFDateUtil.isCellDateFormatted(cell)){
									//用于转化为日期格式
										Date d = cell.getDateCellValue();
										person.setFoundedDate(d);
										}
								}
							}
								//第四十四列<经营场所>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									person.setBusinessPlace(Integer.parseInt(cell.getStringCellValue()));
								}
							}
								//第四十五列<员工人数>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									person.setTotalEmployees(Long.parseLong(cell.getStringCellValue()));
								}
							}
								//第四十六列<占股比例>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									person.setRatioOfInvestments(Integer.parseInt(cell.getStringCellValue()));
								}
							}
								//第四十七列<每月净利润额>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									person.setMonthOfProfit(new BigDecimal(cell.getStringCellValue()));
								}
							}
								//第四十八列<个人负债余额>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									person.setPersonDebtBalance(Long.parseLong(cell.getStringCellValue()));
								}
							}
								//第四十九列<月均还款额>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									person.setMonthRepayAmount(Long.parseLong(cell.getStringCellValue()));
								}
							}
								//第五十列<与谁同住>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									person.setLivingWith(cell.getStringCellValue());
								}else{
									person.setLivingWith("");
								}
							}
								//第五十一列<收入来源>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									person.setIncomeSource(cell.getStringCellValue());
								}else{
									person.setIncomeSource("");
								}
							}
						}
					}	
					person.setCompanyImp(companyImp);
					personList.add(person);
					} 
				}
			} catch (Exception e) {
				e.printStackTrace();  
			}
			return personList;
		}	
		
		//获取借款信息List
		public 	List<Loan>  getLoanList(Workbook wb ,MultipartFile Mfile) {
			List<Loan> loanList=new ArrayList<Loan>();
			try {
				Sheet sheet=wb.getSheetAt(2);
				//得到Excel的行数
				this.totalRows=sheet.getPhysicalNumberOfRows();
				for(int r=1;r<totalRows;r++)
				{
					Row row = sheet.getRow(r);
					if (row == null) continue;
				 	if(isBlankRow(row)) continue;
					if(null != row && row.getFirstCellNum() >-1){
					int rowNum=row.getLastCellNum();		
					Loan loan=new Loan();
					BankAccount grantAccount=new BankAccount();
					//循环Excel的列
					for(int c = 0; c <rowNum; c++)
					{ 
						String count=null;
						Cell cell = row.getCell(c); 
						if (null != cell){
							int i=0;
						
								//第一列<客户身份证>
							if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									Person person=new Person();
									person.setIdnum(cell.getStringCellValue());
									loan.setPerson(person);
								}
								//第二列<风险金>	
							}else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									loan.setRisk(new BigDecimal(cell.getStringCellValue()));
								}
								//第三列<合同金额>		
							}
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									loan.setPactMoney(new BigDecimal(cell.getStringCellValue()));
								}
									
							}
							//第四列<放款时间>	
							else if(c==i++){
								if (0 == cell.getCellType()) {
									//判断是否为日期类型
									if(HSSFDateUtil.isCellDateFormatted(cell)){
									//用于转化为日期格式
										Date d = cell.getDateCellValue();
										loan.setGrantDate(d);
										}
								}else{
									cell.setCellType(Cell.CELL_TYPE_STRING);
									if(!StringUtil.isEmpty(cell.getStringCellValue())){
										loan.setGrantDate(strToDateTime(cell.getStringCellValue()));
									}
								}
										
							}
							//第五列<申请期限>
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									loan.setRequestTime(Long.parseLong(cell.getStringCellValue()));
								}
								//第六列<审批期限>
							}
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									loan.setAuditTime(Integer.parseInt(cell.getStringCellValue()));
								}
								//第七列<销售团队全称>		
							}else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									BaseArea baseArea=new BaseArea();
									baseArea.setFullName(cell.getStringCellValue());
									loan.setSalesTeam(baseArea);
								}
								//第八列<剩余本金>		
							}else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									loan.setResidualPactMoney(new BigDecimal(cell.getStringCellValue()));
								}
								//第九列<实际月利率>		
							}else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									loan.setMonthRate(new BigDecimal(cell.getStringCellValue()));
								}
								//第十列<年利率>		
							}else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									
								}
								//第十一列<合同编号>		
							}
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									loan.setContractNo(cell.getStringCellValue());
								}
							}
								//第十二列<机构户名>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									grantAccount.setAccountName(cell.getStringCellValue());
								}
							}
								//第十三列<机构放款卡类型>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									if(cell.getStringCellValue().equals("对公账户")){
									grantAccount.setCardType(1);
									}else if(cell.getStringCellValue().equals("对私账户")){
										grantAccount.setCardType(2);
									}
								}
							}
								//第十四列<当前期数>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									loan.setCurrNum(Integer.parseInt(cell.getStringCellValue()));
								}
							}
								//第十五列<放款金额>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									loan.setGrantMoney(new BigDecimal(cell.getStringCellValue()));
								}
							}		
								//第十六列<申请日期>	
							else if(c==i++){
								if (0 == cell.getCellType()) {
									//判断是否为日期类型
									if(HSSFDateUtil.isCellDateFormatted(cell)){
									//用于转化为日期格式
										Date d = cell.getDateCellValue();
										loan.setRequestDate(d);
										}
								}else{
									cell.setCellType(Cell.CELL_TYPE_STRING);
									if(!StringUtil.isEmpty(cell.getStringCellValue())){
										loan.setRequestDate(strToDateTime(cell.getStringCellValue()));
									}
								}
							}
								//第十七列<签约时间>	
							else if(c==i++){
								if (0 == cell.getCellType()) {
									//判断是否为日期类型
									if(HSSFDateUtil.isCellDateFormatted(cell)){
									//用于转化为日期格式
										Date d = cell.getDateCellValue();
										loan.setSignDate(d);
										}
								}else
								{
									cell.setCellType(Cell.CELL_TYPE_STRING);
									if(!StringUtil.isEmpty(cell.getStringCellValue())){
										loan.setSignDate(strToDateTime(cell.getStringCellValue()));
									}
								}
							}	
								//第十八列<审批日期>	
							else if(c==i++){
								if (0 == cell.getCellType()) {
									//判断是否为日期类型
									if(HSSFDateUtil.isCellDateFormatted(cell)){
									//用于转化为日期格式
										Date d = cell.getDateCellValue();
										loan.setAuditDate(d);
										}
								}else{
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									loan.setAuditDate(strToDateTime(cell.getStringCellValue()));
								}
								}
							}
								//第十九列<审批金额>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									loan.setAuditMoney(new BigDecimal(cell.getStringCellValue()));
								}
							}
								//第二十列<申请金额>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									loan.setRequestMoney(new BigDecimal(cell.getStringCellValue()));
								}
							}
								//第二十一列<约定还款日>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									loan.setReturnDate(Integer.parseInt(cell.getStringCellValue()));
								}
							}			
								//第二十二列<首期还款日>	
							else if(c==i++){
								if (0 == cell.getCellType()) {
									//判断是否为日期类型
									if(HSSFDateUtil.isCellDateFormatted(cell)){
									//用于转化为日期格式
										Date d = cell.getDateCellValue();
										loan.setStartRepayDate(d);
										}
								}else{
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									loan.setStartRepayDate(strToDateTime(cell.getStringCellValue()));
								}
								}
							}	
								//第二十三列<结束还款日>	
							else if(c==i++){
								if (0 == cell.getCellType()) {
									//判断是否为日期类型
									if(HSSFDateUtil.isCellDateFormatted(cell)){
									//用于转化为日期格式
										Date d = cell.getDateCellValue();
										loan.setEndRepayDate(d);
										}
								}else{
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									loan.setEndRepayDate(strToDateTime(cell.getStringCellValue()));
								}
								}
							}
//								//第二十四列<放款时间>	
//							else if(c==i++){
//								cell.setCellType(Cell.CELL_TYPE_STRING);
//								if(!StringUtil.isEmpty(cell.getStringCellValue())){
//									loan.setGrantDate(strToDateTime(cell.getStringCellValue()));
//								}
//							}	
								//第二十五列<客户来源>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									loan.setCustomerSource(cell.getStringCellValue());
								}
							}	
								//第二十六列<客服工号>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									SysUser service=new SysUser();
									if(cell.getStringCellValue().length()>=8){
									service.setLoginName(cell.getStringCellValue().substring(0, 8));
									}else{
										service.setLoginName(cell.getStringCellValue());
									}
									loan.setService(service);
								}
							}	
								//第二十八列<初审核人员工号>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									SysUser assessor=new SysUser();
									if(cell.getStringCellValue().length()>=8){
										assessor.setLoginName(cell.getStringCellValue().substring(0, 8));
									}else{
										assessor.setLoginName(cell.getStringCellValue());
									}
									loan.setAssessor(assessor);
								}
							}
								//第二十七列<客户经理工号>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									SysUser crm=new SysUser();
									if(cell.getStringCellValue().length()>=8){
										crm.setLoginName(cell.getStringCellValue().substring(0, 8));
									}else{
										crm.setLoginName(cell.getStringCellValue());
									}
									loan.setCrm(crm);
								}
							}
								//第二十九列<所属网点>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									BaseArea baseArea=new BaseArea();
									baseArea.setFullName(cell.getStringCellValue());
									loan.setSalesDept(baseArea);
									
								}
							}
								//第三十列<贷款用途>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									loan.setPurpose(cell.getStringCellValue());
								}
							}
								//第三十一列<状态>待获取正式文件后做处理	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									if(cell.getStringCellValue().equals("正常")){
									loan.setStatus(EnumConstants.LoanStatus.正常.getValue());
									}else if(cell.getStringCellValue().equals("拒绝")){
										loan.setStatus(EnumConstants.LoanStatus.终审拒绝.getValue());
									}else if(cell.getStringCellValue().equals("结清")){
										loan.setStatus(EnumConstants.LoanStatus.结清.getValue());
									}else if(cell.getStringCellValue().equals("通过")){
										loan.setStatus(EnumConstants.LoanStatus.合同签订.getValue());
									}else if(cell.getStringCellValue().equals("退回")){
										loan.setStatus(EnumConstants.LoanStatus.终审退回门店.getValue());
									}else if(cell.getStringCellValue().equals("逾期")){
										loan.setStatus(EnumConstants.LoanStatus.逾期.getValue());
									}else if(cell.getStringCellValue().equals("预结清")){
										loan.setStatus(EnumConstants.LoanStatus.预结清.getValue());
									}else if(cell.getStringCellValue().equals("取消")){
										loan.setStatus(EnumConstants.LoanStatus.取消.getValue());
									}else if(cell.getStringCellValue().equals("申请")){
										loan.setStatus(EnumConstants.LoanStatus.新建.getValue());
									}
								}
							}
								//第三十二列<备注>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									loan.setRemark(cell.getStringCellValue());
								}
							}
//								//第三十三列<借款申请提交时间>	
//							else if(c==i++){
//								cell.setCellType(Cell.CELL_TYPE_STRING);
//								if(!StringUtil.isEmpty(cell.getStringCellValue())){
//									loan.setSubmitDate(strToDateTime(cell.getStringCellValue()));
//								}
//							}
								//第三十四列<合同来源>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									if(cell.getStringCellValue().equals("证大P2P")){
									loan.setContractSrc(EnumConstants.ContractSrc.P2P.getValue());
									}else if(cell.getStringCellValue().equals("证大爱特")){
										loan.setContractSrc(EnumConstants.ContractSrc.AITE.getValue());
									}
								}
							}
								//第三十五列<合同确认时间>	
							else if(c==i++){
								if (0 == cell.getCellType()) {
									//判断是否为日期类型
									if(HSSFDateUtil.isCellDateFormatted(cell)){
									//用于转化为日期格式
										Date d = cell.getDateCellValue();
										loan.setContractConfirmDate(d);
										}
								}else{
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									loan.setContractConfirmDate(strToDateTime(cell.getStringCellValue()));
								}
								}
							}
								//第三十五列<合同退回时间>	
							else if(c==i++){
								if (0 == cell.getCellType()) {
									//判断是否为日期类型
									if(HSSFDateUtil.isCellDateFormatted(cell)){
									//用于转化为日期格式
										Date d = cell.getDateCellValue();
										loan.setContractBackDate(d);
										}
								}else{
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									loan.setContractBackDate(strToDateTime(cell.getStringCellValue()));
								}
								}
							}
								//第三十五列<合同生成时间>	
							else if(c==i++){
								if (0 == cell.getCellType()) {
									//判断是否为日期类型
									if(HSSFDateUtil.isCellDateFormatted(cell)){
									//用于转化为日期格式
										Date d = cell.getDateCellValue();
										loan.setContractCreatedTime(d);
										}
								}else{
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									loan.setContractCreatedTime(strToDateTime(cell.getStringCellValue()));
								}
								}
							}
								//第三十六列<财务审核时间>	
							else if(c==i++){
								if (0 == cell.getCellType()) {
									//判断是否为日期类型
									if(HSSFDateUtil.isCellDateFormatted(cell)){
									//用于转化为日期格式
										Date d = cell.getDateCellValue();
										loan.setFinanceAuditTime(d);
										}
								}else{
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									loan.setFinanceAuditTime(strToDateTime(cell.getStringCellValue()));
								}
								}
							}
							
								//第三十七列<财务退回时间>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									
								}
							}
								//第三十八列<财务放款退回时间>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									
								}
							}
								//第三十九列<评估费>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									loan.setAssessment(new BigDecimal(cell.getStringCellValue()));
								}
							}
								//第四十列<咨询费>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									loan.setConsult(new BigDecimal(cell.getStringCellValue()));
								}
							}
								//第四十一列<乙方管理费>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									loan.setbManage(new BigDecimal(cell.getStringCellValue()));
								}
							}
								//第四十二列<丙方管理费>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									loan.setcManage(new BigDecimal(cell.getStringCellValue()));
								}
							}
								//第四十三列<剩余期限>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									loan.setResidualTime(Integer.parseInt(cell.getStringCellValue()));
								}
							}
								//第四十四列<管理客服工号>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									SysUser manageService=new SysUser();
									if(cell.getStringCellValue().length()>=8){
									manageService.setLoginName(cell.getStringCellValue().substring(0, 8));
									}else{
										manageService.setLoginName(cell.getStringCellValue());
									}
									loan.setManageService(manageService);
								}
							}
								//第四十五列<业务主管工号>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									SysUser bizDirector=new SysUser();
									if(cell.getStringCellValue().length()>=8){
										bizDirector.setLoginName(cell.getStringCellValue().substring(0, 8));
									}else{
										bizDirector.setLoginName(cell.getStringCellValue());
									}
									loan.setBizDirector(bizDirector);
								}
							}
								//第四十六列<还款来源>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									loan.setRepaymentMethod(Integer.parseInt(cell.getStringCellValue()));
								}
							}
								//第四十七列<合作教育机构代码>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									Organ organ=new Organ();
									organ.setCode(cell.getStringCellValue());
									loan.setOrgan(organ);
//									loan.setOrganID(Long.parseLong(cell.getStringCellValue()));
								}
							}
								//第四十八列<方案代码>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									ChannelPlanCheck channelPlanCheck = new ChannelPlanCheck();
									channelPlanCheck.setName(cell.getStringCellValue());
									loan.setChannelPlanCheck(channelPlanCheck);
								}
							}
								//第四十九列<保证金>	
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
								}
							}
							//第五十列<还款类型>
							else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									if(cell.getStringCellValue().equals("0")){
										loan.setLoanType(EnumConstants.RepaymentMethod.BEFORE_LOW_AFTER_HIGH_INTEREST.getValue());
									}else if(cell.getStringCellValue().equals("1")){
										loan.setLoanType(EnumConstants.RepaymentMethod.AVERAGE_CAPITAL_PLUS_INTEREST.getValue());
									}
								}
							}
						
						}
					}
					loan.setGrantAccount(grantAccount);	
					loanList.add(loan);
					} 
				}
			} catch (Exception e) {
				e.printStackTrace();  
			}
			return loanList;
		}		
		
		
		//获取联系人信息List
		public 	List<Contacter>  getContacterList(Workbook wb ,MultipartFile Mfile) {
			List<Contacter> contacterList=new ArrayList<Contacter>();
			try {
				Sheet sheet=wb.getSheetAt(3);
				//得到Excel的行数
				this.totalRows=sheet.getPhysicalNumberOfRows();
				for(int r=1;r<totalRows;r++)
				{
					Row row = sheet.getRow(r);
					if (row == null) continue;
				 	if(isBlankRow(row)) continue;
					if(null != row && row.getFirstCellNum() >-1){
					int rowNum=row.getLastCellNum();		
					Contacter contacter=new Contacter();
					Loan loan=new Loan();
					//循环Excel的列
					for(int c = 0; c <rowNum; c++)
					{ 
						String count=null;
						Cell cell = row.getCell(c); 
						if (null != cell){
							
							int i=0;
								//第一列<姓名>
							if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									contacter.setName(cell.getStringCellValue());
								}
								//第二列<关系>	
							}else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									contacter.setRelationship(cell.getStringCellValue());
								}
								//第三列<手机号码>		
							}else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									contacter.setMobilePhone(cell.getStringCellValue());
								}
								//第四列<法人代表身份证号>		
							}else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									contacter.setHomePhone(cell.getStringCellValue());
								}
								//第五列<工作单位>		
							}else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									contacter.setWorkUnit(cell.getStringCellValue());
								}
								//第六列<知晓贷款>		
							}else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									if(cell.getStringCellValue().equals("f")){
										
										contacter.setHadKnown(EnumConstants.YesOrNo.NO.getValue());
									}else{
										contacter.setHadKnown(EnumConstants.YesOrNo.YES.getValue());
									}
								}
								//第七列<借款人>		
							}else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									SysUser borrower=new SysUser();
									borrower.setName(cell.getStringCellValue());
									contacter.setBorrower(borrower);
								}
								//第八列<地址>		
							}else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									contacter.setAddress(cell.getStringCellValue());
								}
								//第九列<借款编号>		
							}else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									loan.setContractNo(cell.getStringCellValue());
								}
								//第十列<身份证>		
							}else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									Person person=new Person();
									person.setIdnum(cell.getStringCellValue());;
									loan.setPerson(person);
								}
								//第十一列<职务>		
							}else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									contacter.setTitle(cell.getStringCellValue());
								}
							}
							
						}
					}	
					contacter.setLoan(loan);
					contacterList.add(contacter);
					} 
				}
			} catch (Exception e) {
				e.printStackTrace();  
			}
			return contacterList;
		}
		
			
			//获取信贷历史List
		public 	List<CreditHistory>  getCreditHistoryList(Workbook wb ,MultipartFile Mfile) {
			List<CreditHistory> creditHistoryList=new ArrayList<CreditHistory>();
			try {
				Sheet sheet=wb.getSheetAt(4);
				//得到Excel的行数
				this.totalRows=sheet.getPhysicalNumberOfRows();
				for(int r=1;r<totalRows;r++)
				{
					Row row = sheet.getRow(r);
					if (row == null) continue;
				 	if(isBlankRow(row)) continue;
					if(null != row && row.getFirstCellNum() >-1){
					int rowNum=row.getLastCellNum();		
					CreditHistory creditHistory=new CreditHistory();
					Loan loan=new Loan();
					//循环Excel的列
					for(int c = 0; c <rowNum; c++)
					{ 
						String count=null;
						Cell cell = row.getCell(c); 
						if (null != cell){
							
							int i=0;
								//第一列<借款编号>
							if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									loan.setContractNo(cell.getStringCellValue());
								}
								//第二列<是否有信用卡>	
							}else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									creditHistory.setHasCreditCard(Long.parseLong(cell.getStringCellValue()));
								}
								//第三列<信用卡总数>		
							}else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									creditHistory.setCardNum(Integer.parseInt(cell.getStringCellValue()));
								}
								//第四列<总额度>		
							}else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									creditHistory.setTotalAmount(new BigDecimal(cell.getStringCellValue()));
								}
								//第五列<已透支>		
							}else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									creditHistory.setOverdrawAmount(new BigDecimal(cell.getStringCellValue()));
								}
								//第六列<贷款渠道>		
							}else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									creditHistory.setHistoryLoanChannel(cell.getStringCellValue());
								}
								//第七列<金额>		
							}else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									creditHistory.setHistoryAmount(new BigDecimal(cell.getStringCellValue()));
								}
								//第八列<放款日期>		
							}else if(c==i++){
								if (0 == cell.getCellType()) {
									//判断是否为日期类型
									if(HSSFDateUtil.isCellDateFormatted(cell)){
									//用于转化为日期格式
										Date d = cell.getDateCellValue();
										creditHistory.setHistoryGrantDate(d);
										}
								}else{
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									creditHistory.setHistoryGrantDate(strToDateTime(cell.getStringCellValue()));
								}
								}
								//第九列<月还款额>		
							}else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									creditHistory.setHistoryMonPay(new BigDecimal(cell.getStringCellValue()));
								}
								//第十列<贷款逾期信息>		
							}else if(c==i++){
								cell.setCellType(Cell.CELL_TYPE_STRING);
								if(!StringUtil.isEmpty(cell.getStringCellValue())){
									creditHistory.setHistoryOverdue(cell.getStringCellValue());
								}
							}
							
						}
					}	
					creditHistory.setLoan(loan);
						if(creditHistory.getLoan().getContractNo()!=null){
							
							creditHistoryList.add(creditHistory);
						}
					} 
				}
			} catch (Exception e) {
				e.printStackTrace();  
			}
			return creditHistoryList;
		}
			
		//获取合同信息List
	public 	List<Contract>  getContractList(Workbook wb ,MultipartFile Mfile) {
		List<Contract> contractList=new ArrayList<Contract>();
		try {
			Sheet sheet=wb.getSheetAt(6);
			//得到Excel的行数
			this.totalRows=sheet.getPhysicalNumberOfRows();
			for(int r=1;r<totalRows;r++)
			{
				Row row = sheet.getRow(r);
				if (row == null) continue;
			 	if(isBlankRow(row)) continue;
				if(null != row && row.getFirstCellNum() >-1){
				int rowNum=row.getLastCellNum();		
				Contract contract=new Contract();
				Loan loan=new Loan();
				//循环Excel的列
				for(int c = 0; c <rowNum; c++)
				{ 
					String count=null;
					Cell cell = row.getCell(c); 
					if (null != cell){
						
						int i=0;
							//第一列<借款编号>
						if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								loan.setContractNo(cell.getStringCellValue());
							}
							//第二列<合同编号>	
						}else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								contract.setContractNo(cell.getStringCellValue());
							}
							//第三列<合同签订日期>		
						}else if(c==i++){
							if (0 == cell.getCellType()) {
								//判断是否为日期类型
								if(HSSFDateUtil.isCellDateFormatted(cell)){
								//用于转化为日期格式
									Date d = cell.getDateCellValue();
									contract.setSignDate(d);
									}
							}else{
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								contract.setSignDate(strToDateTime(cell.getStringCellValue()));
							}
							}
							//第四列<合同类型>		
						}else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								contract.setType(Integer.parseInt(cell.getStringCellValue()));
							}
							//第五列<合同名称>		
						}else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								contract.setContractName(cell.getStringCellValue());
							}
							//第六列<市名>		
						}else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								contract.setCityName(cell.getStringCellValue());
							}
							//第七列<区名>		
						}else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								contract.setAreaName(cell.getStringCellValue());
							}
							//第八列<借款人名字>		
						}else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								contract.setPersonName(cell.getStringCellValue());
							}
							//第九列<身份证号码>		
						}else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								contract.setIdNum(cell.getStringCellValue());
							}
							//第十列<住址>		
						}else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								contract.setAddress(cell.getStringCellValue());
							}
							//第十一列<邮政编码>		
						}else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								contract.setZipCode(cell.getStringCellValue());
							}
							//第十二列<电子邮箱>		
						}else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								contract.setEmail(cell.getStringCellValue());
							}
							//第十三列<支付金额>		
						}else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								contract.setPayAmount(new BigDecimal(cell.getStringCellValue()));
							}
							//第十四列<借款详细用途>		
						}else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								contract.setPurpose(cell.getStringCellValue());
							}
							//第十五列<借款本金金额>		
						}else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								contract.setPactMoney(new BigDecimal(cell.getStringCellValue()));
							}
							//第十六列<月偿还本息数额>		
						}else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								contract.setMonthInterestAmount(new BigDecimal(cell.getStringCellValue()));
							}
						}
						//第十七列<分期期数>
						else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								contract.setTimes(Integer.parseInt(cell.getStringCellValue()));
							}
						}
						//第十七列<还款开始日期>		
						else if(c==i++){
							if (0 == cell.getCellType()) {
								//判断是否为日期类型
								if(HSSFDateUtil.isCellDateFormatted(cell)){
								//用于转化为日期格式
									Date d = cell.getDateCellValue();
									contract.setStartRepayDate(d);
									}
							}else{
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								contract.setStartRepayDate(strToDateTime(cell.getStringCellValue()));
							}
							}
						}
						//第十七列<还款结束日期>		
						else if(c==i++){
							if (0 == cell.getCellType()) {
								//判断是否为日期类型
								if(HSSFDateUtil.isCellDateFormatted(cell)){
								//用于转化为日期格式
									Date d = cell.getDateCellValue();
									contract.setEndRepayDate(d);
									}
							}{
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								contract.setEndRepayDate(strToDateTime(cell.getStringCellValue()));
							}
							}
						}
							//第十八列<银行户名>	
						else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								contract.setBankAccountName(cell.getStringCellValue());
							}
						}
							//第十九列<银行账户>	
						else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								contract.setBankAccountNum(cell.getStringCellValue());
							}
						}
							//第二十列<开户行>	
						else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								contract.setOpeningBank(cell.getStringCellValue());
							}
						}
							//第二十一列<甲方支付评估费>	
						else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								contract.setAssessmentFees(new BigDecimal(cell.getStringCellValue()));
							}
						}
							//第二十二列<甲方支付每月管理费>	
						else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								contract.setManageFees(new BigDecimal(cell.getStringCellValue()));
							}
						}
							
							//第二十三列<甲方支付丙方管理费>	
						else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								contract.setTtpManageFees(new BigDecimal(cell.getStringCellValue()));
							}
						}
							//第二十四列<联系方式>	
						else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								contract.setContact(cell.getStringCellValue());
							}
						}
							//第二十五列<月还人民币>	
						else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								contract.setMonthAmount(new BigDecimal(cell.getStringCellValue()));
							}
						}
							//第二十五列<自然担保人姓名>	
						else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								contract.setGuaranteeName(cell.getStringCellValue());
							}
						}
							//第二十六列<借款协议编号>	
						else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								contract.setLoanAgreeNum(cell.getStringCellValue());
							}
						}	
							//第二十七列<还款日>
						else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								contract.setRepayDate(cell.getStringCellValue());
							}
						}
						
					}
				}	
				contract.setLoan(loan);
				contractList.add(contract);
				} 
			}
		} catch (Exception e) {
			e.printStackTrace();  
		}
		return contractList;
	}		
		
		
		//获取还款信息List
	public 	List<RepaymentPlan>  getRepaymentPlanList(Workbook wb ,MultipartFile Mfile) {
		List<RepaymentPlan> repaymentPlanList=new ArrayList<RepaymentPlan>();
		try {
			Sheet sheet=wb.getSheetAt(7);
			//得到Excel的行数
			this.totalRows=sheet.getPhysicalNumberOfRows();
			for(int r=1;r<totalRows;r++)
			{
				Row row = sheet.getRow(r);
				if (row == null) continue;
			 	if(isBlankRow(row)) continue;
				if(null != row && row.getFirstCellNum() >-1){
				int rowNum=row.getLastCellNum();		
				RepaymentPlan repaymentPlan=new RepaymentPlan();
				Loan loan=new Loan();
				//循环Excel的列
				for(int c = 0; c <rowNum; c++)
				{ 
					String count=null;
					Cell cell = row.getCell(c); 
					if (null != cell){
						
						int i=0;
							//第一列<借款编号>
						if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								loan.setContractNo(cell.getStringCellValue());
							}
							//第二列<当前还款状态>	
						}else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								if(cell.getStringCellValue().equals("未还款")){
									repaymentPlan.setStatus(EnumConstants.RepaymentPlanState.NOT_REPAYMENT.getValue());
								}else if(cell.getStringCellValue().equals("结清")){
									repaymentPlan.setStatus(EnumConstants.RepaymentPlanState.SETTLE.getValue());
								}else if(cell.getStringCellValue().equals("不足本金")){
									repaymentPlan.setStatus(EnumConstants.RepaymentPlanState.NOT_AMOUNT.getValue());
								}else if(cell.getStringCellValue().equals("不足罚息")){
									repaymentPlan.setStatus(EnumConstants.RepaymentPlanState.NOT_INTEREST.getValue());
								}else if(cell.getStringCellValue().equals("不足利息")){
									repaymentPlan.setStatus(EnumConstants.RepaymentPlanState.NOT_INTEREST.getValue());
								}								
							}
							//第三列<还款金额>		
						}else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								repaymentPlan.setRepayAmount(new BigDecimal(cell.getStringCellValue()));
							}
							//第四列<当前期数>		
						}else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								repaymentPlan.setCurNum(Integer.parseInt(cell.getStringCellValue()));
							}
							//第五列<还款本金>		
						}
//						else if(c==i++){
//							cell.setCellType(Cell.CELL_TYPE_STRING);
//							if(!StringUtil.isEmpty(cell.getStringCellValue())){
//								repaymentPlan.setPrincipalAmt(new BigDecimal(cell.getStringCellValue()));
//							}
//								
//						}
						//第六列<贷款余额>	
						else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								repaymentPlan.setOutstanding(new BigDecimal(cell.getStringCellValue()));
							}
						}
						//第七列<每月应还减去管理费>
//						else if(c==i++){
//							cell.setCellType(Cell.CELL_TYPE_STRING);
//							if(!StringUtil.isEmpty(cell.getStringCellValue())){
//								repaymentPlan.setPrincipalAmt(new BigDecimal(cell.getStringCellValue()));
//							}
//								
//						}
						//第八列<剩余本金>	
						else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								repaymentPlan.setRemainingPrincipal(new BigDecimal(cell.getStringCellValue()));
							}
							//第九列<违约金>		
						}else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								repaymentPlan.setPenalty(new BigDecimal(cell.getStringCellValue()));
							}
							//第十列<风险金>		
						}else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								repaymentPlan.setRisk(new BigDecimal(cell.getStringCellValue()));
							}
							//第十一列<当期一次性还款金额>		
						}else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								repaymentPlan.setOneTimeRepaymentAmount(new BigDecimal(cell.getStringCellValue()));
							}
							//第十二列<结清日期>		
						}else if(c==i++){
							if (0 == cell.getCellType()) {
								//判断是否为日期类型
								if(HSSFDateUtil.isCellDateFormatted(cell)){
								//用于转化为日期格式
									Date d = cell.getDateCellValue();
									repaymentPlan.setFactReturnDate(d);
									}
							}else{
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								repaymentPlan.setFactReturnDate(strToDateTime(cell.getStringCellValue()));
							}
							}
							//第十三列<剩余欠款，用于记录不足额部分>		
						}else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								repaymentPlan.setDeficit(new BigDecimal(cell.getStringCellValue()));
							}
							//第十四列<乙方管理费>		
						}else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								repaymentPlan.setManagePart0Fee(new BigDecimal(cell.getStringCellValue()));
							}
							//第十五列<丙方管理费>		
						}else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								repaymentPlan.setManagePart1Fee(new BigDecimal(cell.getStringCellValue()));
							}
							//第十六列<咨询费>		
						}else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								repaymentPlan.setReferRate(new BigDecimal(cell.getStringCellValue()));
							}
							//第十七列<评估费>		
						}else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								repaymentPlan.setEvalRate(new BigDecimal(cell.getStringCellValue()));
							}
						}
							//第十八列<还款利息>	
						else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								repaymentPlan.setInterestAmt(new BigDecimal(cell.getStringCellValue()));
							}
						}
							//第十九列<还款日期>	
						else if(c==i++){
							if (0 == cell.getCellType()) {
								//判断是否为日期类型
								if(HSSFDateUtil.isCellDateFormatted(cell)){
								//用于转化为日期格式
									Date d = cell.getDateCellValue();
									repaymentPlan.setRepayDay(d);
									}
							}else{
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								repaymentPlan.setRepayDay(strToDateTime(cell.getStringCellValue()));
							}
							}
						}
//							//第二十列<当期剩余乙方管理费>	
//						else if(c==i++){
//							cell.setCellType(Cell.CELL_TYPE_STRING);
//							if(!StringUtil.isEmpty(cell.getStringCellValue())){
//								repaymentPlan.setCurRemainingManagePart0Fee(new BigDecimal(cell.getStringCellValue()));
//							}
//						}
//							//第二十一列<当期剩余丙方管理费>	
//						else if(c==i++){
//							cell.setCellType(Cell.CELL_TYPE_STRING);
//							if(!StringUtil.isEmpty(cell.getStringCellValue())){
//								repaymentPlan.setCurRemainingManagePart1Fee(new BigDecimal(cell.getStringCellValue()));
//							}
//						}
//							//第二十二列<当期剩余咨询费>	
//						else if(c==i++){
//							cell.setCellType(Cell.CELL_TYPE_STRING);
//							if(!StringUtil.isEmpty(cell.getStringCellValue())){
//								repaymentPlan.setCurRemainingReferRate(new BigDecimal(cell.getStringCellValue()));
//							}
//						}
//							
//							//第二十三列<当期剩余评估费>	
//						else if(c==i++){
//							cell.setCellType(Cell.CELL_TYPE_STRING);
//							if(!StringUtil.isEmpty(cell.getStringCellValue())){
//								repaymentPlan.setCurRemainingEvalRate(new BigDecimal(cell.getStringCellValue()));
//							}
//						}
//							//第二十四列<当期剩余风险金>	
//						else if(c==i++){
//							cell.setCellType(Cell.CELL_TYPE_STRING);
//							if(!StringUtil.isEmpty(cell.getStringCellValue())){
//								repaymentPlan.setCurRemainingRisk(new BigDecimal(cell.getStringCellValue()));
//							}
//						}
//							//第二十五列<当期剩余利息>	
//						else if(c==i++){
//							cell.setCellType(Cell.CELL_TYPE_STRING);
//							if(!StringUtil.isEmpty(cell.getStringCellValue())){
//								repaymentPlan.setCurRemainingInterestAmt(new BigDecimal(cell.getStringCellValue()));
//							}
//						}
//							//第二十五列<当期剩余本金>	
//						else if(c==i++){
//							cell.setCellType(Cell.CELL_TYPE_STRING);
//							if(!StringUtil.isEmpty(cell.getStringCellValue())){
//								repaymentPlan.setCurRemainingPrincipal(new BigDecimal(cell.getStringCellValue()));
//							}
//						}
							//第二十六列<罚息起始时间>	
						else if(c==i++){
							if (0 == cell.getCellType()) {
								//判断是否为日期类型
								if(HSSFDateUtil.isCellDateFormatted(cell)){
								//用于转化为日期格式
									Date d = cell.getDateCellValue();
									repaymentPlan.setPenaltyDate(d);
									}
							}else{
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								repaymentPlan.setPenaltyDate(strToDateTime(cell.getStringCellValue()));
							}
							}
						}	
						//第二十五列<当期剩余利息>	
						else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								repaymentPlan.setCurRemainingInterestAmt(new BigDecimal(cell.getStringCellValue()));
							}
						}
							//第二十五列<当期剩余本金>	
						else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								repaymentPlan.setCurRemainingPrincipal(new BigDecimal(cell.getStringCellValue()));
							}
						}
							//第二十七列<退费>
//						else if(c==i++){
//							cell.setCellType(Cell.CELL_TYPE_STRING);
//							if(!StringUtil.isEmpty(cell.getStringCellValue())){
//								repaymentPlan.setRefund(new BigDecimal(cell.getStringCellValue()));
//							}
//						}	
						
					}
				}	
				repaymentPlan.setLoan(loan);
				repaymentPlanList.add(repaymentPlan);
				} 
			}
		} catch (Exception e) {
			e.printStackTrace();  
		}
		return repaymentPlanList;
	}		
		
	
	
	
	//获取客户银行信息List
	public 	List<PersonBank>  getPersonBankList(Workbook wb ,MultipartFile Mfile) {
		List<PersonBank> personBankList=new ArrayList<PersonBank>();
		try {
			Sheet sheet=wb.getSheetAt(8);
			//得到Excel的行数
			this.totalRows=sheet.getPhysicalNumberOfRows();
			for(int r=1;r<totalRows;r++)
			{
				Row row = sheet.getRow(r);
				if (row == null) continue;
				if(isBlankRow(row)) continue;
				int rowNum=row.getLastCellNum();		
				PersonBank personBank=new PersonBank();
				Loan loan=new Loan();
				//循环Excel的列
				for(int c = 0; c <rowNum; c++)
				{ 
					String count=null;
					Cell cell = row.getCell(c); 
					if (null != cell){
						
							//第一列<客户银行>
						if(c==0){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								personBank.setBankName(cell.getStringCellValue());
							}
							//第二列<客户银行支行>	
						}else if(c==1){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								personBank.setBranchName(cell.getStringCellValue());
							}
							//第三列<客户银行账户>	
						}else if(c==2){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								personBank.setAccount(cell.getStringCellValue());
							}
							//第四列<客户身份证>	
						}else if(c==3){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								personBank.setPersonIdnum(cell.getStringCellValue());
							}

							//第五列<借款编号>	
						}else if(c==4){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								loan.setContractNo(cell.getStringCellValue());
							}
						}
						
					}
				}
				if(loan.getContractNo()!=null){
					personBank.setLoan(loan);
					personBankList.add(personBank);
				}
			} 
		} catch (Exception e) {
			e.printStackTrace();  
		}
		return personBankList;

	}
	
	
	
	//获取总账分账List
	public 	List<Ledger>  getLedgerList(Workbook wb ,MultipartFile Mfile) {
		List<Ledger> ledgerList=new ArrayList<Ledger>();
		try {
			Sheet sheet=wb.getSheetAt(9);
			//得到Excel的行数
			this.totalRows=sheet.getPhysicalNumberOfRows();
			for(int r=1;r<totalRows;r++)
			{
				Row row = sheet.getRow(r);
				if (row == null) continue;
			 	if(isBlankRow(row)) continue;
				if(null != row && row.getFirstCellNum() >-1){
				int rowNum=row.getLastCellNum();		
				Ledger ledger=new Ledger();
				Loan loan=new Loan();
				//循环Excel的列
				for(int c = 0; c <rowNum; c++)
				{ 
					String count=null;
					Cell cell = row.getCell(c); 
					if (null != cell){
						int i=0;

							//第一列<借款编号>
						if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								loan.setContractNo(cell.getStringCellValue());
							}
							//第二列<现金>	
						}else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								ledger.setCash(new BigDecimal(cell.getStringCellValue()));
							}
							//第三列<应收利息>		
						}
						else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								ledger.setInterestReceivable(new BigDecimal(cell.getStringCellValue()));
							}
							//第四列<应收罚息>		
						}else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								ledger.setFineReceivable(new BigDecimal(cell.getStringCellValue()));
							}
							//第五列<其他应收款>		
						}else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								ledger.setOtherReceivable(new BigDecimal(cell.getStringCellValue()));
							}
							//第六列<还款本金>
						}
						else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								ledger.setLoanAmount(new BigDecimal(cell.getStringCellValue()));
							}
							//第七列<应付利息>		
						}else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								ledger.setInterestPayable(new BigDecimal(cell.getStringCellValue()));
							}
							//第八列<应付罚息>		
						}else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								ledger.setFinePayable(new BigDecimal(cell.getStringCellValue()));
							}
							//第九列<其他应付款>		
						}else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								ledger.setOtherPayable(new BigDecimal(cell.getStringCellValue()));
							}
							//第十列<逾期罚息收入>		
						}else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								ledger.setOverdueInterestIncome(new BigDecimal(cell.getStringCellValue()));
							}
							//第十一列<咨询费收入>		
						}
						else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								ledger.setConsultIncome(new BigDecimal(cell.getStringCellValue()));
							}
						}
							//第十二列<管理费收入>	
						else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								ledger.setManageIncome(new BigDecimal(cell.getStringCellValue()));
							}
						}
							//第十三列<评估费收入>	
						else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								ledger.setAssessmentFeeIncome(new BigDecimal(cell.getStringCellValue()));
							}
						}
							//第十四列<违约金收入>	
						else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								ledger.setPenaltyIncome(new BigDecimal(cell.getStringCellValue()));
							}
						}
							//第十五列<其他营业收入>	
						else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								ledger.setOtherIncome(new BigDecimal(cell.getStringCellValue()));
							}
						}		
							//第十六列<营业外收入>	
						else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								ledger.setNonbusinessIncome(new BigDecimal(cell.getStringCellValue()));
							}
						}
							//第十七列<利息支出>	
						else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								ledger.setInterestExpense(new BigDecimal(cell.getStringCellValue()));
							}
						}	
							//第十八列<咨询费支出>	
						else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								ledger.setConsultExpense(new BigDecimal(cell.getStringCellValue()));
							}
						}
							//第十九列<评估费支出>	
						else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								ledger.setAssessmentFeeExpense(new BigDecimal(cell.getStringCellValue()));
							}
						}
							//第二十列<管理费支出>	
						else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								ledger.setManageExpense(new BigDecimal(cell.getStringCellValue()));
							}
						}
							//第二十一列<违约金支出>	
						else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								ledger.setPenaltyExpense(new BigDecimal(cell.getStringCellValue()));
							}
						}			
							//第二十二列<其他营业支出>	
						else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								ledger.setOtherExpense(new BigDecimal(cell.getStringCellValue()));
							}
						}	
							//第二十三列<营业外支出>	
						else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								ledger.setNonbusinessExpense(new BigDecimal(cell.getStringCellValue()));
							}
						}
							//第二十四列<备注>	
						else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								ledger.setRemark(cell.getStringCellValue());
							}
						}	
							//第二十五列<逾期罚息支出>	
						else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								ledger.setOverdueInterestExpense(new BigDecimal(cell.getStringCellValue()));
							}
						}	
							//第二十六列<利息收入>	
						else if(c==i++){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								ledger.setInterestIncome(new BigDecimal(cell.getStringCellValue()));
							}
						}	
						
					}
				}	
				ledger.setLoan(loan);
				ledgerList.add(ledger);
				} 
			}
		} catch (Exception e) {
			e.printStackTrace();  
		}
		return ledgerList;
	}		
		
	//获取放还款List
	public 	List<RepayInfo>  getRepayInfoList(Workbook wb ,MultipartFile Mfile) {
		List<RepayInfo> repayInfoList=new ArrayList<RepayInfo>();
		try {
			Sheet sheet=wb.getSheetAt(10);
			//得到Excel的行数
			this.totalRows=sheet.getPhysicalNumberOfRows();
			for(int r=1;r<totalRows;r++)
			{
				Row row = sheet.getRow(r);
				if (row == null) continue;
				if(isBlankRow(row)) continue;
				int rowNum=row.getLastCellNum();		
				RepayInfo repayInfo=new RepayInfo();
				Loan loan=new Loan();
				//循环Excel的列
				for(int c = 0; c <rowNum; c++)
				{ 
					String count=null;
					Cell cell = row.getCell(c); 
					if (null != cell){
						
							//第一列<借款编号>
						if(c==0){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								loan.setContractNo(cell.getStringCellValue());
							}
							//第二列<交易时间>	
						}else if(c==1){
							if (0 == cell.getCellType()) {
								//判断是否为日期类型
								if(HSSFDateUtil.isCellDateFormatted(cell)){
								//用于转化为日期格式
									Date d = cell.getDateCellValue();
									repayInfo.setTradeTime(d);
									}
							}else{
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								repayInfo.setTradeTime(strToDateTime(cell.getStringCellValue()));
							}
							}
							//第三列<交易码>	
						}else if(c==2){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								repayInfo.setTradeCode(cell.getStringCellValue());
							}
							//第四列<交易金额>	
						}else if(c==3){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								repayInfo.setTradeAmount(new BigDecimal(cell.getStringCellValue()));
							}
							//第五列<交易流水号>	
						}else if(c==4){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								repayInfo.setTradeNo(cell.getStringCellValue());
							}
						}
							//第六列<放款员工号>
						else if(c==5){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								SysUser tellUser=new SysUser();
								if(cell.getStringCellValue().length()>=8){
									tellUser.setLoginName(cell.getStringCellValue().substring(0, 8));
								}else{
									tellUser.setLoginName(cell.getStringCellValue());
								}
								repayInfo.setTellUser(tellUser);
							}
						}
							//第七列<期数>
						else if(c==6){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								repayInfo.setTerm(Long.parseLong(cell.getStringCellValue()));
							}
						}
							//第八列<营业网点名>
						else if(c==7){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								BaseArea salesdepartment=new BaseArea();
								salesdepartment.setFullName(cell.getStringCellValue());
								repayInfo.setSalesdepartment(salesdepartment);
							}
						}
							//第九列<备注>
						else if(c==8){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								repayInfo.setRemark(cell.getStringCellValue());
							}
						}
							//第十列<交易类型>
						else if(c==9){
							cell.setCellType(Cell.CELL_TYPE_STRING);
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								if(cell.getStringCellValue().equals("现金")){
								repayInfo.setPayType(EnumConstants.TradeType.CASH.getValue());
								}else if(cell.getStringCellValue().equals("挂账")){
									repayInfo.setPayType(EnumConstants.TradeType.ON_TICK.getValue());
								}else if(cell.getStringCellValue().equals("转账")){
									repayInfo.setPayType(EnumConstants.TradeType.TRANSFER.getValue());
								}else if(cell.getStringCellValue().equals("上海银联代扣")){
									repayInfo.setPayType(EnumConstants.TradeType.TONGLIAN_WITHHOLDING.getValue());
								}else if(cell.getStringCellValue().equals("通联代扣")){
									repayInfo.setPayType(EnumConstants.TradeType.TONGLIAN_WITHHOLDING.getValue());
								}
							}
						}
							
					}
				}
				repayInfo.setLoan(loan);
				repayInfoList.add(repayInfo);
			} 
		} catch (Exception e) {
			e.printStackTrace();  
		}
		return repayInfoList;
	}
		
	public static Date strToDate(String str) throws ParseException{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");//小写的mm表示的是分钟  
		Date date=sdf.parse(str);  
		return date;
	}
	
	public static Date strToDateTime(String str) throws ParseException{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟  
		Date date=sdf.parse(str);  
//		@SuppressWarnings("deprecation")
//		Date date=new Date(str);
		return date;
	}
	
	public static Date strToDateTimeChange(String str) throws ParseException{
		SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");//小写的mm表示的是分钟  
		Date date=sdf.parse(str);  
//		@SuppressWarnings("deprecation")
//		Date date=new Date(str);
		return date;
	}

	public static boolean isExcel2003(String filePath)  {  
		return filePath.matches("^.+\\.(?i)(xls)$");  
	}  

	//@描述：是否是2007的excel，返回true是2007 
	public static boolean isExcel2007(String filePath)  {  
		return filePath.matches("^.+\\.(?i)(xlsx)$");  
	}  
	public static boolean isBlankRow(Row row ){
        if(row == null) return true;
        boolean result = true;
        for(int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++){
        	Cell cell = row.getCell(i, Row.RETURN_BLANK_AS_NULL);
            String value = "";
            if(cell != null){
                switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    value = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    value = String.valueOf((int) cell.getNumericCellValue());
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    value = String.valueOf(cell.getBooleanCellValue());
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    value = String.valueOf(cell.getCellFormula());
                    break;
                //case Cell.CELL_TYPE_BLANK:
                //    break;
                default:
                    break;
                }
                 
                if(!value.trim().equals("")){
                    result = false;
                    break;
                }
            }
        }
         
        return result;
    }

}
