package com.ezendai.credit2.apply.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.apply.dao.ProductDetailDao;
import com.ezendai.credit2.apply.model.ProductDetail;
import com.ezendai.credit2.apply.service.ProductDetailService;
import com.ezendai.credit2.apply.vo.ProductDetailVO;
import com.ezendai.credit2.framework.util.Pager;

@Service
public class ProductDetailServiceImpl implements ProductDetailService {
	@Autowired
	ProductDetailDao productDetailDao;

	@Override
	public ProductDetail insert(ProductDetail productDetail) {
		return productDetailDao.insert(productDetail);
	}

	@Override
	public void deleteById(Long id) {
		productDetailDao.deleteById(id);
	}

	@Override
	public void deleteByIdList(ProductDetailVO productDetailVO) {
		productDetailDao.deleteByIdList(productDetailVO);
	}

	@Override
	public void update(ProductDetailVO productDetailVO) {
		productDetailDao.update(productDetailVO);
	}

	@Override
	public ProductDetail get(Long id) {
		return productDetailDao.get(id);
	}

	@Override
	public List<ProductDetail> findListByVO(ProductDetailVO productDetailVO) {
		return productDetailDao.findListByVo(productDetailVO);
	}

	@Override
	public boolean exists(Map<String, Object> map) {
		return productDetailDao.exists(map);
	}

	@Override
	public Pager findWithPg(ProductDetailVO productDetailVO) {
		return productDetailDao.findWithPg(productDetailVO);
	}

	@Override
	public ProductDetail getProductDetailByVO(ProductDetailVO productDetailVO) {
		return productDetailDao.get(productDetailVO);
	}

	@Override
	public boolean exists(Long id) {
		return productDetailDao.exists(id);
	}

	/** 
	 * @param productId
	 * @return
	 * @see com.ezendai.credit2.apply.service.ProductDetailService#findTermsByProductId(java.lang.Long)
	 */
	@Override
	public List<ProductDetail> findTermsByProductId(Long productId) {
		return productDetailDao.findTermsByProductId(productId);
	}
	
}
