/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.after.expdata.common;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author 00226557
 * @version $Id: TPPConstants.java, v 0.1 2014年12月11日 上午11:11:40 00226557 Exp $
 */
public  class TPPConstants {
	/**TPP接收成功代码**/
	public static final String RECEIVE_SUCCESS_CODE = "000001";
	
	/**TPP2.0接收成功代码**/
	public static final String RECEIVE_SUCCESS_CODE_NEW = "000000";
	
	/**TPP回盘:已接收**/
	public static final Integer RECEIVED = 10;
	/**TPP回盘:已处理**/
	public static final Integer HANDLED = 20;
	
	/**TPP划扣状态:成功**/
	public static final String HANDLE_SUCCESS_CODE = "000000";
	/**TPP划扣状态:失败**/
	public static final String HANDLE_FAILURE_CODE = "111111";
	/**TPP划扣状态:部分成功**/
	public static final String HANDLE_PARTSUCCESS_CODE = "444444";
	
	/**TPP回调接口:成功代码**/
	public static final String RETURN_SUCCESS_CODE = "SUCCESS";
	/**TPP回调接口:失败代码**/
	public static final String RETURN_FAILURE_CODE = "FAILURE";
}
