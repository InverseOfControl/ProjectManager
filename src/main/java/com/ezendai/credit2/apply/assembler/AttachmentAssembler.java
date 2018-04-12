package com.ezendai.credit2.apply.assembler;

import com.ezendai.credit2.apply.model.Attachment;
import com.ezendai.credit2.apply.vo.AttachmentVO;

/**
 * 
 * <pre>
 * 附件VO与Model的转换
 * </pre>
 *
 * @author zhangshihai
 * @version $Id: AttachmentAssembler.java, v 0.1 2014年7月25日 下午3:52:09 zhangshihai Exp $
 */
public class AttachmentAssembler {

	/**
	 * 
	 * <pre>
	 * Model类型转为VO类型
	 * </pre>
	 *
	 * @param attachment
	 * @return
	 */
	public static AttachmentVO transferModel2VO (Attachment attachment) {
		if(attachment == null) {
			return null;
		}
		
		AttachmentVO attachmentVO = new AttachmentVO();
		attachmentVO.setId(attachment.getId());
		attachmentVO.setPersonId(attachment.getPersonId());
		attachmentVO.setGuaranteeId(attachment.getGuaranteeId());
		attachmentVO.setTitle(attachment.getTitle());
		attachmentVO.setCategory(attachment.getCategory());
		attachmentVO.setFileSize(attachment.getFileSize());
		attachmentVO.setSuffix(attachment.getSuffix());
		attachmentVO.setFilePath(attachment.getFilePath());
		attachmentVO.setUploadDate(attachment.getUploadDate());
		attachmentVO.setOperator(attachment.getOperator());
		attachmentVO.setRemark(attachment.getRemark());
		attachmentVO.setVersion(attachment.getVersion());
		attachmentVO.setCreatorId(attachment.getCreatorId());
		attachmentVO.setCreator(attachment.getCreator());
		attachmentVO.setCreatedTime(attachment.getCreatedTime());
		attachmentVO.setModifierId(attachment.getModifierId());
		attachmentVO.setModifier(attachment.getModifier());
		attachmentVO.setModifiedTime(attachment.getModifiedTime());
		
		return attachmentVO;
	}
	
	/**
	 * 
	 * <pre>
	 * VO类型转为Model类型
	 * </pre>
	 *
	 * @param attachmentVO
	 * @return
	 */
	public static Attachment transferVO2Model (AttachmentVO attachmentVO) {
		if (attachmentVO == null) {
			return null;
		}
		
		Attachment attachment = new Attachment();
		attachment.setId(attachmentVO.getId());
		attachment.setPersonId(attachmentVO.getPersonId());
		attachment.setGuaranteeId(attachmentVO.getGuaranteeId());
		attachment.setTitle(attachmentVO.getTitle());
		attachment.setCategory(attachmentVO.getCategory());
		attachment.setFileSize(attachmentVO.getFileSize());
		attachment.setSuffix(attachmentVO.getSuffix());
		attachment.setFilePath(attachment.getFilePath());
		attachment.setUploadDate(attachmentVO.getUploadDate());
		attachment.setOperator(attachmentVO.getOperator());
		attachment.setRemark(attachmentVO.getRemark());
		attachment.setVersion(attachmentVO.getVersion());
		attachment.setCreatorId(attachmentVO.getCreatorId());
		attachment.setCreator(attachmentVO.getCreator());
		attachment.setCreatedTime(attachmentVO.getCreatedTime());
		attachment.setModifierId(attachmentVO.getModifierId());
		attachment.setModifier(attachmentVO.getModifier());
		attachment.setModifiedTime(attachmentVO.getModifiedTime());
		
		return attachment;
	}
}
