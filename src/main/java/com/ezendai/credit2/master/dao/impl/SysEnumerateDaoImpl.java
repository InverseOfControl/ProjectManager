package com.ezendai.credit2.master.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;
import com.ezendai.credit2.master.dao.SysEnumerateDao;
import com.ezendai.credit2.master.model.SysEnumerate;
import com.ezendai.credit2.master.vo.SysEnumerateVO;

@Repository
public class SysEnumerateDaoImpl extends BaseDaoImpl<SysEnumerate> implements SysEnumerateDao {

	public static final String FIND_BY_TYPE = ".findSysEnumerateListByType";

	public static final String FIND_BY_TYPE_CODE = ".findSysEnumerateByTypeAndCode";
	
	@Override
	public List<SysEnumerate> findSysEnumerateListByType(String enumType) {
		return getSqlSession().selectList(getIbatisMapperNameSpace() + FIND_BY_TYPE, enumType);
	}

	@Override
	public SysEnumerate findSysEnumerateByTypeAndCode(SysEnumerateVO sysEnumerateVO) {
		return (SysEnumerate) getSqlSession().selectOne(getIbatisMapperNameSpace() + FIND_BY_TYPE_CODE, sysEnumerateVO);
	}
	/***
	 * 根据code获取枚举值
	 * 必须指定enumType
	 * Code前缀 取得枚举值 例如:获取code为 7 开头
	 * @param  enumType enumCode
	 * @return
	 * @see com.ezendai.credit2.master.dao.SysEnumerateDao#findSysEnumerateListByCode(com.ezendai.credit2.master.vo.SysEnumerateVO)
	 */
	@Override
	public List<SysEnumerate> findOptionTypeListByOptionModule(SysEnumerateVO sysEnumerateVo) {
		return getSqlSession().selectList(getIbatisMapperNameSpace() + ".findOptionTypeListByOptionModule", sysEnumerateVo);
	}
}
