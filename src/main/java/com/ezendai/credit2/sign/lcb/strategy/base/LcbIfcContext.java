package com.ezendai.credit2.sign.lcb.strategy.base;

import com.alibaba.fastjson.JSONObject;
import com.ezendai.credit2.sign.lcb.strategy.service.LcbIfcStrategy;

/**
 * 捞财宝接口调用上下文
 * @author YM10159
 */
public class LcbIfcContext {
	
	private LcbIfcStrategy lcbIfcStrategy;
	
	public LcbIfcContext(LcbIfcStrategy lcbIfcStrategy){
		this.lcbIfcStrategy = lcbIfcStrategy;
	}
	
	public JSONObject call(String flowNode, Object[] args){
		return this.lcbIfcStrategy.callLcb(flowNode,args);
	}
	
	public LcbIfcStrategy getLcbIfcStrategy() {
		return lcbIfcStrategy;
	}

	public void setLcbIfcStrategy(LcbIfcStrategy lcbIfcStrategy) {
		this.lcbIfcStrategy = lcbIfcStrategy;
	}
}
