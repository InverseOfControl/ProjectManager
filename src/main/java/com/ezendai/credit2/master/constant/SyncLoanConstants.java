package com.ezendai.credit2.master.constant;

/**
 * 外债数据对接 常量及枚举定义
 * @author Ivan
 *
 */
public class SyncLoanConstants {
	
	/**
	 * 同步状态枚举
	 *
	 */
	public enum SyncStatus {
		未知(0L),
		待同步(1L),
		同步中(2L),
		同步成功(3L),
		拒绝同步(4L);
		private Long value;
		SyncStatus(Long value) {
			this.value = value;
		}
		public Long getValue() {
			return value;
		}
	}
	
	/**
	 * 同步的数据类型
	 * 借款数据、还款计划数据、实际还款数据、异常还款（提前一次性还款）
	 */
	public enum SyncDataType {
		/** 借款数据 **/
		LOAN_DATA("LOAN_DATA"),
		/** 还款计划数据 **/
		REPAYMENT_PLAN_DATA("REPAYMENT_PLAN_DATA"),
		/** 实际还款数据 **/
		REPAY_DATA("REPAY_DATA"),
		/** 异常还款 **/
		PREREPAYMENT_DATA("PREREPAYMENT_DATA");
		private String value;
		SyncDataType(String value) {
			this.value = value;
		}
		public String getValue() {
			return value;
		}
	}
	
}
