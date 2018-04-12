package com.ezendai.credit2.system.vo;

import java.util.Date;

import com.ezendai.credit2.framework.vo.BaseVO;
import com.ezendai.credit2.system.model.SysUser;

/***
 * 
 * <pre>
 * 系统日志
 * </pre>
 *
 * @author HQ-AT6
 * @version $Id: SysLog.java, v 0.1 2014年7月22日 下午3:38:21 HQ-AT6 Exp $
 */
public class SysLogVO  extends BaseVO {
    /**  */
	private static final long serialVersionUID = 1867468992185077551L;

    private Integer optType;
    
    private Integer optModule;
    /** 操作类型  */
    private String optionType;

    private String ipAddress;

    private String remark;
    /** 开始时间 */
    private Date startTime;
    /** 结束时间 */
    private Date endTime;
   
    /** 用户 **/
    private SysUser sysUser;

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

	public String getOptionType() {
		return optionType;
	}

	public void setOptionType(String optionType) {
		this.optionType = optionType;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public SysUser getSysUser() {
		return sysUser;
	}

	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}

	public Integer getOptModule() {
		return optModule;
	}

	public void setOptModule(Integer optModule) {
		this.optModule = optModule;
	}
    
    
}