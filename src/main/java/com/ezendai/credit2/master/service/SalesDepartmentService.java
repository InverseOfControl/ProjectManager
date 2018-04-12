package com.ezendai.credit2.master.service;

import java.util.List;

import com.ezendai.credit2.master.model.BaseArea;
import com.ezendai.credit2.master.vo.BaseAreaVO;

/**
 * <pre>
 * 网点管理
 * </pre>
 *
 * @author majl
 * @version $Id: SalesDepartmentService.java, v 0.1 2014-6-24 下午3:11:00 majl Exp $
 */
public interface SalesDepartmentService {

	/**
	 * <pre>
	 * 新增网点
	 * </pre>
	 *
	 * @param vo
	 */
	void addSalesDepartment(BaseAreaVO vo);

	/**
	 * <pre>
	 * 修改网点信息
	 * </pre>
	 *
	 * @param vo
	 */
	void editSalesDepartment(BaseAreaVO vo);

	/**
	 * <pre>
	 * 删除网点
	 * </pre>
	 *
	 * @param id
	 */
	void deleteSalesDepartment(Long id);

	/**
	 * <pre>
	 * 分页查询全部网点
	 * </pre>
	 *
	 * @param identifier
	 * @return
	 */
	List<BaseArea> loadAllBaseArea(String identifier);

	/**
	 * <pre>
	 * 单条查询 by Id
	 * </pre>
	 *
	 * @param id
	 * @return
	 */
	BaseArea loadOneBaseAreaById(Long id);

	/**
	 * <pre>
	 * 单条查询 by Name
	 * </pre>
	 *
	 * @param name
	 * @return
	 */
	BaseArea loadOneBaseAreaByName(String name);

}
