/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.apply.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.apply.dao.BankAccountDao;
import com.ezendai.credit2.apply.model.BankAccount;
import com.ezendai.credit2.apply.vo.BankAccountVO;
import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.framework.constant.Constants;
import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;
import com.ezendai.credit2.framework.exception.BusinessException;
import com.ezendai.credit2.framework.model.UserSession;
import com.ezendai.credit2.framework.util.BeanUtil;
import com.ezendai.credit2.framework.util.DateUtil;
import com.ezendai.credit2.framework.util.Pager;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author xiaoxiong
 * @version $Id: BankAccountDaoImpl.java, v 0.1 2014年6月24日 下午1:24:47 xiaoxiong Exp $
 */
@Repository
public class BankAccountDaoImpl extends BaseDaoImpl<BankAccount> implements BankAccountDao {

	/** 
	 * 获取bankaccount->bank对象的属性,现在只能得到 bank tpp type
	 * @param id
	 * @return
	 * @see com.ezendai.credit2.apply.dao.BankAccountDao#getBankAccount(java.lang.Long)
	 */
	@Override
	public BankAccount getBankAccount(Long id) {
		return this.getSqlSession().selectOne(getIbatisMapperNameSpace() + ".getBankAccount", id);
	}

	/** 
	 * @param bankAccountVO
	 * @return
	 * @see com.ezendai.credit2.apply.dao.BankAccountDao#updateByIdList(com.ezendai.credit2.apply.vo.BankAccountVO)
	 */
	@Override
	public int updateByIdList(BankAccountVO bankAccountVO) {
		UserSession user = ApplicationContext.getUser();
		if (user != null) {
			bankAccountVO.setModifierId(user.getId());
			bankAccountVO.setModifier(user.getName());
			bankAccountVO.setModifiedTime(DateUtil.getTodayHHmmss());
		} else {
			bankAccountVO.setModifierId(Constants.SYSTME_ID);
			bankAccountVO.setModifier(Constants.SYSTEM_NAME);
			bankAccountVO.setModifiedTime(DateUtil.getTodayHHmmss());
		}
		if (bankAccountVO.getVersion() != null) {
			bankAccountVO.setVersion(bankAccountVO.getVersion() + 1);
		}
		int affectNum = 0;
		affectNum = getSqlSession().update(getIbatisMapperNameSpace() + ".updateByIdList", bankAccountVO);
		if (affectNum == 0) {
			throw new BusinessException("BaseDao update affectNum equal zero");
		}
		return affectNum;
	}

	@Override
	public BankAccount getBankAccountDetails(Long id) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne(getIbatisMapperNameSpace() + ".getBankAccountDetails", id);
	}

	@Override
	public Pager findWithPGExtension(BankAccountVO bankAccountVO) {
		Object count = getSqlSession().selectOne(getIbatisMapperNameSpace() + ".countExtension", bankAccountVO);
		int totalCount = Integer.parseInt(count.toString());

		Pager pg = bankAccountVO.getPager();
		pg.setTotalCount(totalCount);
		pg.calStart();
		List rstList = null;
		try {
			rstList = getSqlSession().selectList(getIbatisMapperNameSpace() + ".findWithPGExtension", bankAccountVO);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		BeanUtil.copyProperties(pg, bankAccountVO);
		pg.setTotalCount(totalCount);
		pg.setResultList(rstList);
		return pg;
	}
}
