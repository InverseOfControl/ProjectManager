/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.finance.service.impl;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezendai.credit2.apply.model.BankAccount;
import com.ezendai.credit2.apply.model.BusinessLog;
import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.service.BankAccountService;
import com.ezendai.credit2.apply.service.BusinessLogService;
import com.ezendai.credit2.apply.service.LoanService;
import com.ezendai.credit2.apply.vo.LoanVO;
import com.ezendai.credit2.audit.model.ApproveResult;
import com.ezendai.credit2.audit.service.ApproveResultService;
import com.ezendai.credit2.common.util.ExportExcel;
import com.ezendai.credit2.common.util.PoiExportExcel;
import com.ezendai.credit2.finance.service.FinancialAuditService;
import com.ezendai.credit2.framework.util.CollectionUtil;
import com.ezendai.credit2.framework.util.DateUtil;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.system.model.SysLog;
import com.ezendai.credit2.system.service.OrganService;
import com.ezendai.credit2.system.service.SysLogService;

/***
 * 
 * <pre>
 * 财务审核
 * </pre>
 *
 * @author 陈忠星
 * @version $Id: FinancialAuditServiceImpl.java, v 0.1 2014年9月11日 上午10:43:53 陈忠星 Exp $
 */
@Service
public class FinancialAuditServiceImpl implements FinancialAuditService {
    @Autowired
    private  LoanService loanService;
    @Autowired
    private ApproveResultService aproveResultService;
    @Autowired
    private SysLogService sysLogService;
    @Autowired
	private BusinessLogService businessLogService;
    @Autowired
    private  OrganService  organService;
    @Autowired
    private BankAccountService bankAccountService;
    /***
     * 财务审核
     * @param loanId
     * @return
     * @see com.ezendai.credit2.finance.service.FinancialAuditService#financialAudit(java.lang.Long)
     */
    @Override
    @Transactional
    public String financialAudit(Long loanId){
    	LoanVO loanVO = new LoanVO();
    	loanVO.setId(loanId);
		loanVO.setStatus(EnumConstants.LoanStatus.财务放款.getValue());
		loanVO.setFinanceAuditTime(new Date());
		loanService.update(loanVO);
		
		ApproveResult approveResult = new ApproveResult();
		approveResult.setLoanId(loanId);	
		approveResult.setState(EnumConstants.ApproveResultState.FINANCIAL_AUDIT.getValue());
		approveResult.setReason("财务审核");
		aproveResultService.insert(approveResult);		
		
		BusinessLog businessLog = new BusinessLog();
		businessLog.setFlowStatus(EnumConstants.BusinessLogStatus.FINANCE_AUDIT.getValue());
		businessLog.setLoanId(loanId);
		businessLog.setMessage("财务审核");
		businessLogService.insert(businessLog);
		//插入系统日志  
		SysLog sysLog = new SysLog();			
		sysLog.setOptModule(EnumConstants.OptionModule.FINANCE_AUDIT.getValue());
		sysLog.setOptType(EnumConstants.OptionType.FINANCE_AUDIT.getValue());
		sysLog.setRemark("财务审核 借款ID"+loanId);
		sysLogService.insert(sysLog);
		return "success";
    }
    /***
     * 财务审核退回
     * @return
     * @see com.ezendai.credit2.finance.service.FinancialAuditService#financialAudit(java.lang.Long)
     */
    @Override
    @Transactional
    public String financeReturn(ApproveResult approveResult, Integer borrowStatus){
		Loan loan = loanService.get(approveResult.getLoanId());
		String lcbBorrowNo = loan.getLcbBorrowNo();
		// 在推标处 处理
//		int lastNum = Integer.parseInt(lcbBorrowNo.substring(lcbBorrowNo.lastIndexOf("_")+1));
//		lcbBorrowNo = lcbBorrowNo.substring(0, lcbBorrowNo.lastIndexOf("_")+1) + ( lastNum ++ );

		LoanVO loanVO = new LoanVO();
    	loanVO.setId(approveResult.getLoanId());
		loanVO.setStatus(EnumConstants.LoanStatus.财务审核退回.getValue());
		loanVO.setFinanceAuditTime(new Date());
		// 设置捞财宝借款状态(终止借款 或者 流标)
		loanVO.setLcbBorrowStatus(borrowStatus);
		// 更新捞财宝流水号
		loanVO.setLcbBorrowNo(lcbBorrowNo);

		loanService.update(loanVO);
		
		
		approveResult.setState(EnumConstants.ApproveResultState.FINANCIAL_AUDIT_RETURNED.getValue());
		aproveResultService.insert(approveResult);		
		
		BusinessLog businessLog = new BusinessLog();
		businessLog.setFlowStatus(EnumConstants.BusinessLogStatus.FINANCE_RETURN.getValue());
		businessLog.setLoanId(approveResult.getLoanId());
		businessLog.setMessage(String.format("财务审核退回 ,一级原因：" + approveResult.getReason1() + ",二级原因：" +  approveResult.getReason2() + ",备注:" + approveResult.getReason()));
		
		businessLogService.insert(businessLog);
		//插入系统日志  
		SysLog sysLog = new SysLog();			
		sysLog.setOptModule(EnumConstants.OptionModule.FINANCE_AUDIT.getValue());
		sysLog.setOptType(EnumConstants.OptionType.FINANCE_AUDIT_RETURN.getValue());
		sysLog.setRemark("财务审核退回 借款ID"+approveResult.getLoanId());
		sysLogService.insert(sysLog);
		return "success";
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
	public void exportExcel(List<Loan> loanList,String sheetName, OutputStream os) {

		List<ExportExcel> exportExcelList = new ArrayList<ExportExcel>();
		ExportExcel exportExcel = new ExportExcel();
		exportExcel.setSheet(sheetName);
		//数据行
		List<String[]> rows = new ArrayList<String[]>();
		String[] column = new String[28];
		//列头
		column[0] = new String("营业部                ");
		column[1] = new String("产品类型");
		column[2] = new String("借款类型");
		column[3] = new String("子类型");
		column[4] = new String("客户经理");
		column[5] = new String("姓名        ");
		column[6] = new String("证件号码                ");
		column[7] = new String("机构名称");
		column[8] = new String("放款户名");
		column[9] = new String("放款银行        ");
		column[10] = new String("放款支行        ");
		column[11] = new String("放款账号                ");
		column[12] = new String("合同金额");
		column[13] = new String("审批金额");
		column[14] = new String("划款金额");
		column[15] = new String("借款期限");		
		column[16] = new String("还款起始日");
		column[17] = new String("还款止日");
		column[18] = new String("风险金");
		column[19] = new String("咨询费");
		column[20] = new String("评估费");
		column[21] = new String("乙方管理费");
		column[22] = new String("丙方管理费");
		column[23] = new String("利息");
		column[24] = new String("费用合计");
		column[25] = new String("签约日期");
		column[26] = new String("网点类型");
		column[27] = new String("合同来源");
		rows.add(column);
		if (CollectionUtil.isNotEmpty(loanList)) {
			//每一行要显示的内容
			for (Loan loan : loanList) {
				String[] row = new String[28];
				BigDecimal costs = new BigDecimal(0);//费用合计
				row[0] = new String(loan.getSalesDept().getName());
				if (EnumConstants.ProductList.CITY_WIDE_POS_LOAN.getValue().equals(loan.getProductId())) {
					row[1] = new String("同城POS");
				} else if (EnumConstants.ProductList.CITY_WIDE_SE_LOAN.getValue().equals(loan.getProductId())) {
					row[1] = new String("同城小微贷");
				} else if (EnumConstants.ProductList.STUDENT_LOAN.getValue().equals(loan.getProductId())) {
					row[1] = new String("助学贷");
				} else if (EnumConstants.ProductList.WS_SE_LOAN.getValue().equals(loan.getProductId())) {
					row[1] = new String("网商贷");
				}else if (EnumConstants.ProductList.CAR_LOAN.getValue().equals(loan.getProductId())) {
					row[1] = new String("车贷");
				}else if (EnumConstants.ProductList.CAR_NEW_LOAN.getValue().equals(loan.getProductId())) {
					row[1] = new String("车贷新产品");
				}else if (EnumConstants.ProductList.SE_LOAN.getValue().equals(loan.getProductId())) {
					row[1] = new String("小企业贷");
				}else {
					row[1] = new String("");
				}
				if (loan.getProductType().equals(EnumConstants.ProductType.SE_LOAN.getValue())) {
					row[2] = new String("小企业贷");
				} else if (loan.getProductType().equals(EnumConstants.ProductType.CAR_LOAN.getValue())) {
					row[2] = new String("车贷");
				} else {
					row[2] = new String("");
				}
				if (loan.getLoanType() != null && EnumConstants.ProductCarType.TURN_OVER.getValue().equals(loan.getLoanType())) {
					row[3] = new String("移交类");
				} else if (loan.getLoanType() != null && EnumConstants.ProductCarType.CIRCULATE.getValue().equals(loan.getLoanType())) {
					row[3] = new String("流通类");
				} else {
					row[3] = new String("");
				}
				row[4] = new String(loan.getCrm().getName());
				row[5] = new String(loan.getPerson().getName());
				row[6] = new String(loan.getPerson().getIdnum());
				if (loan.getOrganID() != null) {
					row[7] = new String(organService.get(loan.getOrganID()).getName());
				} else {
					row[7] = new String("");
				}
				BankAccount bankAccount = bankAccountService.get(loan.getGrantAccountId());
				if (bankAccount != null) {
					if(bankAccount.getAccountName() !=null)
					row[8] = new String(bankAccount.getAccountName());
					else
						row[8] = new String(loan.getPerson().getName());
				} else {
					row[8] = new String(loan.getPerson().getName());
				}
				if (loan.getGrantAccount() != null && loan.getGrantAccount().getBranchName() != null) {
					row[9] = new String(loan.getGrantAccount().getBankName());
					row[10] = new String(loan.getGrantAccount().getBranchName());
					row[11] = new String(loan.getGrantAccount().getAccount().trim());
				} else {
					row[9] = new String("");
					row[10] = new String("");
					row[11] = new String("");
				}
				if (loan.getPactMoney() != null) {
					row[12] = new String(loan.getPactMoney().toString());
				} else {
					row[12] = new String("");
				}
				if (loan.getAuditMoney() != null) {
					row[13] = new String(loan.getAuditMoney().toString());
				} else {
					row[13] = new String("");
				}
				if (loan.getGrantMoney() != null) {
					row[14] = new String(loan.getGrantMoney().toString());
				} else {
					row[14] = new String("");
				}
				row[15] = new String(loan.getAuditTime().toString());
				if (loan.getStartRepayDate() != null) {
					row[16] = new String(DateUtil.getDate(loan.getStartRepayDate(), "yyyy-MM-dd"));
				} else {
					row[16] = new String("");
				}
				if (loan.getEndRepayDate() != null) {
					row[17] = new String(DateUtil.getDate(loan.getEndRepayDate(), "yyyy-MM-dd"));
				} else {
					row[17] = new String("");
				}
				if (loan.getRisk() != null) {
					costs = costs.add(loan.getRisk());
					row[18] = new String(loan.getRisk().toString());
				} else {
					row[18] = new String("");
				}
				if (loan.getConsult() != null) {
					costs = costs.add(loan.getConsult());
					row[19] = new String(loan.getConsult().toString());
				} else {
					row[19] = new String("");
				}
				if (loan.getAssessment() != null) {
					costs = costs.add(loan.getAssessment());
					row[20] = new String(loan.getAssessment().toString());
				} else {
					row[20] = new String("");
				}
				if (loan.getbManage() != null) {
					costs = costs.add(loan.getbManage());
					row[21] = new String(loan.getbManage().toString());
				} else {
					row[21] = new String("");
				}
				if (loan.getcManage() != null) {
					costs = costs.add(loan.getcManage());
					row[22] = new String(loan.getcManage().toString());

				} else {
					row[22] = new String("");
				}
				if (loan.getProphaseInterest() != null) {
					//费用合计修改为不含利息（18+19+20+21+22）
					//column[18] = new String("风险金");
					//column[19] = new String("咨询费");
					//column[20] = new String("评估费");
					//column[21] = new String("乙方管理费");
					//column[22] = new String("丙方管理费");
					//costs = costs.add(loan.getProphaseInterest());
					row[23] = new String(loan.getProphaseInterest().toString());

				} else {
					row[23] = new String("0");
				}
					row[24] = new String(costs.toString());
					
				if (loan.getSignDate() != null) {
					row[25] = new String(DateUtil.getDate(loan.getSignDate(), "yyyy-MM-dd"));
				} else {
					row[25] = new String("");
				}
				if (loan.getProductType().compareTo(EnumConstants.ProductType.SE_LOAN.getValue()) == 0) {
					row[26] = new String("小企业贷");
				} else if (loan.getProductType().compareTo(EnumConstants.ProductType.CAR_LOAN.getValue()) == 0) {
					row[26] = new String("车贷");
				}
				if (loan.getContractSrc() != null && EnumConstants.ContractSrc.P2P.getValue().equals(loan.getContractSrc())) {
					row[27] = new String("证大P2P");
				} else if (loan.getContractSrc() != null && EnumConstants.ContractSrc.AITE.getValue().equals(loan.getContractSrc())) {
					row[27] = new String("证大爱特");
				} else {
					row[27] = new String("");
				}

				rows.add(row);
			}

		}
		exportExcel.setRows(rows);
		exportExcelList.add(exportExcel);
		//生成Excel文件
		PoiExportExcel.poiWriteExcel_To2007(exportExcelList, os);
	}


}
