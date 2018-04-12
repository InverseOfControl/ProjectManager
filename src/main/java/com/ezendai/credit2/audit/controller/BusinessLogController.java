package com.ezendai.credit2.audit.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezendai.credit2.apply.model.BusinessLog;
import com.ezendai.credit2.apply.service.BusinessLogService;
import com.ezendai.credit2.apply.vo.BusinessLogVO;
import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.enumerate.EnumConstants;

@Controller
@RequestMapping("/audit/businessLog")
public class BusinessLogController {

	@Autowired
	private BusinessLogService businessLogService;
	
	@RequestMapping("/initDetail/{loanId}")
	public String initDetail(@PathVariable String loanId, Model model) {
		model.addAttribute("loanId", loanId);
		return "/audit/businessLog/detail";
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("/detail.json/{loanId}")
	@ResponseBody
	public Map detail(@PathVariable String loanId, Model model,int rows, int page) {
		Pager p = new Pager();
		p.setRows(rows);
		p.setPage(page);		
		BusinessLogVO vo = new BusinessLogVO();
		vo.setLoanId(Long.valueOf(loanId));
		Integer count = businessLogService.getCountByVO(vo);
		vo.setPager(p);
		Pager pager = businessLogService.findWithPg(vo);
		@SuppressWarnings("unchecked")
		List<BusinessLog> businessLogList = pager.getResultList();
		List<BusinessLogView> businessLogViewList = new ArrayList<BusinessLogView>();
		BusinessLogView businessLogView = new BusinessLogView();
		Integer userType = ApplicationContext.getUser().getUserType();

		for (BusinessLog businessLog : businessLogList) {
			businessLogView = transferModel2View(businessLog);
			if (businessLog.getFlowStatus().compareTo(EnumConstants.BusinessLogStatus.NEW.getValue()) == 0) {
				businessLogView.setFlowStatusView("新建");
			} else if (businessLog.getFlowStatus().compareTo(EnumConstants.BusinessLogStatus.SUBMIT.getValue()) == 0) {
				businessLogView.setFlowStatusView("提交");
			} else if (businessLog.getFlowStatus().compareTo(EnumConstants.BusinessLogStatus.APPROVE_AUDIT.getValue()) == 0) {
				businessLogView.setFlowStatusView("审贷会决议");
			}  else if (businessLog.getFlowStatus().compareTo(EnumConstants.BusinessLogStatus.TRIAL_ALLOC.getValue()) == 0) {
				if (userType.equals(EnumConstants.UserType.APPROVE.getValue()) || userType.equals(EnumConstants.UserType.AUDIT.getValue())) {//总部,信审
					businessLogView.setFlowStatusView("初审待分配");
					businessLogView.setMessage("初审待分配");
				}
				else
				{
					businessLogView.setFlowStatusView("审核中");
					businessLogView.setMessage("审核中");
					businessLogView.setOperator("");
				}
				
			}  else if (businessLog.getFlowStatus().compareTo(EnumConstants.BusinessLogStatus.Letter_trial.getValue()) == 0) {
				if (userType.equals(EnumConstants.UserType.APPROVE.getValue()) || userType.equals(EnumConstants.UserType.AUDIT.getValue())) {//总部,信审
					businessLogView.setFlowStatusView("信审分单");
					businessLogView.setMessage("信审分单");
				}
				else
				{
					businessLogView.setFlowStatusView("审核中");
					businessLogView.setMessage("审核中");
					businessLogView.setOperator("");
				}
				
			}
			else if (businessLog.getFlowStatus().compareTo(EnumConstants.BusinessLogStatus.TRIAL.getValue()) == 0) {
				if (userType.equals(EnumConstants.UserType.APPROVE.getValue()) || userType.equals(EnumConstants.UserType.AUDIT.getValue())) {//总部,信审
					businessLogView.setFlowStatusView("初审中");
				}
				else
				{
					businessLogView.setFlowStatusView("审核中");
					businessLogView.setMessage("审核中");
					businessLogView.setOperator("");
				}
			} else if (businessLog.getFlowStatus().compareTo(EnumConstants.BusinessLogStatus.TRIAL_HANGUP.getValue()) == 0) {
				if (userType.equals(EnumConstants.UserType.APPROVE.getValue()) || userType.equals(EnumConstants.UserType.AUDIT.getValue())) {//总部,信审
					businessLogView.setFlowStatusView("初审挂起");
				}
				else
				{
					businessLogView.setFlowStatusView("审核中");
					businessLogView.setMessage("审核中");
					businessLogView.setOperator("");
				}
			} else if (businessLog.getFlowStatus().compareTo(EnumConstants.BusinessLogStatus.CONTRACT_AUDIT.getValue()) == 0) {
				businessLogView.setFlowStatusView("合同生成");
			}else if (businessLog.getFlowStatus().compareTo(EnumConstants.BusinessLogStatus.FINAL.getValue()) == 0) {
				if (userType.equals(EnumConstants.UserType.APPROVE.getValue()) || userType.equals(EnumConstants.UserType.AUDIT.getValue())) {//总部,信审
					businessLogView.setFlowStatusView("终审中");
				}
				else
				{
					businessLogView.setFlowStatusView("审核中");
					businessLogView.setMessage("信审通过");
					businessLogView.setOperator("");
				}
			}else if (businessLog.getFlowStatus().compareTo(EnumConstants.BusinessLogStatus.TRIAL_REFUSE.getValue()) == 0) {
				if (userType.equals(EnumConstants.UserType.APPROVE.getValue()) || userType.equals(EnumConstants.UserType.AUDIT.getValue())) {//总部,信审
					businessLogView.setFlowStatusView("初审拒绝");
				}
				else
				{
					businessLogView.setFlowStatusView("信审拒绝");
					businessLogView.setMessage("审批意见:信审拒绝,"+businessLogView.getMessage().substring(businessLogView.getMessage().indexOf("一级原因"), businessLogView.getMessage().indexOf(",二级原因")));
					businessLogView.setOperator("");
				}
			}else if (businessLog.getFlowStatus().compareTo(EnumConstants.BusinessLogStatus.FINAL_REFUSE.getValue()) == 0) {
				if (userType.equals(EnumConstants.UserType.APPROVE.getValue()) || userType.equals(EnumConstants.UserType.AUDIT.getValue())) {//总部,信审
					businessLogView.setFlowStatusView("终审拒绝");
				}
				else
				{
					businessLogView.setFlowStatusView("信审拒绝");
					businessLogView.setMessage("审批意见:信审拒绝,"+businessLogView.getMessage().substring(businessLogView.getMessage().indexOf("一级原因"), businessLogView.getMessage().indexOf(",二级原因")));
					businessLogView.setOperator("");
				}
			} else if (businessLog.getFlowStatus().compareTo(EnumConstants.BusinessLogStatus.TRIAL_RETURN.getValue()) == 0) {
			 	if (userType.equals(EnumConstants.UserType.APPROVE.getValue()) || userType.equals(EnumConstants.UserType.AUDIT.getValue())) {//总部,信审
					businessLogView.setFlowStatusView("初审退回");
				}
				else
				{
					businessLogView.setFlowStatusView("信审退回");
					businessLogView.setMessage("审批意见:信审退回,"+businessLogView.getMessage().substring(businessLogView.getMessage().indexOf(",退回原因")));
					businessLogView.setOperator("");
				}
			} else if (businessLog.getFlowStatus().compareTo(EnumConstants.BusinessLogStatus.FINAL_RETURN_STORE.getValue()) == 0) {
				if (userType.equals(EnumConstants.UserType.APPROVE.getValue()) || userType.equals(EnumConstants.UserType.AUDIT.getValue())) {//总部,信审
					businessLogView.setFlowStatusView("终审退回门店");
				}
				else
				{
					businessLogView.setFlowStatusView("信审退回");
					businessLogView.setMessage("审批意见:信审退回,"+businessLogView.getMessage().substring(businessLogView.getMessage().indexOf(",退回原因")));
					businessLogView.setOperator("");
				}
			} else if (businessLog.getFlowStatus().compareTo(EnumConstants.BusinessLogStatus.FINAL_RETURN_TRIAL.getValue()) == 0) {
				if (userType.equals(EnumConstants.UserType.APPROVE.getValue()) || userType.equals(EnumConstants.UserType.AUDIT.getValue())) {//总部,信审
					businessLogView.setFlowStatusView("终审退回初审");
				}
				else
				{
					businessLogView.setFlowStatusView("审核中");
					businessLogView.setMessage("审批意见:信审退回,"+businessLogView.getMessage().substring(businessLogView.getMessage().indexOf(",退回原因")));
					businessLogView.setOperator("");
				}
			}else if (businessLog.getFlowStatus().compareTo(EnumConstants.BusinessLogStatus.CONTRACT_SIGN.getValue()) == 0) {
					businessLogView.setFlowStatusView("合同签约");
				
			} else if (businessLog.getFlowStatus().compareTo(EnumConstants.BusinessLogStatus.STORE_AGAIN.getValue()) == 0) {
				businessLogView.setFlowStatusView("门店重提");
			}  else if (businessLog.getFlowStatus().compareTo(EnumConstants.BusinessLogStatus.TRIAL_AGAIN.getValue()) == 0) {
				if (userType.equals(EnumConstants.UserType.APPROVE.getValue()) || userType.equals(EnumConstants.UserType.AUDIT.getValue())) {//总部,信审
					businessLogView.setFlowStatusView("初审重提");
				}
				else
				{
					businessLogView.setFlowStatusView("审核中");
					businessLogView.setMessage("审核中");
					businessLogView.setOperator("");
				}
			} else if (businessLog.getFlowStatus().compareTo(EnumConstants.BusinessLogStatus.SIGN_SUBMIT.getValue()) == 0) {
				businessLogView.setFlowStatusView("合同签约提交");
			}else if (businessLog.getFlowStatus().compareTo(EnumConstants.BusinessLogStatus.CONTRACT_CONFIRM.getValue()) == 0) {
				businessLogView.setFlowStatusView("合同确认");
			}else if (businessLog.getFlowStatus().compareTo(EnumConstants.BusinessLogStatus.CONTRACT_CONFIRM_BACK.getValue()) == 0) {
				businessLogView.setFlowStatusView("合同退回");
			}else if (businessLog.getFlowStatus().compareTo(EnumConstants.BusinessLogStatus.FINANCE_AUDIT.getValue()) == 0) {
				businessLogView.setFlowStatusView("财务审核");
			}else if (businessLog.getFlowStatus().compareTo(EnumConstants.BusinessLogStatus.FINANCE_RETURN.getValue()) == 0) {
				businessLogView.setFlowStatusView("财务审核退回");
			}else if (businessLog.getFlowStatus().compareTo(EnumConstants.BusinessLogStatus.FINANCE_GRANT.getValue()) == 0) {
				businessLogView.setFlowStatusView("财务放款");
			}else if (businessLog.getFlowStatus().compareTo(EnumConstants.BusinessLogStatus.FINANCE_GRANT_RETURN.getValue()) == 0) {
				businessLogView.setFlowStatusView("财务放款退回");
			}else if (businessLog.getFlowStatus().compareTo(EnumConstants.BusinessLogStatus.NORMAL.getValue()) == 0) {
				businessLogView.setFlowStatusView("正常");
			}else if (businessLog.getFlowStatus().compareTo(EnumConstants.BusinessLogStatus.TIME_OUT_CANCEL.getValue()) == 0) {
				businessLogView.setFlowStatusView("超时job取消");
			}else if(EnumConstants.BusinessLogStatus.CONTRACT_CANCELLED.getValue().compareTo(businessLog.getFlowStatus())==0){
				businessLogView.setFlowStatusView("借款取消");
			}else if(EnumConstants.BusinessLogStatus.EXTENSION_CONTRACT_CONFIRM.getValue().compareTo(businessLog.getFlowStatus())==0){
				businessLogView.setFlowStatusView("展期合同确认");
			}else if(EnumConstants.BusinessLogStatus.CANCEL_EXTENSION.getValue().compareTo(businessLog.getFlowStatus())==0){
				businessLogView.setFlowStatusView("展期取消");
			}
			businessLogViewList.add(businessLogView);
		}
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("total", count);
		result.put("rows", businessLogViewList);
		return result;
	}
	
	class BusinessLogView extends BusinessLog{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = -1292325213279532743L;
		private String flowStatusView;
		
		public String getFlowStatusView() {
			return flowStatusView;
		}

		public void setFlowStatusView(String flowStatusView) {
			this.flowStatusView = flowStatusView;
		}
		
	}
	
	public BusinessLogView transferModel2View(BusinessLog businessLog) {
		if (businessLog == null) {
			return null;
		}
		BusinessLogView businessLogView = new BusinessLogView();
		businessLogView.setOperator(businessLog.getOperator());
		businessLogView.setMessage(businessLog.getMessage());
		businessLogView.setCreateDate(businessLog.getCreateDate());
		businessLogView.setLoanId(businessLog.getLoanId());
		return businessLogView;
	}

}
