/**
 * ZenDaiMoney.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.ezendai.credit2.apply.service;

import java.util.List;
import java.util.Map;

import com.ezendai.credit2.apply.model.AttachmentDetail;
import com.ezendai.credit2.apply.vo.AttachmentDetailVO;
import com.ezendai.credit2.framework.util.Pager;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author liyuepeng
 * @version $Id: AttachmentDetailService.java, v 0.1 2014-9-4 下午03:28:58 liyuepeng Exp $
 */
public interface AttachmentDetailService {

	AttachmentDetail insert(AttachmentDetail attachmentDetail);

	void deleteById(Long id);

	void deleteByIdList(AttachmentDetailVO attachmentDetailVO);

	AttachmentDetail get(Long id);

	List<AttachmentDetail> findListByVO(AttachmentDetailVO attachmentDetailVO);

	boolean exists(Map<String, Object> map);

	Pager findWithPg(AttachmentDetailVO attachmentDetailVO);

	AttachmentDetail get(AttachmentDetailVO attachmentDetailVO);

	boolean exists(Long id);

	int update(AttachmentDetailVO attachmentDetailVO);
}
