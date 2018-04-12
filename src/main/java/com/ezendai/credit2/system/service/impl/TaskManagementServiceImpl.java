package com.ezendai.credit2.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.system.dao.TaskManagementDao;
import com.ezendai.credit2.system.model.TaskManagement;
import com.ezendai.credit2.system.service.TaskManagementService;
import com.ezendai.credit2.system.vo.TaskManagementVO;

@Service
public class TaskManagementServiceImpl implements TaskManagementService {

	@Autowired
	private TaskManagementDao taskManagementDao;

	@Override
	public Pager getBlackList(TaskManagementVO taskManagementVO) {
		return taskManagementDao.findWithPg(taskManagementVO);
	}

	@Override
	public TaskManagement get(Long id) {
		return taskManagementDao.get(id);
	}

	@Override
	public void updateTaskManagement(TaskManagementVO taskManagementVO) {
		taskManagementDao.update(taskManagementVO);
	}

	@Override
	public void insertTaskManagement(TaskManagement taskManagement) {
		taskManagementDao.insert(taskManagement);
	}
}
