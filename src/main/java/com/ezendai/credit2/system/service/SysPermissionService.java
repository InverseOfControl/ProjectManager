package com.ezendai.credit2.system.service;

import java.util.List;

import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.system.model.SysPermission;
import com.ezendai.credit2.system.vo.SysPermissionVO;

public interface SysPermissionService {
	
	SysPermission insert(SysPermission sysPermission);
	
	void delete(Long id);
	
	int update(SysPermissionVO sysPermissionVO);
	
	Pager findwithPG(SysPermissionVO sysPermissionVO);

	SysPermission get(Long id);
	
	List<SysPermission> findListByVO(SysPermissionVO sysPermission);
	
	/*   
	* 方法描述：根据角色Id获取权限列表
	* 创建人：张道松
	* 创建时间：2016-07-05    
	*/
	List<SysPermission> findListByRoleId(Long roleId);
	
}
