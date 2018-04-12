package com.ezendai.credit2.system.service.impl;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezendai.credit2.common.util.Encrypter;
import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.framework.cache.CacheService;
import com.ezendai.credit2.framework.constant.Constants;
import com.ezendai.credit2.framework.exception.BusinessException;
import com.ezendai.credit2.framework.model.UserSession;
import com.ezendai.credit2.framework.util.CollectionUtil;
import com.ezendai.credit2.framework.util.CommonUtil;
import com.ezendai.credit2.framework.util.MD5Util;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.constant.BizConstants;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.master.enumerate.EnumConstants.UserType;
import com.ezendai.credit2.master.model.BaseArea;
import com.ezendai.credit2.master.service.BaseAreaService;
import com.ezendai.credit2.system.dao.SysUserDao;
import com.ezendai.credit2.system.model.SysGroupUser;
import com.ezendai.credit2.system.model.SysLog;
import com.ezendai.credit2.system.model.SysPermission;
import com.ezendai.credit2.system.model.SysUser;
import com.ezendai.credit2.system.service.LoginService;
import com.ezendai.credit2.system.service.SysLogService;
import com.ezendai.credit2.system.service.SysUserService;
import com.ezendai.credit2.system.vo.SysUserVO;

@Service
public class SysUserServiceImpl implements SysUserService {

	@Autowired
	private SysUserDao sysUserDao;
	@Autowired
	private CacheService cacheService;
	@Autowired
	private BaseAreaService baseAreaService;
	@Autowired
	private LoginService loginService;
	@Autowired
	private SysLogService sysLogService;

	public List<SysPermission> findPermissionListBySysUserId(Long id) {
		return sysUserDao.findPermissionListBySysUserId(id);
	}

	public SysUser getSysUserByLoginName(String loginName) {
		return sysUserDao.findSysUserByLoginName(loginName);
	}

	@Override
	@Transactional
	public void updateSysUser(SysUserVO sysUserVO, String loginName) {
		if (loginName != null && !(loginName.equals(sysUserVO.getLoginName()))) {
			// 检测登录名是否重复
			checkLoginName(sysUserVO.getLoginName());
		}
		sysUserDao.update(sysUserVO);	
	}
	@Override
	public String getMaxPermission(long areaId , int userType) throws Exception{
	
			String code=baseAreaService.get(areaId).getCode();
			//如果用户类型是客户经理，权限码需要做递增。
			if(UserType.SALES_MANAGER.getValue() == userType ){
					String permission=sysUserDao.getMaxPermission(areaId);
					if(permission ==null){
						return code+="0000001";
					}else if(permission.length() == 25){
					//客户经理的权限是25位，前18位是分组权限码，截取最后7位为客户经理权限码
					String oldCode = permission.substring(18);
					char[] charArray = oldCode.toCharArray();
					//提取大于零的数做递增
					StringBuffer temp=new StringBuffer();
					for(int i=0;i<charArray.length;i++){
						
						if(charArray [i] > 0 ){
							temp.append(charArray [i] );
						}
					}
					int sum =Integer.valueOf(temp.toString())+1;
					//客户经理权限码不足7位，补0
					DecimalFormat df = new DecimalFormat("0000000");
					String newCode = df.format(sum);
			
					return	permission.replace(oldCode, newCode);
				}else if(permission.length() == 18){
					return	permission+="0000001";
				}else{
					throw new Exception("请更换网点为：（XXXXX营业部x分组）");
				}
				
			}
				
		
			if(UserType.BUSINESS_DIRECTOR.getValue() == userType){
				if(code.length() != 18){
					throw new Exception("请更换网点为：（XXXXX营业部x分组）");
				}
				
			}
			if(UserType.CUSTOMER_SERVICE.getValue() == userType || UserType.STORE_MANAGER.getValue() == userType || UserType.STORE_ASSISTANT_MANAGER.getValue() == userType){
				if(code.length() != 8 && code.length() != 13){
					throw new Exception("请更换网点为：（XXXXX营业部或XXXX市）");
				}
				
			}
			if(UserType.AUDIT.getValue() == userType || UserType.FINANCE.getValue() == userType || UserType.SYSTEM_ADMIN.getValue() == userType){
				if(code.length() != 2){
					throw new Exception("请更换网点为：（证大投资）");
				}
				
			}
		
			return code;

		
	}
	public void checkLoginName(String loginName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("loginName", loginName);
		boolean isExist = sysUserDao.exists(map);
		if (isExist) {
			throw new BusinessException("system.error.userLoginName.isExists", loginName);
		}
	}

	@Override
	public List<SysUser> findListByVO(SysUserVO sysUserVo) {
		return sysUserDao.findListByVo(sysUserVo);
	}

	@Override
	public UserSession getUserSessionFromDB(Long userId, String ipAddress) {
		// 创建用户信息
		UserSession user = new UserSession();
		SysUser sysUser = sysUserDao.get(userId);
		user.setId(sysUser.getId());
		user.setName(sysUser.getName());
		user.setLoginName(sysUser.getLoginName());
		user.setIpAddress(ipAddress);
		user.setUserType(sysUser.getUserType());
		// 登陆当前时间为续命时间初始时间
		user.setLastExtension(new Date());
		//设置权限代码
		List<String> permissionCodeList = loginService.findPermissionCodeList(sysUser.getId());
		user.setPermissionCodeList(permissionCodeList);
		return user;

	}

	@Override
	public String session2Cache(Long userId, String ipAddress) {
		UserSession session = getUserSessionFromDB(userId, ipAddress);
		String uuid = CommonUtil.getUUID();
		String memcachedKey = Encrypter.encrypt(uuid);
		cacheService.data2Cache(memcachedKey, session, Constants.SESSION_EXPIRE);
		// 往session容器设置session缓存信息
		ApplicationContext.setUser(session);
		return memcachedKey;

	}

	@Override
	public SysUser get(Long id) {
		return sysUserDao.get(id);
	}

	@Override
	public BaseArea getBaseAreaByUserId(Long userId, String identifier) {
		SysUser sysUser = sysUserDao.get(userId);
		if (sysUser.getDataPermission() == null)
			return null;

		return baseAreaService.getUpperBaseAreaByIdentifier(sysUser.getDataPermission(), identifier);
	}

	@Override
	public List<Long> getSalesDeptIdByUserId(Long userId) {
		return getBaseAreaIdListByUserId(userId,BizConstants.CREDIT2_SALESDEPARTMENT);
	}
	
	@Override
	public List<BaseArea> getSalesDeptByUserId(Long userId) {
		SysUser user = sysUserDao.get(userId);
		if( user.getUserType().equals(EnumConstants.UserType.CHANNEL_SPECIALIST.getValue())){
			return baseAreaService.getSeloanDept();
		}
		
		return getBaseAreaListByUserId(userId,BizConstants.CREDIT2_SALESDEPARTMENT);
	}
	@Override
	public List<BaseArea> getCurSalesDept() {
		Long userId = ApplicationContext.getUser().getId();
		return getSalesDeptByUserId(userId);
	}
	@Override
	public BaseArea getCityByUserId(Long userId) {

		return getBaseAreaByUserId(userId, BizConstants.CREDIT2_CITY);
	}
	@Override
	public BaseArea getCurCity() {
		Long userId = ApplicationContext.getUser().getId();
		return getCityByUserId(userId);
	}

	@Override
	@Transactional
	public int resetPassword(Long userId, String original, String reset) {

		SysUser sysUser = sysUserDao.get(userId);
		if (sysUser != null) {
			if (StringUtils.endsWithIgnoreCase(sysUser.getSignPassword(), MD5Util.md5Hex(original))) {
				SysUserVO sysUserVO = new SysUserVO();
				sysUserVO.setSignPassword(MD5Util.md5Hex(reset));
				sysUserVO.setId(userId);
				return sysUserDao.update(sysUserVO);
			}
		}
		return -1;
	}

	@Override
	public List<SysUser> getAllCrmsInSalesDept(String salesDeptCode) {

		return sysUserDao.getAllCrmsInSalesDept(salesDeptCode);
	}

	@Override
	public List<SysUser> getCrmsInSalesDeptByProductId(Long userId, Long productId) {
		List<SysUser> sysUserList = new ArrayList<SysUser>();
		List<BaseArea> salesDeptList = this.getSalesDeptByUserId(userId);
		for (BaseArea baseArea : salesDeptList) {
			sysUserList.addAll(sysUserDao.getCrmsInSalesDeptByProductId(baseArea.getCode(), productId));
		}
		return sysUserList;
	}
	
	@Override
	public List<SysUser> getCrmsInSalesDeptByProductIdAndSalesDeptId(Long salesDeptId, Long productId) {
		BaseArea baseArea = baseAreaService.get(salesDeptId);
		return  sysUserDao.getCrmsInSalesDeptByProductId(baseArea.getCode(), productId);
	}
	
	

	@Override
	public List<SysUser> getSalesByProductId(Long salesDeptId, Long productId) {
		BaseArea baseArea = baseAreaService.get(salesDeptId);
		return  sysUserDao.getSalesByProductId(baseArea.getCode(), productId);
	}

	@Override
	public List<SysUser> getServicesInCurSalesDeptByProductId(Long userId, Long productId) {
		List<BaseArea> salesDeptList =  this.getSalesDeptByUserId(userId);
	    List<String> salesDeptCodeList = new ArrayList<String>();
	    if(CollectionUtil.isNotEmpty(salesDeptList)){
	    	if(salesDeptList.size() >1){//跨网点账号
	    		for (BaseArea baseArea : salesDeptList) {//截取到为城市权限(8位)
	    			salesDeptCodeList.add(baseArea.getCode().substring(0, 8));
	    		}
	    	}else{//不跨网点,一个账号只有一个门店权限
	    		salesDeptCodeList.add(salesDeptList.get(0).getCode());
	    	}
	    }
		return sysUserDao.getServicesInCurSalesDeptByProductId(salesDeptCodeList, productId);
	}
	
	@Override
	public List<SysUser> getServicesInCurSalesDeptByProductIdList(Long userId, List<Long> productIdList) {
		List<BaseArea> salesDeptList =  this.getSalesDeptByUserId(userId);
	    List<String> salesDeptCodeList = new ArrayList<String>();
	    if(CollectionUtil.isNotEmpty(salesDeptList)){
	    	if(salesDeptList.size() >1){//跨网点账号
	    		for (BaseArea baseArea : salesDeptList) {//截取到为城市权限(8位)
	    			salesDeptCodeList.add(baseArea.getCode().substring(0, 8));
	    		}
	    	}else{//不跨网点,一个账号只有一个门店权限
	    		salesDeptCodeList.add(salesDeptList.get(0).getCode());
	    	}
	    }
		return sysUserDao.getServicesInCurSalesDeptByProductIdList(salesDeptCodeList, productIdList);
	}

	@Override
	public Integer testConnection() {
		return sysUserDao.testConnection();
	}

	@Override
	public List<String> findPermissionCodeList(Long id) {
		return sysUserDao.findPermissionCodeList(id);
	}

	/** 
	 * @return
	 * @see com.ezendai.credit2.system.service.SysUserService#findAllCrmList()
	 */
	@Override
	public List<SysUser> findAllCrmList() {
		return sysUserDao.findAllCrmList();
	}

	/** 
	 * @return
	 * @see com.ezendai.credit2.system.service.SysUserService#getCurSalesDeptManager()
	 */
	@Override
	public List<SysUser> getCurSalesDeptManager() {
		List<BaseArea> baseAreaList = getCurSalesDept();
		if(CollectionUtil.isNotEmpty(baseAreaList)) {
			List<Long> areaIdList = new ArrayList<Long>();
			for(BaseArea baseArea : baseAreaList){
				areaIdList.add(baseArea.getId());
			}
			SysUserVO sysUserVO = new SysUserVO();
			sysUserVO.setAreaIdList(areaIdList);
			List<Integer> userTypeList = new ArrayList<Integer>();
			userTypeList.add(EnumConstants.UserType.STORE_MANAGER.getValue());
			userTypeList.add(EnumConstants.UserType.STORE_ASSISTANT_MANAGER.getValue());
			sysUserVO.setUserTypeList(userTypeList);
			return sysUserDao.findListByVo(sysUserVO);
		}
		return null;
	}

	/** 
	 * @param userId
	 * @param identifier
	 * @return
	 * @see com.ezendai.credit2.system.service.SysUserService#getBaseAreaListByUserId(java.lang.Long, java.lang.String)
	 */
	@Override
	public List<Long> getBaseAreaIdListByUserId(Long userId, String identifier) {
		SysUser sysUser = sysUserDao.get(userId);
		if (sysUser.getDataPermission() == null)
			return null;

		return baseAreaService.getUpperBaseAreaIdListByIdentifier(sysUser.getDataPermission(), identifier);
	}
	/** 
	 * @param userId
	 * @param identifier
	 * @return
	 * @see com.ezendai.credit2.system.service.SysUserService#getBaseAreaListByUserId(java.lang.Long, java.lang.String)
	 */
	@Override
	public List<BaseArea> getBaseAreaListByUserId(Long userId, String identifier) {
		SysUser sysUser = sysUserDao.get(userId);
		if (sysUser.getDataPermission() == null)
			return null;
		
		return baseAreaService.getUpperBaseAreaListByIdentifier(sysUser.getDataPermission(), identifier);
	}

	/** 
	 * @param crm
	 * @return
	 * @see com.ezendai.credit2.system.service.SysUserService#getBizDirectorByCrm(com.ezendai.credit2.system.model.SysUser)
	 */
	@Override
	public SysUser getBizDirectorByCrm(SysUser crm) {
		if (crm != null && StringUtils.isNotBlank(crm.getDataPermission())
				&& crm.getDataPermission().length() >= 18) {
			List<SysUser> list = sysUserDao.getBizDirector(crm.getDataPermission().substring(0, 18),
					EnumConstants.UserType.BUSINESS_DIRECTOR.getValue());
			SysUser bizDirector = null;
			if (list != null && !list.isEmpty()) {
				bizDirector = list.get(0);
			}
			return bizDirector;
		}
		return null;
	}
	/** 
	 * @param crm
	 * @return
	 * @see com.ezendai.credit2.system.service.SysUserService#getBizDirectorByCrm(com.ezendai.credit2.system.model.SysUser)
	 */
	@Override
	public List<SysUser> getBizDirectorByUser(SysUser crm) {
		if (crm != null && StringUtils.isNotBlank(crm.getDataPermission())
				&& crm.getDataPermission().length() >= 13) {
			List<SysUser> list = sysUserDao.getBizDirectorListLike(crm.getDataPermission().substring(0, 13),
					EnumConstants.UserType.BUSINESS_DIRECTOR.getValue());
			if (list != null && !list.isEmpty()) {
				return list;
			}
		
		}if (crm != null && StringUtils.isNotBlank(crm.getDataPermission())
				&& crm.getDataPermission().length() == 8) {
			List<SysUser> list = sysUserDao.getBizDirectorListLike(crm.getDataPermission().substring(0, 8),
					EnumConstants.UserType.BUSINESS_DIRECTOR.getValue());
			if (list != null && !list.isEmpty()) {
				return list;
			}
		
		}
		return null;
	}
	
	/**
	 * 查询员工列表带分页功能
	 * @author Ivan
	 * @param sysUserVO 查询VO
	 * @return 结果集，带总行数
	 */
	@Override
	public Pager findWithPGExtension(SysUserVO sysUserVO) {
		return sysUserDao.findWithPGExtension(sysUserVO);
	}
	
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
	@Override
	public void insertSysUser(SysUser sysUser){
		/** 判断登陆名|员工工号是否已经存在*/
		String loginName = sysUser.getLoginName();
		if (StringUtils.isBlank(loginName)) {
			throw new BusinessException("system.error.userLoginName.isExists", loginName);
		}
		checkLoginName(loginName);
		sysUserDao.insert(sysUser);
		// 插入系统日志
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.EMPLOYEE_INFORMATION_MAINTENANCE.getValue());
		sysLog.setOptType(EnumConstants.OptionType.ADD_EMPLOYEE.getValue());
		sysLog.setRemark("员工姓名：" + sysUser.getName()+"   "+ "工号：" +sysUser.getCode());
		sysLogService.insert(sysLog);
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
	@Override
	public List<SysGroupUser> queryMyGroupList(Long userId) {
		return sysUserDao.queryMyGroupList(userId);
	}
	
//	@Override
//	public List<SysUser> getServicesInCurSalesDeptByDataPermission(
//			String dataPermission, Integer userType) {
//		return sysUserDao.getServicesInCurSalesDeptByDataPermission(dataPermission, userType);
//	}
	@Override
	public List<SysUser> AssessorList(SysUserVO vo) {
		
		return sysUserDao.AssessorList(vo);
	}

	/** 
	 * @param code
	 * @return
	 * @see com.ezendai.credit2.system.service.SysUserService#getSysUserByCode(java.lang.String)
	 */
	@Override
	public SysUser getSysUserByCode(String code) {
		return sysUserDao.getSysUserByCode(code);
	}
	@Override
	public List<SysUser> araeAssessorList(SysUserVO vo) {
		return sysUserDao.araeAssessorList(vo);
	}
	@Override
	public List<SysUser> araeTestAssessorList(SysUserVO vo) {
		return sysUserDao.araeTestAssessorList(vo);
	}

	@Override
	public List<SysUser> araeTestCustomerServiceList(SysUserVO vo) {
		// TODO Auto-generated method stub
		return sysUserDao.araeTestCustomerServiceList(vo);
	}

	@Override
	public List<SysUser> araeCustomerServiceList(SysUserVO vo) {
		// TODO Auto-generated method stub
		return sysUserDao.araeCustomerServiceList(vo);
	}
	
	@Override
	public long selectUserId() {
		return sysUserDao.selectUserId();
	}

	@Override
	public List<Long> getSalesDeptIdByUserIdAndDeptType(Long userId) {
		// TODO Auto-generated method stub
		SysUser sysUser = sysUserDao.get(userId);
		if (sysUser.getDataPermission() == null)
			return null;
		
		return baseAreaService.getUpperBaseAreaIdListByIdentifierAndDeptType(sysUser.getDataPermission().substring(0,4), BizConstants.CREDIT2_SALESDEPARTMENT);
	}
}
