package com.ezendai.credit2.system.enumerate;

public enum SysParameterEnum {

	EXCEL_EXPORT_MAX_NO("Excel导出最大数量", 1, "20000"),

	VALID_IP("定时任务运行ip列表", 1, "172.16.220.95"),
	
	TPP_IP("TPP回调地址ip列表", 1, "127.0.0.1"),

	VALID_ATTACHMENT("附件容量和大小限制", 1, "2;40"),	
	
	BIZ_ACCT_RECEIVE_TIME("对公还款认领时间", 1, "9:00;16:30"),
	
	BIZ_ACCT_EXPORT_TIME("对公还款导出时间开始", 1, "16:30"),
	
	BATCH_UPLOAD_SIZE_LIMIT("批量上传文件大小限制", 1, "50"),
	
	CREDIT_IP("对公还款回调地址ip列表", 1, "127.0.0.1"),
	
	
	SEND_MSG_TEMPLET_ONE("发送短信为一号模板",1,"1"),
	
	SEND_MSG_TEMPLET_SIXTEEN("发送短信为十六号模板",1,"2"),
	
	SEND_MSG_SYSUID("发送短信系统号",1,"1004"),
	
	SEND_MSG_EMPID("发送短信员工号",1,"cqdsend"),
	
	SEND_MSG_POLICY("发送策略",1,"0"),
	
	SEND_MSG_CHANNEL("发送渠道",1,"sms;mail;sms,mail"),
	
	SEND_MSG_MTYPE("信息类别",1,"10000004"),
	
	SEND_MSG_LATESENDTIME("最晚发送时间",1,"2099-12-31 18"),
	
	SEND_MSG_ENCODING("字符编码方式",1,"UTF-8"),
	
	SEND_NEXT_MONTH("当月获取下月1日还款的日期",1,"24"),
	
	SEND_THIS_MONTH("当月获取当月16日还款的日期",1,"8"),
	
	OVERDUE_RECEIVABLES_MANAGER("催收主管",1,"00227758"),
	
	INFO_CATEGORY_CODE("TPP信息类别编码",1,"10023"),
	
	INFO_CATEGORY_CODE_REALTIME("TPP信息类别编码实时划扣",1,"10022"),
	
	TPP_ACCOUNT_NO("证大财富账户号",1,"10000010"),
	
	TPP_ACCOUNT_NO_YONGYOU("证大财富用友账户号",1,"10000010"),
	
	TPP_ACCOUNT_NAME("证大财富收款方姓名",1,"证大财富"),
	
	OVER_DUE_TIME("逾期时间",1,"3"),
	
	TPP_ACCOUNT_CREDIT("TPP银行卡验证",1,"0"),
	
	CURRENT_OFFER_TIMES("实时划扣次数限制",1,"2"), 
	
	EDU_NEW_CONTRACT("助学贷新合同",1,"1"),
	
    EDU_NEW_CONTRACT_TIME("助学贷新合同启用时间",1,"2016-04-18 00:00:00"),
    
    FIRST_ACCOUNT_CONFIG("本方账号配置", 1, ""), 
    
    GRANT_BATCH_UPLOAD("光大本方账号配置", 1, "36510188000802176"), 
    
    OWN_CARD_NAME("合同本方账号名称", 1, "上海证大投资咨询有限公司"), 
    
    OWN_CARD_NUM("合同本方账号卡号", 1, "36510188000802176"), 
    
    OWN_CARD_BANK("合同本方账号银行", 1, "光大银行上海分行"),
    
    OFFER_SPLIT_SINGLE("报盘是否需要拆单",1,"0");

	/** 中文名 */
	private String name;

	/** 类型（1:文本框，2：选择框   3:下拉选择框，值为“是/否”） */
	private Integer inputType;

	/** 默认值 */
	private String value;

	SysParameterEnum(String name, Integer inputType, String value) {

		this.name = name;
		this.inputType = inputType;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public Integer getInputType() {
		return inputType;
	}

	public String getValue() {
		return value;
	}

}
