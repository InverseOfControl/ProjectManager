package com.ezendai.credit2.master.service;

/**
 * <pre>
 * 个贷接口定义
 * </pre>
 *
 * @author cq
 * @version $Id: CreditService.java, v 0.1 2014年12月27日 上午10:29:26 cq Exp $
 */
public interface CreditService {

	
	
	/**
	 * <pre>
	 * 调用个贷对公还款借口
	 * </pre>
	 *
	 * @param repayDate
	 * @param repayTime
	 * @param secondCompany
	 * @param amount
	 * @param type
	 * @return
	 */
	String sendBusinessAccountData(String repayDate,String repayTime,String secondCompany,String amount,String type);
	
	/**
	 * <pre>
	 * 调用个贷对公还款借口
	 * </pre>
	 *
	 * @param repayDate
	 * @param repayTime
	 * @param secondCompany
	 * @param amount
	 * @param type
	 * @param url
	 * @return
	 */
	String sendBusinessAccountDataUnifie(String repayDate,String repayTime,String secondCompany,String amount,String type,String url);
	
	
}
