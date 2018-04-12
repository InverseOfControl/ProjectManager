package com.ezendai.credit2.system.vo;

import java.util.List;

import com.ezendai.credit2.framework.vo.BaseVO;

/**
 * 员工与权限组对照VO
 * @author Ivan
 *
 */
public class SysGroupUserVO extends BaseVO {

	private static final long serialVersionUID = -3564129658682780303L;
	
	/** 主键 */
	private Long id;
	/** 代码 */
	private Long userId;
	/** 名字 */
	private Long groupId;
	/** 版本 */
	private Long version;
	/** 批量移除集合 **/
	public List<Long> removeGroupIdList;
	/** 批量新增集合 **/
	public List<Long> addGroupIdList;
	
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
	public List<Long> getRemoveGroupIdList() {
		return removeGroupIdList;
	}
	public void setRemoveGroupIdList(List<Long> removeGroupIdList) {
		this.removeGroupIdList = removeGroupIdList;
	}
	public List<Long> getAddGroupIdList() {
		return addGroupIdList;
	}
	public void setAddGroupIdList(List<Long> addGroupIdList) {
		this.addGroupIdList = addGroupIdList;
	}
}
