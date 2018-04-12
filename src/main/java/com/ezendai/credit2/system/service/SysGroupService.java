package com.ezendai.credit2.system.service;

import java.util.List;

import com.ezendai.credit2.system.model.SysGroup;
import com.ezendai.credit2.system.vo.SysGroupVO;

/**   
*    
* 项目名称：credit2-main   
* 类名称：SysGroupService   
* 类描述：   
* 创建人：liboyan   
* 创建时间：2016年1月26日 下午4:06:48   
* 修改人：liboyan   
* 修改时间：2016年1月26日 下午4:06:48   
* 修改备注：   
* @version    
*    
*/
public interface SysGroupService {
	/**
	 * 查询权限组列表
	 * @param sysUserVo
	 * @return
	 */
	public List<SysGroup> findListByVO(SysGroupVO sysGroupVO);
	
	
	SysGroup insert(SysGroup sysGroup);
	
	public List<SysGroup> findGroupByUserId(Long userId) throws Exception;
}
