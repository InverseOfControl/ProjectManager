/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.system.dao.OrganSalesManagerDao;
import com.ezendai.credit2.system.model.OrganSalesManager;
import com.ezendai.credit2.system.service.OrganSalesManagerService;
import com.ezendai.credit2.system.vo.OrganSalesManagerVO;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author 00226557
 * @version $Id: OrganSalesManagerServiceImpl.java, v 0.1 2015年9月16日 下午4:07:45 00226557 Exp $
 */
@Service
public class OrganSalesManagerServiceImpl implements OrganSalesManagerService {

	@Autowired
	OrganSalesManagerDao organSalesManagerDao;
	/** 
	 * @param organSalesManagerVO
	 * @return
	 * @see com.ezendai.credit2.system.service.OrganSalesManagerService#update(com.ezendai.credit2.system.vo.OrganSalesManagerVO)
	 */
	@Override
	public int update(OrganSalesManagerVO organSalesManagerVO) {
		return organSalesManagerDao.update(organSalesManagerVO);
	}

	/** 
	 * @param organSalesManager
	 * @return
	 * @see com.ezendai.credit2.system.service.OrganSalesManagerService#insert(com.ezendai.credit2.system.model.OrganSalesManager)
	 */
	@Override
	public OrganSalesManager insert(OrganSalesManager organSalesManager) {
		return organSalesManagerDao.insert(organSalesManager);
	}

	/** 
	 * @param organSalesManagerVO
	 * @return
	 * @see com.ezendai.credit2.system.service.OrganSalesManagerService#findListByVo(com.ezendai.credit2.system.vo.OrganSalesManagerVO)
	 */
	@Override
	public List<OrganSalesManager> findListByVo(OrganSalesManagerVO organSalesManagerVO) {
		return organSalesManagerDao.findListByVo(organSalesManagerVO);
	}

	/** 
	 * @param organSalesManagerVO
	 * @return
	 * @see com.ezendai.credit2.system.service.OrganSalesManagerService#findWithPg(com.ezendai.credit2.system.vo.OrganSalesManagerVO)
	 */
	@Override
	public Pager findWithPg(OrganSalesManagerVO organSalesManagerVO) {
		return organSalesManagerDao.findWithPg(organSalesManagerVO);
	}

	/** 
	 * @param organSalesManagerVO
	 * @return
	 * @see com.ezendai.credit2.system.service.OrganSalesManagerService#count(com.ezendai.credit2.system.vo.OrganSalesManagerVO)
	 */
	@Override
	public Integer count(OrganSalesManagerVO organSalesManagerVO) {
		return null;
	}

	/** 
	 * @param id
	 * @return
	 * @see com.ezendai.credit2.system.service.OrganSalesManagerService#get(java.lang.Long)
	 */
	@Override
	public OrganSalesManager get(Long id) {
		return organSalesManagerDao.get(id);
	}

	/** 
	 * @param organSalesManagerVO
	 * @see com.ezendai.credit2.system.service.OrganSalesManagerService#deleteListByVo(com.ezendai.credit2.system.vo.OrganSalesManagerVO)
	 */
	@Override
	public void deleteListByVo(OrganSalesManagerVO organSalesManagerVO) {
		organSalesManagerDao.deleteListByVo(organSalesManagerVO);
	}

}
