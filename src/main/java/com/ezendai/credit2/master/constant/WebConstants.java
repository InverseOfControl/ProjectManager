package com.ezendai.credit2.master.constant;

import java.util.HashSet;
import java.util.Set;

public class WebConstants {

	public static String contextPath;

	public static String webUrl;
	
	public static String contextReportPath;

	public static String webReportUrl;
	
	public static String cookieKey;
	
	public static String honorUrl;
	

	/** 静态文件前缀 */
	public static final String staticResourcePathPrefix = "/resources/";

	/** 登录页面url */
	public static final String LOGIN_PAGE_URI = "/system/sign";

	/** 登录页面url */
	public static final String UPLOADFILE_URI = "/apply/uploadAttachment";

	/** 登录验证url */
	public static final String LOGIN_VERIFY_URI = "/system/login";

	/** 健康检查url */
	public static final String HEALTH_CHECK_URI = "/healthCheck/verify";
	
	/** tpp call back url */
//	public static final String TPP_CALL_BACK_URI = "/after/offerManagement/tppCallBackData";
	
	/** credit business_account call back url */
	public static final String CREDIT_BUSINESS_ACCOUNT_CALL_BACK_URI = "/after/businessAccount/updateBusinessAccountStatus";
	
	/** credit business_account import data url */
	public static final String CREDIT_BUSINESS_ACCOUNT_IMPORT_DATA = "/after/businessAccount/importBusinessData";
	
	/** credit business_account export receive data url */
	public static final String CREDIT_BUSINESS_ACCOUNT_EXPORT_RECEIVE_DATA = "/after/businessAccount/exportReceiveData";
	
	/** credit repay_entry import data url */
	public static final String CREDIT_REPAY_ENTRY_IMPORT_DATA = "/after/repayEntry/importRepayData";
	
	/** credit penalty_reduce import data url */
	public static final String CREDIT_PENALTY_REDUCE_IMPORT_DATA= "/after/penaltyReduce/importData";
	/** 捞财宝 api  */
	public static final String API_LCB = "/api/lcb";
	/** 电子签章回调地址  */
	public static final String ELE_SIGN_CALLBACK = "/signName/eleSignCallback";
	public static final String ELE_LCB_SIGN_CALLBACK = "/signName/lcbEleSignCallback";
	


	/** 不需要登录验证的uri列表 */
	public static final Set<String> notAuthFilterURIList;
	

	static {
		notAuthFilterURIList = new HashSet<String>();
		notAuthFilterURIList.add(LOGIN_PAGE_URI);
		notAuthFilterURIList.add(LOGIN_VERIFY_URI);
		notAuthFilterURIList.add(UPLOADFILE_URI);
		notAuthFilterURIList.add(HEALTH_CHECK_URI);
//		notAuthFilterURIList.add(TPP_CALL_BACK_URI);
		notAuthFilterURIList.add(CREDIT_BUSINESS_ACCOUNT_CALL_BACK_URI);
		notAuthFilterURIList.add(CREDIT_BUSINESS_ACCOUNT_IMPORT_DATA);
		notAuthFilterURIList.add(CREDIT_BUSINESS_ACCOUNT_EXPORT_RECEIVE_DATA);
		notAuthFilterURIList.add(CREDIT_REPAY_ENTRY_IMPORT_DATA);
		notAuthFilterURIList.add(CREDIT_PENALTY_REDUCE_IMPORT_DATA);
		notAuthFilterURIList.add(API_LCB);
		notAuthFilterURIList.add(ELE_SIGN_CALLBACK);
		notAuthFilterURIList.add(ELE_LCB_SIGN_CALLBACK);
	}

}
