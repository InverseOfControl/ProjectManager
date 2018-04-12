package com.ezendai.credit2.master.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.dao.UserAreaManagerDao;
import com.ezendai.credit2.master.model.BaseArea;
import com.ezendai.credit2.master.model.UserAreaManager;
import com.ezendai.credit2.master.service.BaseAreaService;
import com.ezendai.credit2.master.service.UserAreaManagerService;
import com.ezendai.credit2.master.vo.UserAreaManagerVO;

/**
 * 
 * @Description: 参数接口实现
 * @author 张宜超
 * @date 2016年2月1日
 */
@Service
@Transactional
public class UserAreaManagerServiceImpl implements UserAreaManagerService {

	@Autowired
	private UserAreaManagerDao userAreaManagerDao ;
	/**
	 * 地区service
	 */
	@Autowired
	private BaseAreaService baseAreaService;

	/**
	 * 获取参数list
	 */
	@Override
	public Pager getUserAreaManagerList(UserAreaManagerVO vo) {
		
		return userAreaManagerDao.getUserAreaManagerList(vo);
	}

	@Override
	public Long insertUserAreaManager(UserAreaManager userAreaManager) {
		//通过baseAreaId 获取区域类型
		BaseArea baseArea = baseAreaService.get(userAreaManager.getBaseAreaId());
		userAreaManager.setBaseAreaType(baseArea.getIdentifier());
		UserAreaManager temp = userAreaManagerDao.insert(userAreaManager);
		return temp.getId();
	}

	@Override
	public int updateUserAreaManager(UserAreaManagerVO vo) {
		return userAreaManagerDao.update(vo);
	}

	@Override
	public UserAreaManagerVO findUserAreaManagerById(Long id) {
		UserAreaManagerVO vo = new UserAreaManagerVO();
		UserAreaManagerVO voTemp = null;
		vo.setId(id);
		List<UserAreaManagerVO> list = userAreaManagerDao.findUserAreaManagerByCondition(vo);
		if(list !=null && list.size()>0){
			voTemp = list.get(0);
		}
		return voTemp;
		 
	}
	
}
