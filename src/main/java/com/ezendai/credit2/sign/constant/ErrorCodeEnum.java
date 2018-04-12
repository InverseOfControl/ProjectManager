package com.ezendai.credit2.sign.constant;

public enum ErrorCodeEnum {
	
	/****************参数校验10开头****************/
	/**签约捞财宝相关枚举值*/
	VERIFY_CODE_NULL("100301", "请输入验证码"),
	VERIFY_CODE_ERROR("100302", "验证码错误"),
	LOGIN_NO_SUCCESS("100303", "未登录捞财宝"),
	BANK_CARD_ERROR("100304", "银行卡格式不符合规范"),
	HTML_TO_PDF_ERROR("100305", "html转pdf错误");

	private String code;
	private String defaultMessage;

	ErrorCodeEnum(String code, String defaultMessage) {
		this.code = code;
		this.defaultMessage = defaultMessage;
	}

	public String getErrorCode() {
		return this.code;
	}

	public String getDefaultMessage() {
		return defaultMessage;
	}

}
