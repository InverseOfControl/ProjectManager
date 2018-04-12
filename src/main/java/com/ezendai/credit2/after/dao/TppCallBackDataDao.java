/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.after.dao;

import com.ezendai.credit2.after.model.TppCallBackData;
import com.ezendai.credit2.after.vo.TppCallBackDataVO;
import com.ezendai.credit2.framework.dao.BaseDao;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author 00226557
 * @version $Id: TppCallBackDataDao.java, v 0.1 2014年12月10日 下午1:13:01 00226557 Exp $
 */
public interface TppCallBackDataDao extends BaseDao<TppCallBackData> {
	
	/**通过vo查询单日符合条件记录的总和**/
	Integer count(TppCallBackDataVO tppCallBackDataVO);
}
