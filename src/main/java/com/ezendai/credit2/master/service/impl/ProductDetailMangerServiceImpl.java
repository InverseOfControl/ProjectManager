package com.ezendai.credit2.master.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.dao.ProductDetailManagerDao;
import com.ezendai.credit2.master.model.ProductDetailManager;
import com.ezendai.credit2.master.service.ProductDetailManagerServcie;
import com.ezendai.credit2.master.vo.ProductDetailManagerVO;

/**
 * 
 * @Description: 产品列表
 * @author 张宜超
 * @date 2016年3月8日
 */
@Service
//@Transactional
public class ProductDetailMangerServiceImpl implements ProductDetailManagerServcie {

	@Autowired
	private ProductDetailManagerDao ProductDetailMangerDao;
	
	/**
	 * 得到产品列表page
	 */
	@Override
	public Pager getProductDetailList(ProductDetailManagerVO vo) {
		
		return ProductDetailMangerDao.getProductDetailList(vo);
	}

	/**
	 * 获取一条产品
	 */
	@Override
	public ProductDetailManager getProductDetail(Long id) {
		
		return ProductDetailMangerDao.getProductDetail(id);
	}

	/**
	 * 根据条件获取数据
	 */
	@Override
	public List<ProductDetailManager> getProductDetailByConditions(ProductDetailManagerVO vo) {
		
		return ProductDetailMangerDao.getProductDetailByConditions(vo);
	}

	/**
	 * 获取数据条数
	 */
	@Override
	public int getProductDetailCount(ProductDetailManagerVO vo) {
		
		return ProductDetailMangerDao.getProductDetailCount(vo);
	}

	@Override
	public int updateProductDetail(ProductDetailManagerVO vo) {

		return ProductDetailMangerDao.updateProductDetail(vo);
	}

	@Override
	public ProductDetailManager addProductDetail(ProductDetailManager pdm) {
		
		return ProductDetailMangerDao.addProductDetail(pdm);
	}


}
