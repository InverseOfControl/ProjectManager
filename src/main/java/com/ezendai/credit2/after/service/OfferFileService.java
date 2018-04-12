/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.after.service;

import java.util.List;

import com.ezendai.credit2.after.model.OfferFile;
import com.ezendai.credit2.after.vo.OfferFileVO;

/**
 * <pre>
 * 报盘文件逻辑业务接口
 * </pre>
 *
 * @author zhangshihai
 * @version $Id: OfferFileService.java, v 0.1 2014年12月11日 下午5:23:10 00221921 Exp $
 */
public interface OfferFileService {
	
	OfferFile insert(OfferFile offerFile);

	/** 通过查询vo查找符合条件的Offer集合 */
	List<OfferFile> findListByVo(OfferFileVO offerFileVO);

}
