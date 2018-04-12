package com.ezendai.credit2.master.service;

import java.util.List;

import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.model.SysParameterManager;
import com.ezendai.credit2.master.vo.SysParameterManagerVO;

/**
 * 
 * @Description: 系统参数服务接口
 * @author 张宜超
 * @date 2016年2月1日
 */
public interface SysParameterManagerService {

	/**
	 * 
	 * @Description: 查询分页数据
	 * @param @param vo
	 * @param @return   
	 * @return Pager  
	 * @throws
	 * @author 张宜超
	 * @date 2016年2月1日
	 */
	public Pager getSysParameterList(SysParameterManagerVO vo);
	
	/**
	 * 
	 * @Description: 获取一条数据
	 * @param @param id
	 * @param @return   
	 * @return SysParameterManager  
	 * @throws
	 * @author 张宜超
	 * @date 2016年2月1日
	 */
	public SysParameterManager getSysParameter(Long id);
	
	/**
	 * 
	 * @Description: 多条件查询数据列表
	 * @param @param vo
	 * @param @return   
	 * @return List<SysParameterManager>  
	 * @throws
	 * @author 张宜超
	 * @date 2016年2月1日
	 */
	public List<SysParameterManager> getSysParameterByConditions(
			SysParameterManagerVO vo);
	
	/**
	 * 
	 * @Description: 查询数据个数
	 * @param @param vo
	 * @param @return   
	 * @return int  
	 * @throws
	 * @author 张宜超
	 * @date 2016年2月1日
	 */
	public int getSysParameterCount(SysParameterManagerVO vo);
	
	/**
	 * 
	 * @Description: 添加一条数据
	 * @param @param vo   
	 * @return void  
	 * @throws
	 * @author 张宜超
	 * @date 2016年2月1日
	 */
	public void addSysParameter(SysParameterManagerVO vo);
	
	/**
	 * 
	 * @Description: 更新一条数据
	 * @param @param vo
	 * @param @return   
	 * @return int  
	 * @throws
	 * @author 张宜超
	 * @date 2016年2月1日
	 */
	public int updateSysParameter(SysParameterManagerVO vo);
	
	/**
	 * 
	 * @Description: 删除一条数据
	 * @param @param id
	 * @param @return   
	 * @return int  
	 * @throws
	 * @author 张宜超
	 * @date 2016年2月17日
	 */
	public int deleteSysParameter(Long id);
}
