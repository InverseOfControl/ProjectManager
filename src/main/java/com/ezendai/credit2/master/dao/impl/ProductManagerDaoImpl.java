package com.ezendai.credit2.master.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;
import com.ezendai.credit2.framework.util.BeanUtil;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.dao.ProductManagerDao;
import com.ezendai.credit2.master.model.ProductManager;
import com.ezendai.credit2.master.model.ProductTypes;
import com.ezendai.credit2.master.vo.ProductManagerVO;

/**
 * 
 * @Description: 产品管理表
 * @author 张宜超
 * @date 2016年3月4日
 */
@Repository
public class ProductManagerDaoImpl extends BaseDaoImpl<ProductManager> implements ProductManagerDao {

	/**
	 * 获取数据列表
	 */
	@Override
	public Pager getProductList(ProductManagerVO vo) {
		Object bankCount = null; 
		Integer count = null;
		Pager page = new Pager();
		try{
			bankCount = getSqlSession().selectOne(getIbatisMapperNameSpace() + ".getProductManagerCount", vo);
			if(null != bankCount && bankCount != ""){
				count = Integer.parseInt(bankCount.toString());
			}else{
				count = 0;
			}
		
			page = vo.getPager();
			List<ProductManagerVO> productList = null;
		
			productList = getSqlSession().selectList(getIbatisMapperNameSpace() + ".getProductManagerPage", vo);
			BeanUtil.copyProperties(page, vo);
			if(null != productList){
				//分页前总数据
				page.setTotalCount(count);
				page.setResultList(productList);
			}
		}catch(RuntimeException e){
			e.printStackTrace();
		}
		return page;
	}

	/**
	 * 获取一条数据
	 */
	@Override
	public ProductManager getProduct(Long id) {
		
		return this.getSqlSession().selectOne(getIbatisMapperNameSpace()+".getProductManager",id);
	}

	/**
	 * 多条件获取数据
	 */
	@Override
	public List<ProductManager> getProductByConditions(
			ProductManagerVO vo) {
		List<ProductManager> list = getSqlSession().selectList(getIbatisMapperNameSpace() + ".getProductManagerByConditions", vo);
		return list;
	}

	/**
	 * 获取数据个数
	 */
	@Override
	public int getProductCount(ProductManagerVO vo) {
		
		return getSqlSession().selectOne(getIbatisMapperNameSpace() + ".getProductManagerCount", vo);
	}

	/**
	 * 添加一条数据
	 */
	@Override
	public int addProduct(ProductManagerVO vo) {
		
		return this.getSqlSession().insert(getIbatisMapperNameSpace()+".addProductManager",vo);
	}

	/**
	 * 更新一条数据
	 */
	@Override
	public int updateProduct(ProductManagerVO vo) {
		
		return this.getSqlSession().update(getIbatisMapperNameSpace()+".updateProductManager", vo);
	}

	/**
	 * 根据用户查询产品
	 */
	@Override
	public List<ProductTypes> selectProductsByUserId(Long id) {
		
		return getSqlSession().selectList(getIbatisMapperNameSpace() + ".selectProductsByUserId", id);
	}




	
}
