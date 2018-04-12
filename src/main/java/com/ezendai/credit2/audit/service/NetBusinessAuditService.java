package com.ezendai.credit2.audit.service;

import com.ezendai.credit2.audit.vo.GenerateContractVO;

/**
 * <pre>
 * 小企业贷网商贷合同生成
 * </pre>
 */
public interface NetBusinessAuditService {
	//生成合同
	public void createdContract(GenerateContractVO generateContractVO);
}
