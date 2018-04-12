package com.ezendai.credit2.apply.assembler;

import com.ezendai.credit2.apply.model.AttachmentDetail;
import com.ezendai.credit2.apply.vo.AttachmentDetailVO;


/***
 * 
 * <pre>
 * 附件明细
 * </pre>
 *
 * @author HQ-AT6
 * @version $Id: AttachmentDetailAssembler.java, v 0.1 2014年9月4日 上午9:35:50 HQ-AT6 Exp $
 */
public class AttachmentDetailAssembler {

	/**
	 * 
	 * <pre>
	 * Model类型转为VO类型
	 * </pre>
	 *
	 * @param AttachmentDetail
	 * @return
	 */
	public static AttachmentDetailVO transferModel2VO (AttachmentDetail attachmentDetail) {
		if(attachmentDetail == null) {
			return null;
		}
		
		AttachmentDetailVO attachmentDetailVO = new AttachmentDetailVO();
			attachmentDetailVO.setId(attachmentDetail.getId());
			attachmentDetailVO.setAttachmentId(attachmentDetail.getAttachmentId());
			attachmentDetailVO.setCreatedTime(attachmentDetail.getCreatedTime());
			attachmentDetailVO.setCreator(attachmentDetail.getCreator());
			attachmentDetailVO.setCreatorId(attachmentDetail.getCreatorId());
			attachmentDetailVO.setFileSize(attachmentDetail.getFileSize());
			attachmentDetailVO.setModifiedTime(attachmentDetail.getModifiedTime());
			attachmentDetailVO.setModifier(attachmentDetail.getModifier());
			attachmentDetailVO.setModifierId(attachmentDetail.getModifierId());
			attachmentDetailVO.setName(attachmentDetail.getName());
			attachmentDetailVO.setOriginalName(attachmentDetail.getOriginalName());
			attachmentDetailVO.setRemark(attachmentDetail.getRemark());
			attachmentDetailVO.setSuffix(attachmentDetail.getSuffix());
			attachmentDetailVO.setVersion(attachmentDetail.getVersion());
		return attachmentDetailVO;
	}
	
	/**
	 * 
	 * <pre>
	 * VO类型转为Model类型
	 * </pre>
	 *
	 * @param AttachmentDetailVO
	 * @return
	 */
	public static AttachmentDetail transferVO2Model (AttachmentDetailVO attachmentDetailVO) {
		if (attachmentDetailVO == null) {
			return null;
		}
		
		AttachmentDetail attachmentDetail = new AttachmentDetail();
		attachmentDetail.setId(attachmentDetailVO.getId());
		attachmentDetail.setAttachmentId(attachmentDetailVO.getAttachmentId());
		attachmentDetail.setCreatedTime(attachmentDetailVO.getCreatedTime());
		attachmentDetail.setCreator(attachmentDetailVO.getCreator());
		attachmentDetail.setCreatorId(attachmentDetailVO.getCreatorId());
		attachmentDetail.setFileSize(attachmentDetailVO.getFileSize());
		attachmentDetail.setModifiedTime(attachmentDetailVO.getModifiedTime());
		attachmentDetail.setModifier(attachmentDetailVO.getModifier());
		attachmentDetail.setModifierId(attachmentDetailVO.getModifierId());
		attachmentDetail.setName(attachmentDetailVO.getName());
		attachmentDetail.setOriginalName(attachmentDetailVO.getOriginalName());
		attachmentDetail.setRemark(attachmentDetailVO.getRemark());
		attachmentDetail.setSuffix(attachmentDetailVO.getSuffix());
		attachmentDetail.setVersion(attachmentDetailVO.getVersion());
		
		return attachmentDetail;
	}
}
