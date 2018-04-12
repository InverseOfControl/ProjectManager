package com.ezendai.credit2.master.dao;

import java.util.List;

import com.ezendai.credit2.framework.dao.BaseDao;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.model.SysParameterManager;
import com.ezendai.credit2.master.vo.SysParameterManagerVO;

/**
 * 
 * @Description: 系统参数接口
 * @author 张宜超
 * @date 2016年2月1日
 */
public interface SysParameterManagerDao extends BaseDao<SysParameterManager>{

	/**
	 * 
	 * @Description: 获取系统参数列表
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
	 * @param @param vo
	 * @param @return   
	 * @return SysParameterManager  
	 * @throws
	 * @author 张宜超
	 * @date 2016年2月1日
	 */
	SysParameterManager getSysParameter(Long id);
	
	/**
	 * 
	 * @Description: 多参数获取匹配数据 用于检验等
	 * @param @param vo
	 * @param @return   
	 * @return List<SysParameterManager>  
	 * @throws
	 * @author 张宜超
	 * @date 2016年2月1日
	 */
	List<SysParameterManager> getSysParameterByConditions(SysParameterManagerVO vo);
	
	/**
	 * 
	 * @Description: 获取数据个数
	 * @param @param vo
	 * @param @return   
	 * @return int  
	 * @throws
	 * @author 张宜超
	 * @date 2016年2月1日
	 */
	int getSysParameterCount(SysParameterManagerVO vo);
	
	/**
	 * 
	 * @Description: 添加一条数据
	 * @param @param vo   
	 * @return void  
	 * @throws
	 * @author 张宜超
	 * @date 2016年2月1日
	 */
	void addSysParameter(SysParameterManagerVO vo);
	
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
	int updateSysParameter(SysParameterManagerVO vo);
	
	/**
	 * 
	 * @Description: 删除一条参数
	 * @param @param id
	 * @param @return   
	 * @return int  
	 * @throws
	 * @author 张宜超
	 * @date 2016年2月17日
	 */
	int deleteSysParameter(Long id);
}

