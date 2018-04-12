/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.model.BaseArea;
import com.ezendai.credit2.system.dao.OrganSalesDepartmentDao;
import com.ezendai.credit2.system.model.OrganSalesDepartment;
import com.ezendai.credit2.system.service.OrganSalesDepartmentService;
import com.ezendai.credit2.system.vo.OrganSalesDepartmentVO;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author 00226557
 * @version $Id: OrganSalesDepartmentServiceImpl.java, v 0.1 2015年9月16日 下午4:06:01 00226557 Exp $
 */
@Service
public class OrganSalesDepartmentServiceImpl implements OrganSalesDepartmentService {
	
	@Autowired
	OrganSalesDepartmentDao organSalesDepartmentDao;

	/** 
	 * @param organSalesDepartmentVO
	 * @return
	 * @see com.ezendai.credit2.system.service.OrganSalesDepartmentService#update(com.ezendai.credit2.system.vo.OrganSalesDepartmentVO)
	 */
	@Override
	public int update(OrganSalesDepartmentVO organSalesDepartmentVO) {
		return organSalesDepartmentDao.update(organSalesDepartmentVO);
	}

	/** 
	 * @param organSalesDepartmentVO
	 * @see com.ezendai.credit2.system.service.OrganSalesDepartmentService#deleteByVO(com.ezendai.credit2.system.vo.OrganSalesDepartmentVO)
	 */
	@Override
	public void deleteByVO(OrganSalesDepartmentVO organSalesDepartmentVO) {
		organSalesDepartmentDao.deleteByIdList(organSalesDepartmentVO);
	}

	/** 
	 * @param organBank
	 * @return
	 * @see com.ezendai.credit2.system.service.OrganSalesDepartmentService#insert(com.ezendai.credit2.system.model.OrganBank)
	 */
	@Override
	public OrganSalesDepartment insert(OrganSalesDepartment organSalesDepartment) {
		return organSalesDepartmentDao.insert(organSalesDepartment);
	}

	/** 
	 * @param organSalesDepartmentVO
	 * @return
	 * @see com.ezendai.credit2.system.service.OrganSalesDepartmentService#findListByVo(com.ezendai.credit2.system.vo.OrganSalesDepartmentVO)
	 */
	@Override
	public List<OrganSalesDepartment> findListByVo(OrganSalesDepartmentVO organSalesDepartmentVO) {
		return organSalesDepartmentDao.findListByVo(organSalesDepartmentVO);
	}

	/** 
	 * @param organSalesDepartmentVO
	 * @return
	 * @see com.ezendai.credit2.system.service.OrganSalesDepartmentService#findWithPg(com.ezendai.credit2.system.vo.OrganSalesDepartmentVO)
	 */
	@Override
	public Pager findWithPg(OrganSalesDepartmentVO organSalesDepartmentVO) {
		return organSalesDepartmentDao.findWithPg(organSalesDepartmentVO);
	}

	/** 
	 * @param organSalesDepartmentVO
	 * @return
	 * @see com.ezendai.credit2.system.service.OrganSalesDepartmentService#count(com.ezendai.credit2.system.vo.OrganSalesDepartmentVO)
	 */
	@Override
	public Integer count(OrganSalesDepartmentVO organSalesDepartmentVO) {
		return null;
	}

	/** 
	 * @param id
	 * @return
	 * @see com.ezendai.credit2.system.service.OrganSalesDepartmentService#get(java.lang.Long)
	 */
	@Override
	public OrganSalesDepartment get(Long id) {
		return organSalesDepartmentDao.get(id);
	}

	/** 
	 * @param organSalesDepartmentVO
	 * @return
	 * @see com.ezendai.credit2.system.service.OrganSalesDepartmentService#findSalesDeptListByVo(com.ezendai.credit2.system.vo.OrganSalesDepartmentVO)
	 */
	@Override
	public List<BaseArea> findSalesDeptListByVo(OrganSalesDepartmentVO organSalesDepartmentVO) {
		return organSalesDepartmentDao.findSalesDeptListByVo(organSalesDepartmentVO);
	}

	/** 
	 * @param organSalesDepartmentVO
	 * @see com.ezendai.credit2.system.service.OrganSalesDepartmentService#deleteListByVo(com.ezendai.credit2.system.vo.OrganSalesDepartmentVO)
	 */
	@Override
	public void deleteListByVo(OrganSalesDepartmentVO organSalesDepartmentVO) {
		organSalesDepartmentDao.deleteListByVo(organSalesDepartmentVO);
	}

}
