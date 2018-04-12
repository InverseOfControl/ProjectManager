package com.ezendai.credit2.system.vo;

import com.ezendai.credit2.framework.vo.BaseVO;

/***
 * 
 * <pre>
 * </pre>
 *
 * @author HQ-AT6
 * @version $Id: SysLog.java, v 0.1 2014年7月22日 下午3:38:21 HQ-AT6 Exp $
 */
public class TextResourceVO extends BaseVO {
	
	/**  */
	private static final long serialVersionUID = 6243157281865043048L;
	
	private String text;
	private String type;
	/**
	 * 唯一标识符
	 */
	private String code;
	
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
 

	 
}