package com.ezendai.credit2.master.dao;

import java.util.List;

import com.ezendai.credit2.framework.dao.BaseDao;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.model.UserAreaManager;
import com.ezendai.credit2.master.vo.UserAreaManagerVO;

/**
 * 
 * @Description: 
 * @author cj
 * @date 2016年2月1日
 */
public interface UserAreaManagerDao extends BaseDao<UserAreaManager>{

	/**
	 * 
	 * @Description: 获取用户地区信息
	 * @param @param vo
	 * @param @return   
	 * @return Pager  
	 * @throws
	 * @author cj
	 * @date 2016年7月13日
	 */
	public Pager getUserAreaManagerList(UserAreaManagerVO vo);
	
	/**
	 * 获取所有数据根据条件
	 * @param vo
	 * @return
	 */
	public List<UserAreaManagerVO> findUserAreaManagerByCondition(UserAreaManagerVO vo);
	
	
}

