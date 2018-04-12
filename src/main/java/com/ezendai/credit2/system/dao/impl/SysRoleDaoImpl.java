package com.ezendai.credit2.system.dao.impl;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.framework.vo.BaseVO;
import com.ezendai.credit2.system.dao.SysRoleDao;
import com.ezendai.credit2.system.model.SysRole;

/**   
*    
* 项目名称：credit2-main   
* 类名称：SysRoleDaoImpl   
* 类描述：   
* 创建人：liboyan   
* 创建时间：2016年1月26日 下午4:53:32   
* 修改人：liboyan   
* 修改时间：2016年1月26日 下午4:53:32   
* 修改备注：   
* @version    
*    
*/
@Repository
public class SysRoleDaoImpl extends BaseDaoImpl<SysRole> implements SysRoleDao{
	@Override
	public SysRole insert(SysRole object) {
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
	public SysRole get(Long id) {
		return super.get(id);
	}
	@Override
	public int update(BaseVO vo) {
		return super.update(vo);
	}
	@Override
	public Long getId() {
		return this.getSqlSession().selectOne(getIbatisMapperNameSpace() + ".getId");
	}
		
}
