package com.ezendai.credit2.system.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezendai.credit2.apply.model.Product;
import com.ezendai.credit2.apply.service.ProductService;
import com.ezendai.credit2.apply.service.SysProductUserService;
import com.ezendai.credit2.apply.vo.SysProductUserVO;
import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.framework.constant.Constants;
import com.ezendai.credit2.framework.exception.BusinessException;
import com.ezendai.credit2.framework.model.UserSession;
import com.ezendai.credit2.framework.util.MD5Util;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.master.model.BaseArea;
import com.ezendai.credit2.master.model.SysEnumerate;
import com.ezendai.credit2.master.service.BaseAreaService;
import com.ezendai.credit2.master.service.SysEnumerateService;
import com.ezendai.credit2.system.assembler.SysUserAssembler;
import com.ezendai.credit2.system.model.SysGroupUser;
import com.ezendai.credit2.system.model.SysLog;
import com.ezendai.credit2.system.model.SysUser;
import com.ezendai.credit2.system.service.SysGroupUserService;
import com.ezendai.credit2.system.service.SysLogService;
import com.ezendai.credit2.system.service.SysUserService;
import com.ezendai.credit2.system.vo.SysGroupUserVO;
import com.ezendai.credit2.system.vo.SysUserVO;

/**
 * 
 * @author Ivan
 *
 */
@Controller
@RequestMapping("/sysUser")
public class SysUserController extends BaseController{
	
	/**
	 * 利用Spring自动注入
	 */
	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private BaseAreaService baseAreaService;
	
	@Autowired
	private SysProductUserService sysProductUserService;
	
	@Autowired
	private SysLogService sysLogService;
	
	@Autowired
	private SysEnumerateService  sysEnumerateService;
	
	@Autowired
	private ProductService  productService;
	
	@Autowired
	private SysGroupUserService  sysGroupUserService;
	
	/**
	 * 指定员工管理页面
	 * @author Ivan
	 * @param sysUserVO 前台参数对象
	 * @return
	 */
	@RequestMapping("/main")
	public String main(HttpServletRequest request) {
		/* 设置数据字典 */
		setDataDictionaryArr(new String[] { EnumConstants.USER_TYPE});
		request.setAttribute("userType", ApplicationContext.getUser().getUserType());
		request.setAttribute(GRID_ENUM_JSON, getEnumJson());
		return "/system/sysUserList";
	}
	/**
	 * 
	 * <pre>
	 * 获取 用户类型
	 * </pre>
	 *
	 * @return
	 */
	@RequestMapping("/userTypes")
	@ResponseBody
	public List<SysEnumerate> userTypes(Long userId) {
		UserSession user = ApplicationContext.getUser();
		 List<SysEnumerate> findEnumList = sysEnumerateService.findSysEnumerateListByType("USER_TYPE");
		 List<SysEnumerate> userTypeList =new ArrayList<SysEnumerate>();
		 if( findEnumList == null || findEnumList.size() < 0)
			 return findEnumList;
		 if(!EnumConstants.UserType.SYSTEM_ADMIN.getValue().equals(user.getUserType())){
			 for (int i= 0 ; i< findEnumList.size() ;i++) {
				 int enumCode = findEnumList.get(i).getEnumCode();
				if(enumCode <= 6 && enumCode >= 2){
					userTypeList.add(findEnumList.get(i));
				}
			}
				return userTypeList;
		 }
		 SysEnumerate sysEnumerate = new SysEnumerate();
		 sysEnumerate.setEnumValue("所有");
		 sysEnumerate.setEnumCode(null);
		 findEnumList.add(0, sysEnumerate);
		 return findEnumList;
	}
	/**
	 * 查询员工数据
	 * @author Ivan
	 * @return
	 */
	@RequestMapping("/getSearchJson")
	@ResponseBody
	@SuppressWarnings(value={"rawtypes"})
	public Map getSearchJson(SysUserVO sysUserVO,int rows, int page) {
		UserSession user = ApplicationContext.getUser();
		Pager pager = new Pager();
		pager.setRows(rows);
		pager.setPage(page);
		pager.setSidx("ID");
		pager.setSort("ASC");
		sysUserVO.setPager(pager);
		//查询
		if(!EnumConstants.UserType.SYSTEM_ADMIN.getValue().equals(user.getUserType())){
			// 车贷或小企贷
			List<Product> products = productService.findProductsByUserId(user.getId());
			List<Integer > productIdList = new ArrayList<Integer>();
			for (Product p : products) {
				productIdList.add(Integer.parseInt(p.getId().toString()));
			}
			sysUserVO.setProductIdList(productIdList);
			//可以查看的用户类型
			if(sysUserVO.getUserType() == null){
				List<Integer> userTypeList = new  ArrayList<Integer>();
				userTypeList.add(EnumConstants.UserType.BUSINESS_DIRECTOR.getValue());
				userTypeList.add(EnumConstants.UserType.STORE_MANAGER.getValue());
				userTypeList.add(EnumConstants.UserType.STORE_ASSISTANT_MANAGER.getValue());
				userTypeList.add(EnumConstants.UserType.SALES_MANAGER.getValue());
				userTypeList.add(EnumConstants.UserType.CUSTOMER_SERVICE.getValue());
				sysUserVO.setUserTypeList(userTypeList);
			}
			//门店经理副理客服操作当前营业部下的员工信息
			if(!user.getUserType().equals(EnumConstants.UserType.STORE_ASSISTANT_MANAGER.getValue()) ||!user.getUserType().equals(EnumConstants.UserType.STORE_MANAGER.getValue()) 
					|| !user.getUserType().equals(EnumConstants.UserType.CUSTOMER_SERVICE.getValue())){
					SysUser sysUser = sysUserService.get(user.getId());
					sysUserVO.setMatchDataPermission(sysUser.getDataPermission());
			}
		}
		pager = sysUserService.findWithPGExtension(sysUserVO);
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("total", pager.getTotalCount());
		result.put("rows", pager.getResultList());
		return result;
	}
	
	
	/**
	 * 加载某员工信息
	 * @author Ivan
	 * @return
	 */
	@RequestMapping("/loadSysUserInfo")
	@ResponseBody
	@SuppressWarnings(value={"rawtypes","unchecked"})
	public Map loadSysUserInfo(long userId) {
		Map map = new HashMap();
		String msg = "";
		boolean isSuccess = true;
		SysUser sysUser = sysUserService.get(userId);
		if (sysUser != null) {
			Long areaId = sysUser.getAreaId();
			if (areaId != null) {
				BaseArea baseArea = baseAreaService.get(areaId);
				sysUser.setBaseArea(baseArea);
			}
		} else {
			isSuccess = false;
			msg = "记录不存在";
		}
		map.put("isSuccess", isSuccess);
		map.put("sysUser", sysUser);
		map.put("msg", msg);
		return map;
	}


	/**
	 * 保存某员工信息
	 * @author Ivan
	 * @return
	 */
	@RequestMapping("/saveSysUserInfo")
	@ResponseBody
	public Map<Object,Object> saveSysUserInfo(@RequestParam(value = "id") Long id,
											  @RequestParam(value = "code") String code,	
											  @RequestParam(value = "name") String name,	
											  @RequestParam(value = "userType") Integer userType,	
											  @RequestParam(value = "areaId") Long areaId,	
											  @RequestParam(value = "productList") Long[] productList,
											  @RequestParam(value = "groupList") Long[] groupList	
			) {
		Map <Object,Object> map = new HashMap<Object,Object>();
		SysUserVO sysUserVO = new SysUserVO();
		sysUserVO.setId(id);
		sysUserVO.setName(name);
		sysUserVO.setCode(code);
		sysUserVO.setUserType(userType);
		sysUserVO.setAreaId(areaId);
		boolean isSuccess = true;
		String msg = "";
		try {
			//营业网点编号
			BaseArea baseArea = null;
			Long areaId2 = sysUserVO.getAreaId();
			StringBuffer fullName = new StringBuffer();
			if (areaId2 != null) {
				baseArea = baseAreaService.get(areaId);
				if (baseArea != null) {
					fullName.append(baseArea.getFullName()) ;
					fullName.append("/"+sysUserVO.getName());
				}
			}
			//权限码设置
			String permission = sysUserService.getMaxPermission(areaId, sysUserVO.getUserType());
		
			if (sysUserVO.getId() == null) {
				Long selectUserId = sysUserService.selectUserId();
					if(selectUserId != null){
						sysUserVO.setId(selectUserId);
						//添加权限
						SysGroupUserVO sysGroupUserVO = new SysGroupUserVO();
						sysGroupUserVO.setUserId(selectUserId);
						List<Long> groups =new ArrayList<Long>();
						for (int i = 0; i < groupList.length; i++) {
							groups.add(groupList[i]);
						}
							sysGroupUserVO.setAddGroupIdList(groups);
							sysGroupUserService.modifyGroupUserMap(sysGroupUserVO);
							
							//添加产品
							SysProductUserVO sysProductUserVO = new SysProductUserVO();
							sysProductUserVO.setUserId(selectUserId);
							List<Long>  products = new ArrayList<Long>();
							for (int i = 0; i < productList.length; i++) {
								products.add(productList[i]);
							}
							sysProductUserVO.setAddProductIdList(products);
							sysProductUserService.modifyProductUserMap(sysProductUserVO);
							
							
							//新增操作
							SysUser sysUser = SysUserAssembler.transferVO2Model(sysUserVO);
							sysUser.setLoginName(sysUser.getCode());
							sysUser.setIsDeleted(0);
							sysUser.setStatus(0);
							sysUser.setFullName(fullName.toString());
							sysUser.setDataPermission(permission);
							sysUser.setAcceptAuditTask(1);
							
							//新增员工，默认密码为：111111
							String pwd = "111111";
							pwd = MD5Util.md5Hex(pwd + Constants.PWD_SUFFIX);
							sysUser.setSignPassword(pwd);
							sysUserService.insertSysUser(sysUser);
							msg = "新增成功";
					}
				} else {
							//更新操作
							sysUserVO.setFullName(fullName.toString());
							sysUserVO.setDataPermission(permission);
							sysUserService.updateSysUser(sysUserVO, null);
							
							List<SysGroupUser> groupsUser = null;
							//权限组修改
							SysGroupUserVO sysGroupUserVO = new SysGroupUserVO();
							sysGroupUserVO.setUserId(sysUserVO.getId());
								//查询员工已绑定的权限组
								groupsUser = sysUserService.queryMyGroupList(sysUserVO.getId());
								//已绑定的组
								List<Long> groupList1 = new ArrayList<Long>();
								for (int i=0;i<groupsUser.size();i++) {
									groupList1.add(groupsUser.get(i).getGroupId());
								}
								List<Long> groupList11 = (List<Long>) ((ArrayList)groupList1).clone();
								
								//本次期望绑定的组
								List<Long> groupList2 = new ArrayList<Long>();
									for (int i=0;i<groupList.length;i++) {
										groupList2.add(groupList[i]);
									}
								//返回已解绑的权限组
								groupList1.removeAll(groupList2);
								sysGroupUserVO.setRemoveGroupIdList(groupList1);
								//返回本次需要绑定的权限组
								groupList2.removeAll(groupList11);
								sysGroupUserVO.setAddGroupIdList(groupList2);
								sysGroupUserService.modifyGroupUserMap(sysGroupUserVO);
								
								//产品组修改
								SysProductUserVO sysProductUserVO = new SysProductUserVO();
								sysProductUserVO.setUserId(sysUserVO.getId());
						
									//查询员工已绑定的产品组
									List<Long> productList1 = sysProductUserService.getProductIdByUserId(sysUserVO.getId());
									List<Long> productList11 = (List<Long>) ((ArrayList)productList1).clone();
									
									//本次期望绑定的组
									List<Long> productList2 = new ArrayList<Long>();
										for (int i=0;i<productList.length;i++) {
											productList2.add(productList[i]);
										}
									//返回已解绑的权限组
									productList1.removeAll(productList2);
									sysProductUserVO.setRemoveProductIdList(productList1);
									//返回本次需要绑定的权限组
									productList2.removeAll(productList11);
									sysProductUserVO.setAddProductIdList(productList2);
									sysProductUserService.modifyProductUserMap(sysProductUserVO);
							msg = "修改成功";
							// 插入系统日志
							SysLog sysLog = new SysLog();
							sysLog.setOptModule(EnumConstants.OptionModule.EMPLOYEE_INFORMATION_MAINTENANCE.getValue());
							sysLog.setOptType(EnumConstants.OptionType.EMPLOYEE_INFORMATION_CHANGES.getValue());
							sysLog.setRemark("员工姓名：" + sysUserVO.getName()+"   "+ "工号：" +sysUserVO.getCode());
							sysLogService.insert(sysLog);
						}
		} catch(BusinessException ex) {
			isSuccess = false;
			msg = "工号重复";
		} catch(Exception ex) {
			isSuccess = false;
			msg = ex.getMessage();
		}
		map.put("isSuccess", isSuccess);
		map.put("msg", msg);
		return map;
	}
	
	/**
	 * 重置密码
	 * @author Ivan
	 * @return
	 */
	@RequestMapping("/changePwd")
	@ResponseBody
	public Map changePwd(@RequestParam(value="userId")Long userId, @RequestParam(value="pwd")String pwd) {
		Map map = new HashMap();
		boolean isSuccess = true;
		String msg = "";
		try {
			SysUser sysUser = sysUserService.get(userId);
			if (sysUser != null) {
				SysUserVO sysUserVO = new SysUserVO();
				sysUserVO.setId(userId);
				//将密码进行加密，保存到数据库
				pwd = MD5Util.md5Hex(pwd + Constants.PWD_SUFFIX);
				sysUserVO.setSignPassword(pwd);
				//修改密码
				sysUserService.updateSysUser(sysUserVO, null);
				// 插入系统日志
				SysLog sysLog = new SysLog();
				sysLog.setOptModule(EnumConstants.OptionModule.EMPLOYEE_INFORMATION_MAINTENANCE.getValue());
				sysLog.setOptType(EnumConstants.OptionType.RESET_PASSWORD.getValue());
				sysLog.setRemark("员工姓名：" + sysUser.getName()+"   "+ "工号：" +sysUser.getCode());
				sysLogService.insert(sysLog);
			} else {
				isSuccess = false;
				msg = "客户不存在";
			}
		} catch(Exception ex) {
			isSuccess = false;
			msg = ex.getMessage();
		}
		map.put("isSuccess", isSuccess);
		map.put("msg", msg);
		return map;
	}
	
	/**
	 * 锁定/恢复员工身份
	 * @author Ivan
	 * @return
	 */
	@RequestMapping("/lockSysUser")
	@ResponseBody
	public Map lockSysUser(@RequestParam(value="userId")Long userId, @RequestParam(value="status")int status) {
		Map map = new HashMap();
		boolean isSuccess = true;
		String msg = "";
		try {
			SysUser sysUser = sysUserService.get(userId);
			if (sysUser != null) {
				SysUserVO sysUserVO = new SysUserVO();
				sysUserVO.setId(userId);
				sysUserVO.setStatus(status);
				//锁定
				sysUserService.updateSysUser(sysUserVO, null);
				// 插入系统日志
				SysLog sysLog = new SysLog();
				sysLog.setOptModule(EnumConstants.OptionModule.EMPLOYEE_INFORMATION_MAINTENANCE.getValue());
				sysLog.setOptType(EnumConstants.OptionType.LOCKING.getValue());
				sysLog.setRemark("员工姓名：" + sysUser.getName()+"   "+ "工号：" +sysUser.getCode());
				sysLogService.insert(sysLog);
			} else {
				isSuccess = false;
				msg = "客户不存在";
			}
		} catch(Exception ex) {
			isSuccess = false;
			msg = ex.getMessage();
		}
		map.put("isSuccess", isSuccess);
		map.put("msg", msg);
		return map;
	}
	/**
	 * 删除员工
	 * @author 
	 * @return
	 */
	@RequestMapping("/deletedUser")
	@ResponseBody
	public Map deletedUser(@RequestParam(value="userId")Long userId) {
		Map map = new HashMap();
		boolean isSuccess = true;
		String msg = "";
		try {
			SysUser sysUser = sysUserService.get(userId);
			if (sysUser != null) {
				SysUserVO sysUserVO = new SysUserVO();
				sysUserVO.setId(userId);
				sysUserVO.setIsDeleted(1);
				//将状态改为以删除
				sysUserService.updateSysUser(sysUserVO, null);
				// 插入系统日志
				SysLog sysLog = new SysLog();
				sysLog.setOptModule(EnumConstants.OptionModule.EMPLOYEE_INFORMATION_MAINTENANCE.getValue());
				sysLog.setOptType(EnumConstants.OptionType.DELETE_EMPLOYEE.getValue());
				sysLog.setRemark("员工姓名：" + sysUser.getName()+"   "+ "工号：" +sysUser.getCode());
				sysLogService.insert(sysLog);
			} else {
				isSuccess = false;
				msg = "客户不存在";
			}
		} catch(Exception ex) {
			isSuccess = false;
			msg = ex.getMessage();
		}
		map.put("isSuccess", isSuccess);
		map.put("msg", msg);
		return map;
	}
	
	/**
	 * 查询员工已绑定的权限组列表
	 * @author Ivan
	 * @return
	 */
	@RequestMapping("/queryMyGroupList")
	@ResponseBody
	public Map queryMyGroupList(@RequestParam(value="userId")Long userId) {
		Map map = new HashMap();
		List<SysGroupUser> gorups = null;
		boolean isSuccess = true;
		String msg = "";
		try {
			SysUser sysUser = sysUserService.get(userId);
			if (sysUser != null) {
				gorups = sysUserService.queryMyGroupList(userId);
			} else {
				isSuccess = false;
				msg = "客户不存在";
			}
		} catch(Exception ex) {
			isSuccess = false;
			msg = ex.getMessage();
		}
		map.put("isSuccess", isSuccess);
		map.put("gorups", gorups);
		map.put("msg", msg);
		return map;
	}
	
	/**
	 * 查询员工已绑定的产品组列表
	 * @author Ivan
	 * @return
	 */
	@RequestMapping("/queryMyProductList")
	@ResponseBody
	public Map queryMyProductList(@RequestParam(value="userId")Long userId) {
		Map map = new HashMap();
		List<Long> products = null;
		boolean isSuccess = true;
		String msg = "";
		try {
			SysUser sysUser = sysUserService.get(userId);
			if (sysUser != null) {
				products = sysProductUserService.getProductIdByUserId(userId);
			} else {
				isSuccess = false;
				msg = "客户不存在";
			}
		} catch(Exception ex) {
			isSuccess = false;
			msg = ex.getMessage();
		}
		map.put("isSuccess", isSuccess);
		map.put("products", products);
		map.put("msg", msg);
		return map;
	}
}