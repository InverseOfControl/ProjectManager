package com.ezendai.credit2.sign.lcb.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.service.LoanService;
import com.ezendai.credit2.common.util.HttpUtils;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.sign.constant.LcbConfig;
import com.ezendai.credit2.sign.util.BaseSign;
import com.ezendai.credit2.sign.util.SignHandler;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;

/**
 * @author YM20051
 * @description: TODO
 * @date 2018/1/16 17:18
 */
@SignHandler(flowNode="lcbTerminateBorrow")
public class TerminateBorrowService<T, U> extends BaseSign<T, U> {

    @Autowired
    private LcbConfig lcbConfig;
    @Autowired
    private LoanService loanService;

    @Override
    public boolean execute(T reqObj, U resObj) {
        Map<String,Object> reqMap = (Map<String, Object>) reqObj;

        Map<String,Object> resMap = (Map<String, Object>) resObj;
        Long loanId = (Long) reqMap.get("loanId");
        // 获取捞财宝借款编号
        Loan loan = loanService.get(loanId);

        //真正的业务逻辑代码，调用捞财宝接口
        Map<String, String> para = new HashMap<>();
        para.put("borrowNo", loan.getLcbBorrowNo());
        para.put("reason", (String) reqMap.get("reason"));

        String result = HttpUtils.postGateway(lcbConfig.getLcbStopLoan(), para, lcbConfig.getLcbSignSecretKey());
        JSONObject jsonObject = JSON.parseObject(result);

        resMap.putAll(jsonObject);
        if (!resMap.get("repCode").equals("000000")) {
            resMap.put("repMsg", "借款无法取消，请联系爱特确认是否已放款["+resMap.get("repMsg")+"]!");
            return false;
        }

        return true;
    }
}
