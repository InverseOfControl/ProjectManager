package com.ezendai.credit2.apply.dao;

import java.util.List;

import com.ezendai.credit2.apply.model.Product;
import com.ezendai.credit2.apply.vo.ProductVO;
import com.ezendai.credit2.framework.dao.BaseDao;
import com.ezendai.credit2.framework.util.Pager;

public interface ProductDao extends BaseDao<Product> {
    /**
     * 
     * <pre>
     * 根据用户ID查找贷款类型
     * </pre>
     *
     * @param userId
     * @return
     */
    public List<Product> findLoanTypeByUserId(Long userId);
    
    Pager findWithPgByUserId(ProductVO productVO);
    
    List<Product> findProductTypeByUserId(Long userId);
    
    List<Product> findProductsByUserId(Long userId);
    /**
     * 
     * <pre>
     * 通过product_name看系统中存在相应的记录
     * </pre>
     *
     * @param id
     * @return
     */
    boolean existsByProductName(String prouductName);
}
