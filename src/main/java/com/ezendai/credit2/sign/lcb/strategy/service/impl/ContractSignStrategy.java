package com.ezendai.credit2.sign.lcb.strategy.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ezendai.credit2.audit.vo.GenerateContractVO;
import com.ezendai.credit2.sign.lcb.strategy.service.LcbIfcStrategy;
import com.ezendai.credit2.sign.util.SignFactory;

/**
 * 合同签约策略类
 * @author YM10159
 */
public class ContractSignStrategy extends LcbIfcStrategy {
    @SuppressWarnings({ "unchecked", "static-access" })
	@Override
    public JSONObject callLcb(String flowNode, Object[] args) {
    	JSONObject obj = new JSONObject();
		GenerateContractVO generateContractVO = (GenerateContractVO) args[0];
		Map<String,Object> reqMap = new HashMap<>();
		Map<String,Object> resMap = new HashMap<>();
		resMap.put("repCode", "000000");
		
		reqMap.put("loanId", generateContractVO.getLoanId());
        
        SignFactory.getSignHandler(flowNode).execute(reqMap, resMap);
        return obj.parseObject(JSON.toJSONString(resMap));
    }
}
