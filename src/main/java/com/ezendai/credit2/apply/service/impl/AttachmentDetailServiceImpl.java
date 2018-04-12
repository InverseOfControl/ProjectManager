/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.apply.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.apply.dao.AttachmentDetailDao;
import com.ezendai.credit2.apply.model.AttachmentDetail;
import com.ezendai.credit2.apply.service.AttachmentDetailService;
import com.ezendai.credit2.apply.vo.AttachmentDetailVO;
import com.ezendai.credit2.framework.util.Pager;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author liyuepeng
 * @version $Id: AttachmentDetailServiceImpl.java, v 0.1 2014-9-4 下午03:31:25 liyuepeng Exp $
 */
@Service
public class AttachmentDetailServiceImpl implements AttachmentDetailService {

	@Autowired
	private AttachmentDetailDao attachmentDetailDao;
	/** 
	 * @param attachmentDetail
	 * @return
	 * @see com.ezendai.credit2.apply.service.AttachmentDetailService#insert(com.ezendai.credit2.apply.model.AttachmentDetail)
	 */
	@Override
	public AttachmentDetail insert(AttachmentDetail attachmentDetail) {
		return attachmentDetailDao.insert(attachmentDetail);
	}

	/** 
	 * @param id
	 * @see com.ezendai.credit2.apply.service.AttachmentDetailService#deleteById(java.lang.Long)
	 */
	@Override
	public void deleteById(Long id) {
		attachmentDetailDao.deleteById(id);
	}

	/** 
	 * @param attachmentDetailVO
	 * @see com.ezendai.credit2.apply.service.AttachmentDetailService#deleteByIdList(com.ezendai.credit2.apply.vo.AttachmentDetailVO)
	 */
	@Override
	public void deleteByIdList(AttachmentDetailVO attachmentDetailVO) {
		attachmentDetailDao.deleteByIdList(attachmentDetailVO);
	}

	/** 
	 * @param id
	 * @return
	 * @see com.ezendai.credit2.apply.service.AttachmentDetailService#get(java.lang.Long)
	 */
	@Override
	public AttachmentDetail get(Long id) {
		return attachmentDetailDao.get(id);
	}

	/** 
	 * @param attachmentDetailVO
	 * @return
	 * @see com.ezendai.credit2.apply.service.AttachmentDetailService#findListByVO(com.ezendai.credit2.apply.vo.AttachmentDetailVO)
	 */
	@Override
	public List<AttachmentDetail> findListByVO(AttachmentDetailVO attachmentDetailVO) {
		return attachmentDetailDao.findListByVo(attachmentDetailVO);
	}

	/** 
	 * @param map
	 * @return
	 * @see com.ezendai.credit2.apply.service.AttachmentDetailService#exists(java.util.Map)
	 */
	@Override
	public boolean exists(Map<String, Object> map) {
		return attachmentDetailDao.exists(map);
	}

	/** 
	 * @param attachmentDetailVO
	 * @return
	 * @see com.ezendai.credit2.apply.service.AttachmentDetailService#findWithPg(com.ezendai.credit2.apply.vo.AttachmentDetailVO)
	 */
	@Override
	public Pager findWithPg(AttachmentDetailVO attachmentDetailVO) {
		return attachmentDetailDao.findWithPg(attachmentDetailVO);
	}

	/** 
	 * @param attachmentDetailVO
	 * @return
	 * @see com.ezendai.credit2.apply.service.AttachmentDetailService#get(com.ezendai.credit2.apply.vo.AttachmentDetailVO)
	 */
	@Override
	public AttachmentDetail get(AttachmentDetailVO attachmentDetailVO) {
		return attachmentDetailDao.get(attachmentDetailVO);
	}

	/** 
	 * @param id
	 * @return
	 * @see com.ezendai.credit2.apply.service.AttachmentDetailService#exists(java.lang.Long)
	 */
	@Override
	public boolean exists(Long id) {
		return attachmentDetailDao.exists(id);
	}

	/** 
	 * @param attachmentDetailVO
	 * @return
	 * @see com.ezendai.credit2.apply.service.AttachmentDetailService#update(com.ezendai.credit2.apply.vo.AttachmentDetailVO)
	 */
	@Override
	public int update(AttachmentDetailVO attachmentDetailVO) {
		return attachmentDetailDao.update(attachmentDetailVO);
	}

}
