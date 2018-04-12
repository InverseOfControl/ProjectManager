package com.ezendai.credit2.system.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;
import com.ezendai.credit2.framework.vo.BaseVO;
import com.ezendai.credit2.system.dao.SysGroupDao;
import com.ezendai.credit2.system.model.SysGroup;

@Repository
public class SysGroupDaoImpl extends BaseDaoImpl<SysGroup> implements SysGroupDao {
	@Override
	public int update(BaseVO vo) {
		return super.update(vo);
	}
	@Override
	public void deleteById(Long id) {
		super.deleteById(id);
	}
	@Override
	public List<SysGroup> findGroupByUserId(Long userId) throws Exception {
		return getSqlSession().selectList(getIbatisMapperNameSpace() + ".findGroupByUserId", userId);
	}
}
