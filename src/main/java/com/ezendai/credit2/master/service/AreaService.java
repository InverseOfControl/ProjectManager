package com.ezendai.credit2.master.service;

import java.util.List;

import com.ezendai.credit2.master.model.BaseArea;
import com.ezendai.credit2.master.vo.BaseAreaVO;

/**
 * <pre>
 * 分区管理
 * </pre>
 *
 * @author majl
 * @version $Id: AreaService.java, v 0.1 2014-6-23 下午2:05:18 majl Exp $
 */
public interface AreaService {

	/**
	 * <pre>
	 * 新建分区
	 * </pre>
	 *
	 * @param vo
	 */
	void addArea(BaseAreaVO vo);

	/**
	 * <pre>
	 * 修改分区
	 * </pre>
	 *
	 * @param vo
	 */
	void editArea(BaseAreaVO vo);

	/**
	 * <pre>
	 * 删除分区
	 * </pre>
	 *
	 * @param id
	 */
	void deleteArea(Long id);

	/**
	 * <pre>
	 * 分页查询全部分区
	 * </pre>
	 *
	 * @param identifier
	 * @return
	 */
	List<BaseArea> loadAllBaseArea(String identifier);

	/**
	 * <pre>
	 * 单条查询
	 * </pre>
	 *
	 * @param id
	 * @return
	 */
	BaseArea loadOneBaseArea(Long id);

	BaseArea getBaseAreaByVO(BaseAreaVO baseAreaVO);
	/**
	 * 获取部门经理可以操作的所有营业网点Id（目前只限刘娜（上海营业部经理）操作沈阳以及青岛的营业网点权限）
	 * @param baseAreaVO
	 * @return
	 */
	List<Long> getDeptsByUserIdAndDeptsTypes(BaseAreaVO baseAreaVO);
	

}
