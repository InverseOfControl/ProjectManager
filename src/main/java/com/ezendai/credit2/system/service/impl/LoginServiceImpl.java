package com.ezendai.credit2.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezendai.credit2.system.model.SysUser;
import com.ezendai.credit2.system.service.LoginService;
import com.ezendai.credit2.system.service.SysUserService;
import com.ezendai.credit2.system.vo.SysUserVO;

@Service
public class LoginServiceImpl implements LoginService {
	@Autowired
	private SysUserService sysUserService;

	@Override
	public SysUser getSysUserByLoginName(String loginName) {
		return sysUserService.getSysUserByLoginName(loginName);
	}

	@Override
	@Transactional
	public void updateSysUser(SysUserVO sysUserVO) {
		sysUserService.updateSysUser(sysUserVO, null);
	}

	@Override
	public List<String> findPermissionCodeList(Long id) {
		List<String> permissionCodeList = sysUserService.findPermissionCodeList(id);
		return permissionCodeList;
	}
}
