package com.ezendai.credit2.master.dao;

import java.util.List;

import com.ezendai.credit2.framework.dao.BaseDao;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.model.ProductManager;
import com.ezendai.credit2.master.model.ProductTypes;
import com.ezendai.credit2.master.vo.ProductManagerVO;

/**
 * 
 * @Description: 产品管理表
 * @author 张宜超
 * @date 2016年3月4日
 */
public interface ProductManagerDao extends BaseDao<ProductManager>{

	public Pager getProductList(ProductManagerVO vo);
	
	ProductManager getProduct(Long id);
	
	List<ProductTypes> selectProductsByUserId(Long id);
	
	List<ProductManager> getProductByConditions(ProductManagerVO vo);
	
	int getProductCount(ProductManagerVO vo);
	
	int addProduct(ProductManagerVO vo);
	
	int updateProduct(ProductManagerVO vo);
	
}

