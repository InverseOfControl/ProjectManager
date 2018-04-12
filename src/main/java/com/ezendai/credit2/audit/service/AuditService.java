package com.ezendai.credit2.audit.service;

import java.util.List;
import java.util.Map;

import com.ezendai.credit2.apply.model.Extension;
import com.ezendai.credit2.apply.model.Vehicle;
import com.ezendai.credit2.apply.vo.VehicleVO;
import com.ezendai.credit2.audit.model.ApproveResult;
import com.ezendai.credit2.audit.vo.ApproveResultVO;
import com.ezendai.credit2.audit.vo.GenerateContractVO;
import com.ezendai.credit2.audit.vo.RepaymentPlanVO;

public interface AuditService {

	ApproveResult insertSelective(ApproveResult record);

	public List<ApproveResult> getApproveResultByLoanId(Long loanId);

	public Vehicle insertVehicleVO(VehicleVO vehicleVo);

	//是否可以生成合同
	public Boolean isCreate(Long loanId);
	
	//是否可以生成展期合同
	public Boolean isCreateExtension(Long loanId);

	//生成合同
	public void createdContract(GenerateContractVO generateContractVO);
	
	
	/**
	 * 
	 * <pre>
	 * 生成展期合同
	 * </pre>
	 *
	 * @param generateContractVO
	 */
	public void createdExtensionContract(GenerateContractVO generateContractVO);


	void approve(ApproveResultVO approveVO, Long userId);
	
	
	/**
	 * <pre>
	 * 展期签批
	 * </pre>
	 *
	 * @param approveVO
	 */
	void extensionApprove(ApproveResultVO approveVO);
	
	RepaymentPlanVO getExtensionRepaymentPlanFirstTerm(Extension extension);
    RepaymentPlanVO getExtensionRepaymentPlanFirstTermNew(Extension extension);
    
    /**
     * 生成合同  CAR_FINE_NEW_CAL_EXECUTE_TIME之后使用
     * @param generateContractVO
     */
  	public void createdContractNew(GenerateContractVO generateContractVO);
  	/**
	 * 
	 * <pre>
	 * 生成展期合同 CAR_FINE_NEW_CAL_EXECUTE_TIME之后使用
	 * </pre>
	 *
	 * @param generateContractVO
	 */
  	public void createdExtensionContractNew(GenerateContractVO generateContractVO);
  	/**
	 * 
	 * <pre>
	 * 展期的第一期还款计划CAR_FINE_NEW_CAL_EXECUTE_TIME之后使用
	 * </pre>
	 *
	 * @param generateContractVO
	 */
  	RepaymentPlanVO getExtensionRepaymentPlanFirstTermNewFresh(Extension extension);
  	
  	/**
  	 * 查询众安反欺诈返回的数据等级和结果
  	 */
  	Map<String,Object> getZongAnReturnData(String loanId);
  	
  	/***
  	 * 查询合同金额是否大于20W
  	 */
  	Map<String,Object> contractManeyOverProof(String name,String idNo,String loanId,String agreeMoneyInput,String agreeTimeComb);
}
