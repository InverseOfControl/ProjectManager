package com.ezendai.credit2.system.dao;

import com.ezendai.credit2.framework.dao.BaseDao;
import com.ezendai.credit2.framework.vo.BaseVO;
import com.ezendai.credit2.system.model.SysSerialNum;


/**
 * <pre>
 * 序列号数据库层服务
 * </pre>
 *
 * @author fangqingyuan
 * @version $Id: SysSerialNumDao.java, v 0.1 2014-7-5 下午3:10:27 fangqingyuan Exp $
 */
public interface SysSerialNumDao extends BaseDao<SysSerialNum> {
	/** 更新数据{mapper.xml需要实现} */
	int update(BaseVO vo);
}
