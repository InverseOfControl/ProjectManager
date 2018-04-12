/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.after.dao.impl;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.after.dao.TppCallBackDataDao;
import com.ezendai.credit2.after.model.TppCallBackData;
import com.ezendai.credit2.after.vo.TppCallBackDataVO;
import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author 00226557
 * @version $Id: TppCallBackDataDaoImpl.java, v 0.1 2014年12月10日 下午1:14:36 00226557 Exp $
 */
@Repository
public class TppCallBackDataDaoImpl  extends BaseDaoImpl<TppCallBackData>  implements TppCallBackDataDao {

	/** 
	 * @param tppCallBackDataVO
	 * @return
	 * @see com.ezendai.credit2.after.dao.TppCallBackDataDao#count(com.ezendai.credit2.after.vo.TppCallBackDataVO)
	 */
	@Override
	public Integer count(TppCallBackDataVO tppCallBackDataVO) {
		return this.getSqlSession().selectOne(getIbatisMapperNameSpace()+".count",tppCallBackDataVO);
	}

}
