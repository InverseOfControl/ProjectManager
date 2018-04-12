package com.ezendai.credit2.system.controller;

import java.util.List;

public interface SysRolePermissionSerivce {
	List<Long> queryMyPermissionList(Long roleId);
}
