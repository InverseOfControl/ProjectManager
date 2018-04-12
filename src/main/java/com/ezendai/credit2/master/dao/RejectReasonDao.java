package com.ezendai.credit2.master.dao;

import java.util.List;

import com.ezendai.credit2.framework.dao.BaseDao;
import com.ezendai.credit2.master.model.RejectReason;
import com.ezendai.credit2.master.vo.RejectReasonVO;
/**
 * 拒绝原因仓库
 * 
 * @author chenqi
 * @version 1.0, 2014-08-27
 * @since 1.0
 */
public interface RejectReasonDao extends BaseDao<RejectReason> {

	/**
	 * <pre>
	 *  根据拒绝分类和贷款类型取得id,拒绝理由  
	 * </pre>
	 *
	 * @param rejectReasonVO
	 * @return
	 */
	List<RejectReason> findRejectReasonByTypeAndLoanType(RejectReasonVO rejectReasonVO);

	/**
	 * <pre>
	 *  根据ParentId取得id,拒绝理由  ,限制时间天数
	 * </pre>
	 *
	 * @param rejectReasonVO
	 * @return
	 */
	List<RejectReason> findRejectReasonByParentId(RejectReasonVO rejectReasonVO);
}