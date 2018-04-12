/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.system.dao.TextResourcesDao;
import com.ezendai.credit2.system.model.TextResource;
import com.ezendai.credit2.system.service.TextResourcesService;
import com.ezendai.credit2.system.vo.TextResourceVO;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author 00221921
 * @version $Id: BaseAreaProductServiceImpl.java, v 0.1 2015年4月13日 下午3:20:50 00221921 Exp $
 */
@Service
public class TextResourceServiceImpl  implements TextResourcesService{
	@Autowired
	private TextResourcesDao textResourceDao;

	@Override
	public List<TextResource> findTextResourcesByType(Integer type) {
		// TODO Auto-generated method stub
		return textResourceDao.findTextResourcesByType(type);
	}

	@Override
	public Pager findTextResourcesWithPG(TextResourceVO textResourceVO) {
		// TODO Auto-generated method stub
		return textResourceDao.findTextResourcesWithPG(textResourceVO);
	}

	@Override
	public Long insertTextResource(TextResource textResource)
			throws Exception {
		TextResource text =  textResourceDao.insert(textResource);
		return text.getId();
		
	}

	@Override
	public void updateTextResource(TextResource textResource)
			throws Exception {
		textResourceDao.updateTextResource(textResource);
	}

	@Override
	public List<TextResource> findTextResourceWithCondition(
			TextResourceVO textResourceVO) {
		return textResourceDao.findTextResourceWithCondition(textResourceVO);
	}

	@Override
	public TextResource findTextResourcesById(Long id) {
		return textResourceDao.get(id);
	}

	 
	
}
