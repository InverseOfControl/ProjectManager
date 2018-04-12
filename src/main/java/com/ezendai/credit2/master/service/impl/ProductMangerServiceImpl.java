package com.ezendai.credit2.master.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.dao.ProductManagerDao;
import com.ezendai.credit2.master.model.ProductManager;
import com.ezendai.credit2.master.model.ProductTypes;
import com.ezendai.credit2.master.service.ProductManagerServcie;
import com.ezendai.credit2.master.vo.ProductManagerVO;

/**
 * 
 * @Description: 产品列表
 * @author 张宜超
 * @date 2016年3月8日
 */
@Service
@Transactional
public class ProductMangerServiceImpl implements ProductManagerServcie {

	@Autowired
	private ProductManagerDao productMangerDao;
	
	/**
	 * 得到产品列表page
	 */
	@Override
	public Pager getProductList(ProductManagerVO vo) {
		
		return productMangerDao.getProductList(vo);
	}

	/**
	 * 获取一条产品
	 */
	@Override
	public ProductManager getProduct(Long id) {
		
		return productMangerDao.getProduct(id);
	}

	/**
	 * 根据条件获取数据
	 */
	@Override
	public List<ProductManager> getProductByConditions(ProductManagerVO vo) {
		
		return productMangerDao.getProductByConditions(vo);
	}

	/**
	 * 获取数据条数
	 */
	@Override
	public int getProductCount(ProductManagerVO vo) {
		
		return productMangerDao.getProductCount(vo);
	}

	/**
	 * 更新一条是数据
	 */
	@Override
	public int updateProduct(ProductManagerVO vo) {
		
		return productMangerDao.updateProduct(vo);
	}

	/**
	 * 添加一条数据
	 */
	@Override
	public int addProduct(ProductManagerVO vo) {
		
		return productMangerDao.addProduct(vo);
	}

	/**
	 * 根据用户id查询产品
	 */
	@Override
	public List<ProductTypes> selectProductsByUserId(Long id) {
		
		return productMangerDao.selectProductsByUserId(id);
	}

}
