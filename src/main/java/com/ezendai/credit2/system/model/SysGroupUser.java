package com.ezendai.credit2.system.model;

import com.ezendai.credit2.framework.model.BaseModel;

public class SysGroupUser extends BaseModel {
	private static final long serialVersionUID = 8248204248949537912L;

	/** 主键 */
	private Long id;
	/** 代码 */
	private Long userId;
	/** 名字 */
	private Long groupId;
	/** 版本 */
	private Long version;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}
	
}
