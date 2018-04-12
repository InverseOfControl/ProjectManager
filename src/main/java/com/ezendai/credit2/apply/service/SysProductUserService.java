package com.ezendai.credit2.apply.service;

import java.util.List;

import com.ezendai.credit2.apply.vo.SysProductUserVO;

public interface SysProductUserService {
	/**
	 * <pre>
	 * 根据用户ID获取ProductId
	 * </pre>
	 * @param userId
	 * @return
	 */
	public List<Long> getProductIdByUserId(Long userId);
	
	/**
	 * 修改员工与权限组对照列表
	 * @param sysGroupUserVO
	 * @return
	 */
	public void modifyProductUserMap(SysProductUserVO sysProductUserVO);
}
