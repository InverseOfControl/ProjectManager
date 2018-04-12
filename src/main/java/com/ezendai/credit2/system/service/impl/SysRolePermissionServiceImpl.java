package com.ezendai.credit2.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.system.controller.SysRolePermissionSerivce;
import com.ezendai.credit2.system.dao.SysRolePermissionDao;

/**   
*    
* 项目名称：credit2-main   
* 类名称：SysRolePermissionServiceImpl   
* 类描述：   
* 创建人：liboyan   
* 创建时间：2016年1月27日 上午11:45:57   
* 修改人：liboyan   
* 修改时间：2016年1月27日 上午11:45:57   
* 修改备注：   
* @version    
*    
*/
@Service
public class SysRolePermissionServiceImpl implements SysRolePermissionSerivce {

	@Autowired
	private SysRolePermissionDao sysRolePermissionDao;
	
	
	@Override
	public List<Long> queryMyPermissionList(Long roleId) {
		
		return sysRolePermissionDao.queryMyPermissionList(roleId);
	}

}
