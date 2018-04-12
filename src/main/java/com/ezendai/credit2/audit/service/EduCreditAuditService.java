package com.ezendai.credit2.audit.service;

import com.ezendai.credit2.audit.vo.GenerateContractVO;

/**
 * <pre>
 * 助学贷合同生成
 * </pre>
 */
public interface EduCreditAuditService {
	//生成合同
	public void createdContract(GenerateContractVO generateContractVO);
}
