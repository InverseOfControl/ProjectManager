package com.ezendai.credit2.master.service;

import java.util.List;

import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.model.ProductManager;
import com.ezendai.credit2.master.model.ProductTypes;
import com.ezendai.credit2.master.vo.ProductManagerVO;

/**
 * 
 * @Description: TODO
 * @author 张宜超
 * @date 2016年3月8日
 */

public interface ProductManagerServcie {
	
	/**
	 * 
	 * @Description: 获取产品列表
	 * @param @param vo
	 * @param @return   
	 * @return Pager  
	 * @throws
	 * @author 张宜超
	 * @date 2016年3月8日
	 */
	Pager getProductList(ProductManagerVO vo);
	
	/**
	 * 
	 * @Description: 根据id获取产品
	 * @param @param id
	 * @param @return   
	 * @return ProductManager  
	 * @throws
	 * @author 张宜超
	 * @date 2016年3月8日
	 */
	ProductManager getProduct(Long id);
	
	/**
	 * 
	 * @Description: 条件查询
	 * @param @param vo
	 * @param @return   
	 * @return List<ProductManager>  
	 * @throws
	 * @author 张宜超
	 * @date 2016年3月8日
	 */
	List<ProductManager> getProductByConditions(ProductManagerVO vo);
	
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
	int getProductCount(ProductManagerVO vo);
	
	/**
	 * 
	 * @Description: 更新一条产品数据
	 * @param @param vo
	 * @param @return   
	 * @return int  
	 * @throws
	 * @author 张宜超
	 * @date 2016年3月8日
	 */
	int updateProduct(ProductManagerVO vo);
	
	/**
	 * 
	 * @Description: 添加一条产品
	 * @param @param vo
	 * @param @return   
	 * @return int  
	 * @throws
	 * @author 张宜超
	 * @date 2016年3月9日
	 */
	int addProduct(ProductManagerVO vo);
	
	/**
	 * 
	 * @Description: 根据用户查询产品
	 * @param @param id
	 * @param @return   
	 * @return List<ProductManager>  
	 * @throws
	 * @author 张宜超
	 * @date 2016年3月10日
	 */
	List<ProductTypes> selectProductsByUserId(Long id);
}
