package com.ezendai.credit2.master.service;

import java.util.List;

import com.ezendai.credit2.master.model.BaseArea;
import com.ezendai.credit2.master.vo.BaseAreaVO;

/**
 * <pre>
 * 销售团队管理
 * </pre>
 *
 * @author majl
 * @version $Id: SalesTeamService.java, v 0.1 2014-6-24 下午3:12:02 majl Exp $
 */
public interface SalesTeamService {

	/**
	 * <pre>
	 * 新增销售团队
	 * </pre>
	 *
	 * @param vo
	 */
	void addSalesTeam(BaseAreaVO vo);

	/**
	 * <pre>
	 * 修改销售团队信息
	 * </pre>
	 *
	 * @param vo
	 */
	void editSalesTeam(BaseAreaVO vo);

	/**
	 * <pre>
	 * 删除团队
	 * </pre>
	 *
	 * @param id
	 */
	void deleteSalesTeam(Long id);

	/**
	 * <pre>
	 * 分页查询全部销售团队
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

}
