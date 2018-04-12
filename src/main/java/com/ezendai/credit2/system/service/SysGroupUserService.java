package com.ezendai.credit2.system.service;

import java.util.List;

import com.ezendai.credit2.system.model.SysGroupUser;
import com.ezendai.credit2.system.vo.SysGroupUserVO;

public interface SysGroupUserService {
	/**
	 * 查询员工与权限组对照列表
	 * @param sysGroupUserVO
	 * @return
	 */
	public List<SysGroupUser> findListByVO(SysGroupUserVO sysGroupUserVO);
	
	/**
	 * 修改员工与权限组对照列表
	 * @param sysGroupUserVO
	 * @return
	 */
	public void modifyGroupUserMap(SysGroupUserVO sysGroupUserVO);
	
	public  SysGroupUser findByVO(SysGroupUserVO sysGroupUserVO);
	public  List<SysGroupUser> findByVOTwo(SysGroupUserVO sysGroupUserVO);
	
}
