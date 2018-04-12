package com.ezendai.credit2.system.vo;

import com.ezendai.credit2.framework.vo.BaseVO;

/**
 * 权限组VO
 * @author Ivan
 *
 */
public class SysGroupVO extends BaseVO {

	private static final long serialVersionUID = -3564129658682780303L;
	
	/** 主键 */
	private Long id;
	/** 代码 */
	private String code;
	/** 名字 */
	private String name;
	/** 备注 */
	private String memo;;
	/** 状态 */
	private Long status;
	/** 是否逻辑删除 */
	private Long isDeleted;
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
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public Long getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Long isDeleted) {
		this.isDeleted = isDeleted;
	}
	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}
}
