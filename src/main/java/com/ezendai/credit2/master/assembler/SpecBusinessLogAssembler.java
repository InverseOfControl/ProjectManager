/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.master.assembler;

import com.ezendai.credit2.master.model.SpecBusinessLog;
import com.ezendai.credit2.master.vo.SpecBusinessLogVO;

/**
 * <pre>
 * 特定业务日志Model与VO转换
 * </pre>
 *
 * @author zhangshihai
 * @version $Id: SpecBusinessLogAssembler.java, v 0.1 2014年12月11日 下午1:40:03 00221921 Exp $
 */
public class SpecBusinessLogAssembler {
	public static SpecBusinessLogVO transferModel2VO(SpecBusinessLog specBusinessLog) {
		if(specBusinessLog == null) {
			return null;
		}
		
		SpecBusinessLogVO specBusinessLogVO = new SpecBusinessLogVO();
		specBusinessLogVO.setId(specBusinessLog.getId());
		specBusinessLogVO.setKeyId(specBusinessLog.getKeyId());
		specBusinessLogVO.setKeyType(specBusinessLog.getKeyType());
		specBusinessLogVO.setMessage(specBusinessLog.getMessage());
		specBusinessLogVO.setFlowStatus(specBusinessLog.getFlowStatus());
		specBusinessLogVO.setVersion(specBusinessLog.getVersion());
		specBusinessLogVO.setCreatorId(specBusinessLog.getCreatorId());
		specBusinessLogVO.setCreator(specBusinessLog.getCreator());
		specBusinessLogVO.setCreatedTime(specBusinessLog.getCreatedTime());
		specBusinessLogVO.setModifierId(specBusinessLog.getModifierId());
		specBusinessLogVO.setModifier(specBusinessLog.getModifier());
		specBusinessLogVO.setModifiedTime(specBusinessLog.getModifiedTime());
		
		return specBusinessLogVO;
	}
	
	public static SpecBusinessLog transferVO2Model(SpecBusinessLogVO specBusinessLogVO) {
		if(specBusinessLogVO == null) {
			return null;
		}
		
		SpecBusinessLog specBusinessLog = new SpecBusinessLog();
		specBusinessLog.setId(specBusinessLogVO.getId());
		specBusinessLog.setKeyId(specBusinessLogVO.getKeyId());
		specBusinessLog.setKeyType(specBusinessLogVO.getKeyType());
		specBusinessLog.setMessage(specBusinessLogVO.getMessage());
		specBusinessLog.setFlowStatus(specBusinessLogVO.getFlowStatus());
		specBusinessLog.setVersion(specBusinessLogVO.getVersion());
		specBusinessLog.setCreatorId(specBusinessLogVO.getCreatorId());
		specBusinessLog.setCreator(specBusinessLogVO.getCreator());
		specBusinessLog.setCreatedTime(specBusinessLogVO.getCreatedTime());
		specBusinessLog.setModifierId(specBusinessLogVO.getModifierId());
		specBusinessLog.setModifier(specBusinessLogVO.getModifier());
		specBusinessLog.setModifiedTime(specBusinessLogVO.getModifiedTime());
		
		return specBusinessLog;
	}
}
