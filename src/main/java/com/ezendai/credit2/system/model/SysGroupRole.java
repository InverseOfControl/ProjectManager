package com.ezendai.credit2.system.model;

import com.ezendai.credit2.framework.model.BaseModel;


/**   
*    
* 项目名称：credit2-main   
* 类名称：SysGroupRole   
* 类描述：   
* 创建人：liboyan   
* 创建时间：2016年1月26日 下午4:05:55   
* 修改人：liboyan   
* 修改时间：2016年1月26日 下午4:05:55   
* 修改备注：   
* @version    
*    
*/
/**   

*    
*/
public class SysGroupRole extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private Long groupId;

    private Long roleId;

    private Long version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}