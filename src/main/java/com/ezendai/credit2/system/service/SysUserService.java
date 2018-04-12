package com.ezendai.credit2.system.service;

import java.util.List;

import com.ezendai.credit2.framework.model.UserSession;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.model.BaseArea;
import com.ezendai.credit2.system.model.SysGroupUser;
import com.ezendai.credit2.system.model.SysPermission;
import com.ezendai.credit2.system.model.SysUser;
import com.ezendai.credit2.system.vo.SysUserVO;

 

/**   
*    
* 项目名称：credit2-main   
* 类名称：SysUserService   
* 类描述：   
* 创建人：Administrator   
* 创建时间：2015年8月19日 下午4:31:43   
* 修改人：Administrator   
* 修改时间：2015年8月19日 下午4:31:43   
* 修改备注：   
* @version    
*    
*/
public interface SysUserService {
	/**
	 * 
	 * <pre>
	 * 根据用户名，加载左边的功能树
	 * </pre>
	 *
	 * @param id
	 * @return
	 */
	List<SysPermission> findPermissionListBySysUserId(Long id);

	/**
	 * 
	 * <pre>
	 * 根据登录名，查找登录信息
	 * </pre>
	 *
	 * @param loginName
	 * @return
	 */
	SysUser getSysUserByLoginName(String loginName);
	
	/**
	 * 
	 * <pre>
	 * 根据工号,查找用户相关信息
	 * </pre>
	 *
	 * @param code
	 * @return
	 */
	SysUser getSysUserByCode(String code);

	/**
	 * 
	 * <pre>
	 * 更新用户
	 * </pre>
	 *
	 * @param sysUser
	 * @loginName 检测LOGINNAME是否重复，为空不检测
	 */
	void updateSysUser(SysUserVO sysUserVO, String loginName);

	/**
	 * 
	 * <pre>
	 * 写入用户session
	 * </pre>
	 *
	 * @param userId
	 * @return
	 */
	String session2Cache(Long userId, String ipAddress);

	/**
	 * 
	 * <pre>
	 * 从数据库读取用户信息
	 * </pre>
	 *
	 * @param sysUser
	 * @return
	 */
	UserSession getUserSessionFromDB(Long userId, String ipAddress);

	/**
	 * 
	 * <pre>
	 * 通过id找到sysUser
	 * </pre>
	 *
	 * @param id 主键id
	 * @return
	 */
	SysUser get(Long id);

	/**
	 * 
	 * <pre>
	 * 通过用户ID获取用户所在 的指定类型的区域列表--为了用户能跨区域
	 * </pre>
	 *
	 * @return
	 */
	List<Long> getBaseAreaIdListByUserId(Long userId, String identifier);
	
	/**
	 * 
	 * <pre>
	 * 通过用户ID获取用户所在 的指定类型的区域列表--为了用户能跨区域
	 * </pre>
	 *
	 * @return
	 */
	List<BaseArea> getBaseAreaListByUserId(Long userId, String identifier);
	/**
	 * 
	 * <pre>
	 * 通过用户ID获取用户所在 的指定类型的区域
	 * </pre>
	 *
	 * @return
	 */
	BaseArea getBaseAreaByUserId(Long userId, String identifier);

	/**
	 * 
	 * <pre>
	 * 根据用户ID获取所属营业网点ID
	 * </pre>
	 *
	 * @return
	 */
	List<Long> getSalesDeptIdByUserId(Long userId);
	/**
	 * 
	 * <pre>
	 * 根据用户ID获取所属营业网点
	 * </pre>
	 *
	 * @return
	 */
	List<BaseArea> getSalesDeptByUserId(Long userId);

	/**
	 * 
	 * <pre>
	 * 获取当前用户所属营业网点，如果该用户不存在于该网点，则返回null
	 * </pre>
	 *
	 * @return
	 */
	List<BaseArea> getCurSalesDept();
	
	/**
	 * 
	 * <pre>
	 * 根据用户ID获取所属城市
	 * </pre>
	 *
	 * @return
	 */
	BaseArea getCityByUserId(Long userId);

	/**
	 * 
	 * <pre>
	 * 获取当前用户所属城市，如果该用户不存在于该城市，则返回null
	 * </pre>
	 *
	 * @return
	 */
	BaseArea getCurCity();

	/**
	 * 获取营业网点下的所有客户经理
	 */
	List<SysUser> getAllCrmsInSalesDept(String salesDeptCode);

	List<SysUser> findListByVO(SysUserVO sysUserVo);

	/**
	 * 
	 * <pre>
	 * 获取传入营业网点下可以指定参与产品的客户经理
	 * </pre>
	 *
	 * @param salesDeptId
	 * @param productId
	 * @return
	 */
	List<SysUser> getCrmsInSalesDeptByProductIdAndSalesDeptId(Long salesDeptId , Long productId);
	/**
	 * 
	 * <pre>
	 * 获取传入营业网点下可以指定参与产品的用户
	 * </pre>
	 *
	 * @param salesDeptId
	 * @param productId
	 * @return
	 */
	List<SysUser> getSalesByProductId(Long salesDeptId , Long productId);
	/**
	 * 
	 * <pre>
	 * 获取营业网点下的可以指定参与产品的客户经理
	 * </pre>
	 *
	 * @param userId
	 * @param productId
	 * @return
	 */
	List<SysUser> getCrmsInSalesDeptByProductId(Long userId, Long productId);

	/**
	 * 
	 * 
	 * <pre>
	 * 获取传入的用户可以指定参与产品的客服经理
	 * </pre>
	 *
	 * @param salesDeptCode
	 * @param productId
	 * @return
	 */
	List<SysUser> getServicesInCurSalesDeptByProductId(Long userId, Long productId);
	 
	/**
	 * 
	 * <pre>
	 * 获取传入的用户可以指定参与产品的客服经理
	 * </pre>
	 *
	 * @param salesDeptCode
	 * @param productIdList
	 * @return
	 */
	List<SysUser> getServicesInCurSalesDeptByProductIdList(Long userId, List<Long> productIdList);

	/**
	 * 重置密码
	 * 
	 * @param userId
	 * @param original
	 * @param reset
	 * @return
	 */
	int resetPassword(Long userId, String original, String reset);

	/**
	 * <pre>
	 * 测试连接接口
	 * </pre>
	 *
	 * @return
	 */
	Integer testConnection();

	/**
	 * 
	 * <pre>
	 * 根据用户ID查找权限码列表
	 * </pre>
	 *
	 * @param id
	 * @return
	 */
	List<String> findPermissionCodeList(Long id);
	/**
	 * 
	 * <pre>
	 *根据area_id查找最大权限码
	 * </pre>
	 *
	 * @return
	 * @throws Exception 
	 */
	String   getMaxPermission(long areaId , int userType) throws Exception;
	
	/*
	 * 
	 * <pre>
	 * 获取所有客户经理列表(包含业务主任)
	 * </pre>
	 *
	 * @return
	 */
	List<SysUser> findAllCrmList();

	/**
	 * <pre>
	 * 根据用户权限与类型，获取其所在营业部所有客服
	 * </pre>
	 * @param dataPermission
	 * @param userType
	 * @return
	 */
//	List<SysUser> getServicesInCurSalesDeptByDataPermission(String dataPermission, Integer userType);
	
	/**
	 * 
	 * <pre>
	 * 获取当前用户所属营业网点的经理、副理
	 * </pre>
	 *
	 * @return
	 */
	List<SysUser> getCurSalesDeptManager();
	
	/**
	 * 
	 * <pre>
	 * 根据客户经理，获取其业务主任(依赖于客户经理的data_permission)
	 * </pre>
	 *
	 * @param crm
	 * @return
	 */
	SysUser getBizDirectorByCrm(SysUser crm);
	
	/**
	 * 查询员工列表带分页功能
	 * @author Ivan
	 * @param sysUserVO 查询VO
	 * @return 结果集，带总行数
	 */
	public Pager findWithPGExtension(SysUserVO sysUserVO) ;
	
	/**
	 * 
	 * <pre>
	 * 新增员工
	 * </pre>
	 *
	 * @param sysUserVO
	 * @author Ivan
	 * 
	 */
	void insertSysUser(SysUser sysUser);
	
	
	/**
	 * 
	 * <pre>
	 * 查询员工已绑定的权限组列表
	 * </pre>
	 *
	 * @param userId 员工编号
	 * @author Ivan
	 * 
	 */
	List<SysGroupUser> queryMyGroupList(Long userId);
	
	List<SysUser>  AssessorList(SysUserVO vo);
	 List<SysUser> araeAssessorList(SysUserVO vo) ;
	 
	 List<SysUser> araeTestAssessorList(SysUserVO vo) ;
	 
	   List<SysUser> araeTestCustomerServiceList(SysUserVO vo) ;

	 List<SysUser> araeCustomerServiceList(SysUserVO vo) ;
	 
	 long  selectUserId();
	 
	List<Long> getSalesDeptIdByUserIdAndDeptType(Long userId);
    List<SysUser> getBizDirectorByUser(SysUser crm) ;
}
