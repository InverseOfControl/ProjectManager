package com.ezendai.credit2.system.vo;

import java.util.List;

import com.ezendai.credit2.framework.vo.BaseVO;


public class SysRoleVO extends BaseVO{
    /**
	 * 
	 */
	private static final long serialVersionUID = 3946768854638758652L;

	/**
	 * 
	 */

	private Long id;

    private String code;

    private String name;

    private String memo;

    private Short status;
    
    private Integer isDeleted;

    private Long[] sysPermissionList;
    
    private List<Long> removePermissionList;



	public List<Long> getRemovePermissionList() {
		return removePermissionList;
	}

	public void setRemovePermissionList(List<Long> removePermissionList) {
		this.removePermissionList = removePermissionList;
	}

	public Long[] getSysPermissionList() {
		return sysPermissionList;
	}

	public void setSysPermissionList(Long[] sysPermissionList) {
		this.sysPermissionList = sysPermissionList;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

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
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

   
}