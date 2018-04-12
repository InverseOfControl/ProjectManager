/**
 * 
 */
package com.ezendai.credit2.after.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ezendai.credit2.after.dao.TaskAllocationRuleDao;
import com.ezendai.credit2.after.model.LateDetails;
import com.ezendai.credit2.after.model.TaskAllocationRule;
import com.ezendai.credit2.after.service.RepayService;
import com.ezendai.credit2.after.service.TaskAllocationRuleService;
import com.ezendai.credit2.after.vo.TaskAllocationRuleVO;
import com.ezendai.credit2.audit.model.RepaymentPlan;
import com.ezendai.credit2.common.util.ExportExcel;
import com.ezendai.credit2.framework.util.DateUtil;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.framework.util.StringUtil;
import com.ezendai.credit2.framework.util.WDWUtil;

/**
 * @author 00226557
 * @version 1.0
 */
@Service
public class TaskAllocationRuleServiceImpl implements TaskAllocationRuleService {
	@Autowired
	private  TaskAllocationRuleDao taskAllocationRuleDao;
	@Autowired
	private RepayService repayService;
	//总行数
	private int totalRows = 0;  
	//总条数
	private int totalCells = 0; 
	@Override
	public Pager TaskAllocationRuleWithPG(TaskAllocationRuleVO ruleVO) {
		// TODO Auto-generated method stub
		return taskAllocationRuleDao.TaskAllocationRuleWithPG(ruleVO);
	}

	@Override
	public void insertTaskAllocationRule(TaskAllocationRule rule) {
		// TODO Auto-generated method stub
		  taskAllocationRuleDao.insertTaskAllocationRule(rule);
	}

	@Override
	public void updateTaskAllocationRule(TaskAllocationRule rule) {
		// TODO Auto-generated method stub
		  taskAllocationRuleDao.updateTaskAllocationRule(rule);
	}
		

	@Override
	public List<LateDetails> getLateDetailsList(TaskAllocationRuleVO vo) {
		// TODO Auto-generated method stub
		 Date nowDate = DateUtil.getToday();
		 try {
			vo.setStartDate(dateToStr(nowDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			List<LateDetails> list=	taskAllocationRuleDao.getLateDetailsList(vo);
			List<TaskAllocationRule> ruleList=	taskAllocationRuleDao.getTaskAllocationRuleBySalesDeptId(vo);
			List<TaskAllocationRule> userList=	taskAllocationRuleDao.getCollectorsBySalesDeptId(vo);
			List<TaskAllocationRule> effectiveList=	taskAllocationRuleDao.getAllEffectiveBySalesDeptId(vo);
			// 分配平均数
			int aveNum=0;
			if(userList.size()>0){
				 aveNum=list.size()/userList.size();
			}
			
		    // 客服分配的下标
			int userIndex=0;
			// 单人分配次数
			int userAllocationNum=0;
			if(list!=null ){
				if(list.size()>0){
						for (LateDetails or :list) {
							List<RepaymentPlan> repaymentPlanList = repayService.getAllInterestOrLoan( nowDate, or.getLoanId());
							int overdueFineDay = repayService.getOverdueFineDay(repaymentPlanList, nowDate);
							or.setOverdue(overdueFineDay);
							if (or.getPenaltyDate()==null) {
								//设置罚息起算日
								or.setPenaltyDate(or.getOverdueStartDate());
							}
							 BigDecimal fineInter = repayService.getFine(repaymentPlanList, nowDate);
							 or.setRepayInterest(fineInter);
							 Double allAmount =  or.getOverduePrincipal().doubleValue()+or.getOverdueInterestAmt().doubleValue()+or.getRepayInterest().doubleValue();
							 or.setTotalAmount(new BigDecimal(allAmount));
							 //分配
							   if(userIndex>=userList.size())continue;
							 if(effectiveList.size()== userList.size()){
								//平均分配
								 or.setCollectors(userList.get(userIndex).getUserName());
								 or.setCollectorsLoginName(userList.get(userIndex).getUserLoginName());
								 userAllocationNum++;
								 if(userAllocationNum==aveNum){
									 userIndex++;
									 userAllocationNum=0;
								 } 
								 
							 } 
						}
						//根据规则分配
						int ruleNum=0;
						int startIndex=0;
						for (TaskAllocationRule or :ruleList){
							ruleNum+=or.getNum();
							for(;startIndex<ruleNum;startIndex++){
								if(startIndex>=list.size()){break;}
								 
								list.get(startIndex).setCollectors(or.getUserName());
								list.get(startIndex).setCollectorsLoginName(or.getUserLoginName());
							}
							
						}
					 
				}
				
			}
			
			
			return list;
	}

	@Override
	public void exportExcel(String sheetName, OutputStream os,
			List<LateDetails> excelRepaymentPlan, TaskAllocationRuleVO vo)
			throws ParseException {
		if (excelRepaymentPlan.size()>0) {
			 
			List<String[]> rows = new ArrayList<String[]>();
			for (LateDetails or :excelRepaymentPlan) {
				String[] row = new String[39];
				row[0] = new String(or.getSalesDept()==null ? "":or.getSalesDept());
				row[1] = new String(or.getProductName()==null ? "":or.getProductName());
				row[2] = new String(or.getPersonName()==null ? "":or.getPersonName());
				row[3] = new String(or.getManagerCode()==null ? "":or.getManagerCode());
				row[4] = new String(or.getDirectorName()==null ? "":or.getDirectorName());
				row[5] = new String(or.getDirectorCode()==null ? "":or.getDirectorCode());
				row[6] = new String(or.getIdNum()==null ? "":or.getIdNum());
				row[7] = new String(or.getSignDate()==null ? "":dateToStr(or.getSignDate()));
				row[8] = new String(or.getReturnDate() ==null ? "": or.getReturnDate().toString());
				row[9] = new String(or.getPactMoney() ==null ? "0.00":or.getPactMoney().toString());
				row[10] = new String(or.getOverdueStartDate() ==null ? "":dateToStr(or.getOverdueStartDate()));
				row[11] = new String( or.getLateCurNum()==null ? "":or.getLateCurNum().toString());
				row[12] = new String( or.getOverdue()==null ? "":  or.getOverdue().toString() );
				row[13] = new String(or.getResidualPactMoney() ==null ? "0.00":or.getResidualPactMoney().toString());
				row[14] = new String(or.getOverduePrincipal() ==null ? "0.00":or.getOverduePrincipal().toString());
				row[15] = new String(or.getOverdueInterestAmt() ==null ? "0.00":or.getOverdueInterestAmt().toString());
				row[16] = new String(or.getPenaltyDate()==null ? "": dateToStr(or.getPenaltyDate()));
				row[17] =new String(or.getRepayInterest()==null ? "":	or.getRepayInterest().toString());
				row[18] = new String(or.getTotalAmount()==null ? "":	or.getTotalAmount().toString());
				row[19] = new String(or.getLastRepayAmount()==null ? "":or.getLastRepayAmount().toString());
				row[20] = new String(or.getLastFactReturnDate()==null ? "":dateToStr(or.getLastFactReturnDate()));
				row[21] = new String(or.getEndRepayDate()==null ? "":dateToStr(or.getEndRepayDate()));
				row[22] = new String(or.getOverdueType()==null ? "":	or.getOverdueType().toString());
				row[23] = new String(or.getTracking()==null ? "":or.getTracking().toString());
				row[24] = new String(or.getManagerName()==null ? "":or.getManagerName());
				row[25] = new String(or.getFirstTirialName()==null ? "":or.getFirstTirialName());
				row[26] = new String(or.getFinstTirialName()==null ? "":or.getFinstTirialName());
				row[27] = new String(or.getServiceName()==null ? "":or.getServiceName());
				row[28] = new String(or.getOrganName()==null ? "":or.getOrganName());
				row[29] = new String(or.getContractSrc()==null ? "":or.getContractSrc());
				row[30] = new String(or.getProfessionType()==null ? "":or.getProfessionType());
				row[31] = new String(or.getLoanNumber()==null ? "":or.getLoanNumber().toString());
				row[32] = new String(or.getSingleLoanNumber()==null ? "":or.getSingleLoanNumber().toString());
				row[33] = new String(or.getLastTime()==null ? "":dateToStr(or.getLastTime()));
				row[34] = new String(or.getCreditRecord()==null ? "":or.getCreditRecord());
				row[35] = new String(or.getOverDay()==null ? "":or.getOverDay());
				
				row[36] = new String(or.getCollectorsLoginName()==null ? "":or.getCollectorsLoginName());
				row[37] = new String(or.getCollectors()==null ? "":or.getCollectors());
				row[38] = new String(or.getTaskId()==null ? "":or.getTaskId().toString());
				rows.add(row);
				 
			}					
			 //				

			ExportExcel exportExcel = new ExportExcel();
			exportExcel.setRows(rows);
			List<ExportExcel> exportExcelList=new ArrayList<ExportExcel>();
			exportExcelList.add(exportExcel);
			String[] headers = new String[]{"   营业部         ","产品类型 ","客户姓名","客户经理工号","业务主任","业务主任工号","身份证号码","签约日期",
					"还款日","合同金额","逾期起始日","逾期期数","逾期天数","剩余本金","逾期本金（A1）","逾期利息（A2）","罚息起算日",
					"罚息金额（B）","应还总金额（A1+A2+B）","最后一次还款金额","最后一次还款日期","最后一期应还款日期",
					"逾期类型","跟踪情况/逾期状态","客户经理","审核员","终审审核员",
					"客服","机构名称","合同来源","业主类型","借款次数","单笔借款次数","上笔结清时间","信用记录","逾期超180天","分配客服登录名(必填)","分配客服姓名","TaskID"
					};
			poiWriteExcel_To2007(headers, os,vo, exportExcelList);
		}
	}
	public static void poiWriteExcel_To2007(String[] headers, OutputStream os, TaskAllocationRuleVO vo,List<ExportExcel> exportExcelList) {
		SXSSFWorkbook xssf_w_book = new SXSSFWorkbook();
		Row xssf_w_row = null;//创建一行
		Cell xssf_w_cell = null;//创建每个单元格
		 Sheet sheet = xssf_w_book.createSheet();
		//列头样式
		 CellStyle head_cellStyle = xssf_w_book.createCellStyle();//创建一个单元格样式
		 Font head_font = xssf_w_book.createFont();
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
		 CellStyle cellStyle_CN = xssf_w_book.createCellStyle();//创建数据单元格样式(数据库数据样式)
		 	cellStyle_CN.setBorderBottom(XSSFCellStyle.BORDER_THIN);//单元格边线为细线
			cellStyle_CN.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			cellStyle_CN.setBorderRight(XSSFCellStyle.BORDER_THIN);
			cellStyle_CN.setBorderTop(XSSFCellStyle.BORDER_THIN); 
			cellStyle_CN.setAlignment(XSSFCellStyle.ALIGN_CENTER);//左右居中       
			cellStyle_CN.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//上下居中 
		cellStyle_CN.setWrapText(false);//不换行显示
		
		CellStyle condition_cellStyle = xssf_w_book.createCellStyle();//创建一个单元格样式
		 Font condition_font = xssf_w_book.createFont();
		condition_font.setFontName("宋体");//设置头部字体为宋体
		condition_font.setBoldweight(Font.BOLDWEIGHT_BOLD); //粗体
		condition_cellStyle.setFont(condition_font);//单元格样式使用字体
		condition_cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//上下居中 
		
		 
		 Row headerRow = sheet.createRow((short) 0);
		
		for (int i = 0; i < headers.length; i++) {
 			 Cell cell = headerRow.createCell(i);
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
					xssf_w_row = sheet.createRow(j+1);
				
					for (int a = 0; a < exportExcelList.get(i).getRows().get(j).length; a++) {
						
						//创建单元格
						xssf_w_cell = xssf_w_row.createCell(a);
						 
							//数据显示单元格样式设置
							xssf_w_cell.setCellStyle(cellStyle_CN);
					 
						//xssf_w_sheet.autoSizeColumn(a, true);
						//列头以及显示的数据	j 第几行 a  第几个单元格	 如果为数字	
						if (exportExcelList.get(i).getRows().get(j)[a] != null && exportExcelList.get(i).getRows().get(j)[a].matches("\\d+(\\.\\d+)?") ) {
							if(a==9||a==13||a==14||a==15||a==17||a==18){
								//	XSSFCellStyle cellStyle = xssf_w_book.createCellStyle();
								
									 
										 
											
										 
											cellStyle_CN.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
										 
									 
									xssf_w_cell.setCellStyle(cellStyle_CN);
									//列头以及显示的数据	j 第几行 a  第几个单元格
									xssf_w_cell.setCellValue(Double.parseDouble(exportExcelList.get(i).getRows().get(j)[a]));
								}else{
									cellStyle_CN.setDataFormat(HSSFDataFormat.getBuiltinFormat("@"));
									xssf_w_cell.setCellValue(exportExcelList.get(i).getRows().get(j)[a]);
									
								}
						
								
						} else {
								xssf_w_cell.setCellValue(exportExcelList.get(i).getRows().get(j)[a]);
						}
						

					}
					
				} 
				 
	 			 
					 
				}
		/*	for (int k = 0; k < headers.length; k++) {
				//创建单元格
				sheet.autoSizeColumn(k);
				
			}*/
		}
		 sheet.setColumnHidden((short)38, true);
		try {
			xssf_w_book.write(os);
			os.flush();
			

		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				os.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	/**描述 :读EXCEL文件
	 * @param fielName
	 * @return
	 */
	public 	List<TaskAllocationRule>   getExcelInfo(String fileName,MultipartFile Mfile){	
		List<TaskAllocationRule> list=new ArrayList<TaskAllocationRule>();
		InputStream is = null;  
		try{
		 
			if(!validateExcel(fileName)){
				return null;
			}
			//判断文件时2003版本还是2007版本
			boolean isExcel2003 = true; 
			if(WDWUtil.isExcel2007(fileName)){
				isExcel2003 = false;  
			}
			is = (Mfile.getInputStream());
		list =readExcel(is, isExcel2003); 
			is.close();
		 
		}catch(Exception e){
			e.printStackTrace();
		} finally{
			if(is !=null)
			{
				try{
					is.close();
				}catch(IOException e){
					is = null;    
					e.printStackTrace();  
				}
			}
		}
		return list;
	}
	/**
	 * 此方法两个参数InputStream是字节流。isExcel2003是excel是2003还是2007版本
	 * @param is
	 * @param isExcel2003
	 * @return
	 * @throws IOException
	 */
	public List<TaskAllocationRule> readExcel(InputStream is,boolean isExcel2003){
		
		List<TaskAllocationRule> list=new ArrayList<TaskAllocationRule>();
		try{
			//** 根据版本选择创建Workbook的方式 *//*
			Workbook wb = null;
			//当excel是2003时
			if(isExcel2003){
				wb = new HSSFWorkbook(is); 
			}
			else{
				wb = new XSSFWorkbook(is); 
			}
		 
			Sheet sheet=wb.getSheetAt(0);
			//得到Excel的行数
			this.totalRows=sheet.getPhysicalNumberOfRows();
		 
			//得到Excel的列数(前提是有行数)
			if(totalRows>=1 && sheet.getRow(0) != null){
				this.totalCells=sheet.getRow(0).getPhysicalNumberOfCells();
			}
			 
		
			
			for(int r=1;r<totalRows;r++)
			{
			   Row row = sheet.getRow(r);
			   if (row == null) continue;
			   int rowNum=row.getLastCellNum();	
				TaskAllocationRule rule=new TaskAllocationRule();
				//循环Excel的列
				for(int c = 0; c <rowNum; c++)
				{ 
					 
					Cell cell = row.getCell(c); 
				
					if (null != cell)  
					{
						
						//第36列 用户登录名
						 if(c==36){
						 
						String mm=	cell.getStringCellValue();
							if(!StringUtil.isEmpty(cell.getStringCellValue())){
								rule.setUserLoginName(cell.getStringCellValue());
								};
						 		
						 	  }
						//第37列 用户名
						 else if(c==37){
						 
							 
							 if(!StringUtil.isEmpty(cell.getStringCellValue())){								  
								 rule.setUserName(cell.getStringCellValue()); 
							 
							 }; 	  
						}
						//第37列 taskId
						else if(c==38){
							 if(!StringUtil.isEmpty(cell.getStringCellValue())){								  
								 rule.setTaskId(cell.getStringCellValue()); 
							 
							 }; 	
						}
						 
						}
						
					 
				}
 				 if(rule.getUserLoginName()!=null){
 					list.add(rule);
 				 };
				
			}
		}
		catch (IOException e)  {  
			e.printStackTrace();  
		} 
	 

		return list;
	}
	/**
	 * 描述：验证EXCEL文件
	 * @param filePath
	 * @return
	 */
	public boolean validateExcel(String filePath){
		if (filePath == null || !(WDWUtil.isExcel2003(filePath) || WDWUtil.isExcel2007(filePath))){  
			return false;  
		}  
		return true;
	}
	public static Date strToDate(String str) throws ParseException{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");//小写的mm表示的是分钟  
		Date date=sdf.parse(str);  
		return date;
	}
	public static String dateToStr(Date date) throws ParseException{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟  
		String str = sdf.format(date); 
		return str;
	}

	@Override
	public int getLateDetailsListCount(TaskAllocationRuleVO ruleVO) {
		// TODO Auto-generated method stub
		Date nowDate = DateUtil.getToday();
		 try {
			 ruleVO.setStartDate(dateToStr(nowDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return taskAllocationRuleDao.getLateDetailsListCount(ruleVO);
	}

 
}
