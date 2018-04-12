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
 * @description: 财务审核 和 财务放款 终止借款
 * @date 2018/1/16 17:17
 */
public class TerminateBorrowStrategy extends LcbIfcStrategy {

    @Override
    public JSONObject callLcb(String flowNode, Object[] args) {
        Map<String,Object> reqMap = new HashMap<>();
        Map<String,Object> resMap = new HashMap<>();
        ApproveResult approveResult = (ApproveResult) args[0];

        reqMap.put("loanId", approveResult.getLoanId());
        reqMap.put("reason", approveResult.getReason1()+"["+approveResult.getReason2()+"]");


        SignFactory.getSignHandler(flowNode).execute(reqMap, resMap);
        return JSONObject.parseObject(JSON.toJSONString(resMap));
    }
}
