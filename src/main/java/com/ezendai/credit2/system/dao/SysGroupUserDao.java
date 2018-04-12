package com.ezendai.credit2.system.dao;

import java.util.List;

import com.ezendai.credit2.framework.dao.BaseDao;
import com.ezendai.credit2.system.model.SysGroupUser;
import com.ezendai.credit2.system.vo.SysGroupUserVO;

public interface SysGroupUserDao extends BaseDao<SysGroupUser> {

	/**
	 * 修改员工与权限组对照列表
	 * @param sysGroupUserVO
	 * @return
	 */
	public void modifyGroupUserMap(SysGroupUserVO sysGroupUserVO);
	
	public SysGroupUser findByVO(SysGroupUserVO sysGroupUserVO);
	
	public List<SysGroupUser> findByVOTwo(SysGroupUserVO sysGroupUserVO);
	
}
