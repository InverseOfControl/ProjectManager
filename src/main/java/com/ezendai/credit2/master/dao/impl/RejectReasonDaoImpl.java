package com.ezendai.credit2.master.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;
import com.ezendai.credit2.master.dao.RejectReasonDao;
import com.ezendai.credit2.master.model.RejectReason;
import com.ezendai.credit2.master.vo.RejectReasonVO;


@Repository
public class RejectReasonDaoImpl extends BaseDaoImpl<RejectReason> implements RejectReasonDao {

	@Override
	public List<RejectReason> findRejectReasonByTypeAndLoanType(RejectReasonVO rejectReasonVO) {
		return getSqlSession().selectList(getIbatisMapperNameSpace() + ".findRejectReasonByTypeAndLoanType", rejectReasonVO);
	}

	@Override
	public List<RejectReason> findRejectReasonByParentId(RejectReasonVO rejectReasonVO) {
		return getSqlSession().selectList(getIbatisMapperNameSpace() + ".findRejectReasonByParentId", rejectReasonVO);
	}

}
