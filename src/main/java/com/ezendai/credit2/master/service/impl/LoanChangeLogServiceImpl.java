package com.ezendai.credit2.master.service.impl;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezendai.credit2.apply.model.BankAccount;
import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.common.util.ExportExcel;
import com.ezendai.credit2.common.util.PoiExportExcel;
import com.ezendai.credit2.framework.util.CollectionUtil;
import com.ezendai.credit2.framework.util.DateUtil;
import com.ezendai.credit2.master.dao.LoanChangeLogDao;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.master.model.LoanChangeLog;
import com.ezendai.credit2.master.service.LoanChangeLogService;
import com.ezendai.credit2.master.vo.LoanChangeLogVO;

@Service
public class LoanChangeLogServiceImpl implements LoanChangeLogService {

	@Autowired
	LoanChangeLogDao loanChangeLogDao;

	@Override
	public void insert(LoanChangeLog loanChangeLog) {
		// TODO Auto-generated method stub
		loanChangeLogDao.insert(loanChangeLog);
	}

	@Override
	public List<LoanChangeLog> findListByVO(LoanChangeLogVO loanChangeLogVO) {
		// TODO Auto-generated method stub
		return loanChangeLogDao.findListByVo(loanChangeLogVO);
	}

	/***
	 * 
	 * <pre>
	 * 包装查询的数据后导出Excel
	 * </pre>
	 * 
	 */
	@Override
	@Transactional
	public void exportExcel(List<LoanChangeLog> loanChangeLogList,
			String sheetName, OutputStream os) {

		List<ExportExcel> exportExcelList = new ArrayList<ExportExcel>();
		ExportExcel exportExcel = new ExportExcel();
		exportExcel.setSheet(sheetName);
		// 数据行
		List<String[]> rows = new ArrayList<String[]>();
		String[] column = new String[9];
		// 列头
		column[0] = new String("借款人");
		column[1] = new String("营业部");
		column[2] = new String("机构名称");
		column[3] = new String("合同金额");
		column[4] = new String("操作人");
		column[5] = new String("操作时间");
		column[6] = new String("变更项");
		column[7] = new String("变更前内容");
		column[8] = new String("变更后内容");
		rows.add(column);
		if (CollectionUtil.isNotEmpty(loanChangeLogList)) {
			// 每一行要显示的内容
			for (LoanChangeLog loanChangeLog : loanChangeLogList) {
				String[] columnList = new String[9];
				columnList[0] = new String(loanChangeLog.getPersonName()!=null?loanChangeLog.getPersonName():"");
				columnList[1] = new String(loanChangeLog.getSalesDeptName()!=null?loanChangeLog.getSalesDeptName():"");
				columnList[2] = new String(loanChangeLog.getOrganName()!=null?loanChangeLog.getOrganName():"");
				columnList[3] = new String(loanChangeLog.getPactMoney()
						.toString()!=null?loanChangeLog.getPactMoney().toString():"");
				columnList[4] = new String(loanChangeLog.getOperatorName()!=null?loanChangeLog.getOperatorName():"");
				
				columnList[5] = new String(
						DateUtil.defaultFormatDate(loanChangeLog
								.getOperatorTime()));
				columnList[6] = new String(loanChangeLog.getChangeChoice()!=null?loanChangeLog.getChangeChoice():"");
				columnList[7] = new String(loanChangeLog.getChangeBefore()!=null?loanChangeLog.getChangeBefore():"");
				columnList[8] = new String(loanChangeLog.getChangeAfter()!=null?loanChangeLog.getChangeAfter():"");
				rows.add(columnList);
			}
		}
		exportExcel.setRows(rows);
		exportExcelList.add(exportExcel);
		// 生成Excel文件
		poiWriteExcel_To2007_Fast(exportExcelList, os);
	}

	/***
	 * 
	 * 
	 * @param excelList
	 * @param os
	 *            路径以及文件名
	 */
	public static void poiWriteExcel_To2007_Fast(List<ExportExcel> excelList,
			OutputStream os) {

		SXSSFWorkbook xssf_w_book = new SXSSFWorkbook();
		Sheet xssf_w_sheet = null;
		Row xssf_w_row = null;// 创建一行
		Cell xssf_w_cell = null;// 创建每个单元格

		// 列头样式
		CellStyle head_cellStyle = xssf_w_book.createCellStyle();// 创建一个单元格样式
		Font head_font = xssf_w_book.createFont();
		head_font.setFontName("宋体");// 设置头部字体为宋体
		head_font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 粗体
		head_cellStyle.setFont(head_font);// 单元格样式使用字体
		// 行显示样式
		CellStyle cellStyle_CN = xssf_w_book.createCellStyle();// 创建数据单元格样式(数据库数据样式)
		/*
		 * cellStyle_CN.setBorderBottom(XSSFCellStyle.BORDER_THIN);//单元格边线为细线
		 * cellStyle_CN.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		 * cellStyle_CN.setBorderRight(XSSFCellStyle.BORDER_THIN);
		 * cellStyle_CN.setBorderTop(XSSFCellStyle.BORDER_THIN);
		 */
		cellStyle_CN.setWrapText(false);// 不换行显示
		for (int i = 0; i < excelList.size(); i++) {
			// 创建sheet
			xssf_w_sheet = xssf_w_book.createSheet(excelList.get(i).getSheet());

			int rowLength = excelList.get(i).getRows().size();
			// 遍历行
			for (int j = 0; j < rowLength; j++) {
				// 创建行
				xssf_w_row = xssf_w_sheet.createRow(j);
				for (int a = 0; a < excelList.get(i).getRows().get(j).length; a++) {

					// 创建单元格
					xssf_w_cell = xssf_w_row.createCell(a);
					if (j == 0) {
						// 列头单元格样式设置
						xssf_w_cell.setCellStyle(head_cellStyle);
					} else {
						// 数据显示单元格样式设置
						xssf_w_cell.setCellStyle(cellStyle_CN);
					}
					// xssf_w_sheet.autoSizeColumn(a, true);
					// 列头以及显示的数据 j 第几行 a 第几个单元格 如果为数字
					if (excelList.get(i).getRows().get(j)[a] != null
							&& excelList.get(i).getRows().get(j)[a]
									.matches("\\d+(\\.\\d+)?")) {

						if (excelList.get(i).getRows().get(0)[a].contains("号")||excelList.get(i).getRows().get(0)[a].contains("变更")) {

							xssf_w_cell.setCellValue(excelList.get(i).getRows()
									.get(j)[a]);

						} else {
							// 列头不包含 账号 证件号 身份证号等的数字类型的样式设置,保留两位小数
							CellStyle cellStyle = xssf_w_book.createCellStyle();
							cellStyle.setDataFormat(HSSFDataFormat
									.getBuiltinFormat("0.0000"));
							xssf_w_cell.setCellStyle(cellStyle);
							
							// 列头以及显示的数据 j 第几行 a 第几个单元格
							xssf_w_cell.setCellValue(Double
									.parseDouble(excelList.get(i).getRows()
											.get(j)[a]));
						}

					} else {
						xssf_w_cell.setCellValue(excelList.get(i).getRows()
								.get(j)[a]);
					}

				}

			}
			for (int k = 0; k < excelList.get(0).getRows().get(0).length; k++) {
				// 创建单元格
				xssf_w_sheet.autoSizeColumn(k);

			}
		}
		try {
			xssf_w_book.write(os);
			os.flush();
			os.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
