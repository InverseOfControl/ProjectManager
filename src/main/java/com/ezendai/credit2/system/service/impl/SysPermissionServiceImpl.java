package com.ezendai.credit2.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.framework.exception.BusinessException;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.system.dao.SysPermissionDao;
import com.ezendai.credit2.system.model.SysLog;
import com.ezendai.credit2.system.model.SysPermission;
import com.ezendai.credit2.system.service.SysLogService;
import com.ezendai.credit2.system.service.SysPermissionService;
import com.ezendai.credit2.system.vo.SysPermissionVO;


@Service
public class SysPermissionServiceImpl implements SysPermissionService{
	@Autowired
	private SysPermissionDao dao;
	
	@Autowired
	private SysLogService  sysLogService;

	@Override
	public SysPermission insert(SysPermission sysPermission) {
		SysPermission insert = dao.insert(sysPermission);
		if(insert != null){
			sysLogService.insert(new SysLog(EnumConstants.OptionModule.PERMISSION_MANAGE.getValue(), EnumConstants.OptionType.ADD_PERMISSION.getValue(),"ID："+insert.getId()+ "——" +" NAME:" + insert.getName()));
		}else{
			throw new BusinessException("新增权限菜单失败");
		}
		return insert;
	}

	@Override
	public void delete(Long id) {
		if(id != null){
			 dao.deleteById(id);
			 sysLogService.insert(new SysLog(EnumConstants.OptionModule.PERMISSION_MANAGE.getValue(), EnumConstants.OptionType.DELETE_PERMISSION.getValue(),"ID：" + id));
		}else{
			throw new BusinessException("删除权限菜单失败");
		}
		
		}

	@Override
	public int update(SysPermissionVO sysPermissionVO) {
		 int update = dao.update(sysPermissionVO);
		 if(update > 0){
				sysLogService.insert(new SysLog(EnumConstants.OptionModule.PERMISSION_MANAGE.getValue(), EnumConstants.OptionType.UPDATE_PERMISSION.getValue(),"ID："+sysPermissionVO.getId()+ "——" +" NAME：" + sysPermissionVO.getName()));
			}else{
				throw new BusinessException("修改权限菜单失败");
			}
		return update;
	}

	@Override
	public Pager findwithPG(SysPermissionVO sysPermissionVO) {
		return dao.findWithPg(sysPermissionVO);
	}
	@Override
	public SysPermission get(Long id) {
		return dao.get(id);
	}
	@Override
	public List<SysPermission> findListByVO(SysPermissionVO sysPermission) {
		return dao.findListByVo(sysPermission);
	}
	
	/*   
	* 方法描述：根据角色Id获取权限列表
	* 创建人：张道松
	* 创建时间：2016-07-05    
	*/
	@Override
	public List<SysPermission> findListByRoleId(Long roleId){
		return dao.findListByRoleId(roleId);
	}
}
