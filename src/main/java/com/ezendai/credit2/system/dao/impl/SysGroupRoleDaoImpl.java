package com.ezendai.credit2.system.dao.impl;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;
import com.ezendai.credit2.system.dao.SysGroupRoleDao;
import com.ezendai.credit2.system.model.SysGroupRole;

/**   
*    
* 项目名称：credit2-main   
* 类名称：SysGroupRoleDaoImpl   
* 类描述：   
* 创建人：liboyan   
* 创建时间：2016年1月26日 下午4:05:40   
* 修改人：liboyan   
* 修改时间：2016年1月26日 下午4:05:40   
* 修改备注：   
* @version    
*    
*/
@Repository
public class  SysGroupRoleDaoImpl extends BaseDaoImpl<SysGroupRole> implements SysGroupRoleDao {
		
	@Override
	public SysGroupRole insert(SysGroupRole object) {
		
		return super.insert(object);
	}

}
