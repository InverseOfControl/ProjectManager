package com.ezendai.credit2.apply.dao;

import java.util.List;

import com.ezendai.credit2.apply.model.SysProductUser;
import com.ezendai.credit2.apply.vo.SysProductUserVO;
import com.ezendai.credit2.framework.dao.BaseDao;

public interface SysProductUserDao extends BaseDao<SysProductUser> {

	/**
	 * 
	 * <pre>
	 * 根据用户ID查找贷款类型
	 * </pre>
	 *
	 * @param userId
	 * @return
	 */
	public List<String> findLoanTypeByUserId(Long userId);
	
	
	/**
	 * <pre>
	 * 根据用户ID查找ProductId
	 * </pre>
	 * @param userId
	 * @return
	 */
	public List<Long> findProductIdByUserId(Long userId);
	
	/**
	 * 修改员工与权限组对照列表
	 * @param sysGroupUserVO
	 * @return
	 */
	public void modifyProductUserMap(SysProductUserVO sysProductUserVO);
	
}