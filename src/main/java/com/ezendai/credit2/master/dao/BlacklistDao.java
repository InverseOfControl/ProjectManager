package com.ezendai.credit2.master.dao;

import com.ezendai.credit2.framework.dao.BaseDao;
import com.ezendai.credit2.master.model.Blacklist;
import com.ezendai.credit2.master.vo.BlacklistVO;

/**
 * 黑名单仓库
 * 
 * @author chenqi
 * @version 1.0, 2014-08-28
 * @since 1.0
 */
public interface BlacklistDao extends BaseDao<Blacklist> {

	int updateLimitDays(BlacklistVO blacklistVO);

}
