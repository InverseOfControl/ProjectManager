package com.ezendai.credit2.master.service;

import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.model.UserAreaManager;
import com.ezendai.credit2.master.vo.UserAreaManagerVO;

/**
 * 
 * @Description: 系统参数服务接口
 * @author 张宜超
 * @date 2016年2月1日
 */
public interface UserAreaManagerService {

	/**
	 * 
	 * @Description: 查询分页数据
	 * @param @param vo
	 * @param @return   
	 * @return Pager  
	 * @throws
	 * @author cj
	 * @date 2016年7月13日
	 */
	public Pager getUserAreaManagerList(UserAreaManagerVO vo);
	
	/**
	 * 用户地区管理新增
	 * @param userAreaManager
	 * @return
	 */
	public Long insertUserAreaManager(UserAreaManager userAreaManager);
	/**
	 * 用户地区管理修改
	 * @param vo
	 * @return
	 */
	public int updateUserAreaManager(UserAreaManagerVO vo);
	
	public UserAreaManagerVO findUserAreaManagerById(Long id);
	
	
}
