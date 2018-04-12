package com.ezendai.credit2.after.service;

import java.util.List;

import com.ezendai.credit2.after.model.Offer;
import com.ezendai.credit2.after.vo.OfferVO;
import com.ezendai.credit2.framework.util.Pager;

/**
 * @author LinSanfu
 */

public interface OfferService {
	int update(OfferVO offerVO);
	
	void deleteByVO(OfferVO offerVO);
	
	Offer insert(Offer offer);
	
	/** 通过查询vo查找符合条件的Offer集合 */
	List<Offer> findListByVo(OfferVO offerVO);
	
	/** 通过查询vo查找符合条件的Offer集合 */
	Pager findWithPg(OfferVO offerVO);
	
	/**通过vo查询单日符合条件记录的总和**/
	Integer count(OfferVO offerVO);
	
	/**获取offer对象通过offer id**/
	Offer get(Long offerId);
}
