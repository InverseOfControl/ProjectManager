/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.system.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.system.dao.BaseAreaProductDao;
import com.ezendai.credit2.system.model.BaseAreaProduct;
import com.ezendai.credit2.system.service.BaseAreaProductService;
import com.ezendai.credit2.system.vo.BaseAreaProductVO;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author 00221921
 * @version $Id: BaseAreaProductServiceImpl.java, v 0.1 2015年4月13日 下午3:20:50 00221921 Exp $
 */
@Service
public class BaseAreaProductServiceImpl  implements BaseAreaProductService{
	@Autowired
	private BaseAreaProductDao baseAreaProductDao;

	/** 
	 * @param baseAreaProduct
	 * @return
	 * @see com.ezendai.credit2.system.service.BaseAreaProductService#insert(com.ezendai.credit2.system.model.BaseAreaProduct)
	 */
	@Override
	public BaseAreaProduct insert(BaseAreaProduct baseAreaProduct) {
		return baseAreaProductDao.insert(baseAreaProduct);
	}

	/** 
	 * @param id
	 * @see com.ezendai.credit2.system.service.BaseAreaProductService#deleteById(java.lang.Long)
	 */
	@Override
	public void deleteById(Long id) {
		baseAreaProductDao.deleteById(id);
	}

	/** 
	 * @param baseAreaProductVO
	 * @see com.ezendai.credit2.system.service.BaseAreaProductService#deleteByIdList(com.ezendai.credit2.system.vo.BaseAreaProductVO)
	 */
	@Override
	public void deleteByIdList(BaseAreaProductVO baseAreaProductVO) {
		baseAreaProductDao.deleteByIdList(baseAreaProductVO);
	}

	/** 
	 * @param baseAreaProductVO
	 * @return
	 * @see com.ezendai.credit2.system.service.BaseAreaProductService#update(com.ezendai.credit2.system.vo.BaseAreaProductVO)
	 */
	@Override
	public int update(BaseAreaProductVO baseAreaProductVO) {
		return baseAreaProductDao.update(baseAreaProductVO);
	}

	/** 
	 * @param id
	 * @return
	 * @see com.ezendai.credit2.system.service.BaseAreaProductService#get(java.lang.Long)
	 */
	@Override
	public BaseAreaProduct get(Long id) {
		return baseAreaProductDao.get(id);
	}

	/** 
	 * @param baseAreaProductVO
	 * @return
	 * @see com.ezendai.credit2.system.service.BaseAreaProductService#findListByVO(com.ezendai.credit2.system.vo.BaseAreaProductVO)
	 */
	@Override
	public List<BaseAreaProduct> findListByVO(
			BaseAreaProductVO baseAreaProductVO) {
		return baseAreaProductDao.findListByVo(baseAreaProductVO);
	}

	/** 
	 * @param map
	 * @return
	 * @see com.ezendai.credit2.system.service.BaseAreaProductService#exists(java.util.Map)
	 */
	@Override
	public boolean exists(Map<String, Object> map) {
		return baseAreaProductDao.exists(map);
	}

	/** 
	 * @param baseAreaProductVO
	 * @return
	 * @see com.ezendai.credit2.system.service.BaseAreaProductService#findWithPg(com.ezendai.credit2.system.vo.BaseAreaProductVO)
	 */
	@Override
	public Pager findWithPg(BaseAreaProductVO baseAreaProductVO) {
		return baseAreaProductDao.findWithPg(baseAreaProductVO);
	}

	/** 
	 * @param baseAreaProductVO
	 * @return
	 * @see com.ezendai.credit2.system.service.BaseAreaProductService#get(com.ezendai.credit2.system.vo.BaseAreaProductVO)
	 */
	@Override
	public BaseAreaProduct get(BaseAreaProductVO baseAreaProductVO) {
		return baseAreaProductDao.get(baseAreaProductVO);
	}

	/** 
	 * @param id
	 * @return
	 * @see com.ezendai.credit2.system.service.BaseAreaProductService#exists(java.lang.Long)
	 */
	@Override
	public boolean exists(Long id) {
		return baseAreaProductDao.exists(id);
	}

	/** 
	 * @param id
	 * @see com.ezendai.credit2.system.service.BaseAreaProductService#deleteByProductId(java.lang.Long)
	 */
	@Override
	public void deleteByProductId(Long productId) {
		baseAreaProductDao.deleteByProductId(productId);
	}
	
}
