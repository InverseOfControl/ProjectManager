/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.apply.service;

import java.util.List;
import java.util.Map;

import com.ezendai.credit2.apply.model.Guarantee;
import com.ezendai.credit2.apply.vo.GuaranteeVO;
import com.ezendai.credit2.framework.util.Pager;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author xiaoxiong
 * @version $Id: GuaranteeService.java, v 0.1 2014年6月24日 下午4:30:15 xiaoxiong Exp $
 */
public interface GuaranteeService {

	Guarantee insert(Guarantee guarantee);

	void deleteById(Long id);

	void deleteByIdList(GuaranteeVO guaranteeVO);

	Guarantee get(Long id);

	List<Guarantee> findListByVo(GuaranteeVO guaranteeVO);

	boolean exists(Map<String, Object> map);

	Pager findWithPg(GuaranteeVO guaranteeVO);

	Guarantee get(GuaranteeVO guaranteeVO);

	boolean exists(Long id);
	/***
	 * 根据用户名查询担保人
	 * <pre>
	 * 
	 * </pre>
	 *
	 * @param personId
	 * @return
	 */
   public  List<Guarantee> findListByPersonId(Long personId, Long loanId);
   /***
    * 根据贷款ID查询担保人
    * <pre>
    * 
    * </pre>
    *
    * @param personId
    * @return
    */
   public  List<Guarantee> findListByLoanId(Long loanId);

   int update(GuaranteeVO guaranteeVO);
}
