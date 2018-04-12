/**
 * 
 */
package com.ezendai.credit2.after.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.after.dao.OfferDao;
import com.ezendai.credit2.after.model.Offer;
import com.ezendai.credit2.after.service.OfferService;
import com.ezendai.credit2.after.vo.OfferVO;
import com.ezendai.credit2.framework.util.Pager;

/**
 * @author 00226557
 * @version 1.0
 */
@Service
public class OfferServiceImpl implements OfferService {
	@Autowired
	private  OfferDao offerDao;

	/**
	 * 删除今日未报盘记录
	 */
	@Override
	public void deleteByVO(OfferVO offerVO) {
		offerDao.deleteByVO(offerVO);
	}

	@Override
	public Offer insert(Offer offer) {
		return offerDao.insert(offer);
	}

	@Override
	public List<Offer> findListByVo(OfferVO offerVO) {
		return offerDao.findListByVo(offerVO);
	}

	@Override
	public Pager findWithPg(OfferVO offerVO) {
		return offerDao.findWithPg(offerVO);
	}

	/** 
	 * @param offerVO
	 * @return
	 * @see com.ezendai.credit2.after.service.OfferService#countExt(com.ezendai.credit2.after.vo.OfferVO)
	 */
	@Override
	public Integer count(OfferVO offerVO) {
		return offerDao.count(offerVO);
	}

	/** 
	 * @param loanVO
	 * @return
	 * @see com.ezendai.credit2.after.service.OfferService#update(com.ezendai.credit2.apply.vo.LoanVO)
	 */
	@Override
	public int update(OfferVO offerVO) {
		return offerDao.update(offerVO);
	}

	/** 
	 * @param offerId
	 * @return
	 * @see com.ezendai.credit2.after.service.OfferService#get(java.lang.Long)
	 */
	@Override
	public Offer get(Long offerId) {
		return offerDao.get(offerId);
	}

}
