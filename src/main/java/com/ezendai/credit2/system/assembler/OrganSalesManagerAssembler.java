package com.ezendai.credit2.system.assembler;

import com.ezendai.credit2.system.model.OrganSalesManager;
import com.ezendai.credit2.system.vo.OrganSalesManagerVO;

/**
 * 
 * <pre>
 * 	VO/Model转换
 * </pre>
 *
 * @version $Id: SysUserAssembler.java, v 0.1 2014年8月1日 下午2:49:11 zhangshihai Exp $
 */
public class OrganSalesManagerAssembler {

	/**
	 * 
	 * <pre>
	 * Model转为VO
	 * </pre>
	 *
	 * @param sysUser
	 * @return
	 */
	public static OrganSalesManagerVO transferModel2VO (OrganSalesManager organSalesManager) {
		if (organSalesManager == null) {
			return null;
		}
		OrganSalesManagerVO vo = new OrganSalesManagerVO();
		vo.setOrganId(organSalesManager.getOrganId());
		vo.setUserId(organSalesManager.getUserId());
		vo.setSalesManager(organSalesManager.getSalesManager());
		vo.setCode(organSalesManager.getCode());
		return vo;
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
	public static OrganSalesManager transferVO2Model (OrganSalesManagerVO vo) {
		if (vo == null) {
			return null;
		}
		OrganSalesManager organSalesManager = new OrganSalesManager();
		organSalesManager.setOrganId(vo.getOrganId());
		organSalesManager.setUserId(vo.getUserId());
		organSalesManager.setSalesManager(vo.getSalesManager());
		organSalesManager.setCode(vo.getCode());
		return organSalesManager;
	}
}
