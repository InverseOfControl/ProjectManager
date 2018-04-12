package com.ezendai.credit2.apply.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.apply.dao.ProductDao;
import com.ezendai.credit2.apply.model.Product;
import com.ezendai.credit2.apply.vo.ProductVO;
import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;
import com.ezendai.credit2.framework.util.BeanUtil;
import com.ezendai.credit2.framework.util.Pager;
/**
 * 
 *
 *
 * @author dl
 * @version 
 * 基本操作
 */

@Repository
public class ProductDaoImpl extends BaseDaoImpl<Product> implements ProductDao {
    /**
     * 
     * <pre>
     * 根据用户ID查找贷款类型
     * </pre>     *
     * @param userId
     * @return
     */
    public List<Product> findLoanTypeByUserId(Long userId){
        return this.getSqlSession().selectList(getIbatisMapperNameSpace()+".selectByUserId",userId);
    }

	/** 
	 * @param productVO
	 * @return
	 * @see com.ezendai.credit2.apply.dao.ProductDao#findWithPg(com.ezendai.credit2.apply.vo.ProductVO)
	 */
	@Override
	public Pager findWithPgByUserId(ProductVO productVO) {
		Object count = getSqlSession().selectOne(getIbatisMapperNameSpace() + ".countUnionUser", productVO);
		int totalCount = Integer.parseInt(count.toString());

		Pager pg = productVO.getPager();
		pg.setTotalCount(totalCount);
		pg.calStart();
		List<Product> rstList = null;
		try {
			rstList = getSqlSession().selectList(getIbatisMapperNameSpace() + ".findWithPgByUserId", productVO);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		BeanUtil.copyProperties(pg, productVO);
		pg.setTotalCount(totalCount);
		pg.setResultList(rstList);
		return pg;
	}

	/** 
	 * @param userId
	 * @return
	 * @see com.ezendai.credit2.apply.dao.ProductDao#findProductTypeByUserId(java.lang.Long)
	 */
	@Override
	public List<Product> findProductTypeByUserId(Long userId) {
		return this.getSqlSession().selectList(getIbatisMapperNameSpace()+".selectProductTypeByUserId",userId);
	}

	/** 
	 * @param userId
	 * @return
	 * @see com.ezendai.credit2.apply.dao.ProductDao#findProductsByUserId(java.lang.Long)
	 */
	@Override
	public List<Product> findProductsByUserId(Long userId) {
		return this.getSqlSession().selectList(getIbatisMapperNameSpace()+".selectProductsByUserId",userId);
	}


	/** 
	 * @param prouductName
	 * @return
	 * @see com.ezendai.credit2.apply.dao.ProductDao#existsByProductName(java.lang.String)
	 */
	@Override
	public boolean existsByProductName(String prouductName) {
		Object count = getSqlSession().selectOne(getIbatisMapperNameSpace() + ".existsByProductName", prouductName);
		int totalCount = Integer.parseInt(count.toString());
		return totalCount > 0 ? true : false;
	}

	
}
