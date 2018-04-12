/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.system.enumerate;

/**
 * <pre>
 * 定时任务名称不能超过50个字节
 * </pre>
 *
 * @author 00226557
 * @version $Id: TaskConstants.java, v 0.1 2014年12月4日 上午11:40:43 00226557 Exp $
 */
public class JobConstants {
	
	public static final String GENERATED_OFFER="生成报盘记录";
	
	public static final String SEND_OFFER="发送报盘记录";
	
	public static final String CHANGE_LOAN_STATUS="更改逾期记录";
	
	public static final String CHANGE_EXTENSION_STATUS="更改展期状态记录";
	
	public static final String REPAY_DEAL="挂账金额还款";
	
	public static final String DRAW_RISK_FDD_BATCH ="提取风险金";
	
	public static final String TPP_CALL_BACK_HANDLED = "TPP回盘处理";
	
	public static final String CANCEL_AUDIT_TIMEOUT_LOAN = "取消合同审批超时债权";
	
	public static final String CANCEL_EXTENSION = "取消展期借款";
	
	public static final String EXPIRED_SIGN_RULE = "将签单规则置为失效";
	
	public static final String  NO_CORRESPONDING_RECORD ="没有相应记录";
	
	public static final String  SYNC_LOAN_DATA ="生成外债数据";
	
	public static final String  SYNC_PUSH_LOAN_DATA ="推送借款及还款计划数据";
	
	public static final String  SYNC_PUSH_REPAY_DATA ="推送实际还款数据";
	
	public static final String  SYNC_PUSH_PREREPAYMENT_DATA ="推送提前一次性还款数据";
	
	public static final String  GENERATED_REPAYMENT_PLAN_RISK ="生成还款日还款计划表快照";
	
	public static final String GENERATED_MSG="生成短信记录";
	
	public static final String SEND_MSG="发送短信记录";
	
	public static final String GENERATED_OVERDUE_CASE="生成催收案件";
	
	public static final String NORMAL_TRANSFER_OVERDUE_CASE="催收案件日末正常移交";
	
}
