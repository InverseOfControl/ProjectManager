package com.ezendai.credit2.master.service;

import java.util.List;

import com.ezendai.credit2.master.model.RejectReason;

/**
 * 拒绝原因服务
 * 
 * @author chenqi
 * @version 1.0, 2014-08-27
 * @since 1.0
 */
public interface RejectReasonService {

	/**
	 * <pre>
	 *  根据拒绝分类和贷款类型取得id,拒绝理由  
	 * </pre>
	 *
	 * @param rejectReasonVO
	 * @return
	 */
	List<RejectReason> findRejectReasonByTypeAndLoanType(String type, Integer loanType);

	/**
	 * <pre>
	 * 根据ParentId取得id,拒绝理由  ,限制时间天数
	 * </pre>
	 *
	 * @param rejectReasonVO
	 * @return
	 */
	List<RejectReason> findRejectReasonByParentId(Long parentId);
	
	
	
	/**
	 * <pre>
	 * 通过id找到rejectReason
	 * </pre>
	 *
	 * @param id
	 * @return
	 */
	RejectReason get(Long id);
}
