package com.ezendai.credit2.audit.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.audit.dao.ApproveResultDao;
import com.ezendai.credit2.audit.model.ApproveResult;
import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;

/***
 * <pre>
 * 审批结果
 * 
 * </pre>
 *
 * @author HQ-AT6
 * @version $Id: ApproveResultDaoImpl.java, v 0.1 2014年6月23日 下午4:40:15 HQ-AT6 Exp $
 */
@Repository
public class ApproveResultDaoImpl extends BaseDaoImpl<ApproveResult> implements ApproveResultDao {

	@Override
	public List<ApproveResult> getApproveResultByLoanId(Long loanId) {
		return this.getSqlSession().selectList(getIbatisMapperNameSpace() + ".selectListByloanId",
			loanId);
	}

}