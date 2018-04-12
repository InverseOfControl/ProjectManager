package com.ezendai.credit2.master.service;

import java.util.List;

import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.model.RefusedReason;
import com.ezendai.credit2.master.vo.RefusedReasonVO;

public interface ReasonManagerService {
	
	/**
	 * 根据条件查询拒绝原因，主页面分页查询方法
	 * 
	 */
	Pager getReasonList(RefusedReasonVO vo);
	
	/**
	 * 查询所有的父原因，用于填充下拉列表
	 */
	List<RefusedReason> findAllParentReasonList();
	
	/**
	 * 新增拒绝原因
	 */
	int addReason(RefusedReasonVO vo);
	
	/**
	 * 获取单个原因信息
	 */
	RefusedReason getReasonById(Long id);
	
	/**
	 * 修改拒绝原因
	 */
	int updateReason(RefusedReasonVO reason);
	
	/**
	 * 禁用
	 */
	int disableReason(RefusedReasonVO reason);
}
