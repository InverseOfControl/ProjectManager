/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.system.service;

import java.util.List;
import java.util.Map;

import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.system.model.BaseAreaProduct;
import com.ezendai.credit2.system.vo.BaseAreaProductVO;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author 00221921
 * @version $Id: BaseAreaProductService.java, v 0.1 2015年4月13日 下午3:20:03 00221921 Exp $
 */
public interface BaseAreaProductService {
	BaseAreaProduct insert(BaseAreaProduct baseAreaProduct);

	void deleteById(Long id);
	
	void deleteByProductId(Long productId);

	void deleteByIdList(BaseAreaProductVO baseAreaProductVO);

	int update(BaseAreaProductVO baseAreaProductVO);

	BaseAreaProduct get(Long id);

	List<BaseAreaProduct> findListByVO(BaseAreaProductVO baseAreaProductVO);

	boolean exists(Map<String, Object> map);

	Pager findWithPg(BaseAreaProductVO baseAreaProductVO);

	BaseAreaProduct get(BaseAreaProductVO baseAreaProductVO);

	boolean exists(Long id);
}
