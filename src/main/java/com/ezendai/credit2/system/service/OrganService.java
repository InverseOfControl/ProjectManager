package com.ezendai.credit2.system.service;

import java.util.List;

import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.system.model.Organ;
import com.ezendai.credit2.system.vo.OrganDetailVO;
import com.ezendai.credit2.system.vo.OrganVO;

/**
 * @author LinSanfu
 */

public interface OrganService {
	int update(OrganVO organVO);
	
	void deleteByVO(OrganVO organVO);
	
	Organ insert(Organ organ);
	
	List<Organ> findListByVo(OrganVO organVO);
	
	Pager findWithPg(OrganVO organVO);
	
	Integer count(OrganVO organVO);
	
	Organ get(Long id);
	
	/**
	 * <pre>
	 * 查询机构下面有没有生效的批复方案，
	 * </pre>
	 * @param organId
	 * @return
	 */
	boolean existValidCheckPlan(Long organId);
	
	/**
	 * <pre>
	 * 机构新增
	 * </pre>
	 * @param vo
	 */
	void processOrganInsert(OrganDetailVO vo);
	
	/**
	 * <pre>
	 * 机构编辑
	 * </pre>
	 * @param vo
	 * @param organId
	 */
	void processOrganEdit(OrganDetailVO vo, Long organId);
	
	/**
	 * <pre>
	 * 机构删除--逻辑删除
	 * 1.机构表(organ-status->0)
	 * 2.方案表(channel_plan-is_deleted-->1)
	 * 3.方案表批复表(channel_plan—check-is_deleted-->1)
	 * </pre>
	 * @param vo
	 * @param organId
	 */
	void deleteOrgan(Long organId,Organ organ);
}
