/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.after.assembler;

import com.ezendai.credit2.after.model.OfferFile;
import com.ezendai.credit2.after.vo.OfferFileVO;

/**
 * <pre>
 * 报盘文件Model与VO转换
 * </pre>
 *
 * @author zhangshihai
 * @version $Id: OfferFileAssembler.java, v 0.1 2014年12月11日 上午11:43:03 00221921 Exp $
 */
public class OfferFileAssembler {

	public static OfferFileVO transferModel2VO(OfferFile offerFile) {
		if(offerFile == null) {
			return null;
		}
		
		OfferFileVO offerFileVO = new OfferFileVO();
		offerFileVO.setId(offerFile.getId());
		offerFileVO.setOriginalName(offerFile.getOriginalName());
		offerFileVO.setName(offerFile.getName());
		offerFileVO.setCreatedDate(offerFile.getCreatedDate());
		offerFileVO.setType(offerFile.getType());
		offerFileVO.setRemark(offerFile.getRemark());
		offerFileVO.setVersion(offerFile.getVersion());
		offerFileVO.setCreatorId(offerFile.getCreatorId());
		offerFileVO.setCreator(offerFile.getCreator());
		offerFileVO.setCreatedTime(offerFile.getCreatedTime());
		offerFileVO.setModifierId(offerFile.getModifierId());
		offerFileVO.setModifier(offerFile.getModifier());
		offerFileVO.setModifiedTime(offerFile.getModifiedTime());
		
		return offerFileVO;
	}
	
	public static OfferFile transferVO2Model(OfferFileVO offerFileVO) {
		if(offerFileVO == null) {
			return null;
		}
		
		OfferFile offerFile = new OfferFile();
		offerFile.setId(offerFileVO.getId());
		offerFile.setOriginalName(offerFileVO.getOriginalName());
		offerFile.setName(offerFileVO.getName());
		offerFile.setCreatedDate(offerFileVO.getCreatedDate());
		offerFile.setType(offerFileVO.getType());
		offerFile.setRemark(offerFileVO.getRemark());
		offerFile.setVersion(offerFileVO.getVersion());
		offerFile.setCreatorId(offerFileVO.getCreatorId());
		offerFile.setCreator(offerFileVO.getCreator());
		offerFile.setCreatedTime(offerFileVO.getCreatedTime());
		offerFile.setModifierId(offerFileVO.getModifierId());
		offerFile.setModifier(offerFileVO.getModifier());
		offerFile.setModifiedTime(offerFileVO.getModifiedTime());
		
		return offerFile;
	}
}
