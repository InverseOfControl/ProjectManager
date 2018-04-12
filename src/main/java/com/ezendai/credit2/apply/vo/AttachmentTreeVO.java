package com.ezendai.credit2.apply.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 附件树
 * </pre>
 *
 * @author chenqi
 * @version $Id: AttachmentTreeVO.java, v 0.1 2014年9月9日 上午10:31:23 chenqi Exp $
 */
public class AttachmentTreeVO {

	private Long id;
	private String text;

	private List<AttachmentTreeVO> children = new ArrayList<AttachmentTreeVO>();
	private AttachmentAttributesVO attributes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<AttachmentTreeVO> getChildren() {
		return children;
	}

	public void setChildren(List<AttachmentTreeVO> children) {
		this.children = children;
	}

	public AttachmentAttributesVO getAttributes() {
		return attributes;
	}

	public void setAttributes(AttachmentAttributesVO attributes) {
		this.attributes = attributes;
	}

	@Override
	public String toString() {
		return "AttachmentTreeVO [id=" + id + ", text=" + text + ", children=" + children + ", attributes=" + attributes + "]";
	}

}
