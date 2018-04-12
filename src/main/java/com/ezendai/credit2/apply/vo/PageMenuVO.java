package com.ezendai.credit2.apply.vo;

import java.util.ArrayList;
import java.util.List;

public class PageMenuVO {
	
	private String text;
	
	private String state;
	
	private String iconCls;
	
	private PageAttributeVO attributes;
	
	private List<PageMenuVO> children = new ArrayList<PageMenuVO>();

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public PageAttributeVO getAttributes() {
		return attributes;
	}

	public void setAttributes(PageAttributeVO attributes) {
		this.attributes = attributes;
	}

	public List<PageMenuVO> getChildren() {
		return children;
	}
	
}
