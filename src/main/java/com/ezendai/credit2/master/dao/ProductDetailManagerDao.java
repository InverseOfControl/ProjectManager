package com.ezendai.credit2.master.dao;

import java.util.List;
import com.ezendai.credit2.framework.dao.BaseDao;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.model.ProductDetailManager;
import com.ezendai.credit2.master.vo.ProductDetailManagerVO;

/**
 * 
 * @Description: 产品管理表
 * @author 张宜超
 * @date 2016年3月4日
 */
public interface ProductDetailManagerDao extends BaseDao<ProductDetailManager>{

	public Pager getProductDetailList(ProductDetailManagerVO vo);
	
	ProductDetailManager getProductDetail(Long id);
	
	List<ProductDetailManager> getProductDetailByConditions(ProductDetailManagerVO vo);
	
	int getProductDetailCount(ProductDetailManagerVO vo);
	
	ProductDetailManager addProductDetail(ProductDetailManager pdm);
	
	int updateProductDetail(ProductDetailManagerVO vo);
	
}

