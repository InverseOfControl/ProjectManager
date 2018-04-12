package com.ezendai.credit2.system.assembler;

import com.ezendai.credit2.system.model.OrganBank;
import com.ezendai.credit2.system.model.OrganSalesDepartment;
import com.ezendai.credit2.system.vo.OrganBankVO;
import com.ezendai.credit2.system.vo.OrganSalesDepartmentVO;

/**
 * 
 * <pre>
 * 	VO/Model转换
 * </pre>
 *
 * @version $Id: SysUserAssembler.java, v 0.1 2014年8月1日 下午2:49:11 zhangshihai Exp $
 */
public class OrganSalesDepartmentAssembler {

	/**
	 * 
	 * <pre>
	 * Model转为VO
	 * </pre>
	 *
	 * @param sysUser
	 * @return
	 */
	public static OrganSalesDepartmentVO transferModel2VO (OrganSalesDepartment organSalesDepartment) {
		if (organSalesDepartment == null) {
			return null;
		}
		OrganSalesDepartmentVO vo = new OrganSalesDepartmentVO();
		vo.setOrganId(organSalesDepartment.getOrganId());
		vo.setSalesDeptId(organSalesDepartment.getSalesDeptId());
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
	public static OrganSalesDepartment transferVO2Model (OrganSalesDepartmentVO vo) {
		if (vo == null) {
			return null;
		}
		OrganSalesDepartment organSalesDepartment = new OrganSalesDepartment();
		organSalesDepartment.setOrganId(organSalesDepartment.getOrganId());
		organSalesDepartment.setSalesDeptId(organSalesDepartment.getSalesDeptId());
		return organSalesDepartment;
	}
}
