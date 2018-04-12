package com.ezendai.credit2.system.model;

import com.ezendai.credit2.framework.model.BaseModel;


/**   
*    
* 项目名称：credit2-main   
* 类名称：SysRole   
* 类描述：   
* 创建人：liboyan   
* 创建时间：2016年1月26日 下午4:37:28   
* 修改人：liboyan   
* 修改时间：2016年1月26日 下午4:37:28   
* 修改备注：   
* @version    
*    
*/
public class SysRole extends BaseModel{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private String code;

    private String name;

    private String memo;

    private Short status;
    
    private Short isDeleted;

    private Long[] sysPermissionList;
    
    

	public Long[] getSysPermissionList() {
		return sysPermissionList;
	}

	public void setSysPermissionList(Long[] sysPermissionList) {
		this.sysPermissionList = sysPermissionList;
	}


	public Short getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Short isDeleted) {
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