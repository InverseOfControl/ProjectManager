package com.ezendai.credit2.after.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ezendai.credit2.after.vo.RepayEntryDetailsVO;


/**
 * <pre>
 * 还款录入
 * </pre>
 *
 * @author chenqi
 * @version $Id: RepayEntryService.java, v 0.1 2014年12月11日 下午4:03:35 chenqi Exp $
 */
public interface RepayEntryService {

	/**
	 * <pre>
	 * 批量上传
	 * </pre>
	 *
	 * @param request
	 * @param response
	 */
	Map<String, Object> batchUpload(HttpServletRequest request, HttpServletResponse response);
	
	
	
	 /**
	 * <pre>
	 * 用于显示还款录入点击还款后的还款信息界面
	 * </pre>
	 *
	 * @param loanId
	 * @return
	 */
	RepayEntryDetailsVO viewEdit(Long loanId);
	
	
	/**
	 * <pre>
	 * 还款保存
	 * </pre>
	 *
	 * @param repayEntryDetailsVO
	 * @return
	 */
	String saveRepay(RepayEntryDetailsVO repayEntryDetailsVO);
}
