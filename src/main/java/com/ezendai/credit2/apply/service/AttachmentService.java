package com.ezendai.credit2.apply.service;

import java.util.List;
import java.util.Map;

import com.ezendai.credit2.apply.model.Attachment;
import com.ezendai.credit2.apply.vo.AttachmentVO;
import com.ezendai.credit2.framework.util.Pager;

/**
 * @author zhuyiguo
 * @version $Id: AttachmentService.java, v 0.1 2014年6月26日 上午9:14:41 zhuyiguo Exp $
 */
public interface AttachmentService {
	Attachment insert(Attachment attachment);

	void deleteById(Long id);

	void deleteByIdList(AttachmentVO attachmentVO);

	Attachment get(Long id);

	List<Attachment> findListByVO(AttachmentVO attachmentVO);

	boolean exists(Map<String, Object> map);

	Pager findWithPg(AttachmentVO attachmentVO);

	Attachment get(AttachmentVO attachmentVO);

	boolean exists(Long id);

	int  update(AttachmentVO attachmentVO);

}
