package com.ezendai.credit2.sign.lcb.strategy.service;

import com.alibaba.fastjson.JSONObject;

/**
 * 捞财宝接口调用策略基类
 * @author YM10159
 */
public abstract class LcbIfcStrategy {
	/**
	 * description:调用捞财宝接口
	 * autor:ym10159
	 * date:2017年12月29日 下午4:44:05
	 * @param flowNode 用捞财宝接口的业务节点
	 * @param args 原业务方法请求参数
	 * @return 捞财宝接口调用成功或失败
	 */
	public abstract JSONObject callLcb(String flowNode, Object[] args);
}
