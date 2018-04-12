package com.ezendai.credit2.system.service;

import java.util.List;

import com.ezendai.credit2.system.model.SysUser;
import com.ezendai.credit2.system.vo.SysUserVO;

public interface LoginService {

	/**
	 * 
	 * <pre>
	 * 根据用户名得到用户信息
	 * </pre>
	 *
	 * @param loginName
	 * @return
	 */
	SysUser getSysUserByLoginName(String loginName);

	/**
	 * 
	 * <pre>
	 * 更新用户信息
	 * </pre>
	 *
	 * @param sysUser
	 */
	void updateSysUser(SysUserVO sysUserVO);

	/**
	 * 
	 * <pre>
	 * 根据登录ID得到权限码列表
	 * </pre>
	 *
	 * @param id
	 * @return
	 */
	List<String> findPermissionCodeList(Long id);
}
