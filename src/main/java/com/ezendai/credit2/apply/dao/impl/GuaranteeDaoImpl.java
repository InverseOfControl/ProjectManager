package com.ezendai.credit2.apply.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.apply.dao.GuaranteeDao;
import com.ezendai.credit2.apply.model.Guarantee;
import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;
/***
 * 担保人
 * <pre>
 * 
 * </pre>
 *
 * @author HQ-AT6
 * @version $Id: GuaranteeDaoImpl.java, v 0.1 2014年6月23日 下午4:18:59 HQ-AT6 Exp $
 */
@Repository
public class  GuaranteeDaoImpl extends BaseDaoImpl<Guarantee> implements GuaranteeDao{

	@Override
	public int deleteByPrimaryKey(Long id) {
		return 0;
	}

	@Override
	public int insertSelective(Guarantee record) {
		return 0;
	}

	/***
	 * 根据ID查询联系人
	 * @param id
	 * @return
	 * @see com.ezendai.credit2.apply.dao.GuaranteeDao#selectByPrimaryKey(java.lang.Long)
	 */
	@Override
	public Guarantee selectByPrimaryKey(Long id) {
		return super.get(id);
	}

	/**
	 * 根据客户ID查找担保人信息
	 * <pre>
	 * 
	 * </pre>
	 *
	 * @param personId
	 * @return
	 */
	@Override
	public List<Guarantee> findByPersonId(Long personId) {
		return this.getSqlSession().selectList(getIbatisMapperNameSpace() + ".selectByPersonId",
			personId);
	}

	/**
	 * 根据贷款ID查找担保人信息
	 * <pre>
	 * 
	 * </pre>
	 *
	 * @param personId
	 * @return
	 */
	@Override
	public List<Guarantee> findByLoanId(Long loanId) {
		return this.getSqlSession().selectList(getIbatisMapperNameSpace() + ".selectByLoanId",
			loanId);
	}

	@Override
	public int updateByPrimaryKeySelective(Guarantee record) {
		return 0;
	}

}