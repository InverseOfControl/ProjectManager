package com.ezendai.credit2.system.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezendai.credit2.apply.model.Product;
import com.ezendai.credit2.apply.service.ProductService;
import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.framework.model.UserSession;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.system.model.SysGroup;
import com.ezendai.credit2.system.service.SysGroupService;
import com.ezendai.credit2.system.vo.SysGroupVO;

/**
 * 
 * @author Ivan
 *
 */
@Controller
@RequestMapping("/sysGroup")
public class SysGroupController {
	
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
	
	/**
	 * 利用Spring自动注入
	 */
	@Autowired
	private SysGroupService sysGroupService;
	@Autowired
	private ProductService productService;
	
	/**
	 * 查询权限组数据
	 * @author Ivan
	 * @return
	 */
	@RequestMapping("/getGroupJson")
	@ResponseBody
	public List<TreeData>  getGroupJson() {
		SysGroupVO sysGroupVO = new SysGroupVO();
		List<SysGroup> list = sysGroupService.findListByVO(sysGroupVO);
		List<SysGroup> lists = new ArrayList<SysGroup>();
		List<TreeData> treeList = new ArrayList<TreeData>();
		UserSession user = ApplicationContext.getUser();
		if(!user.getUserType().equals(EnumConstants.UserType.SYSTEM_ADMIN.getValue())){
			// 车贷或小企贷
			int falg =0; 
			List<Product> products = productService.findProductTypeByUserId(user.getId());
			for (Product product : products) {

				if(product.getProductType() == 1){
					falg =1;
				}else{
					falg =2;
				}
			}
			for (SysGroup l: list) {
				if(falg == 2){//车贷门店权限
					if(l.getId()  == 57 || l.getId() == 21 || l.getId() == 11){
						lists.add(l);
					}
					
				}else if(falg == 1 ){//小企业贷门店权限
					if(l.getId()  == 42 || l.getId() == 41 || l.getId() == 40){
						lists.add(l);
					}
				}
			}
		}else{
			lists.addAll(list);
		}
		TreeData rootTreeData = new TreeData(0L,"权限组");
		treeList.add(rootTreeData);
		for (int i=0;i<lists.size();i++) {
			SysGroup sysGroup = lists.get(i);
			rootTreeData.getChildren().add(new TreeData(sysGroup.getId(),sysGroup.getName()));
		}
		return treeList;
	}
}