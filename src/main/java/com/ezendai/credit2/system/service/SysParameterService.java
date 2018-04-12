package com.ezendai.credit2.system.service;

import java.net.UnknownHostException;
import java.util.List;

import com.ezendai.credit2.system.model.SysParameter;
import com.ezendai.credit2.system.vo.SysParameterVO;

/**
 * 
 * <pre>
 * 
 * </pre>
 *
 * @author liyuepeng
 * @version $Id: SysParameterService.java, v 0.1 2014-8-19 下午02:48:22 liyuepeng Exp $
 */
public interface SysParameterService {

	/**
	 * 
	 * <pre>
	 * 新增 SysParameter
	 * </pre>
	 *
	 * @param sysParameter
	 * @return SysParameter
	 */
	SysParameter insertSysParameter(SysParameter sysParameter);

	/**
	 * 
	 * <pre>
	 * 通过id删除SysParameter
	 * </pre>
	 *
	 * @param Long id
	 * @return
	 */
	void deleteSysParameterById(Long id);

	/**
	 * 
	 * <pre>
	 * 修改SysParameter
	 * </pre>
	 *
	 * @param sysParameter
	 * @return
	 */
	void updateSysParameter(SysParameterVO sysParameterVo);

	/**
	 * 
	 * <pre>
	 * 查询SysParameter
	 * </pre>
	 *
	 * @param SysParameterVO
	 * @return List<SysParameter>
	 */
	List<SysParameter> findList(SysParameterVO sysParameterVo);

	/**
	 * 
	 * <pre>
	 * 通过idList批量删除SysParameter
	 * </pre>
	 *
	 * @param SysParameterVO
	 * @return 
	 */
	void deleteSysParameterByIdList(SysParameterVO sysParameterVo);

	/**
	 * 
	 * <pre>
	 * 通过id得到SysParameter
	 * </pre>
	 *
	 * @param Long id
	 * @return SysParameter
	 */
	SysParameter get(Long id);
	
	/**
	 * 
	 * <pre>
	 * 通过CODE获取系统参数
	 * </pre>
	 *
	 * @param code
	 * @return
	 */
	SysParameter getByCode(String code);
	
    /**
     * 新增
     * @param SysParameterVO
     */
    public void add(SysParameterVO sysParameterVo);
    
    /**
     * 更新系统参数，并刷新缓存
     * @param SysParameterVO
     */
    public void updateByCodeAndRefreshCache(SysParameterVO sysParameterVo);
    
    /**
     * 修改
     * @param SysParameterVO
     */
	public void modify(SysParameterVO sysParameterVo);
	
	
	
	/**
	 * <pre>
	 * 根据代码和ip地址判断是否通过验证
	 * </pre>
	 *
	 * @param code
	 * @param ipAddr
	 * @return
	 */
	boolean ipValidationCheck(String code,String ipAddr)  throws UnknownHostException;
	
	/**
	 *  实时查询配置表
	 * @param code
	 * @return
	 */
	public SysParameter getByCodeNoCache(String code);
}
