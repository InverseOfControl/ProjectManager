package com.ezendai.credit2.master.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;
import com.ezendai.credit2.framework.util.BeanUtil;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.dao.ProductDetailManagerDao;
import com.ezendai.credit2.master.model.ProductDetailManager;
import com.ezendai.credit2.master.vo.ProductDetailManagerVO;
import com.ezendai.credit2.master.vo.ProductManagerVO;

/**
 * 
 * @Description: 产品明细表
 * @author 张宜超
 * @date 2016年3月8日
 */
@Repository
public class ProductDetailManagerDaoImpl extends BaseDaoImpl<ProductDetailManager> implements ProductDetailManagerDao {

	@Override
	public Pager getProductDetailList(ProductDetailManagerVO vo) {
		Object bankCount = null; 
		Integer count = null;
		Pager page = new Pager();
		try{
			bankCount = getSqlSession().selectOne(getIbatisMapperNameSpace() + ".getProductDetailManagerCount", vo);
			if(null != bankCount && bankCount != ""){
				count = Integer.parseInt(bankCount.toString());
			}else{
				count = 0;
			}
		
			page = vo.getPager();
			List<ProductManagerVO> productList = null;
		
			productList = getSqlSession().selectList(getIbatisMapperNameSpace() + ".getProductDetailManagerPage", vo);
			BeanUtil.copyProperties(page, vo);
			if(null != productList){
				//分页前总数据
				page.setTotalCount(count);
				page.setResultList(productList);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return page;
	}

	@Override
	public ProductDetailManager getProductDetail(Long id) {
		
		return this.getSqlSession().selectOne(getIbatisMapperNameSpace()+".getProductDetailManager",id);
	}

	@Override
	public List<ProductDetailManager> getProductDetailByConditions(
			ProductDetailManagerVO vo) {
		
		List<ProductDetailManager> list = getSqlSession().selectList(getIbatisMapperNameSpace() + ".getProductDetailManagerByConditions", vo);
		return list;
	}

	@Override
	public int getProductDetailCount(ProductDetailManagerVO vo) {
		return getSqlSession().selectOne(getIbatisMapperNameSpace() + ".getProductDetailManagerCount", vo);
	}

	@Override
	public ProductDetailManager addProductDetail(ProductDetailManager pdm) {
		return super.insert(pdm);
	}

	@Override
	public int updateProductDetail(ProductDetailManagerVO vo) {
		return getSqlSession().update(getIbatisMapperNameSpace() + ".updateProductDetailManager", vo);
	}



}
