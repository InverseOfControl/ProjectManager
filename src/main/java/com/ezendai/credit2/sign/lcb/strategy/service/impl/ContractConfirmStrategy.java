package com.ezendai.credit2.sign.lcb.strategy.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ezendai.credit2.audit.vo.GenerateContractVO;
import com.ezendai.credit2.sign.lcb.strategy.service.LcbIfcStrategy;
import com.ezendai.credit2.sign.util.SignFactory;

import java.util.HashMap;
import java.util.Map;

public class ContractConfirmStrategy extends LcbIfcStrategy {
    @Override
    public JSONObject callLcb(String flowNode, Object[] args) {

        Map<String,Object> reqMap = new HashMap<>();
        Map<String,Object> resMap = new HashMap<>();
        reqMap.put("loanId", args[0]);

        JSONObject obj = new JSONObject();


        SignFactory.getSignHandler(flowNode).execute(reqMap, resMap);
        return obj.parseObject(JSON.toJSONString(resMap));
    }
}
