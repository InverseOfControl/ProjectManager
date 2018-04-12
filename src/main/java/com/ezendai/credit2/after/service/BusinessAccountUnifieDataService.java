/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.after.service;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ezendai.credit2.after.model.BusinessAccount;
import com.ezendai.credit2.after.vo.BusinessAccountVO;
import com.ezendai.credit2.apply.vo.LoanDetailsVO;
import com.ezendai.credit2.apply.vo.LoanVO;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.vo.SpecBusinessLogVO;

/**
 * <pre>
 * 对公还款业务接口
 * </pre>
 *
 * @author zhangshihai
 * @version $Id: BusinessAccountService.java, v 0.1 2014年12月11日 下午3:50:00 00221921 Exp $
 */
public interface BusinessAccountUnifieDataService {

	/**
	 * <pre>
	 * 对公还款确认领取
	 * </pre>
	 *
	 * @param businessAccountId(LoanId,id,secondCompany,repayDate,repayTime,amount,version)
	 * @return
	 */
	String confirmReceive(BusinessAccountVO businessAccountVo);

	/**
	 * <pre>
	 *  对公还款确认领取撤销
	 * </pre>
	 *
	 * @param businessAccountVo(id,secondCompany,repayDate,repayTime,amount)
	 * @return
	 */
	String confirmReceiveCancel(BusinessAccountVO businessAccountVo);

	/**
	 * <pre>
	 * 无需认领
	 * </pre>
	 *
	 * @param businessAccountVo(id,secondCompany,repayDate,repayTime,amount,version)
	 * @return
	 */
	String withoutClaim(BusinessAccountVO businessAccountVo);

	/**
	 * <pre>
	 * 无需认领取消
	 * </pre>
	 *
	 * @param businessAccountVo(id,secondCompany,repayDate,repayTime,amount)
	 * @return
	 */
	String withoutClaimCancel(BusinessAccountVO businessAccountVo);
  
	 

	/**
	 * <pre>
	 * 被个贷调用接口: 更新对公还款状态
	 * </pre>
	 *
	 * @param request
	 * @return
	 */
	String updateBusinessAccountStatus(HttpServletRequest request);
	
	 
	 

}
