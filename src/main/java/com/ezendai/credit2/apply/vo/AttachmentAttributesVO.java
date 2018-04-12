package com.ezendai.credit2.apply.vo;

/**
 * <pre>
 * 附件树属性
 * </pre>
 *
 * @author chenqi
 * @version $Id: AttachmentAttributesVO.java, v 0.1 2014年9月9日 上午10:31:53 chenqi Exp $
 */
public class AttachmentAttributesVO implements java.io.Serializable {

	private static final long serialVersionUID = -6368037298477935080L;
	private String src;
	private String attachmentDetailId;
	
	
	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}


	public String getAttachmentDetailId() {
		return attachmentDetailId;
	}

	public void setAttachmentDetailId(String attachmentDetailId) {
		this.attachmentDetailId = attachmentDetailId;
	}

	@Override
	public String toString() {
		return "AttachmentAttributesVO [src=" + src + ", attachmentDetailId=" + attachmentDetailId + "]";
	}

}
