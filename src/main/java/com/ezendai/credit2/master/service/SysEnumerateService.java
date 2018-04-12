package com.ezendai.credit2.master.service;

import java.util.List;

import com.ezendai.credit2.master.model.SysEnumerate;

/**
 * 枚举服务
 * 
 * @author chenqi
 * @version 1.0, 2014-06-23
 * @since 1.0
 */
public interface SysEnumerateService {
	/**
	 * 
	 * <pre>
	 * 以数据类型为条件加载数据字典数据
	 * </pre>
	 * @param enumType String 数据类型
	 * @return 数据字典数据
	 */
	List<SysEnumerate> findSysEnumerateListByType(String enumType);
	
	/**
	 * 
	 * <pre>
	 * 根据数据字典类型和Code取得备注
	 * </pre>
	 *
	 * @param enumType
	 * @param enumCode
	 * @return
	 */
	String findEnumValue(String enumType, Integer enumCode);
	
	
	/**
	 * 
	 * <pre>
	 * 通过字典类型，与代码数组获取 List<SysEnumerate>
	 * </pre>
	 *
	 * @param enumType
	 * @param enumCodes
	 * @return
	 */
	List<SysEnumerate> findEnumList(String enumType, Integer[] enumCodes);
	
	/**
	 * 
	 * <pre>
	 * 通过字典类型，与排除代码数组获取 List<SysEnumerate>
	 * </pre>
	 *
	 * @param enumType
	 * @param exclusionEnumCodes
	 * @return
	 */
	List<SysEnumerate> findEnumListExclusionEnumCodes(String enumType, Integer[] exclusionEnumCodes);
	
}
