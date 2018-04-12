package com.ezendai.credit2.apply.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezendai.credit2.apply.dao.ProductDao;
import com.ezendai.credit2.apply.model.Product;
import com.ezendai.credit2.apply.service.ProductService;
import com.ezendai.credit2.apply.vo.ProductVO;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.enumerate.EnumConstants;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductDao productDao;

	/**
	    * 
	    * <pre>
	    * 根据用户ID查找贷款类型
	    * </pre>
	    *
	    * @param userId
	    * @return
	    */
	@Override
	public List<Product> findLoanTypeByUserId(Long userId) {
		return productDao.findLoanTypeByUserId(userId);
	}

	@Override
	public Product insert(Product product) {
		// TODO Auto-generated method stub
		return productDao.insert(product);
	}

	@Override
	public void deleteById(Long id) {
		productDao.deleteById(id);
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteByIdList(ProductVO productVO) {
		productDao.deleteByIdList(productVO);
		// TODO Auto-generated method stub

	}

	@Override
	@Transactional
	public void update(ProductVO productVO) {
		productDao.update(productVO);
		// TODO Auto-generated method stub

	}

	@Override
	public Product get(Long id) {
		// TODO Auto-generated method stub
		return productDao.get(id);
	}

	@Override
	public List<Product> findListByVO(ProductVO productVO) {
		// TODO Auto-generated method stub
		return productDao.findListByVo(productVO);
	}

	@Override
	public boolean exists(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return productDao.exists(map);
	}

	@Override
	public Pager findWithPg(ProductVO productVO) {
		// TODO Auto-generated method stub
		return productDao.findWithPg(productVO);
	}

	@Override
	public Product get(ProductVO productVO) {
		// TODO Auto-generated method stub
		return productDao.get(productVO);
	}

	@Override
	public boolean exists(Long id) {
		// TODO Auto-generated method stub
		return productDao.exists(id);
	}

	/** 
	 * @param userId
	 * @param rows
	 * @param page
	 * @return
	 * @see com.ezendai.credit2.apply.service.ProductService#findWithPgByUserId(java.lang.Long, int, int)
	 */
	@Override
	public Pager findWithPgByUserId(Long userId, int rows, int page) {
		ProductVO vo = new ProductVO();
		vo.setUserId(userId);
		List<Integer> statusList = new ArrayList<Integer>();
		statusList.add(EnumConstants.ProductStatus.VALID.getValue());
		statusList.add(EnumConstants.ProductStatus.INVALID.getValue());
		vo.setStatusList(statusList);
		
		Pager p = new Pager();
		p.setRows(rows);
		p.setPage(page);
		p.setSidx("PRODUCT.ID");
		p.setSort("DESC");
		vo.setPager(p);
		return productDao.findWithPgByUserId(vo);
	}

	/** 
	 * @param id
	 * @see com.ezendai.credit2.apply.service.ProductService#deleteByIdLogically(java.lang.Long)
	 */
	@Override
	public void deleteByIdLogically(Long id) {
		ProductVO productVO = new ProductVO();
		productVO.setStatus(-1);
		productDao.update(productVO);
	}

	/** 
	 * @param userId
	 * @return
	 * @see com.ezendai.credit2.apply.service.ProductService#findProductTypeByUserId(java.lang.Long)
	 */
	@Override
	public List<Product> findProductTypeByUserId(Long userId) {
		return productDao.findProductTypeByUserId(userId);
	}

	/** 
	 * @param userId
	 * @return
	 * @see com.ezendai.credit2.apply.service.ProductService#findProductsByUserId(java.lang.Long)
	 */
	@Override
	public List<Product> findProductsByUserId(Long userId) {
		return productDao.findProductsByUserId(userId);
	}



	/** 
	 * @param prouductName
	 * @return
	 * @see com.ezendai.credit2.apply.service.ProductService#existsByProductName(java.lang.String)
	 */
	@Override
	public boolean existsByProductName(String prouductName) {
		return productDao.existsByProductName(prouductName);
	}
}
