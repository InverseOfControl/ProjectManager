package com.ezendai.credit2.apply.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.apply.dao.ProductDetailDao;
import com.ezendai.credit2.apply.model.ProductDetail;
import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;

@Repository
public class ProductDetailDaoImpl extends BaseDaoImpl<ProductDetail> implements ProductDetailDao {

	/** 
	 * @param productId
	 * @return
	 * @see com.ezendai.credit2.apply.dao.ProductDetailDao#findTermsByProductId(java.lang.Long)
	 */
	@Override
	public List<ProductDetail> findTermsByProductId(Long productId) {
		List<ProductDetail> rstList = getSqlSession().selectList(getIbatisMapperNameSpace() + ".findTermsByProductId", productId);
		return rstList;
	}

}
