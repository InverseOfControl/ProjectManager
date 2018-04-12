package com.ezendai.credit2.apply.controller;

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
import com.ezendai.credit2.apply.vo.ProductVO;
import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.framework.model.UserSession;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.system.model.SysGroup;

/**
 * 
 * @author Ivan
 *
 */
@Controller
@RequestMapping("/product")
public class ProductController {
	
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
	private ProductService productService;
	
	/**
	 * 查询产品数据
	 * @author Ivan
	 * @return 树形文本
	 */
	@RequestMapping("/getProductJson")
	@ResponseBody
	public List<TreeData> getProductJson() {
		ProductVO productVO = new ProductVO();
		List<Product> list = productService.findListByVO(productVO);
		List<Product> lists =new ArrayList<Product>();
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
			for (Product l: list) {
				if(falg == 2){//车贷产品
					if(l.getProductType()  == 2){
						lists.add(l);
					}
					
				}else if(falg == 1 ){//小企业贷产品
					if(l.getProductType() == 1){
						lists.add(l);
					}
				}
			}
		}else{
			lists.addAll(list);
		}

		
		List<TreeData> treeList = new ArrayList<TreeData>();
		TreeData rootTreeData = new TreeData(0L,"产品组");
		treeList.add(rootTreeData);
		for (int i=0;i<lists.size();i++) {
			Product product = lists.get(i);
			rootTreeData.getChildren().add(new TreeData(product.getId(),product.getProductName()));
		}
		return treeList;
	}
}