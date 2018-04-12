package com.ezendai.credit2.system.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.framework.vo.BaseVO;
import com.ezendai.credit2.system.dao.SysPermissionDao;
import com.ezendai.credit2.system.model.SysPermission;

@Repository
public class SysPermissionDaoImpl extends BaseDaoImpl<SysPermission>  implements SysPermissionDao {
	@Override
	public SysPermission insert(SysPermission object) {
		return super.insert(object);
	}
	@Override
	public void deleteById(Long id) {
		super.deleteById(id);
	}
	@Override
	public Pager findWithPg(BaseVO vo) {
		return super.findWithPg(vo);
	}
	@Override
	public SysPermission get(Long id) {
		return super.get(id);
	}
	@Override
	public int update(BaseVO vo) {
		return super.update(vo);
	}
		
	/*   
	* 方法描述：根据角色Id获取权限列表
	* 创建人：张道松
	* 创建时间：2016-07-05    
	*/
	@Override
	public List<SysPermission> findListByRoleId(Long roleId){
		return getSqlSession().selectList(getIbatisMapperNameSpace() + ".findListByRoleId", roleId);
	}
}
