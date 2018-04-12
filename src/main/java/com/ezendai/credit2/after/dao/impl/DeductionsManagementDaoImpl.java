package com.ezendai.credit2.after.dao.impl;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.after.dao.DeductionsManagementDao;
import com.ezendai.credit2.after.model.Offer;
import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;

/**
 * 
 * <pre>
 * 划扣管理
 * </pre>
 *
 * @author HQ-AT6
 * @version $Id: DueDateRuleDaoImpl.java, v 0.1 2014年8月22日 下午1:29:03 HQ-AT6 Exp $
 */
@Repository
public class DeductionsManagementDaoImpl extends BaseDaoImpl<Offer> implements DeductionsManagementDao {

	@Override
	public Integer isCheckData(Long loanId) {
		return getSqlSession().selectOne("com.ezendai.credit2.after.mapper.SpecialRepaymentMapper.isCheck",loanId);
	}

}
