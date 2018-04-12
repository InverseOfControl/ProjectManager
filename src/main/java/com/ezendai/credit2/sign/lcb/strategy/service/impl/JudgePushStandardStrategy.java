package com.ezendai.credit2.sign.lcb.strategy.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ezendai.credit2.sign.lcb.strategy.service.LcbIfcStrategy;
import com.ezendai.credit2.sign.util.SignFactory;
/**
 * 判断是否已经推标
 * @author YM10180
 *
 */
public class JudgePushStandardStrategy extends LcbIfcStrategy{

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public JSONObject callLcb(String flowNode, Object[] args) {
		
		Map<String,Object> reqMap = new HashMap<>();
		Map<String,Object> resMap = new HashMap<>();
		JSONObject obj = new JSONObject();
		Long longId=(Long) args[0];
		String LoanCancelSelect=(String) args[1];
		reqMap.put("loanId", longId);
		reqMap.put("LoanCancelSelect", LoanCancelSelect);
		
		//真正的业务逻辑代码，调用捞财宝接口
		SignFactory.getSignHandler(flowNode).execute(reqMap, resMap);;
		return obj.parseObject(JSON.toJSONString(resMap)); 
	}

	
	
	
	public static void main(String[] args) {
		JSONObject obj = new JSONObject();
		Map<String,Object> resMap = new HashMap<>();
		resMap.put("repCode", "000000");
		JSONObject jsonO=obj.parseObject(JSON.toJSONString(resMap));
		System.out.println(obj.parseObject(JSON.toJSONString(resMap)));
		System.out.println(jsonO.getString("repCode"));
	}
}
