package com.ezendai.credit2.system.assembler;

import com.ezendai.credit2.system.model.SysUser;
import com.ezendai.credit2.system.vo.SysUserVO;

/**
 * 
 * <pre>
 * 员工信息	VO/Model转换
 * </pre>
 *
 * @author zhangshihai
 * @version $Id: SysUserAssembler.java, v 0.1 2014年8月1日 下午2:49:11 zhangshihai Exp $
 */
public class SysUserAssembler {

	/**
	 * 
	 * <pre>
	 * Model转为VO
	 * </pre>
	 *
	 * @param sysUser
	 * @return
	 */
	public static SysUserVO transferModel2VO (SysUser sysUser) {
		if (sysUser == null) {
			return null;
		}
		
		SysUserVO sysUserVO = new SysUserVO();
		sysUserVO.setId(sysUser.getId());
		sysUserVO.setSalesTeamId(sysUser.getSalesTeamId());
		sysUserVO.setLoginName(sysUser.getLoginName());
		sysUserVO.setName(sysUser.getName());
		sysUserVO.setEmail(sysUser.getEmail());
		sysUserVO.setTelephone(sysUser.getTelephone());
		sysUserVO.setCellphone(sysUser.getCellphone());
		sysUserVO.setLoginRetry(sysUser.getLoginRetry());
		sysUserVO.setSignPassword(sysUser.getSignPassword());
		sysUserVO.setSignPasswordExpireDate(sysUser.getSignPasswordExpireDate());
		sysUserVO.setLastLoginTime(sysUser.getLastLoginTime());
		sysUserVO.setLastLoginIp(sysUser.getLastLoginIp());
		sysUserVO.setUserType(sysUser.getUserType());
		sysUserVO.setIsDeleted(sysUser.getIsDeleted());
		sysUserVO.setStatus(sysUser.getStatus());
		sysUserVO.setCode(sysUser.getCode());
		sysUserVO.setDataPermission(sysUser.getDataPermission());
		sysUserVO.setFullName(sysUser.getFullName());
		sysUserVO.setAreaId(sysUser.getAreaId());
		
		return sysUserVO;
	}
	
	/**
	 * 
	 * <pre>
	 * VO转为Model
	 * </pre>
	 *
	 * @param sysUserVO
	 * @return
	 */
	public static SysUser transferVO2Model (SysUserVO sysUserVO) {
		if (sysUserVO == null) {
			return null;
		}
		
		SysUser sysUser = new SysUser();
		sysUser.setId(sysUserVO.getId());
		sysUser.setSalesTeamId(sysUserVO.getSalesTeamId());
		sysUser.setLoginName(sysUserVO.getLoginName());
		sysUser.setName(sysUserVO.getName());
		sysUser.setEmail(sysUserVO.getEmail());
		sysUser.setTelephone(sysUserVO.getTelephone());
		sysUser.setCellphone(sysUserVO.getCellphone());
		sysUser.setLoginRetry(sysUserVO.getLoginRetry());
		sysUser.setSignPassword(sysUserVO.getSignPassword());
		sysUser.setSignPasswordExpireDate(sysUserVO.getSignPasswordExpireDate());
		sysUser.setLastLoginTime(sysUserVO.getLastLoginTime());
		sysUser.setLastLoginIp(sysUserVO.getLastLoginIp());
		sysUser.setUserType(sysUserVO.getUserType());
		sysUser.setIsDeleted(sysUserVO.getIsDeleted());
		sysUser.setStatus(sysUserVO.getStatus());
		sysUser.setCode(sysUserVO.getCode());
		sysUser.setDataPermission(sysUserVO.getDataPermission());
		sysUser.setFullName(sysUserVO.getFullName());
		sysUser.setAreaId(sysUserVO.getAreaId());
		
		return sysUser;
	}
}
