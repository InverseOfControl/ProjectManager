package com.ezendai.credit2.sign.lcb.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.model.Product;
import com.ezendai.credit2.apply.service.LoanService;
import com.ezendai.credit2.apply.service.ProductService;
import com.ezendai.credit2.audit.model.RepaymentPlan;
import com.ezendai.credit2.audit.service.RepaymentPlanService;
import com.ezendai.credit2.common.util.HttpUtils;
import com.ezendai.credit2.sign.constant.LcbConfig;
import com.ezendai.credit2.sign.util.BaseSign;
import com.ezendai.credit2.sign.util.SignHandler;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SignHandler(flowNode="lcbContractConfirm")
public class ContractConfirmService<T, U> extends BaseSign<T, U> {

    @Autowired
    private LcbConfig lcbConfig;
    @Autowired
    private RepaymentPlanService repaymentPlanService;
    @Autowired
    private LoanService loanService;
    @Autowired
    private ProductService productService;

    @Override
    public boolean execute(T reqObj, U resObj) {

        Map<String,Object> reqMap = (Map<String, Object>) reqObj;
        Map<String,Object> resMap = (Map<String, Object>) resObj;
        Long loanId = (Long) reqMap.get("loanId");

        // 获取还款计划
        JSONArray plans = new JSONArray();
        List<RepaymentPlan> repaymentPlans = repaymentPlanService.queryRepaymentPlans(loanId);
        for (RepaymentPlan repaymentPlan : repaymentPlans) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("term", repaymentPlan.getCurNum());
            jsonObject.put("interest", repaymentPlan.getInterestAmt());
            jsonObject.put("principal", repaymentPlan.getPrincipalAmt());
            jsonObject.put("repayDay", DateFormatUtils.format(repaymentPlan.getRepayDay(), "yyyy-MM-dd"));
            plans.add(jsonObject);
        }

        // 获取捞财宝借款编号
        Loan loan = loanService.get(loanId);

        Product product = productService.get(loan.getProductId());

        //真正的业务逻辑代码，调用捞财宝接口
        Map<String, String> para = new HashMap<>();
        para.put("borrowNo", loan.getLcbBorrowNo());
        para.put("borrowAmount", loan.getPactMoney().toString()); //借款／合同金额
        para.put("remitMoney", loan.getGrantMoney().toString());//划拨金额
        para.put("borrowTerm", loan.getTime().toString());//借款期限
        para.put("yearRate", product.getYearRate().toString());//年化利率
        para.put("firstRepayDate", DateFormatUtils.format(loan.getStartRepayDate(), "yyyy-MM-dd"));//首期还款日
        para.put("plans", plans.toJSONString()); //还款计划

        String result = HttpUtils.postGateway(lcbConfig.getLcbContractConfirmation(), para, lcbConfig.getLcbSignSecretKey());
        JSONObject jsonObject = JSON.parseObject(result);

        resMap.putAll(jsonObject);
        if (!resMap.get("repCode").equals("000000")) {
            return false;
        }

        return true;
    }
}
