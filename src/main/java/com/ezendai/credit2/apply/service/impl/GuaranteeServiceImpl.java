/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.apply.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezendai.credit2.apply.dao.GuaranteeDao;
import com.ezendai.credit2.apply.model.Guarantee;
import com.ezendai.credit2.apply.service.GuaranteeService;
import com.ezendai.credit2.apply.vo.GuaranteeVO;
import com.ezendai.credit2.framework.util.Pager;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author xiaoxiong
 * @version $Id: GuaranteeServiceImpl.java, v 0.1 2014年6月24日 下午4:31:47 xiaoxiong Exp $
 */
@Service
public class GuaranteeServiceImpl implements GuaranteeService {

	@Autowired
	GuaranteeDao guaranteeDao;

	@Override
	public Guarantee insert(Guarantee guarantee) {
		return guaranteeDao.insert(guarantee);
	} 

	@Override
	public void deleteById(Long id) {
		guaranteeDao.deleteById(id);
	}

	@Override
	public void deleteByIdList(GuaranteeVO guaranteeVO) {
		guaranteeDao.deleteByIdList(guaranteeVO);
	}
	
	@Transactional
	@Override
	public int update(GuaranteeVO guaranteeVO) {
		return guaranteeDao.update(guaranteeVO);
	}

	@Override
	public Guarantee get(Long id) {
		return guaranteeDao.get(id);
	}

	@Override
	public List<Guarantee> findListByVo(GuaranteeVO guaranteeVO) {
		return guaranteeDao.findListByVo(guaranteeVO);
	}
	/***
     * 根据用户名查询担保人
     * <pre>
     * 
     * </pre>
     *
     * @param personId
     * @return
     */
	@Override
    public List<Guarantee> findListByPersonId(Long personId, Long loanId) {
		GuaranteeVO guaranteeVO = new GuaranteeVO();
		guaranteeVO.setPersonId(personId);
		guaranteeVO.setLoanId(loanId);
        return guaranteeDao.findListByVo(guaranteeVO);
	  
    }
    /***
     * 根据贷款ID查询担保人
     * <pre>
     * 
     * </pre>
     *
     * @param personId
     * @return
     */
    @Override
    public List<Guarantee> findListByLoanId(Long loanId) {
        return guaranteeDao.findByPersonId(loanId);
      
    }
	@Override
	public boolean exists(Map<String, Object> map) {
		return guaranteeDao.exists(map);
	}

	@Override
	public Pager findWithPg(GuaranteeVO guaranteeVO) {
		return guaranteeDao.findWithPg(guaranteeVO);
	}

	@Override
	public Guarantee get(GuaranteeVO guaranteeVO) {
		return guaranteeDao.get(guaranteeVO);
	}

	@Override
	public boolean exists(Long id) {
		return guaranteeDao.exists(id);
	}

}
