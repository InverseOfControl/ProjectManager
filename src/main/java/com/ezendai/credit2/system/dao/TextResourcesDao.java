/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.system.dao;

import java.util.List;

import com.ezendai.credit2.framework.dao.BaseDao;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.system.model.TextResource;
import com.ezendai.credit2.system.vo.TextResourceVO;

 
public interface TextResourcesDao extends BaseDao<TextResource> {
 
	
	
	//根据类型查询文字资源
	List<TextResource> findTextResourcesByType(Integer type);
	
	
	Pager findTextResourcesWithPG(TextResourceVO textResourceVO);
	/**
	 * 更新文字资源 参数为model
	 * @param textResource
	 */
	public void updateTextResource(TextResource textResource);
	
	
	public List<TextResource> findTextResourceWithCondition(TextResourceVO textResourceVO);
}
