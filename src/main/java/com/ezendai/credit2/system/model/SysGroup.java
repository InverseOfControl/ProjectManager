package com.ezendai.credit2.system.model;

import com.ezendai.credit2.framework.model.BaseModel;

public class SysGroup extends BaseModel {
	private static final long serialVersionUID = 8248204248949537912L;

	/** 主键 */
	private Long id;
	/** 代码 */
	private String code;
	/** 名字 */
	private String name;
	/** 备注 */
	private String memo;;
	/** 状态 */
	private short status;
	/** 是否逻辑删除 */
	private short isDeleted;
	/** 版本 */
	private Long version;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}

	public short getStatus() {
		return status;
	}
	public void setStatus(short status) {
		this.status = status;
	}

	public short getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(short isDeleted) {
		this.isDeleted = isDeleted;
	}
	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}
	
}
