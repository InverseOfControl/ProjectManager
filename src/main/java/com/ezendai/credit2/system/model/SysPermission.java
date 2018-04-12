package com.ezendai.credit2.system.model;

import com.ezendai.credit2.framework.model.BaseModel;

public class SysPermission extends BaseModel {
	private static final long serialVersionUID = 3031017252788907584L;

	/**权限名称 */
	private String name;
	/**权限码 */
	private String code;
	/**层级 */
	private Integer levels;
	/**层级排序 */
	private Integer levelOrder;
	/**是否菜单 */
	private Integer type;
	/**上级权限ID */
	private Long parentId;
	/**权限对应的url */
	private String url;
	/**是否删除 */
	private Integer isDeleted;
	
	/**状态*/
	private Integer status;

	/** 父节点名称 */
	private SysPermission parent;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getLevels() {
		return levels;
	}

	public void setLevels(Integer levels) {
		this.levels = levels;
	}

	public Integer getLevelOrder() {
		return levelOrder;
	}

	public void setLevelOrder(Integer levelOrder) {
		this.levelOrder = levelOrder;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public SysPermission getParent() {
		return parent;
	}

	public void setParent(SysPermission parent) {
		this.parent = parent;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
