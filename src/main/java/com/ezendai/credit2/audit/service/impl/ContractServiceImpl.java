package com.ezendai.credit2.audit.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezendai.credit2.apply.model.BusinessLog;
import com.ezendai.credit2.apply.service.BusinessLogService;
import com.ezendai.credit2.apply.service.ExtensionService;
import com.ezendai.credit2.apply.service.LoanService;
import com.ezendai.credit2.apply.vo.ExtensionVO;
import com.ezendai.credit2.apply.vo.LoanVO;
import com.ezendai.credit2.audit.dao.ContractDao;
import com.ezendai.credit2.audit.model.ApproveResult;
import com.ezendai.credit2.audit.model.Contract;
import com.ezendai.credit2.audit.service.ApproveResultService;
import com.ezendai.credit2.audit.service.ContractService;
import com.ezendai.credit2.audit.vo.ContractVO;
import com.ezendai.credit2.master.enumerate.EnumConstants;
import com.ezendai.credit2.sign.lcb.dao.IContractGenerateDao;
import com.ezendai.credit2.sign.lcb.model.LcbModel;
import com.ezendai.credit2.system.model.SysLog;
import com.ezendai.credit2.system.service.SysLogService;

@Service
public class ContractServiceImpl implements ContractService {
    @Autowired
    private ContractDao contractDao;
    @Autowired
    private  LoanService loanService;
    @Autowired
    private ApproveResultService aproveResultService;
    @Autowired
    private SysLogService sysLogService;
    @Autowired
	private BusinessLogService businessLogService;
    @Autowired
	private ExtensionService extensionService;
    @Autowired
    private IContractGenerateDao contractGenerateDao; 
    
    
    @Transactional
    @Override
    public Contract insertSelective(Contract contract) {
        return contractDao.insert(contract);
    }

    @Transactional
	@Override
	public void deleteContractByLoanId(Long loanId) {
		contractDao.deleteContractByLoanId(loanId);
	}
    /**
	 * 
	 * <pre>
	 * 合同签约操作
	 * 
	 * loan状态修改为 合同确认（70），更新合同签约时间为当前时间（sign_date）
	 * 插入审核日志approve_result(状态：合同签订，日志内容(reason)：合同签订)
	 * 插入业务日志表：日志内容：操作者，状态，操作时间，日志内容(状态 =40  日志内容是：合同签订确认，状态由合同签订->合同确认)
	 * 插入系统日志表：操作模块 9 ，操作类型 903，日志内容 合同签约
	 * </pre>
	 *
	 * @param loan
	 */
    @Override
    @Transactional
    public int signContract(Long loanId){
    	LoanVO loanVO = new LoanVO();
    	loanVO.setId(loanId);
		loanVO.setStatus(EnumConstants.LoanStatus.合同确认.getValue());
//		loanVO.setSignDate(new Date());
		loanService.update(loanVO);
		
		ApproveResult approveResult = new ApproveResult();
		approveResult.setLoanId(loanId);	
		approveResult.setState(EnumConstants.ApproveResultState.CONTRACT_SIGN.getValue());
		approveResult.setReason("合同签约");
		aproveResultService.insert(approveResult);		
		
		BusinessLog businessLog = new BusinessLog();
		businessLog.setFlowStatus(EnumConstants.BusinessLogStatus.CONTRACT_SIGN.getValue());
		businessLog.setLoanId(loanId);
		businessLog.setMessage("合同签约");
		businessLogService.insert(businessLog);
		//插入系统日志  
		SysLog sysLog = new SysLog();			
		sysLog.setOptModule(EnumConstants.OptionModule.CONTRACT.getValue());
		sysLog.setOptType(EnumConstants.OptionType.CONTRACT_SIGNED.getValue());
		sysLog.setRemark("合同签约 借款ID：  "+loanId);
		sysLogService.insert(sysLog);
		return 1;
    }
    
    

    @Override
    @Transactional
    public void signExtensionContract(Long loanId){
    	

		ExtensionVO extensionVO = new ExtensionVO();
		extensionVO.setId(loanId);
		extensionVO.setSignDate(new Date());
		extensionVO.setStatus(EnumConstants.LoanStatus.展期合同确认.getValue());
		extensionService.update(extensionVO);
		
		
		ApproveResult approveResult = new ApproveResult();
		approveResult.setLoanId(loanId);	
		approveResult.setState(EnumConstants.ApproveResultState.CONTRACT_SIGN.getValue());
		approveResult.setReason("合同签约");
		aproveResultService.insert(approveResult);		
		
		BusinessLog businessLog = new BusinessLog();
		businessLog.setFlowStatus(EnumConstants.BusinessLogStatus.CONTRACT_SIGN.getValue());
		businessLog.setLoanId(loanId);
		businessLog.setMessage("合同签约");
		businessLogService.insert(businessLog);
		//插入系统日志  
		SysLog sysLog = new SysLog();			
		sysLog.setOptModule(EnumConstants.OptionModule.CONTRACT.getValue());
		sysLog.setOptType(EnumConstants.OptionType.CONTRACT_SIGNED.getValue());
		sysLog.setRemark("合同签约 展期借款ID：  "+loanId);
		sysLogService.insert(sysLog);
    }
	/**
	 * 
	 * <pre>
	 * 合同提交操作
	 * 
	 * 将loan状态修改为 合同确认（70），更新合同签约时间为当前时间（sign_date）
	 * 插入审核日志approve_result：状态：合同签订，日志内容(reason)：合同签订
	 * 插入业务日志表：日志内容：操作者，状态，操作时间，日志内容（状态 =50  日志内容是：合同签订确认，状态由loan的状态->合同确认）
	 * 插入系统日志表：操作模块 9 ，操作类型 904，日志内容 合同提交
	 * </pre>
	 *
	 * @param loan
	 */
    @Override
    @Transactional
    public int submitContract(Long loanId){

		
		ApproveResult approveResult = new ApproveResult();
		approveResult.setLoanId(loanId);	
		approveResult.setState(EnumConstants.ApproveResultState.CONTRACT_SIGN.getValue());
		approveResult.setReason("合同签约提交");
		aproveResultService.insert(approveResult);		
		
		BusinessLog businessLog = new BusinessLog();
		businessLog.setFlowStatus(EnumConstants.BusinessLogStatus.SIGN_SUBMIT.getValue());
		businessLog.setLoanId(loanId);
		businessLog.setMessage("合同签约提交");
		businessLogService.insert(businessLog);
		
    	LoanVO loanVO = new LoanVO();
    	loanVO.setId(loanId);
		loanVO.setStatus(EnumConstants.LoanStatus.合同确认.getValue());
//		loanVO.setSignDate(new Date()); 
		loanService.update(loanVO);
		
		//插入系统日志  
		SysLog sysLog = new SysLog();			
		sysLog.setOptModule(EnumConstants.OptionModule.CONTRACT.getValue());
		sysLog.setOptType(EnumConstants.OptionType.CONTRACT_SIGNED.getValue());
		sysLog.setRemark("合同提交  借款ID：  "+loanId);
		sysLogService.insert(sysLog);
		return 1;
    }
    
    @Override
    @Transactional
    public void submitExtensionContract(Long loanId){
    	
    	ApproveResult approveResult = new ApproveResult();
		approveResult.setLoanId(loanId);	
		approveResult.setState(EnumConstants.ApproveResultState.CONTRACT_SIGN.getValue());
		approveResult.setReason("展期合同签约提交");
		aproveResultService.insert(approveResult);		
		
		BusinessLog businessLog = new BusinessLog();
		businessLog.setFlowStatus(EnumConstants.BusinessLogStatus.SIGN_SUBMIT.getValue());
		businessLog.setLoanId(loanId);
		businessLog.setMessage("展期合同签约提交");
		businessLogService.insert(businessLog);
		
		
		ExtensionVO extensionVO = new ExtensionVO();
		extensionVO.setId(loanId);
		extensionVO.setSignDate(new Date());
		extensionVO.setStatus(EnumConstants.LoanStatus.展期合同确认.getValue());
		extensionService.update(extensionVO);
		
		//插入系统日志  
		SysLog sysLog = new SysLog();			
		sysLog.setOptModule(EnumConstants.OptionModule.CONTRACT.getValue());
		sysLog.setOptType(EnumConstants.OptionType.CONTRACT_SIGNED.getValue());
		sysLog.setRemark("展期合同提交  借款ID：  "+loanId);
		sysLogService.insert(sysLog);
    }
	/**
	 * 
	 * <pre>
	 * 根据合同编号或者loanID查询合同
	 * </pre>
	 *
	 * @param contractVo
	 * @return
	 */
    @Override
    public Contract getContractByParams(ContractVO contarctVo){
    	return contractDao.getContractByParams(contarctVo);
    }
    /***
     * 
     * <pre>
     * 获取根据参数合同
     * </pre>
     *
     * @param ContractVo
     * @return
     */
    @Override
    public List<Contract> findContractByVO(ContractVO ContractVo){
    	return contractDao.findListByVo(ContractVo);
    	
    }

	@Override
	public LcbModel getPersonInfoByLoanId(String loanId) {
		return contractGenerateDao.getPersonInfoByLoanId(loanId);
	}

}
