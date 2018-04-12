package com.ezendai.credit2.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.system.dao.SysGroupDao;
import com.ezendai.credit2.system.model.SysGroup;
import com.ezendai.credit2.system.service.SysGroupService;
import com.ezendai.credit2.system.vo.SysGroupVO;

/**   
*    
* 项目名称：credit2-main   
* 类名称：SysGroupServiceImpl   
* 类描述：   
* 创建人：liboyan   
* 创建时间：2016年1月26日 下午4:09:30   
* 修改人：liboyan   
* 修改时间：2016年1月26日 下午4:09:30   
* 修改备注：   
* @version    
*    
*/
@Service
public class SysGroupServiceImpl implements SysGroupService {

	@Autowired
	private SysGroupDao sysGroupDao;

	@Override
	public List<SysGroup> findListByVO(SysGroupVO sysGroupVO) {
		return sysGroupDao.findListByVo(sysGroupVO);
	}
	@Override
	public SysGroup insert(SysGroup sysGroup) {
		return sysGroupDao.insert(sysGroup);
	}
	@Override
	public List<SysGroup> findGroupByUserId(Long userId) throws Exception {
		return sysGroupDao.findGroupByUserId(userId);
	}
}
