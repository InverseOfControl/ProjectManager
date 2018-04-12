/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.system.assembler;

import com.ezendai.credit2.system.model.SysParameter;
import com.ezendai.credit2.system.vo.SysParameterVO;


/**
 * <pre>
 * 
 * </pre>
 *
 * @author liyuepeng
 * @version $Id: SysParameterAssembler.java, v 0.1 2014-8-19 下午02:42:38 liyuepeng Exp $
 */
public class SysParameterAssembler {

	public static void setModelProperty(SysParameterVO vo, SysParameter sysParameter) {
		if (vo != null) {
			sysParameter.setId(vo.getId());
			sysParameter.setName(vo.getName());
			sysParameter.setCode(vo.getCode());
			sysParameter.setParameterValue(vo.getParameterValue());
			sysParameter.setInputType(vo.getInputType());
			sysParameter.setRemark(vo.getRemark());
		}
	}
}
