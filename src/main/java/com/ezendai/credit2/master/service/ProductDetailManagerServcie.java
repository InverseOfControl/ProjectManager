package com.ezendai.credit2.master.service;

import java.util.List;

import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.model.ProductDetailManager;
import com.ezendai.credit2.master.vo.ProductDetailManagerVO;

/**
 * 
 * @Description: TODO
 * @author 张宜超
 * @date 2016年3月8日
 */

public interface ProductDetailManagerServcie {
	
	/**
	 * 
	 * @Description: 获取产品详情列表
	 * @param @param vo
	 * @param @return   
	 * @return Pager  
	 * @throws
	 * @author 张宜超
	 * @date 2016年3月8日
	 */
	Pager getProductDetailList(ProductDetailManagerVO vo);
	
	/**
	 * 
	 * @Description: 根据id获取产品详情
	 * @param @param id
	 * @param @return   
	 * @return ProductDetailManager  
	 * @throws
	 * @author 张宜超
	 * @date 2016年3月8日
	 */
	ProductDetailManager getProductDetail(Long id);
	
	/**
	 * 
	 * @Description: 条件查询
	 * @param @param vo
	 * @param @return   
	 * @return List<ProductDetailManager>  
	 * @throws
	 * @author 张宜超
	 * @date 2016年3月8日
	 */
	List<ProductDetailManager> getProductDetailByConditions(ProductDetailManagerVO vo);
	
	/**
	 * 
	 * @Description: 查询数据条数
	 * @param @param vo
	 * @param @return   
	 * @return int  
	 * @throws
	 * @author 张宜超
	 * @date 2016年3月8日
	 */
	int getProductDetailCount(ProductDetailManagerVO vo);
	
	/**
	 * 
	 * @Description: 更新一条产品详情数据
	 * @param @param vo
	 * @param @return   
	 * @return int  
	 * @throws
	 * @author 张宜超
	 * @date 2016年3月8日
	 */
	int updateProductDetail(ProductDetailManagerVO vo);
	
	/**
	 * 
	 * @Description: 新增一条产品详情数据
	 * @param @param vo
	 * @param @return   
	 * @return int  
	 * @throws
	 * @author 徐安龙
	 * @date 2016年7月13日
	 */
	ProductDetailManager addProductDetail(ProductDetailManager pdm);
}
