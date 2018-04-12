package com.ezendai.credit2.system.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;
import com.ezendai.credit2.system.dao.SysGroupUserDao;
import com.ezendai.credit2.system.model.SysGroupUser;
import com.ezendai.credit2.system.vo.SysGroupUserVO;

@Repository
public class SysGroupUserDaoImpl extends BaseDaoImpl<SysGroupUser> implements SysGroupUserDao {
	
	/**
	 * 修改员工与权限组对照列表
	 * @param sysGroupUserVO
	 * @return
	 */
	@Override
	@Transactional
	public void modifyGroupUserMap(SysGroupUserVO sysGroupUserVO) {
		//执行批量解除绑定关系
		if (sysGroupUserVO.getRemoveGroupIdList() != null && sysGroupUserVO.getRemoveGroupIdList().size() > 0) {
			getSqlSession().delete(getIbatisMapperNameSpace() + ".deleteByGroupIdList", sysGroupUserVO);
		}
		List<Long> addGroupIdList = sysGroupUserVO.getAddGroupIdList();
		if (addGroupIdList != null && addGroupIdList.size() > 0) {
			for (int i=0;i<addGroupIdList.size();i++) {
				Long groupId = addGroupIdList.get(i);
				sysGroupUserVO.setGroupId(groupId);
				getSqlSession().insert(getIbatisMapperNameSpace() + ".insert", sysGroupUserVO);
			}
		}
	}
	@Override
	public SysGroupUser findByVO(SysGroupUserVO sysGroupUserVO) {
		return getSqlSession().selectOne(getIbatisMapperNameSpace() + ".findByVO", sysGroupUserVO);
	}
	
	@Override
	public List<SysGroupUser> findByVOTwo(SysGroupUserVO sysGroupUserVO) {
		return getSqlSession().selectList(getIbatisMapperNameSpace() + ".findByVOTwo", sysGroupUserVO);
	}
}
