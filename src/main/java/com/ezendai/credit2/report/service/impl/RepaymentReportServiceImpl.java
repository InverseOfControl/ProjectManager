package com.ezendai.credit2.report.service.impl;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.common.util.ExportExcel;
import com.ezendai.credit2.common.util.PoiExportExcel;
import com.ezendai.credit2.framework.util.CollectionUtil;
import com.ezendai.credit2.framework.util.DateUtil;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.framework.util.StringUtil;
import com.ezendai.credit2.master.constant.BizConstants;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.master.model.BaseArea;
import com.ezendai.credit2.master.service.BaseAreaService;
import com.ezendai.credit2.report.dao.RepaymentReportDao;
import com.ezendai.credit2.report.model.RepaymentReport;
import com.ezendai.credit2.report.service.RepaymentReportService;
import com.ezendai.credit2.report.vo.RepaymentReportVO;

@Service
public class RepaymentReportServiceImpl implements RepaymentReportService {

	@Autowired
	private RepaymentReportDao repaymentReportDao;
	@Autowired
	private BaseAreaService baseAreaService;

	/** 
	 * @param vo
	 * @return
	 * @see com.ezendai.credit2.report.service.RepaymentReportService#findWithPgOnRepayment(com.ezendai.credit2.report.vo.RepaymentReportVO)
	 */
	@Override
	public Pager findWithPgOnRepayment(RepaymentReportVO vo) {
		return repaymentReportDao.findWithPgOnRepayment(vo);
	}

	/** 
	 * @param vo
	 * @return
	 * @see com.ezendai.credit2.report.service.RepaymentReportService#findWithPgOnNoRepayment(com.ezendai.credit2.report.vo.RepaymentReportVO)
	 */
	@Override
	public Pager findWithPgOnNoRepayment(RepaymentReportVO vo) {
		return repaymentReportDao.findWithPgOnNoRepayment(vo);
	}
	
	public List<RepaymentReport> queryRepaymentReport(RepaymentReportVO vo) {
		return repaymentReportDao.queryRepaymentReport(vo);
	}

	/** 
	 * @param vo
	 * @return
	 * @see com.ezendai.credit2.report.service.RepaymentReportService#queryNoRepaymentReport(com.ezendai.credit2.report.vo.RepaymentReportVO)
	 */
	@Override
	public List<RepaymentReport> queryNoRepaymentReport(RepaymentReportVO vo) {
		return repaymentReportDao.queryNoRepaymentReport(vo);
	};
	
	/** 
	 * @param vo
	 * @return
	 * @see com.ezendai.credit2.report.service.RepaymentReportService#queryRepaymentReportCount(com.ezendai.credit2.report.vo.RepaymentReportVO)
	 */
	@Override
	public int queryRepaymentReportCount(RepaymentReportVO vo) {
		return repaymentReportDao.queryRepaymentReportCount(vo);
	}

	/** 
	 * @param vo
	 * @return
	 * @see com.ezendai.credit2.report.service.RepaymentReportService#queryNoRepaymentReportCount(com.ezendai.credit2.report.vo.RepaymentReportVO)
	 */
	@Override
	public int queryNoRepaymentReportCount(RepaymentReportVO vo) {
		return repaymentReportDao.queryNoRepaymentReportCount(vo);
	}
	
	/***
	 * 
	 * <pre>
	 * 包装查询的数据后导出Excel
	 * </pre>
	 *
	 */
	@Override
	public void exportRepaymentReportExcel(RepaymentReportVO vo,List<RepaymentReport> repaymentReportList, String sheetName, OutputStream os) {

		List<ExportExcel> exportExcelList = new ArrayList<ExportExcel>();
		ExportExcel exportExcel = new ExportExcel();
		exportExcel.setSheet(sheetName);
		//数据行
		List<String[]> rows = new ArrayList<String[]>();
		String[] column = new String[42];
		//列头
		column[0] =  "营业部";
		column[1] =  "客户姓名";	
		column[2] =  "身份证";
		column[3] =  "贷款种类";	
		column[4] =  "合同金额";	
		column[5] =  "期限";
		column[6] =  "当前期数";	
		column[7] =  "应还款日";	
		column[8] =  "本期应还总额";	
		column[9] =  "本期应还本金";	
		column[10] =  "本期应还利息";	
		column[11] =  "本期应还评估费";	
		column[12] =  "本期应还咨询费";	
		column[13] =  "本期应还乙方管理费";	
		column[14] =  "本期应还丙方管理费";	
		column[15] =  "本期应还风险金";
		column[16] =  "实际还款日";
		column[17] =  "实际还款总额";
		column[18] =  "还款性质";
		column[19] =  "还款方式";
		column[20] =  "期初预收";
		column[21] =  "减免金额";
		column[22] =  "罚息";
		column[23] =  "逾期利息";
		column[24] =  "逾期本金";
		column[25] =  "逾期乙方管理费";	
		column[26] =  "逾期丙方管理费";	
		column[27] =  "逾期咨询费";
		column[28] =  "逾期评估费";
		column[29] =  "逾期风险金";
		column[30] =  "当期利息";
		column[31] =  "当期本金";
		column[32] =  "当期乙方管理费";	
		column[33] =  "当期丙方管理费";	
		column[34] =  "当期咨询费";
		column[35] =  "当期评估费";
		column[36] =  "当期风险金";
		column[37] =  "提还违约金";
		column[38] =  "退费(乙方管理费)";
		column[39] =  "期末预收";
		column[40] =  "合同来源";
		column[41] =  "备注 ";
		rows.add(column);
		if (CollectionUtil.isNotEmpty(repaymentReportList)) {
			// 每一行要显示的内容
			for (RepaymentReport report : repaymentReportList) {
				String[] row = new String[42];
				
				row[0] = report.getSalesDeptName();
				row[1] = report.getPersonName();
				row[2] = "****" + report.getIdnum().substring(report.getIdnum().length()-6);
				if(EnumConstants.ProductType.CAR_LOAN.getValue().equals(report.getProductType())){
					row[3] ="车贷";
				}else if(EnumConstants.ProductType.SE_LOAN.getValue().equals(report.getProductType())){
					row[3] ="小企业贷";
				}else{
					row[3] =StringUtil.notNullString(report.getProductType()) ;
				}
				row[4] = report.getPactMoney().setScale(2).toString();
				row[5] = " "+report.getTime().toString();
				row[6] = " "+report.getCurNum().toString();
				if(report.getRepayDay() != null){
					row[7] = DateUtil.getDate(report.getRepayDay(), "yyyy-MM-dd");
				}else{
					row[7] ="";
				}
				row[8] = report.getRepayAmount().setScale(2).toString();
				row[9] = report.getPrincipalAmt().setScale(2).toString();
				row[10] = report.getInterestAmt().setScale(2).toString();
				row[11] = report.getEvalRate().setScale(2).toString();
				row[12] = report.getReferRate().setScale(2).toString();
				row[13] = report.getManagePart0Fee().setScale(2).toString();
				row[14] = report.getManagePart1Fee().setScale(2).toString();
				row[15] = report.getRisk().setScale(2).toString();
				if(report.getTradeTime() != null){
					row[16] = DateUtil.getDate(report.getTradeTime(), "yyyy-MM-dd");
				}else{
					row[16] ="";
				}
				row[17] = StringUtil.notNullString(report.getTradeAmount().setScale(2).toString());
				if(BizConstants.TRADE_CODE_NORMAL.equals(report.getTradeCode())){
					row[18] = "正常还款";
				}else if(BizConstants.TRADE_CODE_ONEOFF.equals(report.getTradeCode())){
					row[18] = "一次性(提前还款)";
				}else{
					row[18] = StringUtil.notNullString(report.getTradeCode());
				}
				if(EnumConstants.TradeType.CASH.getValue().equals(report.getPayType())){
					row[19] ="现金";
				}else if(EnumConstants.TradeType.FUYOU_WITHHOLDING.getValue().equals(report.getPayType())){
					row[19] ="富有代扣";
				}else if(EnumConstants.TradeType.ON_TICK.getValue().equals(report.getPayType())){
					row[19] ="挂账";
				}else if(EnumConstants.TradeType.RISK.getValue().equals(report.getPayType())){
					row[19] ="风险金";
				}else if(EnumConstants.TradeType.TONGLIAN_WITHHOLDING.getValue().equals(report.getPayType())){
					row[19] ="通联代扣 ";
				}else if(EnumConstants.TradeType.TRANSFER.getValue().equals(report.getPayType())){
					row[19] ="转账";
				}else{
					row[19] = StringUtil.notNullString(report.getPayType());
				}
				row[20] = StringUtil.notNullString(report.getTradeAmountBegin().setScale(2).toString());
				row[21] = StringUtil.notNullString(report.getReliefAmount().setScale(2).toString());
				row[22] = StringUtil.notNullString(report.getPenaltyInterestAmt().setScale(2).toString());
				row[23] = StringUtil.notNullString(report.getOverdueInterestAmt().setScale(2).toString());
				row[24] = StringUtil.notNullString(report.getOverduePrincipal().setScale(2).toString());
				row[25] = StringUtil.notNullString(report.getOverdueCurManagePart0Fee().setScale(2).toString());
				row[26] = StringUtil.notNullString(report.getOverdueCurManagePart1Fee().setScale(2).toString());
				row[27] = StringUtil.notNullString(report.getOverdueCurReferRate().setScale(2).toString());
				row[28] = StringUtil.notNullString(report.getOverdueCurEvalRate().setScale(2).toString());
				row[29] = StringUtil.notNullString(report.getOverdueCurRisk().setScale(2).toString());
				row[30] = StringUtil.notNullString(report.getCurInterestAmt().setScale(2).toString());
				row[31] = StringUtil.notNullString(report.getCurPrincipal().setScale(2).toString());
				row[32] = StringUtil.notNullString(report.getCurManagePart0Fee().setScale(2).toString());
				row[33] = StringUtil.notNullString(report.getCurManagePart1Fee().setScale(2).toString());
				row[34] = StringUtil.notNullString(report.getCurReferRate().setScale(2).toString());
				row[35] = StringUtil.notNullString(report.getCurEvalRate().setScale(2).toString());
				row[36] = StringUtil.notNullString(report.getCurRisk().setScale(2).toString());
				row[37] = StringUtil.notNullString(report.getPenalty().setScale(2).toString());
				row[38] = StringUtil.notNullString(report.getCurRefundPart0Fee().setScale(2).toString());
				row[39] = StringUtil.notNullString(report.getTradeAmountEnd().setScale(2).toString());
				row[40] = StringUtil.notNullString("证大P2P"); //hard code
				row[41] = StringUtil.notNullString(report.getRemark()); 
				rows.add(row);
			}
		}
		exportExcel.setRows(rows);
		exportExcelList.add(exportExcel);
		List<String> headList = buildHeadInfo(vo);
		//生成Excel文件
		PoiExportExcel.poiWriteExcel_To2007(headList,exportExcelList, os);
	}
	
	@Override
	public void exportNoRepaymentReportExcel(RepaymentReportVO vo,List<RepaymentReport> repaymentReportList, String sheetName, OutputStream os) {
		
		List<ExportExcel> exportExcelList = new ArrayList<ExportExcel>();
		ExportExcel exportExcel = new ExportExcel();
		exportExcel.setSheet(sheetName);
		//数据行
		List<String[]> rows = new ArrayList<String[]>();
		String[] column = new String[17];
		//列头
		column[0]  = "营业部";
		column[1]  = "客户姓名";	
		column[2]  = "身份证";
		column[3]  = "贷款种类";	
		column[4]  = "合同金额";	
		column[5]  = "期限";
		column[6]  = "当前期数";	
		column[7]  = "应还款日";	
		column[8]  = "本期应还总额";	
		column[9]  = "本期应还本金";	
		column[10] = "本期应还利息";	
		column[11] = "本期应还评估费";	
		column[12] = "本期应还咨询费";	
		column[13] = "本期应还乙方管理费";	
		column[14] = "本期应还丙方管理费";	
		column[15] = "本期应还风险金";
		column[16] = "合同来源";
		rows.add(column);
		
		if (CollectionUtil.isNotEmpty(repaymentReportList)) {
			// 每一行要显示的内容
			for (RepaymentReport report : repaymentReportList) {
				String[] row = new String[18];
				row[0] = StringUtil.notNullString(report.getSalesDeptName());
				row[1] = StringUtil.notNullString(report.getPersonName());
				row[2] = "****" + report.getIdnum().substring(report.getIdnum().length()-6);
				if(EnumConstants.ProductType.CAR_LOAN.getValue().equals(report.getProductType())){
					row[3] ="车贷";
				}else if(EnumConstants.ProductType.SE_LOAN.getValue().equals(report.getProductType())){
					row[3] ="小企业贷";
				}else{
					row[3] =StringUtil.notNullString(report.getProductType()) ;
				}
				row[4] = StringUtil.notNullString(report.getPactMoney());
				row[5] = report.getTime()+" ";
				row[6] = report.getCurNum()+" ";
				if(report.getRepayDay() != null){
					row[7] = DateUtil.getDate(report.getRepayDay(), "yyyy-MM-dd");
				}else{
					row[7] ="";
				}
				row[8] = StringUtil.notNullString(report.getRepayAmount());
				row[9] = StringUtil.notNullString(report.getPrincipalAmt());
				row[10] = StringUtil.notNullString(report.getInterestAmt());
				row[11] = StringUtil.notNullString(report.getEvalRate());
				row[12] = StringUtil.notNullString(report.getReferRate());
				row[13] = StringUtil.notNullString(report.getManagePart0Fee());
				row[14] = StringUtil.notNullString(report.getManagePart1Fee());
				row[15] = StringUtil.notNullString(report.getRisk());
				row[16] = "证大P2P"; //hard code
				rows.add(row);
			}
			
		}
		exportExcel.setRows(rows);
		exportExcelList.add(exportExcel);
		List<String> headList = buildHeadInfo(vo);
		//生成Excel文件
		PoiExportExcel.poiWriteExcel_To2007(headList,exportExcelList, os);
	}

	/**
	 * <pre>
	 * 
	 * </pre>
	 *
	 * @param vo
	 * @return
	 */
	private List<String> buildHeadInfo(RepaymentReportVO vo) {
		//build head info
		List<String> headList = new ArrayList<String>();
 		if(EnumConstants.YesOrNo.YES.getValue().equals(vo.getRepaymentType())){
 			headList.add("还款流水表");
		}else{
			headList.add("无还款记录表");
		}
 		if(vo.getRepayDateStart() != null && vo.getRepayDateEnd() != null){
 			headList.add("实际还款日: "+DateUtil.getDate(vo.getRepayDateStart(), "yyyy-MM-dd")+
 				" ~ "+DateUtil.getDate(vo.getRepayDateEnd(), "yyyy-MM-dd"));
 		}
 		if(vo.getSalesDeptId() != null){
 			BaseArea area = baseAreaService.get(Long.valueOf(vo.getSalesDeptId()));
 			headList.add("营业部: "+area.getName());
 		}else{
 			headList.add("营业部: 全部");
 		}
 		if(EnumConstants.ProductType.CAR_LOAN.getValue().equals(vo.getProductType())){
 			headList.add("贷款种类: "+"车贷");
		}else if(EnumConstants.ProductType.SE_LOAN.getValue().equals(vo.getProductType())){
			headList.add("贷款种类: "+"小企业贷");
		}else{
			headList.add("贷款种类: 全部");
		}
		return headList;
	}

}
