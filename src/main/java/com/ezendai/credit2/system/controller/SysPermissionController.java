package com.ezendai.credit2.system.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezendai.credit2.apply.vo.PageAttributeVO;
import com.ezendai.credit2.apply.vo.PageMenuVO;
import com.ezendai.credit2.framework.exception.BusinessException;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.constant.WebConstants;
import com.ezendai.credit2.system.model.SysPermission;
import com.ezendai.credit2.system.service.SysPermissionService;
import com.ezendai.credit2.system.vo.SysPermissionVO;

/**   
*    
* 项目名称：credit2-main   
* 类名称：SysPermissionController   
* 类描述：   
* 创建人：liboyan   
* 创建时间：2016年1月21日 下午4:55:16   
* 修改人：liboyan   
* 修改时间：2016年1月21日 下午4:55:16   
* 修改备注：   
* @version    
*    
*/
@Controller
@RequestMapping("/sysPermission")

public class SysPermissionController extends BaseController {
	
	private static final  Logger logger = Logger.getLogger(SysPermissionController.class);
	
	class TreeData {
		TreeData(Long id,String text) {
			this.id = id;
			this.text = text;
		}
		private Long id;
		private String text;
		private List<TreeData> children = new ArrayList<TreeData>();
		
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
		public List<TreeData> getChildren() {
			return children;
		}
		public void setChildren(List<TreeData> children) {
			this.children = children;
		}
		
	}
	@Autowired
	private SysPermissionService sysPermissionService;
	
	@RequestMapping("/sysPermissionMain")
	public String index(HttpServletRequest request){
		
		return "system/sysPermissionList";
	}
	
	/* CM-2747 zhangds begin */
	@RequestMapping("/sysPermissionTree")
	@ResponseBody
	public List<TreeData>  sysPermissionTree() {
		SysPermissionVO sysPermissionVO = new SysPermissionVO();
		List<SysPermission> sysPermissionList = sysPermissionService.findListByVO(sysPermissionVO);
		logger.info("...获取所有权限菜单...");
		List<TreeData> treeList = new ArrayList<TreeData>();
		TreeData rootTreeData = new TreeData(0L,"权限菜单");
		
		for (SysPermission e : sysPermissionList) {
			if (NumberUtils.LONG_ZERO.compareTo(e.getParentId()) == 0) {
				TreeData td = new TreeData(e.getId(),e.getName());
				rootTreeData.getChildren().add(td);
			}
		}
		
		dealLevelRelation(rootTreeData.getChildren(), sysPermissionList);
		
		treeList.add(rootTreeData);
		
		return treeList;
	}
	
	/*   
	* 方法描述：菜单权限上下级关联
	* 创建人：张道松
	* 创建时间：2016-07-05    
	*/
	private void dealLevelRelation(List<TreeData> childList, List<SysPermission> allList){
		
		if(childList==null || childList.size()==0) return;
		
		for(TreeData cl : childList){
					
			for (SysPermission sp : allList) {
				
				if (cl.getId().compareTo(sp.getParentId()) == 0) {

					TreeData td2 = new TreeData(sp.getId(),sp.getName());
					cl.getChildren().add(td2);
				}
			}
			
			dealLevelRelation(cl.getChildren(),allList);
		}
		
	}
	/* CM-2747 zhangds end */
	
	@RequestMapping("/list")
	@ResponseBody
	 public Map<String, Object> list(SysPermissionVO sysPermissionVO, int rows, int page){
			Pager p = new Pager();
			p.setRows(rows);
			p.setPage(page);
			sysPermissionVO.setPager(p);
			p.setSidx("ID");
			p.setSort("ASC");
			Pager flowByVOList = sysPermissionService.findwithPG(sysPermissionVO);
			Map<String, Object> result = new LinkedHashMap<String, Object>();
			result.put("total", flowByVOList.getTotalCount());
			result.put("rows", flowByVOList.getResultList());
			return result;
	 }
	@RequestMapping("/loadSysPermission")
	@ResponseBody
	@SuppressWarnings(value={"rawtypes","unchecked"})
	public  Map  loadSysPermission(@RequestParam Long id){
		Map map = new HashMap();
		String msg = "";
		SysPermission sysPermission = sysPermissionService.get(id);
		boolean isSuccess = true;
	
		if (sysPermission != null) {
	
		} else {
			isSuccess = false;
			msg = "记录不存在";
		}
		map.put("isSuccess", isSuccess);
		map.put("sysPermission", sysPermission);
		map.put("msg", msg);
		return map;
	}
	/**
	 * 新增权限
	 * @param baseAreaVO
	 * @return
	 */
	@RequestMapping(value="/insertSysPermission",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	@SuppressWarnings(value={"rawtypes","unchecked"})
	public Map insertSysPermission(SysPermission sysPermission) {
		Map map = new HashMap();
		boolean isSuccess = true;
		String msg = "";
		try {
				sysPermissionService.insert(sysPermission);
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
	 * 修改权限
	 * @param baseAreaVO
	 * @return
	 */
	@RequestMapping(value="/update",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	@SuppressWarnings(value={"rawtypes","unchecked"})
	public Map update(SysPermissionVO sysPermissionVO) {
		Map map = new HashMap();
		boolean isSuccess = true;
		String msg = "";
		try {
				sysPermissionService.update(sysPermissionVO);
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
	 * 删除权限
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
			SysPermission sysPermission = sysPermissionService.get(id);
			if (sysPermission != null) {
				sysPermissionService.delete(id);
			} else {
				isSuccess = false;
				msg = "权限菜单不存在";
			}
		} catch(Exception ex) {
			isSuccess = false;
			msg = ex.getMessage();
		}
		map.put("isSuccess", isSuccess);
		map.put("msg", msg);
		return map;
	}

}
