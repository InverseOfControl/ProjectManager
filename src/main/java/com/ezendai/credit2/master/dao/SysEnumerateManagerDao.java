package com.ezendai.credit2.master.dao;

import java.util.List;

import com.ezendai.credit2.framework.dao.BaseDao;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.model.SysEnumerateManager;
import com.ezendai.credit2.master.vo.SysEnumerateManagerVO;

/**
 * 
 * @Description: ibatis中对接xml的dao与mybatis中的mapper同一作用
 * @author 张宜超
 * @date 2016年2月2日
 */
public interface SysEnumerateManagerDao extends BaseDao<SysEnumerateManager>{

	/**
	 * 
	 * @Description: 获取枚举列表
	 * @param @param vo
	 * @param @return   
	 * @return List<SysEnumerate>  
	 * @throws
	 * @author 张宜超
	 * @date 2016年2月2日
	 */
	public Pager getSysEnumerateList(SysEnumerateManagerVO vo);
	
	/**
	 * 
	 * @Description: 根据枚举id获取枚举信息
	 * @param id
	 * @param   
	 * @return SysEnumerate  
	 * @throws
	 * @author 张宜超
	 * @date 2016年2月2日
	 */
	SysEnumerateManager getSysEnumerate(Long id);
	
	/**
	 * 
	 * @Description: 多条件查询一条枚举信息
	 * @param @param vo
	 * @param @return   
	 * @return SysEnumerate  
	 * @throws
	 * @author 张宜超
	 * @date 2016年2月2日
	 */
	List<SysEnumerateManager> getSysEnumerateByConditions(SysEnumerateManagerVO vo);
	
	/**
	 * 
	 * @Description: 添加枚举信息
	 * @param @param SysEnumerate   
	 * @return void  
	 * @throws
	 * @author 张宜超
	 * @date 2016年2月2日
	 */
	void addSysEnumerate(SysEnumerateManagerVO SysEnumerate);
	
	/**
	 * 
	 * @Description: 修改枚举信息
	 * @param @param vo
	 * @param @return   
	 * @return int  
	 * @throws
	 * @author 张宜超
	 * @date 2016年2月2日
	 */
	int updateSysEnumerate(SysEnumerateManagerVO vo);
	
	/**
	 * 
	 * @Description: 删除枚举信息
	 * @param @param id
	 * @param @return   
	 * @return int  
	 * @throws
	 * @author 张宜超
	 * @date 2016年2月17日
	 */
	int deleteSysEnumerate(Long id);
}

