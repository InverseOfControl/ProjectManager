package com.ezendai.credit2.audit.service;

import com.ezendai.credit2.audit.vo.GenerateContractVO;

/**
 * 
 * <pre>
 * 新的合同生成服务,用来生成小企业贷的新产品:同城小微贷和同城POS贷
 * </pre>
 *
 * @author 00226557
 * @version $Id: ContractAuditService.java, v 0.1 2015年6月26日 下午5:03:42 00226557 Exp $
 */

public interface CityWideContractAuditService {

	//生成合同
	public void createdContract(GenerateContractVO generateContractVO);
	
}
