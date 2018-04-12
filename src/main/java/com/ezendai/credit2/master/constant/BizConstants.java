package com.ezendai.credit2.master.constant;

/**
 * <pre>
 * 业务上的有关常量
 * </pre>
 *
 * @author majl
 * @version $Id: Constants.java, v 0.1 2014-6-25 下午3:53:09 majl Exp $
 */
public class BizConstants {

	/** 分区--Area */
	public static final String CREDIT2_AREA = "zdsys.Area";
	/** 公司--Company */
	public static final String CREDIT2_COMPANY = "zdsys.Company";
	/** 网点--SalesDepartment */
	public static final String CREDIT2_SALESDEPARTMENT = "zdsys.SalesDepartment";
	/** 城市--City */
	public static final String CREDIT2_CITY = "zdsys.City";
	/** 销售团队--SalesTeam */
	public static final String CREDIT2_SALESTEAM = "zdsys.SalesTeam";
	
	/** 系统ID*/
	public static final Long SYSTEM_ID = -1L;
	
	/** 系统user--设置定时任务的USER*/
	public static final String SYSTEM_USER = "SYSTEM";

    /** 部门编号初始值 */
    public static final int START_DEPT_NO = 500;

	/** 分隔符 */
	public static String SPLIT_FLAG = "/";
	
	public static final String TRADE_CODE_OPENACC = "4001";// 放款
	
	public static final String TRADE_CODE_OPENACC_ASC = "4002";// 助学贷放款
 
	/** 账号类型 */
	public final static Integer ACCT_TYPE = 10;
	
	/** TPP接口-通联类型 */
	public final static String Allinpay = "10";
	/** TPP接口-富友类型 */
	public final static String Fuiou = "20";
	/** TPP接口-银联类型 */
	public final static String ChinaUnion = "30";
	/** TPP接口-快捷通类型 */
	public final static String KJTpay = "40";
	
	/** TPP接口-卡折标志 0卡 1折 */
	public final static String CARD_TYPE = "0";
	/** TPP接口-货币类型 156人民币 */
	public final static String CNY_CODE = "156";
	
	/** TPP接口-身份证证件类型 */
	public final static Integer ID_CARD_TYPE =1;
	
	/**正常还款**/
	public final static String TRADE_CODE_NORMAL = "1001";
	
	/**一次性（提前还款）**/
	public final static String TRADE_CODE_ONEOFF = "3001";
	
	/**日终处理柜员号*/
	public final static String ENDOFAY_TELLER = "A0001";
	
	/**补足风险金处理柜员号*/
	public final static String FILIRISK_TELLER = "A0003";
}
