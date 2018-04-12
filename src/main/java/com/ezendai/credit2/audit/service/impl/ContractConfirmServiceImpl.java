package com.ezendai.credit2.audit.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezendai.credit2.apply.model.BusinessLog;
import com.ezendai.credit2.apply.model.Extension;
import com.ezendai.credit2.apply.model.Loan;
import com.ezendai.credit2.apply.service.BusinessLogService;
import com.ezendai.credit2.apply.service.ExtensionService;
import com.ezendai.credit2.apply.service.LoanService;
import com.ezendai.credit2.apply.vo.ExtensionVO;
import com.ezendai.credit2.apply.vo.LoanVO;
import com.ezendai.credit2.audit.model.ApproveResult;
import com.ezendai.credit2.audit.service.ApproveResultService;
import com.ezendai.credit2.audit.service.ContractConfirmService;
import com.ezendai.credit2.finance.model.Ledger;
import com.ezendai.credit2.finance.service.LedgerService;
import com.ezendai.credit2.framework.exception.BusinessException;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.master.model.RejectReason;
import com.ezendai.credit2.master.service.RejectReasonService;
import com.ezendai.credit2.system.model.SysLog;
import com.ezendai.credit2.system.service.SysLogService;

@Service
public class ContractConfirmServiceImpl implements ContractConfirmService {
	@Autowired
	private LoanService loanService;
	@Autowired
	private ExtensionService extensionService;
	@Autowired
	private ApproveResultService aproveResultService;
	@Autowired
	private BusinessLogService businessLogService;
	@Autowired
	private SysLogService sysLogService;
	@Autowired
	private RejectReasonService rejectReasonService;
	@Autowired
	private LedgerService ledgerService;

	@Transactional
	@Override
	public void confirm(Long loanId) {
		Loan loan = loanService.get(loanId);
		//校验loan状态（STATE）是否是 合同确认（70）
		if (loan.getStatus().compareTo(EnumConstants.LoanStatus.合同确认.getValue()) != 0) {
			throw new BusinessException("借款状态不是合同确认");
		}
		LoanVO loanVO = new LoanVO();
		loanVO.setId(loanId);
		loanVO.setStatus(EnumConstants.LoanStatus.财务审核.getValue());
		loanVO.setContractConfirmDate(new Date());
		loanService.update(loanVO);

		ApproveResult approveResult = new ApproveResult();
		approveResult.setLoanId(loanId);
		approveResult.setState(EnumConstants.ApproveResultState.CONTRACT_CONFIRM.getValue());
		approveResult.setReason("合同确认");
		aproveResultService.insert(approveResult);

		BusinessLog businessLog = new BusinessLog();
		businessLog.setFlowStatus(EnumConstants.BusinessLogStatus.CONTRACT_CONFIRM.getValue());
		businessLog.setLoanId(loanId);
		businessLog.setMessage("合同确认");
		businessLogService.insert(businessLog);

		//插入系统日志  
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.CONTRACT_CONFIRM.getValue());
		sysLog.setOptType(EnumConstants.OptionType.CONTRACT_CONFIRM.getValue());
		sysLog.setRemark("合同确认  借款ID： " + loanId);
		sysLogService.insert(sysLog);
	}

	/** 
	 * <pre>
	 * 展期合同确认
	 * </pre>
	 * @param loanId
	 * @see com.ezendai.credit2.audit.service.ContractConfirmService#extensionConfirm(java.lang.Long)
	 */
	@Transactional
	@Override
	public void extensionConfirm(Long extensionId) {
		Extension extension = extensionService.get(extensionId);
		if(extension.getStatus().compareTo(EnumConstants.LoanStatus.展期合同确认.getValue()) != 0) {
			throw new BusinessException("借款状态不是合同确认");
		}
		// 1.更改extension的状态
		ExtensionVO extensionVO = new ExtensionVO();
		extensionVO.setId(extensionId);
		extensionVO.setStatus(EnumConstants.LoanStatus.正常.getValue());
		extensionVO.setContractConfirmDate(new Date());
		extensionService.update(extensionVO);
		
		// 2.生成总账记录
		Ledger externalAccountLedger = new Ledger();
		externalAccountLedger.setVersion(1L);
		externalAccountLedger.setAccountId(extensionId);
		externalAccountLedger.setType(EnumConstants.AccountType.EXTERNAL_ACCOUNT.getValue());
		externalAccountLedger.setLoanAmount(extension.getAuditMoney());
		externalAccountLedger = ledgerService.insert(externalAccountLedger);
		
		ApproveResult approveResult = new ApproveResult();
		approveResult.setLoanId(extensionId);
		approveResult.setState(EnumConstants.ApproveResultState.EXTENSION_CONTRACT_CONFIRM.getValue());
		approveResult.setReason("展期合同确认");
		aproveResultService.insert(approveResult);

		BusinessLog businessLog = new BusinessLog();
		businessLog.setFlowStatus(EnumConstants.BusinessLogStatus.EXTENSION_CONTRACT_CONFIRM.getValue());
		businessLog.setLoanId(extensionId);
		businessLog.setMessage("展期合同确认");
		businessLogService.insert(businessLog);

		//插入系统日志  
		SysLog sysLog = new SysLog();
		sysLog.setOptModule(EnumConstants.OptionModule.EXTENSION_CONTRACT_CONFIRM.getValue());
		sysLog.setOptType(EnumConstants.OptionType.EXTENSION_CONTRACT_CONFIRM.getValue());
		sysLog.setRemark("展期合同确认  借款ID： " + extensionId);
		sysLogService.insert(sysLog);
	}
	
	@Transactional
	@Override
	public void refuse(Long loanId, String reason, Long refuseSecondReasonId, Integer extensionTime) {
		RejectReason rejectReason = rejectReasonService.get(refuseSecondReasonId);
		// 非展期
		if(extensionTime == 0) {
			Loan loan = loanService.get(loanId);
			//校验loan状态（STATE）是否是 合同确认（70）
			if (loan.getStatus().compareTo(EnumConstants.LoanStatus.合同确认.getValue()) != 0) {
				throw new BusinessException("借款状态不是合同确认");
			}
			LoanVO loanVO = new LoanVO();
			loanVO.setId(loanId);
			loanVO.setStatus(EnumConstants.LoanStatus.合同确认退回.getValue());
			loanVO.setContractBackDate(new Date());

			// 更新捞财宝借款状态 (终止借款)
			loanVO.setLcbBorrowStatus(EnumConstants.LoanBorrowStatus.终止借款.getValue());

			loanService.update(loanVO);
			
			ApproveResult ar = new ApproveResult();
			ar.setLoanId(loanId);
			ar.setState(EnumConstants.ApproveResultState.CONFIRMED_RETURN_CONTRACT.getValue());
			ar.setReason(reason);
			ar.setReason1(rejectReason.getParent().getReason());
			ar.setReason2(rejectReason.getReason());
			aproveResultService.insert(ar);

			BusinessLog businessLog = new BusinessLog();
			businessLog.setFlowStatus(EnumConstants.BusinessLogStatus.CONTRACT_CONFIRM_BACK.getValue());
			businessLog.setLoanId(loanId);
			businessLog.setMessage(String.format("合同确认退回 ,一级原因：" + rejectReason.getParent().getReason() + ",二级原因：" + rejectReason.getReason() + ",备注:" + reason));
			businessLogService.insert(businessLog);

			//插入系统日志  
			SysLog sysLog = new SysLog();
			sysLog.setOptModule(EnumConstants.OptionModule.CONTRACT_CONFIRM.getValue());
			sysLog.setOptType(EnumConstants.OptionType.CONTRACT_CONFIRM_BACK.getValue());
			sysLog.setRemark("合同确认退回  借款ID：  " + loanId);
			sysLogService.insert(sysLog);
		}
		// 展期
		else {
			Extension extension = extensionService.get(loanId);
			if(extension.getStatus().compareTo(EnumConstants.LoanStatus.展期合同确认.getValue()) != 0) {
				throw new BusinessException("借款状态不是展期合同确认");
			}
			ExtensionVO extensionVO = new ExtensionVO();
			extensionVO.setId(loanId);
			extensionVO.setStatus(EnumConstants.LoanStatus.展期合同确认退回.getValue());
			extensionVO.setContractBackDate(new Date());
			extensionService.update(extensionVO);
			
			ApproveResult ar = new ApproveResult();
			ar.setLoanId(loanId);
			ar.setState(EnumConstants.ApproveResultState.CONFIRMED_RETURN_EXTENSION_CONTRACT.getValue());
			ar.setReason(reason);
			ar.setReason1(rejectReason.getParent().getReason());
			ar.setReason2(rejectReason.getReason());
			aproveResultService.insert(ar);

			BusinessLog businessLog = new BusinessLog();
			businessLog.setFlowStatus(EnumConstants.BusinessLogStatus.EXTENSION_CONTRACT_CONFIRM_BACK.getValue());
			businessLog.setLoanId(loanId);
			businessLog.setMessage(String.format("展期合同确认退回 ,一级原因：" + rejectReason.getParent().getReason() + ",二级原因：" + rejectReason.getReason() + ",备注:" + reason));
			businessLogService.insert(businessLog);

			//插入系统日志  
			SysLog sysLog = new SysLog();
			sysLog.setOptModule(EnumConstants.OptionModule.CONTRACT_CONFIRM.getValue());
			sysLog.setOptType(EnumConstants.OptionType.EXTENSION_CONTRACT_CONFIRM_BACK.getValue());
			sysLog.setRemark("展期合同确认退回  借款ID：  " + loanId);
			sysLogService.insert(sysLog);
		}
	}

}
