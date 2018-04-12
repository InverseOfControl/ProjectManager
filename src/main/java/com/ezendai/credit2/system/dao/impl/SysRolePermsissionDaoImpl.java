package com.ezendai.credit2.system.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;
import com.ezendai.credit2.system.dao.SysRolePermissionDao;
import com.ezendai.credit2.system.model.SysRolePermission;
import com.ezendai.credit2.system.vo.SysRoleVO;

/**   
*    
* 项目名称：credit2-main   
* 类名称：SysRolePermsissionDaoImpl   
* 类描述：   
* 创建人：liboyan   
* 创建时间：2016年1月27日 上午10:59:53   
* 修改人：liboyan   
* 修改时间：2016年1月27日 上午10:59:53   
* 修改备注：   
* @version    
*    
*/
@Repository
public class SysRolePermsissionDaoImpl extends BaseDaoImpl<SysRolePermission> implements
		SysRolePermissionDao {

	@Override
	public SysRolePermission insert(SysRolePermission object) {
		return super.insert(object);
	}
	@Override
	public List<Long> queryMyPermissionList(Long roleId) {
		return this.getSqlSession().selectList(getIbatisMapperNameSpace() + ".queryMyPermissionList",roleId);
	}
	@Override
	public void deleteByPromissionIdList(SysRoleVO sysRoleVO) {
		this.getSqlSession().delete(getIbatisMapperNameSpace() + ".deleteByPromissionIdList", sysRoleVO);
		
	}
	
	public void deleteByRoleId(Long roleId){
		this.getSqlSession().delete(getIbatisMapperNameSpace() + ".deleteByRoleId", roleId);
	}
}

