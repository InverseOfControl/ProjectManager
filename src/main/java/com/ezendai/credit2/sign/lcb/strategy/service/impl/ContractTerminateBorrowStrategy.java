package com.ezendai.credit2.sign.lcb.strategy.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ezendai.credit2.audit.model.ApproveResult;
import com.ezendai.credit2.sign.lcb.strategy.service.LcbIfcStrategy;
import com.ezendai.credit2.sign.util.SignFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author YM20051
 * @description: 合同确认 终止借款
 * @date 2018/1/16 17:17
 */
public class ContractTerminateBorrowStrategy extends LcbIfcStrategy {

    @Override
    public JSONObject callLcb(String flowNode, Object[] args) {
        Map<String,Object> reqMap = new HashMap<>();
        Map<String,Object> resMap = new HashMap<>();

        reqMap.put("loanId", args[0]);
        reqMap.put("reason", args[1]);
        

        SignFactory.getSignHandler(flowNode).execute(reqMap, resMap);
        return JSONObject.parseObject(JSON.toJSONString(resMap));
    }
}
