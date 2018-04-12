package com.ezendai.credit2.sign.lcb.strategy.service.impl;


import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ezendai.credit2.audit.vo.GenerateContractVO;
import com.ezendai.credit2.sign.lcb.strategy.service.LcbIfcStrategy;
import com.ezendai.credit2.sign.util.SignFactory;
/**
 * 调用众安反欺诈（风险）
 * @author YM10180
 *
 */
public class ZonganRiskStrategy extends LcbIfcStrategy{
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public JSONObject callLcb(String flowNode, Object[] args) {
		//GenerateContractVO generateContractVO = (GenerateContractVO) args[0];
		Map<String,Object> reqMap = new HashMap<>();
		Map<String,Object> resMap = new HashMap<>();
		JSONObject obj = new JSONObject();
		Object[] str= (Object[]) args[0];
		reqMap.put("id", str[0]);
		reqMap.put("personName", str[1]);
		reqMap.put("mobilePhoneLoan", str[2]);
		reqMap.put("personIdnum", str[3]);
		
		
		//真正的业务逻辑代码，调用捞财宝接口
		SignFactory.getSignHandler(flowNode).execute(reqMap, resMap);;
		return obj.parseObject(JSON.toJSONString(resMap)); 
	}

}
