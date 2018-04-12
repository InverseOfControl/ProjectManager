package com.ezendai.credit2.sign.lcb.api.controller;

import com.alibaba.fastjson.JSONObject;

/**
 * @author YM20051
 * @description: api 基类控制器
 * @date 2018/1/21 15:51
 */
public class BaseApiController {

    /**
     * 获取返回值
     * @param code 响应编码
     * @param msg  响应消息
     * @return
     */
    public JSONObject getResult(String code, String msg){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("repCode", code);
        jsonObject.put("repMsg", msg);
        return jsonObject;
    }

}
