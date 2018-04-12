package com.ezendai.credit2.system.dao;

import java.util.List;

import com.ezendai.credit2.framework.dao.BaseDao;
import com.ezendai.credit2.system.model.SysRolePermission;
import com.ezendai.credit2.system.vo.SysRoleVO;


/**   
*    
* 项目名称：credit2-main   
* 类名称：SysRolePermissionDao   
* 类描述：   
* 创建人：liboyan   
* 创建时间：2016年1月27日 上午10:59:59   
* 修改人：liboyan   
* 修改时间：2016年1月27日 上午10:59:59   
* 修改备注：   
* @version    
*    
*/
public interface SysRolePermissionDao extends  BaseDao<SysRolePermission>{
	
	List<Long> queryMyPermissionList(Long roleId);
	
	void deleteByPromissionIdList(SysRoleVO sysRoleVO);
	
	void deleteByRoleId(Long roleId);
	
	
	
}
