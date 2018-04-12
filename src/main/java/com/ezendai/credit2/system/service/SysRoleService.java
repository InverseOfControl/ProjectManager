package com.ezendai.credit2.system.service;

import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.system.model.SysRole;
import com.ezendai.credit2.system.vo.SysRoleVO;

public interface SysRoleService {
	
	SysRole insert(SysRole sysRole);
	
	void delete(Long id);
	
	int update(SysRoleVO sysRoleVO);
	
	Pager findwithPG(SysRoleVO sysRoleVO);

	SysRole get(Long id);
	
}
