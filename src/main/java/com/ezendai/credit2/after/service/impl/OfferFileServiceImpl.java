/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.after.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.after.dao.OfferFileDao;
import com.ezendai.credit2.after.model.OfferFile;
import com.ezendai.credit2.after.service.OfferFileService;
import com.ezendai.credit2.after.vo.OfferFileVO;
/**
 * <pre>
 * 报盘文件业务逻辑接口实现
 * </pre>
 *
 * @author zhangshihai
 * @version $Id: OfferFileServiceImpl.java, v 0.1 2014年12月11日 下午5:23:56 00221921 Exp $
 */
import java.util.List;

@Service
public class OfferFileServiceImpl implements OfferFileService {
	@Autowired
	private OfferFileDao offerFileDao;

	@Override
	public OfferFile insert(OfferFile offerFile) {
		return offerFileDao.insert(offerFile);
	}

	@Override
	public List<OfferFile> findListByVo(OfferFileVO offerFileVO) {
		return offerFileDao.findListByVo(offerFileVO);
	}

	

}
