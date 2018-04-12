package com.ezendai.credit2.apply.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezendai.credit2.apply.dao.AttachmentDao;
import com.ezendai.credit2.apply.model.Attachment;
import com.ezendai.credit2.apply.service.AttachmentService;
import com.ezendai.credit2.apply.vo.AttachmentVO;
import com.ezendai.credit2.framework.util.Pager;

@Service
public class AttachmentServiceImpl implements AttachmentService {

	@Autowired
	AttachmentDao attachmentDao;

	@Override
	public Attachment insert(Attachment attachment) {
		return attachmentDao.insert(attachment);
	}

	@Override
	public void deleteById(Long id) {
		attachmentDao.deleteById(id);
	}

	@Override
	public void deleteByIdList(AttachmentVO attachmentVO) {
		attachmentDao.deleteByIdList(attachmentVO);
	}

	@Override
	@Transactional
	public int update(AttachmentVO attachmentVO) {
		return attachmentDao.update(attachmentVO);
	}

	@Override
	public Attachment get(Long id) {
		return attachmentDao.get(id);
	}

	@Override
	public List<Attachment> findListByVO(AttachmentVO vo) {
		return attachmentDao.findListByVo(vo);
	}

	@Override
	public boolean exists(Map<String, Object> map) {
		return attachmentDao.exists(map);
	}

	@Override
	public Pager findWithPg(AttachmentVO attachmentVO) {
		return attachmentDao.findWithPg(attachmentVO);
	}

	@Override
	public Attachment get(AttachmentVO attachmentVO) {
		return attachmentDao.get(attachmentVO);
	}

	@Override
	public boolean exists(Long id) {
		return attachmentDao.exists(id);
	}

}
