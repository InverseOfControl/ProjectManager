package com.ezendai.credit2.master.service;

import java.util.List;

import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.model.SysEnumerateManager;
import com.ezendai.credit2.master.vo.SysEnumerateManagerVO;

/**
 * 
 * @Description: 枚举service
 * @author 张宜超
 * @date 2016年2月2日
 */
public interface SysEnumerateManagerService {

	/**
	 * 
	 * @Description: 根据条件获取枚举列表
	 * @param @param vo
	 * @param @return   
	 * @return List<SysEnumerate>  
	 * @throws
	 * @author 张宜超
	 * @date 2016年2月2日
	 */
	Pager getSysEnumerateList(SysEnumerateManagerVO vo);
	
	/**
	 * 
	 * @Description: 获取单个枚举信息
	 * @param @param id
	 * @param @return   
	 * @return SysEnumerate  
	 * @throws
	 * @author 张宜超
	 * @date 2016年2月2日
	 */
	SysEnumerateManager getSysEnumerate(Long id);
	
	/**
	 * 
	 * @Description: 添加一条新枚举信息
	 * @param @param SysEnumerate   
	 * @return void  
	 * @throws
	 * @author 张宜超
	 * @date 2016年2月2日
	 */
	void addSysEnumerate(SysEnumerateManagerVO SysEnumerate);
	
	/**
	 * 
	 * @Description: 修改某枚举信息
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
	 * @Description: 多条件查询枚举信息
	 * @param @param vo
	 * @param @return   
	 * @return int  
	 * @throws
	 * @author 张宜超
	 * @date 2016年2月2日
	 */
	List<SysEnumerateManager> getSysEnumerateByConditions(SysEnumerateManagerVO vo);
	
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
