package com.ezendai.credit2.system.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ezendai.credit2.framework.dao.impl.BaseDaoImpl;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.master.model.BaseArea;
import com.ezendai.credit2.system.dao.SysUserDao;
import com.ezendai.credit2.system.model.SysGroupUser;
import com.ezendai.credit2.system.model.SysPermission;
import com.ezendai.credit2.system.model.SysUser;
import com.ezendai.credit2.system.vo.SysUserVO;

@Repository
public class SysUserDaoImpl extends BaseDaoImpl<SysUser> implements SysUserDao {

	public List<SysPermission> findPermissionListBySysUserId(Long id) {
		return getSqlSession().selectList(getIbatisMapperNameSpace() + ".findPermissionListBySysUserId", id);
	}

	public SysUser findSysUserByLoginName(String loginName) {
		return (SysUser) getSqlSession().selectOne(getIbatisMapperNameSpace() + ".findSysUserByLoginName", loginName);
	}

	/***
	 * 根据员工ID查找所在网点
	 * @param userId
	 * @return
	 * @see com.ezendai.credit2.system.dao.SysUserDao#findAreaByUserId(java.lang.Long)
	 */
	@Override
	public List<BaseArea> findAreaByUserId(Long userId) {
		return getSqlSession().selectList(getIbatisMapperNameSpace() + ".findAreaByUserId", userId);
	}

	@Override
	public List<SysUser> getAllCrmsInSalesDept(String salesDeptCode) {
		return getSqlSession().selectList(getIbatisMapperNameSpace() + ".getAllCrmsInSalesDept", salesDeptCode);
	}
@Override
public String getMaxPermission(long areaId) {
	// TODO Auto-generated method stub
	return getSqlSession().selectOne(getIbatisMapperNameSpace()+ ".getMaxPermission", areaId);
}
	/***
	 * 查询该做同样产品的所有 用户
	 * @param userId
	 * @return
	 * @see com.ezendai.credit2.system.dao.SysUserDao#findAreaByUserId(java.lang.Long)
	 */
	@Override
	public List<SysUser> findListByUserId(Map map) {
		return getSqlSession().selectList(getIbatisMapperNameSpace() + ".findListByUserId", map);
	}

	@Override
	public List<SysUser> getCrmsInSalesDeptByProductId(String salesDeptCode, Long productId) {
		Map queryHashMap = new HashMap();
		queryHashMap.put("salesDeptCode", salesDeptCode);
		queryHashMap.put("productId", productId);
		return getSqlSession().selectList(getIbatisMapperNameSpace() + ".getCrmsInSalesDeptByProductId", queryHashMap);
	}	
	

	@Override
	public List<SysUser> getSalesByProductId(String salesDeptCode,
			Long productId) {
		Map queryHashMap = new HashMap();
		queryHashMap.put("salesDeptCode", salesDeptCode);
		queryHashMap.put("productId", productId);
		return getSqlSession().selectList(getIbatisMapperNameSpace() + ".getSalesByProductId", queryHashMap);
	}

	@Override
	public List<SysUser> getServicesInCurSalesDeptByProductId(List<String> salesDeptCodeList, Long productId) {
		Map queryHashMap = new HashMap();
		queryHashMap.put("salesDeptCodeList", salesDeptCodeList);
		queryHashMap.put("productId", productId);
		return getSqlSession().selectList(getIbatisMapperNameSpace() + ".getServicesInCurSalesDeptByProductId", queryHashMap);
	}
	@Override
	public List<SysUser> getServicesInCurSalesDeptByProductIdList(List<String> salesDeptCodeList, List<Long> productIdList) {
		Map queryHashMap = new HashMap();
		queryHashMap.put("salesDeptCodeList", salesDeptCodeList);
		queryHashMap.put("productIdList", productIdList);
		return getSqlSession().selectList(getIbatisMapperNameSpace() + ".getServicesInCurSalesDeptByProductIdList", queryHashMap);
	}

	@Override
	public Integer testConnection() {
		return (Integer) getSqlSession().selectOne(getIbatisMapperNameSpace() + ".testConnection");
	}

	@Override
	public List<String> findPermissionCodeList(Long id) {

		return getSqlSession().selectList(getIbatisMapperNameSpace() + ".findPermissionCodeList", id);
	}

	/** 
	 * @return
	 * @see com.ezendai.credit2.system.dao.SysUserDao#findAllCrmByUserTypeMap()
	 */
	@Override
	public List<SysUser> findAllCrmList() {
		Map<String, Integer> queryHashMap = new HashMap<String, Integer>();
		// 业务主任
		queryHashMap.put("businessDirector", EnumConstants.UserType.BUSINESS_DIRECTOR.getValue());
		// 客户经理
		queryHashMap.put("salesManager", EnumConstants.UserType.SALES_MANAGER.getValue());
		
		return getSqlSession().selectList(getIbatisMapperNameSpace() + ".findAllCrmByUserTypeMap", queryHashMap);
	}

	/** 
	 * @param map
	 * @return
	 * @see com.ezendai.credit2.system.dao.SysUserDao#getBizDirector(java.util.Map)
	 */
	@Override
	public List<SysUser> getBizDirector(String dataPermission, Integer userType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dataPermission", dataPermission);
		map.put("userType", userType);
		return getSqlSession().selectList(getIbatisMapperNameSpace() + ".getBizDirector", map);
	}
	
	/**
	 * 查询员工列表带分页功能
	 * @author Ivan
	 * @param sysUserVO 查询VO
	 * @return 结果集，带总行数
	 */
	@Override
	public Pager findWithPGExtension(SysUserVO sysUserVO) {
		Pager pager = sysUserVO.getPager();
		//查询总行数
		int totalCount = (Integer)getSqlSession().selectOne(getIbatisMapperNameSpace() + ".count", sysUserVO);
		pager.setTotalCount(totalCount);;
		pager.calStart();
		//查询员工列表
		List<SysUser> resultList = getSqlSession().selectList(getIbatisMapperNameSpace() + ".findListWithPGByVO",sysUserVO);
		pager.setResultList(resultList);;
		return pager;
	}
	
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
	public List<SysGroupUser> queryMyGroupList(Long userId) {
		List<SysGroupUser> resultList = getSqlSession().selectList(getIbatisMapperNameSpace() + ".queryMyGroupList",userId);
		return resultList;
	}

//	@Override
//	public List<SysUser> getServicesInCurSalesDeptByDataPermission(
//			String dataPermission, Integer userType) {
//		Map<String, Object> queryHashMap = new HashMap<String, Object>();
//		queryHashMap.put("dataPermission", dataPermission);
//		queryHashMap.put("userType", userType);
//		return getSqlSession().selectList(getIbatisMapperNameSpace() + ".getServicesInCurSalesDeptByDataPermission", queryHashMap);
//	}
	@Override
	public List<SysUser> AssessorList(SysUserVO vo) {
		return getSqlSession().selectList(getIbatisMapperNameSpace() + ".assessorList",vo);
	}

	/** 
	 * @param code
	 * @return
	 * @see com.ezendai.credit2.system.dao.SysUserDao#getSysUserByCode(java.lang.String)
	 */
	@Override
	public SysUser getSysUserByCode(String code) {
		return (SysUser) getSqlSession().selectOne(getIbatisMapperNameSpace() + ".getSysUserByCode", code);
	}
	@Override
	public List<SysUser> araeAssessorList(SysUserVO vo) {
		return getSqlSession().selectList(getIbatisMapperNameSpace() + ".araeAssessorList",vo);
	}
	@Override
	public List<SysUser> araeTestAssessorList(SysUserVO vo) {
		return getSqlSession().selectList(getIbatisMapperNameSpace() + ".araeTestAssessorList",vo);
	}

	@Override
	public List<SysUser> araeTestCustomerServiceList(SysUserVO vo) {
		// TODO Auto-generated method stub
		return getSqlSession().selectList(getIbatisMapperNameSpace() + ".araeTestCustomerServiceList",vo);
	}

	@Override
	public List<SysUser> araeCustomerServiceList(SysUserVO vo) {
		// TODO Auto-generated method stub
		return getSqlSession().selectList(getIbatisMapperNameSpace() + ".araeCustomerServiceList",vo);
	}
	 @Override
	public long selectUserId() {
		 
		 return getSqlSession().selectOne(getIbatisMapperNameSpace() + ".selectUserId");
		
	}

	@Override
	public List<SysUser> getBizDirectorListLike(String dataPermission,
			Integer userType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dataPermission", dataPermission);
		map.put("userType", userType);
		return getSqlSession().selectList(getIbatisMapperNameSpace() + ".getBizDirectorListLike", map);
	}
}
