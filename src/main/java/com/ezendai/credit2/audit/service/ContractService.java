package com.ezendai.credit2.audit.service;

import java.util.List;

import com.ezendai.credit2.audit.model.Contract;
import com.ezendai.credit2.audit.vo.ContractVO;
import com.ezendai.credit2.sign.lcb.model.LcbModel;

public interface ContractService {
    Contract insertSelective(Contract record);
    
    void deleteContractByLoanId(Long loanId);
    /***
     * 
     * <pre>
     * 获取根据参数合同
     * </pre>
     *
     * @param ContractVo
     * @return
     */
	List<Contract> findContractByVO(ContractVO ContractVo);
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
	int signContract(Long loanId);
	
	
	
	/**
	 * <pre>
	 * 展期合同签约操作
	 * </pre>
	 *
	 * @param loanId
	 */
	void signExtensionContract(Long loanId);
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
	int submitContract(Long loanId);
	
	
	/**
	 * <pre>
	 * 展期合同提交操作
	 * </pre>
	 *
	 * @param loanId
	 */
	void submitExtensionContract(Long loanId);
	/**
	 * 
	 * <pre>
	 * 根据合同编号或者loanID查询合同
	 * </pre>
	 *
	 * @param contractVo
	 * @return
	 */
	Contract getContractByParams(ContractVO contarctVo);
	
	/**
	 * description:通过loanId获取客户信息
	 * autor:ym10159
	 * date:2018年1月12日 下午5:58:59
	 * @param loanId 借款ID
	 * @return LcbModel
	 */
	LcbModel getPersonInfoByLoanId(String loanId);
}
