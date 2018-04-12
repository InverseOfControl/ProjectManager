package com.ezendai.credit2.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezendai.credit2.framework.exception.BusinessException;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.system.dao.SysGroupDao;
import com.ezendai.credit2.system.dao.SysGroupRoleDao;
import com.ezendai.credit2.system.dao.SysRoleDao;
import com.ezendai.credit2.system.dao.SysRolePermissionDao;
import com.ezendai.credit2.system.model.SysGroup;
import com.ezendai.credit2.system.model.SysGroupRole;
import com.ezendai.credit2.system.model.SysLog;
import com.ezendai.credit2.system.model.SysRole;
import com.ezendai.credit2.system.model.SysRolePermission;
import com.ezendai.credit2.system.service.SysLogService;
import com.ezendai.credit2.system.service.SysRoleService;
import com.ezendai.credit2.system.vo.SysRoleVO;


/**   
*    
* 项目名称：credit2-main   
* 类名称：SysRoleServiceImpl   
* 类描述：   
* 创建人：liboyan   
* 创建时间：2016年1月26日 下午4:37:35   
* 修改人：liboyan   
* 修改时间：2016年1月26日 下午4:37:35   
* 修改备注：   
* @version    
*    
*/
@Service
public class SysRoleServiceImpl implements SysRoleService {
	@Autowired
	private SysLogService sysLogService;
	
	@Autowired
	private SysRoleDao dao;
	
	@Autowired
	private SysGroupDao sysGroupDao;
	
	@Autowired
	private SysGroupRoleDao sysGroupRoleDao;
	
	@Autowired
	private SysRolePermissionDao  sysRolePermissionDao;
	
	@Transactional(readOnly =  false , rollbackFor = DataAccessException. class) 
	@Override
	public SysRole insert(SysRole sysRole) {
		SysRole  sysRole1 = null;
		SysGroupRole sysGroupRole = new SysGroupRole();
		SysGroup sysGroup = new SysGroup();
		Long id = dao.getId();
		if(id != null){
			sysRole.setId(id);
			sysGroup.setId(id);
			sysGroup.setCode(sysRole.getCode());
			sysGroup.setName(sysRole.getName());
			sysGroup.setMemo(sysRole.getMemo());
			sysGroup.setStatus(sysRole.getStatus());
			sysGroup.setIsDeleted(sysRole.getIsDeleted());
			sysGroupRole.setId(id);
			sysGroupRole.setGroupId(id);
			sysGroupRole.setRoleId(id);
			sysGroupRole.setVersion((long)1);
			sysGroupDao.insert(sysGroup);
			sysGroupRoleDao.insert(sysGroupRole);
			sysRole1 =dao.insert(sysRole);
			for (int i = 0; i < sysRole.getSysPermissionList().length; i++) {
				SysRolePermission sysRolePermission = new SysRolePermission();
				sysRolePermission.setRoleId(id);
				sysRolePermission.setPermissionId(sysRole.getSysPermissionList()[i]);
				sysRolePermissionDao.insert(sysRolePermission);
			}
		}
		if(sysRole1 != null){
			sysLogService.insert(new SysLog(EnumConstants.OptionModule.ROLE_MANAGE.getValue(), EnumConstants.OptionType.ADD_ROLE.getValue(),"ID："+ sysRole1.getId() + "——Name:"+ sysRole1.getName()));
		}else{
			throw new BusinessException("新增角色失败");
		}
		return sysRole1;
	}

	@Transactional(readOnly =  false , rollbackFor = DataAccessException. class) 
	@Override
	public void delete(Long id) {
		if(id != null){
			dao.deleteById(id);
			sysGroupDao.deleteById(id);
			sysRolePermissionDao.deleteByRoleId(id);
			sysLogService.insert(new SysLog(EnumConstants.OptionModule.ROLE_MANAGE.getValue(), EnumConstants.OptionType.DELETE_ROLE.getValue(),"ID："+ id));
		}else{
			throw new BusinessException("删除角色失败");
		}
	}
	@Transactional(readOnly =  false , rollbackFor = DataAccessException. class) 
	@Override
	public int update(SysRoleVO sysRoleVO) {
		//已绑定权限
		List<Long> permissionIdList = sysRolePermissionDao.queryMyPermissionList(sysRoleVO.getId());
		if(permissionIdList.size() > 0){
			sysRoleVO.setRemovePermissionList(permissionIdList);
			sysRolePermissionDao.deleteByIdList(sysRoleVO);
		}
		for (int i = 0; i < sysRoleVO.getSysPermissionList().length; i++) {
			SysRolePermission  sysRolePermission = new SysRolePermission();
			sysRolePermission.setRoleId(sysRoleVO.getId());
			sysRolePermission.setPermissionId(sysRoleVO.getSysPermissionList()[i]);;
			sysRolePermissionDao.insert(sysRolePermission);
		}
		int count = dao.update(sysRoleVO);
		
		sysGroupDao.update(sysRoleVO);  
		if(count > 0){
			sysLogService.insert(new SysLog(EnumConstants.OptionModule.ROLE_MANAGE.getValue(), EnumConstants.OptionType.UPDATE_ROLE.getValue(),"ID："+ sysRoleVO.getId() + "——Name:"+ sysRoleVO.getName()));
		}else{
			throw new BusinessException("修改角色失败");
		}
		return count;
	}

	@Override
	public Pager findwithPG(SysRoleVO sysRoleVO) {
		return dao.findWithPg(sysRoleVO);
	}

	public SysRole get(Long id) {
		return dao.get(id);
	}
}
