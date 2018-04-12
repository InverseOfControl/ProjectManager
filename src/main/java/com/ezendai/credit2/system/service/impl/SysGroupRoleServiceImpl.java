package com.ezendai.credit2.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.system.dao.SysGroupRoleDao;
import com.ezendai.credit2.system.model.SysGroupRole;


/**   
*    
* 项目名称：credit2-main   
* 类名称：SysGroupRoleServiceImpl   
* 类描述：   
* 创建人：liboyan   
* 创建时间：2016年1月26日 下午4:05:34   
* 修改人：liboyan   
* 修改时间：2016年1月26日 下午4:05:34   
* 修改备注：   
* @version    
*    
*/
@Service
public class SysGroupRoleServiceImpl implements SysGroupRoleService {
	@Autowired
	private SysGroupRoleDao dao;
	

@Override
public void insert(SysGroupRole sysGroupRole) {
	dao.insert(sysGroupRole);
}

}
