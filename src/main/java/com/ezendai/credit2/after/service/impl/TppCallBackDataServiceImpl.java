/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.after.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.after.dao.TppCallBackDataDao;
import com.ezendai.credit2.after.model.TppCallBackData;
import com.ezendai.credit2.after.service.TppCallBackDataService;
import com.ezendai.credit2.after.vo.TppCallBackDataVO;
import com.ezendai.credit2.framework.util.Pager;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author 00226557
 * @version $Id: TppCallBackDataServiceImpl.java, v 0.1 2014年12月10日 上午11:32:41 00226557 Exp $
 */
@Service
public class TppCallBackDataServiceImpl implements TppCallBackDataService {

	@Autowired
	private TppCallBackDataDao tppCallBackDataDao;
	/** 
	 * @param offer
	 * @return
	 * @see com.ezendai.credit2.after.service.TppCallBackDataService#insert(com.ezendai.credit2.after.model.TppCallBackData)
	 */
	@Override
	public TppCallBackData insert(TppCallBackData tppCallBackData) {
		return tppCallBackDataDao.insert(tppCallBackData);
	}

	@Override
	public boolean exists(Map<String, Object> map) {
		return tppCallBackDataDao.exists(map);
	}

	/** 
	 * @param TppCallBackDataVO
	 * @return
	 * @see com.ezendai.credit2.after.service.TppCallBackDataService#findListByVo(com.ezendai.credit2.after.vo.TppCallBackDataVO)
	 */
	@Override
	public List<TppCallBackData> findListByVo(TppCallBackDataVO tppCallBackDataVO) {
		return tppCallBackDataDao.findListByVo(tppCallBackDataVO);
	}

	/** 
	 * @param tppCallBackDataVO
	 * @return
	 * @see com.ezendai.credit2.after.service.TppCallBackDataService#update(com.ezendai.credit2.after.vo.TppCallBackDataVO)
	 */
	@Override
	public int update(TppCallBackDataVO tppCallBackDataVO) {
		return tppCallBackDataDao.update(tppCallBackDataVO);
	}

	/** 
	 * @param offerVO
	 * @return
	 * @see com.ezendai.credit2.after.service.TppCallBackDataService#findWithPg(com.ezendai.credit2.after.vo.OfferVO)
	 */
	@Override
	public Pager findWithPg(TppCallBackDataVO tppCallBackDataVO) {
		return tppCallBackDataDao.findWithPg(tppCallBackDataVO);
	}

	/** 
	 * @param offerVO
	 * @return
	 * @see com.ezendai.credit2.after.service.TppCallBackDataService#count(com.ezendai.credit2.after.vo.OfferVO)
	 */
	@Override
	public Integer count(TppCallBackDataVO tppCallBackDataVO) {
		return tppCallBackDataDao.count(tppCallBackDataVO);
	}
}
