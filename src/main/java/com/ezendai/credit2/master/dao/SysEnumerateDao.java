package com.ezendai.credit2.master.dao;

import java.util.List;

import com.ezendai.credit2.framework.dao.BaseDao;
import com.ezendai.credit2.master.model.SysEnumerate;
import com.ezendai.credit2.master.vo.SysEnumerateVO;

/**
 * 枚举仓库
 * 
 * @author chenqi
 * @version 1.0, 2014-06-23
 * @since 1.0
 */
public interface SysEnumerateDao extends BaseDao<SysEnumerate> {
	/**
	 * 
	 * <pre>
	 * 以数据类型为条件加载的数据字典数据
	 * </pre>
	 * @param enumType String 数据类型
	 * @return 数据字典数据
	 */
	List<SysEnumerate> findSysEnumerateListByType(String enumType);

	/**
	 * 
	 * <pre>
	 *  根据数据字典类型和Code取得备注
	 * </pre>
	 *
	 * @param sysEnumerateDto
	 * @return
	 */
	SysEnumerate findSysEnumerateByTypeAndCode(SysEnumerateVO sysEnumerateVO);
	/***
	 * 根据OPTION_MODULE类型的Code为前缀,查询OPTION_TYPE类型的List
	 * @param  enumType enumCode
	 * @return
	 * @see com.ezendai.credit2.master.dao.SysEnumerateDao#findSysEnumerateListByCode(com.ezendai.credit2.master.vo.SysEnumerateVO)
	 */
	List<SysEnumerate> findOptionTypeListByOptionModule(SysEnumerateVO sysEnumerateVO);

}
