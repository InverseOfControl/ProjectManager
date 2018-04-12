package com.ezendai.credit2.audit.assembler;

import com.ezendai.credit2.audit.model.ApproveResult;
import com.ezendai.credit2.audit.vo.ApproveResultVO;

/**
 * 
 * <pre>
 * 审批结果  VO/Model转换
 * </pre>
 *
 * @author zhangshihai
 * @version $Id: ApproveResultAssembler.java, v 0.1 2014年7月31日 下午3:06:42 zhangshihai Exp $
 */
public class ApproveResultAssembler {

	/**
	 * 
	 * <pre>
	 * Model转为VO
	 * </pre>
	 *
	 * @param approveResult
	 * @return
	 */
	public static ApproveResultVO transferModel2VO (ApproveResult approveResult) {
		if (approveResult == null) {
			return null;
		}
		
		ApproveResultVO approveResultVO = new ApproveResultVO();
		approveResultVO.setId(approveResult.getId());
		approveResultVO.setLoanId(approveResult.getLoanId());
		approveResultVO.setStatus(approveResult.getStatus());
		approveResultVO.setReason(approveResult.getReason());
		approveResultVO.setReason1(approveResult.getReason1());
		approveResultVO.setReason2(approveResult.getReason2());
		approveResultVO.setCertificates1(approveResult.getCertificates1());
		approveResultVO.setCertificates2(approveResult.getCertificates2());
		approveResultVO.setCertificates3(approveResult.getCertificates3());
		approveResultVO.setCreator(approveResult.getCreator());
		approveResultVO.setCreatorId(approveResult.getCreatorId());
		approveResultVO.setCreatedTime(approveResult.getCreatedTime());
		approveResultVO.setModifier(approveResult.getModifier());
		approveResultVO.setModifierId(approveResult.getModifierId());
		approveResultVO.setModifiedTime(approveResult.getModifiedTime());
		approveResultVO.setVersion(approveResult.getVersion());
		approveResultVO.setContractMatters(approveResult.getContractMatters());
		approveResultVO.setState(approveResult.getState());

		return approveResultVO;
	}
	
	/**
	 * 
	 * <pre>
	 * VO转为Model
	 * </pre>
	 *
	 * @param approveResultVO
	 * @return
	 */
	public static ApproveResult transferVO2Model (ApproveResultVO approveResultVO) {
		if (approveResultVO == null) {
			return null;
		}
		
		ApproveResult approveResult = new ApproveResult();
		approveResult.setId(approveResultVO.getId());
		approveResult.setLoanId(approveResultVO.getLoanId());
		approveResult.setStatus(approveResultVO.getStatus());
		approveResult.setReason(approveResultVO.getReason());
		approveResult.setReason1(approveResultVO.getReason1());
		approveResult.setReason2(approveResultVO.getReason2());
		approveResult.setCertificates1(approveResultVO.getCertificates1());
		approveResult.setCertificates2(approveResultVO.getCertificates2());
		approveResult.setCertificates3(approveResultVO.getCertificates3());
		approveResult.setCreator(approveResultVO.getCreator());
		approveResult.setCreatorId(approveResultVO.getCreatorId());
		approveResult.setCreatedTime(approveResultVO.getCreatedTime());
		approveResult.setModifier(approveResultVO.getModifier());
		approveResult.setModifierId(approveResultVO.getModifierId());
		approveResult.setModifiedTime(approveResultVO.getModifiedTime());
		approveResult.setVersion(approveResultVO.getVersion());
		approveResult.setContractMatters(approveResultVO.getContractMatters());
		approveResult.setState(approveResultVO.getState());
		return approveResult;
	}
}
