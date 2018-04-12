/**
 * 
 */
package com.ezendai.credit2.after.dao;

import com.ezendai.credit2.after.model.Offer;
import com.ezendai.credit2.after.vo.OfferVO;
import com.ezendai.credit2.framework.dao.BaseDao;

/**
 * @author 00226557
 * @version v1.0
 */
public interface OfferDao extends BaseDao<Offer>{
	 void deleteByVO(OfferVO offerVO);
	 
	 Integer count(OfferVO offerVO);

	int updateOfferTpp(OfferVO offerVo);
}
