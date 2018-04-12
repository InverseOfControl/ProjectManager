package com.ezendai.credit2.system.dao;

import java.util.List;
import java.util.Map;

import com.ezendai.credit2.framework.dao.BaseDao;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.model.BaseArea;
import com.ezendai.credit2.system.model.SysGroupUser;
import com.ezendai.credit2.system.model.SysPermission;
import com.ezendai.credit2.system.model.SysUser;
import com.ezendai.credit2.system.vo.SysUserVO;

public interface SysUserDao extends BaseDao<SysUser> {

	/**
	 * <pre>
	 * 根据员工ID，加载左边的功能树
	 * </pre>
	 *
	 * @param id
	 * @return
	 */
	List<SysPermission> findPermissionListBySysUserId(Long id);

	/**
	 * <pre>
	 * 根据登陆账号,取到该账号对应的用户信息
	 * </pre>
	 *
	 * @param loginName
	 * @return
	 */
	SysUser findSysUserByLoginName(String loginName);

	/**
	 * <pre>
	 *  根据员工ID查找所在网点
	 * </pre>
	 *
	 * @param userId
	 * @return
	 */
	List<BaseArea> findAreaByUserId(Long userId);

	/**
	 * <pre>
	 * 根据权限代码获取出该营业网点下的所有员工
	 * </pre>
	 *
	 * @param salesDeptCode
	 * @return
	 */
	List<SysUser> getAllCrmsInSalesDept(String salesDeptCode);

	List<SysUser> findListByUserId(Map map);

	/**
	 * <pre>
	 * 获取营业网点下的可以指定参与产品的客户经理
	 * </pre>
	 *
	 * @param salesDeptCode
	 * @param productId
	 * @return
	 */
	List<SysUser> getCrmsInSalesDeptByProductId(String salesDeptCode, Long productId);
	/**
	 * <pre>
	 * 获取营业网点下的可以指定参与产品的用户
	 * </pre>
	 *
	 * @param salesDeptCode
	 * @param productId
	 * @return
	 */
	List<SysUser> getSalesByProductId(String salesDeptCode, Long productId);

	/**
	 * <pre>
	 * 获取当前用户所拥有的营业网点下可以指定参与产品的客服经理
	 * </pre>
	 *
	 * @param salesDeptCode
	 * @param productId
	 * @return
	 */
	List<SysUser> getServicesInCurSalesDeptByProductId(List<String> salesDeptCodeList , Long productId);
	
	/**
	 * <pre>
	 *获取当前用户所拥有的营业网点下可以指定参与产品的客服经理
	 * </pre>
	 *
	 * @param salesDeptCode
	 * @param productId
	 * @return
	 */
	List<SysUser> getServicesInCurSalesDeptByProductIdList(List<String> salesDeptCodeList , List<Long> productIdList);

	/**
	 * <pre>
	 * 测试连接接口
	 * </pre>
	 *
	 * @return
	 */
	public Integer testConnection();
	
	/**
	 * <pre>
	 * 根据用户ID查找权限码列表
	 * </pre>
	 *
	 * @param id
	 * @return
	 */
	public List<String> findPermissionCodeList(Long id);
	
	/**
	 * 
	 * <pre>
	 * 获取所有客户经理(包含业务主任)
	 * </pre>
	 *
	 * @param userTypeMap
	 * @return
	 */
	public List<SysUser> findAllCrmList();
	
	/**
	 * 
	 * <pre>
	 *根据dataPermission、userType 获取业务主任
	 * </pre>
	 *
	 * @param map
	 * @return
	 */
	public List<SysUser> getBizDirector(String dataPermission, Integer userType);
	
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
	 * 查询员工已绑定的权限组列表
	 * </pre>
	 *
	 * @param userId 员工编号
	 * @author Ivan
	 * 
	 */
	List<SysGroupUser> queryMyGroupList(Long userId);

	/**
	 * <pre>
	 * 根据用户权限与类型，获取其所在营业部所有客服
	 * </pre>
	 * @param dataPermission
	 * @param userType
	 * @return
	 */
//	public List<SysUser> getServicesInCurSalesDeptByDataPermission(String dataPermission, Integer userType);
	/**
	 * <pre>
	 * 根据area_id查找最大的权限码
	 * </pre>

	 * @param area_id
	 * @return 
	 */
	String getMaxPermission(long areaId);
	
	/**
	 * <pre>
	 * 得到助學貸初審人員
	 * </pre>

	 * @param 
	 * @return 
	 */
	List<SysUser> AssessorList(SysUserVO vo);
	
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
	 * <pre>
	 * 获取五大营业部的的真实客服门店经理  催收人员
	 * </pre>

	 * @param 
	 * @return 
	 */
	List<SysUser> araeAssessorList(SysUserVO vo);
	/**
	 * <pre>
	 * 获取五大营业部的的测试客服门店经理  催收人员
	 * </pre>

	 * @param 
	 * @return 
	 */
	List<SysUser> araeTestAssessorList(SysUserVO vo);
	List<SysUser> araeTestCustomerServiceList(SysUserVO vo);
	List<SysUser> araeCustomerServiceList(SysUserVO vo);
	long  selectUserId();
	public List<SysUser> getBizDirectorListLike(String dataPermission, Integer userType);
	
}
