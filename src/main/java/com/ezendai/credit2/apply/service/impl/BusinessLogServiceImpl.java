package com.ezendai.credit2.apply.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezendai.credit2.apply.dao.BusinessLogDao;
import com.ezendai.credit2.apply.model.BusinessLog;
import com.ezendai.credit2.apply.service.BusinessLogService;
import com.ezendai.credit2.apply.vo.BusinessLogVO;
import com.ezendai.credit2.audit.model.BusinessLogView;
import com.ezendai.credit2.framework.bean.ApplicationContext;
import com.ezendai.credit2.framework.model.UserSession;
import com.ezendai.credit2.framework.util.Pager;
import com.ezendai.credit2.master.constant.BizConstants;
import com.ezendai.credit2.master.enumerate.EnumConstants;

@Service
public class BusinessLogServiceImpl implements BusinessLogService {
    @Autowired
    private BusinessLogDao businessLogDao;
    /**
     * 已经在接口中 businessLog.setOperator 
     * 已经在接口中 businessLog.setCreateDate
     * @param businessLog
     * @return
     * @see com.ezendai.credit2.apply.service.BusinessLogService#insert(com.ezendai.credit2.apply.model.BusinessLog)
     */
    @Transactional
    @Override
    public BusinessLog insert(BusinessLog businessLog) {
    	UserSession user = ApplicationContext.getUser();
    	if(user!=null){
    		businessLog.setOperator(user.getName());
    	}else{
    		businessLog.setOperator(BizConstants.SYSTEM_USER);
    	}
		businessLog.setCreateDate(new Date());    	
        return businessLogDao.insert(businessLog);
    }

    @Override
    public Pager findWithPg(BusinessLogVO businessLogVO) {
        return businessLogDao.findWithPg(businessLogVO);
    }

	@Override
	public Integer getCountByVO(BusinessLogVO businessLogVO) {
		return businessLogDao.getCountByVO(businessLogVO);
	}

	@Override
	public List<BusinessLog> getLogByVO(BusinessLogVO businessLogVO) {
		// TODO Auto-generated method stub
		return businessLogDao.getLogByVO(businessLogVO);
	}

	@Override
	public List<BusinessLogView> findWihtPgService(BusinessLogVO businessLogVO) {
		BusinessLogVO vo = new BusinessLogVO();
		vo.setLoanId(Long.valueOf(businessLogVO.getLoanId()));
		@SuppressWarnings("unchecked")
		List<BusinessLog> businessLogList = this.getLogByVO(vo);
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
		return businessLogViewList;
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
		businessLogView.setFlowStatus(businessLog.getFlowStatus());
		return businessLogView;
	}

}
