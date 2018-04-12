/**
 * 
 */
package com.ezendai.credit2.after.dao.impl;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.after.dao.OfferDao;
import com.ezendai.credit2.after.model.Offer;
import com.ezendai.credit2.after.vo.OfferVO;
import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;

/**
 * @author 00226557
 * @version v1.0
 */
@Repository
public class OfferDaoImpl extends BaseDaoImpl<Offer> implements OfferDao {

	@Override
	public void deleteByVO(OfferVO offerVO) {
		this.getSqlSession().selectOne(getIbatisMapperNameSpace()+".deleteByVO",offerVO);
	}

	/** 
	 * @param offerVO
	 * @return
	 * @see com.ezendai.credit2.after.dao.OfferDao#countExt(com.ezendai.credit2.after.vo.OfferVO)
	 */
	@Override
	public Integer count(OfferVO offerVO) {
		return this.getSqlSession().selectOne(getIbatisMapperNameSpace()+".count",offerVO);
	}

	@Override
	public int updateOfferTpp(OfferVO offerVo) {
		// TODO Auto-generated method stub
		return this.getSqlSession().update(getIbatisMapperNameSpace()+".updateOfferTpp",offerVo);
	}
}
