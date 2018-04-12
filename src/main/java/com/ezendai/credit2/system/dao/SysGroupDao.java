package com.ezendai.credit2.system.dao;

import java.util.List;

import com.ezendai.credit2.framework.dao.BaseDao;
import com.ezendai.credit2.system.model.SysGroup;

public interface SysGroupDao extends BaseDao<SysGroup> {
	/**
	 * 获取分组信息根据用户Id
	 * @return
	 * @throws Exception
	 */
	public List<SysGroup> findGroupByUserId(Long userId) throws Exception;
}
