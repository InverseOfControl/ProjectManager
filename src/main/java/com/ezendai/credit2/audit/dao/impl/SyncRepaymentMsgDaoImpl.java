package com.ezendai.credit2.audit.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.vo.LoanVO;
import com.ezendai.credit2.audit.dao.SyncRepaymentMsgDao;
import com.ezendai.credit2.audit.model.SyncRepaymentMsg;
import com.ezendai.credit2.audit.vo.SyncRepaymentMsgVO;
import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;
import com.ezendai.credit2.framework.util.BeanUtil;
import com.ezendai.credit2.framework.util.Pager;

@Repository
public class SyncRepaymentMsgDaoImpl extends BaseDaoImpl<SyncRepaymentMsg> implements SyncRepaymentMsgDao {

	@Override
	public int count(SyncRepaymentMsgVO syncRepaymentMsgVO) {
		// TODO Auto-generated method stub
		Object count = getSqlSession().selectOne(getIbatisMapperNameSpace() + ".count", syncRepaymentMsgVO);
		int totalCount = Integer.parseInt(count.toString());
		return totalCount;
	}

}
