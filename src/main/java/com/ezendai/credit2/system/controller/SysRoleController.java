package com.ezendai.credit2.system.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezendai.credit2.framework.exception.BusinessException;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.system.controller.SysPermissionController.TreeData;
import com.ezendai.credit2.system.model.SysPermission;
import com.ezendai.credit2.system.model.SysRole;
import com.ezendai.credit2.system.service.SysPermissionService;
import com.ezendai.credit2.system.service.SysRoleService;
import com.ezendai.credit2.system.vo.SysRoleVO;
/**   
*    
* 项目名称：credit2-main   
* 类名称：SysRoleController   
* 类描述：角色权限维护   
* 创建人：liboyan   
* 创建时间：2016年1月21日 下午4:55:16   
* 修改人：liboyan   
* 修改时间：2016年1月21日 下午4:55:16   
* 修改备注：   
* @version    
*    
*/
@Controller
@RequestMapping("/sysRole")

public class SysRoleController extends BaseController {
	
	private static  final  Logger   logger =Logger.getLogger(SysRoleController.class);
	
	@Autowired
	private SysRoleService sysRoleService;
	
	@Autowired
	private SysRolePermissionSerivce sysRolePermissionSerivce;
	
	/* CM-2747 zhangds begin */
	@Autowired
	private SysPermissionService sysPermissionService;
	/* CM-2747 zhangds end */
	
	@RequestMapping("/sysRoleMain")
	public String index(HttpServletRequest request){
		
		return "system/sysRoleList";
	}
	
	@RequestMapping("/sysRoleList")
	@ResponseBody
	 public Map<String, Object> sysRoleList(SysRoleVO sysRoleVO, int rows, int page){
			Pager p = new Pager();
			p.setRows(rows);
			p.setPage(page);
			sysRoleVO.setPager(p);
			p.setSidx("ID");
			p.setSort("ASC");
			Pager flowByVOList = sysRoleService.findwithPG(sysRoleVO);
			Map<String, Object> result = new LinkedHashMap<String, Object>();
			result.put("total", flowByVOList.getTotalCount());
			result.put("rows", flowByVOList.getResultList());
			return result;
	 }
	@RequestMapping("/loadSysRole")
	@ResponseBody
	@SuppressWarnings(value={"rawtypes","unchecked"})
	public  Map  loadSysRole(@RequestParam Long id){
		Map map = new HashMap();
		String msg = "";
		SysRole sysRole = sysRoleService.get(id);
		boolean isSuccess = true;
	
		if (sysRole != null) {
	
		} else {
			isSuccess = false;
			msg = "记录不存在";
		}
		map.put("isSuccess", isSuccess);
		map.put("sysRole", sysRole);
		map.put("msg", msg);
		return map;
	}
	/**
	 * 新增角色
	 * @param baseAreaVO
	 * @return
	 */
	@RequestMapping("/insertSysRole")
	@ResponseBody
	public Map<String , Object> insertSysRole(@RequestParam String code,@RequestParam String  name,@RequestParam String memo,@RequestParam Long[] sysPermissionList) {
		SysRole sysRole = new SysRole();
		sysRole.setCode(code);
		sysRole.setName(name);
		sysRole.setMemo(memo);
		sysRole.setStatus((short)0);
		sysRole.setIsDeleted((short)0);
		sysRole.setSysPermissionList(sysPermissionList);
		Map<String , Object> map = new HashMap<String , Object>();
		boolean isSuccess = true;
		String msg = "";
		try {
				sysRoleService.insert(sysRole);
				msg = "新增成功";
	
		} catch(BusinessException ex) {
			isSuccess = false;
			msg = "新增失败";
		} catch(Exception ex) {
			isSuccess = false;
			msg = ex.getMessage();
		}
		map.put("isSuccess", isSuccess);
		map.put("msg", msg);
		return map;
	}
	/**
	 * 修改角色
	 * @param baseAreaVO
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public Map<String , Object> update(@RequestParam  Long id, @RequestParam String code,@RequestParam String  name,@RequestParam String memo,@RequestParam Long[] sysPermissionList) {
		SysRoleVO sysRoleVO = new SysRoleVO();
		sysRoleVO.setId(id);
		sysRoleVO.setCode(code);
		sysRoleVO.setName(name);
		sysRoleVO.setMemo(memo);
		sysRoleVO.setStatus((short)0);
		sysRoleVO.setIsDeleted(0);
		sysRoleVO.setSysPermissionList(sysPermissionList);
		Map<String , Object> map = new HashMap<String , Object>();
		boolean isSuccess = true;
		String msg = "";
		try {
				sysRoleService.update(sysRoleVO);
				msg = "修改成功";
		} catch(BusinessException ex) {
			isSuccess = false;
			msg = "修改失败";
		} catch(Exception ex) {
			isSuccess = false;
			msg = ex.getMessage();
		}
		map.put("isSuccess", isSuccess);
		map.put("msg", msg);
		return map;
	}
	/**  
	 * 删除角色
	 * @author 
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	@SuppressWarnings(value={"rawtypes","unchecked"})
	public Map delete(@RequestParam(value="id")Long id) {
		Map map = new HashMap();
		boolean isSuccess = true;
		String msg = "";
		try {
			SysRole sysRole = sysRoleService.get(id);
			if (sysRole != null) {
				sysRoleService.delete(id);
			} else {
				isSuccess = false;
				msg = "角色不存在";
			}
		} catch(Exception ex) {
			isSuccess = false;
			msg = ex.getMessage();
		}
		map.put("isSuccess", isSuccess);
		map.put("msg", msg);
		return map;
	}

	/* CM-2747 zhangds begin */
	/**
	 * 查询权限列表
	 * @param roleId
	 * @return
	 */
	@RequestMapping("/queryMyPermissionList")
	@ResponseBody
	public Map queryMyPermissionList(@RequestParam(value="id")Long roleId) {
		Map map = new HashMap();
		List<Long> permissions = null;
		boolean isSuccess = true;
		String msg = "";
		try {
			SysRole sysRole = sysRoleService.get(roleId);
			if (sysRole != null) {
//				permissions = sysRolePermissionSerivce.queryMyPermissionList(roleId);
				permissions = getPermissionIds(roleId);
				logger.info("....获取权限列表...roleId:" +roleId );
			} else {
				isSuccess = false;
				msg = "角色不存在";
			}
		} catch(Exception ex) {
			isSuccess = false;
			msg = ex.getMessage();
		}
		map.put("isSuccess", isSuccess);
		map.put("permissions", permissions);
		map.put("msg", msg);
		return map;   
	}
	
	/*   
	* 方法描述：菜单权限上下级关联
	* 创建人：张道松
	* 创建时间：2016-07-05    
	*/
	private List<Long> getPermissionIds(Long roleId){
		
		List<Long> retIds = new ArrayList<Long>();
		
		List<SysPermission> sysPerList = sysPermissionService.findListByRoleId(roleId);
		
		if(sysPerList==null || sysPerList.size()==0) return retIds;
		
		for(SysPermission sp : sysPerList){
			retIds.add(sp.getId());
		}
		
		for(SysPermission sp : sysPerList){
			if(retIds.contains(sp.getParentId())){
				retIds.remove(sp.getParentId());
			}
		}
		
		return retIds;
	}
	/* CM-2747 zhangds end */
}
