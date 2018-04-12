package com.ezendai.credit2.audit.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezendai.credit2.apply.model.BusinessLog;
import com.ezendai.credit2.apply.model.Guarantee;
import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.model.Person;
import com.ezendai.credit2.apply.service.BusinessLogService;
import com.ezendai.credit2.apply.service.GuaranteeService;
import com.ezendai.credit2.apply.service.LoanService;
import com.ezendai.credit2.apply.service.PersonService;
import com.ezendai.credit2.apply.vo.GuaranteeVO;
import com.ezendai.credit2.apply.vo.LoanVO;
import com.ezendai.credit2.audit.dao.ApproveResultDao;
import com.ezendai.credit2.audit.model.ApproveResult;
import com.ezendai.credit2.audit.service.EduCreditAuditDetailsService;
import com.ezendai.credit2.audit.vo.ApproveResultVO;
import com.ezendai.credit2.framework.util.DateUtil;
import com.ezendai.credit2.framework.util.StringUtil;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.master.model.Blacklist;
import com.ezendai.credit2.master.model.RejectReason;
import com.ezendai.credit2.master.service.BlacklistService;
import com.ezendai.credit2.master.service.RejectReasonService;
import com.ezendai.credit2.system.model.SysLog;
import com.ezendai.credit2.system.service.SysLogService;

@Service
public class EduCreditAuditDetailsServiceImpl implements
		EduCreditAuditDetailsService {

	@Autowired
	private ApproveResultDao approveResultDao;

	@Autowired
	private LoanService loanService;

	@Autowired
	private PersonService personService;

	@Autowired
	private GuaranteeService guaranteeService;

	@Autowired
	private BusinessLogService businessLogService;

	@Autowired
	private SysLogService sysLogService;

	@Autowired
	private RejectReasonService rejectReasonService;

	@Autowired
	private BlacklistService blacklistService;


	@Transactional
	public void approve(ApproveResultVO approveVO, Long userId) {
		// TODO Auto-generated method stub
		Loan loan = loanService.get(approveVO.getLoanId());
		LoanVO loanVO = new LoanVO();
		Date formatDate = new Date();
		if (EnumConstants.ApproveResultState.APPROVAL.getValue().equals(
				approveVO.getState())) {// 同意
			ApproveResult ar = new ApproveResult();
			ar.setLoanId(approveVO.getLoanId());
			ar.setReason(approveVO.getReason());
//			ar.setState(approveVO.getState());
			ar.setRequestMoney(approveVO.getRequestMoney());
			ar.setTerm(approveVO.getTerm());
			ar.setContractMatters(approveVO.getContractMatters());
			checkMemberType(approveVO, ar);
			approveResultDao.insert(ar);

			loanVO.setId(approveVO.getLoanId());
			loanVO.setAuditDate(formatDate);
			loanVO.setAuditMoney(approveVO.getAuditMoney());
			loanVO.setAuditTime(Integer.valueOf(approveVO.getTerm()));
			loanVO.setStatus(EnumConstants.LoanStatus.终审中.getValue());
			if(loan.getStatus().equals(EnumConstants.LoanStatus.终审中.getValue())){
				loanVO.setStatus(EnumConstants.LoanStatus.合同签订.getValue());
			}else if(loan.getStatus().equals(EnumConstants.LoanStatus.终审退回门店.getValue())){
				loanVO.setStatus(EnumConstants.LoanStatus.门店重提.getValue());
			}else if(loan.getStatus().equals(EnumConstants.LoanStatus.终审退回初审.getValue())||loan.getStatus().equals(EnumConstants.LoanStatus.初审退回.getValue())){
				loanVO.setStatus(EnumConstants.LoanStatus.初审重提.getValue());
			}else if(loan.getStatus().equals(EnumConstants.LoanStatus.初审重提.getValue())){
				loanVO.setStatus(EnumConstants.LoanStatus.合同签订.getValue());
			}
			checkMemberType(approveVO, loanVO);
			loanService.update(loanVO);

			BusinessLog businessLog = new BusinessLog();
			
			businessLog
					.setFlowStatus(EnumConstants.BusinessLogStatus.TRIAL
							.getValue());
			if(loan.getStatus().equals(EnumConstants.LoanStatus.终审中.getValue())){
				businessLog
				.setFlowStatus(EnumConstants.BusinessLogStatus.FINAL.getValue());
			}else if(loan.getStatus().equals(EnumConstants.LoanStatus.终审退回门店.getValue())){
				businessLog.setFlowStatus(EnumConstants.BusinessLogStatus.STORE_AGAIN.getValue());
			}else if(loan.getStatus().equals(EnumConstants.LoanStatus.终审退回初审.getValue())||loan.getStatus().equals(EnumConstants.LoanStatus.初审退回.getValue())){
				businessLog.setFlowStatus(EnumConstants.BusinessLogStatus.TRIAL_AGAIN.getValue());
			}else if(loan.getStatus().equals(EnumConstants.LoanStatus.初审重提.getValue())){
				businessLog
				.setFlowStatus(EnumConstants.BusinessLogStatus.FINAL.getValue());
			}
			businessLog.setLoanId(loan.getId());
			String message="审批意见:初审通过"
					+ (approveVO.getContractMatters().equals("") ? ""
							: " ,签约事项：" + approveVO.getContractMatters());
			if(ar.getReason()!=null){
				message =message+(ar.getReason().equals("") ? ""
						: " ,备注：" + ar.getReason()) ;
			}
			if(loan.getStatus().equals(EnumConstants.LoanStatus.终审中.getValue())){
				message="审批意见:终审通过"
						+ (approveVO.getContractMatters().equals("") ? ""
								: " ,签约事项：" + approveVO.getContractMatters());
				if(ar.getReason()!=null){
					message =message+(ar.getReason().equals("") ? ""
							: " ,备注：" + ar.getReason()) ;
				}
			}else if(loan.getStatus().equals(EnumConstants.LoanStatus.终审退回门店.getValue())){
				message="审批意见:初审通过"
						+ (approveVO.getContractMatters().equals("") ? ""
								: " ,签约事项：" + approveVO.getContractMatters());
				if(ar.getReason()!=null){
					message =message+(ar.getReason().equals("") ? ""
							: " ,备注：" + ar.getReason()) ;
				}
			}else if(loan.getStatus().equals(EnumConstants.LoanStatus.终审退回初审.getValue())||loan.getStatus().equals(EnumConstants.LoanStatus.初审退回.getValue())){
				message="审批意见:初审通过"
						+ (approveVO.getContractMatters().equals("") ? ""
								: " ,签约事项：" + approveVO.getContractMatters());
				if(ar.getReason()!=null){
					message =message+(ar.getReason().equals("") ? ""
							: " ,备注：" + ar.getReason()) ;
				}
			}else if(loan.getStatus().equals(EnumConstants.LoanStatus.初审重提.getValue())){
				message="审批意见:终审通过"
						+ (approveVO.getContractMatters().equals("") ? ""
								: " ,签约事项：" + approveVO.getContractMatters());
				if(ar.getReason()!=null){
					message =message+(ar.getReason().equals("") ? ""
							: " ,备注：" + ar.getReason()) ;
				}
			}
			businessLog.setMessage(message);
			businessLogService.insert(businessLog);
			// 插入系统日志
			SysLog sysLog = new SysLog();
			sysLog.setOptModule(EnumConstants.OptionModule.FIRST_TRIAL
					.getValue());
			sysLog.setOptType(EnumConstants.OptionType.FIRST_TRIAL_APPROVE.getValue());
			sysLog.setRemark("初审同意 借款ID   " + loan.getId().toString());
			if(loan.getStatus().equals(EnumConstants.LoanStatus.终审中.getValue())){
				sysLog.setOptModule(EnumConstants.OptionModule.FINAL_TRIAL
						.getValue());
				sysLog.setOptType(EnumConstants.OptionType.FINAL_TRIAL_APPROVE.getValue());
				sysLog.setRemark("终审同意 借款ID   " + loan.getId().toString());
			}else if(loan.getStatus().equals(EnumConstants.LoanStatus.终审退回门店.getValue())){
				sysLog.setOptModule(EnumConstants.OptionModule.FIRST_TRIAL
						.getValue());
				sysLog.setOptType(EnumConstants.OptionType.FIRST_TRIAL_APPROVE.getValue());
				sysLog.setRemark("初审同意 借款ID   " + loan.getId().toString());
			}else if(loan.getStatus().equals(EnumConstants.LoanStatus.终审退回初审.getValue())||loan.getStatus().equals(EnumConstants.LoanStatus.初审退回.getValue())){
				sysLog.setOptModule(EnumConstants.OptionModule.FIRST_TRIAL
						.getValue());
				sysLog.setOptType(EnumConstants.OptionType.FIRST_TRIAL_APPROVE.getValue());
				sysLog.setRemark("初审同意 借款ID   " + loan.getId().toString());
			}else if(loan.getStatus().equals(EnumConstants.LoanStatus.初审重提.getValue())){
				sysLog.setOptModule(EnumConstants.OptionModule.FINAL_TRIAL
						.getValue());
				sysLog.setOptType(EnumConstants.OptionType.FINAL_TRIAL_APPROVE.getValue());
				sysLog.setRemark("终审同意 借款ID   " + loan.getId().toString());
			}
			sysLogService.insert(sysLog);
		} else if (EnumConstants.ApproveResultState.REFUSE_TO.getValue()
				.equals(approveVO.getState())) {// 拒绝
			// 黑名单数据保存
			RejectReason rejectReason = rejectReasonService.get(approveVO
					.getRefuseSecondReasonId());
			saveBlacklist(rejectReason, approveVO, loan);
			ApproveResult ar = new ApproveResult();
			ar.setLoanId(approveVO.getLoanId());
//			ar.setState(approveVO.getState());
			ar.setReason(approveVO.getReason());
			ar.setReason1(rejectReason.getParent().getReason());
			ar.setReason2(rejectReason.getReason());
			approveResultDao.insert(ar);
			loanVO.setId(approveVO.getLoanId());
			loanVO.setAuditDate(formatDate);
			loanVO.setStatus(EnumConstants.LoanStatus.初审拒绝.getValue());
			if(approveVO.getStatus()!=null){
				if(approveVO.getStatus().equals("3")){
					loanVO.setStatus(EnumConstants.LoanStatus.终审拒绝.getValue());
				}
			}
			loanService.update(loanVO);

			BusinessLog businessLog = new BusinessLog();   
			businessLog
					.setFlowStatus(EnumConstants.BusinessLogStatus.TRIAL_REFUSE
							.getValue());
			businessLog.setLoanId(loan.getId());
			String message="审批意见:初审拒绝,一级原因："
					+ rejectReason.getParent().getReason() + ",二级原因："
					+ rejectReason.getReason();
			if(ar.getReason()!=null){
				message =message+(ar.getReason().equals("") ? ""
						: " ,备注：" + ar.getReason()) ;
			}
			businessLog.setMessage(message);
			if(approveVO.getStatus()!=null){
				if(approveVO.getStatus().equals("3")){
					message="审批意见:终审拒绝,一级原因："
							+ rejectReason.getParent().getReason() + ",二级原因："
							+ rejectReason.getReason();
					if(approveVO.getReason()!=null){
						message =message+(approveVO.getReason().equals("") ? ""
								: " ,备注：" + approveVO.getReason()) ;
					}
				businessLog.setMessage(message);
				businessLog
				.setFlowStatus(EnumConstants.BusinessLogStatus.FINAL_REFUSE
						.getValue());
				}
			}
			businessLogService.insert(businessLog);
			// 插入系统日志
			SysLog sysLog = new SysLog();
			sysLog.setOptModule(EnumConstants.OptionModule.FIRST_TRIAL
					.getValue());
			sysLog.setOptType(EnumConstants.OptionType.FIRST_TRIAL_REFUSE.getValue());
			sysLog.setRemark("初审拒绝 借款ID   " + loan.getId().toString());
			if(approveVO.getStatus()!=null){
				if(approveVO.getStatus().equals("3")){
					sysLog.setOptModule(EnumConstants.OptionModule.FINAL_TRIAL
							.getValue());
					sysLog.setOptType(EnumConstants.OptionType.FINAL_TRIAL_REFUSE.getValue());
					sysLog.setRemark("终审拒绝 借款ID   " + loan.getId().toString());
				}
			}
			sysLogService.insert(sysLog);
		} else if (EnumConstants.ApproveResultState.RETURN.getValue().equals(
				approveVO.getState())) {// 退回
			ApproveResult ar = new ApproveResult();
			ar.setLoanId(approveVO.getLoanId());
//			ar.setState(approveVO.getState());
			ar.setContractMatters(approveVO.getContractMatters());
			ar.setReason(approveVO.getReason());
			approveResultDao.insert(ar);
			loanVO.setId(approveVO.getLoanId());
			loanVO.setAuditDate(formatDate);
			if(approveVO.getStatus()!=null){
				if(approveVO.getStatus().equals("1")){
					loanVO.setStatus(EnumConstants.LoanStatus.终审退回门店.getValue());
				}else if(approveVO.getStatus().equals("2")){
					loanVO.setStatus(EnumConstants.LoanStatus.终审退回初审.getValue());
				}else if(approveVO.getStatus().equals("3")){
					loanVO.setStatus(EnumConstants.LoanStatus.初审退回.getValue());
				}
			}
			loanService.update(loanVO);
			BusinessLog businessLog = new BusinessLog();
			if(approveVO.getStatus()!=null){
				if(approveVO.getStatus().equals("1")){
					businessLog.setFlowStatus(EnumConstants.BusinessLogStatus.FINAL_RETURN_STORE.getValue());
				}else if(approveVO.getStatus().equals("2")){
					businessLog.setFlowStatus(EnumConstants.BusinessLogStatus.FINAL_RETURN_TRIAL.getValue());
				}else if(approveVO.getStatus().equals("3")){
					businessLog.setFlowStatus(EnumConstants.BusinessLogStatus.TRIAL_RETURN.getValue());
				}
			}
			businessLog.setLoanId(loan.getId());
			String message="审批意见:信审退回,退回原因："
					+ ar.getContractMatters();
			if(ar.getReason()!=null){
				message =message+(ar.getReason().equals("") ? ""
						: " ,备注：" + ar.getReason()) ;
			}
			if(approveVO.getStatus()!=null){
				if(approveVO.getStatus().equals("1")){
					message="审批意见:终审退回门店,退回原因："
							+ ar.getContractMatters();
					if(ar.getReason()!=null){
						message =message+(ar.getReason().equals("") ? ""
								: " ,备注：" + ar.getReason()) ;
					}
				}else if(approveVO.getStatus().equals("2")){
					message="审批意见:终审退回初审,退回原因："
							+ ar.getContractMatters();
					if(ar.getReason()!=null){
						message =message+(ar.getReason().equals("") ? ""
								: " ,备注：" + ar.getReason()) ;
					}
				}else if(approveVO.getStatus().equals("3")){
					message="审批意见:初审退回,退回原因："
							+ ar.getContractMatters();
					if(ar.getReason()!=null){
						message =message+(ar.getReason().equals("") ? ""
								: " ,备注：" + ar.getReason()) ;
					}
				}
			}
			businessLog.setMessage(message);
			businessLogService.insert(businessLog);
			// 插入系统日志
			SysLog sysLog = new SysLog();
	
			if(approveVO.getStatus()!=null){
				if(approveVO.getStatus().equals("1")){
					sysLog.setOptModule(EnumConstants.OptionModule.FINAL_TRIAL
							.getValue());
					sysLog.setOptType(EnumConstants.OptionType.FINAL_TRIAL_RETURN_TOPORT.getValue());
					sysLog.setRemark("退回门店    借款ID   " + loan.getId().toString());
				}else if(approveVO.getStatus().equals("2")){
					sysLog.setOptModule(EnumConstants.OptionModule.FINAL_TRIAL
							.getValue());
					sysLog.setOptType(EnumConstants.OptionType.FINAL_TRIAL_RETURN_TOFIRST.getValue());
					sysLog.setRemark("退回初审    借款ID   " + loan.getId().toString());
				}else if(approveVO.getStatus().equals("3")){
					sysLog.setOptModule(EnumConstants.OptionModule.FIRST_TRIAL
							.getValue());
					sysLog.setOptType(EnumConstants.OptionType.FIRST_TRIAL_RETURN.getValue());
					sysLog.setRemark("初审退回    借款ID   " + loan.getId().toString());
				}
			}
			sysLogService.insert(sysLog);
		}
	}

	private void checkMemberType(ApproveResultVO approveVO, ApproveResult ar) {
		if (approveVO.getAuditMemberType() != null) {
			ar.setAuditMemberType(approveVO.getAuditMemberType());
		}

	}

	private void checkMemberType(ApproveResultVO approveVO, LoanVO loanVO) {
		if (approveVO.getAuditMemberType() != null) {
			loanVO.setAuditMemberType(approveVO.getAuditMemberType());
		}

	}

	/**
	 * <pre>
	 * 黑名单客户数据保存
	 * </pre>
	 * 
	 * @param rejectReason
	 * @param approveVO
	 * @param loan
	 */
	private void saveBlacklist(RejectReason rejectReason,
			ApproveResultVO approveVO, Loan loan) {
		// 获取二级原因
		Person person = personService.get(loan.getPersonId());
		GuaranteeVO queryGuaranteeVO = new GuaranteeVO();
		queryGuaranteeVO.setPersonId(person.getId());
		queryGuaranteeVO.setLoan(loan);
		queryGuaranteeVO.setFlag(EnumConstants.YesOrNo.YES.getValue());
		Guarantee guarantee = guaranteeService.get(queryGuaranteeVO);
		Blacklist bl = new Blacklist();
		bl.setName(person.getName());
		bl.setIdnum(person.getIdnum());
		bl.setRejectTime(new Date());
		bl.setMphone(person.getMobilePhone());
		bl.setTel(person.getHomePhone());
		bl.setSalesDepartmentId(loan.getSalesDeptId());
		if (loan.getProductType().equals(
				EnumConstants.ProductType.SE_LOAN.getValue())) {
			bl.setLoanType(EnumConstants.ProductType.SE_LOAN.getValue());
		} else if (loan.getProductType().equals(
				EnumConstants.ProductType.CAR_LOAN.getValue())) {
			bl.setLoanType(EnumConstants.ProductType.CAR_LOAN.getValue());
		}
		bl.setLimitDays(rejectReason.getCanRequestDays());
		if (guarantee != null
				&& !StringUtil.isEmpty(guarantee.getCompanyFullName())) {
			bl.setCompany(guarantee.getCompanyFullName());
		}
		bl.setComeFrom(EnumConstants.BlacklistComeFrom.AUDIT.getValue());
		bl.setRejectReasonId(rejectReason.getId());
		bl.setIsDeleted(EnumConstants.YesOrNo.NO.getValue());
		bl.setRemark("黑名单客户生成,一级原因：" + rejectReason.getParent().getReason()
				+ ",二级原因：" + rejectReason.getReason());
		blacklistService.insert(bl);
	}
}
