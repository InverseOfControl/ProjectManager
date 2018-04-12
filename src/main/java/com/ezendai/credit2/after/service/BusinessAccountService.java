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
public interface BusinessAccountService {

	Pager findWithPg(BusinessAccountVO businessAccountVO);
	/**
	 * 
	 * <pre>
	 * 对公还款->领取借款记录列表
	 * </pre>
	 *
	 * @param loanVO
	 * @return
	 */
	Pager findBusinessLoanListWithPg(LoanVO loanVo);

	/**
	 * 
	 * <pre>
	 * 特定业务日志
	 * </pre>
	 *
	 * @param specBusinessLogVo
	 * @return
	 */
	Pager findBusinessLogsListWithPg(SpecBusinessLogVO specBusinessLogVo);

	/**
	 * 
	 * <pre>
	 * 显示小企业贷款详细信息
	 * </pre>
	 *
	 * @param loanId
	 * @param flag
	 * @return
	 */
	LoanDetailsVO toBusinessLoanDetail(Long loanId, String flag);

	/**
	 * 
	 * <pre>
	 * 显示车贷贷款详细信息
	 * </pre>
	 *
	 * @param loanId
	 * @param flag
	 * @return
	 */
	LoanDetailsVO toCarLoanDetail(Long loanId, String flag);
	
	/**
	 * 
	 * <pre>
	 * 显示车贷展期贷款详细信息
	 * </pre>
	 *
	 * @param loanId
	 * @return
	 */
	LoanDetailsVO toCarLoanExtensionDetail(Long loanId);

	/**
	 * <pre>
	 * 对公还款领取
	 * </pre>
	 *
	 * @param businessAccountId
	 * @return
	 */
	boolean receive(Long businessAccountId);

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
	 * @param businessAccountVo
	 * @return 查询出已认领的条数
	 * @see com.ezendai.credit2.after.service.OfferService#countExt(com.ezendai.credit2.after.vo.BusinessAccountVO)
	 */
	Integer receiveResultCount(BusinessAccountVO businessAccountVo);
	/**
	 * 
	 * <pre>
	 * 查询出已认领的结果
	 * </pre>
	 *
	 * @param businessAccountVo
	 * @return
	 */
	List<BusinessAccount>  findReceiveResult(BusinessAccountVO businessAccountVo);
	/**
	 * 导出认领结果
	 * @param businessAccountList
	 * @param sheetName
	 * @param os
	 * @see com.ezendai.credit2.after.service.BusinessAccountService#exportReceiveResult(java.util.List, java.lang.String, java.io.OutputStream)
	 */
	void exportReceiveResult(List<BusinessAccount> businessAccountList, String sheetName,
								OutputStream os);
	/**
	 * 
	 * <pre>
	 * 更新对公还款的状态
	 * </pre>
	 *
	 * @param businessAccountVo
	 * @return
	 */
	int updateBusinessAccountStatus(BusinessAccountVO businessAccountVo);
	
	/**
	 * 
	 * <pre>
	 * 批量导入
	 * </pre>
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	Map<String, Object> batchUpload(HttpServletRequest request, HttpServletResponse response);
	

	/**
	 * <pre>
	 * 被个贷调用接口: 更新对公还款状态
	 * </pre>
	 *
	 * @param request
	 * @return
	 */
	String updateBusinessAccountStatus(HttpServletRequest request);
	
	/**
	 * 
	 * <pre>
	 * 导出已认领
	 * </pre>
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	String exportReceiveData(HttpServletRequest request, HttpServletResponse response);
	/**
	 * 
	 * <pre>
	 * 通过id获取对公还款信息
	 * </pre>
	 *
	 * @param id
	 * @param BusinessAccount
	 * @return
	 */
	BusinessAccount getById(long id);
	/**
	 * 
	 * <pre>
	 * 通过id删除对公还款信息
	 * </pre>
	 *
	 * @param id
	 * @param BusinessAccount
	 * @return
	 */
	void deleteById(long id);

}
