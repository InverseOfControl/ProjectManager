package com.ezendai.credit2.system.dao;

import java.util.List;

import com.ezendai.credit2.framework.dao.BaseDao;
import com.ezendai.credit2.system.model.SysPermission;

public interface SysPermissionDao extends BaseDao<SysPermission>{


	/*   
	* 方法描述：根据角色Id获取权限列表
	* 创建人：张道松
	* 创建时间：2016-07-05    
	*/
	List<SysPermission> findListByRoleId(Long roleId);
}
