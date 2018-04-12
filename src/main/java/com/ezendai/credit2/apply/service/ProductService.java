package com.ezendai.credit2.apply.service;

import java.util.List;
import java.util.Map;

import com.ezendai.credit2.apply.model.Product;
import com.ezendai.credit2.apply.vo.ProductVO;
import com.ezendai.credit2.framework.util.Pager;

public interface ProductService {
	Product insert(Product product);

    void deleteById(Long id);

    void deleteByIdList(ProductVO productVO);

    void update(ProductVO productVO);

    Product get(Long id);

    List<Product> findListByVO(ProductVO productVO);

    boolean exists(Map<String, Object> map);

    Pager findWithPg(ProductVO productVO);

    Product get(ProductVO productVO);

    boolean exists(Long id);
    /***
     * 根据用户ID查找贷款类型
     * <pre>
     * 
     * </pre>
     *
     * @param userId
     * @return
     */
   public  List<Product> findLoanTypeByUserId(Long userId);

   /**
    * 
    * <pre>
    * 根据用户ID,获取其所有产品列表(分页)
    * </pre>
    *
    * @param userId
    * @return
    */
   Pager findWithPgByUserId(Long userId, int rows, int page);
   
   /**
    * 
    * <pre>
    * 逻辑删除
    * </pre>
    *
    * @param id
    */
   void deleteByIdLogically(Long id);
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
   /**
    * 
    * <pre>
    * 根据用户ID，查询其所属借款类型(车贷或小企贷)
    * </pre>
    *
    * @param userId
    * @return
    */
   List<Product> findProductTypeByUserId(Long userId);
   
   /**
    * 
    * <pre>
    * 根据用户ID，查询其可以操作的所有产品
    * </pre>
    *
    * @param userId
    * @return
    */
   List<Product> findProductsByUserId(Long userId);
}
