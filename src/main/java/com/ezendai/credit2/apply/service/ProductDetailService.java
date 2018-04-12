package com.ezendai.credit2.apply.service;

import java.util.List;
import java.util.Map;

import com.ezendai.credit2.apply.model.ProductDetail;
import com.ezendai.credit2.apply.vo.ProductDetailVO;
import com.ezendai.credit2.framework.util.Pager;

/**
 * @author zhuyiguo
 * @version $Id: ProductDetailService.java, v 0.1 2014年6月26日 上午9:14:30 zhuyiguo Exp $
 */
public interface ProductDetailService {

	ProductDetail insert(ProductDetail productDetail);

	void deleteById(Long id);

	void deleteByIdList(ProductDetailVO productDetailVO);

	void update(ProductDetailVO productDetailVO);

	ProductDetail get(Long id);

	List<ProductDetail> findListByVO(ProductDetailVO productDetailVO);

	boolean exists(Map<String, Object> map);

	Pager findWithPg(ProductDetailVO productDetailVO);

	ProductDetail getProductDetailByVO(ProductDetailVO productDetailVO);

	boolean exists(Long id);
	
	List<ProductDetail> findTermsByProductId(Long productId);
}
