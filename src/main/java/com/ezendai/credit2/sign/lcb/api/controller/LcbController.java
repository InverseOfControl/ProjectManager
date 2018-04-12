package com.ezendai.credit2.sign.lcb.api.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ezendai.credit2.apply.model.Extension;
import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.service.ExtensionService;
import com.ezendai.credit2.apply.service.LoanExtensionService;
import com.ezendai.credit2.apply.service.LoanService;
import com.ezendai.credit2.apply.vo.LoanVO;
import com.ezendai.credit2.audit.model.ApproveResult;
import com.ezendai.credit2.audit.model.RepaymentPlan;
import com.ezendai.credit2.audit.service.RepaymentPlanService;
import com.ezendai.credit2.finance.service.FinanceService;
import com.ezendai.credit2.finance.service.FinancialAuditService;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 对接捞财宝api
 * @author YM20051
 * @date 2018/1/16 9:44
 */
@Controller
public class LcbController extends BaseApiController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LoanService loanService;
    @Autowired
    private FinancialAuditService financialAuditService;
    @Autowired
    private FinanceService financeService;
    @Autowired
    private RepaymentPlanService repaymentPlanService;
    @Autowired
    private ExtensionService extensionService;
    /**
     * 流标通知
     * @param borrowNo 借款编号
     * @return
     */
    @RequestMapping("/api/lcb/bidFailureNotice")
    @ResponseBody
    public JSONObject bidFailureNotice(String borrowNo) {
        logger.info("流标通知:借款编号[{}]", borrowNo);
        if(StringUtils.isEmpty(borrowNo)){
            return this.getResult("000001", "借款编号为空！");
        }

        JSONObject jsonObject = this.getResult("000000", "处理成功!");
        LoanVO param = new LoanVO();
        param.setLcbBorrowNo(borrowNo);
        Loan loan = loanService.get(param);

        try {
            if (loan != null) {
                ApproveResult approveResult = new ApproveResult();
                approveResult.setLoanId(loan.getLoanId());
                approveResult.setReason1("捞财宝已流标");
                approveResult.setReason2("");
                approveResult.setReason("捞财宝已流标");
                if(loan.getStatus().intValue() == EnumConstants.LoanStatus.财务审核.getValue()){
                    // 当前单据处于财务审核状态
                    financialAuditService.financeReturn(approveResult, EnumConstants.LoanBorrowStatus.流标.getValue());

                }else if(loan.getStatus().intValue() == EnumConstants.LoanStatus.财务放款.getValue()){
                    // 当前单据处于财务审核状态
                    financeService.financeReturn(approveResult, EnumConstants.LoanBorrowStatus.流标.getValue());
                }else {
                    // 其他状态
                    LoanVO loanVO = new LoanVO();
                    loanVO.setId(loan.getLoanId());
                    loanVO.setLcbBorrowStatus(EnumConstants.LoanBorrowStatus.流标.getValue());
                    loanService.update(loanVO);
                }

            }else{
                jsonObject = this.getResult("111110","单据不存在!" );
            }
        } catch (Exception e) {
            jsonObject = this.getResult("111111","流标失败!" );
            logger.error("流标:" + borrowNo + ",失败：" + e.getMessage(), e);
        }
        logger.info("流标通知:返回结果{}", JSON.toJSON(jsonObject));
        return jsonObject;
    }

    /**
     * 满标通知
     * @param borrowNo 借款编号
     * @return
     */
    @RequestMapping("/api/lcb/bidSuccessNotice")
    @ResponseBody
    public JSONObject bidSuccessNotice(String borrowNo) {
        logger.info("满标通知:借款编号[{}]", borrowNo);
        if(StringUtils.isEmpty(borrowNo)){
            return this.getResult("000001", "借款编号为空！");
        }
        JSONObject jsonObject = this.getResult("000000", "处理成功!");
        LoanVO param = new LoanVO();

        param.setLcbBorrowNo(borrowNo);
        Loan loan = loanService.get(param);

        try {
            if (loan != null) {
                if (loan.getStatus().intValue() != EnumConstants.LoanStatus.财务审核.getValue()) {
                    jsonObject = this.getResult("111109","当前借款状态不是财务审核!" );
                }else{
                    financialAuditService.financialAudit(loan.getLoanId());
                }

            }else{
                jsonObject = this.getResult("111110","单据不存在!" );
            }
        } catch (Exception e) {
            jsonObject = this.getResult("111111","财务审核失败!" );
            logger.error("满标:" + borrowNo + ",失败：" + e.getMessage(), e);
        }
        logger.info("满标通知:返回结果{}", JSON.toJSON(jsonObject));
        return jsonObject;
    }

    /**
     * 标的放款通知
     * @param borrowNo 借款编号
     * @return
     */
    @RequestMapping("/api/lcb/targetLoan")
    @ResponseBody
    public JSONObject targetLoan(String borrowNo) {
        logger.info("标的放款通知:借款编号[{}]", borrowNo);
        if(StringUtils.isEmpty(borrowNo)){
            return this.getResult("000001", "借款编号为空！");
        }
        JSONObject jsonObject = this.getResult("000000", "处理成功!");

        LoanVO param = new LoanVO();
        param.setLcbBorrowNo(borrowNo);
        Loan loan = loanService.get(param);
        try {
            if (loan != null) {
                if (loan.getStatus().intValue() != EnumConstants.LoanStatus.财务放款.getValue()) {
                    jsonObject = this.getResult("111109", "当前借款状态不是财务放款!");
                }else{
                    financeService.makeLoan(loan);
                }
            }else{
                jsonObject = this.getResult("111110", "单据不存在!");
            }
        } catch (Exception e) {

            jsonObject = this.getResult("111111","财务放款失败!" );
            logger.error("标的放款:" + borrowNo + ",失败：" + e.getMessage(), e);
        }
        logger.info("标的放款通知:返回结果{}", JSON.toJSON(jsonObject));
        return jsonObject;
    }

    @RequestMapping("/api/lcb/schedulePayments")
    @ResponseBody
    public JSONObject schedulePayments(String borrowNo){
        logger.info("获取还款计划:借款编号[{}]", borrowNo);
        if(StringUtils.isEmpty(borrowNo)){
            return this.getResult("000001", "借款编号为空！");
        }
        JSONObject jsonObject = this.getResult("000000", "处理成功!");
        LoanVO param = new LoanVO();
        param.setLcbBorrowNo(borrowNo);
        Loan loan = loanService.get(param);

        try {
            List<JSONObject> list = new ArrayList<>();
            // 根据借款ID 获取还款计划
            List<RepaymentPlan> repaymentPlans = repaymentPlanService.queryRepaymentPlans(loan.getLoanId());
            // 获取展期
            Extension extension = extensionService.getExtensionByLoanId(loan.getLoanId());
            if(extension != null){
                repaymentPlans.addAll(repaymentPlanService.queryRepaymentPlans(extension.getId()));
            }

            for (RepaymentPlan repaymentPlan : repaymentPlans) {
                JSONObject object = new JSONObject();
                // 当前期数
                object.put("currentTerm", repaymentPlan.getCurNum());
                // 当期利息
                object.put("currentAccrual", repaymentPlan.getInterestAmt());
                // 结清退费
                object.put("giveBackRate", repaymentPlan.getRefund());
                // 剩余本金
                object.put("principalBalance", repaymentPlan.getRemainingPrincipal());
                // 结清金额(包含服务费), 不包含服务费 (oneTimeRepaymentAmount - managePart0Fee - managePart1Fee)
                object.put("repaymentAll", repaymentPlan.getOneTimeRepaymentAmount().subtract(repaymentPlan.getManagePart0Fee()).subtract(repaymentPlan.getManagePart1Fee()));
                // 应还日期
                object.put("returnDate", repaymentPlan.getRepayDay()==null ? "": DateFormatUtils.format(repaymentPlan.getRepayDay(), "yyyy-MM-dd"));
                // 剩余欠款(包含服务费), 不包含服务费(curRemainingInterestAmt + curRemainingPrincipal)
                object.put("deficit", repaymentPlan.getCurRemainingInterestAmt().add(repaymentPlan.getRemainingPrincipal()));
                // 实还日期
                object.put("factReturnDate", repaymentPlan.getFactReturnDate()==null ? "":DateFormatUtils.format(repaymentPlan.getFactReturnDate(), "yyyy-MM-dd"));
                // 当期应还(包含服务费), 不包含服务费( averageCapital)
                object.put("returneterm", repaymentPlan.getAverageCapital());
                // 结清违约金
                object.put("penalty", ObjectUtils.toString(repaymentPlan.getPenalty()));
                list.add(object);
            }

            jsonObject.put("attachment", list);
        } catch (Exception e) {
            jsonObject = this.getResult("111111","获取还款计划失败!" );
            logger.error("获取还款计划:" + borrowNo + ",失败：" + e.getMessage(), e);
        }
        logger.info("获取还款计划:返回结果{}", JSON.toJSON(jsonObject));
        return jsonObject;
    }

}

