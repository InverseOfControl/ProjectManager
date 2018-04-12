package com.ezendai.credit2.system.service;

import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.system.model.TaskManagement;
import com.ezendai.credit2.system.vo.TaskManagementVO;

public interface TaskManagementService {

	// 查询
	Pager getBlackList(TaskManagementVO taskVO);

	// 增加
	void insertTaskManagement(TaskManagement taskManagement);

	// 修改
	void updateTaskManagement(TaskManagementVO taskManagementVO);

	// 通过id找到TaskManagement
	TaskManagement get(Long id);
}
