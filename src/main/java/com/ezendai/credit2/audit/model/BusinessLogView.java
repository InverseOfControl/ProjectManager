package com.ezendai.credit2.audit.model;

import com.ezendai.credit2.apply.model.BusinessLog;

public class BusinessLogView extends BusinessLog{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1292325213279532743L;
	private String flowStatusView;
	
	public String getFlowStatusView() {
		return flowStatusView;
	}

	public void setFlowStatusView(String flowStatusView) {
		this.flowStatusView = flowStatusView;
	}
}
