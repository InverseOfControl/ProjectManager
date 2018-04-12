package com.ezendai.credit2.system.model;

import com.ezendai.credit2.framework.model.BaseModel;
import com.ezendai.credit2.master.model.SysEnumerate;

/***
 * 
 * <pre>
 * 系统日志
 * </pre>
 *
 * @author HQ-AT6
 * @version $Id: SysLog.java, v 0.1 2014年7月22日 下午3:38:21 HQ-AT6 Exp $
 */
public class SysLog extends BaseModel{
    /**  */
	private static final long serialVersionUID = -6711714800775069828L;

	public SysLog( Integer optModule,Integer optType, String remark) {
		super();
		this.optType = optType;
		this.optModule = optModule;
		this.remark = remark;
	}

	public SysLog() {
		super();
	}

	private Integer optType;
    
    private Integer optModule;
 
    private String ipAddress;

    private String remark;
    /** 操作类型枚举值 */
    private SysEnumerate optTypeEnumerate;
    /** 操作模块枚举值 */
    private SysEnumerate optModuleEnumerate;

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress == null ? null : ipAddress.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
  
	public Integer getOptType() {
		return optType;
	}

	public void setOptType(Integer optType) {
		this.optType = optType;
	}
	public Integer getOptModule() {
		return optModule;
	}

	public void setOptModule(Integer optModule) {
		this.optModule = optModule;
	}

	public SysEnumerate getOptTypeEnumerate() {
		return optTypeEnumerate;
	}

	public void setOptTypeEnumerate(SysEnumerate optTypeEnumerate) {
		this.optTypeEnumerate = optTypeEnumerate;
	}

	public SysEnumerate getOptModuleEnumerate() {
		return optModuleEnumerate;
	}

	public void setOptModuleEnumerate(SysEnumerate optModuleEnumerate) {
		this.optModuleEnumerate = optModuleEnumerate;
	}
    
}