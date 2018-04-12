/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.apply.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.apply.dao.LoanExtensionDao;
import com.ezendai.credit2.apply.model.LoanExtension;
import com.ezendai.credit2.apply.service.LoanExtensionService;
import com.ezendai.credit2.apply.vo.LoanExtensionVO;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.enumerate.EnumConstants;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author 00221921
 * @version $Id: LoanExtensionServiceImpl.java, v 0.1 2015年3月10日 下午2:33:26 00221921 Exp $
 */
@Service
public class LoanExtensionServiceImpl implements LoanExtensionService{
	
	@Autowired
	private LoanExtensionDao loanExtensionDao;

	@Override
	public Long getLoanIdByExtensionId(Long extensionId) {
		return loanExtensionDao.getLoanIdByExtensionId(extensionId);
	}

	/** 
	 * @param loanExtension
	 * @return
	 * @see com.ezendai.credit2.apply.service.LoanExtensionService#insert(com.ezendai.credit2.apply.model.LoanExtension)
	 */
	@Override
	public LoanExtension insert(LoanExtension loanExtension) {
		return loanExtensionDao.insert(loanExtension);
	}

	/** 
	 * @param id
	 * @see com.ezendai.credit2.apply.service.LoanExtensionService#deleteById(java.lang.Long)
	 */
	@Override
	public void deleteById(Long id) {
		loanExtensionDao.deleteById(id);
	}

	/** 
	 * @param loanExtensionVO
	 * @return
	 * @see com.ezendai.credit2.apply.service.LoanExtensionService#update(com.ezendai.credit2.apply.vo.LoanExtensionVO)
	 */
	@Override
	public int update(LoanExtensionVO loanExtensionVO) {
		return loanExtensionDao.update(loanExtensionVO);
	}

	/** 
	 * @param id
	 * @return
	 * @see com.ezendai.credit2.apply.service.LoanExtensionService#get(java.lang.Long)
	 */
	@Override
	public LoanExtension get(Long id) {
		return loanExtensionDao.get(id);
	}

	/** 
	 * @param loanExtensionVO
	 * @return
	 * @see com.ezendai.credit2.apply.service.LoanExtensionService#findListByVo(com.ezendai.credit2.apply.vo.LoanExtensionVO)
	 */
	@Override
	public List<LoanExtension> findListByVo(LoanExtensionVO loanExtensionVO) {
		return loanExtensionDao.findListByVo(loanExtensionVO);
	}

	/** 
	 * @param map
	 * @return
	 * @see com.ezendai.credit2.apply.service.LoanExtensionService#exists(java.util.Map)
	 */
	@Override
	public boolean exists(Map<String, Object> map) {
		return loanExtensionDao.exists(map);
	}

	/** 
	 * @param loanExtensionVO
	 * @return
	 * @see com.ezendai.credit2.apply.service.LoanExtensionService#findWithPg(com.ezendai.credit2.apply.vo.LoanExtensionVO)
	 */
	@Override
	public Pager findWithPg(LoanExtensionVO loanExtensionVO) {
		return loanExtensionDao.findWithPg(loanExtensionVO);
	}

	/** 
	 * @param id
	 * @return
	 * @see com.ezendai.credit2.apply.service.LoanExtensionService#exists(java.lang.Long)
	 */
	@Override
	public boolean exists(Long id) {
		return loanExtensionDao.exists(id);
	}

	/** 
	 * @param loanExtensionVO
	 * @see com.ezendai.credit2.apply.service.LoanExtensionService#deleteByIdList(com.ezendai.credit2.apply.vo.LoanExtensionVO)
	 */
	@Override
	public void deleteByIdList(LoanExtensionVO loanExtensionVO) {
		loanExtensionDao.deleteByIdList(loanExtensionVO);
	}

	/** 
	 * @param loanId
	 * @param extensionTime
	 * @return
	 * @see com.ezendai.credit2.apply.service.LoanExtensionService#getPreExtensionId(java.lang.Long, java.lang.Integer)
	 */
	@Override
	public Long getPreExtensionId(Long extensionId, Integer extensionTime) {
		// 第一次展期，获取loan的Id
		if(extensionTime.compareTo(1) == 0) {
			return loanExtensionDao.getLoanIdByExtensionId(extensionId);
		} else {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("extensionId", extensionId);
			map.put("extensionTime", extensionTime);
			List<Integer> notStatusList = new ArrayList<Integer>();
			notStatusList.add(EnumConstants.LoanStatus.取消.getValue());
			notStatusList.add(EnumConstants.LoanStatus.拒绝.getValue());
			map.put("notStatusList", notStatusList);
			return loanExtensionDao.getPreExtensionId(map);
		}
	}

	@Override
	public Integer maxExtensionTime() {
		// TODO Auto-generated method stub
		return loanExtensionDao.maxExtensionTime();
	}

}
