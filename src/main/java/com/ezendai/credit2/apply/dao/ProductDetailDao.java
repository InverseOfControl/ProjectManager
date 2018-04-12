package com.ezendai.credit2.apply.dao;

import java.util.List;

import com.ezendai.credit2.apply.model.ProductDetail;
import com.ezendai.credit2.framework.dao.BaseDao;

/**
 * @author zhuyiguo
 * @version $Id: ProductDetailDao.java, v 0.1 2014年6月26日 上午9:15:11 zhuyiguo Exp $
 */
public interface ProductDetailDao extends BaseDao<ProductDetail> {
	List<ProductDetail> findTermsByProductId(Long productId);
}
