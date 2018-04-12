package com.ezendai.credit2.apply.dao;

import java.util.List;

import com.ezendai.credit2.apply.model.*;
import com.ezendai.credit2.framework.dao.BaseDao;

public interface GuaranteeDao extends BaseDao<Guarantee>{
	int deleteByPrimaryKey(Long id);

	int insertSelective(Guarantee record);

	Guarantee selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(Guarantee record);

	/**
	 * 根据客户ID查找担保人信息
	 * <pre>
	 * 
	 * </pre>
	 *
	 * @param personId
	 * @return
	 */
	List<Guarantee> findByPersonId(Long personId);

	/**
	 * 根据贷款ID查找担保人信息
	 * <pre>
	 * 
	 * </pre>
	 *
	 * @param personId
	 * @return
	 */

	List<Guarantee> findByLoanId(Long loanId);
}